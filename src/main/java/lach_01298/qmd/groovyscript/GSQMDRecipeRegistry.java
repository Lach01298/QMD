package lach_01298.qmd.groovyscript;

import com.cleanroommc.groovyscript.api.GroovyBlacklist;
import com.cleanroommc.groovyscript.api.documentation.annotations.MethodDescription;
import com.cleanroommc.groovyscript.api.documentation.annotations.MethodDescription.Type;
import com.cleanroommc.groovyscript.helper.Alias;
import com.cleanroommc.groovyscript.registry.VirtualizedRegistry;
import com.google.common.base.CaseFormat;
import lach_01298.qmd.groovyscript.GSQMDHelper;
import lach_01298.qmd.recipe.QMDRecipe;
import lach_01298.qmd.recipe.QMDRecipeHandler;
import lach_01298.qmd.recipe.ingredient.IParticleIngredient;
import lach_01298.qmd.recipes.QMDRecipes;
import nc.integration.groovyscript.GSHelper;
import nc.recipe.ingredient.IFluidIngredient;
import nc.recipe.ingredient.IItemIngredient;
import nc.util.*;

import javax.annotation.Nullable;
import java.util.*;

public abstract class GSQMDRecipeRegistry extends VirtualizedRegistry<QMDRecipe>
{

	@GroovyBlacklist
	protected final Lazy<QMDRecipeHandler> recipeHandler;

	public GSQMDRecipeRegistry(String name)
	{
		super(Alias.generateOf(name, CaseFormat.LOWER_UNDERSCORE));
		this.recipeHandler = new Lazy<>(() -> QMDRecipes.getHandler(name));
	}

	@GroovyBlacklist
	protected QMDRecipeHandler getRecipeHandler()
	{
		return recipeHandler.get();
	}

	@GroovyBlacklist
	@Override
	public void onReload()
	{
		QMDRecipeHandler recipeHandler = getRecipeHandler();
		removeScripted().forEach(recipeHandler::removeRecipe);
		restoreFromBackup().forEach(recipeHandler::addRecipe);
		getRecipeHandler().preReload();
	}

	@GroovyBlacklist
	@Override
	public void afterScriptLoad()
	{
		getRecipeHandler().postReload();
	}

	@GroovyBlacklist
	protected @Nullable QMDRecipe addRecipeInternal(Object... objects)
	{
		QMDRecipeHandler recipeHandler = getRecipeHandler();
		List<Object> objectList = Arrays.asList(objects);
		QMDRecipe recipe = recipeHandler.buildRecipe(
				StreamHelper.map(objectList.subList(0, recipeHandler.itemInputLastIndex), GSHelper::buildAdditionItemIngredient),
				StreamHelper.map(objectList.subList(recipeHandler.itemInputLastIndex, recipeHandler.fluidInputLastIndex), GSHelper::buildAdditionFluidIngredient),
				StreamHelper.map(objectList.subList(recipeHandler.fluidInputLastIndex, recipeHandler.particleInputLastIndex), GSQMDHelper::buildAdditionParticleIngredient),
				StreamHelper.map(objectList.subList(recipeHandler.particleInputLastIndex, recipeHandler.itemOutputLastIndex), GSHelper::buildAdditionItemIngredient),
				StreamHelper.map(objectList.subList(recipeHandler.itemOutputLastIndex, recipeHandler.fluidOutputLastIndex), GSHelper::buildAdditionFluidIngredient),
				StreamHelper.map(objectList.subList(recipeHandler.fluidOutputLastIndex, recipeHandler.particleOutputLastIndex), GSQMDHelper::buildAdditionParticleIngredient),
				objectList.subList(recipeHandler.particleOutputLastIndex, objects.length),
				recipeHandler.isShapeless
		);

		if (recipeHandler.addRecipe(recipe) != null)
		{
			addScripted(recipe);
			return recipe;
		}
		else
		{
			return null;
		}
	}

