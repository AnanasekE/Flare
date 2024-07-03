package ananaseke.flare;

import ananaseke.flare.Plaques.Plaques;
import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FlareClient implements ClientModInitializer {

    public static final Logger LOGGER = LoggerFactory.getLogger("flare");

    @Override
    public void onInitializeClient() {
        Plaques.initialize();
    }
}
