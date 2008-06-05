/*
 * Specs.java
 * by spadequack
 * 
 * Last modified 2008-05-31, 9:40am
 * 
 * This class contains the Weewar unit and terrain specifications for
 * calculating battle damage and movement. All information is stored in maps.
 * (this may not have been the most elegant way to organize it...)
 * 
 */

import java.util.HashMap;
import java.util.Map;

public class Specs {

    /** Terrain attack specs (Name: (Type: Terrain Effect on Attack)) */
    static Map<String, Map<String, Integer>> terrainAttack = new HashMap<String, Map<String, Integer>>();
    

    static {
        Map<String, Integer> airfield = new HashMap<String, Integer>();
        airfield.put("soft", 2);
        airfield.put("hard", 0);
        airfield.put("air", 3);
        airfield.put("speedboat", 0);
        airfield.put("amphibic", 0);
        airfield.put("sub", -2);
        airfield.put("boat", 0);
        terrainAttack.put("Airfield", airfield);
    }
    

    static {
        Map<String, Integer> base = new HashMap<String, Integer>();
        base.put("soft", 2);
        base.put("hard", 0);
        base.put("air", 0);
        base.put("speedboat", 0);
        base.put("amphibic", 0);
        base.put("sub", 0);
        base.put("boat", 0);
        terrainAttack.put("Base", base);
    }
    

    static {
        Map<String, Integer> desert = new HashMap<String, Integer>();
        desert.put("soft", -1);
        desert.put("hard", 0);
        desert.put("air", 0);
        desert.put("speedboat", 0);
        desert.put("amphibic", 0);
        desert.put("sub", 0);
        desert.put("boat", 0);
        terrainAttack.put("Desert", desert);
    }
    

    static {
        Map<String, Integer> harbor = new HashMap<String, Integer>();
        harbor.put("soft", 2);
        harbor.put("hard", 0);
        harbor.put("air", 0);
        harbor.put("speedboat", 0);
        harbor.put("amphibic", 0);
        harbor.put("sub", -2);
        harbor.put("boat", 0);
        terrainAttack.put("Harbor", harbor);
    }
    

    static {
        Map<String, Integer> mountains = new HashMap<String, Integer>();
        mountains.put("soft", 2);
        mountains.put("hard", -10);
        mountains.put("air", 0);
        mountains.put("speedboat", 0);
        mountains.put("amphibic", 0);
        mountains.put("sub", 0);
        mountains.put("boat", 0);
        terrainAttack.put("Mountains", mountains);
    }
    

    static {
        Map<String, Integer> plains = new HashMap<String, Integer>();
        plains.put("soft", 0);
        plains.put("hard", 0);
        plains.put("air", 0);
        plains.put("speedboat", 0);
        plains.put("amphibic", 0);
        plains.put("sub", 0);
        plains.put("boat", 0);
        terrainAttack.put("Plains", plains);
    }
    

    static {
        Map<String, Integer> repairPatch = new HashMap<String, Integer>();
        repairPatch.put("soft", 0);
        repairPatch.put("hard", 0);
        repairPatch.put("air", 0);
        repairPatch.put("speedboat", 0);
        repairPatch.put("amphibic", 0);
        repairPatch.put("sub", 0);
        repairPatch.put("boat", 0);
        terrainAttack.put("Repair Patch", repairPatch);
    }
    

    static {
        Map<String, Integer> swamp = new HashMap<String, Integer>();
        swamp.put("soft", -1);
        swamp.put("hard", -1);
        swamp.put("air", 0);
        swamp.put("speedboat", 0);
        swamp.put("amphibic", 0);
        swamp.put("sub", 0);
        swamp.put("boat", 0);
        terrainAttack.put("Swamp", swamp);
    }
    

    static {
        Map<String, Integer> water = new HashMap<String, Integer>();
        water.put("soft", -10);
        water.put("hard", -10);
        water.put("air", 0);
        water.put("speedboat", 0);
        water.put("amphibic", 0);
        water.put("sub", 0);
        water.put("boat", 0);
        terrainAttack.put("Water", water);
    }
    

