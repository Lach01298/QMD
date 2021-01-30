package lach_01298.qmd.sound;

import net.minecraft.client.audio.MovingSound;
import net.minecraft.entity.Entity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;

public class MovingSoundGluonGun extends MovingSound
{

	public MovingSoundGluonGun(Entity entity)
	{
		super(QMDSounds.gluon_gun, SoundCategory.PLAYERS);
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
		// TODO Auto-generated method stub
		
	}

}
