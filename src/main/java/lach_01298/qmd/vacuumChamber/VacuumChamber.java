package lach_01298.qmd.vacuumChamber;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Set;

import javax.annotation.Nonnull;

import com.google.common.collect.Lists;

import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import lach_01298.qmd.config.QMDConfig;
import lach_01298.qmd.multiblock.IMultiBlockTank;
import lach_01298.qmd.multiblock.IQMDPacketMultiblock;
import lach_01298.qmd.multiblock.network.ContainmentRenderPacket;
import lach_01298.qmd.multiblock.network.VacuumChamberUpdatePacket;
import lach_01298.qmd.network.QMDPacketHandler;
import lach_01298.qmd.particle.ParticleStorageAccelerator;
import lach_01298.qmd.recipes.QMDRecipes;
import lach_01298.qmd.vacuumChamber.tile.IVacuumChamberController;
import lach_01298.qmd.vacuumChamber.tile.IVacuumChamberPart;
import nc.Global;
import nc.multiblock.ILogicMultiblock;
import nc.multiblock.Multiblock;
import nc.multiblock.container.ContainerMultiblockController;
import nc.multiblock.cuboidal.CuboidalMultiblock;
import nc.multiblock.tile.ITileMultiblockPart;
import nc.multiblock.tile.TileBeefAbstract.SyncReason;
import nc.recipe.BasicRecipe;
import nc.recipe.RecipeInfo;
import nc.tile.internal.energy.EnergyStorage;
import nc.tile.internal.fluid.Tank;
import nc.tile.internal.heat.HeatBuffer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class VacuumChamber extends CuboidalMultiblock<VacuumChamber, IVacuumChamberPart>
		implements ILogicMultiblock<VacuumChamber, VacuumChamberLogic, IVacuumChamberPart>, IQMDPacketMultiblock<VacuumChamber, IVacuumChamberPart, VacuumChamberUpdatePacket>, IMultiBlockTank
{

	public static final ObjectSet<Class<? extends IVacuumChamberPart>> PART_CLASSES = new ObjectOpenHashSet<>();
	public static final Object2ObjectMap<String, Constructor<? extends VacuumChamberLogic>> LOGIC_MAP = new Object2ObjectOpenHashMap<>();

	protected @Nonnull VacuumChamberLogic logic = new VacuumChamberLogic(this);
	protected @Nonnull NBTTagCompound cachedData = new NBTTagCompound();

	protected final PartSuperMap<VacuumChamber, IVacuumChamberPart> partSuperMap = new PartSuperMap<>();

	public float materialXOffset = 0F, materialYOffset = 0F, materialZOffset = 0F;
	public float materialAngle = 0F;
	public long prevRenderTime = 0L;

	public boolean refreshFlag = true, isChamberOn = false, cold = false;

	public static final int MAX_TEMP = 400;
	public int ambientTemp = 290, maxOperatingTemp = 0;
	public long heating = 0L, currentHeating = 0L;
	public int maxCoolantIn = 0, maxCoolantOut = 0; // micro buckets per tick
	public RecipeInfo<BasicRecipe> coolingRecipeInfo;

	public int requiredEnergy;

	public IVacuumChamberController controller;

	public final HeatBuffer heatBuffer = new HeatBuffer(QMDConfig.accelerator_base_heat_capacity);
	public final EnergyStorage energyStorage = new EnergyStorage(QMDConfig.accelerator_base_energy_capacity);
	public List<ParticleStorageAccelerator> beams = Lists.newArrayList(new ParticleStorageAccelerator(),new ParticleStorageAccelerator());

	public List<Tank> tanks = Lists.newArrayList(
			new Tank(QMDConfig.accelerator_base_input_tank_capacity,
					QMDRecipes.accelerator_cooling_valid_fluids.get(0)),
			new Tank(QMDConfig.accelerator_base_output_tank_capacity, null), new Tank(1, null), new Tank(1, null),
			new Tank(1, null), new Tank(1, null), new Tank(1, null), new Tank(1, null));

	protected final Set<EntityPlayer> updatePacketListeners;
	
	public VacuumChamber(World world)
	{
		super(world, VacuumChamber.class, IVacuumChamberPart.class);
		for (Class<? extends IVacuumChamberPart> clazz : PART_CLASSES)
		{
			partSuperMap.equip(clazz);
		}
		updatePacketListeners = new ObjectOpenHashSet<>();
	}

	@Override
	public VacuumChamberLogic getLogic()
	{
		return logic;
	}

	@Override
	public void setLogic(String logicID)
	{
		if (logicID.equals(logic.getID()))
			return;
		logic = getNewLogic(LOGIC_MAP.get(logicID));
	}

	// Multiblock Part Getters

	@Override
	public PartSuperMap<VacuumChamber, IVacuumChamberPart> getPartSuperMap()
	{
		return partSuperMap;
	}

	// Multiblock Size Limits

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

	// Multiblock Methods

	@Override
	public void onAttachedPartWithMultiblockData(IVacuumChamberPart part, NBTTagCompound data)
	{
		logic.onAttachedPartWithMultiblockData(part, data);
		syncDataFrom(data, SyncReason.FullSync);

	}

	@Override
	protected void onBlockAdded(IVacuumChamberPart newPart)
	{
		onPartAdded(newPart);
		logic.onBlockAdded(newPart);

	}

	@Override
	protected void onBlockRemoved(IVacuumChamberPart oldPart)
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
		return setLogic(this) && super.isMachineWhole() && logic.isMachineWhole();
	}

	public boolean setLogic(Multiblock multiblock)
	{
		if (getPartMap(IVacuumChamberController.class).isEmpty())
		{
			multiblock.setLastError(Global.MOD_ID + ".multiblock_validation.no_controller", null);
			return false;
		}
		if (getPartMap(IVacuumChamberController.class).size() > 1)
		{
			multiblock.setLastError(Global.MOD_ID + ".multiblock_validation.too_many_controllers", null);
			return false;
		}

		for (IVacuumChamberController contr : getPartMap(IVacuumChamberController.class).values())
		{
			controller = contr;
		}

		setLogic(controller.getLogicID());

		return true;
	}

	@Override
	protected void onAssimilate(VacuumChamber assimilated)
	{
		logic.onAssimilate(assimilated);
	}

	@Override
	protected void onAssimilated(VacuumChamber assimilator)
	{
		logic.onAssimilated(assimilator);
	}

	public long getExternalHeating()
	{
		return (long) ((ambientTemp - getTemperature()) * QMDConfig.accelerator_thermal_conductivity
				* getExteriorSurfaceArea());
	}

	public long getMaxExternalHeating()
	{
		return (long) (ambientTemp * QMDConfig.accelerator_thermal_conductivity * getExteriorSurfaceArea());
	}

	public int getTemperature()
	{
		return Math.round(MAX_TEMP * (float) heatBuffer.getHeatStored() / heatBuffer.getHeatCapacity());
	}

	// Server

	@Override
	protected boolean updateServer()
	{
		boolean flag = false;
		updateActivity();
		if (logic.onUpdateServer())
		{
			flag = true;
		}

		return flag;
	}

	public void updateActivity()
	{

		boolean wasContainmentOn = isChamberOn;
		isChamberOn = isAssembled() && logic.isMultiblockOn();
		if (isChamberOn != wasContainmentOn)
		{
			if (controller != null)
			{
				controller.setActivity(isChamberOn);
				sendMultiblockUpdatePacketToAll();
			}
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
		energyStorage.writeToNBT(data, "energyStorage");
		writeTanks(tanks, data, "tanks");
		writeBeams(beams, data);

		data.setBoolean("isContainmentOn", isChamberOn);
		data.setLong("heating", heating);
		data.setInteger("coolantIn", maxCoolantIn);
		data.setInteger("coolantOut", maxCoolantOut);
		data.setDouble("maxOperatingTemp", maxOperatingTemp);
		data.setLong("requiredEnergy", requiredEnergy);
		data.setBoolean("cold", cold);

		writeLogicNBT(data, syncReason);

	}

	@Override
	public void syncDataFrom(NBTTagCompound data, SyncReason syncReason)
	{
		heatBuffer.readFromNBT(data, "heatBuffer");
		energyStorage.readFromNBT(data, "energyStorage");
		readTanks(tanks, data, "tanks");
		readBeams(beams, data);
		
		isChamberOn = data.getBoolean("isContainmentOn");
		heating = data.getLong("rawHeating");
		maxCoolantIn = data.getInteger("coolantIn");
		maxCoolantOut = data.getInteger("coolantOut");
		maxOperatingTemp = data.getInteger("maxOperatingTemp");
		requiredEnergy = data.getInteger("requiredEnergy");
		cold = data.getBoolean("cold");

		readLogicNBT(data, syncReason);
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
		beams.get(0).readFromNBT(data);
	}

	// Packets
	
	@Override
	public Set<EntityPlayer> getMultiblockUpdatePacketListeners() {
		return updatePacketListeners;
	}

	@Override
	public VacuumChamberUpdatePacket getMultiblockUpdatePacket()
	{
		return logic.getMultiblockUpdatePacket();
	}

	@Override
	public void onMultiblockUpdatePacket(VacuumChamberUpdatePacket message)
	{
		heatBuffer.setHeatCapacity(message.heatBuffer.getHeatCapacity());
		heatBuffer.setHeatStored(message.heatBuffer.getHeatStored());
		energyStorage.setStorageCapacity(message.energyStorage.getMaxEnergyStored());
		energyStorage.setEnergyStored(message.energyStorage.getEnergyStored());

		for (int i = 0; i < tanks.size(); i++)
			tanks.get(i).readInfo(message.tanksInfo.get(i));
		beams = message.beams;
		
		isChamberOn = message.isContainmentOn;
		heating = message.heating;
		currentHeating = message.currentHeating;
		maxCoolantIn = message.maxCoolantIn;
		maxCoolantOut = message.maxCoolantOut;
		maxOperatingTemp = message.maxOperatingTemp;
		requiredEnergy = message.requiredEnergy;

		logic.onMultiblockUpdatePacket(message);
	}

	protected ContainmentRenderPacket getRenderPacket()
	{
		return logic.getRenderPacket();
	}

	public void onRenderPacket(ContainmentRenderPacket message)
	{
		logic.onRenderPacket(message);
	}

	public void sendIndividualRender(EntityPlayer player)
	{
		if (WORLD.isRemote)
		{
			return;
		}
		ContainmentRenderPacket packet = getRenderPacket();
		if (packet == null)
		{
			return;
		}
		QMDPacketHandler.instance.sendTo(packet, (EntityPlayerMP) player);
	}

	public void sendRenderToAllPlayers()
	{
		if (WORLD.isRemote)
		{
			return;
		}
		ContainmentRenderPacket packet = getRenderPacket();
		if (packet == null)
		{
			return;
		}
		QMDPacketHandler.instance.sendToAll(packet);

	}

	/*public ContainerMultiblockController<VacuumChamber, IVacuumChamberController> getContainer(EntityPlayer player)
	{
		return logic.getContainer(player);
	}*/

	@Override
	public void clearAllMaterial()
	{
		logic.clearAllMaterial();

		super.clearAllMaterial();

		if (!WORLD.isRemote)
		{
			updateActivity();
		}
	}

	// Multiblock Validators

	@Override
	protected boolean isBlockGoodForInterior(World world, BlockPos pos)
	{
		return logic.isBlockGoodForInterior(world, pos);
	}

	@Override
	public List<Tank> getTanks()
	{
		return tanks;
	}

}
