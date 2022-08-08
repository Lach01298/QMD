package lach_01298.qmd.machine.tile;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.annotation.Nonnull;

import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import lach_01298.qmd.item.IItemParticleAmount;
import nc.config.NCConfig;
import nc.network.tile.ProcessorUpdatePacket;
import nc.recipe.AbstractRecipeHandler;
import nc.recipe.BasicRecipe;
import nc.recipe.BasicRecipeHandler;
import nc.recipe.RecipeInfo;
import nc.recipe.ingredient.IItemIngredient;
import nc.tile.internal.fluid.Tank;
import nc.tile.internal.inventory.ItemOutputSetting;
import nc.tile.internal.inventory.ItemSorption;
import nc.tile.inventory.ITileInventory;
import nc.tile.inventory.TileSidedInventory;
import nc.tile.processor.IItemProcessor;
import nc.tile.processor.ITileSideConfigGui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.MathHelper;

public class TileItemAmountFuelProcessor extends TileSidedInventory implements IItemProcessor, ITileSideConfigGui<ProcessorUpdatePacket>
{

	public final int defaultProcessTime;
	public double baseProcessTime, baseProcessRadiation;
	public final int itemInputSize, itemFuelSize, itemOutputSize;
	
	public double time;
	public int tick =0;
	public boolean isProcessing, canProcessInputs;
	
	public final boolean shouldLoseProgress;
	public final int processorID, sideConfigYOffset;
	
	
	public final BasicRecipeHandler fuelHandler;
	public final BasicRecipeHandler recipeHandler;
	
	protected RecipeInfo<BasicRecipe> recipeInfo;
	protected RecipeInfo<BasicRecipe> fuelInfo;
	
	
	protected Set<EntityPlayer> playersToUpdate;
	
	public Random rand = new Random();
	
	
	public TileItemAmountFuelProcessor(String name,  int itemInSize,int itemFuelSize, int itemOutSize, @Nonnull List<ItemSorption> itemSorptions, int time, boolean shouldLoseProgress, @Nonnull BasicRecipeHandler recipeHandler,@Nonnull BasicRecipeHandler fuelHandler, int processorID, int sideConfigYOffset)
	{
		super(name, itemInSize + itemFuelSize+ itemOutSize, ITileInventory.inventoryConnectionAll(itemSorptions));
		itemInputSize = itemInSize;
		this.itemFuelSize = itemFuelSize;
		itemOutputSize = itemOutSize;
		defaultProcessTime = time;
		baseProcessTime = time;
		this.shouldLoseProgress = shouldLoseProgress;
		this.processorID = processorID;
		this.sideConfigYOffset = sideConfigYOffset;
		this.recipeHandler = recipeHandler;
		this.fuelHandler = fuelHandler;
		playersToUpdate = new ObjectOpenHashSet<EntityPlayer>();
	}

	public static List<ItemSorption> defaultItemSorptions(int inSize, int fuelSize, int outSize)
	{
		List<ItemSorption> itemSorptions = new ArrayList<ItemSorption>();
		for (int i = 0; i < inSize+fuelSize; i++)
			itemSorptions.add(ItemSorption.IN);
		for (int i = 0; i < outSize; i++)
			itemSorptions.add(ItemSorption.OUT);
		
		return itemSorptions;
	}
	
	
	@Override
	public void onLoad() 
	{
		super.onLoad();
		if (!world.isRemote) 
		{
			refreshRecipe();
			refreshFuel();
			refreshActivity();
			isProcessing = isProcessing();
		}
	}
	
	@Override
	public void update()
	{
		if (!world.isRemote)
		{
			boolean wasProcessing = isProcessing;
			isProcessing = isProcessing();
			boolean shouldUpdate = false;
			if (isProcessing)
				process();
			else
			{
				getRadiationSource().setRadiationLevel(0D);
				if (time > 0 && !isHaltedByRedstone() && (shouldLoseProgress || !canProcessInputs))
					loseProgress();
			}
			if (wasProcessing != isProcessing)
			{
				shouldUpdate = true;
				sendTileUpdatePacketToAll();
			}
			sendTileUpdatePacketToListeners();
			if (shouldUpdate)
				markDirty();
		}
	}
	
	
	@Override
	public void refreshRecipe() 
	{
		recipeInfo = recipeHandler.getRecipeInfoFromInputs(getItemInputs(), new ArrayList<Tank>());	
	}
	
