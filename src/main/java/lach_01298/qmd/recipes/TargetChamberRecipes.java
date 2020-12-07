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
		//addRecipe(itemInput, particleInput [particle, amount, minEnergy minFocus], item output,
		//particleOutput+ [particle, amount], particleOutput0 [particle, amount], particleOutput- [particle, amount], maxEnergy, crossSection, energyRelased, radiation)
		
		
		//proton reactions
		
		addRecipe("ingotBeryllium",new ParticleStack(Particles.proton, 166000, 1000), "ingotLithium6",
				new ParticleStack(Particles.alpha), emptyParticleStack(), emptyParticleStack(), 7500, 0.34, 2640);
		
		addRecipe("ingotBoron10", new ParticleStack(Particles.proton, 156000, 4000),"ingotBeryllium7",
				new ParticleStack(Particles.alpha), emptyParticleStack(), emptyParticleStack(),6000, 0.42, 1700);
		
		addRecipe("ingotBoron11", new ParticleStack(Particles.proton, 1584000, 15500), "dustGraphite",
				emptyParticleStack(), new ParticleStack(Particles.photon), emptyParticleStack(), 26000, 0.01, 15000);
		
		addRecipe("ingotBoron11", new ParticleStack(Particles.proton, 360000, 30000), "ingotBeryllium7",
				new ParticleStack(Particles.triton), new ParticleStack(Particles.neutron,2), emptyParticleStack(), 33000, 0.026, -30000);
		
		addRecipe("dustGraphite", new ParticleStack(Particles.proton, 348000, 40000), "ingotBoron10",
				new ParticleStack(Particles.proton,2), emptyParticleStack(), emptyParticleStack(), 50000, 0.029, -25000);
		
		addRecipe("dustGraphite", new ParticleStack(Particles.proton, 336000, 60000), "ingotBoron11",
				new ParticleStack(Particles.proton), emptyParticleStack(), new ParticleStack(Particles.deuteron), 85000, 0.033, -25000);
		
		addRecipe("dustGraphite", new ParticleStack(Particles.proton, 336000, 27000), "ingotBeryllium7",
				new ParticleStack(Particles.alpha), new ParticleStack(Particles.neutron), new ParticleStack(Particles.proton), 35000, 0.033, -15000);
		
		addRecipe("dustGraphite", new ParticleStack(Particles.proton, 591000, 150000), "ingotBeryllium",
				new ParticleStack(Particles.proton), emptyParticleStack(), new ParticleStack(Particles.triton), 1000000, 0.01, -26000);
		
		addRecipe("ingotSodium", new ParticleStack(Particles.proton, 182100, 20000), "ingotSodium22",
				new ParticleStack(Particles.proton), new ParticleStack(Particles.neutron), emptyParticleStack(), 28000,  0.25, -12400);

		addRecipe("ingotMagnesium24", new ParticleStack(Particles.proton, 212400 , 38000), "ingotSodium22",
				new ParticleStack(Particles.proton, 2), new ParticleStack(Particles.neutron), emptyParticleStack(), 56000, 0.15, -23000);
		
		addRecipe("ingotMagnesium26", new ParticleStack(Particles.proton, 306000 , 23000), "ingotSodium22",
				new ParticleStack(Particles.alpha), new ParticleStack(Particles.neutron), emptyParticleStack(), 29000, 0.045, -14000);
		
		addRecipe("ingotAluminum",  new ParticleStack(Particles.proton, 315000, 40000), "ingotSodium22",
				new ParticleStack(Particles.alpha), new ParticleStack(Particles.neutron), new ParticleStack(Particles.proton), 50000, 0.04, -21500);
		
		addRecipe("ingotAluminum",  new ParticleStack(Particles.proton, 1149000, 1500), "itemSilicon",
				emptyParticleStack(), new ParticleStack(Particles.photon), emptyParticleStack(), 2000, 0.01, 11100);
		
		addRecipe("itemSilicon",  new ParticleStack(Particles.proton, 152700, 19000), "ingotAluminum",
				new ParticleStack(Particles.proton,2), emptyParticleStack(), emptyParticleStack(), 28000, 0.45, -11100);
		
		addRecipe("itemSilicon",  new ParticleStack(Particles.proton, 240000, 32000), "ingotMagnesium26",
				new ParticleStack(Particles.proton,3), emptyParticleStack(), emptyParticleStack(), 50000, 0.1, -18800);
		
		addRecipe("ingotCalcium",  new ParticleStack(Particles.proton, 133500, 16500), "ingotPotassium",
				new ParticleStack(Particles.proton,2), emptyParticleStack(), emptyParticleStack(), 25000, 0.7, -7810);
		
		addRecipe("ingotManganese", new ParticleStack(Particles.proton, 867000, 10000), "ingotIron",
				emptyParticleStack(), new ParticleStack(Particles.photon), emptyParticleStack(), 19500, 0.01, 9670);
		
		addRecipe("ingotCobalt", new ParticleStack(Particles.proton, 252000, 4600), "ingotNickel",
				emptyParticleStack(), new ParticleStack(Particles.photon), emptyParticleStack(), 5600, 0.085, 9020);
		
		addRecipe("ingotCopper", new ParticleStack(Particles.proton, 390000, 4500), "ingotZinc",
				emptyParticleStack(), new ParticleStack(Particles.photon), emptyParticleStack(), 5900, 0.02, 7200);
		
		addRecipe("ingotCopper", new ParticleStack(Particles.proton, 176100, 45000), "ingotNickel",
				new ParticleStack(Particles.alpha), emptyParticleStack(), emptyParticleStack(), 56000, 0.28, 4270);
		
		addRecipe("ingotOsmium", new ParticleStack(Particles.proton, 273300, 8300), "ingotIridium192",
				emptyParticleStack(), new ParticleStack(Particles.neutron), emptyParticleStack(), 10300, 0.065, -2340);
		
		addRecipe("ingotGold", new ParticleStack(Particles.proton, 780000, 100000), "ingotIridium192",
				new ParticleStack(Particles.alpha), new ParticleStack(Particles.neutron), new ParticleStack(Particles.proton), 200000, 0.01, -5780);
		
		addRecipe("ingotGold", new ParticleStack(Particles.proton, 345000, 21000), "ingotPlatinum",
				new ParticleStack(Particles.alpha), emptyParticleStack(), emptyParticleStack(), 25000, 0.03, 9000);
		
		addRecipe("dustBismuth", new ParticleStack(Particles.proton, 960000, 14000), "dustPolonium",
				emptyParticleStack(), new ParticleStack(Particles.photon), emptyParticleStack(), 19000, 0.01, 4470);
		
		addRecipe("dustBismuth", new ParticleStack(Particles.proton, 396000, 20000), "dustLead",
				new ParticleStack(Particles.alpha), emptyParticleStack(), emptyParticleStack(), 24000, 0.019, 10900);
		
		addRecipe("ingotThorium", new ParticleStack(Particles.proton, 172500, 11500), "dustProtactinium231",
				emptyParticleStack(), new ParticleStack(Particles.neutron,2), emptyParticleStack(), 16000, 0.3, -7340);
		
		addRecipe("ingotUranium235", new ParticleStack(Particles.proton, 819000, 20500), "ingotNeptunium236",
				emptyParticleStack(), new ParticleStack(Particles.photon), emptyParticleStack(), 30000, 0.01, 4320);
		
		addRecipe("ingotUranium238", new ParticleStack(Particles.proton, 216900, 12000), "ingotNeptunium237",
				emptyParticleStack(), new ParticleStack(Particles.neutron, 2), emptyParticleStack(), 16500, 0.14, -6930);
		
		addRecipe("ingotUranium238", new ParticleStack(Particles.proton, 147900, 19000), "ingotNeptunium236",
				emptyParticleStack(), new ParticleStack(Particles.neutron,3), emptyParticleStack(), 23000, 0.5, -13500);
		
		addRecipe("ingotPlutonium242", new ParticleStack(Particles.proton, 256800, 12500), "ingotAmericium241",
				emptyParticleStack(), new ParticleStack(Particles.neutron,2), emptyParticleStack(), 16500, 0.2, -7580);
		
		
		
		
		// neutron reactions
			
		addRecipe("ingotBerylium9", new ParticleStack(Particles.neutron, 345000, 15000), "ingotLithium7",
				new ParticleStack(Particles.triton), emptyParticleStack(), emptyParticleStack(), 26000, 0.03, -9930);
		
		addRecipe("ingotBoron11", new ParticleStack(Particles.neutron, 396000, 12500), "ingotBeryllium9",
				new ParticleStack(Particles.triton), emptyParticleStack(), emptyParticleStack(), 20000, 0.019, -9050);
		
		addRecipe("dustGraphite", new ParticleStack(Particles.neutron, 240000, 29500), "ingotBoron11",
				new ParticleStack(Particles.deuteron), emptyParticleStack(), emptyParticleStack(), 60000, 0.1, -13200);
		
		addRecipe("dustGraphite", new ParticleStack(Particles.neutron, 434000, 105000), "ingotBerylium7",
				new ParticleStack(Particles.helion), new ParticleStack(Particles.neutron,3), emptyParticleStack(), 150000, 0.014, -45800);
		
		addRecipe("ingotSodium", new ParticleStack(Particles.neutron, 233100, 17000), "ingotSodium22",
				emptyParticleStack(), new ParticleStack(Particles.neutron,2), emptyParticleStack(), 28000, 0.11, -12400);
		
		addRecipe("ingotMagnesium24", new ParticleStack(Particles.neutron, 233100, 35000), "ingotSodium22",
				new ParticleStack(Particles.deuteron), new ParticleStack(Particles.neutron), emptyParticleStack(), 60000, 0.11, -21400);
		
		addRecipe("ingotAluminum", new ParticleStack(Particles.neutron, 162000, 13000), "ingotMagnesium26",
				new ParticleStack(Particles.proton), new ParticleStack(Particles.neutron), emptyParticleStack(), 30000, 0.37, -7760);
		
		addRecipe("ingotAluminum", new ParticleStack(Particles.neutron, 374000, 43000), "ingotSodium22",
				new ParticleStack(Particles.helion), new ParticleStack(Particles.neutron,3), emptyParticleStack(), 80000, 0.023, -42100);
		
		addRecipe("itemSilicon", new ParticleStack(Particles.neutron, 158100, 18000), "ingotAluminum",
				new ParticleStack(Particles.proton), new ParticleStack(Particles.neutron), emptyParticleStack(), 27000, 0.4, -11100);
		
		addRecipe("ingotCalcium", new ParticleStack(Particles.neutron, 135900, 14000), "ingotPotassium",
				new ParticleStack(Particles.proton), new ParticleStack(Particles.neutron), emptyParticleStack(), 24000, 0.66, -7820);
		
		addRecipe("ingotChromium", new ParticleStack(Particles.neutron, 272100, 12000), "ingotTitanium",
				new ParticleStack(Particles.alpha), emptyParticleStack(), emptyParticleStack(), 20000, 0.066, -191);
		
		addRecipe("ingotManganese", new ParticleStack(Particles.neutron, 170700, 29000), "ingotChromium",
				new ParticleStack(Particles.triton), emptyParticleStack(), emptyParticleStack(), 46000, 0.31, -8790);
		
		addRecipe("ingotIron", new ParticleStack(Particles.neutron, 295800, 11000), "ingotChromium",
				new ParticleStack(Particles.alpha), emptyParticleStack(), emptyParticleStack(), 22000, 0.05, 1350);
		
		addRecipe("ingotCobalt", new ParticleStack(Particles.neutron, 204600, 30000), "ingotIron",
				new ParticleStack(Particles.triton), emptyParticleStack(), emptyParticleStack(), 50000, 0.17, -8420);
		
		addRecipe("ingotNickel", new ParticleStack(Particles.neutron, 158100, 19000), "ingotIron",
				new ParticleStack(Particles.alpha), new ParticleStack(Particles.neutron), emptyParticleStack(), 30000, 0.4, -5380);
		
		addRecipe("ingotCopper", new ParticleStack(Particles.neutron, 143700, 11000), "ingotNickel",
				new ParticleStack(Particles.proton), new ParticleStack(Particles.neutron), emptyParticleStack(), 15000, 0.55, -5610);
		
		addRecipe("ingotZinc", new ParticleStack(Particles.neutron, 139800, 14000), "ingotCopper",
				new ParticleStack(Particles.proton), new ParticleStack(Particles.neutron), emptyParticleStack(), 24500, 0.6, -7200);
		
		addRecipe("ingotZinc", new ParticleStack(Particles.neutron, 227100, 5500), "ingotNickel",
				new ParticleStack(Particles.alpha), emptyParticleStack(), emptyParticleStack(), 13000, 0.12, 4880);
		
		addRecipe("ingotYttrium", new ParticleStack(Particles.neutron, 221700, 30000), "ingotStrontium",
				new ParticleStack(Particles.deuteron), emptyParticleStack(), emptyParticleStack(), 49000, 0.13, -4330);
		
		addRecipe("ingotZirconium", new ParticleStack(Particles.neutron, 174300, 21000), "ingotYttrium",
				new ParticleStack(Particles.deuteron), emptyParticleStack(), emptyParticleStack(), 36000, 0.29, -5620);
		
		addRecipe("ingotZirconium", new ParticleStack(Particles.neutron, 402000, 15000), "ingotStrontium",
				new ParticleStack(Particles.alpha), emptyParticleStack(), emptyParticleStack(), 20000, 0.018, 2780);
		
		addRecipe("ingotNiobium", new ParticleStack(Particles.neutron, 280000, 22000), "ingotYttrium",
				new ParticleStack(Particles.alpha), new ParticleStack(Particles.neutron), emptyParticleStack(), 34000, 0.06, -909);
		
		addRecipe("ingotIridium", new ParticleStack(Particles.neutron, 94500, 9000), "ingotIridium192",
				emptyParticleStack(), new ParticleStack(Particles.neutron,2), emptyParticleStack(), 19500, 1, -7770);
		
		addRecipe("ingotPlatinum", new ParticleStack(Particles.neutron, 453000, 19000), "ingotOsmium",
				new ParticleStack(Particles.alpha), emptyParticleStack(), emptyParticleStack(), 27000, 0.012, 9750);
		
		addRecipe("ingotGold", new ParticleStack(Particles.neutron, 384000, 30000), "ingotIridium",
				new ParticleStack(Particles.alpha), new ParticleStack(Particles.neutron), emptyParticleStack(), 45000, 0.021, 1990);
		
		addRecipe("dustBismuth", new ParticleStack(Particles.neutron, 240000, 70000), "dustLead",
				new ParticleStack(Particles.triton), emptyParticleStack(), emptyParticleStack(), 150000, 0.1, -2170);
		
		addRecipe("ingotUranium235", new ParticleStack(Particles.neutron, 176100, 17000), "ingotUranium233",
				emptyParticleStack(), new ParticleStack(Particles.neutron, 3), emptyParticleStack(), 23500,  0.28, -12100);	
		
		addRecipe("ingotUranium238", new ParticleStack(Particles.neutron, 143700, 26000), "ingotUranium235",
				emptyParticleStack(), new ParticleStack(Particles.neutron, 4), emptyParticleStack(), 34000, 0.55, -17800 );
		
		addRecipe("ingotNeptunium237",new ParticleStack(Particles.neutron, 135300, 10000), "ingotNeptunium236",
				emptyParticleStack(), new ParticleStack(Particles.neutron, 2), emptyParticleStack(), 14000, 0.67, -6580);
		
		addRecipe("ingotPlutonium242", new ParticleStack(Particles.neutron, 113700, 8500), "ingotPlutonium241",
					emptyParticleStack(), new ParticleStack(Particles.neutron, 2), emptyParticleStack(), 13000, 1, -6310);

		addRecipe("ingotAmericium243", new ParticleStack(Particles.neutron, 136500, 9000), "ingotAmericium242",
				emptyParticleStack(), new ParticleStack(Particles.neutron, 2), emptyParticleStack(), 14500, 0.65, -5540);

		addRecipe("ingotCopernicium291All", new ParticleStack(Particles.neutron, 1000, 60000), "wasteFissionHeavy",
				emptyParticleStack(), new ParticleStack(Particles.neutron,8), emptyParticleStack(), 1000000, 1, 0);
		
		
		//deuteron reactions
		
		addRecipe("ingotLithium6", new ParticleStack(Particles.deuteron, 261600, 500), "ingotBeryllium7",
				emptyParticleStack(), new ParticleStack(Particles.neutron), emptyParticleStack(), 3000, 0.075, 2870);
		
		addRecipe("ingotLithium7", new ParticleStack(Particles.deuteron, 145200, 3500), emptyItemStack(),
				 new ParticleStack(Particles.alpha,2), new ParticleStack(Particles.neutron), emptyParticleStack(), 9000, 0.53, 15100);
		
		addRecipe("ingotBeryllium", new ParticleStack(Particles.deuteron, 357000, 34000), "ingotBeryllium7",
				new ParticleStack(Particles.deuteron), new ParticleStack(Particles.neutron,2), emptyParticleStack(), 53000, 0.027, -20600);
		
		addRecipe("ingotBeryllium", new ParticleStack(Particles.deuteron, 172500, 1500), "ingotLithium7",
				new ParticleStack(Particles.alpha), emptyParticleStack(), emptyParticleStack(), 5000, 0.3, 7660);
		
		addRecipe("ingotBeryllium", new ParticleStack(Particles.deuteron, 135300, 55000), emptyItemStack(),
				 new ParticleStack(Particles.alpha,2), new ParticleStack(Particles.neutron), new ParticleStack(Particles.deuteron), 150000, 0.67, -1570);
		
		addRecipe("ingotBoron11", new ParticleStack(Particles.deuteron,  182100, 1500), "dustGraphite",
				emptyParticleStack(), new ParticleStack(Particles.neutron), emptyParticleStack(), 3000, 0.25, 13200);
		
		addRecipe("dustGraphite", new ParticleStack(Particles.deuteron, 177900, 5000), "ingotBoron10",
				new ParticleStack(Particles.alpha), emptyParticleStack(), emptyParticleStack(), 13000, 0.27, -830);
		
		addRecipe("ingotSodium",new ParticleStack(Particles.deuteron, 197700, 30000), "ingotSodium22",
				new ParticleStack(Particles.deuteron), new ParticleStack(Particles.neutron), emptyParticleStack(), 55000, 0.19, -12400);
		
		addRecipe("ingotMagnesium24",new ParticleStack(Particles.deuteron, 204600, 6000), "ingotSodium22",
				new ParticleStack(Particles.alpha), emptyParticleStack(), emptyParticleStack(), 11000, 0.17, 2470);
		
		addRecipe("ingotAluminum",new ParticleStack(Particles.deuteron, 330000, 56000), "ingotSodium22",
				new ParticleStack(Particles.alpha), emptyParticleStack(), new ParticleStack(Particles.triton), 200000, 0.035, -15200);
		
		addRecipe("ingotCobalt",new ParticleStack(Particles.deuteron, 204600, 5000), "ingotCobalt60",
				new ParticleStack(Particles.proton), emptyParticleStack(), emptyParticleStack(), 10000, 0.17, 5270);
		
		addRecipe("ingotYttrium",new ParticleStack(Particles.deuteron, 348000, 60000), "ingotStrontium",
				new ParticleStack(Particles.alpha), emptyParticleStack(), emptyParticleStack(), 200000, 0.029, 8400);
		
		addRecipe("ingotOsmium",new ParticleStack(Particles.deuteron, 136500, 12000), "ingotIridium192",
				emptyParticleStack(), new ParticleStack(Particles.neutron,2), emptyParticleStack(), 14500, 0.65, -4560);
		
		addRecipe("dustBismuth",new ParticleStack(Particles.deuteron, 315000, 10000), "dustPolonium",
				emptyParticleStack(), new ParticleStack(Particles.neutron), emptyParticleStack(), 15000, 0.04, 2250);
		
		addRecipe("ingotUranium233",new ParticleStack(Particles.deuteron, 315000, 10000), "ingotUranium234",
				new ParticleStack(Particles.proton), emptyParticleStack(), emptyParticleStack(), 16000, 0.04, 4620);
		
		addRecipe("ingotUranium234",new ParticleStack(Particles.deuteron, 208200, 11000), "ingotUranium235",
				new ParticleStack(Particles.proton), emptyParticleStack(), emptyParticleStack(), 17000, 0.16, 3070);
		
		addRecipe("ingotUranium235",new ParticleStack(Particles.deuteron, 435000, 11500), "ingotNeptunium236",
				emptyParticleStack(), new ParticleStack(Particles.neutron), emptyParticleStack(), 19500, 0.014, 2090);
		
		addRecipe("ingotUranium238",new ParticleStack(Particles.deuteron, 143700, 24000), "ingotNeptunium236",
				emptyParticleStack(), new ParticleStack(Particles.neutron,4), emptyParticleStack(), 30000, 0.55, -15700);
		
		addRecipe("ingotPlutonium238",new ParticleStack(Particles.deuteron, 243600, 11000), "ingotPlutonium239",
				new ParticleStack(Particles.proton), emptyParticleStack(), emptyParticleStack(), 17000, 0.095, 3420);
		
		addRecipe("ingotPutonium241",new ParticleStack(Particles.deuteron, 189300, 10500), "ingotAmericium241",
				emptyParticleStack(), new ParticleStack(Particles.neutron,2), emptyParticleStack(), 19500, 0.22, -3500);
		
		addRecipe("ingotPlutonium242", new ParticleStack(Particles.deuteron,182100,12000), "ingotAmericium242",
				emptyParticleStack(), new ParticleStack(Particles.neutron, 2), emptyParticleStack(), 16000,  0.25, -4270);
		
		addRecipe("ingotAmericium243", new ParticleStack(Particles.deuteron,247800,12000), "ingotCurium243",
				emptyParticleStack(), new ParticleStack(Particles.neutron, 2), emptyParticleStack(), 15000,  0.09, -3520);
		
		
		
		

		
		// photon reactions
		
		addRecipe("ingotLithium6", new ParticleStack(Particles.photon, 1221000, 5500), emptyItemStack(),
				new ParticleStack(Particles.alpha), new ParticleStack(Particles.neutron), new ParticleStack(Particles.proton), 17000, 0.01, -3700);
		
		addRecipe("ingotLithium7", new ParticleStack(Particles.photon, 480000, 18500), "ingotLithium6",
				emptyParticleStack(), new ParticleStack(Particles.neutron), emptyParticleStack(), 24000, 0.01, -7250);
		
		addRecipe("ingotBeryllium", new ParticleStack(Particles.photon, 534000, 26000), emptyItemStack(),
				new ParticleStack(Particles.alpha,2), new ParticleStack(Particles.neutron), emptyParticleStack(), 46000, 0.01, 472);
		
		addRecipe("ingotBoron11", new ParticleStack(Particles.photon, 513000, 24500), emptyItemStack(),
				new ParticleStack(Particles.alpha,2), new ParticleStack(Particles.neutron), new ParticleStack(Particles.deuteron), 30500, 0.01, -14800);
		
		addRecipe("dustGraphite", new ParticleStack(Particles.photon, 1458000, 31500), "ingotBeryllium7",
				new ParticleStack(Particles.alpha), new ParticleStack(Particles.neutron), emptyParticleStack(), 42500, 0.01, -25200);
		
		addRecipe("dustGraphite", new ParticleStack(Particles.photon, 1317000, 42500), "ingotLithium6",
				new ParticleStack(Particles.alpha), new ParticleStack(Particles.neutron), new ParticleStack(Particles.proton), 55000, 0.01, -30300);
		
		addRecipe("ingotMagnesium26", new ParticleStack(Particles.photon, 390000, 21000), "ingotMagnesium24",
				emptyParticleStack(), new ParticleStack(Particles.neutron, 2), emptyParticleStack(), 28000, 0.02, -18400);
		
		addRecipe("ingotAluminum", new ParticleStack(Particles.photon,426000, 17500), "ingotMagnesium26",
				new ParticleStack(Particles.proton), emptyParticleStack(), emptyParticleStack(), 23000, 0.015,-7760);
		
		addRecipe("itemSilicon", new ParticleStack(Particles.photon, 315000, 18000), "ingotAluminum",
				new ParticleStack(Particles.proton), emptyParticleStack(), emptyParticleStack(), 23000, 0.04,-11100);
		
		addRecipe("ingotIron", new ParticleStack(Particles.photon, 279900, 14500), "ingotManganese",
				new ParticleStack(Particles.proton), emptyParticleStack(), emptyParticleStack(), 21000, 0.06,-9670);
		
		addRecipe("ingotCopper", new ParticleStack(Particles.photon, 363000, 19500), "ingotNickel",
				new ParticleStack(Particles.proton), new ParticleStack(Particles.neutron), emptyParticleStack(), 27000, 0.025,-16200);
		
		addRecipe("ingotZirconium", new ParticleStack(Particles.photon, 390000, 11500), "ingotYttrium",
				new ParticleStack(Particles.proton), emptyParticleStack(), emptyParticleStack(), 19000, 0.02,-7830);
		
		addRecipe("ingotNiobium", new ParticleStack(Particles.photon, 780000, 13000), "ingotYttrium",
				new ParticleStack(Particles.alpha), emptyParticleStack(), emptyParticleStack(), 21000, 0.01,-909);

		addRecipe("ingotUranium235", new ParticleStack(Particles.photon, 204600, 13500), "ingotUranium233",
				emptyParticleStack(), new ParticleStack(Particles.neutron,2), emptyParticleStack(), 18000, 0.17,-12100);
		
		addRecipe("ingotNeptunium237", new ParticleStack(Particles.photon, 152700, 9750), "ingotNeptunium236",
				emptyParticleStack(), new ParticleStack(Particles.neutron), emptyParticleStack(), 14500, 0.45,-6580);
		
		addRecipe("ingotPlutonium239", new ParticleStack(Particles.photon, 194700, 10000), "ingotPlutonium238",
				emptyParticleStack(), new ParticleStack(Particles.neutron), emptyParticleStack(), 13500, 0.2,-5650);
		
		addRecipe("ingotAmericium243", new ParticleStack(Particles.photon, 137400, 10000), "ingotAmericium242",
				emptyParticleStack(), new ParticleStack(Particles.neutron), emptyParticleStack(), 12500, 0.64,-6360);
		
		
		
		
		// triton reactions
		
		addRecipe("ingotBeryllium", new ParticleStack(Particles.triton, 137400, 1000), "ingotBoron11",
				emptyParticleStack(), new ParticleStack(Particles.neutron), emptyParticleStack(), 7000, 0.47, 9050);	
		
		addRecipe("dustGraphite", new ParticleStack(Particles.triton, 176100, 2500), "ingotBoron11",
				new ParticleStack(Particles.alpha), emptyParticleStack(), emptyParticleStack(), 7500, 0.28, 4370);	
		
		
		
		
		// alpha reactions
		
		addRecipe("ingotLithium6", new ParticleStack(Particles.alpha, 6420000, 27000), "ingotBoron10",
				emptyParticleStack(), new ParticleStack(Particles.photon), emptyParticleStack(), 45000, 0.01, 9330);	
		
		addRecipe("ingotLithium7", new ParticleStack(Particles.alpha, 177900, 6500), "ingotBoron10",
				emptyParticleStack(), new ParticleStack(Particles.neutron), emptyParticleStack(), 7600, 0.27, -3810);	
		
		addRecipe("ingotBeryllium", new ParticleStack(Particles.alpha, 147900, 4000), "dustGraphite",
				emptyParticleStack(), new ParticleStack(Particles.neutron), emptyParticleStack(), 6500, 0.5, 4680);	
			
		addRecipe("ingotBeryllium", new ParticleStack(Particles.alpha, 339000, 100000), "ingotBeryllium7",
				new ParticleStack(Particles.alpha), new ParticleStack(Particles.neutron,2), emptyParticleStack(), 145000, 0.032, -20500);	
		
		addRecipe("dustGraphite", new ParticleStack(Particles.alpha, 259500, 53000), "ingotBoron10",
				new ParticleStack(Particles.alpha), new ParticleStack(Particles.neutron),new ParticleStack(Particles.proton), 69000, 0.077, -26900);	
		
		addRecipe("dustGraphite", new ParticleStack(Particles.alpha, 227100, 38000), "ingotBoron11",
				new ParticleStack(Particles.alpha), emptyParticleStack(),new ParticleStack(Particles.proton), 50000, 0.12, -15400);	
		
		addRecipe("ingotAluminum", new ParticleStack(Particles.alpha, 158100, 12000), "itemSilicon",
				new ParticleStack(Particles.positron), new ParticleStack(Particles.neutron), new ParticleStack(Particles.electron_neutrino), 17500, 0.4, -3660);	
		
		addRecipe("ingotCobalt", new ParticleStack(Particles.alpha, 267300, 38000), "ingotCobalt60",
				new ParticleStack(Particles.proton,2), new ParticleStack(Particles.neutron), emptyParticleStack(), 54000, 0.07, -20800);	
		
		addRecipe("ingotOsmium", new ParticleStack(Particles.alpha, 465000, 19000), "ingotPlatinum",
				emptyParticleStack(), new ParticleStack(Particles.neutron), emptyParticleStack(), 27500, 0.011, -9750);	
		
		addRecipe("ingotLead", new ParticleStack(Particles.alpha, 122400, 26000), "dustPolonium",
				emptyParticleStack(), new ParticleStack(Particles.neutron,2), emptyParticleStack(), 32000, 0.94, -20500);	
		
		addRecipe("ingotUranium233", new ParticleStack(Particles.alpha, 1119000, 27000), "ingotNeptunium236",
				new ParticleStack(Particles.proton), emptyParticleStack(), emptyParticleStack(), 30000, 0.01, -11800);	
		
		addRecipe("ingotUranium235", new ParticleStack(Particles.alpha, 960000, 21000), "ingotPlutonium238",
				emptyParticleStack(), new ParticleStack(Particles.neutron), emptyParticleStack(), 32000, 0.01, -11900);	
		
		addRecipe("ingotUranium238", new ParticleStack(Particles.alpha, 170700, 27000), "ingotPlutonium239",
				emptyParticleStack(), new ParticleStack(Particles.neutron,3), emptyParticleStack(), 30000, 0.31, -24100);	
		
		addRecipe("ingotUranium238", new ParticleStack(Particles.alpha, 274500, 34500), "ingotPlutonium238",
				emptyParticleStack(), new ParticleStack(Particles.neutron,3), emptyParticleStack(), 42000, 0.064, -29700);	
		
		addRecipe("ingotPlutonium239", new ParticleStack(Particles.alpha, 1206000, 28000), "ingotAmericium242",
				new ParticleStack(Particles.proton), emptyParticleStack(), emptyParticleStack(), 30000, 0.01, -12300);	
		
		//Helion reactions
		addRecipe("ingotLithium6", new ParticleStack(Particles.helion, 189000, 11000), "ingotBerylium7",
				new ParticleStack(Particles.deuteron), emptyParticleStack(), emptyParticleStack(), 30000, 0.22, -397);	
		
		addRecipe("ingotLithium6", new ParticleStack(Particles.helion, 176000, 30000), emptyItemStack(),
				new ParticleStack(Particles.alpha,2), emptyParticleStack(), new ParticleStack(Particles.proton), 150000, 0.28, 18400);	
		
		addRecipe("ingotBeryllium", new ParticleStack(Particles.helion, 257000, 23000), "ingotBeryllium7",
				new ParticleStack(Particles.alpha), new ParticleStack(Particles.neutron), emptyParticleStack(), 28000, 0.08, 14);	
		
		addRecipe("ingotBoron10", new ParticleStack(Particles.helion, 240000, 30000), "ingotBeryllium7",
				new ParticleStack(Particles.alpha), new ParticleStack(Particles.neutron), new ParticleStack(Particles.proton), 47000, 0.1, -6060);	
		
		addRecipe("dustGraphite", new ParticleStack(Particles.helion, 280000, 22000), "ingotBeryllium7",
				new ParticleStack(Particles.alpha,2), emptyParticleStack(), emptyParticleStack(), 31000, 0.06, -4670);	
		
		addRecipe("ingotAluminum", new ParticleStack(Particles.helion, 257000, 90000), "ingotSodium22",
				new ParticleStack(Particles.alpha,2), emptyParticleStack(), emptyParticleStack(), 165000, 0.08, -912);
		
		addRecipe("ingotCobalt", new ParticleStack(Particles.helion, 296000, 14000), "ingotCobalt60",
				new ParticleStack(Particles.proton,2), emptyParticleStack(), emptyParticleStack(), 24000, 0.05, -226);
		
		addRecipe("ingotLead", new ParticleStack(Particles.helion, 1180000, 23000), "dustPolonium",
				emptyParticleStack(), new ParticleStack(Particles.neutron), emptyParticleStack(), 30000, 0.01, 42);
		
		addRecipe("ingotNeptunium237", new ParticleStack(Particles.helion, 1260000, 23500), "ingotPlutonium238",
				new ParticleStack(Particles.proton), new ParticleStack(Particles.neutron), emptyParticleStack(), 30000, 0.01, -2230);
		
		
		
		
		// Doping
		addRecipe("siliconWafer", new ParticleStack(Particles.boron_ion, 120000, 600,2), "siliconPDoped",
				emptyParticleStack(),emptyParticleStack(),emptyParticleStack(), 1000, 1);
		
		// heavy ion bombardment
		addRecipe("ingotBerkelium248", new ParticleStack(Particles.calcium_48_ion, 3840000, 40000,2), "ingotCopernicium291",
				new ParticleStack(Particles.alpha),new ParticleStack(Particles.neutron),new ParticleStack(Particles.electron_neutrino,2), 50000, 0.01,-24400);
		
		// Inverse beta + decay
		
		addRecipe("ingotNickel", new ParticleStack(Particles.electron_antineutrino, 15360000, 900), "ingotIron",
				new ParticleStack(Particles.positron,2), new ParticleStack(Particles.electron_neutrino), emptyParticleStack(), 10900, 0.005, -893);	
		
		addRecipe("ingotIridium192", new ParticleStack(Particles.electron_antineutrino, 15360000, 0), "ingotOsmium",
				new ParticleStack(Particles.positron), emptyParticleStack(), emptyParticleStack(), 10000, 0.005, 536);	
		
		addRecipe("ingotAmericium242", new ParticleStack(Particles.electron_antineutrino, 15360000, 0), "ingotPlutonium242",
				new ParticleStack(Particles.positron), emptyParticleStack(), emptyParticleStack(), 10000, 0.005, 240);	
		
		addRecipe("ingotCurium243", new ParticleStack(Particles.electron_antineutrino, 15360000, 600), "ingotAmericium243",
				new ParticleStack(Particles.positron), emptyParticleStack(), emptyParticleStack(), 10600, 0.005, -503);	
		
		addRecipe("ingotBerkelium247", new ParticleStack(Particles.electron_antineutrino, 15360000, 600), "ingotCurium247",
				new ParticleStack(Particles.positron), emptyParticleStack(), emptyParticleStack(), 10600, 0.005, -555);	
		
		
		
		
		// Inverse beta - decay
		
		addRecipe("ingotOsmium", new ParticleStack(Particles.electron_neutrino, 15360000, 1600), "ingotIridium192",
				emptyParticleStack(), emptyParticleStack(), new ParticleStack(Particles.electron), 11600, 0.005, -1560);	
		
		addRecipe("ingotPlutonium242", new ParticleStack(Particles.electron_neutrino, 15360000, 1300), "ingotAmericium242",
				emptyParticleStack(), emptyParticleStack(), new ParticleStack(Particles.electron), 11300, 0.005, -1260);	
		
		addRecipe("ingotAmericium243", new ParticleStack(Particles.electron_neutrino, 15360000, 600), "ingotCurium243",
				emptyParticleStack(), emptyParticleStack(), new ParticleStack(Particles.electron), 10600, 0.005, -519);	
		
		addRecipe("ingotCurium247", new ParticleStack(Particles.electron_neutrino, 15360000, 500), "ingotBerkelium247",
				emptyParticleStack(), emptyParticleStack(), new ParticleStack(Particles.electron), 10500, 0.005, -467);	
		
		
		
		
		// proton induced fission
		
		addRecipe("ingotTungsten", new ParticleStack(Particles.proton, 330000, 400000), "wasteFissionLight",
				emptyParticleStack(), new ParticleStack(Particles.neutron), emptyParticleStack(), 1000000, 0.035, 0);
		
		addRecipe("ingotPlatinum", new ParticleStack(Particles.proton, 1182000, 200000), "wasteFissionLight",
				emptyParticleStack(),new ParticleStack(Particles.neutron), emptyParticleStack(), 1000000, 0.01, 0);
		
		addRecipe("ingotGold", new ParticleStack(Particles.proton, 256800, 200000), "wasteFissionLight",
				emptyParticleStack(), new ParticleStack(Particles.neutron), emptyParticleStack(), 1000000, 0.08, 0);
		
		addRecipe("ingotLead", new ParticleStack(Particles.proton, 233100, 200000), "wasteFissionLight",
				emptyParticleStack(), new ParticleStack(Particles.neutron), emptyParticleStack(), 1000000, 0.11, 0);
		
		addRecipe("dustBismuth", new ParticleStack(Particles.proton, 194700, 100000), "wasteFissionLight",
				emptyParticleStack(), new ParticleStack(Particles.neutron), emptyParticleStack(), 1000000, 0.2, 0);
		
		addRecipe("dustRadium", new ParticleStack(Particles.proton, 240000, 60000), "wasteFissionHeavy",
				emptyParticleStack(), new ParticleStack(Particles.neutron), emptyParticleStack(), 1000000, 0.1, 0);
		
		addRecipe("ingotThorium", new ParticleStack(Particles.proton, 116700, 60000), "wasteFissionHeavy",
				emptyParticleStack(), new ParticleStack(Particles.neutron), emptyParticleStack(), 1000000, 0.5, 0);
		
		addRecipe("ingotUranium233All", new ParticleStack(Particles.proton, 105300, 60000), "wasteFissionHeavy",
				emptyParticleStack(), new ParticleStack(Particles.neutron), emptyParticleStack(), 1000000, 1, 0);
		
		addRecipe("ingotUranium234All", new ParticleStack(Particles.proton, 103200, 60000), "wasteFissionHeavy",
				emptyParticleStack(), new ParticleStack(Particles.neutron,2), emptyParticleStack(), 1000000, 1, 0);
		
		addRecipe("ingotUranium235All", new ParticleStack(Particles.proton, 97500, 60000), "wasteFissionHeavy",
				emptyParticleStack(), new ParticleStack(Particles.neutron,4), emptyParticleStack(), 1000000, 1, 0);
		
		addRecipe("ingotUranium238All", new ParticleStack(Particles.proton, 101400, 60000), "wasteFissionHeavy",
				emptyParticleStack(), new ParticleStack(Particles.neutron,2), emptyParticleStack(), 1000000, 1, 0);
		
		addRecipe("ingotNeptunium237All", new ParticleStack(Particles.proton, 99000, 60000), "wasteFissionHeavy",
				emptyParticleStack(), new ParticleStack(Particles.neutron,2), emptyParticleStack(), 1000000, 1, 0);
		
		addRecipe("ingotPlutonium239All", new ParticleStack(Particles.proton, 97500, 60000), "wasteFissionHeavy",
				emptyParticleStack(), new ParticleStack(Particles.neutron,4), emptyParticleStack(), 1000000, 1, 0);
		
		addRecipe("ingotPlutonium241All", new ParticleStack(Particles.proton, 97500, 60000), "wasteFissionHeavy",
				emptyParticleStack(), new ParticleStack(Particles.neutron,4), emptyParticleStack(), 1000000, 1, 0);
		
		addRecipe("ingotPlutonium242All", new ParticleStack(Particles.proton, 97500, 60000), "wasteFissionHeavy",
				emptyParticleStack(), new ParticleStack(Particles.neutron,4), emptyParticleStack(), 1000000, 1, 0);
		
		addRecipe("ingotAmericium241All", new ParticleStack(Particles.proton, 97500, 60000), "wasteFissionHeavy",
				emptyParticleStack(), new ParticleStack(Particles.neutron,4), emptyParticleStack(), 1000000, 1, 0);
		
		addRecipe("ingotAmericium243All", new ParticleStack(Particles.proton, 108300, 60000), "wasteFissionHeavy",
				emptyParticleStack(), new ParticleStack(Particles.neutron,2), emptyParticleStack(), 1000000, 1, 0);
		
		addRecipe("ingotCopernicium291All", new ParticleStack(Particles.proton, 50000, 60000), "wasteFissionHeavy",
				emptyParticleStack(), new ParticleStack(Particles.neutron,8), emptyParticleStack(), 1000000, 1, 0);
		
		
		
		// antiproton production 
		addRecipe("ingotCalifornium252All", new ParticleStack(Particles.proton, 240000, 6580000), "wasteSpallationCalifornium", 
				new ParticleStack(Particles.proton), emptyParticleStack(), new ParticleStack(Particles.antiproton), 20000000, 0.1, -6580000);
		
		addRecipe("ingotCalifornium251All", new ParticleStack(Particles.proton, 240000, 6580000), "wasteSpallationCalifornium",
				new ParticleStack(Particles.proton), emptyParticleStack(), new ParticleStack(Particles.antiproton), 20000000, 0.1, -6580000);
		
		addRecipe("ingotCalifornium250All", new ParticleStack(Particles.proton, 240000, 6580000), "wasteSpallationCalifornium",
				new ParticleStack(Particles.proton), emptyParticleStack(), new ParticleStack(Particles.antiproton), 20000000, 0.1, -6580000);
		
		addRecipe("ingotCalifornium249All", new ParticleStack(Particles.proton, 240000, 6580000), "wasteSpallationCalifornium",
				new ParticleStack(Particles.proton), emptyParticleStack(), new ParticleStack(Particles.antiproton), 20000000, 0.1, -6580000);
		
		addRecipe("ingotBerkelium248All", new ParticleStack(Particles.proton, 240000, 6580000), "wasteSpallationBerkelium",
				new ParticleStack(Particles.proton), emptyParticleStack(), new ParticleStack(Particles.antiproton), 20000000, 0.1, -6580000);
		
		addRecipe("ingotBerkelium247All", new ParticleStack(Particles.proton, 240000, 6580000), "wasteSpallationBerkelium",
				new ParticleStack(Particles.proton), emptyParticleStack(), new ParticleStack(Particles.antiproton), 20000000, 0.1, -6580000);
		
		addRecipe("ingotCurium247All", new ParticleStack(Particles.proton, 240000, 6580000), "wasteSpallationCurium",
				new ParticleStack(Particles.proton), emptyParticleStack(), new ParticleStack(Particles.antiproton), 20000000, 0.1, -6580000);
		
		addRecipe("ingotCurium246All", new ParticleStack(Particles.proton, 240000, 6580000), "wasteSpallationCurium",
				new ParticleStack(Particles.proton), emptyParticleStack(), new ParticleStack(Particles.antiproton), 20000000, 0.1, -6580000);
		
		addRecipe("ingotCurium245All", new ParticleStack(Particles.proton, 240000, 6580000), "wasteSpallationCurium",
				new ParticleStack(Particles.proton), emptyParticleStack(), new ParticleStack(Particles.antiproton), 20000000, 0.1, -6580000);
		
		addRecipe("ingotCurium243All", new ParticleStack(Particles.proton, 240000, 6580000), "wasteSpallationCurium",
				new ParticleStack(Particles.proton), emptyParticleStack(), new ParticleStack(Particles.antiproton), 20000000, 0.1, -6580000);
		
		addRecipe("ingotAmericium243All", new ParticleStack(Particles.proton, 240000, 6580000), "wasteSpallationAmericium",
				new ParticleStack(Particles.proton), emptyParticleStack(), new ParticleStack(Particles.antiproton), 20000000, 0.1, -6580000);
		
		addRecipe("ingotAmericium242All", new ParticleStack(Particles.proton, 240000, 6580000), "wasteSpallationAmericium",
				new ParticleStack(Particles.proton), emptyParticleStack(), new ParticleStack(Particles.antiproton), 20000000, 0.1, -6580000);
		
		addRecipe("ingotAmericium241All", new ParticleStack(Particles.proton, 240000, 6580000), "wasteSpallationAmericium",
				new ParticleStack(Particles.proton), emptyParticleStack(), new ParticleStack(Particles.antiproton), 20000000, 0.1, -6580000);
		
		addRecipe("ingotPlutonium242All", new ParticleStack(Particles.proton, 240000, 6580000), "wasteSpallationPlutonium",
				new ParticleStack(Particles.proton), emptyParticleStack(), new ParticleStack(Particles.antiproton), 20000000, 0.1, -6580000);
		
		addRecipe("ingotPlutonium241All", new ParticleStack(Particles.proton, 240000, 6580000), "wasteSpallationPlutonium",
				new ParticleStack(Particles.proton), emptyParticleStack(), new ParticleStack(Particles.antiproton), 20000000, 0.1, -6580000);
		
		addRecipe("ingotPlutonium239All", new ParticleStack(Particles.proton, 240000, 6580000), "wasteSpallationPlutonium",
				new ParticleStack(Particles.proton), emptyParticleStack(), new ParticleStack(Particles.antiproton), 20000000, 0.1, -6580000);
		
		addRecipe("ingotPlutonium238All", new ParticleStack(Particles.proton, 240000, 6580000), "wasteSpallationPlutonium",
				new ParticleStack(Particles.proton), emptyParticleStack(), new ParticleStack(Particles.antiproton), 20000000, 0.1, -6580000);
		
		addRecipe("ingotNeptunium237All", new ParticleStack(Particles.proton, 240000, 6580000), "wasteSpallationNeptunium",
				new ParticleStack(Particles.proton), emptyParticleStack(), new ParticleStack(Particles.antiproton), 20000000, 0.1, -6580000);
		
		addRecipe("ingotNeptunium236All", new ParticleStack(Particles.proton, 240000, 6580000), "wasteSpallationNeptunium",
				new ParticleStack(Particles.proton), emptyParticleStack(), new ParticleStack(Particles.antiproton), 20000000, 0.1, -6580000);
		
		addRecipe("ingotUranium238All", new ParticleStack(Particles.proton, 240000, 6580000), "wasteSpallationUranium",
				new ParticleStack(Particles.proton), emptyParticleStack(), new ParticleStack(Particles.antiproton), 20000000, 0.1, -6580000);
		
		addRecipe("ingotUranium235All", new ParticleStack(Particles.proton, 240000, 6580000), "wasteSpallationUranium",
				new ParticleStack(Particles.proton), emptyParticleStack(), new ParticleStack(Particles.antiproton), 20000000, 0.1, -6580000);
		
		addRecipe("ingotUranium234", new ParticleStack(Particles.proton, 240000, 6580000), "wasteSpallationUranium",
				new ParticleStack(Particles.proton), emptyParticleStack(), new ParticleStack(Particles.antiproton), 20000000, 0.1, -6580000);
		
		addRecipe("ingotUranium233All", new ParticleStack(Particles.proton, 240000, 6580000), "wasteSpallationUranium",
				new ParticleStack(Particles.proton), emptyParticleStack(), new ParticleStack(Particles.antiproton), 20000000, 0.1, -6580000);
		
		addRecipe("dustProtactinium233", new ParticleStack(Particles.proton, 240000, 6580000), "wasteSpallationProtactinium",
				new ParticleStack(Particles.proton), emptyParticleStack(), new ParticleStack(Particles.antiproton), 20000000, 0.1, -6580000);
		
		addRecipe("dustProtactinium231", new ParticleStack(Particles.proton, 240000, 6580000), "wasteSpallationProtactinium",
				new ParticleStack(Particles.proton), emptyParticleStack(), new ParticleStack(Particles.antiproton), 20000000, 0.1, -6580000);
		
		addRecipe("ingotThorium", new ParticleStack(Particles.proton, 240000, 6580000), "wasteSpallationThorium",
				new ParticleStack(Particles.proton), emptyParticleStack(), new ParticleStack(Particles.antiproton), 20000000, 0.1, -6580000);
		
		addRecipe("dustRadium", new ParticleStack(Particles.proton, 240000, 6580000), "wasteSpallationRadium",
				new ParticleStack(Particles.proton), emptyParticleStack(), new ParticleStack(Particles.antiproton), 20000000, 0.1, -6580000);
		
		addRecipe("dustPolonium", new ParticleStack(Particles.proton, 240000, 6580000), "wasteSpallationPolonium",
				new ParticleStack(Particles.proton), emptyParticleStack(), new ParticleStack(Particles.antiproton), 20000000, 0.1, -6580000);
		
		addRecipe("dustBismuth", new ParticleStack(Particles.proton, 240000, 6580000), "wasteSpallationBismuth",
				new ParticleStack(Particles.proton), emptyParticleStack(), new ParticleStack(Particles.antiproton), 20000000, 0.1, -6580000);
		
		addRecipe("ingotLead", new ParticleStack(Particles.proton, 240000, 6580000), "wasteSpallationLead",
				new ParticleStack(Particles.proton), emptyParticleStack(), new ParticleStack(Particles.antiproton), 20000000, 0.1, -6580000);
		
		addRecipe("ingotGold", new ParticleStack(Particles.proton, 240000, 6580000), "wasteSpallationGold",
				new ParticleStack(Particles.proton), emptyParticleStack(), new ParticleStack(Particles.antiproton), 20000000, 0.1, -6580000);
		
		addRecipe("ingotPlatinum", new ParticleStack(Particles.proton, 240000, 6580000), "wasteSpallationPlatinum",
				new ParticleStack(Particles.proton), emptyParticleStack(), new ParticleStack(Particles.antiproton), 20000000, 0.1, -6580000);
		
		addRecipe("ingotIridium", new ParticleStack(Particles.proton, 240000, 6580000), "wasteSpallationIridium",
				new ParticleStack(Particles.proton), emptyParticleStack(), new ParticleStack(Particles.antiproton), 20000000, 0.1, -6580000);
		
		addRecipe("ingotIridium192", new ParticleStack(Particles.proton, 240000, 6580000), "wasteSpallationIridium",
				new ParticleStack(Particles.proton), emptyParticleStack(), new ParticleStack(Particles.antiproton), 20000000, 0.1, -6580000);
	
		addRecipe("ingotOsmium", new ParticleStack(Particles.proton, 240000, 6580000), "wasteSpallationOsmium",
				new ParticleStack(Particles.proton), emptyParticleStack(), new ParticleStack(Particles.antiproton), 20000000, 0.1, -6580000);
		
		addRecipe("ingotTungsten", new ParticleStack(Particles.proton, 240000, 6580000), "wasteSpallationTungsten",
				new ParticleStack(Particles.proton), emptyParticleStack(), new ParticleStack(Particles.antiproton), 20000000, 0.1, -6580000);
		
		addRecipe("ingotHafnium", new ParticleStack(Particles.proton, 240000, 6580000), "wasteSpallationHafnium",
				new ParticleStack(Particles.proton), emptyParticleStack(), new ParticleStack(Particles.antiproton), 20000000, 0.1, -6580000);
		
		
		
		
		
		
		// Pion production
		
		addRecipe("ingotCalifornium252All", new ParticleStack(Particles.proton, 240000, 1540000), "wasteSpallationCalifornium",
				new ParticleStack(Particles.pion_plus),emptyParticleStack(),new ParticleStack(Particles.pion_minus), 5000000, 0.1, -1540000);
		
		addRecipe("ingotCalifornium251All", new ParticleStack(Particles.proton, 240000, 1540000), "wasteSpallationCalifornium",
				new ParticleStack(Particles.pion_plus),emptyParticleStack(),new ParticleStack(Particles.pion_minus), 5000000, 0.1, -1540000);
		
		addRecipe("ingotCalifornium250All", new ParticleStack(Particles.proton, 240000, 1540000), "wasteSpallationCalifornium",
				new ParticleStack(Particles.pion_plus),emptyParticleStack(),new ParticleStack(Particles.pion_minus), 5000000, 0.1, -1540000);
		
		addRecipe("ingotCalifornium249All", new ParticleStack(Particles.proton, 240000, 1540000), "wasteSpallationCalifornium",
				new ParticleStack(Particles.pion_plus),emptyParticleStack(),new ParticleStack(Particles.pion_minus), 5000000, 0.1, -1540000);
		
		addRecipe("ingotBerkelium248All", new ParticleStack(Particles.proton, 240000, 1540000), "wasteSpallationBerkelium",
				new ParticleStack(Particles.pion_plus),emptyParticleStack(),new ParticleStack(Particles.pion_minus), 5000000, 0.1, -1540000);
		
		addRecipe("ingotBerkelium247All", new ParticleStack(Particles.proton, 240000, 1540000), "wasteSpallationBerkelium",
				new ParticleStack(Particles.pion_plus),emptyParticleStack(),new ParticleStack(Particles.pion_minus), 5000000, 0.1, -1540000);
		
		addRecipe("ingotCurium247All", new ParticleStack(Particles.proton, 240000, 1540000), "wasteSpallationCurium",
				new ParticleStack(Particles.pion_plus),emptyParticleStack(),new ParticleStack(Particles.pion_minus), 5000000, 0.1, -1540000);
		
		addRecipe("ingotCurium246All", new ParticleStack(Particles.proton, 240000, 1540000), "wasteSpallationCurium",
				new ParticleStack(Particles.pion_plus),emptyParticleStack(),new ParticleStack(Particles.pion_minus), 5000000, 0.1, -1540000);
		
		addRecipe("ingotCurium245All", new ParticleStack(Particles.proton, 240000, 1540000), "wasteSpallationCurium",
				new ParticleStack(Particles.pion_plus),emptyParticleStack(),new ParticleStack(Particles.pion_minus), 5000000, 0.1, -1540000);
		
		addRecipe("ingotCurium243All", new ParticleStack(Particles.proton, 240000, 1540000), "wasteSpallationCurium",
				new ParticleStack(Particles.pion_plus),emptyParticleStack(),new ParticleStack(Particles.pion_minus), 5000000, 0.1, -1540000);
		
		addRecipe("ingotAmericium243All", new ParticleStack(Particles.proton, 240000, 1540000), "wasteSpallationAmericium",
				new ParticleStack(Particles.pion_plus),emptyParticleStack(),new ParticleStack(Particles.pion_minus), 5000000, 0.1, -1540000);
		
		addRecipe("ingotAmericium242All", new ParticleStack(Particles.proton, 240000, 1540000), "wasteSpallationAmericium",
				new ParticleStack(Particles.pion_plus),emptyParticleStack(),new ParticleStack(Particles.pion_minus), 5000000, 0.1, -1540000);
		
		addRecipe("ingotAmericium241All", new ParticleStack(Particles.proton, 240000, 1540000), "wasteSpallationAmericium",
				new ParticleStack(Particles.pion_plus),emptyParticleStack(),new ParticleStack(Particles.pion_minus), 5000000, 0.1, -1540000);
		
		addRecipe("ingotPlutonium242All", new ParticleStack(Particles.proton, 240000, 1540000), "wasteSpallationPlutonium",
				new ParticleStack(Particles.pion_plus),emptyParticleStack(),new ParticleStack(Particles.pion_minus), 5000000, 0.1, -1540000);
		
		addRecipe("ingotPlutonium241All", new ParticleStack(Particles.proton, 240000, 1540000), "wasteSpallationPlutonium",
				new ParticleStack(Particles.pion_plus),emptyParticleStack(),new ParticleStack(Particles.pion_minus), 5000000, 0.1, -1540000);
		
		addRecipe("ingotPlutonium239All", new ParticleStack(Particles.proton, 240000, 1540000), "wasteSpallationPlutonium",
				new ParticleStack(Particles.pion_plus),emptyParticleStack(),new ParticleStack(Particles.pion_minus), 5000000, 0.1, -1540000);
		
		addRecipe("ingotPlutonium238All", new ParticleStack(Particles.proton, 240000, 1540000), "wasteSpallationPlutonium",
				new ParticleStack(Particles.pion_plus),emptyParticleStack(),new ParticleStack(Particles.pion_minus), 5000000, 0.1, -1540000);
		
		addRecipe("ingotNeptunium237All", new ParticleStack(Particles.proton, 240000, 1540000), "wasteSpallationNeptunium",
				new ParticleStack(Particles.pion_plus),emptyParticleStack(),new ParticleStack(Particles.pion_minus), 5000000, 0.1, -1540000);
		
		addRecipe("ingotNeptunium236All", new ParticleStack(Particles.proton, 240000, 1540000), "wasteSpallationNeptunium",
				new ParticleStack(Particles.pion_plus),emptyParticleStack(),new ParticleStack(Particles.pion_minus), 5000000, 0.1, -1540000);
		
		addRecipe("ingotUranium238All", new ParticleStack(Particles.proton, 240000, 1540000), "wasteSpallationUranium",
				new ParticleStack(Particles.pion_plus),emptyParticleStack(),new ParticleStack(Particles.pion_minus), 5000000, 0.1, -1540000);
		
		addRecipe("ingotUranium235All", new ParticleStack(Particles.proton, 240000, 1540000), "wasteSpallationUranium",
				new ParticleStack(Particles.pion_plus),emptyParticleStack(),new ParticleStack(Particles.pion_minus), 5000000, 0.1, -1540000);
		
		addRecipe("ingotUranium234", new ParticleStack(Particles.proton, 240000, 1540000), "wasteSpallationUranium",
				new ParticleStack(Particles.pion_plus),emptyParticleStack(),new ParticleStack(Particles.pion_minus), 5000000, 0.1, -1540000);
		
		addRecipe("ingotUranium233All", new ParticleStack(Particles.proton, 240000, 1540000), "wasteSpallationUranium",
				new ParticleStack(Particles.pion_plus),emptyParticleStack(),new ParticleStack(Particles.pion_minus), 5000000, 0.1, -1540000);
	
		addRecipe("dustProtactinium233", new ParticleStack(Particles.proton, 240000, 1540000), "wasteSpallationProtactinium",
				new ParticleStack(Particles.pion_plus),emptyParticleStack(),new ParticleStack(Particles.pion_minus), 5000000, 0.1, -1540000);
		
		addRecipe("dustProtactinium231", new ParticleStack(Particles.proton, 240000, 1540000), "wasteSpallationProtactinium",
				new ParticleStack(Particles.pion_plus),emptyParticleStack(),new ParticleStack(Particles.pion_minus), 5000000, 0.1, -1540000);
		
		addRecipe("ingotThorium", new ParticleStack(Particles.proton, 240000, 1540000), "wasteSpallationThorium",
				new ParticleStack(Particles.pion_plus),emptyParticleStack(),new ParticleStack(Particles.pion_minus), 5000000, 0.1, -1540000);
		
		addRecipe("dustRadium", new ParticleStack(Particles.proton, 240000, 1540000), "wasteSpallationRadium",
				new ParticleStack(Particles.pion_plus),emptyParticleStack(),new ParticleStack(Particles.pion_minus), 5000000, 0.1, -1540000);
		
		addRecipe("dustPolonium", new ParticleStack(Particles.proton, 240000, 1540000), "wasteSpallationPolonium",
				new ParticleStack(Particles.pion_plus),emptyParticleStack(),new ParticleStack(Particles.pion_minus), 5000000, 0.1, -1540000);
		
		addRecipe("dustBismuth", new ParticleStack(Particles.proton, 240000, 1540000), "wasteSpallationBismuth",
				new ParticleStack(Particles.pion_plus),emptyParticleStack(),new ParticleStack(Particles.pion_minus), 5000000, 0.1, -1540000);
		
		addRecipe("ingotLead", new ParticleStack(Particles.proton, 240000, 1540000), "wasteSpallationLead",
				new ParticleStack(Particles.pion_plus),emptyParticleStack(),new ParticleStack(Particles.pion_minus), 5000000, 0.1, -1540000);
		
		addRecipe("ingotGold", new ParticleStack(Particles.proton, 240000, 1540000), "wasteSpallationGold",
				new ParticleStack(Particles.pion_plus),emptyParticleStack(),new ParticleStack(Particles.pion_minus), 5000000, 0.1, -1540000);
		
		addRecipe("ingotPlatinum", new ParticleStack(Particles.proton, 240000, 1540000), "wasteSpallationPlatinum",
				new ParticleStack(Particles.pion_plus),emptyParticleStack(),new ParticleStack(Particles.pion_minus), 5000000, 0.1, -1540000);
		
		addRecipe("ingotIridium", new ParticleStack(Particles.proton, 240000, 1540000), "wasteSpallationIridium",
				new ParticleStack(Particles.pion_plus),emptyParticleStack(),new ParticleStack(Particles.pion_minus), 5000000, 0.1, -1540000);
		
		addRecipe("ingotIridium192", new ParticleStack(Particles.proton, 240000, 1540000), "wasteSpallationIridium",
				new ParticleStack(Particles.pion_plus),emptyParticleStack(),new ParticleStack(Particles.pion_minus), 5000000, 0.1, -1540000);
		
		addRecipe("ingotOsmium", new ParticleStack(Particles.proton, 240000, 1540000), "wasteSpallationOsmium",
				new ParticleStack(Particles.pion_plus),emptyParticleStack(),new ParticleStack(Particles.pion_minus), 5000000, 0.1, -1540000);
		
		addRecipe("ingotTungsten", new ParticleStack(Particles.proton, 240000, 1540000), "wasteSpallationTungsten",
				new ParticleStack(Particles.pion_plus),emptyParticleStack(),new ParticleStack(Particles.pion_minus), 5000000, 0.1, -1540000);
		
		addRecipe("ingotHafnium", new ParticleStack(Particles.proton, 240000, 1540000), "wasteSpallationHafnium",
				new ParticleStack(Particles.pion_plus),emptyParticleStack(),new ParticleStack(Particles.pion_minus), 5000000, 0.1, -1540000);
	
	
	
		
		// antiproton anhilation
		
		addRecipe("ingotCalifornium252All", new ParticleStack(Particles.antiproton, 120000), "wasteSpallationCalifornium",
				new ParticleStack(Particles.pion_plus),new ParticleStack(Particles.pion_naught),new ParticleStack(Particles.pion_minus), 10000000, 1, 1460000);
		
		addRecipe("ingotCalifornium251All", new ParticleStack(Particles.antiproton, 120000), "wasteSpallationCalifornium",
				new ParticleStack(Particles.pion_plus),new ParticleStack(Particles.pion_naught),new ParticleStack(Particles.pion_minus), 10000000, 1, 1460000);
		
		addRecipe("ingotCalifornium250All", new ParticleStack(Particles.antiproton, 120000), "wasteSpallationCalifornium",
				new ParticleStack(Particles.pion_plus),new ParticleStack(Particles.pion_naught),new ParticleStack(Particles.pion_minus), 10000000, 1, 1460000);
		
		addRecipe("ingotCalifornium249All", new ParticleStack(Particles.antiproton, 120000), "wasteSpallationCalifornium",
				new ParticleStack(Particles.pion_plus),new ParticleStack(Particles.pion_naught),new ParticleStack(Particles.pion_minus), 10000000, 1, 1460000);
		
		addRecipe("ingotBerkelium248All", new ParticleStack(Particles.antiproton, 120000), "wasteSpallationBerkelium",
				new ParticleStack(Particles.pion_plus),new ParticleStack(Particles.pion_naught),new ParticleStack(Particles.pion_minus), 10000000, 1, 1460000);
		
		addRecipe("ingotBerkelium247All", new ParticleStack(Particles.antiproton, 120000), "wasteSpallationBerkelium",
				new ParticleStack(Particles.pion_plus),new ParticleStack(Particles.pion_naught),new ParticleStack(Particles.pion_minus), 10000000, 1, 1460000);
		
		addRecipe("ingotCurium247All", new ParticleStack(Particles.antiproton, 120000), "wasteSpallationCurium",
				new ParticleStack(Particles.pion_plus),new ParticleStack(Particles.pion_naught),new ParticleStack(Particles.pion_minus), 10000000, 1, 1460000);
		
		addRecipe("ingotCurium246All", new ParticleStack(Particles.antiproton, 120000), "wasteSpallationCurium",
				new ParticleStack(Particles.pion_plus),new ParticleStack(Particles.pion_naught),new ParticleStack(Particles.pion_minus), 10000000, 1, 1460000);
		
		addRecipe("ingotCurium245All", new ParticleStack(Particles.antiproton, 120000), "wasteSpallationCurium",
				new ParticleStack(Particles.pion_plus),new ParticleStack(Particles.pion_naught),new ParticleStack(Particles.pion_minus), 10000000, 1, 1460000);
		
		addRecipe("ingotCurium243All", new ParticleStack(Particles.antiproton, 120000), "wasteSpallationCurium",
				new ParticleStack(Particles.pion_plus),new ParticleStack(Particles.pion_naught),new ParticleStack(Particles.pion_minus), 10000000, 1, 1460000);
		
		addRecipe("ingotAmericium243All", new ParticleStack(Particles.antiproton, 120000), "wasteSpallationAmericium",
				new ParticleStack(Particles.pion_plus),new ParticleStack(Particles.pion_naught),new ParticleStack(Particles.pion_minus), 10000000, 1, 1460000);
		
		addRecipe("ingotAmericium242All", new ParticleStack(Particles.antiproton, 120000), "wasteSpallationAmericium",
				new ParticleStack(Particles.pion_plus),new ParticleStack(Particles.pion_naught),new ParticleStack(Particles.pion_minus), 10000000, 1, 1460000);
		
		addRecipe("ingotAmericium241All", new ParticleStack(Particles.antiproton, 120000), "wasteSpallationAmericium",
				new ParticleStack(Particles.pion_plus),new ParticleStack(Particles.pion_naught),new ParticleStack(Particles.pion_minus), 10000000, 1, 1460000);
		
		addRecipe("ingotPlutonium242All", new ParticleStack(Particles.antiproton, 120000), "wasteSpallationPlutonium",
				new ParticleStack(Particles.pion_plus),new ParticleStack(Particles.pion_naught),new ParticleStack(Particles.pion_minus), 10000000, 1, 1460000);
		
		addRecipe("ingotPlutonium241All", new ParticleStack(Particles.antiproton, 120000), "wasteSpallationPlutonium",
				new ParticleStack(Particles.pion_plus),new ParticleStack(Particles.pion_naught),new ParticleStack(Particles.pion_minus), 10000000, 1, 1460000);
		
		addRecipe("ingotPlutonium239All", new ParticleStack(Particles.antiproton, 120000), "wasteSpallationPlutonium",
				new ParticleStack(Particles.pion_plus),new ParticleStack(Particles.pion_naught),new ParticleStack(Particles.pion_minus), 10000000, 1, 1460000);
		
		addRecipe("ingotPlutonium238All", new ParticleStack(Particles.antiproton, 120000), "wasteSpallationPlutonium",
				new ParticleStack(Particles.pion_plus),new ParticleStack(Particles.pion_naught),new ParticleStack(Particles.pion_minus), 10000000, 1, 1460000);
		
		addRecipe("ingotNeptunium237All", new ParticleStack(Particles.antiproton, 120000), "wasteSpallationNeptunium",
				new ParticleStack(Particles.pion_plus),new ParticleStack(Particles.pion_naught),new ParticleStack(Particles.pion_minus), 10000000, 1, 1460000);
		
		addRecipe("ingotNeptunium236All", new ParticleStack(Particles.antiproton, 120000), "wasteSpallationNeptunium",
				new ParticleStack(Particles.pion_plus),new ParticleStack(Particles.pion_naught),new ParticleStack(Particles.pion_minus), 10000000, 1, 1460000);
		
		addRecipe("ingotUranium238All", new ParticleStack(Particles.antiproton, 120000), "wasteSpallationUranium",
				new ParticleStack(Particles.pion_plus),new ParticleStack(Particles.pion_naught),new ParticleStack(Particles.pion_minus), 10000000, 1, 1460000);

		addRecipe("ingotUranium235All", new ParticleStack(Particles.antiproton, 120000), "wasteSpallationUranium",
				new ParticleStack(Particles.pion_plus),new ParticleStack(Particles.pion_naught),new ParticleStack(Particles.pion_minus), 10000000, 1, 1460000);
		
		addRecipe("ingotUranium234", new ParticleStack(Particles.antiproton, 120000), "wasteSpallationUranium",
				new ParticleStack(Particles.pion_plus),new ParticleStack(Particles.pion_naught),new ParticleStack(Particles.pion_minus), 10000000, 1, 1460000);

		addRecipe("ingotUranium233All", new ParticleStack(Particles.antiproton, 120000), "wasteSpallationUranium",
				new ParticleStack(Particles.pion_plus),new ParticleStack(Particles.pion_naught),new ParticleStack(Particles.pion_minus), 10000000, 1, 1460000);
		
		addRecipe("dustProtactinium233", new ParticleStack(Particles.antiproton, 120000), "wasteSpallationProtactinium",
				new ParticleStack(Particles.pion_plus),new ParticleStack(Particles.pion_naught),new ParticleStack(Particles.pion_minus), 10000000, 1, 1460000);
		
		addRecipe("dustProtactinium231", new ParticleStack(Particles.antiproton, 120000), "wasteSpallationProtactinium",
				new ParticleStack(Particles.pion_plus),new ParticleStack(Particles.pion_naught),new ParticleStack(Particles.pion_minus), 10000000, 1, 1460000);
		
		addRecipe("ingotThorium", new ParticleStack(Particles.antiproton, 120000), "wasteSpallationThorium",
				new ParticleStack(Particles.pion_plus),new ParticleStack(Particles.pion_naught),new ParticleStack(Particles.pion_minus), 10000000, 1, 1460000);
		
		addRecipe("dustRadium", new ParticleStack(Particles.antiproton, 120000), "wasteSpallationRadium",
				new ParticleStack(Particles.pion_plus),new ParticleStack(Particles.pion_naught),new ParticleStack(Particles.pion_minus), 10000000, 1, 1460000);
		
		addRecipe("dustPolonium", new ParticleStack(Particles.antiproton, 120000), "wasteSpallationPolonium",
				new ParticleStack(Particles.pion_plus),new ParticleStack(Particles.pion_naught),new ParticleStack(Particles.pion_minus), 10000000, 1, 1460000);
		
		addRecipe("dustBismuth", new ParticleStack(Particles.antiproton, 120000), "wasteSpallationBismuth",
				new ParticleStack(Particles.pion_plus),new ParticleStack(Particles.pion_naught),new ParticleStack(Particles.pion_minus), 10000000, 1, 1460000);
		
		addRecipe("ingotLead", new ParticleStack(Particles.antiproton, 120000), "wasteSpallationLead",
				new ParticleStack(Particles.pion_plus),new ParticleStack(Particles.pion_naught),new ParticleStack(Particles.pion_minus), 10000000, 1, 1460000);
		
		addRecipe("ingotGold", new ParticleStack(Particles.antiproton, 120000), "wasteSpallationGold",
				new ParticleStack(Particles.pion_plus),new ParticleStack(Particles.pion_naught),new ParticleStack(Particles.pion_minus), 10000000, 1, 1460000);
		
		addRecipe("ingotPlatinum", new ParticleStack(Particles.antiproton, 120000), "wasteSpallationPlatinum",
				new ParticleStack(Particles.pion_plus),new ParticleStack(Particles.pion_naught),new ParticleStack(Particles.pion_minus), 10000000, 1, 1460000);
		
		addRecipe("ingotIridium", new ParticleStack(Particles.antiproton, 120000), "wasteSpallationIridium",
				new ParticleStack(Particles.pion_plus),new ParticleStack(Particles.pion_naught),new ParticleStack(Particles.pion_minus), 10000000, 1, 1460000);
		
		addRecipe("ingotIridium192", new ParticleStack(Particles.antiproton, 120000), "wasteSpallationIridium",
				new ParticleStack(Particles.pion_plus),new ParticleStack(Particles.pion_naught),new ParticleStack(Particles.pion_minus), 10000000, 1, 1460000);
		
		addRecipe("ingotOsmium", new ParticleStack(Particles.antiproton, 120000), "wasteSpallationOsmium",
				new ParticleStack(Particles.pion_plus),new ParticleStack(Particles.pion_naught),new ParticleStack(Particles.pion_minus), 10000000, 1, 1460000);
		
		addRecipe("ingotTungsten", new ParticleStack(Particles.antiproton, 120000), "wasteSpallationTungsten",
				new ParticleStack(Particles.pion_plus),new ParticleStack(Particles.pion_naught),new ParticleStack(Particles.pion_minus), 10000000, 1, 1460000);
		
		addRecipe("ingotHafnium", new ParticleStack(Particles.antiproton, 120000), "wasteSpallationHafnium",
				new ParticleStack(Particles.pion_plus),new ParticleStack(Particles.pion_naught),new ParticleStack(Particles.pion_minus), 10000000, 1, 1460000);
		
	
		
		
		
	// antideuteron production 
		addRecipe("ingotCalifornium252", new ParticleStack(Particles.deuteron, 480000, 13100000), "wasteSpallationCalifornium", 
				new ParticleStack(Particles.deuteron), emptyParticleStack(), new ParticleStack(Particles.antideuteron), 20000000, 0.01, -13100000);
		
		addRecipe("ingotCalifornium251", new ParticleStack(Particles.deuteron, 480000, 13100000), "wasteSpallationCalifornium",
				new ParticleStack(Particles.deuteron), emptyParticleStack(), new ParticleStack(Particles.antideuteron), 20000000, 0.01, -13100000);
		
		addRecipe("ingotCalifornium250", new ParticleStack(Particles.deuteron, 480000, 13100000), "wasteSpallationCalifornium",
				new ParticleStack(Particles.deuteron), emptyParticleStack(), new ParticleStack(Particles.antideuteron), 20000000, 0.01, -13100000);
		
		addRecipe("ingotCalifornium249", new ParticleStack(Particles.deuteron, 480000, 13100000), "wasteSpallationCalifornium",
				new ParticleStack(Particles.deuteron), emptyParticleStack(), new ParticleStack(Particles.antideuteron), 20000000, 0.01, -13100000);
		
		addRecipe("ingotBerkelium248", new ParticleStack(Particles.deuteron, 480000, 13100000), "wasteSpallationBerkelium",
				new ParticleStack(Particles.deuteron), emptyParticleStack(), new ParticleStack(Particles.antideuteron), 20000000, 0.01, -13100000);
		
		addRecipe("ingotBerkelium247", new ParticleStack(Particles.deuteron, 480000, 13100000), "wasteSpallationBerkelium",
				new ParticleStack(Particles.deuteron), emptyParticleStack(), new ParticleStack(Particles.antideuteron), 20000000, 0.01, -13100000);
		
		addRecipe("ingotCurium247", new ParticleStack(Particles.deuteron, 480000, 13100000), "wasteSpallationCurium",
				new ParticleStack(Particles.deuteron), emptyParticleStack(), new ParticleStack(Particles.antideuteron), 20000000, 0.01, -13100000);
		
		addRecipe("ingotCurium246", new ParticleStack(Particles.deuteron, 480000, 13100000), "wasteSpallationCurium",
				new ParticleStack(Particles.deuteron), emptyParticleStack(), new ParticleStack(Particles.antideuteron), 20000000, 0.01, -13100000);
		
		addRecipe("ingotCurium245", new ParticleStack(Particles.deuteron, 480000, 13100000), "wasteSpallationCurium",
				new ParticleStack(Particles.deuteron), emptyParticleStack(), new ParticleStack(Particles.antideuteron), 20000000, 0.01, -13100000);
		
		addRecipe("ingotCurium243", new ParticleStack(Particles.deuteron, 480000, 13100000), "wasteSpallationCurium",
				new ParticleStack(Particles.deuteron), emptyParticleStack(), new ParticleStack(Particles.antideuteron), 20000000, 0.01, -13100000);
		
		addRecipe("ingotAmericium243", new ParticleStack(Particles.deuteron, 480000, 13100000), "wasteSpallationAmericium",
				new ParticleStack(Particles.deuteron), emptyParticleStack(), new ParticleStack(Particles.antideuteron), 20000000, 0.01, -13100000);
		
		addRecipe("ingotAmericium242", new ParticleStack(Particles.deuteron, 480000, 13100000), "wasteSpallationAmericium",
				new ParticleStack(Particles.deuteron), emptyParticleStack(), new ParticleStack(Particles.antideuteron), 20000000, 0.01, -13100000);
		
		addRecipe("ingotAmericium241", new ParticleStack(Particles.deuteron, 480000, 13100000), "wasteSpallationAmericium",
				new ParticleStack(Particles.deuteron), emptyParticleStack(), new ParticleStack(Particles.antideuteron), 20000000, 0.01, -13100000);
		
		addRecipe("ingotPlutonium242", new ParticleStack(Particles.deuteron, 480000, 13100000), "wasteSpallationPlutonium",
				new ParticleStack(Particles.deuteron), emptyParticleStack(), new ParticleStack(Particles.antideuteron), 20000000, 0.01, -13100000);
		
		addRecipe("ingotPlutonium241", new ParticleStack(Particles.deuteron, 480000, 13100000), "wasteSpallationPlutonium",
				new ParticleStack(Particles.deuteron), emptyParticleStack(), new ParticleStack(Particles.antideuteron), 20000000, 0.01, -13100000);
		
		addRecipe("ingotPlutonium239", new ParticleStack(Particles.deuteron, 480000, 13100000), "wasteSpallationPlutonium",
				new ParticleStack(Particles.deuteron), emptyParticleStack(), new ParticleStack(Particles.antideuteron), 20000000, 0.01, -13100000);
		
		addRecipe("ingotPlutonium238", new ParticleStack(Particles.deuteron, 480000, 13100000), "wasteSpallationPlutonium",
				new ParticleStack(Particles.deuteron), emptyParticleStack(), new ParticleStack(Particles.antideuteron), 20000000, 0.01, -13100000);
		
		addRecipe("ingotNeptunium237", new ParticleStack(Particles.deuteron, 480000, 13100000), "wasteSpallationNeptunium",
				new ParticleStack(Particles.deuteron), emptyParticleStack(), new ParticleStack(Particles.antideuteron), 20000000, 0.01, -13100000);
		
		addRecipe("ingotNeptunium236", new ParticleStack(Particles.deuteron, 480000, 13100000), "wasteSpallationNeptunium",
				new ParticleStack(Particles.deuteron), emptyParticleStack(), new ParticleStack(Particles.antideuteron), 20000000, 0.01, -13100000);
		
		addRecipe("ingotUranium238", new ParticleStack(Particles.deuteron, 480000, 13100000), "wasteSpallationUranium",
				new ParticleStack(Particles.deuteron), emptyParticleStack(), new ParticleStack(Particles.antideuteron), 20000000, 0.01, -13100000);
		
		addRecipe("ingotUranium235", new ParticleStack(Particles.deuteron, 480000, 13100000), "wasteSpallationUranium",
				new ParticleStack(Particles.deuteron), emptyParticleStack(), new ParticleStack(Particles.antideuteron), 20000000, 0.01, -13100000);
		
		addRecipe("ingotUranium234", new ParticleStack(Particles.deuteron, 480000, 13100000), "wasteSpallationUranium",
				new ParticleStack(Particles.deuteron), emptyParticleStack(), new ParticleStack(Particles.antideuteron), 20000000, 0.01, -13100000);
		
		addRecipe("ingotUranium233", new ParticleStack(Particles.deuteron, 480000, 13100000), "wasteSpallationUranium",
				new ParticleStack(Particles.deuteron), emptyParticleStack(), new ParticleStack(Particles.antideuteron), 20000000, 0.01, -13100000);
		
		addRecipe("dustProtactinium233", new ParticleStack(Particles.deuteron, 480000, 13100000), "wasteSpallationProtactinium",
				new ParticleStack(Particles.deuteron), emptyParticleStack(), new ParticleStack(Particles.antideuteron), 20000000, 0.01, -13100000);
		
		addRecipe("dustProtactinium231", new ParticleStack(Particles.deuteron, 480000, 13100000), "wasteSpallationProtactinium",
				new ParticleStack(Particles.deuteron), emptyParticleStack(), new ParticleStack(Particles.antideuteron), 20000000, 0.01, -13100000);
		
		addRecipe("ingotThorium", new ParticleStack(Particles.deuteron, 480000, 13100000), "wasteSpallationThorium",
				new ParticleStack(Particles.deuteron), emptyParticleStack(), new ParticleStack(Particles.antideuteron), 20000000, 0.01, -13100000);
		
		addRecipe("dustRadium", new ParticleStack(Particles.deuteron, 480000, 13100000), "wasteSpallationRadium",
				new ParticleStack(Particles.deuteron), emptyParticleStack(), new ParticleStack(Particles.antideuteron), 20000000, 0.01, -13100000);
		
		addRecipe("dustPolonium", new ParticleStack(Particles.deuteron, 480000, 13100000), "wasteSpallationPolonium",
				new ParticleStack(Particles.deuteron), emptyParticleStack(), new ParticleStack(Particles.antideuteron), 20000000, 0.01, -13100000);
		
		addRecipe("dustBismuth", new ParticleStack(Particles.deuteron, 480000, 13100000), "wasteSpallationBismuth",
				new ParticleStack(Particles.deuteron), emptyParticleStack(), new ParticleStack(Particles.antideuteron), 20000000, 0.01, -13100000);
		
		addRecipe("ingotLead", new ParticleStack(Particles.deuteron, 480000, 13100000), "wasteSpallationLead",
				new ParticleStack(Particles.deuteron), emptyParticleStack(), new ParticleStack(Particles.antideuteron), 20000000, 0.01, -13100000);
		
		addRecipe("ingotGold", new ParticleStack(Particles.deuteron, 480000, 13100000), "wasteSpallationGold",
				new ParticleStack(Particles.deuteron), emptyParticleStack(), new ParticleStack(Particles.antideuteron), 20000000, 0.01, -13100000);
		
		addRecipe("ingotPlatinum", new ParticleStack(Particles.deuteron, 480000, 13100000), "wasteSpallationPlatinum",
				new ParticleStack(Particles.deuteron), emptyParticleStack(), new ParticleStack(Particles.antideuteron), 20000000, 0.01, -13100000);
		
		addRecipe("ingotIridium", new ParticleStack(Particles.deuteron, 480000, 13100000), "wasteSpallationIridium",
				new ParticleStack(Particles.deuteron), emptyParticleStack(), new ParticleStack(Particles.antideuteron), 20000000, 0.01, -13100000);
		
		addRecipe("ingotIridium192", new ParticleStack(Particles.deuteron, 480000, 13100000), "wasteSpallationIridium",
				new ParticleStack(Particles.deuteron), emptyParticleStack(), new ParticleStack(Particles.antideuteron), 20000000, 0.01, -13100000);
	
		addRecipe("ingotOsmium", new ParticleStack(Particles.deuteron, 480000, 13100000), "wasteSpallationOsmium",
				new ParticleStack(Particles.deuteron), emptyParticleStack(), new ParticleStack(Particles.antideuteron), 20000000, 0.01, -13100000);
		
		addRecipe("ingotTungsten", new ParticleStack(Particles.deuteron, 480000, 13100000), "wasteSpallationTungsten",
				new ParticleStack(Particles.deuteron), emptyParticleStack(), new ParticleStack(Particles.antideuteron), 20000000, 0.01, -13100000);
		
		addRecipe("ingotHafnium", new ParticleStack(Particles.deuteron, 480000, 13100000), "wasteSpallationHafnium",
				new ParticleStack(Particles.deuteron), emptyParticleStack(), new ParticleStack(Particles.antideuteron), 20000000, 0.01, -13100000);
		
	
		// antideuteron anhilation
		
				addRecipe("ingotCalifornium252All", new ParticleStack(Particles.antideuteron, 120000), "wasteSpallationCalifornium",
						new ParticleStack(Particles.pion_plus,4),new ParticleStack(Particles.pion_naught,4),new ParticleStack(Particles.pion_minus,4), 10000000, 1, 2090000);
				
				addRecipe("ingotCalifornium251All", new ParticleStack(Particles.antideuteron, 120000), "wasteSpallationCalifornium",
						new ParticleStack(Particles.pion_plus,4),new ParticleStack(Particles.pion_naught,4),new ParticleStack(Particles.pion_minus,4), 10000000, 1, 2090000);
				
				addRecipe("ingotCalifornium250All", new ParticleStack(Particles.antideuteron, 120000), "wasteSpallationCalifornium",
						new ParticleStack(Particles.pion_plus,4),new ParticleStack(Particles.pion_naught,4),new ParticleStack(Particles.pion_minus,4), 10000000, 1, 2090000);
				
				addRecipe("ingotCalifornium249All", new ParticleStack(Particles.antideuteron, 120000), "wasteSpallationCalifornium",
						new ParticleStack(Particles.pion_plus,4),new ParticleStack(Particles.pion_naught,4),new ParticleStack(Particles.pion_minus,4), 10000000, 1, 2090000);
				
				addRecipe("ingotBerkelium248All", new ParticleStack(Particles.antideuteron, 120000), "wasteSpallationBerkelium",
						new ParticleStack(Particles.pion_plus,4),new ParticleStack(Particles.pion_naught,4),new ParticleStack(Particles.pion_minus,4), 10000000, 1, 2090000);
				
				addRecipe("ingotBerkelium247All", new ParticleStack(Particles.antideuteron, 120000), "wasteSpallationBerkelium",
						new ParticleStack(Particles.pion_plus,4),new ParticleStack(Particles.pion_naught,4),new ParticleStack(Particles.pion_minus,4), 10000000, 1, 2090000);
				
				addRecipe("ingotCurium247All", new ParticleStack(Particles.antideuteron, 120000), "wasteSpallationCurium",
						new ParticleStack(Particles.pion_plus,4),new ParticleStack(Particles.pion_naught,4),new ParticleStack(Particles.pion_minus,4), 10000000, 1, 2090000);
				
				addRecipe("ingotCurium246All", new ParticleStack(Particles.antideuteron, 120000), "wasteSpallationCurium",
						new ParticleStack(Particles.pion_plus,4),new ParticleStack(Particles.pion_naught,4),new ParticleStack(Particles.pion_minus,4), 10000000, 1, 2090000);
				
				addRecipe("ingotCurium245All", new ParticleStack(Particles.antideuteron, 120000), "wasteSpallationCurium",
						new ParticleStack(Particles.pion_plus,4),new ParticleStack(Particles.pion_naught,4),new ParticleStack(Particles.pion_minus,4), 10000000, 1, 2090000);
				
				addRecipe("ingotCurium243All", new ParticleStack(Particles.antideuteron, 120000), "wasteSpallationCurium",
						new ParticleStack(Particles.pion_plus,4),new ParticleStack(Particles.pion_naught,4),new ParticleStack(Particles.pion_minus,4), 10000000, 1, 2090000);
				
				addRecipe("ingotAmericium243All", new ParticleStack(Particles.antideuteron, 120000), "wasteSpallationAmericium",
						new ParticleStack(Particles.pion_plus,4),new ParticleStack(Particles.pion_naught,4),new ParticleStack(Particles.pion_minus,4), 10000000, 1, 2090000);
				
				addRecipe("ingotAmericium242All", new ParticleStack(Particles.antideuteron, 120000), "wasteSpallationAmericium",
						new ParticleStack(Particles.pion_plus,4),new ParticleStack(Particles.pion_naught,4),new ParticleStack(Particles.pion_minus,4), 10000000, 1, 2090000);
				
				addRecipe("ingotAmericium241All", new ParticleStack(Particles.antideuteron, 120000), "wasteSpallationAmericium",
						new ParticleStack(Particles.pion_plus,4),new ParticleStack(Particles.pion_naught,4),new ParticleStack(Particles.pion_minus,4), 10000000, 1, 2090000);
				
				addRecipe("ingotPlutonium242All", new ParticleStack(Particles.antideuteron, 120000), "wasteSpallationPlutonium",
						new ParticleStack(Particles.pion_plus,4),new ParticleStack(Particles.pion_naught,4),new ParticleStack(Particles.pion_minus,4), 10000000, 1, 2090000);
				
				addRecipe("ingotPlutonium241All", new ParticleStack(Particles.antideuteron, 120000), "wasteSpallationPlutonium",
						new ParticleStack(Particles.pion_plus,4),new ParticleStack(Particles.pion_naught,4),new ParticleStack(Particles.pion_minus,4), 10000000, 1, 2090000);
				
				addRecipe("ingotPlutonium239All", new ParticleStack(Particles.antideuteron, 120000), "wasteSpallationPlutonium",
						new ParticleStack(Particles.pion_plus,4),new ParticleStack(Particles.pion_naught,4),new ParticleStack(Particles.pion_minus,4), 10000000, 1, 2090000);
				
				addRecipe("ingotPlutonium238All", new ParticleStack(Particles.antideuteron, 120000), "wasteSpallationPlutonium",
						new ParticleStack(Particles.pion_plus,4),new ParticleStack(Particles.pion_naught,4),new ParticleStack(Particles.pion_minus,4), 10000000, 1, 2090000);
				
				addRecipe("ingotNeptunium237All", new ParticleStack(Particles.antideuteron, 120000), "wasteSpallationNeptunium",
						new ParticleStack(Particles.pion_plus,4),new ParticleStack(Particles.pion_naught,4),new ParticleStack(Particles.pion_minus,4), 10000000, 1, 2090000);
				
				addRecipe("ingotNeptunium236All", new ParticleStack(Particles.antideuteron, 120000), "wasteSpallationNeptunium",
						new ParticleStack(Particles.pion_plus,4),new ParticleStack(Particles.pion_naught,4),new ParticleStack(Particles.pion_minus,4), 10000000, 1, 2090000);
				
				addRecipe("ingotUranium238All", new ParticleStack(Particles.antideuteron, 120000), "wasteSpallationUranium",
						new ParticleStack(Particles.pion_plus,4),new ParticleStack(Particles.pion_naught,4),new ParticleStack(Particles.pion_minus,4), 10000000, 1, 2090000);

				addRecipe("ingotUranium235All", new ParticleStack(Particles.antideuteron, 120000), "wasteSpallationUranium",
						new ParticleStack(Particles.pion_plus,4),new ParticleStack(Particles.pion_naught,4),new ParticleStack(Particles.pion_minus,4), 10000000, 1, 2090000);
				
				addRecipe("ingotUranium234", new ParticleStack(Particles.antideuteron, 120000), "wasteSpallationUranium",
						new ParticleStack(Particles.pion_plus,4),new ParticleStack(Particles.pion_naught,4),new ParticleStack(Particles.pion_minus,4), 10000000, 1, 2090000);

				addRecipe("ingotUranium233All", new ParticleStack(Particles.antideuteron, 120000), "wasteSpallationUranium",
						new ParticleStack(Particles.pion_plus,4),new ParticleStack(Particles.pion_naught,4),new ParticleStack(Particles.pion_minus,4), 10000000, 1, 2090000);
				
				addRecipe("dustProtactinium233", new ParticleStack(Particles.antideuteron, 120000), "wasteSpallationProtactinium",
						new ParticleStack(Particles.pion_plus,4),new ParticleStack(Particles.pion_naught,4),new ParticleStack(Particles.pion_minus,4), 10000000, 1, 2090000);
				
				addRecipe("dustProtactinium231", new ParticleStack(Particles.antideuteron, 120000), "wasteSpallationProtactinium",
						new ParticleStack(Particles.pion_plus,4),new ParticleStack(Particles.pion_naught,4),new ParticleStack(Particles.pion_minus,4), 10000000, 1, 2090000);
				
				addRecipe("ingotThorium", new ParticleStack(Particles.antideuteron, 120000), "wasteSpallationThorium",
						new ParticleStack(Particles.pion_plus,4),new ParticleStack(Particles.pion_naught,4),new ParticleStack(Particles.pion_minus,4), 10000000, 1, 2090000);
				
				addRecipe("dustRadium", new ParticleStack(Particles.antideuteron, 120000), "wasteSpallationRadium",
						new ParticleStack(Particles.pion_plus,4),new ParticleStack(Particles.pion_naught,4),new ParticleStack(Particles.pion_minus,4), 10000000, 1, 2090000);
				
				addRecipe("dustPolonium", new ParticleStack(Particles.antideuteron, 120000), "wasteSpallationPolonium",
						new ParticleStack(Particles.pion_plus,4),new ParticleStack(Particles.pion_naught,4),new ParticleStack(Particles.pion_minus,4), 10000000, 1, 2090000);
				
				addRecipe("dustBismuth", new ParticleStack(Particles.antideuteron, 120000), "wasteSpallationBismuth",
						new ParticleStack(Particles.pion_plus,4),new ParticleStack(Particles.pion_naught,4),new ParticleStack(Particles.pion_minus,4), 10000000, 1, 2090000);
				
				addRecipe("ingotLead", new ParticleStack(Particles.antideuteron, 120000), "wasteSpallationLead",
						new ParticleStack(Particles.pion_plus,4),new ParticleStack(Particles.pion_naught,4),new ParticleStack(Particles.pion_minus,4), 10000000, 1, 2090000);
				
				addRecipe("ingotGold", new ParticleStack(Particles.antideuteron, 120000), "wasteSpallationGold",
						new ParticleStack(Particles.pion_plus,4),new ParticleStack(Particles.pion_naught,4),new ParticleStack(Particles.pion_minus,4), 10000000, 1, 2090000);
				
				addRecipe("ingotPlatinum", new ParticleStack(Particles.antideuteron, 120000), "wasteSpallationPlatinum",
						new ParticleStack(Particles.pion_plus,4),new ParticleStack(Particles.pion_naught,4),new ParticleStack(Particles.pion_minus,4), 10000000, 1, 2090000);
				
				addRecipe("ingotIridium", new ParticleStack(Particles.antideuteron, 120000), "wasteSpallationIridium",
						new ParticleStack(Particles.pion_plus,4),new ParticleStack(Particles.pion_naught,4),new ParticleStack(Particles.pion_minus,4), 10000000, 1, 2090000);
				
				addRecipe("ingotIridium192", new ParticleStack(Particles.antideuteron, 120000), "wasteSpallationIridium",
						new ParticleStack(Particles.pion_plus,4),new ParticleStack(Particles.pion_naught,4),new ParticleStack(Particles.pion_minus,4), 10000000, 1, 2090000);
				
				addRecipe("ingotOsmium", new ParticleStack(Particles.antideuteron, 120000), "wasteSpallationOsmium",
						new ParticleStack(Particles.pion_plus,4),new ParticleStack(Particles.pion_naught,4),new ParticleStack(Particles.pion_minus,4), 10000000, 1, 2090000);
				
				addRecipe("ingotTungsten", new ParticleStack(Particles.antideuteron, 120000), "wasteSpallationTungsten",
						new ParticleStack(Particles.pion_plus,4),new ParticleStack(Particles.pion_naught,4),new ParticleStack(Particles.pion_minus,4), 10000000, 1, 2090000);
				
				addRecipe("ingotHafnium", new ParticleStack(Particles.antideuteron, 120000), "wasteSpallationHafnium",
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
