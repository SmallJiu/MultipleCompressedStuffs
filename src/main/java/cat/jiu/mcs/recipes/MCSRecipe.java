package cat.jiu.mcs.recipes;

import cat.jiu.mcs.MCS;
import cat.jiu.mcs.config.Configs;
import cat.jiu.core.util.JiuUtils;
import cat.jiu.mcs.util.MCSUtil;
import cat.jiu.mcs.util.ModSubtypes;
import cat.jiu.mcs.util.base.sub.BaseBlockSub;
import cat.jiu.mcs.util.init.MCSBlocks;
import cat.jiu.mcs.util.init.MCSItems;
import cat.jiu.mcs.util.init.MCSResources;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

@SuppressWarnings("deprecation")
public class MCSRecipe {
	private static final Recipe recipe = new Recipe(MCS.MODID);

	public static void register() {
		try {
			mc();
			item();
			compressed();
			smelting();
		}catch(Exception e) {}
	}

	private static void compressed() {
		itemCompressed();
		blockCompressed();
	}

	private static void mc() {
		recipe.addShapedRecipes(new ItemStack(Items.NETHER_WART, 9), new ItemStack(Blocks.NETHER_WART_BLOCK));
		recipe.addShapedRecipes(new ItemStack(Items.GOLDEN_APPLE, 1, 1),
				new ItemStack(Blocks.GOLD_BLOCK), new ItemStack(Blocks.GOLD_BLOCK), new ItemStack(Blocks.GOLD_BLOCK),
				new ItemStack(Blocks.GOLD_BLOCK), new ItemStack(Items.GOLDEN_APPLE), new ItemStack(Blocks.GOLD_BLOCK),
				new ItemStack(Blocks.GOLD_BLOCK), new ItemStack(MCSBlocks.minecraft.normal.C_GOLD_B), new ItemStack(Blocks.GOLD_BLOCK));
		
		for(int meta = 0; meta < 16; meta++) {
			ItemStack gold_apple = new ItemStack(MCSBlocks.minecraft.normal.C_GOLD_B);
			recipe.addShapedRecipes(new ItemStack(MCSItems.minecraft.food.C_ENCHANTED_GOLD_APPLE_F, 1, meta),
					gold_apple, gold_apple, gold_apple,
					gold_apple, new ItemStack(MCSItems.minecraft.food.C_GOLD_APPLE_F), gold_apple,
					gold_apple, gold_apple, gold_apple);
		}
	}

