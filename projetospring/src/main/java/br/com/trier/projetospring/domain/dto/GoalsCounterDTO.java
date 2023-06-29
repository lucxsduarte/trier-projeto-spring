package br.com.trier.projetospring.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GoalsCounterDTO {

	private String nationalTeam;
	private Integer total_goals_counter;
	private Integer home_goals_counter;
	private Integer away_goals_counter;
}
