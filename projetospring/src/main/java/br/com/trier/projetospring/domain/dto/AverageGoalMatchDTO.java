package br.com.trier.projetospring.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AverageGoalMatchDTO {

	private String nationalTeam;
	private Integer matches;
	private Integer goals;
	private String average_goals_match;
}
