package br.com.project.pi.application.model;

import br.com.project.pi.application.dto.CreatedPlaceDTO;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Table(name = "tb_place")
@Entity
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String name;
    private String description;
    private String image;
    private LocalDateTime addedAt;

    @ManyToOne
    @JoinColumn(name = "list_id")
    private Lists list;

    public Place(){}

    public Place(LocalDateTime addedAt, String description, Long id, String image, Lists list, String name) {
        this.addedAt = addedAt;
        this.description = description;
        this.id = id;
        this.image = image;
        this.list = list;
        this.name = name;
    }

    public Place(@Valid CreatedPlaceDTO dto) {
        this.name = dto.name();
        this.description = dto.description();
        this.image = dto.image();
        this.addedAt = dto.addedAt();
    }

    public LocalDateTime getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(LocalDateTime addedAt) {
        this.addedAt = addedAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Lists getList() {
        return list;
    }

    public void setList(Lists list) {
        this.list = list;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
