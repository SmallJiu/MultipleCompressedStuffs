package cat.jiu.mcs.util.client;

import java.util.Collections;
import java.util.List;

import com.google.common.collect.Lists;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import cat.jiu.core.util.JiuUtils;
import cat.jiu.mcs.MCS;
import cat.jiu.mcs.api.ICompressedStuff;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;

import net.minecraftforge.client.model.pipeline.UnpackedBakedQuad;

public class CompressedBakedModel implements IBakedModel {
	private VertexFormat format;

	public CompressedBakedModel(VertexFormat format) {
		this.format = format;
	}
	String normalT = null;
	
	String topT = null;
	String sideT = null;
	String downT = null;
	String southT = null;
	String northT = null;
	String eastT = null;
	String westT = null;
	
	String allT = null;
	String[] overlaysT = null;

	@Override
	public List<BakedQuad> getQuads(IBlockState state, EnumFacing side, long rand) {
		if(state == null) {
			return Collections.emptyList();
		}
		
		this.initTexture(state.getBlock());

		List<BakedQuad> quads = Lists.newArrayList();
		double o = 0.4;
		
		if(this.normalT != null) {
			this.up(side == EnumFacing.UP, quads, o, this.getTexture(new ResourceLocation(normalT)));
			this.down(side == EnumFacing.DOWN, quads, o, this.getTexture(new ResourceLocation(normalT)));
			this.east(side == EnumFacing.EAST, quads, o, this.getTexture(new ResourceLocation(normalT)));
			this.west(side == EnumFacing.WEST, quads, o, this.getTexture(new ResourceLocation(normalT)));
			this.north(side == EnumFacing.NORTH, quads, o, this.getTexture(new ResourceLocation(normalT)));
			this.south(side == EnumFacing.SOUTH, quads, o, this.getTexture(new ResourceLocation(normalT)));
			
		}else if(this.topT != null) {
			this.up(side == EnumFacing.UP, quads, o, this.getTexture(new ResourceLocation(topT)));
			this.down(side == EnumFacing.DOWN, quads, o, this.getTexture(new ResourceLocation(downT)));
			if(this.sideT != null) {
				this.east(side == EnumFacing.EAST, quads, o, this.getTexture(new ResourceLocation(sideT)));
				this.west(side == EnumFacing.WEST, quads, o, this.getTexture(new ResourceLocation(sideT)));
				this.north(side == EnumFacing.NORTH, quads, o, this.getTexture(new ResourceLocation(sideT)));
				this.south(side == EnumFacing.SOUTH, quads, o, this.getTexture(new ResourceLocation(sideT)));
			}else {
				this.east(side == EnumFacing.EAST, quads, o, this.getTexture(new ResourceLocation(eastT)));
				this.west(side == EnumFacing.WEST, quads, o, this.getTexture(new ResourceLocation(westT)));
				this.north(side == EnumFacing.NORTH, quads, o, this.getTexture(new ResourceLocation(northT)));
				this.south(side == EnumFacing.SOUTH, quads, o, this.getTexture(new ResourceLocation(southT)));
			}
		}else if(allT != null) {
			this.up(side == EnumFacing.UP, quads, o, this.getTexture(new ResourceLocation(allT)));
			for(String overlay : this.overlaysT) {
				this.up(side == EnumFacing.UP, quads, o, this.getTexture(new ResourceLocation(overlay)));
			}
			this.down(side == EnumFacing.DOWN, quads, o, this.getTexture(new ResourceLocation(allT)));
			for(String overlay : this.overlaysT) {
				this.down(side == EnumFacing.DOWN, quads, o, this.getTexture(new ResourceLocation(overlay)));
			}
			this.east(side == EnumFacing.EAST, quads, o, this.getTexture(new ResourceLocation(allT)));
			for(String overlay : this.overlaysT) {
				this.east(side == EnumFacing.EAST, quads, o, this.getTexture(new ResourceLocation(overlay)));
			}
			this.west(side == EnumFacing.WEST, quads, o, this.getTexture(new ResourceLocation(allT)));
			for(String overlay : this.overlaysT) {
				this.west(side == EnumFacing.WEST, quads, o, this.getTexture(new ResourceLocation(overlay)));
			}
			this.north(side == EnumFacing.NORTH, quads, o, this.getTexture(new ResourceLocation(allT)));
			for(String overlay : this.overlaysT) {
				this.north(side == EnumFacing.NORTH, quads, o, this.getTexture(new ResourceLocation(overlay)));
			}
			this.south(side == EnumFacing.SOUTH, quads, o, this.getTexture(new ResourceLocation(allT)));
			for(String overlay : this.overlaysT) {
				this.south(side == EnumFacing.SOUTH, quads, o, this.getTexture(new ResourceLocation(overlay)));
			}
		}
		
		ResourceLocation compressed_texture = new ResourceLocation(MCS.MODID, "textures/blocks/compressed_" + (JiuUtils.item.getMetaFromBlockState(state)+1));
		
		this.up(side == EnumFacing.UP, quads, o, this.getTexture(compressed_texture));
		this.down(side == EnumFacing.DOWN, quads, o, this.getTexture(compressed_texture));
		this.east(side == EnumFacing.EAST, quads, o, this.getTexture(compressed_texture));
		this.west(side == EnumFacing.WEST, quads, o, this.getTexture(compressed_texture));
		this.north(side == EnumFacing.NORTH, quads, o, this.getTexture(compressed_texture));
		this.south(side == EnumFacing.SOUTH, quads, o, this.getTexture(compressed_texture));
		
		return quads;
	}
	
