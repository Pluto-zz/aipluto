
public class Terrain
{
	String type;
	Coordinate coordinate;
	boolean finished;
	
	public boolean isFinished()
	{
		return finished;
	}
	public void setFinished(boolean finished)
	{
		this.finished = finished;
	}
	public String getType()
	{
		return type;
	}
	public void setType(String type)
	{
		this.type = type;
	}
	public Coordinate getCoordinate()
	{
		return coordinate;
	}
	public void setCoordinate(Coordinate coordinate)
	{
		this.coordinate = coordinate;
	}

	@Override
	public String toString()
	{
		
		return "(terrain: "+coordinate+":"+type+")";
	}
}
