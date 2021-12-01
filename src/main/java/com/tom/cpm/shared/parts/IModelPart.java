package com.tom.cpm.shared.parts;

import java.io.IOException;

import com.tom.cpm.shared.io.IOHelper;
import com.tom.cpm.shared.io.IOHelper.ObjectBlock;

public interface IModelPart extends ObjectBlock<ModelPartType> {
	@Override
	void write(IOHelper dout) throws IOException;
	@Override
	ModelPartType getType();
}