	private void up(boolean b, List<BakedQuad> quads, double o, TextureAtlasSprite sprite) {
		if(b) {
			quads.add(createQuad(new Vec3d(1 - o, 1 - o, o), new Vec3d(1 - o, 1, o), new Vec3d(1 - o, 1, 1 - o), new Vec3d(1 - o, 1 - o, 1 - o), sprite));
			quads.add(createQuad(new Vec3d(o, 1 - o, 1 - o), new Vec3d(o, 1, 1 - o), new Vec3d(o, 1, o), new Vec3d(o, 1 - o, o), sprite));
			quads.add(createQuad(new Vec3d(o, 1, o), new Vec3d(1 - o, 1, o), new Vec3d(1 - o, 1 - o, o), new Vec3d(o, 1 - o, o), sprite));
			quads.add(createQuad(new Vec3d(o, 1 - o, 1 - o), new Vec3d(1 - o, 1 - o, 1 - o), new Vec3d(1 - o, 1, 1 - o), new Vec3d(o, 1, 1 - o), sprite));
		}else {
			quads.add(createQuad(new Vec3d(o, 1 - o, 1 - o), new Vec3d(1 - o, 1 - o, 1 - o), new Vec3d(1 - o, 1 - o, o), new Vec3d(o, 1 - o, o), sprite));
		}
	}
	
	private void down(boolean b, List<BakedQuad> quads, double o, TextureAtlasSprite sprite) {
		if(b) {
			quads.add(createQuad(new Vec3d(1 - o, 0, o), new Vec3d(1 - o, o, o), new Vec3d(1 - o, o, 1 - o), new Vec3d(1 - o, 0, 1 - o), sprite));
			quads.add(createQuad(new Vec3d(o, 0, 1 - o), new Vec3d(o, o, 1 - o), new Vec3d(o, o, o), new Vec3d(o, 0, o), sprite));
			quads.add(createQuad(new Vec3d(o, o, o), new Vec3d(1 - o, o, o), new Vec3d(1 - o, 0, o), new Vec3d(o, 0, o), sprite));
			quads.add(createQuad(new Vec3d(o, 0, 1 - o), new Vec3d(1 - o, 0, 1 - o), new Vec3d(1 - o, o, 1 - o), new Vec3d(o, o, 1 - o), sprite));
		}else {
			quads.add(createQuad(new Vec3d(o, o, o), new Vec3d(1 - o, o, o), new Vec3d(1 - o, o, 1 - o), new Vec3d(o, o, 1 - o), sprite));
		}
	}
	
