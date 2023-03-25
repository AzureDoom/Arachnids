package mod.azure.arachnids.client.render.mobs;

import mod.azure.arachnids.client.models.mobs.BrainModel;
import mod.azure.arachnids.entity.bugs.BrainEntity;
import mod.azure.azurelib.renderer.GeoEntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class BrainRender extends GeoEntityRenderer<BrainEntity> {

	public BrainRender(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn, new BrainModel());
		this.shadowRadius = 2.7F;
	}

	@Override
	protected float getDeathMaxRotation(BrainEntity entityLivingBaseIn) {
		return 0.0F;
	}

}