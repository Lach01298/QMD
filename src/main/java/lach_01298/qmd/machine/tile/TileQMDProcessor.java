package lach_01298.qmd.machine.tile;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import lach_01298.qmd.particle.ParticleStack;
import lach_01298.qmd.recipe.QMDRecipe;
import lach_01298.qmd.recipe.QMDRecipeHandler;
import lach_01298.qmd.recipe.QMDRecipeInfo;
import nc.ModCheck;
import nc.config.NCConfig;
import nc.init.NCItems;
import nc.network.tile.processor.EnergyProcessorUpdatePacket;
import nc.recipe.AbstractRecipeHandler;
import nc.recipe.ingredient.IFluidIngredient;
import nc.recipe.ingredient.IItemIngredient;
import nc.tile.energy.ITileEnergy;
import nc.tile.energyFluid.TileEnergyFluidSidedInventory;
import nc.tile.fluid.ITileFluid;
import nc.tile.internal.energy.EnergyConnection;
import nc.tile.internal.fluid.Tank;
import nc.tile.internal.fluid.TankOutputSetting;
import nc.tile.internal.fluid.TankSorption;
import nc.tile.internal.inventory.ItemOutputSetting;
import nc.tile.internal.inventory.ItemSorption;
import nc.tile.inventory.ITileInventory;
import nc.tile.processor.IProcessor;
import nc.util.StackHelper;
import nclegacy.tile.IItemFluidProcessorLegacy;
import nclegacy.tile.ITileSideConfigGuiLegacy;
import nclegacy.tile.IUpgradableLegacy;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;

public class TileQMDProcessor extends TileEnergyFluidSidedInventory implements IItemFluidProcessorLegacy, ITileSideConfigGuiLegacy<EnergyProcessorUpdatePacket>, IUpgradableLegacy, IAutoPushItemFluidProcessor
{
	public final double defaultProcessTime;
	public final double defaultProcessPower;
	public double baseProcessTime;
	public double baseProcessPower;
	public double baseProcessRadiation;
	protected final int itemInputSize;
	protected final int fluidInputSize;
	protected final int itemOutputSize;
	protected final int fluidOutputSize;
	public double time;
	public double resetTime;
	public boolean isProcessing;
	public boolean canProcessInputs;
	public final boolean shouldLoseProgress;
	public final boolean hasUpgrades;
	public final int processorID;
	public final int sideConfigXOffset;
	public final int sideConfigYOffset;
	protected final BiFunction<Double, Double, Long> capacityFunction;
	public final QMDRecipeHandler recipeHandler;
	protected QMDRecipeInfo<QMDRecipe> recipeInfo;
	protected Set<EntityPlayer> updatePacketListeners;

	protected final IProcessor.HandlerPair[] adjacentHandlers = new IProcessor.HandlerPair[6];


	public TileQMDProcessor(String name, int itemInSize, int fluidInSize, int itemOutSize, int fluidOutSize, @Nonnull List<ItemSorption> itemSorptions, @Nonnull IntList fluidCapacity, @Nonnull List<TankSorption> tankSorptions, List<Set<String>> allowedFluids, double time, double power, boolean shouldLoseProgress, BiFunction<Double, Double, Long> capacityFunction, @Nonnull QMDRecipeHandler recipeHandler, int processorID, int sideConfigXOffset, int sideConfigYOffset)
	{
		this(name, itemInSize, fluidInSize, itemOutSize, fluidOutSize, itemSorptions, fluidCapacity, tankSorptions, allowedFluids, time, power, shouldLoseProgress, true, capacityFunction, recipeHandler, processorID, sideConfigXOffset, sideConfigYOffset);
	}

