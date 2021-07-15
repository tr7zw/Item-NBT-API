package dev.tr7zw.nbtapi;

import java.util.Map;

import org.bukkit.persistence.PersistentDataContainer;

import dev.tr7zw.nbtapi.utils.nmsmappings.ReflectionMethod;

public class NBTPersistentDataContainer extends NBTCompound {

	private final PersistentDataContainer container;
	
	protected NBTPersistentDataContainer(PersistentDataContainer container) {
		super(null, null);
		this.container = container;
	}

	@Override
	public Object getCompound() {
		return ReflectionMethod.CRAFT_PERSISTENT_DATA_CONTAINER_TO_TAG.run(container);
	}

	@Override
	protected void setCompound(Object compound) {
		@SuppressWarnings("unchecked")
		Map<Object, Object> map = (Map<Object, Object>) ReflectionMethod.CRAFT_PERSISTENT_DATA_CONTAINER_GET_MAP.run(container);
		map.clear();
		ReflectionMethod.CRAFT_PERSISTENT_DATA_CONTAINER_PUT_ALL.run(container, compound);
	}
	
}
