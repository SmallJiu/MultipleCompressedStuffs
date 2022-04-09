package cat.jiu.mcs.config;

import net.minecraftforge.common.config.Config;

public class ModStuff {
	@Config.RequiresMcRestart
	@Config.LangKey("config.mcs.enable_mod_stuff.tf")
	@Config.Comment("Enable Other Mod Stuffs")
	public boolean ThermalFoundation = false;

	@Config.RequiresMcRestart
	@Config.LangKey("config.mcs.enable_mod_stuff.de")
	@Config.Comment("Enable Other Mod Stuffs")
	public boolean DraconicEvolution = false;

	@Config.RequiresMcRestart
	@Config.LangKey("config.mcs.enable_mod_stuff.ava")
	@Config.Comment("Enable Other Mod Stuffs")
	public boolean Avaritia = false;

	@Config.RequiresMcRestart
	@Config.LangKey("config.mcs.enable_mod_stuff.ic2")
	@Config.Comment("Enable Other Mod Stuffs")
	public boolean IndustrialCraft = false;

	@Config.RequiresMcRestart
	@Config.LangKey("config.mcs.enable_mod_stuff.ae2")
	@Config.Comment("Enable Other Mod Stuffs")
	public boolean AppliedEnergistics2 = false;
	
	@Config.RequiresMcRestart
	@Config.LangKey("config.mcs.enable_mod_stuff.eio")
	@Config.Comment("Enable Other Mod Stuffs")
	public boolean EnderIO = false;

	@Config.RequiresMcRestart
	@Config.LangKey("config.mcs.enable_mod_stuff.proe")
	@Config.Comment("Enable Other Mod Stuffs")
	public boolean ProjectE = false;

	@Config.RequiresMcRestart
	@Config.LangKey("config.mcs.enable_mod_stuff.env")
	@Config.Comment("Enable Other Mod Stuffs")
	public boolean EnvironmentalTech = false;

	@Config.RequiresMcRestart
	@Config.LangKey("config.mcs.enable_mod_stuff.tco")
	@Config.Comment("Enable Other Mod Stuffs")
	public boolean Tconstruct = false;

	@Config.RequiresMcRestart
	@Config.LangKey("config.mcs.enable_mod_stuff.bot")
	@Config.Comment("Enable Other Mod Stuffs")
	public boolean Botania = false;
}
