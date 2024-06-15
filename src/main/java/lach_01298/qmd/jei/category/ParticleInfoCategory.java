package lach_01298.qmd.jei.category;

import lach_01298.qmd.QMD;
import lach_01298.qmd.jei.ingredient.ParticleType;
import lach_01298.qmd.jei.recipe.ParticleInfoRecipe;
import lach_01298.qmd.particle.*;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.*;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import nc.util.Lang;
import net.minecraft.util.ResourceLocation;

import java.util.List;

public class ParticleInfoCategory implements IRecipeCategory<ParticleInfoRecipe>
{
	private final IDrawable background;
	protected final ResourceLocation gui_texture;
	private final IDrawable icon;
	
	
	
	public ParticleInfoCategory(IGuiHelper guiHelper)
	{
		gui_texture = new ResourceLocation(QMD.MOD_ID + ":textures/gui/jei/blank.png");
		background = guiHelper.createDrawable(gui_texture, 0, 0, 150, 140);
		icon = guiHelper.createDrawableIngredient(new ParticleStack(Particles.alpha,0,0));
		
	}
	
	
	@Override
	public String getUid()
	{
		
		return QMDRecipeCategoryUid.PARTICLE_INFO;
	}

	@Override
	public String getTitle()
	{
		
		return Lang.localize("qmd.gui.jei.category.particle_info");
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
	public void setRecipe(IRecipeLayout recipeLayout, ParticleInfoRecipe recipeWrapper, IIngredients ingredients)
	{
		IGuiIngredientGroup<ParticleStack> guiParticleStacks = recipeLayout.getIngredientsGroup(ParticleType.Particle);
		List<List<ParticleStack>> outputs = ingredients.getOutputs(ParticleType.Particle);
		List<List<ParticleStack>> inputs = ingredients.getInputs(ParticleType.Particle);
		
		guiParticleStacks.init(0, false, 0, 10);
		guiParticleStacks.set(0, outputs.get(0));
		
		
		for(int i = 0; i < inputs.size(); i++)
		{
			guiParticleStacks.init(1+i, true, 90+i*16, 10);
			guiParticleStacks.set(1+i, inputs.get(i));
		}
		
		
	}
	

}
