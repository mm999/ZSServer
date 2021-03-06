package com.huishu.ait.echart.series;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuwei on 2016/12/26
 */
public abstract class Serie<T> {

	private String type;

	private String name;

	private List<T> data;

	public Serie(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public List<T> getData() {
		return data;
	}

	public Serie<T> setData(List<T> data) {
		this.data = data;
		return this;
	}

	public Serie<T> addData(T data) {
		if (this.data == null) {
			this.data = new ArrayList<>();
		}
		this.data.add(data);
		return this;
	}

	public String getName() {
		return name;
	}

	public Serie<T> setName(String name) {
		this.name = name;
		return this;
	}

	public static class SerieData<T> {

		private T value;

		private String name;

		public SerieData(String name, T value) {
			this.name = name;
			this.value = value;
		}

		public Object getValue() {
			return value;
		}

		public void setValue(T value) {
			this.value = value;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

	}
}
