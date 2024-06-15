package lach_01298.qmd.recipes;

import lach_01298.qmd.particle.*;
import lach_01298.qmd.recipe.QMDRecipeHandler;
import lach_01298.qmd.recipe.ingredient.*;

import java.util.*;

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
		addDecayRecipe(new ParticleStack(Particles.antineutron), new ParticleStack(Particles.positron), new ParticleStack(Particles.electron_neutrino),new ParticleStack(Particles.antiproton), 1.0);
		
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
		addDecayRecipe(new ParticleStack(Particles.antikaon_naught), new ParticleStack(Particles.pion_plus), null, new ParticleStack(Particles.pion_minus), 0.77);
		
		
		addDecayRecipe(new ParticleStack(Particles.w_minus_boson), null, null, new ParticleStack(Particles.pion_minus) , 0.32);
		addDecayRecipe(new ParticleStack(Particles.w_plus_boson), new ParticleStack(Particles.pion_plus), null, null, 0.32);
		
		addDecayRecipe(new ParticleStack(Particles.z_boson), new ParticleStack(Particles.electron_neutrino), null, new ParticleStack(Particles.electron_antineutrino) , 0.068);
		addDecayRecipe(new ParticleStack(Particles.higgs_boson), null, new ParticleStack(Particles.bottom_eta), null, 0.57);
		
		addDecayRecipe(new ParticleStack(Particles.eta), new ParticleStack(Particles.pion_plus),new ParticleStack(Particles.pion_naught), new ParticleStack(Particles.pion_minus), 0.33);
		addDecayRecipe(new ParticleStack(Particles.eta_prime), new ParticleStack(Particles.pion_plus),new ParticleStack(Particles.eta), new ParticleStack(Particles.pion_minus), 0.33);
		addDecayRecipe(new ParticleStack(Particles.charmed_eta), new ParticleStack(Particles.kaon_naught),null, new ParticleStack(Particles.antikaon_naught), 0.07);
		addDecayRecipe(new ParticleStack(Particles.bottom_eta), new ParticleStack(Particles.antitau),null, new ParticleStack(Particles.tau), 0.08);
		
		addRecipe(new ParticleStack(Particles.triton), new ParticleStack(Particles.helion), new ParticleStack(Particles.electron_antineutrino), new ParticleStack(Particles.electron),Long.MAX_VALUE, 1, 19);
		addRecipe(new ParticleStack(Particles.antitriton), new ParticleStack(Particles.positron), new ParticleStack(Particles.electron_neutrino), new ParticleStack(Particles.antihelion) ,Long.MAX_VALUE, 1, 19);
		
		addDecayRecipe(new ParticleStack(Particles.glueball), new ParticleStack(Particles.kaon_plus), null, new ParticleStack(Particles.kaon_minus), 0.33);
		
		addDecayRecipe(new ParticleStack(Particles.sigma_plus), new ParticleStack(Particles.proton), new ParticleStack(Particles.pion_naught), null, 0.52);
		addDecayRecipe(new ParticleStack(Particles.antisigma_plus), null, new ParticleStack(Particles.pion_naught), new ParticleStack(Particles.antiproton), 0.52);
		
		addDecayRecipe(new ParticleStack(Particles.sigma_minus), null,  new ParticleStack(Particles.neutron), new ParticleStack(Particles.pion_minus), 0.99);
		addDecayRecipe(new ParticleStack(Particles.antisigma_minus),  new ParticleStack(Particles.pion_plus), new ParticleStack(Particles.antineutron), null, 0.99);
		
		addDecayRecipe(new ParticleStack(Particles.delta_plus_plus),  new ParticleStack(Particles.proton), null,  new ParticleStack(Particles.pion_plus), 1.0);
		addDecayRecipe(new ParticleStack(Particles.antidelta_plus_plus),  new ParticleStack(Particles.antiproton), null,  new ParticleStack(Particles.pion_minus), 1.0);
		
		addDecayRecipe(new ParticleStack(Particles.delta_minus),  new ParticleStack(Particles.neutron), null,  new ParticleStack(Particles.pion_minus), 1.0);
		addDecayRecipe(new ParticleStack(Particles.antidelta_minus),  new ParticleStack(Particles.antineutron), null,  new ParticleStack(Particles.pion_plus), 1.0);
		
		addRecipe(new ParticleStack(Particles.photon,1,1124),  new ParticleStack(Particles.electron), new EmptyParticleIngredient(),  new ParticleStack(Particles.positron), 230000L,1.0, -1022L);
		addRecipe(new ParticleStack(Particles.photon,1,233200),  new ParticleStack(Particles.muon), new EmptyParticleIngredient(),  new ParticleStack(Particles.antimuon), Long.MAX_VALUE,0.5, -212000L);
	}
	
	
	public void addDecayRecipe(ParticleStack particleIn, ParticleStack particleOut1, ParticleStack particleOut2, ParticleStack particleOut3, double crossSection)
	{
		double outputMass =0;
		IParticleIngredient p1;
		IParticleIngredient p2;
		IParticleIngredient p3;
		
		if(particleOut1 != null)
		{
			outputMass +=  particleOut1.getParticle().getMass() * particleOut1.getAmount();
			p1 = new ParticleIngredient(particleOut1);
		}
		else
		{
			p1 = new EmptyParticleIngredient();
		}
		
		if(particleOut2 != null)
		{
			outputMass +=  particleOut2.getParticle().getMass() * particleOut2.getAmount();
			p2 = new ParticleIngredient(particleOut2);
		}
		else
		{
			p2 = new EmptyParticleIngredient();
		}
		
		if(particleOut3 != null)
		{
			outputMass +=  particleOut3.getParticle().getMass() * particleOut3.getAmount();
			p3 = new ParticleIngredient(particleOut3);
		}
		else
		{
			p3 = new EmptyParticleIngredient();
		}
		
		
		long energyReleased = (long)((particleIn.getParticle().getMass() * particleIn.getAmount() - outputMass) * 1000);
		addRecipe(particleIn, p1, p2, p3, Long.MAX_VALUE, crossSection, energyReleased);
	}

	@Override
	public List fixedExtras(List extras)
	{
		List fixed = new ArrayList(4);
		fixed.add(extras.size() > 0 && extras.get(0) instanceof Long ? (long) extras.get(0) : Long.MAX_VALUE);
		fixed.add(extras.size() > 1 && extras.get(1) instanceof Double ? (double) extras.get(1) : 1D);
		fixed.add(extras.size() > 2 && extras.get(2) instanceof Long ? (long) extras.get(2) : 0l);
		fixed.add(extras.size() > 3 && extras.get(3) instanceof Double ? (double) extras.get(3) : 0D);
		return fixed;
	}
	
	
	
}
