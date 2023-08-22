package cat.jiu.mcs.config;

import net.minecraftforge.common.config.Config;

public class CustemItem {
	@Config.LangKey("config.mcs.give_food_container")
	@Config.Comment("on food eaten give the empty container")
	public boolean give_food_container = true;

	@Config.LangKey("config.mcs.can_drop_cat_hair")
	@Config.Comment("set cat drop hair")
	public boolean can_drop_cat_hair = true;

	@Config.RangeDouble(
		min = 0.001D,
		max = 1.000D)
	@Config.LangKey("config.mcs.drop_cat_hair_chance")
	@Config.Comment("set cat drop hair chance")
	public double drop_cat_hair_chance = 0.050D;

	@Config.RangeDouble(
		min = 0.001D,
		max = 1.000D)
	@Config.LangKey("config.mcs.break_bedrock_chance")
	@Config.Comment("set cat hammer break bedrock chance")
	public double break_bedrock_chance = 0.1D;
}
