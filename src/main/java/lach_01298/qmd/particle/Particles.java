package lach_01298.qmd.particle;

import java.util.HashMap;
import java.util.Map;

import lach_01298.qmd.QMD;
import lach_01298.qmd.Util;
import net.minecraft.util.ResourceLocation;


public class Particles
{

	public static final Map<String,Particle> list = new HashMap<String,Particle>();
	
	private static ResourceLocation particlePath = new ResourceLocation(QMD.MOD_ID,"textures/particles/");

	
	//quarks
	public static Particle up;
	public static Particle down;
	public static Particle charm;
	public static Particle strange;
	public static Particle top;
	public static Particle bottom;
	public static Particle antiup;
	public static Particle antidown;
	public static Particle anticharm;
	public static Particle antistrange;
	public static Particle antitop;
	public static Particle antibottom;
	
	//leptons
	public static Particle electron;
	public static Particle electron_neutrino;
	public static Particle muon;
	public static Particle muon_neutrino;
	public static Particle tau;
	public static Particle tau_neutrino;
	public static Particle positron;
	public static Particle electron_antineutrino;
	public static Particle antimuon;
	public static Particle muon_antineutrino;
	public static Particle antitau;
	public static Particle tau_antineutrino;
	
	//bosons
	public static Particle photon;
	public static Particle gluon;
	public static Particle w_plus_boson;
	public static Particle w_minus_boson;
	public static Particle z_boson;
	public static Particle higgs_boson;
	
	//Nucleons
	public static Particle proton;
	public static Particle antiproton;
	public static Particle neutron;
	public static Particle antineutron;
	
	//nuclei
	public static Particle deuteron;
	public static Particle antideuteron;
	public static Particle alpha;
	public static Particle antialpha;
	public static Particle triton;
	public static Particle antitriton;
	public static Particle helion;
	public static Particle antihelion;
	
	public static Particle boron_ion;
	public static Particle calcium_48_ion;

	
	//Pions
	public static Particle pion_plus;
	public static Particle pion_naught;
	public static Particle pion_minus;
	
	
	//Kaons
	public static Particle kaon_plus;
	public static Particle kaon_naught;
	public static Particle antikaon_naught;
	public static Particle kaon_minus;
	
	//Eta mesons
	public static Particle eta;
	public static Particle eta_prime;
	public static Particle charmed_eta;
	public static Particle bottom_eta;
	
	
	//other
	public static Particle glueball;
	
	public static void registerParticle (Particle particle)
	{
		if(!list.containsKey(particle.getName()))
		{
			list.put(particle.getName(), particle);
		}
		else
		{
			Util.getLogger().error("tried registering paticle " + particle.getName() + " but " + particle.getName() + " already exists");	
		}
	}
	
	
	
	public static Particle getParticleFromName(String name)
	{
		if(name != null)
		{
			if (!list.containsKey(name))
			{
				Util.getLogger().error("there is no particle with name " + name);
				return null;
			}
			return list.get(name);
		}
		return null;
	}
	
