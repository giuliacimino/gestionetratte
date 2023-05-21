package it.prova.gestionetratte.service;

import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.gestionetratte.exception.TrattaNonAnnullataException;
import it.prova.gestionetratte.exception.TrattaNotFoundException;
import it.prova.gestionetratte.model.Stato;
import it.prova.gestionetratte.model.Tratta;
import it.prova.gestionetratte.repository.tratta.TrattaRepository;

@Service
@Transactional(readOnly = true)
public class TrattaServiceImpl implements TrattaService {
	
	@Autowired
	public TrattaRepository repository;

	public List<Tratta> listAllElements(boolean eager) {
		if (eager)
			return (List<Tratta>) repository.findAllTrattaEager();

		return (List<Tratta>) repository.findAll();
	}

	public Tratta caricaSingoloElemento(Long id) {
		return repository.findById(id).orElse(null);

	}

	public Tratta caricaSingoloElementoEager(Long id) {
		return repository.findSingleTrattaEager(id);

	}

	@Transactional
	public Tratta aggiorna(Tratta trattaInstance) {
		return repository.save(trattaInstance);

	}

	@Transactional
	public Tratta inserisciNuovo(Tratta trattaInstance) {
		return repository.save(trattaInstance);

	}

	@Transactional
	public void rimuovi(Long idToRemove) {
			Tratta trattaToBeRemoved = repository.findById(idToRemove)
					.orElseThrow(() -> new TrattaNotFoundException("Tratta not found con id: " + idToRemove));
			
			if (trattaToBeRemoved.getStato() != Stato.ANNULLATA) {
				throw new TrattaNonAnnullataException(
						"non è possibile eliminare una tratta non annullata.");
			}
			repository.deleteById(idToRemove);
		
	}

	public List<Tratta> findByExample(Tratta example) {
		return repository.findByExample(example);

	}

	public List<Tratta> cercaPerCodiceEDescrizione(String codice, String descrizione) {
		return repository.findByCodiceAndDescrizione(codice, descrizione);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Tratta> trovaTratteAttive(Stato stato) {
		stato= Stato.ATTIVA;
		return repository.findTratteAttive(stato);
	}

	@Override
	public List<Tratta> concludiTratte(Stato stato) {
		List<Tratta> tratteAttive= repository.findTratteAttive(stato);
		for (Tratta trattaItem: tratteAttive) {
			if (trattaItem.getOraAtterraggio().isBefore(LocalTime.now())) {
				trattaItem.setStato(Stato.CONCLUSA);
			}
		}
		repository.saveAll(tratteAttive);
		return tratteAttive;
	}
	
	
	
	


	
	
	

}
