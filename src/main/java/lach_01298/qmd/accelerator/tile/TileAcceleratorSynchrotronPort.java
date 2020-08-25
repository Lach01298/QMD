package lach_01298.qmd.accelerator.tile;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.collect.Lists;

import lach_01298.qmd.capabilities.CapabilityParticleStackHandler;
import lach_01298.qmd.particle.ITileParticleStorage;
import lach_01298.qmd.particle.ParticleStorage;
import lach_01298.qmd.particle.ParticleStorageAccelerator;
import nc.multiblock.cuboidal.CuboidalPartPositionType;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

public class TileAcceleratorSynchrotronPort extends TileAcceleratorPart implements  ITileParticleStorage
{
	
	private final @Nonnull List<ParticleStorageAccelerator> backupTanks = Lists.newArrayList(new ParticleStorageAccelerator());
	
	public TileAcceleratorSynchrotronPort()
	{
		super(CuboidalPartPositionType.WALL);
		
		
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
			return getMultiblock().beams.subList(2, 3);
		}
	
	
	
	
}
