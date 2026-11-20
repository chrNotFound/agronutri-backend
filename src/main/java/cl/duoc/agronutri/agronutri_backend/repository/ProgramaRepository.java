package cl.duoc.agronutri.agronutri_backend.repository;

import cl.duoc.agronutri.agronutri_backend.model.Programa;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProgramaRepository extends JpaRepository<Programa, String> {

    List<Programa> findByPlanId(String planId);
}