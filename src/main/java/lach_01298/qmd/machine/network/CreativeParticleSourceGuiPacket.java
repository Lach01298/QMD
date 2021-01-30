package lach_01298.qmd.machine.network;

import java.util.ArrayList;
import java.util.List;

import io.netty.buffer.ByteBuf;
import lach_01298.qmd.particle.ParticleStorage;
import lach_01298.qmd.particle.ParticleStorageSource;
import lach_01298.qmd.tile.TileCreativeParticleSource;
import lach_01298.qmd.util.ByteUtil;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class CreativeParticleSourceGuiPacket implements IMessage
{

	boolean messageValid;
	
	BlockPos pos;
	public List<ParticleStorageSource> beams;
	
	public CreativeParticleSourceGuiPacket()
	{
		messageValid = false;
		beams = new ArrayList<ParticleStorageSource>();
	}
	
	public CreativeParticleSourceGuiPacket(TileCreativeParticleSource tile)
	{
		pos = tile.getTilePos();
		beams = (List<ParticleStorageSource>) tile.getParticleBeams();
		messageValid = true;
	}
	
	
	@Override
	public void fromBytes(ByteBuf buf)
	{
		try
		{
			pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
			
			int size = buf.readInt();
			for (int i = 0; i < size; i++)
			{
				ParticleStorage storage = ByteUtil.readBufBeam(buf);
				ParticleStorageSource beam = new ParticleStorageSource();
				beam.setParticleStack(storage.getParticleStack());
				beams.add(beam);
			}
		}
		catch (IndexOutOfBoundsException e)
		{
			e.printStackTrace();
			return;
		}
		messageValid = true;

	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		if (!messageValid)
			return;
		buf.writeInt(pos.getX());
		buf.writeInt(pos.getY());
		buf.writeInt(pos.getZ());
		
		buf.writeInt(beams.size());
		for(ParticleStorageSource beam : beams)
		{
			ByteUtil.writeBufBeam(beam, buf);
		}

	}
	
	
	public static class Handler implements IMessageHandler<CreativeParticleSourceGuiPacket, IMessage>
	{

		@Override
		public IMessage onMessage(CreativeParticleSourceGuiPacket message, MessageContext ctx)
		{
			if (!message.messageValid && ctx.side != Side.SERVER)
				return null;
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler)
					.addScheduledTask(() -> processMessage(message, ctx));
			return null;
		}

		void processMessage(CreativeParticleSourceGuiPacket message, MessageContext ctx)
		{
			EntityPlayerMP player = ctx.getServerHandler().player;
			TileEntity tile = player.getServerWorld().getTileEntity(message.pos);
			if (tile instanceof TileCreativeParticleSource)
			{
				TileCreativeParticleSource source = (TileCreativeParticleSource) tile;
				source.setParticleBeams(message.beams);
				source.markDirtyAndNotify(true);
			}
		}
	}
	
	
	
	

}
