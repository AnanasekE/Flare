package ananaseke.flare.dungeons;

import ananaseke.flare.callbacks.DungeonEnterCallback;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.minecraft.text.Text;

public class Dungeon {
    public static void initialize() {
        ClientReceiveMessageEvents.GAME.register((message, overlay) -> {

            if (message.contains(Text.of(" entered The Catacombs, "))) {
                DungeonEnterCallback.EVENT.invoker().onDungeonEntered();
            }
        });
    }
}
