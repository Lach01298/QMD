package lach_01298.qmd.recipes;

import java.util.List;

import lach_01298.qmd.config.QMDConfig;
import lach_01298.qmd.enums.MaterialTypes.CanisterType;
import lach_01298.qmd.enums.MaterialTypes.CellType;
import lach_01298.qmd.enums.MaterialTypes.SourceType;
import lach_01298.qmd.item.QMDItems;
import lach_01298.qmd.particle.ParticleStack;
import lach_01298.qmd.particle.Particles;
import lach_01298.qmd.recipe.QMDRecipeHandler;
import net.minecraft.item.ItemStack;

public class AcceleratorSourceRecipes extends QMDRecipeHandler
{

	public AcceleratorSourceRecipes() 
	{
		super("accelerator_source", 1, 0, 0, 0, 0, 1);
	}

	@Override
	public void addRecipes()
	{
		addRecipe(new ItemStack(QMDItems.source,1,SourceType.TUNGSTEN_FILAMENT.getID()),new ParticleStack(Particles.electron,QMDConfig.source_particle_amount,0,0.2));
		addRecipe(new ItemStack(QMDItems.source,1,SourceType.SODIUM_22.getID()),new ParticleStack(Particles.positron,QMDConfig.source_particle_amount,0,0.2));
		addRecipe(new ItemStack(QMDItems.source,1,SourceType.CALCIUM_48.getID()),new ParticleStack(Particles.calcium_48_ion,QMDConfig.source_particle_amount,0,0.2));
		addRecipe(new ItemStack(QMDItems.canister,1,CanisterType.HYDROGEN.getID()),new ParticleStack(Particles.proton,QMDConfig.source_particle_amount,0,0.2));
		addRecipe(new ItemStack(QMDItems.canister,1,CanisterType.DEUTERIUM.getID()),new ParticleStack(Particles.deuteron,QMDConfig.source_particle_amount,0,0.2));
		addRecipe(new ItemStack(QMDItems.canister,1,CanisterType.TRITIUM.getID()),new ParticleStack(Particles.triton,QMDConfig.source_particle_amount,0,0.2));
		addRecipe(new ItemStack(QMDItems.canister,1,CanisterType.HELIUM3.getID()),new ParticleStack(Particles.helion,QMDConfig.source_particle_amount,0,0.2));
		addRecipe(new ItemStack(QMDItems.canister,1,CanisterType.HELIUM.getID()),new ParticleStack(Particles.alpha,QMDConfig.source_particle_amount,0,0.2));
		addRecipe(new ItemStack(QMDItems.canister,1,CanisterType.DIBORANE.getID()),new ParticleStack(Particles.boron_ion,QMDConfig.source_particle_amount,0,0.2));
		addRecipe(new ItemStack(QMDItems.cell,1,CellType.ANTIHYDROGEN.getID()),new ParticleStack(Particles.antiproton,QMDConfig.source_particle_amount,0,0.2));
		addRecipe(new ItemStack(QMDItems.cell,1,CellType.ANTIDEUTERIUM.getID()),new ParticleStack(Particles.antideuteron,QMDConfig.source_particle_amount,0,0.2));
		addRecipe(new ItemStack(QMDItems.cell,1,CellType.ANTITRITIUM.getID()),new ParticleStack(Particles.antitriton,QMDConfig.source_particle_amount,0,0.2));
		addRecipe(new ItemStack(QMDItems.cell,1,CellType.ANTIHELIUM3.getID()),new ParticleStack(Particles.antihelion,QMDConfig.source_particle_amount,0,0.2));
		addRecipe(new ItemStack(QMDItems.cell,1,CellType.ANTIHELIUM.getID()),new ParticleStack(Particles.antialpha,QMDConfig.source_particle_amount,0,0.2));
		
		
		
		
	}

	@Override
	public List fixExtras(List extras)
	{
		return extras;
	}
	
	
	

	
	
	
}
