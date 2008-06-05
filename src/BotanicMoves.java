/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Matt Whorton
 */
public class BotanicMoves {

    static Instruction[] earlyTurns[] = new Instruction[4][];
    

    static {
        Instruction round1[];
        round1 = new Instruction[4];
        round1[0] = new Instruction(new Coordinate(2, 11), new Coordinate(3, 8), "stay");
        round1[1] = new Instruction(new Coordinate(4, 13), new Coordinate(2, 11), "stay");
        round1[2] = new Instruction(new Coordinate(5, 14), new Coordinate(7, 12), "stay");
        round1[3] = new Instruction(new Coordinate(4, 14), new Coordinate(), "Raider");
        earlyTurns[0] = round1;
    }
    

    static {
        Instruction round2[];
        round2 = new Instruction[5];
        round2[0] = new Instruction(new Coordinate(3, 8), new Coordinate(2, 5), "stay");
        round2[1] = new Instruction(new Coordinate(2, 11), new Coordinate(3, 8), "stay");
        round2[2] = new Instruction(new Coordinate(7, 12), new Coordinate(8, 10), "capture");
        round2[3] = new Instruction(new Coordinate(4, 14), new Coordinate(7, 12), "stay");
        round2[4] = new Instruction(new Coordinate(4, 14), new Coordinate(), "Raider");
        earlyTurns[1] = round2;
    }
    

    static {
        Instruction round3[];
        round3 = new Instruction[4];
        round3[0] = new Instruction(new Coordinate(2, 5), new Coordinate(3, 2), "capture");
        round3[1] = new Instruction(new Coordinate(3, 8), new Coordinate(3, 5), "stay");
        round3[2] = new Instruction(new Coordinate(7, 12), new Coordinate(11, 12), "stay");
        round3[3] = new Instruction(new Coordinate(4, 14), new Coordinate(7, 12), "stay");
        earlyTurns[2] = round3;
    }
    

    static {
        Instruction round4[];
        round4 = new Instruction[3];
        round4[0] = new Instruction(new Coordinate(3, 5), new Coordinate(5, 5), "capture");
        round4[1] = new Instruction(new Coordinate(11, 12), new Coordinate(10, 11), "stay");
        round4[2] = new Instruction(new Coordinate(7, 12), new Coordinate(11, 12), "stay");
        earlyTurns[3] = round4;
    }
    
}