	private void east(boolean b, List<BakedQuad> quads, double o, TextureAtlasSprite sprite) {
		if(b) {
			quads.add(createQuad(new Vec3d(1 - o, 1 - o, 1 - o), new Vec3d(1, 1 - o, 1 - o), new Vec3d(1, 1 - o, o), new Vec3d(1 - o, 1 - o, o), sprite));
			quads.add(createQuad(new Vec3d(1 - o, o, o), new Vec3d(1, o, o), new Vec3d(1, o, 1 - o), new Vec3d(1 - o, o, 1 - o), sprite));
			quads.add(createQuad(new Vec3d(1 - o, 1 - o, o), new Vec3d(1, 1 - o, o), new Vec3d(1, o, o), new Vec3d(1 - o, o, o), sprite));
			quads.add(createQuad(new Vec3d(1 - o, o, 1 - o), new Vec3d(1, o, 1 - o), new Vec3d(1, 1 - o, 1 - o), new Vec3d(1 - o, 1 - o, 1 - o), sprite));
		}else {
			quads.add(createQuad(new Vec3d(1 - o, o, o), new Vec3d(1 - o, 1 - o, o), new Vec3d(1 - o, 1 - o, 1 - o), new Vec3d(1 - o, o, 1 - o), sprite));
		}
	}
	
	private void west(boolean b, List<BakedQuad> quads, double o, TextureAtlasSprite sprite) {
		if(b) {
			quads.add(createQuad(new Vec3d(0, 1 - o, 1 - o), new Vec3d(o, 1 - o, 1 - o), new Vec3d(o, 1 - o, o), new Vec3d(0, 1 - o, o), sprite));
			quads.add(createQuad(new Vec3d(0, o, o), new Vec3d(o, o, o), new Vec3d(o, o, 1 - o), new Vec3d(0, o, 1 - o), sprite));
			quads.add(createQuad(new Vec3d(0, 1 - o, o), new Vec3d(o, 1 - o, o), new Vec3d(o, o, o), new Vec3d(0, o, o), sprite));
			quads.add(createQuad(new Vec3d(0, o, 1 - o), new Vec3d(o, o, 1 - o), new Vec3d(o, 1 - o, 1 - o), new Vec3d(0, 1 - o, 1 - o), sprite));
		}else {
			quads.add(createQuad(new Vec3d(o, o, 1 - o), new Vec3d(o, 1 - o, 1 - o), new Vec3d(o, 1 - o, o), new Vec3d(o, o, o), sprite));
		}
	}
	
	private void north(boolean b, List<BakedQuad> quads, double o, TextureAtlasSprite sprite) {
		if(b) {
			quads.add(createQuad(new Vec3d(o, 1 - o, o), new Vec3d(1 - o, 1 - o, o), new Vec3d(1 - o, 1 - o, 0), new Vec3d(o, 1 - o, 0), sprite));
			quads.add(createQuad(new Vec3d(o, o, 0), new Vec3d(1 - o, o, 0), new Vec3d(1 - o, o, o), new Vec3d(o, o, o), sprite));
			quads.add(createQuad(new Vec3d(1 - o, o, 0), new Vec3d(1 - o, 1 - o, 0), new Vec3d(1 - o, 1 - o, o), new Vec3d(1 - o, o, o), sprite));
			quads.add(createQuad(new Vec3d(o, o, o), new Vec3d(o, 1 - o, o), new Vec3d(o, 1 - o, 0), new Vec3d(o, o, 0), sprite));
		}else {
			quads.add(createQuad(new Vec3d(o, 1 - o, o), new Vec3d(1 - o, 1 - o, o), new Vec3d(1 - o, o, o), new Vec3d(o, o, o), sprite));
		}
	}
	
