package lach_01298.qmd.research;


import java.util.HashSet;
import java.util.Set;
import org.apache.logging.log4j.Level;
import nc.util.NCUtil;

public class Researches
{
	private static Set<String> researches = new HashSet<String>();
	
	
	public static void registerResearches()
	{
	
	
		
		
		addResearch("electron");
		addResearch("xray");
		addResearch("radiation");
		addResearch("proton");
		addResearch("positron");
		addResearch("muon");
		addResearch("neutron");
		addResearch("e_neutrino");
		addResearch("quarks");
		addResearch("tau");
		addResearch("m_neutrino");
		addResearch("charm");
		addResearch("bottom");
		addResearch("t_neutrino");
		addResearch("gluon");
		addResearch("weak_force");
		addResearch("top");
		addResearch("higgs");
		
	}
	
	
	public static void addResearch(String name)
	{
		if(!researches.contains(name))
		{
			researches.add(name);
		}
		else
		{
			
		}
	}
	
	public static Set<String> getResearches()
	{
		return researches;
	}
	
}
