package lach_01298.qmd.particle;

public abstract class Particle
{

	

	
	private double mass; 	//in MeV/c^2
	private double charge;	// in e
	private double spin;	// in h bar
	private ColourCharge colour;
	private double weakisospin;
	
	
	Particle(double mass, double charge, double spin)
	{
		this.mass = mass;
		this.charge = charge;
		this.spin = spin;
	}
	
	
	public double getMass()
	{
		return this.mass;
	}
	
	public double charge()
	{
		return this.charge;
	}
	
	public double spin()
	{
		return this.spin;
	}
	
	
	
}


