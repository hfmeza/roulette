package com.masivian.roulette.data;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

@RedisHash("Roulette")
public class Roulette implements Serializable {

    @Getter
    @Setter
    private Long id;
    @Getter
    private Boolean isOpen;

    @Getter
    private List<Bet> placedBets;

    public Roulette (Long id) {
        this.id = id;
        isOpen = false;
        placedBets = new LinkedList<>();
    }

    public void openRoulette () {
        isOpen = true;
        placedBets = new LinkedList<>();
    }

    public void closeRoulette () {
        isOpen = false;
    }

    public void placeBet (Bet bet) {
        this.placedBets.add(bet);
    }

    public String toString () {
        return "Roulette id " + id + ", status " + (isOpen ? "open" : "closed");
    }

}



