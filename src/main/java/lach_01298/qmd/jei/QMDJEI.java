package lach_01298.qmd.jei;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

import lach_01298.qmd.block.QMDBlocks;
import lach_01298.qmd.jei.catergory.AcceleratorCoolingCategory;
import lach_01298.qmd.jei.catergory.AcceleratorSourceCategory;
import lach_01298.qmd.jei.catergory.AtmosphereCollectorCategory;
import lach_01298.qmd.jei.catergory.BeamDumpCategory;
import lach_01298.qmd.jei.catergory.CellFillingCategory;
import lach_01298.qmd.jei.catergory.CollisionChamberCategory;
import lach_01298.qmd.jei.catergory.DecayChamberCategory;
import lach_01298.qmd.jei.catergory.IrradiatorCategory;
import lach_01298.qmd.jei.catergory.IrradiatorFuelCategory;
import lach_01298.qmd.jei.catergory.NeutralContainmentCategory;
import lach_01298.qmd.jei.catergory.NucleosynthesisChamberCategory;
import lach_01298.qmd.jei.catergory.OreLeacherCategory;
import lach_01298.qmd.jei.catergory.ParticleInfoCategory;
import lach_01298.qmd.jei.catergory.QMDRecipeCategoryUid;
import lach_01298.qmd.jei.catergory.TargetChamberCategory;
import lach_01298.qmd.jei.catergory.VacuumChamberHeatingCategory;
import lach_01298.qmd.jei.ingredient.ParticleStackHelper;
import lach_01298.qmd.jei.ingredient.ParticleStackListFactory;
import lach_01298.qmd.jei.ingredient.ParticleStackRenderer;
import lach_01298.qmd.jei.ingredient.ParticleType;
import lach_01298.qmd.jei.recipe.AcceleratorSourceRecipeMaker;
import lach_01298.qmd.jei.recipe.AtmosphereCollectorRecipeMaker;
import lach_01298.qmd.jei.recipe.BeamDumpRecipeMaker;
import lach_01298.qmd.jei.recipe.CollisionChamberRecipeMaker;
import lach_01298.qmd.jei.recipe.DecayChamberRecipeMaker;
import lach_01298.qmd.jei.recipe.NeutralContainmentRecipeMaker;
import lach_01298.qmd.jei.recipe.NucleosynthesisChamberRecipeMaker;
import lach_01298.qmd.jei.recipe.ParticleInfoRecipeMaker;
import lach_01298.qmd.jei.recipe.QMDRecipeWrapper;
import lach_01298.qmd.jei.recipe.TargetChamberRecipeMaker;
import lach_01298.qmd.particle.ParticleStack;
import lach_01298.qmd.recipes.QMDRecipes;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.ISubtypeRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.ingredients.IIngredientRegistry;
import mezz.jei.api.ingredients.IModIngredientRegistration;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import nc.integration.jei.JEIBasicCategory;
import nc.integration.jei.JEIBasicRecipeWrapper;
import nc.integration.jei.JEIHelper;
import nc.integration.jei.NCJEI.IJEIHandler;
import nc.recipe.BasicRecipeHandler;
import nc.util.StackHelper;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

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

	public void registerCategories(IRecipeCategoryRegistration registry)
	{
		IJeiHelpers jeiHelpers = registry.getJeiHelpers();
		IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
		registry.addRecipeCategories(
				new AcceleratorSourceCategory(guiHelper),
				new ParticleInfoCategory(guiHelper),
				
				new TargetChamberCategory(guiHelper),
				new DecayChamberCategory(guiHelper),
				new CollisionChamberCategory(guiHelper),
				JEIHandler.ORE_LEACHER.getCategory(guiHelper),
				JEIHandler.IRRADIATOR.getCategory(guiHelper),
				JEIHandler.IRRADIATOR_FUEL.getCategory(guiHelper),
				JEIHandler.ACCELERATOR_COOLING.getCategory(guiHelper),
				new BeamDumpCategory(guiHelper),
				new NeutralContainmentCategory(guiHelper),
				JEIHandler.CELL_FILLING.getCategory(guiHelper),
				new AtmosphereCollectorCategory(guiHelper),
				new NucleosynthesisChamberCategory(guiHelper),
				JEIHandler.VACUUM_CHAMBER_HEATING.getCategory(guiHelper)
				);
		
	}

	public void register(IModRegistry registry)
	{
		IIngredientRegistry ingredientRegistry = registry.getIngredientRegistry();
		IJeiHelpers jeiHelpers = registry.getJeiHelpers();
		IGuiHelper guiHelper = jeiHelpers.getGuiHelper();


		registry.addRecipes(AcceleratorSourceRecipeMaker.getRecipes(jeiHelpers), QMDRecipeCategoryUid.SOURCE);
		registry.addRecipeCatalyst(new ItemStack(QMDBlocks.acceleratorSource),QMDRecipeCategoryUid.SOURCE);
	
		registry.addRecipes(ParticleInfoRecipeMaker.getRecipes(jeiHelpers), QMDRecipeCategoryUid.PARTICLE_INFO);
	
		registry.addRecipes(TargetChamberRecipeMaker.getRecipes(jeiHelpers), QMDRecipeCategoryUid.TARGET_CHAMBER);
		registry.addRecipeCatalyst(new ItemStack(QMDBlocks.targetChamberController),QMDRecipeCategoryUid.TARGET_CHAMBER);
		
		registry.addRecipes(DecayChamberRecipeMaker.getRecipes(jeiHelpers), QMDRecipeCategoryUid.DECAY_CHAMBER);
		registry.addRecipeCatalyst(new ItemStack(QMDBlocks.decayChamberController),QMDRecipeCategoryUid.DECAY_CHAMBER);
		
		registry.addRecipes(CollisionChamberRecipeMaker.getRecipes(jeiHelpers), QMDRecipeCategoryUid.COLLISION_CHAMBER);
		registry.addRecipeCatalyst(new ItemStack(QMDBlocks.collisionChamberController),QMDRecipeCategoryUid.COLLISION_CHAMBER);
		
		registry.addRecipes(JEIHandler.ORE_LEACHER.getJEIRecipes(guiHelper), JEIHandler.ORE_LEACHER.getUid());
		registry.addRecipeCatalyst(JEIHandler.ORE_LEACHER.getCrafters().get(0),JEIHandler.ORE_LEACHER.getUid());
		
		registry.addRecipes(JEIHandler.IRRADIATOR.getJEIRecipes(guiHelper), JEIHandler.IRRADIATOR.getUid());
		registry.addRecipeCatalyst(JEIHandler.IRRADIATOR.getCrafters().get(0),JEIHandler.IRRADIATOR.getUid());
		
		registry.addRecipes(JEIHandler.IRRADIATOR_FUEL.getJEIRecipes(guiHelper), JEIHandler.IRRADIATOR_FUEL.getUid());
		registry.addRecipeCatalyst(JEIHandler.IRRADIATOR_FUEL.getCrafters().get(0),JEIHandler.IRRADIATOR_FUEL.getUid());
		
		registry.addRecipes(JEIHandler.ACCELERATOR_COOLING.getJEIRecipes(guiHelper), JEIHandler.ACCELERATOR_COOLING.getUid());
		for (int i = 0; i < JEIHandler.ACCELERATOR_COOLING.getCrafters().size(); i++)
		{
			registry.addRecipeCatalyst(JEIHandler.ACCELERATOR_COOLING.getCrafters().get(i),
					JEIHandler.ACCELERATOR_COOLING.getUid());
		}

		registry.addRecipes(BeamDumpRecipeMaker.getRecipes(jeiHelpers), QMDRecipeCategoryUid.BEAM_DUMP);
		registry.addRecipeCatalyst(new ItemStack(QMDBlocks.beamDumpController),QMDRecipeCategoryUid.BEAM_DUMP);
		
		registry.addRecipes(NeutralContainmentRecipeMaker.getRecipes(jeiHelpers), QMDRecipeCategoryUid.NEUTRAL_CONTAINMENT);
		registry.addRecipeCatalyst(new ItemStack(QMDBlocks.exoticContainmentController),QMDRecipeCategoryUid.NEUTRAL_CONTAINMENT);
		
		registry.addRecipes(JEIHandler.CELL_FILLING.getJEIRecipes(guiHelper), JEIHandler.CELL_FILLING.getUid());
		registry.addRecipeCatalyst(JEIHandler.CELL_FILLING.getCrafters().get(0),JEIHandler.CELL_FILLING.getUid());
		
		registry.addRecipes(AtmosphereCollectorRecipeMaker.getRecipes(jeiHelpers), QMDRecipeCategoryUid.ATMOSPHERE_COLLECTOR);
		registry.addRecipeCatalyst(new ItemStack(QMDBlocks.atmosphereCollector),QMDRecipeCategoryUid.ATMOSPHERE_COLLECTOR);
		
		registry.addRecipes(NucleosynthesisChamberRecipeMaker.getRecipes(jeiHelpers), QMDRecipeCategoryUid.NUCLEOSYNTHESIS_CHAMBER);
		registry.addRecipeCatalyst(new ItemStack(QMDBlocks.nucleosynthesisChamberController),QMDRecipeCategoryUid.NUCLEOSYNTHESIS_CHAMBER);
		
		registry.addRecipes(JEIHandler.VACUUM_CHAMBER_HEATING.getJEIRecipes(guiHelper), JEIHandler.VACUUM_CHAMBER_HEATING.getUid());
		for (int i = 0; i < JEIHandler.VACUUM_CHAMBER_HEATING.getCrafters().size(); i++)
		{
			registry.addRecipeCatalyst(JEIHandler.VACUUM_CHAMBER_HEATING.getCrafters().get(i),
					JEIHandler.VACUUM_CHAMBER_HEATING.getUid());
		}
		
		
	}

	
	public enum JEIHandler implements IJEIHandler 
	{
		ORE_LEACHER(QMDRecipes.ore_leacher, QMDBlocks.oreLeacher, "ore_leacher", QMDRecipeWrapper.OreLeacher.class),
		IRRADIATOR(QMDRecipes.irradiator, QMDBlocks.irradiator, "irradiator", QMDRecipeWrapper.Irradiator.class),
		IRRADIATOR_FUEL(QMDRecipes.irradiator_fuel, QMDBlocks.irradiator, "irradiator", QMDRecipeWrapper.IrradiatorFuel.class),
		ACCELERATOR_COOLING(QMDRecipes.accelerator_cooling, Lists.newArrayList(QMDBlocks.linearAcceleratorController,QMDBlocks.ringAcceleratorController,QMDBlocks.beamDiverterController,QMDBlocks.deceleratorController,QMDBlocks.exoticContainmentController,QMDBlocks.nucleosynthesisChamberController), "jei/accelerator_cooling", QMDRecipeWrapper.AcceleratorCooling.class),
		CELL_FILLING(QMDRecipes.cell_filling, QMDBlocks.exoticContainmentController, "jei/cell_filling", QMDRecipeWrapper.CellFilling.class),
		VACUUM_CHAMBER_HEATING(QMDRecipes.vacuum_chamber_heating, Lists.newArrayList(QMDBlocks.nucleosynthesisChamberController), "jei/accelerator_cooling", QMDRecipeWrapper.VacuumChamberHeating.class);
		
		private BasicRecipeHandler recipeHandler;
		private Class<? extends JEIBasicRecipeWrapper> recipeWrapper;
		private boolean enabled;
		private List<ItemStack> crafters;
		private String textureName;
		
		JEIHandler(BasicRecipeHandler recipeHandler, Block crafter, String textureName, Class<? extends JEIBasicRecipeWrapper> recipeWrapper) 
		{
			this(recipeHandler, Lists.newArrayList(crafter), textureName, recipeWrapper);
		}
		

		JEIHandler(BasicRecipeHandler recipeHandler, List<Block> crafters, String textureName, Class<? extends JEIBasicRecipeWrapper> recipeWrapper) 
		{
			this.recipeHandler = recipeHandler;
			this.recipeWrapper = recipeWrapper;
			this.crafters = new ArrayList<ItemStack>();
			for (Block crafter : crafters) this.crafters.add(StackHelper.fixItemStack(crafter));
			this.textureName = textureName;
		}
		
		@Override
		public JEIBasicCategory getCategory(IGuiHelper guiHelper) 
		{
			switch (this) 
			{
			case ORE_LEACHER:
				return new OreLeacherCategory(guiHelper, this);
			case IRRADIATOR:
				return new IrradiatorCategory(guiHelper, this);
			case IRRADIATOR_FUEL:
				return new IrradiatorFuelCategory(guiHelper, this);
			case ACCELERATOR_COOLING:
				return new AcceleratorCoolingCategory(guiHelper, this);
			case CELL_FILLING:
				return new CellFillingCategory(guiHelper, this);
			case VACUUM_CHAMBER_HEATING:
				return new VacuumChamberHeatingCategory(guiHelper, this);
			default:
				return null;
			}
		}
		
		@Override
		public BasicRecipeHandler getRecipeHandler() 
		{
			return recipeHandler;
		}
		
		@Override
		public Class getRecipeWrapperClass() 
		{
			return recipeWrapper;
		}
		
		@Override
		public List<JEIBasicRecipeWrapper> getJEIRecipes(IGuiHelper guiHelper) 
		{
			return JEIHelper.getJEIRecipes(guiHelper, this, getRecipeHandler(), getRecipeWrapperClass());
		}

		
		@Override
		public String getUid()
		{
			return getRecipeHandler().getName();
		}
		
		@Override
		public boolean getEnabled() 
		{
			return true;
		}
		
		@Override
		public List<ItemStack> getCrafters() 
		{
			return crafters;
		}
		
		@Override
		public String getTextureName() 
		{
			return textureName;
		}
	}
	
	
	
	
	

}
