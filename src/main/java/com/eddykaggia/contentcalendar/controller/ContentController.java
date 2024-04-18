package com.eddykaggia.contentcalendar.controller;

import com.eddykaggia.contentcalendar.model.Content;
import com.eddykaggia.contentcalendar.repository.ContentCollectionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/content")
public class ContentController {

    private final ContentCollectionRepository repository;

    public ContentController(ContentCollectionRepository repository) {
        this.repository = repository;
    }

    // GET All Records
    @GetMapping("")
    public List<Content> findAll() {
        return repository.findAll();
    }

    // GET Specific Record
    @GetMapping("/{id}")
    //@Pathvariable maps the dynamic path to the input
    public Content findById(@PathVariable Integer id) {
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Content not found!"));
    }

    // POST
    //@ResponseStatus returns a  status code to indicate outcome of posting
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    //@RequestBody indicates that we are posting via the request body
    public void create(@RequestBody Content content) {
        repository.save(content);
    }

    // PUT => UPDATE
    @PutMapping("/{id}")
    public void update(@RequestBody Content content, @PathVariable Integer id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Content not found!");
        }
        repository.save(content);
    }
}
