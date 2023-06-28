package br.com.trier.projetospring.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.projetospring.BaseTests;
import br.com.trier.projetospring.domain.Championship;
import br.com.trier.projetospring.services.exceptions.IntegrityViolation;
import br.com.trier.projetospring.services.exceptions.ObjectNotFound;
import jakarta.transaction.Transactional;

@Transactional
public class ChampionshipServiceTest extends BaseTests{

	@Autowired
	ChampionshipService championshipService;
	
	@Test
	@DisplayName("Teste cadastrar campeonato")
	void insertTest() {
		championshipService.save(new Championship(null, 2026, "COPA DO MUNDO"));
		var newChampionship = championshipService.findById(1);
		assertEquals("COPA DO MUNDO", newChampionship.getName());
	}
	
	@Test
	@DisplayName("Teste cadastrar campeonato ano inválido")
	void insertErrorMaxTest() {
		var exception = assertThrows(IntegrityViolation.class, () -> championshipService.save(new Championship(null, 2030, "Copa do Mundo")));
		assertEquals("Ano 2030 inválido para cadastro", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste cadastrar campeonato ano inválido")
	void insertErrorMinTest() {
		var exception = assertThrows(IntegrityViolation.class, () -> championshipService.save(new Championship(null, 1998, "Copa do Mundo")));
		assertEquals("Ano 1998 inválido para cadastro", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste atualizar campeonato")
	@Sql({"classpath:/resources/sqls/championship.sql"})
	void updateTest() {
		championshipService.update(new Championship(1, 2026, "COPA DO MUNDO"));
		var newChampionship = championshipService.findById(1);
		assertEquals("COPA DO MUNDO", newChampionship.getName());
	}
	
	@Test
	@DisplayName("Teste atualizar campeonato ano inválido")
	@Sql({"classpath:/resources/sqls/championship.sql"})
	void updateErrorTest() {
		var exception = assertThrows(IntegrityViolation.class, () -> championshipService.update(new Championship(1, 2030, "Copa do Mundo")));
		assertEquals("Ano 2030 inválido para cadastro", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste deletar campeonato")
	@Sql({"classpath:/resources/sqls/championship.sql"})
	void deleteTest() {
		championshipService.delete(1);
 		var list = championshipService.listAll();
		assertEquals(2, list.size());
	}
	
	@Test
	@DisplayName("Teste deletar campeonato inexistente")
	void deleteErrorTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> championshipService.delete(1));
		assertEquals("Campeonato 1 não encontrado", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste listar todos campeonatos")
	@Sql({"classpath:/resources/sqls/championship.sql"})
	void listAllTest() {
		var list = championshipService.listAll();
		assertEquals(3, list.size());
	}
	
	@Test
	@DisplayName("Teste listar todos vazio")
	void listAllErrorTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> championshipService.listAll());
		assertEquals("Nenhum campeonato cadastrado!", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar campeonato por ID")
	@Sql({"classpath:/resources/sqls/championship.sql"})
	void findByIdTest() {
		var newChampionship = championshipService.findById(1);
		assertEquals(2014, newChampionship.getYear());
	}
	
	@Test
	@DisplayName("Teste buscar campeonato por ID inexistente")
	@Sql({"classpath:/resources/sqls/championship.sql"})
	void findByIdErrorTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> championshipService.findById(10));
		assertEquals("Campeonato 10 não encontrado", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar campeonato por nome")
	@Sql({"classpath:/resources/sqls/championship.sql"})
	void findByNameTest() {
		var list = championshipService.findByNameIgnoreCase("COPA DO MUNDO");
		assertEquals(3, list.size());
	}
	
	@Test
	@DisplayName("Teste buscar campeonato por nome inexistente")
	@Sql({"classpath:/resources/sqls/championship.sql"})
	void findByNameErrorTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> championshipService.findByNameIgnoreCase("COPA"));
		assertEquals("Nenhum campeonato cadastrado com o nome: COPA!", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar campeonato por conter nome")
	@Sql({"classpath:/resources/sqls/championship.sql"})
	void findByNameContainsTest() {
		var list = championshipService.findByNameContainsIgnoreCase("MUNDO");
		assertEquals(3, list.size());
	}
	
	@Test
	@DisplayName("Teste buscar campeonato por conter nome inexistente")
	@Sql({"classpath:/resources/sqls/championship.sql"})
	void findByNameContainsErrorTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> championshipService.findByNameContainsIgnoreCase("AMÉRICA"));
		assertEquals("Nenhum campeonato cadastrado contem o nome: AMÉRICA!", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar campeonato por nome que começa com")
	@Sql({"classpath:/resources/sqls/championship.sql"})
	void findByNameStartsWithTest() {
		var list = championshipService.findByNameStartsWithIgnoreCase("CO");
		assertEquals(3, list.size());
	}
	
	@Test
	@DisplayName("Teste buscar campeonato por conter nome inexistente")
	@Sql({"classpath:/resources/sqls/championship.sql"})
	void findByNameStartsWithErrorTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> championshipService.findByNameStartsWithIgnoreCase("A"));
		assertEquals("Nenhum campeonato cadastrado começa com o nome: A!", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar campeonato por ano")
	@Sql({"classpath:/resources/sqls/championship.sql"})
	void findByYearTest() {
		var list = championshipService.findByYear(2014);
		assertEquals(1, list.size());
	}
	
	@Test
	@DisplayName("Teste buscar campeonato por ano inexistente")
	@Sql({"classpath:/resources/sqls/championship.sql"})
	void findByYearErrorTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> championshipService.findByYear(2015));
		assertEquals("Nenhum campeonato cadastrado no ano de: 2015!", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar campeonato por ano e nome")
	@Sql({"classpath:/resources/sqls/championship.sql"})
	void findByYearAndNameTest() {
		var list = championshipService.findByYearAndName(2014, "COPA DO MUNDO");
		assertEquals(1, list.size());
	}
	
	@Test
	@DisplayName("Teste buscar campeonato por ano e nome inexistente")
	@Sql({"classpath:/resources/sqls/championship.sql"})
	void findByYearAndNameErrorTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> championshipService.findByYearAndName(2015, "COPA DO MUNDO"));
		assertEquals("Nenhum campeonato cadastrado no ano de: 2015 com o nome COPA DO MUNDO!", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar campeonato entre dois anos")
	@Sql({"classpath:/resources/sqls/championship.sql"})
	void findByYearBetweenTest() {
		var list = championshipService.findByYearBetween(2010, 2020);
		assertEquals(2, list.size());
	}
	
	@Test
	@DisplayName("Teste buscar campeonato entre dois anos vazio")
	@Sql({"classpath:/resources/sqls/championship.sql"})
	void findByYearBetweenErrorTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> championshipService.findByYearBetween(2025, 2036));
		assertEquals("Nenhum campeonato cadastrado entre os anos de: 2025 e 2036!", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar campeonato por ano maior ou igual que")
	@Sql({"classpath:/resources/sqls/championship.sql"})
	void findByYearGreaterThanEqualTest() {
		var list = championshipService.findByYearGreaterThanEqual(2017);
		assertEquals(2, list.size());
	}
	
	@Test
	@DisplayName("Teste buscar campeonato por ano maior ou igual vazio")
	@Sql({"classpath:/resources/sqls/championship.sql"})
	void findByYearGreaterThanEqualErrorTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> championshipService.findByYearGreaterThanEqual(2023));
		assertEquals("Nenhum campeonato cadastrado após o ano de: 2023!", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar campeonato por ano maior ou igual que")
	@Sql({"classpath:/resources/sqls/championship.sql"})
	void findByYearLessThanEqualTest() {
		var list = championshipService.findByYearLessThanEqual(2017);
		assertEquals(1, list.size());
	}
	
	@Test
	@DisplayName("Teste buscar campeonato por ano maior ou igual vazio")
	@Sql({"classpath:/resources/sqls/championship.sql"})
	void findByYearLessThanEqualErrorTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> championshipService.findByYearLessThanEqual(2010));
		assertEquals("Nenhum campeonato cadastrado antes do ano de: 2010!", exception.getMessage());
	}
}
