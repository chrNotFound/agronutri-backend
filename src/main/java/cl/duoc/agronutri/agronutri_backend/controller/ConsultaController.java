package cl.duoc.agronutri.agronutri_backend.controller;

import cl.duoc.agronutri.agronutri_backend.model.Analisis;
import cl.duoc.agronutri.agronutri_backend.model.FotoFoliar;
import cl.duoc.agronutri.agronutri_backend.model.Plan;
import cl.duoc.agronutri.agronutri_backend.model.Programa;
import cl.duoc.agronutri.agronutri_backend.repository.AnalisisRepository;
import cl.duoc.agronutri.agronutri_backend.repository.FotoFoliarRepository;
import cl.duoc.agronutri.agronutri_backend.repository.PlanRepository;
import cl.duoc.agronutri.agronutri_backend.repository.ProgramaRepository;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConsultaController {

    private final PlanRepository planes;
    private final AnalisisRepository analisis;
    private final ProgramaRepository programas;
    private final FotoFoliarRepository fotos;

    public ConsultaController(PlanRepository planes, AnalisisRepository analisis,
                              ProgramaRepository programas, FotoFoliarRepository fotos) {
        this.planes = planes;
        this.analisis = analisis;
        this.programas = programas;
        this.fotos = fotos;
    }

    @GetMapping("/api/ping")
    public Map<String, String> ping() {
        return Map.of("estado", "ok");
    }

    @GetMapping("/api/planes")
    public List<Plan> planes() {
        return planes.findAll();
    }

    @GetMapping("/api/planes/{planId}/analisis")
    public List<Analisis> analisis(@PathVariable String planId) {
        return analisis.findByPlanId(planId);
    }

    @GetMapping("/api/planes/{planId}/programas")
    public List<Programa> programas(@PathVariable String planId) {
        return programas.findByPlanId(planId);
    }

    @GetMapping("/api/planes/{planId}/fotos")
    public List<FotoFoliar> fotos(@PathVariable String planId) {
        return fotos.findByPlanId(planId);
    }
}