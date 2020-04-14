package lach_01298.qmd.crafttweaker.particle;

import java.util.List;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.BracketHandler;
import crafttweaker.annotations.ModOnly;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IngredientAny;
import crafttweaker.zenscript.IBracketHandler;
import lach_01298.qmd.particle.Particle;
import lach_01298.qmd.particle.ParticleStack;
import lach_01298.qmd.particle.Particles;
import stanhebben.zenscript.compiler.IEnvironmentGlobal;
import stanhebben.zenscript.expression.ExpressionCallStatic;
import stanhebben.zenscript.expression.ExpressionString;
import stanhebben.zenscript.expression.partial.IPartialExpression;
import stanhebben.zenscript.parser.Token;
import stanhebben.zenscript.symbols.IZenSymbol;
import stanhebben.zenscript.type.natives.IJavaMethod;
import stanhebben.zenscript.util.ZenPosition;

@BracketHandler(priority = 100)
@ZenRegister
public class ParticleBracketHandler implements IBracketHandler 
{

    private final IZenSymbol symbolAny;
    private final IJavaMethod method;

    public ParticleBracketHandler() 
    {
        this.symbolAny = CraftTweakerAPI.getJavaStaticFieldSymbol(IngredientAny.class, "INSTANCE");
        this.method = CraftTweakerAPI.getJavaMethod(ParticleBracketHandler.class, "getParticle", String.class);
    }

    public static IParticleStack getParticle(String name) 
    {
        Particle particle = Particles.getParticleFromName(name);
        if (particle != null) 
        {
            return new CTParticleStack(new ParticleStack(particle));
        } 
        else 
        {
            return null;
        }
    }

    @Override
    public IZenSymbol resolve(IEnvironmentGlobal environment, List<Token> tokens) 
    {
    	if (tokens.size() == 1 && tokens.get(0).getValue().equals("*")) 
        {
            return symbolAny;
        }

        if (tokens.size() > 2) 
        {
            if (tokens.get(0).getValue().equals("particle") && tokens.get(1).getValue().equals(":")) 
            {
                return find(environment, tokens, 2, tokens.size());
            }
        }

        return null;
    }

    private IZenSymbol find(IEnvironmentGlobal environment, List<Token> tokens, int startIndex, int endIndex) 
    {
        StringBuilder valueBuilder = new StringBuilder();
        for (int i = startIndex; i < endIndex; i++) 
        {
            Token token = tokens.get(i);
            valueBuilder.append(token.getValue());
        }

        Particle particle = Particles.getParticleFromName(valueBuilder.toString());
        if (particle != null) 
        {
            return new ParticleReferenceSymbol(environment, valueBuilder.toString());
        }

        return null;
    }

    private class ParticleReferenceSymbol implements IZenSymbol 
    {

        private final IEnvironmentGlobal environment;
        private final String name;

        public ParticleReferenceSymbol(IEnvironmentGlobal environment, String name) 
        {
            this.environment = environment;
            this.name = name;
        }

        @Override
        public IPartialExpression instance(ZenPosition zenPosition) 
        {
            return new ExpressionCallStatic(zenPosition, environment, method, new ExpressionString(zenPosition, name));
        }
    }
}