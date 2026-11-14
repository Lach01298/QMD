package lach_01298.qmd.particle;

import lach_01298.qmd.config.QMDConfig;
import lach_01298.qmd.util.Equations;
import net.minecraft.util.EnumFacing;

public class ParticleStorageBeamline extends ParticleStorage
{

	private int length;
	
	private Flow currentArrivals, currentTransmissions;
	private Flow previousArrivals, previousTransmissions;

	public ParticleStorageBeamline(int length)
	{
		super(null,Integer.MAX_VALUE,Integer.MAX_VALUE);
		this.length = length;
	}
	
	public void setLength(int length)
	{
		this.length = length;
		clearFlow();
	}

	public void onBeginFlowTick()
	{
		currentArrivals = new Flow();
		currentTransmissions = new Flow();
	}

	public void onEndFlowTick()
	{
		previousArrivals = currentArrivals;
		previousTransmissions = currentTransmissions;
		currentArrivals = currentTransmissions = null;
	}

	public void clearFlow()
	{
		currentArrivals = currentTransmissions = previousArrivals = previousTransmissions = null;
	}

	public Flow getPreviousArrivals()
	{
		return previousArrivals;
	}

	public Flow getPreviousTransmissions()
	{
		return previousTransmissions;
	}
	
	public int getLength()
	{
		return length;
	}
	
	public ParticleStack extractParticle(EnumFacing side)
	{
		if (canExtractParticle(side) && particleStack.getFocus() > length * QMDConfig.beamAttenuationRate)
		{
			ParticleStack stack = this.particleStack;
			this.particleStack = null;
			stack.addFocus(-Equations.focusLoss(length, stack));
			if (currentTransmissions != null)
			{
				currentTransmissions.update(stack);
			}
			return stack;
		}
		
		return null;
	}
	
	@Override
	public boolean receiveParticle(EnumFacing side, ParticleStack stack)
	{
		if(stack != null)
		{
			if(stack.getFocus()>0)
			{
				particleStack = stack.copy();
				if (currentArrivals != null)
				{
					currentArrivals.update(stack);
				}
				return true;
			}
		}
		return false;
	}
	
	public static final class Flow
	{
		private long amount, packetCount;
		private ParticleStack sample;
		private boolean varied;
		
		private void update(ParticleStack stack)
		{
			if (sample == null)
			{
				sample = stack.copy();
			}
			else if (sample.getParticle() != stack.getParticle() || sample.getMeanEnergy() != stack.getMeanEnergy() || Double.compare(sample.getFocus(), stack.getFocus()) != 0)
			{
				varied = true;
			}
			
			amount += stack.getAmount();
			packetCount++;
		}
		
		public long getAmount() {
			return amount;
		}
		
		public long getPacketCount() {
			return packetCount;
		}
		
		public boolean isVaried() {
			return varied;
		}
		
		public ParticleStack getStack()
		{
			return varied || sample == null ? null : sample.copy();
		}
	}
}
