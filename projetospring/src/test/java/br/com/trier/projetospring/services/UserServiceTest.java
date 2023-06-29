package br.com.trier.projetospring.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.projetospring.BaseTests;
import br.com.trier.projetospring.domain.User;
import br.com.trier.projetospring.services.exceptions.ObjectNotFound;
import br.com.trier.projetospring.services.exceptions.IntegrityViolation;
import jakarta.transaction.Transactional;

@Transactional
public class UserServiceTest extends BaseTests{

	@Autowired
	UserService userService;
	
	@Test
	@DisplayName("Teste cadastra usuario")
	@Sql({"classpath:/resources/sqls/users.sql"})
	void insertTest()	{
		userService.save(new User(null, "Pedro", "pedro@gmail.com", "123", "USER"));
		var lista = userService.listAll();
		assertEquals(4, lista.size());
	}
	
	@Test
	@DisplayName("Teste cadastra usuario email repetido")
	@Sql({"classpath:/resources/sqls/users.sql"})
	void insertEmailErrorTest()	{
		var exception = assertThrows(IntegrityViolation.class, () -> userService.save(new User(null, "usu2", "lucas@gmail.com", "123", "ADMIN")));
		assertEquals("O email lucas@gmail.com já está sendo usado por outro usuário", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste altera usuario")
	@Sql({"classpath:/resources/sqls/users.sql"})
	void updateTest()	{
		userService.update(new User(4, "Pedro", "pedro@gmail.com", "123", "USER"));
		var newUser = userService.findById(4);
		assertEquals("pedro@gmail.com", newUser.getEmail());
	}
	
	@Test
	@DisplayName("Teste altera usuario com email repetido")
	@Sql({"classpath:/resources/sqls/users.sql"})
	void updateEmailErrorTest()	{
		var exception = assertThrows(IntegrityViolation.class, () -> userService.update(new User(4, "Usuario teste 2", "lucas@gmail.com", "123", "ADMIN")));
		assertEquals("O email lucas@gmail.com já está sendo usado por outro usuário", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste altera usuario inexistente")
	@Sql({"classpath:/resources/sqls/users.sql"})
	void updateNonExistentTest()	{
		var exception = assertThrows(ObjectNotFound.class, () -> userService.update(new User(10, "Usuario teste 4", "test4@test.com.br", "123", "ADMIN")));
		assertEquals("Usuário 10 não encontrado", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste deleta usuario")
	@Sql({"classpath:/resources/sqls/users.sql"})
	void deleteTest()	{
		userService.delete(4);
		var lista = userService.listAll();
		assertEquals(2, lista.size());
	}
	
	@Test
	@DisplayName("Teste deleta usuario inexistente")
	@Sql({"classpath:/resources/sqls/users.sql"})
	void deleteNonExistentTest()	{
		var exception = assertThrows(ObjectNotFound.class, () -> userService.delete(10));
		assertEquals("Usuário 10 não encontrado", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste lista todos")
	@Sql({"classpath:/resources/sqls/users.sql"})
	void listAllTest() {
		var lista = userService.listAll();
		assertEquals(3, lista.size());
	} 
	
	@Test
	@DisplayName("Teste lista todos vazio")
	void listAllNonExistentTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> userService.listAll());
		assertEquals("Nenhum usuário encontrado", exception.getMessage());
	} 
	
	@Test
	@DisplayName("Teste busca por ID")
	@Sql({"classpath:/resources/sqls/users.sql"})
	void findByIdTest() {
		var usuario = userService.findById(3);
		assertTrue(usuario != null);
		assertEquals("Lucas", usuario.getName());
	}
	
	@Test
	@DisplayName("Teste buscar usuario por ID inexistente")
	@Sql({"classpath:/resources/sqls/users.sql"})
	void findByIdNonExistentTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> userService.findById(10));
		assertEquals("Usuário 10 não encontrado", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste busca usuario por nome")
	@Sql({"classpath:/resources/sqls/users.sql"})
	void findByNameUserTest()	{
		var lista = userService.findByNameIgnoreCase("Lucas");
		assertEquals(1, lista.size());
		
		var exception = assertThrows(ObjectNotFound.class, () -> userService.findByNameIgnoreCase("luca"));
		assertEquals("Nenhum usuário se chama luca", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste busca usuario por nome inexistente")
	@Sql({"classpath:/resources/sqls/users.sql"})
	void findByNameUserErrorTest()	{
		var exception = assertThrows(ObjectNotFound.class, () -> userService.findByNameIgnoreCase("Jorge"));
		assertEquals("Nenhum usuário se chama Jorge", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste busca usuario por nome contem")
	@Sql({"classpath:/resources/sqls/users.sql"})
	void findByNameContainsTest()	{
		var lista = userService.findByNameContainsIgnoreCase("Lucas");
		assertEquals(1, lista.size());
	}
	
	@Test
	@DisplayName("Teste busca usuario por nome contem inexistente")
	void findByNameContainsErrorTest()	{
		var exception = assertThrows(ObjectNotFound.class, () -> userService.findByNameContainsIgnoreCase("n"));
		assertEquals("Nenhum usuário cadastrado contem no nome: n", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste busca usuario por nome que começa com")
	@Sql({"classpath:/resources/sqls/users.sql"})
	void findByNameStartsWithIgnoreCaseTest()	{
		var lista = userService.findByNameStartsWithIgnoreCase("L");
		assertEquals(1, lista.size());
	}
	
	@Test
	@DisplayName("Teste busca usuario por nome que começa com inexistente")
	@Sql({"classpath:/resources/sqls/users.sql"})
	void findByNameStartsWithIgnoreCaseErrorTest()	{
		var exception = assertThrows(ObjectNotFound.class, () -> userService.findByNameStartsWithIgnoreCase("h"));
		assertEquals("Nenhum usuário cadastrado com o nome que começa com: h", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste busca usuario por email")
	@Sql({"classpath:/resources/sqls/users.sql"})
	void findByEmailIgnoreCaseTest()	{
		var lista = userService.findByEmailIgnoreCase("lucas@gmail.com");
		assertEquals(1, lista.size());
	}
	
	@Test
	@DisplayName("Teste busca usuario por email inexistente")
	@Sql({"classpath:/resources/sqls/users.sql"})
	void findByEmailIgnoreCaseErrorTest()	{
		var exception = assertThrows(ObjectNotFound.class, () -> userService.findByEmailIgnoreCase("teste@gmail.com"));
		assertEquals("Nenhum usuário cadastrado com o email: teste@gmail.com", exception.getMessage());
	}
}