	public TileQMDProcessor(String name, int itemInSize, int fluidInSize, int itemOutSize, int fluidOutSize, @Nonnull List<ItemSorption> itemSorptions, @Nonnull IntList fluidCapacity, @Nonnull List<TankSorption> tankSorptions, List<Set<String>> allowedFluids, double time, double power, boolean shouldLoseProgress, boolean upgrades, BiFunction<Double, Double, Long> capacityFunction, @Nonnull QMDRecipeHandler recipeHandler, int processorID, int sideConfigXOffset, int sideConfigYOffset)
	{
		super(name, itemInSize + itemOutSize + (upgrades ? 2 : 0), ITileInventory.inventoryConnectionAll(itemSorptions), (Long) capacityFunction.apply(1.0, 1.0), power != 0.0 ? ITileEnergy.energyConnectionAll(EnergyConnection.IN) : ITileEnergy.energyConnectionAll(EnergyConnection.NON), fluidCapacity, allowedFluids, ITileFluid.fluidConnectionAll(tankSorptions));
		this.itemInputSize = itemInSize;
		this.fluidInputSize = fluidInSize;
		this.itemOutputSize = itemOutSize;
		this.fluidOutputSize = fluidOutSize;
		this.defaultProcessTime = NCConfig.processor_time_multiplier * time;
		this.defaultProcessPower = NCConfig.processor_power_multiplier * power;
		this.baseProcessTime = NCConfig.processor_time_multiplier * time;
		this.baseProcessPower = NCConfig.processor_power_multiplier * power;
		this.shouldLoseProgress = shouldLoseProgress;
		this.hasUpgrades = upgrades;
		this.processorID = processorID;
		this.sideConfigXOffset = sideConfigXOffset;
		this.sideConfigYOffset = sideConfigYOffset;
		this.setInputTanksSeparated(fluidInSize > 1);
		this.capacityFunction = capacityFunction;
		this.recipeHandler = recipeHandler;
		this.updatePacketListeners = new ObjectOpenHashSet();
	}





	public static List<ItemSorption> defaultItemSorptions(int inSize, int outSize, boolean upgrades)
	{
		List<ItemSorption> itemSorptions = new ArrayList();

		int i;
		for (i = 0; i < inSize; ++i)
		{
			itemSorptions.add(ItemSorption.IN);
		}

		for (i = 0; i < outSize; ++i)
		{
			itemSorptions.add(ItemSorption.OUT);
		}

		if (upgrades)
		{
			itemSorptions.add(ItemSorption.IN);
			itemSorptions.add(ItemSorption.IN);
		}

		return itemSorptions;
	}

	public static IntList defaultTankCapacities(int capacity, int inSize, int outSize)
	{
		IntList tankCapacities = new IntArrayList();

		for (int i = 0; i < inSize + outSize; ++i)
		{
			tankCapacities.add(capacity);
		}

		return tankCapacities;
	}

	public static List<TankSorption> defaultTankSorptions(int inSize, int outSize)
	{
		List<TankSorption> tankSorptions = new ArrayList();

		int i;
		for (i = 0; i < inSize; ++i)
		{
			tankSorptions.add(TankSorption.IN);
		}

		for (i = 0; i < outSize; ++i)
		{
			tankSorptions.add(TankSorption.OUT);
		}

		return tankSorptions;
	}

	public void onLoad()
	{
		super.onLoad();
		if (!this.world.isRemote)
		{
			updateAdjacentHandlers();
			this.refreshRecipe();
			this.refreshActivity();
			this.refreshUpgrades();
			this.isProcessing = this.isProcessing();
		}

	}

	public void update()
	{
		if (!this.world.isRemote)
		{
			boolean wasProcessing = this.isProcessing;
			this.isProcessing = this.isProcessing();
			boolean shouldUpdate = false;
			if (this.isProcessing)
			{
				this.process();
			}
			else
			{
				this.getRadiationSource().setRadiationLevel(0.0);
				if (this.time > 0.0 && !this.isHaltedByRedstone() && (this.shouldLoseProgress || !this.canProcessInputs))
				{
					this.loseProgress();
				}

				if (!wasProcessing)
				{
					shouldUpdate |= autoPush();
				}
			}

			if (wasProcessing != this.isProcessing)
			{
				shouldUpdate = true;
				this.setActivity(this.isProcessing);
				this.sendTileUpdatePacketToAll();
			}

			this.sendTileUpdatePacketToListeners();
			if (shouldUpdate)
			{
				this.markDirty();
			}
		}

	}

	@Override
	public @Nullable IProcessor.HandlerPair[] getAdjacentHandlers()
	{
		return adjacentHandlers;
	}

	@Override
	public void updateAdjacentHandlers()
	{
		for (int i = 0; i < 6; ++i)
		{
			EnumFacing side = EnumFacing.VALUES[i], opposite = side.getOpposite();
			TileEntity tile = world.getTileEntity(pos.offset(side));
			IItemHandler itemHandler = getCapabilitySafe(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, tile, opposite);
			IFluidHandler fluidHandler = getCapabilitySafe(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, tile, opposite);
			adjacentHandlers[i] = itemHandler == null && fluidHandler == null ? null : new IProcessor.HandlerPair(itemHandler, fluidHandler);
		}
	}

