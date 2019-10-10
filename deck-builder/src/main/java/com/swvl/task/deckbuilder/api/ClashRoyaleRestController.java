package com.swvl.task.deckbuilder.api;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swvl.task.deckbuilder.entities.Card;

@RestController
@RequestMapping("/api")
public class ClashRoyaleRestController {

	private final ObjectMapper objectMapper = new ObjectMapper();
	private final String BASE_URL = "http://www.clashapi.xyz/api/";

	@GetMapping("/cards")
	public ResponseEntity<List<Card>> cards() throws Exception {
		String allCardUrl = BASE_URL + "cards/";

		URL obj = new URL(allCardUrl);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}

		in.close();
		List<Card> cards = Arrays.asList(objectMapper.readValue(response.toString(), Card[].class));

		return ResponseEntity.ok().body(cards);
	}

	@GetMapping("/cards/{id}")
	public ResponseEntity<Card> card(@PathVariable String id) throws Exception {
		String singleCardUrl = BASE_URL + "cards/" + id;

		URL obj = new URL(singleCardUrl);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}

		in.close();
		Card card = objectMapper.readValue(response.toString(), Card.class);

		return ResponseEntity.ok().body(card);
	}

}