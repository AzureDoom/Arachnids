package mod.azure.arachnids.client;

import mod.azure.arachnids.ArachnidsMod;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ArachnidsParticles {

	public static final DefaultParticleType FLARE = register(new Identifier(ArachnidsMod.MODID, "flare"), false);

	private static DefaultParticleType register(Identifier identifier, boolean alwaysSpawn) {
		return Registry.register(Registry.PARTICLE_TYPE, identifier, FabricParticleTypes.simple(alwaysSpawn));
	}
}
