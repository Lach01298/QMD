package lach_01298.qmd.commands;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.annotation.Nullable;

import lach_01298.qmd.research.IResearch;
import lach_01298.qmd.research.ResearchProvider;
import lach_01298.qmd.research.Researches;
import net.minecraft.block.Block;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.CommandBlockBaseLogic;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

public class CommandQMD extends CommandBase
{

	public String getName()
	{
		return "qmd";
	}

	/**
	 * Return the required permission level for this command.
	 */
	public int getRequiredPermissionLevel()
	{
		return 2;
	}

	
	
	 /**
     * Gets the usage string for the command.
     */
    public String getUsage(ICommandSender sender)
    {
        return "qmd.commands.usage";
    }


	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException
	{

		if (sender instanceof CommandBlockBaseLogic)
		{
			return;
		}
		else
		{

			if ("research".equalsIgnoreCase(args[0]))
			{
				if (sender instanceof EntityPlayer)
				{
					EntityPlayer player = (EntityPlayer) sender;
					IResearch playerResearch = player.getCapability(ResearchProvider.Research_CAP, null);
					
					if (!playerResearch.getResearch().isEmpty())
					{
						for (String r : playerResearch.getResearch())
						{
							sender.sendMessage(new TextComponentTranslation("commands.qmd.listresearch", new Object[] { r }));
						}
					}
					else
					{
						sender.sendMessage(new TextComponentTranslation("commands.qmd.research.none", 0));
					}

				}
			}
			else if ("listresearch".equalsIgnoreCase(args[0]))
			{	
					for (String r : Researches.getResearches())
					{
						sender.sendMessage(new TextComponentTranslation("commands.qmd.listresearch", new Object[] { r }));
					}

			}
			else if ("giveresearch".equalsIgnoreCase(args[0]))
			{
				if (sender instanceof EntityPlayer)
				{
					EntityPlayer player = (EntityPlayer) sender;
					IResearch playerResearch = player.getCapability(ResearchProvider.Research_CAP, null);
					if(!playerResearch.giveResearch(args[1]))
					{
						sender.sendMessage(new TextComponentTranslation("commands.qmd.research.invalid", new Object[] { args[1] }));
					}
				}
			}
			else if ("removeresearch".equalsIgnoreCase(args[0]))
			{
				if (sender instanceof EntityPlayer)
				{
					EntityPlayer player = (EntityPlayer) sender;
					IResearch playerResearch = player.getCapability(ResearchProvider.Research_CAP, null);
					if(!playerResearch.removeResearch(args[1]))
					{
						sender.sendMessage(new TextComponentTranslation("commands.qmd.removeresearch.invalid", new Object[] { args[1] }));
					}
				}
			}
			else
			{
				sender.sendMessage(new TextComponentTranslation("commands.generic.exception", new Object[] {}));

			}
		}

	}

	
	
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos)
	{
		if (args.length > 0 && args.length == 1)
		{
			return getListOfStringsMatchingLastWord(args,new String[] { "research", "listresearch", "giveresearch", "removeresearch" });
		}
		else if (args.length > 0 && args.length == 2)
		{
			return getListOfStringsMatchingLastWord(args,Researches.getResearches());
		}
		else
		{
			return Collections.emptyList();
		}

	}


	
	
}
