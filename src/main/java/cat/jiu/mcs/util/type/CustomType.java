package cat.jiu.mcs.util.type;

public enum CustomType {
	ITEM_NORMA, ITEM_FOOD, ITEM_TOOL, ITEM_SWORD, ITEM_PICKAXE, ITEM_AXE, ITEM_SHOVEL, ITEM_HOE, BLOCK, UNKNOWN;

	public static CustomType getType(String[] type) {
		if(type.length == 2) {
			String main_type = type[0];
			String sub_type = type[1];
			if(main_type.equals("item")) {
				switch(sub_type) {
					case "food":
						return ITEM_FOOD;
					case "normal":
						return ITEM_NORMA;
					case "tool":
						return ITEM_TOOL;
					case "sword":
						return ITEM_SWORD;
					case "pickaxe":
						return ITEM_PICKAXE;
					case "shovel":
						return ITEM_SHOVEL;
					case "axe":
						return ITEM_AXE;
					case "hoe":
						return ITEM_HOE;
				}
			}
		}else if(type.length == 1) {
			String main_type = type[0];
			switch(main_type) {
				case "block":
					return CustomType.BLOCK;
			}
		}

		return CustomType.UNKNOWN;
	}
}
