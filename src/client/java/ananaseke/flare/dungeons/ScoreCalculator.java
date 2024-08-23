package ananaseke.flare.dungeons;

import ananaseke.flare.callbacks.DungeonEnterCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.text.Text;

import java.sql.Time;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScoreCalculator {
    private static MinecraftClient client = MinecraftClient.getInstance();

    private static int totalScore = 0;
    private static int skillScore = 20;
    private static int explorationScore = 0;
    private static int timeScore = 0;
    private static int bonusScore = 0;
    private static int floor;

    private static Map<Integer, Float> secretPercentageNeeded = new HashMap<>();
    private static Map<Integer, Integer> timeLimit = new HashMap<>();
    private static long timeElapsed = 0;

    private static int completedRooms;
    private static int totalRooms;
    private static float secretPercentFound;
    private static float secretPercentNeeded;
    private static int failedPuzzles;
    private static int deathPenalty;

    public static void initialize() {
        DungeonEnterCallback.EVENT.register(floor1 -> floor = floor1);
        secretPercentageNeeded.put(1, 0.3F);
        secretPercentageNeeded.put(2, 0.4F);
        secretPercentageNeeded.put(3, 0.5F);
        secretPercentageNeeded.put(4, 0.6F);
        secretPercentageNeeded.put(5, 0.7F);
        secretPercentageNeeded.put(6, 0.85F);
        secretPercentageNeeded.put(7, 1F);

        timeLimit.put(1, 600);
        timeLimit.put(2, 600);
        timeLimit.put(3, 600);
        timeLimit.put(4, 720);
        timeLimit.put(5, 600);
        timeLimit.put(6, 720);
        timeLimit.put(7, 840);
        timeLimit.put(8, 480);
        timeLimit.put(9, 480);
        timeLimit.put(10, 480);
        timeLimit.put(11, 480);
        timeLimit.put(12, 480);
        timeLimit.put(13, 600);
        timeLimit.put(14, 840);

    }

    private static void updateScore() {
        secretPercentageNeeded.clear();
        secretPercentFound = 0;
        secretPercentNeeded = secretPercentageNeeded.getOrDefault(floor, 7F);


        failedPuzzles = 0;
        deathPenalty = 0;

//        skillScore = 20 + (80 * (completedRooms / totalRooms)) - (10 * failedPuzzles) - deathPenalty;
//        explorationScore = (60 * (completedRooms / totalRooms)) + (40 * (secretPercentFound / secretPercentNeeded));
    }

    private static List<PlayerListEntry> getTablist() {
        if (client.player == null) return null;
        return client.player.networkHandler.getListedPlayerListEntries().stream().limit(80L).toList();
    }

}
