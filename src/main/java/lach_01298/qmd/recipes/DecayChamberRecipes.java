package lach_01298.qmd.recipes;

import java.util.ArrayList;
import java.util.List;

import lach_01298.qmd.particle.ParticleStack;
import lach_01298.qmd.particle.Particles;
import lach_01298.qmd.recipe.QMDRecipeHandler;
import lach_01298.qmd.recipe.ingredient.EmptyParticleIngredient;
import lach_01298.qmd.recipe.ingredient.IParticleIngredient;
import lach_01298.qmd.recipe.ingredient.ParticleIngredient;

public class DecayChamberRecipes extends QMDRecipeHandler
{
	public DecayChamberRecipes()
	{
		super("decay_chamber", 0, 0, 1, 0, 0, 3);
		
	}

	@Override
	public void addRecipes()
	{
		//addRecipe(inputParticle(amount,minEnergy,minFocus), outputParticle+(amount), outputParticle0(amount), outputParticle-(amount),maxEnergy, crossSection, energyReleased, radiation)
		
		
		addDecayRecipe(new ParticleStack(Particles.neutron), new ParticleStack(Particles.proton), new ParticleStack(Particles.electron_antineutrino),new ParticleStack(Particles.electron), 1.0);
		
		addDecayRecipe(new ParticleStack(Particles.pion_naught), null,new ParticleStack(Particles.photon,2), null, 0.98);
		addDecayRecipe(new ParticleStack(Particles.pion_plus), new ParticleStack(Particles.antimuon), new ParticleStack(Particles.muon_neutrino),null, 0.99);
		addDecayRecipe(new ParticleStack(Particles.pion_minus), null,new ParticleStack(Particles.muon_antineutrino), new ParticleStack(Particles.muon), 0.99);
		
		addDecayRecipe(new ParticleStack(Particles.muon), new ParticleStack(Particles.electron_antineutrino), new ParticleStack(Particles.muon_neutrino), new ParticleStack(Particles.electron), 1.0);
		addDecayRecipe(new ParticleStack(Particles.antimuon), new ParticleStack(Particles.positron), new ParticleStack(Particles.muon_antineutrino), new ParticleStack(Particles.electron_neutrino), 1.0);
		
		addDecayRecipe(new ParticleStack(Particles.tau), new ParticleStack(Particles.pion_naught), new ParticleStack(Particles.tau_neutrino), new ParticleStack(Particles.pion_minus), 0.25);
		addDecayRecipe(new ParticleStack(Particles.antitau), new ParticleStack(Particles.pion_plus), new ParticleStack(Particles.tau_antineutrino), new ParticleStack(Particles.pion_naught), 0.25);
		
		addDecayRecipe(new ParticleStack(Particles.kaon_plus), new ParticleStack(Particles.antimuon), new ParticleStack(Particles.muon_neutrino), null, 0.63);
		addDecayRecipe(new ParticleStack(Particles.kaon_minus), null, new ParticleStack(Particles.muon_antineutrino), new ParticleStack(Particles.muon), 0.63);
		addDecayRecipe(new ParticleStack(Particles.kaon_naught), new ParticleStack(Particles.pion_plus), null, new ParticleStack(Particles.pion_minus), 0.77);
		
		addDecayRecipe(new ParticleStack(Particles.higgs_boson), new ParticleStack(Particles.antibottom), null, new ParticleStack(Particles.bottom), 0.57);
			
		addDecayRecipe(new ParticleStack(Particles.w_minus_boson), null, null, new ParticleStack(Particles.pion_minus) , 0.32);
		addDecayRecipe(new ParticleStack(Particles.w_plus_boson), new ParticleStack(Particles.pion_plus), null, null, 0.32);
		
		addDecayRecipe(new ParticleStack(Particles.z_boson), new ParticleStack(Particles.electron_neutrino), null, new ParticleStack(Particles.electron_antineutrino) , 0.068);
		
		
	}
	
	
	public void addDecayRecipe(ParticleStack particleIn, ParticleStack particleOut1, ParticleStack particleOut2, ParticleStack particleOut3, double crossSection)
	{
		double outputMass =0;
		IParticleIngredient p1;
		IParticleIngredient p2;
		IParticleIngredient p3;
		
		if(particleOut1 != null)
		{
			outputMass +=  particleOut1.getParticle().getMass();
			p1 = new ParticleIngredient(particleOut1);
		}
		else
		{
			p1 = new EmptyParticleIngredient();
		}
		
		if(particleOut2 != null)
		{
			outputMass +=  particleOut2.getParticle().getMass();
			p2 = new ParticleIngredient(particleOut2);
		}
		else
		{
			p2 = new EmptyParticleIngredient();
		}
		
		if(particleOut3 != null)
		{
			outputMass +=  particleOut3.getParticle().getMass();
			p3 = new ParticleIngredient(particleOut3);
		}
		else
		{
			p3 = new EmptyParticleIngredient();
		}
		
		
		long energyReleased = (long)((particleIn.getParticle().getMass() - outputMass) * 1000);
		addRecipe(particleIn, p1, p2, p3, Long.MAX_VALUE, crossSection, energyReleased);	
	}

	@Override
	public List fixExtras(List extras)
	{
		List fixed = new ArrayList(4);
		fixed.add(extras.size() > 0 && extras.get(0) instanceof Long ? (long) extras.get(0) : Long.MAX_VALUE);
		fixed.add(extras.size() > 1 && extras.get(1) instanceof Double ? (double) extras.get(1) : 1D);
		fixed.add(extras.size() > 2 && extras.get(2) instanceof Long ? (long) extras.get(2) : 0l);
		fixed.add(extras.size() > 3 && extras.get(3) instanceof Double ? (double) extras.get(3) : 0D);
		return fixed;
	}
	
	
	
}
