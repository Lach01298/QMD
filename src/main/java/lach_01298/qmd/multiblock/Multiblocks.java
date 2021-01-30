package lach_01298.qmd.multiblock;

import lach_01298.qmd.accelerator.Accelerator;
import lach_01298.qmd.accelerator.AcceleratorLogic;
import lach_01298.qmd.accelerator.BeamDiverterLogic;
import lach_01298.qmd.accelerator.DeceleratorLogic;
import lach_01298.qmd.accelerator.LinearAcceleratorLogic;
import lach_01298.qmd.accelerator.RingAcceleratorLogic;
import lach_01298.qmd.accelerator.tile.IAcceleratorComponent;
import lach_01298.qmd.accelerator.tile.IAcceleratorController;
import lach_01298.qmd.accelerator.tile.TileAcceleratorBeam;
import lach_01298.qmd.accelerator.tile.TileAcceleratorBeamPort;
import lach_01298.qmd.accelerator.tile.TileAcceleratorComputerPort;
import lach_01298.qmd.accelerator.tile.TileAcceleratorCooler;
import lach_01298.qmd.accelerator.tile.TileAcceleratorEnergyPort;
import lach_01298.qmd.accelerator.tile.TileAcceleratorMagnet;
import lach_01298.qmd.accelerator.tile.TileAcceleratorPort;
import lach_01298.qmd.accelerator.tile.TileAcceleratorRFCavity;
import lach_01298.qmd.accelerator.tile.TileAcceleratorSource;
import lach_01298.qmd.accelerator.tile.TileAcceleratorSynchrotronPort;
import lach_01298.qmd.accelerator.tile.TileAcceleratorVent;
import lach_01298.qmd.accelerator.tile.TileAcceleratorYoke;
import lach_01298.qmd.containment.Containment;
import lach_01298.qmd.containment.ContainmentLogic;
import lach_01298.qmd.containment.NeutralContainmentLogic;
import lach_01298.qmd.containment.tile.IContainmentController;
import lach_01298.qmd.containment.tile.TileContainmentBeamPort;
import lach_01298.qmd.containment.tile.TileContainmentCoil;
import lach_01298.qmd.containment.tile.TileContainmentEnergyPort;
import lach_01298.qmd.containment.tile.TileContainmentLaser;
import lach_01298.qmd.containment.tile.TileContainmentPort;
import lach_01298.qmd.containment.tile.TileContainmentVent;
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
		Accelerator.PART_CLASSES.add(TileAcceleratorSource.class);
		Accelerator.PART_CLASSES.add(TileAcceleratorPort.class);
		Accelerator.PART_CLASSES.add(TileAcceleratorComputerPort.class);
		
		Pipe.PART_CLASSES.add(IPipeController.class);
		
		ParticleChamber.PART_CLASSES.add(IParticleChamberController.class);
		ParticleChamber.PART_CLASSES.add(TileParticleChamber.class);
		ParticleChamber.PART_CLASSES.add(TileParticleChamberBeam.class);
		ParticleChamber.PART_CLASSES.add(TileParticleChamberDetector.class);
		ParticleChamber.PART_CLASSES.add(TileParticleChamberPort.class);
		ParticleChamber.PART_CLASSES.add(TileParticleChamberEnergyPort.class);
		ParticleChamber.PART_CLASSES.add(TileParticleChamberBeamPort.class);
		ParticleChamber.PART_CLASSES.add(TileParticleChamberFluidPort.class);
		
		Containment.PART_CLASSES.add(IContainmentController.class);
		Containment.PART_CLASSES.add(TileContainmentPort.class);
		Containment.PART_CLASSES.add(TileContainmentEnergyPort.class);
		Containment.PART_CLASSES.add(TileContainmentBeamPort.class);
		Containment.PART_CLASSES.add(TileContainmentVent.class);
		Containment.PART_CLASSES.add(TileContainmentCoil.class);
		Containment.PART_CLASSES.add(TileContainmentLaser.class);
		
		try
		{
			Accelerator.LOGIC_MAP.put("", AcceleratorLogic.class.getConstructor(AcceleratorLogic.class));
			Accelerator.LOGIC_MAP.put("linear_accelerator",LinearAcceleratorLogic.class.getConstructor(AcceleratorLogic.class));
			Accelerator.LOGIC_MAP.put("ring_accelerator",RingAcceleratorLogic.class.getConstructor(AcceleratorLogic.class));
			Accelerator.LOGIC_MAP.put("beam_diverter",BeamDiverterLogic.class.getConstructor(AcceleratorLogic.class));
			Accelerator.LOGIC_MAP.put("decelerator",DeceleratorLogic.class.getConstructor(AcceleratorLogic.class));
			
			ParticleChamber.LOGIC_MAP.put("", ParticleChamberLogic.class.getConstructor(ParticleChamberLogic.class));
			ParticleChamber.LOGIC_MAP.put("target_chamber",TargetChamberLogic.class.getConstructor(ParticleChamberLogic.class));
			ParticleChamber.LOGIC_MAP.put("decay_chamber",DecayChamberLogic.class.getConstructor(ParticleChamberLogic.class));
			ParticleChamber.LOGIC_MAP.put("beam_dump",BeamDumpLogic.class.getConstructor(ParticleChamberLogic.class));
			ParticleChamber.LOGIC_MAP.put("collision_chamber",CollisionChamberLogic.class.getConstructor(ParticleChamberLogic.class));
			
			Containment.LOGIC_MAP.put("", ContainmentLogic.class.getConstructor(ContainmentLogic.class));
			Containment.LOGIC_MAP.put("neutral_containment",NeutralContainmentLogic.class.getConstructor(ContainmentLogic.class));
			
			Pipe.LOGIC_MAP.put("", PipeLogic.class.getConstructor(PipeLogic.class));
			Pipe.LOGIC_MAP.put("beamline",BeamlineLogic.class.getConstructor(PipeLogic.class));
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		
		
		
	}
}
