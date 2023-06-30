package br.com.trier.projetospring.domain.dto;

import java.util.List;

import br.com.trier.projetospring.domain.Championship;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChampionshipByNationalTeamsDTO {

	private String national_team_name;
	private List<Championship> championships;
}
