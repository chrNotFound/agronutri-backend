package cl.duoc.agronutri.agronutri_backend.controller;

import cl.duoc.agronutri.agronutri_backend.dto.SyncRequest;
import cl.duoc.agronutri.agronutri_backend.model.Analisis;
import cl.duoc.agronutri.agronutri_backend.model.Plan;
import cl.duoc.agronutri.agronutri_backend.model.Programa;
import cl.duoc.agronutri.agronutri_backend.repository.AnalisisRepository;
import cl.duoc.agronutri.agronutri_backend.repository.PlanRepository;
import cl.duoc.agronutri.agronutri_backend.repository.ProgramaRepository;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class SyncController {

    private final PlanRepository planes;
    private final AnalisisRepository analisis;
    private final ProgramaRepository programas;

    public SyncController(PlanRepository planes, AnalisisRepository analisis, ProgramaRepository programas) {
        this.planes = planes;
        this.analisis = analisis;
        this.programas = programas;
    }

    @PostMapping("/api/sync")
    @Transactional
    public Map<String, Integer> sincronizar(@RequestBody SyncRequest datos) {
        List<Plan> listaPlanes = datos.planes() == null ? List.of() : datos.planes();
        List<Analisis> listaAnalisis = datos.analisis() == null ? List.of() : datos.analisis();
        List<Programa> listaProgramas = datos.programas() == null ? List.of() : datos.programas();

        for (Plan p : listaPlanes) {
            exigirId(p.id);
        }
        for (Analisis a : listaAnalisis) {
            exigirId(a.id);
        }
        for (Programa p : listaProgramas) {
            exigirId(p.id);
        }

        planes.saveAll(listaPlanes);
        analisis.saveAll(listaAnalisis);
        programas.saveAll(listaProgramas);

        return Map.of(
                "planes", listaPlanes.size(),
                "analisis", listaAnalisis.size(),
                "programas", listaProgramas.size());
    }

    private void exigirId(String id) {
        if (id == null || id.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Todos los registros deben traer id");
        }
    }
}