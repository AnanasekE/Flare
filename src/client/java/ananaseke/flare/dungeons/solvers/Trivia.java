package ananaseke.flare.dungeons.solvers;

import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

import java.util.HashMap;
import java.util.Map;

public class Trivia {
    private static final MinecraftClient client = MinecraftClient.getInstance();
    private static final Map<String, String> questionsAndAnswers = new HashMap<>();

    public Trivia() {
        registerQuestions();

        ClientReceiveMessageEvents.GAME.register((message, overlay) -> { // TODO: add check if in dungeons
            String messStr = message.getString();
            assert client.player != null;

            for (Map.Entry<String, String> entry : questionsAndAnswers.entrySet()) {
                if (messStr.contains(entry.getKey())) {
                    client.player.sendMessage(Text.of(entry.getValue()));
                    break;
                }
            }
        });
    }

    private void registerQuestions() {
        questionsAndAnswers.put("What is the status of The Watcher?", "Stalker");
        questionsAndAnswers.put("What is the status of Bonzo?", "New Necromancer");
        questionsAndAnswers.put("What is the status of Scarf?", "Apprentice Necromancer");
        questionsAndAnswers.put("What is the status of The Professor?", "Professor");
        questionsAndAnswers.put("What is the status of Thorn?", "Shaman Necromancer");
        questionsAndAnswers.put("What is the status of Livid?", "Master Necromancer");
        questionsAndAnswers.put("What is the status of Sadan?", "Necromancer Lord");
        questionsAndAnswers.put("What is the status of Maxor?", "Young Wither");
        questionsAndAnswers.put("What is the status of Goldor?", "Wither Soldier");
        questionsAndAnswers.put("What is the status of Storm?", "Elementalist");
        questionsAndAnswers.put("What is the status of Necron?", "Wither Lord");
        questionsAndAnswers.put("How many total Fairy Souls are there?", "222 Fairy Souls");
        questionsAndAnswers.put("How many Fairy Souls are there in Spider's Den?", "19");
        questionsAndAnswers.put("How many Fairy Souls are there in The End?", "12");
        questionsAndAnswers.put("How many Fairy Souls are there in The Barn?", "7");
        questionsAndAnswers.put("How many Fairy Souls are there in Mushroom Desert?", "8");
        questionsAndAnswers.put("How many Fairy Souls are there in Blazing Fortress?", "19");
        questionsAndAnswers.put("How many Fairy Souls are there in The Park?", "11");
        questionsAndAnswers.put("How many Fairy Souls are there in Jerry's Workshop?", "5");
        questionsAndAnswers.put("How many Fairy Souls are there in The Hub?", "79");
        questionsAndAnswers.put("How many Fairy Souls are there in Deep Caverns?", "21");
        questionsAndAnswers.put("How many Fairy Souls are there in Gold Mine?", "12");
        questionsAndAnswers.put("How many Fairy Souls are there in Dungeon Hub?", "7");
        questionsAndAnswers.put("How many Fairy Souls are there in Dwarven Mines?", "11");
        questionsAndAnswers.put("Which brother is on the Spider's Den?", "Rick");
        questionsAndAnswers.put("What is the name of Rick's brother?", "Pat");
        questionsAndAnswers.put("What is the name of the Painter in the Hub?", "Marco");
        questionsAndAnswers.put("What is the name of the person that upgrades pets?", "Kat");
        questionsAndAnswers.put("What is the name of the lady of the Nether?", "Elle");
        questionsAndAnswers.put("Which villager in the Village gives you a Rogue Sword?", "Jamie");
        questionsAndAnswers.put("How many unique minions are there?", "55");
        questionsAndAnswers.put("Which of these enemies does not spawn in the Spider's Den?", "Zombie");
        questionsAndAnswers.put("Which of these monsters only spawns at night?", "Zombie Villager");
        questionsAndAnswers.put("Which of these is not a dragon in The End?", "Zoomer Dragon");
    }
}
