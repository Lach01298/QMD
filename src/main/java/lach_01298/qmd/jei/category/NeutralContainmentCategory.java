package lach_01298.qmd.jei.category;

import lach_01298.qmd.QMD;
import lach_01298.qmd.block.QMDBlocks;
import lach_01298.qmd.jei.ingredient.ParticleType;
import lach_01298.qmd.jei.recipe.NeutralContainmentRecipe;
import lach_01298.qmd.particle.ParticleStack;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.*;
import mezz.jei.api.ingredients.*;
import mezz.jei.api.recipe.IRecipeCategory;
import nc.util.Lang;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;



public class NeutralContainmentCategory implements IRecipeCategory<NeutralContainmentRecipe>
{
	private final IDrawable background;
	protected final ResourceLocation gui_texture;
	private final IDrawable icon;
	

	
	public NeutralContainmentCategory(IGuiHelper guiHelper)
	{
		gui_texture = new ResourceLocation(QMD.MOD_ID + ":textures/gui/neutral_containment_controller.png");
		background = guiHelper.createDrawable(gui_texture, 24, 9, 126, 47);
		icon = guiHelper.createDrawableIngredient(new ItemStack(QMDBlocks.exoticContainmentController));
		
	}
	
	
	@Override
	public String getUid()
	{
		
		return QMDRecipeCategoryUid.NEUTRAL_CONTAINMENT;
	}

	@Override
	public String getTitle()
	{
		
		return Lang.localize("qmd.gui.jei.category.neutral_containment");
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
	public void setRecipe(IRecipeLayout recipeLayout, NeutralContainmentRecipe recipeWrapper, IIngredients ingredients)
	{
		
		IGuiIngredientGroup<ParticleStack> guiParticleStacks = recipeLayout.getIngredientsGroup(ParticleType.Particle);
		
		IGuiFluidStackGroup guiFluidStacks = recipeLayout.getFluidStacks();
		
		List<List<ParticleStack>> particleInputs =ingredients.getInputs(ParticleType.Particle);
		List<List<FluidStack>> fluidOutputs =ingredients.getOutputs(VanillaTypes.FLUID);
		
		
		guiParticleStacks.init(0, true, 3, 19);
		guiParticleStacks.init(1, true, 107, 19);
		guiFluidStacks.init(2, false, 47, 11, 32, 32, fluidOutputs.get(0)== null ? 1000 : Math.max(1, fluidOutputs.get(0).size()), false, null);
		
		if(particleInputs.get(0) != null)
		{
			guiParticleStacks.set(0,particleInputs.get(0));
		}
		if(particleInputs.get(1) != null)
		{
			guiParticleStacks.set(1,particleInputs.get(1));
		}

		guiFluidStacks.set(2, fluidOutputs.get(0));
		
		
		
	}
	

}
