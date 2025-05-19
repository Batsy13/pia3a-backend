package br.com.project.pi.application.controllers;

import br.com.project.pi.application.dto.CreatedListRequestDTO;
import br.com.project.pi.application.services.ListsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("lists")
public class ListsController {

    @Autowired
    private ListsService service;

    @GetMapping
    public ResponseEntity findAll() {
        var list = service.findAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable Long id) {
        var list = service.findById(id);
        return ResponseEntity.ok(list);
    }

    @PostMapping
    public ResponseEntity createdList(@RequestBody CreatedListRequestDTO dto){
        var list = service.createdListsPlace(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(list);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteListById(@PathVariable Long id) throws Exception {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
