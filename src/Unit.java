
public class Unit
{
	String type;
	boolean finished;
	Coordinate coordinate;
	int quantity;
	
	public int getQuantity()
	{
		return quantity;
	}
	public void setQuantity(int quantity)
	{
		this.quantity = quantity;
	}
	public String getType()
	{
		return type;
	}
	public void setType(String type)
	{
		this.type = type;
	}
	public boolean isFinished()
	{
		return finished;
	}
	public void setFinished(boolean finished)
	{
		this.finished = finished;
	}
	public Coordinate getCoordinate()
	{
		return coordinate;
	}
	public void setCoordinate(Coordinate coordinate)
	{
		this.coordinate = coordinate;
	}
	
}
