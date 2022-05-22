package cat.jiu.mcs.blocks.net.client.gui;

import java.awt.Color;
import java.math.BigInteger;

import cat.jiu.core.capability.EnergyUtils;
import cat.jiu.core.util.JiuUtils;
import cat.jiu.mcs.MCS;
import cat.jiu.mcs.blocks.net.NetworkHandler;
import cat.jiu.mcs.blocks.net.container.ContainerCompressor;
import cat.jiu.mcs.blocks.net.msg.MsgCompressorCount;
import cat.jiu.mcs.blocks.net.msg.MsgCompressorSlotActivate;
import cat.jiu.mcs.blocks.tileentity.TileEntityCompressor;

import net.minecraft.client.gui.GuiButton;
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
public class GUICompressor extends GuiContainer {
	private static ResourceLocation TEXTURE = new ResourceLocation(MCS.MODID + ":textures/gui/container/compressor.png");
	private static ResourceLocation ENERGY_TEXTURE = EnergyUtils.AllTeEnergy;
	private final ContainerCompressor container;
	@SuppressWarnings("unused")
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
		this.ySize = 176;
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
	public void initGui() {
		super.initGui();
		GlStateManager.pushMatrix();
		GlStateManager.scale(1, 1, 1);

		this.itemRender = new CompressorRenderItem(this.mc.renderEngine, this.itemRender.getItemModelMesher().getModelManager(), this.mc.getItemColors());

		if(this.te != null) {
			this.buttonList.clear();
			this.addButton(new GuiButton(0, (this.width / 2) + 55, (this.height / 2) - 71, 10, 10, "+") {
				@Override
				public void mouseReleased(int mouseX, int mouseY) {
					NetworkHandler.INSTANCE.sendToServer(new MsgCompressorCount(pos, container.getShrinkCount() + 1));
				}
			});
			this.addButton(new GuiButton(1, (this.width / 2) + 28, (this.height / 2) - 71, 10, 10, "-") {
				@Override
				public void mouseReleased(int mouseX, int mouseY) {
					NetworkHandler.INSTANCE.sendToServer(new MsgCompressorCount(pos, container.getShrinkCount() - 1));
				}
			});
			for(int i = 0; i < 8; i++) {
				if(i != 0) {
					this.addButton(new GuiButton(i + 2, (this.width / 2) - (62 - (18 * i)), (this.height / 2) - 34, 17, 7, "") {
						@Override
						public void mouseReleased(int mouseX, int mouseY) {
							te.setActivateSlot(this.id - 2, !container.slotCanCraft(this.id - 2));
							NetworkHandler.INSTANCE.sendToServer(new MsgCompressorSlotActivate(pos, this.id - 2, te.slotCanCraft(this.id - 2)));
							// MCS.instance.log.info("Slot: " + (this.id-2) + " State: " + container.slotCanCraft(this.id-2));
						}
					});
				}

				this.addButton(new GuiButton(i + 10, (this.width / 2) - (62 - (18 * i)), (this.height / 2) - 5, 17, 7, "") {
					@Override
					public void mouseReleased(int mouseX, int mouseY) {
						te.setActivateSlot(this.id - 2, !container.slotCanCraft(this.id - 2));
						NetworkHandler.INSTANCE.sendToServer(new MsgCompressorSlotActivate(pos, this.id - 2, te.slotCanCraft(this.id - 2)));
						// MCS.instance.log.info("Slot: " + (this.id-2) + " State: " + container.slotCanCraft(this.id-2));
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
		this.drawCenteredString(this.fontRenderer, I18n.format("tile.mcs.compressor.name"), 40, 5, 0XFFFFFF);

		this.mc.getTextureManager().bindTexture(TEXTURE);
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

		this.mc.getTextureManager().bindTexture(ENERGY_TEXTURE);
		super.drawTexturedModalRect(11, 6, 0, 0, 13, 60);

		int i = (int) (this.container.getEnergy() / 83334);// 能量条的比例

		// 渲染能量条 渲染位置xy，材质位置xy，材质长宽xy
		super.drawTexturedModalRect(11, 66 - i, 13, 0, 13, 0 + i);
		this.drawCenteredString(this.fontRenderer, I18n.format("info.mcs.compressor.cache_count"), 142, 5, 16777215);
		this.drawCenteredString(this.fontRenderer, this.container.getShrinkCount() + "", 140, 18, 16777215);
		this.drawCenteredString(this.fontRenderer, "\u2588", 178, 5, this.container.isDebug() ? Color.RED.getRGB() : Color.GREEN.getRGB());
		
		this.drawCenteredString(this.fontRenderer, JiuUtils.big_integer.format(BigInteger.valueOf(this.container.getEnergy()), 3) + "/" + JiuUtils.big_integer.format(this.te.maxEnergy, 1) + " JE", 59, 15, 16777215);

		GlStateManager.popMatrix();
	}
}
