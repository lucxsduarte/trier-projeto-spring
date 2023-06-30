package br.com.trier.projetospring.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MatchDTO {

	private Integer id;
	private String date;
	private Integer country_id;
	private String country_name;
	private Integer championship_id;
	private String championship_name;
	private Integer championship_year;
}