	public void refreshFuel() 
	{
		List<ItemStack> stacks = new ArrayList();
		for(ItemStack stack : getItemFuels())
		{
			stacks.add(IItemParticleAmount.cleanNBT(stack));
		}
		
		fuelInfo = fuelHandler.getRecipeInfoFromInputs(stacks,new ArrayList<Tank>());
	}
	
	
	@Override
	public void refreshActivity() 
	{
		canProcessInputs = canProcessInputs(false);
		refreshFuel(); 
	}
	
	@Override
	public void refreshActivityOnProduction() 
	{
		canProcessInputs = canProcessInputs(true);
		refreshFuel(); 
	}
	
	
	// Processor Stats
	
	public int getProcessTime() 
	{
		if(fuelInfo == null)
		{
			return 0;
		}
		else
		{
			return Math.max(1, (int) Math.round(Math.ceil(baseProcessTime/fuelInfo.getRecipe().getBaseProcessTime(1))));
		}
		
		
	}

	
	public boolean setRecipeStats() 
	{
		if (recipeInfo == null) 
		{
			baseProcessTime = defaultProcessTime;
			
			baseProcessRadiation = 0D;
			return false;
		}
		baseProcessTime = recipeInfo.getRecipe().getBaseProcessTime(defaultProcessTime);
		baseProcessRadiation = recipeInfo.getRecipe().getBaseProcessRadiation();
		return true;
	}
	
	// Processing
	
		public boolean isProcessing() 
		{
			return readyToProcess() && !isHaltedByRedstone();
		}
		
		public boolean isHaltedByRedstone() 
		{
			return getRedstoneControl() && getIsRedstonePowered();
		}
		
		public boolean readyToProcess() 
		{
			return canProcessInputs && fuelInfo != null;
		}
	
		
		public boolean canProcessInputs(boolean justProduced) 
		{
			if (!setRecipeStats()) return false;
			else if (!justProduced && time >= baseProcessTime) return true;
			return canProduceProducts();
		}
		
		
		
		public boolean canProduceProducts() 
		{
			for (int j = 0; j < itemOutputSize; j++) {
				if (getItemOutputSetting(j + itemInputSize + itemFuelSize) == ItemOutputSetting.VOID) 
				{
					getInventoryStacks().set(j + itemInputSize + itemFuelSize, ItemStack.EMPTY);
					continue;
				}
				IItemIngredient itemProduct = getItemProducts().get(j);
				if (itemProduct.getMaxStackSize(0) <= 0) continue;
				if (itemProduct.getStack() == null || itemProduct.getStack().isEmpty()) return false;
				else if (!getInventoryStacks().get(j + itemInputSize + itemFuelSize).isEmpty()) 
				{
					if (!getInventoryStacks().get(j + itemInputSize + itemFuelSize).isItemEqual(itemProduct.getStack())) 
					{
						return false;
					} else if (getItemOutputSetting(j + itemInputSize + itemFuelSize) == ItemOutputSetting.DEFAULT && getInventoryStacks().get(j + itemInputSize + itemFuelSize).getCount() + itemProduct.getMaxStackSize(0) > getInventoryStacks().get(j + itemInputSize + itemFuelSize).getMaxStackSize()) 
					{
						return false;
					}
				}
			}
			return true;
		}
		
		
		public void process() 
		{
			
			time += fuelInfo.getRecipe().getBaseProcessTime(1);
			getRadiationSource().setRadiationLevel(baseProcessRadiation*fuelInfo.getRecipe().getBaseProcessTime(1));
			if(tick >= 20)
			{
				
				for(int i =0; i<itemFuelSize;i++)
				{
					useFuel(itemInputSize+i);
				}
				tick = 0;
			}
			else
			{
				tick++;
			}	
			
			
			if (time >= baseProcessTime) finishProcess();
		}
		
