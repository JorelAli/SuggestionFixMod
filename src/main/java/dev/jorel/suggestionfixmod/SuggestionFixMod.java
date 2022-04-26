package dev.jorel.suggestionfixmod;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SuggestionFixMod implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("suggestionfixmod");

	@Override
	public void onInitialize() {
		LOGGER.info("Initialized suggestion fix mod!");
	}
}
