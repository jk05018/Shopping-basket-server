package org.prgms.shoppingbasket.server.common.utils;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public final class LocalDateTimeUtil {

	public static LocalDateTime toLocalDateTime(Timestamp timestamp) {
		return timestamp != null ? timestamp.toLocalDateTime() : null;
	}
}
