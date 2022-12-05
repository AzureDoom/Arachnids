package mod.azure.arachnids.client.render.mobs.projectiles;

import mod.azure.arachnids.client.models.mobs.projectiles.MZ90Model;
import mod.azure.arachnids.entity.projectiles.MZ90Entity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class MZ90Render extends GeoEntityRenderer<MZ90Entity> {

	public MZ90Render(EntityRendererProvider.Context renderManagerIn) {
		super(renderManagerIn, new MZ90Model());
	}

	@Override
	protected int getBlockLightLevel(MZ90Entity entityIn, BlockPos partialTicks) {
		return 15;
	}
	
}