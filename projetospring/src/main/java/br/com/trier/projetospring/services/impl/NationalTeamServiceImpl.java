package br.com.trier.projetospring.services.impl; 

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.projetospring.domain.Country;
import br.com.trier.projetospring.domain.NationalTeam;
import br.com.trier.projetospring.repositories.NationalTeamRepository;
import br.com.trier.projetospring.services.NationalTeamService;
import br.com.trier.projetospring.services.exceptions.IntegrityViolation;
import br.com.trier.projetospring.services.exceptions.ObjectNotFound;

@Service
public class NationalTeamServiceImpl implements NationalTeamService{

	@Autowired
	NationalTeamRepository repository;
	
	@Override
	public NationalTeam save(NationalTeam nationalTeam) {
		findByName(nationalTeam);
		return repository.save(nationalTeam);
	}

	@Override
	public NationalTeam update(NationalTeam nationalTeam) {
		findById(nationalTeam.getId());
		findByName(nationalTeam);
		return repository.save(nationalTeam);
	}

	@Override
	public void delete(Integer id) {
		NationalTeam newNationalTeam = findById(id);
		repository.delete(newNationalTeam);
		
	}

	@Override
	public NationalTeam findById(Integer id) {
		Optional<NationalTeam> nationalTeam = repository.findById(id);
		return nationalTeam.orElseThrow(() -> new ObjectNotFound("Seleção %s não encontrada".formatted(id)));
	}

	@Override
	public List<NationalTeam> listAll() {
		List<NationalTeam> list = repository.findAll();
		if(list.size() == 0) {
			throw new ObjectNotFound("Nenhuma seleção cadastrada");
		}
		return list;
	}

	@Override
	public List<NationalTeam> findByNameIgnoreCase(String name) {
		List<NationalTeam> list = repository.findByNameIgnoreCase(name);
		if(list.size() == 0) {
			throw new ObjectNotFound("Nenhuma seleção cadastrada com o nome: %s".formatted(name));
		}
		return list;
	}

	@Override
	public List<NationalTeam> findByNameContainsIgnoreCase(String name) {
		List<NationalTeam> list = repository.findByNameContainsIgnoreCase(name);
		if(list.size() == 0) {
			throw new ObjectNotFound("Nenhuma seleção cadastrada contem no nome: %s".formatted(name));
		}
		return list;
	}

	@Override
	public List<NationalTeam> findByNameStartsWithIgnoreCase(String name) {
		List<NationalTeam> list = repository.findByNameStartsWithIgnoreCase(name);
		if(list.size() == 0) {
			throw new ObjectNotFound("Nenhuma seleção cadastrada começa com: %s".formatted(name));
		}
		return list;
	}

	@Override
	public List<NationalTeam> findByCountry(Country country) {
		List<NationalTeam> list = repository.findByCountry(country);
		if(list.size() == 0) {
			throw new ObjectNotFound("Nenhuma seleção cadastrada começa está associada com o país: %s".formatted(country.getName()));
		}
		return list;
	}
	
	private void findByName(NationalTeam nationalTeam) {
		NationalTeam newNationalTeam = repository.findByName(nationalTeam.getName());
		if(newNationalTeam != null && newNationalTeam.getId() != nationalTeam.getId()) {
			throw new IntegrityViolation("Seleçao já cadastrada: %s".formatted(nationalTeam.getName()));
		}
	}
	


}
