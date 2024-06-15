package lach_01298.qmd.crafttweaker.particle;

import crafttweaker.annotations.*;
import stanhebben.zenscript.annotations.*;

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
