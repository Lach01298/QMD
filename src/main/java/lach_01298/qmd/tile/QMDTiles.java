package lach_01298.qmd.tile;

import lach_01298.qmd.EnumTypes;
import lach_01298.qmd.QMD;
import lach_01298.qmd.Util;
import lach_01298.qmd.multiblock.accelerator.tile.TileAcceleratorCooler;
import lach_01298.qmd.multiblock.accelerator.tile.TileAcceleratorEnergyPort;
import lach_01298.qmd.multiblock.accelerator.tile.TileAcceleratorGlass;
import lach_01298.qmd.multiblock.accelerator.tile.TileAcceleratorInlet;
import lach_01298.qmd.multiblock.accelerator.tile.TileAcceleratorMagnet;
import lach_01298.qmd.multiblock.accelerator.tile.TileAcceleratorOutlet;
import lach_01298.qmd.multiblock.accelerator.tile.TileAcceleratorRFCavity;
import lach_01298.qmd.multiblock.accelerator.tile.TileAcceleratorSource;
import lach_01298.qmd.multiblock.accelerator.tile.TileAcceleratorYoke;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import lach_01298.qmd.multiblock.accelerator.tile.TileLinearAcceleratorController;
import lach_01298.qmd.multiblock.accelerator.tile.TileRingAcceleratorController;
import lach_01298.qmd.multiblock.accelerator.tile.TileAcceleratorBeam;
import lach_01298.qmd.multiblock.accelerator.tile.TileAcceleratorBeamPort;
import lach_01298.qmd.multiblock.accelerator.tile.TileAcceleratorCasing;

