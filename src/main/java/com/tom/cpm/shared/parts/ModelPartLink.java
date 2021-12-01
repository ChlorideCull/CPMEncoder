package com.tom.cpm.shared.parts;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import com.tom.cpm.shared.io.ChecksumInputStream;
import com.tom.cpm.shared.io.IOHelper;

public abstract class ModelPartLink implements IModelPart {
	private String link;

	public ModelPartLink(String link) {
		this.link = link;
	}

	@Override
	public void write(IOHelper dout) throws IOException {
		byte[] linka = link.getBytes(StandardCharsets.UTF_8);
		dout.write(linka.length);
		dout.write(linka);
	}
}
