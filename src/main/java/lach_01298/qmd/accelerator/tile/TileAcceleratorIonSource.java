package lach_01298.qmd.accelerator.tile;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import lach_01298.qmd.QMD;
import lach_01298.qmd.accelerator.*;
import lach_01298.qmd.config.QMDConfig;
import lach_01298.qmd.gui.GUI_ID;
import lach_01298.qmd.item.IItemParticleAmount;
import lach_01298.qmd.multiblock.network.AcceleratorSourceUpdatePacket;
import lach_01298.qmd.network.QMDPackets;
import lach_01298.qmd.recipes.QMDRecipes;
import lach_01298.qmd.tile.ITileIONumber;
import lach_01298.qmd.util.InventoryStackList;
import nc.multiblock.cuboidal.CuboidalPartPositionType;
import nc.tile.fluid.ITileFluid;
import nc.tile.internal.fluid.*;
import nc.tile.internal.inventory.*;
import nc.tile.inventory.ITileInventory;
import nc.util.*;
import nclegacy.tile.ITileGuiLegacy;
import net.minecraft.entity.player.*;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.*;
import java.util.*;

public class TileAcceleratorIonSource extends TileAcceleratorPart implements ITileInventory, ITileFluid, ITileIONumber, ITileGuiLegacy<AcceleratorSourceUpdatePacket>, ITickable
{
	private IAcceleratorController controller;
	
	
	private final @Nonnull String inventoryName = QMD.MOD_ID + ".container.accelerator_source";
	private @Nonnull InventoryConnection[] inventoryConnections = ITileInventory.inventoryConnectionAll(Arrays.asList(ItemSorption.NON,ItemSorption.NON));
	private final @Nonnull NonNullList<ItemStack> inventoryStacks = NonNullList.<ItemStack>withSize(2, ItemStack.EMPTY);
	
	private final @Nonnull List<Tank> backupTanks = Lists.newArrayList(new Tank(QMDConfig.accelerator_base_input_tank_capacity * 1000, new HashSet<>()));
	private @Nonnull FluidConnection[] fluidConnections = ITileFluid.fluidConnectionAll(Lists.newArrayList(TankSorption.NON));
	private @Nonnull FluidTileWrapper[] fluidSides;
	
	protected Set<EntityPlayer> playersToUpdate;
	
	public final int outputParticleMultiplier;
	public final double outputFocus;
	public final int basePower;
	public final String name;
	
	private int IONumber;
	
	public TileAcceleratorIonSource(int outputParticleMultipler,double outputFocus,int basePower, String name)
	{
		super(CuboidalPartPositionType.WALL);
		this.outputParticleMultiplier = outputParticleMultipler < 1 ? 1 :outputParticleMultipler;
		this.outputFocus = outputFocus;
		this.basePower = basePower;
		this.name = name;
		
		
		fluidSides = ITileFluid.getDefaultFluidSides(this);
		this.IONumber= 0;
		playersToUpdate = new ObjectOpenHashSet<EntityPlayer>();
	}

	
	public static class Basic extends TileAcceleratorIonSource
	{
		public Basic()
		{
			super(QMDConfig.ion_source_output_multiplier[0],QMDConfig.ion_source_focus[0],QMDConfig.ion_source_power[0],"basic");
		}
	}
	
	public static class Laser extends TileAcceleratorIonSource
	{
		public Laser()
		{
			super(QMDConfig.ion_source_output_multiplier[1],QMDConfig.ion_source_focus[1],QMDConfig.ion_source_power[1],"laser");
		}
	}
	
	
	
	

