package com.swvl.task.deckbuilder.mappers;

import java.util.List;
import java.util.stream.Collectors;

import com.swvl.task.deckbuilder.businessobjects.CardBO;
import com.swvl.task.deckbuilder.entities.Card;

public class CardMapper {
	private CardMapper() {}
	
	public static CardBO toBO(Card card) {
		CardBO bo = new CardBO();
		bo.set_id(card.getClashId());
		bo.setType(card.getType().getName());
		bo.setRarity(card.getRarity().getName());
		bo.setName(card.getName());
		bo.setIdName(card.getIdName());
		bo.setElixirCost(card.getElixirCost());
		bo.setDescription(card.getDescription());
		
		return bo;
	}
	
	public static List <CardBO> toBOs(List <Card> cards){
		return cards.stream().map(CardMapper::toBO).collect(Collectors.toList());
	}

}
