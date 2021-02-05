package lach_01298.qmd.network;

import java.awt.Color;

import io.netty.buffer.ByteBuf;
import lach_01298.qmd.QMD;
import lach_01298.qmd.entity.EntityBeamProjectile;
import lach_01298.qmd.entity.EntityLeptonBeam;
import lach_01298.qmd.proxy.ClientProxy;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class LeptonBeamUpdatePacket extends BeamProjectileUpdatePacket
{

	public int color;

	
	public LeptonBeamUpdatePacket()
	{
		super();
	}
	
	
	public LeptonBeamUpdatePacket(EntityLeptonBeam projectile)
	{
		super(projectile);
		if(validMessage)
		{
			this.color = projectile.getColor().getRGB();
		}
		
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		super.fromBytes(buf);
		color = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		super.toBytes(buf);
		buf.writeInt(color);
	}

	public static class Handler implements IMessageHandler<LeptonBeamUpdatePacket, IMessage>
	{
		@Override
		public IMessage onMessage(LeptonBeamUpdatePacket message, MessageContext ctx)
		{
			
			if (ctx.side.isClient())
			{
				
				if (message.validMessage)
				{
					
					EntityPlayer player = QMD.proxy.getPlayerClient();
					
					if (player.world.getEntityByID(message.entityId) instanceof EntityLeptonBeam)
					{
						EntityLeptonBeam beam = (EntityLeptonBeam) player.world.getEntityByID(message.entityId);

						if (player.world.getEntityByID(message.ownerEntityId) instanceof EntityPlayer)
						{
							beam.setOwner((EntityPlayer) player.world.getEntityByID(message.ownerEntityId));
							beam.setLength(message.length);
							beam.setColour(message.color);
							
							if (message.mainHand)
							{
								beam.setHand(EnumHand.MAIN_HAND);
							}
							else
							{
								beam.setHand(EnumHand.OFF_HAND);
							}
						}
					}
				}
			}
			return null;
		}
	}
}
