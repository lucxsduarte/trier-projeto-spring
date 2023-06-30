package br.com.trier.projetospring.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MatchCountDTO {

	private String national_team_name;
	private String country_name;
	private Integer matches_in_own_country;
	private Integer matches_in_another_country;
}
