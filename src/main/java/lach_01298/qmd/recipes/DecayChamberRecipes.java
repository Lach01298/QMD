package lach_01298.qmd.recipes;

import lach_01298.qmd.particle.ParticleStack;
import lach_01298.qmd.particle.Particles;
import lach_01298.qmd.recipe.QMDRecipeHandler;
import lach_01298.qmd.recipe.ingredient.EmptyParticleIngredient;

public class DecayChamberRecipes extends QMDRecipeHandler
{
	public DecayChamberRecipes()
	{
		super("decay_chamber", 0, 0, 1, 0, 0, 3);
		
	}

	@Override
	public void addRecipes()
	{
		createRecipe(new ParticleStack(Particles.neutron,0,1),new ParticleStack(Particles.proton,0,1),new ParticleStack(Particles.electron_antineutrino,0,1),new ParticleStack(Particles.electron,0,1),1);
		createRecipe(new ParticleStack(Particles.pion_naught,0,1),null,new ParticleStack(Particles.photon,0,2),null,0.98);
		createRecipe(new ParticleStack(Particles.pion_plus,0,1),new ParticleStack(Particles.antimuon,0,1),new ParticleStack(Particles.muon_neutrino,0,1),null,0.99);
		createRecipe(new ParticleStack(Particles.pion_minus,0,1),null,new ParticleStack(Particles.muon_antineutrino,0,1),new ParticleStack(Particles.muon,0,1),0.99);
		createRecipe(new ParticleStack(Particles.muon,0,1),new ParticleStack(Particles.electron_antineutrino,0,1),new ParticleStack(Particles.muon_neutrino,0,1),new ParticleStack(Particles.electron,0,1),1);
		createRecipe(new ParticleStack(Particles.antimuon,0,1),new ParticleStack(Particles.positron,0,1),new ParticleStack(Particles.muon_antineutrino,0,1),new ParticleStack(Particles.electron_neutrino,0,1),1);
		createRecipe(new ParticleStack(Particles.tau,0,1),new ParticleStack(Particles.tau_neutrino,0,1),new ParticleStack(Particles.pion_naught,0,1),new ParticleStack(Particles.pion_minus,0,1),0.25);
		createRecipe(new ParticleStack(Particles.antitau,0,1),new ParticleStack(Particles.pion_plus,0,1),new ParticleStack(Particles.pion_naught,0,1),new ParticleStack(Particles.tau_antineutrino,0,1),0.25);
		createRecipe(new ParticleStack(Particles.kaon_plus,0,1),new ParticleStack(Particles.antimuon,0,1),new ParticleStack(Particles.muon_neutrino,0,1),null,0.63);
		createRecipe(new ParticleStack(Particles.kaon_minus,0,1),new ParticleStack(Particles.muon,0,1),new ParticleStack(Particles.muon_antineutrino,0,1),null,0.63);
		createRecipe(new ParticleStack(Particles.kaon_naught,0,1),new ParticleStack(Particles.pion_plus,0,1),null,new ParticleStack(Particles.pion_minus,0,1),0.77);
	
	
	
	}
	
	
	
	
	/**
	 * 
	 *
	 * @param particleIn input particle 
	 * @param particleOut1 positive output particle 
	 * @param particleOut2 neutral output particle 
	 * @param particleOut3 negative output particle 
	 */
	public void createRecipe(ParticleStack particleIn, ParticleStack particleOut1, ParticleStack particleOut2, ParticleStack particleOut3, double efficancy)
	{
		
		double massExcess = particleIn.getParticle().getMass();
		
		int out1Amount = 0;
		int out2Amount = 0;
		int out3Amount = 0;
		Object pOut1;
		Object pOut2;
		Object pOut3;
		
		if(particleOut1 != null)
		{
			out1Amount = particleOut1.getAmount();
			massExcess -= out1Amount * particleOut1.getParticle().getMass();
		}
		if(particleOut2 != null)
		{
			out2Amount = particleOut2.getAmount();
			massExcess -= out2Amount * particleOut2.getParticle().getMass();
		}
		if(particleOut3 != null)
		{
			out3Amount = particleOut3.getAmount();
			massExcess -= out3Amount * particleOut3.getParticle().getMass();
		}
		
		int totalParticlesOut = out1Amount + out2Amount +out3Amount;
		
		if(particleOut1 != null)
		{
			pOut1 = new ParticleStack(particleOut1.getParticle(),(int)(massExcess*1000/totalParticlesOut),out1Amount,efficancy,totalParticlesOut);
		}
		else
		{
			pOut1 = new EmptyParticleIngredient();
		}
		
		if(particleOut2 != null)
		{
			pOut2 = new ParticleStack(particleOut2.getParticle(),(int)(massExcess*1000/totalParticlesOut),out2Amount,efficancy,totalParticlesOut);
		}
		else
		{
			pOut2 = new EmptyParticleIngredient();
		}
		
		if(particleOut3 != null)
		{
			pOut3 = new ParticleStack(particleOut3.getParticle(),(int)(massExcess*1000/totalParticlesOut),out3Amount,efficancy,totalParticlesOut);
		}
		else
		{
			pOut3 = new EmptyParticleIngredient();
		}
		
		
		
		
		
		addRecipe(new ParticleStack(particleIn.getParticle(),0,1),pOut1,pOut2,pOut3);	
	}
	
	
	
}
