package com.scarrasco.expertosbackend.model.Expert;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.scarrasco.expertosbackend.model.Tag.Tag;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "experts")
public class Expert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("Clave primaria: Long")
    private Long id;

    @Column(nullable = false)
    @ApiModelProperty("Nombre del experto: String")
    private String name;

    @Column(name = "created_at" ,nullable = false)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("Fecha de creación: LocalDateTime")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("Fecha de actualización: LocalDateTime")
    private LocalDateTime updatedAt;

    @Column(name = "status_motive")
    @ApiModelProperty("Estado - Motivo: String")
    private String statusMotive;

    @Enumerated(EnumType.STRING)
    @ApiModelProperty("Disponibilidad: ExpertAvailability Enum")
    private ExpertAvailability availability;

    @ApiModelProperty("Modalidad: String")
    private String modality;

    @Column(columnDefinition = "boolean default false")
    @ApiModelProperty("Autonomo: Boolean")
    private Boolean freelance;

    @Column(name = "contact_phone", nullable = false,  unique = true)
    @ApiModelProperty("Telefono de contacto: String")
    private String contactPhone;

    @Column(name = "contact_email", nullable = false,  unique = true)
    @ApiModelProperty("Correo electrónico de contacto: String")
    private String contactEmail;

    @Column(name = "contact_city", nullable = false)
    @ApiModelProperty("Ciudad del experto: String")
    private String contactCity;

    // No está marcado como null
    @Column(name = "contact_linkedin")
    @ApiModelProperty("Linkedin del experto: String")
    private String contactLinkedin;

    @Column(name = "conditions_percent")
    @ApiModelProperty("Porcentaje de las condiciones firmadas: Double")
    private Double conditionsPercent;

    @Column(name = "conditions_price_hour")
    @ApiModelProperty("Precio por hora firmadas: Double")
    private Double conditionsPriceHour;

    // No está marcado como null
    @Column(columnDefinition = "varchar(255) default 'falta'")
    @ApiModelProperty("Puntuación del experto: String")
    private String score;

    @Column(nullable = false, unique = true)
    @ApiModelProperty("Nif: String")
    private String nif;

    @Column(name = "email_credentials", columnDefinition = "varchar(255) default 'Hola'")
    @ApiModelProperty("Credenciales del email: String")
    private String emailCredentials;

    // Está marcada como cadena vacia
    @Column(name = "email_credentials_password", columnDefinition = "varchar(255) default ''")
    @ApiModelProperty("Credenciales contraseña del email: String")
    private String emailCredentialsPassword;

    // Está marcada como cadena vacia
    @Column(name = "zoom_credentials", columnDefinition = "varchar(255) default 'e'")
    @ApiModelProperty("Credenciales de zoom: String")
    private String zoomCredentials;

    // Está marcada como cadena vacia
    @Column(name = "zoom_credentials_password", columnDefinition = "varchar(255) default ''")
    @ApiModelProperty("Credenciales contraseña de zoom: String")
    private String zoomCredentialsPassword;

    @Column(name = "photo_file")
    @ApiModelProperty("Foto del experto: String")
    private String photoFile;

    @Column(name = "cv_file")
    @ApiModelProperty("CV del experto: String")
    private String cvFile;

    @ApiModelProperty("Observaciones: String")
    private String observations;

    @ApiModelProperty("Origen: String")
    private String origin;

    @Column(nullable = false, columnDefinition = "varchar(255) default 'pendiente'")
    @Enumerated(EnumType.STRING)
    @ApiModelProperty("Estado del Experto: ExpertStatus Enum")
    private ExpertStatus status;

    @ManyToMany
    @JoinTable(
            name = "experts_tags",
            joinColumns = {@JoinColumn(name="id_expert", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name="id_tag", referencedColumnName = "id")},
            uniqueConstraints = {@UniqueConstraint(columnNames = { "id_expert", "id_tag" })}
    )
    @ApiModelProperty("Lista de etiquetas asociadas a un experto: List<Tag>")
    private List<Tag> tags = new ArrayList<>();

    public Expert() {
    }

    public Expert(String name, LocalDateTime createdAt, LocalDateTime updatedAt, String statusMotive, ExpertAvailability availability, String modality, Boolean freelance, String contactPhone, String contactEmail, String contactCity, String contactLinkedin, Double conditionsPercent, Double conditionsPriceHour, String score, String nif, String emailCredentials, String emailCredentialsPassword, String zoomCredentials, String zoomCredentialsPassword, String photoFile, String cvFile, String observations, String origin, ExpertStatus status) {
        this.name = name;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.statusMotive = statusMotive;
        this.availability = availability;
        this.modality = modality;
        this.freelance = freelance;
        this.contactPhone = contactPhone;
        this.contactEmail = contactEmail;
        this.contactCity = contactCity;
        this.contactLinkedin = contactLinkedin;
        this.conditionsPercent = conditionsPercent;
        this.conditionsPriceHour = conditionsPriceHour;
        this.score = score;
        this.nif = nif;
        this.emailCredentials = emailCredentials;
        this.emailCredentialsPassword = emailCredentialsPassword;
        this.zoomCredentials = zoomCredentials;
        this.zoomCredentialsPassword = zoomCredentialsPassword;
        this.photoFile = photoFile;
        this.cvFile = cvFile;
        this.observations = observations;
        this.origin = origin;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public Expert setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Expert setName(String name) {
        this.name = name;
        return this;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Expert setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public Expert setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public String getStatusMotive() {
        return statusMotive;
    }

    public Expert setStatusMotive(String statusMotive) {
        this.statusMotive = statusMotive;
        return this;
    }

    public ExpertAvailability getAvailability() {
        return availability;
    }

    public Expert setAvailability(ExpertAvailability availability) {
        this.availability = availability;
        return this;
    }

    public String getModality() {
        return modality;
    }

    public Expert setModality(String modality) {
        this.modality = modality;
        return this;
    }

    public Boolean getFreelance() {
        return freelance;
    }

    public Expert setFreelance(Boolean freelance) {
        this.freelance = freelance;
        return this;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public Expert setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
        return this;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public Expert setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
        return this;
    }

    public String getContactCity() {
        return contactCity;
    }

    public Expert setContactCity(String contactCity) {
        this.contactCity = contactCity;
        return this;
    }

    public String getContactLinkedin() {
        return contactLinkedin;
    }

    public Expert setContactLinkedin(String contactLinkedin) {
        this.contactLinkedin = contactLinkedin;
        return this;
    }

    public Double getConditionsPercent() {
        return conditionsPercent;
    }

    public Expert setConditionsPercent(Double conditionsPercent) {
        this.conditionsPercent = conditionsPercent;
        return this;
    }

    public Double getConditionsPriceHour() {
        return conditionsPriceHour;
    }

    public Expert setConditionsPriceHour(Double conditionsPriceHour) {
        this.conditionsPriceHour = conditionsPriceHour;
        return this;
    }

    public String getScore() {
        return score;
    }

    public Expert setScore(String score) {
        this.score = score;
        return this;
    }

    public String getNif() {
        return nif;
    }

    public Expert setNif(String nif) {
        this.nif = nif;
        return this;
    }

    public String getEmailCredentials() {
        return emailCredentials;
    }

    public Expert setEmailCredentials(String emailCredentials) {
        this.emailCredentials = emailCredentials;
        return this;
    }

    public String getEmailCredentialsPassword() {
        return emailCredentialsPassword;
    }

    public Expert setEmailCredentialsPassword(String emailCredentialsPassword) {
        this.emailCredentialsPassword = emailCredentialsPassword;
        return this;
    }

    public String getZoomCredentials() {
        return zoomCredentials;
    }

    public Expert setZoomCredentials(String zoomCredentials) {
        this.zoomCredentials = zoomCredentials;
        return this;
    }

    public String getZoomCredentialsPassword() {
        return zoomCredentialsPassword;
    }

    public Expert setZoomCredentialsPassword(String zoomCredentialsPassword) {
        this.zoomCredentialsPassword = zoomCredentialsPassword;
        return this;
    }

    public String getPhotoFile() {
        return photoFile;
    }

    public Expert setPhotoFile(String photoFile) {
        this.photoFile = photoFile;
        return this;
    }

    public String getCvFile() {
        return cvFile;
    }

    public Expert setCvFile(String cvFile) {
        this.cvFile = cvFile;
        return this;
    }

    public String getObservations() {
        return observations;
    }

    public Expert setObservations(String observations) {
        this.observations = observations;
        return this;
    }

    public String getOrigin() {
        return origin;
    }

    public Expert setOrigin(String origin) {
        this.origin = origin;
        return this;
    }

    public ExpertStatus getStatus() {
        return status;
    }

    public Expert setStatus(ExpertStatus status) {
        this.status = status;
        return this;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public Expert setTags(List<Tag> tags) {
        this.tags = tags;
        return this;
    }

    @Override
    public String toString() {
        return "Expert{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", statusMotive='" + statusMotive + '\'' +
                ", availability=" + availability +
                ", modality='" + modality + '\'' +
                ", freelance=" + freelance +
                ", contactPhone='" + contactPhone + '\'' +
                ", contactEmail='" + contactEmail + '\'' +
                ", contactCity='" + contactCity + '\'' +
                ", contactLinkedin='" + contactLinkedin + '\'' +
                ", conditionsPercent=" + conditionsPercent +
                ", conditionsPriceHour=" + conditionsPriceHour +
                ", score=" + score +
                ", nif='" + nif + '\'' +
                ", emailCredentials='" + emailCredentials + '\'' +
                ", emailCredentialsPassword='" + emailCredentialsPassword + '\'' +
                ", zoomCredentials='" + zoomCredentials + '\'' +
                ", zoomCredentialsPassword='" + zoomCredentialsPassword + '\'' +
                ", photoFile='" + photoFile + '\'' +
                ", cvFile='" + cvFile + '\'' +
                ", observations='" + observations + '\'' +
                ", origin='" + origin + '\'' +
                ", status=" + status +
                '}';
    }
}
