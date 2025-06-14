package br.com.project.pi.application.model;


import br.com.project.pi.application.dto.CreatedListRequestDTO;
import br.com.project.pi.application.dto.ListsDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor

@Entity
@Table(name = "tb_list")
public class Lists {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String icon;

    @OneToMany(mappedBy = "list", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Place> places = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Lists(){}

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

    public Lists(CreatedListRequestDTO list) {
        this.name = list.name();
        this.icon = list.icon();
        if(list.places() != null){
            this.places = list.places().stream().map(p -> {
                Place place = new Place();
                place.setName(p.name());
                place.setDescription(p.description());
                place.setImages(p.images());
                place.setAddedAt(p.addedAt());
                place.setList(this);
                return place;
            }).collect(Collectors.toList());
        }
        else {
            this.places = new ArrayList<>();
        }
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
