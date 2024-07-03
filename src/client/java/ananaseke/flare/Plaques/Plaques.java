package ananaseke.flare.Plaques;

import ananaseke.flare.FlareClient;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.DiffuseLighting;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import org.slf4j.Logger;

public class Plaques {
    private static final PlaqueRenderInfo plaqueRenderInfo = new PlaqueRenderInfo();
    public static final Logger LOGGER = FlareClient.LOGGER;

    public static void initialize() {
//		ClientTickEvents.END_CLIENT_TICK.register(client -> {
//			ArrayListDeque<String> messageHistory = client.inGameHud.getChatHud().getMessageHistory();
//			if (client.player != null && messageHistory.size() > 0) {
//				String lastMessage = messageHistory.getLast();
//				LOGGER.info(lastMessage);
//			}
//		});
        ClientReceiveMessageEvents.ALLOW_GAME.register((message, overlay) -> {

            LOGGER.info("Received message: " + message.getString());

            Text mess = Text.of(message.getString().stripLeading());

            if (mess.getString().contains("SKILL LEVEL UP")) {

                if (mess.getString().split(" ").length < 5) return true;

                //remove first 4 charachters from mess
                mess = Text.of(mess.getString().substring(4));

                String skillName = mess.getString().split(" ")[3].toLowerCase().substring(2);
                String level = mess.getString().split(" ")[4].substring(2).replaceFirst("(§[0-9a-fklmnor])", "");
                ItemStack plaqueItemStack;

                switch (skillName) {
                    case "combat" -> plaqueItemStack = new ItemStack(Items.DIAMOND_SWORD);
                    case "mining" -> plaqueItemStack = new ItemStack(Items.DEEPSLATE);
                    case "farming" -> plaqueItemStack = new ItemStack(Items.WHEAT);
                    case "foraging" -> plaqueItemStack = new ItemStack(Items.OAK_LOG);
                    case "fishing" -> plaqueItemStack = new ItemStack(Items.COD);
                    case "enchanting" -> plaqueItemStack = new ItemStack(Items.ENCHANTING_TABLE);
                    case "taming" -> plaqueItemStack = new ItemStack(Items.WARDEN_SPAWN_EGG);
                    case "alchemy" -> plaqueItemStack = new ItemStack(Items.POTION);
                    case "dungeoneering" -> plaqueItemStack = new ItemStack(Items.WITHER_SKELETON_SKULL);
                    case "carpentry" -> plaqueItemStack = new ItemStack(Items.CRAFTING_TABLE);
                    case "social" -> plaqueItemStack = new ItemStack(Items.EMERALD);
                    case "runecrafting" -> plaqueItemStack = new ItemStack(Items.MAGMA_CREAM);
                    default -> plaqueItemStack = new ItemStack(Items.BARRIER);
                }

                String capitalizedSkillName = skillName.substring(0, 1).toUpperCase() + skillName.substring(1);


                plaqueRenderInfo.setData(Text.of(capitalizedSkillName + " skill leveled up " + level), Identifier.of("flare", "textures/gui/plaque_background.png"), plaqueItemStack, 5000);
            }

            return true;
        });
        HudRenderCallback.EVENT.register((drawContext, tickDelta) -> {
            if (plaqueRenderInfo.shouldRender()) renderLevelUp(drawContext, plaqueRenderInfo);
        });
    }

    public static void renderLevelUp(DrawContext drawContext, PlaqueRenderInfo plaqueRenderInfo) {
        MinecraftClient client = MinecraftClient.getInstance();
        int width = client.getWindow().getScaledWidth();
        int height = client.getWindow().getScaledHeight();


        int mod = 2;
        int backgroundWidth = 96 * mod;
        int backgroundHeight = 38 * mod;
        int backgroundX = (width - backgroundWidth) / 2;
        int backgroundY = height / 4;

        renderBackground(drawContext, plaqueRenderInfo, backgroundWidth, backgroundHeight, backgroundX, backgroundY);
        renderPlaque(drawContext, plaqueRenderInfo, client, width, height, mod);
        renderMessage(drawContext, plaqueRenderInfo, client, backgroundWidth, backgroundHeight, backgroundX, backgroundY);


    }

    private static void renderBackground(DrawContext drawContext, PlaqueRenderInfo plaqueRenderInfo, int backgroundWidth, int backgroundHeight, int backgroundX, int backgroundY) {
        drawContext.drawTexture(plaqueRenderInfo.getBackgroundIdentifier(), backgroundX, backgroundY, 0, 0, backgroundWidth, backgroundHeight, backgroundWidth, backgroundHeight);
    }

    private static void renderMessage(DrawContext drawContext, PlaqueRenderInfo plaqueRenderInfo, MinecraftClient client, int backgroundWidth, int backgroundHeight, int backgroundX, int backgroundY) {
        int textX = backgroundX + (backgroundWidth / 2) - (client.textRenderer.getWidth(plaqueRenderInfo.getMessage()) / 2);
        int textY = backgroundY + (backgroundHeight / 2) - (client.textRenderer.fontHeight / 2) + 18;
        drawContext.drawText(client.textRenderer, plaqueRenderInfo.getMessage(), textX, textY, 0xFFFFFF, true);
    }

    private static void renderPlaque(DrawContext drawContext, PlaqueRenderInfo plaqueRenderInfo, MinecraftClient client, int width, int height, int mod) {
        int itemWidth = 12 * mod;
        int itemX = (width - itemWidth) / 2;
        int itemY = (height / 4) + 11;

        float itemMod = 1.75F;
        ItemStack stack = plaqueRenderInfo.getPlaqueItemStack();
        BakedModel bakedModel = client.getItemRenderer().getModel(stack, client.world, client.player, 0);
        drawContext.getMatrices().push();
        drawContext.getMatrices().translate(itemX + (8 * itemMod) - 2, itemY + (8 * itemMod), (float) (150));
        drawContext.getMatrices().scale(16.0F * itemMod, -16.0F * itemMod, 16.0F * itemMod);

        boolean bl = !bakedModel.isSideLit();
        if (bl) {
            DiffuseLighting.disableGuiDepthLighting();
        }
        client.getItemRenderer().renderItem(stack, ModelTransformationMode.GUI, false, drawContext.getMatrices(), drawContext.getVertexConsumers(), 15728880, OverlayTexture.DEFAULT_UV, bakedModel);
        drawContext.draw();
        if (bl) {
            DiffuseLighting.enableGuiDepthLighting();
        }
        drawContext.getMatrices().pop();
    }


}