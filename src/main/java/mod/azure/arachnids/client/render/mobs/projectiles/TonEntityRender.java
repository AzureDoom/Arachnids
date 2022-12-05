package mod.azure.arachnids.client.render.mobs.projectiles;

import mod.azure.arachnids.client.models.mobs.projectiles.TONEntityModel;
import mod.azure.arachnids.entity.projectiles.TacticalOxygenNukeEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class TonEntityRender extends GeoEntityRenderer<TacticalOxygenNukeEntity> {

	public TonEntityRender(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn, new TONEntityModel());
	}

	@Override
	protected int getBlockLightLevel(TacticalOxygenNukeEntity entityIn, BlockPos partialTicks) {
		return 15;
	}

}