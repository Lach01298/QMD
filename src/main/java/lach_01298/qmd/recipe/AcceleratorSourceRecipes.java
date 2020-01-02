package lach_01298.qmd.recipe;

import lach_01298.qmd.item.QMDItems;
import lach_01298.qmd.particle.ParticleBeam;
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
		addRecipe(QMDItems.tungsten_filament,new ParticleBeam(Particles.electron,0,0,1));
		addRecipe(QMDItems.canister_Hydrogen,new ParticleBeam(Particles.proton,0,0,1));
		addRecipe(QMDItems.canister_Helium,new ParticleBeam(Particles.alpha,0,0,1));
		addRecipe(QMDItems.sodium_22_source,new ParticleBeam(Particles.positron,0,0,1));
	}
	
	
	

	
	
	
}
