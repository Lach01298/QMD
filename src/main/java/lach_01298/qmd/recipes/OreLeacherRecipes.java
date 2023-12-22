package lach_01298.qmd.recipes;

import java.util.ArrayList;
import java.util.List;

import nc.recipe.BasicRecipeHandler;
import nc.recipe.ingredient.EmptyItemIngredient;
import nc.util.FluidStackHelper;

public class OreLeacherRecipes extends BasicRecipeHandler
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
				oreStack("dustIron", 3),chanceOreStack("dustChromium", 1, 60),chanceOreStack("dustManganese", 1, 5)
				);
		addRecipe("oreGold",fluidStack("nitric_acid", FluidStackHelper.NUGGET_VOLUME),
				fluidStack("hydrochloric_acid", FluidStackHelper.NUGGET_VOLUME),
				fluidStack("sulfuric_acid", FluidStackHelper.NUGGET_VOLUME),
				oreStack("dustGold", 3),chanceOreStack("dustSilver", 1, 10),new EmptyItemIngredient()
				);
		addRecipe("oreCopper",fluidStack("nitric_acid", FluidStackHelper.NUGGET_VOLUME),
				fluidStack("hydrochloric_acid", FluidStackHelper.NUGGET_VOLUME),
				fluidStack("sulfuric_acid", FluidStackHelper.NUGGET_VOLUME),
				oreStack("dustCopper", 3),chanceOreStack("dustZinc", 1, 5),chanceOreStack("dustIridium", 1, 1)
				);
		addRecipe("oreTin",fluidStack("nitric_acid", FluidStackHelper.NUGGET_VOLUME),
				fluidStack("hydrochloric_acid", FluidStackHelper.NUGGET_VOLUME),
				fluidStack("sulfuric_acid", FluidStackHelper.NUGGET_VOLUME),
				oreStack("dustTin", 3),chanceOreStack("dustZirconium", 1, 25),chanceOreStack("dustTungsten", 1, 10)
				);
		addRecipe("oreLead",fluidStack("nitric_acid", FluidStackHelper.NUGGET_VOLUME),
				fluidStack("hydrochloric_acid", FluidStackHelper.NUGGET_VOLUME),
				fluidStack("sulfuric_acid", FluidStackHelper.NUGGET_VOLUME),
				oreStack("dustLead", 3),chanceOreStack("dustNickel", 1, 10),chanceOreStack("dustCobalt", 1, 5)
				);
		addRecipe("oreUranium",fluidStack("nitric_acid", FluidStackHelper.NUGGET_VOLUME),
				fluidStack("hydrochloric_acid", FluidStackHelper.NUGGET_VOLUME),
				fluidStack("sulfuric_acid", FluidStackHelper.NUGGET_VOLUME),
				oreStack("dustUranium", 3),chanceOreStack("dustThorium", 1, 10),chanceOreStack("dustNiobium", 1, 10)
				);
		addRecipe("oreThorium",fluidStack("nitric_acid", FluidStackHelper.NUGGET_VOLUME),
				fluidStack("hydrochloric_acid", FluidStackHelper.NUGGET_VOLUME),
				fluidStack("sulfuric_acid", FluidStackHelper.NUGGET_VOLUME),
				oreStack("dustThorium", 3),chanceOreStack("dustTitanium", 1, 15),chanceOreStack("dustHafnium", 1, 10)
				);
		addRecipe("oreLithium",fluidStack("nitric_acid", FluidStackHelper.NUGGET_VOLUME),
				fluidStack("hydrochloric_acid", FluidStackHelper.NUGGET_VOLUME),
				fluidStack("sulfuric_acid", FluidStackHelper.NUGGET_VOLUME),
				oreStack("dustLithium", 3),chanceOreStack("dustAluminum", 1, 10),new EmptyItemIngredient()
				);
		addRecipe("oreMagnesium",fluidStack("nitric_acid", FluidStackHelper.NUGGET_VOLUME),
				fluidStack("hydrochloric_acid", FluidStackHelper.NUGGET_VOLUME),
				fluidStack("sulfuric_acid", FluidStackHelper.NUGGET_VOLUME),
				oreStack("dustMagnesium", 3),chanceOreStack("dustCalcium", 1, 10),chanceOreStack("dustPotassium", 1, 5)
				);
		addRecipe("oreBoron",fluidStack("nitric_acid", FluidStackHelper.NUGGET_VOLUME),
				fluidStack("hydrochloric_acid", FluidStackHelper.NUGGET_VOLUME),
				fluidStack("sulfuric_acid", FluidStackHelper.NUGGET_VOLUME),
				oreStack("dustBoron", 3),chanceOreStack("dustSodiumChloride", 2,50,1),new EmptyItemIngredient()
				);

		//other mod ores
		
		addRecipe("oreOsmium",fluidStack("nitric_acid", FluidStackHelper.NUGGET_VOLUME),
				fluidStack("hydrochloric_acid", FluidStackHelper.NUGGET_VOLUME),
				fluidStack("sulfuric_acid", FluidStackHelper.NUGGET_VOLUME),
				oreStack("dustOsmium", 3),chanceOreStack("dustPlatinum", 1,10),chanceOreStack("dustIridium", 1,10)
				);
		addRecipe("oreIridium",fluidStack("nitric_acid", FluidStackHelper.NUGGET_VOLUME),
				fluidStack("hydrochloric_acid", FluidStackHelper.NUGGET_VOLUME),
				fluidStack("sulfuric_acid", FluidStackHelper.NUGGET_VOLUME),
				oreStack("dustIridium", 3),chanceOreStack("dustPlatinum", 1,10),chanceOreStack("dustOsmium", 1,10)
				);
		addRecipe("orePlatinum",fluidStack("nitric_acid", FluidStackHelper.NUGGET_VOLUME),
				fluidStack("hydrochloric_acid", FluidStackHelper.NUGGET_VOLUME),
				fluidStack("sulfuric_acid", FluidStackHelper.NUGGET_VOLUME),
				oreStack("dustPlatinum", 3),chanceOreStack("dustIridium", 1,10),chanceOreStack("dustOsmium", 1,10)
				);
		addRecipe("oreNickel",fluidStack("nitric_acid", FluidStackHelper.NUGGET_VOLUME),
				fluidStack("hydrochloric_acid", FluidStackHelper.NUGGET_VOLUME),
				fluidStack("sulfuric_acid", FluidStackHelper.NUGGET_VOLUME),
				oreStack("dustNickel", 3),chanceOreStack("dustIron", 1,25),chanceOreStack("dustAluminum", 1,10)
				);
		addRecipe("oreTitanium",fluidStack("nitric_acid", FluidStackHelper.NUGGET_VOLUME),
				fluidStack("hydrochloric_acid", FluidStackHelper.NUGGET_VOLUME),
				fluidStack("sulfuric_acid", FluidStackHelper.NUGGET_VOLUME),
				oreStack("dustTitanium", 3),chanceOreStack("dustIron", 1,25),chanceOreStack("dustManganese", 1,10)
				);
		addRecipe("oreSilver",fluidStack("nitric_acid", FluidStackHelper.NUGGET_VOLUME),
				fluidStack("hydrochloric_acid", FluidStackHelper.NUGGET_VOLUME),
				fluidStack("sulfuric_acid", FluidStackHelper.NUGGET_VOLUME),
				oreStack("dustSilver", 3),chanceOreStack("dustLead", 1,25),new EmptyItemIngredient()
				);
		addRecipe("oreAluminum",fluidStack("nitric_acid", FluidStackHelper.NUGGET_VOLUME),
				fluidStack("hydrochloric_acid", FluidStackHelper.NUGGET_VOLUME),
				fluidStack("sulfuric_acid", FluidStackHelper.NUGGET_VOLUME),
				oreStack("dustAluminum", 3),chanceOreStack("dustIron", 1,25),new EmptyItemIngredient()
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
