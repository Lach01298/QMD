package lach_01298.qmd.particle;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Nonnull;

import lach_01298.qmd.QMD;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

public class Particle
{

	

	protected final String name;
	protected final double mass; 	//in MeV/c^2
	protected final double charge;	// in e
	protected final double spin;	// in h bar
	protected final boolean coloured;
	protected final boolean weakCharged;
	protected Particle antiparticle;
	protected HashMap<Particle, Integer> componentParticles = new HashMap<Particle, Integer>();
	
	//render stuff
	protected final ResourceLocation texture;
	
	public Particle(@Nonnull String name, ResourceLocation texture,  double mass, double charge, double spin, boolean weakCharged)
	{
		this.name = name;
		this.texture = texture;
		
		
		
		this.mass = mass;
		this.charge = charge;
		this.spin = spin;
		this.weakCharged = weakCharged;
		this.coloured = false;
		this.antiparticle = this; // default case have to register Antiparticles
	}
	


	public Particle(@Nonnull String name, ResourceLocation texture, double mass, double charge, double spin, boolean weakCharged, boolean coloured)
	{
		this.name = name;
		this.texture = texture;
		
		
		this.mass = mass;
		this.charge = charge;
		this.spin = spin;
		this.weakCharged = weakCharged;
		this.coloured = coloured;
		
		this.antiparticle = this; // default case have to register Antiparticles
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
		if(componentParticles.containsKey(component))
		{
			componentParticles.replace(component, componentParticles.get(component)+1);
		}
		else
		{
			componentParticles.put(component, 1);
		}
		
		
	}

	public void addComponentParticle(Particle component, int amount)
	{
		if(componentParticles.containsKey(component))
		{
			componentParticles.replace(component, componentParticles.get(component)+amount);
		}
		else
		{
			componentParticles.put(component, amount);
		}
	}
	
	public void setComponentParticles(HashMap<Particle, Integer> particles)
	{
		componentParticles = particles;	
	}

	public HashMap<Particle, Integer> getComponentParticles()
	{
		return componentParticles;
	}

	public boolean hasComponentParticles()
	{
		return !componentParticles.isEmpty();
	}
	
	public String getUnlocalizedName()
    {
		return QMD.MOD_ID + ".particle." + this.name + ".name";
	}


	public ResourceLocation getTexture()
	{
		return texture;
	}
	
	
	
	public boolean interactsWithEM()
	{
		return this.charge !=0;
	}
	public boolean interactsWithStrong()
	{
		return this.coloured;
	}
	
	public boolean interactsWithWeak()
	{
		return this.weakCharged;
	}
	
	
	
	
}


