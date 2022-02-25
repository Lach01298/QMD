package lach_01298.qmd.recipes;

import java.util.ArrayList;
import java.util.List;

import lach_01298.qmd.particle.ParticleStack;
import lach_01298.qmd.particle.Particles;
import lach_01298.qmd.recipe.QMDRecipeHandler;
import nc.recipe.ingredient.EmptyFluidIngredient;

public class NucleosynthesisChamberRecipes extends QMDRecipeHandler
{
	public NucleosynthesisChamberRecipes()
	{
		super("nucleosynthesis_chamber", 0, 2, 1, 0, 2, 0);
		
	}

	@Override
	public void addRecipes()
	{
		//addRecipe(inputFluid1, inputFluid2, inputParticle,  outputFluid1, outputFluid2,  maxEnergy, heatRelased)
		addRecipe(fluidStack("hydrogen", 1000),fluidStack("hydrogen", 1000), new ParticleStack(Particles.muon,951300,0,1),fluidStack("deuterium", 1000), new EmptyFluidIngredient(), 1000L, 1442L);
		addRecipe(fluidStack("hydrogen", 1000),fluidStack("deuterium", 1000), new ParticleStack(Particles.muon,143100,0,1),fluidStack("helium_3", 1000), new EmptyFluidIngredient(), 1000L, 5493L);
		addRecipe(fluidStack("helium_3", 1000),fluidStack("helium_3", 1000), new ParticleStack(Particles.muon,290000,0,1),fluidStack("helium", 1000), fluidStack("hydrogen", 2000), 1000L, 12859L);

		addRecipe(fluidStack("deuterium", 1000),fluidStack("tritium", 1000), new ParticleStack(Particles.muon,155900,0,1),fluidStack("helium", 1000), new EmptyFluidIngredient(), 1000L, 17600L);
		addRecipe(fluidStack("tritium", 1000),fluidStack("tritium", 1000), new ParticleStack(Particles.muon,161600,0,1),fluidStack("helium", 1000), new EmptyFluidIngredient(), 1000L, 11300L);
		addRecipe(fluidStack("deuterium", 1000),fluidStack("helium_3", 1000), new ParticleStack(Particles.muon,200600,0,1),fluidStack("helium", 1000), fluidStack("hydrogen", 1000), 1000L, 18300L);
		addRecipe(fluidStack("hydrogen", 1000),fluidStack("lithium_7", 144), new ParticleStack(Particles.muon,223000,0,1),fluidStack("helium", 2000), new EmptyFluidIngredient(), 1000L, 17200L);
		addRecipe(fluidStack("deuterium", 1000),fluidStack("lithium_6", 144), new ParticleStack(Particles.muon,252200,0,1),fluidStack("helium", 2000), new EmptyFluidIngredient(), 1000L, 22400L);
		addRecipe(fluidStack("tritium", 1000),fluidStack("helium_3", 1000), new ParticleStack(Particles.muon,210200,0,1),fluidStack("helium", 1000), fluidStack("hydrogen", 1000), 1000L, 12100L);
		addRecipe(fluidStack("deuterium", 1000),fluidStack("deuterium", 1000), new ParticleStack(Particles.muon,151600,0,1),fluidStack("tritium", 1000), fluidStack("hydrogen", 1000), 1000L, 4030L);
		addRecipe(fluidStack("helium_3", 1000),fluidStack("lithium_6", 144), new ParticleStack(Particles.muon,385700,0,1),fluidStack("helium", 2000), fluidStack("hydrogen", 1000), 1000L, 16900L);
		addRecipe(fluidStack("hydrogen", 1000),fluidStack("boron_11", 144), new ParticleStack(Particles.muon,286700,0,1),fluidStack("helium", 3000), new EmptyFluidIngredient(), 1000L, 8700L);
		addRecipe(fluidStack("hydrogen", 1000),fluidStack("lithium_6", 144), new ParticleStack(Particles.muon,222000,0,1),fluidStack("helium", 1000), fluidStack("helium_3", 1000), 1000L, 4000L);
		
		
		addRecipe(fluidStack("coal", 100),fluidStack("hydrogen", 2000), new ParticleStack(Particles.muon,5418000,0,1),fluidStack("nitrogen", 1000), new EmptyFluidIngredient(), 1000L, 10690L);
		addRecipe(fluidStack("nitrogen", 1000),fluidStack("hydrogen", 2000), new ParticleStack(Particles.muon,594700,0,1),fluidStack("coal", 100), fluidStack("helium", 1000), 1000L, 14040L);
		
		addRecipe(fluidStack("helium", 1000),fluidStack("helium", 2000), new ParticleStack(Particles.muon,604600,0,1),fluidStack("coal", 100), new EmptyFluidIngredient(), 1000L, 7160L);
		addRecipe(fluidStack("helium", 1000),fluidStack("coal", 100), new ParticleStack(Particles.muon,635200,0,1),fluidStack("oxygen", 1000), new EmptyFluidIngredient(), 1000L, 4730L);
		addRecipe(fluidStack("helium", 1000),fluidStack("oxygen", 1000), new ParticleStack(Particles.muon,763800,0,1),fluidStack("neon", 1000), new EmptyFluidIngredient(), 1000L, 9320L);
		addRecipe(fluidStack("helium", 1000),fluidStack("neon", 1000), new ParticleStack(Particles.muon,880100,0,1),fluidStack("magnesium", 144), new EmptyFluidIngredient(), 1000L, 6980L);
		addRecipe(fluidStack("helium", 1000),fluidStack("magnesium", 144), new ParticleStack(Particles.muon,987300,0,1),fluidStack("silicon", 144), new EmptyFluidIngredient(), 1000L, 6980L);
		addRecipe(fluidStack("helium", 1000),fluidStack("silicon", 144), new ParticleStack(Particles.muon,1088000,0,1),fluidStack("sulfur", 666), new EmptyFluidIngredient(), 1000L, 6950L);
		addRecipe(fluidStack("helium", 1000),fluidStack("sulfur", 666), new ParticleStack(Particles.muon,1182000,0,1),fluidStack("argon", 1000), new EmptyFluidIngredient(), 1000L, 6640L);
		addRecipe(fluidStack("helium", 1000),fluidStack("argon", 1000), new ParticleStack(Particles.muon,1272000,0,1),fluidStack("calcium", 144), new EmptyFluidIngredient(), 1000L, 7040L);
		addRecipe(fluidStack("helium", 1000),fluidStack("calcium", 144), new ParticleStack(Particles.muon,2456000,0,1),fluidStack("titanium", 144), new EmptyFluidIngredient(), 1000L, 12830L);
		addRecipe(fluidStack("helium", 1000),fluidStack("titanium", 144), new ParticleStack(Particles.muon,1443000,0,1),fluidStack("chromium", 144), new EmptyFluidIngredient(), 1000L, 7940L);
		addRecipe(fluidStack("helium", 1000),fluidStack("chromium", 144), new ParticleStack(Particles.muon,1522000,0,1),fluidStack("iron", 144), new EmptyFluidIngredient(), 1000L, 8000L);
		addRecipe(fluidStack("helium", 1000),fluidStack("iron", 144), new ParticleStack(Particles.muon,1599000,0,1),fluidStack("nickel", 144), new EmptyFluidIngredient(), 1000L, 7830L);

		addRecipe(fluidStack("oxygen", 1000),fluidStack("oxygen", 1000), new ParticleStack(Particles.muon,2270000,0,1),fluidStack("silicon", 144), fluidStack("helium", 1000), 1000L, 9593L);
		addRecipe(fluidStack("coal", 100),fluidStack("coal", 100), new ParticleStack(Particles.muon,1480000,0,1),fluidStack("sodium", 144), fluidStack("hydrogen", 1000), 1000L, 2241L);

		
		
		addRecipe(fluidStack("iron", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,4904000,0,1),fluidStack("cobalt", 144), new EmptyFluidIngredient(), 10000L, 2530L);
		addRecipe(fluidStack("cobalt", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,6705000,0,1),fluidStack("copper", 144), new EmptyFluidIngredient(), 10000L, 3460L);
		addRecipe(fluidStack("nickel", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,5070000,0,1),fluidStack("copper", 144), new EmptyFluidIngredient(), 10000L, 2480L);
		addRecipe(fluidStack("copper", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,1730000,0,1),fluidStack("zinc", 144), new EmptyFluidIngredient(), 10000L, 798L);
		addRecipe(fluidStack("zinc", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,19170000,0,1),fluidStack("arsenic", 666), new EmptyFluidIngredient(), 10000L, 9430L);
		addRecipe(fluidStack("arsenic", 666), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,24380000,0,1),fluidStack("strontium", 144), new EmptyFluidIngredient(), 10000L, 11700L);
		addRecipe(fluidStack("strontium", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,4030000,0,1),fluidStack("strontium_90", 144), new EmptyFluidIngredient(), 10000L, 1420L);
		addRecipe(fluidStack("zirconium", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,16280000,0,1),fluidStack("molybdenum", 144), new EmptyFluidIngredient(), 10000L, 6290L);
		addRecipe(fluidStack("molybdenum", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,16890000,0,1),fluidStack("ruthenium_106", 144), new EmptyFluidIngredient(), 10000L, 6180L);
		addRecipe(fluidStack("ruthenium_106", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,6551000,0,1),fluidStack("silver", 144), new EmptyFluidIngredient(), 10000L, 2510L);
		addRecipe(fluidStack("silver", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,24300000,0,1),fluidStack("tin", 144), new EmptyFluidIngredient(), 10000L, 8960L);
		addRecipe(fluidStack("tin", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,16100000,0,1),fluidStack("iodine", 144), new EmptyFluidIngredient(), 10000L, 5280L);
		addRecipe(fluidStack("iodine", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,23540000,0,1),fluidStack("caesium_137", 144), new EmptyFluidIngredient(), 10000L, 7730L);
		addRecipe(fluidStack("caesium_137", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,14570000,0,1),fluidStack("neodymium", 144), new EmptyFluidIngredient(), 10000L, 4330L);
		addRecipe(fluidStack("neodymium", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,22230000,0,1),fluidStack("samarium", 144), new EmptyFluidIngredient(), 10000L, 6240L);
		addRecipe(fluidStack("samarium", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,17710000,0,1),fluidStack("terbium", 144), new EmptyFluidIngredient(), 10000L, 4970L);
		addRecipe(fluidStack("europium_155", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,10200000,0,1),fluidStack("terbium", 144), new EmptyFluidIngredient(), 10000L, 2900L);
		addRecipe(fluidStack("terbium", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,23180000,0,1),fluidStack("erbium", 144), new EmptyFluidIngredient(), 10000L, 6460L);
		addRecipe(fluidStack("erbium", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,15790000,0,1),fluidStack("ytterbium", 144), new EmptyFluidIngredient(), 10000L, 4140L);
		addRecipe(fluidStack("ytterbium", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,16010000,0,1),fluidStack("hafnium", 144), new EmptyFluidIngredient(), 10000L, 4020L);
		addRecipe(fluidStack("hafnium", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,10810000,0,1),fluidStack("tungsten", 144), new EmptyFluidIngredient(), 10000L, 2720L);
		addRecipe(fluidStack("tungsten", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,21800000,0,1),fluidStack("osmium", 144), new EmptyFluidIngredient(), 10000L, 5370L);
		addRecipe(fluidStack("osmium", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,2770000,0,1),fluidStack("iridium", 144), new EmptyFluidIngredient(), 10000L, 621L);
		addRecipe(fluidStack("iridium", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,5550000,0,1),fluidStack("platinum", 144), new EmptyFluidIngredient(), 10000L, 1390L);
		addRecipe(fluidStack("platinum", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,5572000,0,1),fluidStack("gold", 144), new EmptyFluidIngredient(), 10000L, 1400L);
		addRecipe(fluidStack("gold", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,13980000,0,1),fluidStack("mercury", 144), new EmptyFluidIngredient(), 10000L, 3610L);
		addRecipe(fluidStack("mercury", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,16940000,0,1),fluidStack("lead", 144), new EmptyFluidIngredient(), 10000L, 4180L);
		addRecipe(fluidStack("lead", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,2854000,0,1),fluidStack("bismuth", 144), new EmptyFluidIngredient(), 10000L, 407L);
		addRecipe(fluidStack("bismuth", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,2859000,0,1),fluidStack("polonium", 144), new EmptyFluidIngredient(), 10000L, 525L);
		addRecipe(fluidStack("polonium", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,45830000,0,1),fluidStack("radium", 144), new EmptyFluidIngredient(), 10000L, 8750L);
		addRecipe(fluidStack("radium", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,17660000,0,1),fluidStack("thorium", 144), new EmptyFluidIngredient(), 10000L, 3560L);
		addRecipe(fluidStack("thorium", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,17830000,0,1),fluidStack("uranium_238", 144), new EmptyFluidIngredient(), 10000L, 3550L);
		addRecipe(fluidStack("uranium_238", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,12000000,0,1),fluidStack("plutonium_242", 144), new EmptyFluidIngredient(), 10000L, 2390L);
		addRecipe(fluidStack("plutonium_242", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,15090000,0,1),fluidStack("curium_247", 144), new EmptyFluidIngredient(), 10000L, 2850L);
		addRecipe(fluidStack("curium_247", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,15200000,0,1),fluidStack("californium_252", 144), new EmptyFluidIngredient(), 10000L, 2880L);

		
		
	}

	@Override
	public List fixExtras(List extras)
	{
		List fixed = new ArrayList(2);
		fixed.add(extras.size() > 0 && extras.get(0) instanceof Long ? (long) extras.get(0) : 0L);
		fixed.add(extras.size() > 1 && extras.get(1) instanceof Long ? (long) extras.get(1) : Long.MAX_VALUE);
		return fixed;
	}
	
	
	
}