	@Override
	public void onMachineAssembled(Accelerator accelerator)
	{
		if(accelerator.controller instanceof TileMassSpectrometerController)
		{
			controller =  (TileMassSpectrometerController) accelerator.controller;
			
			for (int i = 0; i < 6; i++)
			{
				setItemSorption(EnumFacing.byIndex(i), 0, ItemSorption.IN);
				setItemSorption(EnumFacing.byIndex(i), 1, ItemSorption.NON);
				setTankSorption(EnumFacing.byIndex(i), 0, TankSorption.IN);
			}

		}
		else if(accelerator.controller instanceof TileLinearAcceleratorController)
		{
			controller =  (TileLinearAcceleratorController) accelerator.controller;
			for (int i = 0; i < 6; i++)
			{
				setItemSorption(EnumFacing.byIndex(i), 0, ItemSorption.BOTH);
				setItemSorption(EnumFacing.byIndex(i), 1, ItemSorption.BOTH);
				setTankSorption(EnumFacing.byIndex(i), 0, TankSorption.IN);
			}
		}
		else
		{
			for (int i = 0; i < 6; i++)
			{
				setItemSorption(EnumFacing.byIndex(i), 0, ItemSorption.NON);
				setItemSorption(EnumFacing.byIndex(i), 1, ItemSorption.NON);
				setTankSorption(EnumFacing.byIndex(i), 0, TankSorption.NON);
			}
		}
		
		super.onMachineAssembled(accelerator);
		
	}

	@Override
	public void onMachineBroken()
	{
		controller = null;
		for (int i = 0; i < 6; i++)
		{
			setItemSorption(EnumFacing.byIndex(i), 0, ItemSorption.NON);
			setItemSorption(EnumFacing.byIndex(i), 1, ItemSorption.NON);
			setTankSorption(EnumFacing.byIndex(i), 0, TankSorption.NON);
		}
		
		super.onMachineBroken();
		
	}

	// Items
	
	@Override
	public NonNullList<ItemStack> getInventoryStacks()
	{
		if(controller != null && IONumber !=0)
		{
			if(getLogic() instanceof MassSpectrometerLogic && controller instanceof TileMassSpectrometerController)
			{
				TileMassSpectrometerController massSpec = (TileMassSpectrometerController) controller;
				return new InventoryStackList(massSpec.getInventoryStacks().subList(0,2));
			}
		}

		return inventoryStacks;
	}

	@Override
	public String getName()
	{
		return Lang.localize("gui."+inventoryName);
	}

	@Override
	public InventoryConnection[] getInventoryConnections()
	{
		return inventoryConnections;
	}

	@Override
	public void setInventoryConnections(InventoryConnection[] connections)
	{
		inventoryConnections = connections;
	}


	@Override
	public ItemOutputSetting getItemOutputSetting(int slot)
	{
		return ItemOutputSetting.DEFAULT;
	}

	@Override
	public void setItemOutputSetting(int slot, ItemOutputSetting setting)
	{
	}
	
	@Override
	public int getInventoryStackLimit()
	{
		if(controller instanceof TileMassSpectrometerController)
		{
			return 64;
		}
		
		return 1;
	}
	
	@Override
	public  boolean isItemValidForSlot(int slot, ItemStack stack)
	{
		if(controller instanceof TileMassSpectrometerController)
		{
			
			return QMDRecipes.mass_spectrometer.isValidItemInput(stack);
		}
		
		return QMDRecipes.accelerator_source.isValidItemInput(IItemParticleAmount.cleanNBT(stack));
	}
	
	
	// Fluids
	
	@Override
	public @Nonnull List<Tank> getTanks()
	{
		if(getMultiblock() != null && IONumber !=0)
		{
				return getMultiblock().isAssembled() ? getMultiblock().tanks.subList(IONumber, IONumber+1) : backupTanks;
		}

		return  backupTanks;
	}
	@Override
	@Nonnull
	public FluidConnection[] getFluidConnections()
	{
		return fluidConnections;
	}
	
	@Override
	public void setFluidConnections(@Nonnull FluidConnection[] connections)
	{
		fluidConnections = connections;
	}

	@Override
	@Nonnull
	public FluidTileWrapper[] getFluidSides()
	{
		return fluidSides;
	}

	@Override
	public GasTileWrapper getGasWrapper()
	{
		return null;
	}


	@Override
	public boolean getInputTanksSeparated()
	{
		return false;
	}

	@Override
	public void setInputTanksSeparated(boolean separated)
	{
	}

	@Override
	public boolean getVoidUnusableFluidInput(int tankNumber)
	{
		return false;
	}

