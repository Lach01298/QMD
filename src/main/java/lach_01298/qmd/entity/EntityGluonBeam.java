package lach_01298.qmd.entity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class EntityGluonBeam extends EntityBeamProjectile
{

	
	public EntityGluonBeam(World world)
	{
		super(world);
	}

	public EntityGluonBeam(World world, EntityPlayer player, double length, EnumHand hand)
	{
		super(world, player, length, hand, 100);
		
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
		if (this.owner == null)
        {
            this.setDead();
        }
		else
		{
			setLocationAndAngles(owner.posX, owner.posY+owner.eyeHeight, owner.posZ, owner.rotationYaw, owner.rotationPitch);
			if(!owner.isHandActive())
			{
				 this.setDead();
			}
		}
	}
	
	
	
}
