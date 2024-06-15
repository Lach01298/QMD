package lach_01298.qmd.item;

import lach_01298.qmd.accelerator.tile.TileAcceleratorBeamPort;
import lach_01298.qmd.capabilities.CapabilityParticleStackHandler;
import lach_01298.qmd.enums.EnumTypes.IOType;
import lach_01298.qmd.particle.*;
import lach_01298.qmd.particleChamber.CollisionChamberLogic;
import lach_01298.qmd.particleChamber.tile.TileParticleChamberBeamPort;
import lach_01298.qmd.pipe.TileBeamline;
import lach_01298.qmd.util.*;
import nc.item.NCItem;
import nc.util.Lang;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.*;
import net.minecraft.world.World;

import java.text.DecimalFormat;



public class ItemBeamMeter extends NCItem
{

	public ItemBeamMeter(String... tooltip)
	{
		super(tooltip);
		maxStackSize = 1;
	}

	@Override
	public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX,
			float hitY, float hitZ, EnumHand hand)
	{
		if (!world.isRemote)
		{
			TileEntity tile = world.getTileEntity(pos);
			if (tile != null)
			{
				if (player.isSneaking())
				{
					if(tile instanceof TileParticleChamberBeamPort)
					{
						TileParticleChamberBeamPort port = (TileParticleChamberBeamPort) tile;
						if(port.getIOType() == IOType.OUTPUT)
						{
							int inputNumberOffset = 0;
							if (port.getMultiblock().getLogic() instanceof CollisionChamberLogic)
							{
								inputNumberOffset = 1;
							}
							TextComponentString message = new TextComponentString(
									 Lang.localize("qmd.block.particle_chamber_port_setting",TextFormatting.LIGHT_PURPLE + " "+(port.getIONumber()-inputNumberOffset)));
							player.sendMessage(message);
							return EnumActionResult.SUCCESS;
						}
					}
					if(tile instanceof TileAcceleratorBeamPort)
					{
						TileAcceleratorBeamPort port = (TileAcceleratorBeamPort) tile;
						TextFormatting colour;
						switch(port.getSetting())
						{
						case INPUT:
							colour = TextFormatting.DARK_AQUA;
							break;
						case OUTPUT:
							colour = TextFormatting.RED;
							break;
						default:
							colour = TextFormatting.GRAY;
							break;
						}
						TextComponentString message = new TextComponentString(Lang.localize("qmd.block.accelerator_port_setting",colour+Lang.localize("qmd.block.port_mode."+ port.getSetting().name())));
						player.sendMessage(message);
						return EnumActionResult.SUCCESS;
					}
					
				}
				else
				{
					for (EnumFacing face : EnumFacing.VALUES)
					{
						
						if (tile.hasCapability(CapabilityParticleStackHandler.PARTICLE_HANDLER_CAPABILITY, face))
						{
							IParticleStackHandler particleStorage = tile
									.getCapability(CapabilityParticleStackHandler.PARTICLE_HANDLER_CAPABILITY, face);

							ParticleStack particle = particleStorage.getParticle();

							if (particle != null)
							{
								if (tile instanceof TileBeamline)
								{
									TileBeamline beam = (TileBeamline) tile;
									if (beam.getMultiblock() != null)
									{
										particle.addFocus(
												-Equations.focusLoss(beam.getMultiblock().length(), particle));
									}
								}

								DecimalFormat df = new DecimalFormat("#.####");
								DecimalFormat df2 = new DecimalFormat("#.#");

								TextComponentString message = new TextComponentString(
										TextFormatting.AQUA
												+ Lang.localize("gui.qmd.particlestack.name",
														TextFormatting.WHITE + Lang.localize("qmd.particle."
																+ particle.getParticle().getName() + ".name"))
												+ " " + TextFormatting.YELLOW
												+ Lang.localize("gui.qmd.particlestack.amount",
														TextFormatting.WHITE
																+ Units.getSIFormat(particle.getAmount(), "pu"))
												+ " " + TextFormatting.GREEN
												+ (Lang.localize("gui.qmd.particlestack.mean_energy",
														TextFormatting.WHITE
																+ Units.getSIFormat(particle.getMeanEnergy(), 3, "eV")))
												+ " " + TextFormatting.DARK_AQUA
												+ Lang.localize("gui.qmd.particlestack.focus",
														TextFormatting.WHITE + df.format(particle.getFocus())));

								player.sendMessage(message);

								TextComponentString lossMessage = new TextComponentString(TextFormatting.DARK_AQUA
										+ Lang.localize("gui.qmd.particlestack.focus_loss",
												TextFormatting.WHITE + df.format(Equations.focusLoss(1, particle)))
										+ " " + TextFormatting.GREEN
										+ Lang.localize("gui.qmd.particlestack.travel_distance",
												TextFormatting.WHITE + df2.format(Equations.travelDistance(particle)))

								);
								player.sendMessage(lossMessage);
							}
							else
							{
								player.sendMessage(
										new TextComponentString(Lang.localize("gui.qmd.particlestack.empty")));
							}
							return EnumActionResult.SUCCESS;
						}

						
					}
				}

			}
		}

		return EnumActionResult.PASS;
	}
	
}
