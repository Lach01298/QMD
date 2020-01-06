package lach_01298.qmd.particle;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import io.netty.handler.logging.LogLevel;
import lach_01298.qmd.QMD;
import lach_01298.qmd.Util;
import net.minecraft.util.ResourceLocation;


public class Particles
{

	public static final Map<String,Particle> list = new HashMap<String,Particle>();
	
	

	
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
	
	//composite particles
	public static Particle proton;
	public static Particle antiproton;
	public static Particle neutron;
	public static Particle antineutron;
	public static Particle pion_plus;
	public static Particle pion_naught;
	public static Particle pion_minus;
	
	//ions
	public static Particle deuteron;
	public static Particle antideuteron;
	public static Particle alpha;
	public static Particle antialpha;
	
	
	
	
	
	
	public static void registerPaticle (Particle particle)
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
		up = new Particle("up_quark",new ResourceLocation(QMD.MOD_ID, "up_quark"),Color.GREEN,2.2,2d/3d,1d/2d,1d/2d, ColourCharge.UNSPECIFIED,ColourCharge.WHITE);
		down = new Particle("down_quark",new ResourceLocation(QMD.MOD_ID, "up_quark"),Color.GREEN,4.7,-1d/3d,1d/2d,-1d/2d, ColourCharge.UNSPECIFIED,ColourCharge.WHITE);
		charm = new Particle("charm_quark",new ResourceLocation(QMD.MOD_ID, "up_quark"),Color.GREEN,1276d,2d/3d,1d/2d,1d/2d, ColourCharge.UNSPECIFIED,ColourCharge.WHITE);
		strange = new Particle("strange_quark",new ResourceLocation(QMD.MOD_ID, "up_quark"),Color.GREEN,95d,-1d/3d,1d/2d,-1d/2d, ColourCharge.UNSPECIFIED,ColourCharge.WHITE);
		top = new Particle("top_quark",new ResourceLocation(QMD.MOD_ID, "up_quark"),Color.GREEN,173210d,2d/3d,1d/2d,1d/2d, ColourCharge.UNSPECIFIED,ColourCharge.WHITE);
		bottom = new Particle("bottom_quark",new ResourceLocation(QMD.MOD_ID, "up_quark"),Color.GREEN,4180d,-1d/3d,1d/2d,-1d/2d, ColourCharge.UNSPECIFIED,ColourCharge.WHITE);
		
		//antiquarks
		antiup = new Particle("antiup_quark",new ResourceLocation(QMD.MOD_ID, "up_quark"),Color.GREEN,2.2,-2d/3d,1d/2d,1d/2d, ColourCharge.UNSPECIFIED,ColourCharge.WHITE);
		antidown = new Particle("antidown_quark",new ResourceLocation(QMD.MOD_ID, "up_quark"),Color.GREEN,4.7,1d/3d,1d/2d,-1d/2d, ColourCharge.UNSPECIFIED,ColourCharge.WHITE);
		anticharm = new Particle("anticharm_quark",new ResourceLocation(QMD.MOD_ID, "up_quark"),Color.GREEN,1276d,-2d/3d,1d/2d,1d/2d, ColourCharge.UNSPECIFIED,ColourCharge.WHITE);
		antistrange = new Particle("antistrange_quark",new ResourceLocation(QMD.MOD_ID, "up_quark"),Color.GREEN,95d,1d/3d,1d/2d,-1d/2d, ColourCharge.UNSPECIFIED,ColourCharge.WHITE);
		antitop = new Particle("antitop_quark",new ResourceLocation(QMD.MOD_ID, "up_quark"),Color.GREEN,173210d,-2d/3d,1d/2d,1d/2d, ColourCharge.UNSPECIFIED,ColourCharge.WHITE);
		antibottom = new Particle("antibottom_quark",new ResourceLocation(QMD.MOD_ID, "up_quark"),Color.GREEN,4180d,1d/3d,1d/2d,-1d/2d, ColourCharge.UNSPECIFIED,ColourCharge.WHITE);
		