	@Override
	public void onBlockNeighborChanged(IBlockState state, World world, BlockPos pos, BlockPos fromPos)
	{
		super.onBlockNeighborChanged(state, world, pos, fromPos);
		updateAdjacentHandlers();
	}

	public void refreshRecipe()
	{
		this.recipeInfo = this.recipeHandler.getRecipeInfoFromInputs(this.getItemInputs(), this.getFluidInputs(), new ArrayList<ParticleStack>());
	}

	public void refreshActivity()
	{
		this.canProcessInputs = this.canProcessInputs();
	}

	public void refreshActivityOnProduction()
	{
		this.canProcessInputs = this.canProcessInputs();
	}

	public int getProcessTime()
	{
		return Math.max(1, (int) Math.round(Math.ceil(this.baseProcessTime / this.getSpeedMultiplier())));
	}

	public int getProcessPower()
	{
		return Math.min(Integer.MAX_VALUE, (int) (this.baseProcessPower * this.getPowerMultiplier()));
	}

	public int getProcessEnergy()
	{
		return this.getProcessTime() * this.getProcessPower();
	}

	public boolean setRecipeStats()
	{
		if (this.recipeInfo == null)
		{
			this.baseProcessTime = this.defaultProcessTime;
			this.baseProcessPower = this.defaultProcessPower;
			this.baseProcessRadiation = 0.0;
			return false;
		}
		else
		{
			QMDRecipe recipe = this.recipeInfo.recipe;
			this.baseProcessTime = recipe.getBaseProcessTime(this.defaultProcessTime);
			this.baseProcessPower = recipe.getBaseProcessPower(this.defaultProcessPower);
			this.baseProcessRadiation = recipe.getBaseProcessRadiation();
			return true;
		}
	}

	public void setCapacityFromSpeed()
	{
		long capacity = (Long) this.capacityFunction.apply(this.getSpeedMultiplier(), this.getPowerMultiplier());
		this.getEnergyStorage().setStorageCapacity(capacity);
		this.getEnergyStorage().setMaxTransfer(capacity);
	}

	private int getMaxEnergyModified()
	{
		return ModCheck.galacticraftLoaded() ? Math.max(0, this.getMaxEnergyStored() - 16) : this.getMaxEnergyStored();
	}

	public boolean isProcessing()
	{
		return this.readyToProcess() && !this.isHaltedByRedstone();
	}

	public boolean isHaltedByRedstone()
	{
		return this.getRedstoneControl() && this.getIsRedstonePowered();
	}

	public boolean readyToProcess()
	{
		return this.canProcessInputs && this.hasSufficientEnergy();
	}

	public boolean canProcessInputs()
	{
		boolean validRecipe = this.setRecipeStats();
		boolean canProcess = validRecipe && this.canProduceProducts();
		if (!canProcess)
		{
			this.time = MathHelper.clamp(this.time, 0.0, this.baseProcessTime - 1.0);
		}

		return canProcess;
	}

	public boolean hasSufficientEnergy()
	{
		return this.time <= this.resetTime && (this.getProcessEnergy() >= this.getMaxEnergyModified() && this.getEnergyStored() >= this.getMaxEnergyModified() || this.getProcessEnergy() <= this.getEnergyStored()) || this.time > this.resetTime && this.getEnergyStored() >= this.getProcessPower();
	}

