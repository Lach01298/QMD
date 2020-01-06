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
		addRecipe(QMDItems.tungsten_filament,new ParticleStack(Particles.electron,0,100,0));
		addRecipe(QMDItems.canister_Hydrogen,new ParticleStack(Particles.proton,0,100,0));
		addRecipe(QMDItems.canister_Helium,new ParticleStack(Particles.alpha,0,100,0));
		addRecipe(QMDItems.sodium_22_source,new ParticleStack(Particles.positron,0,100,0));
	}
	
	
	

	
	
	
}
