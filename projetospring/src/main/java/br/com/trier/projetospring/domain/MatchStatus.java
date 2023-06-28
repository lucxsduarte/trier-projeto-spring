package br.com.trier.projetospring.domain;

import br.com.trier.projetospring.domain.dto.MatchStatusDTO;
import br.com.trier.projetospring.utils.DateUtils;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity(name = "match_status")
public class MatchStatus {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "match_status_id")
	@Setter
	private Integer id;
	
	@ManyToOne
	private Match match;
	
	@ManyToOne
	private Referee referee;
	
	@ManyToOne
	private NationalTeam homeTeam;
	
	@ManyToOne
	private NationalTeam awayTeam;
	
	@ManyToOne
	private NationalTeam winner;
	
	@Column(name = "match_status_home_score")
	private Integer homeScore;
	
	@Column(name = "match_status_away_score")
	private Integer awayScore;
	
	public MatchStatusDTO toDTO() {
		return new MatchStatusDTO(id, match.getId(), DateUtils.zoneDateTimeToStr(match.getDate()), match.getCountry().getName(), match.getChampionship().getName(), 
				referee.getId(), referee.getName(), referee.getAge(), referee.getCountry().getName(),
				homeTeam.getId(), homeTeam.getName(), homeTeam.getCountry().getName(),
				awayTeam.getId(), awayTeam.getName(), awayTeam.getCountry().getName(),
				winner.getId(), winner.getName(),
				homeScore, awayScore);
	}
	
	public MatchStatus(MatchStatusDTO dto) {
		this(dto.getId(), 
				new Match(dto.getMatch_id(), null, null, null),
				new Referee(dto.getReferee_id(), null, null, null),
				new NationalTeam(dto.getHomeTeam_id(), null, null),
				new NationalTeam(dto.getAwayTeam_id(), null, null),
				new NationalTeam(dto.getWinner_id(), null, null),
				dto.getHomeScore(),
				dto.getAwayScore()
				);
	}
	
	public MatchStatus(MatchStatusDTO dto, Match match, Referee referee, NationalTeam homeTeam, NationalTeam awayTeam, NationalTeam winner) {
		this(dto.getId(), 
				match,
				referee,
				homeTeam,
				awayTeam,
				winner,
				dto.getHomeScore(),
				dto.getAwayScore()
				);
	}
}
