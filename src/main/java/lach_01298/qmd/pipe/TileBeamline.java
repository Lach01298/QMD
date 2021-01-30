package lach_01298.qmd.pipe;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.collect.Lists;

import lach_01298.qmd.capabilities.CapabilityParticleStackHandler;
import lach_01298.qmd.particle.ITileParticleStorage;
import lach_01298.qmd.particle.ParticleStorage;
import lach_01298.qmd.particle.ParticleStorageBeamline;
import nc.multiblock.container.ContainerMultiblockController;
import nc.multiblock.tile.IMultiblockGuiPart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

public class TileBeamline extends TilePipePart implements IPipeController, ITileParticleStorage
{
	private final @Nonnull List<ParticleStorageBeamline> backupTanks = Lists.newArrayList(new ParticleStorageBeamline(1));
	
	public TileBeamline()
	{
		super();
	}

	
	@Override
	public String getLogicID()
	{
		return	"beamline";
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
				if (side.getAxis() == logic.axis)
				{
					return true;
				}
			}

		}
		return super.hasCapability(capability, side);
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
				
				if (side.getAxis() == logic.axis)
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


	@Override
	public <TILE extends IMultiblockGuiPart<Pipe>> ContainerMultiblockController<Pipe, TILE> getContainer(
			EntityPlayer player)
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
