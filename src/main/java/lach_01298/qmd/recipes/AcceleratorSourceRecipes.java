package lach_01298.qmd.recipes;

import java.util.List;

import lach_01298.qmd.config.QMDConfig;
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
		addRecipe(QMDItems.tungsten_filament,new ParticleStack(Particles.electron,QMDConfig.source_particle_amount));
		addRecipe(QMDItems.canister_hydrogen,new ParticleStack(Particles.proton,QMDConfig.source_particle_amount));
		addRecipe(QMDItems.canister_helium,new ParticleStack(Particles.alpha,QMDConfig.source_particle_amount));
		addRecipe(QMDItems.canister_deuterium,new ParticleStack(Particles.deuteron,QMDConfig.source_particle_amount));
		addRecipe(QMDItems.canister_diborane,new ParticleStack(Particles.boron_ion,QMDConfig.source_particle_amount));
		addRecipe(QMDItems.canister_helium_3,new ParticleStack(Particles.helion,QMDConfig.source_particle_amount));
		addRecipe(QMDItems.source_sodium_22,new ParticleStack(Particles.positron,QMDConfig.source_particle_amount));
		addRecipe(QMDItems.source_calcium_48,new ParticleStack(Particles.calcium_48_ion,QMDConfig.source_particle_amount));
		
	}

	@Override
	public List fixExtras(List extras)
	{
		return extras;
	}
	
	
	

	
	
	
}
