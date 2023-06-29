package br.com.trier.projetospring.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.projetospring.BaseTests;
import br.com.trier.projetospring.domain.Referee;
import br.com.trier.projetospring.services.exceptions.IntegrityViolation;
import br.com.trier.projetospring.services.exceptions.ObjectNotFound;
import jakarta.transaction.Transactional;

@Transactional
public class RefereeServiceTest extends BaseTests{

	@Autowired
	RefereeService refereeService;
	@Autowired
	CountryService countryService;
	
	@Test
	@DisplayName("Teste cadastrar árbitro")
	@Sql({"classpath:/resources/sqls/country.sql"})
	void insertTest() {
		refereeService.save(new Referee(null, "Geovane", 40, countryService.findById(1)));
		var newReferee = refereeService.findById(1);
		assertEquals("BRASIL", newReferee.getCountry().getName());
	}
	
	@Test
	@DisplayName("Teste cadastrar árbitro inválido")
	@Sql({"classpath:/resources/sqls/country.sql"})
	void insertErrorTest() {
		var exception = assertThrows(IntegrityViolation.class, () -> refereeService.save(new Referee(null, "Geovane", 51, countryService.findById(1))));
		assertEquals("O árbitro não pode ter menos que 35 anos ou mais que 50 anos", exception.getMessage());	
	}
	
	@Test
	@DisplayName("Teste atualizar árbitro")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/referee.sql"})
	void updateTest() {
		refereeService.update(new Referee(2, "Geovane", 40, countryService.findById(1)));
		var newReferee = refereeService.findById(1);
		assertEquals("BRASIL", newReferee.getCountry().getName());
	}
	
	@Test
	@DisplayName("Teste atualizar árbitro idade maior inválida")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/referee.sql"})
	void updateErrorAgeGreaterTest() {
		var exception = assertThrows(IntegrityViolation.class, () -> refereeService.update(new Referee(2, "Geovane", 51, countryService.findById(1))));
		assertEquals("O árbitro não pode ter menos que 35 anos ou mais que 50 anos", exception.getMessage());	
	}
	
	@Test
	@DisplayName("Teste atualizar árbitro idade menor inválida")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/referee.sql"})
	void updateErrorAgeLessTest() {
		var exception = assertThrows(IntegrityViolation.class, () -> refereeService.update(new Referee(2, "Geovane", 32, countryService.findById(1))));
		assertEquals("O árbitro não pode ter menos que 35 anos ou mais que 50 anos", exception.getMessage());	
	}
	
	@Test
	@DisplayName("Teste deletar árbitro")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/referee.sql"})
	void deleteTest() {
		refereeService.delete(1);
		var list = refereeService.listAll();
		assertEquals(2, list.size());
	}
	
