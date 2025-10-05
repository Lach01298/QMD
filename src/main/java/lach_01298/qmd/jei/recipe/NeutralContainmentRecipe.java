package lach_01298.qmd.jei.recipe;

import lach_01298.qmd.QMD;
import lach_01298.qmd.QMDConstants;
import lach_01298.qmd.recipe.QMDRecipe;
import lach_01298.qmd.recipes.QMDRecipes;
import lach_01298.qmd.util.Units;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawableStatic;
import nc.util.Lang;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ResourceLocation;

import java.awt.*;

public class NeutralContainmentRecipe extends JEIRecipeWrapper
{


	public final IDrawable arrow2;
	public final int arrowDrawPosX2, arrowDrawPosY2;
	
	
	public NeutralContainmentRecipe(IGuiHelper guiHelper, QMDRecipe recipe)
	{
		
		super(guiHelper, QMDRecipes.neutral_containment, recipe, new ResourceLocation(QMD.MOD_ID + ":textures/gui/neutral_containment_controller.png"), 0, 0, 206, 0, 10, 6, 36, 24,0);
		
		IDrawableStatic arrowDrawable = guiHelper.createDrawable(new ResourceLocation(QMD.MOD_ID + ":textures/gui/neutral_containment_controller.png"), 206, 6, Math.max(10, 1), Math.max(6, 1));
		arrow2 = guiHelper.createAnimatedDrawable(arrowDrawable, getProgressArrow2Time(), IDrawableAnimated.StartDirection.RIGHT, false);
		arrowDrawPosX2 = 80;
		arrowDrawPosY2 = 24;
	
	}
	
	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY)
	{
		super.drawInfo(minecraft, recipeWidth, recipeHeight, mouseX, mouseY);
		
		if(!inputParticles.get(1).isEmpty())
		{
			arrow2.draw(minecraft, arrowDrawPosX2, arrowDrawPosY2);
		}
		
		FontRenderer fontRenderer = minecraft.fontRenderer;
		if(recipe.getMaxEnergy() != Long.MAX_VALUE)
		{
			
			String rangeString = Lang.localize("gui.qmd.jei.reaction.range",  Units.getParticleEnergy(inputParticles.get(0).get(0).getMeanEnergy()) + "-" + Units.getSIFormat(recipe.getMaxEnergy(),3,"eV"));
			fontRenderer.drawString(rangeString, 0, 1, Color.gray.getRGB());
		}
		
	}

	@Override
	protected int getProgressArrowTime()
	{
		return Math.max(inputParticles.get(0).get(0).getAmount()/Math.max(QMDConstants.ionSourceOutput/200,1),5);
	}

	protected int getProgressArrow2Time()
	{
		if (!inputParticles.get(1).isEmpty())
		{
			if (inputParticles.get(1).get(0) != null)
			{
				return Math.max(inputParticles.get(1).get(0).getAmount()/Math.max(QMDConstants.ionSourceOutput/200,1),5);
			}
		}
		return 100;
	}

}
