import java.util.Collection;
import java.util.LinkedList;
import java.util.List;


public class Game
{
	String name;
	String link; 
	boolean requiringAnInviteAccept=false;
	int id;
	boolean inNeedOfAttention;
	int mapId;
        int currentRound;
        String state;
	
	List<Faction> factions = new LinkedList<Faction>();
	Collection<String> players = new LinkedList<String>();
	
	public boolean isInNeedOfAttention()
	{
		return inNeedOfAttention;
	}
	public void setInNeedOfAttention(boolean inNeedOfAttention)
	{
		this.inNeedOfAttention = inNeedOfAttention;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public Collection<String> getPlayers()
	{
		return players;
	}
	public void setPlayers(Collection<String> players)
	{
		this.players = players;
	}
	public String getLink()
	{
		return link;
	}
	public void setLink(String link)
	{
		this.link = link;
	}
	public boolean isRequiringAnInviteAccept()
	{
		return requiringAnInviteAccept;
	}
	public void setRequiringAnInviteAccept(boolean requiringAnInviteAccept)
	{
		this.requiringAnInviteAccept = requiringAnInviteAccept;
	}
	public List<Faction> getFactions()
	{
		return factions;
	}
	public void setFactions(List<Faction> factions)
	{
		this.factions = factions;
	}
	public Faction getFactionByPlayerName(String name)
	{
		for( Faction faction : getFactions() )
		{
			if( faction.getPlayerName().equals( name ) )
				return faction;
		}
		return null;
	}
	public Object getUnit(Coordinate c)
	{
		for( Faction faction : getFactions() )
		{
			for( Unit unit: faction.getUnits() )
			{
				if( unit.getCoordinate().equals( c ) )
					return unit;
			}
		}
		return null;
	}
	public int getMapId()
	{
		return mapId;
	}
	public void setMapId(int mapId)
	{
		this.mapId = mapId;
	}
	public int getRound()
	{
		return currentRound;
	}
	public void setRound(int round)
	{
		this.currentRound = round;
	}
	
	public Faction getTerrainOwner(Coordinate c)
	{
		for( Faction faction : getFactions() )
		{
			for( Terrain t : faction.getTerrains() )
			{
				if( t.getCoordinate().equals( c ) )
					return faction;
			}
		}
		return null;
	}
	public int getUnitCount()
	{
		int s = 0;
		for( Faction faction : getFactions() )
			s+=faction.getUnits().size();
		return s;
	}
        
	public String getState()
	{
		return state;
	}
	public void setState(String s)
	{
		this.state = s;
	}
	
	
}
