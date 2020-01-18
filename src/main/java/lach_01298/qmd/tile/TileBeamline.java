package lach_01298.qmd.tile;

import lach_01298.qmd.EnumTypes;
import lach_01298.qmd.capabilities.CapabilityParticleStackHandler;
import lach_01298.qmd.multiblock.accelerator.Accelerator;
import lach_01298.qmd.multiblock.accelerator.tile.IAcceleratorController;
import lach_01298.qmd.particle.AcceleratorStorage;
import lach_01298.qmd.particle.ITileParticleStorage;
import lach_01298.qmd.particle.ParticleStorage;
import lach_01298.qmd.pipe.BeamlineLogic;
import lach_01298.qmd.pipe.IPipeController;
import lach_01298.qmd.pipe.Pipe;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraftforge.common.capabilities.Capability;

import static lach_01298.qmd.block.BlockProperties.AXIS_HORIZONTAL;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.collect.Lists;

public class TileBeamline extends TilePipePart implements IPipeController, ITileParticleStorage
{
	private final @Nonnull List<AcceleratorStorage> backupTanks = Lists.newArrayList(new AcceleratorStorage());
	
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
	public void update()
	{
		// TODO Auto-generated method stub
		
	}


	@Override
	public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing side)
	{
		if (capability == CapabilityParticleStackHandler.PARTICLE_HANDLER_CAPABILITY)
		{
			return true;
		}
		return super.hasCapability(capability, side);
	}

	@Override
	public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing side)
	{
		if (capability == CapabilityParticleStackHandler.PARTICLE_HANDLER_CAPABILITY)
		{
			if (!getTanks().isEmpty())
			{
				return (T) getTanks().get(0);
			}
			return null;
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
