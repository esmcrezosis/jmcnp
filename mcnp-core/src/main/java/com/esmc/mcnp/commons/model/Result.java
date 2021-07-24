package com.esmc.mcnp.commons.model;

import java.util.HashMap;
import java.util.Map;

/**
 *  Réponse de la page
 */
public class Result extends HashMap<String, Object> {

	private static final long serialVersionUID = 1L;

	public Result() {
		put("code", 0);
	}

	public static Result error() {
		return error(500, "Exception inconnue, veuillez contacter l'administrateur");
	}

	public static Result error(String msg) {
		return error(500, msg);
	}

	public static Result error(int code, String msg) {
		Result r = new Result();
		r.put("code", code);
		r.put("msg", msg);
		return r;
	}

	public static Result ok(Object msg) {
		Result r = new Result();
		r.put("msg", msg);
		return r;
	}


	public static Result ok(Map<String, Object> map) {
		Result r = new Result();
		r.putAll(map);
		return r;
	}

	public static Result ok() {
		Result r = new Result();
		r.put("msg", "Opération réussie");
		return r;
	}

	@Override
	public Result put(String key, Object value) {
		super.put(key, value);
		return this;
	}
}