	public static void init()
	{
		
		//quarks
		up = new Particle("up_quark",Util.appendPath(particlePath, "up_quark.png"),2.2,2d/3d,1d/2d,true,true);
		down = new Particle("down_quark",Util.appendPath(particlePath, "down_quark.png"),4.7,-1d/3d,1d/2d,true,true);
		charm = new Particle("charm_quark",Util.appendPath(particlePath, "charm_quark.png"),1280d,2d/3d,1d/2d,true,true);
		strange = new Particle("strange_quark",Util.appendPath(particlePath, "strange_quark.png"),95d,-1d/3d,1d/2d,true,true);
		top = new Particle("top_quark",Util.appendPath(particlePath, "top_quark.png"),173000d,2d/3d,1d/2d,true,true);
		bottom = new Particle("bottom_quark",Util.appendPath(particlePath, "bottom_quark.png"),4180d,-1d/3d,1d/2d,true,true);
		
		//antiquarks
		antiup = makeAntiParticle(up,"antiup_quark",Util.appendPath(particlePath, "antiup_quark.png"));
		antidown = makeAntiParticle(down,"antidown_quark",Util.appendPath(particlePath, "antidown_quark.png"));
		anticharm = makeAntiParticle(charm,"anticharm_quark",Util.appendPath(particlePath, "anticharm_quark.png"));
		antistrange = makeAntiParticle(strange,"antistrange_quark",Util.appendPath(particlePath, "antistrange_quark.png"));
		antitop = makeAntiParticle(top,"antitop_quark",Util.appendPath(particlePath, "antitop_quark.png"));
		antibottom = makeAntiParticle(bottom,"antibottom_quark",Util.appendPath(particlePath, "antibottom_quark.png"));
		
		//leptons
		electron = new Particle("electron",Util.appendPath(particlePath, "electron.png"),0.511,-1,1d/2d,true);
		muon = new Particle("muon",Util.appendPath(particlePath, "muon.png"),106,-1,1d/2d,true);
		tau = new Particle("tau",Util.appendPath(particlePath, "tau.png"),1780,-1,1d/2d,true);
		electron_neutrino = new Particle("electron_neutrino",Util.appendPath(particlePath, "electron_neutrino.png"),0.00000012,0,1d/2d,true);
		muon_neutrino = new Particle("muon_neutrino",Util.appendPath(particlePath, "muon_neutrino.png"),0.00000012,0,1d/2d,true);
		tau_neutrino = new Particle("tau_neutrino",Util.appendPath(particlePath, "tau_neutrino.png"),0.00000012,0,1d/2d,true);
		
		//antileptons
		positron = makeAntiParticle(electron,"positron",Util.appendPath(particlePath, "positron.png"));
		antimuon = makeAntiParticle(muon,"antimuon",Util.appendPath(particlePath, "antimuon.png"));
		antitau = makeAntiParticle(tau,"antitau",Util.appendPath(particlePath, "antitau.png"));
		electron_antineutrino = makeAntiParticle(electron_neutrino,"electron_antineutrino",Util.appendPath(particlePath, "electron_antineutrino.png"));
		muon_antineutrino = makeAntiParticle(muon_neutrino,"muon_antineutrino",Util.appendPath(particlePath, "muon_antineutrino.png"));
		tau_antineutrino = makeAntiParticle(tau_neutrino,"tau_antineutrino",Util.appendPath(particlePath, "tau_antineutrino.png"));
		
		//bosons
		photon = new Particle("photon",Util.appendPath(particlePath, "photon.png"),0,0,1,false,false);
		gluon = new Particle("gluon",Util.appendPath(particlePath, "gluon.png"),0,0,1,false,true);
		w_plus_boson = new Particle("w_plus_boson",Util.appendPath(particlePath, "w_plus_boson.png"),80400d,1,1,true,false);
		w_minus_boson = new Particle("w_minus_boson",Util.appendPath(particlePath, "w_minus_boson.png"),80400d,-1,1,true,false);
		z_boson = new Particle("z_boson",Util.appendPath(particlePath, "z_boson.png"),91200d,0,1,false,false);
		higgs_boson = new Particle("higgs_boson",Util.appendPath(particlePath, "higgs_boson.png"),125000d,0,0,true,false);
		
		
		//register anti particles
		up.setAntiParticle(antiup);
		down.setAntiParticle(antidown);
		charm.setAntiParticle(anticharm);
		strange.setAntiParticle(antistrange);
		top.setAntiParticle(antitop);
		bottom.setAntiParticle(antibottom);
		electron.setAntiParticle(positron);
		electron_neutrino.setAntiParticle(electron_antineutrino);
		muon.setAntiParticle(antimuon);
		muon_neutrino.setAntiParticle(muon_antineutrino);
		tau.setAntiParticle(antitau);
		tau_neutrino.setAntiParticle(tau_antineutrino);
		w_plus_boson.setAntiParticle(w_minus_boson);
		
		
		initComposites();
	}

