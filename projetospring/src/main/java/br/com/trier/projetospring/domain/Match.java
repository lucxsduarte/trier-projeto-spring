package br.com.trier.projetospring.domain;

import java.time.ZonedDateTime;

import br.com.trier.projetospring.domain.dto.MatchDTO;
import br.com.trier.projetospring.utils.DateUtils;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity(name = "match")
public class Match {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "match_id")
	@Setter
	private Integer id;
	
	@Column(name = "match_date")
	private ZonedDateTime date;
	
	@ManyToOne
	private Country country;
	
	@ManyToOne
	private Championship championship;
	
	public MatchDTO toDTO() {
		return new MatchDTO(id, DateUtils.zoneDateTimeToStr(date), country.getId(), country.getName(), championship.getId(), championship.getName(), championship.getYear());
	}
	
	public Match(MatchDTO dto) {
		this(dto.getId(), 
				DateUtils.strToZonedDateTime(dto.getDate()), 
				new Country(dto.getCountry_id(), null), 
				new Championship(dto.getChampionship_id(), null, null));
	}
	
	public Match(MatchDTO dto, Country country, Championship championship) {
		this(dto.getId(), 
				DateUtils.strToZonedDateTime(dto.getDate()), 
				country, 
				championship);
	}
}
