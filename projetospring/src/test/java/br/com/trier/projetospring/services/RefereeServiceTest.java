package br.com.trier.projetospring.services;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.trier.projetospring.BaseTests;
import jakarta.transaction.Transactional;

@Transactional
public class RefereeServiceTest extends BaseTests{

	@Autowired
	RefereeService refereeService;
	@Autowired
	CountryService countryService;
}
