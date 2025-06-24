package lach_01298.qmd.recipes;

import lach_01298.qmd.config.QMDConfig;
import lach_01298.qmd.particle.*;
import lach_01298.qmd.recipe.QMDRecipeHandler;
import nc.recipe.ingredient.EmptyFluidIngredient;

import java.util.*;

public class NucleosynthesisChamberRecipes extends QMDRecipeHandler
{
	public NucleosynthesisChamberRecipes()
	{
		super("nucleosynthesis_chamber", 0, 2, 1, 0, 2, 0);
		
	}

	@Override
	public void addRecipes()
	{
		double scaleFactor = QMDConfig.rsf_nucleosynthesis/100d;
		
		//addRecipe(inputFluid1, inputFluid2, inputParticle,  outputFluid1, outputFluid2,  maxEnergy, heatRelased)
		addRecipe(fluidStack("hydrogen", 500),fluidStack("hydrogen", 500), new ParticleStack(Particles.muon,(int) (951300*scaleFactor),0,1),fluidStack("deuterium", 500), new EmptyFluidIngredient(), 1000L, 1950000L);
		addRecipe(fluidStack("hydrogen", 500),fluidStack("deuterium", 500), new ParticleStack(Particles.muon,(int) (143100*scaleFactor),0,1),fluidStack("helium_3", 1000), new EmptyFluidIngredient(), 1000L, 5490000L);
		addRecipe(fluidStack("helium_3", 1000),fluidStack("helium_3", 1000), new ParticleStack(Particles.muon,(int) (290000*scaleFactor),0,1),fluidStack("helium", 1000), fluidStack("hydrogen", 1000), 1000L, 12900000L);

		addRecipe(fluidStack("deuterium", 500),fluidStack("tritium", 500), new ParticleStack(Particles.muon,(int) (155900*scaleFactor),0,1),fluidStack("helium", 1000), new EmptyFluidIngredient(), 1000L, 17600000L);
		addRecipe(fluidStack("tritium", 500),fluidStack("tritium", 500), new ParticleStack(Particles.muon,(int) (161600*scaleFactor),0,1),fluidStack("helium", 1000), new EmptyFluidIngredient(), 1000L, 11300000L);
		addRecipe(fluidStack("deuterium", 500),fluidStack("helium_3", 1000), new ParticleStack(Particles.muon,(int) (200600*scaleFactor),0,1),fluidStack("helium", 1000), fluidStack("hydrogen", 500), 1000L, 18400000L);
		addRecipe(fluidStack("hydrogen", 500),fluidStack("lithium_7", 144), new ParticleStack(Particles.muon,(int) (223000*scaleFactor),0,1),fluidStack("helium", 2000), new EmptyFluidIngredient(), 1000L, 17300000L);
		addRecipe(fluidStack("deuterium", 500),fluidStack("lithium_6", 144), new ParticleStack(Particles.muon,(int) (252200*scaleFactor),0,1),fluidStack("helium", 2000), new EmptyFluidIngredient(), 1000L, 22400000L);
		addRecipe(fluidStack("tritium", 500),fluidStack("helium_3", 1000), new ParticleStack(Particles.muon,(int) (210200*scaleFactor),0,1),fluidStack("helium", 1000), fluidStack("hydrogen", 500), 1000L, 12100000L);
		addRecipe(fluidStack("deuterium", 500),fluidStack("deuterium", 500), new ParticleStack(Particles.muon,(int) (151600*scaleFactor),0,1),fluidStack("tritium", 500), fluidStack("hydrogen", 500), 1000L, 4030000L);
		addRecipe(fluidStack("helium_3", 1000),fluidStack("lithium_6", 144), new ParticleStack(Particles.muon,(int) (385700*scaleFactor),0,1),fluidStack("helium", 2000), fluidStack("hydrogen", 500), 1000L, 16900000L);
		addRecipe(fluidStack("hydrogen", 500),fluidStack("boron_11", 144), new ParticleStack(Particles.muon,(int) (286700*scaleFactor),0,1),fluidStack("helium", 3000), new EmptyFluidIngredient(), 1000L, 8680000L);
		addRecipe(fluidStack("hydrogen", 500),fluidStack("lithium_6", 144), new ParticleStack(Particles.muon,(int) (222000*scaleFactor),0,1),fluidStack("helium", 1000), fluidStack("helium_3", 1000), 1000L, 4020000L);
		
		
		addRecipe(fluidStack("carbon", 100),fluidStack("hydrogen", 1000), new ParticleStack(Particles.muon,(int) (541800*scaleFactor),0,1),fluidStack("nitrogen", 500), new EmptyFluidIngredient(), 1000L, 10700000L);
		addRecipe(fluidStack("nitrogen", 500),fluidStack("hydrogen", 1000), new ParticleStack(Particles.muon,(int) (594700*scaleFactor),0,1),fluidStack("carbon", 100), fluidStack("helium", 1000), 1000L, 14000000L);
		
		addRecipe(fluidStack("helium", 1000),fluidStack("helium", 2000), new ParticleStack(Particles.muon,(int) (604600*scaleFactor),0,1),fluidStack("carbon", 100), new EmptyFluidIngredient(), 1000L, 7160000L);
		addRecipe(fluidStack("helium", 1000),fluidStack("carbon", 100), new ParticleStack(Particles.muon,(int) (635200*scaleFactor),0,1),fluidStack("oxygen", 500), new EmptyFluidIngredient(), 1000L, 4730000L);
		addRecipe(fluidStack("helium", 1000),fluidStack("oxygen", 500), new ParticleStack(Particles.muon,(int) (763800*scaleFactor),0,1),fluidStack("neon", 1000), new EmptyFluidIngredient(), 1000L, 9320000L);
		addRecipe(fluidStack("helium", 1000),fluidStack("neon", 1000), new ParticleStack(Particles.muon,(int) (880100*scaleFactor),0,1),fluidStack("magnesium", 144), new EmptyFluidIngredient(), 1000L, 6980000L);
		addRecipe(fluidStack("helium", 1000),fluidStack("magnesium", 144), new ParticleStack(Particles.muon,(int) (987300*scaleFactor),0,1),fluidStack("silicon", 144), new EmptyFluidIngredient(), 1000L, 6980000L);
		addRecipe(fluidStack("helium", 1000),fluidStack("silicon", 144), new ParticleStack(Particles.muon,(int) (1088000*scaleFactor),0,1),fluidStack("sulfur", 666), new EmptyFluidIngredient(), 1000L, 6950000L);
		addRecipe(fluidStack("helium", 1000),fluidStack("sulfur", 666), new ParticleStack(Particles.muon,(int) (1182000*scaleFactor),0,1),fluidStack("argon", 1000), new EmptyFluidIngredient(), 1000L, 6640000L);
		addRecipe(fluidStack("helium", 1000),fluidStack("argon", 1000), new ParticleStack(Particles.muon,(int) (1272000*scaleFactor),0,1),fluidStack("calcium", 144), new EmptyFluidIngredient(), 1000L, 7040000L);
		addRecipe(fluidStack("helium", 1000),fluidStack("calcium", 144), new ParticleStack(Particles.muon,(int) (2456000*scaleFactor),0,1),fluidStack("titanium", 144), new EmptyFluidIngredient(), 1000L, 18500000L);
		addRecipe(fluidStack("helium", 1000),fluidStack("titanium", 144), new ParticleStack(Particles.muon,(int) (1443000*scaleFactor),0,1),fluidStack("chromium", 144), new EmptyFluidIngredient(), 1000L, 9350000L);
		addRecipe(fluidStack("helium", 1000),fluidStack("chromium", 144), new ParticleStack(Particles.muon,(int) (1522000*scaleFactor),0,1),fluidStack("iron", 144), new EmptyFluidIngredient(), 1000L, 7610000L);
		addRecipe(fluidStack("helium", 1000),fluidStack("iron", 144), new ParticleStack(Particles.muon,(int) (1599000*scaleFactor),0,1),fluidStack("nickel", 144), new EmptyFluidIngredient(), 1000L, 6290000L);

		addRecipe(fluidStack("oxygen", 500),fluidStack("oxygen", 500), new ParticleStack(Particles.muon,(int) (2270000*scaleFactor),0,1),fluidStack("silicon", 144), fluidStack("helium", 1000), 1000L, 9590000L);
		addRecipe(fluidStack("carbon", 100),fluidStack("carbon", 100), new ParticleStack(Particles.muon,(int) (1480000*scaleFactor),0,1),fluidStack("sodium", 144), fluidStack("hydrogen", 500), 1000L, 2240000L);

		
		
		addRecipe(fluidStack("iron", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,(int) (4904000*scaleFactor),0,1),fluidStack("cobalt", 144), new EmptyFluidIngredient(), 10000L, 2530000L);
		addRecipe(fluidStack("cobalt", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,(int) (6705000*scaleFactor),0,1),fluidStack("copper", 144), new EmptyFluidIngredient(), 10000L, 3460000L);
		addRecipe(fluidStack("nickel", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,(int) (5070000*scaleFactor),0,1),fluidStack("copper", 144), new EmptyFluidIngredient(), 10000L, 2480000L);
		addRecipe(fluidStack("copper", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,(int) (1730000*scaleFactor),0,1),fluidStack("zinc", 144), new EmptyFluidIngredient(), 10000L, 798000L);
		addRecipe(fluidStack("zinc", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,(int) (19170000*scaleFactor),0,1),fluidStack("arsenic", 666), new EmptyFluidIngredient(), 10000L, 9430000L);
		addRecipe(fluidStack("arsenic", 666), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,(int) (24380000*scaleFactor),0,1),fluidStack("strontium", 144), new EmptyFluidIngredient(), 10000L, 11700000L);
		addRecipe(fluidStack("strontium", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,(int) (4030000*scaleFactor),0,1),fluidStack("strontium_90", 144), new EmptyFluidIngredient(), 10000L, 1420000L);
		addRecipe(fluidStack("zirconium", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,(int) (16280000*scaleFactor),0,1),fluidStack("molybdenum", 144), new EmptyFluidIngredient(), 10000L, 6290000L);
		addRecipe(fluidStack("molybdenum", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,(int) (16890000*scaleFactor),0,1),fluidStack("ruthenium_106", 144), new EmptyFluidIngredient(), 10000L, 6180000L);
		addRecipe(fluidStack("ruthenium_106", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,(int) (6551000*scaleFactor),0,1),fluidStack("silver", 144), new EmptyFluidIngredient(), 10000L, 2510000L);
		addRecipe(fluidStack("silver", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,(int) (24300000*scaleFactor),0,1),fluidStack("tin", 144), new EmptyFluidIngredient(), 10000L, 8960000L);
		addRecipe(fluidStack("tin", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,(int) (16100000*scaleFactor),0,1),fluidStack("iodine", 144), new EmptyFluidIngredient(), 10000L, 5280000L);
		addRecipe(fluidStack("iodine", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,(int) (23540000*scaleFactor),0,1),fluidStack("caesium_137", 144), new EmptyFluidIngredient(), 10000L, 7730000L);
		addRecipe(fluidStack("caesium_137", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,(int) (14570000*scaleFactor),0,1),fluidStack("neodymium", 144), new EmptyFluidIngredient(), 10000L, 4330000L);
		addRecipe(fluidStack("neodymium", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,(int) (22230000*scaleFactor),0,1),fluidStack("samarium", 144), new EmptyFluidIngredient(), 10000L, 6240000L);
		addRecipe(fluidStack("samarium", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,(int) (17710000*scaleFactor),0,1),fluidStack("terbium", 144), new EmptyFluidIngredient(), 10000L, 4970000L);
		addRecipe(fluidStack("europium_155", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,(int) (10200000*scaleFactor),0,1),fluidStack("terbium", 144), new EmptyFluidIngredient(), 10000L, 2900000L);
		addRecipe(fluidStack("terbium", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,(int) (23180000*scaleFactor),0,1),fluidStack("erbium", 144), new EmptyFluidIngredient(), 10000L, 6460000L);
		addRecipe(fluidStack("erbium", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,(int) (15790000*scaleFactor),0,1),fluidStack("ytterbium", 144), new EmptyFluidIngredient(), 10000L, 4140000L);
		addRecipe(fluidStack("ytterbium", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,(int) (16010000*scaleFactor),0,1),fluidStack("hafnium", 144), new EmptyFluidIngredient(), 10000L, 4020000L);
		addRecipe(fluidStack("hafnium", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,(int) (10810000*scaleFactor),0,1),fluidStack("tungsten", 144), new EmptyFluidIngredient(), 10000L, 2720000L);
		addRecipe(fluidStack("tungsten", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,(int) (21800000*scaleFactor),0,1),fluidStack("osmium", 144), new EmptyFluidIngredient(), 10000L, 5370000L);
		addRecipe(fluidStack("osmium", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,(int) (2770000*scaleFactor),0,1),fluidStack("iridium", 144), new EmptyFluidIngredient(), 10000L, 621000L);
		addRecipe(fluidStack("iridium", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,(int) (5550000*scaleFactor),0,1),fluidStack("platinum", 144), new EmptyFluidIngredient(), 10000L, 1390000L);
		addRecipe(fluidStack("platinum", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,(int) (5572000*scaleFactor),0,1),fluidStack("gold", 144), new EmptyFluidIngredient(), 10000L, 1400000L);
		addRecipe(fluidStack("gold", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,(int) (13980000*scaleFactor),0,1),fluidStack("mercury", 144), new EmptyFluidIngredient(), 10000L, 3610000L);
		addRecipe(fluidStack("mercury", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,(int) (16940000*scaleFactor),0,1),fluidStack("lead", 144), new EmptyFluidIngredient(), 10000L, 4180000L);
		addRecipe(fluidStack("lead", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,(int) (2854000*scaleFactor),0,1),fluidStack("bismuth", 144), new EmptyFluidIngredient(), 10000L, 407000L);
		addRecipe(fluidStack("bismuth", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,(int) (2859000*scaleFactor),0,1),fluidStack("polonium", 144), new EmptyFluidIngredient(), 10000L, 525000L);
		addRecipe(fluidStack("polonium", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,(int) (45830000*scaleFactor),0,1),fluidStack("radium", 144), new EmptyFluidIngredient(), 10000L, 8750000L);
		addRecipe(fluidStack("radium", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,(int) (17660000*scaleFactor),0,1),fluidStack("thorium", 144), new EmptyFluidIngredient(), 10000L, 3560000L);
		addRecipe(fluidStack("thorium", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,(int) (17830000*scaleFactor),0,1),fluidStack("uranium_238", 144), new EmptyFluidIngredient(), 10000L, 3550000L);
		addRecipe(fluidStack("uranium_238", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,(int) (12000000*scaleFactor),0,1),fluidStack("plutonium_242", 144), new EmptyFluidIngredient(), 10000L, 2390000L);
		addRecipe(fluidStack("plutonium_242", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,(int) (15090000*scaleFactor),0,1),fluidStack("curium_247", 144), new EmptyFluidIngredient(), 10000L, 2850000L);
		addRecipe(fluidStack("curium_247", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,(int) (15200000*scaleFactor),0,1),fluidStack("californium_252", 144), new EmptyFluidIngredient(), 10000L, 2880000L);

		
		
	}

	@Override
	public List fixedExtras(List extras)
	{
		List fixed = new ArrayList(2);
		fixed.add(extras.size() > 0 && extras.get(0) instanceof Long ? (long) extras.get(0) : 0L);
		fixed.add(extras.size() > 1 && extras.get(1) instanceof Long ? (long) extras.get(1) : Long.MAX_VALUE);
		return fixed;
	}
	
	
	
}
