package cat.jiu.mcs.config;

import net.minecraftforge.common.config.Config;

public class CustomCompressedBlock {
//	@Config.RequiresMcRestart
//	@Config.LangKey("config.mcs.enable_test_stuff")
//	@Config.Comment("Enable Test Mod Stuffs")
//	public boolean Enable_Test_Stuff = false;

	@Config.LangKey("config.mcs.enable_mod_stuff")
	@Config.Comment("Enable Other Mod Stuffs")
	@Config.RequiresMcRestart
	public boolean Enable_Mod_Stuff = false;

	public final ModStuff Mod_Stuff = new ModStuff();

	public final CustemAlreadyStuff custem_already_stuff = new CustemAlreadyStuff();
}
