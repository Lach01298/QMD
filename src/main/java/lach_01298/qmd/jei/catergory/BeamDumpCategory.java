package lach_01298.qmd.jei.catergory;

import java.util.List;

import lach_01298.qmd.QMD;
import lach_01298.qmd.block.QMDBlocks;
import lach_01298.qmd.jei.ingredient.ParticleType;
import lach_01298.qmd.jei.recipe.BeamDumpRecipe;
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



public class BeamDumpCategory implements IRecipeCategory<BeamDumpRecipe>
{
	private final IDrawable background;
	protected final ResourceLocation gui_texture;
	private final IDrawable icon;
	

	
	public BeamDumpCategory(IGuiHelper guiHelper) 
	{
		gui_texture = new ResourceLocation(QMD.MOD_ID + ":textures/gui/beam_dump_controller.png");
		background = guiHelper.createDrawable(gui_texture, 36, 36, 62, 25);
		icon = guiHelper.createDrawableIngredient(new ItemStack(QMDBlocks.beamDumpController));
		
	}
	
	
	@Override
	public String getUid()
	{
		
		return QMDRecipeCategoryUid.BEAM_DUMP;
	}

	@Override
	public String getTitle()
	{
		
		return Lang.localise("qmd.gui.jei.category.beam_dump");
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
	public void setRecipe(IRecipeLayout recipeLayout, BeamDumpRecipe recipeWrapper, IIngredients ingredients)
	{
		
		IGuiIngredientGroup<ParticleStack> guiParticleStacks = recipeLayout.getIngredientsGroup(ParticleType.Particle);
		
		IGuiFluidStackGroup guiFluidStacks = recipeLayout.getFluidStacks();
		
		List<List<ParticleStack>> particleInput =ingredients.getInputs(ParticleType.Particle);
		List<List<FluidStack>> fluidOutputs =ingredients.getOutputs(VanillaTypes.FLUID);
		
		
		guiParticleStacks.init(0, true, 1, 1);
		guiFluidStacks.init(1, false, 45, 1, 16, 16, fluidOutputs.get(0)== null ? 1000 : Math.max(1, fluidOutputs.get(0).size()), true, null);
		
		
		guiParticleStacks.set(0,particleInput.get(0));
		guiFluidStacks.set(1, fluidOutputs.get(0));
		
		
		
	}
	

}
