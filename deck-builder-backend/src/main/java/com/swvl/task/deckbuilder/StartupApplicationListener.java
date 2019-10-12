package com.swvl.task.deckbuilder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swvl.task.deckbuilder.businessobjects.CardBO;
import com.swvl.task.deckbuilder.entities.Card;
import com.swvl.task.deckbuilder.entities.CardRarity;
import com.swvl.task.deckbuilder.entities.CardType;
import com.swvl.task.deckbuilder.repositories.CardRarityRepository;
import com.swvl.task.deckbuilder.repositories.CardRepository;
import com.swvl.task.deckbuilder.repositories.CardTypeRepository;

@Component
public class StartupApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

	private final ObjectMapper objectMapper = new ObjectMapper();
	private final String BASE_URL = "http://www.clashapi.xyz/api/";

	@Autowired
	private CardRarityRepository cardRarityRepository;

	@Autowired
	private CardTypeRepository cardTypeRepository;
	
	@Autowired
	private CardRepository cardRepository;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		try {
			List<CardBO> cardsTemplates = getAllCards();

			List<CardType> types = cardsTemplates.stream().map(CardBO::getType).distinct().map(x -> new CardType(x))
					.collect(Collectors.toList());
			types = cardTypeRepository.saveAll(types);

			List<CardRarity> rarities = cardsTemplates.stream().map(CardBO::getRarity).distinct().map(x -> new CardRarity(x))
					.collect(Collectors.toList());
			rarities = cardRarityRepository.saveAll(rarities);

			List<Card> cards = new ArrayList<>();
			for (CardBO ct : cardsTemplates) {
				Card card = new Card(ct.get_id(), ct.getIdName(), ct.getName(), ct.getDescription(), ct.getElixirCost());
				card.setRarity(rarities.stream().filter(x -> x.getName().equals(ct.getRarity())).findFirst().get());
				card.setType(types.stream().filter(x -> x.getName().equals(ct.getType())).findFirst().get());
				
				cards.add(card);
			}
			
			cards = cardRepository.saveAll(cards);

		} catch (Exception e) {
			// TODO ...
			e.printStackTrace();
		}
	}

	private List<CardBO> getAllCards() throws Exception {
		String allCardUrl = BASE_URL + "cards/";

		URL obj = new URL(allCardUrl);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

		String inputLine;
		StringBuilder response = new StringBuilder();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}

		in.close();
		List<CardBO> cards = Arrays.asList(objectMapper.readValue(response.toString(), CardBO[].class));
		return cards;
	}

}