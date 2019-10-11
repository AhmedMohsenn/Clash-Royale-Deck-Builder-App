package com.swvl.task.deckbuilder.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swvl.task.deckbuilder.entities.CardType;

public interface CardTypeRepository extends JpaRepository<CardType, Integer> {

}
