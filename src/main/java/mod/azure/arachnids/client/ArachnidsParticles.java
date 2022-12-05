package mod.azure.arachnids.client;

import mod.azure.arachnids.ArachnidsMod;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

public class ArachnidsParticles {

	public static final SimpleParticleType FLARE = register(new ResourceLocation(ArachnidsMod.MODID, "flare"), false);

	private static SimpleParticleType register(ResourceLocation identifier, boolean alwaysSpawn) {
		return Registry.register(BuiltInRegistries.PARTICLE_TYPE, identifier, FabricParticleTypes.simple(alwaysSpawn));
	}
}
