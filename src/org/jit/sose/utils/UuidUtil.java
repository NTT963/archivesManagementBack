package org.jit.sose.utils;

import java.util.UUID;

public class UuidUtil {
	public String getUuid(){
		return UUID.randomUUID().toString().replaceAll("-", "").toLowerCase();
	}
}