public class QMDTiles
{
	private static ResourceLocation acceleratorPath = new ResourceLocation(QMD.MOD_ID,"accelerator_");
	private static ResourceLocation magnetPath = new ResourceLocation(QMD.MOD_ID,"accelerator_magnet");
	private static ResourceLocation cavityPath = new ResourceLocation(QMD.MOD_ID,"accelerator_cavity");
	private static ResourceLocation coolerPath = new ResourceLocation(QMD.MOD_ID,"accelerator_cooler");

	
	public static void register() 
	{
		
		//other
		GameRegistry.registerTileEntity(TileBeamline.class,new ResourceLocation(QMD.MOD_ID,"beamline"));
		
		
		//Accelerator parts
		GameRegistry.registerTileEntity(TileLinearAcceleratorController.class,Util.appendPath(acceleratorPath, "linear_controller"));
		GameRegistry.registerTileEntity(TileRingAcceleratorController.class,Util.appendPath(acceleratorPath, "ring_controller"));
		GameRegistry.registerTileEntity(TileAcceleratorBeam.class,Util.appendPath(acceleratorPath, "beam"));
		GameRegistry.registerTileEntity(TileAcceleratorCasing.class,Util.appendPath(acceleratorPath, "casing"));
		GameRegistry.registerTileEntity(TileAcceleratorGlass.class,Util.appendPath(acceleratorPath, "glass"));
		GameRegistry.registerTileEntity(TileAcceleratorInlet.class,Util.appendPath(acceleratorPath, "inlet"));
		GameRegistry.registerTileEntity(TileAcceleratorOutlet.class,Util.appendPath(acceleratorPath, "outlet"));
		GameRegistry.registerTileEntity(TileAcceleratorBeamPort.class,Util.appendPath(acceleratorPath, "beam_port"));
		GameRegistry.registerTileEntity(TileAcceleratorSource.class,Util.appendPath(acceleratorPath, "source"));
		GameRegistry.registerTileEntity(TileAcceleratorYoke.class,Util.appendPath(acceleratorPath, "yoke"));
		GameRegistry.registerTileEntity(TileAcceleratorEnergyPort.class,Util.appendPath(acceleratorPath, "energy_port"));
		
		//magnets
		GameRegistry.registerTileEntity(TileAcceleratorMagnet.Copper.class, Util.appendPath(magnetPath, EnumTypes.MagnetType.COPPER.getName()));
		GameRegistry.registerTileEntity(TileAcceleratorMagnet.MagnesiumDiboride.class, Util.appendPath(magnetPath, EnumTypes.MagnetType.MAGNESIUM_DIBORIDE.getName()));
		GameRegistry.registerTileEntity(TileAcceleratorMagnet.NiobiumTin.class, Util.appendPath(magnetPath, EnumTypes.MagnetType.NIOBIUM_TIN.getName()));
		
		//RF Cavities
		GameRegistry.registerTileEntity(TileAcceleratorRFCavity.Copper.class, Util.appendPath(cavityPath, EnumTypes.RFCavityType.COPPER.getName()));
		GameRegistry.registerTileEntity(TileAcceleratorRFCavity.MagnesiumDiboride.class, Util.appendPath(cavityPath, EnumTypes.RFCavityType.MAGNESIUM_DIBORIDE.getName()));
		GameRegistry.registerTileEntity(TileAcceleratorRFCavity.NiobiumTin.class, Util.appendPath(cavityPath, EnumTypes.RFCavityType.NIOBIUM_TIN.getName()));

	
		
		//coolers
		GameRegistry.registerTileEntity(TileAcceleratorCooler.Water.class, Util.appendPath(coolerPath, EnumTypes.CoolerType1.WATER.getName()));
		GameRegistry.registerTileEntity(TileAcceleratorCooler.Iron.class, Util.appendPath(coolerPath, EnumTypes.CoolerType1.IRON.getName()));
		GameRegistry.registerTileEntity(TileAcceleratorCooler.Redstone.class, Util.appendPath(coolerPath, EnumTypes.CoolerType1.REDSTONE.getName()));
		GameRegistry.registerTileEntity(TileAcceleratorCooler.Quartz.class, Util.appendPath(coolerPath, EnumTypes.CoolerType1.QUARTZ.getName()));
		GameRegistry.registerTileEntity(TileAcceleratorCooler.Obsidian.class, Util.appendPath(coolerPath, EnumTypes.CoolerType1.OBSIDIAN.getName()));
		GameRegistry.registerTileEntity(TileAcceleratorCooler.NetherBrick.class, Util.appendPath(coolerPath, EnumTypes.CoolerType1.NETHER_BRICK.getName()));
		GameRegistry.registerTileEntity(TileAcceleratorCooler.Glowstone.class,Util.appendPath(coolerPath, EnumTypes.CoolerType1.GLOWSTONE.getName()));
		GameRegistry.registerTileEntity(TileAcceleratorCooler.Lapis.class, Util.appendPath(coolerPath, EnumTypes.CoolerType1.LAPIS.getName()));
		GameRegistry.registerTileEntity(TileAcceleratorCooler.Gold.class,Util.appendPath(coolerPath, EnumTypes.CoolerType1.GOLD.getName()));
		GameRegistry.registerTileEntity(TileAcceleratorCooler.Prismarine.class, Util.appendPath(coolerPath, EnumTypes.CoolerType1.PRISMARINE.getName()));
		GameRegistry.registerTileEntity(TileAcceleratorCooler.Slime.class, Util.appendPath(coolerPath, EnumTypes.CoolerType1.SLIME.getName()));
		GameRegistry.registerTileEntity(TileAcceleratorCooler.EndStone.class, Util.appendPath(coolerPath, EnumTypes.CoolerType1.END_STONE.getName()));
		GameRegistry.registerTileEntity(TileAcceleratorCooler.Purpur.class, Util.appendPath(coolerPath, EnumTypes.CoolerType1.PURPUR.getName()));
		GameRegistry.registerTileEntity(TileAcceleratorCooler.Diamond.class, Util.appendPath(coolerPath, EnumTypes.CoolerType1.DIAMOND.getName()));
		GameRegistry.registerTileEntity(TileAcceleratorCooler.Emerald.class, Util.appendPath(coolerPath, EnumTypes.CoolerType1.EMERALD.getName()));
		GameRegistry.registerTileEntity(TileAcceleratorCooler.Copper.class, Util.appendPath(coolerPath, EnumTypes.CoolerType1.COPPER.getName()));
		GameRegistry.registerTileEntity(TileAcceleratorCooler.Tin.class,  Util.appendPath(coolerPath, EnumTypes.CoolerType2.TIN.getName()));
		GameRegistry.registerTileEntity(TileAcceleratorCooler.Lead.class, Util.appendPath(coolerPath, EnumTypes.CoolerType2.LEAD.getName()));
		GameRegistry.registerTileEntity(TileAcceleratorCooler.Boron.class, Util.appendPath(coolerPath, EnumTypes.CoolerType2.BORON.getName()));
		GameRegistry.registerTileEntity(TileAcceleratorCooler.Lithium.class, Util.appendPath(coolerPath, EnumTypes.CoolerType2.LITHIUM.getName()));
		GameRegistry.registerTileEntity(TileAcceleratorCooler.Magnesium.class, Util.appendPath(coolerPath, EnumTypes.CoolerType2.MAGNESIUM.getName()));
		GameRegistry.registerTileEntity(TileAcceleratorCooler.Manganese.class, Util.appendPath(coolerPath, EnumTypes.CoolerType2.MANGANESE.getName()));
		GameRegistry.registerTileEntity(TileAcceleratorCooler.Aluminum.class, Util.appendPath(coolerPath, EnumTypes.CoolerType2.ALUMINUM.getName()));
		GameRegistry.registerTileEntity(TileAcceleratorCooler.Silver.class, Util.appendPath(coolerPath, EnumTypes.CoolerType2.SILVER.getName()));
		GameRegistry.registerTileEntity(TileAcceleratorCooler.Fluorite.class, Util.appendPath(coolerPath, EnumTypes.CoolerType2.FLUORITE.getName()));
		GameRegistry.registerTileEntity(TileAcceleratorCooler.Villiaumite.class, Util.appendPath(coolerPath, EnumTypes.CoolerType2.VILLIAUMITE.getName()));
		GameRegistry.registerTileEntity(TileAcceleratorCooler.Carobbiite.class, Util.appendPath(coolerPath, EnumTypes.CoolerType2.CAROBBIITE.getName()));
		GameRegistry.registerTileEntity(TileAcceleratorCooler.Arsenic.class, Util.appendPath(coolerPath, EnumTypes.CoolerType2.ARSENIC.getName()));
		GameRegistry.registerTileEntity(TileAcceleratorCooler.LiquidNitrogen.class, Util.appendPath(coolerPath, EnumTypes.CoolerType2.LIQUID_NITROGEN.getName()));
		GameRegistry.registerTileEntity(TileAcceleratorCooler.LiquidHelium.class, Util.appendPath(coolerPath, EnumTypes.CoolerType2.LIQUID_HELIUM.getName()));
		GameRegistry.registerTileEntity(TileAcceleratorCooler.Enderium.class, Util.appendPath(coolerPath, EnumTypes.CoolerType2.ENDERIUM.getName()));
		GameRegistry.registerTileEntity(TileAcceleratorCooler.Cryotheum.class, Util.appendPath(coolerPath, EnumTypes.CoolerType2.CRYOTHEUM.getName()));
	}
}
