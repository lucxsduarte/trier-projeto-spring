package br.com.trier.projetospring.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.trier.projetospring.domain.NationalTeam;
import br.com.trier.projetospring.services.CountryService;
import br.com.trier.projetospring.services.NationalTeamService;

@RestController
@RequestMapping(value = "/nationalteam")
public class NationalTeamResource {

	@Autowired
	private NationalTeamService service;
	@Autowired
	private CountryService countryService;
	
	@PostMapping
	public ResponseEntity<NationalTeam> insert(@RequestBody NationalTeam nationalTeam){
		return ResponseEntity.ok(service.save(nationalTeam));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<NationalTeam> update(@PathVariable Integer id, @RequestBody NationalTeam nationalTeam){
		nationalTeam.setId(id);
		return ResponseEntity.ok(service.update(nationalTeam));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<NationalTeam> findById(@PathVariable Integer id){
		return ResponseEntity.ok(service.findById(id));
	}
	
	@GetMapping
	public ResponseEntity<List<NationalTeam>> listAll(){
		return ResponseEntity.ok(service.listAll());
	}
	
	@GetMapping("/name/{name}")
	public ResponseEntity<List<NationalTeam>> findByNameIgnoreCase(@PathVariable String name){
		return ResponseEntity.ok(service.findByNameIgnoreCase(name));
	}
	
	@GetMapping("/name/contains/{name}")
	public ResponseEntity<List<NationalTeam>> findByNameContainsIgnoreCase(@PathVariable String name){
		return ResponseEntity.ok(service.findByNameContainsIgnoreCase(name));
	}
	
	@GetMapping("/name/starts/{name}")
	public ResponseEntity<List<NationalTeam>> findByNameStartsWithIgnoreCase(@PathVariable String name){
		return ResponseEntity.ok(service.findByNameStartsWithIgnoreCase(name));
	}
	
	@GetMapping("/country/{id}")
	public ResponseEntity<List<NationalTeam>> findByCountry(@PathVariable Integer country_id){
		return ResponseEntity.ok(service.findByCountry(countryService.findById(country_id)));
	}
}
