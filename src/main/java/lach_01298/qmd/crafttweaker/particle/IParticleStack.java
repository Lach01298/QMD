package lach_01298.qmd.crafttweaker.particle;

import crafttweaker.annotations.*;
import crafttweaker.api.item.IIngredient;
import stanhebben.zenscript.annotations.*;

@ZenClass("mod.qmd.particle.IParticleStack")
@ModOnly("mtlib")
@ZenRegister
public interface IParticleStack extends IIngredient
{

    @ZenGetter("definition")
    IParticleDefinition getDefinition();

    @ZenGetter("NAME")
    String getName();

    @ZenGetter("displayName")
    String getDisplayName();

   
    @ZenGetter("meanEnergy")
    long getMeanEnergy();

    @ZenGetter("amount")
    int getAmount();
    
    @ZenGetter("focus")
    double getFocus();
    
    @ZenOperator(OperatorType.MUL)
    @ZenMethod
    IParticleStack withAmount(int amount);
    
    @ZenOperator(OperatorType.XOR)
    @ZenMethod
    IParticleStack withEnergy(long energy);
    
    @ZenOperator(OperatorType.CAT)
    @ZenMethod
    IParticleStack withFocus(double focus);

    Object getInternal();
}
