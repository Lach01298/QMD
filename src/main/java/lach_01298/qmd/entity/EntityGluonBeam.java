package lach_01298.qmd.entity;

import lach_01298.qmd.item.QMDItems;
import lach_01298.qmd.sound.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.*;

public class EntityGluonBeam extends EntityBeamProjectile
{

	@SideOnly(Side.CLIENT)
	private ISound sound;
	
	
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
		
		if(world.isRemote)
		{
			if(this.ticksExisted == 1)
			{
				playStartSound();
			}
			else
			{
				playSound();
			}
		}
		else
		{
			if(owner != null)
			{
				setLocationAndAngles(owner.posX, owner.posY+owner.eyeHeight, owner.posZ, owner.rotationYaw, owner.rotationPitch);
				if(!owner.isHandActive()||owner.getHeldItem(hand).getItem() != QMDItems.gluonGun)
				{
					this.setDead();
				}
				
			}
			else
			{
				if (this.livingTime <= 0)
		        {
		            this.setDead();
		        }
				else
				{
					--this.livingTime;
				}
			}
			sendUpdatePacket();
		}

	}
	
	
	 public void setDead()
	 {
		 if(world.isRemote)
	     {
	    	 playStopSound();
	     }
		 this.isDead = true;
	  }
	
	
	@SideOnly(Side.CLIENT)
	private void playStopSound()
	{
		if(sound != null && Minecraft.getMinecraft().getSoundHandler().isSoundPlaying(sound))
		{
			Minecraft.getMinecraft().getSoundHandler().stopSound(sound);
		}
		world.playSound(posX, posY, posZ, QMDSounds.gluon_gun_stop, SoundCategory.NEUTRAL, 0.1f, 1.0f, false);
	}
	
	@SideOnly(Side.CLIENT)
	private void playStartSound()
	{
		sound = new MovingSoundGluonGunStart(this);
		Minecraft.getMinecraft().getSoundHandler().playSound(sound);
	}
	
	@SideOnly(Side.CLIENT)
	private void playSound()
	{
		if(sound == null || !Minecraft.getMinecraft().getSoundHandler().isSoundPlaying(sound))
		{
			sound = new MovingSoundGluonGun(this);
			Minecraft.getMinecraft().getSoundHandler().playSound(sound);
		}
	}
	
	
	
}
