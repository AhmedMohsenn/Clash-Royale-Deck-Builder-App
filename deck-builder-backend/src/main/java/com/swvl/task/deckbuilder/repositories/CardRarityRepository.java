package com.swvl.task.deckbuilder.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swvl.task.deckbuilder.entities.CardRarity;

public interface CardRarityRepository extends JpaRepository<CardRarity, Integer> {

}