		//leptons
		electron = new Particle("electron",new ResourceLocation(QMD.MOD_ID, "up_quark"),Color.GREEN,0.511,-1,1d/2d,-1d/2d);
		muon = new Particle("muon",new ResourceLocation(QMD.MOD_ID, "up_quark"),Color.GREEN,105.7,-1,1d/2d,-1d/2d);
		tau = new Particle("tau",new ResourceLocation(QMD.MOD_ID, "up_quark"),Color.GREEN,1776,-1,1d/2d,-1d/2d);
		electron_neutrino = new Particle("electron_neutrino",new ResourceLocation(QMD.MOD_ID, "up_quark"),Color.GREEN,0,0,1d/2d,1d/2d);
		muon_neutrino = new Particle("muon_neutrino",new ResourceLocation(QMD.MOD_ID, "up_quark"),Color.GREEN,0,0,1d/2d,1d/2d);
		tau_neutrino = new Particle("tau_neutrino",new ResourceLocation(QMD.MOD_ID, "up_quark"),Color.GREEN,0,0,1d/2d,1d/2d);
		
		//antileptons
		positron = new Particle("positron",new ResourceLocation(QMD.MOD_ID, "up_quark"),Color.GREEN,0.511,1,1d/2d,-1d/2d);
		antimuon = new Particle("antimuon",new ResourceLocation(QMD.MOD_ID, "up_quark"),Color.GREEN,105.7,1,1d/2d,-1d/2d);
		antitau = new Particle("antitau",new ResourceLocation(QMD.MOD_ID, "up_quark"),Color.GREEN,1776,1,1d/2d,-1d/2d);
		electron_antineutrino = new Particle("electron_antineutrino",new ResourceLocation(QMD.MOD_ID, "up_quark"),Color.GREEN,0,0,1d/2d,1d/2d);
		muon_antineutrino = new Particle("muon_antineutrino",new ResourceLocation(QMD.MOD_ID, "up_quark"),Color.GREEN,0,0,1d/2d,1d/2d);
		tau_antineutrino = new Particle("tau_antineutrino",new ResourceLocation(QMD.MOD_ID, "up_quark"),Color.GREEN,0,0,1d/2d,1d/2d);
		
		//bosons
		photon = new Particle("photon",new ResourceLocation(QMD.MOD_ID, "up_quark"),Color.GREEN,0,0,1,0);
		gluon = new Particle("gluon",new ResourceLocation(QMD.MOD_ID, "up_quark"),Color.GREEN,0,0,1,0,ColourCharge.UNSPECIFIED,ColourCharge.UNSPECIFIED);
		w_plus_boson = new Particle("w_plus_boson",new ResourceLocation(QMD.MOD_ID, "up_quark"),Color.GREEN,8038,1,1,1);
		w_minus_boson = new Particle("w_minus_boson",new ResourceLocation(QMD.MOD_ID, "up_quark"),Color.GREEN,8038,-1,1,-1);
		z_boson = new Particle("z_boson",new ResourceLocation(QMD.MOD_ID, "up_quark"),Color.GREEN,9118,0,1,0);
		higgs_boson = new Particle("higgs_boson",new ResourceLocation(QMD.MOD_ID, "up_quark"),Color.GREEN,125180,0,0,-1d/2d);
		
		
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
		
		registerPaticle(up);
		registerPaticle(antiup);
		registerPaticle(down);
		registerPaticle(antidown);
		registerPaticle(charm);
		registerPaticle(anticharm);
		registerPaticle(strange);
		registerPaticle(antistrange);
		registerPaticle(top);
		registerPaticle(antitop);
		registerPaticle(bottom);
		registerPaticle(antibottom);
		
