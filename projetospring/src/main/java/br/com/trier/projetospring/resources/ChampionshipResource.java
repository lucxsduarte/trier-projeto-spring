package br.com.trier.projetospring.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.trier.projetospring.domain.Championship;
import br.com.trier.projetospring.services.ChampionshipService;

@RestController
@RequestMapping(value = "/championship")
public class ChampionshipResource {
	
	@Autowired
	private ChampionshipService service;
	
	@Secured({"ROLE_ADMIN"})
	@PostMapping
	public ResponseEntity<Championship> insert(@RequestBody Championship country){
		return ResponseEntity.ok(service.save(country));
	}
	
	@Secured({"ROLE_ADMIN"})
	@PutMapping("/{id}")
	public ResponseEntity<Championship> update(@PathVariable Integer id, @RequestBody Championship country){
		country.setId(id);
		return ResponseEntity.ok(service.update(country));
	}
	
	@Secured({"ROLE_ADMIN"})
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.ok().build();
	}
	
	@Secured({"ROLE_USER"})
	@GetMapping("/{id}")
	public ResponseEntity<Championship> findById(@PathVariable Integer id){
		return ResponseEntity.ok(service.findById(id));
	}
	
	@Secured({"ROLE_USER"})
	@GetMapping
	public ResponseEntity<List<Championship>> listAll(){
		return ResponseEntity.ok(service.listAll());
	}
	
	@Secured({"ROLE_USER"})
	@GetMapping("/name/{name}")
	public ResponseEntity<List<Championship>> findByName(@PathVariable String name){
		return ResponseEntity.ok(service.findByNameIgnoreCase(name));
	}
	
	@Secured({"ROLE_USER"})
	@GetMapping("/name/contains/{name}")
	public ResponseEntity<List<Championship>> findByNameContains(@PathVariable String name){
		return ResponseEntity.ok(service.findByNameContainsIgnoreCase(name));
	}
	
	@Secured({"ROLE_USER"})
	@GetMapping("/name/starts/{name}")
	public ResponseEntity<List<Championship>> findByNameStarts(@PathVariable String name){
		return ResponseEntity.ok(service.findByNameStartsWithIgnoreCase(name));
	}
	
	@Secured({"ROLE_USER"})
	@GetMapping("/year/{year}")
	public ResponseEntity<List<Championship>> findByYear(@PathVariable Integer year){
		return ResponseEntity.ok(service.findByYear(year));
	}
	
	@Secured({"ROLE_USER"})
	@GetMapping("/year/name/{year}/{name}")
	public ResponseEntity<List<Championship>> findByYearAndName(@PathVariable Integer year, @PathVariable String name){
		return ResponseEntity.ok(service.findByYearAndName(year, name));
	}
	
	@Secured({"ROLE_USER"})
	@GetMapping("/year/between/{initialYear}/{finalYear}")
	public ResponseEntity<List<Championship>> findByYearBetween(@PathVariable Integer initialYear, @PathVariable Integer finalYear){
		return ResponseEntity.ok(service.findByYearBetween(initialYear, finalYear));
	}
	
	@Secured({"ROLE_USER"})
	@GetMapping("/year/greater/{year}")
	public ResponseEntity<List<Championship>> findByYearGreaterThanEqual(@PathVariable Integer year){
		return ResponseEntity.ok(service.findByYearGreaterThanEqual(year));
	}
	
	@Secured({"ROLE_USER"})
	@GetMapping("/year/less/{year}")
	public ResponseEntity<List<Championship>> findByYearLessThanEqual(@PathVariable Integer year){
		return ResponseEntity.ok(service.findByYearLessThanEqual(year));
	}

}
