package com.ctoedu.demo.api.service.auth;

import java.util.Map;
import java.util.Set;

public interface AuthedService {

	Map<String, Set<String>> find(String dataId, String pType);
}
