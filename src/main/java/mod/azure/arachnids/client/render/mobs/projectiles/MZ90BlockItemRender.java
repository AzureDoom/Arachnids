package mod.azure.arachnids.client.render.mobs.projectiles;

import mod.azure.arachnids.client.models.mobs.projectiles.MZ90BlockModel;
import mod.azure.arachnids.items.ammo.MZ90Item;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class MZ90BlockItemRender extends GeoItemRenderer<MZ90Item> {

	public MZ90BlockItemRender() {
		super(new MZ90BlockModel());
	}

}