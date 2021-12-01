package com.tom.cpm.shared.parts;

import java.io.IOException;

import com.tom.cpm.shared.io.IOHelper;

public class ModelPartDefinitionLink extends ModelPartLink {

	public ModelPartDefinitionLink(String link) {
		super(link);
	}

	@Override
	public ModelPartType getType() {
		return ModelPartType.DEFINITION_LINK;
	}
}
