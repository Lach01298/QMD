package lach_01298.qmd.recipes;

import lach_01298.qmd.QMDConstants;
import lach_01298.qmd.enums.MaterialTypes.*;
import lach_01298.qmd.item.QMDItems;
import lach_01298.qmd.particle.*;
import lach_01298.qmd.recipe.QMDRecipeHandler;
import nc.recipe.ingredient.*;
import nc.util.FluidStackHelper;
import net.minecraft.item.ItemStack;

import java.util.List;

public class AcceleratorSourceRecipes extends QMDRecipeHandler
{

	public AcceleratorSourceRecipes()
	{
		super("accelerator_source", 1, 1, 0, 0, 0, 1);
	}

	@Override
	public void addRecipes()
	{
		addRecipe(new EmptyItemIngredient(), fluidStack("hydrogen", FluidStackHelper.BUCKET_VOLUME * QMDConstants.ionSourceOutput/QMDConstants.moleAmount), new ParticleStack(Particles.proton,QMDConstants.ionSourceOutput));
		addRecipe(new EmptyItemIngredient(), fluidStack("deuterium", FluidStackHelper.BUCKET_VOLUME * QMDConstants.ionSourceOutput/QMDConstants.moleAmount), new ParticleStack(Particles.deuteron,QMDConstants.ionSourceOutput));
		addRecipe(new EmptyItemIngredient(), fluidStack("tritium", FluidStackHelper.BUCKET_VOLUME * QMDConstants.ionSourceOutput/QMDConstants.moleAmount), new ParticleStack(Particles.triton,QMDConstants.ionSourceOutput));
		addRecipe(new EmptyItemIngredient(), fluidStack("helium3", FluidStackHelper.BUCKET_VOLUME * QMDConstants.ionSourceOutput/QMDConstants.moleAmount), new ParticleStack(Particles.helion,QMDConstants.ionSourceOutput));
		addRecipe(new EmptyItemIngredient(), fluidStack("helium", FluidStackHelper.BUCKET_VOLUME * QMDConstants.ionSourceOutput/QMDConstants.moleAmount), new ParticleStack(Particles.alpha,QMDConstants.ionSourceOutput));
		addRecipe(new EmptyItemIngredient(), fluidStack("diborane", FluidStackHelper.BUCKET_VOLUME * QMDConstants.ionSourceOutput/QMDConstants.moleAmount), new ParticleStack(Particles.boron_ion,QMDConstants.ionSourceOutput));
		addRecipe(new ItemStack(QMDItems.source,1,SourceType.TUNGSTEN_FILAMENT.getID()), new EmptyFluidIngredient(), new ParticleStack(Particles.electron,QMDConstants.ionSourceOutput));
		addRecipe(new ItemStack(QMDItems.source,1,SourceType.SODIUM_22.getID()), new EmptyFluidIngredient(), new ParticleStack(Particles.positron,QMDConstants.ionSourceOutput));
		addRecipe(new ItemStack(QMDItems.source,1,SourceType.CALCIUM_48.getID()), new EmptyFluidIngredient(), new ParticleStack(Particles.calcium_48_ion,QMDConstants.ionSourceOutput));
		addRecipe(new ItemStack(QMDItems.cell,1,CellType.ANTIHYDROGEN.getID()), new EmptyFluidIngredient(), new ParticleStack(Particles.antiproton,QMDConstants.ionSourceOutput));
		addRecipe(new ItemStack(QMDItems.cell,1,CellType.ANTIDEUTERIUM.getID()), new EmptyFluidIngredient(), new ParticleStack(Particles.antideuteron,QMDConstants.ionSourceOutput));
		addRecipe(new ItemStack(QMDItems.cell,1,CellType.ANTITRITIUM.getID()), new EmptyFluidIngredient(), new ParticleStack(Particles.antitriton,QMDConstants.ionSourceOutput));
		addRecipe(new ItemStack(QMDItems.cell,1,CellType.ANTIHELIUM3.getID()), new EmptyFluidIngredient(), new ParticleStack(Particles.antihelion,QMDConstants.ionSourceOutput));
		addRecipe(new ItemStack(QMDItems.cell,1,CellType.ANTIHELIUM.getID()), new EmptyFluidIngredient(), new ParticleStack(Particles.antialpha,QMDConstants.ionSourceOutput));
		

		
		
	}

	@Override
	public List fixedExtras(List extras)
	{
		return extras;
	}
	
	
	

	
	
	
}
