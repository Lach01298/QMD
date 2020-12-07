package lach_01298.qmd;

import static nc.config.NCConfig.radiation_world_chunks_per_tick;

import java.util.Collection;
import java.util.Random;

import lach_01298.qmd.config.QMDConfig;
import lach_01298.qmd.item.ITickItem;
import nc.tile.dummy.TileDummy;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkProviderServer;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class TickItemHandler
{

	private static final Random RAND = new Random();

	@SubscribeEvent
	public void updateWorldTickItems(TickEvent.WorldTickEvent event)
	{

		if (event.phase != TickEvent.Phase.START || event.side == Side.CLIENT || !(event.world instanceof WorldServer))
		{
			return;
		}

		WorldServer world = (WorldServer) event.world;

		ChunkProviderServer chunkProvider = world.getChunkProvider();
		Collection<Chunk> loadedChunks = chunkProvider.getLoadedChunks();
		int chunkArrSize = loadedChunks.size();
		Chunk[] chunkArray = loadedChunks.toArray(new Chunk[chunkArrSize]);
		int chunkStart = RAND.nextInt(chunkArrSize + 1);
		int chunksPerTick = Math.min(QMDConfig.item_ticker_chunks_per_tick, chunkArrSize);
		
		if (chunkArrSize > 0)
		{
			for (int i = chunkStart; i < chunkStart + chunksPerTick; i++)
			{
				Chunk chunk = chunkArray[i % chunkArrSize];
				if (!chunk.isLoaded())
				{
					continue;
				}

				Collection<TileEntity> tileCollection = chunk.getTileEntityMap().values();
				TileEntity[] tileArray = tileCollection.toArray(new TileEntity[tileCollection.size()]);

				for (TileEntity tile : tileArray)
				{
					IItemHandler inventory = getTileInventory(tile, null);
					if (inventory != null) 
					{
						
						for (int j = 0; j < inventory.getSlots(); j++) 
						{
							ItemStack stack = inventory.getStackInSlot(j);
							if(stack.getItem() instanceof ITickItem)
							{
								ITickItem item = (ITickItem) stack.getItem();
								item.updateTick(stack, tile,event.world.getTotalWorldTime());
							}
						}
					}

					for(EnumFacing side : EnumFacing.values())
					{
						inventory = getTileInventory(tile, side);
						if (inventory != null) 
						{	
							for (int j = 0; j < inventory.getSlots(); j++) 
							{
								ItemStack stack = inventory.getStackInSlot(j);
								if(stack.getItem() instanceof ITickItem)
								{
									ITickItem item = (ITickItem) stack.getItem();
									item.updateTick(stack, tile,event.world.getTotalWorldTime());
								}
							}
						}
					}
				}
			}
		}
	}
	
	
	public static IItemHandler getTileInventory(ICapabilityProvider provider, EnumFacing side) 
	{
		if (!(provider instanceof TileEntity) || provider instanceof TileDummy || !provider.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side)) {
			return null;
		}
		return provider.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side);
	}

}