    static {
        Map<String, Integer> woods = new HashMap<String, Integer>();
        woods.put("soft", 2);
        woods.put("hard", 0);
        woods.put("air", 0);
        woods.put("speedboat", 0);
        woods.put("amphibic", 0);
        woods.put("sub", 0);
        woods.put("boat", 0);
        terrainAttack.put("Woods", woods);
    }
    /** Terrain defense specs (Name: (Type: Terrain Effect on Defense)) */
    static Map<String, Map<String, Integer>> terrainDefense = new HashMap<String, Map<String, Integer>>();
    

    static {
        Map<String, Integer> airfield = new HashMap<String, Integer>();
        airfield.put("soft", 2);
        airfield.put("hard", -1);
        airfield.put("air", 3);
        airfield.put("speedboat", 0);
        airfield.put("amphibic", -1);
        airfield.put("sub", -1);
        airfield.put("boat", -1);
        terrainDefense.put("Airfield", airfield);
    }
    

    static {
        Map<String, Integer> base = new HashMap<String, Integer>();
        base.put("soft", 2);
        base.put("hard", -1);
        base.put("air", 0);
        base.put("speedboat", 0);
        base.put("amphibic", 0);
        base.put("sub", 0);
        base.put("boat", 0);
        terrainDefense.put("Base", base);
    }
    

    static {
        Map<String, Integer> desert = new HashMap<String, Integer>();
        desert.put("soft", -1);
        desert.put("hard", 0);
        desert.put("air", 0);
        desert.put("speedboat", 0);
        desert.put("amphibic", 0);
        desert.put("sub", 0);
        desert.put("boat", 0);
        terrainDefense.put("Desert", desert);
    }
    

    static {
        Map<String, Integer> harbor = new HashMap<String, Integer>();
        harbor.put("soft", 2);
        harbor.put("hard", -1);
        harbor.put("air", 0);
        harbor.put("speedboat", 0);
        harbor.put("amphibic", -1);
        harbor.put("sub", -1);
        harbor.put("boat", -1);
        terrainDefense.put("Harbor", harbor);
    }
    

    static {
        Map<String, Integer> mountains = new HashMap<String, Integer>();
        mountains.put("soft", 4);
        mountains.put("hard", -10);
        mountains.put("air", 0);
        mountains.put("speedboat", 0);
        mountains.put("amphibic", 0);
        mountains.put("sub", 0);
        mountains.put("boat", 0);
        terrainDefense.put("Mountains", mountains);
    }
    

    static {
        Map<String, Integer> plains = new HashMap<String, Integer>();
        plains.put("soft", 0);
        plains.put("hard", 0);
        plains.put("air", 0);
        plains.put("speedboat", 0);
        plains.put("amphibic", 0);
        plains.put("sub", 0);
        plains.put("boat", 0);
        terrainDefense.put("Plains", plains);
    }
    

    static {
        Map<String, Integer> repairPatch = new HashMap<String, Integer>();
        repairPatch.put("soft", -6);
        repairPatch.put("hard", -6);
        repairPatch.put("air", 0);
        repairPatch.put("speedboat", 0);
        repairPatch.put("amphibic", 0);
        repairPatch.put("sub", 0);
        repairPatch.put("boat", 0);
        terrainDefense.put("Repair Patch", repairPatch);
    }
    

    static {
        Map<String, Integer> swamp = new HashMap<String, Integer>();
        swamp.put("soft", -2);
        swamp.put("hard", -2);
        swamp.put("air", 0);
        swamp.put("speedboat", 0);
        swamp.put("amphibic", 0);
        swamp.put("sub", 0);
        swamp.put("boat", 0);
        terrainDefense.put("Swamp", swamp);
    }
    

    static {
        Map<String, Integer> water = new HashMap<String, Integer>();
        water.put("soft", -10);
        water.put("hard", -10);
        water.put("air", 0);
        water.put("speedboat", 0);
        water.put("amphibic", 0);
        water.put("sub", 0);
        water.put("boat", 0);
        terrainDefense.put("Water", water);
    }
    

