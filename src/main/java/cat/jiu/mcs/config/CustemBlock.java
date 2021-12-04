package cat.jiu.mcs.config;

import net.minecraftforge.common.config.Config;

public class CustemBlock {
	@Config.LangKey("config.mcs.custem_change_block")
	@Config.Comment(
			"Custem what the block can change blocks, Use | to split value\n"
			+ "don't need restarting game or rejoin save\n"
			+ "block_name:who block can be change\n"
			+ "meta:meta of who block can change\n"
			+ "change_name:when the block is change,is change or give to block or item name\n"
			+ "amout:change stuff amout,only item valid\n"
			+ "meta:meta of change stuff,if is block,it must be >= 15\n"
			+ "tick:after ticks\n"
			+ "s:after seconds\n"
			+ "m:after minutes\n"
			+ "drop_block:can drop change stuff,only block valid")
	public String[] custem_change_block = new String[] { 
		"block_name, meta, change_name, amout, meta, tick, s, m, drop_block?",
		"compressed_ghast_tear|0|minecraft:diamond_block|99|0|0|2|0|true",
		"compressed_ender_pearl|1|minecraft:diamond|99|0|0|3|0|true"
	};
	
	@Config.LangKey("config.mcs.custem_can_use_wrench_break")
	@Config.Comment("Custem block can use mod wrench to break, Use | to split value\n"
			+ "food_name: food name, don't need 'mcs:'\n"
			+ "meta: food meta")
	public String[] custem_can_use_wrench_break = new String[] { 
		"block_name, meta",
		"compressed_tin_block|3",
		"compressed_bronze_block|3"
	};
	
	@Config.LangKey("config.mcs.custem_break_drop_item")
	@Config.Comment("Custem destroyer Right Click block drop items\n"
			+ "drop items you can add many\n"
			+ "Use # and | to split value\n"
			
			+ "block_name: block name, need modid\n"
			+ "meta: food meta\n"
			+ "dName: drop item name, need modid\n"
			+ "dAmout: drop item amout\n"
			+ "aMeta: drop item meta")
	public String[] custem_break_drop_item = new String[] { 
		"block_name, meta, dName, dAmout, dMeta,...,...",
		"mcs:compressed_tin_block#3#mcs:compressed_beef|6|1|mcs:compressed_beef|5|9",
		"minecraft:grass#0#mcs:compressed_beef|1|0|mcs:compressed_beef|5|65535"
	};
}
