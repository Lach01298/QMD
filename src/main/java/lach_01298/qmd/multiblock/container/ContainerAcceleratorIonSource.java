package lach_01298.qmd.multiblock.container;

import lach_01298.qmd.accelerator.tile.TileAcceleratorIonSource;
import lach_01298.qmd.accelerator.tile.TileMassSpectrometerController;
import lach_01298.qmd.container.slot.SlotDisabled;
import lach_01298.qmd.container.slot.SlotQMDProcessorInput;
import lach_01298.qmd.item.IItemParticleAmount;
import lach_01298.qmd.recipes.QMDRecipes;
import nclegacy.container.ContainerTileLegacy;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerAcceleratorIonSource extends ContainerTileLegacy<TileAcceleratorIonSource>
{
	protected final TileAcceleratorIonSource source;
	
	protected int inputSlotsSize;
	protected int outputSlotsSize;
	protected int otherSlotsSize;

	

	public ContainerAcceleratorIonSource(EntityPlayer player, TileAcceleratorIonSource source)
	{
		super(source);
		this.source= source;
		
		
		this.outputSlotsSize = 0;
		this.otherSlotsSize = 0;
		this.inputSlotsSize = 2;
		source.addTileUpdatePacketListener(player);
		
		if(source.getMultiblock().controller instanceof TileMassSpectrometerController)
		{
			addSlotToContainer(new SlotQMDProcessorInput(source, QMDRecipes.mass_spectrometer, 0, 71, 26));
			addSlotToContainer(new SlotDisabled(source,1));
			this.otherSlotsSize = 1;
			this.inputSlotsSize = 1;
		}
		else
		{
			addSlotToContainer(new SlotQMDProcessorInput(source, QMDRecipes.accelerator_source, 0, 71, 26, 1));
			addSlotToContainer(new SlotQMDProcessorInput(source, QMDRecipes.accelerator_source, 1, 89, 26, 1));
			this.otherSlotsSize = 0;
			this.inputSlotsSize = 2;
		}
		
		
		addPlayerInventory(player,8,84);
		
	}

	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int index)
	{
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = inventorySlots.get(index);
		
		int invPlayerStart = inputSlotsSize+outputSlotsSize+otherSlotsSize;
		int invPlayerEnd =  invPlayerStart +36;
		
		if (slot != null && slot.getHasStack())
		{
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();
			
			if (index < invPlayerStart)
			{
				if (!mergeItemStack(itemstack1, invPlayerStart, invPlayerEnd, false))
				{
					return ItemStack.EMPTY;
				}
			}
			else
			{
				if (source.getMultiblock().controller instanceof TileMassSpectrometerController)
				{
					if (QMDRecipes.mass_spectrometer.isValidItemInput(itemstack1))
					{
						if (!mergeItemStack(itemstack1, 0, inputSlotsSize, false))
						{
							return ItemStack.EMPTY;
						}
						else
						{
							return ItemStack.EMPTY;
						}
					}
				}
				else
				{
					if (QMDRecipes.accelerator_source.isValidItemInput(IItemParticleAmount.cleanNBT(itemstack1)))
					{
						if (!mergeItemStack(itemstack1, 0, inputSlotsSize, false))
						{
							return ItemStack.EMPTY;
						}
						else
						{
							return ItemStack.EMPTY;
						}
					}
				}

			}
			if (itemstack1.isEmpty())
			{
				slot.putStack(ItemStack.EMPTY);
			}
			else
			{
				slot.onSlotChanged();
			}
			if (itemstack1.getCount() == itemstack.getCount())
			{
				return ItemStack.EMPTY;
			}
			slot.onTake(player, itemstack1);
		}
		
		
		return itemstack;
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer playerIn)
	{
		return source.isUsableByPlayer(playerIn);
	}

	
	@Override
	public void onContainerClosed(EntityPlayer player)
	{
		super.onContainerClosed(player);
		source.removeTileUpdatePacketListener(player);
	}
	

protected void addPlayerInventory(EntityPlayer player, int xOffset,int yOffset)
{
	int slotWidth = 18;
	// add player inventory
	for (int i = 0; i < 3; i++)
	{
		for (int j = 0; j < 9; j++)
		{
			addSlotToContainer(new Slot(player.inventory, j + 9 * i + 9, xOffset + slotWidth * j, yOffset + slotWidth * i));
		}
	}

	for (int i = 0; i < 9; i++)
	{
		addSlotToContainer(new Slot(player.inventory, i, xOffset + slotWidth * i, yOffset+ slotWidth*3+4));
	}
}




}
