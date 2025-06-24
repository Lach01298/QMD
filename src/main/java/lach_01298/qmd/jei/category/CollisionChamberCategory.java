package lach_01298.qmd.jei.category;

import lach_01298.qmd.block.QMDBlocks;
import lach_01298.qmd.jei.QMDJEIHelper;
import lach_01298.qmd.jei.ingredient.ParticleType;
import lach_01298.qmd.jei.recipe.CollisionChamberRecipe;
import lach_01298.qmd.particle.ParticleStack;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IGuiIngredientGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import nc.recipe.IngredientSorption;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;



public class CollisionChamberCategory extends QMDProcessorCategory<CollisionChamberRecipe>
{

	public CollisionChamberCategory(IGuiHelper guiHelper)
	{
		super(guiHelper, "collision_chamber",":textures/gui/jei/collision_chamber.png",new ItemStack(QMDBlocks.collisionChamberController), 0, 0, 160, 112);
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, CollisionChamberRecipe recipeWrapper, IIngredients ingredients)
	{
		super.setRecipe(recipeLayout, recipeWrapper, ingredients);

		IGuiIngredientGroup<ParticleStack> guiParticleStacks = recipeLayout.getIngredientsGroup(ParticleType.Particle);

		List<List<ParticleStack>> particleInputs = new ArrayList();


		for(List<ParticleStack> stacks : ingredients.getInputs(ParticleType.Particle))
		{
			List<ParticleStack> particleInput =new ArrayList();
			for(ParticleStack stack : stacks)
			{

				ParticleStack newStack = stack.copy();
				newStack.setMeanEnergy(newStack.getMeanEnergy()/2);
				particleInput.add(newStack);

			}
			particleInputs.add(particleInput);
		}


		List<List<ParticleStack>> particleOutputs =ingredients.getOutputs(ParticleType.Particle);

		guiParticleStacks.init(1, true, 43, 33);
		guiParticleStacks.init(2, true, 111, 33);


		guiParticleStacks.init(3, false, 46, 2);
		guiParticleStacks.init(4, false, 108, 2);
		guiParticleStacks.init(5, false, 46, 64);
		guiParticleStacks.init(6, false, 108, 64);


		guiParticleStacks.set(1,particleInputs.get(0));
		guiParticleStacks.set(2,particleInputs.get(1));

		guiParticleStacks.set(3, particleOutputs.get(0));
		guiParticleStacks.set(4, particleOutputs.get(1));
		guiParticleStacks.set(5, particleOutputs.get(2));
		guiParticleStacks.set(6, particleOutputs.get(3));
	}
	

}
