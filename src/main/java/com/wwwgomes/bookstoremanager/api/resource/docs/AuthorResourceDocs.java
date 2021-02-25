package com.wwwgomes.bookstoremanager.api.resource.docs;

import com.wwwgomes.bookstoremanager.api.dto.AuthorDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api("Authors management")
public interface AuthorResourceDocs {

    @ApiOperation(value = "Author creation operation")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Success author creation"),
            @ApiResponse(code = 400,
                    message = "Missing required fields, wrong field range value or author already registered on system")
    })
    AuthorDTO create(AuthorDTO authorDTO);

    @ApiOperation(value = "Find author by id operation.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Success author found"),
            @ApiResponse(code = 400, message = "Author not found error code.")
    })
    AuthorDTO findById(Long id);
}
