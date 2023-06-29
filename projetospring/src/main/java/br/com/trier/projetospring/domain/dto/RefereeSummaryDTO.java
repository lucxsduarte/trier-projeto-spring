package br.com.trier.projetospring.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RefereeSummaryDTO {

	private String referee_name;
	private Integer referee_age;
	private String referee_country;
	private Integer referee_matches;
}
