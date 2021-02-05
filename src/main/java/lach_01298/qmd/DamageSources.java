package lach_01298.qmd;

import javax.annotation.Nullable;

import lach_01298.qmd.entity.EntityGluonBeam;
import lach_01298.qmd.entity.EntityLeptonBeam;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;

public class DamageSources
{

	public static final DamageSource causeLeptonCannonDamage(EntityLeptonBeam beam, @Nullable Entity indirectEntityIn)
	{
		return (new EntityDamageSourceIndirect("lepton_cannon", beam, indirectEntityIn)).setDamageBypassesArmor();
	}

	public static DamageSource causeGluonGunDamage(EntityGluonBeam beam, @Nullable Entity indirectEntityIn)
	{
		return (new EntityDamageSourceIndirect("gluon_gun", beam, indirectEntityIn)).setDamageBypassesArmor();
	}
}
