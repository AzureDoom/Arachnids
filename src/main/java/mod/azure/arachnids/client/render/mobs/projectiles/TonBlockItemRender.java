package mod.azure.arachnids.client.render.mobs.projectiles;

import mod.azure.arachnids.client.models.mobs.projectiles.TONBlockModel;
import mod.azure.arachnids.items.ammo.TONItem;
import mod.azure.azurelib.renderer.GeoItemRenderer;

public class TonBlockItemRender extends GeoItemRenderer<TONItem> {

	public TonBlockItemRender() {
		super(new TONBlockModel());
	}

}