package lach_01298.qmd.recipes;

import java.util.ArrayList;
import java.util.List;

import lach_01298.qmd.particle.ParticleStack;
import lach_01298.qmd.particle.Particles;
import lach_01298.qmd.recipe.QMDRecipeHandler;

public class TargetChamberRecipes extends QMDRecipeHandler
{

	public TargetChamberRecipes()
	{
		super("target_chamber", 1, 0, 1, 1, 0, 3);
		
	}

	@Override
	public void addRecipes()
	{
		//addRecipe(itemInput, particleInput [particle, amount, minEnergy, minFocus], item output,
		//particleOutput+ [particle, amount], particleOutput0 [particle, amount], particleOutput- [particle, amount], maxEnergy, crossSection, energyRelased, radiation)
		
		
		//proton reactions
		
		addRecipe("ingotBeryllium",new ParticleStack(Particles.proton, 1600000, 1000), "ingotLithium6",
				new ParticleStack(Particles.alpha), emptyParticleStack(), emptyParticleStack(), 7500, 0.625, 2640);
		
		addRecipe("ingotBoron10", new ParticleStack(Particles.proton, 1250000, 4000),"ingotBeryllium7",
				new ParticleStack(Particles.alpha), emptyParticleStack(), emptyParticleStack(),6000, 0.8, 1700);
		
		addRecipe("ingotBoron11", new ParticleStack(Particles.proton, 50000000, 15500), "dustGraphite",
				emptyParticleStack(), new ParticleStack(Particles.photon), emptyParticleStack(), 26000, 0.02, 15000);
		
		addRecipe("ingotBoron11", new ParticleStack(Particles.proton, 20000000, 30000), "ingotBeryllium7",
				new ParticleStack(Particles.triton), new ParticleStack(Particles.neutron,2), emptyParticleStack(), 33000, 0.05, -30000);
		
		addRecipe("dustGraphite", new ParticleStack(Particles.proton, 15625000, 40000), "ingotBoron10",
				new ParticleStack(Particles.proton,2), emptyParticleStack(), emptyParticleStack(), 50000, 0.064, -25000);
		
		addRecipe("dustGraphite", new ParticleStack(Particles.proton, 12500000, 60000), "ingotBoron11",
				new ParticleStack(Particles.proton), emptyParticleStack(), new ParticleStack(Particles.deuteron), 85000, 0.08, -25000);
		
		addRecipe("dustGraphite", new ParticleStack(Particles.proton, 12500000, 27000), "ingotBeryllium7",
				new ParticleStack(Particles.alpha), new ParticleStack(Particles.neutron), new ParticleStack(Particles.proton), 35000, 0.08, -15000);
		
		addRecipe("dustGraphite", new ParticleStack(Particles.proton, 50000000, 150000), "ingotBeryllium",
				new ParticleStack(Particles.proton), emptyParticleStack(), new ParticleStack(Particles.triton), 1000000, 0.02, -26000);
		
		addRecipe("ingotSodium", new ParticleStack(Particles.proton, 2000000, 20000), "ingotSodium22",
				new ParticleStack(Particles.proton), new ParticleStack(Particles.neutron), emptyParticleStack(), 28000,  0.5, -12400);

		addRecipe("ingotMagnesium24", new ParticleStack(Particles.proton, 3125000 , 38000), "ingotSodium22",
				new ParticleStack(Particles.proton, 2), new ParticleStack(Particles.neutron), emptyParticleStack(), 56000, 0.32, -23000);
		
		addRecipe("ingotMagnesium26", new ParticleStack(Particles.proton, 10000000 , 23000), "ingotSodium22",
				new ParticleStack(Particles.alpha), new ParticleStack(Particles.neutron), emptyParticleStack(), 29000, 0.1, -14000);
		
		addRecipe("ingotAluminum",  new ParticleStack(Particles.proton, 12500000, 40000), "ingotSodium22",
				new ParticleStack(Particles.alpha), new ParticleStack(Particles.neutron), new ParticleStack(Particles.proton), 50000, 0.08, -21500);
		
		addRecipe("ingotAluminum",  new ParticleStack(Particles.proton, 50000000, 1500), "itemSilicon",
				emptyParticleStack(), new ParticleStack(Particles.photon), emptyParticleStack(), 2000, 0.02, 11100);
		
		addRecipe("itemSilicon",  new ParticleStack(Particles.proton, 1000000, 19000), "ingotAluminum",
				new ParticleStack(Particles.proton,2), emptyParticleStack(), emptyParticleStack(), 28000, 1, -11100);
		
		addRecipe("itemSilicon",  new ParticleStack(Particles.proton, 5000000, 32000), "ingotMagnesium26",
				new ParticleStack(Particles.proton,3), emptyParticleStack(), emptyParticleStack(), 50000, 0.2, -18800);
		
		addRecipe("ingotCalcium",  new ParticleStack(Particles.proton, 1000000, 16500), "ingotPotassium",
				new ParticleStack(Particles.proton,2), emptyParticleStack(), emptyParticleStack(), 25000, 1, -7810);
		
		addRecipe("ingotManganese", new ParticleStack(Particles.proton, 50000000, 10000), "ingotIron",
				emptyParticleStack(), new ParticleStack(Particles.photon), emptyParticleStack(), 19500, 0.02, 9670);
		
		addRecipe("ingotCobalt", new ParticleStack(Particles.proton, 5000000, 4600), "ingotNickel",
				emptyParticleStack(), new ParticleStack(Particles.photon), emptyParticleStack(), 5600, 0.2, 9020);
		
		addRecipe("ingotCopper", new ParticleStack(Particles.proton, 25000000, 4500), "ingotZinc",
				emptyParticleStack(), new ParticleStack(Particles.photon), emptyParticleStack(), 5900, 0.04, 7200);
		
		addRecipe("ingotCopper", new ParticleStack(Particles.proton, 1600000, 45000), "ingotNickel",
				new ParticleStack(Particles.alpha), emptyParticleStack(), emptyParticleStack(), 56000, 0.625, 4270);
		
		addRecipe("ingotOsmium", new ParticleStack(Particles.proton, 6250000, 8300), "ingotIridium192",
				emptyParticleStack(), new ParticleStack(Particles.neutron), emptyParticleStack(), 10300, 0.16, -2340);
		
		addRecipe("ingotGold", new ParticleStack(Particles.proton, 50000000, 100000), "ingotIridium192",
				new ParticleStack(Particles.alpha), new ParticleStack(Particles.neutron), new ParticleStack(Particles.proton), 200000, 0.02, -5780);
		
		addRecipe("ingotGold", new ParticleStack(Particles.proton, 15625000, 21000), "ingotPlatinum",
				new ParticleStack(Particles.alpha), emptyParticleStack(), emptyParticleStack(), 25000, 0.064, 9000);
		
		addRecipe("dustBismuth", new ParticleStack(Particles.proton, 50000000, 14000), "dustPolonium",
				emptyParticleStack(), new ParticleStack(Particles.photon), emptyParticleStack(), 19000, 0.02, 4470);
		
		addRecipe("dustBismuth", new ParticleStack(Particles.proton, 25000000, 20000), "dustLead",
				new ParticleStack(Particles.alpha), emptyParticleStack(), emptyParticleStack(), 24000, 0.04, 10900);
		
		addRecipe("ingotThorium", new ParticleStack(Particles.proton, 1600000, 11500), "dustProtactinium231",
				emptyParticleStack(), new ParticleStack(Particles.neutron,2), emptyParticleStack(), 16000, 0.625, -7340);
		
		addRecipe("ingotUranium235", new ParticleStack(Particles.proton, 50000000, 20500), "ingotNeptunium236",
				emptyParticleStack(), new ParticleStack(Particles.photon), emptyParticleStack(), 30000, 0.02, 4320);
		
		addRecipe("ingotUranium238", new ParticleStack(Particles.proton, 3125000, 12000), "ingotNeptunium237",
				emptyParticleStack(), new ParticleStack(Particles.neutron, 2), emptyParticleStack(), 16500, 0.32, -6930);
		
		addRecipe("ingotUranium238", new ParticleStack(Particles.proton, 1000000, 19000), "ingotNeptunium236",
				emptyParticleStack(), new ParticleStack(Particles.neutron,3), emptyParticleStack(), 23000, 1, -13500);
		
		addRecipe("ingotPlutonium242", new ParticleStack(Particles.proton, 2500000, 12500), "ingotAmericium241",
				emptyParticleStack(), new ParticleStack(Particles.neutron,2), emptyParticleStack(), 16500, 0.4, -7580);
		
		
		
		
		// neutron reactions
			
		addRecipe("ingotBeryllium7", new ParticleStack(Particles.neutron, 1000000, 0), "ingotLithium7",
				new ParticleStack(Particles.proton), emptyParticleStack(), emptyParticleStack(), 10000, 1, 2150);
		
		addRecipe("ingotBeryllium", new ParticleStack(Particles.neutron, 15625000, 15000), "ingotLithium7",
				new ParticleStack(Particles.triton), emptyParticleStack(), emptyParticleStack(), 26000, 0.064, -9930);
		
		addRecipe("ingotBoron10", new ParticleStack(Particles.neutron, 50000000, 0), "ingotBoron11",
				emptyParticleStack(), new ParticleStack(Particles.photon), emptyParticleStack(), 1000, 0.02, 11500);
		
		addRecipe("ingotBoron11", new ParticleStack(Particles.neutron, 25000000, 12500), "ingotBeryllium9",
				new ParticleStack(Particles.triton), emptyParticleStack(), emptyParticleStack(), 20000, 0.04, -9050);

		addRecipe("dustGraphite", new ParticleStack(Particles.neutron, 5000000, 29500), "ingotBoron11",
				new ParticleStack(Particles.deuteron), emptyParticleStack(), emptyParticleStack(), 60000, 0.2, -13200);
		
		addRecipe("dustGraphite", new ParticleStack(Particles.neutron, 31250000, 105000), "ingotBeryllium7",
				new ParticleStack(Particles.helion), new ParticleStack(Particles.neutron,3), emptyParticleStack(), 150000, 0.032, -45800);
		
		addRecipe("ingotSodium", new ParticleStack(Particles.neutron, 4000000, 17000), "ingotSodium22",
				emptyParticleStack(), new ParticleStack(Particles.neutron,2), emptyParticleStack(), 28000, 0.25, -12400);
		
		addRecipe("ingotMagnesium24", new ParticleStack(Particles.neutron, 4000000, 35000), "ingotSodium22",
				new ParticleStack(Particles.deuteron), new ParticleStack(Particles.neutron), emptyParticleStack(), 60000, 0.25, -21400);
		
		addRecipe("ingotAluminum", new ParticleStack(Particles.neutron, 1250000, 13000), "ingotMagnesium26",
				new ParticleStack(Particles.proton), new ParticleStack(Particles.neutron), emptyParticleStack(), 30000, 0.8, -7760);

		addRecipe("ingotAluminum", new ParticleStack(Particles.neutron, 20000000, 43000), "ingotSodium22",
				new ParticleStack(Particles.helion), new ParticleStack(Particles.neutron,3), emptyParticleStack(), 80000, 0.05, -42100);

		addRecipe("itemSilicon", new ParticleStack(Particles.neutron, 1250000, 18000), "ingotAluminum",
				new ParticleStack(Particles.proton), new ParticleStack(Particles.neutron), emptyParticleStack(), 27000, 0.8, -11100);
		
		addRecipe("ingotCalcium", new ParticleStack(Particles.neutron, 1000000, 14000), "ingotPotassium",
				new ParticleStack(Particles.proton), new ParticleStack(Particles.neutron), emptyParticleStack(), 24000, 1, -7820);
		
		addRecipe("ingotChromium", new ParticleStack(Particles.neutron, 6250000, 12000), "ingotTitanium",
				new ParticleStack(Particles.alpha), emptyParticleStack(), emptyParticleStack(), 20000, 0.16, -191);
		
		addRecipe("ingotManganese", new ParticleStack(Particles.neutron, 1600000, 29000), "ingotChromium",
				new ParticleStack(Particles.triton), emptyParticleStack(), emptyParticleStack(), 46000, 0.625, -8790);
		
		addRecipe("ingotIron", new ParticleStack(Particles.neutron, 10000000, 11000), "ingotChromium",
				new ParticleStack(Particles.alpha), emptyParticleStack(), emptyParticleStack(), 22000, 0.1, 1350);
		
		addRecipe("ingotCobalt", new ParticleStack(Particles.neutron, 2000000, 0), "ingotCobalt60",
				emptyParticleStack(), new ParticleStack(Particles.photon), emptyParticleStack(), 10000, 0.5, 7490);
		
		addRecipe("ingotCobalt", new ParticleStack(Particles.neutron, 2500000, 30000), "ingotIron",
				new ParticleStack(Particles.triton), emptyParticleStack(), emptyParticleStack(), 50000, 0.4, -8420);
		
	
		addRecipe("ingotNickel", new ParticleStack(Particles.neutron, 1250000, 19000), "ingotIron",
				new ParticleStack(Particles.alpha), new ParticleStack(Particles.neutron), emptyParticleStack(), 30000, 0.8, -5380);
		
		addRecipe("ingotCopper", new ParticleStack(Particles.neutron, 1000000, 11000), "ingotNickel",
				new ParticleStack(Particles.proton), new ParticleStack(Particles.neutron), emptyParticleStack(), 15000, 1.0, -5610);
		
		addRecipe("ingotZinc", new ParticleStack(Particles.neutron, 1000000, 14000), "ingotCopper",
				new ParticleStack(Particles.proton), new ParticleStack(Particles.neutron), emptyParticleStack(), 24500, 1, -7200);
		
		addRecipe("ingotZinc", new ParticleStack(Particles.neutron, 4000000, 5500), "ingotNickel",
				new ParticleStack(Particles.alpha), emptyParticleStack(), emptyParticleStack(), 13000, 0.25, 4880);
		
		addRecipe("ingotYttrium", new ParticleStack(Particles.neutron, 3125000, 30000), "ingotStrontium",
				new ParticleStack(Particles.deuteron), emptyParticleStack(), emptyParticleStack(), 49000, 0.32, -4330);
		
		addRecipe("ingotZirconium", new ParticleStack(Particles.neutron, 1600000, 21000), "ingotYttrium",
				new ParticleStack(Particles.deuteron), emptyParticleStack(), emptyParticleStack(), 36000, 0.625, -5620);

		addRecipe("ingotZirconium", new ParticleStack(Particles.neutron, 25000000, 15000), "ingotStrontium",
				new ParticleStack(Particles.alpha), emptyParticleStack(), emptyParticleStack(), 20000, 0.04, 2780);

		addRecipe("ingotNiobium", new ParticleStack(Particles.neutron, 8000000, 22000), "ingotYttrium",
				new ParticleStack(Particles.alpha), new ParticleStack(Particles.neutron), emptyParticleStack(), 34000, 0.125, -909);

		addRecipe("ingotIridium192", new ParticleStack(Particles.neutron, 1000000, 0), "ingotIridium",
				emptyParticleStack(), new ParticleStack(Particles.photon), emptyParticleStack(), 5000, 1, 7770);
		
		addRecipe("ingotIridium", new ParticleStack(Particles.neutron, 1000000, 9000), "ingotIridium192",
				emptyParticleStack(), new ParticleStack(Particles.neutron,2), emptyParticleStack(), 19500, 1, -7770);

		addRecipe("ingotPlatinum", new ParticleStack(Particles.neutron, 40000000, 19000), "ingotOsmium",
				new ParticleStack(Particles.alpha), emptyParticleStack(), emptyParticleStack(), 27000, 0.025, 9750);
		
		addRecipe("ingotGold", new ParticleStack(Particles.neutron, 20000000, 30000), "ingotIridium",
				new ParticleStack(Particles.alpha), new ParticleStack(Particles.neutron), emptyParticleStack(), 45000, 0.05, 1990);
		
		addRecipe("dustBismuth", new ParticleStack(Particles.neutron, 5000000, 70000), "dustLead",
				new ParticleStack(Particles.triton), emptyParticleStack(), emptyParticleStack(), 150000, 0.2, -2170);
		
		addRecipe("ingotUranium233", new ParticleStack(Particles.neutron, 1000000, 0), "ingotUranium234",
				emptyParticleStack(), new ParticleStack(Particles.photon), emptyParticleStack(), 5000,  1.0, 6840);	
		
		addRecipe("ingotUranium234", new ParticleStack(Particles.neutron, 1000000, 0), "ingotUranium235",
				emptyParticleStack(), new ParticleStack(Particles.photon), emptyParticleStack(), 5000,  1.0, 5300);	
		
		addRecipe("ingotUranium235", new ParticleStack(Particles.neutron, 1600000, 17000), "ingotUranium233",
				emptyParticleStack(), new ParticleStack(Particles.neutron, 3), emptyParticleStack(), 23500,  0.625, -12100);	
		
		addRecipe("ingotUranium238", new ParticleStack(Particles.neutron, 1000000, 26000), "ingotUranium235",
				emptyParticleStack(), new ParticleStack(Particles.neutron, 4), emptyParticleStack(), 34000, 0.55, -17800);
		
		addRecipe("ingotNeptunium236",new ParticleStack(Particles.neutron, 1000000, 10000), "ingotNeptunium237",
				emptyParticleStack(), new ParticleStack(Particles.photon), emptyParticleStack(), 14000, 1, 6580);
		
		addRecipe("ingotNeptunium237",new ParticleStack(Particles.neutron, 1000000, 10000), "ingotNeptunium236",
				emptyParticleStack(), new ParticleStack(Particles.neutron, 2), emptyParticleStack(), 14000, 1, -6580);
		
		addRecipe("ingotPlutonium238", new ParticleStack(Particles.neutron, 1000000, 0), "ingotPlutonium239",
				emptyParticleStack(), new ParticleStack(Particles.photon), emptyParticleStack(), 5000, 1, 5650);
		
		addRecipe("ingotPlutonium241", new ParticleStack(Particles.neutron, 1000000, 0), "ingotPlutonium242",
				emptyParticleStack(), new ParticleStack(Particles.photon), emptyParticleStack(), 5000, 1, 6310);
		
		addRecipe("ingotPlutonium242", new ParticleStack(Particles.neutron, 1000000, 8500), "ingotPlutonium241",
				emptyParticleStack(), new ParticleStack(Particles.neutron, 2), emptyParticleStack(), 13000, 1, -6310);
		
		addRecipe("ingotAmericium241", new ParticleStack(Particles.neutron, 1000000, 0), "ingotAmericium242",
				emptyParticleStack(), new ParticleStack(Particles.photon), emptyParticleStack(), 5000, 1, 5540);
		
		addRecipe("ingotAmericium242", new ParticleStack(Particles.neutron, 1000000, 0), "ingotAmericium243",
				emptyParticleStack(), new ParticleStack(Particles.photon), emptyParticleStack(), 5000, 1, 5540);
		
		addRecipe("ingotAmericium243", new ParticleStack(Particles.neutron, 1000000, 9000), "ingotAmericium242",
				emptyParticleStack(), new ParticleStack(Particles.neutron, 2), emptyParticleStack(), 14500, 1, -5540);

		addRecipe("ingotCopernicium291All", new ParticleStack(Particles.neutron, 1000, 60000), "wasteFissionHeavy",
				emptyParticleStack(), new ParticleStack(Particles.neutron,8), emptyParticleStack(), 1000000, 1, 0);
		
		addRecipe("dustTerbium", new ParticleStack(Particles.neutron, 20000000, 20800), "dustEuropium155",
				new ParticleStack(Particles.alpha), new ParticleStack(Particles.neutron), emptyParticleStack(), 34000, 0.5, 883);
		
		
		//deuteron reactions
		
		addRecipe("ingotLithium6", new ParticleStack(Particles.deuteron, 6250000, 500), "ingotBeryllium7",
				emptyParticleStack(), new ParticleStack(Particles.neutron), emptyParticleStack(), 3000, 0.16, 2870);
		
		addRecipe("ingotLithium7", new ParticleStack(Particles.deuteron, 1000000, 3500), emptyItemStack(),
				 new ParticleStack(Particles.alpha,2), new ParticleStack(Particles.neutron), emptyParticleStack(), 9000, 1.0, 15100);
		
		addRecipe("ingotBeryllium", new ParticleStack(Particles.deuteron, 15625000, 34000), "ingotBeryllium7",
				new ParticleStack(Particles.deuteron), new ParticleStack(Particles.neutron,2), emptyParticleStack(), 53000, 0.064, -20600);
		
		addRecipe("ingotBeryllium", new ParticleStack(Particles.deuteron, 1600000, 1500), "ingotLithium7",
				new ParticleStack(Particles.alpha), emptyParticleStack(), emptyParticleStack(), 5000, 0.625, 7660);
		
		addRecipe("ingotBeryllium", new ParticleStack(Particles.deuteron, 1000000, 55000), emptyItemStack(),
				 new ParticleStack(Particles.alpha,2), new ParticleStack(Particles.neutron), new ParticleStack(Particles.deuteron), 150000, 1.0, -1570);
		
		addRecipe("ingotBoron11", new ParticleStack(Particles.deuteron,  2000000, 1500), "dustGraphite",
				emptyParticleStack(), new ParticleStack(Particles.neutron), emptyParticleStack(), 3000, 0.5, 13200);
		
		addRecipe("dustGraphite", new ParticleStack(Particles.deuteron, 4000000, 5000), "ingotBoron10",
				new ParticleStack(Particles.alpha), emptyParticleStack(), emptyParticleStack(), 13000, 0.625, -830);
		
		addRecipe("ingotSodium",new ParticleStack(Particles.deuteron, 2500000, 30000), "ingotSodium22",
				new ParticleStack(Particles.deuteron), new ParticleStack(Particles.neutron), emptyParticleStack(), 55000, 0.4, -12400);
		
		addRecipe("ingotMagnesium24",new ParticleStack(Particles.deuteron, 2500000, 6000), "ingotSodium22",
				new ParticleStack(Particles.alpha), emptyParticleStack(), emptyParticleStack(), 11000, 0.4, 2470);
		
		addRecipe("ingotAluminum",new ParticleStack(Particles.deuteron, 12500000, 56000), "ingotSodium22",
				new ParticleStack(Particles.alpha), emptyParticleStack(), new ParticleStack(Particles.triton), 200000, 0.08, -15200);
		
		addRecipe("ingotCobalt",new ParticleStack(Particles.deuteron, 2500000, 5000), "ingotCobalt60",
				new ParticleStack(Particles.proton), emptyParticleStack(), emptyParticleStack(), 10000, 0.4, 5270);
		
		addRecipe("ingotYttrium",new ParticleStack(Particles.deuteron, 15625000, 60000), "ingotStrontium",
				new ParticleStack(Particles.alpha), emptyParticleStack(), emptyParticleStack(), 200000, 0.064, 8400);
		
		addRecipe("ingotOsmium",new ParticleStack(Particles.deuteron, 1000000, 12000), "ingotIridium192",
				emptyParticleStack(), new ParticleStack(Particles.neutron,2), emptyParticleStack(), 14500, 1.0, -4560);
		
		addRecipe("dustBismuth",new ParticleStack(Particles.deuteron, 12500000, 10000), "dustPolonium",
				emptyParticleStack(), new ParticleStack(Particles.neutron), emptyParticleStack(), 15000, 0.08, 2250);
		
		addRecipe("ingotUranium233",new ParticleStack(Particles.deuteron, 12500000, 10000), "ingotUranium234",
				new ParticleStack(Particles.proton), emptyParticleStack(), emptyParticleStack(), 16000, 0.08, 4620);
		
		addRecipe("ingotUranium234",new ParticleStack(Particles.deuteron, 3125000, 11000), "ingotUranium235",
				new ParticleStack(Particles.proton), emptyParticleStack(), emptyParticleStack(), 17000, 0.32, 3070);
		
		addRecipe("ingotUranium235",new ParticleStack(Particles.deuteron, 31250000, 11500), "ingotNeptunium236",
				emptyParticleStack(), new ParticleStack(Particles.neutron), emptyParticleStack(), 19500, 0.032, 2090);
		
		addRecipe("ingotUranium238",new ParticleStack(Particles.deuteron, 1000000, 24000), "ingotNeptunium236",
				emptyParticleStack(), new ParticleStack(Particles.neutron,4), emptyParticleStack(), 30000, 1.0, -15700);
		
		addRecipe("ingotPlutonium238",new ParticleStack(Particles.deuteron, 5000000, 11000), "ingotPlutonium239",
				new ParticleStack(Particles.proton), emptyParticleStack(), emptyParticleStack(), 17000, 0.2, 3420);
		
		addRecipe("ingotPlutonium241",new ParticleStack(Particles.deuteron, 2000000, 10500), "ingotAmericium241",
				emptyParticleStack(), new ParticleStack(Particles.neutron,2), emptyParticleStack(), 19500, 0.5, -3500);
		
		addRecipe("ingotPlutonium242", new ParticleStack(Particles.deuteron,2000000,12000), "ingotAmericium242",
				emptyParticleStack(), new ParticleStack(Particles.neutron, 2), emptyParticleStack(), 16000,  0.5, -4270);
		
		addRecipe("ingotAmericium243", new ParticleStack(Particles.deuteron,5000000,12000), "ingotCurium243",
				emptyParticleStack(), new ParticleStack(Particles.neutron, 2), emptyParticleStack(), 15000,  0.2, -3520);
		
		
		
		

		
		// photon reactions
		
		addRecipe("ingotLithium6", new ParticleStack(Particles.photon, 50000000, 5500), emptyItemStack(),
				new ParticleStack(Particles.alpha), new ParticleStack(Particles.neutron), new ParticleStack(Particles.proton), 17000, 0.02, -3700);
		
		addRecipe("ingotLithium7", new ParticleStack(Particles.photon, 50000000, 18500), "ingotLithium6",
				emptyParticleStack(), new ParticleStack(Particles.neutron), emptyParticleStack(), 24000, 0.02, -7250);
		
		addRecipe("ingotBeryllium", new ParticleStack(Particles.photon, 50000000, 26000), emptyItemStack(),
				new ParticleStack(Particles.alpha,2), new ParticleStack(Particles.neutron), emptyParticleStack(), 46000, 0.02, 472);
		
		addRecipe("ingotBoron11", new ParticleStack(Particles.photon, 50000000, 24500), emptyItemStack(),
				new ParticleStack(Particles.alpha,2), new ParticleStack(Particles.neutron), new ParticleStack(Particles.deuteron), 30500, 0.02, -14800);
		
		addRecipe("dustGraphite", new ParticleStack(Particles.photon, 50000000, 31500), "ingotBeryllium7",
				new ParticleStack(Particles.alpha), new ParticleStack(Particles.neutron), emptyParticleStack(), 42500, 0.02, -25200);
		
		addRecipe("dustGraphite", new ParticleStack(Particles.photon, 50000000, 42500), "ingotLithium6",
				new ParticleStack(Particles.alpha), new ParticleStack(Particles.neutron), new ParticleStack(Particles.proton), 55000, 0.02, -30300);
		
		addRecipe("ingotMagnesium26", new ParticleStack(Particles.photon, 25000000, 21000), "ingotMagnesium24",
				emptyParticleStack(), new ParticleStack(Particles.neutron, 2), emptyParticleStack(), 28000, 0.04, -18400);
		
		addRecipe("ingotAluminum", new ParticleStack(Particles.photon,31250000, 17500), "ingotMagnesium26",
				new ParticleStack(Particles.proton), emptyParticleStack(), emptyParticleStack(), 23000, 0.032,-7760);
		
		addRecipe("itemSilicon", new ParticleStack(Particles.photon, 12500000, 18000), "ingotAluminum",
				new ParticleStack(Particles.proton), emptyParticleStack(), emptyParticleStack(), 23000, 0.08,-11100);
		
		addRecipe("ingotIron", new ParticleStack(Particles.photon, 8000000, 14500), "ingotManganese",
				new ParticleStack(Particles.proton), emptyParticleStack(), emptyParticleStack(), 21000, 0.125,-9670);
		
		addRecipe("ingotCopper", new ParticleStack(Particles.photon, 20000000, 19500), "ingotNickel",
				new ParticleStack(Particles.proton), new ParticleStack(Particles.neutron), emptyParticleStack(), 27000, 0.05,-16200);
		
		addRecipe("ingotZirconium", new ParticleStack(Particles.photon, 25000000, 11500), "ingotYttrium",
				new ParticleStack(Particles.proton), emptyParticleStack(), emptyParticleStack(), 19000, 0.04,-7830);
		
		addRecipe("ingotNiobium", new ParticleStack(Particles.photon, 50000000, 13000), "ingotYttrium",
				new ParticleStack(Particles.alpha), emptyParticleStack(), emptyParticleStack(), 21000, 0.02,-909);

		addRecipe("ingotUranium235", new ParticleStack(Particles.photon, 3125000, 13500), "ingotUranium233",
				emptyParticleStack(), new ParticleStack(Particles.neutron,2), emptyParticleStack(), 18000, 0.32,-12100);
		
		addRecipe("ingotNeptunium237", new ParticleStack(Particles.photon, 1000000, 9750), "ingotNeptunium236",
				emptyParticleStack(), new ParticleStack(Particles.neutron), emptyParticleStack(), 14500, 1.0,-6580);
		
		addRecipe("ingotPlutonium239", new ParticleStack(Particles.photon, 2500000, 10000), "ingotPlutonium238",
				emptyParticleStack(), new ParticleStack(Particles.neutron), emptyParticleStack(), 13500, 0.4,-5650);
		
		addRecipe("ingotAmericium243", new ParticleStack(Particles.photon, 1000000, 10000), "ingotAmericium242",
				emptyParticleStack(), new ParticleStack(Particles.neutron), emptyParticleStack(), 12500, 1,-6360);
		
		
		
		
		// triton reactions
		
		addRecipe("ingotBeryllium", new ParticleStack(Particles.triton, 1000000, 1000), "ingotBoron11",
				emptyParticleStack(), new ParticleStack(Particles.neutron), emptyParticleStack(), 7000, 1, 9050);	
		
		addRecipe("dustGraphite", new ParticleStack(Particles.triton, 1600000, 2500), "ingotBoron11",
				new ParticleStack(Particles.alpha), emptyParticleStack(), emptyParticleStack(), 7500, 0.625, 4370);	
		
		
		
		
		// alpha reactions
		
		addRecipe("ingotLithium6", new ParticleStack(Particles.alpha, 50000000, 27000), "ingotBoron10",
				emptyParticleStack(), new ParticleStack(Particles.photon), emptyParticleStack(), 45000, 0.02, 9330);	
		
		addRecipe("ingotLithium7", new ParticleStack(Particles.alpha, 2000000, 6500), "ingotBoron10",
				emptyParticleStack(), new ParticleStack(Particles.neutron), emptyParticleStack(), 7600, 0.5, -3810);	
		
		addRecipe("ingotBeryllium", new ParticleStack(Particles.alpha, 1000000, 4000), "dustGraphite",
				emptyParticleStack(), new ParticleStack(Particles.neutron), emptyParticleStack(), 6500, 1.0, 4680);	
			
		addRecipe("ingotBeryllium", new ParticleStack(Particles.alpha, 15625000, 100000), "ingotBeryllium7",
				new ParticleStack(Particles.alpha), new ParticleStack(Particles.neutron,2), emptyParticleStack(), 145000, 0.064, -20500);	
		
		addRecipe("dustGraphite", new ParticleStack(Particles.alpha, 6250000, 53000), "ingotBoron10",
				new ParticleStack(Particles.alpha), new ParticleStack(Particles.neutron),new ParticleStack(Particles.proton), 69000, 0.16, -26900);	
		
		addRecipe("dustGraphite", new ParticleStack(Particles.alpha, 227100, 38000), "ingotBoron11",
				new ParticleStack(Particles.alpha), emptyParticleStack(),new ParticleStack(Particles.proton), 50000, 0.25, -15400);	
		
		addRecipe("ingotAluminum", new ParticleStack(Particles.alpha, 4000000, 12000), "itemSilicon",
				new ParticleStack(Particles.positron), new ParticleStack(Particles.neutron), new ParticleStack(Particles.electron_neutrino), 17500, 0.4, -3660);	
		
		addRecipe("ingotCobalt", new ParticleStack(Particles.alpha, 6250000, 38000), "ingotCobalt60",
				new ParticleStack(Particles.proton,2), new ParticleStack(Particles.neutron), emptyParticleStack(), 54000, 0.16, -20800);	
		
		addRecipe("ingotOsmium", new ParticleStack(Particles.alpha, 40000000, 19000), "ingotPlatinum",
				emptyParticleStack(), new ParticleStack(Particles.neutron), emptyParticleStack(), 27500, 0.025, -9750);	
		
		addRecipe("ingotLead", new ParticleStack(Particles.alpha, 1000000, 26000), "dustPolonium",
				emptyParticleStack(), new ParticleStack(Particles.neutron,2), emptyParticleStack(), 32000, 1.0, -20500);	
		
		addRecipe("ingotUranium233", new ParticleStack(Particles.alpha, 50000000, 27000), "ingotNeptunium236",
				new ParticleStack(Particles.proton), emptyParticleStack(), emptyParticleStack(), 30000, 0.02, -11800);	
		
		addRecipe("ingotUranium235", new ParticleStack(Particles.alpha, 50000000, 21000), "ingotPlutonium238",
				emptyParticleStack(), new ParticleStack(Particles.neutron), emptyParticleStack(), 32000, 0.02, -11900);	
		
		addRecipe("ingotUranium238", new ParticleStack(Particles.alpha, 1600000, 27000), "ingotPlutonium239",
				emptyParticleStack(), new ParticleStack(Particles.neutron,3), emptyParticleStack(), 30000, 0.625, -24100);	
		
		addRecipe("ingotUranium238", new ParticleStack(Particles.alpha, 7812500, 34500), "ingotPlutonium238",
				emptyParticleStack(), new ParticleStack(Particles.neutron,3), emptyParticleStack(), 42000, 0.128, -29700);	
		
		addRecipe("ingotPlutonium239", new ParticleStack(Particles.alpha, 50000000, 28000), "ingotAmericium242",
				new ParticleStack(Particles.proton), emptyParticleStack(), emptyParticleStack(), 30000, 0.02, -12300);	
		
		addRecipe("dustYtterbium", new ParticleStack(Particles.alpha, 1000000, 21200), "ingotHafnium",
				emptyParticleStack(), new ParticleStack(Particles.neutron,2), emptyParticleStack(), 26600, 1.0, -15800);	
		
		
		//Helion reactions
		addRecipe("ingotLithium6", new ParticleStack(Particles.helion, 2000000, 11000), "ingotBeryllium7",
				new ParticleStack(Particles.deuteron), emptyParticleStack(), emptyParticleStack(), 30000, 0.5, -397);	
		
		addRecipe("ingotLithium6", new ParticleStack(Particles.helion, 1600000, 30000), emptyItemStack(),
				new ParticleStack(Particles.alpha,2), emptyParticleStack(), new ParticleStack(Particles.proton), 150000, 0.625, 18400);	
		
		addRecipe("ingotBeryllium", new ParticleStack(Particles.helion, 6250000, 23000), "ingotBeryllium7",
				new ParticleStack(Particles.alpha), new ParticleStack(Particles.neutron), emptyParticleStack(), 28000, 0.16, 14);	
		
		addRecipe("ingotBoron10", new ParticleStack(Particles.helion, 5000000, 30000), "ingotBeryllium7",
				new ParticleStack(Particles.alpha), new ParticleStack(Particles.neutron), new ParticleStack(Particles.proton), 47000, 0.2, -6060);	
		
		addRecipe("dustGraphite", new ParticleStack(Particles.helion, 8000000, 22000), "ingotBeryllium7",
				new ParticleStack(Particles.alpha,2), emptyParticleStack(), emptyParticleStack(), 31000, 0.125, -4670);	
		
		addRecipe("ingotAluminum", new ParticleStack(Particles.helion, 6250000, 90000), "ingotSodium22",
				new ParticleStack(Particles.alpha,2), emptyParticleStack(), emptyParticleStack(), 165000, 0.16, -912);
		
		addRecipe("ingotCobalt", new ParticleStack(Particles.helion, 10000000, 14000), "ingotCobalt60",
				new ParticleStack(Particles.proton,2), emptyParticleStack(), emptyParticleStack(), 24000, 0.1, -226);
		
		addRecipe("ingotLead", new ParticleStack(Particles.helion, 50000000, 23000), "dustPolonium",
				emptyParticleStack(), new ParticleStack(Particles.neutron), emptyParticleStack(), 30000, 0.02, 42);
		
		addRecipe("ingotNeptunium237", new ParticleStack(Particles.helion, 50000000, 23500), "ingotPlutonium238",
				new ParticleStack(Particles.proton), new ParticleStack(Particles.neutron), emptyParticleStack(), 30000, 0.02, -2230);
		
	
		
		// Doping
		addRecipe("siliconWafer", new ParticleStack(Particles.boron_ion, 1000000, 600,2), "siliconPDoped",
				emptyParticleStack(),emptyParticleStack(),emptyParticleStack(), 1000, 1);
		
		// heavy ion bombardment
		addRecipe("ingotBerkelium248", new ParticleStack(Particles.calcium_48_ion, 50000000, 40000,2), "ingotCopernicium291",
				new ParticleStack(Particles.alpha),new ParticleStack(Particles.neutron),new ParticleStack(Particles.electron_neutrino,2), 50000, 0.02,-24400);
		
		// Inverse beta + decay
		
		addRecipe("ingotNickel", new ParticleStack(Particles.electron_antineutrino, 100000000, 900), "ingotIron",
				new ParticleStack(Particles.positron,2), new ParticleStack(Particles.electron_neutrino), emptyParticleStack(), 10900, 0.01, -893);	
		
		addRecipe("ingotIridium192", new ParticleStack(Particles.electron_antineutrino, 100000000, 0), "ingotOsmium",
				new ParticleStack(Particles.positron), emptyParticleStack(), emptyParticleStack(), 10000, 0.01, 536);	
		
		addRecipe("ingotAmericium242", new ParticleStack(Particles.electron_antineutrino, 100000000, 0), "ingotPlutonium242",
				new ParticleStack(Particles.positron), emptyParticleStack(), emptyParticleStack(), 10000, 0.01, 240);	
		
		addRecipe("ingotCurium243", new ParticleStack(Particles.electron_antineutrino, 100000000, 600), "ingotAmericium243",
				new ParticleStack(Particles.positron), emptyParticleStack(), emptyParticleStack(), 10600, 0.01, -503);	
		
		addRecipe("ingotBerkelium247", new ParticleStack(Particles.electron_antineutrino, 100000000, 600), "ingotCurium247",
				new ParticleStack(Particles.positron), emptyParticleStack(), emptyParticleStack(), 10600, 0.01, -555);	
		
		
		
		// Inverse beta - decay
		
		addRecipe("ingotOsmium", new ParticleStack(Particles.electron_neutrino, 100000000, 1600), "ingotIridium192",
				emptyParticleStack(), emptyParticleStack(), new ParticleStack(Particles.electron), 11600, 0.01, -1560);	
		
		addRecipe("ingotPlutonium242", new ParticleStack(Particles.electron_neutrino, 100000000, 1300), "ingotAmericium242",
				emptyParticleStack(), emptyParticleStack(), new ParticleStack(Particles.electron), 11300, 0.01, -1260);	
		
		addRecipe("ingotAmericium243", new ParticleStack(Particles.electron_neutrino, 100000000, 600), "ingotCurium243",
				emptyParticleStack(), emptyParticleStack(), new ParticleStack(Particles.electron), 10600, 0.01, -519);	
		
		addRecipe("ingotCurium247", new ParticleStack(Particles.electron_neutrino, 100000000, 500), "ingotBerkelium247",
				emptyParticleStack(), emptyParticleStack(), new ParticleStack(Particles.electron), 10500, 0.01, -467);	
		
		
		
		// proton induced fission
		
		addRecipe("ingotTungsten", new ParticleStack(Particles.proton, 12500000, 400000), "wasteFissionLight",
				emptyParticleStack(), new ParticleStack(Particles.neutron), emptyParticleStack(), 1000000, 0.08, 0);
		
		addRecipe("ingotPlatinum", new ParticleStack(Particles.proton, 50000000, 200000), "wasteFissionLight",
				emptyParticleStack(),new ParticleStack(Particles.neutron), emptyParticleStack(), 1000000, 0.02, 0);
		
		addRecipe("ingotGold", new ParticleStack(Particles.proton, 6250000, 200000), "wasteFissionLight",
				emptyParticleStack(), new ParticleStack(Particles.neutron), emptyParticleStack(), 1000000, 0.16, 0);
		
		addRecipe("ingotMercury", new ParticleStack(Particles.proton, 50000000, 200000), "wasteFissionLight",
				emptyParticleStack(), new ParticleStack(Particles.neutron), emptyParticleStack(), 1000000, 0.02, 0);
		
		addRecipe("ingotLead", new ParticleStack(Particles.proton, 4000000, 200000), "wasteFissionLight",
				emptyParticleStack(), new ParticleStack(Particles.neutron), emptyParticleStack(), 1000000, 0.25, 0);
		
		addRecipe("dustBismuth", new ParticleStack(Particles.proton, 2500000, 100000), "wasteFissionLight",
				emptyParticleStack(), new ParticleStack(Particles.neutron), emptyParticleStack(), 1000000, 0.4, 0);
		
		addRecipe("dustRadium", new ParticleStack(Particles.proton, 5000000, 60000), "wasteFissionHeavy",
				emptyParticleStack(), new ParticleStack(Particles.neutron), emptyParticleStack(), 1000000, 0.2, 0);
		
		addRecipe("ingotThorium", new ParticleStack(Particles.proton, 1000000, 60000), "wasteFissionHeavy",
				emptyParticleStack(), new ParticleStack(Particles.neutron), emptyParticleStack(), 1000000, 1.0, 0);
		
		addRecipe("ingotUranium233All", new ParticleStack(Particles.proton, 1000000, 60000), "wasteFissionHeavy",
				emptyParticleStack(), new ParticleStack(Particles.neutron), emptyParticleStack(), 1000000, 1.0, 0);
		
		addRecipe("ingotUranium234All", new ParticleStack(Particles.proton, 1000000, 60000), "wasteFissionHeavy",
				emptyParticleStack(), new ParticleStack(Particles.neutron,2), emptyParticleStack(), 1000000, 1.0, 0);
		
		addRecipe("ingotUranium235All", new ParticleStack(Particles.proton, 1000000, 60000), "wasteFissionHeavy",
				emptyParticleStack(), new ParticleStack(Particles.neutron,4), emptyParticleStack(), 1000000, 1.0, 0);
		
		addRecipe("ingotUranium238All", new ParticleStack(Particles.proton, 1000000, 60000), "wasteFissionHeavy",
				emptyParticleStack(), new ParticleStack(Particles.neutron,2), emptyParticleStack(), 1000000, 1.0, 0);
		
		addRecipe("ingotNeptunium237All", new ParticleStack(Particles.proton, 1000000, 60000), "wasteFissionHeavy",
				emptyParticleStack(), new ParticleStack(Particles.neutron,2), emptyParticleStack(), 1000000, 1.0, 0);
		
		addRecipe("ingotPlutonium239All", new ParticleStack(Particles.proton, 1000000, 60000), "wasteFissionHeavy",
				emptyParticleStack(), new ParticleStack(Particles.neutron,4), emptyParticleStack(), 1000000, 1.0, 0);
		
		addRecipe("ingotPlutonium241All", new ParticleStack(Particles.proton, 1000000, 60000), "wasteFissionHeavy",
				emptyParticleStack(), new ParticleStack(Particles.neutron,4), emptyParticleStack(), 1000000, 1.0, 0);
		
		addRecipe("ingotPlutonium242All", new ParticleStack(Particles.proton, 1000000, 60000), "wasteFissionHeavy",
				emptyParticleStack(), new ParticleStack(Particles.neutron,4), emptyParticleStack(), 1000000, 1.0, 0);
		
		addRecipe("ingotAmericium241All", new ParticleStack(Particles.proton, 1000000, 60000), "wasteFissionHeavy",
				emptyParticleStack(), new ParticleStack(Particles.neutron,4), emptyParticleStack(), 1000000, 1.0, 0);
		
		addRecipe("ingotAmericium243All", new ParticleStack(Particles.proton, 1000000, 60000), "wasteFissionHeavy",
				emptyParticleStack(), new ParticleStack(Particles.neutron,2), emptyParticleStack(), 1000000, 1.0, 0);
		
		addRecipe("ingotCopernicium291All", new ParticleStack(Particles.proton, 1000000, 60000), "wasteFissionHeavy",
				emptyParticleStack(), new ParticleStack(Particles.neutron,8), emptyParticleStack(), 1000000, 1.0, 0);
		

		
		
		// antiproton production 
		addRecipe("ingotCalifornium252All", new ParticleStack(Particles.proton, 5000000, 6580000), "wasteSpallationCalifornium", 
				new ParticleStack(Particles.proton), emptyParticleStack(), new ParticleStack(Particles.antiproton), 20000000, 0.2, -6580000);
		
		addRecipe("ingotCalifornium251All", new ParticleStack(Particles.proton, 5000000, 6580000), "wasteSpallationCalifornium",
				new ParticleStack(Particles.proton), emptyParticleStack(), new ParticleStack(Particles.antiproton), 20000000, 0.2, -6580000);
		
		addRecipe("ingotCalifornium250All", new ParticleStack(Particles.proton, 5000000, 6580000), "wasteSpallationCalifornium",
				new ParticleStack(Particles.proton), emptyParticleStack(), new ParticleStack(Particles.antiproton), 20000000, 0.2, -6580000);
		
		addRecipe("ingotCalifornium249All", new ParticleStack(Particles.proton, 5000000, 6580000), "wasteSpallationCalifornium",
				new ParticleStack(Particles.proton), emptyParticleStack(), new ParticleStack(Particles.antiproton), 20000000, 0.2, -6580000);
		
		addRecipe("ingotBerkelium248All", new ParticleStack(Particles.proton, 5000000, 6580000), "wasteSpallationBerkelium",
				new ParticleStack(Particles.proton), emptyParticleStack(), new ParticleStack(Particles.antiproton), 20000000, 0.2, -6580000);
		
		addRecipe("ingotBerkelium247All", new ParticleStack(Particles.proton, 5000000, 6580000), "wasteSpallationBerkelium",
				new ParticleStack(Particles.proton), emptyParticleStack(), new ParticleStack(Particles.antiproton), 20000000, 0.2, -6580000);
		
		addRecipe("ingotCurium247All", new ParticleStack(Particles.proton, 5000000, 6580000), "wasteSpallationCurium",
				new ParticleStack(Particles.proton), emptyParticleStack(), new ParticleStack(Particles.antiproton), 20000000, 0.2, -6580000);
		
		addRecipe("ingotCurium246All", new ParticleStack(Particles.proton, 5000000, 6580000), "wasteSpallationCurium",
				new ParticleStack(Particles.proton), emptyParticleStack(), new ParticleStack(Particles.antiproton), 20000000, 0.2, -6580000);
		
		addRecipe("ingotCurium245All", new ParticleStack(Particles.proton, 5000000, 6580000), "wasteSpallationCurium",
				new ParticleStack(Particles.proton), emptyParticleStack(), new ParticleStack(Particles.antiproton), 20000000, 0.2, -6580000);
		
		addRecipe("ingotCurium243All", new ParticleStack(Particles.proton, 5000000, 6580000), "wasteSpallationCurium",
				new ParticleStack(Particles.proton), emptyParticleStack(), new ParticleStack(Particles.antiproton), 20000000, 0.2, -6580000);
		
		addRecipe("ingotAmericium243All", new ParticleStack(Particles.proton, 5000000, 6580000), "wasteSpallationAmericium",
				new ParticleStack(Particles.proton), emptyParticleStack(), new ParticleStack(Particles.antiproton), 20000000, 0.2, -6580000);
		
		addRecipe("ingotAmericium242All", new ParticleStack(Particles.proton, 5000000, 6580000), "wasteSpallationAmericium",
				new ParticleStack(Particles.proton), emptyParticleStack(), new ParticleStack(Particles.antiproton), 20000000, 0.2, -6580000);
		
		addRecipe("ingotAmericium241All", new ParticleStack(Particles.proton, 5000000, 6580000), "wasteSpallationAmericium",
				new ParticleStack(Particles.proton), emptyParticleStack(), new ParticleStack(Particles.antiproton), 20000000, 0.2, -6580000);
		
		addRecipe("ingotPlutonium242All", new ParticleStack(Particles.proton, 5000000, 6580000), "wasteSpallationPlutonium",
				new ParticleStack(Particles.proton), emptyParticleStack(), new ParticleStack(Particles.antiproton), 20000000, 0.2, -6580000);
		
		addRecipe("ingotPlutonium241All", new ParticleStack(Particles.proton, 5000000, 6580000), "wasteSpallationPlutonium",
				new ParticleStack(Particles.proton), emptyParticleStack(), new ParticleStack(Particles.antiproton), 20000000, 0.2, -6580000);
		
		addRecipe("ingotPlutonium239All", new ParticleStack(Particles.proton, 5000000, 6580000), "wasteSpallationPlutonium",
				new ParticleStack(Particles.proton), emptyParticleStack(), new ParticleStack(Particles.antiproton), 20000000, 0.2, -6580000);
		
		addRecipe("ingotPlutonium238All", new ParticleStack(Particles.proton, 5000000, 6580000), "wasteSpallationPlutonium",
				new ParticleStack(Particles.proton), emptyParticleStack(), new ParticleStack(Particles.antiproton), 20000000, 0.2, -6580000);
		
		addRecipe("ingotNeptunium237All", new ParticleStack(Particles.proton, 5000000, 6580000), "wasteSpallationNeptunium",
				new ParticleStack(Particles.proton), emptyParticleStack(), new ParticleStack(Particles.antiproton), 20000000, 0.2, -6580000);
		
		addRecipe("ingotNeptunium236All", new ParticleStack(Particles.proton, 5000000, 6580000), "wasteSpallationNeptunium",
				new ParticleStack(Particles.proton), emptyParticleStack(), new ParticleStack(Particles.antiproton), 20000000, 0.2, -6580000);
		
		addRecipe("ingotUranium238All", new ParticleStack(Particles.proton, 5000000, 6580000), "wasteSpallationUranium",
				new ParticleStack(Particles.proton), emptyParticleStack(), new ParticleStack(Particles.antiproton), 20000000, 0.2, -6580000);
		
		addRecipe("ingotUranium235All", new ParticleStack(Particles.proton, 5000000, 6580000), "wasteSpallationUranium",
				new ParticleStack(Particles.proton), emptyParticleStack(), new ParticleStack(Particles.antiproton), 20000000, 0.2, -6580000);
		
		addRecipe("ingotUranium234", new ParticleStack(Particles.proton, 5000000, 6580000), "wasteSpallationUranium",
				new ParticleStack(Particles.proton), emptyParticleStack(), new ParticleStack(Particles.antiproton), 20000000, 0.2, -6580000);
		
		addRecipe("ingotUranium233All", new ParticleStack(Particles.proton, 5000000, 6580000), "wasteSpallationUranium",
				new ParticleStack(Particles.proton), emptyParticleStack(), new ParticleStack(Particles.antiproton), 20000000, 0.2, -6580000);
		
		addRecipe("dustProtactinium233", new ParticleStack(Particles.proton, 5000000, 6580000), "wasteSpallationProtactinium",
				new ParticleStack(Particles.proton), emptyParticleStack(), new ParticleStack(Particles.antiproton), 20000000, 0.2, -6580000);
		
		addRecipe("dustProtactinium231", new ParticleStack(Particles.proton, 5000000, 6580000), "wasteSpallationProtactinium",
				new ParticleStack(Particles.proton), emptyParticleStack(), new ParticleStack(Particles.antiproton), 20000000, 0.2, -6580000);
		
		addRecipe("ingotThorium", new ParticleStack(Particles.proton, 5000000, 6580000), "wasteSpallationThorium",
				new ParticleStack(Particles.proton), emptyParticleStack(), new ParticleStack(Particles.antiproton), 20000000, 0.2, -6580000);
		
		addRecipe("dustRadium", new ParticleStack(Particles.proton, 5000000, 6580000), "wasteSpallationRadium",
				new ParticleStack(Particles.proton), emptyParticleStack(), new ParticleStack(Particles.antiproton), 20000000, 0.2, -6580000);
		
		addRecipe("dustPolonium", new ParticleStack(Particles.proton, 5000000, 6580000), "wasteSpallationPolonium",
				new ParticleStack(Particles.proton), emptyParticleStack(), new ParticleStack(Particles.antiproton), 20000000, 0.2, -6580000);
		
		addRecipe("dustBismuth", new ParticleStack(Particles.proton, 5000000, 6580000), "wasteSpallationBismuth",
				new ParticleStack(Particles.proton), emptyParticleStack(), new ParticleStack(Particles.antiproton), 20000000, 0.2, -6580000);
		
		addRecipe("ingotLead", new ParticleStack(Particles.proton, 5000000, 6580000), "wasteSpallationLead",
				new ParticleStack(Particles.proton), emptyParticleStack(), new ParticleStack(Particles.antiproton), 20000000, 0.2, -6580000);
		
		addRecipe("ingotMercury", new ParticleStack(Particles.proton, 5000000, 6580000), "wasteSpallationMercury",
				new ParticleStack(Particles.proton), emptyParticleStack(), new ParticleStack(Particles.antiproton), 20000000, 0.2, -6580000);
		
		addRecipe("ingotGold", new ParticleStack(Particles.proton, 5000000, 6580000), "wasteSpallationGold",
				new ParticleStack(Particles.proton), emptyParticleStack(), new ParticleStack(Particles.antiproton), 20000000, 0.2, -6580000);
		
		addRecipe("ingotPlatinum", new ParticleStack(Particles.proton, 5000000, 6580000), "wasteSpallationPlatinum",
				new ParticleStack(Particles.proton), emptyParticleStack(), new ParticleStack(Particles.antiproton), 20000000, 0.2, -6580000);
		
		addRecipe("ingotIridium", new ParticleStack(Particles.proton, 5000000, 6580000), "wasteSpallationIridium",
				new ParticleStack(Particles.proton), emptyParticleStack(), new ParticleStack(Particles.antiproton), 20000000, 0.2, -6580000);
		
		addRecipe("ingotIridium192", new ParticleStack(Particles.proton, 5000000, 6580000), "wasteSpallationIridium",
				new ParticleStack(Particles.proton), emptyParticleStack(), new ParticleStack(Particles.antiproton), 20000000, 0.2, -6580000);
	
		addRecipe("ingotOsmium", new ParticleStack(Particles.proton, 5000000, 6580000), "wasteSpallationOsmium",
				new ParticleStack(Particles.proton), emptyParticleStack(), new ParticleStack(Particles.antiproton), 20000000, 0.2, -6580000);
		
		addRecipe("ingotTungsten", new ParticleStack(Particles.proton, 5000000, 6580000), "wasteSpallationTungsten",
				new ParticleStack(Particles.proton), emptyParticleStack(), new ParticleStack(Particles.antiproton), 20000000, 0.2, -6580000);
		
		addRecipe("ingotHafnium", new ParticleStack(Particles.proton, 5000000, 6580000), "wasteSpallationHafnium",
				new ParticleStack(Particles.proton), emptyParticleStack(), new ParticleStack(Particles.antiproton), 20000000, 0.2, -6580000);
		
		
		
		
		
		
		// Pion production
		
		addRecipe("ingotCalifornium252All", new ParticleStack(Particles.proton, 5000000, 1540000), "wasteSpallationCalifornium",
				new ParticleStack(Particles.pion_plus),emptyParticleStack(),new ParticleStack(Particles.pion_minus), 5000000, 0.2, -1540000);
		
		addRecipe("ingotCalifornium251All", new ParticleStack(Particles.proton, 5000000, 1540000), "wasteSpallationCalifornium",
				new ParticleStack(Particles.pion_plus),emptyParticleStack(),new ParticleStack(Particles.pion_minus), 5000000, 0.2, -1540000);
		
		addRecipe("ingotCalifornium250All", new ParticleStack(Particles.proton, 5000000, 1540000), "wasteSpallationCalifornium",
				new ParticleStack(Particles.pion_plus),emptyParticleStack(),new ParticleStack(Particles.pion_minus), 5000000, 0.2, -1540000);
		
		addRecipe("ingotCalifornium249All", new ParticleStack(Particles.proton, 5000000, 1540000), "wasteSpallationCalifornium",
				new ParticleStack(Particles.pion_plus),emptyParticleStack(),new ParticleStack(Particles.pion_minus), 5000000, 0.2, -1540000);
		
		addRecipe("ingotBerkelium248All", new ParticleStack(Particles.proton, 5000000, 1540000), "wasteSpallationBerkelium",
				new ParticleStack(Particles.pion_plus),emptyParticleStack(),new ParticleStack(Particles.pion_minus), 5000000, 0.2, -1540000);
		
		addRecipe("ingotBerkelium247All", new ParticleStack(Particles.proton, 5000000, 1540000), "wasteSpallationBerkelium",
				new ParticleStack(Particles.pion_plus),emptyParticleStack(),new ParticleStack(Particles.pion_minus), 5000000, 0.2, -1540000);
		
		addRecipe("ingotCurium247All", new ParticleStack(Particles.proton, 5000000, 1540000), "wasteSpallationCurium",
				new ParticleStack(Particles.pion_plus),emptyParticleStack(),new ParticleStack(Particles.pion_minus), 5000000, 0.2, -1540000);
		
		addRecipe("ingotCurium246All", new ParticleStack(Particles.proton, 5000000, 1540000), "wasteSpallationCurium",
				new ParticleStack(Particles.pion_plus),emptyParticleStack(),new ParticleStack(Particles.pion_minus), 5000000, 0.2, -1540000);
		
		addRecipe("ingotCurium245All", new ParticleStack(Particles.proton, 5000000, 1540000), "wasteSpallationCurium",
				new ParticleStack(Particles.pion_plus),emptyParticleStack(),new ParticleStack(Particles.pion_minus), 5000000, 0.2, -1540000);
		
		addRecipe("ingotCurium243All", new ParticleStack(Particles.proton, 5000000, 1540000), "wasteSpallationCurium",
				new ParticleStack(Particles.pion_plus),emptyParticleStack(),new ParticleStack(Particles.pion_minus), 5000000, 0.2, -1540000);
		
		addRecipe("ingotAmericium243All", new ParticleStack(Particles.proton, 5000000, 1540000), "wasteSpallationAmericium",
				new ParticleStack(Particles.pion_plus),emptyParticleStack(),new ParticleStack(Particles.pion_minus), 5000000, 0.2, -1540000);
		
		addRecipe("ingotAmericium242All", new ParticleStack(Particles.proton, 5000000, 1540000), "wasteSpallationAmericium",
				new ParticleStack(Particles.pion_plus),emptyParticleStack(),new ParticleStack(Particles.pion_minus), 5000000, 0.2, -1540000);
		
		addRecipe("ingotAmericium241All", new ParticleStack(Particles.proton, 5000000, 1540000), "wasteSpallationAmericium",
				new ParticleStack(Particles.pion_plus),emptyParticleStack(),new ParticleStack(Particles.pion_minus), 5000000, 0.2, -1540000);
		
		addRecipe("ingotPlutonium242All", new ParticleStack(Particles.proton, 5000000, 1540000), "wasteSpallationPlutonium",
				new ParticleStack(Particles.pion_plus),emptyParticleStack(),new ParticleStack(Particles.pion_minus), 5000000, 0.2, -1540000);
		
		addRecipe("ingotPlutonium241All", new ParticleStack(Particles.proton, 5000000, 1540000), "wasteSpallationPlutonium",
				new ParticleStack(Particles.pion_plus),emptyParticleStack(),new ParticleStack(Particles.pion_minus), 5000000, 0.2, -1540000);
		
		addRecipe("ingotPlutonium239All", new ParticleStack(Particles.proton, 5000000, 1540000), "wasteSpallationPlutonium",
				new ParticleStack(Particles.pion_plus),emptyParticleStack(),new ParticleStack(Particles.pion_minus), 5000000, 0.2, -1540000);
		
		addRecipe("ingotPlutonium238All", new ParticleStack(Particles.proton, 5000000, 1540000), "wasteSpallationPlutonium",
				new ParticleStack(Particles.pion_plus),emptyParticleStack(),new ParticleStack(Particles.pion_minus), 5000000, 0.2, -1540000);
		
		addRecipe("ingotNeptunium237All", new ParticleStack(Particles.proton, 5000000, 1540000), "wasteSpallationNeptunium",
				new ParticleStack(Particles.pion_plus),emptyParticleStack(),new ParticleStack(Particles.pion_minus), 5000000, 0.2, -1540000);
		
		addRecipe("ingotNeptunium236All", new ParticleStack(Particles.proton, 5000000, 1540000), "wasteSpallationNeptunium",
				new ParticleStack(Particles.pion_plus),emptyParticleStack(),new ParticleStack(Particles.pion_minus), 5000000, 0.2, -1540000);
		
		addRecipe("ingotUranium238All", new ParticleStack(Particles.proton, 5000000, 1540000), "wasteSpallationUranium",
				new ParticleStack(Particles.pion_plus),emptyParticleStack(),new ParticleStack(Particles.pion_minus), 5000000, 0.2, -1540000);
		
		addRecipe("ingotUranium235All", new ParticleStack(Particles.proton, 5000000, 1540000), "wasteSpallationUranium",
				new ParticleStack(Particles.pion_plus),emptyParticleStack(),new ParticleStack(Particles.pion_minus), 5000000, 0.2, -1540000);
		
		addRecipe("ingotUranium234", new ParticleStack(Particles.proton, 5000000, 1540000), "wasteSpallationUranium",
				new ParticleStack(Particles.pion_plus),emptyParticleStack(),new ParticleStack(Particles.pion_minus), 5000000, 0.2, -1540000);
		
		addRecipe("ingotUranium233All", new ParticleStack(Particles.proton, 5000000, 1540000), "wasteSpallationUranium",
				new ParticleStack(Particles.pion_plus),emptyParticleStack(),new ParticleStack(Particles.pion_minus), 5000000, 0.2, -1540000);
	
		addRecipe("dustProtactinium233", new ParticleStack(Particles.proton, 5000000, 1540000), "wasteSpallationProtactinium",
				new ParticleStack(Particles.pion_plus),emptyParticleStack(),new ParticleStack(Particles.pion_minus), 5000000, 0.2, -1540000);
		
		addRecipe("dustProtactinium231", new ParticleStack(Particles.proton, 5000000, 1540000), "wasteSpallationProtactinium",
				new ParticleStack(Particles.pion_plus),emptyParticleStack(),new ParticleStack(Particles.pion_minus), 5000000, 0.2, -1540000);
		
		addRecipe("ingotThorium", new ParticleStack(Particles.proton, 5000000, 1540000), "wasteSpallationThorium",
				new ParticleStack(Particles.pion_plus),emptyParticleStack(),new ParticleStack(Particles.pion_minus), 5000000, 0.2, -1540000);
		
		addRecipe("dustRadium", new ParticleStack(Particles.proton, 5000000, 1540000), "wasteSpallationRadium",
				new ParticleStack(Particles.pion_plus),emptyParticleStack(),new ParticleStack(Particles.pion_minus), 5000000, 0.2, -1540000);
		
		addRecipe("dustPolonium", new ParticleStack(Particles.proton, 5000000, 1540000), "wasteSpallationPolonium",
				new ParticleStack(Particles.pion_plus),emptyParticleStack(),new ParticleStack(Particles.pion_minus), 5000000, 0.2, -1540000);
		
		addRecipe("dustBismuth", new ParticleStack(Particles.proton, 5000000, 1540000), "wasteSpallationBismuth",
				new ParticleStack(Particles.pion_plus),emptyParticleStack(),new ParticleStack(Particles.pion_minus), 5000000, 0.2, -1540000);
		
		addRecipe("ingotLead", new ParticleStack(Particles.proton, 5000000, 1540000), "wasteSpallationLead",
				new ParticleStack(Particles.pion_plus),emptyParticleStack(),new ParticleStack(Particles.pion_minus), 5000000, 0.2, -1540000);
		
		addRecipe("ingotMercury", new ParticleStack(Particles.proton, 5000000, 1540000), "wasteSpallationMercury",
				new ParticleStack(Particles.pion_plus),emptyParticleStack(),new ParticleStack(Particles.pion_minus), 5000000, 0.2, -1540000);
		
		addRecipe("ingotGold", new ParticleStack(Particles.proton, 5000000, 1540000), "wasteSpallationGold",
				new ParticleStack(Particles.pion_plus),emptyParticleStack(),new ParticleStack(Particles.pion_minus), 5000000, 0.2, -1540000);
		
		addRecipe("ingotPlatinum", new ParticleStack(Particles.proton, 5000000, 1540000), "wasteSpallationPlatinum",
				new ParticleStack(Particles.pion_plus),emptyParticleStack(),new ParticleStack(Particles.pion_minus), 5000000, 0.2, -1540000);
		
		addRecipe("ingotIridium", new ParticleStack(Particles.proton, 5000000, 1540000), "wasteSpallationIridium",
				new ParticleStack(Particles.pion_plus),emptyParticleStack(),new ParticleStack(Particles.pion_minus), 5000000, 0.2, -1540000);
		
		addRecipe("ingotIridium192", new ParticleStack(Particles.proton, 5000000, 1540000), "wasteSpallationIridium",
				new ParticleStack(Particles.pion_plus),emptyParticleStack(),new ParticleStack(Particles.pion_minus), 5000000, 0.2, -1540000);
		
		addRecipe("ingotOsmium", new ParticleStack(Particles.proton, 5000000, 1540000), "wasteSpallationOsmium",
				new ParticleStack(Particles.pion_plus),emptyParticleStack(),new ParticleStack(Particles.pion_minus), 5000000, 0.2, -1540000);
		
		addRecipe("ingotTungsten", new ParticleStack(Particles.proton, 5000000, 1540000), "wasteSpallationTungsten",
				new ParticleStack(Particles.pion_plus),emptyParticleStack(),new ParticleStack(Particles.pion_minus), 5000000, 0.2, -1540000);
		
		addRecipe("ingotHafnium", new ParticleStack(Particles.proton, 5000000, 1540000), "wasteSpallationHafnium",
				new ParticleStack(Particles.pion_plus),emptyParticleStack(),new ParticleStack(Particles.pion_minus), 5000000, 0.2, -1540000);
	
	
	
		
		// antiproton anhilation
		
		addRecipe("ingotCalifornium252All", new ParticleStack(Particles.antiproton, 1000000), "wasteSpallationCalifornium",
				new ParticleStack(Particles.pion_plus),new ParticleStack(Particles.pion_naught),new ParticleStack(Particles.pion_minus), 10000000, 1, 1460000);
		
		addRecipe("ingotCalifornium251All", new ParticleStack(Particles.antiproton, 1000000), "wasteSpallationCalifornium",
				new ParticleStack(Particles.pion_plus),new ParticleStack(Particles.pion_naught),new ParticleStack(Particles.pion_minus), 10000000, 1, 1460000);
		
		addRecipe("ingotCalifornium250All", new ParticleStack(Particles.antiproton, 1000000), "wasteSpallationCalifornium",
				new ParticleStack(Particles.pion_plus),new ParticleStack(Particles.pion_naught),new ParticleStack(Particles.pion_minus), 10000000, 1, 1460000);
		
		addRecipe("ingotCalifornium249All", new ParticleStack(Particles.antiproton, 1000000), "wasteSpallationCalifornium",
				new ParticleStack(Particles.pion_plus),new ParticleStack(Particles.pion_naught),new ParticleStack(Particles.pion_minus), 10000000, 1, 1460000);
		
		addRecipe("ingotBerkelium248All", new ParticleStack(Particles.antiproton, 1000000), "wasteSpallationBerkelium",
				new ParticleStack(Particles.pion_plus),new ParticleStack(Particles.pion_naught),new ParticleStack(Particles.pion_minus), 10000000, 1, 1460000);
		
		addRecipe("ingotBerkelium247All", new ParticleStack(Particles.antiproton, 1000000), "wasteSpallationBerkelium",
				new ParticleStack(Particles.pion_plus),new ParticleStack(Particles.pion_naught),new ParticleStack(Particles.pion_minus), 10000000, 1, 1460000);
		
		addRecipe("ingotCurium247All", new ParticleStack(Particles.antiproton, 1000000), "wasteSpallationCurium",
				new ParticleStack(Particles.pion_plus),new ParticleStack(Particles.pion_naught),new ParticleStack(Particles.pion_minus), 10000000, 1, 1460000);
		
		addRecipe("ingotCurium246All", new ParticleStack(Particles.antiproton, 1000000), "wasteSpallationCurium",
				new ParticleStack(Particles.pion_plus),new ParticleStack(Particles.pion_naught),new ParticleStack(Particles.pion_minus), 10000000, 1, 1460000);
		
		addRecipe("ingotCurium245All", new ParticleStack(Particles.antiproton, 1000000), "wasteSpallationCurium",
				new ParticleStack(Particles.pion_plus),new ParticleStack(Particles.pion_naught),new ParticleStack(Particles.pion_minus), 10000000, 1, 1460000);
		
		addRecipe("ingotCurium243All", new ParticleStack(Particles.antiproton, 1000000), "wasteSpallationCurium",
				new ParticleStack(Particles.pion_plus),new ParticleStack(Particles.pion_naught),new ParticleStack(Particles.pion_minus), 10000000, 1, 1460000);
		
		addRecipe("ingotAmericium243All", new ParticleStack(Particles.antiproton, 1000000), "wasteSpallationAmericium",
				new ParticleStack(Particles.pion_plus),new ParticleStack(Particles.pion_naught),new ParticleStack(Particles.pion_minus), 10000000, 1, 1460000);
		
		addRecipe("ingotAmericium242All", new ParticleStack(Particles.antiproton, 1000000), "wasteSpallationAmericium",
				new ParticleStack(Particles.pion_plus),new ParticleStack(Particles.pion_naught),new ParticleStack(Particles.pion_minus), 10000000, 1, 1460000);
		
		addRecipe("ingotAmericium241All", new ParticleStack(Particles.antiproton, 1000000), "wasteSpallationAmericium",
				new ParticleStack(Particles.pion_plus),new ParticleStack(Particles.pion_naught),new ParticleStack(Particles.pion_minus), 10000000, 1, 1460000);
		
		addRecipe("ingotPlutonium242All", new ParticleStack(Particles.antiproton, 1000000), "wasteSpallationPlutonium",
				new ParticleStack(Particles.pion_plus),new ParticleStack(Particles.pion_naught),new ParticleStack(Particles.pion_minus), 10000000, 1, 1460000);
		
		addRecipe("ingotPlutonium241All", new ParticleStack(Particles.antiproton, 1000000), "wasteSpallationPlutonium",
				new ParticleStack(Particles.pion_plus),new ParticleStack(Particles.pion_naught),new ParticleStack(Particles.pion_minus), 10000000, 1, 1460000);
		
		addRecipe("ingotPlutonium239All", new ParticleStack(Particles.antiproton, 1000000), "wasteSpallationPlutonium",
				new ParticleStack(Particles.pion_plus),new ParticleStack(Particles.pion_naught),new ParticleStack(Particles.pion_minus), 10000000, 1, 1460000);
		
		addRecipe("ingotPlutonium238All", new ParticleStack(Particles.antiproton, 1000000), "wasteSpallationPlutonium",
				new ParticleStack(Particles.pion_plus),new ParticleStack(Particles.pion_naught),new ParticleStack(Particles.pion_minus), 10000000, 1, 1460000);
		
		addRecipe("ingotNeptunium237All", new ParticleStack(Particles.antiproton, 1000000), "wasteSpallationNeptunium",
				new ParticleStack(Particles.pion_plus),new ParticleStack(Particles.pion_naught),new ParticleStack(Particles.pion_minus), 10000000, 1, 1460000);
		
		addRecipe("ingotNeptunium236All", new ParticleStack(Particles.antiproton, 1000000), "wasteSpallationNeptunium",
				new ParticleStack(Particles.pion_plus),new ParticleStack(Particles.pion_naught),new ParticleStack(Particles.pion_minus), 10000000, 1, 1460000);
		
		addRecipe("ingotUranium238All", new ParticleStack(Particles.antiproton, 1000000), "wasteSpallationUranium",
				new ParticleStack(Particles.pion_plus),new ParticleStack(Particles.pion_naught),new ParticleStack(Particles.pion_minus), 10000000, 1, 1460000);

		addRecipe("ingotUranium235All", new ParticleStack(Particles.antiproton, 1000000), "wasteSpallationUranium",
				new ParticleStack(Particles.pion_plus),new ParticleStack(Particles.pion_naught),new ParticleStack(Particles.pion_minus), 10000000, 1, 1460000);
		
		addRecipe("ingotUranium234", new ParticleStack(Particles.antiproton, 1000000), "wasteSpallationUranium",
				new ParticleStack(Particles.pion_plus),new ParticleStack(Particles.pion_naught),new ParticleStack(Particles.pion_minus), 10000000, 1, 1460000);

		addRecipe("ingotUranium233All", new ParticleStack(Particles.antiproton, 1000000), "wasteSpallationUranium",
				new ParticleStack(Particles.pion_plus),new ParticleStack(Particles.pion_naught),new ParticleStack(Particles.pion_minus), 10000000, 1, 1460000);
		
		addRecipe("dustProtactinium233", new ParticleStack(Particles.antiproton, 1000000), "wasteSpallationProtactinium",
				new ParticleStack(Particles.pion_plus),new ParticleStack(Particles.pion_naught),new ParticleStack(Particles.pion_minus), 10000000, 1, 1460000);
		
		addRecipe("dustProtactinium231", new ParticleStack(Particles.antiproton, 1000000), "wasteSpallationProtactinium",
				new ParticleStack(Particles.pion_plus),new ParticleStack(Particles.pion_naught),new ParticleStack(Particles.pion_minus), 10000000, 1, 1460000);
		
		addRecipe("ingotThorium", new ParticleStack(Particles.antiproton, 1000000), "wasteSpallationThorium",
				new ParticleStack(Particles.pion_plus),new ParticleStack(Particles.pion_naught),new ParticleStack(Particles.pion_minus), 10000000, 1, 1460000);
		
		addRecipe("dustRadium", new ParticleStack(Particles.antiproton, 1000000), "wasteSpallationRadium",
				new ParticleStack(Particles.pion_plus),new ParticleStack(Particles.pion_naught),new ParticleStack(Particles.pion_minus), 10000000, 1, 1460000);
		
		addRecipe("dustPolonium", new ParticleStack(Particles.antiproton, 1000000), "wasteSpallationPolonium",
				new ParticleStack(Particles.pion_plus),new ParticleStack(Particles.pion_naught),new ParticleStack(Particles.pion_minus), 10000000, 1, 1460000);
		
		addRecipe("dustBismuth", new ParticleStack(Particles.antiproton, 1000000), "wasteSpallationBismuth",
				new ParticleStack(Particles.pion_plus),new ParticleStack(Particles.pion_naught),new ParticleStack(Particles.pion_minus), 10000000, 1, 1460000);
		
		addRecipe("ingotLead", new ParticleStack(Particles.antiproton, 1000000), "wasteSpallationLead",
				new ParticleStack(Particles.pion_plus),new ParticleStack(Particles.pion_naught),new ParticleStack(Particles.pion_minus), 10000000, 1, 1460000);
		
		addRecipe("ingotMercury", new ParticleStack(Particles.antiproton, 1000000), "wasteSpallationMercury",
				new ParticleStack(Particles.pion_plus),new ParticleStack(Particles.pion_naught),new ParticleStack(Particles.pion_minus), 10000000, 1, 1460000);
		
		addRecipe("ingotGold", new ParticleStack(Particles.antiproton, 1000000), "wasteSpallationGold",
				new ParticleStack(Particles.pion_plus),new ParticleStack(Particles.pion_naught),new ParticleStack(Particles.pion_minus), 10000000, 1, 1460000);
		
		addRecipe("ingotPlatinum", new ParticleStack(Particles.antiproton, 1000000), "wasteSpallationPlatinum",
				new ParticleStack(Particles.pion_plus),new ParticleStack(Particles.pion_naught),new ParticleStack(Particles.pion_minus), 10000000, 1, 1460000);
		
		addRecipe("ingotIridium", new ParticleStack(Particles.antiproton, 1000000), "wasteSpallationIridium",
				new ParticleStack(Particles.pion_plus),new ParticleStack(Particles.pion_naught),new ParticleStack(Particles.pion_minus), 10000000, 1, 1460000);
		
		addRecipe("ingotIridium192", new ParticleStack(Particles.antiproton, 1000000), "wasteSpallationIridium",
				new ParticleStack(Particles.pion_plus),new ParticleStack(Particles.pion_naught),new ParticleStack(Particles.pion_minus), 10000000, 1, 1460000);
		
		addRecipe("ingotOsmium", new ParticleStack(Particles.antiproton, 1000000), "wasteSpallationOsmium",
				new ParticleStack(Particles.pion_plus),new ParticleStack(Particles.pion_naught),new ParticleStack(Particles.pion_minus), 10000000, 1, 1460000);
		
		addRecipe("ingotTungsten", new ParticleStack(Particles.antiproton, 1000000), "wasteSpallationTungsten",
				new ParticleStack(Particles.pion_plus),new ParticleStack(Particles.pion_naught),new ParticleStack(Particles.pion_minus), 10000000, 1, 1460000);
		
		addRecipe("ingotHafnium", new ParticleStack(Particles.antiproton, 1000000), "wasteSpallationHafnium",
				new ParticleStack(Particles.pion_plus),new ParticleStack(Particles.pion_naught),new ParticleStack(Particles.pion_minus), 10000000, 1, 1460000);
		
	
		
		
		
	// antideuteron production 
		addRecipe("ingotCalifornium252", new ParticleStack(Particles.deuteron, 10000000, 13100000), "wasteSpallationCalifornium", 
				new ParticleStack(Particles.deuteron), emptyParticleStack(), new ParticleStack(Particles.antideuteron), 20000000, 0.1, -13100000);
		
		addRecipe("ingotCalifornium251", new ParticleStack(Particles.deuteron, 10000000, 13100000), "wasteSpallationCalifornium",
				new ParticleStack(Particles.deuteron), emptyParticleStack(), new ParticleStack(Particles.antideuteron), 20000000, 0.1, -13100000);
		
		addRecipe("ingotCalifornium250", new ParticleStack(Particles.deuteron, 10000000, 13100000), "wasteSpallationCalifornium",
				new ParticleStack(Particles.deuteron), emptyParticleStack(), new ParticleStack(Particles.antideuteron), 20000000, 0.1, -13100000);
		
		addRecipe("ingotCalifornium249", new ParticleStack(Particles.deuteron, 10000000, 13100000), "wasteSpallationCalifornium",
				new ParticleStack(Particles.deuteron), emptyParticleStack(), new ParticleStack(Particles.antideuteron), 20000000, 0.1, -13100000);
		
		addRecipe("ingotBerkelium248", new ParticleStack(Particles.deuteron, 10000000, 13100000), "wasteSpallationBerkelium",
				new ParticleStack(Particles.deuteron), emptyParticleStack(), new ParticleStack(Particles.antideuteron), 20000000, 0.1, -13100000);
		
		addRecipe("ingotBerkelium247", new ParticleStack(Particles.deuteron, 10000000, 13100000), "wasteSpallationBerkelium",
				new ParticleStack(Particles.deuteron), emptyParticleStack(), new ParticleStack(Particles.antideuteron), 20000000, 0.1, -13100000);
		
		addRecipe("ingotCurium247", new ParticleStack(Particles.deuteron, 10000000, 13100000), "wasteSpallationCurium",
				new ParticleStack(Particles.deuteron), emptyParticleStack(), new ParticleStack(Particles.antideuteron), 20000000, 0.1, -13100000);
		
		addRecipe("ingotCurium246", new ParticleStack(Particles.deuteron, 10000000, 13100000), "wasteSpallationCurium",
				new ParticleStack(Particles.deuteron), emptyParticleStack(), new ParticleStack(Particles.antideuteron), 20000000, 0.1, -13100000);
		
		addRecipe("ingotCurium245", new ParticleStack(Particles.deuteron, 10000000, 13100000), "wasteSpallationCurium",
				new ParticleStack(Particles.deuteron), emptyParticleStack(), new ParticleStack(Particles.antideuteron), 20000000, 0.1, -13100000);
		
		addRecipe("ingotCurium243", new ParticleStack(Particles.deuteron, 10000000, 13100000), "wasteSpallationCurium",
				new ParticleStack(Particles.deuteron), emptyParticleStack(), new ParticleStack(Particles.antideuteron), 20000000, 0.1, -13100000);
		
		addRecipe("ingotAmericium243", new ParticleStack(Particles.deuteron, 10000000, 13100000), "wasteSpallationAmericium",
				new ParticleStack(Particles.deuteron), emptyParticleStack(), new ParticleStack(Particles.antideuteron), 20000000, 0.1, -13100000);
		
		addRecipe("ingotAmericium242", new ParticleStack(Particles.deuteron, 10000000, 13100000), "wasteSpallationAmericium",
				new ParticleStack(Particles.deuteron), emptyParticleStack(), new ParticleStack(Particles.antideuteron), 20000000, 0.1, -13100000);
		
		addRecipe("ingotAmericium241", new ParticleStack(Particles.deuteron, 10000000, 13100000), "wasteSpallationAmericium",
				new ParticleStack(Particles.deuteron), emptyParticleStack(), new ParticleStack(Particles.antideuteron), 20000000, 0.1, -13100000);
		
		addRecipe("ingotPlutonium242", new ParticleStack(Particles.deuteron, 10000000, 13100000), "wasteSpallationPlutonium",
				new ParticleStack(Particles.deuteron), emptyParticleStack(), new ParticleStack(Particles.antideuteron), 20000000, 0.1, -13100000);
		
		addRecipe("ingotPlutonium241", new ParticleStack(Particles.deuteron, 10000000, 13100000), "wasteSpallationPlutonium",
				new ParticleStack(Particles.deuteron), emptyParticleStack(), new ParticleStack(Particles.antideuteron), 20000000, 0.1, -13100000);
		
		addRecipe("ingotPlutonium239", new ParticleStack(Particles.deuteron, 10000000, 13100000), "wasteSpallationPlutonium",
				new ParticleStack(Particles.deuteron), emptyParticleStack(), new ParticleStack(Particles.antideuteron), 20000000, 0.1, -13100000);
		
		addRecipe("ingotPlutonium238", new ParticleStack(Particles.deuteron, 10000000, 13100000), "wasteSpallationPlutonium",
				new ParticleStack(Particles.deuteron), emptyParticleStack(), new ParticleStack(Particles.antideuteron), 20000000, 0.1, -13100000);
		
		addRecipe("ingotNeptunium237", new ParticleStack(Particles.deuteron, 10000000, 13100000), "wasteSpallationNeptunium",
				new ParticleStack(Particles.deuteron), emptyParticleStack(), new ParticleStack(Particles.antideuteron), 20000000, 0.1, -13100000);
		
		addRecipe("ingotNeptunium236", new ParticleStack(Particles.deuteron, 10000000, 13100000), "wasteSpallationNeptunium",
				new ParticleStack(Particles.deuteron), emptyParticleStack(), new ParticleStack(Particles.antideuteron), 20000000, 0.1, -13100000);
		
		addRecipe("ingotUranium238", new ParticleStack(Particles.deuteron, 10000000, 13100000), "wasteSpallationUranium",
				new ParticleStack(Particles.deuteron), emptyParticleStack(), new ParticleStack(Particles.antideuteron), 20000000, 0.1, -13100000);
		
		addRecipe("ingotUranium235", new ParticleStack(Particles.deuteron, 10000000, 13100000), "wasteSpallationUranium",
				new ParticleStack(Particles.deuteron), emptyParticleStack(), new ParticleStack(Particles.antideuteron), 20000000, 0.1, -13100000);
		
		addRecipe("ingotUranium234", new ParticleStack(Particles.deuteron, 10000000, 13100000), "wasteSpallationUranium",
				new ParticleStack(Particles.deuteron), emptyParticleStack(), new ParticleStack(Particles.antideuteron), 20000000, 0.1, -13100000);
		
		addRecipe("ingotUranium233", new ParticleStack(Particles.deuteron, 10000000, 13100000), "wasteSpallationUranium",
				new ParticleStack(Particles.deuteron), emptyParticleStack(), new ParticleStack(Particles.antideuteron), 20000000, 0.1, -13100000);
		
		addRecipe("dustProtactinium233", new ParticleStack(Particles.deuteron, 10000000, 13100000), "wasteSpallationProtactinium",
				new ParticleStack(Particles.deuteron), emptyParticleStack(), new ParticleStack(Particles.antideuteron), 20000000, 0.1, -13100000);
		
		addRecipe("dustProtactinium231", new ParticleStack(Particles.deuteron, 10000000, 13100000), "wasteSpallationProtactinium",
				new ParticleStack(Particles.deuteron), emptyParticleStack(), new ParticleStack(Particles.antideuteron), 20000000, 0.1, -13100000);
		
		addRecipe("ingotThorium", new ParticleStack(Particles.deuteron, 10000000, 13100000), "wasteSpallationThorium",
				new ParticleStack(Particles.deuteron), emptyParticleStack(), new ParticleStack(Particles.antideuteron), 20000000, 0.1, -13100000);
		
		addRecipe("dustRadium", new ParticleStack(Particles.deuteron, 10000000, 13100000), "wasteSpallationRadium",
				new ParticleStack(Particles.deuteron), emptyParticleStack(), new ParticleStack(Particles.antideuteron), 20000000, 0.1, -13100000);
		
		addRecipe("dustPolonium", new ParticleStack(Particles.deuteron, 10000000, 13100000), "wasteSpallationPolonium",
				new ParticleStack(Particles.deuteron), emptyParticleStack(), new ParticleStack(Particles.antideuteron), 20000000, 0.1, -13100000);
		
		addRecipe("dustBismuth", new ParticleStack(Particles.deuteron, 10000000, 13100000), "wasteSpallationBismuth",
				new ParticleStack(Particles.deuteron), emptyParticleStack(), new ParticleStack(Particles.antideuteron), 20000000, 0.1, -13100000);
		
		addRecipe("ingotLead", new ParticleStack(Particles.deuteron, 10000000, 13100000), "wasteSpallationLead",
				new ParticleStack(Particles.deuteron), emptyParticleStack(), new ParticleStack(Particles.antideuteron), 20000000, 0.1, -13100000);
		
		addRecipe("ingotMercury", new ParticleStack(Particles.deuteron, 10000000, 13100000), "wasteSpallationMercury",
				new ParticleStack(Particles.deuteron), emptyParticleStack(), new ParticleStack(Particles.antideuteron), 20000000, 0.1, -13100000);
		
		addRecipe("ingotGold", new ParticleStack(Particles.deuteron, 10000000, 13100000), "wasteSpallationGold",
				new ParticleStack(Particles.deuteron), emptyParticleStack(), new ParticleStack(Particles.antideuteron), 20000000, 0.1, -13100000);
		
		addRecipe("ingotPlatinum", new ParticleStack(Particles.deuteron, 10000000, 13100000), "wasteSpallationPlatinum",
				new ParticleStack(Particles.deuteron), emptyParticleStack(), new ParticleStack(Particles.antideuteron), 20000000, 0.1, -13100000);
		
		addRecipe("ingotIridium", new ParticleStack(Particles.deuteron, 10000000, 13100000), "wasteSpallationIridium",
				new ParticleStack(Particles.deuteron), emptyParticleStack(), new ParticleStack(Particles.antideuteron), 20000000, 0.1, -13100000);
		
		addRecipe("ingotIridium192", new ParticleStack(Particles.deuteron, 10000000, 13100000), "wasteSpallationIridium",
				new ParticleStack(Particles.deuteron), emptyParticleStack(), new ParticleStack(Particles.antideuteron), 20000000, 0.1, -13100000);
	
		addRecipe("ingotOsmium", new ParticleStack(Particles.deuteron, 10000000, 13100000), "wasteSpallationOsmium",
				new ParticleStack(Particles.deuteron), emptyParticleStack(), new ParticleStack(Particles.antideuteron), 20000000, 0.1, -13100000);
		
		addRecipe("ingotTungsten", new ParticleStack(Particles.deuteron, 10000000, 13100000), "wasteSpallationTungsten",
				new ParticleStack(Particles.deuteron), emptyParticleStack(), new ParticleStack(Particles.antideuteron), 20000000, 0.1, -13100000);
		
		addRecipe("ingotHafnium", new ParticleStack(Particles.deuteron, 10000000, 13100000), "wasteSpallationHafnium",
				new ParticleStack(Particles.deuteron), emptyParticleStack(), new ParticleStack(Particles.antideuteron), 20000000, 0.1, -13100000);
		
	
		// antideuteron anhilation
		
				addRecipe("ingotCalifornium252All", new ParticleStack(Particles.antideuteron, 1000000), "wasteSpallationCalifornium",
						new ParticleStack(Particles.pion_plus,4),new ParticleStack(Particles.pion_naught,4),new ParticleStack(Particles.pion_minus,4), 10000000, 1, 2090000);
				
				addRecipe("ingotCalifornium251All", new ParticleStack(Particles.antideuteron, 1000000), "wasteSpallationCalifornium",
						new ParticleStack(Particles.pion_plus,4),new ParticleStack(Particles.pion_naught,4),new ParticleStack(Particles.pion_minus,4), 10000000, 1, 2090000);
				
				addRecipe("ingotCalifornium250All", new ParticleStack(Particles.antideuteron, 1000000), "wasteSpallationCalifornium",
						new ParticleStack(Particles.pion_plus,4),new ParticleStack(Particles.pion_naught,4),new ParticleStack(Particles.pion_minus,4), 10000000, 1, 2090000);
				
				addRecipe("ingotCalifornium249All", new ParticleStack(Particles.antideuteron, 1000000), "wasteSpallationCalifornium",
						new ParticleStack(Particles.pion_plus,4),new ParticleStack(Particles.pion_naught,4),new ParticleStack(Particles.pion_minus,4), 10000000, 1, 2090000);
				
				addRecipe("ingotBerkelium248All", new ParticleStack(Particles.antideuteron, 1000000), "wasteSpallationBerkelium",
						new ParticleStack(Particles.pion_plus,4),new ParticleStack(Particles.pion_naught,4),new ParticleStack(Particles.pion_minus,4), 10000000, 1, 2090000);
				
				addRecipe("ingotBerkelium247All", new ParticleStack(Particles.antideuteron, 1000000), "wasteSpallationBerkelium",
						new ParticleStack(Particles.pion_plus,4),new ParticleStack(Particles.pion_naught,4),new ParticleStack(Particles.pion_minus,4), 10000000, 1, 2090000);
				
				addRecipe("ingotCurium247All", new ParticleStack(Particles.antideuteron, 1000000), "wasteSpallationCurium",
						new ParticleStack(Particles.pion_plus,4),new ParticleStack(Particles.pion_naught,4),new ParticleStack(Particles.pion_minus,4), 10000000, 1, 2090000);
				
				addRecipe("ingotCurium246All", new ParticleStack(Particles.antideuteron, 1000000), "wasteSpallationCurium",
						new ParticleStack(Particles.pion_plus,4),new ParticleStack(Particles.pion_naught,4),new ParticleStack(Particles.pion_minus,4), 10000000, 1, 2090000);
				
				addRecipe("ingotCurium245All", new ParticleStack(Particles.antideuteron, 1000000), "wasteSpallationCurium",
						new ParticleStack(Particles.pion_plus,4),new ParticleStack(Particles.pion_naught,4),new ParticleStack(Particles.pion_minus,4), 10000000, 1, 2090000);
				
				addRecipe("ingotCurium243All", new ParticleStack(Particles.antideuteron, 1000000), "wasteSpallationCurium",
						new ParticleStack(Particles.pion_plus,4),new ParticleStack(Particles.pion_naught,4),new ParticleStack(Particles.pion_minus,4), 10000000, 1, 2090000);
				
				addRecipe("ingotAmericium243All", new ParticleStack(Particles.antideuteron, 1000000), "wasteSpallationAmericium",
						new ParticleStack(Particles.pion_plus,4),new ParticleStack(Particles.pion_naught,4),new ParticleStack(Particles.pion_minus,4), 10000000, 1, 2090000);
				
				addRecipe("ingotAmericium242All", new ParticleStack(Particles.antideuteron, 1000000), "wasteSpallationAmericium",
						new ParticleStack(Particles.pion_plus,4),new ParticleStack(Particles.pion_naught,4),new ParticleStack(Particles.pion_minus,4), 10000000, 1, 2090000);
				
				addRecipe("ingotAmericium241All", new ParticleStack(Particles.antideuteron, 1000000), "wasteSpallationAmericium",
						new ParticleStack(Particles.pion_plus,4),new ParticleStack(Particles.pion_naught,4),new ParticleStack(Particles.pion_minus,4), 10000000, 1, 2090000);
				
				addRecipe("ingotPlutonium242All", new ParticleStack(Particles.antideuteron, 1000000), "wasteSpallationPlutonium",
						new ParticleStack(Particles.pion_plus,4),new ParticleStack(Particles.pion_naught,4),new ParticleStack(Particles.pion_minus,4), 10000000, 1, 2090000);
				
				addRecipe("ingotPlutonium241All", new ParticleStack(Particles.antideuteron, 1000000), "wasteSpallationPlutonium",
						new ParticleStack(Particles.pion_plus,4),new ParticleStack(Particles.pion_naught,4),new ParticleStack(Particles.pion_minus,4), 10000000, 1, 2090000);
				
				addRecipe("ingotPlutonium239All", new ParticleStack(Particles.antideuteron, 1000000), "wasteSpallationPlutonium",
						new ParticleStack(Particles.pion_plus,4),new ParticleStack(Particles.pion_naught,4),new ParticleStack(Particles.pion_minus,4), 10000000, 1, 2090000);
				
				addRecipe("ingotPlutonium238All", new ParticleStack(Particles.antideuteron, 1000000), "wasteSpallationPlutonium",
						new ParticleStack(Particles.pion_plus,4),new ParticleStack(Particles.pion_naught,4),new ParticleStack(Particles.pion_minus,4), 10000000, 1, 2090000);
				
				addRecipe("ingotNeptunium237All", new ParticleStack(Particles.antideuteron, 1000000), "wasteSpallationNeptunium",
						new ParticleStack(Particles.pion_plus,4),new ParticleStack(Particles.pion_naught,4),new ParticleStack(Particles.pion_minus,4), 10000000, 1, 2090000);
				
				addRecipe("ingotNeptunium236All", new ParticleStack(Particles.antideuteron, 1000000), "wasteSpallationNeptunium",
						new ParticleStack(Particles.pion_plus,4),new ParticleStack(Particles.pion_naught,4),new ParticleStack(Particles.pion_minus,4), 10000000, 1, 2090000);
				
				addRecipe("ingotUranium238All", new ParticleStack(Particles.antideuteron, 1000000), "wasteSpallationUranium",
						new ParticleStack(Particles.pion_plus,4),new ParticleStack(Particles.pion_naught,4),new ParticleStack(Particles.pion_minus,4), 10000000, 1, 2090000);

				addRecipe("ingotUranium235All", new ParticleStack(Particles.antideuteron, 1000000), "wasteSpallationUranium",
						new ParticleStack(Particles.pion_plus,4),new ParticleStack(Particles.pion_naught,4),new ParticleStack(Particles.pion_minus,4), 10000000, 1, 2090000);
				
				addRecipe("ingotUranium234", new ParticleStack(Particles.antideuteron, 1000000), "wasteSpallationUranium",
						new ParticleStack(Particles.pion_plus,4),new ParticleStack(Particles.pion_naught,4),new ParticleStack(Particles.pion_minus,4), 10000000, 1, 2090000);

				addRecipe("ingotUranium233All", new ParticleStack(Particles.antideuteron, 1000000), "wasteSpallationUranium",
						new ParticleStack(Particles.pion_plus,4),new ParticleStack(Particles.pion_naught,4),new ParticleStack(Particles.pion_minus,4), 10000000, 1, 2090000);
				
				addRecipe("dustProtactinium233", new ParticleStack(Particles.antideuteron, 1000000), "wasteSpallationProtactinium",
						new ParticleStack(Particles.pion_plus,4),new ParticleStack(Particles.pion_naught,4),new ParticleStack(Particles.pion_minus,4), 10000000, 1, 2090000);
				
				addRecipe("dustProtactinium231", new ParticleStack(Particles.antideuteron, 1000000), "wasteSpallationProtactinium",
						new ParticleStack(Particles.pion_plus,4),new ParticleStack(Particles.pion_naught,4),new ParticleStack(Particles.pion_minus,4), 10000000, 1, 2090000);
				
				addRecipe("ingotThorium", new ParticleStack(Particles.antideuteron, 1000000), "wasteSpallationThorium",
						new ParticleStack(Particles.pion_plus,4),new ParticleStack(Particles.pion_naught,4),new ParticleStack(Particles.pion_minus,4), 10000000, 1, 2090000);
				
				addRecipe("dustRadium", new ParticleStack(Particles.antideuteron, 1000000), "wasteSpallationRadium",
						new ParticleStack(Particles.pion_plus,4),new ParticleStack(Particles.pion_naught,4),new ParticleStack(Particles.pion_minus,4), 10000000, 1, 2090000);
				
				addRecipe("dustPolonium", new ParticleStack(Particles.antideuteron, 1000000), "wasteSpallationPolonium",
						new ParticleStack(Particles.pion_plus,4),new ParticleStack(Particles.pion_naught,4),new ParticleStack(Particles.pion_minus,4), 10000000, 1, 2090000);
				
				addRecipe("dustBismuth", new ParticleStack(Particles.antideuteron, 1000000), "wasteSpallationBismuth",
						new ParticleStack(Particles.pion_plus,4),new ParticleStack(Particles.pion_naught,4),new ParticleStack(Particles.pion_minus,4), 10000000, 1, 2090000);
				
				addRecipe("ingotLead", new ParticleStack(Particles.antideuteron, 1000000), "wasteSpallationLead",
						new ParticleStack(Particles.pion_plus,4),new ParticleStack(Particles.pion_naught,4),new ParticleStack(Particles.pion_minus,4), 10000000, 1, 2090000);
				
				addRecipe("ingotMercury", new ParticleStack(Particles.antideuteron, 1000000), "wasteSpallationMercury",
						new ParticleStack(Particles.pion_plus,4),new ParticleStack(Particles.pion_naught,4),new ParticleStack(Particles.pion_minus,4), 10000000, 1, 2090000);
				
				addRecipe("ingotGold", new ParticleStack(Particles.antideuteron, 1000000), "wasteSpallationGold",
						new ParticleStack(Particles.pion_plus,4),new ParticleStack(Particles.pion_naught,4),new ParticleStack(Particles.pion_minus,4), 10000000, 1, 2090000);
				
				addRecipe("ingotPlatinum", new ParticleStack(Particles.antideuteron, 1000000), "wasteSpallationPlatinum",
						new ParticleStack(Particles.pion_plus,4),new ParticleStack(Particles.pion_naught,4),new ParticleStack(Particles.pion_minus,4), 10000000, 1, 2090000);
				
				addRecipe("ingotIridium", new ParticleStack(Particles.antideuteron, 1000000), "wasteSpallationIridium",
						new ParticleStack(Particles.pion_plus,4),new ParticleStack(Particles.pion_naught,4),new ParticleStack(Particles.pion_minus,4), 10000000, 1, 2090000);
				
				addRecipe("ingotIridium192", new ParticleStack(Particles.antideuteron, 1000000), "wasteSpallationIridium",
						new ParticleStack(Particles.pion_plus,4),new ParticleStack(Particles.pion_naught,4),new ParticleStack(Particles.pion_minus,4), 10000000, 1, 2090000);
				
				addRecipe("ingotOsmium", new ParticleStack(Particles.antideuteron, 1000000), "wasteSpallationOsmium",
						new ParticleStack(Particles.pion_plus,4),new ParticleStack(Particles.pion_naught,4),new ParticleStack(Particles.pion_minus,4), 10000000, 1, 2090000);
				
				addRecipe("ingotTungsten", new ParticleStack(Particles.antideuteron, 1000000), "wasteSpallationTungsten",
						new ParticleStack(Particles.pion_plus,4),new ParticleStack(Particles.pion_naught,4),new ParticleStack(Particles.pion_minus,4), 10000000, 1, 2090000);
				
				addRecipe("ingotHafnium", new ParticleStack(Particles.antideuteron, 1000000), "wasteSpallationHafnium",
						new ParticleStack(Particles.pion_plus,4),new ParticleStack(Particles.pion_naught,4),new ParticleStack(Particles.pion_minus,4), 10000000, 1, 2090000);
					
		
		
		
		
	}

	@Override
	public List fixExtras(List extras)
	{
		List fixed = new ArrayList(4);
		
		fixed.add(extras.size() > 0 && extras.get(0) instanceof Integer ? ((Number) (extras.get(0))).longValue() : 0l);
		fixed.add(extras.size() > 1 && extras.get(1) instanceof Double ? (double) extras.get(1) : 1D);
		fixed.add(extras.size() > 2 && extras.get(2) instanceof Integer ? ((Number) (extras.get(2))).longValue() : 0l);
		fixed.add(extras.size() > 3 && extras.get(3) instanceof Double ? (double) extras.get(3) : 0D);
		return fixed;
	}
	

}
