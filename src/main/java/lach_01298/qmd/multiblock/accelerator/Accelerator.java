package lach_01298.qmd.multiblock.accelerator;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import com.google.common.collect.Lists;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import lach_01298.qmd.multiblock.CuboidalOrToroidalMultiblock;
import lach_01298.qmd.multiblock.accelerator.tile.IAcceleratorController;
import lach_01298.qmd.multiblock.accelerator.tile.IAcceleratorPart;
import lach_01298.qmd.multiblock.accelerator.tile.TileAcceleratorBeam;
import lach_01298.qmd.multiblock.accelerator.tile.TileAcceleratorBeamPort;
import lach_01298.qmd.multiblock.accelerator.tile.TileAcceleratorEnergyPort;
import lach_01298.qmd.multiblock.accelerator.tile.TileAcceleratorMagnet;
import lach_01298.qmd.multiblock.accelerator.tile.TileAcceleratorPort;
import lach_01298.qmd.multiblock.accelerator.tile.TileAcceleratorRFCavity;
import lach_01298.qmd.multiblock.accelerator.tile.TileAcceleratorSource;
import lach_01298.qmd.multiblock.accelerator.tile.TileAcceleratorYoke;
import lach_01298.qmd.multiblock.network.AcceleratorUpdatePacket;
import lach_01298.qmd.particle.ParticleBeam;
import nc.Global;
import nc.config.NCConfig;
import nc.multiblock.ILogicMultiblock;
import nc.multiblock.ITileMultiblockPart;
import nc.multiblock.Multiblock;
import nc.multiblock.ILogicMultiblock.PartSuperMap;
import nc.multiblock.TileBeefBase.SyncReason;
import nc.multiblock.container.ContainerMultiblockController;
import nc.multiblock.cuboidal.CuboidalMultiblock;
import nc.multiblock.fission.FissionCluster;
import nc.multiblock.network.FissionUpdatePacket;
import nc.recipe.NCRecipes;
import nc.tile.internal.energy.EnergyStorage;
import nc.tile.internal.fluid.Tank;
import nc.tile.internal.heat.HeatBuffer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidRegistry;

public class Accelerator extends CuboidalOrToroidalMultiblock<AcceleratorUpdatePacket> implements ILogicMultiblock<AcceleratorLogic, IAcceleratorPart>
{

	public static final ObjectSet<Class<? extends IAcceleratorPart>> PART_CLASSES = new ObjectOpenHashSet<>();

	protected @Nonnull AcceleratorLogic logic = new AcceleratorLogic(this);

	protected @Nonnull NBTTagCompound cachedData = new NBTTagCompound();

	protected final PartSuperMap<IAcceleratorPart> partSuperMap = new PartSuperMap<>();

	protected final Long2ObjectMap<TileAcceleratorBeam> beamMap = new Long2ObjectOpenHashMap<>();
	protected final Long2ObjectMap<RFCavity> rfCavityMap = new Long2ObjectOpenHashMap<>();
	protected final Long2ObjectMap<QuadrupoleMagnet> quadrupoleMap = new Long2ObjectOpenHashMap<>();
	protected final Long2ObjectMap<DipoleMagnet> dipoleMap = new Long2ObjectOpenHashMap<>();
	
	

	
	protected final ObjectSet<TileAcceleratorPort> ports = new ObjectOpenHashSet<>();
	protected final ObjectSet<TileAcceleratorBeamPort> beamPorts = new ObjectOpenHashSet<>();
	protected final ObjectSet<TileAcceleratorEnergyPort> energyPorts = new ObjectOpenHashSet<>();
	
	
	
	
	public IAcceleratorController controller;

	
	
	public static final int BASE_MAX_HEAT = 25000, MAX_TEMP = 2400;  
	public static final int	BASE_MAX_ENERGY = 100000;
	public static final int BASE_TANK_CAPACITY = 4000, BASE_MAX_INPUT = 4000, BASE_MAX_OUTPUT = 16000;
	
	public final HeatBuffer heatBuffer = new HeatBuffer(BASE_MAX_HEAT);
	public final EnergyStorage energyStorage = new EnergyStorage(BASE_MAX_ENERGY);
	public final ParticleBeam beam = new ParticleBeam();
	public final List<Tank> tanks = Lists.newArrayList(new Tank(BASE_MAX_INPUT, NCRecipes.turbine_valid_fluids.get(0)), new Tank(BASE_MAX_OUTPUT, null));
	
