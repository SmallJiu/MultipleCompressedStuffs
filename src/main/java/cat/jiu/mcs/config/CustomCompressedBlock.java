package cat.jiu.mcs.config;

import net.minecraftforge.common.config.Config;

public class CustomCompressedBlock {
	@Config.RequiresMcRestart
	@Config.LangKey("config.mcs.enable_test_stuff")
	@Config.Comment("Enable Test Mod Stuffs")
	public boolean Enable_Test_Stuff = false;
	
	@Config.RequiresMcRestart
	@Config.LangKey("config.mcs.enable_mod_stuff")
	@Config.Comment("Enable Other Mod Stuffs")
	public boolean Enable_Mod_Stuff = false;
	
	public final ModStuff ModStuff = new ModStuff();
	
	@Config.RequiresMcRestart
	@Config.LangKey("config.mcs.ore_dict_register")
	@Config.Comment("register OreDict")
	public String[] ore_dict_register = new String[] {
		"ItemName, ItemMeta, OreDict",
		"minecraft:obsidian|0|obsidian",
		"minecraft:end_stone|0|endstone" 
	};
	
	public final CustemAlreadyStuff custem_already_stuff = new CustemAlreadyStuff();
}
