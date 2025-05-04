package ananaseke.flare.dungeons;

import ananaseke.flare.FlareClient;
import ananaseke.flare.Utils.Utils;
import ananaseke.flare.callbacks.DungeonEvents;
import ananaseke.flare.dungeons.solvers.terminals.Terminals;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.text.Text;

import java.util.Objects;

public class Dungeon {
    public static boolean isInDungeon = false;

    public static void initialize() {
//        ScoreCalculator.initialize();
        Terminals.initialize();
        FragRunMode.initialize();
        new DungeonHubPartyInfo();
        new DungeonBlockClickHighlighter();

        ClientPlayConnectionEvents.JOIN.register(Dungeon::onPlayerJoin);

        ClientReceiveMessageEvents.GAME.register((message, overlay) -> {
            int floor = 0;
            Utils.TestRegexResult result = Utils.testRegex(message.getString(), ".+ entered( MM|) The Catacombs, Floor (VII|VI|V|IV|III|II|I)!");
            if (result.isMatching) {
                if (result.groups.isEmpty()) return;

                // [VIP] AnanasekE entered The Catacombs, Floor I!
                // [VIP] AnanasekE entered MM The Catacombs, Floor I!

                String floorRoman = result.groups.get(1);
                switch (floorRoman) {
                    case "I" -> floor = 1;
                    case "II" -> floor = 2;
                    case "III" -> floor = 3;
                    case "IV" -> floor = 4;
                    case "V" -> floor = 5;
                    case "VI" -> floor = 6;
                    case "VII" -> floor = 7;
                }
                String mmGroup = result.groups.getFirst();
                if (!Objects.equals(mmGroup, "")) {
                    floor += 7;
                }
                FlareClient.LOGGER.info("Player entered a dungeon");
                DungeonEvents.ENTER.invoker().onDungeonEntered(floor);
            }


//            if (message.contains(Text.of("[MORT] Good Luck."))) {
//                DungeonEvents.START.invoker().onDungeonStarted();
//            }
        });

        ClientReceiveMessageEvents.GAME.register((message, overlay) -> {
            Utils.TestRegexResult result = Utils.testRegex(message.getString(), "^\\[MORT] Good Luck\\.$");
            if (result.isMatching) {
                FlareClient.LOGGER.info("mort says good luck");
                DungeonEvents.START.invoker().onDungeonStarted();
            }
        });


//        DungeonEvents.ENTER.register(floor -> isInDungeon = true);
        DungeonEvents.START.register(() -> isInDungeon = true);
        DungeonEvents.EXIT.register(() -> isInDungeon = false);
    }

    private static void onPlayerJoin(ClientPlayNetworkHandler handler, PacketSender sender, MinecraftClient client) {
        if (isInDungeon) {
            FlareClient.LOGGER.info("EXITING DUNGEON");
            DungeonEvents.EXIT.invoker().onDungeonExited();
        }
    }
}
