package com.esmc.mcnp.web.model.grid;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Grid<T extends Serializable> {

	/**
	 * Total pages
	 */
	private String total;

	/**
	 * Contains the actual data
	 */
	private List<T> rows;

	public Grid() {
	}

	Grid(String page, String total, String records, List<T> rows) {
		super();
		this.total = total;
		this.rows = rows;
	}

	@Override
	public String toString() {
		return "JqgridResponse [total=" + total + ", records=" + "]";
	}
}
