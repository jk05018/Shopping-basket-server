package org.prgms.shoppingbasket.server.common.utils;

import java.nio.ByteBuffer;
import java.util.UUID;

/* UUID 변환기*/
public final class UUIDConverter {

	private UUIDConverter() {
	}

	public static byte[] uuidToBytes(UUID uuid) {
		if (uuid == null)
			return null;
		var byteBuffer = ByteBuffer.wrap(new byte[16]);
		byteBuffer.putLong(uuid.getMostSignificantBits());
		byteBuffer.putLong(uuid.getLeastSignificantBits());
		return byteBuffer.array();
	}

	public static UUID bytesToUUID(byte[] bytes) {
		if (bytes == null)
			return null;
		var byteBuffer = ByteBuffer.wrap(bytes);
		return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
	}
}
