package lach_01298.qmd.capabilities;

import lach_01298.qmd.research.IResearch;
import lach_01298.qmd.research.Research;
import lach_01298.qmd.research.ResearchProvider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;

public class EventHandler
{
	@SubscribeEvent
	public void onPlayerLogsIn(PlayerLoggedInEvent event)
	{
		EntityPlayer player = event.player;
		IResearch research = player.getCapability(ResearchProvider.Research_CAP, null);
		
	}

	/** 
	* Copy data from dead player to the new player 
	*/ 
	@SubscribeEvent 
	public void onPlayerClone(PlayerEvent.Clone event) 
	{ 
	EntityPlayer player = event.getEntityPlayer(); 
	IResearch research = player.getCapability(ResearchProvider.Research_CAP, null); 
	Research oldResearch = event.getOriginal().getCapability(ResearchProvider.Research_CAP, null); 
	research.setPlayerResearches(oldResearch.getResearch()); 
	}

}