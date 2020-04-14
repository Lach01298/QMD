package lach_01298.qmd.jei.catergory;

import java.util.List;

import lach_01298.qmd.QMD;
import lach_01298.qmd.block.QMDBlocks;
import lach_01298.qmd.jei.ingredient.IGUIParticleStackGroup;
import lach_01298.qmd.jei.ingredient.ParticleType;
import lach_01298.qmd.jei.recipe.AcceleratorSourceRecipe;
import lach_01298.qmd.jei.recipe.TargetChamberRecipe;
import lach_01298.qmd.particle.ParticleStack;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiIngredientGroup;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import nc.util.Lang;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;



public class TargetChamberCategory implements IRecipeCategory<TargetChamberRecipe>
{
	private final IDrawable background;
	protected final ResourceLocation gui_texture;
	private final IDrawable icon;
	

	
	public TargetChamberCategory(IGuiHelper guiHelper) 
	{
		gui_texture = new ResourceLocation(QMD.MOD_ID + ":textures/gui/jei/target_chamber.png");
		background = guiHelper.createDrawable(gui_texture, 0, 0, 150, 100);
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
		IGuiIngredientGroup<ParticleStack> guiParticleStacks = recipeLayout.getIngredientsGroup(ParticleType.Particle);
		
		List<List<ItemStack>> itemInput = ingredients.getInputs(VanillaTypes.ITEM);
		List<List<ItemStack>> itemOutput = ingredients.getOutputs(VanillaTypes.ITEM);
		List<List<ParticleStack>> particleInput =ingredients.getInputs(ParticleType.Particle);
		
		List<List<ParticleStack>> particleOutputs =ingredients.getOutputs(ParticleType.Particle);
		
		
		
		guiItemStacks.init(0, true, 42, 25);
		
		guiParticleStacks.init(1, true, 8, 26);
		
		guiItemStacks.init(2, false, 84, 25);
		
		guiParticleStacks.init(3, true, 76, 3);
		guiParticleStacks.init(4, true, 122, 26);
		guiParticleStacks.init(5, true, 76, 49);
		
		
		guiItemStacks.set(0,itemInput.get(0));
		
		guiParticleStacks.set(1,particleInput.get(0));
		
		guiItemStacks.set(2,itemOutput.get(0));
		
		if(particleOutputs.get(0) != null)
		{
			guiParticleStacks.set(3,particleOutputs.get(0));
		}
		
		if(particleOutputs.get(1) != null)
		{
			guiParticleStacks.set(4,particleOutputs.get(1));
		}
		
		if(particleOutputs.get(2) != null)
		{
			guiParticleStacks.set(5,particleOutputs.get(2));
		}
	}
	

}



