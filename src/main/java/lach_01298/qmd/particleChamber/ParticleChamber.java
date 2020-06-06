package lach_01298.qmd.particleChamber;

import java.lang.reflect.Constructor;
import java.util.List;

import javax.annotation.Nonnull;

import com.google.common.collect.Lists;

import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import lach_01298.qmd.multiblock.network.ParticleChamberUpdatePacket;
import lach_01298.qmd.network.QMDPacketHandler;
import lach_01298.qmd.particle.ParticleStorageAccelerator;
import lach_01298.qmd.particleChamber.tile.IParticleChamberController;
import lach_01298.qmd.particleChamber.tile.IParticleChamberPart;
import nc.Global;
import nc.multiblock.ILogicMultiblock;
import nc.multiblock.Multiblock;
import nc.multiblock.container.ContainerMultiblockController;
import nc.multiblock.cuboidal.CuboidalMultiblock;
import nc.multiblock.tile.ITileMultiblockPart;
import nc.multiblock.tile.TileBeefAbstract.SyncReason;
import nc.tile.internal.energy.EnergyStorage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ParticleChamber extends CuboidalMultiblock<IParticleChamberPart, ParticleChamberUpdatePacket> implements ILogicMultiblock<ParticleChamberLogic, IParticleChamberPart>
{ 

	public static final ObjectSet<Class<? extends IParticleChamberPart>> PART_CLASSES = new ObjectOpenHashSet<>();
	public static final Object2ObjectMap<String, Constructor<? extends ParticleChamberLogic>> LOGIC_MAP = new Object2ObjectOpenHashMap<>();

	
	protected @Nonnull ParticleChamberLogic logic = new ParticleChamberLogic(this);
	protected @Nonnull NBTTagCompound cachedData = new NBTTagCompound();
	
	protected final PartSuperMap<IParticleChamberPart> partSuperMap = new PartSuperMap<>();

	
	public boolean refreshFlag = true, isChamberOn = false;
	public int requiredEnergy;
	public double efficiency = 1;
	
	
	public IParticleChamberController controller;
	
	
	public static final int	BASE_MAX_ENERGY = 40000;
	
	public final EnergyStorage energyStorage = new EnergyStorage(BASE_MAX_ENERGY);
	
	public List<ParticleStorageAccelerator> beams = Lists.newArrayList(new ParticleStorageAccelerator());
	
	public ParticleChamber(World world)
	{
		super(world);
		for (Class<? extends IParticleChamberPart> clazz : PART_CLASSES)
		{
			partSuperMap.equip(clazz);
		}
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
	public PartSuperMap<IParticleChamberPart> getPartSuperMap()
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
		logic.onResetStats();
	}
	
	
	@Override
	protected boolean updateServer()
	{
		if (refreshFlag) 
		{
			logic.refreshChamber();
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
		boolean wasChamberOn = isChamberOn;
		isChamberOn = isAssembled() && logic.isChamberOn();
		if (isChamberOn != wasChamberOn)
		{
			if (controller != null)
			{
				controller.updateBlockState(isChamberOn);
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
		energyStorage.writeToNBT(data,"energyStorage");
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
		readBeams(beams,data);
		
		isChamberOn = data.getBoolean("isChamberOn");
		requiredEnergy = data.getInteger("requiredEnergy");
		efficiency = data.getDouble("efficiency");

		readLogicNBT(data, syncReason);	
	}

	

	@Override
	protected ParticleChamberUpdatePacket getUpdatePacket()
	{
		return logic.getUpdatePacket();
	}

	@Override
	public void onPacket(ParticleChamberUpdatePacket message)
	{
		energyStorage.setStorageCapacity(message.energyStorage.getMaxEnergyStored());
		energyStorage.setEnergyStored(message.energyStorage.getEnergyStored());
		
		isChamberOn = message.isChamberOn;
		efficiency = message.efficiency;
		requiredEnergy = message.requiredEnergy;
		
		logic.onPacket(message);
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
		ParticleChamberUpdatePacket packet = getUpdatePacket();
		if (packet == null)
		{
			return;
		}
		QMDPacketHandler.instance.sendToAll(getUpdatePacket());
	}
	
	public void sendUpdateToListeningPlayers()
	{
		ParticleChamberUpdatePacket packet = getUpdatePacket();
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
		ParticleChamberUpdatePacket packet = getUpdatePacket();
		if (packet == null)
		{
			return;
		}
		QMDPacketHandler.instance.sendTo(getUpdatePacket(), (EntityPlayerMP) player);
	}

	public ContainerMultiblockController<ParticleChamber, IParticleChamberController> getContainer(EntityPlayer player)
	{
		return logic.getContainer(player);
	}

	public boolean switchOutputs(BlockPos pos)
	{
		return logic.switchOutputs(pos);	
	}

}
