package lach_01298.qmd.recipes;

import lach_01298.qmd.particle.*;
import lach_01298.qmd.recipe.QMDRecipeHandler;
import lach_01298.qmd.recipe.ingredient.*;
import nc.recipe.BasicRecipeHandler;
import nc.util.NCMath;

import javax.annotation.Nonnull;
import java.util.*;
import lach_01298.qmd.util.Util;

public class CollisionChamberRecipes extends QMDRecipeHandler
{
	public CollisionChamberRecipes()
	{
		super("collision_chamber", 0, 0, 2, 0, 0, 4);
		
	}

	@Override
	public void addRecipes()
	{
		
		
		//neutron absorption
		addCollisionRecipe(new ParticleStack(Particles.proton,1,0,5),new ParticleStack(Particles.neutron,1,0,5), new ParticleStack(Particles.deuteron),new ParticleStack(Particles.photon),null,null,0.5,0,2220,30000);
		addCollisionRecipe(new ParticleStack(Particles.antiproton,1,0,5),new ParticleStack(Particles.antineutron,1,0,5), new ParticleStack(Particles.antideuteron),new ParticleStack(Particles.photon),null,null,0.5,0,2220,30000);
		
		addCollisionRecipe(new ParticleStack(Particles.deuteron,1,0,5),new ParticleStack(Particles.neutron,1,0,5), new ParticleStack(Particles.triton),new ParticleStack(Particles.photon),null,null,0.5,0, 6260,30000);
		addCollisionRecipe(new ParticleStack(Particles.antideuteron,1,0,5),new ParticleStack(Particles.antineutron,1,0,5), new ParticleStack(Particles.antitriton),new ParticleStack(Particles.photon),null,null,0.5,0, 6260,30000);
		
		addCollisionRecipe(new ParticleStack(Particles.helion,1,0,5),new ParticleStack(Particles.neutron,1,0,5), new ParticleStack(Particles.alpha),new ParticleStack(Particles.photon),null,null,0.5,0, 20600,30000);
		addCollisionRecipe(new ParticleStack(Particles.antihelion,1,0,5),new ParticleStack(Particles.antineutron,1,0,5), new ParticleStack(Particles.antialpha),new ParticleStack(Particles.photon),null,null,0.5,0, 20600,30000);
		
		//fusion
		addCollisionRecipe(new ParticleStack(Particles.proton,1,0,5),new ParticleStack(Particles.proton,1,0,5), new ParticleStack(Particles.deuteron),new ParticleStack(Particles.positron),new ParticleStack(Particles.electron_neutrino),null,0.25,700,420,10000);
		addCollisionRecipe(new ParticleStack(Particles.antiproton,1,0,5),new ParticleStack(Particles.antiproton,1,0,5), new ParticleStack(Particles.antideuteron),new ParticleStack(Particles.electron),new ParticleStack(Particles.electron_antineutrino),null,0.25,700,420,10000);
		
		addCollisionRecipe(new ParticleStack(Particles.proton,1,0,5),new ParticleStack(Particles.deuteron,1,0,5), new ParticleStack(Particles.helion),new ParticleStack(Particles.photon),null,null,0.5,700,5490,10000);
		addCollisionRecipe(new ParticleStack(Particles.antiproton,1,0,5),new ParticleStack(Particles.antideuteron,1,0,5), new ParticleStack(Particles.antihelion),new ParticleStack(Particles.photon),null,null,0.5,700, 5490,10000);
		
		addCollisionRecipe(new ParticleStack(Particles.helion,1,0,5),new ParticleStack(Particles.helion,1,0,5), new ParticleStack(Particles.alpha),new ParticleStack(Particles.proton,2),null,null,0.5,700,12900,10000);
		addCollisionRecipe(new ParticleStack(Particles.antihelion,1,0,5),new ParticleStack(Particles.antihelion,1,0,5), new ParticleStack(Particles.antialpha),new ParticleStack(Particles.antiproton,2),null,null,0.5,700,12900,10000);
		
		addCollisionRecipe(new ParticleStack(Particles.deuteron,1,0,5),new ParticleStack(Particles.triton,1,0,5), new ParticleStack(Particles.alpha),new ParticleStack(Particles.neutron),null,null,0.5,700,17600,10000);
		addCollisionRecipe(new ParticleStack(Particles.antideuteron,1,0,5),new ParticleStack(Particles.antitriton,1,0,5), new ParticleStack(Particles.antialpha),new ParticleStack(Particles.antineutron),null,null,0.5,700,17600,10000);
		
		addCollisionRecipe(new ParticleStack(Particles.triton,1,0,5),new ParticleStack(Particles.triton,1,0,5), new ParticleStack(Particles.alpha),new ParticleStack(Particles.neutron,2),null,null,0.5,700,11300,10000);
		addCollisionRecipe(new ParticleStack(Particles.antitriton,1,0,5),new ParticleStack(Particles.antitriton,1,0,5), new ParticleStack(Particles.antialpha),new ParticleStack(Particles.antineutron,2),null,null,0.5,700,11300,10000);
		
		addCollisionRecipe(new ParticleStack(Particles.deuteron,1,0,5),new ParticleStack(Particles.helion,1,0,5), new ParticleStack(Particles.alpha),new ParticleStack(Particles.proton),null,null,0.5,700,18400,10000);
		addCollisionRecipe(new ParticleStack(Particles.antideuteron,1,0,5),new ParticleStack(Particles.antihelion,1,0,5), new ParticleStack(Particles.antialpha),new ParticleStack(Particles.antiproton),null,null,0.5,700,18400,10000);
		
		addCollisionRecipe(new ParticleStack(Particles.helion,1,0,5),new ParticleStack(Particles.triton,1,0,5), new ParticleStack(Particles.alpha),new ParticleStack(Particles.proton),new ParticleStack(Particles.neutron),null,0.5,700,12100,10000);
		addCollisionRecipe(new ParticleStack(Particles.antihelion,1,0,5),new ParticleStack(Particles.antitriton,1,0,5), new ParticleStack(Particles.antialpha),new ParticleStack(Particles.antiproton),new ParticleStack(Particles.antineutron),null,0.5,700,12100,10000);
		
		addCollisionRecipe(new ParticleStack(Particles.deuteron,1,0,5),new ParticleStack(Particles.deuteron,1,0,5), new ParticleStack(Particles.triton),new ParticleStack(Particles.proton),null,null,0.5,700, 4030,10000);
		addCollisionRecipe(new ParticleStack(Particles.antideuteron,1,0,5),new ParticleStack(Particles.antideuteron,1,0,5), new ParticleStack(Particles.antitriton),new ParticleStack(Particles.antiproton),null,null,0.5,700, 4030,10000);

		// antimatter annihilation
		addCollisionRecipe(new ParticleStack(Particles.proton,1,0,5),new ParticleStack(Particles.antiproton,1,0,5), new ParticleStack(Particles.pion_plus,4),new ParticleStack(Particles.pion_naught,4),new ParticleStack(Particles.pion_minus,4),null,1.0,0,220000,50000000);
		addCollisionRecipe(new ParticleStack(Particles.neutron,1,0,5),new ParticleStack(Particles.antineutron,1,0,5), new ParticleStack(Particles.pion_plus,4),new ParticleStack(Particles.pion_naught,4),new ParticleStack(Particles.pion_minus,4),null,1.0,0,223000,50000000);

		addCollisionRecipe(new ParticleStack(Particles.proton,1,0,5),new ParticleStack(Particles.antineutron,1,0,5), new ParticleStack(Particles.pion_plus,5),new ParticleStack(Particles.pion_naught,4),new ParticleStack(Particles.pion_minus,4),null,1.0,0,81800,50000000);
		addCollisionRecipe(new ParticleStack(Particles.antiproton,1,0,5),new ParticleStack(Particles.neutron,1,0,5), new ParticleStack(Particles.pion_plus,4),new ParticleStack(Particles.pion_naught,4),new ParticleStack(Particles.pion_minus,5),null,1.0,0,81800,50000000);
		
		// High energy collisions
		addCollisionRecipe(new ParticleStack(Particles.electron,1,0,5),new ParticleStack(Particles.electron,1,0,5), new ParticleStack(Particles.electron_neutrino,2),new ParticleStack(Particles.muon,2),new ParticleStack(Particles.muon_antineutrino,2),null,0.025);
		addCollisionRecipe(new ParticleStack(Particles.positron,1,0,5),new ParticleStack(Particles.positron,1,0,5), new ParticleStack(Particles.electron_antineutrino,2),new ParticleStack(Particles.antimuon,2),new ParticleStack(Particles.muon_neutrino,2),null,0.025);
		
		addCollisionRecipe(new ParticleStack(Particles.electron,1,0,5),new ParticleStack(Particles.positron,1,0,5), new ParticleStack(Particles.muon),new ParticleStack(Particles.antimuon),null,null,0.10);
		
		addCollisionRecipe(new ParticleStack(Particles.proton,1,0,5),new ParticleStack(Particles.proton,1,0,5), new ParticleStack(Particles.proton,2),new ParticleStack(Particles.pion_plus),new ParticleStack(Particles.pion_minus),new ParticleStack(Particles.pion_naught),0.10);
		addCollisionRecipe(new ParticleStack(Particles.antiproton,1,0,5),new ParticleStack(Particles.antiproton,1,0,5), new ParticleStack(Particles.antiproton,2),new ParticleStack(Particles.pion_minus),new ParticleStack(Particles.pion_plus),new ParticleStack(Particles.pion_naught),0.10);
		
		addCollisionRecipe(new ParticleStack(Particles.proton,1,0,5),new ParticleStack(Particles.proton,1,0,5), new ParticleStack(Particles.delta_minus,1),new ParticleStack(Particles.sigma_plus),new ParticleStack(Particles.kaon_plus),new ParticleStack(Particles.pion_plus),0.025);
		addCollisionRecipe(new ParticleStack(Particles.antiproton,1,0,5),new ParticleStack(Particles.antiproton,1,0,5), new ParticleStack(Particles.antidelta_minus,1),new ParticleStack(Particles.antisigma_plus),new ParticleStack(Particles.kaon_minus),new ParticleStack(Particles.pion_minus),0.025);
		
		addCollisionRecipe(new ParticleStack(Particles.electron,1,0,5),new ParticleStack(Particles.positron,1,0,5), new ParticleStack(Particles.tau),new ParticleStack(Particles.antitau),null,null,0.025);

		addCollisionRecipe(new ParticleStack(Particles.proton,1,0,5),new ParticleStack(Particles.proton,1,0,5), new ParticleStack(Particles.neutron,1),new ParticleStack(Particles.delta_plus_plus),new ParticleStack(Particles.z_boson),null,0.025);
		addCollisionRecipe(new ParticleStack(Particles.antiproton,1,0,5),new ParticleStack(Particles.antiproton,1,0,5), new ParticleStack(Particles.antineutron,1),new ParticleStack(Particles.antidelta_plus_plus),new ParticleStack(Particles.z_boson),null,0.025);

		addCollisionRecipe(new ParticleStack(Particles.proton,1,0,5),new ParticleStack(Particles.antiproton,1,0,5), new ParticleStack(Particles.glueball),new ParticleStack(Particles.w_plus_boson),new ParticleStack(Particles.w_minus_boson),new ParticleStack(Particles.charmed_eta),0.025);
		
		addCollisionRecipe(new ParticleStack(Particles.proton,1,0,5),new ParticleStack(Particles.proton,1,0,5), new ParticleStack(Particles.higgs_boson,2),new ParticleStack(Particles.delta_plus_plus),new ParticleStack(Particles.kaon_plus),new ParticleStack(Particles.sigma_minus),0.025);
		addCollisionRecipe(new ParticleStack(Particles.antiproton,1,0,5),new ParticleStack(Particles.antiproton,1,0,5), new ParticleStack(Particles.higgs_boson,2),new ParticleStack(Particles.antidelta_plus_plus),new ParticleStack(Particles.kaon_minus),new ParticleStack(Particles.antisigma_minus),0.025);
		
		
		

		
		

		
		

		
	}
	
