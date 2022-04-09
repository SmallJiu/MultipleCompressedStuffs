package cat.jiu.mcs.config;

import net.minecraftforge.common.config.Config;

public class CustemBlock {
	@Config.LangKey("config.mcs.custem_can_use_wrench_break")
	@Config.Comment("Custem block can use mod wrench to break, Use | to split value\n" + "food_name: food name, don't need 'mcs:'\n" + "meta: food meta")
	public String[] custem_can_use_wrench_break = new String[]{"block_name, meta", "compressed_tin_block|3", "compressed_bronze_block|3"};

	@Config.LangKey("config.mcs.custem_break_drop_item")
	@Config.Comment("Custem destroyer Right Click block drop items\n" + "drop items you can add many\n" + "Use # and | to split value\n" + "block_name: block name, need modid\n" + "meta: food meta\n" + "dName: drop item name, need modid\n" + "dAmout: drop item amout\n" + "aMeta: drop item meta")
	public String[] custem_break_drop_item = new String[]{"<block_name>#<meta>#<dName>|<dAmout>|<dMeta>|...", "mcs:compressed_tin_block#3#mcs:compressed_beef|6|1|mcs:compressed_beef|5|9", "minecraft:grass#0#mcs:compressed_beef|1|0|mcs:compressed_beef|5|65535"};
}
