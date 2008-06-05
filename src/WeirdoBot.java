
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import java.util.Set;
import org.jdom.JDOMException;

public class WeirdoBot implements Runnable {

    public static final String elizaUrl = "http://test.weewar.com/api1/";
    public static final String myAiName = "aiPluto";
    String username = null;
    String token = null;
    public static Eliza eliza;

    public WeirdoBot(String u, String t) {
        username = u;
        token = t;
        eliza = new Eliza(elizaUrl, username, token);
    }
    Map<String, List<String>> buildOptions = new HashMap();
    

    {
        List<String> base = new LinkedList<String>();
        base.add("Trooper");
        base.add("Trooper");
        base.add("Trooper");
        base.add("Trooper");
        base.add("Trooper");
        base.add("Trooper");
        base.add("Heavy Trooper");
        base.add("Raider");
        base.add("Raider");
        base.add("Tank");
        base.add("Tank");
        base.add("Heavy Tank");
        base.add("Light Artillery");
        base.add("Light Artillery");
        base.add("Light Artillery");
        base.add("Light Artillery");
        base.add("Light Artillery");
        buildOptions.put("Base", base);
    }
    private static Random random = new Random();

    /**
     * generate numbers from 1 to n including
     * @param n
     */
    public static int dice(int n) {
        return random.nextInt(n) + 1;
    }
    public static final int botanicTroublesId = 5;