	public boolean logicInit = false, refreshFlag = true, isAcceleratorOn = false;
	
	public int ambientTemp = 290;
	public long cooling = 0L, rawHeating = 0L;
	public double efficiency = 0D;
	public int requiredEnergy = 0, acceleratingVoltage =0;
	public int RFCavityNumber =0, quadrupoleNumber =0;
	public double quadrupoleStrength =0;
	
	private static final int thickness = 5;
	
	
	public Accelerator(World world)
	{
		super(world,thickness);
		for (Class<? extends IAcceleratorPart> clazz : PART_CLASSES)
		{
			partSuperMap.equip(clazz);
		}
	}

	@Override
	public @Nonnull AcceleratorLogic getLogic()
	{
		return logic;
	}

	@Override
	public PartSuperMap<IAcceleratorPart> getPartSuperMap()
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
	

	public ObjectSet<TileAcceleratorPort> getPorts()
	{
		return ports;
	}
	
	public ObjectSet<TileAcceleratorBeamPort> getBeamPorts()
	{
		return beamPorts;
	}
	
	public ObjectSet<TileAcceleratorEnergyPort> getEnergyPorts()
	{
		return energyPorts;
	}
	
	
	

	public void resetStats()
	{
		logic.onResetStats();
		cooling = rawHeating  = 0L;
		quadrupoleStrength = efficiency = 0D;
		RFCavityNumber = quadrupoleNumber = acceleratingVoltage = requiredEnergy = 0;
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
		if(!(this.WORLD.getTileEntity(center.up()) instanceof TileAcceleratorMagnet) )
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
	
	
	
	public boolean isValidDipole(BlockPos center, boolean conner)
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
		int beams= 0;
		for(BlockPos pos : faces)
		{
			if(this.WORLD.getTileEntity(pos) instanceof TileAcceleratorBeam)
			{
				beams++;
			}
		}
		if(beams < 2)
		{
			
			return false;
		}
		if(beams != 2 && !conner)
		{

			return false;
		}

		return true;	
	}
	
	// Multiblock Methods

	@Override
	public void onAttachedPartWithMultiblockData(ITileMultiblockPart part, NBTTagCompound data)
	{
		logic.onAttachedPartWithMultiblockData(part, data);
		syncDataFrom(data, SyncReason.FullSync);
	}

	@Override
	protected void onBlockAdded(ITileMultiblockPart newPart)
	{
		onPartAdded(newPart);
		logic.onBlockAdded(newPart);
	}

	@Override
	protected void onBlockRemoved(ITileMultiblockPart oldPart)
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
	protected boolean isMachineWhole(Multiblock multiblock)
	{
		return setLogic(multiblock)  && super.isMachineWhole(multiblock) && logic.isMachineWhole(multiblock);
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
		
		logicInit = logic.getClass() == controller.getLogicClass();
		if (!logicInit) 
		{
			logic.unload();
			logic = controller.createNewLogic(logic);
			syncDataFrom(cachedData, SyncReason.FullSync);
			cachedData = new NBTTagCompound();
			logic.load();
		}
		
		return logicInit = true;
	}

	@Override
	protected void onAssimilate(Multiblock assimilated)
	{
		logic.onAssimilate(assimilated);
	}

	@Override
	protected void onAssimilated(Multiblock assimilator)
	{
		logic.onAssimilated(assimilator);
	}

	// Server

	@Override
	protected boolean updateServer()
	{
		if (refreshFlag) {
			logic.refreshAccelerator();
		}
		updateActivity();
		
		if (logic.onUpdateServer()) 
		{
			return true;
		}
		
		logic.updateRedstone();
		sendUpdateToListeningPlayers();
		
		return true;
	}
	
	
	public void updateActivity()
	{
		boolean wasAcceleratorOn = isAcceleratorOn;
		isAcceleratorOn = isAssembled() && logic.isAcceleratorOn();
		if (isAcceleratorOn != wasAcceleratorOn)
		{
			if (controller != null)
			{
				controller.updateBlockState(isAcceleratorOn);
			}
			sendUpdateToAllPlayers();
		}
	}
	
