package lach_01298.qmd.recipe;

import lach_01298.qmd.particle.ParticleStack;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import org.apache.commons.lang3.tuple.Triple;

import java.util.*;

public class QMDRecipeTupleGenerator
{
	
	public static final QMDRecipeTupleGenerator INSTANCE = new QMDRecipeTupleGenerator();
	
	private QMDRecipeTupleGenerator() {}
	
	private boolean itemEnd, fluidEnd, particleEnd;
	
	public void generateMaterialListTuples(List<Triple<List<ItemStack>, List<FluidStack>, List<ParticleStack>>> tuples, int[] maxNumbers, int[] inputNumbers, List<List<ItemStack>> itemInputLists, List<List<FluidStack>> fluidInputLists, List<List<ParticleStack>> particleInputLists) {
		do
		{
			generateNextMaterialListTuple(tuples, maxNumbers, inputNumbers, itemInputLists, fluidInputLists, particleInputLists);
		}
		while (!itemEnd || !fluidEnd || !particleEnd);
	}
	
	private void generateNextMaterialListTuple(List<Triple<List<ItemStack>, List<FluidStack>, List<ParticleStack>>> tuples, int[] maxNumbers, int[] inputNumbers, List<List<ItemStack>> itemInputLists, List<List<FluidStack>> fluidInputLists, List<List<ParticleStack>> particleInputLists) {
		int itemInputSize = itemInputLists.size(), fluidInputSize = fluidInputLists.size(), particleInputSize = particleInputLists.size();
		
		List<ItemStack> itemInputs = new ArrayList<>();
		List<FluidStack> fluidInputs = new ArrayList<>();
		List<ParticleStack> particleInputs = new ArrayList<>();
		
		for (int i = 0; i < itemInputSize; i++)
		{
			itemInputs.add(itemInputLists.get(i).get(inputNumbers[i]));
		}
		
		for (int i = 0; i < fluidInputSize; i++)
		{
			fluidInputs.add(fluidInputLists.get(i).get(inputNumbers[i + itemInputSize]));
		}
		for (int i = 0; i < particleInputSize; i++)
		{
			particleInputs.add(particleInputLists.get(i).get(inputNumbers[i + itemInputSize+ fluidInputSize]));
		}
		
		tuples.add(Triple.of(itemInputs, fluidInputs,particleInputs));
		
		itemEnd = false;
		if (itemInputSize == 0)
		{
			itemEnd = true;
		}
		else
		{
			for (int i = 0; i < itemInputSize; i++)
			{
				if (inputNumbers[i] < maxNumbers[i])
				{
					inputNumbers[i]++;
					break;
				}
				else
				{
					inputNumbers[i] = 0;
					if (i == itemInputSize - 1)
						itemEnd = true;
				}
			}
		}
		
		fluidEnd = false;
		if (fluidInputSize == 0)
		{
			fluidEnd = true;
		}
		else if (itemEnd)
		{
			for (int i = 0; i < fluidInputSize; i++)
			{
				if (inputNumbers[i + itemInputSize] < maxNumbers[i + itemInputSize])
				{
					inputNumbers[i + itemInputSize]++;
					break;
				}
				else
				{
					inputNumbers[i + itemInputSize] = 0;
					if (i == fluidInputSize - 1)
						fluidEnd = true;
				}
			}
		}
		
		particleEnd = false;
		if (particleInputSize == 0)
		{
			particleEnd = true;
		}
		else if (itemEnd && fluidEnd)
		{
			for (int i = 0; i < particleInputSize; i++)
			{
				if (inputNumbers[i + itemInputSize+fluidInputSize] < maxNumbers[i + itemInputSize+fluidInputSize])
				{
					inputNumbers[i + itemInputSize+fluidInputSize]++;
					break;
				}
				else
				{
					inputNumbers[i + itemInputSize] = 0;
					if (i == particleInputSize - 1)
						particleEnd = true;
				}
			}
		}
		else if (itemEnd && !fluidEnd)
		{
			for (int i = 0; i < particleInputSize; i++)
			{
				if (inputNumbers[i + itemInputSize+fluidInputSize] < maxNumbers[i + itemInputSize+fluidInputSize])
				{
					inputNumbers[i + itemInputSize+fluidInputSize]++;
					break;
				}
				else
				{
					inputNumbers[i + itemInputSize+fluidInputSize] = 0;
					if (i == particleInputSize - 1)
						particleEnd = true;
				}
			}
		}
		else if (!itemEnd && fluidEnd)
		{
			for (int i = 0; i < particleInputSize; i++)
			{
				if (inputNumbers[i + itemInputSize+fluidInputSize] < maxNumbers[i + itemInputSize+fluidInputSize])
				{
					inputNumbers[i + itemInputSize+fluidInputSize]++;
					break;
				}
				else
				{
					inputNumbers[i + itemInputSize+fluidInputSize] = 0;
					if (i == particleInputSize - 1)
						particleEnd = true;
				}
			}
		}
	}
}
