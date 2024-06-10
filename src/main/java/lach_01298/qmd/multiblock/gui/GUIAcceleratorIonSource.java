package lach_01298.qmd.multiblock.gui;

import lach_01298.qmd.QMD;
import lach_01298.qmd.accelerator.tile.TileAcceleratorIonSource;
import lach_01298.qmd.multiblock.container.ContainerAcceleratorIonSource;
import lach_01298.qmd.multiblock.network.QMDClearTankPacket;
import nc.gui.NCGui;
import nc.gui.element.*;
import nc.util.NCUtil;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIAcceleratorIonSource extends NCGui
{

	protected final EntityPlayer player;
	protected final TileAcceleratorIonSource tile;
	protected final ResourceLocation gui_textures;
	
	public GUIAcceleratorIonSource(EntityPlayer player, TileAcceleratorIonSource tile)
	{
		super(new ContainerAcceleratorIonSource(player, tile));
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
		
		
		
		GuiFluidRenderer.renderGuiTank(tile.getTanks().get(0), guiLeft + 80, guiTop + 43, zLevel, 16, 16);
		
		
	}

	
	@Override
	public void renderTooltips(int mouseX, int mouseY)
	{
		

		drawFluidTooltip(tile.getTanks().get(0), mouseX, mouseY, 80, 43, 16, 16);

	}
	
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		super.drawScreen(mouseX, mouseY, partialTicks);

		renderTooltips(mouseX, mouseY);
	}
	
	
	@Override
	public void initGui()
	{
		super.initGui();
		buttonList.add(new NCButton.ClearTank(0, guiLeft + 80, guiTop + 43, 16, 16));

	}

	@Override
	protected void actionPerformed(GuiButton guiButton)
	{
		if (tile.getWorld().isRemote)
		{
			if(NCUtil.isModifierKeyDown())
			{
				switch(guiButton.id)
				{
				case 0:
					new QMDClearTankPacket(tile.getTilePos(),2).sendToServer();
					break;
				}
				
			}

		}
	}
	
	
	
}
