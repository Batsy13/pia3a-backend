package br.com.project.pi.application.model;

import br.com.project.pi.application.dto.CreatedPlaceDTO;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    @ElementCollection
    @CollectionTable(name = "place_images", joinColumns = @JoinColumn(name = "place_id"))
    @Column(name = "image_url")
    private List<String> images = new ArrayList<>();
    private LocalDateTime addedAt;

    @ManyToOne
    @JoinColumn(name = "list_id")
    private Lists list;

    public Place(){}

    public Place(LocalDateTime addedAt, String description, Long id, List<String> images, Lists list, String name) {
        this.addedAt = addedAt;
        this.description = description;
        this.id = id;
        this.images = images;
        this.list = list;
        this.name = name;
    }

    public Place(@Valid CreatedPlaceDTO dto) {
        this.name = dto.name();
        this.description = dto.description();
        this.images = dto.images();
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

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
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
