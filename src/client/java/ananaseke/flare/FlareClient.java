package ananaseke.flare;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FlareClient implements ClientModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("flare");
    private final PlaqueRenderInfo plaqueRenderInfo = new PlaqueRenderInfo();

    @Override
    public void onInitializeClient() {
//		ClientTickEvents.END_CLIENT_TICK.register(client -> {
//			ArrayListDeque<String> messageHistory = client.inGameHud.getChatHud().getMessageHistory();
//			if (client.player != null && messageHistory.size() > 0) {
//				String lastMessage = messageHistory.getLast();
//				LOGGER.info(lastMessage);
//			}
//		});
        ClientReceiveMessageEvents.ALLOW_GAME.register((message, overlay) -> {

            LOGGER.info("Received message: " + message.getString());
            Text mess = Text.of(message.getString());

            if (mess.contains(Text.of("SKILL LEVEL UP"))) {
                // render the texture
                plaqueRenderInfo.setData(
                        Text.of("Farming skill leveled up XI -> XII"),
                        Identifier.of("flare", "textures/gui/test.png"),
                        Identifier.of("minecraft", "textures/item/wheat.png"),
                        5000
                );
            }

            return true;
        });
        HudRenderCallback.EVENT.register((drawContext, tickDelta) -> {
            if (plaqueRenderInfo.shouldRender()) renderPlaque(drawContext, plaqueRenderInfo);
        });
    }

    public void renderPlaque(DrawContext drawContext, PlaqueRenderInfo plaqueRenderInfo) {
        MinecraftClient client = MinecraftClient.getInstance();
        int width = client.getWindow().getScaledWidth();
        int height = client.getWindow().getScaledHeight();


        int mod = 2;
        int sourceWidth = 96;
        int sourceHeight = 38;
        int backgroundWidth = sourceWidth * mod;
        int backgroundHeight = sourceHeight * mod;
        int x = (width - backgroundWidth) / 2;
        int y = height / 4;

        // Draw plaque background
        drawContext.drawTexture(
                plaqueRenderInfo.getBackgroundIdentifier(),
                x, y,
                0, 0,
                backgroundWidth, backgroundHeight,
                sourceWidth, sourceHeight
        );

        // Draw the message
        int textX = x + (backgroundWidth / 2) - (client.textRenderer.getWidth(plaqueRenderInfo.getMessage()) / 2);
        int textY = y + (backgroundHeight / 2) - (client.textRenderer.fontHeight / 2);
        drawContext.drawText(client.textRenderer, plaqueRenderInfo.getMessage(), textX, textY, 0xFFFFFF, false);

    }


}