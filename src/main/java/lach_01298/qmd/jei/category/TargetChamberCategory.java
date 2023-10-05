package lach_01298.qmd.jei.category;

import java.util.List;

import lach_01298.qmd.QMD;
import lach_01298.qmd.block.QMDBlocks;
import lach_01298.qmd.jei.ingredient.ParticleType;
import lach_01298.qmd.jei.recipe.TargetChamberRecipe;
import lach_01298.qmd.particle.ParticleStack;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IGuiIngredientGroup;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeCategory;
import nc.util.Lang;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;



public class TargetChamberCategory implements IRecipeCategory<TargetChamberRecipe>
{
	private final IDrawable background;
	protected final ResourceLocation gui_texture;
	private final IDrawable icon;
	

	
	public TargetChamberCategory(IGuiHelper guiHelper) 
	{
		gui_texture = new ResourceLocation(QMD.MOD_ID + ":textures/gui/jei/target_chamber.png");
		background = guiHelper.createDrawable(gui_texture, 0, 0, 150, 115);
		icon = guiHelper.createDrawableIngredient(new ItemStack(QMDBlocks.targetChamberController));
		
	}
	
	
	@Override
	public String getUid()
	{
		
		return QMDRecipeCategoryUid.TARGET_CHAMBER;
	}

	@Override
	public String getTitle()
	{
		
		return Lang.localise("qmd.gui.jei.category.target_chamber");
	}

	@Override
	public String getModName()
	{
		
		return QMD.MOD_NAME;
	}

	@Override
	public IDrawable getBackground()
	{
		return background;
	}

	@Override
	public IDrawable getIcon()
	{
		return icon;
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, TargetChamberRecipe recipeWrapper, IIngredients ingredients)
	{
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
		IGuiFluidStackGroup guiFluidStacks = recipeLayout.getFluidStacks();
		IGuiIngredientGroup<ParticleStack> guiParticleStacks = recipeLayout.getIngredientsGroup(ParticleType.Particle);
		
		List<List<ItemStack>> itemInput = ingredients.getInputs(VanillaTypes.ITEM);
		List<List<ItemStack>> itemOutput = ingredients.getOutputs(VanillaTypes.ITEM);
		List<List<FluidStack>> fluidInputs =ingredients.getInputs(VanillaTypes.FLUID);
		List<List<FluidStack>> fluidOutputs =ingredients.getOutputs(VanillaTypes.FLUID);
		List<List<ParticleStack>> particleInput =ingredients.getInputs(ParticleType.Particle);
		List<List<ParticleStack>> particleOutputs =ingredients.getOutputs(ParticleType.Particle);
		
		
		
		guiItemStacks.init(0, true, 42, 25);
		guiFluidStacks.init(1, true, 43, 43, 16, 16, fluidInputs.get(0)== null ? 1000 : Math.max(1, fluidInputs.get(0).size()), false, null);
		guiParticleStacks.init(2, true, 8, 34);
		
		guiItemStacks.init(3, false, 83, 25);
		guiFluidStacks.init(4, false, 84, 43, 16, 16, fluidOutputs.get(0)== null ? 1000 : Math.max(1, fluidOutputs.get(0).size()), false, null);
		guiParticleStacks.init(5, false, 76, 3);
		guiParticleStacks.init(6, false, 119, 34);
		guiParticleStacks.init(7, false, 76, 66);
		
		
		guiItemStacks.set(0,itemInput.get(0));
		
		if(fluidInputs.get(0) != null)
		{
			guiFluidStacks.set(1, fluidInputs.get(0));
		}
		
		guiParticleStacks.set(2,particleInput.get(0));
		
		
		
		guiItemStacks.set(3,itemOutput.get(0));
		
		if(fluidOutputs.get(0) != null)
		{
			if (fluidOutputs.get(0).get(0) != null)
			{
				guiFluidStacks.set(4, fluidOutputs.get(0));
			}	
		}

		guiParticleStacks.set(5,particleOutputs.get(0));
		guiParticleStacks.set(6,particleOutputs.get(1));
		guiParticleStacks.set(7,particleOutputs.get(2));
		
		
		
		
		
	}
	

}



