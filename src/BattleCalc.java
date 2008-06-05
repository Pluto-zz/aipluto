/*
 * BattleCalc.java
 * by spadequack
 * 
 * Last modified 2008-05-31, 9:45am
 * 
 * This class contains functions for calculating the exact probabilities that a 
 * defending unit will end up with 0, 1, ..., 10 subunits after an attack.
 * 
 * For example: getProbabilities(8, "DFA", "Woods", 5, "Berserker", "Swamp", 2);
 * This finds out the probabilities that a 5-Berserker that is standing on Swamp
 * will have 0, 1, ..., 10 subunits left after being attacked by an 8-DFA 
 * standing on Woods that has an attack bonus (from previous attacks) of 2. The
 * result returned is:
 * [0=87.0, 1=13.0, 2=0.0, 3=0.0, 4=0.0, 5=0.0, 6=0.0, 7=0.0, 8=0.0, 9=0.0, 
 * 10=0.0] 
 * which means there is an 87% chance of the Berserker being completely
 * destroyed (0 subunits), a 13% chance of the Berserker having 1 subunit remain
 * and 0% chance of the Berserker having any other number of subunits remain.
 * 
 * Bonuses should be calculated beforehand as this class does not do that.
 * See http://weewar.com/specifications for more details.
 * 
 * This class also contains code for calculating the probabilities by 
 * simulation.
 * 
 * The code and concepts are based on Robert "bmaker" Nitsch's wwbattlelib.py.
 * 
 * Uses Specs.java
 * 
 */

import java.util.*;

public class BattleCalc {

    private static Random rand = new Random();
    /** Number of times to run a simulation */
    private static final int SIM_NUM = 1000;

    /**
     * Calculate factorial - not used because it overflows on factorial(x), x >
     * 20ish - here x can be 60.
     * 
     * @param x
     *            input number
     * @return factorial(x) = x*(x-1)*(x-2)*...*2*1
     */
    public static long factorial(int x) {
        // recursive
		/*
         * if (x > 1) return x * factorial(x - 1); else return 1;
         */
        // iterative
        long f = 1;
        for (int i = x; i > 1; i--) {
            f *= i;
        }
        return f;
    }

    /**
     * Calculates the probability mass function. (From binomial distribution.)
     * See http://en.wikipedia.org/wiki/Binomial_distribution and
     * http://en.wikipedia.org/wiki/Probability_mass_function
     * 
     * @param n
     *            number of independent trials
     * @param p
     *            probability of a success
     * @param k
     *            number of successes
     * @return the probability of getting exactly k successes in n trials
     */
    public static double binomial(int n, double p, int k) {
        if (p == 1.0 && n == k) {
            return 1.0;
        }
        return binomialCoeff2(n, k) * Math.pow(p, k) * Math.pow(1 - p, n - k);
    }

    /**
     * Calculates the binomial coefficient. Doesn't work for high numbers
     * because factorial creates an overflow. Thus, this is not used, but is
     * here for reference.
     * 
     * @param n
     * @param k
     * @return n choose k
     */
    public static double binomialCoeff(int n, int k) {
        return ((double) factorial(n)) / (factorial(n - k) * factorial(k));
    }

    /**
     * Calculates the binomial coefficient. Works for higher numbers. Based on
     * theorem that choose(n,k) = choose(n-1, k-1)*n/k Idea from:
     * http://blog.plover.com/math/choose.html
     * 
     * @param n
     * @param k
     * @return n choose k
     */
    public static double binomialCoeff2(int n, int k) {
        if (k > n / 2) {
            return binomialCoeff2(n, n - k);
        }
        if (k == 0) {
            return 1;
        }
        if (n == 0) {
            return 0;
        }
        return binomialCoeff2(n - 1, k - 1) * ((double) n) / k;
    }

    /**
     * Calculates the sum of binomial(n,p,start) to binomial(n,p,stop-1)
     * 
     * @param n
     *            number of independent trials
     * @param p
     *            probability of a success
     * @param start
     *            starting number of successes, inclusive
     * @param stop
     *            ending number of successes, exclusive
     * @return the sum of binomial(n,p,start) to binomial(n,p,stop-1)
     */
    public static double f(int n, double p, int start, int stop) {
        double sum = 0;
        for (int i = start; i < stop; i++) {
            sum += binomial(n, p, i);
        }
        return sum;
    }

