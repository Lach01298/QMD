package lach_01298.qmd.recipes;

import java.util.List;

import lach_01298.qmd.config.QMDConfig;
import lach_01298.qmd.enums.MaterialTypes.CellType;
import lach_01298.qmd.enums.MaterialTypes.SourceType;
import lach_01298.qmd.item.QMDItems;
import lach_01298.qmd.particle.ParticleStack;
import lach_01298.qmd.particle.Particles;
import lach_01298.qmd.recipe.QMDRecipeHandler;
import nc.recipe.ingredient.EmptyFluidIngredient;
import nc.recipe.ingredient.EmptyItemIngredient;
import nc.util.FluidStackHelper;
import net.minecraft.item.ItemStack;

public class AcceleratorSourceRecipes extends QMDRecipeHandler
{

	public AcceleratorSourceRecipes() 
	{
		super("accelerator_source", 1, 1, 0, 0, 0, 1);
	}

	@Override
	public void addRecipes()
	{
		addRecipe(new EmptyItemIngredient(), fluidStack("hydrogen", FluidStackHelper.BUCKET_VOLUME * QMDConfig.ion_source_output/QMDConfig.mole_amount), new ParticleStack(Particles.proton,QMDConfig.ion_source_output));
		addRecipe(new EmptyItemIngredient(), fluidStack("deuterium", FluidStackHelper.BUCKET_VOLUME * QMDConfig.ion_source_output/QMDConfig.mole_amount), new ParticleStack(Particles.deuteron,QMDConfig.ion_source_output));
		addRecipe(new EmptyItemIngredient(), fluidStack("tritium", FluidStackHelper.BUCKET_VOLUME * QMDConfig.ion_source_output/QMDConfig.mole_amount), new ParticleStack(Particles.triton,QMDConfig.ion_source_output));
		addRecipe(new EmptyItemIngredient(), fluidStack("helium3", FluidStackHelper.BUCKET_VOLUME * QMDConfig.ion_source_output/QMDConfig.mole_amount), new ParticleStack(Particles.helion,QMDConfig.ion_source_output));
		addRecipe(new EmptyItemIngredient(), fluidStack("helium", FluidStackHelper.BUCKET_VOLUME * QMDConfig.ion_source_output/QMDConfig.mole_amount), new ParticleStack(Particles.alpha,QMDConfig.ion_source_output));
		addRecipe(new EmptyItemIngredient(), fluidStack("diborane", FluidStackHelper.BUCKET_VOLUME * QMDConfig.ion_source_output/QMDConfig.mole_amount), new ParticleStack(Particles.boron_ion,QMDConfig.ion_source_output));		
		addRecipe(new ItemStack(QMDItems.source,1,SourceType.TUNGSTEN_FILAMENT.getID()), new EmptyFluidIngredient(), new ParticleStack(Particles.electron,QMDConfig.ion_source_output));
		addRecipe(new ItemStack(QMDItems.source,1,SourceType.SODIUM_22.getID()), new EmptyFluidIngredient(), new ParticleStack(Particles.positron,QMDConfig.ion_source_output));
		addRecipe(new ItemStack(QMDItems.source,1,SourceType.CALCIUM_48.getID()), new EmptyFluidIngredient(), new ParticleStack(Particles.calcium_48_ion,QMDConfig.ion_source_output));
		addRecipe(new ItemStack(QMDItems.cell,1,CellType.ANTIHYDROGEN.getID()), new EmptyFluidIngredient(), new ParticleStack(Particles.antiproton,QMDConfig.ion_source_output));
		addRecipe(new ItemStack(QMDItems.cell,1,CellType.ANTIDEUTERIUM.getID()), new EmptyFluidIngredient(), new ParticleStack(Particles.antideuteron,QMDConfig.ion_source_output));
		addRecipe(new ItemStack(QMDItems.cell,1,CellType.ANTITRITIUM.getID()), new EmptyFluidIngredient(), new ParticleStack(Particles.antitriton,QMDConfig.ion_source_output));
		addRecipe(new ItemStack(QMDItems.cell,1,CellType.ANTIHELIUM3.getID()), new EmptyFluidIngredient(), new ParticleStack(Particles.antihelion,QMDConfig.ion_source_output));
		addRecipe(new ItemStack(QMDItems.cell,1,CellType.ANTIHELIUM.getID()), new EmptyFluidIngredient(), new ParticleStack(Particles.antialpha,QMDConfig.ion_source_output));
		

		
		
	}

	@Override
	public List fixExtras(List extras)
	{
		return extras;
	}
	
	
	

	
	
	
}
