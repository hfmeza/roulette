package com.masivian.roulette;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.masivian.roulette.data.Roulette;
import com.masivian.roulette.data.RouletteRepository;
import com.masivian.roulette.exceptions.RouletteException;
import com.masivian.roulette.service.RouletteService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

@SpringBootTest
class RouletteApplicationTests {

	@MockBean
	private RouletteRepository repository;

	@Autowired
	RouletteService service;

	@Test
	public void testRouletteCreation () throws RouletteException {
		service.createNewRoulette();
		Mockito.verify(repository).save(Mockito.any(Roulette.class));
	}

	@Test
	public void testRouletteList () throws RouletteException {
		Mockito.when(repository.findAll()).thenReturn(List.of(new Roulette(1l), new Roulette(2l)));

		List<Roulette> roulettes = service.getRoulettes();
		Mockito.verify(repository).findAll();
		assert(roulettes.size() == 2);
	}

}
