package cl.duoc.agronutri.agronutri_backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "programa")
public class Programa {

    @Id
    public String id;
    public String planId;
    public String cultivo;
    public String etapa;
    public String frecuencia;
    @Column(columnDefinition = "TEXT")
    public String detalle;
    @Column(columnDefinition = "TEXT")
    public String alertas;
    public String estado;
    @Column(columnDefinition = "TEXT")
    public String observacionAsesor;
    public Long creadoEn;
}