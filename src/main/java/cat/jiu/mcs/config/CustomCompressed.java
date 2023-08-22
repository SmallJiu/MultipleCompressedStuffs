package cat.jiu.mcs.config;

import net.minecraftforge.common.config.Config;

public class CustomCompressed {
//	@Config.RequiresMcRestart
//	@Config.LangKey("config.mcs.enable_test_stuff")
//	@Config.Comment("Enable Test Mod Stuffs")
//	public boolean Enable_Test_Stuff = false;

	@Config.LangKey("config.mcs.custom.mod_stuff")
	@Config.Comment("Enable Mod Stuffs")
	public final ModStuff Mod_Stuff = new ModStuff();
	
	@Config.LangKey("config.mcs.custom.custem_already_stuff")
	@Config.Comment("Enable Mod Stuffs")
	public final CustemAlreadyStuff custem_already_stuff = new CustemAlreadyStuff();
}