	public static void register()
	{
		registerParticle(up);
		registerParticle(antiup);
		registerParticle(down);
		registerParticle(antidown);
		registerParticle(charm);
		registerParticle(anticharm);
		registerParticle(strange);
		registerParticle(antistrange);
		registerParticle(top);
		registerParticle(antitop);
		registerParticle(bottom);
		registerParticle(antibottom);
		
		registerParticle(electron);
		registerParticle(positron);
		registerParticle(electron_neutrino);
		registerParticle(electron_antineutrino);
		registerParticle(muon);
		registerParticle(antimuon);
		registerParticle(muon_neutrino);
		registerParticle(muon_antineutrino);
		registerParticle(tau);
		registerParticle(antitau);
		registerParticle(tau_neutrino);
		registerParticle(tau_antineutrino);
		
		
		registerParticle(photon);
		registerParticle(gluon);
		registerParticle(w_plus_boson);
		registerParticle(w_minus_boson);
		registerParticle(z_boson);
		registerParticle(higgs_boson);
		
		registerParticle(proton);
		registerParticle(antiproton);
		registerParticle(neutron);
		registerParticle(antineutron);

		registerParticle(deuteron);
		registerParticle(antideuteron);
		registerParticle(alpha);
		registerParticle(antialpha);
		registerParticle(triton);
		registerParticle(antitriton);
		registerParticle(helion);
		registerParticle(antihelion);

		registerParticle(boron_ion);
		registerParticle(calcium_48_ion);
		
		registerParticle(pion_plus);
		registerParticle(pion_naught);
		registerParticle(pion_minus);
		
		registerParticle(kaon_plus);
		registerParticle(kaon_naught);
		registerParticle(antikaon_naught);
		registerParticle(kaon_minus);
		
		registerParticle(eta);
		registerParticle(eta_prime);
		registerParticle(charmed_eta);
		registerParticle(bottom_eta);
		
		registerParticle(glueball);
		
	}
	
	
	
