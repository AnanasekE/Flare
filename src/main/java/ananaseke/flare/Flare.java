package ananaseke.flare;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Flare implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger("flare");

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Hello Fabric world!");

		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			dispatcher.register(CommandManager.literal("test_skill_level_up").executes(context -> {
//				context.getSource().sendChatMessage(SentMessage.of(SignedMessage.ofUnsigned("Hello Fabric from test_skill_level_up command!")), false, null);
				context.getSource().sendFeedback(() -> Text.of("  §b§lSKILL LEVEL UP §3Farming §8VI➜§3VII"), false);
				return 1;
			}));
		});

	}
}