package br.com.trier.projetospring.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.projetospring.domain.Match;
import br.com.trier.projetospring.domain.MatchStatus;
import br.com.trier.projetospring.domain.NationalTeam;
import br.com.trier.projetospring.domain.Referee;
import br.com.trier.projetospring.repositories.MatchStatusRepository;
import br.com.trier.projetospring.services.MatchStatusService;
import br.com.trier.projetospring.services.exceptions.IntegrityViolation;
import br.com.trier.projetospring.services.exceptions.ObjectNotFound;

@Service
public class MatchStatusServiceImpl implements MatchStatusService{

	@Autowired
	MatchStatusRepository repository;
	
	@Override
	public MatchStatus save(MatchStatus match_status) {
		validateMatch(match_status);
		validateTeams(match_status);
		validateReferee(match_status);
		validateWinner(match_status);
		validateScore(match_status);
		return repository.save(match_status);
	}

	@Override
	public MatchStatus update(MatchStatus match_status) {
		findById(match_status.getId());
		validateTeams(match_status);
		validateReferee(match_status);
		validateWinner(match_status);
		validateScore(match_status);
		return repository.save(match_status);
	}

	@Override
	public void delete(Integer id) {
		MatchStatus match_status = findById(id);
		repository.delete(match_status);
		
	}

	@Override
	public MatchStatus findById(Integer id) {
		return repository.findById(id).orElseThrow(() -> new ObjectNotFound("Status da partida %s não encontrado".formatted(id)));
	}

	@Override
	public List<MatchStatus> listAll() {
		List<MatchStatus> list = repository.findAll();
		if (list.size() == 0) {
			throw new ObjectNotFound("Nenhum status de partida cadastrado");
		}
		return list;
	}

	@Override
	public List<MatchStatus> findByMatch(Match match) {
		List<MatchStatus> list = repository.findByMatch(match);
		if (list.size() == 0) {
			throw new ObjectNotFound("Nenhum status de partida cadastrado para a partida %s".formatted(match.getId()));
		}
		return list;
	}

	@Override
	public List<MatchStatus> findByReferee(Referee referee) {
		List<MatchStatus> list = repository.findByReferee(referee);
		if (list.size() == 0) {
			throw new ObjectNotFound("Nenhum status de partida cadastrado para o árbitro %s".formatted(referee.getId()));
		}
		return list;
	}

	@Override
	public List<MatchStatus> findByMatchAndReferee(Match match, Referee referee) {
		List<MatchStatus> list = repository.findByMatchAndReferee(match, referee);
		if (list.size() == 0) {
			throw new ObjectNotFound("Nenhum status de partida cadastrado para a partida %s e o árbitro %s".formatted(match.getId(), referee.getId()));
		}
		return list;
	}

	@Override
	public List<MatchStatus> findByHomeTeam(NationalTeam nationalTeam) {
		List<MatchStatus> list = repository.findByHomeTeam(nationalTeam);
		if (list.size() == 0) {
			throw new ObjectNotFound("Nenhum status de partida cadastrado para a seleção %s jogando em casa".formatted(nationalTeam.getId()));
		}
		return list;
	}

	@Override
	public List<MatchStatus> findByAwayTeam(NationalTeam nationalTeam) {
		List<MatchStatus> list = repository.findByAwayTeam(nationalTeam);
		if (list.size() == 0) {
			throw new ObjectNotFound("Nenhum status de partida cadastrado para a seleção %s jogando fora de casa".formatted(nationalTeam.getId()));
		}
		return list;
	}

	@Override
	public List<MatchStatus> findByWinner(NationalTeam nationalTeam) {
		List<MatchStatus> list = repository.findByWinner(nationalTeam);
		if (list.size() == 0) {
			throw new ObjectNotFound("Nenhum status de partida cadastrado onde a seleção %s ganhou o jogo".formatted(nationalTeam.getId()));
		}
		return list;
	}

	@Override
	public List<MatchStatus> findByHomeScore(Integer homeScore) {
		List<MatchStatus> list = repository.findByHomeScore(homeScore);
		if (list.size() == 0) {
			throw new ObjectNotFound("Nenhum status de partida cadastrado onde a seleção de casa marcou %s gol(s)".formatted(homeScore));
		}
		return list;
	}

	@Override
	public List<MatchStatus> findByAwayScore(Integer awayScore) {
		List<MatchStatus> list = repository.findByAwayScore(awayScore);
		if (list.size() == 0) {
			throw new ObjectNotFound("Nenhum status de partida cadastrado onde a seleção visitante marcou %s gol(s)".formatted(awayScore));
		}
		return list;
	}

	@Override
	public List<MatchStatus> findByHomeScoreAndAwayScore(Integer homeScore, Integer awayScore) {
		List<MatchStatus> list = repository.findByHomeScoreAndAwayScore(homeScore, awayScore);
		if (list.size() == 0) {
			throw new ObjectNotFound("Nenhum status de partida cadastrado onde a seleção de casa marcou %s gol(s) e a seleção visitante marcou marcou %s gol(s)".formatted(homeScore, awayScore));
		}
		return list;
	}

	@Override
	public List<MatchStatus> findByHomeTeamAndHomeScore(NationalTeam nationalTeam, Integer homeScore) {
		List<MatchStatus> list = repository.findByHomeTeamAndHomeScore(nationalTeam, homeScore);
		if (list.size() == 0) {
			throw new ObjectNotFound("Nenhum status de partida cadastrado para a seleção %s jogando em casa e marcando %s gol(s)".formatted(nationalTeam.getId(), homeScore));
		}
		return list;
	}

	@Override
	public List<MatchStatus> findByAwayTeamAndAwayScore(NationalTeam nationalTeam, Integer awayScore) {
		List<MatchStatus> list = repository.findByAwayTeamAndAwayScore(nationalTeam, awayScore);
		if (list.size() == 0) {
			throw new ObjectNotFound("Nenhum status de partida cadastrado para a seleção %s jogando fora de casa e marcando %s gol(s)".formatted(nationalTeam.getId(), awayScore));
		}
		return list;
	}
	
	private void validateMatch(MatchStatus matchStatus) {
		Match match = matchStatus.getMatch();
		boolean exists = repository.existsById(match.getId());
		if(exists) {
			throw new IntegrityViolation("Já existe um status desta partida");
		}
	}
	
	private void validateTeams(MatchStatus matchStatus) {
		if(matchStatus.getHomeTeam().getId() == matchStatus.getAwayTeam().getId() ) {
			throw new IntegrityViolation("Uma seleção não pode jogar contra ela mesma!");
		}
	}
	
	private void validateReferee(MatchStatus matchStatus) {
		if(matchStatus.getReferee().getCountry().getId().equals(matchStatus.getHomeTeam().getCountry().getId()) || 
				matchStatus.getReferee().getCountry().getId().equals(matchStatus.getAwayTeam().getCountry().getId())){
			throw new IntegrityViolation("Árbitro não pode apitar uma partida da seleção do seu país!");
		}
	}
	
	private void validateWinner(MatchStatus matchStatus) {
		if(matchStatus.getWinner().getId() != matchStatus.getHomeTeam().getId() && matchStatus.getWinner().getId() != matchStatus.getAwayTeam().getId() ) {
			throw new IntegrityViolation("O vencedor escolhido não está participando da partida!");
		}
	}
	
	private void validateScore(MatchStatus matchStatus) {
		if(matchStatus.getHomeScore() < 0 || matchStatus.getAwayScore() < 0 ) {
			throw new IntegrityViolation("Quantidade de gols não pode ser menor que 0");
		}
	}
}
