package lach_01298.qmd.multiblock.container;

import lach_01298.qmd.accelerator.Accelerator;
import lach_01298.qmd.accelerator.tile.*;
import lach_01298.qmd.container.slot.SlotDisabled;
import lach_01298.qmd.multiblock.network.AcceleratorUpdatePacket;
import lach_01298.qmd.recipes.QMDRecipes;
import nc.container.multiblock.controller.ContainerMultiblockController;
import nc.container.slot.*;
import nc.recipe.BasicRecipeHandler;
import nc.tile.TileContainerInfo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerMassSpectrometerController extends ContainerMultiblockController<Accelerator, IAcceleratorPart, AcceleratorUpdatePacket, TileMassSpectrometerController, TileContainerInfo<TileMassSpectrometerController>>
{
	protected final BasicRecipeHandler recipeHandler;
	
	protected int inputSlotsSize;
	protected int outputSlotsSize;
	protected int otherSlotsSize;
	
	public final TileMassSpectrometerController tile;
	public ContainerMassSpectrometerController(EntityPlayer player, TileMassSpectrometerController tile)
	{
		super(player, tile);
		this.tile= tile;
		this.recipeHandler = QMDRecipes.mass_spectrometer;
		
		this.inputSlotsSize = 1;
		this.outputSlotsSize = 4;
		this.otherSlotsSize = 1;
		
		
		addSlotToContainer(new SlotProcessorInput(tile,recipeHandler, 0, 46, 14));
		addSlotToContainer(new SlotDisabled(tile,1));
		addSlotToContainer(new SlotFurnace(player, tile, 2, 82, 14));
		addSlotToContainer(new SlotFurnace(player, tile, 3, 101, 14));
		addSlotToContainer(new SlotFurnace(player, tile, 4, 120, 14));
		addSlotToContainer(new SlotFurnace(player, tile, 5, 139, 14));
		
		addPlayerInventory(player,8,119);

	}

	
	@Override
	public boolean canInteractWith(EntityPlayer playerIn)
	{
		return tile.isUsableByPlayer(playerIn);
	}
	
	
    public void putStackInSlot(int slotID, ItemStack stack)
    {
        this.getSlot(slotID).putStack(stack);
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
