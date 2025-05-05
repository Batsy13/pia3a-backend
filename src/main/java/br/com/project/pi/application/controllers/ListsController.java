package br.com.project.pi.application.controllers;

import br.com.project.pi.application.services.ListsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
