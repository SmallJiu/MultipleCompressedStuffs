package cat.jiu.mcs.config;

import net.minecraftforge.common.config.Config;

public class CustomCompressedBlock {
	@Config.RequiresMcRestart
	@Config.LangKey("config.mcs.name_of_uncompressed_block")
	@Config.Comment("UnFinish !\nList of uncompressed block of custom compressed block name")
	public String[] name_of_uncompressed_block = new String[] { "jiu_tech:data_base", "jiu_tech:magic_dirt" };
	
	@Config.RequiresMcRestart
	@Config.LangKey("config.mcs.enable_test_stuff")
	@Config.Comment("Enable Test Mod Stuffs")
	public boolean enable_test_stuff = false;
	
	@Config.RequiresMcRestart
	@Config.LangKey("config.mcs.enable_mod_stuff")
	@Config.Comment("Enable Other Mod Stuffs")
	public boolean Enable_Mod_Stuff = false;
	
	public final ModStuff ModStuff = new ModStuff();
	
	public final OreDict register_ore = new OreDict();
	
	public class OreDict {
		@Config.RequiresMcRestart
		@Config.LangKey("config.mcs.ore_dict_register")
		@Config.Comment("register OreDict")
		public String[] ore_dict_register = new String[] {
			"ItemName, ItemMeta, OreDict",
			"minecraft:obsidian|0|obsidian",
			"minecraft:end_stone|0|endstone" };
	}
	
	public final CustemAlreadyStuff custem_already_stuff = new CustemAlreadyStuff();
}
