package cat.jiu.multiple_compressed_blocks.config;

import cat.jiu.multiple_compressed_blocks.MultipleCompressedBlocks;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = MultipleCompressedBlocks.MODID, name = "jiu/" + MultipleCompressedBlocks.MODID + "/main", category = "config_main")
@Config.LangKey("config.mcb.main")
@Mod.EventBusSubscriber(modid = MultipleCompressedBlocks.MODID)
public class Configs {

	@Config.LangKey("config.mcb.show_oredict")
	@Config.Comment("show oredict in Tooltip Information")
	public static boolean show_oredict = true;

	@Config.LangKey("config.mcb.use_default_recipes")
	public static final UseDefaultRecipes use_default_recipes = new UseDefaultRecipes();

	public static class UseDefaultRecipes {

		@Config.LangKey("config.mcb.recipe.bone")
		@Config.Comment("Use Mod Default Recipe")
		@Config.RequiresMcRestart
		public boolean bone_block = true;

		@Config.LangKey("config.mcb.recipe.coal_block")
		@Config.Comment("Use Mod Default Recipe")
		@Config.RequiresMcRestart
		public boolean coal_block = true;
		
		@Config.LangKey("config.mcb.recipe.coal_block")
		@Config.Comment("Use Mod Default Recipe")
		@Config.RequiresMcRestart
		public boolean cobble_stone = true;

		@Config.LangKey("config.mcb.recipe.diamon_block")
		@Config.Comment("Use Mod Default Recipe")
		@Config.RequiresMcRestart
		public boolean diamon_block = true;

		@Config.LangKey("config.mcb.recipe.dirt")
		@Config.Comment("Use Mod Default Recipe")
		@Config.RequiresMcRestart
		public boolean dirt = true;

		@Config.LangKey("config.mcb.recipe.emerald_block")
		@Config.Comment("Use Mod Default Recipe")
		@Config.RequiresMcRestart
		public boolean emerald_block = true;

		@Config.LangKey("config.mcb.recipe.glass")
		@Config.Comment("Use Mod Default Recipe")
		@Config.RequiresMcRestart
		public boolean glass = true;

		@Config.LangKey("config.mcb.recipe.glow_stone_block")
		@Config.Comment("Use Mod Default Recipe")
		@Config.RequiresMcRestart
		public boolean glow_stone_block = true;

		@Config.LangKey("config.mcb.recipe.glod_block")
		@Config.Comment("Use Mod Default Recipe")
		@Config.RequiresMcRestart
		public boolean gold_block = true;

		@Config.LangKey("config.mcb.recipe.gravel")
		@Config.Comment("Use Mod Default Recipe")
		@Config.RequiresMcRestart
		public boolean gravel = true;

		@Config.LangKey("config.mcb.recipe.ice")
		@Config.Comment("Use Mod Default Recipe")
		@Config.RequiresMcRestart
		public boolean ice = true;

		@Config.LangKey("config.mcb.recipe.iron_block")
		@Config.Comment("Use Mod Default Recipe")
		@Config.RequiresMcRestart
		public boolean iron_block = true;

		@Config.LangKey("config.mcb.recipe.melon_block")
		@Config.Comment("Use Mod Default Recipe")
		@Config.RequiresMcRestart
		public boolean melon_block = true;

		@Config.LangKey("config.mcb.recipe.netherrack")
		@Config.Comment("Use Mod Default Recipe")
		@Config.RequiresMcRestart
		public boolean netherrack = true;

		@Config.LangKey("config.mcb.recipe.pumpkin_block")
		@Config.Comment("Use Mod Default Recipe")
		@Config.RequiresMcRestart
		public boolean pumpkin_block = true;

		@Config.LangKey("config.mcb.recipe.redstone_block")
		@Config.Comment("Use Mod Default Recipe")
		@Config.RequiresMcRestart
		public boolean redstone_block = true;

		@Config.LangKey("config.mcb.recipe.sand")
		@Config.Comment("Use Mod Default Recipe")
		@Config.RequiresMcRestart
		public boolean sand = true;

		@Config.LangKey("config.mcb.recipe.snow")
		@Config.Comment("Use Mod Default Recipe")
		@Config.RequiresMcRestart
		public boolean snow = true;

		@Config.LangKey("config.mcb.recipe.stone")
		@Config.Comment("Use Mod Default Recipe")
		@Config.RequiresMcRestart
		public boolean stone = true;

		@Config.LangKey("config.mcb.recipe.wool")
		@Config.Comment("Use Mod Default Recipe")
		@Config.RequiresMcRestart
		public boolean wool = true;
	}

	@SubscribeEvent
	public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
		if (event.getModID().equals(MultipleCompressedBlocks.MODID)) {
			ConfigManager.sync(MultipleCompressedBlocks.MODID, Config.Type.INSTANCE);
		}
	}
}
