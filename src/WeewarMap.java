
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;


public class WeewarMap
{
	Map<Coordinate, Terrain> terrains = new HashMap<Coordinate,Terrain>();
	int width, height;

	public int getWidth()
	{
		return width;
	}


	public void setWidth(int width)
	{
		this.width = width;
	}


	public int getHeight()
	{
		return height;
	}


	public void setHeight(int height)
	{
		this.height = height;
	}


	public Collection<Terrain> getTerrains()
	{
		return terrains.values();
	}


	public void add( Terrain t )
	{
		terrains.put( t.getCoordinate(), t );
	}
	
	public Terrain get( Coordinate c )
	{
		return terrains.get( c );
	}


	public Collection<Terrain> getTerrainsByType(String type)
	{
		Collection<Terrain> terrains = new LinkedList<Terrain>(); 
		for( Terrain t : getTerrains() )
			if( t.getType().equals( type ) )
				terrains.add( t );
		return terrains;
	}
	
}
