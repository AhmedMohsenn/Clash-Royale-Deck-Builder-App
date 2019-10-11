package com.swvl.task.deckbuilder.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swvl.task.deckbuilder.entities.Card;

public interface CardRepository extends JpaRepository<Card, Integer> {

	public Optional<Card> findByClashIdOrIdName(String clashId, String idName);
	
}
