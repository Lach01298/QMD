package lach_01298.qmd.jei;

import com.google.common.collect.Lists;
import lach_01298.qmd.block.QMDBlocks;
import lach_01298.qmd.jei.category.*;
import lach_01298.qmd.jei.ingredient.ParticleStackHelper;
import lach_01298.qmd.jei.ingredient.ParticleStackListFactory;
import lach_01298.qmd.jei.ingredient.ParticleStackRenderer;
import lach_01298.qmd.jei.ingredient.ParticleType;
import lach_01298.qmd.jei.recipe.*;
import lach_01298.qmd.machine.container.ContainerIrradiator;
import lach_01298.qmd.machine.container.ContainerOreLeacher;
import lach_01298.qmd.machine.gui.GuiIrradiator;
import lach_01298.qmd.machine.gui.GuiOreLeacher;
import lach_01298.qmd.multiblock.container.ContainerExoticContainmentController;
import lach_01298.qmd.multiblock.container.ContainerMassSpectrometerController;
import lach_01298.qmd.multiblock.container.ContainerTargetChamberController;
import lach_01298.qmd.multiblock.gui.*;
import lach_01298.qmd.particle.ParticleStack;
import mezz.jei.api.*;
import mezz.jei.api.ingredients.IIngredientRegistry;
import mezz.jei.api.ingredients.IModIngredientRegistration;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import mezz.jei.api.recipe.transfer.IRecipeTransferRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

import java.util.List;

@JEIPlugin
public class QMDJEI implements IModPlugin
{

	@Override
	public void registerItemSubtypes(ISubtypeRegistry subtypeRegistry)
	{
	
	}
	
	@Override
	public void registerIngredients(IModIngredientRegistration registry)
	{

		List<ParticleStack> particleStacks = ParticleStackListFactory.create();
		ParticleStackHelper particleStackHelper = new ParticleStackHelper();
		ParticleStackRenderer particleStackRenderer = new ParticleStackRenderer();
		
		registry.register(ParticleType.Particle, particleStacks, particleStackHelper, particleStackRenderer);
	}

	@Override
	public void registerCategories(IRecipeCategoryRegistration registry)
	{
		IJeiHelpers jeiHelpers = registry.getJeiHelpers();
		IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
		registry.addRecipeCategories(
				new AcceleratorSourceCategory(guiHelper),
				new AcceleratorCoolingCategory(guiHelper),
				new MassSpectrometerCategory(guiHelper),

				new TargetChamberCategory(guiHelper),
				new DecayChamberCategory(guiHelper),
				new BeamDumpCategory(guiHelper),
				new CollisionChamberCategory(guiHelper),

				new OreLeacherCategory(guiHelper),
				new IrradiatorCategory(guiHelper),
				new IrradiatorFuelCategory(guiHelper),

				new NeutralContainmentCategory(guiHelper),
				new CellFillingCategory(guiHelper),
				new NucleosynthesisChamberCategory(guiHelper),
				new VacuumChamberHeatingCategory(guiHelper),

				new AtmosphereCollectorCategory(guiHelper),
				new ParticleInfoCategory(guiHelper)
				);
		
	}

