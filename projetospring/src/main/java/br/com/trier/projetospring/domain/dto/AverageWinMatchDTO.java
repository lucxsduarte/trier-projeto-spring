package br.com.trier.projetospring.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AverageWinMatchDTO {

	private String nationalTeam;
	private Integer matches;
	private Integer win_counter;
	private Integer loss_counter;
	private String average_win_match;
}
