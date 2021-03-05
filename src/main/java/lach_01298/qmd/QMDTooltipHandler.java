package lach_01298.qmd;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

import lach_01298.qmd.accelerator.CoolerPlacement;
import nc.recipe.ProcessorRecipe;
import nc.recipe.RecipeInfo;
import nc.util.FontRenderHelper;
import nc.util.InfoHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class QMDTooltipHandler
{
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent(priority = EventPriority.HIGH)
	public void addAdditionalTooltips(ItemTooltipEvent event) 
	{
		List<String> tooltip = event.getToolTip();
		ItemStack stack = event.getItemStack();
		
		addPlacementRuleTooltip(tooltip, stack);
		
	}
	
	
		// Placement Rule Tooltips

		@SideOnly(Side.CLIENT)
		private static void addPlacementRuleTooltip(List<String> tooltip, ItemStack stack) 
		{
			RecipeInfo<ProcessorRecipe> recipeInfo = CoolerPlacement.tooltip_recipe_handler.getRecipeInfoFromInputs(Lists.newArrayList(stack), new ArrayList<>());
			ProcessorRecipe recipe = recipeInfo == null ? null : recipeInfo.getRecipe();
			if (recipe != null) 
			{
				String rule = CoolerPlacement.TOOLTIP_MAP.get(recipe.getPlacementRuleID());
				if (rule != null) 
				{
					InfoHelper.infoFull(tooltip, TextFormatting.AQUA, FontRenderHelper.wrapString(rule, InfoHelper.MAXIMUM_TEXT_WIDTH));
				}
			}	
		}
}
