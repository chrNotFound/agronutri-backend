package cl.duoc.agronutri.agronutri_backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "analisis")
public class Analisis {

    @Id
    public String id;
    public String planId;
    public String tipo;
    public Double ph;
    public Double ce;
    @Column(name = "n")
    public Double n;
    @Column(name = "p")
    public Double p;
    @Column(name = "k")
    public Double k;
    @Column(name = "ca")
    public Double ca;
    @Column(name = "mg")
    public Double mg;
    public Boolean verificado;
    public Long creadoEn;
}