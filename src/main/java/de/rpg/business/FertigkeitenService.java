package de.rpg.business;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

import de.rpg.character.Fertigkeit;
import de.rpg.erschaffung.FertigkeitSpezifikation;
import de.rpg.persistenz.FertigkeitRepository;
import de.rpg.persistenz.FertigkeitSpezifikationRepository;
import de.rpg.view.Option;
import jakarta.persistence.EntityNotFoundException;

@Service
public class FertigkeitenService {
	
	private final FertigkeitRepository fertigkeitRepository;
	private final FertigkeitSpezifikationRepository fertigkeitSpezifikationRepository;
	
	public FertigkeitenService(FertigkeitRepository fertigkeitRepository,
			FertigkeitSpezifikationRepository fertigkeitSpezifikationRepository) {
		super();
		this.fertigkeitRepository = fertigkeitRepository;
		this.fertigkeitSpezifikationRepository = fertigkeitSpezifikationRepository;
	}

	public List<Option> getAllFertigkeitenOptionen() {
		return StreamSupport.stream(fertigkeitSpezifikationRepository.findAll().spliterator(), false)
			.map(fert -> Option.builder().text(fert.getName()).value(String.valueOf(fert.getId())).build())
			.toList();
	}
	
	public Iterable<FertigkeitSpezifikation> getAllFertigkeiten() {
		return fertigkeitSpezifikationRepository.findAll();
	}

	public FertigkeitSpezifikation findSpecById(String value) {
		Long id = Long.parseLong(value);
		Optional<FertigkeitSpezifikation> optional = fertigkeitSpezifikationRepository.findById(id);
		return optional.orElseThrow(() -> new EntityNotFoundException());
	}

	public Fertigkeit save(Fertigkeit fertigkeit) {
		Fertigkeit saved = fertigkeitRepository.save(fertigkeit);
		return saved;
	}

	public Fertigkeit findById(Long id) {
		Optional<Fertigkeit> optional = fertigkeitRepository.findById(id);
		return optional.orElseThrow(() -> new EntityNotFoundException());
	}
}
