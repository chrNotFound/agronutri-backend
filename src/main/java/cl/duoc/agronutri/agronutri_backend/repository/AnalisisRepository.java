package cl.duoc.agronutri.agronutri_backend.repository;

import cl.duoc.agronutri.agronutri_backend.model.Analisis;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AnalisisRepository extends JpaRepository<Analisis, String> {

    List<Analisis> findByPlanId(String planId);
}