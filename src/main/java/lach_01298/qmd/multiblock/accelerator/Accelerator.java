package lach_01298.qmd.multiblock.accelerator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Lists;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import lach_01298.qmd.config.QMDConfig;
import lach_01298.qmd.multiblock.CuboidalOrToroidalMultiblockBase;
import lach_01298.qmd.multiblock.accelerator.container.ContainerAcceleratorController;
import lach_01298.qmd.multiblock.accelerator.tile.TileAcceleratorBeam;
import lach_01298.qmd.multiblock.accelerator.tile.TileAcceleratorController;
import lach_01298.qmd.multiblock.accelerator.tile.TileAcceleratorCooler;
import lach_01298.qmd.multiblock.accelerator.tile.TileAcceleratorMagnet;
import lach_01298.qmd.multiblock.accelerator.tile.TileAcceleratorPartBase;
import lach_01298.qmd.multiblock.accelerator.tile.TileAcceleratorPort;
import lach_01298.qmd.multiblock.accelerator.tile.TileAcceleratorRFCavity;
import lach_01298.qmd.multiblock.accelerator.tile.TileAcceleratorSource;
import lach_01298.qmd.multiblock.accelerator.tile.TileAcceleratorTarget;
import lach_01298.qmd.multiblock.accelerator.tile.TileAcceleratorYoke;
import lach_01298.qmd.multiblock.network.AcceleratorRenderPacket;
import lach_01298.qmd.multiblock.network.AcceleratorUpdatePacket;
import nc.Global;
import nc.config.NCConfig;
import nc.handler.SoundHandler;
import nc.handler.SoundHandler.SoundInfo;
import nc.init.NCSounds;
import nc.multiblock.IMultiblockPart;
import nc.multiblock.MultiblockBase;
import nc.multiblock.TileBeefBase.SyncReason;
import nc.multiblock.container.ContainerTurbineController;
import nc.multiblock.cuboidal.CuboidalMultiblockBase;
import nc.multiblock.fission.FissionCluster;
import nc.multiblock.fission.FissionReactor;
import nc.multiblock.fission.salt.tile.TileSaltFissionHeater;
import nc.multiblock.fission.salt.tile.TileSaltFissionVessel;
import nc.multiblock.fission.solid.tile.TileSolidFissionCell;
import nc.multiblock.fission.tile.IFissionController;
import nc.multiblock.fission.tile.TileFissionPort;
import nc.multiblock.fission.tile.TileFissionVent;
import nc.multiblock.turbine.tile.TileTurbineDynamoCoil;
import nc.multiblock.turbine.tile.TileTurbineOutlet;
import nc.multiblock.turbine.tile.TileTurbineRotorShaft;
import nc.multiblock.validation.IMultiblockValidator;
import nc.network.PacketHandler;
import nc.recipe.NCRecipes;
import nc.recipe.ProcessorRecipe;
import nc.recipe.ProcessorRecipeHandler;
import nc.recipe.RecipeInfo;
import nc.recipe.ingredient.IFluidIngredient;
import nc.tile.internal.energy.EnergyStorage;
import nc.tile.internal.fluid.Tank;
import nc.tile.internal.fluid.TankSorption;
import nc.tile.internal.heat.HeatBuffer;
import nc.util.MaterialHelper;
import nc.util.NCMath;
import nc.util.NCUtil;
import nc.util.SoundHelper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.EnumFacing.AxisDirection;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Accelerator extends CuboidalOrToroidalMultiblockBase<AcceleratorUpdatePacket>
{
	
	
	protected final ObjectSet<TileAcceleratorController> controllers = new ObjectOpenHashSet<>();
	protected final ObjectSet<TileAcceleratorTarget> targets = new ObjectOpenHashSet<>();
	protected final ObjectSet<TileAcceleratorSource> sources = new ObjectOpenHashSet<>();
	protected final ObjectSet<TileAcceleratorPort> ports = new ObjectOpenHashSet<>();
	
	protected final Long2ObjectMap<TileAcceleratorRFCavity> RFCavityMap = new Long2ObjectOpenHashMap<>();
	protected final Long2ObjectMap<TileAcceleratorMagnet> magnetMap = new Long2ObjectOpenHashMap<>();
	protected final Long2ObjectMap<TileAcceleratorBeam> beamMap = new Long2ObjectOpenHashMap<>();
	protected final Long2ObjectMap<TileAcceleratorYoke> yokeMap = new Long2ObjectOpenHashMap<>();
	protected final Long2ObjectMap<TileAcceleratorCooler> coolerMap = new Long2ObjectOpenHashMap<>();
	
	protected final Long2ObjectMap<DipoleMagnet> dipoleMap = new Long2ObjectOpenHashMap<>();
	protected final Long2ObjectMap<QuadrupoleMagnet> quadrupoleMap = new Long2ObjectOpenHashMap<>();
	protected final Long2ObjectMap<RFCavity> cavityMap = new Long2ObjectOpenHashMap<>();
	
	protected TileAcceleratorController controller;
	//TODO liquid helium
	public final List<Tank> tanks = Lists.newArrayList(new Tank(BASE_MAX_INPUT, NCRecipes.turbine_valid_fluids.get(0)), new Tank(BASE_MAX_OUTPUT, null));
	
	//TODO max temp to 10 K
	protected static final int BASE_MAX_ENERGY = 64000, BASE_MAX_HEAT = 25000, MAX_TEMP = 1000, BASE_MAX_INPUT = 4000, BASE_MAX_OUTPUT = 16000;

	public final EnergyStorage energyStorage = new EnergyStorage(BASE_MAX_ENERGY);
	public final HeatBuffer heatBuffer = new HeatBuffer(BASE_MAX_HEAT);
	
	public int ambientTemp = 290;
	
	protected int updateCount = 0;

	public boolean isAcceleratorOn, computerActivated, isProcessing;
	 
	public EnumFacing flowDir = null;

	public long cooling =0;
	public long heating =0;
	public long netHeating =0;
	public int requiredEnergy = 0;
	public int requiredCoolant = 0;
	
	public double totalEfficiency = 0;
	
	public int quadrupoleNumber =0;
	public double quadrupoleStrength =0;
	public double luminosity = 0D;
	
	public int dipoleNumber =0;
	public double dipoleStrength =0;
	
	public int RFCavityNumber =0;
	public int RFCavityVoltage = 0;
	public int maxParticleEnergy = 0; //in keV
	
	
	public Accelerator(World world)
	{
		super(world, 5);
	}

	// Multiblock Part Getters

	public ObjectSet<TileAcceleratorController> getControllers()
	{
		return controllers;
	}

	public ObjectSet<TileAcceleratorTarget> getTargets()
	{
		return targets;
	}

	public ObjectSet<TileAcceleratorSource> getSources()
	{
		return sources;
	}
	
	public ObjectSet<TileAcceleratorPort> getPorts()
	{
		return ports;
	}
	
	public Long2ObjectMap<TileAcceleratorRFCavity> getRFCavityMap() 
	{
		return RFCavityMap;
	}
	
	public Long2ObjectMap<TileAcceleratorMagnet> getMagnetMap() 
	{
		return magnetMap;
	}
	
	public Long2ObjectMap<TileAcceleratorBeam> getBeamMap() 
	{
		return beamMap;
	}
	
	public Long2ObjectMap<TileAcceleratorYoke> getYokeMap() 
	{
		return yokeMap;
	}
	
	public Long2ObjectMap<TileAcceleratorCooler> getCoolerMap() 
	{
		return coolerMap;
	}

	
	// Multiblock Size Limits
	
	@Override
	protected int getMinimumInteriorLength() 
	{
		return QMDConfig.accelerator_min_length;
	}
	
	@Override
	protected int getMaximumInteriorLength() 
	{
		return QMDConfig.accelerator_max_length;
	}
	
	@Override
	protected int getMinimumNumberOfBlocksForAssembledMachine() 
	{
		return NCMath.hollowCuboid(Math.max(5, getMinimumInteriorLength() + 2), Math.max(5, getMinimumInteriorLength() + 2), getMinimumInteriorLength() + 2);
	}
	
	
	
	// Multiblock Methods
	
	@Override
	public void onAttachedPartWithMultiblockData(IMultiblockPart part, NBTTagCompound data) 
	{
		syncDataFrom(data, SyncReason.FullSync);
	}
	
	@Override
	protected void onBlockAdded(IMultiblockPart newPart) 
	{
		if (newPart instanceof TileAcceleratorController) controllers.add((TileAcceleratorController) newPart);
		if (newPart instanceof TileAcceleratorTarget) targets.add((TileAcceleratorTarget) newPart);
		if (newPart instanceof TileAcceleratorSource) sources.add((TileAcceleratorSource) newPart);
		if (newPart instanceof TileAcceleratorPort) ports.add((TileAcceleratorPort) newPart);
		if (newPart instanceof TileAcceleratorRFCavity) RFCavityMap.put(newPart.getTilePos().toLong(), (TileAcceleratorRFCavity) newPart);
		if (newPart instanceof TileAcceleratorMagnet) magnetMap.put(newPart.getTilePos().toLong(), (TileAcceleratorMagnet) newPart);
		if (newPart instanceof TileAcceleratorBeam) beamMap.put(newPart.getTilePos().toLong(), (TileAcceleratorBeam) newPart);
		if (newPart instanceof TileAcceleratorYoke) yokeMap.put(newPart.getTilePos().toLong(), (TileAcceleratorYoke) newPart);
		if (newPart instanceof TileAcceleratorCooler) coolerMap.put(newPart.getTilePos().toLong(), (TileAcceleratorCooler) newPart);
	}
	
	@Override
	protected void onBlockRemoved(IMultiblockPart oldPart) 
	{
		if (oldPart instanceof TileAcceleratorController) controllers.remove(oldPart);
		if (oldPart instanceof TileAcceleratorTarget) targets.remove(oldPart);
		if (oldPart instanceof TileAcceleratorSource) sources.remove(oldPart);
		if (oldPart instanceof TileAcceleratorPort) ports.remove(oldPart);
		if (oldPart instanceof TileAcceleratorRFCavity) RFCavityMap.remove(oldPart.getTilePos().toLong());
		if (oldPart instanceof TileAcceleratorMagnet) magnetMap.remove(oldPart.getTilePos().toLong());
		if (oldPart instanceof TileAcceleratorBeam) beamMap.remove(oldPart.getTilePos().toLong());
		if (oldPart instanceof TileAcceleratorYoke) yokeMap.remove(oldPart.getTilePos().toLong());
		if (oldPart instanceof TileAcceleratorCooler) coolerMap.remove(oldPart.getTilePos().toLong());

	}
	
	@Override
	protected void onMachineAssembled() 
	{
		onAcceleratorFormed();
	}
	
	@Override
	protected void onMachineRestored() 
	{
		onAcceleratorFormed();
	}
	
	protected void onAcceleratorFormed() 
	{
		for (TileAcceleratorController control : controllers) controller = control;
		
		energyStorage.setEnergyStored(BASE_MAX_ENERGY * getExteriorVolume());
		energyStorage.setMaxTransfer(BASE_MAX_ENERGY * getExteriorVolume());
		heatBuffer.setHeatCapacity(BASE_MAX_HEAT*getExteriorVolume());
		tanks.get(0).setCapacity(BASE_MAX_INPUT * getExteriorVolume());
		tanks.get(1).setCapacity(BASE_MAX_OUTPUT * getExteriorVolume());
		
		ambientTemp = 273 + (int) (WORLD.getBiome(getMiddleCoord()).getTemperature(getMiddleCoord())*20F);
		
		if (!WORLD.isRemote) 
		{
		
			
			
			for (TileAcceleratorPort port : ports)
			{
				for (EnumFacing side : EnumFacing.VALUES)
				{
					port.setTankSorption(side, 0, side == flowDir ? TankSorption.OUT : TankSorption.NON);
				}
			}
			
			
			
			
		}

	}

	
	
	
	protected void refreshQuadrupoleMagnets() 
	{
		if (quadrupoleMap.isEmpty()) 
		{
			quadrupoleNumber = 0;
			quadrupoleStrength = 0;
			return;
		}
	}
	
	
	protected void refreshDipoleMagnets() 
	{
		if (dipoleMap.isEmpty()) 
		{
			dipoleNumber = 0;
			dipoleStrength =0;
			return;
		}
	}
	
	protected void refreshRFCavities() 
	{
		if (dipoleMap.isEmpty()) 
		{
			RFCavityNumber = 0;
			RFCavityVoltage =0;
			return;
		}
	}
	
	
	
	
	
	@Override
	protected void onMachinePaused() 
	{
		
	}
	
	@Override
	protected void onMachineDisassembled() 
	{
		resetStats();
		if (controller != null) controller.updateBlockState(false);
		isAcceleratorOn = false;
	}
	
	
	protected void resetStats() 
	{
		
	}
	
	@Override
	protected boolean isMachineWhole(IMultiblockValidator validatorCallback) {
		
		// no controllers
		if (controllers.size() == 0) {
			validatorCallback.setLastError(Global.MOD_ID + ".multiblock_validation.no_controller", null);
			return false;
		}
		// more than one controller
		if (controllers.size() > 1) {
			validatorCallback.setLastError(Global.MOD_ID + ".multiblock_validation.too_many_controllers", null);
			return false;
		}
		
		
		
		
		// are there ports
		
		if (ports.size() < 2) 
		{
			validatorCallback.setLastError(Global.MOD_ID + ".multiblock_validation.turbine.valve_wrong_wall", null);
			return false;
		}
		
		
		//beam

		
		
		
		
		
		
		return super.isMachineWhole(validatorCallback);
	}
	
	
	
	
	@Override
	protected void onAssimilate(MultiblockBase assimilated) 
	{
		if (!(assimilated instanceof Accelerator)) return;
		Accelerator assimilatedAccelerator = (Accelerator) assimilated;
		heatBuffer.mergeHeatBuffers(assimilatedAccelerator.heatBuffer);
	}
	
	@Override
	protected void onAssimilated(MultiblockBase assimilator) 
	{
		
	}
	
	
	// Server
	@Override
	protected boolean updateServer()
	{
		updateAccelerator();
		if (heatBuffer.isFull() && NCConfig.fission_overheat)
		{
			heatBuffer.setHeatStored(0);
			doMeltdown();
			return true;
		}

		if (!isAcceleratorOn)
		{
			heatBuffer.changeHeatStored(-getHeatDissipation());
		}

		sendUpdateToListeningPlayers();

		return true;
	}
	
	public long getRawNetHeating() 
	{
		return heating - cooling;
	}
	
	public long getHeatDissipation() 
	{
		return Math.max(1L, heatBuffer.getHeatStored()*getExteriorSurfaceArea()/(NCMath.cube(6)*672000L));
	}
	
	
	public int getTemperature() 
	{
		//TODO
		return Math.round(ambientTemp + (MAX_TEMP - ambientTemp)*(float)heatBuffer.getHeatStored()/heatBuffer.getHeatCapacity());
	}
	
	
	protected void doMeltdown() 
	{
		//TODO
	}
	
	
	public void updateActivity()
	{
		boolean wasAcceleratorOn = isAcceleratorOn;
		isAcceleratorOn = heating > 0L && isAssembled();
		if (isAcceleratorOn != wasAcceleratorOn)
		{
			if (controller != null)
				controller.updateBlockState(isAcceleratorOn);
			sendUpdateToAllPlayers();
		}
	}
	
	public void updateFluidHeating() 
	{
	//TODO
	}
	
	
	public void setIsAcceleratorOn() {
		boolean oldIsAcceleratorOn = isAcceleratorOn;
		isAcceleratorOn = (isRedstonePowered()|| computerActivated) && isAssembled();
		if (isAcceleratorOn != oldIsAcceleratorOn) {
			if (controller != null) controller.updateBlockState(isAcceleratorOn);
			sendUpdateToAllPlayers();
		}
	}
	
	protected boolean isRedstonePowered() {
		if (controller != null && controller.checkIsRedstonePowered(WORLD, controller.getPos())) return true;
		return false;
	}
	
	protected void updateAccelerator() 
	{
		
	}



	protected void incrementUpdateCount()
	{
		updateCount++;
		updateCount %= updateTime();
	}

	protected static int updateTime()
	{
		return NCConfig.machine_update_rate / 4;
	}

	protected boolean shouldUpdate()
	{
		return updateCount == 0;
	}

	// Client

	@Override
	protected void updateClient()
	{
		updateParticles();
		updateSounds();
	}

	@SideOnly(Side.CLIENT)
	private void updateParticles()
	{

	}

	@SideOnly(Side.CLIENT)
	protected double particleSpeedOffest()
	{
	return 0;
	}
	
	@SideOnly(Side.CLIENT)
	protected double[] particleSpawnPos(BlockPos pos)
	{
		return null;

	}

	@SideOnly(Side.CLIENT)
	private void updateSounds()
	{

	}

	@SideOnly(Side.CLIENT)
	private void stopSounds()
	{

	}

	// NBT
	 

	@Override
	protected void syncDataTo(NBTTagCompound data, SyncReason syncReason)
	{
		heatBuffer.writeToNBT(data);
		energyStorage.writeToNBT(data);
		writeTanks(data);
		
		data.setBoolean("isAcceleratorOn", isAcceleratorOn);
		data.setBoolean("isProcessing", isProcessing);
		
		data.setLong("cooling", cooling);
		data.setLong("heating", heating);
		data.setLong("netHeating", netHeating);
		data.setLong("requiredEnergy", requiredEnergy);
		data.setLong("requiredCoolant", requiredCoolant);
		
		data.setDouble("totalEfficiency",totalEfficiency);
		
		data.setInteger("quadrapoleNumber", quadrupoleNumber);
		data.setDouble("luminosity", luminosity);
		
		data.setInteger("dipoleNumber", dipoleNumber);
		
		data.setInteger("RFCavityNumber", RFCavityNumber);
		data.setDouble("maxParticleEnergy", maxParticleEnergy);
		
		
		
	}

	@Override
	protected void syncDataFrom(NBTTagCompound data, SyncReason syncReason)
	{
		heatBuffer.readFromNBT(data);
		energyStorage.readFromNBT(data);
		readTanks(data);
		
		isAcceleratorOn = data.getBoolean("isAcceleratorOn");
		isProcessing = data.getBoolean("isProcessing");
		
		cooling = data.getLong("cooling");
		heating = data.getLong("heating");
		netHeating = data.getLong("netHeating");
		requiredEnergy = data.getInteger("requiredEnergy");
		requiredCoolant = data.getInteger("requiredCoolant");
		
		totalEfficiency = data.getDouble("totalEfficiency");
		
		quadrupoleNumber = data.getInteger("quadrapoleNumber");
		luminosity = data.getDouble("luminosity");
		
		dipoleNumber = data.getInteger("dipoleNumber");
		
		RFCavityNumber = data.getInteger("RFCavityNumber");
		maxParticleEnergy = data.getInteger("maxParticleEnergy");
		
	}
	
	
	
	protected NBTTagCompound writeTanks(NBTTagCompound nbt)
	{
		if (!tanks.isEmpty())
			for (int i = 0; i < tanks.size(); i++)
			{
				nbt.setInteger("capacity" + i, tanks.get(i).getCapacity());
				nbt.setInteger("fluidAmount" + i, tanks.get(i).getFluidAmount());
				nbt.setString("fluidName" + i, tanks.get(i).getFluidName());
			}
		return nbt;
	}
	
	protected void readTanks(NBTTagCompound nbt)
	{
		if (!tanks.isEmpty())
			for (int i = 0; i < tanks.size(); i++)
			{
				tanks.get(i).setCapacity(nbt.getInteger("capacity" + i));
				if (nbt.getString("fluidName" + i).equals("nullFluid") || nbt.getInteger("fluidAmount" + i) == 0)
					tanks.get(i).setFluidStored(null);
				else
					tanks.get(i).setFluidStored(FluidRegistry.getFluid(nbt.getString("fluidName" + i)), nbt.getInteger("fluidAmount" + i));
			}
	}
	
	
	
	// Packets
	
	@Override
	protected AcceleratorUpdatePacket getUpdatePacket()
	{
		return new AcceleratorUpdatePacket(controller.getTilePos(), isAcceleratorOn, cooling, heating, requiredEnergy, requiredCoolant, totalEfficiency, quadrupoleNumber, luminosity, dipoleNumber, RFCavityNumber,
				maxParticleEnergy, heatBuffer.getHeatCapacity(), heatBuffer.getHeatStored(), energyStorage.getMaxEnergyStored(), energyStorage.getEnergyStored());
	}

	@Override
	public void onPacket(AcceleratorUpdatePacket message) {
		isAcceleratorOn = message.isAcceleratorOn;
		cooling = message.cooling;
		heating = message.heating;
		requiredEnergy = message.requiredEnergy;
		requiredCoolant = message.requiredCoolant;
		totalEfficiency = message.totalEfficiency;
		quadrupoleNumber = message.quadrapoleNumber;
		luminosity = message.luminosity;
		dipoleNumber = message.dipoleNumber;
		RFCavityNumber = message.RFCavityNumber;
		maxParticleEnergy = message.maxParticleEnergy;
		heatBuffer.setHeatCapacity(message.heatCapacity);
		heatBuffer.setHeatStored(message.heat);
		energyStorage.setEnergyStored(message.energy);
		energyStorage.setStorageCapacity(message.maxEnergy);
	}
	
	protected AcceleratorRenderPacket getRenderPacket() {
		return null;
	}
	
	public void onRenderPacket(AcceleratorRenderPacket message) {

	}
	
	public Container getContainer(EntityPlayer player) {
		return new ContainerAcceleratorController(player, controller);
	}
	

	@Override
	public void clearAll()
	{
		for (Tank tank : tanks)
		{
			tank.setFluidStored(null);
		}

	}
	
	
	// Multiblock Validators
	
	@Override
	protected boolean isBlockGoodForInterior(World world, int x, int y, int z, IMultiblockValidator validatorCallback)
	{
		BlockPos pos = new BlockPos(x, y, z);
		if (MaterialHelper.isReplaceable(world.getBlockState(pos).getMaterial()) || world.getTileEntity(pos) instanceof TileAcceleratorPartBase)
			return true;
		else
			return standardLastError(x, y, z, validatorCallback);
	}
}

