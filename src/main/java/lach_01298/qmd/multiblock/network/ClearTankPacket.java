package lach_01298.qmd.multiblock.network;

import java.util.List;

import io.netty.buffer.ByteBuf;
import lach_01298.qmd.multiblock.IMultiBlockTank;
import lach_01298.qmd.util.Util;
import nc.multiblock.Multiblock;
import nc.multiblock.tile.ITileMultiblockPart;
import nc.tile.internal.fluid.Tank;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class ClearTankPacket implements IMessage 
{
	
	private BlockPos pos;
	private int tankID;
	
	public ClearTankPacket() 
	{
		
	}
	
	public ClearTankPacket(BlockPos pos,int tankID) 
	{
		this.pos = pos;
		this.tankID = tankID;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) 
	{
		pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
		tankID = buf.readInt();
	}
	
	@Override
	public void toBytes(ByteBuf buf) 
	{
		buf.writeInt(pos.getX());
		buf.writeInt(pos.getY());
		buf.writeInt(pos.getZ());
		buf.writeInt(tankID);
	}
	
	public static class Handler implements IMessageHandler<ClearTankPacket, IMessage> 
	{
		
		@Override
		public IMessage onMessage(ClearTankPacket message, MessageContext ctx) 
		{
			if (ctx.side == Side.SERVER) 
			{
				FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> processMessage(message, ctx));
			}
			return null;
		}
		
		void processMessage(ClearTankPacket message, MessageContext ctx) 
		{
			EntityPlayerMP player = ctx.getServerHandler().player;
			World world = player.getServerWorld();
			if (!world.isBlockLoaded(message.pos) || !world.isBlockModifiable(player, message.pos)) 
			{
				return;
			}
			TileEntity tile = world.getTileEntity(message.pos);
			if (tile instanceof ITileMultiblockPart) {
				Multiblock multiblock = ((ITileMultiblockPart) tile).getMultiblock();
				if(multiblock instanceof IMultiBlockTank)
				{
					IMultiBlockTank mbTanks = (IMultiBlockTank) multiblock;
					List<Tank> tanks = mbTanks.getTanks();
					if(tanks.size() > message.tankID)
					{
						tanks.get(message.tankID).setFluid(null);
					}
					else
					{
						Util.getLogger().error("cannot clear multiblock tank " +message.tankID + " as multiblock only has "+tanks.size()+" tanks");	
					}				
				}
			}
		}
	}
}