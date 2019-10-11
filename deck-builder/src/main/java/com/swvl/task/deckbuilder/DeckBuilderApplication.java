package com.swvl.task.deckbuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.swvl.task.deckbuilder.entities.CardType;
import com.swvl.task.deckbuilder.repositories.CardTypeRepository;

@SpringBootApplication
public class DeckBuilderApplication {

	public static void main(String[] args) {
		SpringApplication.run(DeckBuilderApplication.class, args);
	}

}
