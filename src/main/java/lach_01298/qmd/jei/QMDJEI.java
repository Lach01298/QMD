package lach_01298.qmd.jei;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

import lach_01298.qmd.block.QMDBlocks;
import lach_01298.qmd.jei.catergory.AcceleratorSourceCategory;
import lach_01298.qmd.jei.catergory.DecayChamberCategory;
import lach_01298.qmd.jei.catergory.IrradiatorCategory;
import lach_01298.qmd.jei.catergory.IrradiatorFuelCategory;
import lach_01298.qmd.jei.catergory.OreLeacherCategory;
import lach_01298.qmd.jei.catergory.ParticleInfoCategory;
import lach_01298.qmd.jei.catergory.QMDRecipeCategoryUid;
import lach_01298.qmd.jei.catergory.TargetChamberCategory;
import lach_01298.qmd.jei.ingredient.ParticleStackHelper;
import lach_01298.qmd.jei.ingredient.ParticleStackListFactory;
import lach_01298.qmd.jei.ingredient.ParticleStackRenderer;
import lach_01298.qmd.jei.ingredient.ParticleType;
import lach_01298.qmd.jei.recipe.AcceleratorSourceRecipe;
import lach_01298.qmd.jei.recipe.AcceleratorSourceRecipeMaker;
import lach_01298.qmd.jei.recipe.DecayChamberRecipeMaker;
import lach_01298.qmd.jei.recipe.ParticleInfoRecipeMaker;
import lach_01298.qmd.jei.recipe.TargetChamberRecipeMaker;
import lach_01298.qmd.particle.ParticleStack;
import lach_01298.qmd.recipes.AcceleratorSourceRecipes;
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
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import nc.config.NCConfig;
import nc.init.NCBlocks;
import nc.integration.jei.IJEIHandler;
import nc.integration.jei.JEICategoryAbstract;
import nc.integration.jei.JEIMethods;
import nc.integration.jei.JEIRecipeWrapper;
import nc.integration.jei.JEIRecipeWrapperAbstract;
import lach_01298.qmd.jei.recipe.QMDRecipeWrapper;
import nc.recipe.NCRecipes;
import nc.recipe.ProcessorRecipeHandler;
import nc.util.ItemStackHelper;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
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
				JEIProcessorHandler.ORE_LEACHER.getCategory(guiHelper),
				JEIProcessorHandler.IRRADIATOR.getCategory(guiHelper),
				JEIProcessorHandler.IRRADIATOR_FUEL.getCategory(guiHelper)
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
		
		registry.addRecipes(JEIProcessorHandler.ORE_LEACHER.getJEIRecipes(guiHelper), JEIProcessorHandler.ORE_LEACHER.getUUID());
		registry.addRecipeCatalyst(JEIProcessorHandler.ORE_LEACHER.getCrafters().get(0),JEIProcessorHandler.ORE_LEACHER.getUUID());
		
		registry.addRecipes(JEIProcessorHandler.IRRADIATOR.getJEIRecipes(guiHelper), JEIProcessorHandler.IRRADIATOR.getUUID());
		registry.addRecipeCatalyst(JEIProcessorHandler.IRRADIATOR.getCrafters().get(0),JEIProcessorHandler.IRRADIATOR.getUUID());
		
		registry.addRecipes(JEIProcessorHandler.IRRADIATOR_FUEL.getJEIRecipes(guiHelper), JEIProcessorHandler.IRRADIATOR_FUEL.getUUID());
		registry.addRecipeCatalyst(JEIProcessorHandler.IRRADIATOR_FUEL.getCrafters().get(0),JEIProcessorHandler.IRRADIATOR_FUEL.getUUID());
	}

	
	public enum JEIProcessorHandler implements IJEIHandler 
	{
		ORE_LEACHER(QMDRecipes.ore_leacher, QMDBlocks.oreLeacher, "ore_leacher", QMDRecipeWrapper.OreLeacher.class),
		IRRADIATOR(QMDRecipes.irradiator, QMDBlocks.irradiator, "irradiator", QMDRecipeWrapper.Irradiator.class),
		IRRADIATOR_FUEL(QMDRecipes.irradiator_fuel, QMDBlocks.irradiator, "irradiator", QMDRecipeWrapper.IrradiatorFuel.class);
	
		private ProcessorRecipeHandler recipeHandler;
		private Class<? extends JEIRecipeWrapperAbstract> recipeWrapper;
		private boolean enabled;
		private List<ItemStack> crafters;
		private String textureName;
		
		JEIProcessorHandler(ProcessorRecipeHandler recipeHandler, Block crafter, String textureName, Class<? extends JEIRecipeWrapperAbstract> recipeWrapper) 
		{
			this(recipeHandler, Lists.newArrayList(crafter), textureName, recipeWrapper);
		}
		

		JEIProcessorHandler(ProcessorRecipeHandler recipeHandler, List<Block> crafters, String textureName, Class<? extends JEIRecipeWrapperAbstract> recipeWrapper) 
		{
			this.recipeHandler = recipeHandler;
			this.recipeWrapper = recipeWrapper;
			this.crafters = new ArrayList<ItemStack>();
			for (Block crafter : crafters) this.crafters.add(ItemStackHelper.fixItemStack(crafter));
			this.textureName = textureName;
		}
		
		@Override
		public JEICategoryAbstract getCategory(IGuiHelper guiHelper) 
		{
			switch (this) 
			{
			case ORE_LEACHER:
				return new OreLeacherCategory(guiHelper, this);
			case IRRADIATOR:
				return new IrradiatorCategory(guiHelper, this);
			case IRRADIATOR_FUEL:
				return new IrradiatorFuelCategory(guiHelper, this);
			default:
				return null;
			}
		}
		
		@Override
		public ProcessorRecipeHandler getRecipeHandler() 
		{
			return recipeHandler;
		}
		
		@Override
		public Class<? extends JEIRecipeWrapperAbstract> getJEIRecipeWrapper() 
		{
			return recipeWrapper;
		}
		
		@Override
		public ArrayList<JEIRecipeWrapperAbstract> getJEIRecipes(IGuiHelper guiHelper) 
		{
			return JEIMethods.getJEIRecipes(guiHelper, this, getRecipeHandler(), getJEIRecipeWrapper());
		}

		
		@Override
		public String getUUID() 
		{
			return getRecipeHandler().getRecipeName();
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
