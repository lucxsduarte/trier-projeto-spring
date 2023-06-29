package br.com.trier.projetospring.resources;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.projetospring.ProjetospringApplication;
import br.com.trier.projetospring.config.jwt.LoginDTO;
import br.com.trier.projetospring.domain.dto.UserDTO;

@ActiveProfiles("test")
@SpringBootTest(classes = ProjetospringApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserResourceTest {

	@Autowired
	protected TestRestTemplate rest;
	
	private HttpHeaders getHeaders(String email, String password) {
		LoginDTO loginDTO = new LoginDTO(email, password);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<LoginDTO> requestEntity = new HttpEntity<>(loginDTO, headers);
		ResponseEntity<String> responseEntity = rest.exchange(
				"/auth/token", 
				HttpMethod.POST, 
				requestEntity, 
				String.class);
		
		String token = responseEntity.getBody();
		headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(token);
		return headers;
	}
	
	private ResponseEntity<UserDTO> getUser(String url) {
		return rest.exchange(
				url, 
				HttpMethod.GET, 
				new HttpEntity<>(getHeaders("lucas@gmail.com", "123")),
				UserDTO.class
				);
	}
	
	private ResponseEntity<List<UserDTO>> getUsers(String url) {
		return rest.exchange(
				url, 
				HttpMethod.GET, 
				new HttpEntity<>(getHeaders("lucas@gmail.com", "123")),
				new ParameterizedTypeReference<List<UserDTO>>() {}
				);
	}
	
	@Test
	@DisplayName("teste cadastrar usuário")
	@Sql(scripts="classpath:/resources/sqls/truncateTables.sql")
	@Sql(scripts="classpath:/resources/sqls/users.sql")
	public void testCreateUser() {
		UserDTO dto = new UserDTO(null, "nome", "email", "senha", "ADMIN");
		HttpHeaders headers = getHeaders("lucas@gmail.com", "123");
		HttpEntity<UserDTO> requestEntity = new HttpEntity<>(dto, headers);
		ResponseEntity<UserDTO> responseEntity = rest.exchange(
				"/user",
				HttpMethod.POST,
				requestEntity,
				UserDTO.class
		);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		UserDTO user = responseEntity.getBody();
		assertEquals("nome", user.getName());
	}
	
	@Test
	@DisplayName("teste update usuário")
	@Sql(scripts="classpath:/resources/sqls/truncateTables.sql")
	@Sql(scripts="classpath:/resources/sqls/users.sql")
	public void testUpdateUser() {
		UserDTO dto = new UserDTO(3, "nome", "email@email.com", "senha", "ADMIN");
		HttpHeaders headers = getHeaders("lucas@gmail.com","123");
		HttpEntity<UserDTO> requestEntity = new HttpEntity<>(dto, headers);
		ResponseEntity<UserDTO> responseEntity = rest.exchange(
				"/user/3",
				HttpMethod.PUT,
				requestEntity,
				UserDTO.class
		);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		UserDTO user = responseEntity.getBody();
		assertEquals("nome", user.getName());
	}
	
	@Test
	@DisplayName("teste delete usuario")
	@Sql(scripts="classpath:/resources/sqls/truncateTables.sql")
	@Sql(scripts="classpath:/resources/sqls/users.sql")
	public void testDeleteId() {
		HttpHeaders headers = getHeaders("lucas@gmail.com","123");
		HttpEntity<Void> requestEntity = new HttpEntity<>(null, headers);
		ResponseEntity<Void> responseEntity = rest.exchange(
                "/user/3",
                HttpMethod.DELETE,
                requestEntity,
                Void.class
        );
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	@DisplayName("teste listar todos")
	@Sql(scripts="classpath:/resources/sqls/truncateTables.sql")
	@Sql(scripts="classpath:/resources/sqls/users.sql")
	public void testListAll() {
		ResponseEntity<List<UserDTO>> responseEntity = rest.exchange(
				"/user",
                HttpMethod.GET,
                new HttpEntity<>(getHeaders("lucas@gmail.com", "123")),
                new ParameterizedTypeReference<List<UserDTO>>() {}
        );
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
	    assertEquals(3, responseEntity.getBody().size());
	}
	
	@Test
	@DisplayName("teste buscar por id")
	@Sql(scripts="classpath:/resources/sqls/truncateTables.sql")
	@Sql(scripts="classpath:/resources/sqls/users.sql")
	public void testFindById() {
		ResponseEntity<UserDTO> response = getUser("/user/3");
		assertEquals(HttpStatus.OK, response.getStatusCode());
		UserDTO user = response.getBody();
		assertEquals("Lucas", user.getName());
	}
	
	@Test
	@DisplayName("teste buscar por nome")
	@Sql(scripts="classpath:/resources/sqls/truncateTables.sql")
	@Sql(scripts="classpath:/resources/sqls/users.sql")
	public void testFindByName() {
		ResponseEntity<List<UserDTO>> response = getUsers("/user/name/joao");
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("Joao", response.getBody().get(0).getName());
	}
	
	@Test
	@DisplayName("teste buscar por nome contem")
	@Sql(scripts="classpath:/resources/sqls/truncateTables.sql")
	@Sql(scripts="classpath:/resources/sqls/users.sql")
	public void testFindByNameContains() {
		ResponseEntity<List<UserDTO>> response = getUsers("/user/name/contains/a");
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	@DisplayName("teste buscar por nome que começa com")
	@Sql(scripts="classpath:/resources/sqls/truncateTables.sql")
	@Sql(scripts="classpath:/resources/sqls/users.sql")
	public void testFindByNameStartsWith() {
		ResponseEntity<List<UserDTO>> response = getUsers("/user/name/starts/L");
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	@DisplayName("teste buscar por email")
	@Sql(scripts="classpath:/resources/sqls/truncateTables.sql")
	@Sql(scripts="classpath:/resources/sqls/users.sql")
	public void testFindByEmail() {
		ResponseEntity<List<UserDTO>> response = getUsers("/user/email/lucas@gmail.com");
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
}