	@GroovyBlacklist
	protected void removeRecipeWithInputInternal(Object... inputs)
	{
		QMDRecipeHandler recipeHandler = getRecipeHandler();
		List<Object> inputList = Arrays.asList(inputs);
		List<IItemIngredient> itemIngredients = StreamHelper.map(inputList.subList(0, recipeHandler.itemInputSize), GSHelper::buildRemovalItemIngredient);
		List<IFluidIngredient> fluidIngredients = StreamHelper.map(inputList.subList(recipeHandler.itemInputSize, recipeHandler.itemInputSize + recipeHandler.fluidInputSize), GSHelper::buildRemovalFluidIngredient);
		List<IParticleIngredient> particleIngredients = StreamHelper.map(inputList.subList(recipeHandler.itemInputSize + recipeHandler.fluidInputSize, recipeHandler.itemInputSize + recipeHandler.fluidInputSize + recipeHandler.particleInputSize), GSQMDHelper::buildRemovalParticleIngredient);
		QMDRecipe recipe = recipeHandler.getRecipeFromIngredients(itemIngredients, fluidIngredients, particleIngredients);
		while (recipeHandler.removeRecipe(recipe))
		{
			addBackup(recipe);
			recipe = recipeHandler.getRecipeFromIngredients(itemIngredients, fluidIngredients,particleIngredients);
		}
	}

	@GroovyBlacklist
	protected void removeRecipeWithOutputInternal(Object... outputs)
	{
		QMDRecipeHandler recipeHandler = getRecipeHandler();
		List<Object> outputList = Arrays.asList(outputs);
		List<IItemIngredient> itemProducts = StreamHelper.map(outputList.subList(0, recipeHandler.itemOutputSize), GSHelper::buildRemovalItemIngredient);
		List<IFluidIngredient> fluidProducts = StreamHelper.map(outputList.subList(recipeHandler.itemOutputSize, recipeHandler.itemOutputSize + recipeHandler.fluidOutputSize), GSHelper::buildRemovalFluidIngredient);
		List<IParticleIngredient> particleProducts = StreamHelper.map(outputList.subList(recipeHandler.itemOutputSize + recipeHandler.fluidOutputSize, recipeHandler.itemOutputSize + recipeHandler.fluidOutputSize + recipeHandler.particleOutputSize), GSQMDHelper::buildRemovalParticleIngredient);
		QMDRecipe recipe = recipeHandler.getRecipeFromProducts(itemProducts, fluidProducts, particleProducts);
		while (recipeHandler.removeRecipe(recipe))
		{
			addBackup(recipe);
			recipe = recipeHandler.getRecipeFromProducts(itemProducts, fluidProducts, particleProducts);
		}
	}

	@GroovyBlacklist
	protected void removeAllRecipesInternal()
	{
		QMDRecipeHandler recipeHandler = getRecipeHandler();
		recipeHandler.getRecipeList().forEach(this::addBackup);
		recipeHandler.removeAllRecipes();
	}

	@MethodDescription(type = Type.QUERY)
	@Override
	public String getName()
	{
		return super.getName();
	}

	@MethodDescription(type = Type.QUERY)
	public List<QMDRecipe> getRecipeList()
	{
		return getRecipeHandler().getRecipeList();
	}

	@MethodDescription(type = Type.QUERY)
	public int getItemInputSize()
	{
		return getRecipeHandler().getItemInputSize();
	}

	@MethodDescription(type = Type.QUERY)
	public int getFluidInputSize()
	{
		return getRecipeHandler().getFluidInputSize();
	}

	@MethodDescription(type = Type.QUERY)
	public int getParticleInputSize()
	{
		return getRecipeHandler().getParticleInputSize();
	}

	@MethodDescription(type = Type.QUERY)
	public int getItemOutputSize()
	{
		return getRecipeHandler().getItemOutputSize();
	}

	@MethodDescription(type = Type.QUERY)
	public int getFluidOutputSize()
	{
		return getRecipeHandler().getFluidOutputSize();
	}

	@MethodDescription(type = Type.QUERY)
	public int getParticleOutputSize()
	{
		return getRecipeHandler().getFluidOutputSize();
	}

	@MethodDescription(type = Type.QUERY)
	public boolean isShapeless()
	{
		return getRecipeHandler().isShapeless();
	}

	@MethodDescription(type = Type.ADDITION)
	public void addRecipe(Object... objects)
	{
		addRecipeInternal(objects);
	}

	@MethodDescription(type = Type.REMOVAL)
	public void removeRecipeWithInput(Object... inputs)
	{
		removeRecipeWithInputInternal(inputs);
	}

	@MethodDescription(type = Type.REMOVAL)
	public void removeRecipeWithOutput(Object... outputs)
	{
		removeRecipeWithOutputInternal(outputs);
	}

	@MethodDescription(type = Type.REMOVAL)
	public void removeAllRecipes()
	{
		removeAllRecipesInternal();
	}
}