	public boolean canProduceProducts()
	{
		int j;
		for (j = 0; j < this.itemOutputSize; ++j)
		{
			if (this.getItemOutputSetting(j + this.itemInputSize) == ItemOutputSetting.VOID)
			{
				this.getInventoryStacks().set(j + this.itemInputSize, ItemStack.EMPTY);
			}
			else
			{
				IItemIngredient itemProduct = (IItemIngredient) this.getItemProducts().get(j);
				if (itemProduct.getMaxStackSize(0) > 0)
				{
					if (itemProduct.getStack() == null || ((ItemStack) itemProduct.getStack()).isEmpty())
					{
						return false;
					}

					if (!((ItemStack) this.getInventoryStacks().get(j + this.itemInputSize)).isEmpty())
					{
						if (!((ItemStack) this.getInventoryStacks().get(j + this.itemInputSize)).isItemEqual((ItemStack) itemProduct.getStack()))
						{
							return false;
						}

						if (this.getItemOutputSetting(j + this.itemInputSize) == ItemOutputSetting.DEFAULT && ((ItemStack) this.getInventoryStacks().get(j + this.itemInputSize)).getCount() + itemProduct.getMaxStackSize(0) > ((ItemStack) this.getInventoryStacks().get(j + this.itemInputSize)).getMaxStackSize())
						{
							return false;
						}
					}
				}
			}
		}

		for (j = 0; j < this.fluidOutputSize; ++j)
		{
			if (this.getTankOutputSetting(j + this.fluidInputSize) == TankOutputSetting.VOID)
			{
				this.clearTank(j + this.fluidInputSize);
			}
			else
			{
				IFluidIngredient fluidProduct = (IFluidIngredient) this.getFluidProducts().get(j);
				if (fluidProduct.getMaxStackSize(0) > 0)
				{
					if (fluidProduct.getStack() == null)
					{
						return false;
					}

					if (!((Tank) this.getTanks().get(j + this.fluidInputSize)).isEmpty())
					{
						if (!((Tank) this.getTanks().get(j + this.fluidInputSize)).getFluid().isFluidEqual((FluidStack) fluidProduct.getStack()))
						{
							return false;
						}

						if (this.getTankOutputSetting(j + this.fluidInputSize) == TankOutputSetting.DEFAULT && ((Tank) this.getTanks().get(j + this.fluidInputSize)).getFluidAmount() + fluidProduct.getMaxStackSize(0) > ((Tank) this.getTanks().get(j + this.fluidInputSize)).getCapacity())
						{
							return false;
						}
					}
				}
			}
		}

		return true;
	}

	public void process()
	{
		this.time += this.getSpeedMultiplier();
		this.getEnergyStorage().changeEnergyStored((long) (-this.getProcessPower()));
		this.getRadiationSource().setRadiationLevel(this.baseProcessRadiation * this.getSpeedMultiplier());

		while (this.time >= this.baseProcessTime)
		{
			this.finishProcess();
		}

	}

	public void finishProcess()
	{
		double oldProcessTime = this.baseProcessTime;
		this.produceProducts();
		autoPush();
		this.refreshRecipe();
		this.time = this.resetTime = Math.max(0.0, this.time - oldProcessTime);
		this.refreshActivityOnProduction();
		if (!this.canProcessInputs)
		{
			this.time = this.resetTime = 0.0;

			for (int i = 0; i < this.fluidInputSize; ++i)
			{
				if (this.getVoidUnusableFluidInput(i))
				{
					((Tank) this.getTanks().get(i)).setFluid((FluidStack) null);
				}
			}
		}

	}

	public void produceProducts()
	{
		if (this.recipeInfo != null)
		{
			IntList itemInputOrder = this.recipeInfo.getItemInputOrder();
			IntList fluidInputOrder = this.recipeInfo.getFluidInputOrder();
			if (itemInputOrder != AbstractRecipeHandler.INVALID && fluidInputOrder != AbstractRecipeHandler.INVALID)
			{
				int j;
				for (j = 0; j < this.itemInputSize; ++j)
				{
					int itemIngredientStackSize = ((IItemIngredient) this.getItemIngredients().get((Integer) itemInputOrder.get(j))).getMaxStackSize((Integer) this.recipeInfo.getItemIngredientNumbers().get(j));
					if (itemIngredientStackSize > 0)
					{
						((ItemStack) this.getInventoryStacks().get(j)).shrink(itemIngredientStackSize);
					}

					if (((ItemStack) this.getInventoryStacks().get(j)).getCount() <= 0)
					{
						this.getInventoryStacks().set(j, ItemStack.EMPTY);
					}
				}

				int count;
				for (j = 0; j < this.fluidInputSize; ++j)
				{
					Tank tank = (Tank) this.getTanks().get(j);
					count = ((IFluidIngredient) this.getFluidIngredients().get((Integer) fluidInputOrder.get(j))).getMaxStackSize((Integer) this.recipeInfo.getFluidIngredientNumbers().get(j));
					if (count > 0)
					{
						tank.changeFluidAmount(-count);
					}

					if (tank.getFluidAmount() <= 0)
					{
						tank.setFluidStored((FluidStack) null);
					}
				}

				for (j = 0; j < this.itemOutputSize; ++j)
				{
					if (this.getItemOutputSetting(j + this.itemInputSize) == ItemOutputSetting.VOID)
					{
						this.getInventoryStacks().set(j + this.itemInputSize, ItemStack.EMPTY);
					}
					else
					{
						IItemIngredient itemProduct = (IItemIngredient) this.getItemProducts().get(j);
						if (itemProduct.getMaxStackSize(0) > 0)
						{
							if (((ItemStack) this.getInventoryStacks().get(j + this.itemInputSize)).isEmpty())
							{
								this.getInventoryStacks().set(j + this.itemInputSize, itemProduct.getNextStack(0));
							}
							else if (((ItemStack) this.getInventoryStacks().get(j + this.itemInputSize)).isItemEqual((ItemStack) itemProduct.getStack()))
							{
								count = Math.min(this.getInventoryStackLimit(), ((ItemStack) this.getInventoryStacks().get(j + this.itemInputSize)).getCount() + itemProduct.getNextStackSize(0));
								((ItemStack) this.getInventoryStacks().get(j + this.itemInputSize)).setCount(count);
							}
						}
					}
				}

				for (j = 0; j < this.fluidOutputSize; ++j)
				{
					if (this.getTankOutputSetting(j + this.fluidInputSize) == TankOutputSetting.VOID)
					{
						this.clearTank(j + this.fluidInputSize);
					}
					else
					{
						IFluidIngredient fluidProduct = (IFluidIngredient) this.getFluidProducts().get(j);
						if (fluidProduct.getMaxStackSize(0) > 0)
						{
							if (((Tank) this.getTanks().get(j + this.fluidInputSize)).isEmpty())
							{
								((Tank) this.getTanks().get(j + this.fluidInputSize)).setFluidStored(fluidProduct.getNextStack(0));
							}
							else if (((Tank) this.getTanks().get(j + this.fluidInputSize)).getFluid().isFluidEqual((FluidStack) fluidProduct.getStack()))
							{
								((Tank) this.getTanks().get(j + this.fluidInputSize)).changeFluidAmount(fluidProduct.getNextStackSize(0));
							}
						}
					}
				}

			}
		}
	}

