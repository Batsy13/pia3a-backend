package br.com.project.pi.application.model;


import br.com.project.pi.application.dto.CreatedListRequest;
import br.com.project.pi.application.dto.ListsDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_list")
public class Lists {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String icon;

    @OneToMany(mappedBy = "list", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private java.util.List<Place> places;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    public Lists(String icon, Long id, String name, List<Place> places, User user) {
        this.icon = icon;
        this.id = id;
        this.name = name;
        this.places = places;
        this.user = user;
    }

    public Lists(ListsDTO list) {
        this.id = list.id();
        this.name = list.name();
        this.icon = list.icon();
    }

    public Lists(CreatedListRequest list) {
        this.name = list.name();
        this.icon = list.icon();
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Place> getPlaces() {
        return places;
    }

    public void setPlaces(List<Place> places) {
        this.places = places;
    }
}
