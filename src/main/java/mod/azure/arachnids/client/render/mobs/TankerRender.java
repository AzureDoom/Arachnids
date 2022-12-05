package mod.azure.arachnids.client.render.mobs;

import mod.azure.arachnids.client.models.mobs.TankerModel;
import mod.azure.arachnids.entity.bugs.TankerEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class TankerRender extends GeoEntityRenderer<TankerEntity> {

	public TankerRender(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn, new TankerModel());
		this.shadowRadius = 6.7F;
	}

	@Override
	protected float getDeathMaxRotation(TankerEntity entityLivingBaseIn) {
		return 0.0F;
	}

}