	private static void smelting() {
		addSmelting(MCSItems.minecraft.food.C_BEEF_F, MCSItems.minecraft.food.C_COOKED_BEEF_F);
		addSmelting(MCSItems.minecraft.food.C_CHICKEN_F, MCSItems.minecraft.food.C_COOKED_CHICKEN_F);
		addSmelting(MCSItems.minecraft.food.C_FISH_F, MCSItems.minecraft.food.C_COOKED_FISH_F);
		addSmelting(MCSItems.minecraft.food.C_MUTTON_F, MCSItems.minecraft.food.C_COOKED_MUTTON_F);
		addSmelting(MCSItems.minecraft.food.C_PORKCHOP_F, MCSItems.minecraft.food.C_COOKED_PORKCHOP_F);
		addSmelting(MCSItems.minecraft.food.C_RABBIT_F, MCSItems.minecraft.food.C_COOKED_RABBIT_F);
		addSmelting(MCSItems.minecraft.food.C_SALMON_FISH_F, MCSItems.minecraft.food.C_COOKED_SALMON_FISH_F);
		addSmelting(MCSItems.minecraft.food.C_POTATO_F, MCSItems.minecraft.food.C_BAKED_POTATO_F);

		addSmelting(MCSItems.draconic_evolution.normal.C_DRACONIUM_DUST_I, MCSBlocks.draconic_evolution.normal.C_DRACONIUM_BLOCK_B, 1);

		// ThermalFoundation: dust to block
		addSmelting(MCSItems.thermal_foundation.normal.C_DUST_LUMIUM_I, MCSBlocks.thermal_foundation.normal.C_LUMIUM_B, 1);
		addSmelting(MCSItems.thermal_foundation.normal.C_DUST_ENDERIUM_I, MCSBlocks.thermal_foundation.normal.C_ENDERIUM_B, 1);
		addSmelting(MCSItems.thermal_foundation.normal.C_DUST_ALUMINUM_I, MCSBlocks.thermal_foundation.normal.C_ALUMINUM_B, 1);
		addSmelting(MCSItems.thermal_foundation.normal.C_DUST_BRONZE_I, MCSBlocks.thermal_foundation.normal.C_BRONZE_B, 1);
		addSmelting(MCSItems.thermal_foundation.normal.C_DUST_CONSTANTAN_I, MCSBlocks.thermal_foundation.normal.C_CONSTANTAN_B, 1);
		addSmelting(MCSItems.thermal_foundation.normal.C_DUST_COPPER_I, MCSBlocks.thermal_foundation.normal.C_COPPER_B, 1);
		addSmelting(MCSItems.thermal_foundation.normal.C_DUST_ELECTRUM_I, MCSBlocks.thermal_foundation.normal.C_ELECTRUM_B, 1);
		addSmelting(MCSItems.thermal_foundation.normal.C_DUST_GOLD_I, MCSBlocks.minecraft.normal.C_GOLD_B, 1);
		addSmelting(MCSItems.thermal_foundation.normal.C_DUST_INVAR_I, MCSBlocks.thermal_foundation.normal.C_INVAR_B, 1);
		addSmelting(MCSItems.thermal_foundation.normal.C_DUST_IRIDIUM_I, MCSBlocks.thermal_foundation.normal.C_IRIDIUM_B, 1);
		addSmelting(MCSItems.thermal_foundation.normal.C_DUST_IRON_I, MCSBlocks.minecraft.normal.C_IRON_B, 1);
		addSmelting(MCSItems.thermal_foundation.normal.C_DUST_LEAD_I, MCSBlocks.thermal_foundation.normal.C_LEAD_B, 1);
		addSmelting(MCSItems.thermal_foundation.normal.C_DUST_MITHRIL_I, MCSBlocks.thermal_foundation.normal.C_MITHRIL_B, 1);
		addSmelting(MCSItems.thermal_foundation.normal.C_DUST_NICKL_I, MCSBlocks.thermal_foundation.normal.C_NICKEL_B, 1);
		addSmelting(MCSItems.thermal_foundation.normal.C_DUST_PLATINUM_I, MCSBlocks.thermal_foundation.normal.C_PLATINUM_B, 1);
		addSmelting(MCSItems.thermal_foundation.normal.C_DUST_SIGNALUM_I, MCSBlocks.thermal_foundation.normal.C_SIGNALUM_B, 1);
		addSmelting(MCSItems.thermal_foundation.normal.C_DUST_SILVER_I, MCSBlocks.thermal_foundation.normal.C_SILVER_B, 1);
		addSmelting(MCSItems.thermal_foundation.normal.C_DUST_STEEL_I, MCSBlocks.thermal_foundation.normal.C_STEEL_B, 1);
		addSmelting(MCSItems.thermal_foundation.normal.C_DUST_TIN_I, MCSBlocks.thermal_foundation.normal.C_TIN_B, 1);

	}

	/**
	 * 物品a 合成 物品a的meta-x的物品b
	 */
	public static void addSmelting(Item in, Block out, int metaDisparity) {
		if(in != null && out != null) {
			for(ModSubtypes type : ModSubtypes.values()) {
				int imeta = type.getMeta();
				int ometa = imeta - metaDisparity;
				if(!(ometa < 0)) {
					recipe.addSmelting(new ItemStack(in, 1, imeta), new ItemStack(out, 1, ometa), 0);
				}
			}
		}
	}

