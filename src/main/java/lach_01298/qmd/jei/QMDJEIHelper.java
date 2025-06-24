package lach_01298.qmd.jei;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import lach_01298.qmd.particle.ParticleStack;
import mezz.jei.api.gui.IGuiIngredientGroup;
import mezz.jei.api.ingredients.IIngredients;
import nc.recipe.IngredientSorption;

import java.util.List;

public class QMDJEIHelper
{

	protected static class RecipeParticleInfo
	{
		public int slotIndex;
		public int stackX;
		public int stackY;

		public RecipeParticleInfo(int slotIndex, int stackX, int stackY)
		{
			this.slotIndex = slotIndex;
			this.stackX = stackX;
			this.stackY = stackY;
		}
	}


	public static class RecipeParticleMapper extends RecipeIngredientMapper<IGuiIngredientGroup<ParticleStack>, RecipeParticleInfo>
	{

		public void put(IngredientSorption type, int groupIndex, int slotIndex, int stackX, int stackY)
		{
			put(type, groupIndex, new RecipeParticleInfo(slotIndex, stackX, stackY));
		}

		@Override
		public void apply(IGuiIngredientGroup<ParticleStack> particles, IIngredients ingredients)
		{
			for (Object2ObjectMap.Entry<IngredientSorption, Int2ObjectMap<RecipeParticleInfo>> entry : internal.object2ObjectEntrySet())
			{
				boolean isInput = entry.getKey().equals(IngredientSorption.INPUT);
				List<List<ParticleStack>> stackLists = isInput ? ingredients.getInputs(ParticleStack.class) : ingredients.getOutputs(ParticleStack.class);

				for (Int2ObjectMap.Entry<RecipeParticleInfo> mapping : entry.getValue().int2ObjectEntrySet())
				{
					RecipeParticleInfo info = mapping.getValue();
					particles.init(info.slotIndex, isInput, info.stackX - 1, info.stackY - 1);
					particles.set(info.slotIndex, stackLists.get(mapping.getIntKey()));
				}
			}
		}
	}

	protected static abstract class RecipeIngredientMapper<T, INFO>
	{

		public Object2ObjectMap<IngredientSorption, Int2ObjectMap<INFO>> internal = new Object2ObjectOpenHashMap<>();

		protected RecipeIngredientMapper()
		{
			internal.put(IngredientSorption.INPUT, new Int2ObjectOpenHashMap<>());
			internal.put(IngredientSorption.OUTPUT, new Int2ObjectOpenHashMap<>());
		}

		protected void put(IngredientSorption type, int groupIndex, INFO info)
		{
			internal.get(type).put(groupIndex, info);
		}

		public abstract void apply(T group, IIngredients ingredients);
	}


}
