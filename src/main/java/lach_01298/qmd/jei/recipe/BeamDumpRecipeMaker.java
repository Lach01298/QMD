package lach_01298.qmd.jei.recipe;

import java.util.ArrayList;
import java.util.List;

import lach_01298.qmd.particle.ParticleStack;
import lach_01298.qmd.recipe.QMDRecipe;
import lach_01298.qmd.recipe.QMDRecipeHelper;
import lach_01298.qmd.recipes.QMDRecipes;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.recipe.IStackHelper;
import net.minecraftforge.fluids.FluidStack;

public class BeamDumpRecipeMaker
{
	private BeamDumpRecipeMaker()
	{
	}

	public static List<BeamDumpRecipe> getRecipes(IJeiHelpers helpers)
	{
		
		List<QMDRecipe> recipes = QMDRecipes.beam_dump.getRecipeList();
		List<BeamDumpRecipe> jeiRecipes = new ArrayList<>();

		for (QMDRecipe recipe : recipes)
		{

			BeamDumpRecipe jeiRecipe = new BeamDumpRecipe(helpers.getGuiHelper(),recipe);
			jeiRecipes.add(jeiRecipe);
		}

		return jeiRecipes;
	}
}
