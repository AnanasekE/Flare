package ananaseke.flare.dungeons;

import ananaseke.flare.Config;
import ananaseke.flare.FlareClient;
import ananaseke.flare.KeyBinds;
import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

public class FragRunMode {
    static boolean isInDungeon = false;

    public static void initialize() {
        Config config = AutoConfig.getConfigHolder(Config.class).getConfig();

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (!config.fragRunMode) return;
            if (KeyBinds.fragRunModeKeybind.wasPressed()) {
                FlareClient.LOGGER.info("Clicked");
                if (client.getNetworkHandler() == null) return;
                FlareClient.LOGGER.info("networkHandler exists");
                if (client.player == null) return;
                FlareClient.LOGGER.info("player exists");
                if (isInDungeon) {
                    client.getNetworkHandler().sendCommand("warp dhub");
                    isInDungeon = false;
                } else {
                    client.getNetworkHandler().sendCommand("joindungeon CATACOMBS_FLOOR_SEVEN");
                    isInDungeon = true;
                }
            }
        });
    }
}
