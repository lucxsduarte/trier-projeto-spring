package br.com.trier.projetospring.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MatchStatusDTO {

	private Integer id;
	
	private Integer match_id;
	
	private String match_date;
	
	private String match_country;
	
	private String match_championship;
	
	private Integer referee_id;
	
	private String referee_name;
	
	private Integer referee_age;
	
	private String referee_country;
	
	private Integer homeTeam_id;
	
	private String homeTeam_name;
	
	private String homeTeam_country;
	
	private Integer awayTeam_id;
	
	private String awayTeam_name;
	
	private String awayTeam_country;
	
	private Integer winner_id;
	
	private String winner_name;
	
	private Integer homeScore;
	
	private Integer awayScore;
	
}
