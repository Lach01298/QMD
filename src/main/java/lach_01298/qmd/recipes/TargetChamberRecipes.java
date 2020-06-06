package lach_01298.qmd.recipes;

import java.util.ArrayList;
import java.util.List;

import lach_01298.qmd.QMDRadSources;
import lach_01298.qmd.particle.ParticleStack;
import lach_01298.qmd.particle.Particles;
import lach_01298.qmd.recipe.QMDRecipeHandler;
import nc.radiation.RadSources;

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
		addRecipe("ingotBeryllium",new ParticleStack(Particles.proton, 120000, 1000), "ingotLithium6",
				new ParticleStack(Particles.alpha), emptyParticleStack(), emptyParticleStack(), 7500, 0.34, 2125);
				

		
		addRecipe("ingotBeryllium", new ParticleStack(Particles.proton, 1200000, 29000), "ingotBoron10",
				emptyParticleStack(), new ParticleStack(Particles.photon), emptyParticleStack(), 31000, 0.01, 6585);
	
		
		addRecipe("ingotBoron10", new ParticleStack(Particles.proton, 180000, 4000),"ingotBeryllium7",
				new ParticleStack(Particles.alpha), emptyParticleStack(), emptyParticleStack(),6000, 0.3, 1144);
		
		addRecipe("ingotBoron11", new ParticleStack(Particles.proton, 1200000, 15000), "dustGraphite",
				emptyParticleStack(), new ParticleStack(Particles.photon), emptyParticleStack(), 17000, 0.01, 15956);
		

		addRecipe("dustGraphite", new ParticleStack(Particles.proton, 840000, 45000), "ingotBeryllium7",
				new ParticleStack(Particles.alpha), new ParticleStack(Particles.neutron), new ParticleStack(Particles.proton), 50000, 0.06, -26266);

	
		addRecipe("dustGraphite", new ParticleStack(Particles.proton, 180000, 15000), "ingotBeryllium",
				new ParticleStack(Particles.alpha), emptyParticleStack(), emptyParticleStack(), 17000, 0.28, -7551);
		
		addRecipe("ingotSodium", new ParticleStack(Particles.proton, 180000, 24000), "ingotSodium22",
				new ParticleStack(Particles.proton), new ParticleStack(Particles.neutron), emptyParticleStack(), 26000,  0.28, -12418);
		
		addRecipe("ingotMagnesium24", new ParticleStack(Particles.proton, 360000 , 35000), "ingotSodium22",
				new ParticleStack(Particles.proton, 2), new ParticleStack(Particles.neutron), emptyParticleStack(), 50000, 0.14, -24111);
		
		addRecipe("ingotAluminum",  new ParticleStack(Particles.proton, 960000, 45000), "ingotSodium22",
				new ParticleStack(Particles.alpha), new ParticleStack(Particles.neutron), new ParticleStack(Particles.proton), 50000, 0.05, -22510);

		addRecipe("ingotManganese", new ParticleStack(Particles.proton, 1200000, 10000), "ingotIron",
				emptyParticleStack(), new ParticleStack(Particles.photon), emptyParticleStack(), 15000, 0.01, 10183);
		
		addRecipe("ingotUranium238", new ParticleStack(Particles.proton, 180000, 110000), "dustProtactinium233",
				new ParticleStack(Particles.alpha), new ParticleStack(Particles.neutron, 2), emptyParticleStack(), 120000, 0.27, -1459);
		
		addRecipe("ingotUranium238", new ParticleStack(Particles.proton, 120000, 19000), "ingotNeptunium236",
				new ParticleStack(Particles.triton), emptyParticleStack(), emptyParticleStack(), 23000, 0.44, -12996);
		
		addRecipe("ingotUranium235", new ParticleStack(Particles.proton, 480000, 65000), "ingotUranium233",
				new ParticleStack(Particles.triton), emptyParticleStack(), emptyParticleStack(), 75000, 0.11, -3660);
		
		
		
		//deuteron reactions
		addRecipe("ingotBeryllium", new ParticleStack(Particles.deuteron, 180000, 4000), "ingotLithium7",
				new ParticleStack(Particles.alpha), emptyParticleStack(), emptyParticleStack(), 5000, 0.3, 7152);
		
		addRecipe("ingotBoron11", new ParticleStack(Particles.deuteron,  180000, 2000), "dustGraphite",
				emptyParticleStack(), new ParticleStack(Particles.neutron), emptyParticleStack(), 4000, 0.24, 13732);
		
		addRecipe("dustGraphite", new ParticleStack(Particles.deuteron, 180000, 5000), "ingotBoron10",
				new ParticleStack(Particles.alpha), emptyParticleStack(), emptyParticleStack(), 15000, 0.28, -1339);
		
		addRecipe("ingotMagnesium24",new ParticleStack(Particles.deuteron, 300000, 8000), "ingotSodium22",
				new ParticleStack(Particles.alpha), emptyParticleStack(), emptyParticleStack(), 10000, 0.18, 1958);
		
		addRecipe("ingotPlutonium242", new ParticleStack(Particles.deuteron,180000,11000), "ingotAmericium242",
				emptyParticleStack(), new ParticleStack(Particles.neutron, 2), emptyParticleStack(), 16000,  0.25, -3758);
		

		
		// photon reactions
		addRecipe("ingotMagnesium26", new ParticleStack(Particles.photon, 240000, 25000), "ingotMagnesium24",
				emptyParticleStack(), new ParticleStack(Particles.neutron, 2), emptyParticleStack(), 30000, 0.2, -18423);
		
		addRecipe("itemSilicon", new ParticleStack(Particles.photon,120000, 19500), "ingotAluminum",
				new ParticleStack(Particles.proton), emptyParticleStack(), emptyParticleStack(), 20500, 0.4,-11585);
		

		
		
		// neutron reactions
		addRecipe("ingotPlutonium242", new ParticleStack(Particles.neutron, 40000, 10000), "ingotPlutonium241",
				emptyParticleStack(), new ParticleStack(Particles.neutron, 2), emptyParticleStack(), 12000, 1);

		addRecipe("ingotNeptunium237",new ParticleStack(Particles.neutron, 80000, 10000), "ingotNeptunium236",
				emptyParticleStack(), new ParticleStack(Particles.neutron, 2), emptyParticleStack(), 14000, 0.67);
		
		addRecipe("ingotUranium238", new ParticleStack(Particles.neutron, 90000, 20000), "ingotUranium235",
				emptyParticleStack(), new ParticleStack(Particles.neutron, 4), emptyParticleStack(), 21000, 0.54, -17825 );
		
		addRecipe("ingotUranium235", new ParticleStack(Particles.neutron, 180000, 11000), "ingotUranium233",
				emptyParticleStack(), new ParticleStack(Particles.neutron, 3), emptyParticleStack(), 15000,  0.28);	
		

		
		// triton reactions
		addRecipe("ingotBeryllium", new ParticleStack(Particles.triton, 120000, 4000), "ingotBoron11",
				emptyParticleStack(), new ParticleStack(Particles.neutron), emptyParticleStack(), 7000, 0.47, 9559);	
		
		
		// alpha reactions
		addRecipe("ingotLithium7", new ParticleStack(Particles.alpha, 180000, 7500), "ingotBoron10",
				emptyParticleStack(), new ParticleStack(Particles.neutron), emptyParticleStack(), 15000, 0.3, -2790);	
		
		addRecipe("ingotBeryllium", new ParticleStack(Particles.alpha, 90000, 7500), "dustGraphite",
				emptyParticleStack(), new ParticleStack(Particles.neutron), emptyParticleStack(), 8000, 0.5, 5702);	
			
		addRecipe("dustGraphite", new ParticleStack(Particles.alpha, 420000, 35000), "ingotBoron11",
				new ParticleStack(Particles.alpha), emptyParticleStack(),new ParticleStack(Particles.proton), 45000, 0.12, -15956);	
		
		addRecipe("ingotAluminum", new ParticleStack(Particles.alpha, 300000, 4000), "itemSilicon",
				new ParticleStack(Particles.positron), new ParticleStack(Particles.neutron), new ParticleStack(Particles.electron_neutrino), 6000, 0.4);	
		
		// Other reactions
		addRecipe("siliconWafer", new ParticleStack(Particles.boron_ion, 120000, 600,2), "siliconPDoped",
				emptyParticleStack(),emptyParticleStack(),emptyParticleStack(), 1000);
		
	
		// proton induced fission
		addRecipe("ingotAmericium241", new ParticleStack(Particles.proton, 120000, 50000), "ingotNeodymium",
				emptyParticleStack(), new ParticleStack(Particles.neutron, 2), emptyParticleStack(), 95000, 1, -1000);
		
		addRecipe("ingotPlutonium242", new ParticleStack(Particles.proton, 120000, 65000), "ingotZirconium", 
				emptyParticleStack(), new ParticleStack(Particles.neutron, 2), emptyParticleStack(), 100000, 0.9, -1000);
		
		addRecipe("ingotPlutonium238", new ParticleStack(Particles.proton, 120000, 45000), "ingotPromethium147",
				emptyParticleStack(), new ParticleStack(Particles.neutron, 2), emptyParticleStack(), 100000, 0.9, -1000);
		
		addRecipe("ingotNeptunium237", new ParticleStack(Particles.proton, 120000, 45000), "ingotYttrium",
				emptyParticleStack(), new ParticleStack(Particles.neutron, 2), emptyParticleStack(), 100000, 0.8, -1000);
		
		addRecipe("ingotUranium238", new ParticleStack(Particles.proton, 120000, 45000), "ingotZirconium", 
				emptyParticleStack(), new ParticleStack(Particles.neutron, 2), emptyParticleStack(), 100000, 0.7, -1000);
		
		addRecipe("ingotUranium233", new ParticleStack(Particles.proton, 120000, 45000), "ingotStrontium90",
				emptyParticleStack(), new ParticleStack(Particles.neutron, 2), emptyParticleStack(), 100000, 0.7, -1000);
		
		addRecipe("ingotThorium", new ParticleStack(Particles.proton, 120000, 45000), "ingotStrontium",
				emptyParticleStack(), new ParticleStack(Particles.neutron, 2), emptyParticleStack(), 100000, 0.5, -1000);
		
		
		// spallation reactions
		addRecipe("ingotCalifornium252", new ParticleStack(Particles.proton, 1200000, 6575000), "ingotUranium234", 
				new ParticleStack(Particles.proton), emptyParticleStack(), new ParticleStack(Particles.antiproton), 2*6575000, 0.3, -6575000, RadSources.CALIFORNIUM_252);
		
		addRecipe("ingotCalifornium251", new ParticleStack(Particles.proton, 1200000, 6575000), "ingotUranium233",
				new ParticleStack(Particles.proton), emptyParticleStack(), new ParticleStack(Particles.antiproton), 2*6575000, 0.3, -6575000, RadSources.CALIFORNIUM_251);
		
		addRecipe("ingotCalifornium250", new ParticleStack(Particles.proton, 1200000, 6575000), "ingotThorium",
				new ParticleStack(Particles.proton), emptyParticleStack(), new ParticleStack(Particles.antiproton), 2*6575000, 0.3, -6575000, RadSources.CALIFORNIUM_250);
		
		addRecipe("ingotCalifornium249", new ParticleStack(Particles.proton, 1200000, 6575000), "dustProtactinium231",
				new ParticleStack(Particles.proton), emptyParticleStack(), new ParticleStack(Particles.antiproton), 2*6575000, 0.3, -6575000, RadSources.CALIFORNIUM_249);
		
		addRecipe("ingotBerkelium248", new ParticleStack(Particles.proton, 1200000, 6575000), "dustRadium",
				new ParticleStack(Particles.proton), emptyParticleStack(), new ParticleStack(Particles.antiproton), 2*6575000, 0.2, -6575000, RadSources.BERKELIUM_248);
		
		addRecipe("ingotBerkelium247", new ParticleStack(Particles.proton, 1200000, 6575000), "dustBismuth",
				new ParticleStack(Particles.proton), emptyParticleStack(), new ParticleStack(Particles.antiproton), 2*6575000, 0.2, -6575000, RadSources.BERKELIUM_247);
		
		addRecipe("ingotCurium247", new ParticleStack(Particles.proton, 1200000, 6575000), "dustBismuth",
				new ParticleStack(Particles.proton), emptyParticleStack(), new ParticleStack(Particles.antiproton), 2*6575000, 0.1, -6575000, RadSources.CURIUM_247);
		
		addRecipe("ingotCurium246", new ParticleStack(Particles.proton, 1200000, 6575000), "ingotLead",
				new ParticleStack(Particles.proton), emptyParticleStack(), new ParticleStack(Particles.antiproton), 2*6575000, 0.1, -6575000, RadSources.CURIUM_246);
		
		addRecipe("ingotCurium245", new ParticleStack(Particles.proton, 1200000, 6575000), "ingotLead",
				new ParticleStack(Particles.proton), emptyParticleStack(), new ParticleStack(Particles.antiproton), 2*6575000, 0.1, -6575000, RadSources.CURIUM_245);
		
		addRecipe("ingotCurium243", new ParticleStack(Particles.proton, 1200000, 6575000), "dustBismuth",
				new ParticleStack(Particles.proton), emptyParticleStack(), new ParticleStack(Particles.antiproton), 2*6575000, 0.1, -6575000, RadSources.CURIUM_243);
		
		addRecipe("ingotAmericium243", new ParticleStack(Particles.proton, 1200000, 6575000), "dustBismuth",
				new ParticleStack(Particles.proton), emptyParticleStack(), new ParticleStack(Particles.antiproton), 2*6575000, 0.09, -6575000, RadSources.AMERICIUM_243);
		
		addRecipe("ingotAmericium242", new ParticleStack(Particles.proton, 1200000, 6575000), "ingotLead",
				new ParticleStack(Particles.proton), emptyParticleStack(), new ParticleStack(Particles.antiproton), 2*6575000, 0.09, -6575000, RadSources.AMERICIUM_242);
		
		addRecipe("ingotAmericium241", new ParticleStack(Particles.proton, 1200000, 6575000), "ingotLead",
				new ParticleStack(Particles.proton), emptyParticleStack(), new ParticleStack(Particles.antiproton), 2*6575000, 0.09, -6575000, RadSources.AMERICIUM_241);
		
		addRecipe("ingotPlutonium242", new ParticleStack(Particles.proton, 1200000, 6575000), "ingotLead",
				new ParticleStack(Particles.proton), emptyParticleStack(), new ParticleStack(Particles.antiproton), 2*6575000, 0.08, -6575000, RadSources.PLUTONIUM_242);
		
		addRecipe("ingotPlutonium241", new ParticleStack(Particles.proton, 1200000, 6575000), "ingotLead",
				new ParticleStack(Particles.proton), emptyParticleStack(), new ParticleStack(Particles.antiproton), 2*6575000, 0.08, -6575000, RadSources.PLUTONIUM_241);
		
		addRecipe("ingotPlutonium239", new ParticleStack(Particles.proton, 1200000, 6575000), "dustBismuth",
				new ParticleStack(Particles.proton), emptyParticleStack(), new ParticleStack(Particles.antiproton), 2*6575000, 0.08, -6575000, RadSources.PLUTONIUM_239);
		
		addRecipe("ingotPlutonium238", new ParticleStack(Particles.proton, 1200000, 6575000), "ingotLead",
				new ParticleStack(Particles.proton), emptyParticleStack(), new ParticleStack(Particles.antiproton), 2*6575000, 0.08, -6575000, RadSources.PLUTONIUM_238);
		
		addRecipe("ingotNeptunium237", new ParticleStack(Particles.proton, 1200000, 6575000), "ingotLead",
				new ParticleStack(Particles.proton), emptyParticleStack(), new ParticleStack(Particles.antiproton), 2*6575000, 0.07, -6575000, RadSources.NEPTUNIUM_237);
		
		addRecipe("ingotNeptunium236", new ParticleStack(Particles.proton, 1200000, 6575000), "ingotLead210",
				new ParticleStack(Particles.proton), emptyParticleStack(), new ParticleStack(Particles.antiproton), 2*6575000, 0.07, -6575000, RadSources.NEPTUNIUM_236);
		
		addRecipe("ingotUranium238", new ParticleStack(Particles.proton, 1200000, 6575000), "ingotLead",
				new ParticleStack(Particles.proton), emptyParticleStack(), new ParticleStack(Particles.antiproton), 2*6575000, 0.06, -6575000, RadSources.URANIUM_238);
		
		addRecipe("ingotUranium235", new ParticleStack(Particles.proton, 1200000, 6575000), "dustBismuth",
				new ParticleStack(Particles.proton), emptyParticleStack(), new ParticleStack(Particles.antiproton), 2*6575000, 0.06, -6575000, RadSources.URANIUM_235);
		
		addRecipe("ingotUranium233", new ParticleStack(Particles.proton, 1200000, 6575000), "ingotLead",
				new ParticleStack(Particles.proton), emptyParticleStack(), new ParticleStack(Particles.antiproton), 2*6575000, 0.05, -6575000, RadSources.URANIUM_233);
		
		addRecipe("ingotThorium", new ParticleStack(Particles.proton, 1200000, 6575000), "dustPolonium",
				new ParticleStack(Particles.proton), emptyParticleStack(), new ParticleStack(Particles.antiproton), 2*6575000, 0.04, -6575000, RadSources.THORIUM);
		
		addRecipe("dustBismuth", new ParticleStack(Particles.proton, 1200000, 6575000), "ingotIridium",
				new ParticleStack(Particles.proton), emptyParticleStack(), new ParticleStack(Particles.antiproton), 2*6575000, 0.04, -6575000);
		
		addRecipe("ingotLead210", new ParticleStack(Particles.proton, 1200000, 6575000), "ingotIridium192",
				new ParticleStack(Particles.proton), emptyParticleStack(), new ParticleStack(Particles.antiproton), 2*6575000, 0.04, -6575000, QMDRadSources.LEAD_210);
		
		addRecipe("ingotLead", new ParticleStack(Particles.proton, 1200000, 6575000), "ingotOsmium",
				new ParticleStack(Particles.proton), emptyParticleStack(), new ParticleStack(Particles.antiproton), 2*6575000, 0.04, -6575000);
		
		addRecipe("ingotGold", new ParticleStack(Particles.proton, 1200000, 6575000), "ingotHafnium",
				new ParticleStack(Particles.proton), emptyParticleStack(), new ParticleStack(Particles.antiproton), 2*6575000, 0.04, -6575000);
		
		addRecipe("ingotPlatinum", new ParticleStack(Particles.proton, 1200000, 6575000), "ingotHafnium",
				new ParticleStack(Particles.proton), emptyParticleStack(), new ParticleStack(Particles.antiproton), 2*6575000, 0.04, -6575000);
	
		addRecipe("ingotNiobium", new ParticleStack(Particles.proton, 1200000, 6575000), "dustArsenic",
				new ParticleStack(Particles.proton), emptyParticleStack(), new ParticleStack(Particles.antiproton), 2*6575000, 0.02, -6575000);
		
		addRecipe("ingotZirconium", new ParticleStack(Particles.proton, 1200000, 6575000), "ingotGermanium",
				new ParticleStack(Particles.proton), emptyParticleStack(), new ParticleStack(Particles.antiproton), 2*6575000, 0.02, -6575000);
		
		addRecipe("ingotStrontium", new ParticleStack(Particles.proton, 1200000, 6575000), "ingotGermanium",
				new ParticleStack(Particles.proton), emptyParticleStack(), new ParticleStack(Particles.antiproton), 2*6575000, 0.02, -6575000);
		
		addRecipe("ingotNickel", new ParticleStack(Particles.proton, 1200000, 6575000), "ingotCalcium",
				new ParticleStack(Particles.proton), emptyParticleStack(), new ParticleStack(Particles.antiproton), 2*6575000, 0.01, -6575000);
		
		addRecipe("ingotChromium", new ParticleStack(Particles.proton, 1200000, 6575000), "dustSulfur",
				new ParticleStack(Particles.proton), emptyParticleStack(), new ParticleStack(Particles.antiproton), 2*6575000, 0.01, -6575000);
		
		addRecipe("ingotTiatanium", new ParticleStack(Particles.proton, 1200000, 6575000), "itemSilicon",
				new ParticleStack(Particles.proton), emptyParticleStack(), new ParticleStack(Particles.antiproton), 2*6575000, 0.01, -6575000);
		
		addRecipe("ingotCalcium", new ParticleStack(Particles.proton, 1200000, 6575000), "ingotSodium22",
				new ParticleStack(Particles.proton), emptyParticleStack(), new ParticleStack(Particles.antiproton), 2*6575000, 0.01, -6575000);
		
		
		
		
		
		
		addRecipe("ingotCalifornium252", new ParticleStack(Particles.proton, 1200000, 1539000), "ingotUranium238",
				new ParticleStack(Particles.pion_plus),emptyParticleStack(),new ParticleStack(Particles.pion_minus), 2*1539000, 0.3, -1539000, RadSources.CALIFORNIUM_252);
		
		addRecipe("ingotCalifornium251", new ParticleStack(Particles.proton, 1200000, 1539000), "ingotNeptunium237",
				new ParticleStack(Particles.pion_plus),emptyParticleStack(),new ParticleStack(Particles.pion_minus), 2*1539000, 0.3, -1539000, RadSources.CALIFORNIUM_251);
		
		addRecipe("ingotCalifornium250", new ParticleStack(Particles.proton, 1200000, 1539000), "ingotNeptunium236",
				new ParticleStack(Particles.pion_plus),emptyParticleStack(),new ParticleStack(Particles.pion_minus), 2*1539000, 0.3, -1539000, RadSources.CALIFORNIUM_250);
		
		addRecipe("ingotCalifornium249", new ParticleStack(Particles.proton, 1200000, 1539000), "ingotUranium235",
				new ParticleStack(Particles.pion_plus),emptyParticleStack(),new ParticleStack(Particles.pion_minus), 2*1539000, 0.3, -1539000, RadSources.CALIFORNIUM_250);
		
		addRecipe("ingotBerkelium248", new ParticleStack(Particles.proton, 1200000, 1539000), "ingotUranium234",
				new ParticleStack(Particles.pion_plus),emptyParticleStack(),new ParticleStack(Particles.pion_minus), 2*1539000, 0.2, -1539000, RadSources.BERKELIUM_248);
		
		addRecipe("ingotBerkelium247", new ParticleStack(Particles.proton, 1200000, 1539000), "ingotUranium233",
				new ParticleStack(Particles.pion_plus),emptyParticleStack(),new ParticleStack(Particles.pion_minus), 2*1539000, 0.2, -1539000, RadSources.BERKELIUM_247);
		
		addRecipe("ingotCurium247", new ParticleStack(Particles.proton, 1200000, 1539000), "ingotUranium233",
				new ParticleStack(Particles.pion_plus),emptyParticleStack(),new ParticleStack(Particles.pion_minus), 2*1539000, 0.1, -1539000, RadSources.CURIUM_247);
		
		addRecipe("ingotCurium246", new ParticleStack(Particles.proton, 1200000, 1539000), "ingotThorium",
				new ParticleStack(Particles.pion_plus),emptyParticleStack(),new ParticleStack(Particles.pion_minus), 2*1539000, 0.1, -1539000, RadSources.CURIUM_246);
		
		addRecipe("ingotCurium245", new ParticleStack(Particles.proton, 1200000, 1539000), "dustProtactinium231",
				new ParticleStack(Particles.pion_plus),emptyParticleStack(),new ParticleStack(Particles.pion_minus), 2*1539000, 0.1, -1539000, RadSources.CURIUM_245);
		
		addRecipe("ingotCurium243", new ParticleStack(Particles.proton, 1200000, 1539000), "dustBismuth",
				new ParticleStack(Particles.pion_plus),emptyParticleStack(),new ParticleStack(Particles.pion_minus), 2*1539000, 0.1, -1539000, RadSources.CURIUM_243);
		
		addRecipe("ingotAmericium243", new ParticleStack(Particles.proton, 1200000, 1539000), "dustBismuth",
				new ParticleStack(Particles.pion_plus),emptyParticleStack(),new ParticleStack(Particles.pion_minus), 2*1539000, 0.09, -1539000, RadSources.AMERICIUM_243);
		
		addRecipe("ingotAmericium242", new ParticleStack(Particles.proton, 1200000, 1539000), "ingotLead",
				new ParticleStack(Particles.pion_plus),emptyParticleStack(),new ParticleStack(Particles.pion_minus), 2*1539000, 0.09, -1539000, RadSources.AMERICIUM_242);
		
		addRecipe("ingotAmericium241", new ParticleStack(Particles.proton, 1200000, 1539000), "ingotLead",
				new ParticleStack(Particles.pion_plus),emptyParticleStack(),new ParticleStack(Particles.pion_minus), 2*1539000, 0.09, -1539000, RadSources.AMERICIUM_241);
		
		addRecipe("ingotPlutonium242", new ParticleStack(Particles.proton, 1200000, 1539000), "ingotLead",
				new ParticleStack(Particles.pion_plus),emptyParticleStack(),new ParticleStack(Particles.pion_minus), 2*1539000, 0.08, -1539000, RadSources.PLUTONIUM_242);
		
		addRecipe("ingotPlutonium241", new ParticleStack(Particles.proton, 1200000, 1539000), "ingotLead",
				new ParticleStack(Particles.pion_plus),emptyParticleStack(),new ParticleStack(Particles.pion_minus), 2*1539000, 0.08, -1539000, RadSources.PLUTONIUM_241);
		
		addRecipe("ingotPlutonium239", new ParticleStack(Particles.proton, 1200000, 1539000), "dustBismuth",
				new ParticleStack(Particles.pion_plus),emptyParticleStack(),new ParticleStack(Particles.pion_minus), 2*1539000, 0.08, -1539000, RadSources.PLUTONIUM_239);
		
		addRecipe("ingotPlutonium238", new ParticleStack(Particles.proton, 1200000, 1539000), "ingotLead",
				new ParticleStack(Particles.pion_plus),emptyParticleStack(),new ParticleStack(Particles.pion_minus), 2*1539000, 0.08, -1539000, RadSources.PLUTONIUM_238);
		
		addRecipe("ingotNeptunium237", new ParticleStack(Particles.proton, 1200000, 1539000), "ingotLead",
				new ParticleStack(Particles.pion_plus),emptyParticleStack(),new ParticleStack(Particles.pion_minus), 2*1539000, 0.07, -1539000, RadSources.NEPTUNIUM_237);
		
		addRecipe("ingotNeptunium236", new ParticleStack(Particles.proton, 1200000, 1539000), "ingotLead210",
				new ParticleStack(Particles.pion_plus),emptyParticleStack(),new ParticleStack(Particles.pion_minus), 2*1539000, 0.07, -1539000, RadSources.NEPTUNIUM_236);
		
		addRecipe("ingotUranium238", new ParticleStack(Particles.proton, 1200000, 1539000), "ingotLead",
				new ParticleStack(Particles.pion_plus),emptyParticleStack(),new ParticleStack(Particles.pion_minus), 2*1539000, 0.06, -1539000, RadSources.URANIUM_238);
		
		addRecipe("ingotUranium235", new ParticleStack(Particles.proton, 1200000, 1539000), "dustBismuth",
				new ParticleStack(Particles.pion_plus),emptyParticleStack(),new ParticleStack(Particles.pion_minus), 2*1539000, 0.06, -1539000, RadSources.URANIUM_235);
		
		addRecipe("ingotUranium233", new ParticleStack(Particles.proton, 1200000, 1539000), "ingotLead",
				new ParticleStack(Particles.pion_plus),emptyParticleStack(),new ParticleStack(Particles.pion_minus), 2*1539000, 0.06, -1539000, RadSources.URANIUM_233);
	
		addRecipe("ingotThorium", new ParticleStack(Particles.proton, 1200000, 1539000), "ingotLead210",
				new ParticleStack(Particles.pion_plus),emptyParticleStack(),new ParticleStack(Particles.pion_minus), 2*1539000, 0.05, -1539000, RadSources.THORIUM);
		
		addRecipe("dustBismuth", new ParticleStack(Particles.proton, 1200000, 1539000), "ingotPlatinum",
				new ParticleStack(Particles.pion_plus),emptyParticleStack(),new ParticleStack(Particles.pion_minus), 2*1539000, 0.04, -1539000);
		
		addRecipe("ingotLead210", new ParticleStack(Particles.proton, 1200000, 1539000), "ingotPlatinum",
				new ParticleStack(Particles.pion_plus),emptyParticleStack(),new ParticleStack(Particles.pion_minus), 2*1539000, 0.04, -1539000, QMDRadSources.LEAD_210);
		
		addRecipe("ingotLead", new ParticleStack(Particles.proton, 1200000, 1539000), "ingotPlatinum",
				new ParticleStack(Particles.pion_plus),emptyParticleStack(),new ParticleStack(Particles.pion_minus), 2*1539000, 0.04, -1539000);
		
		addRecipe("ingotGold", new ParticleStack(Particles.proton, 1200000, 1539000), "ingotTungsten",
				new ParticleStack(Particles.pion_plus),emptyParticleStack(),new ParticleStack(Particles.pion_minus), 2*1539000, 0.04, -1539000);
		
		addRecipe("ingotIridium", new ParticleStack(Particles.proton, 1200000, 1539000), "ingotHafnium",
				new ParticleStack(Particles.pion_plus),emptyParticleStack(),new ParticleStack(Particles.pion_minus), 2*1539000, 0.04, -1539000);
		
		addRecipe("ingotOsmium", new ParticleStack(Particles.proton, 1200000, 1539000), "ingotHafnium",
				new ParticleStack(Particles.pion_plus),emptyParticleStack(),new ParticleStack(Particles.pion_minus), 2*1539000, 0.04, -1539000);
		
		addRecipe("ingotSilver", new ParticleStack(Particles.proton, 1200000, 1539000), "ingotNiobium",
				new ParticleStack(Particles.pion_plus),emptyParticleStack(),new ParticleStack(Particles.pion_minus), 2*1539000, 0.02, -1539000);
		
		addRecipe("ingotZinc", new ParticleStack(Particles.proton, 1200000, 1539000), "ingotTitianium",
				new ParticleStack(Particles.pion_plus),emptyParticleStack(),new ParticleStack(Particles.pion_minus), 2*1539000, 0.01, -1539000);
	
		addRecipe("ingotCopper", new ParticleStack(Particles.proton, 1200000, 1539000), "ingotTitianium",
				new ParticleStack(Particles.pion_plus),emptyParticleStack(),new ParticleStack(Particles.pion_minus), 2*1539000, 0.01, -1539000);
		
		addRecipe("ingotIron", new ParticleStack(Particles.proton, 1200000, 1539000), "ingotCalcium",
				new ParticleStack(Particles.pion_plus),emptyParticleStack(),new ParticleStack(Particles.pion_minus), 2*1539000, 0.01, -1539000);
		
		addRecipe("ingotTitianium", new ParticleStack(Particles.proton, 1200000, 1539000), "dustSulfur",
				new ParticleStack(Particles.pion_plus),emptyParticleStack(),new ParticleStack(Particles.pion_minus), 2*1539000, 0.01, -1539000);
	
		addRecipe("ingotCalcium", new ParticleStack(Particles.proton, 1200000, 1539000), "ingotMagnesium26",
				new ParticleStack(Particles.pion_plus),emptyParticleStack(),new ParticleStack(Particles.pion_minus), 2*1539000, 0.01, -1539000);
	
		
		// antiproton anhilation
		
		long q = (int)(2*Particles.proton.getMass()-(2*Particles.pion_plus.getMass()+ Particles.pion_naught.getMass()))*1000;
		
		addRecipe("ingotCalifornium252", new ParticleStack(Particles.antiproton, 1200000), "ingotNeptunium237",
				new ParticleStack(Particles.pion_plus),new ParticleStack(Particles.pion_naught),new ParticleStack(Particles.pion_minus), 10000000, 0.3, q, RadSources.CALIFORNIUM_252);
		
		addRecipe("ingotCalifornium251", new ParticleStack(Particles.antiproton, 1200000), "ingotNeptunium236",
				new ParticleStack(Particles.pion_plus),new ParticleStack(Particles.pion_naught),new ParticleStack(Particles.pion_minus), 10000000, 0.3, q, RadSources.CALIFORNIUM_251);
		
		addRecipe("ingotCalifornium250", new ParticleStack(Particles.antiproton, 1200000), "ingotUranium235",
				new ParticleStack(Particles.pion_plus),new ParticleStack(Particles.pion_naught),new ParticleStack(Particles.pion_minus), 10000000, 0.3, q, RadSources.CALIFORNIUM_250);
		
		addRecipe("ingotCalifornium249", new ParticleStack(Particles.antiproton, 1200000), "ingotUranium234",
				new ParticleStack(Particles.pion_plus),new ParticleStack(Particles.pion_naught),new ParticleStack(Particles.pion_minus), 10000000, 0.3, q, RadSources.CALIFORNIUM_249);
		
		addRecipe("ingotBerkelium248", new ParticleStack(Particles.antiproton, 1200000), "ingotUranium233",
				new ParticleStack(Particles.pion_plus),new ParticleStack(Particles.pion_naught),new ParticleStack(Particles.pion_minus), 10000000, 0., q, RadSources.BERKELIUM_248);
		
		addRecipe("ingotBerkelium247", new ParticleStack(Particles.antiproton, 1200000), "ingotThorium",
				new ParticleStack(Particles.pion_plus),new ParticleStack(Particles.pion_naught),new ParticleStack(Particles.pion_minus), 10000000, 0.2, q, RadSources.BERKELIUM_247);
		
		addRecipe("ingotCurium247", new ParticleStack(Particles.antiproton, 1200000), "ingotThorium",
				new ParticleStack(Particles.pion_plus),new ParticleStack(Particles.pion_naught),new ParticleStack(Particles.pion_minus), 10000000, 0.1, q, RadSources.CURIUM_247);
		
		addRecipe("ingotCurium246", new ParticleStack(Particles.antiproton, 1200000), "dustProtactinium231",
				new ParticleStack(Particles.pion_plus),new ParticleStack(Particles.pion_naught),new ParticleStack(Particles.pion_minus), 10000000, 0.1, q, RadSources.CURIUM_246);
		
		addRecipe("ingotCurium245", new ParticleStack(Particles.antiproton, 1200000), "dustRadium",
				new ParticleStack(Particles.pion_plus),new ParticleStack(Particles.pion_naught),new ParticleStack(Particles.pion_minus), 10000000, 0.1, q, RadSources.CURIUM_245);
		
		addRecipe("ingotCurium243", new ParticleStack(Particles.antiproton, 1200000), "ingotLead",
				new ParticleStack(Particles.pion_plus),new ParticleStack(Particles.pion_naught),new ParticleStack(Particles.pion_minus), 10000000, 0.1, q, RadSources.CURIUM_243);
		
		addRecipe("ingotAmericium243", new ParticleStack(Particles.antiproton, 1200000), "ingotLead",
				new ParticleStack(Particles.pion_plus),new ParticleStack(Particles.pion_naught),new ParticleStack(Particles.pion_minus), 10000000, 0.09, q, RadSources.AMERICIUM_243);
		
		addRecipe("ingotAmericium242", new ParticleStack(Particles.antiproton, 1200000), "ingotLead",
				new ParticleStack(Particles.pion_plus),new ParticleStack(Particles.pion_naught),new ParticleStack(Particles.pion_minus), 10000000, 0.09, q, RadSources.AMERICIUM_242);
		
		addRecipe("ingotAmericium241", new ParticleStack(Particles.antiproton, 1200000), "dustRadium",
				new ParticleStack(Particles.pion_plus),new ParticleStack(Particles.pion_naught),new ParticleStack(Particles.pion_minus), 10000000, 0.09, q, RadSources.AMERICIUM_241);
		
		addRecipe("ingotPlutonium242", new ParticleStack(Particles.antiproton, 1200000), "ingotLead",
				new ParticleStack(Particles.pion_plus),new ParticleStack(Particles.pion_naught),new ParticleStack(Particles.pion_minus), 10000000, 0.08, q, RadSources.PLUTONIUM_242);
		
		addRecipe("ingotPlutonium241", new ParticleStack(Particles.antiproton, 1200000), "dustRadium",
				new ParticleStack(Particles.pion_plus),new ParticleStack(Particles.pion_naught),new ParticleStack(Particles.pion_minus), 10000000, 0.08, q, RadSources.PLUTONIUM_241);
		
		addRecipe("ingotPlutonium239", new ParticleStack(Particles.antiproton, 1200000), "ingotLead",
				new ParticleStack(Particles.pion_plus),new ParticleStack(Particles.pion_naught),new ParticleStack(Particles.pion_minus), 10000000, 0.08, q, RadSources.PLUTONIUM_239);
		
		addRecipe("ingotPlutonium238", new ParticleStack(Particles.antiproton, 1200000), "ingotLead",
				new ParticleStack(Particles.pion_plus),new ParticleStack(Particles.pion_naught),new ParticleStack(Particles.pion_minus), 10000000, 0.08, q, RadSources.PLUTONIUM_238);
		
		addRecipe("ingotNeptunium237", new ParticleStack(Particles.antiproton, 1200000), "dustPolonium",
				new ParticleStack(Particles.pion_plus),new ParticleStack(Particles.pion_naught),new ParticleStack(Particles.pion_minus), 10000000, 0.07, q, RadSources.NEPTUNIUM_237);
		
		addRecipe("ingotNeptunium236", new ParticleStack(Particles.antiproton, 1200000), "dustPolonium",
				new ParticleStack(Particles.pion_plus),new ParticleStack(Particles.pion_naught),new ParticleStack(Particles.pion_minus), 10000000, 0.07, q, RadSources.NEPTUNIUM_236);
		
		
		addRecipe("ingotUranium238", new ParticleStack(Particles.antiproton, 1200000), "ingotLead",
				new ParticleStack(Particles.pion_plus),new ParticleStack(Particles.pion_naught),new ParticleStack(Particles.pion_minus), 10000000, 0.06, q, RadSources.URANIUM_238);

		addRecipe("ingotUranium235", new ParticleStack(Particles.antiproton, 1200000), "ingotLead",
				new ParticleStack(Particles.pion_plus),new ParticleStack(Particles.pion_naught),new ParticleStack(Particles.pion_minus), 10000000, 0.06, q, RadSources.URANIUM_235);

		addRecipe("ingotUranium233", new ParticleStack(Particles.antiproton, 1200000), "ingotLead210",
				new ParticleStack(Particles.pion_plus),new ParticleStack(Particles.pion_naught),new ParticleStack(Particles.pion_minus), 10000000, 0.06, q, RadSources.URANIUM_233);
		
		addRecipe("ingotThorium", new ParticleStack(Particles.antiproton, 1200000), "ingotLead",
				new ParticleStack(Particles.pion_plus),new ParticleStack(Particles.pion_naught),new ParticleStack(Particles.pion_minus), 10000000, 0.05, q, RadSources.THORIUM);
		
	
		addRecipe("ingotLead210", new ParticleStack(Particles.antiproton, 1200000), "ingotPlatnium",
				new ParticleStack(Particles.pion_plus),new ParticleStack(Particles.pion_naught),new ParticleStack(Particles.pion_minus), 10000000, 0.04, q, QMDRadSources.LEAD_210);
		
		addRecipe("dustBismuth", new ParticleStack(Particles.antiproton, 1200000), "ingotPlatnium",
				new ParticleStack(Particles.pion_plus),new ParticleStack(Particles.pion_naught),new ParticleStack(Particles.pion_minus), 10000000, 0.04, q);
		
		addRecipe("dustBismuth", new ParticleStack(Particles.antiproton, 1200000), "ingotPlatnium",
				new ParticleStack(Particles.pion_plus),new ParticleStack(Particles.pion_naught),new ParticleStack(Particles.pion_minus), 10000000, 0.04, q);
		
		addRecipe("ingotLead", new ParticleStack(Particles.antiproton, 1200000), "ingotIridium",
				new ParticleStack(Particles.pion_plus),new ParticleStack(Particles.pion_naught),new ParticleStack(Particles.pion_minus), 10000000, 0.04, q);
		
		addRecipe("ingotGold", new ParticleStack(Particles.antiproton, 1200000), "ingotTungsten",
				new ParticleStack(Particles.pion_plus),new ParticleStack(Particles.pion_naught),new ParticleStack(Particles.pion_minus), 10000000, 0.04, q);
		
		addRecipe("ingotPlatinum", new ParticleStack(Particles.antiproton, 1200000), "ingotHafnium",
				new ParticleStack(Particles.pion_plus),new ParticleStack(Particles.pion_naught),new ParticleStack(Particles.pion_minus), 10000000, 0.04, q);
		
		addRecipe("ingotIridium", new ParticleStack(Particles.antiproton, 1200000), "ingotHafnium",
				new ParticleStack(Particles.pion_plus),new ParticleStack(Particles.pion_naught),new ParticleStack(Particles.pion_minus), 10000000, 0.04, q);
		
		addRecipe("ingotOsmium", new ParticleStack(Particles.antiproton, 1200000), "ingotHafnium",
				new ParticleStack(Particles.pion_plus),new ParticleStack(Particles.pion_naught),new ParticleStack(Particles.pion_minus), 10000000, 0.04, q);
		
		addRecipe("ingotSilver", new ParticleStack(Particles.antiproton, 1200000), "ingotZirconium",
				new ParticleStack(Particles.pion_plus),new ParticleStack(Particles.pion_naught),new ParticleStack(Particles.pion_minus), 10000000, 0.02, q);
		
		addRecipe("ingotZirconium", new ParticleStack(Particles.antiproton, 1200000), "dustArsenic",
				new ParticleStack(Particles.pion_plus),new ParticleStack(Particles.pion_naught),new ParticleStack(Particles.pion_minus), 10000000, 0.02, q);
		
		addRecipe("ingotStrontium", new ParticleStack(Particles.antiproton, 1200000), "ingotGermanium",
				new ParticleStack(Particles.pion_plus),new ParticleStack(Particles.pion_naught),new ParticleStack(Particles.pion_minus), 10000000, 0.02, q);
		
		addRecipe("ingotTitianium", new ParticleStack(Particles.antiproton, 1200000), "dustSulfur",
				new ParticleStack(Particles.pion_plus),new ParticleStack(Particles.pion_naught),new ParticleStack(Particles.pion_minus), 10000000, 0.01, q);
		
		addRecipe("ingotPotasium", new ParticleStack(Particles.antiproton, 1200000), "ingotMagnesium24",
				new ParticleStack(Particles.pion_plus),new ParticleStack(Particles.pion_naught),new ParticleStack(Particles.pion_minus), 10000000, 0.01, q);
	
		
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
