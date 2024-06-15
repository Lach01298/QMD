package lach_01298.qmd.pipe;

import com.google.common.collect.Lists;
import lach_01298.qmd.capabilities.CapabilityParticleStackHandler;
import lach_01298.qmd.particle.*;
import nc.handler.TileInfoHandler;
import nc.tile.TileContainerInfo;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.*;
import java.util.List;

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
}
