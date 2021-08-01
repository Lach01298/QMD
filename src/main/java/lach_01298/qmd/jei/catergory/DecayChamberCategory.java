package lach_01298.qmd.jei.catergory;

import java.util.List;

import lach_01298.qmd.QMD;
import lach_01298.qmd.block.QMDBlocks;
import lach_01298.qmd.jei.ingredient.ParticleType;
import lach_01298.qmd.jei.recipe.DecayChamberRecipe;
import lach_01298.qmd.particle.ParticleStack;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiIngredientGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import nc.util.Lang;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;



public class DecayChamberCategory implements IRecipeCategory<DecayChamberRecipe>
{
	private final IDrawable background;
	protected final ResourceLocation gui_texture;
	private final IDrawable icon;
	

	
	public DecayChamberCategory(IGuiHelper guiHelper) 
	{
		gui_texture = new ResourceLocation(QMD.MOD_ID + ":textures/gui/jei/decay_chamber.png");
		background = guiHelper.createDrawable(gui_texture, 0, 0, 150, 100);
		icon = guiHelper.createDrawableIngredient(new ItemStack(QMDBlocks.decayChamberController));
		
	}
	
	
	@Override
	public String getUid()
	{
		
		return QMDRecipeCategoryUid.DECAY_CHAMBER;
	}

	@Override
	public String getTitle()
	{
		
		return Lang.localise("qmd.gui.jei.category.decay_chamber");
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
	public void setRecipe(IRecipeLayout recipeLayout, DecayChamberRecipe recipeWrapper, IIngredients ingredients)
	{
		
		IGuiIngredientGroup<ParticleStack> guiParticleStacks = recipeLayout.getIngredientsGroup(ParticleType.Particle);
		
		List<List<ParticleStack>> particleInput =ingredients.getInputs(ParticleType.Particle);
		List<List<ParticleStack>> particleOutputs =ingredients.getOutputs(ParticleType.Particle);
		
		
		guiParticleStacks.init(1, true, 49, 31);
		
		
		guiParticleStacks.init(3, false, 82, 8);
		guiParticleStacks.init(4, false, 82, 31);
		guiParticleStacks.init(5, false, 82, 54);
			
		
		guiParticleStacks.set(1,particleInput.get(0));
		
		
	
		guiParticleStacks.set(3, particleOutputs.get(0));
		guiParticleStacks.set(4, particleOutputs.get(1));
		guiParticleStacks.set(5, particleOutputs.get(2));
		
	}
	

}
