package lach_01298.qmd.sound;

import lach_01298.qmd.entity.EntityGluonBeam;
import net.minecraft.client.audio.MovingSound;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.fml.relauncher.*;


@SideOnly(Side.CLIENT)
public class MovingSoundGluonGun extends MovingSound
{
	private final EntityGluonBeam beam;
	
	public MovingSoundGluonGun(EntityGluonBeam entity)
	{
		super(QMDSounds.gluon_gun, SoundCategory.NEUTRAL);
		this.beam = entity;
		this.repeat = false;
        this.repeatDelay = 0;
        this.volume = 0.1F;
        this.xPosF = (float) entity.posX;
        this.yPosF = (float) entity.posY;
        this.zPosF = (float) entity.posZ;
	}

	@Override
	public void update()
	{
		
		if (this.beam.isDead)
		{
			this.donePlaying = true;
		}
		else
		{
			this.xPosF = (float) this.beam.posX;
			this.yPosF = (float) this.beam.posY;
			this.zPosF = (float) this.beam.posZ;
		}
	}

}
