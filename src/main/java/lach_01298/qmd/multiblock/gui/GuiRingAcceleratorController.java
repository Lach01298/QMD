package lach_01298.qmd.multiblock.gui;

import java.util.ArrayList;
import java.util.List;

import lach_01298.qmd.QMD;
import lach_01298.qmd.multiblock.accelerator.Accelerator;
import lach_01298.qmd.multiblock.accelerator.linear.LinearAcceleratorLogic;
import nc.multiblock.gui.GuiLogicMultiblockController;
import nc.multiblock.gui.GuiMultiblockController;
import nc.multiblock.gui.element.MultiblockButton;
import nc.multiblock.network.ClearAllMaterialPacket;
import nc.network.PacketHandler;
import nc.util.Lang;
import nc.util.NCMath;
import nc.util.NCUtil;
import nc.util.StringHelper;
import nc.util.UnitHelper;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;

public class GuiRingAcceleratorController extends GuiLogicMultiblockController<Accelerator, LinearAcceleratorLogic>
{

	protected final ResourceLocation gui_texture;

	

	public GuiRingAcceleratorController(Accelerator multiblock, BlockPos controllerPos, Container container)
	{
		super(multiblock, controllerPos, container);
		gui_texture = new ResourceLocation(QMD.MOD_ID + ":textures/gui/accelerator_controllerpng");
		xSize = 196;
		ySize = 187;
	}

	@Override
	protected ResourceLocation getGuiTexture()
	{
		return gui_texture;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) 
	{
	
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		// TODO Auto-generated method stub

	}
	
	public void renderTooltips(int mouseX, int mouseY)
	{

	}
	
	
	@Override
	public void initGui() 
	{
		super.initGui();
		
	}
	
	@Override
	protected void actionPerformed(GuiButton guiButton)
	{
		if (multiblock.WORLD.isRemote)
		{
			if (guiButton.id == 0 && NCUtil.isModifierKeyDown())
			{
				PacketHandler.instance.sendToServer(new ClearAllMaterialPacket(controllerPos));
			}
		}
	}

}