package lach_01298.qmd.multiblock.accelerator;

import java.util.Iterator;
import java.util.Random;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import lach_01298.qmd.config.QMDConfig;
import lach_01298.qmd.multiblock.accelerator.tile.IAcceleratorController;
import lach_01298.qmd.multiblock.accelerator.tile.IAcceleratorPart;
import lach_01298.qmd.multiblock.accelerator.tile.TileAcceleratorBeam;
import lach_01298.qmd.multiblock.accelerator.tile.TileAcceleratorBeamPort;
import lach_01298.qmd.multiblock.accelerator.tile.TileAcceleratorEnergyPort;
import lach_01298.qmd.multiblock.accelerator.tile.TileAcceleratorPort;
import lach_01298.qmd.multiblock.accelerator.tile.TileAcceleratorSource;
import lach_01298.qmd.multiblock.network.AcceleratorUpdatePacket;
import nc.config.NCConfig;
import nc.multiblock.Multiblock;
import nc.multiblock.MultiblockLogic;
import nc.multiblock.TileBeefBase.SyncReason;
import nc.multiblock.container.ContainerMultiblockController;
import nc.multiblock.container.ContainerSolidFissionController;
import nc.multiblock.fission.FissionReactor;
import nc.multiblock.fission.tile.IFissionController;
import nc.multiblock.fission.tile.TileFissionPort;
import nc.recipe.NCRecipes;
import nc.util.NCMath;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;

public class AcceleratorLogic extends MultiblockLogic<Accelerator, IAcceleratorPart, AcceleratorUpdatePacket> 
{

	protected final ObjectSet<TileAcceleratorSource> sources = new ObjectOpenHashSet<>();
	
	
public Random rand = new Random();
	
	public AcceleratorLogic(Accelerator accelerator) 
	{
		super(accelerator);
	}
	
	public AcceleratorLogic(AcceleratorLogic oldLogic) 
	{
		super(oldLogic);
	}
	
	protected Accelerator getAccelerator() 
	{
		return multiblock;
	}
	

	
	@Override
	public void load() {}
	
	@Override
	public void unload() {}
	
	public void onResetStats() {}
	
	// Multiblock Size Limits
	
	
	
	// Multiblock Methods
	
	@Override
	public void onMachineAssembled() 
	{
		onAcceleratorFormed();
	}
	
	@Override
	public void onMachineRestored() 
	{
		onAcceleratorFormed();
	}
	
	public void onAcceleratorFormed() 
	{
		for (IAcceleratorController contr : getPartMap(IAcceleratorController.class).values()) 
		{
			 getAccelerator().controller = contr;
		}
		
		getAccelerator().energyStorage.setEnergyStored(Accelerator.BASE_MAX_ENERGY * getCapacityMultiplier());
		getAccelerator().energyStorage.setMaxTransfer(Accelerator.BASE_MAX_ENERGY * getCapacityMultiplier());
		getAccelerator().heatBuffer.setHeatCapacity(Accelerator.BASE_MAX_HEAT * getCapacityMultiplier());
		getAccelerator().tanks.get(0).setCapacity(Accelerator.BASE_MAX_INPUT * getCapacityMultiplier());
		getAccelerator().tanks.get(1).setCapacity(Accelerator.BASE_MAX_OUTPUT * getCapacityMultiplier());
		getAccelerator().ambientTemp = 273 + (int) (getWorld().getBiome(getAccelerator().getMiddleCoord()).getTemperature(getAccelerator().getMiddleCoord())*20F);
		
		if (!getWorld().isRemote) 
		{
			linkPorts();
			refreshAccelerator();
			getAccelerator().updateActivity();	
		}
	
	}
	
	
	public int getCapacityMultiplier() 
	{
		return Math.max(getAccelerator().getExteriorSurfaceArea(), getAccelerator().getInteriorVolume());
	}
	
	
	private void linkPorts()
	{
		Long2ObjectMap<TileAcceleratorPort> portMap = getPartMap(TileAcceleratorPort.class);
		Long2ObjectMap<TileAcceleratorEnergyPort> energyPortMap = getPartMap(TileAcceleratorEnergyPort.class);
		Long2ObjectMap<TileAcceleratorBeamPort> beamPortMap = getPartMap(TileAcceleratorBeamPort.class);

		// ports
		for (TileAcceleratorPort port : portMap.values())
		{
			
		}
		
	}

	public void refreshAccelerator() 
	{

	}
	
	
	@Override
	public void onMachinePaused() {}
	
	public void onMachineDisassembled()
	{
		getAccelerator().resetStats();
		if (getAccelerator().controller != null)
		{
			getAccelerator().controller.updateBlockState(false);
		}
		getAccelerator().isAcceleratorOn = false;
	}

	public boolean isMachineWhole(Multiblock multiblock) 
	{
		multiblock.setLastError("zerocore.api.nc.multiblock.validation.invalid_logic", null);
		return false;
	}
	
	public void onAssimilate(Multiblock assimilated) 
	{	
		if (!(assimilated instanceof Accelerator)) return;
		Accelerator assimilatedAccelerator = (Accelerator) assimilated;
		getAccelerator().heatBuffer.mergeHeatBuffers(assimilatedAccelerator.heatBuffer);
	}
	
	public void onAssimilated(Multiblock assimilator) 
	{
		
	}
	

	
	// Server
	
	public boolean onUpdateServer() 
	{
		if (!getAccelerator().isAcceleratorOn) {
			getAccelerator().heatBuffer.changeHeatStored(-getHeatDissipation());
		}
		return false;
	}
	
	private int getHeatDissipation()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean isAcceleratorOn() 
	{
		return false;
	}
	
	
	public void updateRedstone() 
	{

	}
	
	
	public void doMeltdown() 
	{
		
	}
	

	
	// Client
	
	public void onUpdateClient() 
	{
		
	}
	

	
	// NBT
	
	@Override
	public void writeToNBT(NBTTagCompound data, SyncReason syncReason) 
	{
		
	}
	
	@Override
	public void readFromNBT(NBTTagCompound data, SyncReason syncReason) 
	{
		
	}
	
	// Packets
	
	@Override
	public AcceleratorUpdatePacket getUpdatePacket() 
	{
		return null;
	}
	
	@Override
	public void onPacket(AcceleratorUpdatePacket message) 
	{
		
	}
	
	public ContainerMultiblockController<Accelerator, IAcceleratorController> getContainer(EntityPlayer player) 
	{
		return null;
	}
	
	public void clearAllMaterial() 
	{
		
	}


	@Override
	public int getMinimumInteriorLength()
	{
		return 3;
	}

	@Override
	public int getMaximumInteriorLength()
	{
		// TODO Auto-generated method stub
		return QMDConfig.accelerator_linear_max_size;
	}

}
