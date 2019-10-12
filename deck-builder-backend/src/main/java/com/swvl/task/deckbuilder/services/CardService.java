package com.swvl.task.deckbuilder.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.swvl.task.deckbuilder.businessobjects.CardBO;
import com.swvl.task.deckbuilder.entities.Card;
import com.swvl.task.deckbuilder.mappers.CardMapper;
import com.swvl.task.deckbuilder.repositories.CardRepository;

@Service
public class CardService {

	@Autowired
	CardRepository cardRepository;

	public List<CardBO> getAllCards() {
		return CardMapper.toBOs(cardRepository.findAll());
	}

	public Optional<CardBO> findByClashIdOrIdName(String cardId) {
		Optional<Card> card = cardRepository.findByClashIdOrIdName(cardId, cardId);

		if (card.isPresent())
			return Optional.of(CardMapper.toBO(card.get()));

		return Optional.empty();
	}

}