		private void useFuel(int slot)
		{
			if(getInventoryStacks().get(slot).getItem() instanceof IItemParticleAmount)
			{
				IItemParticleAmount item = (IItemParticleAmount) getInventoryStacks().get(slot).getItem();
				getInventoryStacks().set(slot,item.use(getInventoryStacks().get(slot), 1000));	//TODO
			}
		}

		public void finishProcess() 
		{
			double oldProcessTime = baseProcessTime;
			produceProducts();
			refreshRecipe();
			refreshFuel();
			if (!setRecipeStats()) time = 0;
			else time = MathHelper.clamp(time - oldProcessTime, 0D, baseProcessTime);
			refreshActivityOnProduction();
			if (!canProcessInputs) time = 0;
		}
		
		public void produceProducts() 
		{
			if (recipeInfo == null) return;
			List<Integer> itemInputOrder = recipeInfo.getItemInputOrder();
			if (itemInputOrder == AbstractRecipeHandler.INVALID) return;
			
			for (int i = 0; i < itemInputSize; i++) 
			{
				int itemIngredientStackSize = getItemIngredients().get(itemInputOrder.get(i)).getMaxStackSize(recipeInfo.getItemIngredientNumbers().get(i));
				if (itemIngredientStackSize > 0) getInventoryStacks().get(i).shrink(itemIngredientStackSize);
				if (getInventoryStacks().get(i).getCount() <= 0) getInventoryStacks().set(i, ItemStack.EMPTY);
			}
			for (int j = 0; j < itemOutputSize; j++) 
			{
				if (getItemOutputSetting(j + itemInputSize + itemFuelSize) == ItemOutputSetting.VOID) 
				{
					getInventoryStacks().set(j + itemInputSize + itemFuelSize, ItemStack.EMPTY);
					continue;
				}
				IItemIngredient itemProduct = getItemProducts().get(j);
				if (itemProduct.getMaxStackSize(0) <= 0) continue;
				if (getInventoryStacks().get(j + itemInputSize + itemFuelSize).isEmpty()) 
				{
					getInventoryStacks().set(j + itemInputSize + itemFuelSize, itemProduct.getNextStack(0));
				} else if (getInventoryStacks().get(j + itemInputSize + itemFuelSize).isItemEqual(itemProduct.getStack())) 
				{
					int count = Math.min(getInventoryStackLimit(), getInventoryStacks().get(j + itemInputSize + itemFuelSize).getCount() + itemProduct.getNextStackSize(0));
					getInventoryStacks().get(j + itemInputSize + itemFuelSize).setCount(count);
				}
			}
		}
		
		public void loseProgress() 
		{
			time = MathHelper.clamp(time - 1.5D*100, 0D, baseProcessTime);
		}
		
		// IProcessor
		
		@Override
		public List<ItemStack> getItemInputs() 
		{
			return getInventoryStacks().subList(0, itemInputSize);
		}
		
		public List<ItemStack> getItemFuels() 
		{
			return getInventoryStacks().subList(itemInputSize, itemInputSize+itemFuelSize);
		}
		
		@Override
		public List<IItemIngredient> getItemIngredients() 
		{
			return recipeInfo.getRecipe().getItemIngredients();
		}
		
		@Override
		public List<IItemIngredient> getItemProducts() 
		{
			return recipeInfo.getRecipe().getItemProducts();
		}
		
		// ITileInventory
		
		@Override
		public ItemStack decrStackSize(int slot, int amount) 
		{
			ItemStack stack = super.decrStackSize(slot, amount);
			if (!world.isRemote) 
			{
				if (slot < itemInputSize) 
				{
					refreshRecipe();
					refreshFuel();
					refreshActivity();
				}
				else if (slot < itemInputSize + itemOutputSize) 
				{
					refreshActivity();
				}
			}
			return stack;
		}
		
		@Override
		public void setInventorySlotContents(int slot, ItemStack stack) 
		{
			super.setInventorySlotContents(slot, stack);
			if (!world.isRemote) 
			{
				if (slot < itemInputSize) 
				{
					refreshRecipe();
					refreshFuel();
					refreshActivity();
				}
				else if (slot < itemInputSize + itemOutputSize) 
				{
					refreshActivity();
				}
				
			}
		}
		
