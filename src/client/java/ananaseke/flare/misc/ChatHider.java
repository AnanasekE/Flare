package ananaseke.flare.misc;

import ananaseke.flare.Config;
import ananaseke.flare.FlareClient;
import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.minecraft.text.Text;

public class ChatHider {
    public static void initialize() {
        Config config = AutoConfig.getConfigHolder(Config.class).getConfig();


        ClientReceiveMessageEvents.ALLOW_GAME.register((message, overlay) -> {
//            FlareClient.LOGGER.info(message.getString());
            String messageStr = message.getString();

            if (messageStr.contains("You earned ") && messageStr.contains("Event EXP from playing SkyBlock!")) {
                return false;
            } else if (config.mortMessageHider && messageStr.contains("[NPC] Mort:")) {
                return false;
            } else if (config.bossMessageHider && (
                    messageStr.contains("[BOSS] The Watcher: ") ||
                            messageStr.contains("[BOSS] The Professor: ") ||
                            messageStr.contains("[BOSS] Scarf: ") ||
                            messageStr.contains("[BOSS] Bonzo: ") ||
                            messageStr.contains("[BOSS] Thorn: ") ||
                            messageStr.contains("[BOSS] Livid: ") ||
                            messageStr.contains("[BOSS] Sadan: ") ||
                            messageStr.contains("[BOSS] Storm: ") ||
                            messageStr.contains("[BOSS] Goldor: ") ||
                            messageStr.contains("[BOSS] Maxor: ") ||
                            messageStr.contains("[BOSS] Necron: ")
            )) {
                return false;
            } else if (config.killComboHider && messageStr.contains("Kill Combo")) {
                return false;
            } else if (config.crowdHider && messageStr.contains("[CROWD]")) {
                return false;
            } else if (config.killComboHider && messageStr.contains("Your Kill Combo has expired! You reached a ")) {
                return false;
            } else if (config.superboomHider && messageStr.contains("has obtained Superboom TNT!")) {
                return false;
            } else if (config.dungeonBuffHider && (
                    messageStr.contains("DUNGEON BUFF!") ||
                            messageStr.contains("     Also granted you") ||
                            messageStr.contains("     Granted you") ||
                            messageStr.contains("has obtained Blessing")
            )) {
                return false;
            } else if (config.reviveStoneHider && messageStr.contains("has obtained Revive Stone!")) {
                return false;
            } else if (config.abilityHider && (
                    messageStr.contains("is ready to use! Press DROP to activate it!") ||
                            messageStr.contains("is now available!")
            )) {
                return false;
            } else if (config.witherEssenceHider && messageStr.contains(" found a Wither Essence! Everyone gains an extra essence!")) {
                return false;
            } else if (config.abilityDamageHider && (
                    messageStr.contains("Your Guided Sheep hit ") &&
                            (messageStr.contains(" enemy for ") || messageStr.contains(" enemies for ")) &&
                            messageStr.contains(" damage.")
            )) {
                return false;
            } else if (config.youArePlayingOnProfileHider && (
                            messageStr.contains("You are playing on profile:") ||
                            messageStr.contains("Profile ID: ")
            )) {
                return false;
            }

            return true;
        });

    }
}

