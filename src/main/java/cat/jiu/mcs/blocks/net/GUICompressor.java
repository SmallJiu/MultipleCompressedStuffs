package cat.jiu.mcs.blocks.net;

import cat.jiu.mcs.MCS;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GUICompressor extends GuiContainer{
	
	private static ResourceLocation TEXTURE = new ResourceLocation(MCS.MODID + ":textures/gui/container/compressor.png");
	private final ContainerCompressor con;
	
	public GUICompressor(EntityPlayer player, World world, BlockPos pos) {
		super(new ContainerCompressor(player, world, pos));
		this.con = (ContainerCompressor) this.inventorySlots;
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
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		GlStateManager.pushMatrix();
		String text = I18n.format("tile.mcs.compressor.name");
		this.drawCenteredString(this.fontRenderer, text, 168, 5, 0XFFFFFF);
		
		this.mc.getTextureManager().bindTexture(TEXTURE);
//		GlStateManager.scale(1,1,2);
		//渲染
		
		super.drawTexturedModalRect(11, 8, 188, 0, 13, 36);
		
		int energy = 0;
//		NonNullList<BigItemStack> slots = null;
		if(this.con.getTileEntity() != null) {
			energy = this.con.getTileEntity().energy;
//			slots = this.con.getTileEntity().compressedSlots;
			
			int i = energy / 142857;
			super.drawTexturedModalRect(12, 44-i, 202, 0, 12, 0+i);
			this.drawCenteredString(this.fontRenderer, energy+"/"+this.con.getTileEntity().maxEnergy + " FE", 59, 15, 16777215);
			
//			this.drawCenteredString(this.fontRenderer, (slots.get(0).getCount())+"", 120, 16, 16777215);
//			
//			this.drawCenteredString(this.fontRenderer, slots.get(2).getCount().toString(), 45, 40, 16777215);
			
		}
		GlStateManager.popMatrix();
	}
}