	@Override
	public void setVoidUnusableFluidInput(int tankNumber, boolean voidUnusableFluidInput)
	{
	}

	@Override
	public TankOutputSetting getTankOutputSetting(int tankNumber)
	{
		return TankOutputSetting.DEFAULT;
	}

	@Override
	public void setTankOutputSetting(int tankNumber, TankOutputSetting setting)
	{
	}

	@Override
	public boolean hasConfigurableFluidConnections()
	{
		return true;
	}
	
	
	// NBT

	@Override
	public NBTTagCompound writeAll(NBTTagCompound nbt) {
		super.writeAll(nbt);
		writeInventory(nbt);
		writeInventoryConnections(nbt);
		writeTanks(nbt);
		writeFluidConnections(nbt);
		writeTankSettings(nbt);
		nbt.setInteger("IONumber", IONumber);

		return nbt;
	}
	
	@Override
	public void readAll(NBTTagCompound nbt)
	{
		super.readAll(nbt);
		readInventory(nbt);
		readInventoryConnections(nbt);
		readTanks(nbt);
		readFluidConnections(nbt);
		readTankSettings(nbt);
		IONumber = nbt.getInteger("IONumber");
		
		
	}
	

	@Override
	public NBTTagCompound writeInventory(NBTTagCompound nbt)
	{
		NBTHelper.writeAllItems(nbt, inventoryStacks);
		return nbt;
	}

	@Override
	public void readInventory(NBTTagCompound nbt)
	{
		NBTHelper.readAllItems(nbt, inventoryStacks);
	}

	
	@Override
	public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing side)
	{
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
		{
			return !getInventoryStacks().isEmpty() && hasInventorySideCapability(side);
		}
		if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
		{
			return !getTanks().isEmpty() && hasFluidSideCapability(side);
		}
		return super.hasCapability(capability, side);
	}

	@Override
	public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing side)
	{
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
		{
			if (!getInventoryStacks().isEmpty() && hasInventorySideCapability(side))
			{
				return (T) getItemHandler(side);
			}
			return null;

		}
		if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
		{
			if (!getTanks().isEmpty() && hasFluidSideCapability(side))
			{
				return (T) getFluidSide(nonNullSide(side));
			}
			return null;
		}
		return super.getCapability(capability, side);
	}

	// Gui
	
	@Override
	public int getGuiID()
	{
		
		return GUI_ID.ACCELERATOR_SOURCE;
	}

	@Override
	public Set<EntityPlayer> getTileUpdatePacketListeners()
	{
		
		return playersToUpdate;
	}

	@Override
	public AcceleratorSourceUpdatePacket getTileUpdatePacket()
	{
		return new AcceleratorSourceUpdatePacket(pos, getTanks());
	}

	@Override
	public void onTileUpdatePacket(AcceleratorSourceUpdatePacket message)
	{
		for (int i = 0; i < getTanks().size(); ++i)
		{
			getTanks().get(i).readInfo(message.tanksInfo.get(i));
		}
	}

	
	
	@Override
	public void update()
	{
		if (!world.isRemote)
		{
			sendTileUpdatePacketToListeners();
		}
	}
	
	

	// IO seting
	
	public void setIONumber(int number)
	{
		if(number >= 0 && number <= 2)
		{
			IONumber= number;
		}
	}
	
	public int getIONumber()
	{
		return	IONumber;
	}
	
	@Override
	public void sendTileUpdatePacketToListeners() {
		for (EntityPlayer player : getTileUpdatePacketListeners()) {
			QMDPackets.wrapper.sendTo(getTileUpdatePacket(), (EntityPlayerMP) player);
		}
	}
	
	@Override
	public void sendTileUpdatePacketToPlayer(EntityPlayer player) {
		if (getTileWorld().isRemote) {
			return;
		}
		QMDPackets.wrapper.sendTo(getTileUpdatePacket(), (EntityPlayerMP) player);
	}
	
	@Override
	public void sendTileUpdatePacketToAll() {
		QMDPackets.wrapper.sendToAll(getTileUpdatePacket());
	}
	
}
