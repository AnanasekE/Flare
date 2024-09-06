package ananaseke.flare.dungeons;

import ananaseke.flare.FlareClient;
import ananaseke.flare.callbacks.DungeonEnterCallback;
import ananaseke.flare.callbacks.DungeonStartCallback;
import ananaseke.flare.dungeons.solvers.terminals.Terminals;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class Dungeon {

    public static void initialize() {
//        ScoreCalculator.initialize();
        Terminals.initialize();
        FragRunMode.initialize();
//        Croesus.initialize();

        ClientReceiveMessageEvents.GAME.register((message, overlay) -> {
            int floor = 0;
            if (message.contains(Text.of(" entered The Catacombs, "))) { // TODO fix to work with master mode
                floor = switch (Arrays.stream(message.getString().replaceAll("-", "").split(",")).toList().getLast().trim().replaceAll("!", "")) {
                    case "Floor I" -> 1;
                    case "Floor II" -> 2;
                    case "Floor III" -> 3;
                    case "Floor IV" -> 4;
                    case "Floor V" -> 5;
                    case "Floor VI" -> 6;
                    case "Floor VII" -> 7;
                    default -> floor;
                };
                FlareClient.LOGGER.info(String.valueOf(floor));
                if (floor == 0) return;
                DungeonEnterCallback.EVENT.invoker().onDungeonEntered(7);
            }

            if (message.contains(Text.of("[MORT] Good Luck."))) {
                DungeonStartCallback.EVENT.invoker().onDungeonStarted();
            }
        });
    }
}
