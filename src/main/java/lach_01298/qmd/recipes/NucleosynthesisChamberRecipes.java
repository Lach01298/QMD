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
		//addRecipe(inputFluid1, inputFluid2, inputParticle,  outputFluid1, outputFluid2,  heatRelased,  maxEnergy)
		addRecipe(fluidStack("hydrogen", 100),fluidStack("hydrogen", 100), new ParticleStack(Particles.muon,41710,0,1),fluidStack("deuterium", 100), new EmptyFluidIngredient(), 1442L, 1000L);
		addRecipe(fluidStack("hydrogen", 100),fluidStack("deuterium", 100), new ParticleStack(Particles.muon,817,0,1),fluidStack("helium_3", 100), new EmptyFluidIngredient(), 5493L, 1000L);
		addRecipe(fluidStack("helium_3", 100),fluidStack("helium_3", 100), new ParticleStack(Particles.muon,4899,0,1),fluidStack("helium", 100), fluidStack("hydrogen", 200), 12860L, 1000L);

		addRecipe(fluidStack("deuterium", 100),fluidStack("tritium", 100), new ParticleStack(Particles.muon,1095,0,1),fluidStack("helium", 100), new EmptyFluidIngredient(), 17600L, 1000L);
		addRecipe(fluidStack("tritium", 100),fluidStack("tritium", 100), new ParticleStack(Particles.muon,1225,0,1),fluidStack("helium", 100), new EmptyFluidIngredient(), 11300L, 1000L);
		addRecipe(fluidStack("deuterium", 100),fluidStack("helium_3", 100), new ParticleStack(Particles.muon,2191,0,1),fluidStack("helium", 100), fluidStack("hydrogen", 100), 18300L, 1000L);
		addRecipe(fluidStack("hydrogen", 100),fluidStack("lithium_7", 100), new ParticleStack(Particles.muon,2806,0,1),fluidStack("helium", 200), new EmptyFluidIngredient(), 17200L, 1000L);
		addRecipe(fluidStack("deuterium", 100),fluidStack("lithium_6", 100), new ParticleStack(Particles.muon,3674,0,1),fluidStack("helium", 200), new EmptyFluidIngredient(), 22400L, 1000L);
		addRecipe(fluidStack("tritium", 100),fluidStack("helium_3", 100), new ParticleStack(Particles.muon,2449,0,1),fluidStack("helium", 100), fluidStack("hydrogen", 100), 12100L, 1000L);
		addRecipe(fluidStack("deuterium", 100),fluidStack("deuterium", 100), new ParticleStack(Particles.muon,1000,0,1),fluidStack("tritium", 100), fluidStack("hydrogen", 100), 4030L, 1000L);
		addRecipe(fluidStack("helium_3", 100),fluidStack("lithium_6", 100), new ParticleStack(Particles.muon,8485,0,1),fluidStack("helium", 200), fluidStack("hydrogen", 100), 16900L, 1000L);
		addRecipe(fluidStack("hydrogen", 100),fluidStack("boron_11", 100), new ParticleStack(Particles.muon,4787,0,1),fluidStack("helium", 300), new EmptyFluidIngredient(), 8700L, 1000L);
		addRecipe(fluidStack("hydrogen", 100),fluidStack("lithium_6", 100), new ParticleStack(Particles.muon,2777,0,1),fluidStack("helium", 100), fluidStack("helium_3", 100), 4000L, 1000L);
		
		
		addRecipe(fluidStack("coal", 100),fluidStack("hydrogen", 200), new ParticleStack(Particles.muon,15710,0,1),fluidStack("nitrogen", 100), new EmptyFluidIngredient(), 10690L, 1000L);
		addRecipe(fluidStack("nitrogen", 100),fluidStack("hydrogen", 200), new ParticleStack(Particles.muon,18520,0,1),fluidStack("coal", 100), fluidStack("helium", 100), 14040L, 1000L);
		
		addRecipe(fluidStack("helium", 100),fluidStack("helium", 200), new ParticleStack(Particles.muon,19060,0,1),fluidStack("coal", 100), new EmptyFluidIngredient(), 7160L, 1000L);
		addRecipe(fluidStack("helium", 100),fluidStack("coal", 100), new ParticleStack(Particles.muon,20780,0,1),fluidStack("oxygen", 100), new EmptyFluidIngredient(), 4730L, 1000L);
		addRecipe(fluidStack("helium", 100),fluidStack("oxygen", 100), new ParticleStack(Particles.muon,28620,0,1),fluidStack("neon", 100), new EmptyFluidIngredient(), 9320L, 1000L);
		addRecipe(fluidStack("helium", 100),fluidStack("neon", 100), new ParticleStack(Particles.muon,36510,0,1),fluidStack("magnesium", 100), new EmptyFluidIngredient(), 6980L, 1000L);
		addRecipe(fluidStack("helium", 100),fluidStack("magnesium", 100), new ParticleStack(Particles.muon,44440,0,1),fluidStack("silicon", 100), new EmptyFluidIngredient(), 6980L, 1000L);
		addRecipe(fluidStack("helium", 100),fluidStack("silicon", 100), new ParticleStack(Particles.muon,52380,0,1),fluidStack("sulfur", 100), new EmptyFluidIngredient(), 6950L, 1000L);
		addRecipe(fluidStack("helium", 100),fluidStack("sulfur", 100), new ParticleStack(Particles.muon,60340,0,1),fluidStack("argon", 100), new EmptyFluidIngredient(), 6640L, 1000L);
		addRecipe(fluidStack("helium", 100),fluidStack("argon", 100), new ParticleStack(Particles.muon,68310,0,1),fluidStack("calcium", 100), new EmptyFluidIngredient(), 7040L, 1000L);
		addRecipe(fluidStack("helium", 100),fluidStack("calcium", 100), new ParticleStack(Particles.muon,206600,0,1),fluidStack("titanium", 100), new EmptyFluidIngredient(), 12830L, 1000L);
		addRecipe(fluidStack("helium", 100),fluidStack("titanium", 100), new ParticleStack(Particles.muon,84550,0,1),fluidStack("chromium", 100), new EmptyFluidIngredient(), 7940L, 1000L);
		addRecipe(fluidStack("helium", 100),fluidStack("chromium", 100), new ParticleStack(Particles.muon,92510,0,1),fluidStack("iron", 100), new EmptyFluidIngredient(), 8000L, 1000L);
		addRecipe(fluidStack("helium", 100),fluidStack("iron", 100), new ParticleStack(Particles.muon,100500,0,1),fluidStack("nickel", 100), new EmptyFluidIngredient(), 7830L, 1000L);

		addRecipe(fluidStack("oxygen", 100),fluidStack("oxygen", 100), new ParticleStack(Particles.muon,181000,0,1),fluidStack("silicon", 100), fluidStack("helium", 100), 9593L, 1000L);
		addRecipe(fluidStack("coal", 100),fluidStack("coal", 100), new ParticleStack(Particles.muon,88180,0,1),fluidStack("sodium", 100), fluidStack("hydrogen", 100), 2241L, 1000L);

		
		
		addRecipe(fluidStack("iron", 1000), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,1213000,0,1),fluidStack("cobalt", 1000), new EmptyFluidIngredient(), 25300L, 10000L);
		addRecipe(fluidStack("cobalt", 1000), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,1638000,0,1),fluidStack("copper", 1000), new EmptyFluidIngredient(), 34600L, 10000L);
		addRecipe(fluidStack("nickel", 1000), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,1233000,0,1),fluidStack("copper", 1000), new EmptyFluidIngredient(), 24800L, 10000L);
		addRecipe(fluidStack("copper", 1000), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,415900,0,1),fluidStack("zinc", 1000), new EmptyFluidIngredient(), 7980L, 10000L);
		addRecipe(fluidStack("zinc", 1000), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,4592000,0,1),fluidStack("arsenic", 1000), new EmptyFluidIngredient(), 94300L, 10000L);
		addRecipe(fluidStack("arsenic", 1000), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,5630000,0,1),fluidStack("strontium", 1000), new EmptyFluidIngredient(), 117000L, 10000L);
		addRecipe(fluidStack("strontium", 1000), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,897700,0,1),fluidStack("strontium_90", 1000), new EmptyFluidIngredient(), 14200L, 10000L);
		addRecipe(fluidStack("zirconium", 1000), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,3609000,0,1),fluidStack("molybdenum", 1000), new EmptyFluidIngredient(), 62900L, 10000L);
		addRecipe(fluidStack("molybdenum", 1000), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,3676000,0,1),fluidStack("ruthenium_106", 1000), new EmptyFluidIngredient(), 61800L, 10000L);
		addRecipe(fluidStack("ruthenium_106", 1000), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,1402000,0,1),fluidStack("silver", 1000), new EmptyFluidIngredient(), 25100L, 10000L);
		addRecipe(fluidStack("silver", 1000), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,5171000,0,1),fluidStack("tin", 1000), new EmptyFluidIngredient(), 89600L, 10000L);
		addRecipe(fluidStack("tin", 1000), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,3357000,0,1),fluidStack("iodine", 1000), new EmptyFluidIngredient(), 52800L, 10000L);
		addRecipe(fluidStack("iodine", 1000), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,4852000,0,1),fluidStack("caesium_137", 1000), new EmptyFluidIngredient(), 77300L, 10000L);
		addRecipe(fluidStack("caesium_137", 1000), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,2956000,0,1),fluidStack("neodymium", 1000), new EmptyFluidIngredient(), 43300L, 10000L);
		addRecipe(fluidStack("neodymium", 1000), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,4473000,0,1),fluidStack("samarium", 1000), new EmptyFluidIngredient(), 62400L, 10000L);
		addRecipe(fluidStack("samarium", 1000), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,3521000,0,1),fluidStack("terbium", 1000), new EmptyFluidIngredient(), 49700L, 10000L);
		addRecipe(fluidStack("europium_155", 1000), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,2020000,0,1),fluidStack("terbium", 1000), new EmptyFluidIngredient(), 29000L, 10000L);
		addRecipe(fluidStack("terbium", 1000), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,4568000,0,1),fluidStack("erbium", 1000), new EmptyFluidIngredient(), 64600L, 10000L);
		addRecipe(fluidStack("erbium", 1000), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,3078000,0,1),fluidStack("ytterbium", 1000), new EmptyFluidIngredient(), 41400L, 10000L);
		addRecipe(fluidStack("ytterbium", 1000), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,3099000,0,1),fluidStack("hafnium", 1000), new EmptyFluidIngredient(), 40200L, 10000L);
		addRecipe(fluidStack("hafnium", 1000), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,2079000,0,1),fluidStack("tungsten", 1000), new EmptyFluidIngredient(), 27200L, 10000L);
		addRecipe(fluidStack("tungsten", 1000), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,4176000,0,1),fluidStack("osmium", 1000), new EmptyFluidIngredient(), 53700L, 10000L);
		addRecipe(fluidStack("osmium", 1000), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,526300,0,1),fluidStack("iridium", 1000), new EmptyFluidIngredient(), 6210L, 10000L);
		addRecipe(fluidStack("iridium", 1000), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,1054000,0,1),fluidStack("platinum", 1000), new EmptyFluidIngredient(), 13900L, 10000L);
		addRecipe(fluidStack("platinum", 1000), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,1056000,0,1),fluidStack("gold", 1000), new EmptyFluidIngredient(), 14000L, 10000L);
		addRecipe(fluidStack("gold", 1000), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,2644000,0,1),fluidStack("mercury", 1000), new EmptyFluidIngredient(), 36100L, 10000L);
		addRecipe(fluidStack("mercury", 1000), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,3188000,0,1),fluidStack("lead", 1000), new EmptyFluidIngredient(), 41800L, 10000L);
		addRecipe(fluidStack("lead", 1000), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,534200,0,1),fluidStack("bismuth", 1000), new EmptyFluidIngredient(), 4070L, 10000L);
		addRecipe(fluidStack("bismuth", 1000), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,534700,0,1),fluidStack("polonium", 1000), new EmptyFluidIngredient(), 5250L, 10000L);
		addRecipe(fluidStack("polonium", 1000), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,8563000,0,1),fluidStack("radium", 1000), new EmptyFluidIngredient(), 87500L, 10000L);
		addRecipe(fluidStack("radium", 1000), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,3255000,0,1),fluidStack("thorium", 1000), new EmptyFluidIngredient(), 35600L, 10000L);
		addRecipe(fluidStack("thorium", 1000), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,3271000,0,1),fluidStack("uranium_238", 1000), new EmptyFluidIngredient(), 35500L, 10000L);
		addRecipe(fluidStack("uranium_238", 1000), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,2191000,0,1),fluidStack("plutonium_242", 1000), new EmptyFluidIngredient(), 23900L, 10000L);
		addRecipe(fluidStack("plutonium_242", 1000), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,2747000,0,1),fluidStack("curium_247", 1000), new EmptyFluidIngredient(), 28500L, 10000L);
		addRecipe(fluidStack("curium_247", 1000), new EmptyFluidIngredient(), new ParticleStack(Particles.neutron,2757000,0,1),fluidStack("californium_252", 1000), new EmptyFluidIngredient(), 28800L, 10000L);

		
		
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
