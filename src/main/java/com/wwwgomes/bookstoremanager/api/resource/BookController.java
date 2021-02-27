package com.wwwgomes.bookstoremanager.api.resource;

import com.wwwgomes.bookstoremanager.api.dto.AuthenticatedUser;
import com.wwwgomes.bookstoremanager.api.dto.BookRequestDTO;
import com.wwwgomes.bookstoremanager.api.dto.BookResponseDTO;
import com.wwwgomes.bookstoremanager.api.resource.docs.BookControllerDocs;
import com.wwwgomes.bookstoremanager.api.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
public class BookController implements BookControllerDocs {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookResponseDTO create(
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser,
            @RequestBody @Valid BookRequestDTO bookRequestDTO) {
        return bookService.create(authenticatedUser, bookRequestDTO);
    }

    @GetMapping("/{bookId}")
    public BookResponseDTO findByIdAndUser(
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser,
            @PathVariable Long bookId) {
        return bookService.findByIdAndUser(authenticatedUser, bookId);
    }

    @GetMapping
    public List<BookResponseDTO> findAllByUser(@AuthenticationPrincipal AuthenticatedUser authenticatedUser) {
        return bookService.findAllByUser(authenticatedUser);
    }

    @DeleteMapping("/{bookId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteByIdAndUser(
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser,
            @PathVariable Long bookId) {
        bookService.deleteByIdAndUser(authenticatedUser, bookId);
    }

    @PutMapping("/{bookId}")
    public BookResponseDTO updateByIdAndUser(
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser,
            @PathVariable Long bookId,
            @RequestBody @Valid BookRequestDTO bookRequestDTO) {
        return bookService.updateByIdAndUser(authenticatedUser, bookId, bookRequestDTO);
    }
}
