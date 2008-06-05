/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Matt Whorton
 */
import java.util.HashMap;
import java.util.Map;
import java.lang.Math;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

public class UnitStats {

    float armyStrength = 0;  //army strength equals unit quantity * cost
    int armyPotential = 0;   //army potential is the army strength if all units have full health (quantity = 10)
    float armyHealth = 0;    //health of the whole army equals strength/potential
    int totalUnits = 0;
    Map<String, Integer> unitCount = new HashMap<String, Integer>();
    Map<String, Integer> baseCount = new HashMap<String, Integer>();
    int perTurnCredits = 0;
    //  int currentBank = 0;    
    

    {
        unitCount.put("Trooper", 0);
        unitCount.put("Heavy Trooper", 0);
        unitCount.put("Raider", 0);
        unitCount.put("Tank", 0);
        unitCount.put("Heavy Tank", 0);
        unitCount.put("Light Artillery", 0);
        unitCount.put("Heavy Artillery", 0);
        unitCount.put("Capturing", 0);
    }
    

    {
        baseCount.put("Base", 0);
        baseCount.put("Airfield", 0);
        baseCount.put("Harbor", 0);
    }

    public UnitStats() {
    }

    public UnitStats(Faction faction, List<Coordinate> coords) {
        for (Coordinate coord : coords) {
            Unit unit = faction.getUnit(coord);
            if (unit != null) {
                addUnit(unit);
            }
        }
    }

    public void addUnit(Unit unit) {
        armyStrength += unit.getQuantity() / 10.0 * Specs.buildCost.get(unit.getType());
        armyPotential += Specs.buildCost.get(unit.getType());
        armyHealth = armyStrength / armyPotential * 100;
        unitCount.put(unit.getType(), unitCount.get(unit.getType()) + 1);
        totalUnits++;
    }

    public int getUnitCount(String unitType) {
        return unitCount.get(unitType);
    }

    public int getArmyStrength() {
        return Math.round(armyStrength);
    }

    public int getArmyPotential() {
        return armyPotential;
    }

    public int getArmyHealth() {
        return Math.round(armyHealth);
    }

    public int getTotalUnitCount() {
        return totalUnits;
    }

    public void addBase(Terrain base) {
        baseCount.put(base.getType(), baseCount.get(base.getType()) + 1);
    }

    public int getBaseCount(String baseType) {
        return baseCount.get(baseType);
    }

    public void setIncome(int creditsPerBase) {
        perTurnCredits = creditsPerBase * baseCount.get("Base");
    }

    public int getIncome() {
        return perTurnCredits;
    }

    // take the unit counts of your enemy and compares them to yours to see where you are defficient
    // subtracts the passed enemy unitCounts from yours and then sorts the counts in ascending order
    public LinkedHashMap<String, Integer> compareUnitCounts(Map<String, Integer> enemyStats) {

        Map<String, Integer> statsDifference = new HashMap<String, Integer>();

        for (String key : this.unitCount.keySet()) {
            statsDifference.put(key, this.unitCount.get(key) - enemyStats.get(key));
        }

        // sorts the new difference map so you can see where you are most defficient
        return sortHashMapByValues(statsDifference, true);
    }

    // this will sort a hashmap by its values.  a sortedmap can only be sorted by its keys
    // i got this algorithm from http://www.theserverside.com/discussions/thread.tss?thread_id=29569, by Tim Ringwood
    private LinkedHashMap<String, Integer> sortHashMapByValues(Map<String, Integer> passedMap, boolean ascending) {

        List<String> mapKeys = new ArrayList(passedMap.keySet());
        List<Integer> mapValues = new ArrayList(passedMap.values());
        Collections.sort(mapValues);
        Collections.sort(mapKeys);

        if (!ascending) {
            Collections.reverse(mapValues);
        }
        LinkedHashMap someMap = new LinkedHashMap();
        Iterator valueIt = mapValues.iterator();

        while (valueIt.hasNext()) {
            Object val = valueIt.next();
            Iterator keyIt = mapKeys.iterator();
            while (keyIt.hasNext()) {
                Object key = keyIt.next();
                if (passedMap.get(key).toString().equals(val.toString())) {
                    passedMap.remove(key);
                    mapKeys.remove(key);
                    someMap.put(key, val);
                    break;
                }
            }
        }

        return someMap;
    }
}

