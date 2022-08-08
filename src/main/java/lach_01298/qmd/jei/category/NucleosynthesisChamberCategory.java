package lach_01298.qmd.jei.category;

import java.util.List;

import lach_01298.qmd.QMD;
import lach_01298.qmd.block.QMDBlocks;
import lach_01298.qmd.jei.ingredient.ParticleType;
import lach_01298.qmd.jei.recipe.NucleosynthesisChamberRecipe;
import lach_01298.qmd.particle.ParticleStack;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IGuiIngredientGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeCategory;
import nc.util.Lang;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;



public class NucleosynthesisChamberCategory implements IRecipeCategory<NucleosynthesisChamberRecipe>
{
	private final IDrawable background;
	protected final ResourceLocation gui_texture;
	private final IDrawable icon;
	

	
	public NucleosynthesisChamberCategory(IGuiHelper guiHelper) 
	{
		gui_texture = new ResourceLocation(QMD.MOD_ID + ":textures/gui/nucleosynthesis_chamber_controller.png");
		background = guiHelper.createDrawable(gui_texture, 29, 15, 124, 66);
		icon = guiHelper.createDrawableIngredient(new ItemStack(QMDBlocks.nucleosynthesisChamberController));
		
	}
	
	
	@Override
	public String getUid()
	{
		
		return QMDRecipeCategoryUid.NUCLEOSYNTHESIS_CHAMBER;
	}

	@Override
	public String getTitle()
	{
		
		return Lang.localise("qmd.gui.jei.category.nucleosynthesis_chamber");
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
	public void setRecipe(IRecipeLayout recipeLayout, NucleosynthesisChamberRecipe recipeWrapper, IIngredients ingredients)
	{
		
		IGuiIngredientGroup<ParticleStack> guiParticleStacks = recipeLayout.getIngredientsGroup(ParticleType.Particle);
		
		IGuiFluidStackGroup guiFluidStacks = recipeLayout.getFluidStacks();
		
		List<List<ParticleStack>> particleInput =ingredients.getInputs(ParticleType.Particle);
		List<List<FluidStack>> fluidInputs =ingredients.getInputs(VanillaTypes.FLUID);
		List<List<FluidStack>> fluidOutputs =ingredients.getOutputs(VanillaTypes.FLUID);
		
		
		guiParticleStacks.init(0, true, 3, 15);
		guiFluidStacks.init(1, true, 25, 3, 16, 16, fluidInputs.get(0)== null ? 1000 : Math.max(1, fluidInputs.get(0).size()), false, null);
		guiFluidStacks.init(2, true, 25, 27, 16, 16, fluidInputs.get(1)== null ? 1000 : Math.max(1, fluidInputs.get(1).size()), false, null);
		guiFluidStacks.init(3, false, 105, 3, 16, 16, fluidOutputs.get(0)== null ? 1000 : Math.max(1, fluidOutputs.get(0).size()), false, null);
		guiFluidStacks.init(4, false, 105, 27, 16, 16, fluidOutputs.get(1)== null ? 1000 : Math.max(1, fluidOutputs.get(1).size()), false, null);

		
		guiParticleStacks.set(0,particleInput.get(0));
		
		if(fluidInputs.get(0) != null)
		{
			guiFluidStacks.set(1, fluidInputs.get(0));
		}
		if(fluidInputs.get(1) != null)
		{
			guiFluidStacks.set(2, fluidInputs.get(1));
		}
		if(fluidOutputs.get(0) != null)
		{
			if (fluidOutputs.get(0).get(0) != null)
			{
				guiFluidStacks.set(3, fluidOutputs.get(0));
			}	
		}
		if(fluidOutputs.get(1) != null)
		{
			if(fluidOutputs.get(1).get(0) != null)
			{
				guiFluidStacks.set(4, fluidOutputs.get(1));
			}
		}
		
		
		
		
		
		
	}
	

}
