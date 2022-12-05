package mod.azure.arachnids.client.render.mobs;

import mod.azure.arachnids.client.models.mobs.ArkellianModel;
import mod.azure.arachnids.entity.bugs.ArkellianEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class ArkellianRender extends GeoEntityRenderer<ArkellianEntity> {

	public ArkellianRender(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn, new ArkellianModel());
		this.shadowRadius = 0.7F;
	}

	@Override
	protected float getDeathMaxRotation(ArkellianEntity entityLivingBaseIn) {
		return 0.0F;
	}

}