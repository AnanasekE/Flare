package ananaseke.flare.dungeons.solvers;

import ananaseke.flare.FlareClient;
import ananaseke.flare.callbacks.DungeonEnterCallback;
import ananaseke.flare.misc.AnnouncementManager;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.HashMap;
import java.util.Map;

public class Riddles {
    private static MinecraftClient client = MinecraftClient.getInstance();
    private static Map<String, String> npcStatements = new HashMap<>();
    private static Optional<String> messageOp = Optional.empty();
    static boolean isRoomOpen = false;

    public static void initialize() {
        ClientReceiveMessageEvents.GAME.register((message, overlay) -> {
//            FlareClient.LOGGER.info(message.getString().replaceAll("(§[0-9a-fklmnor])", ""));
            String messStr = message.getString().replaceAll("(§[0-9a-fklmnor])", "");
            String response = solveRiddle(messStr);
//            FlareClient.LOGGER.info(response);
            if (response != null) {
                assert client.player != null;
                client.player.sendMessage(Text.of(response));
                messageOp = Optional.of(response);
            }
        });
        HudRenderCallback.EVENT.register((drawContext, tickCounter) -> {
            if (messageOp.isPresent()) {
                AnnouncementManager.announceToPlayer(messageOp.get(), 5000);
                messageOp = Optional.empty();
            }
        });
        DungeonEnterCallback.EVENT.register((floor) -> {
            npcStatements.clear();
        });
    }


    public static String solveRiddle(String message) {
        // Regular expression to extract the NPC name and the statement
        Pattern pattern = Pattern.compile("\\[NPC] (\\w+): (.+)");
        Matcher matcher = pattern.matcher(message);

        if (matcher.find()) {
            String npcName = matcher.group(1);
            String statement = matcher.group(2);

            if (client.player == null) return null;
            client.player.networkHandler.getListedPlayerListEntries().stream().limit(80L).toList().forEach(entry -> {
                if (entry.getDisplayName() == null) return;
                if (entry.getDisplayName().getString().contains("Three Weirdos")) isRoomOpen = true;
            });
            if (!isRoomOpen) return null;
            // Store the NPC name and their statement
            npcStatements.put(npcName, statement);

            // Check if we have all the necessary statements (assuming 3 NPCs per riddle)
            if (npcStatements.size() >= 3) {
                return solve(npcStatements);
            }
        }

        return null;
    }

    private static String solve(Map<String, String> npcStatements) {
        // This maps the NPC names to their statements
        Map<String, String> statementsMap = new HashMap<>(npcStatements);

        String alpha = null, beta = null, gamma = null;
        String alphaStatement = null, betaStatement = null, gammaStatement = null;

        for (Map.Entry<String, String> entry : statementsMap.entrySet()) {
            String npc = entry.getKey();
            String statement = entry.getValue();

            if (statement.contains("One of us is telling the truth!") ||
                    statement.contains("The reward is not in my chest. They are both lying.") ||
                    statement.contains("My chest doesn't have the reward. At least one of the others is telling the truth!") ||
                    statement.contains("They are both lying, the reward is in my chest!") ||
                    statement.contains("We are all telling the truth!") ||
                    statement.contains("Both of them are telling the truth. Also, ")) {
                alpha = npc;
                alphaStatement = statement;
            } else if (statement.contains("They are both telling the truth. The reward isn't in") ||
                    statement.contains("The reward is in my chest!") ||
                    statement.contains("My chest doesn't have the reward. We are all telling the truth.") ||
                    statement.contains("They are both telling the truth, the reward is in") ||
                    statement.contains("At least one of them is lying, and the reward is not in") ||
                    statement.contains(" is telling the truth.")) {
                beta = npc;
                betaStatement = statement;
            } else {
                gamma = npc;
                gammaStatement = statement;
            }
        }

        FlareClient.LOGGER.info(alphaStatement + ", " + betaStatement + " / " + gammaStatement);

        // Logic for Riddle 1
        if (alphaStatement.contains("One of us is telling the truth!") &&
                betaStatement.contains("They are both telling the truth. The reward isn't in") &&
                gammaStatement.contains("The reward is not in my chest!")) {

            return "The reward is in " + gamma + "'s chest.";
        }

        // Logic for Riddle 2
        if (alphaStatement.contains("The reward is not in my chest. They are both lying.") &&
                betaStatement.contains("The reward is in my chest!") &&
                gammaStatement.contains("The reward isn't in any of our chests.")) {

            return "The reward is in " + gamma + "'s chest.";
        }

        // Logic for Riddle 3
        if (alphaStatement.contains("My chest doesn't have the reward. At least one of the others is telling the truth!") &&
                betaStatement.contains("My chest doesn't have the reward. We are all telling the truth.") &&
                gammaStatement.contains("One of the others is lying!")) {

            return "The reward is in " + beta + "'s chest.";
        }

        // Logic for Riddle 4
        if (alphaStatement.contains("They are both lying, the reward is in my chest!") &&
                betaStatement.contains("They are both telling the truth, the reward is in " + alpha + "'s chest!") &&
                gammaStatement.contains("My chest has the reward and I'm telling the truth!")) {

            return "The reward is in " + gamma + "'s chest.";
        }

        // Logic for Riddle 5
        if (alphaStatement.contains("We are all telling the truth!") &&
                betaStatement.contains("At least one of them is lying, and the reward is not in " + gamma + "'s chest!") &&
                gammaStatement.contains("Alpha is telling the truth and the reward is in his chest.")) {

            return "The reward is in " + beta + "'s chest.";
        }

        // Logic for Riddle 6
        if (alphaStatement.contains("Both of them are telling the truth. Also, " + beta + " has the reward in their chest!") &&
                betaStatement.contains(gamma + " is telling the truth.") &&
                gammaStatement.contains("My chest has the reward!")) {

            return "The reward is in " + alpha + "'s chest.";
        }

        return null;
    }

}
