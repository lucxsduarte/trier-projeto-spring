package br.com.trier.projetospring.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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
	private NationalTeam home_team;
	
	@ManyToOne
	private NationalTeam away_team;
	
	@ManyToOne
	private NationalTeam winner;
	
	@Column(name = "match_status_home_score")
	private Integer home_score;
	
	@Column(name = "match_status_away_score")
	private Integer away_Score;
}
