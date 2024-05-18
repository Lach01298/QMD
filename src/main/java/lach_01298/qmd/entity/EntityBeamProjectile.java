package lach_01298.qmd.entity;

import lach_01298.qmd.network.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;


public abstract class EntityBeamProjectile extends Entity
{
	protected double length = 1;
	protected int livingTime;
	protected EntityPlayer owner;
	protected EnumHand hand;
	
	public EntityBeamProjectile(World world)
	{
		super(world);
		this.livingTime = 100;
		this.setSize(0.25f,0.25f);
		
	}
	public EntityBeamProjectile(World world, EntityPlayer player, double length, EnumHand hand, int lifetime)
	{
		super(world);
		this.setSize(0.25f,0.25f);
		
		this.setLocationAndAngles(player.posX, player.posY+player.eyeHeight, player.posZ ,player.rotationYaw, player.rotationPitch);

		owner = player;
		
		this.hand = hand;
		this.length = length;
		this.livingTime = lifetime;
		
		
	}

	@Override
	public AxisAlignedBB getRenderBoundingBox()
	{
		return new AxisAlignedBB(this.posX-this.length, this.posY-this.length, this.posZ-this.length, this.posX+this.length, this.posY*2+this.length, this.posZ+this.length);
	}

	
	
	public void setOwner(EntityPlayer owner)
	{
		this.owner = owner;
	}

	public void setLength(double length)
	{
		this.length = length;
	}
	public void setHand(EnumHand hand)
	{
		this.hand= hand;
	}
	
	public EntityPlayer getOwner()
	{
		return owner;
	}

	public double getLength()
	{
		return length;
	}
	public EnumHand getHand()
	{
		return hand;
	}

	
	
	
	@Override
	protected void entityInit()
	{
	
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound compound)
	{
		livingTime = compound.getInteger("livingTime");
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound compound)
	{
		compound.setInteger("livingTime", livingTime);
	}

	@Override
	public void onUpdate()
	{
		super.onUpdate();
		
		if (this.livingTime <= 0)
        {
            this.setDead();
        }
		else
		{
			--this.livingTime;
			
		}
		sendUpdatePacket();
	}
	
	protected void sendUpdatePacket()
	{
		if (!this.world.isRemote)
		{
			QMDPacketHandler.instance.sendToAllAround(new BeamProjectileUpdatePacket(this), new TargetPoint(this.world.provider.getDimension(), this.posX,this.posY,this.posZ, 128));
		}
	}

}
