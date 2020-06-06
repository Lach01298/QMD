package lach_01298.qmd.pipe;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.collect.Lists;

import lach_01298.qmd.capabilities.CapabilityParticleStackHandler;
import lach_01298.qmd.particle.ITileParticleStorage;
import lach_01298.qmd.particle.ParticleStorage;
import lach_01298.qmd.particle.ParticleStorageBeamline;
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
					
					if (!getTanks().isEmpty())
					{
						return (T) getTanks().get(0);
					}
					return null;
				}
				
			}
		}

		return super.getCapability(capability, side);
	}

	@Override
	public List<? extends ParticleStorage> getTanks()
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