    /**
     * Calculates the probability that one single hit succeeds. Also known as
     * 'p'. See http://weewar.com/specifications
     * 
     * @param a
     *            attack strength of the attacking unit
     * @param d
     *            defense strength of the defending unit
     * @param ta
     *            effect of the terrain that the attacking unit is on
     * @param td
     *            effect of the terrain that the defending unit is on
     * @param b
     *            gang-up bonus
     * @return probability that a single hit succeeds
     */
    public static double getP(int a, int d, int ta, int td, int b) {
        double p = 0.05 * (((a + ta) - (d + td)) + b) + 0.5;
        if (p < 0) {
            p = 0;
        } else if (p > 1) {
            p = 1;
        }
        return p;
    }

    // // Simulation Code ////
    /**
     * Determines how many hits occur by simulation.
     * 
     * @param atkSubunits
     *            subunits that the attacking unit has
     * @param a
     *            attack strength of the attacking unit
     * @param d
     *            defense strength of the defending unit
     * @param ta
     *            effect of the terrain that the attacking unit is on
     * @param td
     *            effect of the terrain that the defending unit is on
     * @param b
     *            gang-up bonus
     * @return the number of hits that occur
     */
    public static double calculateHits(int atkSubunits, int a, int d, int ta,
            int td, int b) {
        double p = getP(a, d, ta, td, b);
        int hits = 0;
        for (int i = 0; i < atkSubunits * 6; i++) {
            if ((rand.nextInt(100) + 1) < p * 100) {
                hits += 1;
            }
        }
        return hits / 6.0;
    }

    /**
     * Calculates the probability that the defending unit will have 0, 1, ...,
     * 10 subunits left after the attack, by simulation.
     * 
     * @param atkSubunits
     *            subunits that the attacking unit has
     * @param defSubunits
     *            subunits that the defending unit has
     * @param a
     *            attack strength of the attacking unit
     * @param d
     *            defense strength of the defending unit
     * @param ta
     *            effect of the terrain that the attacking unit is on
     * @param td
     *            effect of the terrain that the defending unit is on
     * @param b
     *            gang-up bonus
     * @return an array with the number of remaining subunits as the index and
     *         its probability as the value. Example: [0: 0.1, 1: 0.15, 2: ...]
     */
    public static double[] getProbabilitiesSim(int atkSubunits,
            int defSubunits, int a, int d, int ta, int td, int b) {
        double[] probabilities = new double[11];
        for (int i = 0; i < SIM_NUM; i++) {
            int remaining = defSubunits - (int) Math.floor(calculateHits(atkSubunits, a, d, ta, td,
                    b));
            if (remaining < 0) {
                remaining = 0;
            }
            probabilities[remaining] += 100.0 / SIM_NUM;
        }
        for (int i = 0; i < 11; i++) {
            probabilities[i] = (int) Math.round(probabilities[i]);
        }
        return probabilities;
    }

    /**
     * Calculates the probability that the defending unit will have 0, 1, ...,
     * 10 subunits left after the attack, by simulation.
     * 
     * @param atkSubunits
     *            subunits that the attacking unit has
     * @param atk
     *            name of the attacking unit
     * @param atkTerrain
     *            name of the terrain the attacking unit is on
     * @param defSubunits
     *            subunits that the defending unit has
     * @param def
     *            name of the defending unit
     * @param defTerrain
     *            name of the terrain the defending unit is on
     * @param bonus
     *            gang-up bonus
     * @return an array with the number of remaining subunits as the index and
     *         its probability as the value. Example: [0: 0.1, 1: 0.15, 2: ...]
     */
    public static double[] getProbabilitiesSim(int atkSubunits, String atk,
            String atkTerrain, int defSubunits, String def, String defTerrain,
            int bonus) {
        String atkType = Specs.unitTypes.get(atk);
        String defType = Specs.unitTypes.get(def);
        double[] probs = getProbabilitiesSim(atkSubunits, defSubunits,
                Specs.unitAttack.get(atk).get(defType), Specs.unitDefense.get(def), Specs.terrainAttack.get(atkTerrain).get(
                atkType), Specs.terrainDefense.get(defTerrain).get(
                defType), bonus);
        return probs;
    }

