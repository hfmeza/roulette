package com.masivian.roulette.data;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.Optional;

@Getter
@Setter
public class Bet implements Serializable {

    private Integer number;
    private String color;
    private Integer value;

    public Bet () {

    }

    public Bet (Integer number, String color, Integer value) {
        this.number = number;
        this.color = color;
        this.value = value;
    }

    public Bet (Integer number, Integer value) {
        this.number = number;
        this.color = null;
        this.value = value;
    }

    public Bet (String color, Integer value) {
        this.number = null;
        this.color = color;
        this.value = value;
    }

    @Override
    public String toString() {
        return "Bet{" +
                "number=" + number +
                ", color=" + color +
                ", value=" + value +
                '}';
    }
}



