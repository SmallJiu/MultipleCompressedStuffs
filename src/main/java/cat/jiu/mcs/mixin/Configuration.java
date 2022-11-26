package cat.jiu.mcs.mixin;

import java.util.Map;

import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.Mixins;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

public class Configuration implements IFMLLoadingPlugin {
	public Configuration() {
		MixinBootstrap.init();
		Mixins.addConfigurations("mcs.mixin.json");
	}
	public String[] getASMTransformerClass() {return null;}
	public String getModContainerClass() {return null;}
	public String getSetupClass() {return null;}
	public void injectData(Map<String, Object> data) {}
	public String getAccessTransformerClass() {return null;}
}
