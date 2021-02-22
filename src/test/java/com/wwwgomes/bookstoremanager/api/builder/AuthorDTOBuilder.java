package com.wwwgomes.bookstoremanager.api.builder;

import com.wwwgomes.bookstoremanager.api.dto.AuthorDTO;
import lombok.Builder;

@Builder
public class AuthorDTOBuilder {

    @Builder.Default
    private final Long id = 1L;

    @Builder.Default
    private final String name = "Williams Gomes";

    @Builder.Default
    private final int age = 27;

    public AuthorDTO buildAuthorDTO() {
        return new AuthorDTO(id, name, age);
    }
}
