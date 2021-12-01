package com.tom.cpm.shared.parts;

import java.io.IOException;

import com.tom.cpm.shared.io.IOHelper;

public class ModelPartEnd implements IModelPart {
	public static final ModelPartEnd END = new ModelPartEnd();

	ModelPartEnd() {}

	@Override
	public void write(IOHelper dout) throws IOException {}

	@Override
	public ModelPartType getType() {
		return ModelPartType.END;
	}
}
