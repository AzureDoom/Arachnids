package mod.azure.arachnids.client.render.mobs;

import mod.azure.arachnids.client.models.mobs.ScorpionModel;
import mod.azure.arachnids.entity.bugs.ScorpionEntity;
import mod.azure.azurelib.renderer.GeoEntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class ScorpionRender extends GeoEntityRenderer<ScorpionEntity> {

	public ScorpionRender(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn, new ScorpionModel());
		this.shadowRadius = 6.8F;
	}

	@Override
	protected float getDeathMaxRotation(ScorpionEntity entityLivingBaseIn) {
		return 0.0F;
	}

}