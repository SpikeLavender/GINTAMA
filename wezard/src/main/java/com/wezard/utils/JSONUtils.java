package com.wezard.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class JSONUtils {

	/**
	 * d打印格式化
	 */
	public static <T> String printFormatWitNull(T data) {
		return JSON.toJSONString(data,
				SerializerFeature.PrettyFormat,
				SerializerFeature.WriteMapNullValue,
				SerializerFeature.WriteDateUseDateFormat);
	}

	public static <T> String printFormat(T data) {
		return JSON.toJSONString(data,
				SerializerFeature.PrettyFormat,
				SerializerFeature.WriteDateUseDateFormat);
	}

	public static JSONObject parseObject(String str) {
	    return JSON.parseObject(str);
    }
}
