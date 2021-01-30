package lach_01298.qmd.entity;

import java.awt.Color;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class EntityLeptonBeam extends EntityBeamProjectile
{

	public Color color;
	public EntityLeptonBeam(World world)
	{
		super(world);
	}

	public EntityLeptonBeam(World world, EntityPlayer player, double length, EnumHand hand, Integer color)
	{
		super(world, player, length, hand, 3);
		this.color = new Color(color);
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

}
