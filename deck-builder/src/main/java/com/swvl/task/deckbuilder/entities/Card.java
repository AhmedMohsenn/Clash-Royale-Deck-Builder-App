package com.swvl.task.deckbuilder.entities;

public class Card {

	private String _id;
	private String idName;
	private String rarity;
	private String type;
	private String name;
	private String description;
	private int elixirCost;
	private int copyId;
	private int arena;
	private int order;
	private int __v;

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getIdName() {
		return idName;
	}

	public void setIdName(String idName) {
		this.idName = idName;
	}

	public String getRarity() {
		return rarity;
	}

	public void setRarity(String rarity) {
		this.rarity = rarity;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getElixirCost() {
		return elixirCost;
	}

	public void setElixirCost(int elixirCost) {
		this.elixirCost = elixirCost;
	}

	public int getCopyId() {
		return copyId;
	}

	public void setCopyId(int copyId) {
		this.copyId = copyId;
	}

	public int getArena() {
		return arena;
	}

	public void setArena(int arena) {
		this.arena = arena;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public int get__v() {
		return __v;
	}

	public void set__v(int __v) {
		this.__v = __v;
	}

}
