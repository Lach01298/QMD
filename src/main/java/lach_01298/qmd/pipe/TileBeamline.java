package lach_01298.qmd.pipe;

import static nc.block.property.BlockProperties.AXIS_ALL;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.collect.Lists;

import lach_01298.qmd.capabilities.CapabilityParticleStackHandler;
import lach_01298.qmd.particle.ITileParticleStorage;
import lach_01298.qmd.particle.ParticleStorage;
import lach_01298.qmd.particle.ParticleStorageBeamline;
import nc.handler.TileInfoHandler;
import nc.tile.TileContainerInfo;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;

public class TileBeamline extends TilePipePart implements IPipeController<TileBeamline>, ITileParticleStorage
{
	protected final TileContainerInfo<TileBeamline> info = TileInfoHandler.getTileContainerInfo("beamline");
	
	private final @Nonnull List<ParticleStorageBeamline> backupTanks = Lists.newArrayList(new ParticleStorageBeamline(1));
	
	public TileBeamline()
	{
		super();
	}

	
	@Override
	public String getLogicID()
	{
		return "beamline";
	}
	
	@Override
	public TileContainerInfo<TileBeamline> getContainerInfo()
	{
		return info;
	}
 
	@Override
	public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing side)
	{
		if (capability == CapabilityParticleStackHandler.PARTICLE_HANDLER_CAPABILITY)
		{
			BeamlineLogic logic = null;
			if (this.getMultiblock() != null)
			{

				if (this.getMultiblock().getLogic() instanceof BeamlineLogic)
				{
					logic = (BeamlineLogic) this.getMultiblock().getLogic();
				}
			}
			if (logic != null)
			{
				if (side.getAxis() == logic.getAxis())
				{
					return true;
				}
			}

		}
		return super.hasCapability(capability, side);
	}

	@Override
	public boolean onUseMultitool(ItemStack multitoolStack, EntityPlayerMP player, World world, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if(this.getMultiblock() != null)
		{
			if(this.getMultiblock().getNumConnectedBlocks() == 1 && this.getMultiblock().getLogic() instanceof BeamlineLogic)
			{
				Axis axis = getWorld().getBlockState(getPos()).getValue(AXIS_ALL);
				
				switch(axis)
				{
				case X:
					axis = EnumFacing.Axis.Y;
					break;
				case Y:
					axis = EnumFacing.Axis.Z;
					break;
				case Z:
					axis = EnumFacing.Axis.X;
				}
				getWorld().setBlockState(getPos(),getWorld().getBlockState(getPos()).withProperty(AXIS_ALL, axis));
				((BeamlineLogic)this.getMultiblock().getLogic()).setAxis(axis);
				markDirtyAndNotify(true);
				return true;
			}
		}
		
		return false;
	}
	
	
	
	@Override
	public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing side)
	{

		if (capability == CapabilityParticleStackHandler.PARTICLE_HANDLER_CAPABILITY)
		{
			
			BeamlineLogic logic = null;
			if (this.getMultiblock() != null)
			{
				if (this.getMultiblock().getLogic() instanceof BeamlineLogic)
				{
					logic = (BeamlineLogic) this.getMultiblock().getLogic();
				}
			}

			if (logic != null)
			{
				
				if (side.getAxis() == logic.getAxis())
				{
					
					if (!getParticleBeams().isEmpty())
					{
						return (T) getParticleBeams().get(0);
					}
					return null;
				}
				
			}
		}

		return super.getCapability(capability, side);
	}

	@Override
	public List<? extends ParticleStorage> getParticleBeams()
	{
		if (!isMultiblockAssembled())
			return backupTanks;

		if (getMultiblock().getLogic() instanceof BeamlineLogic)
		{
			BeamlineLogic logic = (BeamlineLogic) getMultiblock().getLogic();
			return Lists.newArrayList(logic.storage);
		}

		return backupTanks;
	}
}
