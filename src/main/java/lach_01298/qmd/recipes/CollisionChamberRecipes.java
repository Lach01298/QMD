package lach_01298.qmd.recipes;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import lach_01298.qmd.particle.ParticleStack;
import lach_01298.qmd.particle.Particles;
import lach_01298.qmd.recipe.QMDRecipeHandler;
import lach_01298.qmd.recipe.ingredient.EmptyParticleIngredient;
import lach_01298.qmd.recipe.ingredient.IParticleIngredient;
import lach_01298.qmd.recipe.ingredient.ParticleIngredient;

public class CollisionChamberRecipes extends QMDRecipeHandler
{
	public CollisionChamberRecipes()
	{
		super("collision_chamber", 0, 0, 2, 0, 0, 4);
		
	}

	@Override
	public void addRecipes()
	{
		
		
		addCollisionRecipe(new ParticleStack(Particles.proton,1,0,5),new ParticleStack(Particles.proton,1,0,5), new ParticleStack(Particles.higgs_boson,2),new ParticleStack(Particles.delta_plus_plus),new ParticleStack(Particles.kaon_plus),new ParticleStack(Particles.sigma_minus),0.01);
		addCollisionRecipe(new ParticleStack(Particles.antiproton,1,0,5),new ParticleStack(Particles.antiproton,1,0,5), new ParticleStack(Particles.higgs_boson,2),new ParticleStack(Particles.antidelta_plus_plus),new ParticleStack(Particles.kaon_minus),new ParticleStack(Particles.antisigma_minus),0.01);
		
		addCollisionRecipe(new ParticleStack(Particles.proton,1,0,5),new ParticleStack(Particles.proton,1,0,5), new ParticleStack(Particles.delta_minus,1),new ParticleStack(Particles.sigma_plus),new ParticleStack(Particles.kaon_plus),new ParticleStack(Particles.pion_plus),0.01);
		addCollisionRecipe(new ParticleStack(Particles.antiproton,1,0,5),new ParticleStack(Particles.antiproton,1,0,5), new ParticleStack(Particles.antidelta_minus,1),new ParticleStack(Particles.antisigma_plus),new ParticleStack(Particles.kaon_minus),new ParticleStack(Particles.pion_minus),0.01);
		
		addCollisionRecipe(new ParticleStack(Particles.electron,1,0,5),new ParticleStack(Particles.positron,1,0,5), new ParticleStack(Particles.tau),new ParticleStack(Particles.antitau),new ParticleStack(Particles.z_boson),null,0.015);

		addCollisionRecipe(new ParticleStack(Particles.proton,1,0,5),new ParticleStack(Particles.antiproton,1,0,5), new ParticleStack(Particles.glueball),new ParticleStack(Particles.w_plus_boson),new ParticleStack(Particles.w_minus_boson),new ParticleStack(Particles.charmed_eta),0.02);
		
		//fusion recipes
		
		addCollisionRecipe(new ParticleStack(Particles.proton,1,0,5),new ParticleStack(Particles.proton,1,0,5), new ParticleStack(Particles.deuteron),new ParticleStack(Particles.positron),new ParticleStack(Particles.electron_neutrino),null,0.5,1400,421,10000);
		addCollisionRecipe(new ParticleStack(Particles.antiproton,1,0,5),new ParticleStack(Particles.antiproton,1,0,5), new ParticleStack(Particles.antideuteron),new ParticleStack(Particles.electron),new ParticleStack(Particles.electron_antineutrino),null,0.5,1400,421,10000);
		addCollisionRecipe(new ParticleStack(Particles.proton,1,0,5),new ParticleStack(Particles.neutron,1,0,5), new ParticleStack(Particles.deuteron),new ParticleStack(Particles.photon),null,null,0.5,0,2225,10000);
		addCollisionRecipe(new ParticleStack(Particles.antiproton,1,0,5),new ParticleStack(Particles.antineutron,1,0,5), new ParticleStack(Particles.antideuteron),new ParticleStack(Particles.photon),null,null,0.5,0,2225,10000);
		addCollisionRecipe(new ParticleStack(Particles.proton,1,0,5),new ParticleStack(Particles.deuteron,1,0,5), new ParticleStack(Particles.helion),new ParticleStack(Particles.photon),null,null,0.5,1400,5493,10000);
		addCollisionRecipe(new ParticleStack(Particles.antiproton,1,0,5),new ParticleStack(Particles.antideuteron,1,0,5), new ParticleStack(Particles.antihelion),new ParticleStack(Particles.photon),null,null,0.5,1400, 5493,10000);
		addCollisionRecipe(new ParticleStack(Particles.proton,1,0,5),new ParticleStack(Particles.triton,1,0,5), new ParticleStack(Particles.alpha),new ParticleStack(Particles.photon),null,null,0.5,1400, 19814, 10000);
		addCollisionRecipe(new ParticleStack(Particles.antiproton,1,0,5),new ParticleStack(Particles.antitriton,1,0,5), new ParticleStack(Particles.antialpha),new ParticleStack(Particles.photon),null,null,0.5,1400,19814,10000);
		addCollisionRecipe(new ParticleStack(Particles.deuteron,1,0,5),new ParticleStack(Particles.deuteron,1,0,5), new ParticleStack(Particles.alpha),new ParticleStack(Particles.photon),null,null,0.5,1400, 23845,10000);
		addCollisionRecipe(new ParticleStack(Particles.antideuteron,1,0,5),new ParticleStack(Particles.antideuteron,1,0,5), new ParticleStack(Particles.antialpha),new ParticleStack(Particles.photon),null,null,0.5,1400, 23845,10000);
		addCollisionRecipe(new ParticleStack(Particles.helion,1,0,5),new ParticleStack(Particles.neutron,1,0,5), new ParticleStack(Particles.alpha),new ParticleStack(Particles.photon),null,null,0.5,0, 20577,10000);
		addCollisionRecipe(new ParticleStack(Particles.antihelion,1,0,5),new ParticleStack(Particles.antineutron,1,0,5), new ParticleStack(Particles.antialpha),new ParticleStack(Particles.photon),null,null,0.5,0, 20577,10000);
		addCollisionRecipe(new ParticleStack(Particles.deuteron,1,0,5),new ParticleStack(Particles.neutron,1,0,5), new ParticleStack(Particles.triton),new ParticleStack(Particles.photon),null,null,0.5,0, 5990,10000);
		addCollisionRecipe(new ParticleStack(Particles.antideuteron,1,0,5),new ParticleStack(Particles.antineutron,1,0,5), new ParticleStack(Particles.antitriton),new ParticleStack(Particles.photon),null,null,0.5,0, 5990,10000);

		addCollisionRecipe(new ParticleStack(Particles.proton,1,0,5),new ParticleStack(Particles.proton,1,0,5), new ParticleStack(Particles.neutron),new ParticleStack(Particles.photon),null,null,0.5,0, 5990,10000);

		
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

		particleIn1.setMeanEnergy(minEnergy/2);
		particleIn2.setMeanEnergy(minEnergy/2);
	
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
		long energyReleased = (long)((inputMass - outputMass) * 1000);
		long recipeEnergy = (long) (Math.abs(energyReleased)*1.1); //just an arbitrary amount more energy than the minimum possible
		
		particleIn1.setMeanEnergy(recipeEnergy/2);
		particleIn2.setMeanEnergy(recipeEnergy/2);
	
		in1 = new ParticleIngredient(particleIn1);
		in2 = new ParticleIngredient(particleIn2);
		

		addRecipe(in1, in2, out1, out2, out3, out4, (long)(recipeEnergy*1.5), crossSection, energyReleased);	
		
	}
	
	@Override
	public List fixExtras(List extras)
	{
		List fixed = new ArrayList(4);
		fixed.add(extras.size() > 0 && extras.get(0) instanceof Long ? (long) extras.get(0) : 0L);
		fixed.add(extras.size() > 1 && extras.get(1) instanceof Double ? (double) extras.get(1) : 1D);
		fixed.add(extras.size() > 2 && extras.get(2) instanceof Long ? (long) extras.get(2) : 0l);
		fixed.add(extras.size() > 3 && extras.get(3) instanceof Double ? (double) extras.get(3) : 0D);
		return fixed;
	}
	
	
	
}
