package cat.jiu.mcs.blocks.net.client.gui;

import java.io.IOException;

import org.lwjgl.input.Mouse;

import cat.jiu.core.util.JiuUtils;
import cat.jiu.core.util.base.BaseUI;
import cat.jiu.mcs.MCS;
import cat.jiu.mcs.blocks.net.container.ContainerCompressedChest;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GUICompressedChest extends BaseUI.BaseGui<ContainerCompressedChest, TileEntity> {
	private static ResourceLocation TEXTURE = new ResourceLocation(MCS.MODID + ":textures/gui/container/compressed_chest.png");
	private float currentScroll;
	private int selectRows = 0;
	private boolean isScrolling;
	private boolean wasClicking;
	private final ItemStack chest;

	public GUICompressedChest(EntityPlayer player, World world, BlockPos pos) {
		super(new ContainerCompressedChest(player, world, pos), player, world, pos, TEXTURE, 192, 222);
		this.chest = JiuUtils.item.getStackFromBlockState(world.getBlockState(pos));
	}

	@Override
	public void init() {}
	
	@Override
	public void drawGuiScreen(int mouseX, int mouseY, float partialTicks) {
		this.scrollBar(mouseX, mouseY);
	}
	
	private void scrollBar(int mouseX, int mouseY) {
		boolean flag = Mouse.isButtonDown(0);
		int barX = this.guiLeft + 174;
		int barY = this.guiTop + 18;
		int barWidth = barX + 14;
		int barHeight = barY + 108;

		if(!this.wasClicking && flag && mouseX >= barX && mouseY >= barY && mouseX < barWidth && mouseY < barHeight) {
			this.isScrolling = this.container.canScroll();
		}

		if(!flag) {
			this.isScrolling = false;
		}
		this.wasClicking = flag;
		
		if(this.isScrolling) {
			this.currentScroll = ((float) (mouseY - barY) - 7.5F) / ((float) (barHeight - barY) - 15.0F);
			this.currentScroll = MathHelper.clamp(this.currentScroll, 0.0F, 1.0F);
			int outRows = (this.container.getTileEntity().getSlotSize() + 9 - 1) / 9 - 6;// 超出物品栏的栏数
			int selectRows = MathHelper.clamp((int) ((double) (currentScroll * (float) outRows) + 0.5D), 0, outRows);
			if(this.selectRows != selectRows) {
				this.selectRows = selectRows;
				this.container.scrollTo(this.currentScroll);
			}
		}
	}
	
	@Override
	public void handleMouseInput() throws IOException {
		super.handleMouseInput();
		if(this.container.canScroll()) {
			int outRows = (this.container.getSlots() + 9 - 1) / 9 - 6;
			int dWheel = Mouse.getEventDWheel();
            if (dWheel > 0)dWheel = 1;
            if (dWheel < 0)dWheel = -1;
            this.currentScroll = (float)((double)this.currentScroll - (double)dWheel / (double)outRows);
            this.container.scrollTo(this.currentScroll = MathHelper.clamp(this.currentScroll, 0.0F, 1.0F));
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
		int guiLeft = this.guiLeft + 174;
		int guiTop = this.guiTop + 18;
		int guiMaxDown = guiTop + 118;
		
		this.drawTexturedModalRect(
				guiLeft, // x
				guiTop + (int) ((float) (guiMaxDown - guiTop - 27) * this.currentScroll),// y
				194 + (this.container.canScroll() ? 0 : 12),// textureX
				0,// textureY
				12,// H
				15);// W
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		GlStateManager.pushMatrix();
		GlStateManager.scale(1, 1, 1);
		
		StringBuffer s = new StringBuffer();
		s.append(this.chest.getDisplayName());
		s.append(" ");
		s.append(this.container.getTileEntity().getSlotSize() - this.container.getEmptySlots());
		s.append("/");
		s.append(this.container.getTileEntity().getSlotSize());
		
		this.drawCenteredString(this.fontRenderer, s.toString(), 48, 5, 0XFFFFFF);
		
		this.mc.getTextureManager().bindTexture(GUICompressor.TEXTURE);
		for(int slotY = 0; slotY < 6; ++slotY) {
			for(int slotX = 0; slotX < 9; ++slotX) {
				if(!super.getContainer().canAddItemToSlot(slotX, slotY)) {
					this.drawTexturedModalRect(8 + 18 * slotX, 18 + 18 * slotY, 188, 59, 16, 16);
				}
			}
		}
		GlStateManager.popMatrix();
	}
}
