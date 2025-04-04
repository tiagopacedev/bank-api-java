package me.dio.controller;

import me.dio.domain.model.Card;
import me.dio.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cards")
public class CardController {

    @Autowired
    private CardService cardService;

    @GetMapping("/{cardId}/limit")
    public ResponseEntity<String> getCardLimit(@PathVariable Long cardId) {
        Card card = cardService.getCardById(cardId);
        return ResponseEntity.ok("Available Limit: " + card.getAvailableLimit());
    }
}