	@Test
	@DisplayName("Teste deletar árbitro inexistente")
	@Sql({"classpath:/resources/sqls/country.sql"})
	void deleteErrorTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> refereeService.delete(1));
		assertEquals("Árbitro 1 não encontrado", exception.getMessage());	
	}
	
	@Test
	@DisplayName("Teste listar árbitros")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/referee.sql"})
	void listAllTest() {
		var list = refereeService.listAll();
		assertEquals(3, list.size());
	}
	
	@Test
	@DisplayName("Teste listar árbitros vazio")
	@Sql({"classpath:/resources/sqls/country.sql"})
	void listAllErrorTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> refereeService.listAll());
		assertEquals("Nenhum árbitro cadastrado", exception.getMessage());	
	}
	
	@Test
	@DisplayName("Teste buscar árbitro por ID")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/referee.sql"})
	void findByIdTest() {
		var referee = refereeService.findById(1);
		assertEquals("Lucas", referee.getName());
	}
	
	@Test
	@DisplayName("Teste buscar árbitro por ID inexistente")
	@Sql({"classpath:/resources/sqls/country.sql"})
	void findByIdErrorTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> refereeService.findById(1));
		assertEquals("Árbitro 1 não encontrado", exception.getMessage());	
	}
	
	@Test
	@DisplayName("Teste buscar árbitro por nome")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/referee.sql"})
	void findByNameTest() {
		var list = refereeService.findByNameIgnoreCase("Lucas");
		assertEquals("Lucas", list.get(0).getName());
	}
	
	@Test
	@DisplayName("Teste buscar árbitro por nome inexistente")
	@Sql({"classpath:/resources/sqls/country.sql"})
	void findByNameErrorTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> refereeService.findByNameIgnoreCase("Lucas"));
		assertEquals("Nenhum árbitro cadastrado com esse nome: Lucas", exception.getMessage());	
	}
	
	@Test
	@DisplayName("Teste buscar árbitro por nome contém")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/referee.sql"})
	void findByNameContainsTest() {
		var list = refereeService.findByNameContainsIgnoreCase("o");
		assertEquals(2, list.size());
	}
	
	@Test
	@DisplayName("Teste buscar árbitro por nome contém inexistente")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/referee.sql"})
	void findByNameContainsErrorTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> refereeService.findByNameContainsIgnoreCase("z"));
		assertEquals("Nenhum árbitro cadastrado contem z no nome", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar árbitro por nome começa por")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/referee.sql"})
	void findByNameStartsWithTest() {
		var list = refereeService.findByNameStartsWithIgnoreCase("L");
		assertEquals(1, list.size());
	}
	
	@Test
	@DisplayName("Teste buscar árbitro por nome começa por inexistente")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/referee.sql"})
	void findByNameStartsWithErrorTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> refereeService.findByNameStartsWithIgnoreCase("p"));
		assertEquals("Nenhum árbitro cadastrado começa com p no nome", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar árbitro por país")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/referee.sql"})
	void findByCountryTest() {
		var list = refereeService.findByCountry(countryService.findById(1));
		assertEquals(2, list.size());
	}
	
	@Test
	@DisplayName("Teste buscar árbitro por país inexistente")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/referee.sql"})
	void findByCountryErrorTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> refereeService.findByCountry(countryService.findById(2)));
		assertEquals("Nenhum árbitro cadastrado do país: 2", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar árbitro por idade")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/referee.sql"})
	void findByAgeTest() {
		var list = refereeService.findByAge(35);
		assertEquals(1, list.size());
	}
	
	@Test
	@DisplayName("Teste buscar árbitro por idade inexistente")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/referee.sql"})
	void findByAgeErrorTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> refereeService.findByAge(36));
		assertEquals("Nenhum árbitro cadastrado com a idade: 36", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar árbitro por idade e nome")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/referee.sql"})
	void findByAgeAndNameTest() {
		var list = refereeService.findByAgeAndName(35, "Lucas");
		assertEquals(1, list.size());
	}
	
	@Test
	@DisplayName("Teste buscar árbitro por idade e nome inexistente")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/referee.sql"})
	void findByAgeAndNameErrorTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> refereeService.findByAgeAndName(36, "Lucas"));
		assertEquals("Nenhum árbitro cadastrado com a idade 36 e o nome Lucas", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar árbitro por idade e país")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/referee.sql"})
	void findByAgeAndCountryTest() {
		var list = refereeService.findByAgeAndCountry(35, countryService.findById(1));
		assertEquals(1, list.size());
	}
	
	@Test
	@DisplayName("Teste buscar árbitro por idade e país inexistente")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/referee.sql"})
	void findByAgeAndCountryErrorTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> refereeService.findByAgeAndCountry(35, countryService.findById(2)));
		assertEquals("Nenhum árbitro cadastrado com a idade 35 e o país 2", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar árbitro entre duas idades")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/referee.sql"})
	void findByAgeBetweenTest() {
		var list = refereeService.findByAgeBetween(35, 40);
		assertEquals(2, list.size());
	}
	
	@Test
	@DisplayName("Teste buscar árbitro entre duas idades inexistente")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/referee.sql"})
	void findByAgeBetweenErrorTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> refereeService.findByAgeBetween(36, 39));
		assertEquals("Nenhum árbitro cadastrado com a idade entre 36 e 39", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar árbitro por idade maior ou igual")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/referee.sql"})
	void findByAgeGreaterThanEqualTest() {
		var list = refereeService.findByAgeGreaterThanEqual(35);
		assertEquals(3, list.size());
	}
	
	@Test
	@DisplayName("Teste buscar árbitro por idade maior ou igual inexistente")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/referee.sql"})
	void findByAgeGreaterThanEqualErrorTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> refereeService.findByAgeGreaterThanEqual(46));
		assertEquals("Nenhum árbitro cadastrado com a idade maior que 46", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar árbitro por idade menor ou igual")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/referee.sql"})
	void findByAgeLessThanEqualTest() {
		var list = refereeService.findByAgeLessThanEqual(45);
		assertEquals(3, list.size());
	}
	
	@Test
	@DisplayName("Teste buscar árbitro por idade menor ou igual inexistente")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/referee.sql"})
	void findByAgeLessThanEqualErrorTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> refereeService.findByAgeLessThanEqual(34));
		assertEquals("Nenhum árbitro cadastrado com a idade menor que 34", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar árbitro por idade nome e país")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/referee.sql"})
	void findByNameAndCountryTest() {
		var list = refereeService.findByNameAndCountry("Lucas", countryService.findById(1));
		assertEquals(1, list.size());
	}
	
	@Test
	@DisplayName("Teste buscar árbitro por idade nome e país inexistente")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/referee.sql"})
	void findByNameAndCountryErrorTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> refereeService.findByNameAndCountry("Lucas", countryService.findById(2)));
		assertEquals("Nenhum árbitro cadastrado com o nome Lucas no país 2", exception.getMessage());
	}
}
