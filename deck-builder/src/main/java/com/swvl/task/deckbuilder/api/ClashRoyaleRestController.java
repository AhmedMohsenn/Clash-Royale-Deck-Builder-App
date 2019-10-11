package com.swvl.task.deckbuilder.api;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swvl.task.deckbuilder.businessobjects.CardBO;
import com.swvl.task.deckbuilder.services.CardService;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class ClashRoyaleRestController {

	@Autowired
	private CardService cardService;

	@GetMapping("/cards")
	public ResponseEntity<List<CardBO>> cards() {
		return ResponseEntity.ok().body(cardService.getAllCards());
	}

	@GetMapping("/cards/{cardId}")
	public ResponseEntity<CardBO> card(@PathVariable String cardId) throws Exception {
		Optional<CardBO> card = cardService.findByClashIdOrIdName(cardId);

		if (!card.isPresent())
			return ResponseEntity.notFound().build();

		return ResponseEntity.ok().body(card.get());
	}

}