package cl.duoc.agronutri.agronutri_backend.repository;

import cl.duoc.agronutri.agronutri_backend.model.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanRepository extends JpaRepository<Plan, String> {
}