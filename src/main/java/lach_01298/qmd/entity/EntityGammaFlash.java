package lach_01298.qmd.entity;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityGammaFlash extends Entity
{

	 private int livingTime;
	 private double size;
	
	public EntityGammaFlash(World worldIn)
	{
		super(worldIn);
		this.livingTime = 50;
		this.size = 1;
	}
	 
	public EntityGammaFlash(World worldIn, double x, double y, double z, double size)
	{
		super(worldIn);
		this.setLocationAndAngles(x, y, z, 0.0F, 0.0F);
		this.livingTime = 500;
		this.size = size;
	}

	public double getSize()
	{
		return size;
	}
	
	
	@Override
	protected void entityInit()
	{
	}


	@Override
	protected void readEntityFromNBT(NBTTagCompound compound)
	{	
	}


	@Override
	protected void writeEntityToNBT(NBTTagCompound compound)
	{	
	}
	
	@Override
	public void onUpdate()
	{
		super.onUpdate();
		
		if (this.livingTime == 0)
        {
            this.setDead();
        }
		else
		{
			--this.livingTime;
		}
		
		
		
	}

}
