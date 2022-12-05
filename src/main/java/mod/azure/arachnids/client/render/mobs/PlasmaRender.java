package mod.azure.arachnids.client.render.mobs;

import mod.azure.arachnids.client.models.mobs.PlasmaModel;
import mod.azure.arachnids.entity.bugs.PlasmaEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class PlasmaRender extends GeoEntityRenderer<PlasmaEntity> {

	public PlasmaRender(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn, new PlasmaModel());
		this.shadowRadius = 6.8F;
	}

	@Override
	protected float getDeathMaxRotation(PlasmaEntity entityLivingBaseIn) {
		return 0.0F;
	}

}