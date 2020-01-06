package lach_01298.qmd.multiblock.particleChamber;

import javax.annotation.Nonnull;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import lach_01298.qmd.multiblock.accelerator.AcceleratorLogic;
import lach_01298.qmd.multiblock.accelerator.tile.IAcceleratorPart;
import lach_01298.qmd.multiblock.network.ParticleChamberUpdatePacket;
import lach_01298.qmd.multiblock.particleChamber.tile.IParticleChamberPart;
import lach_01298.qmd.recipe.QMDRecipe;
import lach_01298.qmd.recipe.QMDRecipeInfo;
import nc.multiblock.ILogicMultiblock;
import nc.multiblock.ITileMultiblockPart;
import nc.multiblock.Multiblock;
import nc.multiblock.ILogicMultiblock.PartSuperMap;
import nc.multiblock.TileBeefBase.SyncReason;
import nc.multiblock.cuboidal.CuboidalMultiblock;
import nc.recipe.ProcessorRecipe;
import nc.recipe.RecipeInfo;
import nc.util.SuperMap;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ParticleChamber extends CuboidalMultiblock<ParticleChamberUpdatePacket> implements ILogicMultiblock<ParticleChamberLogic, IParticleChamberPart>
{

	public static final ObjectSet<Class<? extends IParticleChamberPart>> PART_CLASSES = new ObjectOpenHashSet<>();
	protected @Nonnull ParticleChamberLogic logic = new ParticleChamberLogic(this);
	protected @Nonnull NBTTagCompound cachedData = new NBTTagCompound();
	
	protected final PartSuperMap<IParticleChamberPart> partSuperMap = new PartSuperMap<>();
	
	public QMDRecipeInfo<QMDRecipe> RecipeInfo;
	public double effecicy;
	
	
	
	protected ParticleChamber(World world)
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
	public PartSuperMap<IParticleChamberPart> getPartSuperMap()
	{
		return partSuperMap;
	}

	@Override
	protected int getMinimumInteriorLength()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected int getMaximumInteriorLength()
	{
		// TODO Auto-generated method stub
		return 0;
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
		if (refreshFlag) 
		{
			logic.refreshParticleChamber();
		}
		updateActivity();
		
		if (logic.onUpdateServer()) 
		{
			return true;
		}
		
		sendUpdateToListeningPlayers();
		
		return true;
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
	protected void syncDataFrom(NBTTagCompound data, SyncReason syncReason)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void syncDataTo(NBTTagCompound data, SyncReason syncReason)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	protected ParticleChamberUpdatePacket getUpdatePacket()
	{
		return logic.getUpdatePacket();
	}

	@Override
	public void onPacket(ParticleChamberUpdatePacket message)
	{
		logic.onPacket(message);
	}

}
