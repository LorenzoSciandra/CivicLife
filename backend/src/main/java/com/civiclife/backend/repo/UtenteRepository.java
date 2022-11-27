package com.civiclife.backend.repo;
import com.civiclife.backend.model.Utente;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface UtenteRepository extends CrudRepository<Utente, Long> {
    List<Utente> findByAge(int age);
}

