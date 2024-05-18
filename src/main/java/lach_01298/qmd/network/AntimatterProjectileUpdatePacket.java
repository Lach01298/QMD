package lach_01298.qmd.network;

import io.netty.buffer.ByteBuf;
import lach_01298.qmd.QMD;
import lach_01298.qmd.entity.EntityAntimatterProjectile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.*;

public class AntimatterProjectileUpdatePacket implements IMessage
{

	public int entityId;
	public int color;
	public boolean validMessage;

	//clientside constructor
	public AntimatterProjectileUpdatePacket()
	{
		validMessage = false;
	}
	
	
	public AntimatterProjectileUpdatePacket(EntityAntimatterProjectile projectile)
	{
		this.entityId = projectile.getEntityId();
		this.color = projectile.getColor().getRGB();;
		validMessage = true;

	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		entityId = buf.readInt();
		color = buf.readInt();
		validMessage = buf.readBoolean();
		
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(entityId);
		buf.writeInt(color);
		buf.writeBoolean(validMessage);
	}

	public static class Handler implements IMessageHandler<AntimatterProjectileUpdatePacket, IMessage>
	{
		@Override
		public IMessage onMessage(AntimatterProjectileUpdatePacket message, MessageContext ctx)
		{
			
			if (ctx.side.isClient())
			{
				if (message.validMessage)
				{
					EntityPlayer player = QMD.proxy.getPlayerClient();

					if (player.world.getEntityByID(message.entityId) instanceof EntityAntimatterProjectile)
					{
						EntityAntimatterProjectile projectile = (EntityAntimatterProjectile) player.world.getEntityByID(message.entityId);

						
						projectile.setColor(message.color);
					}
				}
			}
			return null;
		}
	}
}
