package it.prova.gestionetratte;

import java.time.LocalDate;
import java.time.LocalTime;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import it.prova.gestionetratte.model.Airbus;
import it.prova.gestionetratte.model.Stato;
import it.prova.gestionetratte.model.Tratta;
import it.prova.gestionetratte.service.AirbusService;
import it.prova.gestionetratte.service.TrattaService;

@SpringBootApplication
public class GestionetratteApplication implements CommandLineRunner {
	
	@Autowired
	AirbusService airbusService;
	
	@Autowired
	TrattaService trattaService;

	public static void main(String[] args) {
		SpringApplication.run(GestionetratteApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		String codice1 = "SA6D";
		String descrizione1 = "descrizione1";
		Airbus airbus1 = airbusService.cercaPerCodiceEDescrizione(codice1, descrizione1);

		if (airbus1 == null) {
			airbus1 = new Airbus(codice1, descrizione1, LocalDate.of(2013, 12, 18), 389);
			airbusService.inserisciNuovo(airbus1);
		}

		Tratta tratta1 = new Tratta("GGWG", "descrizioneTratta1", LocalDate.of(2022, 12, 19), LocalTime.of(15, 05), LocalTime.of(17, 30),Stato.ATTIVA, airbus1);
		if (trattaService.cercaPerCodiceEDescrizione(tratta1.getCodice(),tratta1.getDescrizione()).isEmpty())
			trattaService.inserisciNuovo(tratta1);

		String codice2 = "FSF5HG ";
		String descrizione2 = "descrizione2";
		Airbus airbus2 = airbusService.cercaPerCodiceEDescrizione(codice2, descrizione2);

		if (airbus2 == null) {
			airbus2 = new Airbus(codice2, descrizione2, LocalDate.of(2017, 11, 27), 456);
			airbusService.inserisciNuovo(airbus2);
		}

		Tratta tratta2 = new Tratta("FHDH", "descrizione2", LocalDate.of(2021, 2, 19), LocalTime.of(17, 8), LocalTime.of(21, 42), Stato.CONCLUSA, airbus2);
		if (trattaService.cercaPerCodiceEDescrizione(tratta2.getCodice(), tratta2.getDescrizione()).isEmpty())
			trattaService.inserisciNuovo(tratta2);
		
	}

}