	public int getTemperature() 
	{
		return Math.round(ambientTemp + (MAX_TEMP - ambientTemp)*(float)heatBuffer.getHeatStored()/heatBuffer.getHeatCapacity());
	}

	// Client

	@Override
	protected void updateClient()
	{
		logic.onUpdateClient();
	}

	// NBT

	@Override
	protected void syncDataTo(NBTTagCompound data, SyncReason syncReason)
	{
		heatBuffer.writeToNBT(data);
		energyStorage.writeToNBT(data);
		beam.writeToNBT(data);
		writeTanks(tanks,data);
		
		data.setBoolean("isAcceleratorOn", isAcceleratorOn);
		data.setLong("cooling", cooling);
		data.setLong("rawHeating", rawHeating);
		data.setLong("requiredEnergy", requiredEnergy);
		data.setDouble("efficiency",efficiency);
		data.setInteger("acceleratingVoltage", acceleratingVoltage);
		data.setInteger("RFCavityNumber", RFCavityNumber);
		data.setInteger("quadrapoleNumber", quadrupoleNumber);
		data.setDouble("quadrupoleStrength", quadrupoleStrength);
	
		
		
		
		logic.writeToNBT(data, syncReason);
	}

	@Override
	protected void syncDataFrom(NBTTagCompound data, SyncReason syncReason)
	{
		heatBuffer.readFromNBT(data);
		energyStorage.readFromNBT(data);
		beam.readFromNBT(data);
		readTanks(tanks,data);
		
		isAcceleratorOn = data.getBoolean("isAcceleratorOn");
		cooling = data.getLong("cooling");
		rawHeating = data.getLong("rawHeating");
		requiredEnergy = data.getInteger("requiredEnergy");
		efficiency = data.getDouble("efficiency");
		acceleratingVoltage = data.getInteger("acceleratingVoltage");
		RFCavityNumber = data.getInteger("RFCavityNumber");
		quadrupoleNumber = data.getInteger("quadrapoleNumber");
		quadrupoleStrength = data.getDouble("quadrupoleStrength");
		
		if (!logicInit) 
		{
			cachedData = data.copy();
			logicInit = true;
		}
		logic.readFromNBT(data, syncReason);	
	}

	
	
	
	// Packets

	@Override
	protected AcceleratorUpdatePacket getUpdatePacket()
	{
		return logic.getUpdatePacket();
	}

	@Override
	public void onPacket(AcceleratorUpdatePacket message)
	{
		heatBuffer.setHeatCapacity(message.heatBuffer.getHeatCapacity());
		heatBuffer.setHeatStored(message.heatBuffer.getHeatStored());
		energyStorage.setStorageCapacity(message.energyStorage.getEnergyStored());
		energyStorage.setEnergyStored(message.energyStorage.getEnergyStored());
		//readTanks(message.)
		beam.setParticle(message.beam.getParticle());
		beam.setMeanEnergy(message.beam.getMeanEnergy());
		beam.setEnergySpread(message.beam.getEnergySpread());
		beam.setLuminosity(message.beam.getLuminosity());
		
		
		isAcceleratorOn = message.isAcceleratorOn;
		cooling = message.cooling;
		rawHeating = message.rawHeating;
		requiredEnergy = message.requiredEnergy;
		efficiency = message.efficiency;
		acceleratingVoltage = message.acceleratingVoltage;
		RFCavityNumber = message.RFCavityNumber;
		quadrupoleNumber = message.quadrupoleNumber;
		quadrupoleStrength = message.quadrupoleStrength;
		
		
		logic.onPacket(message);
	}

	public ContainerMultiblockController<Accelerator, IAcceleratorController> getContainer(EntityPlayer player)
	{
		return logic.getContainer(player);
	}

	@Override
	public void clearAllMaterial()
	{
		for (Tank tank : tanks) 
		{
			tank.setFluidStored(null);
		}
		
		logic.clearAllMaterial();

		ILogicMultiblock.super.clearAllMaterial();

		if (!WORLD.isRemote)
		{
			logic.refreshAccelerator();
			updateActivity();
		}
	}

	// Multiblock Validators

	@Override
	protected boolean isBlockGoodForInterior(World world, int x, int y, int z, Multiblock multiblock)
	{
		return logic.isBlockGoodForInterior(world, x, y, z, multiblock);
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
}
