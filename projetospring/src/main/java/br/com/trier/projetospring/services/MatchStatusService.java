package br.com.trier.projetospring.services;

import java.util.List;

import br.com.trier.projetospring.domain.Match;
import br.com.trier.projetospring.domain.MatchStatus;
import br.com.trier.projetospring.domain.NationalTeam;
import br.com.trier.projetospring.domain.Referee;

public interface MatchStatusService {

	MatchStatus save(MatchStatus match_status);
	MatchStatus update(MatchStatus match_Status);
	void delete (Integer id);
	MatchStatus findById(Integer id);
	List<MatchStatus> listAll();
	List<MatchStatus> findByMatch(Match match);
	List<MatchStatus> findByReferee(Referee referee);
	List<MatchStatus> findByMatchAndReferee(Match match, Referee referee);
	List<MatchStatus> findByHomeTeam(NationalTeam nationalTeam);
	List<MatchStatus> findByAwayTeam(NationalTeam nationalTeam);
	List<MatchStatus> findByWinner(NationalTeam nationalTeam);
	List<MatchStatus> findByHomeScore(Integer homeScore);
	List<MatchStatus> findByAwayScore(Integer awayScore);
	List<MatchStatus> findByHomeScoreAndAwayScore(Integer homeScore, Integer awayScore);
	List<MatchStatus> findByHomeTeamAndHomeScore(NationalTeam nationalTeam, Integer homeScore);
	List<MatchStatus> findByAwayTeamAndAwayScore(NationalTeam nationalTeam, Integer awayScore);
}
