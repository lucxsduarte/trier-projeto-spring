package br.com.trier.projetospring.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AllGoalsByChampionshipDTO {

	private String championship_name;
	private Integer championship_matches;
	private Integer championship_goals;
}