	public void loseProgress()
	{
		this.time = MathHelper.clamp(this.time - 1.5 * this.getSpeedMultiplier(), 0.0, this.baseProcessTime);
		if (this.time < this.resetTime)
		{
			this.resetTime = this.time;
		}

	}

	public int getItemInputSize()
	{
		return this.itemInputSize;
	}

	public int getItemOutputSize()
	{
		return this.itemOutputSize;
	}

	public int getFluidInputSize()
	{
		return this.fluidInputSize;
	}

	public int getFluidOutputputSize()
	{
		return this.fluidOutputSize;
	}

	public List<ItemStack> getItemInputs()
	{
		return this.getInventoryStacks().subList(0, this.itemInputSize);
	}

	public List<Tank> getFluidInputs()
	{
		return this.getTanks().subList(0, this.fluidInputSize);
	}

	public List<IItemIngredient> getItemIngredients()
	{
		return ((QMDRecipe) this.recipeInfo.recipe).getItemIngredients();
	}

	public List<IFluidIngredient> getFluidIngredients()
	{
		return ((QMDRecipe) this.recipeInfo.recipe).getFluidIngredients();
	}

	public List<IItemIngredient> getItemProducts()
	{
		return ((QMDRecipe) this.recipeInfo.recipe).getItemProducts();
	}

	public List<IFluidIngredient> getFluidProducts()
	{
		return ((QMDRecipe) this.recipeInfo.recipe).getFluidProducts();
	}

	public boolean hasUpgrades()
	{
		return this.hasUpgrades;
	}

	public int getSpeedUpgradeSlot()
	{
		return this.itemInputSize + this.itemOutputSize;
	}

	public int getEnergyUpgradeSlot()
	{
		return this.itemInputSize + this.itemOutputSize + 1;
	}

	public int getSpeedCount()
	{
		return this.hasUpgrades ? ((ItemStack) this.getInventoryStacks().get(this.getSpeedUpgradeSlot())).getCount() + 1 : 1;
	}

	public int getEnergyCount()
	{
		return this.hasUpgrades ? Math.min(this.getSpeedCount(), ((ItemStack) this.getInventoryStacks().get(this.getEnergyUpgradeSlot())).getCount() + 1) : 1;
	}

	public void refreshUpgrades()
	{
		this.setCapacityFromSpeed();
	}

	public int getSinkTier()
	{
		return 10;
	}

	public int getSourceTier()
	{
		return 1;
	}

