package lach_01298.qmd.particle;

import javax.annotation.Nonnull;
import java.util.List;

public interface ITileParticleStorage
{
	public @Nonnull List<? extends ParticleStorage> getParticleBeams();
}
