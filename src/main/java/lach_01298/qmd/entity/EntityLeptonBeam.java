package lach_01298.qmd.entity;

import lach_01298.qmd.network.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

import java.awt.*;

public class EntityLeptonBeam extends EntityBeamProjectile
{

	private Color color;
	public EntityLeptonBeam(World world)
	{
		super(world);
	}

	public EntityLeptonBeam(World world, EntityPlayer player, double length, EnumHand hand, Integer color)
	{
		super(world, player, length, hand, 4);
		this.color = new Color(color);
		
	}

	
	public void setColour(int color)
	{
		this.color = new Color(color);
	}
	
	public Color getColor()
	{
		return this.color;
	}
	
	@Override
	protected void entityInit()
	{
	
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound compound)
	{
		super.readEntityFromNBT(compound);
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound compound)
	{
		super.writeEntityToNBT(compound);
	}
	
	@Override
	protected void sendUpdatePacket()
	{
		if (!this.world.isRemote)
		{
			QMDPackets.wrapper.sendToAllAround(new LeptonBeamUpdatePacket(this), new TargetPoint(this.world.provider.getDimension(), this.posX,this.posY,this.posZ, 128));
		}
	}

}