	private static void initComposites()
	{
		//Nucleons
		proton = new Particle("proton",Util.appendPath(particlePath, "proton.png"),938d,1,1d/2d,true,true);
		proton.addComponentParticle(up,2);
		proton.addComponentParticle(down);
		antiproton = makeAntiParticle(proton, "antiproton",Util.appendPath(particlePath, "antiproton.png"));
		
		neutron = new Particle("neutron",Util.appendPath(particlePath, "neutron.png"),940d,0,1d/2d,true,true);
		neutron.addComponentParticle(up);
		neutron.addComponentParticle(down,2);
		antineutron = makeAntiParticle(neutron, "antineutron",Util.appendPath(particlePath, "antineutron.png"));
		
		
		//nuclei
		deuteron = new Particle("deuteron",Util.appendPath(particlePath, "deuteron.png"),1880d,1,0,true,true);
		deuteron.addComponentParticle(proton);
		deuteron.addComponentParticle(neutron);
		antideuteron = makeAntiParticle(deuteron, "antideuteron",Util.appendPath(particlePath, "antideuteron.png"));
		
		alpha = new Particle("alpha",Util.appendPath(particlePath, "alpha.png"),3730d,2,0,true,true);
		alpha.addComponentParticle(proton,2);
		alpha.addComponentParticle(neutron,2);

		antialpha = makeAntiParticle(alpha, "antialpha",Util.appendPath(particlePath, "antialpha.png"));
		
		
		triton = new Particle("triton",Util.appendPath(particlePath, "triton.png"),2810d,1,1/2,true,true);
		triton.addComponentParticle(proton);
		triton.addComponentParticle(neutron,2);
		antitriton = makeAntiParticle(triton, "antitriton",Util.appendPath(particlePath, "antitriton.png"));
		
		helion = new Particle("helion",Util.appendPath(particlePath, "helion.png"),2810d,2,1/2,true,true);
		helion.addComponentParticle(proton,2);
		helion.addComponentParticle(neutron);
		antihelion = makeAntiParticle(triton, "antihelion",Util.appendPath(particlePath, "antihelion.png"));
		
		
		boron_ion = new Particle("boron_ion",Util.appendPath(particlePath, "boron_ion.png"),10200d,1,1/2,true,true);
		boron_ion.addComponentParticle(proton,5);
		boron_ion.addComponentParticle(neutron,5);
		boron_ion.addComponentParticle(electron,4);
		
		calcium_48_ion = new Particle("calcium_48_ion",Util.appendPath(particlePath, "calcium_48_ion.png"),44600d,1,0,true,true);
		calcium_48_ion.addComponentParticle(proton,20);
		calcium_48_ion.addComponentParticle(neutron,28);
		calcium_48_ion.addComponentParticle(electron,19);
		
		
		//Pions
		pion_plus = new Particle("pion_plus",Util.appendPath(particlePath, "pion_plus.png"),140d,1,0,true,true);
		pion_plus.addComponentParticle(up);
		pion_plus.addComponentParticle(antidown);
		pion_minus = makeAntiParticle(pion_plus, "pion_minus",Util.appendPath(particlePath, "pion_minus.png"));
		
		pion_naught = new Particle("pion_naught",Util.appendPath(particlePath, "pion_naught.png"),135d,0,0,true,true);
		pion_naught.addComponentParticle(up);
		pion_naught.addComponentParticle(antiup);
		
		
		//Kaons
		kaon_plus =  new Particle("kaon_plus",Util.appendPath(particlePath, "kaon_plus.png"),464d,1,0,true,true);
		kaon_plus.addComponentParticle(up);
		kaon_plus.addComponentParticle(antistrange);
		kaon_minus =  makeAntiParticle(kaon_plus, "kaon_minus",Util.appendPath(particlePath, "kaon_minus.png"));
		
		kaon_naught =  new Particle("kaon_naught",Util.appendPath(particlePath, "kaon_naught.png"),498d,1,0,true,true);
		kaon_naught.addComponentParticle(down);
		kaon_naught.addComponentParticle(antistrange);
		antikaon_naught =  makeAntiParticle(kaon_naught, "antikaon_naught",Util.appendPath(particlePath, "antikaon_naught.png"));
		
		//eta
		
		eta =  new Particle("eta",Util.appendPath(particlePath, "eta.png"),548d,0,0,true,true);
		eta.addComponentParticle(down);
		eta.addComponentParticle(antidown);
		
		eta_prime =  new Particle("eta_prime",Util.appendPath(particlePath, "eta_prime.png"),958d,0,0,true,true);
		eta_prime.addComponentParticle(strange);
		eta_prime.addComponentParticle(antistrange);
		
		charmed_eta =  new Particle("charmed_eta",Util.appendPath(particlePath, "charmed_eta.png"),2980d,0,0,true,true);
		charmed_eta.addComponentParticle(charm);
		charmed_eta.addComponentParticle(anticharm);
		
		bottom_eta =  new Particle("bottom_eta",Util.appendPath(particlePath, "bottom_eta.png"),9400d,0,0,true,true);
		bottom_eta.addComponentParticle(bottom);
		bottom_eta.addComponentParticle(antibottom);
		
		
		
		//other
		glueball =  new Particle("glueball",Util.appendPath(particlePath, "glueball.png"),1730d,0,0,false,true);
		glueball.addComponentParticle(gluon,2);
		
	}
	
	
	public static Particle makeAntiParticle(Particle particle, String name,ResourceLocation texture)
	{
		double mass = particle.getMass();
		double charge = -particle.getCharge();
		double spin = particle.getSpin();
		boolean weakCharged = particle.interactsWithWeak();
		boolean isColoured = particle.interactsWithStrong();
		 HashMap<Particle, Integer> anticomponents = new  HashMap<Particle, Integer>();
		for (Map.Entry<Particle, Integer> component : particle.getComponentParticles().entrySet())
		{
			anticomponents.put(component.getKey().getAntiParticle(), component.getValue());
		}

		Particle antiparticle = new Particle(name, texture, mass, charge, spin, weakCharged,isColoured);
		antiparticle.setComponentParticles(anticomponents);
		antiparticle.setAntiParticle(particle);
		return antiparticle;

	}
	
	
	
	
	
}
