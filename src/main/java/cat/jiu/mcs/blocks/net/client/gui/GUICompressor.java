//Deobfuscated with https://github.com/PetoPetko/Minecraft-Deobfuscator3000 using mappings "1.12 stable mappings"!

package cat.jiu.mcs.blocks.net.client.gui;

import cat.jiu.core.energy.EnergyUtils;
import cat.jiu.core.util.JiuUtils;
import cat.jiu.mcs.MCS;
import cat.jiu.mcs.blocks.net.container.ContainerCompressor;
import cat.jiu.mcs.blocks.tileentity.TileEntityCompressor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SuppressWarnings("unused")
@SideOnly(Side.CLIENT)
public class GUICompressor extends GuiContainer{
	private static ResourceLocation TEXTURE = new ResourceLocation(MCS.MODID + ":textures/gui/container/compressor.png");
	private static ResourceLocation ENERGY_TEXTURE = EnergyUtils.AllTeEnergy;
	private final ContainerCompressor container;
	private final World world;
	private final BlockPos pos;
	private TileEntityCompressor te = null;
	
	public GUICompressor(EntityPlayer player, World world, BlockPos pos) {
		super(new ContainerCompressor(player, world, pos));
		this.container = (ContainerCompressor) this.inventorySlots;
		if(world.getTileEntity(pos) instanceof TileEntityCompressor) {
			this.te = (TileEntityCompressor) world.getTileEntity(pos);
		}
		this.world = world;
		this.pos = pos;
		this.xSize = 187;
		this.ySize = 152;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		super.renderHoveredToolTip(mouseX, mouseY);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;
		
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(TEXTURE);
		this.drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);
//		this.drawString(fontRenderer, this.con.getEnergy() + "/" + JiuUtils.other.formatNumber(this.te.storage.getMaxEnergyStoredWithLong())+ "FE", x + 32, y + 15, 0xFFFFFF);
	}
	
	@Override
	public void initGui() {
		super.initGui();
		
		if(this.te != null) {
			this.buttonList.clear();
			this.addButton(new GuiButton(0, (this.width/2)+52, (this.height/2)-60, 10, 10, "+") {
				@Override
				public void mouseReleased(int mouseX, int mouseY) {
					te.setShrinkCount(te.getShrinkCount() + 1);
				}
			});
			this.addButton(new GuiButton(1, (this.width/2)+32, (this.height/2)-60, 10, 10, "-") {
				@Override
				public void mouseReleased(int mouseX, int mouseY) {
					te.setShrinkCount(te.getShrinkCount() - 1);
				}
			});
		}
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		GlStateManager.pushMatrix();
		GlStateManager.scale(1, 1, 1);
		this.drawCenteredString(this.fontRenderer, I18n.format("tile.mcs.compressor.name"), 40, 5, 0XFFFFFF);
		
		this.mc.getTextureManager().bindTexture(ENERGY_TEXTURE);
		super.drawTexturedModalRect(11, 8, 27, 0, 13, 36);
		
		if(this.te != null) {
			int i = this.container.getEnergy() / 142857;// 能量条的比例
			
			// 渲染能量�? 渲染位置xy，材质位置xy，材质长宽xy
			super.drawTexturedModalRect(11, 44-i, 40, 0, 13, 0+i);
			
			this.drawCenteredString(this.fontRenderer, this.container.getTileEntity().getShrinkCount()+"", 140, 18, 16777215);
			this.drawCenteredString(this.fontRenderer, "ActivateCount", 142, 5, 0XFFFFFF);
			
//			this.mc.getTextureManager().bindTexture(EnergyUtils.AllEioEnergy);
//			super.drawTexturedModalRect(-12, 8, 0, 22, 11, 36);
//			
//			super.drawTexturedModalRect(-11, 44-i, 34, 0, 9, 0+i);// 渲染能量�?
//			
//			this.mc.getTextureManager().bindTexture(EnergyUtils.AllDeEnergy);
//			super.drawTexturedModalRect(-30, 8, 0, 22, 14, 36);
//			
//			super.drawTexturedModalRect(-30, 44-i, 42, 0, 13, 0+i);// 渲染能量�?
//			MCS.instance.log.info("Energy: " + this.con.getEnergy());
//			this.drawCenteredString(this.fontRenderer, JiuUtils.other.formatNumber(this.container.getEnergy()) + "/" + JiuUtils.other.formatNumber(this.te.maxEnergy)+ " JE", 59, 15, 16777215);
			this.drawCenteredString(this.fontRenderer, this.formatNumber(this.container.getEnergy()) + "/" + this.formatNumber(this.te.maxEnergy)+ " JE", 59, 15, 16777215);
		}
		
		GlStateManager.popMatrix();
	}
	
	public String formatNumber(long value) {
		if(value >= 1000000000000000000L) {
			return (Math.round((float)value / 100000000.0)) / 10000000000.0 + "E";
		}
		if(value >= 1000000000000000L) {
			return (Math.round((float)value / 100000000.0)) / 10000000.0 + "P";
		}
		if(value >= 1000000000000L) {
			return (Math.round((float)value / 1000000.0) / 1000000.0) + "T";
		}
		if(value >= 1000000000L) {
			return (Math.round((float)value / 100000.0)) / 10000.0 + "G";
		}
		if(value >= 1000000L) {
			return (Math.round((float)value / 1000.0) / 1000.0) + "M";
		}
		if(value > 1000L) {
			return ((Math.round(value)) / 1000.0) + "K";
		}
		if(value < 1000L) {
			return Long.toString(value);
		}
		return "to big!";
	}
}
