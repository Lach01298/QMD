package lach_01298.qmd.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;






public abstract class EntityBeamProjectile extends Entity
{
	public double length = 1;
	protected int livingTime;
	public EntityPlayer owner;
	public EnumHand hand;
	
	public EntityBeamProjectile(World world)
	{
		super(world);
		this.livingTime = 100;
	}
	public EntityBeamProjectile(World world, EntityPlayer player, double length, EnumHand hand, int lifetime)
	{
		super(world);
		this.setLocationAndAngles(player.posX, player.posY+player.eyeHeight, player.posZ ,player.rotationYaw, player.rotationPitch);
		owner = player;
		this.hand = hand;
		this.length = length;
		this.livingTime = lifetime;
		this.setSize(0.25f,0.25f);
		
		this.motionX = (double) (-Math.sin(this.rotationYaw / 180.0F * (float) Math.PI)
				* Math.cos(this.rotationPitch / 180.0F * (float) Math.PI) * 0.4f);
		this.motionZ = (double) (Math.cos(this.rotationYaw / 180.0F * (float) Math.PI)
				* Math.cos(this.rotationPitch / 180.0F * (float) Math.PI) * 0.4f);
		this.motionY = (double) (-Math.sin((this.rotationPitch) / 180.0F * (float) Math.PI) * 0.4f);
		
	}

	@Override
	public AxisAlignedBB getRenderBoundingBox() 
	{
		
		Vec3d pos2 = new Vec3d(this.motionX, this.motionY, this.motionZ).normalize().scale(length);
		return new AxisAlignedBB(this.posX, this.posY, this.posZ, this.posX+pos2.z, this.posY + pos2.y, this.posZ + pos2.z);
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
		
		if (this.owner == null)
        {
            this.setDead();
        }
		
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
