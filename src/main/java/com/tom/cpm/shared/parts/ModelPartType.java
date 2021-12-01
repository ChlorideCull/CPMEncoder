package com.tom.cpm.shared.parts;

public enum ModelPartType {
	END(),
	PLAYER(),
	TEMPLATE(),
	DEFINITION(),
	DEFINITION_LINK(),
	SKIN(),
	SKIN_LINK(),
	PLAYER_PARTPOS(),
	RENDER_EFFECT(),
	UUID_LOCK(),
	ANIMATION_DATA(),
	SKIN_TYPE(),
	MODEL_ROOT(),
	@Deprecated LIST_ICON(),
	DUP_ROOT(),
	CLONEABLE(),
	SCALE(),
	TEXTURE(),
	ANIMATED_TEX(),
	;
	public static final ModelPartType[] VALUES = values();
}
