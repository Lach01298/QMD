package lach_01298.qmd.tile;

import lach_01298.qmd.QMD;
import lach_01298.qmd.accelerator.tile.*;
import lach_01298.qmd.liquefier.tile.TileLiquefierController;
import lach_01298.qmd.multiblock.container.*;
import lach_01298.qmd.multiblock.gui.*;
import lach_01298.qmd.particleChamber.tile.*;
import lach_01298.qmd.pipe.TileBeamline;
import lach_01298.qmd.vacuumChamber.tile.*;
import nc.ModCheck;
import nc.gui.GuiFunction;
import nc.tile.TileContainerInfo;

import static nc.handler.TileInfoHandler.*;

public class QMDTileInfoHandler {
	
	public static void preInit() {
		registerContainerInfo(new TileContainerInfo<>(QMD.MOD_ID, "linear_accelerator_controller", TileLinearAcceleratorController.class, ContainerLinearAcceleratorController::new, clientGetGuiInfoTileFunction(() -> GuiLinearAcceleratorController::new)));
		registerContainerInfo(new TileContainerInfo<>(QMD.MOD_ID, "ring_accelerator_controller", TileRingAcceleratorController.class, ContainerRingAcceleratorController::new, clientGetGuiInfoTileFunction(() -> GuiRingAcceleratorController::new)));
		registerContainerInfo(new TileContainerInfo<>(QMD.MOD_ID, "beam_diverter_controller", TileBeamDiverterController.class, ContainerBeamDiverterController::new, clientGetGuiInfoTileFunction(() -> GuiBeamDiverterController::new)));
		registerContainerInfo(new TileContainerInfo<>(QMD.MOD_ID, "decelerator_controller", TileDeceleratorController.class, ContainerDeceleratorController::new, clientGetGuiInfoTileFunction(() -> GuiDeceleratorController::new)));
		registerContainerInfo(new TileContainerInfo<>(QMD.MOD_ID, "beam_splitter_controller", TileBeamSplitterController.class, ContainerBeamSplitterController::new, clientGetGuiInfoTileFunction(() -> GuiBeamSplitterController::new)));
		registerContainerInfo(new TileContainerInfo<>(QMD.MOD_ID, "mass_spectrometer_controller", TileMassSpectrometerController.class, ContainerMassSpectrometerController::new, clientGetGuiInfoTileFunction(() -> GuiMassSpectrometerController::new)));
		
		registerContainerInfo(new TileContainerInfo<>(QMD.MOD_ID, "target_chamber_controller", TileTargetChamberController.class, ContainerTargetChamberController::new, clientGetGuiInfoTileFunction(() -> GuiTargetChamberController::new)));
		registerContainerInfo(new TileContainerInfo<>(QMD.MOD_ID, "decay_chamber_controller", TileDecayChamberController.class, ContainerDecayChamberController::new, clientGetGuiInfoTileFunction(() -> GuiDecayChamberController::new)));
		registerContainerInfo(new TileContainerInfo<>(QMD.MOD_ID, "beam_dump_controller", TileBeamDumpController.class, ContainerBeamDumpController::new, clientGetGuiInfoTileFunction(() -> GuiBeamDumpController::new)));
		registerContainerInfo(new TileContainerInfo<>(QMD.MOD_ID, "collision_chamber_controller", TileCollisionChamberController.class, ContainerCollisionChamberController::new, clientGetGuiInfoTileFunction(() -> GuiCollisionChamberController::new)));
		
		registerContainerInfo(new TileContainerInfo<>(QMD.MOD_ID, "neutral_containment_controller", TileExoticContainmentController.class, ContainerExoticContainmentController::new, clientGetGuiInfoTileFunction(() -> GuiNeutralContainmentController::new)));
		registerContainerInfo(new TileContainerInfo<>(QMD.MOD_ID, "nucleosynthesis_chamber_controller", TileNucleosynthesisChamberController.class, ContainerNucleosynthesisChamberController::new, clientGetGuiInfoTileFunction(() -> GuiNucleosynthesisChamberController::new)));
		
		registerContainerInfo(new TileContainerInfo<>(QMD.MOD_ID, "beamline", TileBeamline.class, null, (GuiFunction<TileBeamline>) null));

		registerContainerInfo(new TileContainerInfo<>(QMD.MOD_ID, "liquefier_controller", TileLiquefierController.class, ContainerLiquefierController::new, clientGetGuiInfoTileFunction(() -> GUILiquefierController::new)));
	}
	
	public static void init() {
		if (ModCheck.jeiLoaded()) {
		
		}
	}
}
