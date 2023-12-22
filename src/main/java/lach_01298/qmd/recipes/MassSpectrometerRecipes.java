package lach_01298.qmd.recipes;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

import nc.recipe.AbstractRecipeHandler;
import nc.recipe.BasicRecipeHandler;
import nc.recipe.ingredient.EmptyFluidIngredient;
import nc.recipe.ingredient.EmptyItemIngredient;
import nc.util.FluidStackHelper;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class MassSpectrometerRecipes extends BasicRecipeHandler
{

	public MassSpectrometerRecipes()
	{
		super("mass_spectrometer", 1, 1, 4, 4);
	}

	@Override
	public void addRecipes()
	{
		
		// ores
		addRecipe("oreIron",new EmptyFluidIngredient(),
				chanceOreStack("dustChromium", 3, 40, 2),chanceOreStack("dustManganese", 1, 20),oreStack("dustIron", 4),new EmptyItemIngredient(),
				new EmptyFluidIngredient(),new EmptyFluidIngredient(),new EmptyFluidIngredient(),new EmptyFluidIngredient());
		
		addRecipe("oreGold",new EmptyFluidIngredient(),
				chanceOreStack("dustSilver", 1, 40),oreStack("dustGold", 4),new EmptyItemIngredient(),new EmptyItemIngredient(),
				new EmptyFluidIngredient(),new EmptyFluidIngredient(),new EmptyFluidIngredient(),new EmptyFluidIngredient());
		
		addRecipe("oreCopper",new EmptyFluidIngredient(),
				oreStack("dustCopper", 4),chanceOreStack("dustZinc", 1, 20),chanceOreStack("dustIridium", 1, 4),new EmptyItemIngredient(),
				new EmptyFluidIngredient(),new EmptyFluidIngredient(),new EmptyFluidIngredient(),new EmptyFluidIngredient());
		
		addRecipe("oreTin",new EmptyFluidIngredient(),
				oreStack("dustZirconium", 1),oreStack("dustTin", 4),chanceOreStack("dustTungsten", 1, 40),new EmptyItemIngredient(),
				new EmptyFluidIngredient(),new EmptyFluidIngredient(),new EmptyFluidIngredient(),new EmptyFluidIngredient());
		
		addRecipe("oreLead",new EmptyFluidIngredient(),
				chanceOreStack("dustNickel", 1, 40),chanceOreStack("dustCobalt", 1, 20),oreStack("dustLead", 4),new EmptyItemIngredient(),
				new EmptyFluidIngredient(),new EmptyFluidIngredient(),new EmptyFluidIngredient(),new EmptyFluidIngredient());
		
		addRecipe("oreUranium",new EmptyFluidIngredient(),
				chanceOreStack("dustNiobium", 1, 40),chanceOreStack("dustThorium", 1, 40),oreStack("dustUranium", 4),chanceOreStack("dustRadium", 1, 5),
				new EmptyFluidIngredient(),new EmptyFluidIngredient(),new EmptyFluidIngredient(),new EmptyFluidIngredient());
		
		addRecipe("oreThorium",new EmptyFluidIngredient(),
				chanceOreStack("dustTitanium", 1, 60),chanceOreStack("dustHafnium", 1, 40),oreStack("dustThorium", 4),new EmptyItemIngredient(),
				new EmptyFluidIngredient(),new EmptyFluidIngredient(),new EmptyFluidIngredient(),new EmptyFluidIngredient());
		
		addRecipe("oreLithium",new EmptyFluidIngredient(),
				oreStack("dustLithium", 4),chanceOreStack("dustSodium", 1, 20),chanceOreStack("dustAluminum", 1, 40),new EmptyItemIngredient(),
				new EmptyFluidIngredient(),new EmptyFluidIngredient(),new EmptyFluidIngredient(),new EmptyFluidIngredient());
		
		addRecipe("oreMagnesium",new EmptyFluidIngredient(),
				oreStack("dustMagnesium", 4),chanceOreStack("dustPotassium", 1, 20),chanceOreStack("dustCalcium", 1, 40),new EmptyItemIngredient(),
				new EmptyFluidIngredient(),new EmptyFluidIngredient(),new EmptyFluidIngredient(),new EmptyFluidIngredient());
		
		addRecipe("oreBoron",new EmptyFluidIngredient(),
				oreStack("dustBoron", 4),oreStack("dustSodium", 3),new EmptyItemIngredient(),new EmptyItemIngredient(),
				new EmptyFluidIngredient(),new EmptyFluidIngredient(),fluidStack("chlorine", 1500),new EmptyFluidIngredient());
		
		// other mod ores
		
		addRecipe("oreOsmium",new EmptyFluidIngredient(),
				oreStack("dustOsmium", 4),chanceOreStack("dustIridium", 1,40),chanceOreStack("dustPlatinum", 1, 40),new EmptyItemIngredient(),
				new EmptyFluidIngredient(),new EmptyFluidIngredient(),new EmptyFluidIngredient(),new EmptyFluidIngredient());
		
		addRecipe("oreIridium",new EmptyFluidIngredient(),
				chanceOreStack("dustOsmium", 1,40), oreStack("dustIridium", 4),chanceOreStack("dustPlatinum", 1, 40),new EmptyItemIngredient(),
				new EmptyFluidIngredient(),new EmptyFluidIngredient(),new EmptyFluidIngredient(),new EmptyFluidIngredient());
		
		addRecipe("orePlatinum",new EmptyFluidIngredient(),
				chanceOreStack("dustOsmium", 1,40),chanceOreStack("dustIridium", 1, 40),oreStack("dustPlatinum", 4),new EmptyItemIngredient(),
				new EmptyFluidIngredient(),new EmptyFluidIngredient(),new EmptyFluidIngredient(),new EmptyFluidIngredient());
		
		addRecipe("oreNickel",new EmptyFluidIngredient(),
				chanceOreStack("dustAluminum", 1,40),oreStack("dustIron", 1),oreStack("dustNickel", 4),new EmptyItemIngredient(),
				new EmptyFluidIngredient(),new EmptyFluidIngredient(),new EmptyFluidIngredient(),new EmptyFluidIngredient());
		
		addRecipe("oreTitanium",new EmptyFluidIngredient(),
				oreStack("dustTitanium", 4),chanceOreStack("dustManganese", 1,40),oreStack("dustIron", 1),new EmptyItemIngredient(),
				new EmptyFluidIngredient(),new EmptyFluidIngredient(),new EmptyFluidIngredient(),new EmptyFluidIngredient());
		
		addRecipe("oreSilver",new EmptyFluidIngredient(),
				oreStack("dustSilver", 4),oreStack("dustLead", 1),new EmptyItemIngredient(),new EmptyItemIngredient(),
				new EmptyFluidIngredient(),new EmptyFluidIngredient(),new EmptyFluidIngredient(),new EmptyFluidIngredient());
		
		addRecipe("oreAluminum",new EmptyFluidIngredient(),
				oreStack("dustAluminum", 4),oreStack("dustIron", 1),new EmptyItemIngredient(),new EmptyItemIngredient(),
				new EmptyFluidIngredient(),new EmptyFluidIngredient(),new EmptyFluidIngredient(),new EmptyFluidIngredient());
		
		// isotope separation
		
		addRecipe(oreStack("ingotUranium", 10),new EmptyFluidIngredient(),
				oreStack("ingotUranium235", 1), oreStack("ingotUranium238", 9),new EmptyItemIngredient(),new EmptyItemIngredient(),
				new EmptyFluidIngredient(),new EmptyFluidIngredient(),new EmptyFluidIngredient(),new EmptyFluidIngredient());
		
		addRecipe(oreStack("ingotBoron", 12),new EmptyFluidIngredient(),
				oreStack("ingotBoron10", 3), oreStack("ingotBoron11", 9),new EmptyItemIngredient(),new EmptyItemIngredient(),
				new EmptyFluidIngredient(),new EmptyFluidIngredient(),new EmptyFluidIngredient(),new EmptyFluidIngredient());
		
		addRecipe(oreStack("ingotLithium", 10),new EmptyFluidIngredient(),
				oreStack("ingotLithium6", 1), oreStack("ingotLithium7", 9),new EmptyItemIngredient(),new EmptyItemIngredient(),
				new EmptyFluidIngredient(),new EmptyFluidIngredient(),new EmptyFluidIngredient(),new EmptyFluidIngredient());
		
		addRecipe(oreStackList(Lists.newArrayList("ingotMagnesium", "dustMagnesium"), 9),new EmptyFluidIngredient(),
				oreStack("ingotMagnesium24", 8), oreStack("ingotMagnesium26", 1),new EmptyItemIngredient(),new EmptyItemIngredient(),
				new EmptyFluidIngredient(),new EmptyFluidIngredient(),new EmptyFluidIngredient(),new EmptyFluidIngredient());
		
		addRecipe(oreStackList(Lists.newArrayList("ingotCalcium", "dustCalcium"), 8),new EmptyFluidIngredient(),
				oreStack("ingotCalcium48", 1), new EmptyItemIngredient(),new EmptyItemIngredient(),new EmptyItemIngredient(),
				new EmptyFluidIngredient(),new EmptyFluidIngredient(),new EmptyFluidIngredient(),new EmptyFluidIngredient());
		
		
		//common materials
		
		addRecipe(oreStackList(Lists.newArrayList("sand","blockGlass"),1), new EmptyFluidIngredient(),
				new EmptyItemIngredient(),"itemSilicon", new EmptyItemIngredient(), new EmptyItemIngredient(),
				fluidStack("oxygen", 1000),new EmptyFluidIngredient(),new EmptyFluidIngredient(), new EmptyFluidIngredient());
		
		addRecipe("dustRedstone", new EmptyFluidIngredient(),
				"dustSulfur",new EmptyItemIngredient(), new EmptyItemIngredient(), new EmptyItemIngredient(),
				new EmptyFluidIngredient(),fluidStack("mercury", 144),new EmptyFluidIngredient(), new EmptyFluidIngredient());
		
		addRecipe("coal", new EmptyFluidIngredient(),
				"dustGraphite",chanceOreStack("dustSulfur",1,15), new EmptyItemIngredient(), new EmptyItemIngredient(),
				new EmptyFluidIngredient(),new EmptyFluidIngredient(),new EmptyFluidIngredient(), new EmptyFluidIngredient());
		
		addRecipe(new EmptyItemIngredient(), fluidStack("coal", FluidStackHelper.COAL_DUST_VOLUME),
				new EmptyItemIngredient(),new EmptyItemIngredient(), new EmptyItemIngredient(), new EmptyItemIngredient(),
				fluidStack("carbon", FluidStackHelper.COAL_DUST_VOLUME),fluidStack("sulfur", FluidStackHelper.GEM_VOLUME/6),new EmptyFluidIngredient(), new EmptyFluidIngredient());
		
		addRecipe("logWood", new EmptyFluidIngredient(),
				new EmptyItemIngredient(),"dustGraphite", new EmptyItemIngredient(), new EmptyItemIngredient(),
				chanceFluidStack("hydrogen", 1000, 12,10),new EmptyFluidIngredient(),chanceFluidStack("nitrogen", 1000,2,10), chanceFluidStack("oxygen", 1000,42,10));
		
		addRecipe(Items.SUGAR, new EmptyFluidIngredient(),
				new EmptyItemIngredient(),chanceOreStack("dustGraphite",1,26), new EmptyItemIngredient(), new EmptyItemIngredient(),
				chanceFluidStack("hydrogen", 1000,48,10),new EmptyFluidIngredient(),chanceFluidStack("oxygen", 1000,24,10), new EmptyFluidIngredient());
		
		addRecipe("bone", new EmptyFluidIngredient(),
				new EmptyItemIngredient(),new EmptyItemIngredient(), chanceOreStack("dustCalcium",1,23), new EmptyItemIngredient(),
				chanceFluidStack("hydrogen", 1000,4,10),chanceFluidStack("oxygen", 1000,59,10),new EmptyFluidIngredient(), new EmptyFluidIngredient());
		
		addRecipe(new ItemStack(Items.ROTTEN_FLESH), new EmptyFluidIngredient(),
				new EmptyItemIngredient(),chanceOreStack("dustGraphite",1,19), new EmptyItemIngredient(), new EmptyItemIngredient(),
				chanceFluidStack("hydrogen", 1000, 10,10),new EmptyFluidIngredient(),chanceFluidStack("nitrogen", 1000,3,10), chanceFluidStack("oxygen", 1000,65,10));
		
		addRecipe(oreStackList(Lists.newArrayList("gemQuartz", "dustQuartz"),1),new EmptyFluidIngredient(),
				new EmptyItemIngredient(), "itemSilicon",new EmptyItemIngredient(), new EmptyItemIngredient(),
				fluidStack("oxygen", 1000), new EmptyFluidIngredient(), new EmptyFluidIngredient(), new EmptyFluidIngredient());
		
		addRecipe("gemEmerald",new EmptyFluidIngredient(),
				oreStack("dustBeryllium", 3), new EmptyItemIngredient(),oreStack("itemSilicon", 6), oreStack("dustAluminum", 2),
				new EmptyFluidIngredient(), fluidStack("oxygen", 9000), new EmptyFluidIngredient(), new EmptyFluidIngredient());
		
		addRecipe("gemLapis",new EmptyFluidIngredient(),
				oreStack("dustCalcium", 4), oreStack("dustAluminium", 3),oreStack("itemSilicon", 3), oreStack("dustSulfur", 1),
				new EmptyFluidIngredient(), new EmptyFluidIngredient(), new EmptyFluidIngredient(), new EmptyFluidIngredient());
		
		// chemicals
		
		addRecipe(oreStackList(Lists.newArrayList("dustManganeseOxide", "ingotManganeseOxide"),1),new EmptyFluidIngredient(),
				new EmptyItemIngredient(), "dustManganese",new EmptyItemIngredient(),new EmptyItemIngredient(),
				fluidStack("oxygen", 500),new EmptyFluidIngredient(), new EmptyFluidIngredient(), new EmptyFluidIngredient());
		
		addRecipe(oreStackList(Lists.newArrayList("dustManganeseDioxide", "ingotManganeseDioxide"),1),new EmptyFluidIngredient(),
				new EmptyItemIngredient(), "dustManganese",new EmptyItemIngredient(),new EmptyItemIngredient(),
				fluidStack("oxygen", 1000),new EmptyFluidIngredient(), new EmptyFluidIngredient(), new EmptyFluidIngredient());
		
		addRecipe(oreStackList(Lists.newArrayList("gemRhodochrosite", "dustRhodochrosite"),1),new EmptyFluidIngredient(),
				"dustGraphite", new EmptyItemIngredient(),"dustMaganese", new EmptyItemIngredient(),
				new EmptyFluidIngredient(),fluidStack("oxygen", 1500), new EmptyFluidIngredient(), new EmptyFluidIngredient());
		
		addRecipe(oreStackList(Lists.newArrayList("gemBoronNitride", "dustBoronNitride"),1),new EmptyFluidIngredient(),
				"dustBoron", new EmptyItemIngredient(),new EmptyItemIngredient(), new EmptyItemIngredient(),
				new EmptyFluidIngredient(),fluidStack("nitrogen", 500), new EmptyFluidIngredient(), new EmptyFluidIngredient());
		
		addRecipe(oreStackList(Lists.newArrayList("gemFluorite", "dustFluorite"),1),new EmptyFluidIngredient(),
				new EmptyItemIngredient(), "dustCalcium",new EmptyItemIngredient(), new EmptyItemIngredient(),
				fluidStack("fluorine", 1000), new EmptyFluidIngredient(), new EmptyFluidIngredient(), new EmptyFluidIngredient());
		
		addRecipe(oreStackList(Lists.newArrayList("gemVilliaumite", "dustVilliaumite","dustSodiumFluoride"),1),new EmptyFluidIngredient(),
				new EmptyItemIngredient(), "dustSodium",new EmptyItemIngredient(), new EmptyItemIngredient(),
				fluidStack("fluorine", 500), new EmptyFluidIngredient(), new EmptyFluidIngredient(), new EmptyFluidIngredient());
		
		addRecipe(oreStackList(Lists.newArrayList("gemCarobbiite", "dustCarobbiite","dustPotassiumFluoride"),1),new EmptyFluidIngredient(),
				new EmptyItemIngredient(), "dustPotassium",new EmptyItemIngredient(), new EmptyItemIngredient(),
				fluidStack("fluorine", 500), new EmptyFluidIngredient(), new EmptyFluidIngredient(), new EmptyFluidIngredient());
		
		addRecipe("gemBoronArsenide",new EmptyFluidIngredient(),
				"dustBoron", "dustArsenic",new EmptyItemIngredient(), new EmptyItemIngredient(),
				new EmptyFluidIngredient(), new EmptyFluidIngredient(), new EmptyFluidIngredient(), new EmptyFluidIngredient());
		
		addRecipe("dustCalciumSulfate",new EmptyFluidIngredient(),
				new EmptyItemIngredient(), "dustSulfur","dustCalcium", new EmptyItemIngredient(),
				fluidStack("oxygen", 2000), new EmptyFluidIngredient(), new EmptyFluidIngredient(), new EmptyFluidIngredient());
		
		addRecipe("dustSodiumHydroxide",new EmptyFluidIngredient(),
				new EmptyItemIngredient(), new EmptyItemIngredient(),"dustSodium", new EmptyItemIngredient(),
				fluidStack("hydrogen", 500), fluidStack("oxygen", 500), new EmptyFluidIngredient(), new EmptyFluidIngredient());
		
		addRecipe("dustPotassiumHydroxide",new EmptyFluidIngredient(),
				new EmptyItemIngredient(), new EmptyItemIngredient(),"dustPotassium", new EmptyItemIngredient(),
				fluidStack("hydrogen", 500), fluidStack("oxygen", 500), new EmptyFluidIngredient(), new EmptyFluidIngredient());
		
		addRecipe("dustBorax",new EmptyFluidIngredient(),
				new EmptyItemIngredient(), oreStack("dustBoron",2), new EmptyItemIngredient(), oreStack("dustSodium",2),
				fluidStack("hydrogen", 10000), new EmptyFluidIngredient(), fluidStack("oxygen", 8500), new EmptyFluidIngredient());
		
		addRecipe("dustTungstenOxide", new EmptyFluidIngredient(),
				new EmptyItemIngredient(),"dustTungsten", new EmptyItemIngredient(), new EmptyItemIngredient(),
				fluidStack("oxygen", 1000),new EmptyFluidIngredient(),new EmptyFluidIngredient(), new EmptyFluidIngredient());
		
		addRecipe("dustSodiumNitrate", new EmptyFluidIngredient(),
				new EmptyItemIngredient(), new EmptyItemIngredient(),"ingotSodium", new EmptyItemIngredient(),
				fluidStack("nitrogen", 500),fluidStack("oxygen", 1500),new EmptyFluidIngredient(), new EmptyFluidIngredient());
		
		addRecipe("dustSalt", new EmptyFluidIngredient(),
				"dustSodium", new EmptyItemIngredient(),new EmptyItemIngredient(),new EmptyItemIngredient(),
				new EmptyFluidIngredient(),fluidStack("chlorine", 500), new EmptyFluidIngredient(), new EmptyFluidIngredient());
		
		addRecipe("dustCopperOxide",new EmptyFluidIngredient(),
				new EmptyItemIngredient(), "dustCopper",new EmptyItemIngredient(),new EmptyItemIngredient(),
				fluidStack("oxygen", 1000), new EmptyFluidIngredient(), new EmptyFluidIngredient(), new EmptyFluidIngredient());
		
		addRecipe("dustHafniumOxide",new EmptyFluidIngredient(),
				new EmptyItemIngredient(), "dustHafnium",new EmptyItemIngredient(),new EmptyItemIngredient(),
				fluidStack("oxygen", 1000), new EmptyFluidIngredient(), new EmptyFluidIngredient(), new EmptyFluidIngredient());
		
		addRecipe("dustZincSulfide",new EmptyFluidIngredient(),
				"dustSulfur", "dustZinc",new EmptyItemIngredient(),new EmptyItemIngredient(),
				new EmptyFluidIngredient(), new EmptyFluidIngredient(), new EmptyFluidIngredient(), new EmptyFluidIngredient());		
		
		//alloys
		addRecipe(oreStackList(Lists.newArrayList("ingotBronze","dustBronze"),4),new EmptyFluidIngredient(),
				oreStack("dustCopper",3), "dustTin", new EmptyItemIngredient(), new EmptyItemIngredient(),
				new EmptyFluidIngredient(), new EmptyFluidIngredient(), new EmptyFluidIngredient(), new EmptyFluidIngredient());
		
		addRecipe(oreStack("ingotTough",4),new EmptyFluidIngredient(),
				oreStack("dustLithium",2), "dustBoron", "dustIron", new EmptyItemIngredient(),
				new EmptyFluidIngredient(), new EmptyFluidIngredient(), new EmptyFluidIngredient(), new EmptyFluidIngredient());
		
		addRecipe(oreStack("ingotHardCarbon",2),new EmptyFluidIngredient(),
				oreStack("dustGraphite",3), new EmptyItemIngredient(), new EmptyItemIngredient(), new EmptyItemIngredient(),
				new EmptyFluidIngredient(), new EmptyFluidIngredient(), new EmptyFluidIngredient(), new EmptyFluidIngredient());
		
		addRecipe(oreStack("ingotMagnesiumDiboride",3),new EmptyFluidIngredient(),
				oreStack("dustBoron",2), "dustMagnesium", new EmptyItemIngredient(), new EmptyItemIngredient(),
				new EmptyFluidIngredient(), new EmptyFluidIngredient(), new EmptyFluidIngredient(), new EmptyFluidIngredient());
		
		addRecipe(oreStack("ingotLithiumManganeseDioxide",2),new EmptyFluidIngredient(),
				"dustLithium", new EmptyItemIngredient(), "dustManganese", new EmptyItemIngredient(),
				new EmptyFluidIngredient(), fluidStack("oxygen", 1000), new EmptyFluidIngredient(), new EmptyFluidIngredient());
		
		addRecipe(oreStack("ingotFerroBoron",2),new EmptyFluidIngredient(),
				"dustBoron", "dustIron", new EmptyItemIngredient(), new EmptyItemIngredient(),
				new EmptyFluidIngredient(),new EmptyFluidIngredient(), new EmptyFluidIngredient(), new EmptyFluidIngredient());
		
		addRecipe(oreStack("ingotShibuichi",4),new EmptyFluidIngredient(),
				oreStack("dustCopper",3), "dustSilver", new EmptyItemIngredient(), new EmptyItemIngredient(),
				new EmptyFluidIngredient(),new EmptyFluidIngredient(), new EmptyFluidIngredient(), new EmptyFluidIngredient());
		
		addRecipe(oreStack("ingotTinSilver",4),new EmptyFluidIngredient(),
				"dustSilver", oreStack("dustTin",3), new EmptyItemIngredient(), new EmptyItemIngredient(),
				new EmptyFluidIngredient(),new EmptyFluidIngredient(), new EmptyFluidIngredient(), new EmptyFluidIngredient());
		
		addRecipe(oreStack("ingotLeadPlatinum",4),new EmptyFluidIngredient(),
				"dustPlatinum", oreStack("dustLead",3), new EmptyItemIngredient(), new EmptyItemIngredient(),
				new EmptyFluidIngredient(),new EmptyFluidIngredient(), new EmptyFluidIngredient(), new EmptyFluidIngredient());
		
		addRecipe(oreStack("ingotExtreme",8),new EmptyFluidIngredient(),
				oreStack("dustLithium",4), oreStack("dustboron",2), oreStack("dustGraphite",3), oreStackList(Lists.newArrayList("dustIron","ingotIron"),2),
				new EmptyFluidIngredient(), new EmptyFluidIngredient(), new EmptyFluidIngredient(), new EmptyFluidIngredient());
		
		addRecipe(oreStack("ingotZircaloy",8),new EmptyFluidIngredient(),
				oreStack("dustZirconium",7), "dustTin", new EmptyItemIngredient(), new EmptyItemIngredient(),
				new EmptyFluidIngredient(),new EmptyFluidIngredient(), new EmptyFluidIngredient(), new EmptyFluidIngredient());
		
		addRecipe(oreStack("ingotSiliconCarbide",2),new EmptyFluidIngredient(),
				"dustGraphite", "itemSilicon", new EmptyItemIngredient(), new EmptyItemIngredient(),
				new EmptyFluidIngredient(),new EmptyFluidIngredient(), new EmptyFluidIngredient(), new EmptyFluidIngredient());
				
		addRecipe(oreStack("ingotHSLASteel",32),new EmptyFluidIngredient(),
				"dustGraphite", "dustManganese", oreStack("dustIron",30), new EmptyItemIngredient(),
				new EmptyFluidIngredient(),new EmptyFluidIngredient(), new EmptyFluidIngredient(), new EmptyFluidIngredient());
			
		addRecipe(oreStack("ingotZirconiumMolybdenum",16),new EmptyFluidIngredient(),
				oreStack("dustZirconium",1), oreStack("dustMolybdenum",15), new EmptyItemIngredient(), new EmptyItemIngredient(),
				new EmptyFluidIngredient(),new EmptyFluidIngredient(), new EmptyFluidIngredient(), new EmptyFluidIngredient());
		
		addRecipe(oreStack("ingotTungstenCarbide",2),new EmptyFluidIngredient(),
				"dustGraphite", "dustTungsten", new EmptyItemIngredient(), new EmptyItemIngredient(),
				new EmptyFluidIngredient(),new EmptyFluidIngredient(), new EmptyFluidIngredient(), new EmptyFluidIngredient());
		
		addRecipe(oreStack("ingotNiobiumTin",4),new EmptyFluidIngredient(),
				oreStack("dustNiobium",3), "dustTin", new EmptyItemIngredient(), new EmptyItemIngredient(),
				new EmptyFluidIngredient(),new EmptyFluidIngredient(), new EmptyFluidIngredient(), new EmptyFluidIngredient());
		
		addRecipe(oreStack("ingotStainlessSteel",6),new EmptyFluidIngredient(),
				"dustChromium", oreStack("dustIron",5), new EmptyItemIngredient(), new EmptyItemIngredient(),
				new EmptyFluidIngredient(),new EmptyFluidIngredient(), new EmptyFluidIngredient(), new EmptyFluidIngredient());
		
		addRecipe(oreStack("ingotNiobiumTitanium",2),new EmptyFluidIngredient(),
				"dustTitanium", "dustNiobium", new EmptyItemIngredient(), new EmptyItemIngredient(),
				new EmptyFluidIngredient(),new EmptyFluidIngredient(), new EmptyFluidIngredient(), new EmptyFluidIngredient());
		
		addRecipe(oreStack("ingotOsmiridium",2),new EmptyFluidIngredient(),
				"dustOsmium", "dustIridium", new EmptyItemIngredient(), new EmptyItemIngredient(),
				new EmptyFluidIngredient(),new EmptyFluidIngredient(), new EmptyFluidIngredient(), new EmptyFluidIngredient());
		
		addRecipe(oreStack("ingotNichrome",2),new EmptyFluidIngredient(),
				"dustChromium", "dustNickel", new EmptyItemIngredient(), new EmptyItemIngredient(),
				new EmptyFluidIngredient(),new EmptyFluidIngredient(), new EmptyFluidIngredient(), new EmptyFluidIngredient());
		
		addRecipe(oreStack("ingotSuperAlloy",6),new EmptyFluidIngredient(),
				"dustTitanium", oreStack("dustChromium",2), oreStack("dustNickel",2), "niobium",
				new EmptyFluidIngredient(),new EmptyFluidIngredient(), new EmptyFluidIngredient(), new EmptyFluidIngredient());
		
		addRecipe(oreStack("ingotElectrum",2),new EmptyFluidIngredient(),
				"dustSilver", "dustGold", new EmptyItemIngredient(), new EmptyItemIngredient(),
				new EmptyFluidIngredient(),new EmptyFluidIngredient(), new EmptyFluidIngredient(), new EmptyFluidIngredient());
		
		addRecipe(oreStack("ingotInvar",3),new EmptyFluidIngredient(),
				"dustNickel", "dustIron", new EmptyItemIngredient(), new EmptyItemIngredient(),
				new EmptyFluidIngredient(),new EmptyFluidIngredient(), new EmptyFluidIngredient(), new EmptyFluidIngredient());
		
		addRecipe(oreStack("ingotConstantan",2),new EmptyFluidIngredient(),
				"dustNickel", "dustCopper", new EmptyItemIngredient(), new EmptyItemIngredient(),
				new EmptyFluidIngredient(),new EmptyFluidIngredient(), new EmptyFluidIngredient(), new EmptyFluidIngredient());
		
		// chemical fluids
		
		addRecipe(new EmptyItemIngredient(),fluidStack("water", FluidStackHelper.BUCKET_VOLUME),
				new EmptyItemIngredient(), new EmptyItemIngredient(), new EmptyItemIngredient(), new EmptyItemIngredient(),
				fluidStack("hydrogen", 990),fluidStack("deuterium", 10), fluidStack("oxygen", FluidStackHelper.BUCKET_VOLUME/2), new EmptyFluidIngredient());
		
		addRecipe(new EmptyItemIngredient(),fluidStack("heavy_water", FluidStackHelper.BUCKET_VOLUME),
				new EmptyItemIngredient(), new EmptyItemIngredient(), new EmptyItemIngredient(), new EmptyItemIngredient(),
				fluidStack("deuterium", FluidStackHelper.BUCKET_VOLUME),fluidStack("oxygen", FluidStackHelper.BUCKET_VOLUME/2), new EmptyFluidIngredient(), new EmptyFluidIngredient());
		
		addRecipe(new EmptyItemIngredient(),fluidStack("ethanol", FluidStackHelper.BUCKET_VOLUME),
				new EmptyItemIngredient(), oreStack("dustGraphite",2), new EmptyItemIngredient(), new EmptyItemIngredient(),
				fluidStack("hydrogen", FluidStackHelper.BUCKET_VOLUME*3),new EmptyFluidIngredient(), fluidStack("oxygen", FluidStackHelper.BUCKET_VOLUME/2), new EmptyFluidIngredient());
		
		addRecipe(new EmptyItemIngredient(),fluidStack("methanol", FluidStackHelper.BUCKET_VOLUME),
				new EmptyItemIngredient(), oreStack("dustGraphite",1), new EmptyItemIngredient(), new EmptyItemIngredient(),
				fluidStack("hydrogen", FluidStackHelper.BUCKET_VOLUME*2),new EmptyFluidIngredient(), fluidStack("oxygen", FluidStackHelper.BUCKET_VOLUME/2), new EmptyFluidIngredient());
		
		addRecipe(new EmptyItemIngredient(),fluidStack("carbon_dioxide", FluidStackHelper.BUCKET_VOLUME),
				"dustGraphite", new EmptyItemIngredient(), new EmptyItemIngredient(), new EmptyItemIngredient(),
				new EmptyFluidIngredient(),fluidStack("oxygen", FluidStackHelper.BUCKET_VOLUME), new EmptyFluidIngredient(), new EmptyFluidIngredient());
		
		addRecipe(new EmptyItemIngredient(),fluidStack("carbon_monoxide", FluidStackHelper.BUCKET_VOLUME),
				"dustGraphite", new EmptyItemIngredient(), new EmptyItemIngredient(), new EmptyItemIngredient(),
				new EmptyFluidIngredient(),fluidStack("oxygen", FluidStackHelper.BUCKET_VOLUME/2), new EmptyFluidIngredient(), new EmptyFluidIngredient());
		
		addRecipe(new EmptyItemIngredient(),fluidStack("ethene", FluidStackHelper.BUCKET_VOLUME),
				new EmptyItemIngredient(), oreStack("dustGraphite",2), new EmptyItemIngredient(), new EmptyItemIngredient(),
				fluidStack("hydrogen", FluidStackHelper.BUCKET_VOLUME*2),new EmptyFluidIngredient(), new EmptyFluidIngredient(), new EmptyFluidIngredient());
		
		addRecipe(new EmptyItemIngredient(),fluidStack("fluoromethane", FluidStackHelper.BUCKET_VOLUME),
				new EmptyItemIngredient(), oreStack("dustGraphite",1), new EmptyItemIngredient(), new EmptyItemIngredient(),
				fluidStack("hydrogen", FluidStackHelper.BUCKET_VOLUME*3/2),new EmptyFluidIngredient(), fluidStack("fluorine", FluidStackHelper.BUCKET_VOLUME/2), new EmptyFluidIngredient());
		
		addRecipe(new EmptyItemIngredient(),fluidStack("oxygen_difluoride", FluidStackHelper.BUCKET_VOLUME),
				new EmptyItemIngredient(), new EmptyItemIngredient(), new EmptyItemIngredient(), new EmptyItemIngredient(),
				fluidStack("oxygen", FluidStackHelper.BUCKET_VOLUME/2), fluidStack("fluorine", FluidStackHelper.BUCKET_VOLUME), new EmptyFluidIngredient(), new EmptyFluidIngredient());
		
		addRecipe(new EmptyItemIngredient(),fluidStack("oxygen_difluoride", FluidStackHelper.BUCKET_VOLUME),
				new EmptyItemIngredient(), new EmptyItemIngredient(), new EmptyItemIngredient(), new EmptyItemIngredient(),
				fluidStack("oxygen", FluidStackHelper.BUCKET_VOLUME/2), fluidStack("fluorine", FluidStackHelper.BUCKET_VOLUME), new EmptyFluidIngredient(), new EmptyFluidIngredient());
		
		addRecipe(new EmptyItemIngredient(),fluidStack("diborane", FluidStackHelper.BUCKET_VOLUME),
				new EmptyItemIngredient(), oreStack("dustBoron",2), new EmptyItemIngredient(), new EmptyItemIngredient(),
				fluidStack("hydrogen", FluidStackHelper.BUCKET_VOLUME*3), new EmptyFluidIngredient(), new EmptyFluidIngredient(), new EmptyFluidIngredient());
		
		addRecipe(new EmptyItemIngredient(),fluidStack("sulfur_dioxide", FluidStackHelper.BUCKET_VOLUME),
				new EmptyItemIngredient(), oreStack("dustSulfur",1), new EmptyItemIngredient(), new EmptyItemIngredient(),
				fluidStack("oxygen", FluidStackHelper.BUCKET_VOLUME), new EmptyFluidIngredient(), new EmptyFluidIngredient(), new EmptyFluidIngredient());
		
		addRecipe(new EmptyItemIngredient(),fluidStack("sulfur_trioxide", FluidStackHelper.BUCKET_VOLUME),
				new EmptyItemIngredient(), oreStack("dustSulfur",1), new EmptyItemIngredient(), new EmptyItemIngredient(),
				fluidStack("oxygen", FluidStackHelper.BUCKET_VOLUME*3/2), new EmptyFluidIngredient(), new EmptyFluidIngredient(), new EmptyFluidIngredient());
		
		addRecipe(new EmptyItemIngredient(),fluidStack("hydrofluoric_acid", FluidStackHelper.BUCKET_VOLUME),
				new EmptyItemIngredient(), new EmptyItemIngredient(), new EmptyItemIngredient(), new EmptyItemIngredient(),
				fluidStack("hydrogen", FluidStackHelper.BUCKET_VOLUME/2), fluidStack("fluorine", FluidStackHelper.BUCKET_VOLUME/2), new EmptyFluidIngredient(), new EmptyFluidIngredient());
		
		addRecipe(new EmptyItemIngredient(),fluidStack("boric_acid", FluidStackHelper.BUCKET_VOLUME),
				new EmptyItemIngredient(), oreStack("dustBoron",1), new EmptyItemIngredient(), new EmptyItemIngredient(),
				fluidStack("hydrogen", FluidStackHelper.BUCKET_VOLUME*3/2), new EmptyFluidIngredient(), fluidStack("oxygen", FluidStackHelper.BUCKET_VOLUME*3/2), new EmptyFluidIngredient());
		
		addRecipe(new EmptyItemIngredient(),fluidStack("sulfuric_acid", FluidStackHelper.BUCKET_VOLUME),
				new EmptyItemIngredient(), new EmptyItemIngredient(), oreStack("dustSulfur",1), new EmptyItemIngredient(),
				fluidStack("hydrogen", FluidStackHelper.BUCKET_VOLUME),fluidStack("oxygen", FluidStackHelper.BUCKET_VOLUME*2), new EmptyFluidIngredient(),  new EmptyFluidIngredient());
		
		addRecipe(new EmptyItemIngredient(),fluidStack("hydrochloric_acid", FluidStackHelper.BUCKET_VOLUME),
				new EmptyItemIngredient(), new EmptyItemIngredient(), new EmptyItemIngredient(), new EmptyItemIngredient(),
				fluidStack("hydrogen", FluidStackHelper.BUCKET_VOLUME/2), fluidStack("chlorine", FluidStackHelper.BUCKET_VOLUME/2), new EmptyFluidIngredient(), new EmptyFluidIngredient());
		
		addRecipe(new EmptyItemIngredient(),fluidStack("nitric_acid", FluidStackHelper.BUCKET_VOLUME),
				new EmptyItemIngredient(), new EmptyItemIngredient(), new EmptyItemIngredient(), new EmptyItemIngredient(),
				fluidStack("hydrogen", FluidStackHelper.BUCKET_VOLUME/2), fluidStack("nitrogen", FluidStackHelper.BUCKET_VOLUME/2), fluidStack("oxygen", FluidStackHelper.BUCKET_VOLUME*3/2), new EmptyFluidIngredient());
		
		addRecipe(new EmptyItemIngredient(),fluidStack("nitric_oxide", FluidStackHelper.BUCKET_VOLUME),
				new EmptyItemIngredient(), new EmptyItemIngredient(), new EmptyItemIngredient(), new EmptyItemIngredient(),
				fluidStack("nitrogen", FluidStackHelper.BUCKET_VOLUME/2), fluidStack("oxygen", FluidStackHelper.BUCKET_VOLUME/2), new EmptyFluidIngredient(), new EmptyFluidIngredient());
		
		addRecipe(new EmptyItemIngredient(),fluidStack("nitrogen_dioxide", FluidStackHelper.BUCKET_VOLUME),
				new EmptyItemIngredient(), new EmptyItemIngredient(), new EmptyItemIngredient(), new EmptyItemIngredient(),
				fluidStack("nitrogen", FluidStackHelper.BUCKET_VOLUME/2), fluidStack("oxygen", FluidStackHelper.BUCKET_VOLUME), new EmptyFluidIngredient(), new EmptyFluidIngredient());
		
		
		
		
	}

	@Override
	public List fixExtras(List extras)
	{
		List fixed = new ArrayList(1);
		fixed.add(extras.size() > 0 && extras.get(0) instanceof Double ? (double) extras.get(0) : 1D);
		return fixed;
	}

}
