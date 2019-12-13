package lach_01298.qmd.research;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;



/** 
* Default implementation of IMana 
*/ 
public class Research implements IResearch 
{
	Set<String> playerResearches = new HashSet<String>();
	Map<String,Integer> playerPoints = new HashMap<String,Integer>();

	
	public Research()
	{
		generatePlayerResearch();
	}
	
	
	public  void setPlayerResearches(Set<String> researches)
	{
		this.playerResearches = researches;
		
	}

	@Override
	public boolean giveResearch(String id)
	{
		if(Researches.getResearches().contains(id))
		{
			playerResearches.add(id);
			return true;
		}
		else
		{
			return false;
		}
		
	}

	@Override
	public boolean removeResearch(String id)
	{
		if(playerResearches.contains(id))
		{
			playerResearches.remove(id);
			return true;
		}
		else
		{
			return false;
		}
		
	}

	@Override
	public int getResearchPoints(String id)
	{
		if(Researches.getResearches().contains(id))
		{
			return playerPoints.get(id);
		}
		return 0;
	}
	
	
	@Override
	public void addResearchPoints(int points, String id)
	{
		int oldPoints = getResearchPoints(id);
		playerPoints.replace(id, oldPoints + points);
		
	}

	@Override
	public void removeResearchPoints(int points, String id)
	{
		int oldPoints = getResearchPoints(id);
		playerPoints.replace(id, oldPoints - points);
		
		
	}

	@Override
	public boolean hasResearch(String id)
	{
		return playerResearches.contains(id);
	}
	
	public void generatePlayerResearch()
	{
		for(String r: Researches.getResearches())
		{
			playerPoints.put(r, 0);
		}
	}




	@Override
	public Set<String> getResearch()
	{
		return playerResearches;
	}

	

	

}