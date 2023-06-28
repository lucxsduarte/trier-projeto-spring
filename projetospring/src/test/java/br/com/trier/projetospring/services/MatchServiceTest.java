package br.com.trier.projetospring.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.projetospring.BaseTests;
import br.com.trier.projetospring.domain.Match;
import br.com.trier.projetospring.services.exceptions.IntegrityViolation;
import br.com.trier.projetospring.services.exceptions.ObjectNotFound;
import jakarta.transaction.Transactional;

@Transactional
public class MatchServiceTest extends BaseTests{

	@Autowired
	MatchService matchService;
	@Autowired
	CountryService countryService;
	@Autowired
	ChampionshipService championshipService;
	
	@Test
	@DisplayName("Teste cadastrar partida")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/championship.sql"})
	void insertTest() {
		matchService.save(new Match(null, ZonedDateTime.of(2014,07,20,10,30,0,0, ZoneId.systemDefault()), countryService.findById(1), championshipService.findById(1)));
		var newMatch = matchService.findById(1);
		assertEquals("BRASIL", newMatch.getCountry().getName());
	}
	
	@Test
	@DisplayName("Teste cadastrar partida com data inválida")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/championship.sql"})
	void insertDateErrorTest() {
		
		var exception = assertThrows(IntegrityViolation.class, () -> matchService.save(new Match(null, ZonedDateTime.of(2028,07,20,10,30,0,0, ZoneId.systemDefault()), countryService.findById(1), championshipService.findById(1))));
		assertEquals("Ano da partida não pode ser diferente de um campeonato cadastrado", exception.getMessage());	
	}
	
	@Test
	@DisplayName("Teste cadastrar partida com campeonato nulo")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/championship.sql"})
	void insertChampionshipErrorTest() {
		
		var exception = assertThrows(IntegrityViolation.class, () -> 
		matchService.save(new Match(null, ZonedDateTime.of(2014,07,20,10,30,0,0, ZoneId.systemDefault()), countryService.findById(1), null)));
		assertEquals("Campeonato não pode ser nulo", exception.getMessage());	
	}
	
	@Test
	@DisplayName("Teste cadastrar partida com data nula")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/championship.sql"})
	void insertDateNullErrorTest() {
		var exception = assertThrows(IntegrityViolation.class, () -> matchService.save(new Match(null, null, countryService.findById(1), championshipService.findById(1))));
		assertEquals("Data inválida", exception.getMessage());	
	}
	
	@Test
	@DisplayName("Teste atualizar partida")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/championship.sql"})
	@Sql({"classpath:/resources/sqls/match.sql"})
	void updateTest() {
		matchService.update(new Match(2, ZonedDateTime.of(2014,07,20,10,30,0,0, ZoneId.systemDefault()), countryService.findById(1), championshipService.findById(1)));
		var newMatch = matchService.findById(2);
		assertEquals("BRASIL", newMatch.getCountry().getName());
	}
	
