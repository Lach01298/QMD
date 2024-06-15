package lach_01298.qmd.machine.container;

import lach_01298.qmd.recipes.QMDRecipes;
import nc.container.slot.*;
import nclegacy.container.ContainerItemFluidProcessorLegacy;
import nclegacy.tile.TileItemFluidProcessorLegacy;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;

public class ContainerOreLeacher extends ContainerItemFluidProcessorLegacy
{

	public ContainerOreLeacher(EntityPlayer player, TileItemFluidProcessorLegacy tileEntity)
	{
		super(player, tileEntity, QMDRecipes.ore_leacher);
		
		addSlotToContainer(new SlotProcessorInput(tileEntity, recipeHandler, 0, 36, 11));
		
		addSlotToContainer(new SlotFurnace(player, tileEntity, 1, 112, 42));
		addSlotToContainer(new SlotFurnace(player, tileEntity, 2, 132, 42));
		addSlotToContainer(new SlotFurnace(player, tileEntity, 3, 152, 42));
		
		addSlotToContainer(new SlotSpecificInput(tileEntity, 4, 132, 64, SPEED_UPGRADE));
		addSlotToContainer(new SlotSpecificInput(tileEntity, 5, 152, 64, ENERGY_UPGRADE));
		
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				addSlotToContainer(new Slot(player.inventory, j + 9*i + 9, 8 + 18*j, 84 + 18*i));
			}
		}
		
		for (int i = 0; i < 9; i++) {
			addSlotToContainer(new Slot(player.inventory, i, 8 + 18*i, 142));
		}
		
		
		
		
		
	}

}