	private void south(boolean b, List<BakedQuad> quads, double o, TextureAtlasSprite sprite) {
		if(b) {
			quads.add(createQuad(new Vec3d(o, 1 - o, 1), new Vec3d(1 - o, 1 - o, 1), new Vec3d(1 - o, 1 - o, 1 - o), new Vec3d(o, 1 - o, 1 - o), sprite));
			quads.add(createQuad(new Vec3d(o, o, 1 - o), new Vec3d(1 - o, o, 1 - o), new Vec3d(1 - o, o, 1), new Vec3d(o, o, 1), sprite));
			quads.add(createQuad(new Vec3d(1 - o, o, 1 - o), new Vec3d(1 - o, 1 - o, 1 - o), new Vec3d(1 - o, 1 - o, 1), new Vec3d(1 - o, o, 1), sprite));
			quads.add(createQuad(new Vec3d(o, o, 1), new Vec3d(o, 1 - o, 1), new Vec3d(o, 1 - o, 1 - o), new Vec3d(o, o, 1 - o), sprite));
		}else {
			quads.add(createQuad(new Vec3d(o, o, 1 - o), new Vec3d(1 - o, o, 1 - o), new Vec3d(1 - o, 1 - o, 1 - o), new Vec3d(o, 1 - o, 1 - o), sprite));
		}
	}
	
	private void initTexture(Block block) {
		if(block instanceof ICompressedStuff) {
			ICompressedStuff com = (ICompressedStuff) block;
			String blockModid = com.getOwnerMod();
			String blockName = block.getRegistryName().getResourcePath();
			
			if(MCS.getTextures().has(blockModid)) {
				JsonObject modTextures = MCS.getTextures().getAsJsonObject(blockModid).getAsJsonObject("block").getAsJsonObject(com.isHas() ? "has" : "normal");
				if(modTextures.has(blockName)) {
					JsonElement blockT = modTextures.get(blockName);
					if(blockT.isJsonPrimitive()) {
						normalT = blockT.getAsString();
					}else if(blockT.isJsonObject()) {
						JsonObject blockS = blockT.getAsJsonObject();
						if(blockS.has("all")) {
							allT = blockS.get("all").getAsString();
							String[] overT = new String[blockS.size()-1];
							for(int i = 1; i < 5; i++) {
								if(i >= overT.length) break;
								String over = "overlay_" + i;
								if(blockS.has(over)) {
									overT[i-1] = blockS.get(over).getAsString();
								}
							}
							overlaysT = overT;
						}else if(blockS.has("top")) {
							topT = blockS.get("top").getAsString();
							downT = blockS.get("down").getAsString();
							if(blockS.has("side")) {
								sideT = blockS.get("side").getAsString();
							}else {
								westT = blockS.get("west").getAsString();
								northT = blockS.get("north").getAsString();
								southT = blockS.get("south").getAsString();
								eastT = blockS.get("east").getAsString();
							}
						}
					}
				}
			}
		}
	}
	
	private BakedQuad createQuad(Vec3d v1, Vec3d v2, Vec3d v3, Vec3d v4, TextureAtlasSprite sprite) {
		Vec3d normal = v3.subtract(v2).crossProduct(v1.subtract(v2)).normalize();

		UnpackedBakedQuad.Builder builder = new UnpackedBakedQuad.Builder(format);
		builder.setTexture(sprite);
		putVertex(builder, normal, v1.x, v1.y, v1.z, 0, 0, sprite);
		putVertex(builder, normal, v2.x, v2.y, v2.z, 0, 16, sprite);
		putVertex(builder, normal, v3.x, v3.y, v3.z, 16, 16, sprite);
		putVertex(builder, normal, v4.x, v4.y, v4.z, 16, 0, sprite);
		return builder.build();
	}
	
	private void putVertex(UnpackedBakedQuad.Builder builder, Vec3d normal, double x, double y, double z, float u, float v, TextureAtlasSprite sprite) {
		for(int e = 0; e < format.getElementCount(); e++) {
			switch(format.getElement(e).getUsage()) {
				case POSITION:
					builder.put(e, (float) x, (float) y, (float) z, 1.0f);
					break;
				case COLOR:
					builder.put(e, 1.0f, 1.0f, 1.0f, 1.0f);
					break;
				case UV:
					if(format.getElement(e).getIndex() == 0) {
						u = sprite.getInterpolatedU(u);
						v = sprite.getInterpolatedV(v);
						builder.put(e, u, v, 0f, 1f);
						break;
					}
				case NORMAL:
					builder.put(e, (float) normal.x, (float) normal.y, (float) normal.z, 0f);
					break;
				default:
					builder.put(e);
					break;
			}
		}
	}

