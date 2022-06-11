package cat.jiu.mcs.blocks.net.client.gui;

import java.math.BigInteger;
import java.util.List;

import com.google.common.collect.Lists;

import cat.jiu.core.capability.EnergyUtils;
import cat.jiu.core.util.JiuUtils;
import cat.jiu.core.util.base.BaseUI;
import cat.jiu.mcs.MCS;
import cat.jiu.mcs.blocks.net.NetworkHandler;
import cat.jiu.mcs.blocks.net.container.ContainerCompressor;
import cat.jiu.mcs.blocks.net.msg.MsgCompressorCount;
import cat.jiu.mcs.blocks.net.msg.MsgCompressorSlotActivate;
import cat.jiu.mcs.blocks.tileentity.TileEntityCompressor;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GUICompressor extends BaseUI.BaseGui<ContainerCompressor, TileEntityCompressor> {
	public GUICompressor(EntityPlayer player, World world, BlockPos pos) {
		super(new ContainerCompressor(player, world, pos), player, world, pos, new ResourceLocation(MCS.MODID + ":textures/gui/container/compressor.png"), 187, 176);
	}

	@Override
	public void drawGuiScreen(int mouseX, int mouseY, float partialTicks) {
		if(super.isInRange(mouseX, mouseY, 11, 6, 24, 66) ) {
			List<String> text = Lists.newArrayList();
			text.add(JiuUtils.big_integer.format(BigInteger.valueOf(this.container.getEnergy()), 3) + " / " + JiuUtils.big_integer.format(this.te.maxEnergy, 1) + " JE");
			text.add(JiuUtils.big_integer.format(this.container.getEnergy()) + " JE");
			this.drawHoveringText(text, mouseX, mouseY);
		}
	}

	@Override
	public void init() {
		GlStateManager.pushMatrix();
		GlStateManager.scale(1, 1, 1);

		this.itemRender = new CompressorRenderItem(this.mc.renderEngine, this.itemRender.getItemModelMesher().getModelManager(), this.mc.getItemColors());

		if(this.te != null) {
			this.buttonList.clear();
			this.addButton(new GuiButton(0, (this.width / 2) + 55, (this.height / 2) - 71, 10, 10, "+") {
				public void mouseReleased(int mouseX, int mouseY) {
					NetworkHandler.INSTANCE.sendToServer(new MsgCompressorCount(pos, container.getShrinkCount() + 1));
				}
			});
			this.addButton(new GuiButton(1, (this.width / 2) + 28, (this.height / 2) - 71, 10, 10, "-") {
				public void mouseReleased(int mouseX, int mouseY) {
					NetworkHandler.INSTANCE.sendToServer(new MsgCompressorCount(pos, container.getShrinkCount() - 1));
				}
			});
			for(int i = 0; i < 8; i++) {
				if(i != 0) {
					this.addButton(new GuiButton(i + 2, (this.width / 2) - (62 - (18 * i)), (this.height / 2) - 34, 17, 7, "") {
						public void mouseReleased(int mouseX, int mouseY) {
							te.setActivateSlot(this.id - 2, !container.slotCanCraft(this.id - 2));
							NetworkHandler.INSTANCE.sendToServer(new MsgCompressorSlotActivate(pos, this.id - 2, te.slotCanCraft(this.id - 2)));
						}
					});
				}

				this.addButton(new GuiButton(i + 10, (this.width / 2) - (62 - (18 * i)), (this.height / 2) - 5, 17, 7, "") {
					public void mouseReleased(int mouseX, int mouseY) {
						te.setActivateSlot(this.id - 2, !container.slotCanCraft(this.id - 2));
						NetworkHandler.INSTANCE.sendToServer(new MsgCompressorSlotActivate(pos, this.id - 2, te.slotCanCraft(this.id - 2)));
					}
				});
			}
		}
		GlStateManager.popMatrix();
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		GlStateManager.pushMatrix();
		GlStateManager.scale(1, 1, 1);
		this.drawCenteredString(this.fontRenderer, I18n.format("tile.mcs.compressor.name"), 45, 8, 0XFFFFFF);

		this.mc.getTextureManager().bindTexture(this.background);
		for(int slot = 0; slot < 8; slot++) {
			boolean slotActivate0 = this.container.slotCanCraft(slot);
			boolean slotActivate1 = this.container.slotCanCraft(slot + 8);
			if(!slotActivate0) {
				super.drawTexturedModalRect(33 + (18 * slot), 37, 189, 60, 14, 14);
			}
			if(!slotActivate1) {
				super.drawTexturedModalRect(33 + (18 * slot), 66, 189, 60, 14, 14);
			}
		}
		if(this.container.isDebug()) {
			super.drawTexturedModalRect(95, 25, 188, 90, 16, 7);
		}else {
			super.drawTexturedModalRect(95, 25, 188, 97, 16, 7);
		}
		
		this.mc.getTextureManager().bindTexture(EnergyUtils.AllTeEnergy);
		super.drawTexturedModalRect(11, 6, 0, 0, 13, 60);
		
		int i = (int) (this.container.getEnergy() / 83334);// 能量条的比例
		
		// 渲染能量条 渲染位置xy，材质位置xy，材质长宽xy
		super.drawTexturedModalRect(11, 66 - i, 13, 0, 13, 0 + i);
		this.drawCenteredString(this.fontRenderer, I18n.format("info.mcs.compressor.cache_count"), 141, 5, 16777215);
		this.drawCenteredString(this.fontRenderer, Integer.toString(this.container.getShrinkCount()), 140, 18, 16777215);
		GlStateManager.popMatrix();
	}
}
