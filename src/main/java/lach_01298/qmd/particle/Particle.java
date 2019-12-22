package lach_01298.qmd.particle;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

public class Particle
{

	

	private String name;
	private double mass; 	//in MeV/c^2
	private double charge;	// in e
	private double spin;	// in h bar
	private ColourCharge colour;
	private ColourCharge secondColour; //only for gluons
	private double weakIsospin;
	private Particle antiparticle;
	private List<Particle> componentParticles = new ArrayList<Particle>();
	
	
	public Particle(@Nonnull String name, double mass, double charge, double spin, double weakisospin)
	{
		this.name = name;
		this.mass = mass;
		this.charge = charge;
		this.spin = spin;
		this.weakIsospin = weakisospin;
		this.colour = ColourCharge.WHITE;
		this.secondColour = ColourCharge.WHITE;
		this.antiparticle = this; // default case have to register Antiparticles
	}
	
	public Particle(@Nonnull String name, double mass, double charge, double spin, double weakisospin, ColourCharge colour,ColourCharge secondaryColour)
	{
		this.name = name;
		this.mass = mass;
		this.charge = charge;
		this.spin = spin;
		this.weakIsospin = weakisospin;
		this.colour = colour;
		this.secondColour = secondaryColour;
	}
	
	
	
	
	public String getName()
	{
		return name;
	}
	public double getMass()
	{
		return this.mass;
	}
	
	public double getCharge()
	{
		return this.charge;
	}
	
	public double getSpin()
	{
		return this.spin;
	}
	
	public double getWeakIsospin()
	{
		return this.weakIsospin;
	}

	public void setAntiParticle(Particle antiparticle)
	{
		this.antiparticle = antiparticle;
		antiparticle.antiparticle = this;
	}

	public Particle getAntiParticle()
	{
		return this.antiparticle;
	}

	public void addComponentParticle(Particle component)
	{
		componentParticles.add(component);
	}

	public void setComponentParticles(List<Particle> components)
	{
		componentParticles = components;
	}

	public List<Particle> getComponentParticles()
	{
		return componentParticles;
	}

	public boolean hasComponentParticles()
	{
		return !componentParticles.isEmpty();
	}
	
}


