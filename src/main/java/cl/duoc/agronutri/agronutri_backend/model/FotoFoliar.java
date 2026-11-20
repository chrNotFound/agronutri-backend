package cl.duoc.agronutri.agronutri_backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "foto_foliar")
public class FotoFoliar {

    @Id
    public String id;
    public String planId;
    public String nota;
    public String nombreArchivo;
    public Long recibidaEn;
}