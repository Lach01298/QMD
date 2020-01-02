package lach_01298.qmd.multiblock.container;

import lach_01298.qmd.container.QMDMachineSlot;
import lach_01298.qmd.multiblock.accelerator.tile.TileAcceleratorSource;
import lach_01298.qmd.recipe.QMDRecipeHandler;
import lach_01298.qmd.recipe.QMDRecipes;
import nc.container.ContainerTile;
import nc.container.NCContainer;
import nc.container.SlotFurnace;
import nc.container.SlotProcessorInput;
import nc.container.SlotSpecificInput;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerAcceleratorSource extends ContainerTile
{

	public ContainerAcceleratorSource(EntityPlayer player, TileAcceleratorSource tile)
	{
		super(tile);
		
		addSlotToContainer(new QMDMachineSlot(tile,QMDRecipes.accelerator_source, 0, 80, 30));

		
		//add player inventory
		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 9; j++)
			{
				addSlotToContainer(new Slot(player.inventory, j + 9 * i + 9, 8 + 18 * j, 84 + 18 * i));
			}
		}

		for (int i = 0; i < 9; i++)
		{
			addSlotToContainer(new Slot(player.inventory, i, 8 + 18 * i, 142));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn)
	{
		return true;
	}
}

