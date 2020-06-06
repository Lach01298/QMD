package lach_01298.qmd.machine.container;

import lach_01298.qmd.container.slot.SlotDamageFuel;
import lach_01298.qmd.machine.tile.TileItemDamageFuelProcessor;
import lach_01298.qmd.recipes.QMDRecipes;
import nc.container.slot.SlotFurnace;
import nc.container.slot.SlotProcessorInput;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;

public class ContainerIrradiator extends ContainerItemDamageFuelProcessor
{

	public ContainerIrradiator(EntityPlayer player, TileItemDamageFuelProcessor tileEntity)
	{
		super(player, tileEntity, QMDRecipes.irradiator,QMDRecipes.irradiator_fuel);
		
		addSlotToContainer(new SlotProcessorInput(tileEntity, recipeHandler, 0, 44, 54));
		addSlotToContainer(new SlotDamageFuel(tileEntity, fuelHandler, 1, 80, 21));
		
		addSlotToContainer(new SlotFurnace(player, tileEntity, 2, 116, 54));
		
		
		
		
		for (int i = 0; i < 3; i++) 
		{
			for (int j = 0; j < 9; j++) 
			{
				addSlotToContainer(new Slot(player.inventory, j + 9*i + 9, 8 + 18*j, 84 + 18*i));
			}
		}
		
		for (int i = 0; i < 9; i++) 
		{
			addSlotToContainer(new Slot(player.inventory, i, 8 + 18*i, 142));
		}
		
		
		
		
		
	}

}
