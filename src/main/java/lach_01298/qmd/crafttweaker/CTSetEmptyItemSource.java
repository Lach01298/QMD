package lach_01298.qmd.crafttweaker;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import lach_01298.qmd.item.ItemCustomParticleSource;
import lach_01298.qmd.particle.Particle;
import lach_01298.qmd.particle.ParticleStack;
import net.minecraft.item.ItemStack;

public class CTSetEmptyItemSource implements IAction
{

	ItemCustomParticleSource source;
	ItemStack emptySource;
	public boolean ingredientError;
	public static boolean hasErrored = false;
	
	public CTSetEmptyItemSource(IIngredient itemSource, IIngredient ItemSourceEmpty)
	{
		if(itemSource instanceof IItemStack)
		{
			if(((IItemStack) itemSource).getInternal() instanceof ItemStack)
			{
				ItemStack stack = (ItemStack) itemSource.getInternal();
				
				if(stack.getItem() instanceof ItemCustomParticleSource)
				{
					source = (ItemCustomParticleSource) stack.getItem();
				}	
			}			
		}
		
		if(ItemSourceEmpty instanceof IItemStack)
		{
			if(((IItemStack) ItemSourceEmpty).getInternal() instanceof ItemStack)
			{
				emptySource = (ItemStack) ItemSourceEmpty.getInternal();
					
			}			
		}
		
		if (source == null ||emptySource == null)
		{
			ingredientError = true;
		}
			
	}

	@Override
	public void apply()
	{
		if(!ingredientError)
		{
			source.setEmptyItem(emptySource);
		}
		

	}

	@Override
	public String describe()
	{
		if(!ingredientError)
		{
			return String.format("Setting empty source item %1$s to source: %2$s", source.getTranslationKey(), emptySource.getTranslationKey(),source.getTranslationKey());
		}
		else
		{
			callError();
			return String.format("Error: Failed to set empty source item %1$s to source: %2$s", source.getTranslationKey(), emptySource.getTranslationKey(),source.getTranslationKey());
		}
		
	}

	public static void callError() 
	{
		if (!hasErrored) {
			CraftTweakerAPI.logError("At least one QMD CraftTweaker empty source item setting method has errored - check the CraftTweaker log for more details");
		}
		hasErrored = true;
	}
	
	
}
