package br.com.trier.projetospring.services.impl;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.projetospring.domain.Championship;
import br.com.trier.projetospring.domain.Country;
import br.com.trier.projetospring.domain.Match;
import br.com.trier.projetospring.repositories.MatchRepository;
import br.com.trier.projetospring.services.MatchService;
import br.com.trier.projetospring.services.exceptions.IntegrityViolation;
import br.com.trier.projetospring.services.exceptions.ObjectNotFound;

@Service
public class MatchServiceImpl implements MatchService{

	@Autowired
	MatchRepository repository;
	
	@Override
	public Match save(Match match) {
		validateDate(match);
		return repository.save(match);
	}

	@Override
	public Match update(Match match) {
		findById(match.getId());
		return repository.save(match);
	}

	@Override
	public void delete(Integer id) {
		Match match = findById(id);
		repository.delete(match);
		
	}

	@Override
	public Match findById(Integer id) {
		return repository.findById(id).orElseThrow(() -> new ObjectNotFound("Partida %S não encontrada".formatted(id)));
	}

	@Override
	public List<Match> listAll() {
		List<Match> list = repository.findAll();
		if(list.size() == 0) {
			throw new ObjectNotFound("Nenhuma partida cadastrada");
		}
		return list;
	}

	@Override
	public List<Match> findByDate(ZonedDateTime date) {
		List<Match> list = repository.findByDate(date);
		if(list.size() == 0) {
			throw new ObjectNotFound("Nenhuma partida cadastrada na data: %s".formatted(date));
		}
		return list;
	}

	@Override
	public List<Match> findByDateBetween(ZonedDateTime initialDate, ZonedDateTime finalDate) {
		List<Match> list = repository.findByDateBetween(initialDate, finalDate);
		if(list.size() == 0) {
			throw new ObjectNotFound("Nenhuma partida cadastrada entre a data %s e %s".formatted(initialDate, finalDate));
		}
		return list;
	}

	@Override
	public List<Match> findByCountry(Country country) {
		List<Match> list = repository.findByCountry(country);
		if(list.size() == 0) {
			throw new ObjectNotFound("Nenhuma partida cadastrada no país: %s".formatted(country.getId()));
		}
		return list;
	}

	@Override
	public List<Match> findByChampionship(Championship championship) {
		List<Match> list = repository.findByChampionship(championship);
		if(list.size() == 0) {
			throw new ObjectNotFound("Nenhuma partida cadastrada no campeonato: %s".formatted(championship.getId()));
		}
		return list;
	}

	@Override
	public List<Match> findByChampionshipAndCountry(Championship championship, Country country) {
		List<Match> list = repository.findByChampionshipAndCountry(championship, country);
		if(list.size() == 0) {
			throw new ObjectNotFound("Nenhuma partida cadastrada no campeonato %s e no país %s".formatted(championship.getId(), country.getId()));
		}
		return list;
	}

	@Override
	public List<Match> findByDateAndCountry(ZonedDateTime date, Country country) {
		List<Match> list = repository.findByDateAndCountry(date, country);
		if(list.size() == 0) {
			throw new ObjectNotFound("Nenhuma partida cadastrada na data %s e no país %s".formatted(date, country.getId()));
		}
		return list;
	}

	@Override
	public List<Match> findByDateAndChampionship(ZonedDateTime date, Championship championship) {
		List<Match> list = repository.findByDateAndChampionship(date, championship);
		if(list.size() == 0) {
			throw new ObjectNotFound("Nenhuma partida cadastrada na data %s e no campeonato %s".formatted(date, championship.getId()));
		}
		return list;
	}
	
	private void validateDate(Match match) {
		if(match.getDate() == null) {
			throw new IntegrityViolation("Data inválida");
		}
		if(match.getChampionship() == null) {
			throw new IntegrityViolation("Campeonato não pode ser nulo");
		}
		if(match.getDate().getYear() != match.getChampionship().getYear()) {
			throw new IntegrityViolation("Ano da partida não pode ser diferente de um campeonato cadastrado");
		}
	}

}