	@Test
	@DisplayName("Teste atualizar partida partida inexistente")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/championship.sql"})
	@Sql({"classpath:/resources/sqls/match.sql"})
	void updateErrorTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> matchService.update(new Match(4, ZonedDateTime.of(2014,07,20,10,30,0,0, ZoneId.systemDefault()), countryService.findById(1), championshipService.findById(1))));
		assertEquals("Partida 4 não encontrada", exception.getMessage());	
	}
	
	@Test
	@DisplayName("Teste deletar partidas")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/championship.sql"})
	@Sql({"classpath:/resources/sqls/match.sql"})
	void deleteTest() {
		matchService.delete(1);
		var list = matchService.listAll();
		assertEquals(1, list.size());
	}
	
	@Test
	@DisplayName("Teste deletar partidas inexistente")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/championship.sql"})
	void deleteErrorTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> matchService.delete(1));
		assertEquals("Partida 1 não encontrada", exception.getMessage());	
	}
	
	@Test
	@DisplayName("Teste listar todas partidas")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/championship.sql"})
	@Sql({"classpath:/resources/sqls/match.sql"})
	void listAllTest() {
		var list = matchService.listAll();
		assertEquals(2, list.size());
	}
	
	@Test
	@DisplayName("Teste listar todas partidas vazio")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/championship.sql"})
	void listAllErrorTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> matchService.listAll());
		assertEquals("Nenhuma partida cadastrada", exception.getMessage());	
	}
	
	@Test
	@DisplayName("Teste buscar partida por ID")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/championship.sql"})
	@Sql({"classpath:/resources/sqls/match.sql"})
	void findByIdTest() {
		var match = matchService.findById(2);
		assertEquals("BRASIL", match.getCountry().getName());
	}
	
	@Test
	@DisplayName("Teste buscar partida por ID inexistente")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/championship.sql"})
	@Sql({"classpath:/resources/sqls/match.sql"})
	void findByIdErrorTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> matchService.findById(4));
		assertEquals("Partida 4 não encontrada", exception.getMessage());	
	}
	
	@Test
	@DisplayName("Teste partidas por data")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/championship.sql"})
	@Sql({"classpath:/resources/sqls/match.sql"})
	void findByDateTest() {
		var list = matchService.findByDate(ZonedDateTime.of(2014,07,20,10,30,0,0, ZoneId.systemDefault()));
		assertEquals(1, list.size());
	}
	
	@Test
	@DisplayName("Teste partidas por data inexistente")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/championship.sql"})
	@Sql({"classpath:/resources/sqls/match.sql"})
	void findByDateErrorTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> matchService.findByDate(ZonedDateTime.of(2015,07,20,10,30,0,0, ZoneId.systemDefault())));
		assertEquals("Nenhuma partida cadastrada na data: 2015-07-20T10:30-03:00[America/Sao_Paulo]", exception.getMessage());	
	}
	
	@Test
	@DisplayName("Teste partidas entre datas")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/championship.sql"})
	@Sql({"classpath:/resources/sqls/match.sql"})
	void findByDateBetweenTest() {
		var list = matchService.findByDateBetween(ZonedDateTime.of(2013,07,20,10,30,0,0, ZoneId.systemDefault()), ZonedDateTime.of(2016,07,20,10,30,0,0, ZoneId.systemDefault()));
		assertEquals(1, list.size());
	}
	
	@Test
	@DisplayName("Teste partidas inexistente entre datas")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/championship.sql"})
	@Sql({"classpath:/resources/sqls/match.sql"})
	void findByDateBetweenErrorTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> matchService.findByDateBetween(ZonedDateTime.of(2015,07,20,10,30,0,0, ZoneId.systemDefault()), ZonedDateTime.of(2016,07,20,10,30,0,0, ZoneId.systemDefault())));
		assertEquals("Nenhuma partida cadastrada entre a data 2015-07-20T10:30-03:00[America/Sao_Paulo] e 2016-07-20T10:30-03:00[America/Sao_Paulo]", exception.getMessage());	
	}
	
	@Test
	@DisplayName("Teste partidas por campeonato")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/championship.sql"})
	@Sql({"classpath:/resources/sqls/match.sql"})
	void findByChampionshipTest() {
		var list = matchService.findByChampionship(championshipService.findById(1));
		assertEquals(1, list.size());
	}
	
	@Test
	@DisplayName("Teste buscar partida por campeonato inexistente")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/championship.sql"})
	@Sql({"classpath:/resources/sqls/match.sql"})
	void findByChampionshipErrorTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> matchService.findByChampionship(championshipService.findById(3)));
		assertEquals("Nenhuma partida cadastrada no campeonato: 3", exception.getMessage());	
	}
	
	@Test
	@DisplayName("Teste partidas por país")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/championship.sql"})
	@Sql({"classpath:/resources/sqls/match.sql"})
	void findByYearTest() {
		var list = matchService.findByCountry(countryService.findById(1));
		assertEquals(2, list.size());
	}
	
	@Test
	@DisplayName("Teste buscar partida por país inexistente")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/championship.sql"})
	@Sql({"classpath:/resources/sqls/match.sql"})
	void findByYearErrorTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> matchService.findByCountry(countryService.findById(3)));
		assertEquals("Nenhuma partida cadastrada no país: 3", exception.getMessage());	
	}
	
	@Test
	@DisplayName("Teste partidas por campeonato e país")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/championship.sql"})
	@Sql({"classpath:/resources/sqls/match.sql"})
	void findByChampionshipAndCountryTest() {
		var list = matchService.findByChampionshipAndCountry(championshipService.findById(1),countryService.findById(1));
		assertEquals(1, list.size());
	}
	
	@Test
	@DisplayName("Teste partidas por campeonato e país inexistente")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/championship.sql"})
	@Sql({"classpath:/resources/sqls/match.sql"})
	void findByChampionshipAndCountryErrorTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> matchService.findByChampionshipAndCountry(championshipService.findById(2),countryService.findById(2)));
		assertEquals("Nenhuma partida cadastrada no campeonato 2 e no país 2", exception.getMessage());	
	}
	
	@Test
	@DisplayName("Teste partidas por data e país")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/championship.sql"})
	@Sql({"classpath:/resources/sqls/match.sql"})
	void findByDateAndCountryTest() {
		var list = matchService.findByDateAndCountry(ZonedDateTime.of(2014,07,20,10,30,0,0, ZoneId.systemDefault()), countryService.findById(1));
		assertEquals(1, list.size());
	}
	
	@Test
	@DisplayName("Teste partidas por data e país inexistente")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/championship.sql"})
	@Sql({"classpath:/resources/sqls/match.sql"})
	void findByDateAndCountryErrorTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> matchService.findByDateAndCountry(ZonedDateTime.of(2015,07,20,10,30,0,0, ZoneId.systemDefault()), countryService.findById(1)));
		assertEquals("Nenhuma partida cadastrada na data 2015-07-20T10:30-03:00[America/Sao_Paulo] e no país 1", exception.getMessage());	
	}
	
	@Test
	@DisplayName("Teste partidas por data e campeonato")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/championship.sql"})
	@Sql({"classpath:/resources/sqls/match.sql"})
	void findByDateAndChampionshipTest() {
		var list = matchService.findByDateAndChampionship(ZonedDateTime.of(2014,07,20,10,30,0,0, ZoneId.systemDefault()), championshipService.findById(1));
		assertEquals(1, list.size());
	}
	
	@Test
	@DisplayName("Teste partidas por data e campeonato inexistente")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/championship.sql"})
	@Sql({"classpath:/resources/sqls/match.sql"})
	void findByDateAndChampionshipErrorTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> matchService.findByDateAndChampionship(ZonedDateTime.of(2015,07,20,10,30,0,0, ZoneId.systemDefault()), championshipService.findById(1)));
		assertEquals("Nenhuma partida cadastrada na data 2015-07-20T10:30-03:00[America/Sao_Paulo] e no campeonato 1", exception.getMessage());	
	}
	
}
