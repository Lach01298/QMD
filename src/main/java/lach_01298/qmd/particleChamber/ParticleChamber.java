package lach_01298.qmd.particleChamber;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.objects.*;
import lach_01298.qmd.config.QMDConfig;
import lach_01298.qmd.multiblock.*;
import lach_01298.qmd.multiblock.network.ParticleChamberUpdatePacket;
import lach_01298.qmd.particle.ParticleStorageAccelerator;
import lach_01298.qmd.particleChamber.tile.*;
import nc.Global;
import nc.multiblock.*;
import nc.multiblock.cuboidal.CuboidalMultiblock;
import nc.tile.internal.energy.EnergyStorage;
import nc.tile.internal.fluid.Tank;
import nc.tile.multiblock.TilePartAbstract.SyncReason;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.function.UnaryOperator;

public class ParticleChamber extends CuboidalMultiblock<ParticleChamber, IParticleChamberPart>
		implements ILogicMultiblock<ParticleChamber, ParticleChamberLogic, IParticleChamberPart>, IQMDPacketMultiblock<ParticleChamber, IParticleChamberPart, ParticleChamberUpdatePacket>, IMultiBlockTank
{

	public static final ObjectSet<Class<? extends IParticleChamberPart>> PART_CLASSES = new ObjectOpenHashSet<>();
	public static final Object2ObjectMap<String, UnaryOperator<ParticleChamberLogic>> LOGIC_MAP = new Object2ObjectOpenHashMap<>();

	
	protected @Nonnull ParticleChamberLogic logic = new ParticleChamberLogic(this);
	protected @Nonnull NBTTagCompound cachedData = new NBTTagCompound();
	
	protected final PartSuperMap<ParticleChamber, IParticleChamberPart> partSuperMap = new PartSuperMap<>();

	
	public boolean refreshFlag = true, isChamberOn = false;
	public int requiredEnergy;
	public double efficiency = 1;
	
	
	public IParticleChamberController controller;
	
	public final EnergyStorage energyStorage = new EnergyStorage(QMDConfig.particle_chamber_base_energy_capacity);
	
	public List<ParticleStorageAccelerator> beams = Lists.newArrayList(new ParticleStorageAccelerator(),new ParticleStorageAccelerator(),new ParticleStorageAccelerator(),new ParticleStorageAccelerator(),new ParticleStorageAccelerator(),new ParticleStorageAccelerator());
	public List<Tank> tanks = Lists.newArrayList(new Tank(QMDConfig.particle_chamber_input_tank_capacity,null), new Tank(QMDConfig.particle_chamber_output_tank_capacity,null));
	
	protected final Set<EntityPlayer> updatePacketListeners;
	
	public ParticleChamber(World world)
	{
		super(world, ParticleChamber.class, IParticleChamberPart.class);
		for (Class<? extends IParticleChamberPart> clazz : PART_CLASSES)
		{
			partSuperMap.equip(clazz);
		}
		updatePacketListeners = new ObjectOpenHashSet<>();
	}

	@Override
	public ParticleChamberLogic getLogic()
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
	public PartSuperMap<ParticleChamber, IParticleChamberPart> getPartSuperMap()
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
	public void onAttachedPartWithMultiblockData(IParticleChamberPart part, NBTTagCompound data)
	{
		logic.onAttachedPartWithMultiblockData(part, data);
		syncDataFrom(data, SyncReason.FullSync);
		
	}

	@Override
	protected void onBlockAdded(IParticleChamberPart newPart)
	{
		onPartAdded(newPart);
		logic.onBlockAdded(newPart);
		
	}

	@Override
	protected void onBlockRemoved(IParticleChamberPart oldPart)
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
	protected void onAssimilate(ParticleChamber assimilated)
	{
		logic.onAssimilate(assimilated);
	}

	@Override
	protected void onAssimilated(ParticleChamber assimilator)
	{
		logic.onAssimilated(assimilator);
	}

	
	@Override
	protected boolean isMachineWhole()
	{
		return setLogic(this)  && super.isMachineWhole() && logic.isMachineWhole();
	}
	
	public boolean setLogic(Multiblock multiblock)
	{
		if (getPartMap(IParticleChamberController.class).isEmpty()) {
			multiblock.setLastError(Global.MOD_ID + ".multiblock_validation.no_controller", null);
			return false;
		}
		if (getPartMap(IParticleChamberController.class).size() > 1) {
			multiblock.setLastError(Global.MOD_ID + ".multiblock_validation.too_many_controllers", null);
			return false;
		}
		
		for (IParticleChamberController contr : getPartMap(IParticleChamberController.class).values())
		{
			controller = contr;
		}
		
		setLogic(controller.getLogicID());
		
		return true;
	}
	
	
	
	public void resetStats()
	{
		logic.refreshChamberStats();
	}
	
	
	@Override
	protected boolean updateServer()
	{
		boolean flag = refreshFlag;
		if (refreshFlag)
		{
			logic.refreshChamber();
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
		boolean wasChamberOn = isChamberOn;
		isChamberOn = isAssembled() && logic.isChamberOn();
		
		if (isChamberOn != wasChamberOn)
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
		energyStorage.writeToNBT(data,"energyStorage");
		writeTanks(tanks,data,"tanks");
		writeBeams(beams,data);
		
		data.setBoolean("isChamberOn", isChamberOn);
		data.setInteger("requiredEnergy", requiredEnergy);
		data.setDouble("efficiency", efficiency);
		
		writeLogicNBT(data, syncReason);
		
	}
	
	@Override
	public void syncDataFrom(NBTTagCompound data, SyncReason syncReason)
	{
		energyStorage.readFromNBT(data,"energyStorage");
		readTanks(tanks,data, "tanks");
		readBeams(beams,data);
		
		isChamberOn = data.getBoolean("isChamberOn");
		requiredEnergy = data.getInteger("requiredEnergy");
		efficiency = data.getDouble("efficiency");

		readLogicNBT(data, syncReason);
	}

	// Packets
	
	@Override
	public Set<EntityPlayer> getMultiblockUpdatePacketListeners() {
		return updatePacketListeners;
	}

	@Override
	public ParticleChamberUpdatePacket getMultiblockUpdatePacket()
	{
		return logic.getMultiblockUpdatePacket();
	}

	@Override
	public void onMultiblockUpdatePacket(ParticleChamberUpdatePacket message)
	{
		energyStorage.setStorageCapacity(message.energyStorage.getMaxEnergyStored());
		energyStorage.setEnergyStored(message.energyStorage.getEnergyStored());
		
		for (int i = 0; i < tanks.size(); i++) tanks.get(i).readInfo(message.tanksInfo.get(i));
		beams = message.beams;
		
		isChamberOn = message.isChamberOn;
		efficiency = message.efficiency;
		requiredEnergy = message.requiredEnergy;
		
		logic.onMultiblockUpdatePacket(message);
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

	/*public ContainerMultiblockController<ParticleChamber, IParticleChamberController> getContainer(EntityPlayer player)
	{
		return logic.getContainer(player);
	}*/

	public boolean toggleSetting(BlockPos pos, int ioNumber)
	{
		return logic.toggleSetting(pos,ioNumber);
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
