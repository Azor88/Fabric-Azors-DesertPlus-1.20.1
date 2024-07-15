package net.azor.azorsdesertplus.item;

import net.azor.azorsdesertplus.AzorsDesertPlus;
import net.azor.azorsdesertplus.block.ModBlocks;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {
    public static final ItemGroup DESERT_GROUP = Registry.register(Registries.ITEM_GROUP,
            new Identifier(AzorsDesertPlus.MOD_ID, "modname"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.azorsdesertplus.modname"))
                    .icon(() -> new ItemStack(Items.DEAD_BUSH)).entries((displayContext, entries) -> {
                        entries.add(ModItems.QUICKSAND_BUCKET);
                    }).build());

    public static void registerItemGroups() {
        AzorsDesertPlus.LOGGER.info("Registering Item Group!");
    }
}
