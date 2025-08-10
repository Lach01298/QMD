package lach_01298.qmd.multiblock;

import lach_01298.qmd.accelerator.*;
import lach_01298.qmd.accelerator.tile.*;
import lach_01298.qmd.liquefier.LiquefierLogic;
import lach_01298.qmd.liquefier.tile.*;
import lach_01298.qmd.particleChamber.*;
import lach_01298.qmd.particleChamber.tile.*;
import lach_01298.qmd.pipe.*;
import lach_01298.qmd.vacuumChamber.*;
import lach_01298.qmd.vacuumChamber.tile.*;
import nc.multiblock.hx.HeatExchanger;


public class Multiblocks
{
	public static void init()
	{
		Accelerator.PART_CLASSES.add(IAcceleratorController.class);
		Accelerator.PART_CLASSES.add(IAcceleratorComponent.class);
		Accelerator.PART_CLASSES.add(TileAcceleratorCooler.class);
		Accelerator.PART_CLASSES.add(TileAcceleratorVent.class);
		Accelerator.PART_CLASSES.add(TileAcceleratorBeamPort.class);
		Accelerator.PART_CLASSES.add(TileAcceleratorSynchrotronPort.class);
		Accelerator.PART_CLASSES.add(TileAcceleratorEnergyPort.class);
		Accelerator.PART_CLASSES.add(TileAcceleratorBeam.class);
		Accelerator.PART_CLASSES.add(TileAcceleratorMagnet.class);
		Accelerator.PART_CLASSES.add(TileAcceleratorYoke.class);
		Accelerator.PART_CLASSES.add(TileAcceleratorRFCavity.class);
		Accelerator.PART_CLASSES.add(TileAcceleratorIonSource.class);
		Accelerator.PART_CLASSES.add(TileAcceleratorIonCollector.class);
		Accelerator.PART_CLASSES.add(TileAcceleratorPort.class);
		Accelerator.PART_CLASSES.add(TileAcceleratorComputerPort.class);
		Accelerator.PART_CLASSES.add(TileAcceleratorRedstonePort.class);
		
		Pipe.PART_CLASSES.add(IPipeController.class);
		
		ParticleChamber.PART_CLASSES.add(IParticleChamberController.class);
		ParticleChamber.PART_CLASSES.add(TileParticleChamber.class);
		ParticleChamber.PART_CLASSES.add(TileParticleChamberBeam.class);
		ParticleChamber.PART_CLASSES.add(TileParticleChamberDetector.class);
		ParticleChamber.PART_CLASSES.add(TileParticleChamberPort.class);
		ParticleChamber.PART_CLASSES.add(TileParticleChamberEnergyPort.class);
		ParticleChamber.PART_CLASSES.add(TileParticleChamberBeamPort.class);
		ParticleChamber.PART_CLASSES.add(TileParticleChamberFluidPort.class);
		
		VacuumChamber.PART_CLASSES.add(IVacuumChamberController.class);
		VacuumChamber.PART_CLASSES.add(TileVacuumChamberPort.class);
		VacuumChamber.PART_CLASSES.add(TileVacuumChamberEnergyPort.class);
		VacuumChamber.PART_CLASSES.add(TileVacuumChamberBeamPort.class);
		VacuumChamber.PART_CLASSES.add(TileVacuumChamberVent.class);
		VacuumChamber.PART_CLASSES.add(TileVacuumChamberCoil.class);
		VacuumChamber.PART_CLASSES.add(TileVacuumChamberLaser.class);
		VacuumChamber.PART_CLASSES.add(TileVacuumChamberFluidPort.class);
		VacuumChamber.PART_CLASSES.add(TileVacuumChamberBeam.class);
		VacuumChamber.PART_CLASSES.add(TileVacuumChamberPlasmaNozzle.class);
		VacuumChamber.PART_CLASSES.add(TileVacuumChamberPlasmaGlass.class);
		VacuumChamber.PART_CLASSES.add(TileVacuumChamberHeater.class);
		VacuumChamber.PART_CLASSES.add(TileVacuumChamberHeaterVent.class);
		VacuumChamber.PART_CLASSES.add(TileVacuumChamberRedstonePort.class);
		VacuumChamber.PART_CLASSES.add(IVacuumChamberComponent.class);

		HeatExchanger.PART_CLASSES.add(TileLiquefierCompressor.class);
		HeatExchanger.PART_CLASSES.add(TileLiquefierNozzle.class);
		HeatExchanger.PART_CLASSES.add(TileLiquefierFluidPort.class);
		HeatExchanger.PART_CLASSES.add(TileLiquefierEnergyPort.class);
		
		try
		{
			Accelerator.LOGIC_MAP.put("", AcceleratorLogic::new);
			Accelerator.LOGIC_MAP.put("linear_accelerator", LinearAcceleratorLogic::new);
			Accelerator.LOGIC_MAP.put("ring_accelerator", RingAcceleratorLogic::new);
			Accelerator.LOGIC_MAP.put("beam_diverter", BeamDiverterLogic::new);
			Accelerator.LOGIC_MAP.put("decelerator", DeceleratorLogic::new);
			Accelerator.LOGIC_MAP.put("beam_splitter", BeamSplitterLogic::new);
			Accelerator.LOGIC_MAP.put("mass_spectrometer", MassSpectrometerLogic::new);
			
			ParticleChamber.LOGIC_MAP.put("", ParticleChamberLogic::new);
			ParticleChamber.LOGIC_MAP.put("target_chamber", TargetChamberLogic::new);
			ParticleChamber.LOGIC_MAP.put("decay_chamber", DecayChamberLogic::new);
			ParticleChamber.LOGIC_MAP.put("beam_dump", BeamDumpLogic::new);
			ParticleChamber.LOGIC_MAP.put("collision_chamber", CollisionChamberLogic::new);
			
			VacuumChamber.LOGIC_MAP.put("", VacuumChamberLogic::new);
			VacuumChamber.LOGIC_MAP.put("neutral_containment", ExoticContainmentLogic::new);
			VacuumChamber.LOGIC_MAP.put("nucleosynthesis_chamber", NucleosynthesisChamberLogic::new);

			HeatExchanger.LOGIC_MAP.put("liquefier", LiquefierLogic::new);

			Pipe.LOGIC_MAP.put("", PipeLogic::new);
			Pipe.LOGIC_MAP.put("beamline", BeamlineLogic::new);
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		
		
		
	}
}
