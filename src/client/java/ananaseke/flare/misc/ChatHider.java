package ananaseke.flare.misc;

import ananaseke.flare.Config;
import ananaseke.flare.FlareClient;
import ananaseke.flare.KeyBinds;
import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ChatHider {
    public List<MessageFilter> filterList = new ArrayList<>();

    public ChatHider() {
        Config config = AutoConfig.getConfigHolder(Config.class).getConfig();

        // TODO: retake some messages and make them regexes
        filterList.add(new MessageFilter(c -> c.bossMessageHider, Set.of(
                "\\[BOSS\\] (The Watcher|The Professor|Scarf|Bonzo|Thorn|Livid|Sadan|Storm|Goldor|Maxor|Necron):"
        )));

        filterList.add(new MessageFilter(c -> c.mortMessageHider, Set.of(
                "§e\\[NPC\\] §bMort§f:"
        )));

        filterList.add(new MessageFilter(c -> true, Set.of(
                "You earned .+ Event EXP from playing SkyBlock!"
        )));

        filterList.add(new MessageFilter(c -> c.killComboHider, Set.of(
                "Kill Combo", "Your Kill Combo has expired! You reached a "
        )));

        filterList.add(new MessageFilter(c -> c.crowdHider, Set.of(
                "\\[CROWD\\]"
        )));

        filterList.add(new MessageFilter(c -> c.superboomHider, Set.of(
                "has obtained Superboom TNT!"
        )));

        filterList.add(new MessageFilter(c -> c.dungeonBuffHider, Set.of(
                "DUNGEON BUFF!","Also granted you","Granted you","has obtained Blessing"
        )));

        filterList.add(new MessageFilter(c -> c.reviveStoneHider, Set.of(
                "has obtained Revive Stone!"
        )));

        filterList.add(new MessageFilter(c -> c.abilityHider, Set.of(
                "is ready to use! Press DROP to activate it!","is now available!"
        )));

        filterList.add(new MessageFilter(c -> c.witherEssenceHider, Set.of(
                " found a Wither Essence! Everyone gains an extra essence!"
        )));

        filterList.add(new MessageFilter(c -> c.abilityDamageHider, Set.of(
                "Your Guided Sheep hit .+ (enemy|enemies) for .+ damage\\."
        )));

        filterList.add(new MessageFilter(c -> c.youArePlayingOnProfileHider, Set.of(
                "§aYou are playing on profile: .+",
                "Profile ID: .+"
        )));

        // [STATUE] Oruo the Omniscient: Answer incorrectly, and your moment of ineptitude will live on for generations.

        ClientReceiveMessageEvents.ALLOW_GAME.register((message, overlay) -> {
            String messageStr = message.getString();
//            if (!(messageStr.contains("Defense") && messageStr.contains("Mana")) || KeyBinds.devKeybind.wasPressed()) {
//                FlareClient.LOGGER.info("TEST: {}", messageStr); // FIXME
//            }


            for (MessageFilter filter : filterList) {
                if (filter.shouldHide(messageStr, config)) {
                    FlareClient.LOGGER.info("Hiding message: {}", messageStr);
                    return false;
                }
            }

            return true;
        });
    }
}
