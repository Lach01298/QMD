package lach_01298.qmd.groovyscript.particle;

import lach_01298.qmd.particle.ParticleStack;

import java.util.ArrayList;
import java.util.Collection;

public class ParticleStackList extends ArrayList<ParticleStack>
{

	public ParticleStackList()
	{
	}

	public ParticleStackList(Collection<ParticleStack> collection)
	{
		super(collection);
	}

	public ParticleStack getOrEmpty(int i)
	{
		if (i < 0 || i >= size())
		{
			return null;
		}
		return get(i);
	}

	public int getRealSize()
	{
		if (isEmpty()) return 0;
		int realSize = 0;
		for (ParticleStack t : this)
		{
			if (!QMDIngredientHelper.isEmpty(t)) realSize++;
		}
		return realSize;
	}

	public void trim()
	{
		if (!isEmpty())
		{
			removeIf(QMDIngredientHelper::isEmpty);
		}
	}

	public void copyElements()
	{
		for (int i = 0; i < size(); i++)
		{
			ParticleStack gasStack = get(i);
			if (!QMDIngredientHelper.isEmpty(gasStack))
			{
				set(i, gasStack.copy());
			}
		}
	}
}