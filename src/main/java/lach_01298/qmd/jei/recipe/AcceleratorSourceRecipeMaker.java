package lach_01298.qmd.jei.recipe;

import lach_01298.qmd.particle.ParticleStack;
import lach_01298.qmd.recipe.*;
import lach_01298.qmd.recipes.QMDRecipes;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.recipe.IStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.*;

public class AcceleratorSourceRecipeMaker
{
	private AcceleratorSourceRecipeMaker()
	{
	}

	public static List<AcceleratorSourceRecipe> getRecipes(IJeiHelpers helpers)
	{
		List<QMDRecipe> recipes = QMDRecipes.accelerator_source.getRecipeList();
		List<AcceleratorSourceRecipe> jeiRecipes = new ArrayList<>();

		for (QMDRecipe recipe : recipes)
		{
			AcceleratorSourceRecipe jeiRecipe = new AcceleratorSourceRecipe(helpers.getGuiHelper(),recipe);
			jeiRecipes.add(jeiRecipe);
		}

		return jeiRecipes;
	}
}
