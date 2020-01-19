package lach_01298.qmd.jei;

import java.util.List;

import lach_01298.qmd.block.QMDBlocks;
import lach_01298.qmd.jei.catergory.AcceleratorSourceCategory;
import lach_01298.qmd.jei.catergory.ParticleInfoCategory;
import lach_01298.qmd.jei.catergory.QMDRecipeCategoryUid;
import lach_01298.qmd.jei.catergory.TargetChamberCategory;
import lach_01298.qmd.jei.ingredient.ParticleStackHelper;
import lach_01298.qmd.jei.ingredient.ParticleStackListFactory;
import lach_01298.qmd.jei.ingredient.ParticleStackRenderer;
import lach_01298.qmd.jei.ingredient.ParticleType;
import lach_01298.qmd.jei.recipe.AcceleratorSourceRecipe;
import lach_01298.qmd.jei.recipe.AcceleratorSourceRecipeMaker;
import lach_01298.qmd.jei.recipe.ParticleInfoRecipeMaker;
import lach_01298.qmd.jei.recipe.TargetChamberTargetRecipeMaker;
import lach_01298.qmd.particle.ParticleStack;
import lach_01298.qmd.recipe.AcceleratorSourceRecipes;
import lach_01298.qmd.recipe.QMDRecipes;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.ISubtypeRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.ingredients.IIngredientRegistry;
import mezz.jei.api.ingredients.IModIngredientRegistration;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

@JEIPlugin
public class QMDJEI implements IModPlugin
{

	@Override
	public void registerItemSubtypes(ISubtypeRegistry subtypeRegistry) 
	{
		
	}
	
	@Override
	public void registerIngredients(IModIngredientRegistration registry)
	{

		List<ParticleStack> particleStacks = ParticleStackListFactory.create();
		ParticleStackHelper particleStackHelper = new ParticleStackHelper();
		ParticleStackRenderer particleStackRenderer = new ParticleStackRenderer();
		
		registry.register(ParticleType.Particle, particleStacks, particleStackHelper, particleStackRenderer);
	}

	public void registerCategories(IRecipeCategoryRegistration registry)
	{
		IJeiHelpers jeiHelpers = registry.getJeiHelpers();
		IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
		registry.addRecipeCategories(
				new AcceleratorSourceCategory(guiHelper),
				new ParticleInfoCategory(guiHelper),
				new TargetChamberCategory(guiHelper)
				);

	}

	public void register(IModRegistry registry)
	{
		IIngredientRegistry ingredientRegistry = registry.getIngredientRegistry();
		IJeiHelpers jeiHelpers = registry.getJeiHelpers();


		registry.addRecipes(AcceleratorSourceRecipeMaker.getRecipes(jeiHelpers), QMDRecipeCategoryUid.SOURCE);
		registry.addRecipeCatalyst(new ItemStack(QMDBlocks.acceleratorSource),QMDRecipeCategoryUid.SOURCE);
	
		registry.addRecipes(ParticleInfoRecipeMaker.getRecipes(jeiHelpers), QMDRecipeCategoryUid.PARTICLE_INFO);
	
		registry.addRecipes(TargetChamberTargetRecipeMaker.getRecipes(jeiHelpers), QMDRecipeCategoryUid.TARGET_CHAMBER);
		registry.addRecipeCatalyst(new ItemStack(QMDBlocks.particleChamberTarget),QMDRecipeCategoryUid.TARGET_CHAMBER);
	}


}
