package ananaseke.flare.misc;

import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.minecraft.text.Text;

public class ChatHider {
    public static void initialize() {
        ClientReceiveMessageEvents.ALLOW_CHAT.register((message, signedMessage, sender, params, receptionTimestamp) -> {
//            if (message.contains(Text.of("You earned ")) && message.contains(Text.of("Event EXP from playing SkyBlock!"))) {
//                return false;
//            } else if (message.contains(Text.of("[NPC] Mort:"))) {
//                return false;
//            } else if (message.contains(Text.of("[BOSS] The Watcher: "))) {
//
//            } else if (message.contains(Text.of("+ Kill Combo ))) {
//
//            }
            return true;
        });
    }
}

