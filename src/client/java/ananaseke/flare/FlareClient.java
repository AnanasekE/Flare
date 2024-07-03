package ananaseke.flare;

import ananaseke.flare.Plaques.Plaques;
import ananaseke.flare.fullbright.Fullbright;
import net.fabricmc.api.ClientModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FlareClient implements ClientModInitializer {

    public static final Logger LOGGER = LoggerFactory.getLogger("flare");
    public static final String MOD_ID = "flare";

    @Override
    public void onInitializeClient() {
        Plaques.initialize();
        Fullbright.initialize();



    }
}
