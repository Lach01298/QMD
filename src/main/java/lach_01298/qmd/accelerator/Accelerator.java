package lach_01298.qmd.accelerator;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Nonnull;

import com.google.common.collect.Lists;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import lach_01298.qmd.accelerator.tile.IAcceleratorController;
import lach_01298.qmd.accelerator.tile.IAcceleratorPart;
import lach_01298.qmd.accelerator.tile.TileAcceleratorBeam;
import lach_01298.qmd.accelerator.tile.TileAcceleratorBeamPort;
import lach_01298.qmd.accelerator.tile.TileAcceleratorMagnet;
import lach_01298.qmd.accelerator.tile.TileAcceleratorRFCavity;
import lach_01298.qmd.accelerator.tile.TileAcceleratorYoke;
import lach_01298.qmd.config.QMDConfig;
import lach_01298.qmd.enums.EnumTypes.IOType;
import lach_01298.qmd.multiblock.CuboidalOrToroidalMultiblock;
import lach_01298.qmd.multiblock.IMultiBlockTank;
import lach_01298.qmd.multiblock.IQMDPacketMultiblock;
import lach_01298.qmd.multiblock.network.AcceleratorUpdatePacket;
import lach_01298.qmd.particle.ParticleStorageAccelerator;
import lach_01298.qmd.recipes.QMDRecipes;
import nc.Global;
import nc.multiblock.ILogicMultiblock;
import nc.multiblock.IPacketMultiblock;
import nc.multiblock.Multiblock;
import nc.multiblock.container.ContainerMultiblockController;
import nc.multiblock.tile.ITileMultiblockPart;
import nc.multiblock.tile.TileBeefAbstract.SyncReason;
import nc.recipe.BasicRecipe;
import nc.recipe.RecipeInfo;
import nc.tile.internal.energy.EnergyStorage;
import nc.tile.internal.fluid.Tank;
import nc.tile.internal.heat.HeatBuffer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Accelerator extends CuboidalOrToroidalMultiblock<Accelerator, IAcceleratorPart>
		implements ILogicMultiblock<Accelerator, AcceleratorLogic, IAcceleratorPart>, IQMDPacketMultiblock<Accelerator, IAcceleratorPart, AcceleratorUpdatePacket>, IMultiBlockTank
{

	public static final ObjectSet<Class<? extends IAcceleratorPart>> PART_CLASSES = new ObjectOpenHashSet<>();
	public static final Object2ObjectMap<String, Constructor<? extends AcceleratorLogic>> LOGIC_MAP = new Object2ObjectOpenHashMap<>(); 
	
	protected @Nonnull AcceleratorLogic logic = new AcceleratorLogic(this);
	protected @Nonnull NBTTagCompound cachedData = new NBTTagCompound();

	protected final PartSuperMap<Accelerator, IAcceleratorPart> partSuperMap = new PartSuperMap<>();

	protected final Long2ObjectMap<TileAcceleratorBeam> beamMap = new Long2ObjectOpenHashMap<>();
	protected final Long2ObjectMap<RFCavity> rfCavityMap = new Long2ObjectOpenHashMap<>();
	protected final Long2ObjectMap<QuadrupoleMagnet> quadrupoleMap = new Long2ObjectOpenHashMap<>();
	protected final Long2ObjectMap<DipoleMagnet> dipoleMap = new Long2ObjectOpenHashMap<>();
	
	protected final ObjectSet<TileAcceleratorBeamPort> beamPorts = new ObjectOpenHashSet<>();

	
	public IAcceleratorController controller;

	public TileAcceleratorBeamPort input;
	public TileAcceleratorBeamPort output;
	
	
	public final HeatBuffer heatBuffer = new HeatBuffer(QMDConfig.accelerator_base_heat_capacity);
	public final EnergyStorage energyStorage = new EnergyStorage(QMDConfig.accelerator_base_energy_capacity);
	public List<Tank> tanks = Lists.newArrayList(new Tank(QMDConfig.accelerator_base_input_tank_capacity, QMDRecipes.accelerator_cooling_valid_fluids.get(0)), new Tank(QMDConfig.accelerator_base_output_tank_capacity, null));
	public final List<ParticleStorageAccelerator> beams = Lists.newArrayList(new ParticleStorageAccelerator(),new ParticleStorageAccelerator(),new ParticleStorageAccelerator());
	
	public boolean refreshFlag = true, isAcceleratorOn = false, cold = false;
	
	public static final int MAX_TEMP = 400;
	
	public int ambientTemp = 290, maxOperatingTemp = 0;

	public long cooling = 0L, rawHeating = 0L, currentHeating=0L;
	public int maxCoolantIn =0, maxCoolantOut=0; // micro buckets per tick
	public double efficiency = 0D;
	public int requiredEnergy = 0, acceleratingVoltage =0;
	public int RFCavityNumber =0, quadrupoleNumber =0,dipoleNumber =0;
	public double quadrupoleStrength =0, dipoleStrength =0;
	
	//OC computer control
	public int energyPercentage =0;
	public boolean  computerControlled = false;
	
	public int errorCode =0;
	public static final int errorCode_Nothing = 0;
	public static final int errorCode_ToHot = 1;
	public static final int errorCode_OutOfPower = 2;
	public static final int errorCode_NotEnoughQuadrupoles = 3;
	public static final int errorCode_InputParticleEnergyToLow = 4;
	public static final int errorCode_InputParticleEnergyToHigh = 5;
	
	private static final int thickness = 5;
	
	
	public RecipeInfo<BasicRecipe> coolingRecipeInfo;
	
	protected final Set<EntityPlayer> updatePacketListeners;
	
	
	
	
	public Accelerator(World world)
	{
		super(world, Accelerator.class, IAcceleratorPart.class, thickness);
		for (Class<? extends IAcceleratorPart> clazz : PART_CLASSES)
		{
			partSuperMap.equip(clazz);
		}
		updatePacketListeners = new ObjectOpenHashSet<>();
	}

	@Override
	public @Nonnull AcceleratorLogic getLogic()
	{
		return logic;
	}
	
	@Override
	public void setLogic(String logicID)
	{
		if (logicID.equals(logic.getID())) return;
		logic = getNewLogic(LOGIC_MAP.get(logicID));	
	}

	@Override
	public PartSuperMap<Accelerator, IAcceleratorPart> getPartSuperMap()
	{
		return partSuperMap;
	}
	
	// Multiblock Part Getters
	
	public Long2ObjectMap<TileAcceleratorBeam> getBeamMap()
	{
		return beamMap;
	}
	
	public Long2ObjectMap<RFCavity> getRFCavityMap()
	{
		return rfCavityMap;
	}
	
	public Long2ObjectMap<QuadrupoleMagnet> getQuadrupoleMap()
	{
		return quadrupoleMap;
	}
	
	public Long2ObjectMap<DipoleMagnet> getDipoleMap()
	{
		return dipoleMap;
	}
	
	public ObjectSet<TileAcceleratorBeamPort> getValidBeamPorts()
	{
		return beamPorts;
	}






	public void resetStats()
	{
		logic.onResetStats();
		cooling = rawHeating =  currentHeating  = 0L;
		quadrupoleStrength = efficiency= dipoleStrength = 0D;
		RFCavityNumber = quadrupoleNumber = dipoleNumber = acceleratingVoltage = requiredEnergy = 0;
		coolingRecipeInfo = null;
		maxCoolantIn = maxCoolantOut=0;
	}

	


	public boolean isValidRFCavity(BlockPos center, Axis axis)
	{
		if(!(this.WORLD.getTileEntity(center.up()) instanceof TileAcceleratorRFCavity) )
		{
			return false;
		}
		
		Class cavityType = this.WORLD.getTileEntity(center.up()).getClass();
		
		if(!cavityType.isInstance(this.WORLD.getTileEntity(center.down())))
		{
			return false;
		}
		
		if(axis == Axis.X)
		{
			//check no Cavitys next to this one
			if (this.WORLD.getTileEntity(center.up().east()) instanceof TileAcceleratorRFCavity || this.WORLD.getTileEntity(center.up().west()) instanceof TileAcceleratorRFCavity)
			{
				return false;
			}
			
			if (cavityType.isInstance(this.WORLD.getTileEntity(center.north()))
					&& cavityType.isInstance(this.WORLD.getTileEntity(center.south()))
					&& cavityType.isInstance(this.WORLD.getTileEntity(center.add(0,1,1)))
					&& cavityType.isInstance(this.WORLD.getTileEntity(center.add(0,1,-1)))
					&& cavityType.isInstance(this.WORLD.getTileEntity(center.add(0,-1,1)))
					&& cavityType.isInstance(this.WORLD.getTileEntity(center.add(0,-1,-1))))
			{
				return true;
			}
		}
		if(axis == Axis.Z)
		{
			//check no Cavitys next to this one
			if (this.WORLD.getTileEntity(center.up().north()) instanceof TileAcceleratorRFCavity || this.WORLD.getTileEntity(center.up().south()) instanceof TileAcceleratorRFCavity)
			{
				return false;
			}
			
			if (cavityType.isInstance(this.WORLD.getTileEntity(center.east()))
					&& cavityType.isInstance(this.WORLD.getTileEntity(center.west()))
					&& cavityType.isInstance(this.WORLD.getTileEntity(center.add(1,1,0)))
					&& cavityType.isInstance(this.WORLD.getTileEntity(center.add(-1,1,0)))
					&& cavityType.isInstance(this.WORLD.getTileEntity(center.add(1,-1,0)))
					&& cavityType.isInstance(this.WORLD.getTileEntity(center.add(-1,-1,0))))
			{
				return true;
			}
		}
		return false;	
	}
	
	
	public boolean isValidQuadrupole(BlockPos center, Axis axis)
	{
		if(!(this.WORLD.getTileEntity(center.up()) instanceof TileAcceleratorMagnet))
		{
			return false;
		}
		
		Class magnetType = this.WORLD.getTileEntity(center.up()).getClass();
		
		if(!magnetType.isInstance(this.WORLD.getTileEntity(center.down())))
		{
			return false;
		}
		
		if(axis == Axis.X)
		{
			if (magnetType.isInstance(this.WORLD.getTileEntity(center.north())) && magnetType.isInstance(this.WORLD.getTileEntity(center.south())))
			{
				return true;
			}
		}
		if(axis == Axis.Z)
		{
			if (magnetType.isInstance(this.WORLD.getTileEntity(center.east())) && magnetType.isInstance(this.WORLD.getTileEntity(center.west())))
			{
				return true;
			}
		}
		return false;	
	}
	
	
	
	public boolean isValidDipole(BlockPos center, boolean vertical)
	{
		if(vertical)
		{
			if(this.WORLD.getTileEntity(center.north()) instanceof TileAcceleratorMagnet )
			{
				Class magnetType = this.WORLD.getTileEntity(center.north()).getClass();
				if(!magnetType.isInstance(this.WORLD.getTileEntity(center.south())))
				{
					return false;
				}	
				if (!(this.WORLD.getTileEntity(center.north().up()) instanceof TileAcceleratorYoke) || 
						!(this.WORLD.getTileEntity(center.north().down()) instanceof TileAcceleratorYoke) ||
						!(this.WORLD.getTileEntity(center.north().east()) instanceof TileAcceleratorYoke) ||
						!(this.WORLD.getTileEntity(center.north().west()) instanceof TileAcceleratorYoke) ||
						!(this.WORLD.getTileEntity(center.north().up().east()) instanceof TileAcceleratorYoke) ||
						!(this.WORLD.getTileEntity(center.north().up().west()) instanceof TileAcceleratorYoke) ||
						!(this.WORLD.getTileEntity(center.north().down().east()) instanceof TileAcceleratorYoke) ||
						!(this.WORLD.getTileEntity(center.north().down().west()) instanceof TileAcceleratorYoke) ||
						!(this.WORLD.getTileEntity(center.south().up()) instanceof TileAcceleratorYoke) || 
						!(this.WORLD.getTileEntity(center.south().down()) instanceof TileAcceleratorYoke) ||
						!(this.WORLD.getTileEntity(center.south().east()) instanceof TileAcceleratorYoke) ||
						!(this.WORLD.getTileEntity(center.south().west()) instanceof TileAcceleratorYoke) ||
						!(this.WORLD.getTileEntity(center.south().up().east()) instanceof TileAcceleratorYoke) ||
						!(this.WORLD.getTileEntity(center.south().up().west()) instanceof TileAcceleratorYoke) ||
						!(this.WORLD.getTileEntity(center.south().down().east()) instanceof TileAcceleratorYoke) ||
						!(this.WORLD.getTileEntity(center.south().down().west()) instanceof TileAcceleratorYoke) ||
						!(this.WORLD.getTileEntity(center.up().east()) instanceof TileAcceleratorYoke) ||
						!(this.WORLD.getTileEntity(center.up().west()) instanceof TileAcceleratorYoke) ||
						!(this.WORLD.getTileEntity(center.down().east()) instanceof TileAcceleratorYoke) ||
						!(this.WORLD.getTileEntity(center.down().west()) instanceof TileAcceleratorYoke))		
				{		
					return false;
				}
				List<BlockPos> faces = new ArrayList<BlockPos>();
				faces.add(center.up());
				faces.add(center.down());
				faces.add(center.east());
				faces.add(center.west());
				
				for(BlockPos pos : faces)
				{
					if(!(this.WORLD.getTileEntity(pos) instanceof TileAcceleratorBeam) && !(this.WORLD.getTileEntity(pos) instanceof TileAcceleratorYoke))
					{
						
						return false;
					}
				}	
			}
			else if(this.WORLD.getTileEntity(center.east()) instanceof TileAcceleratorMagnet )
			{
				Class magnetType = this.WORLD.getTileEntity(center.east()).getClass();
				if(!magnetType.isInstance(this.WORLD.getTileEntity(center.west())))
				{
					return false;
				}
				
				if (!(this.WORLD.getTileEntity(center.east().up()) instanceof TileAcceleratorYoke) || 
						!(this.WORLD.getTileEntity(center.east().down()) instanceof TileAcceleratorYoke) ||
						!(this.WORLD.getTileEntity(center.east().north()) instanceof TileAcceleratorYoke) ||
						!(this.WORLD.getTileEntity(center.east().south()) instanceof TileAcceleratorYoke) ||
						!(this.WORLD.getTileEntity(center.east().up().north()) instanceof TileAcceleratorYoke) ||
						!(this.WORLD.getTileEntity(center.east().up().south()) instanceof TileAcceleratorYoke) ||
						!(this.WORLD.getTileEntity(center.east().down().north()) instanceof TileAcceleratorYoke) ||
						!(this.WORLD.getTileEntity(center.east().down().south()) instanceof TileAcceleratorYoke) ||
						!(this.WORLD.getTileEntity(center.west().up()) instanceof TileAcceleratorYoke) || 
						!(this.WORLD.getTileEntity(center.west().down()) instanceof TileAcceleratorYoke) ||
						!(this.WORLD.getTileEntity(center.west().north()) instanceof TileAcceleratorYoke) ||
						!(this.WORLD.getTileEntity(center.west().south()) instanceof TileAcceleratorYoke) ||
						!(this.WORLD.getTileEntity(center.west().up().north()) instanceof TileAcceleratorYoke) ||
						!(this.WORLD.getTileEntity(center.west().up().south()) instanceof TileAcceleratorYoke) ||
						!(this.WORLD.getTileEntity(center.west().down().north()) instanceof TileAcceleratorYoke) ||
						!(this.WORLD.getTileEntity(center.west().down().south()) instanceof TileAcceleratorYoke) ||
						!(this.WORLD.getTileEntity(center.up().north()) instanceof TileAcceleratorYoke) ||
						!(this.WORLD.getTileEntity(center.up().south()) instanceof TileAcceleratorYoke) ||
						!(this.WORLD.getTileEntity(center.down().north()) instanceof TileAcceleratorYoke) ||
						!(this.WORLD.getTileEntity(center.down().south()) instanceof TileAcceleratorYoke))		
				{
					
					return false;
				}
				List<BlockPos> faces = new ArrayList<BlockPos>();
				faces.add(center.up());
				faces.add(center.down());
				faces.add(center.north());
				faces.add(center.south());
				
				for(BlockPos pos : faces)
				{
					if(!(this.WORLD.getTileEntity(pos) instanceof TileAcceleratorBeam) && !(this.WORLD.getTileEntity(pos) instanceof TileAcceleratorYoke))
					{
						
						return false;
					}
				}
			}
			else
			{
				return false;
			}
		}
		else
		{
			if(!(this.WORLD.getTileEntity(center.up()) instanceof TileAcceleratorMagnet))
			{
				return false;
			}
			
			Class magnetType = this.WORLD.getTileEntity(center.up()).getClass();
			
			if(!magnetType.isInstance(this.WORLD.getTileEntity(center.down())))
			{
				return false;
			}
			
			if (!(this.WORLD.getTileEntity(center.up().north()) instanceof TileAcceleratorYoke) || 
					!(this.WORLD.getTileEntity(center.up().south()) instanceof TileAcceleratorYoke) ||
					!(this.WORLD.getTileEntity(center.up().east()) instanceof TileAcceleratorYoke) ||
					!(this.WORLD.getTileEntity(center.up().west()) instanceof TileAcceleratorYoke) ||
					!(this.WORLD.getTileEntity(center.up().north().east()) instanceof TileAcceleratorYoke) ||
					!(this.WORLD.getTileEntity(center.up().north().west()) instanceof TileAcceleratorYoke) ||
					!(this.WORLD.getTileEntity(center.up().south().east()) instanceof TileAcceleratorYoke) ||
					!(this.WORLD.getTileEntity(center.up().south().west()) instanceof TileAcceleratorYoke) ||
					!(this.WORLD.getTileEntity(center.down().north()) instanceof TileAcceleratorYoke)|| 
					!(this.WORLD.getTileEntity(center.down().south()) instanceof TileAcceleratorYoke)||
					!(this.WORLD.getTileEntity(center.down().east()) instanceof TileAcceleratorYoke) ||
					!(this.WORLD.getTileEntity(center.down().west()) instanceof TileAcceleratorYoke) ||
					!(this.WORLD.getTileEntity(center.down().north().east()) instanceof TileAcceleratorYoke) ||
					!(this.WORLD.getTileEntity(center.down().north().west()) instanceof TileAcceleratorYoke) ||
					!(this.WORLD.getTileEntity(center.down().south().east()) instanceof TileAcceleratorYoke) ||
					!(this.WORLD.getTileEntity(center.down().south().west()) instanceof TileAcceleratorYoke) ||
					!(this.WORLD.getTileEntity(center.north().east()) instanceof TileAcceleratorYoke) ||
					!(this.WORLD.getTileEntity(center.north().west()) instanceof TileAcceleratorYoke) ||
					!(this.WORLD.getTileEntity(center.south().east()) instanceof TileAcceleratorYoke) ||
					!(this.WORLD.getTileEntity(center.south().west()) instanceof TileAcceleratorYoke))		
			{
				
				return false;
			}
			List<BlockPos> faces = new ArrayList<BlockPos>();
			faces.add(center.north());
			faces.add(center.south());
			faces.add(center.east());
			faces.add(center.west());
			
			for(BlockPos pos : faces)
			{
				if(!(this.WORLD.getTileEntity(pos) instanceof TileAcceleratorBeam) && !(this.WORLD.getTileEntity(pos) instanceof TileAcceleratorYoke))
				{
					
					return false;
				}
			}
		}

		return true;	
	}
	
	// Multiblock Methods

	@Override
	public void onAttachedPartWithMultiblockData(IAcceleratorPart part, NBTTagCompound data)
	{
		logic.onAttachedPartWithMultiblockData(part, data);
		syncDataFrom(data, SyncReason.FullSync);
	}

	@Override
	protected void onBlockAdded(IAcceleratorPart newPart)
	{
		onPartAdded(newPart);
		logic.onBlockAdded(newPart);
	}

	@Override
	protected void onBlockRemoved(IAcceleratorPart oldPart)
	{
		onPartRemoved(oldPart);
		logic.onBlockRemoved(oldPart);
	}

	@Override
	protected void onMachineAssembled()
	{
		logic.onMachineAssembled();
	}

	@Override
	protected void onMachineRestored()
	{
		logic.onMachineRestored();
	}

	@Override
	protected void onMachinePaused()
	{
		logic.onMachinePaused();
	}

	@Override
	protected void onMachineDisassembled()
	{
		logic.onMachineDisassembled();
	}

	@Override
	protected boolean isMachineWhole()
	{
		return setLogic(this)  && super.isMachineWhole() && logic.isMachineWhole();
	}

	public boolean setLogic(Multiblock multiblock)
	{
		if (getPartMap(IAcceleratorController.class).isEmpty()) {
			multiblock.setLastError(Global.MOD_ID + ".multiblock_validation.no_controller", null);
			return false;
		}
		if (getPartMap(IAcceleratorController.class).size() > 1) {
			multiblock.setLastError(Global.MOD_ID + ".multiblock_validation.too_many_controllers", null);
			return false;
		}
		
		for (IAcceleratorController contr : getPartMap(IAcceleratorController.class).values()) 
		{
			controller = contr;
		}
		
		setLogic(controller.getLogicID());
		
		return true;
	}

	@Override
	protected void onAssimilate(Accelerator assimilated)
	{
		logic.onAssimilate(assimilated);
	}

	@Override
	protected void onAssimilated(Accelerator assimilator)
	{
		logic.onAssimilated(assimilator);
	}

	// Server

	@Override
	protected boolean updateServer()
	{
		boolean flag = refreshFlag;
		
		if (refreshFlag) 
		{
			//logic.refreshAccelerator();
		}
		updateActivity();
		
		if (logic.onUpdateServer()) 
		{
			flag = true;
		}
		
		if (controller != null) 
		{
			sendMultiblockUpdatePacketToListeners();
		}
		
		return flag;
	}
	
	
	public void updateActivity()
	{
		boolean wasAcceleratorOn = isAcceleratorOn;
		isAcceleratorOn = isAssembled() && logic.isAcceleratorOn();
		if (isAcceleratorOn != wasAcceleratorOn)
		{
			if (controller != null)
			{
				
				controller.setActivity(isAcceleratorOn);
				sendMultiblockUpdatePacketToAll();
			}
		}
	}
	
	public int getTemperature() 
	{
		return Math.round(MAX_TEMP*(float)heatBuffer.getHeatStored()/heatBuffer.getHeatCapacity());
	}
	
	public long getExternalHeating() 
	{
		return (long) ((ambientTemp - getTemperature()) * QMDConfig.accelerator_thermal_conductivity * getExteriorSurfaceArea());
	}
	
	public long getMaxExternalHeating() 
	{
		return (long) (ambientTemp * QMDConfig.accelerator_thermal_conductivity * getExteriorSurfaceArea());
	}

	
	public void switchIO()
	{
		
		boolean changed = false;
		for (TileAcceleratorBeamPort port :getPartMap(TileAcceleratorBeamPort.class).values())
		{
			if(port.isTriggered())
			{	
				if(port.getSetting() != port.getIOType())
				{	
					port.switchMode();
					changed = true;
					if(port.getIOType() == IOType.INPUT)
					{
						input = port;
						
					}
					if(port.getIOType() == IOType.OUTPUT)
					{
						output = port;
					}
				}
				port.resetTrigger();
			}
		}
		
		if(changed)
		{
			for (TileAcceleratorBeamPort port :getPartMap(TileAcceleratorBeamPort.class).values())
			{
				if(port != input && port != output)
				{
					port.setIOType(IOType.DISABLED);
				}
			}
			checkIfMachineIsWhole();
		}
	}
	
	
	
	
	
	// Client

	@Override
	protected void updateClient()
	{
		logic.onUpdateClient();
	}

	// NBT

	@Override
	public void syncDataTo(NBTTagCompound data, SyncReason syncReason)
	{
		heatBuffer.writeToNBT(data, "heatBuffer");
		energyStorage.writeToNBT(data,"energyStorage");
		writeTanks(tanks,data,"tanks");
		writeBeams(beams,data);
		
		data.setBoolean("isAcceleratorOn", isAcceleratorOn);
		data.setLong("cooling", cooling);
		data.setLong("rawHeating", rawHeating);
		data.setInteger("coolantIn", maxCoolantIn);
		data.setInteger("coolantOut", maxCoolantOut);
		data.setDouble("maxOperatingTemp", maxOperatingTemp);
		data.setLong("requiredEnergy", requiredEnergy);
		data.setDouble("efficiency",efficiency);
		data.setInteger("acceleratingVoltage", acceleratingVoltage);
		data.setInteger("RFCavityNumber", RFCavityNumber);
		data.setInteger("quadrapoleNumber", quadrupoleNumber);
		data.setDouble("quadrupoleStrength", quadrupoleStrength);
		data.setInteger("dipoleNumber", dipoleNumber);
		data.setDouble("dipoleStrength", dipoleStrength);
		data.setInteger("errorCode",errorCode);
		data.setBoolean("cold", cold);
		data.setBoolean("computerControlled", computerControlled);
		data.setInteger("energyPercentage", energyPercentage);
		
		writeLogicNBT(data, syncReason);
	}

	@Override
	public void syncDataFrom(NBTTagCompound data, SyncReason syncReason)
	{
		heatBuffer.readFromNBT(data,"heatBuffer");
		energyStorage.readFromNBT(data,"energyStorage");
		readTanks(tanks,data, "tanks");
		readBeams(beams,data);
		
		
		
		isAcceleratorOn = data.getBoolean("isAcceleratorOn");
		cooling = data.getLong("cooling");
		rawHeating = data.getLong("rawHeating");
		maxCoolantIn = data.getInteger("coolantIn");
		maxCoolantOut = data.getInteger("coolantOut");
		maxOperatingTemp =data.getInteger("maxOperatingTemp");
		requiredEnergy = data.getInteger("requiredEnergy");
		efficiency = data.getDouble("efficiency");
		acceleratingVoltage = data.getInteger("acceleratingVoltage");
		RFCavityNumber = data.getInteger("RFCavityNumber");
		quadrupoleNumber = data.getInteger("quadrapoleNumber");
		quadrupoleStrength = data.getDouble("quadrupoleStrength");
		dipoleNumber = data.getInteger("dipoleNumber");
		dipoleStrength = data.getDouble("dipoleStrength");
		errorCode = data.getInteger("errorCode");
		cold = data.getBoolean("cold");
		computerControlled =data.getBoolean("computerControlled");
		energyPercentage =data.getInteger("energyPercentage");
		
		readLogicNBT(data, syncReason);
	}

	
	
	
	// Packets
	
	@Override
	public Set<EntityPlayer> getMultiblockUpdatePacketListeners() {
		return updatePacketListeners;
	}

	@Override
	public AcceleratorUpdatePacket getMultiblockUpdatePacket()
	{
		return logic.getMultiblockUpdatePacket();
	}

	@Override
	public void onMultiblockUpdatePacket(AcceleratorUpdatePacket message)
	{
		heatBuffer.setHeatCapacity(message.heatBuffer.getHeatCapacity());
		heatBuffer.setHeatStored(message.heatBuffer.getHeatStored());
		energyStorage.setStorageCapacity(message.energyStorage.getMaxEnergyStored());
		energyStorage.setEnergyStored(message.energyStorage.getEnergyStored());
		
		for (int i = 0; i < tanks.size(); i++) tanks.get(i).readInfo(message.tanksInfo.get(i));
		for(int i = 0; i< message.beams.size(); i++) beams.set(i, message.beams.get(i));
		
		isAcceleratorOn = message.isAcceleratorOn;
		cooling = message.cooling;
		rawHeating = message.rawHeating;
		currentHeating = message.currentHeating;
		maxCoolantIn = message.maxCoolantIn;
		maxCoolantOut = message.maxCoolantOut;
		maxOperatingTemp = message.maxOperatingTemp;
		requiredEnergy = message.requiredEnergy;
		efficiency = message.efficiency;
		acceleratingVoltage = message.acceleratingVoltage;
		RFCavityNumber = message.RFCavityNumber;
		quadrupoleNumber = message.quadrupoleNumber;
		quadrupoleStrength = message.quadrupoleStrength;
		dipoleNumber = message.dipoleNumber;
		dipoleStrength = message.dipoleStrength;
		errorCode =message.errorCode;
		
		logic.onMultiblockUpdatePacket(message);
	}

	/*public ContainerMultiblockController<Accelerator, IAcceleratorController> getContainer(EntityPlayer player)
	{
		return logic.getContainer(player);
	}*/

	@Override
	public void clearAllMaterial()
	{
		logic.clearAllMaterial();
		super.clearAllMaterial();
	}

	// Multiblock Validators

	@Override
	protected boolean isBlockGoodForInterior(World world, BlockPos pos)
	{
		return logic.isBlockGoodForInterior(world, pos);
	}



	@Override
	protected int getMinimumInteriorLength()
	{
		return logic.getMinimumInteriorLength();
	}

	@Override
	protected int getMaximumInteriorLength()
	{
		return logic.getMaximumInteriorLength();
	}

	public NBTTagCompound writeBeams(List<ParticleStorageAccelerator> beams, NBTTagCompound data)
	{
		for (int i = 0; i < beams.size(); i++)
		{
			beams.get(i).writeToNBT(data, i);
		}
		
		return data;
	}

	public void readBeams(List<ParticleStorageAccelerator> beams, NBTTagCompound data)
	{
		for (int i = 0; i < beams.size(); i++)
		{
			beams.get(i).readFromNBT(data, i);
		}
	}

	@Override
	public List<Tank> getTanks()
	{
		return tanks;
	}

}
