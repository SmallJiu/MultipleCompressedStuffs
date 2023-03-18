package cat.jiu.mcs.config;

import net.minecraftforge.common.config.Config;

public class ModStuff {
	@Config.LangKey("config.mcs.enable_mod_stuff.tf")
	@Config.Comment("Enable Other Mod Stuffs")
	@Config.RequiresMcRestart
	public boolean ThermalFoundation = false;

	@Config.LangKey("config.mcs.enable_mod_stuff.de")
	@Config.Comment("Enable Other Mod Stuffs")
	@Config.RequiresMcRestart
	public boolean DraconicEvolution = false;

	@Config.LangKey("config.mcs.enable_mod_stuff.ava")
	@Config.Comment("Enable Other Mod Stuffs")
	@Config.RequiresMcRestart
	public boolean Avaritia = false;

	@Config.LangKey("config.mcs.enable_mod_stuff.ava")
	@Config.Comment("Enable Other Mod Stuffs")
	@Config.RequiresMcRestart
	public boolean Torcherino = false;
	
	@Config.LangKey("config.mcs.enable_mod_stuff.ic2")
	@Config.Comment("Enable Other Mod Stuffs")
	@Config.RequiresMcRestart
	public boolean IndustrialCraft = false;

	@Config.LangKey("config.mcs.enable_mod_stuff.ae2")
	@Config.Comment("Enable Other Mod Stuffs")
	@Config.RequiresMcRestart
	public boolean AppliedEnergistics2 = false;
	
	@Config.LangKey("config.mcs.enable_mod_stuff.eio")
	@Config.Comment("Enable Other Mod Stuffs")
	@Config.RequiresMcRestart
	public boolean EnderIO = false;

	@Config.LangKey("config.mcs.enable_mod_stuff.proe")
	@Config.Comment("Enable Other Mod Stuffs")
	@Config.RequiresMcRestart
	public boolean ProjectE = false;

	@Config.LangKey("config.mcs.enable_mod_stuff.env")
	@Config.RequiresMcRestart
	@Config.Comment("Enable Other Mod Stuffs")
	public boolean EnvironmentalTech = false;

	@Config.LangKey("config.mcs.enable_mod_stuff.tco")
	@Config.Comment("Enable Other Mod Stuffs")
	@Config.RequiresMcRestart
	public boolean Tconstruct = false;

	@Config.LangKey("config.mcs.enable_mod_stuff.bot")
	@Config.Comment("Enable Other Mod Stuffs")
	@Config.RequiresMcRestart
	public boolean Botania = false;
}
