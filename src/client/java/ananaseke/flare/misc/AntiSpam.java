package ananaseke.flare.misc;

public class AntiSpam {
    //    private static int lastMessageCount = 1;
//    private static final Logger LOGGER = FlareClient.LOGGER;
//    private static String lastMessage = "";
//    private static MessageSignatureData lastMessageSignature = null;
//    private static final Lock lock = new ReentrantLock();


//    private static MessageSignatureData messageSignatureData;
//    private static Integer consecutiveMessageCount = 1;

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
//                }
//                lastMessageCount = 1;
//                lastMessage = currentMessage;
//                lastMessageSignature = currentSignature;
//                return true;
//            } finally {
//                lock.unlock();
//            }
//        });


//        ClientReceiveMessageEvents.ALLOW_CHAT.register((message, signedMessage, sender, params, receptionTimestamp) -> {
//            MinecraftClient client = MinecraftClient.getInstance();
//            ChatHud chatHud = client.inGameHud.getChatHud();
//            FlareClient.LOGGER.info("Message: " + message.getString());
//            if (signedMessage == null) return true;
//            FlareClient.LOGGER.info("Message: " + message.getString() + " Signature: " + signedMessage.signature());
//
//            if (messageSignatureData == null) return true;
//            if (messageSignatureData.equals(signedMessage.signature())) {
//                chatHud.removeMessage(messageSignatureData);
//                chatHud.addMessage(Text.of(message.getString() + " (" + consecutiveMessageCount + ")"));
//                consecutiveMessageCount += 1;
//                return false;
//            }
//            consecutiveMessageCount = 1;
//
//            messageSignatureData = signedMessage.signature();
//
//            return true;
//        });
    }
}
