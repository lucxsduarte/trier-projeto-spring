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

import br.com.trier.projetospring.domain.Country;
import br.com.trier.projetospring.services.CountryService;

@RestController
@RequestMapping(value = "/country")
public class CountryResource {
	
	@Autowired
	private CountryService service;
	
	@PostMapping
	public ResponseEntity<Country> insert(@RequestBody Country country){
		return ResponseEntity.ok(service.save(country));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Country> update(@PathVariable Integer id, @RequestBody Country country){
		country.setId(id);
		return ResponseEntity.ok(service.update(country));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Country> findById(@PathVariable Integer id){
		return ResponseEntity.ok(service.findById(id));
	}
	
	@GetMapping
	public ResponseEntity<List<Country>> listAll(){
		return ResponseEntity.ok(service.listAll());
	}
	
	@GetMapping("/name/{name}")
	public ResponseEntity<List<Country>> findByNameIgnoreCase(@PathVariable String name){
		return ResponseEntity.ok(service.findByNameIgnoreCase(name));
	}
	
	@GetMapping("/name/contains/{name}")
	public ResponseEntity<List<Country>> findByNameContains(@PathVariable String name){
		return ResponseEntity.ok(service.findByNameContains(name));
	}
	
	@GetMapping("/name/starts/{name}")
	public ResponseEntity<List<Country>> findByNameStartsWith(@PathVariable String name){
		return ResponseEntity.ok(service.findByNameStartsWith(name));
	}

}
