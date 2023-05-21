package it.prova.gestionetratte.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.gestionetratte.exception.AirbusNotFoundException;
import it.prova.gestionetratte.exception.TratteAirbusNotNullException;
import it.prova.gestionetratte.model.Airbus;
import it.prova.gestionetratte.repository.airbus.AirbusRepository;

@Service
@Transactional(readOnly = true)
public class AirbusServiceImpl implements AirbusService {
	
	@Autowired
	private AirbusRepository repository;

	public List<Airbus> listAllElements() {
		return (List<Airbus>) repository.findAll();
	}

	public List<Airbus> listAllElementsEager() {
		return (List<Airbus>) repository.findAllEager();

	}

	public Airbus caricaSingoloElemento(Long id) {
		return repository.findById(id).orElse(null);

	}

	public Airbus caricaSingoloElementoConTratte(Long id) {
		return repository.findByIdEager(id);

	}
	
	@Transactional
	public Airbus aggiorna(Airbus airbusInstance) {
		return repository.save(airbusInstance);

	}
	
	@Transactional
	public Airbus inserisciNuovo(Airbus airbusInstance) {
		return repository.save(airbusInstance);

	}
	
	@Transactional
	public void rimuovi(Long idToRemove) {
		Airbus airbusToDelete = repository.findById(idToRemove)
				.orElseThrow(() -> new AirbusNotFoundException("Airbus not found con id: " + idToRemove));
		if (airbusToDelete == null) {
			throw new AirbusNotFoundException("Airbus not found con id: "+ idToRemove);
		}
		if (!airbusToDelete.getTratte().isEmpty()) {
			throw new TratteAirbusNotNullException("non è possibile eliminare un airbus con tratte!");
		}
		repository.deleteById(idToRemove);
	}

	public List<Airbus> findByExample(Airbus example) {
		return repository.findByExample(example);

	}

	@Override
	public Airbus cercaPerCodiceEDescrizione(String codice, String descrizione) {
		return repository.findByCodiceAndDescrizione(codice, descrizione);

	}
	
	
	
	

}
