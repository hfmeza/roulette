package com.masivian.roulette.data;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.Optional;

@Getter
@Setter
public class Bet implements Serializable {

    private Optional<Integer> number;
    private Optional<String> color;
    private Integer value;

    public Bet (Integer number, Integer value) {
        this.number = Optional.of(number);
        this.color = Optional.empty();
        this.value = value;
    }

    public Bet (String color, Integer value) {
        this.number = Optional.empty();
        this.color = Optional.of(color);
        this.value = value;
    }


}



