package lach_01298.qmd.multiblock.gui;

import lach_01298.qmd.QMD;
import lach_01298.qmd.accelerator.tile.TileAcceleratorSource;
import lach_01298.qmd.multiblock.container.ContainerAcceleratorSource;
import nc.gui.NCGui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIAcceleratorSource extends NCGui 
{

	protected final EntityPlayer player;
	protected final TileAcceleratorSource tile;
	protected final ResourceLocation gui_textures;
	
	public GUIAcceleratorSource( EntityPlayer player, TileAcceleratorSource tile)
	{
		super(new ContainerAcceleratorSource(player, tile));
		this.player = player;
		this.tile = tile;
		gui_textures = new ResourceLocation(QMD.MOD_ID + ":textures/gui/accelerator_source.png");
		
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		String s = tile.getName();
		fontRenderer.drawString(s, xSize / 2 - fontRenderer.getStringWidth(s) / 2, 6, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1F, 1F, 1F, 1F);
		mc.getTextureManager().bindTexture(gui_textures);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}

}
