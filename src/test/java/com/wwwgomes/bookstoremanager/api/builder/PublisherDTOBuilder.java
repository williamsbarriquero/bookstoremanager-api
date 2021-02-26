package com.wwwgomes.bookstoremanager.api.builder;

import com.wwwgomes.bookstoremanager.api.dto.PublisherDTO;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public class PublisherDTOBuilder {

    @Builder.Default
    private final Long id = 1L;

    private final String name = "Peleias Editora";

    private final String code = "PELE1234";

    private final LocalDate foundationDate = LocalDate.of(2020, 6, 1);

    public PublisherDTO buildPublisherDTO() {
        return new PublisherDTO(id,
                name,
                code,
                foundationDate);
    }

}
