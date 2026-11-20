package cl.duoc.agronutri.agronutri_backend.repository;

import cl.duoc.agronutri.agronutri_backend.model.FotoFoliar;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FotoFoliarRepository extends JpaRepository<FotoFoliar, String> {

    List<FotoFoliar> findByPlanId(String planId);
}