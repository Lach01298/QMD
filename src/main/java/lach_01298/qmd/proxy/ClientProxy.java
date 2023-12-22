package lach_01298.qmd.proxy;

import static lach_01298.qmd.config.QMDConfig.clientPreInit;

import lach_01298.qmd.QMD;
import lach_01298.qmd.QMDTooltipHandler;
import lach_01298.qmd.item.QMDArmour;
import lach_01298.qmd.render.ArmPositionHandler;
import lach_01298.qmd.render.DrillBlockRenderHandler;
import lach_01298.qmd.render.QMDRenderHandler;
import lach_01298.qmd.render.entity.BeamRenderer;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy 
{

	@Override
	public void preInit(FMLPreInitializationEvent preEvent)
	{
		super.preInit(preEvent);
		clientPreInit();
		QMDRenderHandler.init();
		MinecraftForge.EVENT_BUS.register(DrillBlockRenderHandler.INSTANCE);
		
		
	}

	@Override
	public void init(FMLInitializationEvent event)
	{
		super.init(event);
		ItemColors itemcolors = Minecraft.getMinecraft().getItemColors();
		itemcolors.registerItemColorHandler(new IItemColor()
		{
			public int colorMultiplier(ItemStack stack, int tintIndex)
			{
				return tintIndex > 0 ? -1 : ((ItemArmor) stack.getItem()).getColor(stack);
			}
		}, QMDArmour.helm_hev, QMDArmour.chest_hev, QMDArmour.legs_hev, QMDArmour.boots_hev);

	}

	@Override
	public void postInit(FMLPostInitializationEvent postEvent)
	{
		super.postInit(postEvent);
		MinecraftForge.EVENT_BUS.register(new QMDTooltipHandler());
		MinecraftForge.EVENT_BUS.register(new ArmPositionHandler());
		MinecraftForge.EVENT_BUS.register(new BeamRenderer());
		((IReloadableResourceManager) Minecraft.getMinecraft().getResourceManager()).registerReloadListener(DrillBlockRenderHandler.INSTANCE);
	}


	@Override
	public EntityPlayer getPlayerClient() 
	{
		return Minecraft.getMinecraft().player;
	}


	@Override
	public void registerFluidBlockRendering(Block block, String name)
	{
		super.registerFluidBlockRendering(block, name);
		FluidStateMapper mapper = new FluidStateMapper(name);

		Item item = Item.getItemFromBlock(block);
		ModelBakery.registerItemVariants(item);
		ModelLoader.setCustomMeshDefinition(item, mapper);

		// ModelLoader.setCustomStateMapper(block, new
		// StateMap.Builder().ignore(block.LEVEL).build());
		ModelLoader.setCustomStateMapper(block, mapper);
	}

	public static class FluidStateMapper extends StateMapperBase implements ItemMeshDefinition
	{
		public final ModelResourceLocation location;

		public FluidStateMapper(String name)
		{
			location = new ModelResourceLocation(QMD.MOD_ID + ":fluids", name);
		}

		@Override
		protected ModelResourceLocation getModelResourceLocation(IBlockState state)
		{
			return location;
		}

		@Override
		public ModelResourceLocation getModelLocation(ItemStack stack)
		{
			return location;
		}
	}
}