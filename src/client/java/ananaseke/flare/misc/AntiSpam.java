package ananaseke.flare.misc;

import ananaseke.flare.FlareClient;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.message.MessageSignatureData;
import net.minecraft.text.Text;
import org.slf4j.Logger;

import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AntiSpam {
//    private static int lastMessageCount = 1;
//    private static final Logger LOGGER = FlareClient.LOGGER;
//    private static String lastMessage = "";
//    private static MessageSignatureData lastMessageSignature = null;
//    private static final Lock lock = new ReentrantLock();

    public static void initialize() {
//        ClientReceiveMessageEvents.ALLOW_CHAT.register((message, signedMessage, sender, params, receptionTimestamp) -> {
//            lock.lock();
//            try {
//                MinecraftClient client = MinecraftClient.getInstance();
//                if (client == null || client.inGameHud == null || client.inGameHud.getChatHud() == null) {
//                    return true; // Allow message if client or chat HUD is not available
//                }
//
//                String currentMessage = message.getString();
//                MessageSignatureData currentSignature = signedMessage.signature();
//
//                if (Objects.equals(currentMessage, lastMessage)) {
//                    lastMessageCount++;
//                    client.inGameHud.getChatHud().removeMessage(lastMessageSignature); // Updated to use signature
//                    client.inGameHud.getChatHud().addMessage(Text.of(currentMessage + " (" + lastMessageCount + ")"));
//                    LOGGER.info("Filtered duplicate message: " + currentMessage + " (" + lastMessageCount + ")");
//                    return false;
//                } else {
//                    lastMessageCount = 1;
//                }
//
//                lastMessage = currentMessage;
//                lastMessageSignature = currentSignature;
//                return true;
//            } finally {
//                lock.unlock();
//            }
//        });
    }
}