    @Override
    public void run() {
        while (true) {
            try {

                Collection<Game> games = eliza.headquarterGames();

                for (Game game : games) {
                    int mapId = eliza.getGameMapId(game.getId());
                    System.out.print("." + game.getId() + ".");

                    // accept only invites to Botanic Troubles (map 5)
                    if (game.isRequiringAnInviteAccept()) {
                        if (mapId == botanicTroublesId) {
                            System.out.println("  .. accepting invitation to botanic troubles");
                            eliza.acceptInvite(game.getId());
                        } else {
                            // System.out.println(" .. declining invitation");
                            // eliza.declineInvite(gameId);
                        }
                    } else if (game.isInNeedOfAttention()) {
                        playTurn(game);
                    }
                }

                Thread.sleep(2000);

            /* // use to pause the bot so you can see what it did
            System.out.print("-Hit Enter to continue...");
            // Read a line of text from the user.
            BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
            String input = stdin.readLine();  */

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JDOMException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void playHardCode(Faction faction, Game game, int round) throws IOException, JDOMException, InterruptedException {

        Instruction roundInstructions[] = BotanicMoves.earlyTurns[round - 1];

        if (faction == game.getFactions().get(1)) { // AI is red
            // redMoves();
            } else { // AI is blue
            // blueMoves();
            }

        // loop through all the instructions for this round
        for (int i = 0; i < roundInstructions.length; i++) {
            System.out.println("#" + i + " move: " + roundInstructions[i].startPosition + " to: " + roundInstructions[i].endPosition);
            String action = roundInstructions[i].action;  // the action variable stores what to do for each instruction
            boolean doCapture = action.equals("capture") ? true : false;
            if (!action.equals("stay") && !action.equals("capture")) {
                //build what action is set to
                String x = eliza.build(game.getId(), roundInstructions[i].startPosition, action);
                System.out.println("     .... building " + action + " " + x);
            } else {
                // if not building, then will move or capture
                // repairing is probably not necessary in the early rounds
                String m = eliza.moveAttackCapture(game.getId(), roundInstructions[i].startPosition, roundInstructions[i].endPosition, null, doCapture);
                System.out.println("   ..Just moved/captured: " + m);
            }
        }
    }

    private void playTurn(Game game) throws IOException, JDOMException, InterruptedException {

        int gameId = game.getId();
        System.out.println("  Getting Game and Map info: " + gameId);
        // gets the game state, which is the position of all players' units and bases
        Game detailedGameState = eliza.getParsedGameState(gameId);
        // gets the map info, which has the terrain names of each position, as well as the starting base and unit postions
        WeewarMap mapInfo = eliza.getMap(detailedGameState.getMapId());
        // gets this bot's unit, base info, etc from the game state
        Faction myFaction = detailedGameState.getFactionByPlayerName(username);

        // uncomment the next line to play with new functionality
        // runExampleCode(detailedGameState, myFaction);

        int currentRound = detailedGameState.getRound();
        if (currentRound < 5) {

            // do pre-programmed strategy
            System.out.println("     " + "doing hard-coded strategy in round: " + currentRound);
            playHardCode(myFaction, detailedGameState, currentRound);

            // the hard code is really fast since there are no other server calls.
            // adding this delay will help the browser notice that the ai's turn is over
            Thread.sleep(1200);

        } else { //loops through all the units and either repairs, captures, or moves and attacks
            System.out.println("  .. moving my dudes. ");
            for (Unit unit : myFaction.getUnits()) {

                //** log data
                System.out.println("-----------------------------------------------------------------------------");
                System.out.println("     " + unit.getType() + "(" + unit.getQuantity() + ") on " + unit.getCoordinate());
                doUnitAction(game, detailedGameState, mapInfo, myFaction, unit);
            }

            // build units
            buildUnits(detailedGameState, myFaction);
        }

        // end the turn
        String fin = eliza.finishTurn(gameId);
        System.out.println("  .. finishing Turn => " + fin);
    }

    private void doUnitAction(Game game, Game detailedGameState, WeewarMap mapInfo, Faction myFaction, Unit unit) throws IOException, JDOMException {

        int gameId = game.getId();

        //** repair if quantity below 5
        if (!unit.isFinished() && unit.getQuantity() < 5) {
            repairUnit(gameId, unit);
            return;
        }

        // request movement coordinates
        List<Coordinate> possibleMovementCoordinates = eliza.getMovementCoords(gameId, unit.getCoordinate(), unit.getType());
        Collections.shuffle(possibleMovementCoordinates);
        // add no-move as a possibility
        possibleMovementCoordinates.add(0, unit.getCoordinate());

        // go for a capture if possible
        tryCapture(possibleMovementCoordinates, mapInfo, detailedGameState, myFaction, unit);


        // get coordinates of all enemy units
        List<Coordinate> targets = getTargets(detailedGameState, mapInfo, myFaction);
        // distance from current coordinate to nearest target
        int minDistance = minDistance(unit.getCoordinate(), targets);

        int n = 5; // number of movements to consider next

        if (minDistance <= 5 && !unit.isFinished()) {

            // Different moving spots that will be evaluated 

            // check for possible attack targets from one of the targets
            for (int i = 0; i < n && i < possibleMovementCoordinates.size(); i++) {
                Coordinate c = possibleMovementCoordinates.get(i);
                System.out.println("     " + ".. checking movement Option :" + c + " ");
                Coordinate a = getAttackCoodinate(detailedGameState, unit, c);
                System.out.println("     " + "..  1attack coord :" + a + " ");
                if (a != null && detailedGameState.getUnit(c) == null) {
                    String m = eliza.moveAttackCapture(gameId, unit.getCoordinate(), c, a, false);
                    System.out.println("     " + ".. 1moving to " + c + " attacking " + a + " =>" + m);
                    if (c != null) {
                        unit.setCoordinate(c);
                    }
                    unit.setFinished(true);
                    break;
                }
            }
        }

        if (!unit.isFinished() && possibleMovementCoordinates.size() > 1) {

            List<Coordinate> cities = getEnemyCities(detailedGameState, mapInfo, myFaction);
            Collections.shuffle(targets);
            Collections.shuffle(cities);
            possibleMovementCoordinates.remove(0);

            while (possibleMovementCoordinates.size() > 5) {
                possibleMovementCoordinates.remove(5);
            }
            while (cities.size() > 3) {
                cities.remove(3);
            }
            while (targets.size() > 3) {
                targets.remove(3);
            }
            boolean cnt = true;
            while (cnt) {
                System.out.println("     " + ".. possible movement options: " + possibleMovementCoordinates);
                System.out.println("     " + ".. possible Targets: " + targets);
                Coordinate c;

                if (unit.getType().equals("Trooper")) {
                    c = getClosest(possibleMovementCoordinates, cities);
                } else {
                    c = getClosest(possibleMovementCoordinates, targets);
                    if (c.equals(unit.getCoordinate()) && targets.isEmpty() && possibleMovementCoordinates.size() > 1) {
                        c = possibleMovementCoordinates.get(1);
                    }
                }

                if (!c.equals(unit.getCoordinate()) && detailedGameState.getUnit(c) == null) {
                    String m = eliza.moveAttackCapture(gameId, unit.getCoordinate(), c, null, false);
                    System.out.println("     " + ".. moving to " + c + " =>" + m);
                    unit.setCoordinate(c);
                    cnt = false;
                } else {
                    possibleMovementCoordinates.remove(c);
                }
                cnt = cnt && possibleMovementCoordinates.size() > 0;
            }
        }

    //Thread.sleep( 300 );

    }

    private void buildUnits(Game gameState, Faction faction) throws IOException {

        System.out.println("  Credits: " + faction.getCredits());
        System.out.println("     Terrains :" + faction.getTerrains());
        if (faction.getCredits() >= 75) {
            // get all the AI's bases
            for (Terrain terrain : faction.getTerrains()) {
                System.out.println("     " + terrain.getType() + " on " + terrain.getCoordinate() + " finished: " + terrain.isFinished() + ", unit: " + (gameState.getUnit(terrain.getCoordinate()) != null));

                // if can build, base is not "finished", and no unit on the base
                if (faction.getCredits() >= 75 && !terrain.isFinished() && gameState.getUnit(terrain.getCoordinate()) == null) {
                    System.out.println("     " + terrain.getType() + " on " + terrain.getCoordinate());
                    List<String> options = copyStringList(buildOptions.get(terrain.getType()));

                    boolean repeat = true;
                    while (!options.isEmpty() && repeat) {
                        // pick random unit to build from list
                        int nd = dice(options.size());
                        String buildType = options.get(nd - 1);
                        String x = eliza.build(gameState.getId(), terrain.getCoordinate(), options.get(nd - 1));
                        System.out.println("     .... building " + options.get(nd - 1) + " " + x);
                        if (x.indexOf("<error>") == -1) {
                            faction.setCredits(faction.getCredits() - Specs.buildCost.get(buildType));
                            repeat = false;
                        } else // remove this build option
                        {
                            options.remove(nd - 1);
                        }
                    }
                }
            }
        }
    }

    private void repairUnit(int id, Unit unit) throws IOException {

        String m = eliza.repair(id, unit.getCoordinate());
        System.out.println("     " + ".. repairing => " + m);
        unit.setFinished(true);
    }

    private Coordinate getClosest(List<Coordinate> movementOptions, Collection<Coordinate> targets) {
        Coordinate best = null;
        int n = Integer.MAX_VALUE;
        for (Coordinate c : movementOptions) {
            if (best == null) {
                best = c;
            }
            for (Coordinate target : targets) {
                int d = c.getDistance(target);
                if (d < n) {
                    best = c;
                    n = d;
                }
            }
        }
        return best;
    }

    private int minDistance(Coordinate from, Collection<Coordinate> targets) {
        Coordinate best = null;
        int n = Integer.MAX_VALUE;
        for (Coordinate c : targets) {
            int d = from.getDistance(c);
            if (d < n) {
                n = d;
            }

        }
        return n;
    }

    private List<Coordinate> getTargets(Game game, WeewarMap mapInfo, Faction myFaction) {
        List<Coordinate> targets = new LinkedList<Coordinate>();
        for (Faction faction : game.getFactions()) {
            if (faction == myFaction) {
                continue;
            }
            for (Unit otherUnit : faction.getUnits()) {
                targets.add(otherUnit.getCoordinate());
            }
        }
        // TODO Auto-generated method stub
        return targets;
    }

    private List<Coordinate> getEnemyCities(Game game, WeewarMap mapInfo, Faction myFaction) {
        List<Coordinate> targets = new LinkedList<Coordinate>();
        Collection<Terrain> bases = mapInfo.getTerrainsByType("Base");
        for (Terrain base : bases) {
            if (game.getTerrainOwner(base.getCoordinate()) != myFaction) {
                targets.add(base.getCoordinate());

            // TODO Auto-generated method stub
            }
        }
        return targets;
    }

    private Coordinate matchFreeBase(List<Coordinate> coords, WeewarMap mapInfo, Game g, Faction myFaction) {

        //System.out.println("Coords:"+coords );
        for (Coordinate c : coords) {
            Terrain t = mapInfo.get(c);
            //System.out.println("type :"+t.getType() );
            Faction owner = g.getTerrainOwner(c);
            if (t.getType().equals("Base") && (owner == null || owner != myFaction) && g.getUnit(c) == null) {
                return c;
            }
        }
        return null;
    }

    private Coordinate getAttackCoodinate(Game game, Unit unit, Coordinate from) throws IOException, JDOMException {
        List<Coordinate> coords = eliza.getAttackCoords(game.getId(), from, unit.getType());
        System.out.println("   cords: " + coords);
        if (!coords.isEmpty()) {
            int n = dice(coords.size());
            return coords.get(n - 1);
        }
        return null;
    }

    private Coordinate getMovementCoordinate(Game game, Unit unit, List<Coordinate> possibleMovementCoordinates) throws IOException, JDOMException {
        List<Coordinate> coords = possibleMovementCoordinates;

        while (!coords.isEmpty()) {
            int n = dice(coords.size());


            Coordinate c = coords.get(n - 1);
            //System.out.println( "     "+".. trying to move to "+c+" =>"+ game.getUnit( c ));
            if (game.getUnit(c) == null) {
                return c;
            } else {
                coords.remove(c);
            }
        }
        return null;
    }

    /**
     * Copies a list of Strings to a new list.
     * 
     * @param l
     *            list to be copied
     * @return new list containing Strings of l, in the same order
     */
    private List<String> copyStringList(List<String> l) {
        List<String> list = new LinkedList<String>();
        for (String s : l) {
            list.add(s);
        }
        return list;
    }

    private void tryCapture(List<Coordinate> possibleMovementCoordinates, WeewarMap mapInfo, Game detailedGameState, Faction myFaction, Unit unit) throws IOException {

        if (!unit.isFinished() && unit.getType().contains("Trooper")) {
            Coordinate c = matchFreeBase(possibleMovementCoordinates, mapInfo, detailedGameState, myFaction);
            if (c != null) {
                String m = eliza.moveAttackCapture(detailedGameState.getId(), unit.getCoordinate(), c, null, true);
                unit.setCoordinate(c);
                System.out.println("     " + ".. moving to " + c + " and capturing =>" + m);
                unit.setFinished(true);
            }
        }

    }

    private void runExampleCode(Game detailedGameState, Faction myFaction) throws IOException, JDOMException {

        // will output all the unit stats for all the factions in a nice format
        showAllUnitStats(detailedGameState);

        // for testing rangedDirection function in Coordinate.java - still underconstruction
        Coordinate testCoord = new Coordinate(4, 4);
        Coordinate testCoord2 = new Coordinate(2, 0);
        System.out.println("direction test: " + testCoord.getRangedDirection(testCoord2));
        
        // for picking up the enemy faction - useful for comparing your unit stats to his, etc.
        Faction enemyFaction;
        if (detailedGameState.getFactions().get(0).getPlayerName().equals(myAiName)) {
            enemyFaction = detailedGameState.getFactions().get(1);
        } else {
            enemyFaction = detailedGameState.getFactions().get(0);
        }

        // use this to grab an ared of coordinates...
        List<Coordinate> circleCoords = testCoord.getCircle(2);
        System.out.println("circle: " + circleCoords);

        // which you can then use to get area-specific unit stats for particular factions
        UnitStats myAreaStats = new UnitStats(myFaction, circleCoords);
        UnitStats enemyAreaStats = new UnitStats(enemyFaction, circleCoords);

        // use these functions to ouput these types of specialized unit stats for testing
        showUnitStats(myAreaStats);
        showUnitStats(enemyAreaStats);

        // use compareUnitCounts to get the difference between your unit counts and your enemy's
        // a negative number will mean you have less of that unit that your enemy
        Map<String, Integer> unitCountDiff = myFaction.unitStats.compareUnitCounts(enemyFaction.unitStats.unitCount);
        // outputs the unitCountDiff
        for (String key : unitCountDiff.keySet()) {
            System.out.println(key + ": " + unitCountDiff.get(key));
        }
        
        // i also added a refresh functionality to eliza.java and faction.java
        // to see it in action, first output the ai's stats...
        showUnitStats(myFaction.unitStats);

        // then, playing as the enemy, damage some of the ai's units...
        // you can use this code to pause the bot while you do this
        System.out.print("-Hit Enter to refresh your faction...");
        // Read a line of text from the user.
        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
        String input = stdin.readLine();

        // then call the refresh funciton to get the updated unit stats
        int gameId = detailedGameState.getId();
        myFaction.refresh(gameId);
        showUnitStats(myFaction.unitStats);
    }

    private void showAllUnitStats(Game game) throws IOException {
        List<Faction> allFactions = game.getFactions();
        for (Faction faction : allFactions) {
            System.out.println("---- " + faction.getPlayerName() + "'s Unit Stats ----");
            System.out.println("   -- army values --");
            System.out.println("         strength:   " + faction.unitStats.getArmyStrength());
            System.out.println("         potential:  " + faction.unitStats.getArmyPotential());
            System.out.println("         health:     " + faction.unitStats.getArmyHealth());
            System.out.println("         unit count: " + faction.unitStats.getTotalUnitCount());
            System.out.println("   -- unit breakdown --");
            //Set<String> allUnitTypes = faction.unitStats.unitCount.keySet();
            for (String unitType : faction.unitStats.unitCount.keySet()) {
                System.out.println("         " + unitType + ": " + faction.unitStats.getUnitCount(unitType));
            }
            System.out.println("   -- base breakdown --");
            Set<String> allBaseTypes = faction.unitStats.baseCount.keySet();
            for (String baseType : allBaseTypes) {
                System.out.println("         " + baseType + ": " + faction.unitStats.getBaseCount(baseType));
            }
        }
    }

    private void showUnitStats(UnitStats stats) throws IOException {

        System.out.println("---- Unit Stats ----");
        System.out.println("   -- army values --");
        System.out.println("         strength:   " + stats.getArmyStrength());
        System.out.println("         potential:  " + stats.getArmyPotential());
        System.out.println("         health:     " + stats.getArmyHealth());
        System.out.println("         unit count: " + stats.getTotalUnitCount());
        System.out.println("   -- unit breakdown --");
        for (String unitType : stats.unitCount.keySet()) {
            System.out.println("         " + unitType + ": " + stats.getUnitCount(unitType));
        }
        System.out.println("   -- base breakdown --");
        Set<String> allBaseTypes = stats.baseCount.keySet();
        for (String baseType : allBaseTypes) {
            System.out.println("         " + baseType + ": " + stats.getBaseCount(baseType));
        }

    }

    public static void main(String argv[]) {

        Thread t = new Thread(new WeirdoBot(argv[0], argv[1]));
        t.start();
    }
}
