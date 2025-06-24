package lach_01298.qmd.groovyscript;

import com.cleanroommc.groovyscript.api.GroovyBlacklist;
import com.cleanroommc.groovyscript.api.IIngredient;
import com.cleanroommc.groovyscript.api.Result;
import com.cleanroommc.groovyscript.api.infocommand.InfoParserRegistry;
import com.cleanroommc.groovyscript.compat.mods.GroovyContainer;
import com.cleanroommc.groovyscript.compat.mods.GroovyPropertyContainer;
import com.cleanroommc.groovyscript.helper.ingredient.GroovyScriptCodeConverter;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import lach_01298.qmd.particle.Particle;
import lach_01298.qmd.particle.ParticleStack;
import lach_01298.qmd.particle.Particles;
import lach_01298.qmd.recipes.QMDRecipes;
import nc.integration.groovyscript.ingredient.GSChanceFluidIngredient;
import nc.integration.groovyscript.ingredient.GSChanceItemIngredient;
import org.jetbrains.annotations.Nullable;

public class QMDGSContainer extends GroovyPropertyContainer
{

	@GroovyBlacklist
	protected static QMDGSContainer instance;

	@GroovyBlacklist
	protected final Object2ObjectMap<String, GSQMDRecipeRegistry> registryCache = new Object2ObjectOpenHashMap<>();

	protected QMDGSContainer()
	{
		super();
		for (String name : QMDRecipes.RECIPE_HANDLER_NAME_ARRAY)
		{
			addProperty(getRecipeRegistryInternal(name));
		}
		addProperty(new GSStaticQMDRecipeHandler());
		addProperty(new GSChanceItemIngredient());
		addProperty(new GSChanceFluidIngredient());
	}

	@GroovyBlacklist
	protected GSQMDRecipeRegistry getRecipeRegistry(String name)
	{
		GSQMDRecipeRegistry registry = registryCache.get(name);
		if (registry == null)
		{
			registry = getRecipeRegistryInternal(name);
			addProperty(registry);
			registryCache.put(name, registry);
		}
		return registry;
	}

	@GroovyBlacklist
	protected GSQMDRecipeRegistry getRecipeRegistryInternal(String name)
	{
		return switch (name)
		{
			case "target_chamber" -> new GSQMDRecipeRegistries.GSTargetChamberRecipeRegistry(name);
			default -> new GSQMDRecipeRegistries.GSQMDProcessorRecipeRegistry(name);
		};
	}



	public static String asGroovyCode(ParticleStack particleStack, boolean colored)
	{
		return GroovyScriptCodeConverter.formatGenericHandler("particle", particleStack.getParticle().getName(), colored);
	}


	public static boolean isParticle(IIngredient ingredient)
	{
		return ingredient instanceof ParticleStack;
	}


	public static boolean isEmpty(@Nullable ParticleStack particleStack)
	{
		return particleStack == null || particleStack.getParticle() == null || particleStack.getAmount() <= 0;
	}
	



}