package lach_01298.qmd.containment;

import static lach_01298.qmd.recipes.QMDRecipes.neutral_containment;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import lach_01298.qmd.QMD;
import lach_01298.qmd.containment.tile.IContainmentController;
import lach_01298.qmd.containment.tile.TileContainmentBeamPort;
import lach_01298.qmd.containment.tile.TileContainmentCoil;
import lach_01298.qmd.containment.tile.TileContainmentLaser;
import lach_01298.qmd.containment.tile.TileNeutralContainmentController;
import lach_01298.qmd.enums.EnumTypes.IOType;
import lach_01298.qmd.multiblock.container.ContainerNeutralContainmentController;
import lach_01298.qmd.multiblock.network.ContainmentRenderPacket;
import lach_01298.qmd.multiblock.network.ContainmentResendFormPacket;
import lach_01298.qmd.multiblock.network.ContainmentUpdatePacket;
import lach_01298.qmd.multiblock.network.NeutralContainmentUpdatePacket;
import lach_01298.qmd.network.QMDPacketHandler;
import lach_01298.qmd.particle.ParticleStack;
import lach_01298.qmd.particle.ParticleStorageAccelerator;
import lach_01298.qmd.recipe.QMDRecipe;
import lach_01298.qmd.recipe.QMDRecipeInfo;
import nc.multiblock.Multiblock;
import nc.multiblock.container.ContainerMultiblockController;
import nc.multiblock.tile.TileBeefAbstract.SyncReason;
import nc.multiblock.turbine.tile.ITurbineController;
import nc.network.PacketHandler;
import nc.tile.internal.fluid.Tank;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class NeutralContainmentLogic extends ContainmentLogic
{

	public long particle1WorkDone, particle2WorkDone, recipeParticle1Work =600, recipeParticle2Work = 600;
	
	public QMDRecipeInfo<QMDRecipe> recipeInfo;
	
	public QMDRecipeInfo<QMDRecipe> rememberedRecipeInfo;
	
	
	public NeutralContainmentLogic(ContainmentLogic oldLogic)
	{
		super(oldLogic);
		getMultiblock().tanks.add(new Tank(1, null));
		getMultiblock().beams.add(new ParticleStorageAccelerator());
	}

	@Override
	public String getID()
	{
		return "neutral_containment";
	}

	// Multiblock Validation
	
	
	@Override
	public int getMinimumInteriorLength()
	{
		return 5;
	}

	@Override
	public int getMaximumInteriorLength()
	{
		return 9;
	}
	
	
	
	
	public boolean isMachineWhole(Multiblock multiblock) 
	{
		Containment con = getMultiblock();
		
		if (con.getExteriorLengthX() != getMultiblock().getExteriorLengthZ())
		{
			multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.containment.must_be_square", null);
			return false;
		}
		
		if (con.getExteriorLengthX() % 2 != 1)
		{
			multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.containment.must_be_odd", null);
			return false;
		}
		
		
		
		//coils
		for (BlockPos pos : getCoilPositions())
		{
			if (!(con.WORLD.getTileEntity(pos) instanceof TileContainmentCoil))
			{
				multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.containment.neutral.must_be_coil", pos);
				return false;
			}
		}
		
		Axis axis;
		BlockPos westMiddle = new BlockPos(con.getMinX(), con.getMiddleY(), con.getMiddleZ());
		BlockPos northMiddle = new BlockPos(con.getMiddleX(), con.getMiddleY(), con.getMinZ());
		
		if(multiblock.WORLD.getTileEntity(westMiddle) instanceof TileContainmentLaser)
		{
			axis = Axis.X;
		}
		else if(con.WORLD.getTileEntity(westMiddle) instanceof TileContainmentBeamPort)
		{
			axis = Axis.Z;
		}
		else
		{
			multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.containment.neutral.beam_port_or_laser", westMiddle);
			return false;
		}
		
		if(axis == Axis.X)
		{
			if(!(con.WORLD.getTileEntity(westMiddle.add(con.getInteriorLengthX()+1,0,0)) instanceof TileContainmentLaser))
			{
				multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.containment.neutral.must_be_laser", westMiddle.add(con.getInteriorLengthX()+1,0,0));
				return false;
			}
			
			if(!(con.WORLD.getTileEntity(northMiddle) instanceof TileContainmentBeamPort))
			{
				multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.containment.neutral.must_be_input_beam", northMiddle);
				return false;
			}
			else
			{
				TileContainmentBeamPort beam =(TileContainmentBeamPort) con.WORLD.getTileEntity(northMiddle);
				if(beam.getIOType() != IOType.INPUT)
				{
					multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.containment.neutral.must_be_input_beam", northMiddle);
					return false;
				}
			}
			
			if(!(con.WORLD.getTileEntity(northMiddle.add(0, 0, con.getInteriorLengthZ()+1)) instanceof TileContainmentBeamPort))
			{
				multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.containment.neutral.must_be_input_beam", northMiddle.add(0, 0, con.getInteriorLengthZ()+1));
				return false;
			}
			else
			{
				TileContainmentBeamPort beam =(TileContainmentBeamPort) con.WORLD.getTileEntity(northMiddle.add(0, 0, con.getInteriorLengthZ()+1));
				if(beam.getIOType() != IOType.INPUT)
				{
					multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.containment.neutral.must_be_input_beam", northMiddle.add(0, 0, con.getInteriorLengthZ()+1));
					return false;
				}
			}

		}
		else
		{
			if(!(con.WORLD.getTileEntity(westMiddle) instanceof TileContainmentBeamPort))
			{
				multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.containment.neutral.must_be_input_beam", westMiddle);
				return false;
			}
			else
			{
				TileContainmentBeamPort beam =(TileContainmentBeamPort) con.WORLD.getTileEntity(westMiddle);
				if(beam.getIOType() != IOType.INPUT)
				{
					multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.containment.neutral.must_be_input_beam", westMiddle);
					return false;
				}
			}
			
			if(!(con.WORLD.getTileEntity(westMiddle.add(con.getInteriorLengthX()+1,0,0)) instanceof TileContainmentBeamPort))
			{
				multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.containment.neutral.must_be_input_beam", westMiddle.add(con.getInteriorLengthX()+1,0,0));
				return false;
			}
			else
			{
				TileContainmentBeamPort beam =(TileContainmentBeamPort) con.WORLD.getTileEntity(westMiddle.add(con.getInteriorLengthX()+1,0,0));
				if(beam.getIOType() != IOType.INPUT)
				{
					multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.containment.neutral.must_be_input_beam", westMiddle.add(con.getInteriorLengthX()+1,0,0));
					return false;
				}
			}
		
			if(!(con.WORLD.getTileEntity(northMiddle) instanceof TileContainmentLaser))
			{
				multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.containment.neutral.must_be_laser", northMiddle);
				return false;
			}
			
			if(!(con.WORLD.getTileEntity(northMiddle.add(0, 0, con.getInteriorLengthZ()+1)) instanceof TileContainmentLaser))
			{
				multiblock.setLastError(QMD.MOD_ID + ".multiblock_validation.containment.neutral.must_be_laser", northMiddle.add(0, 0, con.getInteriorLengthZ()+1));
				return false;
			}
				
		}

		for (IContainmentController controller : getParts(IContainmentController.class))
		{
			controller.setIsRenderer(false);
		}
		for (IContainmentController controller : getParts(IContainmentController.class))
		{
			System.out.println("h");
			controller.setIsRenderer(true);
			break;
		}
		
		return super.isMachineWhole(multiblock);
	}
	
	
	
	
	
	public Set<BlockPos> getCoilPositions()
	{
		Set<BlockPos> postions = new HashSet<BlockPos>();
		Containment con = getMultiblock();
		
		boolean top = false;
		for(int i =0; i < 2; i++)
		{
			for (BlockPos pos : BlockPos.getAllInBoxMutable(
					con.getExtremeInteriorCoord(false, top, false).add(1, 0, 1),
					con.getExtremeInteriorCoord(true, top, false).add(-1, 0, 1)))
			{
				postions.add(pos.toImmutable());
			}
			for (BlockPos pos : BlockPos.getAllInBoxMutable(
					con.getExtremeInteriorCoord(false, top, true).add(1, 0, -1),
					con.getExtremeInteriorCoord(true, top, true).add(-1, 0, -1)))
			{
				postions.add(pos.toImmutable());
			}
			for (BlockPos pos : BlockPos.getAllInBoxMutable(
					con.getExtremeInteriorCoord(false, top, false).add(1, 0, 2),
					con.getExtremeInteriorCoord(false, top, true).add(1, 0, -2)))
			{
				postions.add(pos.toImmutable());
			}
			for (BlockPos pos : BlockPos.getAllInBoxMutable(
					con.getExtremeInteriorCoord(true, top, false).add(-1, 0, 2),
					con.getExtremeInteriorCoord(true, top, true).add(-1,0,-2)))
			{
				postions.add(pos.toImmutable());
			}
			top = true;
		}
		return postions;
	}
	
	
	
	
	
	
	
	
	// Multiblock Methods
	
	@Override
	public void onContainmentFormed()
	{
		getMultiblock().tanks.get(2).setCapacity((int) (Math.pow((getMultiblock().getInteriorLengthX()-4),3)*16000));
		if (!getWorld().isRemote)
		{
			int energy = 0;
			long heat = 0;
			for(TileContainmentCoil magnet :getMultiblock().getPartMap(TileContainmentCoil.class).values())
			{
				heat += 100; //TODO
				energy += 8000;
			}
			int io = 0;
			for(TileContainmentBeamPort port :getMultiblock().getPartMap(TileContainmentBeamPort.class).values())
			{
				port.setIONumber(io);
				io++;
			}
			
			getMultiblock().requiredEnergy = energy;
			getMultiblock().heating = heat; 

			if (getMultiblock().controller != null)
			{
				QMDPacketHandler.instance.sendToAll(getMultiblock().getFormPacket());
				getMultiblock().sendUpdateToListeningPlayers();
			}
			
		}
		
		 super.onContainmentFormed();
	}
	
	public void onMachineDisassembled()
	{
		for(TileContainmentBeamPort tile :getPartMap(TileContainmentBeamPort.class).values())
		{
			tile.setIONumber(0);
		}
		super.onMachineDisassembled();
	}
	
	@Override
	public boolean onUpdateServer()
	{
		getMultiblock().beams.get(0).setParticleStack(null);
		getMultiblock().beams.get(1).setParticleStack(null);
		pull();
		
		if (getMultiblock().energyStorage.extractEnergy(getMultiblock().requiredEnergy, true) == getMultiblock().requiredEnergy)
		{
			
			getMultiblock().energyStorage.changeEnergyStored(-getMultiblock().requiredEnergy);
			internalHeating();
			if (getMultiblock().getTemperature() <= getMultiblock().maxOperatingTemp)
			{
				operational = true;
				
				refreshRecipe();
				if(recipeInfo != null)
				{
					if(rememberedRecipeInfo != null)
					{
						if(rememberedRecipeInfo.getRecipe() !=recipeInfo.getRecipe())
						{
							particle1WorkDone= 0;
							particle2WorkDone =0;
						}	
					}
					rememberedRecipeInfo = recipeInfo;	
					
					if(canProduceProduct())
					{		
						produceProduct();
					}	
				}
			}
			else
			{
				if(operational)
				{
					//quenchMagnets();
				}
				operational = false;
			
			}

		}
		else
		{
			operational = false;
		}
		
		
		if (getMultiblock().controller != null) 
		{
			QMDPacketHandler.instance.sendToAll(getMultiblock().getRenderPacket());
			getMultiblock().sendUpdateToListeningPlayers();
		}
		

		return super.onUpdateServer();
	}

	
	
	protected void refreshRecipe() 
	{
		ArrayList<ParticleStack> particles = new ArrayList<ParticleStack>();
		particles.add(getMultiblock().beams.get(0).getParticleStack());
		particles.add(getMultiblock().beams.get(1).getParticleStack());
		
		recipeInfo = neutral_containment.getRecipeInfoFromInputs(new ArrayList<ItemStack>(), new ArrayList<Tank>(), particles);
		
	}
	
	private boolean canProduceProduct()
	{
		
		FluidStack product = recipeInfo.getRecipe().getFluidProducts().get(0).getStack();
		if(getMultiblock().tanks.get(2).canFillFluidType(product))
		{
			return true;
		}
		
		return false;
	}
	
	private void produceProduct()
	{
		recipeParticle1Work = recipeInfo.getRecipe().getParticleIngredients().get(0).getStack().getAmount();
		recipeParticle2Work = recipeInfo.getRecipe().getParticleIngredients().get(1).getStack().getAmount();
		
		if (particle1WorkDone >= recipeParticle1Work)
		{
			if (particle2WorkDone >= recipeParticle2Work)
			{
				FluidStack product = recipeInfo.getRecipe().getFluidProducts().get(0).getStack();
				getMultiblock().tanks.get(2).fill(product, true);
				
				particle1WorkDone -=recipeParticle1Work;
				particle2WorkDone -=recipeParticle2Work;
			}	
		}
		else
		{
			if(getMultiblock().beams.get(0).getParticleStack() != null) 
			{
				particle1WorkDone += getMultiblock().beams.get(0).getParticleStack().getAmount();

			}
			if(getMultiblock().beams.get(1).getParticleStack() != null) 
			{
				particle2WorkDone += getMultiblock().beams.get(1).getParticleStack().getAmount();
			}
		}
	}
	
	@Override
	public void onUpdateClient() {
		
	}
	
	@SideOnly(Side.CLIENT)
	protected void updateRender() 
	{
		
	}
	
	
	
	public boolean isMultiblockOn()
	{
		return operational;
	}
	
	
	
	private void refreshStats()
	{
		
		
	}

	
	// Recipe Stuff
	

	@Override
	public ContainmentUpdatePacket getUpdatePacket()
	{
		return new NeutralContainmentUpdatePacket(getMultiblock().controller.getTilePos(),
				getMultiblock().isContainmentOn, getMultiblock().heating, getMultiblock().maxCoolantIn,
				getMultiblock().maxCoolantOut, getMultiblock().maxOperatingTemp, getMultiblock().requiredEnergy,
				getMultiblock().heatBuffer, getMultiblock().energyStorage, getMultiblock().tanks, getMultiblock().beams,
				particle1WorkDone, particle2WorkDone, recipeParticle1Work, recipeParticle2Work);
	}
	
	@Override
	public void onPacket(ContainmentUpdatePacket message)
	{
		super.onPacket(message);
		if (message instanceof NeutralContainmentUpdatePacket)
		{
			NeutralContainmentUpdatePacket packet = (NeutralContainmentUpdatePacket) message;
			particle1WorkDone = packet.particle1WorkDone;
			particle2WorkDone = packet.particle2WorkDone;
			recipeParticle1Work = packet.recipeParticle1Work;
			recipeParticle2Work = packet.recipeParticle2Work;
			
		

		}
	}
	
	public void onRenderPacket(ContainmentRenderPacket message) 
	{
		getMultiblock().tanks.get(2).setFluidAmount(message.tanksInfo.get(2).amount()); 
	}
	
	// NBT
	
	@Override
	public void writeToLogicTag(NBTTagCompound logicTag, SyncReason syncReason)
	{
		super.writeToLogicTag(logicTag, syncReason);
		
		logicTag.setLong("particle1WorkDone", particle1WorkDone);
		logicTag.setLong("particle2WorkDone", particle2WorkDone);
		logicTag.setLong("recipeParticle1Work", recipeParticle1Work);
		logicTag.setLong("recipeParticle2Work", recipeParticle2Work);
	}
	
	@Override
	public void readFromLogicTag(NBTTagCompound logicTag, SyncReason syncReason)
	{
		super.readFromLogicTag(logicTag, syncReason);

		particle1WorkDone = logicTag.getLong("particle1WorkDone");
		particle2WorkDone = logicTag.getLong("particle2WorkDone");
		recipeParticle1Work = logicTag.getLong("recipeParticle1Work");
		recipeParticle2Work = logicTag.getLong("recipeParticle2Work");
		
	}
	
	
	
	
	
	
	@Override
	public ContainerMultiblockController<Containment, IContainmentController> getContainer(EntityPlayer player) 
	{
		return new ContainerNeutralContainmentController(player, (TileNeutralContainmentController) getMultiblock().controller);
	}
	
	
}
