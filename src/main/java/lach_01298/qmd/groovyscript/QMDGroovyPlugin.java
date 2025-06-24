package lach_01298.qmd.groovyscript;

import com.cleanroommc.groovyscript.api.GroovyPlugin;
import com.cleanroommc.groovyscript.compat.mods.*;
import lach_01298.qmd.QMD;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.*;

public class QMDGroovyPlugin implements GroovyPlugin
{

	@Override
	public @Nullable GroovyPropertyContainer createGroovyPropertyContainer()
	{
		return QMDGSContainer.instance = new QMDGSContainer();
	}

	@Override
	public @NotNull String getModId()
	{
		return QMD.MOD_ID;
	}

	@Override
	public @NotNull String getContainerName()
	{
		return QMD.MOD_NAME;
	}

	@Override
	public @NotNull Collection<String> getAliases()
	{
		return Arrays.asList(getModId(), "qmd");
	}

	@Override
	public void onCompatLoaded(GroovyContainer<?> container)
	{

	}
}