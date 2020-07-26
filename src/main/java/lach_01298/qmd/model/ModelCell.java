package lach_01298.qmd.model;

import java.awt.Color;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import lach_01298.qmd.QMD;
import lach_01298.qmd.item.QMDItems;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ItemLayerModel;
import net.minecraftforge.client.model.ItemTextureQuadConverter;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.client.model.PerspectiveMapWrapper;
import net.minecraftforge.client.model.SimpleModelState;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelCell implements IModel
{

	public static ModelCell model = new ModelCell(new ResourceLocation("qmd:items/cell_cover"),
			new ResourceLocation("qmd:items/cell"));

	public static final ModelResourceLocation MODEL_LOCATION = new ModelResourceLocation(
			new ResourceLocation(QMD.MOD_ID, "cell"), "default");

	private static final float NORTH_Z_FLUID = 7.6f / 16f;
	private static final float SOUTH_Z_FLUID = 8.4f / 16f;

	public static void init()
	{
		ModelLoader.setCustomMeshDefinition(QMDItems.cell, stack -> MODEL_LOCATION);
		ModelBakery.registerItemVariants(QMDItems.cell, MODEL_LOCATION);
		ModelLoaderRegistry.registerLoader(new CellLoader());
	}

	private final ResourceLocation baseTexture;
	private final ResourceLocation emptyTexture;
	private final Fluid fluid;

	public ModelCell(ResourceLocation baseTexture, ResourceLocation emptyTexture)
	{
		this(baseTexture, emptyTexture, null);
	}

	public ModelCell(ResourceLocation baseTexture, ResourceLocation emptyTexture, Fluid fluid)
	{
		this.baseTexture = baseTexture;
		this.emptyTexture = emptyTexture;
		this.fluid = fluid;
	}

	@Override
	public Collection<ResourceLocation> getDependencies()
	{
		return ImmutableList.of();
	}

	@Override
	public Collection<ResourceLocation> getTextures()
	{
		return ImmutableList.of(baseTexture, emptyTexture);
	}

	@Override
	public IBakedModel bake(IModelState state, VertexFormat format,
			Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter)
	{

		ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transformMap = PerspectiveMapWrapper
				.getTransforms(state);
		TRSRTransformation transform = state.apply(Optional.empty()).orElse(TRSRTransformation.identity());

		ImmutableList.Builder<BakedQuad> builder = ImmutableList.builder();
		builder.addAll(new ItemLayerModel(ImmutableList.of(baseTexture)).bake(transform, format, bakedTextureGetter)
				.getQuads(null, null, 0L));

		ResourceLocation sprite = fluid != null ? fluid.getStill() : emptyTexture;
		int color = fluid != null ? fluid.getColor() : Color.WHITE.getRGB();
		TextureAtlasSprite fluidSprite = bakedTextureGetter.apply(sprite);
		if (fluid != null)
		{
			if (fluidSprite != null)
			{
				builder.add(ItemTextureQuadConverter.genQuad(format, transform, 5, 2, 11, 14, NORTH_Z_FLUID,
						fluidSprite, EnumFacing.NORTH, color, -1));
				builder.add(ItemTextureQuadConverter.genQuad(format, transform, 5, 2, 11, 14, SOUTH_Z_FLUID,
						fluidSprite, EnumFacing.SOUTH, color, -1));
			}
		}

		return new BakedCell(builder.build(), this, bakedTextureGetter.apply(baseTexture), format, transformMap);
	}

	@Override
	public IModelState getDefaultState()
	{
		return TRSRTransformation.identity();
	}

	public static class CellLoader implements ICustomModelLoader
	{

		@Override
		public boolean accepts(ResourceLocation modelLocation)
		{
			return modelLocation.getNamespace().equals("qmd") && modelLocation.getPath().contains("cell");
		}

		@Override
		public IModel loadModel(ResourceLocation modelLocation) throws Exception
		{
			return model;
		}

		@Override
		public void onResourceManagerReload(IResourceManager resourceManager)
		{
		}

	}

	public static class BakedCell implements IBakedModel
	{

		private final List<BakedQuad> quads;
		private final ModelCell parent;
		private final TextureAtlasSprite particle;
		private final VertexFormat format;
		private final ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transformMap;

		public BakedCell(List<BakedQuad> quads, ModelCell parent, TextureAtlasSprite particle, VertexFormat format,
				ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transformMap)
		{
			this.transformMap = transformMap;
			this.quads = quads;
			this.parent = parent;
			this.particle = particle;
			this.format = format;
		}

		@Override
		public List<BakedQuad> getQuads(@Nullable IBlockState state, @Nullable EnumFacing side, long rand)
		{
			return quads;
		}

		@Override
		public boolean isAmbientOcclusion()
		{
			return true;
		}

		@Override
		public boolean isGui3d()
		{
			return false;
		}

		@Override
		public boolean isBuiltInRenderer()
		{
			return false;
		}

		@Override
		public TextureAtlasSprite getParticleTexture()
		{
			return particle;
		}

		@Override
		public ItemCameraTransforms getItemCameraTransforms()
		{
			return ModelHelper.DEFAULT_ITEM_TRANSFORMS;
		}

		@Override
		public ItemOverrideList getOverrides()
		{
			return OVERRIDES;
		}

	}

	public static final OverrideHandler OVERRIDES = new OverrideHandler();

	public static class OverrideHandler extends ItemOverrideList
	{

		private final HashMap<String, IBakedModel> modelCache = new HashMap<>();

		private final Function<ResourceLocation, TextureAtlasSprite> textureGetter = location -> Minecraft
				.getMinecraft().getTextureMapBlocks().getAtlasSprite(location.toString());

		private OverrideHandler()
		{
			super(ImmutableList.of());
		}

		@Override
		public IBakedModel handleItemState(IBakedModel originalModel, ItemStack stack, World world,
				EntityLivingBase entity)
		{
			FluidStack fluidStack = FluidUtil.getFluidContained(stack);

			if (fluidStack == null)
				return originalModel; // return default bucket

			String name = fluidStack.getFluid().getName();
			if (!modelCache.containsKey(name))
			{
				BakedCell bakedCell = (BakedCell) originalModel;
				ModelCell model = new ModelCell(bakedCell.parent.baseTexture, bakedCell.parent.emptyTexture,
						fluidStack.getFluid());
				modelCache.put(name,
						model.bake(new SimpleModelState(bakedCell.transformMap), bakedCell.format, textureGetter));
			}

			return modelCache.get(name);
		}

	}

}
