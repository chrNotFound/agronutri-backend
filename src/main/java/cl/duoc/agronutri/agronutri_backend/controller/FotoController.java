package cl.duoc.agronutri.agronutri_backend.controller;

import cl.duoc.agronutri.agronutri_backend.model.FotoFoliar;
import cl.duoc.agronutri.agronutri_backend.repository.FotoFoliarRepository;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class FotoController {

    private final FotoFoliarRepository fotos;
    private final Path carpeta;

    public FotoController(FotoFoliarRepository fotos,
                          @Value("${agronutri.carpeta-fotos}") String carpeta) throws IOException {
        this.fotos = fotos;
        this.carpeta = Paths.get(carpeta).toAbsolutePath().normalize();
        Files.createDirectories(this.carpeta);
    }

    @PostMapping("/api/fotos")
    public ResponseEntity<Void> subir(
            @RequestParam("archivo") MultipartFile archivo,
            @RequestParam("id") String id,
            @RequestParam("planId") String planId,
            @RequestParam(value = "nota", defaultValue = "") String nota) throws IOException {

        validarId(id);
        if (archivo.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El archivo viene vacío");
        }

        String nombre = id + ".jpg";
        Files.copy(archivo.getInputStream(), carpeta.resolve(nombre), StandardCopyOption.REPLACE_EXISTING);

        FotoFoliar foto = new FotoFoliar();
        foto.id = id;
        foto.planId = planId;
        foto.nota = nota.isBlank() ? null : nota;
        foto.nombreArchivo = nombre;
        foto.recibidaEn = System.currentTimeMillis();
        fotos.save(foto);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/api/fotos/{id}/archivo")
    public ResponseEntity<Resource> descargar(@PathVariable String id) {
        validarId(id);
        FotoFoliar foto = fotos.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Foto no encontrada"));
        Path ruta = carpeta.resolve(foto.nombreArchivo);
        if (!Files.exists(ruta)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "El archivo ya no está en el servidor");
        }
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(new FileSystemResource(ruta));
    }

    private void validarId(String id) {
        if (!id.matches("[A-Za-z0-9-]{1,64}")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "id inválido");
        }
    }
}