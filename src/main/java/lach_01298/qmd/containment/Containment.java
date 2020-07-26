package lach_01298.qmd.containment;

import java.lang.reflect.Constructor;
import java.util.List;

import javax.annotation.Nonnull;

import com.google.common.collect.Lists;

import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import lach_01298.qmd.accelerator.Accelerator;
import lach_01298.qmd.containment.tile.IContainmentController;
import lach_01298.qmd.containment.tile.IContainmentPart;
import lach_01298.qmd.multiblock.network.ContainmentFormPacket;
import lach_01298.qmd.multiblock.network.ContainmentRenderPacket;
import lach_01298.qmd.multiblock.network.ContainmentUpdatePacket;
import lach_01298.qmd.network.QMDPacketHandler;
import lach_01298.qmd.particle.ParticleStorageAccelerator;
import lach_01298.qmd.recipes.QMDRecipes;
import nc.Global;
import nc.multiblock.ILogicMultiblock;
import nc.multiblock.Multiblock;
import nc.multiblock.container.ContainerMultiblockController;
import nc.multiblock.cuboidal.CuboidalMultiblock;
import nc.multiblock.tile.ITileMultiblockPart;
import nc.multiblock.tile.TileBeefAbstract.SyncReason;
import nc.recipe.ProcessorRecipe;
import nc.recipe.RecipeInfo;
import nc.tile.internal.energy.EnergyStorage;
import nc.tile.internal.fluid.Tank;
import nc.tile.internal.heat.HeatBuffer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class Containment extends CuboidalMultiblock<IContainmentPart, ContainmentUpdatePacket> implements ILogicMultiblock<ContainmentLogic, IContainmentPart>
{ 

	public static final ObjectSet<Class<? extends IContainmentPart>> PART_CLASSES = new ObjectOpenHashSet<>();
	public static final Object2ObjectMap<String, Constructor<? extends ContainmentLogic>> LOGIC_MAP = new Object2ObjectOpenHashMap<>();

	
	protected @Nonnull ContainmentLogic logic = new ContainmentLogic(this);
	protected @Nonnull NBTTagCompound cachedData = new NBTTagCompound();
	
	protected final PartSuperMap<IContainmentPart> partSuperMap = new PartSuperMap<>();

	public float materialXOffset = 0F, materialYOffset= 0F, materialZOffset= 0F;
	public float materialAngle = 0F;
	public long prevRenderTime = 0L;
	
	public boolean refreshFlag = true, isContainmentOn = false, cold = false;
	
	public static final int MAX_TEMP = 400;
	public int ambientTemp = 290, maxOperatingTemp = 0;
	public long heating = 0L;
	public double maxCoolantIn =0, maxCoolantOut=0;
	public RecipeInfo<ProcessorRecipe> coolingRecipeInfo;
	
	public int requiredEnergy;

	public IContainmentController controller;
	
	
	public static final int BASE_MAX_HEAT = 25000;
	public static final int	BASE_MAX_ENERGY = 40000;
	public static final double THERMAL_CONDUCTIVITY = 0.005;
	
	public final HeatBuffer heatBuffer = new HeatBuffer(BASE_MAX_HEAT);
	public final EnergyStorage energyStorage = new EnergyStorage(BASE_MAX_ENERGY);
	public List<ParticleStorageAccelerator> beams = Lists.newArrayList(new ParticleStorageAccelerator());
	
	public List<Tank> tanks = Lists.newArrayList(new Tank(Accelerator.BASE_MAX_INPUT, QMDRecipes.accelerator_cooling_valid_fluids.get(0)), new Tank(Accelerator.BASE_MAX_OUTPUT, null));
	
	public Containment(World world)
	{
		super(world);
		for (Class<? extends IContainmentPart> clazz : PART_CLASSES)
		{
			partSuperMap.equip(clazz);
		}
	}

	@Override
	public ContainmentLogic getLogic()
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
	public PartSuperMap<IContainmentPart> getPartSuperMap()
	{
		return partSuperMap;
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
	protected void onAssimilate(Multiblock assimilated)
	{
		logic.onAssimilate(assimilated);
	}

	@Override
	protected void onAssimilated(Multiblock assimilator)
	{
		logic.onAssimilated(assimilator);
	}

	
	@Override
	protected boolean isMachineWhole(Multiblock multiblock)
	{
		return setLogic(multiblock)  && super.isMachineWhole(multiblock) && logic.isMachineWhole(multiblock);
	}
	
	public boolean setLogic(Multiblock multiblock)
	{
		if (getPartMap(IContainmentController.class).isEmpty()) {
			multiblock.setLastError(Global.MOD_ID + ".multiblock_validation.no_controller", null);
			return false;
		}
		if (getPartMap(IContainmentController.class).size() > 1) {
			multiblock.setLastError(Global.MOD_ID + ".multiblock_validation.too_many_controllers", null);
			return false;
		}
		
		for (IContainmentController contr : getPartMap(IContainmentController.class).values()) 
		{
			controller = contr;
		}
		
		setLogic(controller.getLogicID());
		
		return true;
	}
	
	
	public long getExternalHeating()
	{
		return (long) ((ambientTemp - getTemperature()) * THERMAL_CONDUCTIVITY * getExteriorSurfaceArea());
	}
	
	public long getMaxExternalHeating()
	{
		return (long) (ambientTemp  * THERMAL_CONDUCTIVITY * getExteriorSurfaceArea());
	}
	
	public int getTemperature() 
	{
		return Math.round(MAX_TEMP*(float)heatBuffer.getHeatStored()/heatBuffer.getHeatCapacity());
	}
	
	
	
	
	
	
	
	@Override
	protected boolean updateServer()
	{
		if (refreshFlag) 
		{
			logic.refreshMultiblock();
		}
		updateActivity();
		
		if (logic.onUpdateServer()) 
		{
			return true;
		}
		
		sendUpdateToListeningPlayers();
		
		return true;
	}

	public void updateActivity()
	{
		
		boolean wasContainmentOn = isContainmentOn;
		isContainmentOn = isAssembled() && logic.isMultiblockOn();
		if (isContainmentOn != wasContainmentOn)
		{
			if (controller != null)
			{
				controller.updateBlockState(isContainmentOn);
			}
			sendUpdateToAllPlayers();
		}
		
	}

	@Override
	protected void updateClient()
	{
		logic.onUpdateClient();	
	}

	@Override
	protected boolean isBlockGoodForInterior(World world, int x, int y, int z, Multiblock multiblock)
	{
		return logic.isBlockGoodForInterior(world, x, y, z, multiblock);
	}

	@Override
	public void syncDataTo(NBTTagCompound data, SyncReason syncReason)
	{
		heatBuffer.writeToNBT(data, "heatBuffer");
		energyStorage.writeToNBT(data,"energyStorage");
		writeTanks(tanks,data,"tanks");
		writeBeams(beams,data);
		
		data.setBoolean("isAcceleratorOn", isContainmentOn);
		data.setLong("heating", heating);
		data.setDouble("coolantIn", maxCoolantIn);
		data.setDouble("coolantOut", maxCoolantOut);
		data.setDouble("maxOperatingTemp", maxOperatingTemp);
		data.setLong("requiredEnergy", requiredEnergy);
		data.setBoolean("cold", cold);
		
		writeLogicNBT(data, syncReason);
		
	}
	
	@Override
	public void syncDataFrom(NBTTagCompound data, SyncReason syncReason)
	{
		heatBuffer.readFromNBT(data,"heatBuffer");
		energyStorage.readFromNBT(data,"energyStorage");
		readTanks(tanks,data, "tanks");
		readBeams(beams,data);
		
		isContainmentOn = data.getBoolean("isContainmentOn");
		heating = data.getLong("rawHeating");
		maxCoolantIn = data.getDouble("coolantIn");
		maxCoolantOut = data.getDouble("coolantOut");
		maxOperatingTemp =data.getInteger("maxOperatingTemp");
		requiredEnergy = data.getInteger("requiredEnergy");
		cold = data.getBoolean("cold");
		
		readLogicNBT(data, syncReason);
	}

	

	@Override
	protected ContainmentUpdatePacket getUpdatePacket()
	{
		return logic.getUpdatePacket();
	}

	@Override
	public void onPacket(ContainmentUpdatePacket message)
	{
		heatBuffer.setHeatCapacity(message.heatBuffer.getHeatCapacity());
		heatBuffer.setHeatStored(message.heatBuffer.getHeatStored());
		energyStorage.setStorageCapacity(message.energyStorage.getMaxEnergyStored());
		energyStorage.setEnergyStored(message.energyStorage.getEnergyStored());
		
		for (int i = 0; i < tanks.size(); i++) tanks.get(i).readInfo(message.tanksInfo.get(i));
		beams = message.beams;
		
		isContainmentOn = message.isContainmentOn;
		heating = message.heating;
		maxCoolantIn = message.maxCoolantIn;
		maxCoolantOut = message.maxCoolantOut;
		maxOperatingTemp = message.maxOperatingTemp;
		requiredEnergy = message.requiredEnergy;
		
		logic.onPacket(message);
	}

	protected ContainmentRenderPacket getRenderPacket()
	{
		return logic.getRenderPacket();
	}

	public void onRenderPacket(ContainmentRenderPacket message)
	{
		logic.onRenderPacket(message);
	}

	public ContainmentFormPacket getFormPacket()
	{
		return logic.getFormPacket();
	}

	public void onFormPacket(ContainmentFormPacket message)
	{
		logic.onFormPacket(message);
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
	
	
	//packet handler
	
	public void sendUpdateToAllPlayers()
	{
		ContainmentUpdatePacket packet = getUpdatePacket();
		if (packet == null)
		{
			return;
		}
		QMDPacketHandler.instance.sendToAll(getUpdatePacket());
	}
	
	public void sendUpdateToListeningPlayers()
	{
		ContainmentUpdatePacket packet = getUpdatePacket();
		if (packet == null)
		{
			return;
		}
		for (EntityPlayer player : playersToUpdate)
		{
			QMDPacketHandler.instance.sendTo(getUpdatePacket(), (EntityPlayerMP) player);
		}
	}
	
	public void sendIndividualUpdate(EntityPlayer player)
	{
		if (WORLD.isRemote)
		{
			return;
		}
		ContainmentUpdatePacket packet = getUpdatePacket();
		if (packet == null)
		{
			return;
		}
		QMDPacketHandler.instance.sendTo(getUpdatePacket(), (EntityPlayerMP) player);
	}

	public ContainerMultiblockController<Containment, IContainmentController> getContainer(EntityPlayer player)
	{
		return logic.getContainer(player);
	}

	
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
	
	
	

}