		registerPaticle(electron);
		registerPaticle(positron);
		registerPaticle(electron_neutrino);
		registerPaticle(electron_antineutrino);
		registerPaticle(muon);
		registerPaticle(antimuon);
		registerPaticle(muon_neutrino);
		registerPaticle(muon_antineutrino);
		registerPaticle(tau);
		registerPaticle(antitau);
		registerPaticle(tau_neutrino);
		registerPaticle(tau_antineutrino);
		
		
		registerPaticle(photon);
		registerPaticle(gluon);
		registerPaticle(w_plus_boson);
		registerPaticle(w_minus_boson);
		registerPaticle(z_boson);
		registerPaticle(higgs_boson);
		
		registerPaticle(proton);
		registerPaticle(antiproton);
		registerPaticle(neutron);
		registerPaticle(antineutron);
		registerPaticle(pion_plus);
		registerPaticle(pion_naught);
		registerPaticle(pion_minus);
		registerPaticle(deuteron);
		registerPaticle(antideuteron);
		registerPaticle(alpha);
		registerPaticle(antialpha);

		
		
		
	}
	
	
	
	private static void initComposites()
	{
		proton = new Particle("proton",new ResourceLocation(QMD.MOD_ID, "up_quark"),Color.RED,938.3,1,1d/2d,0);
		proton.addComponentParticle(up);
		proton.addComponentParticle(up);
		proton.addComponentParticle(down);
		
		neutron = new Particle("neutron",new ResourceLocation(QMD.MOD_ID, "up_quark"),Color.BLUE,939.6,0,1d/2d,0);
		neutron.addComponentParticle(up);
		neutron.addComponentParticle(down);
		neutron.addComponentParticle(down);
		
		
		pion_plus = new Particle("pion_plus",new ResourceLocation(QMD.MOD_ID, "up_quark"),Color.GREEN,139.6,1,0,0);
		pion_plus.addComponentParticle(up);
		pion_plus.addComponentParticle(down);
		
		pion_naught = new Particle("pion_naught",new ResourceLocation(QMD.MOD_ID, "up_quark"),Color.GREEN,135.0,0,0,0);
		pion_plus.addComponentParticle(up);
		pion_plus.addComponentParticle(down);
		
		deuteron = new Particle("deuteron",new ResourceLocation(QMD.MOD_ID, "up_quark"),Color.GREEN,1875.0,1,0,0);
		deuteron.addComponentParticle(proton);
		deuteron.addComponentParticle(neutron);
		
		alpha = new Particle("alpha",new ResourceLocation(QMD.MOD_ID, "up_quark"),Color.GREEN,3727,2,0,0);
		alpha.addComponentParticle(proton);
		alpha.addComponentParticle(proton);
		alpha.addComponentParticle(neutron);
		alpha.addComponentParticle(neutron);
		
		
		
		antiproton = makeAntiParticle(proton, "antiproton",new ResourceLocation(QMD.MOD_ID, "up_quark"),Color.GREEN);
		antineutron = makeAntiParticle(neutron, "antineutron",new ResourceLocation(QMD.MOD_ID, "up_quark"),Color.GREEN);
		pion_minus = makeAntiParticle(pion_plus, "pion_minus",new ResourceLocation(QMD.MOD_ID, "up_quark"),Color.GREEN);
		antideuteron = makeAntiParticle(deuteron, "antideuteron",new ResourceLocation(QMD.MOD_ID, "up_quark"),Color.GREEN);
		antialpha = makeAntiParticle(alpha, "antialpha",new ResourceLocation(QMD.MOD_ID, "up_quark"),Color.GREEN);
		
		
		
	}
	
	
	public static Particle makeAntiParticle(Particle particle, String name,ResourceLocation texture, Color color)
	{
		double mass = particle.getMass();
		double charge = -particle.getCharge();
		double spin = particle.getSpin();
		double weakIsospin = particle.getWeakIsospin();
		List<Particle> anticomponent = new ArrayList<Particle>();
		for (Particle component : particle.getComponentParticles())
		{
			anticomponent.add(component.getAntiParticle());
		}

		Particle antiparticle = new Particle(name, texture, color, mass, charge, spin, weakIsospin);
		antiparticle.setComponentParticles(anticomponent);
		antiparticle.setAntiParticle(particle);
		return antiparticle;

	}
	
	
	
	
	
}
