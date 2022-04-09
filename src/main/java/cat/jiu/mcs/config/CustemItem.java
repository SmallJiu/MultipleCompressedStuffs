package cat.jiu.mcs.config;

import net.minecraftforge.common.config.Config;

public class CustemItem {
	@Config.LangKey("config.mcs.custem_eat_effect")
	@Config.Comment("Custem player eat the food give the effects, Use # and | to split value\n" + "food_name: food name, don't need 'mcs:'\n" + "meta: food meta\n" + "effect_name: effect name, need modid\n" + "time: effect time, measure time by the second, max value is 107374182\n" + "level: effect level")
	public String[] custem_eat_effect = new String[]{"food_name, meta, effects, effect_name, time(seconds), level", "compressed_cooked_beef#1#minecraft:luck|107374182|2|minecraft:speed|10|2", "compressed_cooked_beef#3#minecraft:speed|10|2|minecraft:luck|107374182|2"};

	@Config.LangKey("config.mcs.give_food_container")
	@Config.Comment("on food ate give the empty container")
	public boolean give_food_container = false;

	@Config.LangKey("config.mcs.can_drop_cat_hair")
	@Config.Comment("set cat drop hair")
	public boolean can_drop_cat_hair = true;

	@Config.RangeDouble(
		min = 0.001D,
		max = 0.999D)
	@Config.LangKey("config.mcs.drop_cat_hair_chance")
	@Config.Comment("set cat drop hair chance")
	public double drop_cat_hair_chance = 0.950D;

	@Config.RangeDouble(
		min = 0.001D,
		max = 0.999D)
	@Config.LangKey("config.mcs.break_bedrock_chance")
	@Config.Comment("set cat hammer break bedrock chance")
	public double break_bedrock_chance = 0.1D;
}
