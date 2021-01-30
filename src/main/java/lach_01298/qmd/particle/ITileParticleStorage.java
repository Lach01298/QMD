package lach_01298.qmd.particle;

import java.util.List;

import javax.annotation.Nonnull;

public interface ITileParticleStorage
{
	public @Nonnull List<? extends ParticleStorage> getParticleBeams();
}
