/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Matt Whorton
 */
public class Instruction {

    Coordinate startPosition;
    Coordinate endPosition;
    String action = "";
    
    public Instruction() {
    }

    public Instruction( Coordinate start, Coordinate end, String action )
    {
            this.startPosition = start;
            this.endPosition = end;
            this.action = action;
    }
    
    
    public Coordinate getStartPosition()
    {
            return startPosition;
    }
    public void setStartPosition(Coordinate c)
    {
            this.startPosition = c;
    }
    
    public Coordinate getEndPosition()
    {
            return endPosition;
    }
    public void setEndPosition(Coordinate c)
    {
            this.endPosition = c;
    }
    
    public String getAction()
    {
            return action;
    }
    public void setAction(String a)
    {
            this.action = a;
    }
    
}
