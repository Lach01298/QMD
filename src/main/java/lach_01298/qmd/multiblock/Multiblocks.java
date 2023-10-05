package lach_01298.qmd.multiblock;

import lach_01298.qmd.accelerator.Accelerator;
import lach_01298.qmd.accelerator.AcceleratorLogic;
import lach_01298.qmd.accelerator.BeamDiverterLogic;
import lach_01298.qmd.accelerator.BeamSplitterLogic;
import lach_01298.qmd.accelerator.DeceleratorLogic;
import lach_01298.qmd.accelerator.LinearAcceleratorLogic;
import lach_01298.qmd.accelerator.MassSpectrometerLogic;
import lach_01298.qmd.accelerator.RingAcceleratorLogic;
import lach_01298.qmd.accelerator.tile.IAcceleratorComponent;
import lach_01298.qmd.accelerator.tile.IAcceleratorController;
import lach_01298.qmd.accelerator.tile.TileAcceleratorBeam;
import lach_01298.qmd.accelerator.tile.TileAcceleratorBeamPort;
import lach_01298.qmd.accelerator.tile.TileAcceleratorComputerPort;
import lach_01298.qmd.accelerator.tile.TileAcceleratorCooler;
import lach_01298.qmd.accelerator.tile.TileAcceleratorEnergyPort;
import lach_01298.qmd.accelerator.tile.TileAcceleratorIonCollector;
import lach_01298.qmd.accelerator.tile.TileAcceleratorIonSource;
import lach_01298.qmd.accelerator.tile.TileAcceleratorMagnet;
import lach_01298.qmd.accelerator.tile.TileAcceleratorPort;
import lach_01298.qmd.accelerator.tile.TileAcceleratorRFCavity;
import lach_01298.qmd.accelerator.tile.TileAcceleratorRedstonePort;
import lach_01298.qmd.accelerator.tile.TileAcceleratorSynchrotronPort;
import lach_01298.qmd.accelerator.tile.TileAcceleratorVent;
import lach_01298.qmd.accelerator.tile.TileAcceleratorYoke;
import lach_01298.qmd.particleChamber.BeamDumpLogic;
import lach_01298.qmd.particleChamber.CollisionChamberLogic;
import lach_01298.qmd.particleChamber.DecayChamberLogic;
import lach_01298.qmd.particleChamber.ParticleChamber;
import lach_01298.qmd.particleChamber.ParticleChamberLogic;
import lach_01298.qmd.particleChamber.TargetChamberLogic;
import lach_01298.qmd.particleChamber.tile.IParticleChamberController;
import lach_01298.qmd.particleChamber.tile.TileParticleChamber;
import lach_01298.qmd.particleChamber.tile.TileParticleChamberBeam;
import lach_01298.qmd.particleChamber.tile.TileParticleChamberBeamPort;
import lach_01298.qmd.particleChamber.tile.TileParticleChamberDetector;
import lach_01298.qmd.particleChamber.tile.TileParticleChamberEnergyPort;
import lach_01298.qmd.particleChamber.tile.TileParticleChamberFluidPort;
import lach_01298.qmd.particleChamber.tile.TileParticleChamberPort;
import lach_01298.qmd.pipe.BeamlineLogic;
import lach_01298.qmd.pipe.IPipeController;
import lach_01298.qmd.pipe.Pipe;
import lach_01298.qmd.pipe.PipeLogic;
import lach_01298.qmd.vacuumChamber.ExoticContainmentLogic;
import lach_01298.qmd.vacuumChamber.NucleosynthesisChamberLogic;
import lach_01298.qmd.vacuumChamber.VacuumChamber;
import lach_01298.qmd.vacuumChamber.VacuumChamberLogic;
import lach_01298.qmd.vacuumChamber.tile.IVacuumChamberComponent;
import lach_01298.qmd.vacuumChamber.tile.IVacuumChamberController;
import lach_01298.qmd.vacuumChamber.tile.TileVacuumChamberBeam;
import lach_01298.qmd.vacuumChamber.tile.TileVacuumChamberBeamPort;
import lach_01298.qmd.vacuumChamber.tile.TileVacuumChamberCoil;
import lach_01298.qmd.vacuumChamber.tile.TileVacuumChamberEnergyPort;
import lach_01298.qmd.vacuumChamber.tile.TileVacuumChamberFluidPort;
import lach_01298.qmd.vacuumChamber.tile.TileVacuumChamberHeater;
import lach_01298.qmd.vacuumChamber.tile.TileVacuumChamberHeaterVent;
import lach_01298.qmd.vacuumChamber.tile.TileVacuumChamberLaser;
import lach_01298.qmd.vacuumChamber.tile.TileVacuumChamberPlasmaGlass;
import lach_01298.qmd.vacuumChamber.tile.TileVacuumChamberPlasmaNozzle;
import lach_01298.qmd.vacuumChamber.tile.TileVacuumChamberPort;
import lach_01298.qmd.vacuumChamber.tile.TileVacuumChamberRedstonePort;
import lach_01298.qmd.vacuumChamber.tile.TileVacuumChamberVent;


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
		
		try
		{
			Accelerator.LOGIC_MAP.put("", AcceleratorLogic.class.getConstructor(AcceleratorLogic.class));
			Accelerator.LOGIC_MAP.put("linear_accelerator",LinearAcceleratorLogic.class.getConstructor(AcceleratorLogic.class));
			Accelerator.LOGIC_MAP.put("ring_accelerator",RingAcceleratorLogic.class.getConstructor(AcceleratorLogic.class));
			Accelerator.LOGIC_MAP.put("beam_diverter",BeamDiverterLogic.class.getConstructor(AcceleratorLogic.class));
			Accelerator.LOGIC_MAP.put("decelerator",DeceleratorLogic.class.getConstructor(AcceleratorLogic.class));
			Accelerator.LOGIC_MAP.put("beam_splitter",BeamSplitterLogic.class.getConstructor(AcceleratorLogic.class));
			Accelerator.LOGIC_MAP.put("mass_spectrometer",MassSpectrometerLogic.class.getConstructor(AcceleratorLogic.class));
			
			ParticleChamber.LOGIC_MAP.put("", ParticleChamberLogic.class.getConstructor(ParticleChamberLogic.class));
			ParticleChamber.LOGIC_MAP.put("target_chamber",TargetChamberLogic.class.getConstructor(ParticleChamberLogic.class));
			ParticleChamber.LOGIC_MAP.put("decay_chamber",DecayChamberLogic.class.getConstructor(ParticleChamberLogic.class));
			ParticleChamber.LOGIC_MAP.put("beam_dump",BeamDumpLogic.class.getConstructor(ParticleChamberLogic.class));
			ParticleChamber.LOGIC_MAP.put("collision_chamber",CollisionChamberLogic.class.getConstructor(ParticleChamberLogic.class));
			
			VacuumChamber.LOGIC_MAP.put("", VacuumChamberLogic.class.getConstructor(VacuumChamberLogic.class));
			VacuumChamber.LOGIC_MAP.put("neutral_containment",ExoticContainmentLogic.class.getConstructor(VacuumChamberLogic.class));
			VacuumChamber.LOGIC_MAP.put("nucleosynthesis_chamber",NucleosynthesisChamberLogic.class.getConstructor(VacuumChamberLogic.class));
			
			Pipe.LOGIC_MAP.put("", PipeLogic.class.getConstructor(PipeLogic.class));
			Pipe.LOGIC_MAP.put("beamline",BeamlineLogic.class.getConstructor(PipeLogic.class));
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		
		
		
	}
}
