package lach_01298.qmd.particle;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import com.google.common.collect.Lists;

import io.netty.handler.logging.LogLevel;
import lach_01298.qmd.QMD;
import lach_01298.qmd.Util;
import net.minecraft.util.ResourceLocation;
import scala.actors.threadpool.Arrays;


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
	
	public static Particle boron_ion;

	
	//Pions
	public static Particle pion_plus;
	public static Particle pion_naught;
	public static Particle pion_minus;
	
	
	//Kaons
	public static Particle kaon_plus;
	public static Particle kaon_naught;
	public static Particle kaon_minus;
	
	//D mesons
	public static Particle d_meson_plus;
	public static Particle d_meson_naught;
	public static Particle d_meson_minus;
	public static Particle strange_d_meson_plus;
	public static Particle strange_d_meson_minus;
	
	//B mesons
	public static Particle b_meson_plus;
	public static Particle b_meson_naught;
	public static Particle b_meson_minus;
	public static Particle strange_b_meson_plus;
	public static Particle strange_b_meson_minus;
	public static Particle charmed_b_meson_plus;
	public static Particle charmed_b_meson_minus;
	
	//lambda
	public static Particle lambda;
	public static Particle antilambda;
	public static Particle charmed_lambda;
	public static Particle charmed_antilambda;
	public static Particle bottom_lambda;
	public static Particle bottom_antilambda;


	
	public static void registerParticle (Particle particle)
	{
		if(!list.containsKey(particle.getName()))
		{
			list.put(particle.getName(), particle);
		}
		Util.getLogger().error("tried registering paticle " + particle.getName() + " but " + particle.getName() + " already exists");	
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
		up = new Particle("up_quark",Util.appendPath(particlePath, "up_quark.png"),2.2,2d/3d,1d/2d,true, true);
		down = new Particle("down_quark",Util.appendPath(particlePath, "down_quark.png"),4.7,-1d/3d,1d/2d,true, true);
		charm = new Particle("charm_quark",Util.appendPath(particlePath, "charm_quark.png"),1276d,2d/3d,1d/2d,true,true);
		strange = new Particle("strange_quark",Util.appendPath(particlePath, "strange_quark.png"),95d,-1d/3d,1d/2d,true,true);
		top = new Particle("top_quark",Util.appendPath(particlePath, "top_quark.png"),173210d,2d/3d,1d/2d,true, true);
		bottom = new Particle("bottom_quark",Util.appendPath(particlePath, "bottom_quark.png"),4180d,-1d/3d,1d/2d,true, true);
		
		//antiquarks
		antiup = makeAntiParticle(up,"antiup_quark",Util.appendPath(particlePath, "antiup_quark.png"));
		antidown = makeAntiParticle(down,"antidown_quark",Util.appendPath(particlePath, "antidown_quark.png"));
		anticharm = makeAntiParticle(charm,"anticharm_quark",Util.appendPath(particlePath, "anticharm_quark.png"));
		antistrange = makeAntiParticle(strange,"antistrange_quark",Util.appendPath(particlePath, "antistrange_quark.png"));
		antitop = makeAntiParticle(top,"antitop_quark",Util.appendPath(particlePath, "antitop_quark.png"));
		antibottom = makeAntiParticle(bottom,"antibottom_quark",Util.appendPath(particlePath, "antibottom_quark.png"));
		
		//leptons
		electron = new Particle("electron",Util.appendPath(particlePath, "electron.png"),0.511,-1,1d/2d,true);
		muon = new Particle("muon",Util.appendPath(particlePath, "muon.png"),105.7,-1,1d/2d,true);
		tau = new Particle("tau",Util.appendPath(particlePath, "tau.png"),1776,-1,1d/2d,true);
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
		photon = new Particle("photon",Util.appendPath(particlePath, "photon.png"),0,0,1,false);
		gluon = new Particle("gluon",Util.appendPath(particlePath, "gluon.png"),0,0,1,false,true);
		w_plus_boson = new Particle("w_plus_boson",Util.appendPath(particlePath, "w_plus_boson.png"),8038,1,1,true);
		w_minus_boson = new Particle("w_minus_boson",Util.appendPath(particlePath, "w_minus_boson.png"),8038,-1,1,true);
		z_boson = new Particle("z_boson",Util.appendPath(particlePath, "z_boson.png"),9118,0,1,false);
		higgs_boson = new Particle("higgs_boson",Util.appendPath(particlePath, "higgs_boson.png"),125180,0,0,true);
		
		
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
		register();
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

		registerParticle(boron_ion);
		
		registerParticle(pion_plus);
		registerParticle(pion_naught);
		registerParticle(pion_minus);
		
		registerParticle(kaon_plus);
		registerParticle(kaon_naught);
		registerParticle(kaon_minus);
		
	}
	
	
	
	private static void initComposites()
	{
		//Nucleons
		proton = new Particle("proton",Util.appendPath(particlePath, "proton.png"),938.3,1,1d/2d,true);
		proton.addComponentParticle(up);
		proton.addComponentParticle(up);
		proton.addComponentParticle(down);
		antiproton = makeAntiParticle(proton, "antiproton",Util.appendPath(particlePath, "antiproton.png"));
		
		neutron = new Particle("neutron",Util.appendPath(particlePath, "neutron.png"),939.6,0,1d/2d,true);
		neutron.addComponentParticle(up);
		neutron.addComponentParticle(down);
		neutron.addComponentParticle(down);
		antineutron = makeAntiParticle(neutron, "antineutron",Util.appendPath(particlePath, "antineutron.png"));
		
		
		//nuclei
		deuteron = new Particle("deuteron",Util.appendPath(particlePath, "deuteron.png"),1875.0,1,0,true);
		deuteron.addComponentParticle(proton);
		deuteron.addComponentParticle(neutron);
		antideuteron = makeAntiParticle(deuteron, "antideuteron",Util.appendPath(particlePath, "antideuteron.png"));
		
		alpha = new Particle("alpha",Util.appendPath(particlePath, "alpha.png"),3727,2,0,true);
		alpha.addComponentParticle(proton);
		alpha.addComponentParticle(proton);
		alpha.addComponentParticle(neutron);
		alpha.addComponentParticle(neutron);
		antialpha = makeAntiParticle(alpha, "antialpha",Util.appendPath(particlePath, "antialpha.png"));
		
		
		triton = new Particle("triton",Util.appendPath(particlePath, "triton.png"),2809,1,1/2,true);
		triton.addComponentParticle(proton);
		triton.addComponentParticle(neutron);
		triton.addComponentParticle(neutron);
		antitriton = makeAntiParticle(triton, "antitriton",Util.appendPath(particlePath, "antitriton.png"));
		
		
		boron_ion = new Particle("boron_ion",Util.appendPath(particlePath, "boron_ion.png"),10246,1,1/2,true);
		boron_ion.setComponentParticles(Lists.newArrayList(proton,proton,proton,proton,proton,neutron,neutron,neutron,neutron,neutron,neutron,electron,electron,electron,electron));
		
		
		
		
		//Pions
		pion_plus = new Particle("pion_plus",Util.appendPath(particlePath, "pion_plus.png"),139.6,1,0,true);
		pion_plus.addComponentParticle(up);
		pion_plus.addComponentParticle(antidown);
		pion_minus = makeAntiParticle(pion_plus, "pion_minus",Util.appendPath(particlePath, "pion_minus.png"));
		
		pion_naught = new Particle("pion_naught",Util.appendPath(particlePath, "pion_naught.png"),135.0,0,0,true);
		pion_naught.addComponentParticle(up);
		pion_naught.addComponentParticle(antiup);
		
		
		//Kaons
		kaon_plus =  new Particle("kaon_plus",Util.appendPath(particlePath, "kaon_plus.png"),463.7,1,0,true);
		kaon_plus.addComponentParticle(up);
		kaon_plus.addComponentParticle(antistrange);
		kaon_minus =  makeAntiParticle(kaon_plus, "kaon_minus",Util.appendPath(particlePath, "kaon_minus.png"));
		
		kaon_naught =  new Particle("kaon_naught",Util.appendPath(particlePath, "kaon_naught.png"),497.6,1,0,true);
		kaon_naught.addComponentParticle(down);
		kaon_naught.addComponentParticle(antistrange);
		
		
	}
	
	
	public static Particle makeAntiParticle(Particle particle, String name,ResourceLocation texture)
	{
		double mass = particle.getMass();
		double charge = -particle.getCharge();
		double spin = particle.getSpin();
		boolean weakCharged = particle.interactsWithWeak();
		boolean isColoured = particle.interactsWithStrong();
		List<Particle> anticomponent = new ArrayList<Particle>();
		for (Particle component : particle.getComponentParticles())
		{
			anticomponent.add(component.getAntiParticle());
		}

		Particle antiparticle = new Particle(name, texture, mass, charge, spin, weakCharged,isColoured);
		antiparticle.setComponentParticles(anticomponent);
		antiparticle.setAntiParticle(particle);
		return antiparticle;

	}
	
	
	
	
	
}
