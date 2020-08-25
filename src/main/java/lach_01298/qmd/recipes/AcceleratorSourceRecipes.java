package lach_01298.qmd.recipes;

import java.util.List;

import lach_01298.qmd.item.QMDItems;
import lach_01298.qmd.particle.ParticleStack;
import lach_01298.qmd.particle.Particles;
import lach_01298.qmd.recipe.QMDRecipeHandler;

public class AcceleratorSourceRecipes extends QMDRecipeHandler
{

	public AcceleratorSourceRecipes() 
	{
		super("accelerator_source", 1, 0, 0, 0, 0, 1);
	}

	@Override
	public void addRecipes()
	{
		addRecipe(QMDItems.tungsten_filament,new ParticleStack(Particles.electron,100));
		addRecipe(QMDItems.canister_hydrogen,new ParticleStack(Particles.proton,100));
		addRecipe(QMDItems.canister_helium,new ParticleStack(Particles.alpha,100));
		addRecipe(QMDItems.canister_deuterium,new ParticleStack(Particles.deuteron,100));
		addRecipe(QMDItems.canister_diborane,new ParticleStack(Particles.boron_ion,100));
		addRecipe(QMDItems.canister_helium_3,new ParticleStack(Particles.helion,100));
		addRecipe(QMDItems.source_sodium_22,new ParticleStack(Particles.positron,100));
		
	}

	@Override
	public List fixExtras(List extras)
	{
		return extras;
	}
	
	
	

	
	
	
}