	@Override
	public boolean isAmbientOcclusion() {
		return true;
	}

	@Override
	public boolean isGui3d() {
		return true;
	}

	@Override
	public boolean isBuiltInRenderer() {
		return false;
	}
	
	private TextureAtlasSprite getTexture(ResourceLocation loc) {
		return Minecraft.getMinecraft().getTextureMapBlocks().registerSprite(loc);
	}

	@Override
	public TextureAtlasSprite getParticleTexture() {
		if(this.normalT != null) {
			return this.getTexture(new ResourceLocation(this.normalT));
		}else if(this.sideT != null) {
			return this.getTexture(new ResourceLocation(this.sideT));
		}else if(this.allT != null) {
			return this.getTexture(new ResourceLocation(this.allT));
		}
		return null;
	}

	@Override
	public ItemOverrideList getOverrides() {
		return ItemOverrideList.NONE;
	}
	/*
	if(up) {
		quads.add(createQuad(new Vec3d(1 - o, 1 - o, o), new Vec3d(1 - o, 1, o), new Vec3d(1 - o, 1, 1 - o), new Vec3d(1 - o, 1 - o, 1 - o), sprite));
		quads.add(createQuad(new Vec3d(o, 1 - o, 1 - o), new Vec3d(o, 1, 1 - o), new Vec3d(o, 1, o), new Vec3d(o, 1 - o, o), sprite));
		quads.add(createQuad(new Vec3d(o, 1, o), new Vec3d(1 - o, 1, o), new Vec3d(1 - o, 1 - o, o), new Vec3d(o, 1 - o, o), sprite));
		quads.add(createQuad(new Vec3d(o, 1 - o, 1 - o), new Vec3d(1 - o, 1 - o, 1 - o), new Vec3d(1 - o, 1, 1 - o), new Vec3d(o, 1, 1 - o), sprite));
	}else {
		quads.add(createQuad(new Vec3d(o, 1 - o, 1 - o), new Vec3d(1 - o, 1 - o, 1 - o), new Vec3d(1 - o, 1 - o, o), new Vec3d(o, 1 - o, o), sprite));
	}

	if(down) {
		quads.add(createQuad(new Vec3d(1 - o, 0, o), new Vec3d(1 - o, o, o), new Vec3d(1 - o, o, 1 - o), new Vec3d(1 - o, 0, 1 - o), sprite));
		quads.add(createQuad(new Vec3d(o, 0, 1 - o), new Vec3d(o, o, 1 - o), new Vec3d(o, o, o), new Vec3d(o, 0, o), sprite));
		quads.add(createQuad(new Vec3d(o, o, o), new Vec3d(1 - o, o, o), new Vec3d(1 - o, 0, o), new Vec3d(o, 0, o), sprite));
		quads.add(createQuad(new Vec3d(o, 0, 1 - o), new Vec3d(1 - o, 0, 1 - o), new Vec3d(1 - o, o, 1 - o), new Vec3d(o, o, 1 - o), sprite));
	}else {
		quads.add(createQuad(new Vec3d(o, o, o), new Vec3d(1 - o, o, o), new Vec3d(1 - o, o, 1 - o), new Vec3d(o, o, 1 - o), sprite));
	}

	if(east) {
		quads.add(createQuad(new Vec3d(1 - o, 1 - o, 1 - o), new Vec3d(1, 1 - o, 1 - o), new Vec3d(1, 1 - o, o), new Vec3d(1 - o, 1 - o, o), sprite));
		quads.add(createQuad(new Vec3d(1 - o, o, o), new Vec3d(1, o, o), new Vec3d(1, o, 1 - o), new Vec3d(1 - o, o, 1 - o), sprite));
		quads.add(createQuad(new Vec3d(1 - o, 1 - o, o), new Vec3d(1, 1 - o, o), new Vec3d(1, o, o), new Vec3d(1 - o, o, o), sprite));
		quads.add(createQuad(new Vec3d(1 - o, o, 1 - o), new Vec3d(1, o, 1 - o), new Vec3d(1, 1 - o, 1 - o), new Vec3d(1 - o, 1 - o, 1 - o), sprite));
	}else {
		quads.add(createQuad(new Vec3d(1 - o, o, o), new Vec3d(1 - o, 1 - o, o), new Vec3d(1 - o, 1 - o, 1 - o), new Vec3d(1 - o, o, 1 - o), sprite));
	}

	if(west) {
		quads.add(createQuad(new Vec3d(0, 1 - o, 1 - o), new Vec3d(o, 1 - o, 1 - o), new Vec3d(o, 1 - o, o), new Vec3d(0, 1 - o, o), sprite));
		quads.add(createQuad(new Vec3d(0, o, o), new Vec3d(o, o, o), new Vec3d(o, o, 1 - o), new Vec3d(0, o, 1 - o), sprite));
		quads.add(createQuad(new Vec3d(0, 1 - o, o), new Vec3d(o, 1 - o, o), new Vec3d(o, o, o), new Vec3d(0, o, o), sprite));
		quads.add(createQuad(new Vec3d(0, o, 1 - o), new Vec3d(o, o, 1 - o), new Vec3d(o, 1 - o, 1 - o), new Vec3d(0, 1 - o, 1 - o), sprite));
	}else {
		quads.add(createQuad(new Vec3d(o, o, 1 - o), new Vec3d(o, 1 - o, 1 - o), new Vec3d(o, 1 - o, o), new Vec3d(o, o, o), sprite));
	}

	if(north) {
		quads.add(createQuad(new Vec3d(o, 1 - o, o), new Vec3d(1 - o, 1 - o, o), new Vec3d(1 - o, 1 - o, 0), new Vec3d(o, 1 - o, 0), sprite));
		quads.add(createQuad(new Vec3d(o, o, 0), new Vec3d(1 - o, o, 0), new Vec3d(1 - o, o, o), new Vec3d(o, o, o), sprite));
		quads.add(createQuad(new Vec3d(1 - o, o, 0), new Vec3d(1 - o, 1 - o, 0), new Vec3d(1 - o, 1 - o, o), new Vec3d(1 - o, o, o), sprite));
		quads.add(createQuad(new Vec3d(o, o, o), new Vec3d(o, 1 - o, o), new Vec3d(o, 1 - o, 0), new Vec3d(o, o, 0), sprite));
	}else {
		quads.add(createQuad(new Vec3d(o, 1 - o, o), new Vec3d(1 - o, 1 - o, o), new Vec3d(1 - o, o, o), new Vec3d(o, o, o), sprite));
	}
	if(south) {
		quads.add(createQuad(new Vec3d(o, 1 - o, 1), new Vec3d(1 - o, 1 - o, 1), new Vec3d(1 - o, 1 - o, 1 - o), new Vec3d(o, 1 - o, 1 - o), sprite));
		quads.add(createQuad(new Vec3d(o, o, 1 - o), new Vec3d(1 - o, o, 1 - o), new Vec3d(1 - o, o, 1), new Vec3d(o, o, 1), sprite));
		quads.add(createQuad(new Vec3d(1 - o, o, 1 - o), new Vec3d(1 - o, 1 - o, 1 - o), new Vec3d(1 - o, 1 - o, 1), new Vec3d(1 - o, o, 1), sprite));
		quads.add(createQuad(new Vec3d(o, o, 1), new Vec3d(o, 1 - o, 1), new Vec3d(o, 1 - o, 1 - o), new Vec3d(o, o, 1 - o), sprite));
	}else {
		quads.add(createQuad(new Vec3d(o, o, 1 - o), new Vec3d(1 - o, o, 1 - o), new Vec3d(1 - o, 1 - o, 1 - o), new Vec3d(o, 1 - o, 1 - o), sprite));
	}
	*/
}
