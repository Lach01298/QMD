package lach_01298.qmd.model;

import net.minecraft.client.model.*;
import net.minecraftforge.fml.relauncher.*;

@SideOnly(Side.CLIENT)
public class ModelMaterial extends ModelBase
{
	public ModelRenderer box;
	
	public ModelMaterial()
	{
		this.box = (new ModelRenderer(this, 0, 0)).setTextureSize(16, 16);
		this.box.addBox(0f, 0f, 0f, 16, 16, 16,0.0F);
	}
	
	
	public void renderAll()
    {
		this.box.render(1);
    }
	
}
