package lach_01298.qmd.machine.gui;

import java.io.IOException;

import org.lwjgl.input.Keyboard;

import lach_01298.qmd.QMD;
import lach_01298.qmd.gui.GuiParticle;
import lach_01298.qmd.machine.container.ContainerCreativeParticleSource;
import lach_01298.qmd.machine.network.CreativeParticleSourceGuiPacket;
import lach_01298.qmd.network.QMDPacketHandler;
import lach_01298.qmd.particle.ParticleStack;
import lach_01298.qmd.particle.Particles;
import lach_01298.qmd.tile.TileCreativeParticleSource;
import nc.gui.NCGui;
import nc.util.Lang;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiCreativeParticleSource extends NCGui
{

	protected final EntityPlayer player;
	protected final TileCreativeParticleSource tile;
	protected final ResourceLocation gui_textures;
	
	private GuiTextField particleNameField;
    private GuiTextField amountField;
    private GuiTextField energyField;
    private GuiTextField focusField;
    
    private String particleName = "";
    private int amount = 0;
    private long energy = 0;
    private double focus = 0;
	
	private final GuiParticle guiParticle;
	
	public GuiCreativeParticleSource(EntityPlayer player, TileCreativeParticleSource tile)
	{
		super(new ContainerCreativeParticleSource(player, tile));
		this.player = player;
		this.tile = tile;
		gui_textures = new ResourceLocation(QMD.MOD_ID + ":textures/gui/creative_particle_source.png");
		xSize = 176;
		ySize = 115;
		guiParticle = new GuiParticle(this);
		particleName = tile.getParticleName();
		amount = tile.getParticleAmount();
		energy = tile.getParticleEnergy();
		focus = tile.getParticleFocus();
	}


	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) 
	{
		String s = tile.getDisplayName().getUnformattedText();
		fontRenderer.drawString(s, xSize / 2 - fontRenderer.getStringWidth(s) / 2, 4, 4210752);
		
		fontRenderer.drawString(Lang.localise("gui.qmd.container.creative_particle_source.particle_name"),  10, 15, 4210752);
		fontRenderer.drawString(Lang.localise("gui.qmd.container.creative_particle_source.particle_amount"),  10, 40, 4210752);
		fontRenderer.drawString(Lang.localise("gui.qmd.container.creative_particle_source.particle_energy"),  10, 65, 4210752);
		fontRenderer.drawString(Lang.localise("gui.qmd.container.creative_particle_source.particle_focus"),  10, 90, 4210752);
	}
	
	
	@Override
	public void renderTooltips(int mouseX, int mouseY)
	{

		guiParticle.drawToolTipBoxwithFocus(tile.getParticleBeams().get(0).getParticleStack(), guiLeft + 134, guiTop + 43, mouseX, mouseY);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) 
	{
		GlStateManager.color(1F, 1F, 1F, 1F);
		mc.getTextureManager().bindTexture(gui_textures);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		guiParticle.drawParticleStack(tile.getParticleBeams().get(0).getParticleStack(), guiLeft + 134, guiTop + 43);
	}

	 public void drawScreen(int mouseX, int mouseY, float partialTicks)
	 {
		 super.drawScreen(mouseX, mouseY, partialTicks);
		 this.particleNameField.drawTextBox();
		 this.amountField.drawTextBox();
		 this.energyField.drawTextBox();
		 this.focusField.drawTextBox();
		 
		
	 }
	
	
	
	@Override
	public void initGui() 
	{
		super.initGui();
 
		Keyboard.enableRepeatEvents(true); 
		this.buttonList.add(new GuiButton(0, guiLeft+118, guiTop+16, 50, 20, Lang.localise("gui.qmd.container.creative_particle_source.set")));
		this.particleNameField = new GuiTextField(1, this.fontRenderer, guiLeft+10, guiTop+25, 100, 10);
		this.particleNameField.setText(particleName);

		this.amountField = new GuiTextField(2, this.fontRenderer, guiLeft+10, guiTop+50, 100, 10);
		this.amountField.setText(Integer.toString(amount));
		
		this.energyField = new GuiTextField(3, this.fontRenderer, guiLeft+ 10, guiTop+75, 100, 10);
		this.energyField.setText(Long.toString(energy));
		
		this.focusField = new GuiTextField(4, this.fontRenderer, guiLeft+10, guiTop+100, 100, 10);
		this.focusField.setText(Double.toString(focus));
		
		this.particleNameField.setFocused(true);
	}
	
	
	@Override
	protected void actionPerformed(GuiButton guiButton) 
	{
		if (tile.getWorld().isRemote)
		{
			if (guiButton.id == 0)
			{			
				ParticleStack stack = new ParticleStack(Particles.getParticleFromName(particleName), amount, energy, focus);
				if(stack.getParticle() != null)
				{
					tile.getParticleBeams().get(0).setParticleStack(stack);
				}
				QMDPacketHandler.instance.sendToServer(new CreativeParticleSourceGuiPacket(tile));
			}
		}
		
		
		
	}
	
    /**
     * Fired when a key is typed (except F11 which toggles full screen). This is the equivalent of
     * KeyListener.keyTyped(KeyEvent e). Args : character (character on the key), keyCode (lwjgl Keyboard key code)
     */
    protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
    	if (keyCode == 1)
        {
            this.mc.player.closeScreen();
        }
    	
    	if (this.particleNameField.isFocused())
        {

    		
    		this.particleNameField.textboxKeyTyped(typedChar, keyCode);
        	this.particleName = this.particleNameField.getText();
        }
        
        if (this.amountField.isFocused())
        {
            this.amountField.textboxKeyTyped(typedChar, keyCode);
            try {
            	this.amount = Integer.valueOf(this.amountField.getText());
            } catch (Exception e) 
            {
              
            }
            
        }
        
        if (this.energyField.isFocused())
        {
			this.energyField.textboxKeyTyped(typedChar, keyCode);
			try
			{
				this.energy = Long.valueOf(this.energyField.getText());
			}
			catch (Exception e)
			{

			}
            
        }
        
        if (this.focusField.isFocused())
        {
			this.focusField.textboxKeyTyped(typedChar, keyCode);
			try
			{
				this.focus = Double.valueOf(this.focusField.getText());
			}
			catch (Exception e)
			{

			}
        }
  
    }
	
    
    /**
     * Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
     */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        this.particleNameField.mouseClicked(mouseX, mouseY, mouseButton);
        this.amountField.mouseClicked(mouseX, mouseY, mouseButton);
        this.energyField.mouseClicked(mouseX, mouseY, mouseButton);
        this.focusField.mouseClicked(mouseX, mouseY, mouseButton);
      
    }

	
	
}
