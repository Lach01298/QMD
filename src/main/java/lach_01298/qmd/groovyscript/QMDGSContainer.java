//package lach_01298.qmd.groovyscript;
//
//import com.cleanroommc.groovyscript.api.GroovyBlacklist;
//import com.cleanroommc.groovyscript.compat.mods.GroovyPropertyContainer;
//import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
//import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
//import lach_01298.qmd.groovyscript.particle.GSParticleIngredient;
//import lach_01298.qmd.recipes.QMDRecipes;
//
//public class QMDGSContainer extends GroovyPropertyContainer
//{
//
//	@GroovyBlacklist
//	protected static QMDGSContainer instance;
//
//	@GroovyBlacklist
//	protected final Object2ObjectMap<String, GSQMDRecipeRegistry> registryCache = new Object2ObjectOpenHashMap<>();
//
//	protected QMDGSContainer()
//	{
//		super();
//		for (String name : QMDRecipes.RECIPE_HANDLER_NAME_ARRAY)
//		{
//			addProperty(getRecipeRegistryInternal(name));
//		}
//		addProperty(new GSStaticQMDRecipeHandler());
//		addProperty(new GSParticleIngredient());
//	}
//
//	@GroovyBlacklist
//	protected GSQMDRecipeRegistry getRecipeRegistry(String name)
//	{
//		GSQMDRecipeRegistry registry = registryCache.get(name);
//		if (registry == null)
//		{
//			registry = getRecipeRegistryInternal(name);
//			addProperty(registry);
//			registryCache.put(name, registry);
//		}
//		return registry;
//	}
//
//	@GroovyBlacklist
//	protected GSQMDRecipeRegistry getRecipeRegistryInternal(String name)
//	{
//		return switch (name)
//		{
//			case "target_chamber" -> new GSQMDRecipeRegistries.GSTargetChamberRecipeRegistry(name);
//			case "accelerator_cooling" -> new GSQMDRecipeRegistries.GSAcceleratorCoolingRecipeRegistry(name);
//			default -> new GSQMDRecipeRegistries.GSQMDProcessorRecipeRegistry(name);
//		};
//	}
//
//
//
//
//
//
//}