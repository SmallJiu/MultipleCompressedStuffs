package cat.jiu.mcs.util.client.model.texture;

import com.google.gson.JsonObject;

public class ModTextures {
	private ItemTextures items;
	private BlockTextures blocks;
	
	public ModTextures() {}
	public ModTextures(ItemTextures items, BlockTextures blocks) {
		this.items = items;
		this.blocks = blocks;
	}

	public ModTextures(JsonObject modTextures) {
		if(modTextures.has("item")) {
			this.items = new ItemTextures(modTextures.getAsJsonObject("item"));
		}
		if(modTextures.has("block")) {
			this.blocks = new BlockTextures(modTextures.getAsJsonObject("block"));
		}
	}
	
	public void addBlocks(BlockTextures blocks) {
		if(this.blocks==null) this.blocks = new BlockTextures();
		this.blocks.addAll(blocks);
	}
	public void addItems(ItemTextures items) {
		if(this.items==null) this.items = new ItemTextures();
		this.items.addAll(items);
	}
	
	public BlockTextures getBlocks() {
		if(this.blocks==null) this.blocks = new BlockTextures();
		return blocks;
	}
	public ItemTextures getItems() {
		if(this.items==null) this.items = new ItemTextures();
		return items;
	}
	public JsonObject toJson() {
		JsonObject o = new JsonObject();
		
		if(this.items!=null) {
			o.add("item", this.items.toJson());
		}
		if(this.blocks!=null) {
			o.add("block", this.blocks.toJson());
		}
		
		return o;
	}
	@Override
	public String toString() {
		return this.toJson().toString();
	}
}
