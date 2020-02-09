package lach_01298.qmd.multiblock.container;

import javax.annotation.Nullable;

import lach_01298.qmd.container.slot.SlotQMDProcessorInput;
import lach_01298.qmd.multiblock.accelerator.Accelerator;
import lach_01298.qmd.multiblock.accelerator.tile.IAcceleratorController;
import lach_01298.qmd.multiblock.accelerator.tile.TileAcceleratorSource;
import lach_01298.qmd.multiblock.particleChamber.ParticleChamber;
import lach_01298.qmd.multiblock.particleChamber.tile.IParticleChamberController;
import lach_01298.qmd.multiblock.particleChamber.tile.TileTargetChamberController;
import lach_01298.qmd.recipe.QMDRecipeHandler;
import lach_01298.qmd.recipes.QMDRecipes;
import nc.container.slot.SlotFurnace;
import nc.multiblock.container.ContainerMultiblockController;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerTargetChamberController extends ContainerMultiblockController<ParticleChamber, IParticleChamberController>
{
	protected final QMDRecipeHandler recipeHandler;
	public final @Nullable IInventory invWrapper;
	
	protected int inputSlotsSize;
	protected int outputSlotsSize;
	protected int otherSlotsSize;
	
	public final TileTargetChamberController tile;
	public ContainerTargetChamberController(EntityPlayer player, TileTargetChamberController tile)
	{
		super(player, tile);
		this.tile= tile;
		invWrapper = tile.getInventory();
		this.recipeHandler = QMDRecipes.target_chamber;
		
		this.inputSlotsSize = 1;
		this.outputSlotsSize = 1;
		this.otherSlotsSize = 0;
		
		
		addSlotToContainer(new SlotQMDProcessorInput(tile,recipeHandler, 0, 47, 30));
		addSlotToContainer(new SlotFurnace(player, tile, 1, 89, 30));
		
		addPlayerInventory(player,8,84);

	}
	
	
	
	
	
	
	@Override
	public boolean canInteractWith(EntityPlayer playerIn)
	{
		return tile.isUsableByPlayer(playerIn);
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
