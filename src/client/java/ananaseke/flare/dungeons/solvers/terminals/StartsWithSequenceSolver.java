package ananaseke.flare.dungeons.solvers.terminals;

import ananaseke.flare.KeyBinds;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class StartsWithSequenceSolver {
//    static MinecraftClient client = MinecraftClient.getInstance();

    public static void initialize() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (!KeyBinds.devKeybindToggle) return;
            if (client.player == null) return;
            if (client.currentScreen == null) return;
            client.player.sendMessage(client.currentScreen.getTitle());
            if (!client.currentScreen.getTitle().getString().startsWith("What starts with:")) return;
            String name = client.currentScreen.getTitle().getString();
//            char letter = name.split(":")

        });
    }
}
