package com.masivian.roulette.rest;

import com.masivian.roulette.data.Bet;
import com.masivian.roulette.data.Roulette;
import com.masivian.roulette.exceptions.FailedBetException;
import com.masivian.roulette.exceptions.RouletteException;
import com.masivian.roulette.service.RouletteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    @Autowired
    RouletteService service;

    @PostMapping("/roulette/{id}/bet")
    ResponseEntity<String> betRoulette(@PathVariable Long id,
                                       @RequestParam(value = "number", required = false) Integer number,
                                       @RequestParam(value = "color",required = false) String color,
                                       @RequestParam("value") Integer value) {
        try {
            Bet bet = new Bet(number, color, value);

            service.betOnRoulette(id, bet);
            return new ResponseEntity<>(String.valueOf("OK"), HttpStatus.OK);
        } catch (RouletteException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (FailedBetException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/roulette/{id}/open")
    ResponseEntity<String> openRoulette(@PathVariable Long id) {
        try {
            boolean result = service.openRoulette(id);
            return new ResponseEntity<>(String.valueOf(result), HttpStatus.OK);
        } catch (RouletteException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/roulette")
    ResponseEntity<String> getRoulettes() {
            List<Roulette> results = service.getRoulettes();
            return new ResponseEntity<>(results.stream().map(Object::toString).collect(Collectors.joining(",")),
                    HttpStatus.OK);

    }

    @PostMapping("/roulette/{id}/close")
    ResponseEntity<String> closeRoulette(@PathVariable Long id) {
        try {
            List<Double> results = service.closeRoulette(id);
            return new ResponseEntity<>(results.stream().map(Object::toString).collect(Collectors.joining(",")),
                    HttpStatus.OK);
        } catch (RouletteException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/roulette")
    ResponseEntity<String> newRoulette() {
        try {
            Long id = service.createNewRoulette();
            return new ResponseEntity<>(String.valueOf(id), HttpStatus.OK);
        } catch (RouletteException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
