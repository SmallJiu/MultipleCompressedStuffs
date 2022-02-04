package cat.jiu.mcs.util.type;

public class CustomItemType {
	private final String name;
	private final String unItem;
	private final int unMeta;
	private final boolean isFood;
	private final float foodAmount;
	private final float foodSaturation;
	private final boolean isWolfFood;
	
	public CustomItemType(String name, String unItem, int unMeta, boolean isFood, float foodAmount, float foodSaturation, boolean isWolfFood) {
		this.name = name;
		this.unItem = unItem;
		this.unMeta = unMeta;
		this.isFood = isFood;
		this.foodAmount = foodAmount;
		this.foodSaturation = foodSaturation;
		this.isWolfFood = isWolfFood;
	}
	
	public String getName() { return name; }
	public String getUnItem() { return unItem; }
	public int getUnMeta() { return unMeta; }
	public boolean isFood() { return isFood; }
	public float getFoodAmount() { return foodAmount; }
	public float getFoodSaturation() { return foodSaturation; }
	public boolean isWolfFood() { return isWolfFood; }
}
