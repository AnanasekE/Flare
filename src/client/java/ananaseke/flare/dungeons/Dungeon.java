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
        ScoreCalculator.initialize();
        Terminals.initialize();
        FragRunMode.initialize();

        ClientReceiveMessageEvents.GAME.register((message, overlay) -> {
            int floor = 0;
            if (message.contains(Text.of(" entered The Catacombs, "))) {
                switch (Arrays.stream(message.getString().replaceAll("-", "").split(",")).toList().getLast().trim().replaceAll("!", "")) {
                    case "Floor I":
                        floor = 1;
                        break;
                    case "Floor II":
                        floor = 2;
                        break;
                    case "Floor III":
                        floor = 3;
                        break;
                    case "Floor IV":
                        floor = 4;
                        break;
                    case "Floor V":
                        floor = 5;
                        break;
                    case "Floor VI":
                        floor = 6;
                        break;
                    case "Floor VII":
                        floor = 7;
                        break;
                }
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
