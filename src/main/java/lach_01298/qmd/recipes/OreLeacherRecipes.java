package lach_01298.qmd.recipes;

import java.util.ArrayList;
import java.util.List;

import nc.recipe.ProcessorRecipeHandler;
import nc.recipe.ingredient.EmptyItemIngredient;
import nc.util.FluidStackHelper;

public class OreLeacherRecipes extends ProcessorRecipeHandler
{

	public OreLeacherRecipes()
	{
		super("ore_leacher", 1, 3, 3, 0);
		
	}

	@Override
	public void addRecipes()
	{

		
		addRecipe("oreIron",fluidStack("nitric_acid", FluidStackHelper.NUGGET_VOLUME),
				fluidStack("hydrochloric_acid", FluidStackHelper.NUGGET_VOLUME),
				fluidStack("sulfuric_acid", FluidStackHelper.NUGGET_VOLUME),
				chanceOreStack("dustIron", 3, 50,2),chanceOreStack("dustChromium", 1, 40),chanceOreStack("dustManganese", 1, 5)
				);
		addRecipe("oreGold",fluidStack("nitric_acid", FluidStackHelper.NUGGET_VOLUME),
				fluidStack("hydrochloric_acid", FluidStackHelper.NUGGET_VOLUME),
				fluidStack("sulfuric_acid", FluidStackHelper.NUGGET_VOLUME),
				chanceOreStack("dustGold", 3, 50,2),chanceOreStack("dustSilver", 1, 10),new EmptyItemIngredient()
				);
		addRecipe("oreCopper",fluidStack("nitric_acid", FluidStackHelper.NUGGET_VOLUME),
				fluidStack("hydrochloric_acid", FluidStackHelper.NUGGET_VOLUME),
				fluidStack("sulfuric_acid", FluidStackHelper.NUGGET_VOLUME),
				chanceOreStack("dustCopper", 3, 50,2),chanceOreStack("dustZinc", 1, 5),chanceOreStack("dustIridium", 1, 1)
				);
		addRecipe("oreTin",fluidStack("nitric_acid", FluidStackHelper.NUGGET_VOLUME),
				fluidStack("hydrochloric_acid", FluidStackHelper.NUGGET_VOLUME),
				fluidStack("sulfuric_acid", FluidStackHelper.NUGGET_VOLUME),
				chanceOreStack("dustTin", 3, 50,2),chanceOreStack("dustZirconium", 1, 25),chanceOreStack("dustTungsten", 1, 10)
				);
		addRecipe("oreLead",fluidStack("nitric_acid", FluidStackHelper.NUGGET_VOLUME),
				fluidStack("hydrochloric_acid", FluidStackHelper.NUGGET_VOLUME),
				fluidStack("sulfuric_acid", FluidStackHelper.NUGGET_VOLUME),
				chanceOreStack("dustLead", 3, 50,2),chanceOreStack("dustNickel", 1, 10),chanceOreStack("dustCobalt", 1, 5)
				);
		addRecipe("oreUranium",fluidStack("nitric_acid", FluidStackHelper.NUGGET_VOLUME),
				fluidStack("hydrochloric_acid", FluidStackHelper.NUGGET_VOLUME),
				fluidStack("sulfuric_acid", FluidStackHelper.NUGGET_VOLUME),
				chanceOreStack("dustUranium", 3, 50,2),chanceOreStack("dustThorium", 1, 10),chanceOreStack("dustNiobium", 1, 5)
				);
		addRecipe("oreThorium",fluidStack("nitric_acid", FluidStackHelper.NUGGET_VOLUME),
				fluidStack("hydrochloric_acid", FluidStackHelper.NUGGET_VOLUME),
				fluidStack("sulfuric_acid", FluidStackHelper.NUGGET_VOLUME),
				chanceOreStack("dustThorium", 3, 50,2),chanceOreStack("dustTitanium", 1, 10),chanceOreStack("dustHafnium", 1, 5)
				);
		addRecipe("oreLithium",fluidStack("nitric_acid", FluidStackHelper.NUGGET_VOLUME),
				fluidStack("hydrochloric_acid", FluidStackHelper.NUGGET_VOLUME),
				fluidStack("sulfuric_acid", FluidStackHelper.NUGGET_VOLUME),
				chanceOreStack("dustLithium", 3, 50,2),chanceOreStack("dustAluminum", 1, 10),chanceOreStack("dustSodium", 1, 5)
				);
		addRecipe("oreMagnesium",fluidStack("nitric_acid", FluidStackHelper.NUGGET_VOLUME),
				fluidStack("hydrochloric_acid", FluidStackHelper.NUGGET_VOLUME),
				fluidStack("sulfuric_acid", FluidStackHelper.NUGGET_VOLUME),
				chanceOreStack("dustMagnesium", 3, 50,2),chanceOreStack("dustCalcium", 1, 10),chanceOreStack("dustStrontium", 1, 5)
				);
		addRecipe("oreBoron",fluidStack("nitric_acid", FluidStackHelper.NUGGET_VOLUME),
				fluidStack("hydrochloric_acid", FluidStackHelper.NUGGET_VOLUME),
				fluidStack("sulfuric_acid", FluidStackHelper.NUGGET_VOLUME),
				chanceOreStack("dustBoron", 3, 50,2),new EmptyItemIngredient(),new EmptyItemIngredient()
				);
	}

	@Override
	public List fixExtras(List extras) 
	{
		List fixed = new ArrayList(3);
		fixed.add(extras.size() > 0 && extras.get(0) instanceof Double ? (double) extras.get(0) : 1D);
		fixed.add(extras.size() > 1 && extras.get(1) instanceof Double ? (double) extras.get(1) : 1D);
		fixed.add(extras.size() > 2 && extras.get(2) instanceof Double ? (double) extras.get(2) : 0D);
		return fixed;
	}

}