	private static void addSmelting(Item in, Item out) {
		if(in != null && out != null) {
			for(ModSubtypes type : ModSubtypes.values()) {
				int meta = type.getMeta();
				recipe.addSmelting(new ItemStack(in, 1, meta), new ItemStack(out, 1, meta), 0);
			}
			recipe.addSmelting(new ItemStack(in, 1, Short.MAX_VALUE), new ItemStack(out, 1, Short.MAX_VALUE), 0);
		}
	}

	private static void item() {
		recipe.addShapedRecipes(new ItemStack(MCSItems.normal.CAT_INGOT),
				new ItemStack(MCSItems.normal.CAT_HAIR), new ItemStack(MCSItems.normal.CAT_HAIR), new ItemStack(MCSItems.normal.CAT_HAIR),
				new ItemStack(MCSItems.normal.CAT_HAIR), new ItemStack(MCSItems.minecraft.food.C_FISH_F, 1, 1), new ItemStack(MCSItems.normal.CAT_HAIR),
				new ItemStack(MCSItems.normal.CAT_HAIR), new ItemStack(MCSItems.normal.CAT_HAIR), new ItemStack(MCSItems.normal.CAT_HAIR));

		if(Configs.Custom.Mod_Stuff.ThermalFoundation) {
			recipe.addShapedRecipes(new ItemStack(MCSBlocks.COMPRESSOR),
					new ItemStack(MCSBlocks.minecraft.normal.C_OBSIDIAN_B), new ItemStack(Blocks.PISTON), new ItemStack(MCSBlocks.minecraft.normal.C_OBSIDIAN_B),
					new ItemStack(MCSBlocks.minecraft.normal.C_OBSIDIAN_B, 1, 1), new ItemStack(MCSItems.thermal_foundation.normal.C_GEAR_PLATINUM_I), new ItemStack(MCSBlocks.minecraft.normal.C_OBSIDIAN_B, 1, 1),
					new ItemStack(MCSBlocks.minecraft.normal.C_OBSIDIAN_B), new ItemStack(Blocks.PISTON), new ItemStack(MCSBlocks.minecraft.normal.C_OBSIDIAN_B));
		}else {
			recipe.addShapedRecipes(new ItemStack(MCSBlocks.COMPRESSOR),
					new ItemStack(MCSBlocks.minecraft.normal.C_OBSIDIAN_B), new ItemStack(Blocks.PISTON), new ItemStack(MCSBlocks.minecraft.normal.C_OBSIDIAN_B),
					new ItemStack(MCSBlocks.minecraft.normal.C_OBSIDIAN_B, 1, 1), recipe.EMPTY, new ItemStack(MCSBlocks.minecraft.normal.C_OBSIDIAN_B, 1, 1),
					new ItemStack(MCSBlocks.minecraft.normal.C_OBSIDIAN_B), new ItemStack(Blocks.PISTON), new ItemStack(MCSBlocks.minecraft.normal.C_OBSIDIAN_B));
		}

		recipe.addShapedRecipes(new ItemStack(MCSItems.normal.CAT_HAMMER),
				new ItemStack(MCSItems.normal.CAT_INGOT), new ItemStack(MCSItems.normal.CAT_INGOT), new ItemStack(MCSItems.normal.CAT_INGOT),
				new ItemStack(MCSItems.normal.CAT_INGOT), new ItemStack(Items.MILK_BUCKET), new ItemStack(MCSItems.normal.CAT_INGOT),
				recipe.EMPTY, new ItemStack(Items.MILK_BUCKET), recipe.EMPTY);
		
		recipe.addShapedRecipes(new ItemStack(MCSItems.normal.DESTROYER),
				new ItemStack(MCSBlocks.minecraft.normal.C_TNT_B, 1, 1), new ItemStack(MCSBlocks.minecraft.has.C_NETHER_STAR_B, 1, 3), new ItemStack(MCSBlocks.minecraft.normal.C_TNT_B, 1, 1),
				new ItemStack(MCSBlocks.minecraft.normal.C_TNT_B, 1, 1), new ItemStack(MCSItems.normal.CAT_HAMMER), new ItemStack(MCSBlocks.minecraft.normal.C_TNT_B),
				new ItemStack(MCSBlocks.minecraft.normal.C_TNT_B, 1, 1), new ItemStack(MCSBlocks.minecraft.normal.C_BEDROCK_B, 1, 2), new ItemStack(MCSBlocks.minecraft.normal.C_TNT_B, 1, 1));
		
		if(Configs.Custom.Mod_Stuff.IndustrialCraft) {
			icCompressedItemCrafting();
		}
	}
	
