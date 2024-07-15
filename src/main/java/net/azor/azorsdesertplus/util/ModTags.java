package net.azor.azorsdesertplus.util;

import net.azor.azorsdesertplus.AzorsDesertPlus;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;


public class ModTags {
    public static final TagKey<EntityType<?>> QUICKSAND_WALKABLE_MOBS = createTag("quicksand_walkable_mobs");

    private static TagKey<EntityType<?>> createTag(String name) {
        return TagKey.of(RegistryKeys.ENTITY_TYPE, new Identifier(AzorsDesertPlus.MOD_ID, name));
    }

}
