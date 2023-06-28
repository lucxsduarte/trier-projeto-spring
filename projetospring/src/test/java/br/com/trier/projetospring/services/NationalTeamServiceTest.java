package br.com.trier.projetospring.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.projetospring.BaseTests;
import br.com.trier.projetospring.domain.NationalTeam;
import br.com.trier.projetospring.services.exceptions.IntegrityViolation;
import br.com.trier.projetospring.services.exceptions.ObjectNotFound;
import jakarta.transaction.Transactional;

@Transactional
public class NationalTeamServiceTest extends BaseTests{

	@Autowired
	NationalTeamService nationalTeamService;
	@Autowired
	CountryService countryService;
	
	@Test
	@DisplayName("Teste cadastrar seleção")
	@Sql({"classpath:/resources/sqls/country.sql"})
	void insertTest() {
		nationalTeamService.save(new NationalTeam(null, "Seleção Brasileira", countryService.findById(1)));
		var newChampionship = nationalTeamService.findById(1);
		assertEquals("Seleção Brasileira", newChampionship.getName());
	}
	
	@Test
	@DisplayName("Teste cadastrar seleção com nome já cadastrado")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/nationalteam.sql"})
	void insertErrorTest() {
		var exception = assertThrows(IntegrityViolation.class, () -> nationalTeamService.save(new NationalTeam(null, "Seleção Brasileira", countryService.findById(1))));
		assertEquals("Seleçao já cadastrada: Seleção Brasileira", exception.getMessage());	
	}
	
	@Test
	@DisplayName("Teste atualizar seleção")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/nationalteam.sql"})
	void updateTest() {
		nationalTeamService.update(new NationalTeam(2, "Seleção Egípcia", countryService.findById(1)));
		var newChampionship = nationalTeamService.findById(2);
		assertEquals("Seleção Egípcia", newChampionship.getName());
	}
	
	@Test
	@DisplayName("Teste atualizar seleção com nome já cadastrado")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/nationalteam.sql"})
	void updateErrorTest() {
		var exception = assertThrows(IntegrityViolation.class, () -> nationalTeamService.update(new NationalTeam(2, "Seleção Brasileira", countryService.findById(1))));
		assertEquals("Seleçao já cadastrada: Seleção Brasileira", exception.getMessage());	
	}
	
	@Test
	@DisplayName("Teste deletar seleção")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/nationalteam.sql"})
	void deleteTest() {
		nationalTeamService.delete(1);
		var list = nationalTeamService.listAll();
		assertEquals(3, list.size());
	}
	
	@Test
	@DisplayName("Teste deletar seleção inexistente")
	@Sql({"classpath:/resources/sqls/country.sql"})
	void deleteErrorTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> nationalTeamService.delete(1));
		assertEquals("Seleção 1 não encontrada", exception.getMessage());	
	}
	
	@Test
	@DisplayName("Teste listar todas seleções")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/nationalteam.sql"})
	void listAllTest() {
		var list = countryService.listAll();
		assertEquals(5, list.size());
	}
	
	@Test
	@DisplayName("Teste listar todas seleções vazio")
	@Sql({"classpath:/resources/sqls/country.sql"})
	void listAllErrorTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> nationalTeamService.listAll());
		assertEquals("Nenhuma seleção cadastrada", exception.getMessage());	
	}
	
	@Test
	@DisplayName("Teste seleção por ID")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/nationalteam.sql"})
	void findByIdTest() {
		var nationalTeam = nationalTeamService.findById(2);
		assertEquals("Seleção Italiana", nationalTeam.getName());
	}
	
	@Test
	@DisplayName("Teste seleção por ID inexistente")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/nationalteam.sql"})
	void findByIdErrorTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> nationalTeamService.findById(5));
		assertEquals("Seleção 5 não encontrada", exception.getMessage());	
	}
	
	@Test
	@DisplayName("Teste seleção por nome")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/nationalteam.sql"})
	void findByNameTest() {
		var list = nationalTeamService.findByNameIgnoreCase("Seleção brasileira");
		assertEquals("Seleção Brasileira", list.get(0).getName());
	}
	
	@Test
	@DisplayName("Teste seleção por nome inexistente")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/nationalteam.sql"})
	void findByIdNameErrorTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> nationalTeamService.findByNameIgnoreCase("Seleção de Portugal"));
		assertEquals("Nenhuma seleção cadastrada com o nome: Seleção de Portugal", exception.getMessage());	
	}
	
	@Test
	@DisplayName("Teste seleção por nome contem")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/nationalteam.sql"})
	void findByNameContainsTest() {
		var list = nationalTeamService.findByNameContainsIgnoreCase("a");
		assertEquals(4, list.size());
	}
	
	@Test
	@DisplayName("Teste seleção por nome contem inexistente")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/nationalteam.sql"})
	void findByIdNameContainsErrorTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> nationalTeamService.findByNameContainsIgnoreCase("z"));
		assertEquals("Nenhuma seleção cadastrada contem no nome: z", exception.getMessage());	
	}
	
	@Test
	@DisplayName("Teste seleção por nome começa com")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/nationalteam.sql"})
	void findByNameStartsWithTest() {
		var list = nationalTeamService.findByNameStartsWithIgnoreCase("seleção");
		assertEquals(4, list.size());
	}
	
	@Test
	@DisplayName("Teste seleção por nome começa com vazio")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/nationalteam.sql"})
	void findByNameStartsWithErrorTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> nationalTeamService.findByNameStartsWithIgnoreCase("z"));
		assertEquals("Nenhuma seleção cadastrada começa com: z", exception.getMessage());	
	}
	
	@Test
	@DisplayName("Teste seleção por país")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/nationalteam.sql"})
	void findByCountryTest() {
		var list = nationalTeamService.findByCountry(countryService.findById(1));
		assertEquals("Seleção Brasileira", list.get(0).getName());
	}
	
	@Test
	@DisplayName("Teste seleção por país inexistente")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/nationalteam.sql"})
	void findByCountryErrorTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> nationalTeamService.findByCountry(countryService.findById(5)));
		assertEquals("Nenhuma seleção cadastrada começa está associada com o país: PORTUGAL", exception.getMessage());	
	}
}
