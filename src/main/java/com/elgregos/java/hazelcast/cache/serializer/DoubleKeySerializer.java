package com.elgregos.java.hazelcast.cache.serializer;

import java.io.IOException;

import com.elgregos.java.hazelcast.entities.key.DoubleKey;
import com.hazelcast.nio.serialization.ByteArraySerializer;

public class DoubleKeySerializer implements ByteArraySerializer<DoubleKey> {

	private static final String KEY_SEPARATOR = "_";

	@Override
	public void destroy() {
	}

	@Override
	public int getTypeId() {
		return 2;
	}

	@Override
	public DoubleKey read(byte[] buffer) throws IOException {
		final String[] codes = new String(buffer).split(KEY_SEPARATOR);
		return new DoubleKey(codes[0], codes[1]);
	}

	@Override
	public byte[] write(DoubleKey key) throws IOException {
		final StringBuilder builder = new StringBuilder(key.getFirstCode());
		builder.append(KEY_SEPARATOR).append(key.getSecondCode());
		return builder.toString().getBytes();
	}

}
