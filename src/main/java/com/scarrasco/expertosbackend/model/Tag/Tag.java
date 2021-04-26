package com.scarrasco.expertosbackend.model.Tag;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.scarrasco.expertosbackend.model.Expert.Expert;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tags")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty("Clave primaria: Long")
    private Long id;

    @Column(nullable = false)
    @ApiModelProperty("Nombre de la etiqueta: String")
    private String name;

    @ApiModelProperty("Nombre de quién creó la etiqueta: String")
    private String creator;

    @Column(name = "created_at" ,nullable = false)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("Fecha de creación: LocalDateTime")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("Fecha de actualización: LocalDateTime")
    private LocalDateTime updatedAt;

    @ManyToMany(mappedBy = "tags")
    @JsonIgnore
    @ApiModelProperty("Lista de expertos asociados a una etiqueta: List<Expert>")
    private List<Expert> experts = new ArrayList<>();

    public Tag() {
    }

    public Tag(String name, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.name = name;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public Tag setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Tag setName(String name) {
        this.name = name;
        return this;
    }

    public String getCreator() {
        return creator;
    }

    public Tag setCreator(String creator) {
        this.creator = creator;
        return this;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Tag setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public Tag setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public List<Expert> getExperts() {
        return experts;
    }

    public Tag setExperts(List<Expert> experts) {
        this.experts = experts;
        return this;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", creator='" + creator + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
