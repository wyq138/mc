package com.minecraft.block;

public enum BlockType {

    AIR(0, 0, false),
    STONE(1, 0, true),
    GRASS(3, 0, true),
    DIRT(2, 0, true),
    COBBLESTONE(16, 0, true),
    WOOD(5, 1, true),
    SAPLING(15, 3, false),
    BEDROCK(17, 0, true),
    WATER(8, 13, false),
    STATIONARY_WATER(9, 13, false),
    LAVA(10, 30, false),
    STATIONARY_LAVA(11, 30, false),
    SAND(12, 2, true),
    GRAVEL(13, 3, true),
    GOLD_ORE(14, 0, true),
    IRON_ORE(15, 0, true),
    COAL_ORE(16, 0, true),
    LOG(17, 1, true),
    LEAVES(18, 1, false),
    SPONGE(19, 8, true),
    GLASS(20, 0, false),
    LAPIS_ORE(21, 0, true),
    LAPIS_BLOCK(22, 4, true),
    DISPENSER(23, 10, true),
    SANDSTONE(24, 2, true),
    NOTE_BLOCK(25, 10, true),
    BEDROCK2(26, 0, true),
    POWERED_RAIL(27, 8, true),
    DETECTOR_RAIL(28, 8, true),
    ACTIVATOR_RAIL(29, 8, true),
    RAIL(30, 8, true),
    GOLD_BLOCK(41, 7, true),
    IRON_BLOCK(42, 6, true),
    DOUBLE_STONE_SLAB(43, 0, true),
    STONE_SLAB(44, 0, true),
    BRICK_BLOCK(45, 7, true),
    TNT_BLOCK(46, 8, true),
    BOOKSHELF(47, 3, true),
    MOSSY_COBBLESTONE(48, 0, true),
    OBSIDIAN(49, 5, true),
    TORCH(50, 0, false),
    FIRE(51, 15, false),
    MOB_SPAWNER(52, 2, true),
    OAK_STAIRS(53, 3, true),
    CHEST(54, 10, true),
    REDSTONE_WIRE(55, 0, false),
    DIAMOND_ORE(56, 0, true),
    DIAMOND_BLOCK(57, 8, true),
    CRAFTING_TABLE(58, 11, true),
    WHEAT_BLOCK(59, 4, true),
    FARMLAND(60, 2, true),
    FURNACE(61, 12, true),
    BURNING_FURNACE(62, 12, true),
    SIGN_POST(63, 4, false),
    WOODEN_DOOR(64, 4, false),
    LADDER(65, 4, false),
    RAILS(66, 8, true),
    COBBLESTONE_STAIRS(67, 0, true),
    WALL_SIGN(68, 4, false),
    LEVER(69, 0, false),
    STONE_PRESSURE_PLATE(70, 0, false),
    IRON_DOOR(71, 6, false),
    WOODEN_PRESSURE_PLATE(72, 3, false),
    REDSTONE_ORE(73, 0, true),
    REDSTONE_TORCH_OFF(74, 0, false),
    REDSTONE_TORCH_ON(75, 0, false),
    STONE_BUTTON(76, 0, false),
    SNOW_LAYER(78, 13, false),
    ICE(79, 9, false),
    SNOW_BLOCK(80, 13, true),
    CACTUS(81, 6, false),
    CLAY(82, 9, true),
    SUGAR_CANE_BLOCK(83, 4, false),
    JUKEBOX(84, 4, true),
    FENCE(85, 4, true),
    PUMPKIN(86, 6, true),
    NETHERRACK(87, 5, true),
    SOULSAND(88, 10, true),
    GLOWSTONE(89, 10, true),
    PORTAL(90, 14, false),
    JACK_O_LANTERN(91, 4, true),
    CAKE_BLOCK(92, 10, false),
    REDSTONE_REPEATER_OFF(93, 5, true),
    REDSTONE_REPEATER_ON(94, 5, true),
    ORANGE_FLOWER(37, 1, false),
    ROSE_FLOWER(38, 1, false),
    BROWN_MUSHROOM(39, 13, false),
    RED_MUSHROOM(40, 13, false),
    GOLD_RAIL(40, 8, true),
    EMERALD_ORE(129, 0, true),
    EMERALD_BLOCK(133, 9, true),
    COMMAND_BLOCK(137, 10, true),
    BEACON(138, 7, true),
    COBBLESTONE_WALL(139, 0, true),
    FLOWER_POT(140, 4, true),
    CARROTS(141, 4, false),
    POTATOES(142, 4, false),
    WOODEN_BUTTON(143, 4, false),
    SKULL(144, 0, true),
    ANVIL(145, 0, true),
    TRAPPED_CHEST(146, 10, true),
    LIGHT_WEIGHTED_PRESSURE_PLATE(147, 0, false),
    HEAVY_WEIGHTED_PRESSURE_PLATE(148, 0, false),
    COMPARATOR_OFF(149, 5, true),
    COMPARATOR_ON(150, 5, true),
    DAYLIGHT_DETECTOR(151, 0, true),
    REDSTONE_BLOCK(152, 0, true),
    QUARTZ_ORE(153, 0, true),
    HOPPER(154, 10, true),
    QUARTZ_BLOCK(155, 0, true),
    QUARTZ_STAIRS(156, 0, true),
    ACTIVATOR_RAIL(157, 8, true),
    DROPPER(158, 10, true),
    IRON_TRAPDOOR(167, 6, false),
    PRISMARINE(168, 0, true),
    SEA_LANTERN(169, 0, true),
    HAY_BLOCK(170, 4, true),
    CARPET(171, 4, false),
    HARDENED_CLAY(172, 9, true),
    COAL_BLOCK(173, 10, true),
    PACKED_ICE(174, 9, true),
    DOUBLE_PLANT(175, 4, false),
    STANDING_BANNER(176, 4, false),
    WALL_BANNER(177, 4, false),
    DAYLIGHT_DETECTOR_INVERTED(178, 0, true),
    RED_SANDSTONE(179, 2, true),
    STONE_SLAB2(180, 0, true),
    DOUBLE_STONE_SLAB2(181, 0, true),
    SPRUCE_STAIRS(182, 4, true),
    BIRCH_STAIRS(183, 5, true),
    JUNGLE_STAIRS(184, 3, true),
    ACACIA_STAIRS(185, 6, true),
    DARK_OAK_STAIRS(186, 7, true),
    SLIME_BLOCK(165, 9, true),
    BARRIER(166, 0, true),
    IRON_TRAPDOOR(167, 6, false),
    PRISMARINE_STAIRS(188, 0, true),
    DARK_PRISMARINE(189, 0, true),
    DARK_PRISMARINE_STAIRS(190, 0, true),
    END_ROD(191, 9, true),
    CHORUS_PLANT(199, 10, false),
    CHORUS_FLOWER(200, 10, true),
    PURPUR_BLOCK(201, 10, true),
    PURPUR_PILLAR(202, 10, true),
    PURPUR_STAIRS(203, 10, true),
    END_BRICKS(206, 10, true),
    GRASS_PATH(208, 2, true),
    END_GATE(209, 10, true),
    REPEATING_COMMAND_BLOCK(210, 10, true),
    CHAIN_COMMAND_BLOCK(211, 10, true),
);

    private int id;
    private int textureIndex;
    private boolean solid;

    BlockType(int id, int textureIndex, boolean solid) {
        this.id = id;
        this.textureIndex = textureIndex;
        this.solid = solid;
    }

    public int getId() {
        return id;
    }

    public float getTextureX() {
        return (textureIndex % 16) / 16.0f;
    }

    public float getTextureY() {
        return (textureIndex / 16) / 16.0f;
    }

    public boolean isSolid() {
        return solid;
    }

    public static BlockType fromId(int id) {
        for (BlockType type : values()) {
            if (type.id == id) {
                return type;
            }
        }
        return AIR;
    }
}
