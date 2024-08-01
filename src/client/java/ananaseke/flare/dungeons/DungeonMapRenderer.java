package ananaseke.flare.dungeons;

import ananaseke.flare.Config;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;

public class DungeonMapRenderer {
    public static void initialize() {
        Config config = AutoConfig.getConfigHolder(Config.class).getConfig();
        if (config.shouldRenderDungeonMap) {

        }
    }
}
