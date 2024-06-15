package lach_01298.qmd.crafttweaker.particle;

import crafttweaker.api.item.*;
import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.api.player.IPlayer;
import lach_01298.qmd.particle.ParticleStack;
import nc.util.Lang;

import java.util.List;

public class CTParticleStack implements IParticleStack
{

    private final ParticleStack stack;

    public CTParticleStack(ParticleStack stack)
    {
        this.stack = stack;
    }
    
    public CTParticleStack()
    {
        this.stack = new ParticleStack();
    }


    @Override
    public IParticleDefinition getDefinition()
    {
        return new CTParticleDefinition(stack.getParticle());
    }

    @Override
    public String getName()
    {
        return stack.getParticle().getName();
    }

    @Override
    public String getDisplayName()
    {
        return Lang.localize(stack.getParticle().getUnlocalizedName());
    }

    @Override
    public String getMark()
    {
        return null;
    }

    @Override
    public int getAmount()
    {
        return stack.getAmount();
    }
    
	@Override
	public long getMeanEnergy()
	{
		return stack.getMeanEnergy();
	}

	@Override
	public double getFocus()
	{
		return stack.getFocus();
	}

    @Override
    public List<IItemStack> getItems()
    {
        return null;
    }

    @Override
    public IItemStack[] getItemArray()
    {
        return new IItemStack[0];
    }

    @Override
    public List<ILiquidStack> getLiquids()
    {
        return null;
    }

    @Override
    public IIngredient amount(int amount)
    {
        return withAmount(amount);
    }

    @Override
    public IIngredient or(IIngredient iIngredient)
    {
        return null;
    }

    @Override
    public IIngredient transform(IItemTransformer iItemTransformer)
    {
        return null;
    }

    @Override
    public IIngredient only(IItemCondition iItemCondition)
    {
        return null;
    }

    @Override
    public IIngredient marked(String s)
    {
        return null;
    }

    @Override
    public boolean matches(IItemStack iItemStack)
    {
        return false;
    }

    @Override
    public boolean matchesExact(IItemStack iItemStack)
    {
        return false;
    }

    @Override
    public boolean matches(ILiquidStack iLiquidStack)
    {
        return false;
    }

    @Override
    public boolean contains(IIngredient iIngredient)
    {
        return false;
    }

    @Override
    public IItemStack applyTransform(IItemStack iItemStack, IPlayer iPlayer)
    {
        return null;
    }

    @Override
    public boolean hasTransformers()
    {
        return false;
    }

    @Override
    public IParticleStack withAmount(int amount)
    {
        return new CTParticleStack(new ParticleStack(stack.getParticle(), amount, stack.getMeanEnergy(), stack.getFocus()));
    }

    @Override
	public IParticleStack withEnergy(long energy)
	{
    	return new CTParticleStack(new ParticleStack(stack.getParticle(),stack.getAmount(), energy, stack.getFocus()));
	}


	@Override
	public IParticleStack withFocus(double focus)
	{
    	return new CTParticleStack(new ParticleStack(stack.getParticle(), stack.getAmount(), stack.getMeanEnergy(), focus));
	}
    
    @Override
    public Object getInternal()
    {
        return stack;
    }

    @Override
    public String toString()
    {
        return String.format("<particle:%s[%dpu,%dkeV,%f]>", stack.getParticle().getName(), stack.getAmount(),stack.getMeanEnergy(),stack.getFocus());
    }


	@Override
	public IIngredient transformNew(IItemTransformerNew transformer)
	{
		return null;
	}


	@Override
	public IItemStack applyNewTransform(IItemStack item)
	{
		return null;
	}


	@Override
	public boolean hasNewTransformers()
	{
		return false;
	}


	@Override
	public String toCommandString()
	{
		return null;
	}


	




	
}
