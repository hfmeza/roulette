package com.masivian.roulette.service;

import com.masivian.roulette.data.Bet;
import com.masivian.roulette.exceptions.FailedBetException;
import com.masivian.roulette.data.Roulette;
import com.masivian.roulette.data.RouletteRepository;
import com.masivian.roulette.exceptions.RouletteException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.*;

@Slf4j
public class RouletteService {

    @Autowired
    RouletteRepository repository;

    @Value("${minimumBettableNumber}")
    private int minimumBettableNumber;
    @Value("${maximumBettableNumber}")
    private int maximumBettableNumber;
    @Value("${allowedBettableColors}")
    private String allowedBettableColors;
    @Value("${maximumBetAmount}")
    private int maximumBetAmount;
    @Value("${numericBetMultiplier}")
    private double numericBetMultiplier;
    @Value("${colorBetMultiplier}")
    private double colorBetMultiplier;

    public Long createNewRoulette () throws RouletteException {
        try {
            Long newRouletteId = repository.count() + 1;
            Roulette roulette = new Roulette(newRouletteId);
            repository.save(roulette);
            log.info("New Roulette has been created with id {}", newRouletteId);

            return newRouletteId;
        } catch (Exception e) {
            log.error("Error while creating a new roulette", e);
            throw new RouletteException("Error while creating a new roulette", e);
        }
    }

    public Boolean openRoulette (Long id) throws RouletteException {

        Roulette roulette = getRouletteById(id);
        roulette.openRoulette();
        repository.save(roulette);
        log.info("The roulette with id {} has been opened for bets", id);

        return true;

    }

    public void betOnRoulette (Long rouletteId, Bet bet) throws RouletteException, FailedBetException {
        Roulette roulette = getRouletteById(rouletteId);
        placeBet(roulette, bet);
    }

    public List<Double> closeRoulette (Long rouletteId) throws RouletteException {
        Random rand = new Random(); //instance of random class

        Roulette roulette = getRouletteById(rouletteId);

        int winningNumber = rand.nextInt(maximumBettableNumber);

        List<Double> betResults = new LinkedList<>();
        for (Bet bet : roulette.getPlacedBets()) {
            if (bet.getNumber().isPresent() && bet.getNumber().get() == winningNumber) {
                betResults.add(bet.getValue() * numericBetMultiplier);
            }
            if (bet.getColor().isPresent() && bet.getColor().get().equals(getNumbersColor(winningNumber))) {
                betResults.add(bet.getValue() * colorBetMultiplier);
            }
        }

        return betResults;

    }


    private Roulette getRouletteById(Long rouletteId) throws RouletteException {
        Roulette roulette = null;
        try {
            Optional<Roulette> theRoulette = repository.findById(rouletteId);
            if (theRoulette.isEmpty())
                throw new RouletteException("Error, the roulette with id " + rouletteId + " doesn't exist");
            roulette = theRoulette.get();
        } catch (Exception e) {
            throw new RouletteException("Error loading the roulette", e);
        }
        return roulette;
    }

    private String getNumbersColor (int number) {
        if (number % 2 == 0)
            return "red";
        else
            return "black";
    }

    private void placeBet(Roulette theRoulette, Bet bet) throws FailedBetException {
        if (theRoulette.getIsOpen()) {
            Bet cleanBet = validateAndCleanBet(bet);
            theRoulette.placeBet(cleanBet);
            repository.save(theRoulette);
        } else {
            log.error("The roulette with id " + theRoulette.getId() + " is not open for bets");
            throw new FailedBetException("The roulette with id " + theRoulette.getId() + "is not open for bets");
        }
    }

    private Bet validateAndCleanBet(Bet bet) throws FailedBetException {
        if (bet.getNumber().isPresent() && bet.getColor().isPresent()) {
            throw new FailedBetException("A bet needs to have either a number or a color");
        }
        if (bet.getNumber().isEmpty() && bet.getColor().isEmpty()) {
            throw new FailedBetException("A bet cannot be placed on a number and a color at the same time");
        }
        if (bet.getValue() < 0 || bet.getValue() > maximumBetAmount) {
            throw new FailedBetException("The value is not valid, either < 0 or > " + maximumBetAmount);
        }

        if (bet.getNumber().isPresent()) {
            int theNumber = bet.getNumber().get();
            if (theNumber < minimumBettableNumber || theNumber > maximumBettableNumber) {
                throw new FailedBetException("The number is not in the allowed range (" + minimumBettableNumber + " - " + maximumBettableNumber + ")");
            }
            return new Bet(theNumber, bet.getValue());
        }
        if (bet.getColor().isPresent()) {
            String theColor = bet.getColor().get().toLowerCase(Locale.ROOT);
            if (!allowedBettableColors.toLowerCase(Locale.ROOT).contains(theColor)) {
                throw new FailedBetException("The color is not valid ( " + allowedBettableColors + ")");
            }
            return new Bet(theColor, bet.getValue());
        }

        return bet;
    }


}