	private static void icCompressedItemCrafting() {
		MCSItems.IndustrialCraft.Normal ic2 = MCSItems.ic2.normal;
		for(int meta = 0; meta < 16; meta++) {
			ItemStack circuit = new ItemStack(ic2.C_circuit_I, 1, meta);
			ItemStack re_battery = new ItemStack(ic2.C_re_battery_I, 1, meta);
			recipe.addShapedRecipes(new ItemStack(ic2.C_charging_re_battery_I, 1, meta),
					circuit, re_battery, circuit,
					re_battery, recipe.EMPTY, re_battery,
					circuit, re_battery, circuit
			);
			
			ItemStack heat_exchanger = new ItemStack(ic2.C_heat_exchanger_I, 1, meta);
			ItemStack advanced_re_battery = new ItemStack(ic2.C_charging_advanced_re_battery_I, 1, meta);
			ItemStack C_re_battery_I = new ItemStack(ic2.C_charging_re_battery_I, 1, meta);
			recipe.addShapedRecipes(new ItemStack(ic2.C_charging_advanced_re_battery_I, 1, meta),
					heat_exchanger, advanced_re_battery, heat_exchanger,
					advanced_re_battery, C_re_battery_I, advanced_re_battery,
					heat_exchanger, advanced_re_battery, heat_exchanger
			);
			
			ItemStack component_heat_exchanger = new ItemStack(ic2.C_component_heat_exchanger_I, 1, meta);
			ItemStack energy_crystal = new ItemStack(ic2.C_energy_crystal_I, 1, meta);
			ItemStack C_advanced_re_battery_I = new ItemStack(ic2.C_advanced_re_battery_I, 1, meta);
			recipe.addShapedRecipes(new ItemStack(ic2.C_charging_energy_crystal_I, 1, meta),
					component_heat_exchanger, energy_crystal, component_heat_exchanger,
					energy_crystal, C_advanced_re_battery_I, energy_crystal,
					component_heat_exchanger, energy_crystal, component_heat_exchanger
			);
			
			ItemStack advanced_heat_exchanger = new ItemStack(ic2.C_advanced_heat_exchanger_I, 1, meta);
			ItemStack lapotron_crystal = new ItemStack(ic2.C_lapotron_crystal_I, 1, meta);
			ItemStack C_charging_energy_crystal_I = new ItemStack(ic2.C_charging_energy_crystal_I, 1, meta);
			recipe.addShapedRecipes(new ItemStack(ic2.C_charging_lapotron_crystal_I, 1, meta),
					advanced_heat_exchanger, lapotron_crystal, advanced_heat_exchanger,
					lapotron_crystal, C_charging_energy_crystal_I, lapotron_crystal,
					advanced_heat_exchanger, lapotron_crystal, advanced_heat_exchanger
			);
			
			ItemStack thick_neutron_reflector = new ItemStack(ic2.C_thick_neutron_reflector_I, 1, meta);
			ItemStack dense_plate_copper = new ItemStack(ic2.C_dense_plate_copper_I, 1, meta);
			ItemStack plate_iridium = new ItemStack(ic2.C_plate_iridium_reinforcing_I, 1, meta);
			recipe.addShapedRecipes(new ItemStack(ic2.C_iridium_reflector_I, 1, meta),
					thick_neutron_reflector, thick_neutron_reflector, thick_neutron_reflector,
					dense_plate_copper, plate_iridium, dense_plate_copper,
					thick_neutron_reflector, thick_neutron_reflector, thick_neutron_reflector
			);
			recipe.addShapedRecipes(new ItemStack(ic2.C_iridium_reflector_I, 1, meta),
					thick_neutron_reflector, dense_plate_copper, thick_neutron_reflector,
					thick_neutron_reflector, plate_iridium, thick_neutron_reflector,
					thick_neutron_reflector, dense_plate_copper, thick_neutron_reflector
			);
			
			ItemStack casing_copper = new ItemStack(ic2.C_casing_copper_I, 1, meta);
			recipe.addShapedRecipes(new ItemStack(ic2.C_copper_boiler_I, 1, meta),
					casing_copper, casing_copper, casing_copper,
					casing_copper, recipe.EMPTY, casing_copper,
					casing_copper, casing_copper, casing_copper
			);
			
			recipe.addSmelting(new ItemStack(ic2.C_resin_I, 1, meta), new ItemStack(ic2.C_rubber_I,1, meta), 9 );
			
			ItemStack dense_plate_iron = new ItemStack(ic2.C_dense_plate_iron_I, 1, meta);
			ItemStack plutonium = new ItemStack(ic2.C_plutonium_I, 1, meta);
			recipe.addShapedRecipes(new ItemStack(ic2.C_RTG_PELLET_I, 1, meta),
					dense_plate_iron, plutonium, dense_plate_iron,
					dense_plate_iron, plutonium, dense_plate_iron,
					dense_plate_iron, plutonium, dense_plate_iron
			);
			recipe.addShapedRecipes(new ItemStack(ic2.C_RTG_PELLET_I, 1, meta),
					dense_plate_iron, dense_plate_iron, dense_plate_iron,
					plutonium, plutonium, plutonium,
					dense_plate_iron, dense_plate_iron, dense_plate_iron
			);
			
			ItemStack U_238 = new ItemStack(ic2.C_URANIUM_238_I, 1, meta);
			recipe.addShapedRecipes(new ItemStack(ic2.C_MOX_I, 1, meta),
					U_238, plutonium, U_238,
					U_238, plutonium, U_238,
					U_238, plutonium, U_238
			);
			recipe.addShapedRecipes(new ItemStack(ic2.C_MOX_I, 1, meta),
					U_238, U_238, U_238,
					plutonium, plutonium, plutonium,
					U_238, U_238, U_238
			);
			
			ItemStack u_block = new ItemStack(MCSBlocks.ic2.normal.C_URANIUM_BLOCK_B, 1, meta);
			recipe.add1x1Recipes(JiuUtils.item.copyStack(U_238, 9, false), u_block);
			recipe.add3x3AllRecipes(u_block, U_238);
			
			ItemStack glass = new ItemStack(MCSBlocks.minecraft.normal.C_GLASS_B, 1, meta);
			ItemStack plate_alloy = new ItemStack(ic2.C_plate_alloy_I, 1, meta);
			recipe.addShapedRecipes(new ItemStack(MCSBlocks.ic2.normal.C_REINFORCED_GLASS_B, 1, meta),
					glass, plate_alloy, glass,
					glass, glass, glass,
					glass, plate_alloy, glass
			);
			recipe.addShapedRecipes(new ItemStack(MCSBlocks.ic2.normal.C_REINFORCED_GLASS_B, 1, meta),
					glass, glass, glass,
					plate_alloy, glass, plate_alloy,
					glass, glass, glass
			);
		}
	}

