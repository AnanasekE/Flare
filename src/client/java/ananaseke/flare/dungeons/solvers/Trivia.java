package ananaseke.flare.dungeons.solvers;

import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class Trivia {
    private static final MinecraftClient client = MinecraftClient.getInstance();

    public static void initialize() {
        ClientReceiveMessageEvents.GAME.register((message, overlay) -> {
            String messStr = message.getString();
            assert client.player != null;
            if (messStr.contains("What is the status of The Watcher?")) {
                client.player.sendMessage(Text.of("Stalker"));
            } else if (messStr.contains("What is the status of Bonzo?")) {
                client.player.sendMessage(Text.of("New Necromancer"));
            } else if (messStr.contains("What is the status of Scarf?")) {
                client.player.sendMessage(Text.of("Apprentice Necromancer"));
            } else if (messStr.contains("What is the status of The Professor?")) {
                client.player.sendMessage(Text.of("Professor"));
            } else if (messStr.contains("What is the status of Thorn?")) {
                client.player.sendMessage(Text.of("Shaman Necromancer"));
            } else if (messStr.contains("What is the status of Livid?")) {
                client.player.sendMessage(Text.of("Master Necromancer"));
            } else if (messStr.contains("What is the status of Sadan?")) {
                client.player.sendMessage(Text.of("Necromancer Lord"));
            } else if (messStr.contains("What is the status of Maxor?")) {
                client.player.sendMessage(Text.of("Young Wither"));
            } else if (messStr.contains("What is the status of Goldor?")) {
                client.player.sendMessage(Text.of("Wither Soldier"));
            } else if (messStr.contains("What is the status of Storm?")) {
                client.player.sendMessage(Text.of("Elementalist"));
            } else if (messStr.contains("What is the status of Necron?")) {
                client.player.sendMessage(Text.of("Wither Lord"));
            } else if (messStr.contains("How many total Fairy Souls are there?")) {
                client.player.sendMessage(Text.of("222 Fairy Souls"));
            } else if (messStr.contains("How many Fairy Souls are there in Spider's Den?")) {
                client.player.sendMessage(Text.of("19"));
            } else if (messStr.contains("How many Fairy Souls are there in The End?")) {
                client.player.sendMessage(Text.of("12"));
            } else if (messStr.contains("How many Fairy Souls are there in The Barn?")) {
                client.player.sendMessage(Text.of("7"));
            } else if (messStr.contains("How many Fairy Souls are there in Mushroom Desert?")) {
                client.player.sendMessage(Text.of("8"));
            } else if (messStr.contains("How many Fairy Souls are there in Blazing Fortress?")) {
                client.player.sendMessage(Text.of("19"));
            } else if (messStr.contains("How many Fairy Souls are there in The Park?")) {
                client.player.sendMessage(Text.of("11"));
            } else if (messStr.contains("How many Fairy Souls are there in Jerry's Workshop?")) {
                client.player.sendMessage(Text.of("5"));
            } else if (messStr.contains("How many Fairy Souls are there in The Hub?")) {
                client.player.sendMessage(Text.of("79"));
            } else if (messStr.contains("How many Fairy Souls are there in Deep Caverns?")) {
                client.player.sendMessage(Text.of("21"));
            } else if (messStr.contains("How many Fairy Souls are there in Gold Mine?")) {
                client.player.sendMessage(Text.of("12"));
            } else if (messStr.contains("How many Fairy Souls are there in Dungeon Hub?")) {
                client.player.sendMessage(Text.of("7"));
            } else if (messStr.contains("How many Fairy Souls are there in Dwarven Mines?")) {
                client.player.sendMessage(Text.of("11"));
            } else if (messStr.contains("Which brother is on the Spider's Den?")) {
                client.player.sendMessage(Text.of("Rick"));
            } else if (messStr.contains("What is the name of Rick's brother?")) {
                client.player.sendMessage(Text.of("Pat"));
            } else if (messStr.contains("What is the name of the Painter in the Hub?")) {
                client.player.sendMessage(Text.of("Marco"));
            } else if (messStr.contains("What is the name of the person that upgrades pets?")) {
                client.player.sendMessage(Text.of("Kat"));
            } else if (messStr.contains("What is the name of the lady of the Nether?")) {
                client.player.sendMessage(Text.of("Elle"));
            } else if (messStr.contains("Which villager in the Village gives you a Rogue Sword?")) {
                client.player.sendMessage(Text.of("Jamie"));
            } else if (messStr.contains("How many unique minions are there?")) {
                client.player.sendMessage(Text.of("55"));
            } else if (messStr.contains("Which of these enemies does not spawn in the Spider's Den?")) {
                client.player.sendMessage(Text.of("Zombie"));
            } else if (messStr.contains("Which of these monsters only spawns at night?")) {
                client.player.sendMessage(Text.of("Zombie Villager"));
            } else if (messStr.contains("Which of these is not a dragon in The End?")) {
                client.player.sendMessage(Text.of("Zoomer Dragon"));
            }
        });
    }
}
