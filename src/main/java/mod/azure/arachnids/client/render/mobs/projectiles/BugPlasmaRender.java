package mod.azure.arachnids.client.render.mobs.projectiles;

import mod.azure.arachnids.client.models.mobs.projectiles.BugPlasmaModel;
import mod.azure.arachnids.entity.projectiles.BugPlasmaEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class BugPlasmaRender extends GeoEntityRenderer<BugPlasmaEntity> {

	public BugPlasmaRender(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn, new BugPlasmaModel());
	}

	@Override
	protected int getBlockLightLevel(BugPlasmaEntity entityIn, BlockPos partialTicks) {
		return 15;
	}

}