	private static void itemCompressed() {
		if(Configs.use_default_recipes) {
			if(Configs.use_3x3_recipes) {
				for(Item item : MCSResources.ITEMS) {
					ItemStack unCompressedItem = MCSUtil.item.getUnCompressed(item);
					boolean canMakeDefaultStackRecipe = MCSUtil.item.canMakeDefaultStackRecipe(item);

					if(!unCompressedItem.isEmpty()) {
						if(canMakeDefaultStackRecipe) {
							// 压缩
							recipe.add3x3AllRecipes(new ItemStack(item), unCompressedItem);
							for(int meta = 1; meta < 16; ++meta) {
								recipe.add3x3AllRecipes(new ItemStack(item, 1, meta), new ItemStack(item, 1, (meta - 1)));
							}

							// 解压
							recipe.add1x1Recipes(JiuUtils.item.copyStack(unCompressedItem, 9, false), new ItemStack(item));
							for(int meta = 0; meta < 15; ++meta) {
								recipe.add1x1Recipes(new ItemStack(item, 9, meta), new ItemStack(item, 1, (meta + 1)));
							}
						}
					}
				}
			}else {
				for(Item item : MCSResources.ITEMS) {
					ItemStack unCompressedItem = MCSUtil.item.getUnCompressed(item);
					Boolean canMakeDefaultStackRecipe = MCSUtil.item.canMakeDefaultStackRecipe(item);

					if(!unCompressedItem.isEmpty()) {
						if(canMakeDefaultStackRecipe) {
							// 压缩
							recipe.add2x2AllRecipes(new ItemStack(item), unCompressedItem);
							for(int meta = 1; meta < 16; ++meta) {
								recipe.add2x2AllRecipes(new ItemStack(item, 1, meta), new ItemStack(item, 1, (meta - 1)));
							}
							
							// 解压
							recipe.add1x1Recipes(JiuUtils.item.copyStack(unCompressedItem, 4, false), new ItemStack(item));
							for(int meta = 0; meta < 15; ++meta) {
								recipe.add1x1Recipes(new ItemStack(item, 4, meta), new ItemStack(item, 1, (meta + 1)));
							}
						}
					}
				}

			}
		}
	}