    static {
        Map<String, Integer> woods = new HashMap<String, Integer>();
        woods.put("soft", 3);
        woods.put("hard", -3);
        woods.put("air", 0);
        woods.put("speedboat", 0);
        woods.put("amphibic", 0);
        woods.put("sub", 0);
        woods.put("boat", 0);
        terrainDefense.put("Woods", woods);
    }
    /** Terrain movement specs (Name: (Type: Movement Cost)) */
    static Map<String, Map<String, Integer>> terrainMovement = new HashMap<String, Map<String, Integer>>();
    

    static {
        Map<String, Integer> airfield = new HashMap<String, Integer>();
        airfield.put("soft", 3);
        airfield.put("hard", 2);
        airfield.put("air", 3);
        airfield.put("speedboat", 99);
        airfield.put("amphibic", 3);
        airfield.put("sub", 99);
        airfield.put("boat", 99);
        terrainMovement.put("Airfield", airfield);
    }
    

    static {
        Map<String, Integer> base = new HashMap<String, Integer>();
        base.put("soft", 3);
        base.put("hard", 2);
        base.put("air", 3);
        base.put("speedboat", 99);
        base.put("amphibic", 3);
        base.put("sub", 99);
        base.put("boat", 99);
        terrainMovement.put("Base", base);
    }
    

    static {
        Map<String, Integer> desert = new HashMap<String, Integer>();
        desert.put("soft", 5);
        desert.put("hard", 4);
        desert.put("air", 3);
        desert.put("speedboat", 99);
        desert.put("amphibic", 3);
        desert.put("sub", 99);
        desert.put("boat", 99);
        terrainMovement.put("Desert", desert);
    }
    

    static {
        Map<String, Integer> harbor = new HashMap<String, Integer>();
        harbor.put("soft", 3);
        harbor.put("hard", 2);
        harbor.put("air", 3);
        harbor.put("speedboat", 3);
        harbor.put("amphibic", 3);
        harbor.put("sub", 3);
        harbor.put("boat", 3);
        terrainMovement.put("Harbor", harbor);
    }
    

    static {
        Map<String, Integer> mountains = new HashMap<String, Integer>();
        mountains.put("soft", 6);
        mountains.put("hard", 99);
        mountains.put("air", 3);
        mountains.put("speedboat", 99);
        mountains.put("amphibic", 99);
        mountains.put("sub", 99);
        mountains.put("boat", 99);
        terrainMovement.put("Mountains", mountains);
    }
    

    static {
        Map<String, Integer> plains = new HashMap<String, Integer>();
        plains.put("soft", 3);
        plains.put("hard", 3);
        plains.put("air", 3);
        plains.put("speedboat", 99);
        plains.put("amphibic", 3);
        plains.put("sub", 99);
        plains.put("boat", 99);
        terrainMovement.put("Plains", plains);
    }
    

    static {
        Map<String, Integer> repairPatch = new HashMap<String, Integer>();
        repairPatch.put("soft", 3);
        repairPatch.put("hard", 3);
        repairPatch.put("air", 3);
        repairPatch.put("speedboat", 99);
        repairPatch.put("amphibic", 3);
        repairPatch.put("sub", 99);
        repairPatch.put("boat", 99);
        terrainMovement.put("Repair Patch", repairPatch);
    }
    

    static {
        Map<String, Integer> swamp = new HashMap<String, Integer>();
        swamp.put("soft", 6);
        swamp.put("hard", 6);
        swamp.put("air", 3);
        swamp.put("speedboat", 99);
        swamp.put("amphibic", 3);
        swamp.put("sub", 99);
        swamp.put("boat", 99);
        terrainMovement.put("Swamp", swamp);
    }
    

    static {
        Map<String, Integer> water = new HashMap<String, Integer>();
        water.put("soft", 99);
        water.put("hard", 99);
        water.put("air", 3);
        water.put("speedboat", 3);
        water.put("amphibic", 3);
        water.put("sub", 3);
        water.put("boat", 3);
        terrainMovement.put("Water", water);
    }
    

