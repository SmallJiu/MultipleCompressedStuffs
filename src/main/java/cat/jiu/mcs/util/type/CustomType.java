package cat.jiu.mcs.util.type;

public enum CustomType {
	ITEM_NORMA, ITEM_FOOD, ITEM_TOOL, ITEM_SWORD, ITEM_PICKAXE, ITEM_AXE, BLOCK, UNKNOWN;
	
	public static CustomType getType(String[] type) {
		if(type.length == 2) {
			String main_type = type[0];
			String sub_type = type[1];
			if(main_type.equals("item")) {
				if(sub_type.equals("food")) {
					return ITEM_FOOD;
				}
				if(sub_type.equals("normal")) {
					return ITEM_NORMA;
				}
				if(sub_type.equals("tool")) {
					return ITEM_TOOL;
				}
				if(sub_type.equals("sword")) {
					return ITEM_SWORD;
				}
				if(sub_type.equals("pickaxe")) {
					return ITEM_PICKAXE;
				}
				if(sub_type.equals("axe")) {
					return ITEM_AXE;
				}
			}
		}else if(type.length == 1) {
			String main_type = type[0];
			if(main_type.equals("block")) {
				return CustomType.BLOCK;
			}
		}
		
		return CustomType.UNKNOWN;
	}
}
