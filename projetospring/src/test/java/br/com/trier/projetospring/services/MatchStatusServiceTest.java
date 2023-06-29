package br.com.trier.projetospring.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.projetospring.BaseTests;
import br.com.trier.projetospring.domain.MatchStatus;
import br.com.trier.projetospring.services.exceptions.IntegrityViolation;
import br.com.trier.projetospring.services.exceptions.ObjectNotFound;
import jakarta.transaction.Transactional;

@Transactional
public class MatchStatusServiceTest extends BaseTests{

	@Autowired
	MatchStatusService matchStatusService;
	@Autowired
	MatchService matchService;
	@Autowired
	RefereeService refereeService;
	@Autowired
	NationalTeamService nationalTeamService;
	
	@Test
	@DisplayName("Teste cadastrar status da partida")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/referee.sql"})
	@Sql({"classpath:/resources/sqls/nationalteam.sql"})
	@Sql({"classpath:/resources/sqls/championship.sql"})
	@Sql({"classpath:/resources/sqls/match.sql"})
	void insertTest() {
		matchStatusService.save(new MatchStatus(null, matchService.findById(1), refereeService.findById(3), nationalTeamService.findById(4), nationalTeamService.findById(1), nationalTeamService.findById(1), 3, 1 ));
		var newMatchStatus = matchStatusService.findById(1);
		assertEquals("BRASIL", newMatchStatus.getMatch().getCountry().getName());
	}
	
	@Test
	@DisplayName("Teste cadastrar status da partida erro na partida")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/referee.sql"})
	@Sql({"classpath:/resources/sqls/nationalteam.sql"})
	@Sql({"classpath:/resources/sqls/championship.sql"})
	@Sql({"classpath:/resources/sqls/match.sql"})
	@Sql({"classpath:/resources/sqls/matchStatus.sql"})
	void insertMatchErrorTest() {
		var exception = assertThrows(IntegrityViolation.class, () -> matchStatusService.save(new MatchStatus(null, matchService.findById(1), refereeService.findById(3), nationalTeamService.findById(4), nationalTeamService.findById(1), nationalTeamService.findById(1), 3, 1 )));
		assertEquals("Já existe um status desta partida", exception.getMessage());	
	}
	
	@Test
	@DisplayName("Teste cadastrar status da partida erro no time")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/referee.sql"})
	@Sql({"classpath:/resources/sqls/nationalteam.sql"})
	@Sql({"classpath:/resources/sqls/championship.sql"})
	@Sql({"classpath:/resources/sqls/match.sql"})
	void insertTeamsErrorTest() {
		var exception = assertThrows(IntegrityViolation.class, () -> matchStatusService.save(new MatchStatus(null, matchService.findById(1), refereeService.findById(3), nationalTeamService.findById(4), nationalTeamService.findById(4), nationalTeamService.findById(4), 3, 1 )));
		assertEquals("Uma seleção não pode jogar contra ela mesma!", exception.getMessage());	
	}
	
	@Test
	@DisplayName("Teste cadastrar status da partida erro no árbitro")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/referee.sql"})
	@Sql({"classpath:/resources/sqls/nationalteam.sql"})
	@Sql({"classpath:/resources/sqls/championship.sql"})
	@Sql({"classpath:/resources/sqls/match.sql"})
	void insertRefereeErrorHomeTest() {
		var exception = assertThrows(IntegrityViolation.class, () -> matchStatusService.save(
				new MatchStatus(null, matchService.findById(1), refereeService.findById(3), nationalTeamService.findById(3), nationalTeamService.findById(1), nationalTeamService.findById(1), 3, 1 )));
		assertEquals("Árbitro não pode apitar uma partida da seleção do seu país!", exception.getMessage());	
	}
	
