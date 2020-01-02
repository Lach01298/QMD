package lach_01298.qmd.particle;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

public class Particle
{

	

	protected final String name;
	protected final double mass; 	//in MeV/c^2
	protected final double charge;	// in e
	protected final double spin;	// in h bar
	protected final ColourCharge colour;
	protected final ColourCharge secondColour; //only for gluons
	protected final double weakIsospin;
	protected Particle antiparticle;
	protected List<Particle> componentParticles = new ArrayList<Particle>();
	
	//render stuff
	protected final ResourceLocation texture;
	protected int color = 0xFFFFFFFF;
	
	public Particle(@Nonnull String name, ResourceLocation texture, Color color,  double mass, double charge, double spin, double weakisospin)
	{
		this.name = name;
		this.texture = texture;
		this.setColor(color);
		
		
		
		this.mass = mass;
		this.charge = charge;
		this.spin = spin;
		this.weakIsospin = weakisospin;
		this.colour = ColourCharge.WHITE;
		this.secondColour = ColourCharge.WHITE;
		this.antiparticle = this; // default case have to register Antiparticles
	}
	


	public Particle(@Nonnull String name, ResourceLocation texture, Color color, double mass, double charge, double spin, double weakisospin, ColourCharge colour,ColourCharge secondaryColour)
	{
		this.name = name;
		this.texture = texture;
		this.setColor(color);
		
		this.mass = mass;
		this.charge = charge;
		this.spin = spin;
		this.weakIsospin = weakisospin;
		this.colour = colour;
		this.secondColour = secondaryColour;
		this.antiparticle = this; // default case have to register Antiparticles
	}
	
	public Particle setColor(Color color)
    {
        this.color = color.getRGB();
        return this;
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
	
	public String getUnlocalizedName()
    {
		return "particle." + this.name;
	}

	public int getColor()
	{
		return color;
	}

	public ResourceLocation getTexture()
	{
		return texture;
	}
}