	public void addCollisionRecipe(@Nonnull ParticleStack particleIn1,@Nonnull ParticleStack particleIn2, ParticleStack particleOut1, ParticleStack particleOut2, ParticleStack particleOut3,ParticleStack particleOut4, double crossSection, long minEnergy, long energyReleased, long maxEnergy)
	{
		IParticleIngredient in1;
		IParticleIngredient in2;
		IParticleIngredient out1;
		IParticleIngredient out2;
		IParticleIngredient out3;
		IParticleIngredient out4;
		
		if(particleOut1 != null)
		{
			out1 = new ParticleIngredient(particleOut1);
		}
		else
		{
			out1 = new EmptyParticleIngredient();
		}
		
		if(particleOut2 != null)
		{
			out2 = new ParticleIngredient(particleOut2);
		}
		else
		{
			out2 = new EmptyParticleIngredient();
		}
		
		if(particleOut3 != null)
		{
			out3 = new ParticleIngredient(particleOut3);
		}
		else
		{
			out3 = new EmptyParticleIngredient();
		}
		
		if(particleOut4 != null)
		{
			out4 = new ParticleIngredient(particleOut4);
		}
		else
		{
			out4 = new EmptyParticleIngredient();
		}

		particleIn1.setMeanEnergy(minEnergy);
		particleIn2.setMeanEnergy(minEnergy);
	
		in1 = new ParticleIngredient(particleIn1);
		in2 = new ParticleIngredient(particleIn2);
		

		addRecipe(in1, in2, out1, out2, out3, out4, maxEnergy, crossSection, energyReleased);
	}
	
	
	
