package com.tom.cpm.shared.parts;

import java.io.IOException;

import com.tom.cpm.shared.io.IOHelper;

public class ModelPartSkinType implements IModelPart {
	private int type;
	public ModelPartSkinType(int type) {
		this.type = type;
	}

	@Override
	public void write(IOHelper dout) throws IOException {
		dout.write(type);
	}

	@Override
	public ModelPartType getType() {
		return ModelPartType.SKIN_TYPE;
	}
}
