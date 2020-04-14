package lach_01298.qmd.crafttweaker.particle;

import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.OperatorType;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenOperator;

@ZenClass("mod.qmd.particle.IParticleDefinition")
@ModOnly("mtlib")
@ZenRegister
public interface IParticleDefinition 
{

    @ZenGetter("NAME")
    String getName();

    @ZenGetter("displayName")
    String getDisplayName();
    
    @ZenGetter("mass")
    double getMass();
    
    @ZenGetter("charge")
    double getCharge();
    
    @ZenGetter("spin")
    double getSpin();
    
}