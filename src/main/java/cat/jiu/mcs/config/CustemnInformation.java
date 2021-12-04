package cat.jiu.mcs.config;

import net.minecraftforge.common.config.Config;

public class CustemnInformation {
	@Config.LangKey("config.mcs.custem_info.item.food")
	@Config.Comment("Custem Item Tooltip Information, Use # to split value, Use | to create new line\n"
			+ "name: name for item, don't need 'mcs:'\n"
			+ "meta: item meta\n"
			+ "info: custem information, please use | to create new line")
	public String[] item_food = new String[] { 
		"name, meta, info",
		"compressed_cooked_beef#2#Hi, this is custem information,|You can use config to change it.",
		"compressed_cooked_beef#0#Hi, this is custem information,|You can use config to change it."
	};
	
	@Config.LangKey("config.mcs.custem_info.item")
	@Config.Comment("Custem Item Tooltip Information, Use # to split value, Use | to create new line\n"
			+ "name: name for item, don't need 'mcs:'\n"
			+ "meta: item meta\n"
			+ "info: custem information, please use | to create new line")
	public String[] item = new String[] { 
		"name, meta, info",
		"compressed_plate_iron#2#Hi, this is custem information,|You can use config to change it.",
		"compressed_plate_iron#1#Hi, this is custem information,|You can use config to change it."
	};
	
	@Config.LangKey("config.mcs.custem_info.block")
	@Config.Comment("Custem Block Tooltip Information, Use # to split value, Use | to create new line\n"
			+ "name: name for block, don't need 'mcs:'\n"
			+ "meta: block meta\n" 
			+ "info: custem information, please use | to create new line")
	public String[] block = new String[] { 
		"name, meta, info",
		"compressed_bone_block#1#Hi, this is custem information,|You can use config to change it.",
		"compressed_diamond_block#3#Hi, this is custem information,|You can use config to change it."
	};
}