    // // Exact Calculation Code ////
    /**
     * Calculates the probability that the defending unit will have 0, 1, ...,
     * 10 subunits left after the attack. See http://weewar.com/specifications
     * 
     * @param atkSubunits
     *            subunits that the attacking unit has
     * @param defSubunits
     *            subunits that the defending unit has
     * @param a
     *            attack strength of the attacking unit
     * @param d
     *            defense strength of the defending unit
     * @param ta
     *            effect of the terrain that the attacking unit is on
     * @param td
     *            effect of the terrain that the defending unit is on
     * @param b
     *            gang-up bonus
     * @return an array with the number of remaining subunits as the index and
     *         its probability as the value. Example: [0: 0.1, 1: 0.15, 2: ...]
     */
    public static double[] getProbabilities(int atkSubunits, int defSubunits,
            int a, int d, int ta, int td, int b) {
        int n = atkSubunits * 6;
        double p = getP(a, d, ta, td, b);
        p -= 0.01; // to account for r < (not <=) p. (always 1% chance to fail)
        double[] probabilities = new double[11];

        // (0 to 5 hits = 0 subunits lost, 6 to 11 hits = 1 subunit lost, ...
        // 54 to 59 hits = 9 subunits lost, 60 hits = 10 subunits lost)
        for (int i = 0; i < defSubunits; i++) {
            int x1 = i * 6;
            int x2 = x1 + 6;

            // get the probability that x1 to x2 hits are generated
            probabilities[defSubunits - i] = f(n, p, x1, x2);
        }
        probabilities[0] = f(n, p, defSubunits * 6, 61);

        for (int i = 0; i < 11; i++) {
            probabilities[i] = Math.round(probabilities[i] * 100);
        }

        return probabilities;
    }

    /**
     * Calculates the probability that the defending unit will have 0, 1, ...,
     * 10 subunits left after the attack. See http://weewar.com/specifications
     * 
     * @param atkSubunits
     *            subunits that the attacking unit has
     * @param atk
     *            name of the attacking unit
     * @param atkTerrain
     *            name of the terrain the attacking unit is on
     * @param defSubunits
     *            subunits that the defending unit has
     * @param def
     *            name of the defending unit
     * @param defTerrain
     *            name of the terrain the defending unit is on
     * @param bonus
     *            gang-up bonus
     * @return an array with the number of remaining subunits as the index and
     *         its probability as the value. Example: [0: 0.1, 1: 0.15, 2: ...]
     */
    public static double[] getProbabilities(int atkSubunits, String atk,
            String atkTerrain, int defSubunits, String def, String defTerrain,
            int bonus) {
        String atkType = Specs.unitTypes.get(atk);
        String defType = Specs.unitTypes.get(def);
        double[] probs = getProbabilities(atkSubunits, defSubunits,
                Specs.unitAttack.get(atk).get(defType), Specs.unitDefense.get(def), Specs.terrainAttack.get(atkTerrain).get(
                atkType), Specs.terrainDefense.get(defTerrain).get(
                defType), bonus);
        return probs;
    }

//    public static void main(String[] args) {
//        double[] probs = getProbabilities(6, "Light Artillery", "Plains", 8,
//                "Tank", "Swamp", 1);
//        System.out.println();
//        printArray(probs);
//    }

    /**
     * Prints an array in this format: [0=a[0], 1=a[1], ...,
     * (a.length-1)=a[a.length-1]]
     * 
     * @param a
     */
    private static void printArray(double[] a) {
        String print = "[";
        for (int i = 0; i < a.length; i++) {
            print += i + "=" + a[i];
            if (i == 10) {
                print += "]";
            } else {
                print += ", ";
            }
        }
        System.out.println(print);
    }
}