	@Test
	@DisplayName("Teste cadastrar status da partida erro no árbitro")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/referee.sql"})
	@Sql({"classpath:/resources/sqls/nationalteam.sql"})
	@Sql({"classpath:/resources/sqls/championship.sql"})
	@Sql({"classpath:/resources/sqls/match.sql"})
	void insertRefereeErrorAwayTest() {
		var exception = assertThrows(IntegrityViolation.class, () -> matchStatusService.save(
				new MatchStatus(null, matchService.findById(1), refereeService.findById(1), nationalTeamService.findById(4), nationalTeamService.findById(1), nationalTeamService.findById(1), 3, 1 )));
		assertEquals("Árbitro não pode apitar uma partida da seleção do seu país!", exception.getMessage());	
	}
	
	@Test
	@DisplayName("Teste cadastrar status da partida erro no vencedor")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/referee.sql"})
	@Sql({"classpath:/resources/sqls/nationalteam.sql"})
	@Sql({"classpath:/resources/sqls/championship.sql"})
	@Sql({"classpath:/resources/sqls/match.sql"})
	void insertWinnerErrorTest() {
		var exception = assertThrows(IntegrityViolation.class, () -> matchStatusService.save(
				new MatchStatus(null, matchService.findById(1), refereeService.findById(3), nationalTeamService.findById(4), nationalTeamService.findById(1), nationalTeamService.findById(2), 3, 1 )));
		assertEquals("O vencedor escolhido não está participando da partida!", exception.getMessage());	
	}
	
	@Test
	@DisplayName("Teste cadastrar status da partida erro no placar da casa")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/referee.sql"})
	@Sql({"classpath:/resources/sqls/nationalteam.sql"})
	@Sql({"classpath:/resources/sqls/championship.sql"})
	@Sql({"classpath:/resources/sqls/match.sql"})
	void insertHomeScoreErrorTest() {
		var exception = assertThrows(IntegrityViolation.class, () -> matchStatusService.save(
				new MatchStatus(null, matchService.findById(1), refereeService.findById(3), nationalTeamService.findById(4), nationalTeamService.findById(1), nationalTeamService.findById(1), -3, 1 )));
		assertEquals("Quantidade de gols não pode ser menor que 0", exception.getMessage());	
	}
	
	@Test
	@DisplayName("Teste cadastrar status da partida erro no placar visitante")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/referee.sql"})
	@Sql({"classpath:/resources/sqls/nationalteam.sql"})
	@Sql({"classpath:/resources/sqls/championship.sql"})
	@Sql({"classpath:/resources/sqls/match.sql"})
	void insertAwayScoreErrorTest() {
		var exception = assertThrows(IntegrityViolation.class, () -> matchStatusService.save(new MatchStatus(null, matchService.findById(1), refereeService.findById(3), nationalTeamService.findById(4), nationalTeamService.findById(1), nationalTeamService.findById(1), 3, -1 )));
		assertEquals("Quantidade de gols não pode ser menor que 0", exception.getMessage());	
	}
	
	@Test
	@DisplayName("Teste atualizar status da partida")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/referee.sql"})
	@Sql({"classpath:/resources/sqls/nationalteam.sql"})
	@Sql({"classpath:/resources/sqls/championship.sql"})
	@Sql({"classpath:/resources/sqls/match.sql"})
	@Sql({"classpath:/resources/sqls/matchStatus.sql"})
	void updateTest() {
		matchStatusService.update(new MatchStatus(1, matchService.findById(1), refereeService.findById(3), nationalTeamService.findById(4), nationalTeamService.findById(1), nationalTeamService.findById(1), 3, 1 ));
		var newMatchStatus = matchStatusService.findById(1);
		assertEquals("BRASIL", newMatchStatus.getMatch().getCountry().getName());
	}
	
	@Test
	@DisplayName("Teste deletar status da partida")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/referee.sql"})
	@Sql({"classpath:/resources/sqls/nationalteam.sql"})
	@Sql({"classpath:/resources/sqls/championship.sql"})
	@Sql({"classpath:/resources/sqls/match.sql"})
	@Sql({"classpath:/resources/sqls/matchStatus.sql"})
	void deleteTest() {
		matchStatusService.delete(2);
		var list = matchStatusService.listAll();
		assertEquals(1, list.size());
	}
	