    static {
        Map<String, Integer> woods = new HashMap<String, Integer>();
        woods.put("soft", 4);
        woods.put("hard", 6);
        woods.put("air", 3);
        woods.put("speedboat", 99);
        woods.put("amphibic", 99);
        woods.put("sub", 99);
        woods.put("boat", 99);
        terrainMovement.put("Woods", woods);
    }
    /** Unit attack specs (Name: (Type: Attack Strength)) */
    static Map<String, Map<String, Integer>> unitAttack = new HashMap<String, Map<String, Integer>>();
    

    static {
        Map<String, Integer> antiAircraft = new HashMap<String, Integer>();
        antiAircraft.put("soft", 8);
        antiAircraft.put("hard", 3);
        antiAircraft.put("air", 9);
        antiAircraft.put("speedboat", 3);
        antiAircraft.put("amphibic", 3);
        antiAircraft.put("sub", 0);
        antiAircraft.put("boat", 3);
        unitAttack.put("Anti Aircraft", antiAircraft);
    }
    

    static {
        Map<String, Integer> assualtArtillery = new HashMap<String, Integer>();
        assualtArtillery.put("soft", 8);
        assualtArtillery.put("hard", 6);
        assualtArtillery.put("air", 6);
        assualtArtillery.put("speedboat", 6);
        assualtArtillery.put("amphibic", 6);
        assualtArtillery.put("sub", 0);
        assualtArtillery.put("boat", 6);
        unitAttack.put("Assualt Artillery", assualtArtillery);
    }
    

    static {
        Map<String, Integer> battleship = new HashMap<String, Integer>();
        battleship.put("soft", 10);
        battleship.put("hard", 14);
        battleship.put("air", 6);
        battleship.put("speedboat", 14);
        battleship.put("amphibic", 14);
        battleship.put("sub", 4);
        battleship.put("boat", 14);
        unitAttack.put("Battleship", battleship);
    }
    

    static {
        Map<String, Integer> berserker = new HashMap<String, Integer>();
        berserker.put("soft", 14);
        berserker.put("hard", 16);
        berserker.put("air", 0);
        berserker.put("speedboat", 14);
        berserker.put("amphibic", 14);
        berserker.put("sub", 0);
        berserker.put("boat", 14);
        unitAttack.put("Berserker", berserker);
    }
    

    static {
        Map<String, Integer> bomber = new HashMap<String, Integer>();
        bomber.put("soft", 14);
        bomber.put("hard", 14);
        bomber.put("air", 0);
        bomber.put("speedboat", 14);
        bomber.put("amphibic", 14);
        bomber.put("sub", 0);
        bomber.put("boat", 14);
        unitAttack.put("Bomber", bomber);
    }
    

    static {
        Map<String, Integer> dfa = new HashMap<String, Integer>();
        dfa.put("soft", 16);
        dfa.put("hard", 14);
        dfa.put("air", 0);
        dfa.put("speedboat", 14);
        dfa.put("amphibic", 14);
        dfa.put("sub", 0);
        dfa.put("boat", 14);
        unitAttack.put("DFA", dfa);
    }
    

    static {
        Map<String, Integer> destroyer = new HashMap<String, Integer>();
        destroyer.put("soft", 10);
        destroyer.put("hard", 10);
        destroyer.put("air", 12);
        destroyer.put("speedboat", 12);
        destroyer.put("amphibic", 12);
        destroyer.put("sub", 16);
        destroyer.put("boat", 10);
        unitAttack.put("Destroyer", destroyer);
    }
    

    static {
        Map<String, Integer> heavyArtillery = new HashMap<String, Integer>();
        heavyArtillery.put("soft", 12);
        heavyArtillery.put("hard", 10);
        heavyArtillery.put("air", 10);
        heavyArtillery.put("speedboat", 10);
        heavyArtillery.put("amphibic", 10);
        heavyArtillery.put("sub", 0);
        heavyArtillery.put("boat", 10);
        unitAttack.put("Heavy Artillery", heavyArtillery);
    }
    

    static {
        Map<String, Integer> heavyTank = new HashMap<String, Integer>();
        heavyTank.put("soft", 10);
        heavyTank.put("hard", 12);
        heavyTank.put("air", 10);
        heavyTank.put("speedboat", 10);
        heavyTank.put("amphibic", 10);
        heavyTank.put("sub", 0);
        heavyTank.put("boat", 10);
        unitAttack.put("Heavy Tank", heavyTank);
    }
    

