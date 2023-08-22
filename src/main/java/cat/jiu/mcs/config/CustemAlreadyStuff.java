package cat.jiu.mcs.config;

import net.minecraftforge.common.config.Config;

public class CustemAlreadyStuff {
	@Config.LangKey("config.mcs.custom.custem_already_stuff.block")
	@Config.Comment("Player loggedIn give a random food")
	public final CustemBlock block = new CustemBlock();
	
	@Config.LangKey("config.mcs.custom.custem_already_stuff.item")
	@Config.Comment("Player loggedIn give a random food")
	public final CustemItem item = new CustemItem();

	@Config.LangKey("config.mcs.logging_give_food")
	@Config.Comment("Player loggedIn give a random food")
	public boolean logging_give_food = false;
}
