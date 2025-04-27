package br.com.project.pi.application.controllers;

import br.com.project.pi.application.services.ListsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("lists")
public class ListsController {

    @Autowired
    private ListsService service;


}
