package ananaseke.flare;

import ananaseke.flare.Plaques.Plaques;
import ananaseke.flare.combat.Slayers;
import ananaseke.flare.dungeons.*;
import ananaseke.flare.fullbright.Fullbright;
import ananaseke.flare.garden.VisitorTracker;
import ananaseke.flare.misc.AspectOfTheVoid;
import ananaseke.flare.misc.ChatHider;
import ananaseke.flare.misc.ChocolateFactory;
import ananaseke.flare.overlays.ItemOverlays;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FlareClient implements ClientModInitializer {

    public static final Logger LOGGER = LoggerFactory.getLogger("flare");
    public static final String MOD_ID = "flare";

    Config config;

    @Override
    public void onInitializeClient() {
        AutoConfig.register(Config.class, GsonConfigSerializer::new);
        config = AutoConfig.getConfigHolder(Config.class).getConfig();

        Plaques.initialize();
        Fullbright.initialize();
        ItemOverlays.initialize();
        Info.initialize();
        KeyBinds.initialize();
        new ChatHider();
        VisitorTracker.initialize();
        new DungeonMapRenderer();
        Solvers.initialize();
        HighlightStarredMobs.initialize();
        Dungeon.initialize();
        Commands.initialize();
        new ChocolateFactory();
        new Croesus();
        new PartyFinder();
        new DevTools();
        new Slayers();
        new AspectOfTheVoid();


    }
}


