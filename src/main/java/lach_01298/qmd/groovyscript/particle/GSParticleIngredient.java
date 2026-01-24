//package lach_01298.qmd.groovyscript.particle;
//
//import com.cleanroommc.groovyscript.api.documentation.annotations.MethodDescription;
//import com.cleanroommc.groovyscript.helper.Alias;
//import com.cleanroommc.groovyscript.registry.NamedRegistry;
//import com.google.common.base.CaseFormat;
//import lach_01298.qmd.groovyscript.GSQMDHelper;
//import lach_01298.qmd.recipe.ingredient.IParticleIngredient;
//
//public class GSParticleIngredient extends NamedRegistry
//{
//	public GSParticleIngredient()
//	{
//		super(Alias.generateOf("Particle", CaseFormat.UPPER_CAMEL));
//	}
//
//	@MethodDescription(type = MethodDescription.Type.QUERY)
//	public IParticleIngredient create(Object object)
//	{
//		return GSQMDHelper.buildAdditionParticleIngredient(object);
//	}
//}
