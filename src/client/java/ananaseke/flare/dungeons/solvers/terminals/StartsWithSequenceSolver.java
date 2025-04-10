package ananaseke.flare.dungeons.solvers.terminals;

import ananaseke.flare.KeyBinds;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.text.Text;

public class StartsWithSequenceSolver {
//    static MinecraftClient client = MinecraftClient.getInstance();

    public static void initialize() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> { // FIXME - the logic is already in Terminals
            if (!KeyBinds.isToggleActive(KeyBinds.devKeybind)) return;
            if (client.player == null) return;
            if (client.currentScreen == null) return;
            client.player.sendMessage(client.currentScreen.getTitle());
            client.player.sendMessage(Text.of(client.currentScreen.getClass().getName()));
            if (!client.currentScreen.getTitle().getString().startsWith("What starts with:")) return;
            String name = client.currentScreen.getTitle().getString();
//            char letter = name.split(":")

        });
    }
}