    static {
        Map<String, Integer> heavyTrooper = new HashMap<String, Integer>();
        heavyTrooper.put("soft", 6);
        heavyTrooper.put("hard", 8);
        heavyTrooper.put("air", 6);
        heavyTrooper.put("speedboat", 8);
        heavyTrooper.put("amphibic", 8);
        heavyTrooper.put("sub", 0);
        heavyTrooper.put("boat", 8);
        unitAttack.put("Heavy Trooper", heavyTrooper);
    }
    

    static {
        Map<String, Integer> helicopter = new HashMap<String, Integer>();
        helicopter.put("soft", 16);
        helicopter.put("hard", 10);
        helicopter.put("air", 6);
        helicopter.put("speedboat", 12);
        helicopter.put("amphibic", 12);
        helicopter.put("sub", 0);
        helicopter.put("boat", 8);
        unitAttack.put("Helicopter", helicopter);
    }
    

    static {
        Map<String, Integer> hovercraft = new HashMap<String, Integer>();
        hovercraft.put("soft", 10);
        hovercraft.put("hard", 6);
        hovercraft.put("air", 0);
        hovercraft.put("speedboat", 8);
        hovercraft.put("amphibic", 10);
        hovercraft.put("sub", 0);
        hovercraft.put("boat", 6);
        unitAttack.put("Hovercraft", hovercraft);
    }
    

    static {
        Map<String, Integer> jetfighter = new HashMap<String, Integer>();
        jetfighter.put("soft", 6);
        jetfighter.put("hard", 8);
        jetfighter.put("air", 16);
        jetfighter.put("speedboat", 6);
        jetfighter.put("amphibic", 6);
        jetfighter.put("sub", 0);
        jetfighter.put("boat", 6);
        unitAttack.put("Jetfighter", jetfighter);
    }
    

    static {
        Map<String, Integer> lightArtillery = new HashMap<String, Integer>();
        lightArtillery.put("soft", 10);
        lightArtillery.put("hard", 4);
        lightArtillery.put("air", 0);
        lightArtillery.put("speedboat", 4);
        lightArtillery.put("amphibic", 4);
        lightArtillery.put("sub", 0);
        lightArtillery.put("boat", 4);
        unitAttack.put("Light Artillery", lightArtillery);
    }
    

    static {
        Map<String, Integer> raider = new HashMap<String, Integer>();
        raider.put("soft", 10);
        raider.put("hard", 4);
        raider.put("air", 4);
        raider.put("speedboat", 4);
        raider.put("amphibic", 8);
        raider.put("sub", 0);
        raider.put("boat", 4);
        unitAttack.put("Raider", raider);
    }
    

    static {
        Map<String, Integer> speedboat = new HashMap<String, Integer>();
        speedboat.put("soft", 8);
        speedboat.put("hard", 6);
        speedboat.put("air", 6);
        speedboat.put("speedboat", 10);
        speedboat.put("amphibic", 16);
        speedboat.put("sub", 0);
        speedboat.put("boat", 6);
        unitAttack.put("Speedboat", speedboat);
    }
    

    static {
        Map<String, Integer> submarine = new HashMap<String, Integer>();
        submarine.put("soft", 0);
        submarine.put("hard", 0);
        submarine.put("air", 0);
        submarine.put("speedboat", 0);
        submarine.put("amphibic", 0);
        submarine.put("sub", 10);
        submarine.put("boat", 16);
        unitAttack.put("Submarine", submarine);
    }
    

    static {
        Map<String, Integer> tank = new HashMap<String, Integer>();
        tank.put("soft", 10);
        tank.put("hard", 7);
        tank.put("air", 0);
        tank.put("speedboat", 7);
        tank.put("amphibic", 7);
        tank.put("sub", 0);
        tank.put("boat", 7);
        unitAttack.put("Tank", tank);
    }
    

