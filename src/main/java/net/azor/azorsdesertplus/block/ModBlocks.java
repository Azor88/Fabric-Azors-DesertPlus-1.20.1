package net.azor.azorsdesertplus.block;

import net.azor.azorsdesertplus.AzorsDesertPlus;
import net.azor.azorsdesertplus.block.custom.QuicksandBlock;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;

public class ModBlocks {

    public static final Block QUICKSAND = registerBlock("quicksand",
            new QuicksandBlock(FabricBlockSettings.copyOf(Blocks.POWDER_SNOW)
                    .sounds(BlockSoundGroup.SAND).dropsNothing()));
    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, new Identifier(AzorsDesertPlus.MOD_ID, name), block);
    }
    private static Item registerBlockItem(String name, Block block) {
        return Registry.register(Registries.ITEM, new Identifier(AzorsDesertPlus.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings()));
    }
    public static void registerModBlocks() {
        AzorsDesertPlus.LOGGER.info("Registering Blocks");
    }
}
