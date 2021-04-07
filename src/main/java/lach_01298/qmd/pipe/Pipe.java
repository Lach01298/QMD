package lach_01298.qmd.pipe;

import java.lang.reflect.Constructor;

import javax.annotation.Nonnull;

import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import nc.Global;
import nc.multiblock.ILogicMultiblock;
import nc.multiblock.Multiblock;
import nc.multiblock.tile.ITileMultiblockPart;
import nc.multiblock.tile.TileBeefAbstract.SyncReason;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Pipe extends PipeMultiblock<IPipePart, PipeUpdatePacket> implements ILogicMultiblock<PipeLogic, IPipePart>
{
	 
	public static final ObjectSet<Class<? extends IPipePart>> PART_CLASSES = new ObjectOpenHashSet<>();
	public static final Object2ObjectMap<String, Constructor<? extends PipeLogic>> LOGIC_MAP = new Object2ObjectOpenHashMap<>();
	
	protected @Nonnull PipeLogic logic = new PipeLogic(this);
	
	protected final PartSuperMap<IPipePart> partSuperMap = new PartSuperMap<>();
	public IPipeController controller;
	
	public Pipe(World world)
	{
		super(world);
		for (Class<? extends IPipePart> clazz : PART_CLASSES)
		{
			partSuperMap.equip(clazz);
		}
	}

	
	@Override
	public @Nonnull PipeLogic getLogic() 
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
	public PartSuperMap<IPipePart> getPartSuperMap()
	{
		return partSuperMap;
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
	protected boolean updateServer()
	{
		if (logic.onUpdateServer()) 
		{
			return true;
		}
		
		sendUpdateToListeningPlayers();
		
		return true;
	}
	
	@Override
	protected boolean isMachineWhole()
	{
		
		return setLogic(this) && super.isMachineWhole() && logic.isMachineWhole();
	}
	

	@Override
	protected void updateClient()
	{	
		logic.onUpdateClient();	
	}

	@Override
	protected boolean isBlockGoodForInterior(World world, BlockPos pos)
	{
		return logic.isBlockGoodForInterior(world,pos);
	}

	@Override
	public void syncDataFrom(NBTTagCompound data, SyncReason syncReason)
	{
		logic.readFromLogicTag(data, syncReason);	
		
	}

	@Override
	public void syncDataTo(NBTTagCompound data, SyncReason syncReason)
	{
		
		logic.writeToLogicTag(data, syncReason);
	}

	@Override
	protected PipeUpdatePacket getUpdatePacket()
	{
		
		return logic.getUpdatePacket();
	}

	@Override
	public void onPacket(PipeUpdatePacket message)
	{
		logic.onPacket(message);
	}

	
	public boolean setLogic(Multiblock multiblock)
	{
		if (getPartMap(IPipeController.class).isEmpty())
		{
			multiblock.setLastError(Global.MOD_ID + ".multiblock_validation.no_controller", null);
			return false;
		}

		for (IPipeController contr : getPartMap(IPipeController.class).values())
		{
			controller = contr;
		}
		
		setLogic(controller.getLogicID());

		return true;
	}
	
	

}
