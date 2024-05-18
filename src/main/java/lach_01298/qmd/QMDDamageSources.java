package lach_01298.qmd;

import lach_01298.qmd.entity.*;
import net.minecraft.entity.Entity;
import net.minecraft.util.*;

import javax.annotation.Nullable;

public class QMDDamageSources
{
	public static final DamageSource ANTIMATTER_ANNIHLATION = new DamageSource("antimatter_annihilation").setDamageBypassesArmor().setDamageIsAbsolute();
	public static final DamageSource SELF_POISONING = new DamageSource("self_poisoning").setDamageBypassesArmor().setDamageIsAbsolute();
	
	public static final DamageSource causeLeptonCannonDamage(EntityLeptonBeam beam, @Nullable Entity indirectEntityIn)
	{
		return (new EntityDamageSourceIndirect("lepton_cannon", beam, indirectEntityIn)).setDamageBypassesArmor();
	}

	public static DamageSource causeGluonGunDamage(EntityGluonBeam beam, @Nullable Entity indirectEntityIn)
	{
		return (new EntityDamageSourceIndirect("gluon_gun", beam, indirectEntityIn)).setDamageBypassesArmor();
	}
	
	public static DamageSource causeAntimatterLauncherDamage(EntityAntimatterProjectile projectile, @Nullable Entity indirectEntityIn)
	{
		return (new EntityDamageSourceIndirect("antimatter_launcher", projectile, indirectEntityIn)).setDamageBypassesArmor();
	}

}
