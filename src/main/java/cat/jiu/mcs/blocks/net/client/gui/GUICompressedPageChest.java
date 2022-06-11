package cat.jiu.mcs.blocks.net.client.gui;

import cat.jiu.core.util.JiuUtils;
import cat.jiu.core.util.base.BaseUI;
import cat.jiu.mcs.blocks.net.container.ContainerCompressedPageChest;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GUICompressedPageChest extends BaseUI.BaseGui<ContainerCompressedPageChest, TileEntity> {
	private static ResourceLocation TEXTURE = new ResourceLocation("textures/gui/container/generic_54.png");
	private final ItemStack chest;
	private int page = 0;

	public GUICompressedPageChest(EntityPlayer player, World world, BlockPos pos) {
		super(new ContainerCompressedPageChest(player, world, pos), player, world, pos, TEXTURE, 192, 222);
		this.chest = JiuUtils.item.getStackFromBlockState(world.getBlockState(pos));
	}

	@Override
	public void init() {
		this.buttonList.clear();
		this.buttonList.add(new GuiButton(1000, this.width/2 + 50, this.height/2 + -108, 13, 13, "->") {
			@Override
			public void mouseReleased(int mouseX, int mouseY) {
				int apage = page + 1;
				if(apage > container.getMaxPage()) {
					apage = container.getMaxPage()-1;
				}
				page = apage;
				container.toPage(page);
			}
		});
		this.buttonList.add(new GuiButton(1001, this.width/2 + 20, this.height/2 + -108, 13, 13, "<-") {
			@Override
			public void mouseReleased(int mouseX, int mouseY) {
				int apage = page - 1;
				if(apage < 0) {
					apage = 1;
				}
				page = apage;
				container.toPage(page);
			}
		});
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
		
		this.drawCenteredString(this.fontRenderer, s.toString(), 55, 5, 0XFFFFFF);
		this.drawCenteredString(this.fontRenderer, (this.page+1) + "/" + (this.container.getMaxPage()+1), 137, 5, 0XFFFFFF);

		GlStateManager.popMatrix();
	}
}
