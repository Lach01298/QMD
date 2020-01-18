package lach_01298.qmd.recipe;

import lach_01298.qmd.item.QMDItems;
import lach_01298.qmd.particle.ParticleStack;
import lach_01298.qmd.particle.Particles;

public class AcceleratorSourceRecipes extends QMDRecipeHandler
{

	public AcceleratorSourceRecipes() 
	{
		super("accelerator_source", 1, 0, 0, 0, 0, 1);
	}

	@Override
	public void addRecipes()
	{
		addRecipe(QMDItems.tungsten_filament,new ParticleStack(Particles.electron,0,100));
		addRecipe(QMDItems.canister_Hydrogen,new ParticleStack(Particles.proton,0,100));
		addRecipe(QMDItems.canister_Helium,new ParticleStack(Particles.alpha,0,100));
		addRecipe(QMDItems.canister_Deuterium,new ParticleStack(Particles.deuteron,0,100));
		addRecipe(QMDItems.source_sodium_22,new ParticleStack(Particles.positron,0,100));
	}
	
	
	

	
	
	
}
