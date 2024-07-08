package ananaseke.flare;

import ananaseke.flare.Plaques.Plaques;
import ananaseke.flare.fullbright.Fullbright;
import ananaseke.flare.misc.AntiSpam;
import ananaseke.flare.overlays.ItemOverlays;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.item.FilledMapItem;
import net.minecraft.item.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FlareClient implements ClientModInitializer {

    public static final Logger LOGGER = LoggerFactory.getLogger("flare");
    public static final String MOD_ID = "flare";

//    public static FilledMapItem map;
//    public static boolean shouldRenderMap = false;

    @Override
    public void onInitializeClient() {
        Plaques.initialize();
        Fullbright.initialize();
        ItemOverlays.initialize();
        Info.initialize();
        AntiSpam.initialize();

//        ClientTickEvents.END_CLIENT_TICK.register(client -> {
//            Item item = client.player.getInventory().getStack(9).getItem();
//            if (item instanceof FilledMapItem) {
//                map = (FilledMapItem) item;
//                shouldRenderMap = true;
//            }
//        });


    }
}


