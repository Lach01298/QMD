package lach_01298.qmd.multiblock.container;

import lach_01298.qmd.accelerator.tile.TileAcceleratorSource;
import lach_01298.qmd.container.slot.SlotQMDProcessorInput;
import lach_01298.qmd.recipe.QMDRecipeHandler;
import lach_01298.qmd.recipes.QMDRecipes;
import nc.container.ContainerTile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerAcceleratorSource extends ContainerTile<TileAcceleratorSource>
{
	protected final TileAcceleratorSource source;
	protected final QMDRecipeHandler recipeHandler;
	
	protected int inputSlotsSize;
	protected int outputSlotsSize;
	protected int otherSlotsSize;

	

	public ContainerAcceleratorSource(EntityPlayer player, TileAcceleratorSource source)
	{
		super(source);
		this.source= source;
		
		this.recipeHandler = QMDRecipes.accelerator_source;
		
		this.inputSlotsSize = 1;
		this.outputSlotsSize = 0;
		this.otherSlotsSize = 0;
		
		
		addSlotToContainer(new SlotQMDProcessorInput(source, recipeHandler, 0, 80, 30));
		
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
				if(recipeHandler.isValidItemInput(itemstack1))
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
		source.stopUpdatingPlayer(player);
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