	private static void blockCompressed() {
		if(Configs.use_default_recipes) {
			for(BaseBlockSub block : MCSResources.SUB_BLOCKS) {
				ItemStack unCompressedItem = block.getUnCompressedStack();

				if(block.canMakeDefaultStackRecipe()) {
					if(Configs.use_3x3_recipes) {
						// 方块压缩
						{
							recipe.add3x3AllRecipes(new ItemStack(block), unCompressedItem);
							for(int meta = 1; meta < 16; ++meta) {
								recipe.add3x3AllRecipes(new ItemStack(block, 1, meta), new ItemStack(block, 1, (meta - 1)));
							}
						}

						// 方块解压
						{
							recipe.add1x1Recipes(JiuUtils.item.copyStack(unCompressedItem, 9, false), new ItemStack(block));
							for(int meta = 1; meta < 16; ++meta) {
								recipe.add1x1Recipes(new ItemStack(block, 9, meta - 1), new ItemStack(block, 1, meta));

							}
						}
					}else {
						// 方块压缩
						{
							recipe.add2x2AllRecipes(new ItemStack(block), unCompressedItem);
							for(int meta = 1; meta < 16; ++meta) {
								recipe.add2x2AllRecipes(new ItemStack(block, 1, meta), new ItemStack(block, 1, (meta - 1)));
							}
						}

						// 方块解压
						{
							recipe.add1x1Recipes(JiuUtils.item.copyStack(unCompressedItem, 4, false), new ItemStack(block));

							for(int meta = 1; meta < 15; ++meta) {
								recipe.add1x1Recipes(new ItemStack(block, 4, meta - 1), new ItemStack(block, 1, meta));
							}
						}
					}
				}
			}
		}
	}
}