	public ItemStack decrStackSize(int slot, int amount)
	{
		ItemStack stack = super.decrStackSize(slot, amount);
		if (!this.world.isRemote)
		{
			if (slot < this.itemInputSize)
			{
				this.refreshRecipe();
				this.refreshActivity();
			}
			else if (slot < this.itemInputSize + this.itemOutputSize)
			{
				this.refreshActivity();
			}
			else if (slot == this.getSpeedUpgradeSlot() || slot == this.getEnergyUpgradeSlot())
			{
				this.refreshUpgrades();
			}
		}

		return stack;
	}

	public void setInventorySlotContents(int slot, ItemStack stack)
	{
		super.setInventorySlotContents(slot, stack);
		if (!this.world.isRemote)
		{
			if (slot < this.itemInputSize)
			{
				this.refreshRecipe();
				this.refreshActivity();
			}
			else if (slot < this.itemInputSize + this.itemOutputSize)
			{
				this.refreshActivity();
			}
			else if (slot == this.getSpeedUpgradeSlot() || slot == this.getEnergyUpgradeSlot())
			{
				this.refreshUpgrades();
			}
		}

	}

	public void markDirty()
	{
		this.refreshRecipe();
		this.refreshActivity();
		this.refreshUpgrades();
		super.markDirty();
	}

	public boolean isItemValidForSlot(int slot, ItemStack stack)
	{
		if (stack.isEmpty())
		{
			return false;
		}
		else
		{
			if (this.hasUpgrades && stack.getItem() == NCItems.upgrade)
			{
				if (slot == this.getSpeedUpgradeSlot())
				{
					return StackHelper.getMetadata(stack) == 0;
				}

				if (slot == this.getEnergyUpgradeSlot())
				{
					return StackHelper.getMetadata(stack) == 1;
				}
			}

			if (slot >= this.itemInputSize)
			{
				return false;
			}
			else
			{
				return this.recipeHandler.isValidItemInput(stack, slot);
			}
		}
	}

	public List<ItemStack> inputItemStacksExcludingSlot(int slot)
	{
		List<ItemStack> inputItemsExcludingSlot = new ArrayList(this.getItemInputs());
		inputItemsExcludingSlot.remove(slot);
		return inputItemsExcludingSlot;
	}

	public boolean canInsertItem(int slot, ItemStack stack, EnumFacing side)
	{
		return super.canInsertItem(slot, stack, side) && this.isItemValidForSlot(slot, stack);
	}

	public boolean hasConfigurableInventoryConnections()
	{
		return true;
	}

	public boolean hasConfigurableFluidConnections()
	{
		return true;
	}

	public NBTTagCompound writeAll(NBTTagCompound nbt)
	{
		super.writeAll(nbt);
		nbt.setDouble("time", this.time);
		nbt.setDouble("resetTime", this.resetTime);
		nbt.setBoolean("isProcessing", this.isProcessing);
		nbt.setBoolean("canProcessInputs", this.canProcessInputs);
		return nbt;
	}

	public void readAll(NBTTagCompound nbt)
	{
		super.readAll(nbt);
		this.time = nbt.getDouble("time");
		this.resetTime = nbt.getDouble("resetTime");
		this.isProcessing = nbt.getBoolean("isProcessing");
		this.canProcessInputs = nbt.getBoolean("canProcessInputs");
		if (nbt.hasKey("redstoneControl"))
		{
			this.setRedstoneControl(nbt.getBoolean("redstoneControl"));
		}
		else
		{
			this.setRedstoneControl(true);
		}

	}

	public int getGuiID()
	{
		return this.processorID;
	}

	public Set<EntityPlayer> getTileUpdatePacketListeners()
	{
		return this.updatePacketListeners;
	}

	public EnergyProcessorUpdatePacket getTileUpdatePacket()
	{
		return new EnergyProcessorUpdatePacket(this.pos, this.isProcessing, this.time, this.baseProcessTime, this.getTanks(), this.baseProcessPower, (long) this.getEnergyStored());
	}

	public void onTileUpdatePacket(EnergyProcessorUpdatePacket message)
	{
		this.isProcessing = message.isProcessing;
		this.time = message.time;
		this.getEnergyStorage().setEnergyStored(message.energyStored);
		this.baseProcessTime = message.baseProcessTime;
		this.baseProcessPower = message.baseProcessPower;
		int i = 0;

		for (int len = this.getTanks().size(); i < len; ++i)
		{
			((Tank) this.getTanks().get(i)).readInfo((Tank.TankInfo) message.tankInfos.get(i));
		}

	}

	public int getSideConfigXOffset()
	{
		return this.sideConfigXOffset;
	}

	public int getSideConfigYOffset()
	{
		return this.sideConfigYOffset;
	}
}
