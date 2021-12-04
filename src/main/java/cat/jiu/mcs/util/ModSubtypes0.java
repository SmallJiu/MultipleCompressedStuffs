package cat.jiu.mcs.util;

import cat.jiu.core.api.ISubBlockSerializable;

public enum ModSubtypes0 implements ISubBlockSerializable {

	LEVEL_1(0),
	LEVEL_2(1),
	LEVEL_3(2),
	LEVEL_4(3),
	LEVEL_5(4),
	LEVEL_6(5),
	LEVEL_7(6),
	LEVEL_8(7),
	LEVEL_9(8),
	LEVEL_10(9),
	LEVEL_11(10),
	LEVEL_12(11),
	LEVEL_13(12),
	LEVEL_14(13),
	LEVEL_15(14),
	LEVEL_16(15);
	
	private final int meta;
	
	ModSubtypes0(int meta) {
		this.meta = meta;
	}
	
	@Override
	public int getMeta() { return meta; }
	@Override
	public String getName() { return "level_" + meta; }
	@Override
	public String func_176610_l() { return getName(); }
}