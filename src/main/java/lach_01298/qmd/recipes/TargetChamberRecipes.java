package lach_01298.qmd.recipes;

import java.util.ArrayList;

import lach_01298.qmd.enums.MaterialEnums;
import lach_01298.qmd.item.QMDItems;
import lach_01298.qmd.particle.ParticleStack;
import lach_01298.qmd.particle.Particles;
import lach_01298.qmd.recipe.QMDRecipeHandler;
import lach_01298.qmd.recipe.ingredient.EmptyParticleIngredient;
import nc.enumm.MetaEnums;
import nc.init.NCItems;
import net.minecraft.item.ItemStack;

public class TargetChamberRecipes extends QMDRecipeHandler
{

	public TargetChamberRecipes()
	{
		super("target_chamber", 1, 0, 1, 1, 0, 3);
		
	}

	@Override
	public void addRecipes()
	{
		//addRecipe(item input, particle input, item output,particle output +, particle output neutral, particle -)
		
		
		
		createRecipe("ingotBeryllium", "ingotLithium6", new ParticleStack(Particles.proton, 1000, 120000), 2125, 7500, 0.34,
				new ParticleStack(Particles.alpha, 0, 1), null, null);

		
		createRecipe("ingotBeryllium", "ingotBoron10", new ParticleStack(Particles.proton, 29000, 1200000), 6585, 31000, 0.01,
				null, new ParticleStack(Particles.photon,0,1), null);
		
		createRecipe("ingotBoron10", "ingotBeryllium7", new ParticleStack(Particles.proton, 4000, 180000), 1144, 6000, 0.3,
				new ParticleStack(Particles.alpha, 0, 1), null, null);
		
		createRecipe("ingotBoron11", "charcoal", new ParticleStack(Particles.proton, 15000, 1200000), 15956, 17000, 0.01,
				null, new ParticleStack(Particles.photon,0,1), null);
		

		createRecipe("charcoal", "ingotBeryllium7", new ParticleStack(Particles.proton, 45000, 840000), -26266, 50000, 0.06,
				new ParticleStack(Particles.alpha, 0, 1), new ParticleStack(Particles.neutron, 0, 1), new ParticleStack(Particles.proton, 0, 1));

	
		createRecipe("charcoal", "ingotBeryllium", new ParticleStack(Particles.proton, 15000, 180000), -7551, 17000, 0.28,
				new ParticleStack(Particles.alpha, 0, 1), null, null);
		
		createRecipe("ingotSodium", "ingotSodium22", new ParticleStack(Particles.proton, 24000, 180000), -12418, 26000, 0.28,
				new ParticleStack(Particles.proton, 0, 1), new ParticleStack(Particles.neutron, 0, 1), null);
		
		createRecipe("ingotMagnesium24", "ingotSodium22", new ParticleStack(Particles.proton, 35000, 360000), -24111, 50000, 0.14,
				new ParticleStack(Particles.proton, 0, 2), new ParticleStack(Particles.neutron, 0, 1), null);
		
		createRecipe("ingotAluminum", "ingotSodium22", new ParticleStack(Particles.proton, 45000, 960000), -22510, 50000, 0.05,
				new ParticleStack(Particles.alpha, 0, 1), new ParticleStack(Particles.neutron, 0, 1), new ParticleStack(Particles.proton, 0, 1));

		createRecipe("ingotManganese", "ingotIron", new ParticleStack(Particles.proton, 10000, 1200000), 10183, 15000, 0.01,
				null, new ParticleStack(Particles.photon,0,1), null);
		
		createRecipe("ingotUranium238", "dustProtactinium233", new ParticleStack(Particles.proton, 110000, 180000), -1459, 120000, 0.27,
				new ParticleStack(Particles.alpha, 0, 1), new ParticleStack(Particles.neutron, 0, 2),null);
		
		createRecipe("ingotUranium238", "ingotNeptunium236", new ParticleStack(Particles.proton, 19000, 120000), -12996, 23000, 0.44,
				new ParticleStack(Particles.triton, 0, 1), null,null);
		
		createRecipe("ingotUranium235", "ingotUranium233", new ParticleStack(Particles.proton, 65000, 480000), -3660, 75000, 0.11,
				new ParticleStack(Particles.triton, 0, 1), null,null);
		
		
		
		
		createRecipe("ingotBeryllium", "ingotLithium7", new ParticleStack(Particles.deuteron, 4000, 180000), 7152, 5000, 0.3,
				new ParticleStack(Particles.alpha, 0, 1), null,null);
		
		createRecipe("ingotBoron11", "charcoal", new ParticleStack(Particles.deuteron, 2000, 180000), 13732, 4000, 0.24,
				null, new ParticleStack(Particles.neutron, 0, 1),null);
		
		createRecipe("charcoal", "ingotBoron10", new ParticleStack(Particles.deuteron, 5000, 180000), -1339, 15000, 0.28,
				new ParticleStack(Particles.alpha, 0, 1), null,null);
		
		createRecipe("ingotMagnesium24", "ingotSodium22", new ParticleStack(Particles.deuteron, 8000, 300000), 1958, 10000, 0.18,
				new ParticleStack(Particles.alpha, 0, 1), null,null);
		
		createRecipe("ingotPlutonium242", "ingotAmericium242", new ParticleStack(Particles.deuteron, 11000, 180000), -3758, 16000, 0.25,
				null, new ParticleStack(Particles.neutron, 0, 2),null);
		

		
		
		createRecipe("ingotMagnesium26", "ingotMagnesium24", new ParticleStack(Particles.photon, 25000, 240000), -18423, 30000, 0.2,
				null, new ParticleStack(Particles.neutron, 0, 2),null);
		
		createRecipe("itemSilicon", "ingotAluminum", new ParticleStack(Particles.photon, 19500, 120000), -11585, 20500, 0.4,
				new ParticleStack(Particles.proton, 0, 1), null,null);
		

		
		
		
		createRecipe("ingotPlutonium242", "ingotPlutonium241", new ParticleStack(Particles.neutron, 10000, 40000), 0, 12000, 1,
				null, new ParticleStack(Particles.neutron, 0, 2),null);

		createRecipe("ingotNeptunium237", "ingotNeptunium236", new ParticleStack(Particles.neutron, 10000, 80000), 0, 14000, 0.67,
				null, new ParticleStack(Particles.neutron, 0, 2),null);
		
		createRecipe("ingotUranium238", "ingotUranium235", new ParticleStack(Particles.neutron, 20000, 90000), -17825, 21000, 0.54,
				null, new ParticleStack(Particles.neutron, 0, 4),null);
		
		createRecipe("ingotUranium235", "ingotUranium233", new ParticleStack(Particles.neutron, 11000, 180000), 0, 15000, 0.28,
				null, new ParticleStack(Particles.neutron, 0, 3),null);	
		

		
		
		createRecipe("ingotBeryllium", "ingotBoron11", new ParticleStack(Particles.triton, 4000, 120000), 9559, 7000, 0.47,
				null, new ParticleStack(Particles.neutron, 0, 1),null);	
		
		
		createRecipe("ingotLithium7", "ingotBoron10", new ParticleStack(Particles.alpha, 7500, 180000), -2790, 15000, 0.3,
				null, new ParticleStack(Particles.neutron, 0, 1),null);	
		
		createRecipe("ingotBeryllium", "charcoal", new ParticleStack(Particles.alpha, 7500, 90000), 5702, 8000, 0.5,
				null, new ParticleStack(Particles.neutron, 0, 1),null);	
			
		createRecipe("charcoal", "ingotBoron11", new ParticleStack(Particles.alpha, 35000, 420000), -15956, 45000, 0.12,
				new ParticleStack(Particles.alpha, 0, 1), null,new ParticleStack(Particles.proton, 0, 1));	
		
		
	
	//Spallation recipes	
		createRecipe("ingotUranium238", "dustBismuth", new ParticleStack(Particles.antiproton, 1, 1200000),(int)(2*Particles.proton.getMass()-(2*Particles.pion_plus.getMass()+ Particles.pion_naught.getMass()))*1000, 10000000, 2,
				new ParticleStack(Particles.pion_plus, 0, 1),new ParticleStack(Particles.pion_naught, 0, 1),new ParticleStack(Particles.pion_minus, 0, 1));
		
		createRecipe("ingotUranium238", "ingotLead", new ParticleStack(Particles.proton, 6575000, 1200000), -6575000, 2*6575000, 1,
				new ParticleStack(Particles.proton, 0, 1), null,new ParticleStack(Particles.antiproton, 0, 1));	
		
		createRecipe("ingotUranium238", "ingotLead", new ParticleStack(Particles.proton, 1539000, 1200000), -1539000, 2*1539000, 1,
				new ParticleStack(Particles.pion_plus, 0, 1),null,new ParticleStack(Particles.pion_minus, 0, 1));	
	
		createRecipe("ingotThorium", "ingotLead", new ParticleStack(Particles.antiproton, 1, 1200000), (int)(2*Particles.proton.getMass()-(2*Particles.pion_plus.getMass()+ Particles.pion_naught.getMass()))*1000, 10000000, 1.5,
				new ParticleStack(Particles.pion_plus, 0, 1),new ParticleStack(Particles.pion_naught, 0, 1),new ParticleStack(Particles.pion_minus, 0, 1));
		
		createRecipe("ingotThorium", "dustPolonium", new ParticleStack(Particles.proton, 6575000, 1200000),-6575000, 2*6575000, 1,
				new ParticleStack(Particles.proton, 0, 1), null,new ParticleStack(Particles.antiproton, 0, 1));	
		
		createRecipe("ingotThorium", "ingotLead210", new ParticleStack(Particles.proton, 1539000, 1200000), -1539000, 2*1539000, 1,
				new ParticleStack(Particles.pion_plus, 0, 1),null,new ParticleStack(Particles.pion_minus, 0, 1));	
		
		createRecipe("ingotLead", "ingotIridium", new ParticleStack(Particles.antiproton, 1, 1200000), (int)(2*Particles.proton.getMass()-(2*Particles.pion_plus.getMass()+ Particles.pion_naught.getMass()))*1000, 10000000, 1,
				new ParticleStack(Particles.pion_plus, 0, 1),new ParticleStack(Particles.pion_naught, 0, 1),new ParticleStack(Particles.pion_minus, 0, 1));
		
		createRecipe("ingotLead", "ingotOsmium", new ParticleStack(Particles.proton, 6575000, 1200000), -6575000, 2*6575000, 1,
				new ParticleStack(Particles.proton, 0, 1), null,new ParticleStack(Particles.antiproton, 0, 1));	
		
		createRecipe("ingotLead", "ingotPlatinum", new ParticleStack(Particles.proton, 1539000, 1200000), -1539000, 2*1539000, 1,
				new ParticleStack(Particles.pion_plus, 0, 1),null,new ParticleStack(Particles.pion_minus, 0, 1));		
	
		createRecipe("ingotBismuth", "ingotOsmium", new ParticleStack(Particles.antiproton, 1, 1200000), (int)(2*Particles.proton.getMass()-(2*Particles.pion_plus.getMass()+ Particles.pion_naught.getMass()))*1000, 10000000, 1,
				new ParticleStack(Particles.pion_plus, 0, 1),new ParticleStack(Particles.pion_naught, 0, 1),new ParticleStack(Particles.pion_minus, 0, 1));
		
		createRecipe("ingotBismuth", "ingotIridium", new ParticleStack(Particles.proton, 6575000, 1200000), -6575000, 2*6575000, 1,
				new ParticleStack(Particles.proton, 0, 1), null,new ParticleStack(Particles.antiproton, 0, 1));	
		
		createRecipe("ingotBismuth", "ingotPlatinum", new ParticleStack(Particles.proton, 1539000, 1200000), -1539000, 2*1539000, 1,
				new ParticleStack(Particles.pion_plus, 0, 1),null,new ParticleStack(Particles.pion_minus, 0, 1));
		
		createRecipe("ingotZinc", "ingotTitanium", new ParticleStack(Particles.antiproton, 1, 1200000), (int)(2*Particles.proton.getMass()-(2*Particles.pion_plus.getMass()+ Particles.pion_naught.getMass()))*1000, 10000000, 0.5,
				new ParticleStack(Particles.pion_plus, 0, 1),new ParticleStack(Particles.pion_naught, 0, 1),new ParticleStack(Particles.pion_minus, 0, 1));
		
		createRecipe("ingotZinc", "ingotTitianium", new ParticleStack(Particles.proton, 6575000, 1200000), -6575000, 2*6575000, 0.5,
				new ParticleStack(Particles.proton, 0, 1), null,new ParticleStack(Particles.antiproton, 0, 1));	
		
		createRecipe("ingotZinc", "ingotVanadium50", new ParticleStack(Particles.proton, 1539000, 1200000), -1539000, 2*1539000, 0.5,
				new ParticleStack(Particles.pion_plus, 0, 1),null,new ParticleStack(Particles.pion_minus, 0, 1));
		
		createRecipe("ingotNickel", "ingotPotassium", new ParticleStack(Particles.antiproton, 1, 1200000), (int)(2*Particles.proton.getMass()-(2*Particles.pion_plus.getMass()+ Particles.pion_naught.getMass()))*1000, 10000000, 0.5,
				new ParticleStack(Particles.pion_plus, 0, 1),new ParticleStack(Particles.pion_naught, 0, 1),new ParticleStack(Particles.pion_minus, 0, 1));
		
		createRecipe("ingotNickel", "ingotCalcium", new ParticleStack(Particles.proton, 6575000, 1200000), -6575000, 2*6575000, 0.5,
				new ParticleStack(Particles.proton, 0, 1), null,new ParticleStack(Particles.antiproton, 0, 1));	
		
		createRecipe("ingotNickel", "ingotCalcium", new ParticleStack(Particles.proton, 1539000, 1200000), -1539000, 2*1539000, 0.5,
				new ParticleStack(Particles.pion_plus, 0, 1),null,new ParticleStack(Particles.pion_minus, 0, 1));
		
		
		createRecipe("ingotCobalt", "ingotCalcium", new ParticleStack(Particles.antiproton, 1, 1200000), (int)(2*Particles.proton.getMass()-(2*Particles.pion_plus.getMass()+ Particles.pion_naught.getMass()))*1000, 10000000, 0.5,
				new ParticleStack(Particles.pion_plus, 0, 1),new ParticleStack(Particles.pion_naught, 0, 1),new ParticleStack(Particles.pion_minus, 0, 1));
		
		createRecipe("ingotCopper", "ingotTitanium", new ParticleStack(Particles.antiproton, 1, 1200000), (int)(2*Particles.proton.getMass()-(2*Particles.pion_plus.getMass()+ Particles.pion_naught.getMass()))*1000, 10000000, 0.5,
				new ParticleStack(Particles.pion_plus, 0, 1),new ParticleStack(Particles.pion_naught, 0, 1),new ParticleStack(Particles.pion_minus, 0, 1));
		
		createRecipe("ingotSilver", "ingotZirconium", new ParticleStack(Particles.antiproton, 1, 1200000), (int)(2*Particles.proton.getMass()-(2*Particles.pion_plus.getMass()+ Particles.pion_naught.getMass()))*1000, 10000000, 0.5,
				new ParticleStack(Particles.pion_plus, 0, 1),new ParticleStack(Particles.pion_naught, 0, 1),new ParticleStack(Particles.pion_minus, 0, 1));
		
		//Semiconductor recipes
		createRecipe("siliconWafer", new ItemStack(QMDItems.semiconductor,1,MaterialEnums.SemiconductorType.SILICON_P_DOPED.getID()), new ParticleStack(Particles.boron_ion, 4000, 120000), 0, 5000, 0,
				null,null,null);
		
		
		
		
	}
	
	
	/**
	 * 
	 * @param itemIn input item
	 * @param itemOut output item
	 * @param particleIn input particle (particle, threshold energy, process time)
	 * @param q energy released in reaction
	 * @param eMax maxiumEnergy for reaction
	 * @param efficency output beam amount multiplier
	 * @param particleOut1 positive output particle (particle, 0, amount released in reaction)
	 * @param particleOut2 neutral output particle (particle, 0, amount released in reaction)
	 * @param particleOut3 negative output particle (particle, 0, amount released in reaction)
	 */
	public void createRecipe(Object itemIn, Object itemOut, ParticleStack particleIn, int q, int eMax,double efficency, ParticleStack particleOut1, ParticleStack particleOut2, ParticleStack particleOut3)
	{
		long thresholdEnergy = particleIn.getMeanEnergy();
		int processTime = particleIn.getAmount();
		
		
		int out1Amount = 0;
		int out2Amount = 0;
		int out3Amount = 0;
		Object pOut1;
		Object pOut2;
		Object pOut3;
		
		if(particleOut1 != null)
		{
			out1Amount = particleOut1.getAmount();
			
		}
		if(particleOut2 != null)
		{
			out2Amount = particleOut2.getAmount();
		}
		if(particleOut3 != null)
		{
			out3Amount = particleOut3.getAmount();
		}
		
		int totalParticlesOut = out1Amount + out2Amount +out3Amount;
		
		if(particleOut1 != null)
		{
			pOut1 = new ParticleStack(particleOut1.getParticle(),q/totalParticlesOut,out1Amount,efficency,totalParticlesOut);
		}
		else
		{
			pOut1 = new EmptyParticleIngredient();
		}
		
		if(particleOut2 != null)
		{
			pOut2 = new ParticleStack(particleOut2.getParticle(),q/totalParticlesOut,out2Amount,efficency,totalParticlesOut);
		}
		else
		{
			pOut2 = new EmptyParticleIngredient();
		}
		
		if(particleOut3 != null)
		{
			pOut3 = new ParticleStack(particleOut3.getParticle(),q/totalParticlesOut,out3Amount,efficency,totalParticlesOut);
		}
		else
		{
			pOut3 = new EmptyParticleIngredient();
		}
		
		double spread = (double)eMax/(double)thresholdEnergy -1.0;
		
		
		
		addRecipe(itemIn, new ParticleStack(particleIn.getParticle(),thresholdEnergy, processTime,spread,100),itemOut,pOut1,pOut2,pOut3);	
	}
	
	
	
	
	
	
	
	

}
