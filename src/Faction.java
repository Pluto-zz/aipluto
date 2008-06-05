
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import org.jdom.JDOMException;

public class Faction {

    String state;
    String playerName;
    int credits;
    Collection<Unit> units = new LinkedList<Unit>();
    Collection<Terrain> terrains = new LinkedList<Terrain>();
    UnitStats unitStats = new UnitStats();

    public void refresh(int id) throws IOException, JDOMException {
        units = new LinkedList<Unit>();
        terrains = new LinkedList<Terrain>();
        unitStats = new UnitStats();
        WeirdoBot.eliza.refreshFaction(id, this);
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Collection<Unit> getUnits() {
        return units;
    }

    public Unit getUnit(Coordinate c) {
        for (Unit unit : units) {
            if (unit.getCoordinate().equals(c)) {
                return unit;
            }
        }
        return null;
    }

    public void setUnits(Collection<Unit> units) {
        this.units = units;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public Collection<Terrain> getTerrains() {
        return terrains;
    }

    public void setTerrains(Collection<Terrain> terrains) {
        this.terrains = terrains;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }
}