	public void addCollisionRecipe(@Nonnull ParticleStack particleIn1,@Nonnull ParticleStack particleIn2, ParticleStack particleOut1, ParticleStack particleOut2, ParticleStack particleOut3,ParticleStack particleOut4, double crossSection)
	{
		
		IParticleIngredient in1;
		IParticleIngredient in2;
		IParticleIngredient out1;
		IParticleIngredient out2;
		IParticleIngredient out3;
		IParticleIngredient out4;
		
		
		double inputMass =0;
		double outputMass =0;
		
		if(particleOut1 != null)
		{
			outputMass +=  particleOut1.getParticle().getMass() * particleOut1.getAmount();
			out1 = new ParticleIngredient(particleOut1);
		}
		else
		{
			out1 = new EmptyParticleIngredient();
		}
		
		if(particleOut2 != null)
		{
			outputMass +=  particleOut2.getParticle().getMass() * particleOut2.getAmount();
			out2 = new ParticleIngredient(particleOut2);
		}
		else
		{
			out2 = new EmptyParticleIngredient();
		}
		
		if(particleOut3 != null)
		{
			outputMass +=  particleOut3.getParticle().getMass() * particleOut3.getAmount();
			out3 = new ParticleIngredient(particleOut3);
		}
		else
		{
			out3 = new EmptyParticleIngredient();
		}
		
		if(particleOut4 != null)
		{
			outputMass +=  particleOut4.getParticle().getMass() * particleOut4.getAmount();
			out4 = new ParticleIngredient(particleOut4);
		}
		else
		{
			out4 = new EmptyParticleIngredient();
		}
		
		
		inputMass = particleIn1.getParticle().getMass() * particleIn1.getAmount()+ particleIn2.getParticle().getMass() * particleIn2.getAmount();
		long energyReleased = (long)(Util.roundToSigFigs((inputMass - outputMass) * 1000,3));
		long minEnergy = (long) (Util.roundToSigFigs(Math.abs((inputMass - outputMass) * 1000)*1.1,3)); //just an arbitrary amount more energy than the minimum possible
		long maxEnergy = (long) (Util.roundToSigFigs(minEnergy*1.5,3));

		particleIn1.setMeanEnergy(minEnergy);
		particleIn2.setMeanEnergy(minEnergy);
	
		in1 = new ParticleIngredient(particleIn1);
		in2 = new ParticleIngredient(particleIn2);
		

		addRecipe(in1, in2, out1, out2, out3, out4, maxEnergy, crossSection, energyReleased);
		
	}
	
	@Override
	public List fixedExtras(List extras)
	{
		BasicRecipeHandler.ExtrasFixer fixer = new BasicRecipeHandler.ExtrasFixer(extras);
		fixer.add(Long.class, 0l); 		// max energy
		fixer.add(Double.class, 1D);		// cross section
		fixer.add(Long.class, 0l);		// energy released

		return fixer.fixed;
	}
	
	
	
}
