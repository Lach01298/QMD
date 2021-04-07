package lach_01298.qmd;

import lach_01298.qmd.block.QMDBlocks;
import lach_01298.qmd.enums.MaterialTypes.CanisterType;
import lach_01298.qmd.enums.MaterialTypes.LuminousPaintType;
import lach_01298.qmd.enums.MaterialTypes.SourceType;
import lach_01298.qmd.item.QMDItems;
import nc.radiation.RadSources;
import net.minecraft.item.ItemStack;

public class QMDRadSources
{
	
	private static final double FLUID_MULTIPLIER = 125D / 18D;
	public static final double SODIUM_22 = 0.384;
	public static final double BERYLLIUM_7 = 6.89;
	public static final double URANIUM_234 = 0.00000407;
	public static final double PROTACTINIUM_231 = 0.0000305;
	public static final double COBALT_60 = 0.190;
	public static final double IRIDIUM_192 = 4.94;
	public static final double COPERNICIUM_291 = 0.000833;
	
	public static final double MIX_291 = RadSources.getFuelRadiation(COPERNICIUM_291, 8, COPERNICIUM_291, 1);
	public static final double DEPLETED_MIX_291 =  RadSources.getDepletedFuelRadiation(RadSources.AMERICIUM_243, 4, RadSources.CURIUM_243, 2, RadSources.CURIUM_245, 1,  RadSources.BERKELIUM_247, 1, RadSources.RUTHENIUM_106, RadSources.EUROPIUM_155, 1.5D, 0.6D);
	public static final double MIX_291_FISSION = (MIX_291 + DEPLETED_MIX_291) / 64D;
	
	public static void init() 
	{
		RadSources.putMaterial(BERYLLIUM_7, "Beryllium7");
		RadSources.putMaterial(SODIUM_22, "Sodium22");
		RadSources.putMaterial(URANIUM_234, "Uranium234");
		RadSources.putMaterial(PROTACTINIUM_231, "Protactinium231");
		RadSources.putMaterial(COBALT_60, "Cobalt60");
		RadSources.putMaterial(IRIDIUM_192, "Iridium192");
		
		RadSources.put(SODIUM_22/2D, new ItemStack(QMDItems.source,1,SourceType.SODIUM_22.getID()));
		RadSources.put(COBALT_60/2D, new ItemStack(QMDItems.source,1,SourceType.COBALT_60.getID()));
		RadSources.put(IRIDIUM_192/2D, new ItemStack(QMDItems.source,1,SourceType.IRIDIUM_192.getID()));
		
		RadSources.put(RadSources.STRONTIUM_90/4D, QMDBlocks.rtgStrontium);
		
		RadSources.putFluid(RadSources.TRITIUM, "antitritium");
		
		RadSources.putOre(RadSources.CAESIUM_137, "wasteFissionHeavy");
		RadSources.putOre(RadSources.CAESIUM_137, "wasteFissionLight");
		RadSources.putOre(RadSources.CAESIUM_137, "wasteSpallationCalifornium");
		RadSources.putOre(RadSources.CAESIUM_137, "wasteSpallationBerkelium");
		RadSources.putOre(RadSources.CAESIUM_137, "wasteSpallationCurium");
		RadSources.putOre(RadSources.CAESIUM_137, "wasteSpallationAmericium");
		RadSources.putOre(RadSources.CAESIUM_137, "wasteSpallationPlutonium");
		RadSources.putOre(RadSources.CAESIUM_137, "wasteSpallationNeptunium");
		RadSources.putOre(RadSources.CAESIUM_137, "wasteSpallationUranium");
		RadSources.putOre(RadSources.CAESIUM_137, "wasteSpallationProtactinium");
		RadSources.putOre(RadSources.CAESIUM_137, "wasteSpallationThorium");
		RadSources.putOre(RadSources.CAESIUM_137, "wasteSpallationRadium");
		RadSources.putOre(RadSources.CAESIUM_137, "wasteSpallationPolonium");
		RadSources.putOre(RadSources.CAESIUM_137, "wasteSpallationBismuth");
		RadSources.putOre(RadSources.CAESIUM_137, "wasteSpallationLead");
		RadSources.putOre(RadSources.CAESIUM_137, "wasteSpallationGold");
		RadSources.putOre(RadSources.CAESIUM_137, "wasteSpallationPlatinum");
		RadSources.putOre(RadSources.CAESIUM_137, "wasteSpallationIridium");
		RadSources.putOre(RadSources.CAESIUM_137, "wasteSpallationOsmium");
		RadSources.putOre(RadSources.CAESIUM_137, "wasteSpallationTungsten");
		RadSources.putOre(RadSources.CAESIUM_137, "wasteSpallationHafnium");
		
		RadSources.putOre(RadSources.TRITIUM*FLUID_MULTIPLIER*0.1, "cellAntitritium");
		RadSources.put(RadSources.TRITIUM*FLUID_MULTIPLIER*0.1, new ItemStack(QMDItems.canister,1,CanisterType.TRITIUM.getID()));
		
		RadSources.put(RadSources.RADIUM/16D, new ItemStack(QMDItems.luminousPaint,1,LuminousPaintType.GREEN.getID()));
		RadSources.put(RadSources.RADIUM/16D, new ItemStack(QMDItems.luminousPaint,1,LuminousPaintType.BLUE.getID()));
		RadSources.put(RadSources.RADIUM/16D, new ItemStack(QMDItems.luminousPaint,1,LuminousPaintType.ORANGE.getID()));
		RadSources.put(RadSources.RADIUM/16D, QMDBlocks.greenLuminousPaint);
		RadSources.put(RadSources.RADIUM/16D, QMDBlocks.blueLuminousPaint);
		RadSources.put(RadSources.RADIUM/16D, QMDBlocks.orangeLuminousPaint);
		
		
		RadSources.putIsotope(COPERNICIUM_291, "Copernicium291", "copernicium_291");
		RadSources.putFuel(MIX_291, DEPLETED_MIX_291, "MIX291", "mix_291");
		
		RadSources.addToFoodMaps(new ItemStack(QMDItems.flesh), 0, 3.0);
	}
	
	
	
}
