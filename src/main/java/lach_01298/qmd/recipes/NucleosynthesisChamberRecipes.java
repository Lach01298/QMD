package lach_01298.qmd.recipes;

import lach_01298.qmd.config.QMDConfig;
import lach_01298.qmd.particle.*;
import lach_01298.qmd.recipe.QMDRecipeHandler;
import nc.recipe.BasicRecipeHandler;
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
		// muon catalysed fusion
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
		addRecipe(fluidStack("helium", 2000),fluidStack("titanium", 144), new ParticleStack(Particles.muon,(int) (1443000*scaleFactor),0,1),fluidStack("chromium", 144), new EmptyFluidIngredient(), 1000L, 9350000L);
		addRecipe(fluidStack("helium", 1000),fluidStack("chromium", 144), new ParticleStack(Particles.muon,(int) (1522000*scaleFactor),0,1),fluidStack("iron", 144), new EmptyFluidIngredient(), 1000L, 7610000L);

		addRecipe(fluidStack("oxygen", 500),fluidStack("oxygen", 500), new ParticleStack(Particles.muon,(int) (2270000*scaleFactor),0,1),fluidStack("silicon", 144), fluidStack("helium", 1000), 1000L, 9590000L);
		addRecipe(fluidStack("carbon", 100),fluidStack("carbon", 100), new ParticleStack(Particles.muon,(int) (1480000*scaleFactor),0,1),fluidStack("sodium", 144), fluidStack("hydrogen", 500), 1000L, 2240000L);
		addRecipe(fluidStack("neon", 1000),fluidStack("neon", 1000), new ParticleStack(Particles.muon,(int) (3168000*scaleFactor),0,1),fluidStack("magnesium", 144), fluidStack("oxygen", 500), 1000L, 4590000L);
		
		// r-Process
		addRecipe(fluidStack("iron", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,(int) (15410000*scaleFactor),0,1),fluidStack("copper", 144), new EmptyFluidIngredient(), 10000L, 7930000L);
		addRecipe(fluidStack("cobalt", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,(int) (10150000*scaleFactor),0,1),fluidStack("copper", 144), new EmptyFluidIngredient(), 10000L, 5350000L);
		addRecipe(fluidStack("nickel", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,(int) (16990000*scaleFactor),0,1),fluidStack("zinc", 144), new EmptyFluidIngredient(), 10000L, 9000000L);
		addRecipe(fluidStack("copper", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,(int) (20010000*scaleFactor),0,1),fluidStack("arsenic", 666), new EmptyFluidIngredient(), 10000L, 10400000L);
		addRecipe(fluidStack("zinc", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,(int) (18270000*scaleFactor),0,1),fluidStack("arsenic", 666), new EmptyFluidIngredient(), 10000L, 9580000L);
		addRecipe(fluidStack("arsenic", 666), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,(int) (20800000*scaleFactor),0,1),fluidStack("strontium", 144), new EmptyFluidIngredient(), 10000L, 12000000L);
		addRecipe(fluidStack("strontium", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,(int) (6157000*scaleFactor),0,1),fluidStack("zirconium", 144), new EmptyFluidIngredient(), 10000L, 3280000L);
		addRecipe(fluidStack("strontium_90", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,(int) (12250000*scaleFactor),0,1),fluidStack("molybdenum", 144), new EmptyFluidIngredient(), 10000L, 6670000L);
		addRecipe(fluidStack("yttrium", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,(int) (4605000*scaleFactor),0,1),fluidStack("zirconium", 144), new EmptyFluidIngredient(), 10000L, 2500000L);
		addRecipe(fluidStack("zirconium", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,(int) (24490000*scaleFactor),0,1),fluidStack("ruthenium_106", 144), new EmptyFluidIngredient(), 10000L, 12700000L);
		addRecipe(fluidStack("niobium", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,(int) (7591000*scaleFactor),0,1),fluidStack("molybdenum", 144), new EmptyFluidIngredient(), 10000L, 4130000L);
		addRecipe(fluidStack("molybdenum", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,(int) (11990000*scaleFactor),0,1),fluidStack("ruthenium_106", 144), new EmptyFluidIngredient(), 10000L, 6280000L);
		addRecipe(fluidStack("ruthenium_106", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,(int) (4406000*scaleFactor),0,1),fluidStack("silver", 144), new EmptyFluidIngredient(), 10000L, 2660000L);
		addRecipe(fluidStack("palladium", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,(int) (20560000*scaleFactor),0,1),fluidStack("tin", 144), new EmptyFluidIngredient(), 10000L, 11400000L);
		addRecipe(fluidStack("silver", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,(int) (29300000*scaleFactor),0,1),fluidStack("iodine", 144), new EmptyFluidIngredient(), 10000L, 16200000L);
		addRecipe(fluidStack("tin", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,(int) (9953000*scaleFactor),0,1),fluidStack("iodine", 144), new EmptyFluidIngredient(), 10000L, 5440000L);
		addRecipe(fluidStack("iodine", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,(int) (14010000*scaleFactor),0,1),fluidStack("caesium_137", 144), new EmptyFluidIngredient(), 10000L, 7830000L);
		addRecipe(fluidStack("caesium_137", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,(int) (20590000*scaleFactor),0,1),fluidStack("samarium", 144), new EmptyFluidIngredient(), 10000L, 10900000L);
		addRecipe(fluidStack("barium", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,(int) (12330000*scaleFactor),0,1),fluidStack("promethium_147", 144), new EmptyFluidIngredient(), 10000L, 6340000L);
		addRecipe(fluidStack("neodymium", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,(int) (17670000*scaleFactor),0,1),fluidStack("europium_155", 144), new EmptyFluidIngredient(), 10000L, 9080000L);
		addRecipe(fluidStack("promethium_147", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,(int) (14810000*scaleFactor),0,1),fluidStack("gadolinium", 144), new EmptyFluidIngredient(), 10000L, 8040000L);
		addRecipe(fluidStack("samarium", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,(int) (8004000*scaleFactor),0,1),fluidStack("gadolinium", 144), new EmptyFluidIngredient(), 10000L, 4430000L);
		addRecipe(fluidStack("europium_155", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,(int) (11940000*scaleFactor),0,1),fluidStack("dysprosium", 144), new EmptyFluidIngredient(), 10000L, 6680000L);
		addRecipe(fluidStack("gadolinium", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,(int) (9238000*scaleFactor),0,1),fluidStack("holmium", 144), new EmptyFluidIngredient(), 10000L, 5070000L);
		addRecipe(fluidStack("dysprosium", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,(int) (5224000*scaleFactor),0,1),fluidStack("erbium", 144), new EmptyFluidIngredient(), 10000L, 2930000L);
		addRecipe(fluidStack("holmium", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,(int) (16950000*scaleFactor),0,1),fluidStack("hafnium", 144), new EmptyFluidIngredient(), 10000L, 9250000L);
		addRecipe(fluidStack("erbium", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,(int) (18220000*scaleFactor),0,1),fluidStack("hafnium", 144), new EmptyFluidIngredient(), 10000L, 9790000L);
		addRecipe(fluidStack("ytterbium", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,(int) (12840000*scaleFactor),0,1),fluidStack("tungsten", 144), new EmptyFluidIngredient(), 10000L, 6950000L);
		addRecipe(fluidStack("hafnium", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,(int) (12720000*scaleFactor),0,1),fluidStack("osmium", 144), new EmptyFluidIngredient(), 10000L, 6960000L);
		addRecipe(fluidStack("tungsten", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,(int) (13910000*scaleFactor),0,1),fluidStack("platinum", 144), new EmptyFluidIngredient(), 10000L, 7590000L);
		addRecipe(fluidStack("osmium", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,(int) (6244000*scaleFactor),0,1),fluidStack("gold", 144), new EmptyFluidIngredient(), 10000L, 3560000L);
		addRecipe(fluidStack("iridium", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,(int) (4987000*scaleFactor),0,1),fluidStack("gold", 144), new EmptyFluidIngredient(), 10000L, 2890000L);
		addRecipe(fluidStack("platinum", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,(int) (16160000*scaleFactor),0,1),fluidStack("lead", 144), new EmptyFluidIngredient(), 10000L, 9390000L);
		addRecipe(fluidStack("gold", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,(int) (13630000*scaleFactor),0,1),fluidStack("lead", 144), new EmptyFluidIngredient(), 10000L, 7940000L);
		addRecipe(fluidStack("mercury", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,(int) (8613000*scaleFactor),0,1),fluidStack("bismuth", 144), new EmptyFluidIngredient(), 10000L, 4740000L);
		addRecipe(fluidStack("lead", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,(int) (1220000*scaleFactor),0,1),fluidStack("bismuth", 144), new EmptyFluidIngredient(), 10000L, 458000L);
		addRecipe(fluidStack("bismuth", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,(int) (1218000*scaleFactor),0,1),fluidStack("polonium", 144), new EmptyFluidIngredient(), 10000L, 577000L);
		addRecipe(fluidStack("polonium", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,(int) (19460000*scaleFactor),0,1),fluidStack("radium", 144), new EmptyFluidIngredient(), 10000L, 8950000L);
		addRecipe(fluidStack("radium", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,(int) (7142000*scaleFactor),0,1),fluidStack("thorium", 144), new EmptyFluidIngredient(), 10000L, 3660000L);
		addRecipe(fluidStack("thorium", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,(int) (7086000*scaleFactor),0,1),fluidStack("uranium_238", 144), new EmptyFluidIngredient(), 10000L, 3660000L);
		addRecipe(fluidStack("uranium_238", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,(int) (4688000*scaleFactor),0,1),fluidStack("plutonium_242", 144), new EmptyFluidIngredient(), 10000L, 2490000L);
		addRecipe(fluidStack("plutonium_242", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,(int) (5831000*scaleFactor),0,1),fluidStack("curium_247", 144), new EmptyFluidIngredient(), 10000L, 2950000L);
		addRecipe(fluidStack("curium_247", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,(int) (5795000*scaleFactor),0,1),fluidStack("californium_252", 144), new EmptyFluidIngredient(), 10000L, 2990000L);


		// p-Process
		addRecipe(fluidStack("iron", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.proton,(int) (2702000*scaleFactor),7080,1),fluidStack("nickel", 144), new EmptyFluidIngredient(), 17100L, 1420000L);
		addRecipe(fluidStack("cobalt", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.proton,(int) (7213000*scaleFactor),7900,1),fluidStack("zinc", 144), new EmptyFluidIngredient(), 17900L, 3920000L);
		addRecipe(fluidStack("arsenic", 666), new EmptyFluidIngredient(), new ParticleStack(Particles.proton,(int) (14070000*scaleFactor),9660,1),fluidStack("strontium", 144), new EmptyFluidIngredient(), 19700L, 7120000L);
		addRecipe(fluidStack("strontium", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.proton,(int) (9115000*scaleFactor),9810,1),fluidStack("molybdenum", 144), new EmptyFluidIngredient(), 19800L, 4320000L);
		addRecipe(fluidStack("strontium_90", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.proton,(int) (4338000*scaleFactor),9150,1),fluidStack("niobium", 144), new EmptyFluidIngredient(), 19200L, 2310000L);
		addRecipe(fluidStack("yttrium", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.proton,(int) (7574000*scaleFactor),9810,1),fluidStack("molybdenum", 144), new EmptyFluidIngredient(), 19800L, 3610000L);
		addRecipe(fluidStack("zirconium", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.proton,(int) (6043000*scaleFactor),9810,1),fluidStack("molybdenum", 144), new EmptyFluidIngredient(), 19800L, 2780000L);
		addRecipe(fluidStack("molybdenum", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.proton,(int) (14270000*scaleFactor),11000,1),fluidStack("silver", 144), new EmptyFluidIngredient(), 21000L, 6380000L);
		addRecipe(fluidStack("ruthenium_106", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.proton,(int) (15860000*scaleFactor),11400,1),fluidStack("tin", 144), new EmptyFluidIngredient(), 21400L, 7610000L);
		addRecipe(fluidStack("palladium", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.proton,(int) (16220000*scaleFactor),11800,1),fluidStack("tin", 144), new EmptyFluidIngredient(), 21800L, 7140000L);
		addRecipe(fluidStack("silver", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.proton,(int) (14560000*scaleFactor),11800,1),fluidStack("tin", 144), new EmptyFluidIngredient(), 21800L, 6570000L);
		addRecipe(fluidStack("tin", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.proton,(int) (19540000*scaleFactor),12600,1),fluidStack("barium", 144), new EmptyFluidIngredient(), 22600L, 8170000L);
		addRecipe(fluidStack("iodine", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.proton,(int) (7706000*scaleFactor),11800,1),fluidStack("barium", 144), new EmptyFluidIngredient(), 21800L, 3490000L);
		addRecipe(fluidStack("caesium_137", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.proton,(int) (7599000*scaleFactor),11900,1),fluidStack("neodymium", 144), new EmptyFluidIngredient(), 21900L, 3580000L);
		addRecipe(fluidStack("barium", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.proton,(int) (6067000*scaleFactor),11900,1),fluidStack("neodymium", 144), new EmptyFluidIngredient(), 21900L, 2680000L);
		addRecipe(fluidStack("neodymium", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.proton,(int) (3059000*scaleFactor),12300,1),fluidStack("samarium", 144), new EmptyFluidIngredient(), 22300L, 1060000L);
		addRecipe(fluidStack("promethium_147", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.proton,(int) (14410000*scaleFactor),13600,1),fluidStack("dysprosium", 144), new EmptyFluidIngredient(), 23600L, 5500000L);
		addRecipe(fluidStack("samarium", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.proton,(int) (6133000*scaleFactor),12800,1),fluidStack("dysprosium", 144), new EmptyFluidIngredient(), 22800L, 2490000L);
		addRecipe(fluidStack("europium_155", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.proton,(int) (17690000*scaleFactor),14100,1),fluidStack("erbium", 144), new EmptyFluidIngredient(), 24100L, 7020000L);
		addRecipe(fluidStack("gadolinium", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.proton,(int) (12600000*scaleFactor),13700,1),fluidStack("erbium", 144), new EmptyFluidIngredient(), 23700L, 5050000L);
		addRecipe(fluidStack("dysprosium", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.proton,(int) (9307000*scaleFactor),13600,1),fluidStack("ytterbium", 144), new EmptyFluidIngredient(), 23600L, 3750000L);
		addRecipe(fluidStack("holmium", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.proton,(int) (7741000*scaleFactor),13600,1),fluidStack("ytterbium", 144), new EmptyFluidIngredient(), 23600L, 3130000L);
		addRecipe(fluidStack("erbium", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.proton,(int) (6182000*scaleFactor),13600,1),fluidStack("ytterbium", 144), new EmptyFluidIngredient(), 23600L, 2400000L);
		addRecipe(fluidStack("ytterbium", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.proton,(int) (15920000*scaleFactor),14800,1),fluidStack("osmium", 144), new EmptyFluidIngredient(), 24800L, 5810000L);
		addRecipe(fluidStack("hafnium", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.proton,(int) (15880000*scaleFactor),15000,1),fluidStack("platinum", 144), new EmptyFluidIngredient(), 25000L, 5840000L);
		addRecipe(fluidStack("tungsten", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.proton,(int) (10960000*scaleFactor),14800,1),fluidStack("iridium", 144), new EmptyFluidIngredient(), 24800L, 4000000L);
		addRecipe(fluidStack("osmium", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.proton,(int) (10880000*scaleFactor),15000,1),fluidStack("mercury", 144), new EmptyFluidIngredient(), 25000L, 4320000L);
		addRecipe(fluidStack("iridium", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.proton,(int) (9309000*scaleFactor),15000,1),fluidStack("mercury", 144), new EmptyFluidIngredient(), 25000L, 3720000L);
		addRecipe(fluidStack("platinum", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.proton,(int) (6147000*scaleFactor),14800,1),fluidStack("mercury", 144), new EmptyFluidIngredient(), 24800L, 2490000L);
		addRecipe(fluidStack("gold", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.proton,(int) (6156000*scaleFactor),15000,1),fluidStack("mercury", 144), new EmptyFluidIngredient(), 25000L, 2410000L);
		addRecipe(fluidStack("mercury", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.proton,(int) (6121000*scaleFactor),15000,1),fluidStack("lead", 144), new EmptyFluidIngredient(), 25000L, 2460000L);
		addRecipe(fluidStack("lead", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.proton,(int) (3023000*scaleFactor),14900,1),fluidStack("polonium", 144), new EmptyFluidIngredient(), 24900L, 878000L);
		addRecipe(fluidStack("bismuth", 144), new EmptyFluidIngredient(), new ParticleStack(Particles.proton,(int) (1509000*scaleFactor),14900,1),fluidStack("polonium", 144), new EmptyFluidIngredient(), 24900L, 498000L);

	}

	@Override
	public List fixedExtras(List extras)
	{
		BasicRecipeHandler.ExtrasFixer fixer = new BasicRecipeHandler.ExtrasFixer(extras);
		fixer.add(Long.class, 0L); 		// max energy
		fixer.add(Long.class, Long.MAX_VALUE);		//  heat released

		return fixer.fixed;
	}
	
	
	
}
