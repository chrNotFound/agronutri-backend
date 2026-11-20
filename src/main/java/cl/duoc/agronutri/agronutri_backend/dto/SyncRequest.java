package cl.duoc.agronutri.agronutri_backend.dto;

import cl.duoc.agronutri.agronutri_backend.model.Analisis;
import cl.duoc.agronutri.agronutri_backend.model.Plan;
import cl.duoc.agronutri.agronutri_backend.model.Programa;
import java.util.List;

public record SyncRequest(
        List<Plan> planes,
        List<Analisis> analisis,
        List<Programa> programas
) {
}