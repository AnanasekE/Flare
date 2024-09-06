package ananaseke.flare.dungeons;

import ananaseke.flare.callbacks.DungeonEnterCallback;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.PlayerListEntry;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScoreCalculator implements ClientModInitializer {
    private final MinecraftClient mc = MinecraftClient.getInstance();

    private int totalScore = 0;
    private int skillScore = 20;
    private int explorationScore = 0;
    private int timeScore = 0;
    private int bonusScore = 0;
    private int floor;

    private final Map<Integer, Float> secretPercentageNeeded = new HashMap<>();
    private final Map<Integer, Integer> timeLimit = new HashMap<>();
    private long timeElapsed = 0;

    private int completedRooms;
    private int totalRooms;
    private float secretPercentFound;
    private float secretPercentNeeded;
    private int failedPuzzles;
    private int deathPenalty;

    private void updateScore() {
        secretPercentageNeeded.clear();
        secretPercentFound = 0;
        secretPercentNeeded = secretPercentageNeeded.getOrDefault(floor, 7F);


        failedPuzzles = 0;
        deathPenalty = 0;

        skillScore = 20 + (80 * (completedRooms / totalRooms)) - (10 * failedPuzzles) - deathPenalty;
        explorationScore = (60 * (completedRooms / totalRooms)) + (int) (40 * (secretPercentFound / secretPercentNeeded));
    }

    private List<PlayerListEntry> getTablist() {
        if (mc.player == null) return null;
        return mc.player.networkHandler.getListedPlayerListEntries().stream().limit(80L).toList();
    }

    @Override
    public void onInitializeClient() {
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

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            updateScore();
        });

    }
}