    static {
        Map<String, Integer> trooper = new HashMap<String, Integer>();
        trooper.put("soft", 6);
        trooper.put("hard", 3);
        trooper.put("air", 0);
        trooper.put("speedboat", 3);
        trooper.put("amphibic", 3);
        trooper.put("sub", 0);
        trooper.put("boat", 3);
        unitAttack.put("Trooper", trooper);
    }
    /** Unit defense specs (Name: Defense Strength) */
    static Map<String, Integer> unitDefense = new HashMap<String, Integer>();
    

    static {
        unitDefense.put("Anti Aircraft", 4);
        unitDefense.put("Assualt Artillery", 6);
        unitDefense.put("Battleship", 14);
        unitDefense.put("Berserker", 14);
        unitDefense.put("Bomber", 10);
        unitDefense.put("DFA", 14);
        unitDefense.put("Destroyer", 12);
        unitDefense.put("Heavy Artillery", 4);
        unitDefense.put("Heavy Tank", 14);
        unitDefense.put("Heavy Trooper", 6); // 2 when capturing
        unitDefense.put("Helicopter", 10);
        unitDefense.put("Hovercraft", 8);
        unitDefense.put("Jetfighter", 12);
        unitDefense.put("Light Artillery", 3);
        unitDefense.put("Raider", 8);
        unitDefense.put("Speedboat", 6);
        unitDefense.put("Submarine", 10);
        unitDefense.put("Tank", 10);
        unitDefense.put("Trooper", 6); // 2 when capturing
    }
    /** Unit mobility specs (Name: Movement Points) */
    static Map<String, Integer> unitMobility = new HashMap<String, Integer>();
    

    static {
        unitMobility.put("Anti Aircraft", 9);
        unitMobility.put("Assualt Artillery", 12);
        unitMobility.put("Battleship", 6);
        unitMobility.put("Berserker", 6);
        unitMobility.put("Bomber", 18); // then move 6
        unitMobility.put("DFA", 6);
        unitMobility.put("Destroyer", 12);
        unitMobility.put("Heavy Artillery", 6);
        unitMobility.put("Heavy Tank", 7);
        unitMobility.put("Heavy Trooper", 6);
        unitMobility.put("Helicopter", 15); // then move 3
        unitMobility.put("Hovercraft", 12);
        unitMobility.put("Jetfighter", 18); // then move 6
        unitMobility.put("Light Artillery", 9);
        unitMobility.put("Raider", 12);
        unitMobility.put("Speedboat", 12);
        unitMobility.put("Submarine", 9);
        unitMobility.put("Tank", 9);
        unitMobility.put("Trooper", 9);
    }
    /** Unit types (Name: Type) */
    static Map<String, String> unitTypes = new HashMap<String, String>();
    

    static {
        unitTypes.put("Anti Aircraft", "hard");
        unitTypes.put("Assualt Artillery", "hard");
        unitTypes.put("Battleship", "boat");
        unitTypes.put("Berserker", "hard");
        unitTypes.put("Bomber", "air");
        unitTypes.put("DFA", "hard");
        unitTypes.put("Destroyer", "boat");
        unitTypes.put("Heavy Artillery", "hard");
        unitTypes.put("Heavy Tank", "hard");
        unitTypes.put("Heavy Trooper", "soft");
        unitTypes.put("Helicopter", "air");
        unitTypes.put("Hovercraft", "amphibic");
        unitTypes.put("Jetfighter", "air");
        unitTypes.put("Light Artillery", "hard");
        unitTypes.put("Raider", "hard");
        unitTypes.put("Speedboat", "speedboat");
        unitTypes.put("Submarine", "sub");
        unitTypes.put("Tank", "hard");
        unitTypes.put("Trooper", "soft");
    }
    
    // Unit build costs
    static Map<String, Integer> buildCost = new HashMap<String, Integer>();    

    static {
        buildCost.put("Trooper", 75);
        buildCost.put("Heavy Trooper", 150);
        buildCost.put("Raider", 200);
        buildCost.put("Tank", 300);
        buildCost.put("Heavy Tank", 600);
        buildCost.put("Light Artillery", 200);
        buildCost.put("Heavy Artillery", 600);
    }
}
