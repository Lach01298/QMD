package lach_01298.qmd.recipe;

import java.util.ArrayList;

import lach_01298.qmd.MaterialEnums;
import lach_01298.qmd.item.QMDItems;
import lach_01298.qmd.particle.ParticleStack;
import lach_01298.qmd.particle.Particles;
import lach_01298.qmd.recipe.ingredient.EmptyParticleIngredient;
import nc.enumm.MetaEnums;
import nc.init.NCItems;
import net.minecraft.item.ItemStack;

public class TargetChamberRecipes extends QMDRecipeHandler
{

	public TargetChamberRecipes()
	{
		super("target_chamber", 1, 0, 1, 1, 0, 3);
		
	}

	@Override
	public void addRecipes()
	{
		//addRecipe(item input, particle input, item output,particle output +, particle output neutral, particle -)
		
		addRecipe("ingotBeryllium",
				new ParticleStack(Particles.proton,1000,147000,6.5,100),
				"ingotLithium6",
						new ParticleStack(Particles.alpha,2125,34),
						new EmptyParticleIngredient(),
						new EmptyParticleIngredient()
				);
//		addRecipe("ingotBeryllium",
//				new ParticleStack(Particles.proton,29000,500000000,0.068966,100),
//				"ingotBoron10",
//				new EmptyParticleIngredient(),
//						new ParticleStack(Particles.photon,6585,1),
//						new EmptyParticleIngredient()
//				);
		addRecipe("ingotBoron10",
				new ParticleStack(Particles.proton,4000,166000,0.05,100),
				"ingotBeryllium7",
				new ParticleStack(Particles.alpha,1144,30),
						new EmptyParticleIngredient(),
						new EmptyParticleIngredient()
				);
		addRecipe("ingotBoron11",
				new ParticleStack(Particles.proton,15000,227000000,0.1333,100),
				"charcoal",
				new EmptyParticleIngredient(),
				new ParticleStack(Particles.photon,15956,1),
						new EmptyParticleIngredient()
				);
//		addRecipe("charcoal",
//				new ParticleStack(Particles.proton,45000,833000,0.1111,100),
//				"ingotBeryllium7",
//				new ParticleStack(Particles.alpha,-8756,6),
//				new ParticleStack(Particles.neutron,-8756,6),
//				new ParticleStack(Particles.proton,-8756,6)
//				);
		addRecipe("charcoal",
				new ParticleStack(Particles.proton,15000,178000,0.1333,100),
				"ingotBeryllium",
				new ParticleStack(Particles.alpha,-7551,28),
						new EmptyParticleIngredient(),
						new EmptyParticleIngredient()
				);
		addRecipe("ingotSodium",
				new ParticleStack(Particles.proton,24000,178000,0.08333,100),
				"ingotSodium22",
				new ParticleStack(Particles.proton,-6209,28),
				new ParticleStack(Particles.neutron,-6209,28),
						new EmptyParticleIngredient()
				);
		addRecipe("ingotMagnesium24",
				new ParticleStack(Particles.proton,35000,357000,0.4285,100),
				"ingotSodium22",
				new ParticleStack(Particles.proton,-12056,56),
				new ParticleStack(Particles.neutron,-12056,28),
						new EmptyParticleIngredient()
				);
		addRecipe("ingotAluminum",
				new ParticleStack(Particles.proton,45000,1000000,0.1111,100),
				"ingotSodium22",
				new ParticleStack(Particles.alpha,-7504,5),
				new ParticleStack(Particles.neutron,-7504,5),
				new ParticleStack(Particles.proton,-7504,5)
				);
		addRecipe("ingotManganese",
				new ParticleStack(Particles.proton,10000,3700000,0.5,100),
				"ingotIron",
				new EmptyParticleIngredient(),
				new ParticleStack(Particles.photon,10183,2),
				new EmptyParticleIngredient()
				);
		addRecipe("ingotUranium238",
				new ParticleStack(Particles.proton,110000,185000,0.09091,100),
				"dustProtactinium233",
				new ParticleStack(Particles.alpha,-1459,27),
				new ParticleStack(Particles.neutron,-1459,54),
				new EmptyParticleIngredient()
				);
//		addRecipe("ingotUranium238",
//				new ParticleStack(Particles.proton,19000,113000,0.2105,100),
//				"ingotNeptunium236",
//				new ParticleStack(Particles.triton,-12996,44),
//				new EmptyParticleIngredient(),
//				new EmptyParticleIngredient()
//				);
		addRecipe("ingotUranium235",
				new ParticleStack(Particles.proton,65000,454000,0.1538,100),
				"ingotUranium233",
				new ParticleStack(Particles.triton,-3660,11),
				new EmptyParticleIngredient(),
				new EmptyParticleIngredient()
				);
		
		
		addRecipe("ingotBeryllium",
				new ParticleStack(Particles.deuteron,4000,166000,0.25,100),
				"ingotLithium7",
				new ParticleStack(Particles.alpha,7152,30),
				new EmptyParticleIngredient(),
				new EmptyParticleIngredient()
				);
		addRecipe("ingotBoron11",
				new ParticleStack(Particles.deuteron,2000,208000,1,100),
				"charcoal",
				new EmptyParticleIngredient(),
				new ParticleStack(Particles.neutron,13732,24),
				new EmptyParticleIngredient()
				);
		addRecipe("charcoal",
				new ParticleStack(Particles.deuteron,5000,178000,2,100),
				"ingotBoron10",
				new ParticleStack(Particles.alpha,-1339,28),
				new EmptyParticleIngredient(),
				new EmptyParticleIngredient()
				);
		addRecipe("ingotMagnesium24",
				new ParticleStack(Particles.deuteron,8000,277000,0.25,100),
				"ingotSodium22",
				new ParticleStack(Particles.alpha,1958,18),
				new EmptyParticleIngredient(),
				new EmptyParticleIngredient()
				);
		addRecipe("ingotPlutonium242",
				new ParticleStack(Particles.deuteron,11000,200000,0.4545,100),
				"ingotAmericium242",
				new EmptyParticleIngredient(),
				new ParticleStack(Particles.neutron,-1879,50),
				new EmptyParticleIngredient()
				);
		addRecipe("ingotUranium238",
				new ParticleStack(Particles.deuteron,15000,333000,0.2,100),
				"ingotUranium239",
				new ParticleStack(Particles.proton,2581,15),
				new EmptyParticleIngredient(),
				new EmptyParticleIngredient()
				);
		
		
		addRecipe("ingotMagnesium26",
				new ParticleStack(Particles.photon,25000,263000,0.2,100),
				"ingotMagnesium24",
				new EmptyParticleIngredient(),
				new ParticleStack(Particles.neutron,-9212,38),
				new EmptyParticleIngredient()
				);
		addRecipe("itemSilicon",
				new ParticleStack(Particles.photon,19500,125000,0.05128,100),
				"ingotAluminum",
				new ParticleStack(Particles.proton,-11585,40),
				new EmptyParticleIngredient(),
				new EmptyParticleIngredient()
				);
		
		
		addRecipe("ingotPlutonium242",
				new ParticleStack(Particles.neutron,10000,41600,0.2,100),
				"ingotPlutonium241",
				new EmptyParticleIngredient(),
				new ParticleStack(Particles.neutron,-4000,240),
				new EmptyParticleIngredient()
				);
		addRecipe("ingotNeptunium237",
				new ParticleStack(Particles.neutron,12000,74600,0.1666,100),
				"ingotNeptunium236",
				new EmptyParticleIngredient(),
				new ParticleStack(Particles.neutron,-4000,134),
				new EmptyParticleIngredient()
				);
		addRecipe("ingotUranium238",
				new ParticleStack(Particles.neutron,20000,92500,0.05,100),
				"ingotUranium235",
				new EmptyParticleIngredient(),
				new ParticleStack(Particles.neutron,-4456,216),
				new EmptyParticleIngredient()
				);
		addRecipe("ingotUranium235",
				new ParticleStack(Particles.neutron,11000,178000,0.3636,100),
				"ingotUranium233",
				new EmptyParticleIngredient(),
				new ParticleStack(Particles.neutron,-4000,84),
				new EmptyParticleIngredient()
				);
		
		addRecipe("ingotBeryllium",
				new ParticleStack(Particles.triton,4000,106000,0.75,100),
				"ingotBoron11",
				new EmptyParticleIngredient(),
				new ParticleStack(Particles.neutron,9559,47),
				new EmptyParticleIngredient()
				);
		
		addRecipe("ingotLithium7",
				new ParticleStack(Particles.alpha,7500,166000,1,100),
				"ingotBoron10",
				new EmptyParticleIngredient(),
				new ParticleStack(Particles.neutron,-2790,30),
				new EmptyParticleIngredient()
				);		
		addRecipe("ingotBeryllium",
				new ParticleStack(Particles.alpha,7500,100000,0.06666,100),
				"charcoal",
				new EmptyParticleIngredient(),
				new ParticleStack(Particles.neutron,5702,50),
				new EmptyParticleIngredient()
				);
		addRecipe("charcoal",
				new ParticleStack(Particles.alpha,35000,416000,0.2847,100),
				"ingotBoron11",
				new ParticleStack(Particles.alpha,-7978,12),
				new EmptyParticleIngredient(),
				new ParticleStack(Particles.proton,-7978,12)
				);
	}

}
