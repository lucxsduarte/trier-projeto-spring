package br.com.trier.projetospring.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.projetospring.BaseTests;
import br.com.trier.projetospring.domain.Country;
import br.com.trier.projetospring.services.exceptions.IntegrityViolation;
import br.com.trier.projetospring.services.exceptions.ObjectNotFound;
import jakarta.transaction.Transactional;

@Transactional
public class CountryServiceTest extends BaseTests{

	@Autowired
	CountryService countryService;
	
	@Test
	@DisplayName("Teste cadastrar país")
	void insertTest() {
		countryService.save(new Country(null, "BRASIL"));
		var newChampionship = countryService.findById(1);
		assertEquals("BRASIL", newChampionship.getName());
	}
	
	@Test
	@DisplayName("Teste cadastrar país com nome já cadastrado")
	@Sql({"classpath:/resources/sqls/country.sql"})
	void insertErrorTest() {
		var exception = assertThrows(IntegrityViolation.class, () -> countryService.save(new Country(null, "BRASIL")));
		assertEquals("País já cadastrado: BRASIL", exception.getMessage());	
	}
	
	@Test
	@DisplayName("Teste atualizar país")
	@Sql({"classpath:/resources/sqls/country.sql"})
	void updateTest() {
		countryService.update(new Country(1, "BOLÍVIA"));
		var newChampionship = countryService.findById(1);
		assertEquals("BOLÍVIA", newChampionship.getName());
	}
	
	@Test
	@DisplayName("Teste atualizar país com nome já cadastrado")
	@Sql({"classpath:/resources/sqls/country.sql"})
	void updateErrorTest() {
		var exception = assertThrows(IntegrityViolation.class, () -> countryService.update(new Country(1, "ARGENTINA")));
		assertEquals("País já cadastrado: ARGENTINA", exception.getMessage());	
	}
	
	@Test
	@DisplayName("Teste deletar país")
	@Sql({"classpath:/resources/sqls/country.sql"})
	void deleteTest() {
		countryService.delete(1);
		var list = countryService.listAll();
		assertEquals(4, list.size());
	}
	
	@Test
	@DisplayName("Teste deletar país inexistente")
	void deleteErrorTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> countryService.delete(1));
		assertEquals("País 1 não encontrado", exception.getMessage());	
	}
	
	@Test
	@DisplayName("Teste listar todos países")
	@Sql({"classpath:/resources/sqls/country.sql"})
	void listAllTest() {
		var list = countryService.listAll();
		assertEquals(5, list.size());
	}
	
	@Test
	@DisplayName("Teste listar todos países vazio")
	void listAllErrorTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> countryService.listAll());
		assertEquals("Nenhum país cadastrado", exception.getMessage());	
	}
	
	@Test
	@DisplayName("Teste país por ID")
	@Sql({"classpath:/resources/sqls/country.sql"})
	void findByIdTest() {
		var country = countryService.findById(2);
		assertEquals("ITÁLIA", country.getName());
	}
	
	@Test
	@DisplayName("Teste país por ID inexistente")
	@Sql({"classpath:/resources/sqls/country.sql"})
	void findByIdErrorTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> countryService.findById(10));
		assertEquals("País 10 não encontrado", exception.getMessage());	
	}
	
	@Test
	@DisplayName("Teste país por nome")
	@Sql({"classpath:/resources/sqls/country.sql"})
	void findByNameTest() {
		var list = countryService.findByNameIgnoreCase("brasil");
		assertEquals("BRASIL", list.get(0).getName());
	}
	
	@Test
	@DisplayName("Teste país por nome inexistente")
	@Sql({"classpath:/resources/sqls/country.sql"})
	void findByIdNameErrorTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> countryService.findByNameIgnoreCase("Gana"));
		assertEquals("Nenhum país cadastrado se chama: Gana", exception.getMessage());	
	}
	
	@Test
	@DisplayName("Teste país por nome contem")
	@Sql({"classpath:/resources/sqls/country.sql"})
	void findByNameContainsTest() {
		var list = countryService.findByNameContainsIgnoreCase("a");
		assertEquals(5, list.size());
	}
	
	@Test
	@DisplayName("Teste país por nome contem inexistente")
	@Sql({"classpath:/resources/sqls/country.sql"})
	void findByIdNameContainsErrorTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> countryService.findByNameContainsIgnoreCase("z"));
		assertEquals("Nenhum país cadastrado contem: z", exception.getMessage());	
	}
	
	@Test
	@DisplayName("Teste país por nome começa com")
	@Sql({"classpath:/resources/sqls/country.sql"})
	void findByNameStartsWithTest() {
		var list = countryService.findByNameStartsWithIgnoreCase("a");
		assertEquals(1, list.size());
	}
	
	@Test
	@DisplayName("Teste país por nome começa com vazio")
	@Sql({"classpath:/resources/sqls/country.sql"})
	void findByNameStartsWithErrorTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> countryService.findByNameStartsWithIgnoreCase("z"));
		assertEquals("Nenhum país cadastrado começa com: z", exception.getMessage());	
	}
}
