package net.azor.azorsdesertplus;

import net.azor.azorsdesertplus.block.ModBlocks;
import net.azor.azorsdesertplus.item.ModItemGroups;
import net.azor.azorsdesertplus.item.ModItems;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AzorsDesertPlus implements ModInitializer {

    public static final String MOD_ID = "azorsdesertplus";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {

		LOGGER.info("Hello Fabric world!");
		ModBlocks.registerModBlocks();
		ModItems.registerModItems();
		ModItemGroups.registerItemGroups();
	}
}