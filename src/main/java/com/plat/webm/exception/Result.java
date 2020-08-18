package com.plat.webm.exception;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;


/**
 * 
 * @Title: Result.java
 * @Description: 返回异常的格式定义
 * @since 1.0.0 2016-10-27
 *
 */
public class Result extends HashMap<String, Object> {

	private static final long serialVersionUID = 1L;

	public Result() {
		put("code", 0);
		put("msg", "success");
	}

	public static Result error() {
		return error(500, "未知错误，请联系管理员");
	}

	public static Result error(String msg) {
		return error(500, msg);
	}

	public static Result error(int code, String msg) {
		Result result = new Result();
		result.put("code", code);
		result.put("msg", msg);
		return result;
	}

	public static Result ok(String msg) {
		Result result = new Result();
		result.put("msg", msg);
		return result;
	}

	public static Result ok(Map<String, Object> map) {
		Result result = new Result();
		result.putAll(map);
		return result;
	}

	public static Result ok(String data, String msg) {
		Result result = new Result();
		result.put("data", data);
		result.put("msg", msg);
		return result;
	}

	public static Result ok() {
		return new Result();
	}

	public Result put(String key, Object value) {
		super.put(key, value);
		return this;
	}

	public static Object ok(JSONArray json, String msg) {
		Result result = new Result();
		result.put("data", json);
		result.put("msg", msg);
		return result;
	}

	public static Object ok(JSONObject json, String msg) {
		Result result = new Result();
		result.put("data", json);
		result.put("msg", msg);
		return result;
	}

	public static Object ok(Map<String, Object> map, String msg) {
		Result result = new Result();
		result.put("data", map);
		result.put("msg", msg);
		return result;
	}

	public static Object ok(Page page, String msg) {
		Result result = new Result();
		result.put("data", page);
		result.put("msg", msg);
		return result;
	}

}