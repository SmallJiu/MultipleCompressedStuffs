package cat.jiu.mcs.util;

import java.util.Collection;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;

import net.minecraft.block.properties.PropertyHelper;

public class PropertySubtypes extends PropertyHelper<ModSubtypes> {
	public PropertySubtypes(String name) {
		super(name, ModSubtypes.class);
	}
	
	protected static final Collection<ModSubtypes> values = Lists.newArrayList(ModSubtypes.values(ModSubtypes.MAX <= 16 ? ModSubtypes.MAX : 16));
	@Override
	public Collection<ModSubtypes> getAllowedValues() {
		return values;
	}

	@Override
	public Optional<ModSubtypes> parseValue(String value) {
		return Optional.of(ModSubtypes.byName(value));
	}

	@Override
	public String getName(ModSubtypes value) {
		return value.getName();
	}
}