	@Override
	public void register(IModRegistry registry)
	{
		IIngredientRegistry ingredientRegistry = registry.getIngredientRegistry();
		IJeiHelpers jeiHelpers = registry.getJeiHelpers();
		IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
		IRecipeTransferRegistry recipeTransferRegistry = registry.getRecipeTransferRegistry();

		registry.addRecipes(AcceleratorSourceRecipeMaker.getRecipes(jeiHelpers), QMDRecipeCategoryUid.ACCELERATOR_SOURCE);
		registry.addRecipeCatalyst(new ItemStack(QMDBlocks.acceleratorSource),QMDRecipeCategoryUid.ACCELERATOR_SOURCE);
		registry.addRecipeCatalyst(new ItemStack(QMDBlocks.acceleratorLaserIonSource),QMDRecipeCategoryUid.ACCELERATOR_SOURCE);

		registry.addRecipes(AcceleratorCoolingRecipeMaker.getRecipes(jeiHelpers), QMDRecipeCategoryUid.ACCELERATOR_COOLING);
		List<Block> AcceleratorCoolingCatalysts = Lists.newArrayList(QMDBlocks.linearAcceleratorController, QMDBlocks.ringAcceleratorController, QMDBlocks.beamDiverterController, QMDBlocks.deceleratorController, QMDBlocks.massSpectrometerController, QMDBlocks.exoticContainmentController, QMDBlocks.nucleosynthesisChamberController);
		for(Block catalyst : AcceleratorCoolingCatalysts)
		{
			registry.addRecipeCatalyst(new ItemStack(catalyst),QMDRecipeCategoryUid.ACCELERATOR_COOLING);
		}

		registry.addRecipes(MassSpectrometerRecipeMaker.getRecipes(jeiHelpers), QMDRecipeCategoryUid.MASS_SPECTROMETER);
		registry.addRecipeCatalyst(new ItemStack(QMDBlocks.massSpectrometerController),QMDRecipeCategoryUid.MASS_SPECTROMETER);
		registry.addRecipeClickArea(GuiMassSpectrometerController.class, 52, 51, 201, 55, QMDRecipeCategoryUid.MASS_SPECTROMETER);
		recipeTransferRegistry.addRecipeTransferHandler(ContainerMassSpectrometerController.class, QMDRecipeCategoryUid.MASS_SPECTROMETER, 0, 1, 5, 36);


		registry.addRecipes(TargetChamberRecipeMaker.getRecipes(jeiHelpers), QMDRecipeCategoryUid.TARGET_CHAMBER);
		registry.addRecipeCatalyst(new ItemStack(QMDBlocks.targetChamberController),QMDRecipeCategoryUid.TARGET_CHAMBER);
		registry.addRecipeClickArea(GuiTargetChamberController.class, 71, 48, 20, 16, QMDRecipeCategoryUid.TARGET_CHAMBER);
		recipeTransferRegistry.addRecipeTransferHandler(ContainerTargetChamberController.class, QMDRecipeCategoryUid.TARGET_CHAMBER, 0, 1, 2, 36);
		
		registry.addRecipes(DecayChamberRecipeMaker.getRecipes(jeiHelpers), QMDRecipeCategoryUid.DECAY_CHAMBER);
		registry.addRecipeCatalyst(new ItemStack(QMDBlocks.decayChamberController),QMDRecipeCategoryUid.DECAY_CHAMBER);
		registry.addRecipeClickArea(GuiDecayChamberController.class, 85, 13, 15, 64, QMDRecipeCategoryUid.DECAY_CHAMBER);

		registry.addRecipes(BeamDumpRecipeMaker.getRecipes(jeiHelpers), QMDRecipeCategoryUid.BEAM_DUMP);
		registry.addRecipeCatalyst(new ItemStack(QMDBlocks.beamDumpController),QMDRecipeCategoryUid.BEAM_DUMP);
		registry.addRecipeClickArea(GuiBeamDumpController.class, 54, 36, 26, 18, QMDRecipeCategoryUid.BEAM_DUMP);

		registry.addRecipes(CollisionChamberRecipeMaker.getRecipes(jeiHelpers), QMDRecipeCategoryUid.COLLISION_CHAMBER);
		registry.addRecipeCatalyst(new ItemStack(QMDBlocks.collisionChamberController),QMDRecipeCategoryUid.COLLISION_CHAMBER);
		registry.addRecipeClickArea(GuiCollisionChamberController.class, 66, 31, 44, 44, QMDRecipeCategoryUid.COLLISION_CHAMBER);

		registry.addRecipes(OreLeacherRecipeMaker.getRecipes(jeiHelpers), QMDRecipeCategoryUid.ORE_LEACHER);
		registry.addRecipeCatalyst(new ItemStack(QMDBlocks.oreLeacher),QMDRecipeCategoryUid.ORE_LEACHER);
		registry.addRecipeClickArea(GuiOreLeacher.class, 94, 41, 16, 18, QMDRecipeCategoryUid.ORE_LEACHER);
		recipeTransferRegistry.addRecipeTransferHandler(ContainerOreLeacher.class, QMDRecipeCategoryUid.ORE_LEACHER, 0, 1, 4, 36);
		
		registry.addRecipes(IrradiatorRecipeMaker.getRecipes(jeiHelpers), QMDRecipeCategoryUid.IRRADIATOR);
		registry.addRecipeCatalyst(new ItemStack(QMDBlocks.irradiator),QMDRecipeCategoryUid.IRRADIATOR);
		registry.addRecipeClickArea(GuiIrradiator.class, 62, 57, 52, 10, QMDRecipeCategoryUid.IRRADIATOR);
		recipeTransferRegistry.addRecipeTransferHandler(ContainerIrradiator.class, QMDRecipeCategoryUid.IRRADIATOR, 0, 1, 3, 35);

		registry.addRecipes(IrradiatorFuelRecipeMaker.getRecipes(jeiHelpers), QMDRecipeCategoryUid.IRRADIATOR_FUEL);
		registry.addRecipeCatalyst(new ItemStack(QMDBlocks.irradiator),QMDRecipeCategoryUid.IRRADIATOR_FUEL);
		registry.addRecipeClickArea(GuiIrradiator.class, 68, 38, 40, 19, QMDRecipeCategoryUid.IRRADIATOR_FUEL);
		recipeTransferRegistry.addRecipeTransferHandler(ContainerIrradiator.class, QMDRecipeCategoryUid.IRRADIATOR_FUEL, 1, 1, 3, 35);


		registry.addRecipes(NeutralContainmentRecipeMaker.getRecipes(jeiHelpers), QMDRecipeCategoryUid.NEUTRAL_CONTAINMENT);
		registry.addRecipeCatalyst(new ItemStack(QMDBlocks.exoticContainmentController),QMDRecipeCategoryUid.NEUTRAL_CONTAINMENT);

		registry.addRecipes(CellFillingRecipeMaker.getRecipes(jeiHelpers), QMDRecipeCategoryUid.CELL_FILLING);
		registry.addRecipeCatalyst(new ItemStack(QMDBlocks.exoticContainmentController),QMDRecipeCategoryUid.CELL_FILLING);
		registry.addRecipeClickArea(GuiNeutralContainmentController.class, 77, 60, 22, 16, QMDRecipeCategoryUid.CELL_FILLING);
		recipeTransferRegistry.addRecipeTransferHandler(ContainerExoticContainmentController.class, QMDRecipeCategoryUid.CELL_FILLING, 0, 1, 2, 34);

		registry.addRecipes(NucleosynthesisChamberRecipeMaker.getRecipes(jeiHelpers), QMDRecipeCategoryUid.NUCLEOSYNTHESIS_CHAMBER);
		registry.addRecipeCatalyst(new ItemStack(QMDBlocks.nucleosynthesisChamberController),QMDRecipeCategoryUid.NUCLEOSYNTHESIS_CHAMBER);
		registry.addRecipeClickArea(GuiNucleosynthesisChamberController.class, 71, 24, 62, 28, QMDRecipeCategoryUid.NUCLEOSYNTHESIS_CHAMBER);

		registry.addRecipes(VacuumChamberHeatingRecipeMaker.getRecipes(jeiHelpers), QMDRecipeCategoryUid.VACUUM_CHAMBER_HEATING);
		registry.addRecipeCatalyst(new ItemStack(QMDBlocks.nucleosynthesisChamberController), QMDRecipeCategoryUid.VACUUM_CHAMBER_HEATING);




		registry.addRecipes(AtmosphereCollectorRecipeMaker.getRecipes(jeiHelpers), QMDRecipeCategoryUid.ATMOSPHERE_COLLECTOR);
		registry.addRecipeCatalyst(new ItemStack(QMDBlocks.atmosphereCollector),QMDRecipeCategoryUid.ATMOSPHERE_COLLECTOR);


		registry.addRecipes(ParticleInfoRecipeMaker.getRecipes(jeiHelpers), QMDRecipeCategoryUid.PARTICLE_INFO);
	}
}
