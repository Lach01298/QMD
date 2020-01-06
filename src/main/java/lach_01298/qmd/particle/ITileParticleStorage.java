package lach_01298.qmd.particle;

import java.util.List;

import javax.annotation.Nonnull;

import nc.tile.ITile;
import nc.tile.internal.fluid.Tank;

public interface ITileParticleStorage extends ITile
{
	public @Nonnull List<? extends ParticleStorage> getTanks();
}
