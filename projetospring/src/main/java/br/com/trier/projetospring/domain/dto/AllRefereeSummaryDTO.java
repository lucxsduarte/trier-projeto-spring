package br.com.trier.projetospring.domain.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AllRefereeSummaryDTO {

	private List<RefereeSummaryDTO> referees;
}
