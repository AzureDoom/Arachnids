package mod.azure.arachnids.util;

import mod.azure.arachnids.ArachnidsMod;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ArachnidsSounds {

	public static SoundEvent CLIPRELOAD = of("arachnids.clipreload");
	public static SoundEvent MAR1FIRE = of("arachnids.mar1fire");
	public static SoundEvent MAR2FIRE = of("arachnids.mar2fire");
	public static SoundEvent M55FIRE = of("arachnids.m55fire");
	public static SoundEvent M55RELOAD = of("arachnids.m55reload");
	public static SoundEvent GRENADELAUNCHER = of("arachnids.grenadelauncher");
	public static SoundEvent FLAREGUN = of("arachnids.flare_gun");

	public static SoundEvent ARKELLIAN_DEATH = of("arachnids.arkellian_death");
	public static SoundEvent ARKELLIAN_HURT = of("arachnids.arkellian_hurt");
	public static SoundEvent ARKELLIAN_IDLE = of("arachnids.arkellian_idle");
	public static SoundEvent ARKELLIAN_MOVING = of("arachnids.arkellian_moving");

	public static SoundEvent BRAINBUG_DEATH = of("arachnids.brainbug_death");
	public static SoundEvent BRAINBUG_HURT = of("arachnids.brainbug_hurt");
	public static SoundEvent BRAINBUG_IDLE = of("arachnids.brainbug_idle");

	public static SoundEvent HOPPER_DEATH = of("arachnids.hopper_death");
	public static SoundEvent HOPPER_HURT = of("arachnids.hopper_hurt");
	public static SoundEvent HOPPER_IDLE = of("arachnids.hopper_idle");
	public static SoundEvent HOPPER_MOVING = of("arachnids.hopper_moving");

	public static SoundEvent PLASMA_ATTACK = of("arachnids.plasma_attack");
	public static SoundEvent PLASMA_DEATH = of("arachnids.plasma_death");
	public static SoundEvent PLASMA_HURT = of("arachnids.plasma_hurt");
	public static SoundEvent PLASMA_IDLE = of("arachnids.plasma_idle");
	public static SoundEvent PLASMA_MOVING = of("arachnids.plasma_moving");

	public static SoundEvent SCORPION_DEATH = of("arachnids.scorpion_death");
	public static SoundEvent SCORPION_HURT = of("arachnids.scorpion_hurt");
	public static SoundEvent SCORPION_IDLE = of("arachnids.scorpion_idle");
	public static SoundEvent SCORPION_MOVING = of("arachnids.scorpion_moving");
	public static SoundEvent SCORPION_POWERUP = of("arachnids.scorpion_powerup");

	public static SoundEvent TANKER_ATTACK = of("arachnids.tanker_attack");
	public static SoundEvent TANKER_DEATH = of("arachnids.tanker_death");
	public static SoundEvent TANKER_HURT = of("arachnids.tanker_hurt");
	public static SoundEvent TANKER_IDLE = of("arachnids.tanker_idle");
	public static SoundEvent TANKER_MOVING = of("arachnids.tanker_moving");
	public static SoundEvent TANKER_POWERUP = of("arachnids.tanker_powerup");

	public static SoundEvent WARRIOR_ATTACK = of("arachnids.warrior_attack");
	public static SoundEvent WARRIOR_DEATH = of("arachnids.warrior_death");
	public static SoundEvent WARRIOR_HURT = of("arachnids.warrior_hurt");
	public static SoundEvent WARRIOR_IDLE = of("arachnids.warrior_idle");
	public static SoundEvent WARRIOR_MOVING = of("arachnids.warrior_moving");

	public static SoundEvent WORKER_ATTACK = of("arachnids.worker_attack");
	public static SoundEvent WORKER_DEATH = of("arachnids.worker_death");
	public static SoundEvent WORKER_HURT = of("arachnids.worker_hurt");
	public static SoundEvent WORKER_IDLE = of("arachnids.worker_idle");

	static SoundEvent of(String id) {
		SoundEvent sound = new SoundEvent(new Identifier(ArachnidsMod.MODID, id));
		Registry.register(Registry.SOUND_EVENT, new Identifier(ArachnidsMod.MODID, id), sound);
		return sound;
	}
}
