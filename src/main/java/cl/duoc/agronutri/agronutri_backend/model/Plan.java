package cl.duoc.agronutri.agronutri_backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "plan")
public class Plan {

    @Id
    public String id;
    public String predio;
    public String cuartel;
    public String cultivo;
    public String etapaFenologica;
    public Long creadoEn;
}