		@Override
		public void markDirty() 
		{
			refreshRecipe();
			refreshFuel();
			refreshActivity();
			
			super.markDirty();
		}
		
		@Override
		public boolean isItemValidForSlot(int slot, ItemStack stack) 
		{
			if (stack.isEmpty()) return false;
			if (slot >= itemInputSize + itemFuelSize) return false;
			if(slot >= itemInputSize)
			{
				if(getInventoryStacks().get(slot).getCount() >= 1)
				{
					return false;
				}		
				
				return NCConfig.smart_processor_input ? fuelHandler.isValidItemInput(slot, IItemParticleAmount.cleanNBT(stack), recipeInfo, getItemInputs(), fuelItemStacksExcludingSlot(slot)) : fuelHandler.isValidItemInput(IItemParticleAmount.cleanNBT(stack));
			}
			else
			{
				return NCConfig.smart_processor_input ? recipeHandler.isValidItemInput(slot, stack, recipeInfo, getItemInputs(), inputItemStacksExcludingSlot(slot)) : recipeHandler.isValidItemInput(stack);
			}
			
		}
		
		public List<ItemStack> inputItemStacksExcludingSlot(int slot) 
		{
			List<ItemStack> inputItemsExcludingSlot = new ArrayList<ItemStack>(getItemInputs());
			inputItemsExcludingSlot.remove(slot);
			return inputItemsExcludingSlot;
		}
		
		public List<ItemStack> fuelItemStacksExcludingSlot(int slot) 
		{
			List<ItemStack> fuelItemsExcludingSlot = new ArrayList<ItemStack>(getItemFuels());
			fuelItemsExcludingSlot.remove(slot-itemInputSize);
			return fuelItemsExcludingSlot;
		}
		
		@Override
		public boolean canInsertItem(int slot, ItemStack stack, EnumFacing side) 
		{
			return super.canInsertItem(slot, stack, side) && isItemValidForSlot(slot, stack);
		}
		
		@Override
		public boolean hasConfigurableInventoryConnections() 
		{
			return true;
		}
		
		// NBT
		
		@Override
		public NBTTagCompound writeAll(NBTTagCompound nbt) 
		{
			super.writeAll(nbt);
			nbt.setDouble("time", time);
			nbt.setBoolean("isProcessing", isProcessing);
			nbt.setBoolean("canProcessInputs", canProcessInputs);
			return nbt;
		}
		
		@Override
		public void readAll(NBTTagCompound nbt) 
		{
			super.readAll(nbt);
			time = nbt.getDouble("time");
			isProcessing = nbt.getBoolean("isProcessing");
			canProcessInputs = nbt.getBoolean("canProcessInputs");
			if (nbt.hasKey("redstoneControl")) 
			{
				setRedstoneControl(nbt.getBoolean("redstoneControl"));
			} else setRedstoneControl(true);
		}
		
		// IGui
		
		@Override
		public int getGuiID() 
		{
			return processorID;
		}
		
		@Override
		public Set<EntityPlayer> getTileUpdatePacketListeners() 
		{
			return playersToUpdate;
		}
		
		@Override
		public ProcessorUpdatePacket getTileUpdatePacket() 
		{
			return new ProcessorUpdatePacket(pos,isProcessing ,time, tick, baseProcessTime, 0, new ArrayList<Tank>());
		}
		
		@Override
		public void onTileUpdatePacket(ProcessorUpdatePacket message) 
		{
			time = message.time;
			tick = (int) message.energyStored;
			baseProcessTime = message.baseProcessTime;
			
		}
		
		@Override
		public int getSideConfigXOffset() 
		{
			return 0;
		}
		
		@Override
		public int getSideConfigYOffset() 
		{
			return sideConfigYOffset;
		}

		@Override
		public int getItemInputSize()
		{
			return itemInputSize;
		}

		@Override
		public int getItemOutputSize()
		{
			return itemOutputSize;
		}
		
		

		
}
