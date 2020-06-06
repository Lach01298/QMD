package lach_01298.qmd.particle;

import java.util.List;

import javax.annotation.Nonnull;

import nc.tile.ITile;

public interface ITileParticleStorage extends ITile
{
	public @Nonnull List<? extends ParticleStorage> getTanks();
}