	@Test
	@DisplayName("Teste deletar status da partida inexistente")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/referee.sql"})
	@Sql({"classpath:/resources/sqls/nationalteam.sql"})
	@Sql({"classpath:/resources/sqls/championship.sql"})
	@Sql({"classpath:/resources/sqls/match.sql"})
	void deleteErrorTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> matchStatusService.delete(1));
		assertEquals("Status da partida 1 não encontrado", exception.getMessage());	
	}
	
	@Test
	@DisplayName("Teste listar todos status da partida")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/referee.sql"})
	@Sql({"classpath:/resources/sqls/nationalteam.sql"})
	@Sql({"classpath:/resources/sqls/championship.sql"})
	@Sql({"classpath:/resources/sqls/match.sql"})
	@Sql({"classpath:/resources/sqls/matchStatus.sql"})
	void listAllTest() {
		var list = matchStatusService.listAll();
		assertEquals(2, list.size());
	}
	
	@Test
	@DisplayName("Teste listar todos status da partida inexistente")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/referee.sql"})
	@Sql({"classpath:/resources/sqls/nationalteam.sql"})
	@Sql({"classpath:/resources/sqls/championship.sql"})
	@Sql({"classpath:/resources/sqls/match.sql"})
	void listAllErrorTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> matchStatusService.listAll());
		assertEquals("Nenhum status de partida cadastrado", exception.getMessage());	
	}
	
	@Test
	@DisplayName("Teste buscar status da partida por ID")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/referee.sql"})
	@Sql({"classpath:/resources/sqls/nationalteam.sql"})
	@Sql({"classpath:/resources/sqls/championship.sql"})
	@Sql({"classpath:/resources/sqls/match.sql"})
	@Sql({"classpath:/resources/sqls/matchStatus.sql"})
	void findByIdTest() {
		var newMatchStatus = matchStatusService.findById(1);
		assertEquals("BRASIL", newMatchStatus.getMatch().getCountry().getName());
	}
	
	@Test
	@DisplayName("Teste buscar status da partida por ID inexistente")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/referee.sql"})
	@Sql({"classpath:/resources/sqls/nationalteam.sql"})
	@Sql({"classpath:/resources/sqls/championship.sql"})
	@Sql({"classpath:/resources/sqls/match.sql"})
	void findByIdErrorTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> matchStatusService.findById(1));
		assertEquals("Status da partida 1 não encontrado", exception.getMessage());	
	}
	
	@Test
	@DisplayName("Teste buscar status da partida por partida")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/referee.sql"})
	@Sql({"classpath:/resources/sqls/nationalteam.sql"})
	@Sql({"classpath:/resources/sqls/championship.sql"})
	@Sql({"classpath:/resources/sqls/match.sql"})
	@Sql({"classpath:/resources/sqls/matchStatus.sql"})
	void findByMatchTest() {
		var list = matchStatusService.findByMatch(matchService.findById(1));
		assertEquals(1, list.size());
	}
	
	@Test
	@DisplayName("Teste buscar status da partida por partida inexistente")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/referee.sql"})
	@Sql({"classpath:/resources/sqls/nationalteam.sql"})
	@Sql({"classpath:/resources/sqls/championship.sql"})
	@Sql({"classpath:/resources/sqls/match.sql"})
	@Sql({"classpath:/resources/sqls/matchStatus.sql"})
	void findByMatchErrorTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> matchStatusService.findByMatch(matchService.findById(3)));
		assertEquals("Nenhum status de partida cadastrado para a partida 3", exception.getMessage());	
	}
	
	@Test
	@DisplayName("Teste buscar status da partida por árbitro")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/referee.sql"})
	@Sql({"classpath:/resources/sqls/nationalteam.sql"})
	@Sql({"classpath:/resources/sqls/championship.sql"})
	@Sql({"classpath:/resources/sqls/match.sql"})
	@Sql({"classpath:/resources/sqls/matchStatus.sql"})
	void findByRefereeTest() {
		var list = matchStatusService.findByReferee(refereeService.findById(1));
		assertEquals(1, list.size());
	}
	
	@Test
	@DisplayName("Teste buscar status da partida por árbitro inexistente")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/referee.sql"})
	@Sql({"classpath:/resources/sqls/nationalteam.sql"})
	@Sql({"classpath:/resources/sqls/championship.sql"})
	@Sql({"classpath:/resources/sqls/match.sql"})
	@Sql({"classpath:/resources/sqls/matchStatus.sql"})
	void findByRefereeErrorTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> matchStatusService.findByReferee(refereeService.findById(2)));
		assertEquals("Nenhum status de partida cadastrado para o árbitro 2", exception.getMessage());	
	}
	
	@Test
	@DisplayName("Teste buscar status da partida por partida e árbitro")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/referee.sql"})
	@Sql({"classpath:/resources/sqls/nationalteam.sql"})
	@Sql({"classpath:/resources/sqls/championship.sql"})
	@Sql({"classpath:/resources/sqls/match.sql"})
	@Sql({"classpath:/resources/sqls/matchStatus.sql"})
	void findByMatchAndRefereeTest() {
		var list = matchStatusService.findByMatchAndReferee(matchService.findById(1), refereeService.findById(3));
		assertEquals(1, list.size());
	}
	
	@Test
	@DisplayName("Teste buscar status da partida por partida e árbitro inexistente")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/referee.sql"})
	@Sql({"classpath:/resources/sqls/nationalteam.sql"})
	@Sql({"classpath:/resources/sqls/championship.sql"})
	@Sql({"classpath:/resources/sqls/match.sql"})
	@Sql({"classpath:/resources/sqls/matchStatus.sql"})
	void findByMatchAndRefereeErrorTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> matchStatusService.findByMatchAndReferee(matchService.findById(1), refereeService.findById(1)));
		assertEquals("Nenhum status de partida cadastrado para a partida 1 e o árbitro 1", exception.getMessage());	
	}
	
	@Test
	@DisplayName("Teste buscar status da partida por time da casa")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/referee.sql"})
	@Sql({"classpath:/resources/sqls/nationalteam.sql"})
	@Sql({"classpath:/resources/sqls/championship.sql"})
	@Sql({"classpath:/resources/sqls/match.sql"})
	@Sql({"classpath:/resources/sqls/matchStatus.sql"})
	void findByHomeTeamTest() {
		var list = matchStatusService.findByHomeTeam(nationalTeamService.findById(4));
		assertEquals(2, list.size());
	}
	
	@Test
	@DisplayName("Teste buscar status da partida por time da casa inexistente")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/referee.sql"})
	@Sql({"classpath:/resources/sqls/nationalteam.sql"})
	@Sql({"classpath:/resources/sqls/championship.sql"})
	@Sql({"classpath:/resources/sqls/match.sql"})
	@Sql({"classpath:/resources/sqls/matchStatus.sql"})
	void findByHomeTeamErrorTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> matchStatusService.findByHomeTeam(nationalTeamService.findById(1)));
		assertEquals("Nenhum status de partida cadastrado para a seleção 1 jogando em casa", exception.getMessage());	
	}
	
	@Test
	@DisplayName("Teste buscar status da partida por time visitante")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/referee.sql"})
	@Sql({"classpath:/resources/sqls/nationalteam.sql"})
	@Sql({"classpath:/resources/sqls/championship.sql"})
	@Sql({"classpath:/resources/sqls/match.sql"})
	@Sql({"classpath:/resources/sqls/matchStatus.sql"})
	void findByAwayTeamTest() {
		var list = matchStatusService.findByAwayTeam(nationalTeamService.findById(1));
		assertEquals(1, list.size());
	}
	
	@Test
	@DisplayName("Teste buscar status da partida por time visitante inexistente")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/referee.sql"})
	@Sql({"classpath:/resources/sqls/nationalteam.sql"})
	@Sql({"classpath:/resources/sqls/championship.sql"})
	@Sql({"classpath:/resources/sqls/match.sql"})
	@Sql({"classpath:/resources/sqls/matchStatus.sql"})
	void findByAwayTeamErrorTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> matchStatusService.findByAwayTeam(nationalTeamService.findById(4)));
		assertEquals("Nenhum status de partida cadastrado para a seleção 4 jogando fora de casa", exception.getMessage());	
	}
	
	@Test
	@DisplayName("Teste buscar status da partida por time vencedor")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/referee.sql"})
	@Sql({"classpath:/resources/sqls/nationalteam.sql"})
	@Sql({"classpath:/resources/sqls/championship.sql"})
	@Sql({"classpath:/resources/sqls/match.sql"})
	@Sql({"classpath:/resources/sqls/matchStatus.sql"})
	void findByWinnerTest() {
		var list = matchStatusService.findByWinner(nationalTeamService.findById(1));
		assertEquals(1, list.size());
	}
	
	@Test
	@DisplayName("Teste buscar status da partida por time vencedor inexistente")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/referee.sql"})
	@Sql({"classpath:/resources/sqls/nationalteam.sql"})
	@Sql({"classpath:/resources/sqls/championship.sql"})
	@Sql({"classpath:/resources/sqls/match.sql"})
	@Sql({"classpath:/resources/sqls/matchStatus.sql"})
	void findByWinnerErrorTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> matchStatusService.findByWinner(nationalTeamService.findById(4)));
		assertEquals("Nenhum status de partida cadastrado onde a seleção 4 ganhou o jogo", exception.getMessage());	
	}
	
	@Test
	@DisplayName("Teste buscar status da partida por placar da casa")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/referee.sql"})
	@Sql({"classpath:/resources/sqls/nationalteam.sql"})
	@Sql({"classpath:/resources/sqls/championship.sql"})
	@Sql({"classpath:/resources/sqls/match.sql"})
	@Sql({"classpath:/resources/sqls/matchStatus.sql"})
	void findByHomeScoreTest() {
		var list = matchStatusService.findByHomeScore(1);
		assertEquals(2, list.size());
	}
	
	@Test
	@DisplayName("Teste buscar status da partida por placar da casa inexistente")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/referee.sql"})
	@Sql({"classpath:/resources/sqls/nationalteam.sql"})
	@Sql({"classpath:/resources/sqls/championship.sql"})
	@Sql({"classpath:/resources/sqls/match.sql"})
	@Sql({"classpath:/resources/sqls/matchStatus.sql"})
	void findByHomeScoreErrorTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> matchStatusService.findByHomeScore(2));
		assertEquals("Nenhum status de partida cadastrado onde a seleção de casa marcou 2 gol(s)", exception.getMessage());	
	}
	
	@Test
	@DisplayName("Teste buscar status da partida por placar visitante")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/referee.sql"})
	@Sql({"classpath:/resources/sqls/nationalteam.sql"})
	@Sql({"classpath:/resources/sqls/championship.sql"})
	@Sql({"classpath:/resources/sqls/match.sql"})
	@Sql({"classpath:/resources/sqls/matchStatus.sql"})
	void findByAwayScoreTest() {
		var list = matchStatusService.findByAwayScore(3);
		assertEquals(2, list.size());
	}
	
	@Test
	@DisplayName("Teste buscar status da partida por placar visitante inexistente")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/referee.sql"})
	@Sql({"classpath:/resources/sqls/nationalteam.sql"})
	@Sql({"classpath:/resources/sqls/championship.sql"})
	@Sql({"classpath:/resources/sqls/match.sql"})
	@Sql({"classpath:/resources/sqls/matchStatus.sql"})
	void findByAwayScoreErrorTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> matchStatusService.findByAwayScore(1));
		assertEquals("Nenhum status de partida cadastrado onde a seleção visitante marcou 1 gol(s)", exception.getMessage());	
	}
	
	@Test
	@DisplayName("Teste buscar status da partida por time da casa e placar da casa")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/referee.sql"})
	@Sql({"classpath:/resources/sqls/nationalteam.sql"})
	@Sql({"classpath:/resources/sqls/championship.sql"})
	@Sql({"classpath:/resources/sqls/match.sql"})
	@Sql({"classpath:/resources/sqls/matchStatus.sql"})
	void findByHomeTeamAndHomeScoreTest() {
		var list = matchStatusService.findByHomeTeamAndHomeScore(nationalTeamService.findById(4), 1);
		assertEquals(2, list.size());
	}
	
	@Test
	@DisplayName("Teste buscar status da partida por time da casa e placar da casa")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/referee.sql"})
	@Sql({"classpath:/resources/sqls/nationalteam.sql"})
	@Sql({"classpath:/resources/sqls/championship.sql"})
	@Sql({"classpath:/resources/sqls/match.sql"})
	@Sql({"classpath:/resources/sqls/matchStatus.sql"})
	void findByHomeTeamAndHomeScoreErrorTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> matchStatusService.findByHomeTeamAndHomeScore(nationalTeamService.findById(4), 2));
		assertEquals("Nenhum status de partida cadastrado para a seleção 4 jogando em casa e marcando 2 gol(s)", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar status da partida por placares")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/referee.sql"})
	@Sql({"classpath:/resources/sqls/nationalteam.sql"})
	@Sql({"classpath:/resources/sqls/championship.sql"})
	@Sql({"classpath:/resources/sqls/match.sql"})
	@Sql({"classpath:/resources/sqls/matchStatus.sql"})
	void findByHomeScoreAndAwayScoreTest() {
		var list = matchStatusService.findByHomeScoreAndAwayScore(1, 3);
		assertEquals(2, list.size());
	}
	
	@Test
	@DisplayName("Teste buscar status da partida por placares inválido")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/referee.sql"})
	@Sql({"classpath:/resources/sqls/nationalteam.sql"})
	@Sql({"classpath:/resources/sqls/championship.sql"})
	@Sql({"classpath:/resources/sqls/match.sql"})
	@Sql({"classpath:/resources/sqls/matchStatus.sql"})
	void findByHomeScoreAndAwayScoreErrorTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> matchStatusService.findByHomeScoreAndAwayScore(4, 1));
		assertEquals("Nenhum status de partida cadastrado onde a seleção de casa marcou 4 gol(s) e a seleção visitante marcou marcou 1 gol(s)", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar status da partida por visitante e placar visitante")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/referee.sql"})
	@Sql({"classpath:/resources/sqls/nationalteam.sql"})
	@Sql({"classpath:/resources/sqls/championship.sql"})
	@Sql({"classpath:/resources/sqls/match.sql"})
	@Sql({"classpath:/resources/sqls/matchStatus.sql"})
	void findByAwayTeamAndAwayScoreTest() {
		var list = matchStatusService.findByAwayTeamAndAwayScore(nationalTeamService.findById(1), 3);
		assertEquals(1, list.size());
	}
	
	@Test
	@DisplayName("Teste buscar status da partida por visitante e placar visitante inexistente")
	@Sql({"classpath:/resources/sqls/country.sql"})
	@Sql({"classpath:/resources/sqls/referee.sql"})
	@Sql({"classpath:/resources/sqls/nationalteam.sql"})
	@Sql({"classpath:/resources/sqls/championship.sql"})
	@Sql({"classpath:/resources/sqls/match.sql"})
	@Sql({"classpath:/resources/sqls/matchStatus.sql"})
	void findByAwayTeamAndAwayScoreErrorTest() {
		var exception = assertThrows(ObjectNotFound.class, () -> matchStatusService.findByAwayTeamAndAwayScore(nationalTeamService.findById(1), 2));
		assertEquals("Nenhum status de partida cadastrado para a seleção 1 jogando fora de casa e marcando 2 gol(s)", exception.getMessage());
	}
}
