package com.wwwgomes.bookstoremanager.api.resource;

import com.wwwgomes.bookstoremanager.api.dto.PublisherDTO;
import com.wwwgomes.bookstoremanager.api.resource.docs.PublisherControllerDocs;
import com.wwwgomes.bookstoremanager.api.service.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/publishers")
public class PublisherResource implements PublisherControllerDocs {

    private final PublisherService publisherService;

    @Autowired
    public PublisherResource(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Override
    public PublisherDTO create(@RequestBody @Valid PublisherDTO publisherDTO) {
        return publisherService.create(publisherDTO);
    }

    @Override
    @GetMapping("/{id}")
    public PublisherDTO findById(@PathVariable Long id) {
        return publisherService.findById(id);
    }

    @GetMapping
    @Override
    public List<PublisherDTO> findAll() {
        return publisherService.findAll();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Override
    public void delete(@PathVariable Long id) {
        publisherService.delete(id);
    }
}
