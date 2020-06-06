package lach_01298.qmd.machine.gui;

import lach_01298.qmd.QMD;
import lach_01298.qmd.machine.tile.TileItemDamageFuelProcessor;
import nc.gui.NCGui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public abstract class GuiItemDamageFuelMachine extends NCGui {
	
	protected final EntityPlayer player;
	protected final TileItemDamageFuelProcessor tile;
	protected final ResourceLocation gui_textures;
	

	public GuiItemDamageFuelMachine(String name, EntityPlayer player, TileItemDamageFuelProcessor tile, Container inventory) 
	{
		super(inventory);
		this.player = player;
		this.tile = tile;
		gui_textures = new ResourceLocation(QMD.MOD_ID + ":textures/gui/" + name + ".png");
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) 
	{
		String s = tile.getDisplayName().getUnformattedText();
		fontRenderer.drawString(s, xSize / 2 - fontRenderer.getStringWidth(s) / 2, 6, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) 
	{
		GlStateManager.color(1F, 1F, 1F, 1F);
		mc.getTextureManager().bindTexture(gui_textures);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}
	
	protected int getCookProgressScaled(double pixels) 
	{
		double i = tile.time;
		double j = tile.baseProcessTime;
		return j != 0D ? (int) Math.round(i * pixels / j) : 0;
	}
	
	@Override
	protected void actionPerformed(GuiButton guiButton) 
	{
		/*if (tile.getWorld().isRemote) {
			if (guiButton != null) if (guiButton instanceof NCButton) {
				
			}
		}*/
	}
	
	
	
	
	
}