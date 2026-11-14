package lach_01298.qmd.render;

import lach_01298.qmd.item.ItemDrill;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.*;

import javax.annotation.Nullable;
import java.util.Objects;

@SideOnly(Side.CLIENT)
public class DrillHighlightHandler
{
	private EntityPlayerSP previousPlayer;
	private int previousSlot = -1;

	@SubscribeEvent(priority = EventPriority.LOWEST)
	public void onClientTick(TickEvent.ClientTickEvent event)
	{
		if (event.phase != TickEvent.Phase.START)
		{
			return;
		}
		
		Minecraft minecraft = Minecraft.getMinecraft();
		if (minecraft.isGamePaused())
		{
			return;
		}
		
		EntityPlayerSP player = minecraft.player;
		if (player == null)
		{
			previousPlayer = null;
			previousSlot = -1;
			return;
		}

		int slot = player.inventory.currentItem;
		GuiIngame gui = minecraft.ingameGUI;
		ItemStack selected = player.inventory.getCurrentItem(), highlighted = gui.highlightingItemStack;
		
		if (player == previousPlayer && slot == previousSlot && selected != highlighted && drillsEqual(selected, highlighted))
		{
			gui.highlightingItemStack = selected;
		}
		
		previousPlayer = player;
		previousSlot = slot;
	}

	private static boolean drillsEqual(ItemStack selected, ItemStack highlighted)
	{
		return !selected.isEmpty() && !highlighted.isEmpty() && selected.getItem() instanceof ItemDrill && selected.getItem() == highlighted.getItem() && selected.getMetadata() == highlighted.getMetadata() && selected.getCount() == highlighted.getCount() && selected.areCapsCompatible(highlighted) && Objects.equals(getDrillHighlightTag(selected), getDrillHighlightTag(highlighted));
	}

	private static @Nullable NBTTagCompound getDrillHighlightTag(ItemStack stack)
	{
		NBTTagCompound tag = stack.getTagCompound();
		if (tag == null)
		{
			return null;
		}
		
		tag = tag.copy();
		
		if (tag.hasKey("energyStorage", 10))
		{
			NBTTagCompound energyStorage = tag.getCompoundTag("energyStorage");
			energyStorage.removeTag("energy");
			if (energyStorage.isEmpty())
			{
				tag.removeTag("energyStorage");
			}
		}
		
		return tag.isEmpty() ? null : tag;
	}
}
