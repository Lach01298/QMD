package lach_01298.qmd.network;

import io.netty.buffer.ByteBuf;
import lach_01298.qmd.QMD;
import lach_01298.qmd.entity.EntityBeamProjectile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.network.simpleimpl.*;

public class BeamProjectileUpdatePacket extends QMDPacket
{

	public int entityId;
	public int ownerEntityId;
	public double length;
	public boolean mainHand;
	public boolean validMessage;

	//clientside constructor
	public BeamProjectileUpdatePacket()
	{
		validMessage = false;
	}
	
	
	public BeamProjectileUpdatePacket(EntityBeamProjectile projectile)
	{
		this.entityId = projectile.getEntityId();
		
		if(projectile.getOwner() != null)
		{
			
			this.ownerEntityId = projectile.getOwner().getEntityId();
			this.length = projectile.getLength();
			if(projectile.getHand() == EnumHand.MAIN_HAND)
			{
				mainHand = true;
			}
			else
			{
				mainHand = false;
			}
			
			validMessage = true;
		}
		else
		{
			validMessage = false;
		}
		
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		entityId = buf.readInt();
		ownerEntityId = buf.readInt();
		length = buf.readDouble();
		mainHand = buf.readBoolean();
		validMessage = buf.readBoolean();
		
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(entityId);
		buf.writeInt(ownerEntityId);
		buf.writeDouble(length);
		buf.writeBoolean(mainHand);
		buf.writeBoolean(validMessage);
	}

	public static class Handler implements IMessageHandler<BeamProjectileUpdatePacket, IMessage>
	{
		@Override
		public IMessage onMessage(BeamProjectileUpdatePacket message, MessageContext ctx)
		{

			if (ctx.side.isClient())
			{
				if (message.validMessage)
				{

					EntityPlayer player = QMD.proxy.getPlayerClient();

					if (player.world.getEntityByID(message.entityId) instanceof EntityBeamProjectile)
					{
						EntityBeamProjectile beam = (EntityBeamProjectile) player.world.getEntityByID(message.entityId);

						if (player.world.getEntityByID(message.ownerEntityId) instanceof EntityPlayer)
						{
							beam.setOwner((EntityPlayer) player.world.getEntityByID(message.ownerEntityId));
							beam.setLength(message.length);
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
