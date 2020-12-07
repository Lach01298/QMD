package lach_01298.qmd.model;

import com.google.common.base.Charsets;

import lach_01298.qmd.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ModelBlock;
import net.minecraft.client.resources.IResource;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

@SideOnly(Side.CLIENT)
public class ModelHelper
{

	public static final ItemCameraTransforms DEFAULT_ITEM_TRANSFORMS = loadTransformFromJson(
			new ResourceLocation("minecraft:models/item/generated"));
	public static final ItemCameraTransforms HANDHELD_ITEM_TRANSFORMS = loadTransformFromJson(
			new ResourceLocation("minecraft:models/item/handheld"));

	public static ItemCameraTransforms loadTransformFromJson(ResourceLocation location)
	{
		try
		{

			return ModelBlock.deserialize(getReaderForResource(location)).getAllTransforms();
		}
		catch (IOException exception)
		{
			Util.getLogger().warn("Can't load resource " + location);
			exception.printStackTrace();
			return null;
		}
	}

	public static Reader getReaderForResource(ResourceLocation location) throws IOException
	{
		ResourceLocation file = new ResourceLocation(location.getNamespace(), location.getPath() + ".json");
		IResource iresource = Minecraft.getMinecraft().getResourceManager().getResource(file);
		return new BufferedReader(new InputStreamReader(iresource.getInputStream(), Charsets.UTF_8));
	}

}