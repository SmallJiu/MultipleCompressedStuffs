package cat.jiu.mcs.util.base;

import cat.jiu.core.util.RegisterModel;
import cat.jiu.mcs.MCS;
import cat.jiu.mcs.interfaces.IHasModel;
import cat.jiu.mcs.util.init.MCSBlocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class BaseBlockNormal extends BaseBlock implements IHasModel{
	protected final String langModID;
	protected final RegisterModel model = new RegisterModel(MCS.MODID);
	
	public BaseBlockNormal(String nameIn, String langModID,  Material materialIn, SoundType soundIn, CreativeTabs tabIn, float hardnessIn) {
		super(nameIn, materialIn, soundIn, tabIn, hardnessIn, false);
		this.langModID = langModID;
		MCSBlocks.NORMAL_BLOCKS.add(this);
		MCSBlocks.NORMAL_BLOCKS_MAP.put(nameIn, this);
	}
	
	public BaseBlockNormal(String nameIn, String langModID, Material materialIn, SoundType soundIn, CreativeTabs tabIn) {
		this(nameIn, langModID,  materialIn, soundIn, tabIn, 10);
	}
	
	public BaseBlockNormal(String nameIn, String langModID, Material materialIn,  CreativeTabs tabIn) {
		this(nameIn,  langModID, materialIn, SoundType.STONE, tabIn);
	}
	
	public BaseBlockNormal(String nameIn, String langModID, SoundType soundIn, CreativeTabs tabIn) {
		this(nameIn, langModID, Material.IRON, soundIn, tabIn);
	}
	
	public BaseBlockNormal(String nameIn, String langModID, CreativeTabs tabIn, float hardnessIn) {
		this(nameIn, langModID, Material.IRON, SoundType.STONE, tabIn, hardnessIn);
	}
	
	public BaseBlockNormal(String nameIn, String langModID, float hardnessIn) {
		this(nameIn, langModID, MCS.COMPERESSED_BLOCKS, hardnessIn);
	}
	
	public BaseBlockNormal(String nameIn, String langModID, CreativeTabs tabIn) {
		this(nameIn, langModID, Material.IRON, tabIn);
	}
	
	public BaseBlockNormal(String nameIn, String langModID) {
		this(nameIn, langModID, MCS.COMPERESSED_BLOCKS);
	}
	
	public BaseBlockNormal(String nameIn) {
		this(nameIn, "minecraft");
	}
	
	private String[] model_res = null;
	
	public BaseBlockNormal setBlockModelResourceLocation(String name, String resname) {
		this.model_res = new String[] { name, resname };
		return this;
	}

	@Override
	public void registerItemModel() {
		if(this.model_res != null){
			String name = this.model_res[0];
			String resname = this.model_res[1];
			
			model.registerItemModel(this, name, resname);
		}else{
			model.registerItemModel(this, "normal/blocks", this.name);
		}
	}
	
	public String[] getBlockModelResourceLocation() {
		return this.model_res;	
	}
}
