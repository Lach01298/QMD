package lach_01298.qmd.recipes;

import lach_01298.qmd.util.Util;
import nc.util.FluidRegHelper;
import net.minecraftforge.fluids.*;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

import static lach_01298.qmd.config.QMDConfig.atmosphere_collector_recipes;

public class AtmosphereCollectorRecipes
{
	public static Map<Integer,FluidStack> recipes = new HashMap<Integer,FluidStack>();
	
	
	
	public static FluidStack getRecipe(int dimesionId)
	{
		if(recipes.containsKey(dimesionId))
		{
			return recipes.get(dimesionId);
		}
		return null;
	}
	
	
	
	public static void addRecipe(int dimesionId,FluidStack stack)
	{
		if(recipes.containsKey(dimesionId))
		{
			Util.getLogger().error("there is already an atmosphereCollector recipe with  dimesionID " + dimesionId);
			return;
		}
		if(stack == null)
		{
			Util.getLogger().error("atmosphereCollector recipe fluidStack can not be null for dimesionID " + dimesionId);
			return;
		}
		
		recipes.put(dimesionId, stack);
	
	}
	
	public static void registerRecipes()
	{
		for (String recipe : atmosphere_collector_recipes)
		{
			String[] recipeParts = StringUtils.split(recipe, ":");
			if(recipeParts.length != 3)
			{
				Util.getLogger().error("invailid atmosphereCollector recipe: " + recipe);
			}
			else
			{
				 if(!FluidRegHelper.fluidExists(recipeParts[1]))
				 {
					 Util.getLogger().error("invailid atmosphereCollector recipe: " + recipe + ". There is no fluid called: " + recipeParts[1]);
				 }
				 else
				 {
					 int dimesionId = Integer.parseInt(recipeParts[0]);
					 Fluid fluid =FluidRegistry.getFluid(recipeParts[1]);
					 int stackSize = Integer.parseInt(recipeParts[2]);
					 addRecipe(dimesionId, new FluidStack(fluid,stackSize));
					 
				 }
				 
			}
			
		}
	}
	
	
	
	
	
}
