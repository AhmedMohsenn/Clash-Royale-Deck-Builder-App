package com.swvl.task.deckbuilder.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "card")
public class Card {

	@Override
	public String toString() {
		return "Card [id=" + id + ", clashId=" + clashId + ", idName=" + idName + ", rarity=" + rarity + ", type=" + type + ", name=" + name
				+ ", description=" + description + ", elixirCost=" + elixirCost + "]";
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotNull
	@Column(unique = true)
	private String clashId;

	@NotNull
	@Column(unique = true)
	private String idName;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	private CardRarity rarity;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	private CardType type;

	@NotNull
	@Column(unique = true)
	private String name;

	private String description;

	@NotNull
	private int elixirCost;

	public Card(@NotNull String clashId, @NotNull String idName, @NotNull String name, @NotNull String description, @NotNull int elixirCost) {
		this.clashId = clashId;
		this.idName = idName;
		this.name = name;
		this.description = description;
		this.elixirCost = elixirCost;
	}

	public Card() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getClashId() {
		return clashId;
	}

	public void setClashId(String clashId) {
		this.clashId = clashId;
	}

	public String getIdName() {
		return idName;
	}

	public void setIdName(String idName) {
		this.idName = idName;
	}

	public CardRarity getRarity() {
		return rarity;
	}

	public void setRarity(CardRarity rarity) {
		this.rarity = rarity;
	}

	public CardType getType() {
		return type;
	}

	public void setType(CardType type) {
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

}
