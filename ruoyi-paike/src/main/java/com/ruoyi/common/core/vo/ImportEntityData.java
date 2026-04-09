package com.ruoyi.common.core.vo;

import lombok.Data;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class ImportEntityData {
	private int index = 0;
	private int total = 0;
	private int successNum = 0;
	private int failureNum = 0;
	private int poolSize = 100;
	private List<Field> keyFields;
	private List datas;
	private List inserts = new ArrayList();
	private List updates = new ArrayList();

	private Map<String, Object> map;

	public void success() {
		successNum++;
	}

	public void failure() {
		failureNum++;
	}

	public void put(String key, Object obj) {
		if (map == null) {
			map = new HashMap<>();
		}
		map.put(key, obj);
	}

	public <T> T get(String key) {
		if (map == null) {
			return null;
		}
		return (T) map.get(key);
	}
}
