package com.wwwgomes.bookstoremanager.api.resource;

import com.wwwgomes.bookstoremanager.api.dto.JwtRequest;
import com.wwwgomes.bookstoremanager.api.dto.JwtResponse;
import com.wwwgomes.bookstoremanager.api.dto.MessageDTO;
import com.wwwgomes.bookstoremanager.api.dto.UserDTO;
import com.wwwgomes.bookstoremanager.api.resource.docs.UserResourceDocs;
import com.wwwgomes.bookstoremanager.api.service.AuthenticationService;
import com.wwwgomes.bookstoremanager.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/users")
public class UserResource implements UserResourceDocs {

    private final UserService userService;

    private final AuthenticationService authenticationService;

    @Autowired
    public UserResource(UserService userService, AuthenticationService authenticationService) {
        this.userService = userService;
        this.authenticationService = authenticationService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MessageDTO create(@RequestBody @Valid UserDTO userToCreateDTO) {
        return userService.create(userToCreateDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }

    @PutMapping("/{id}")
    public MessageDTO update(@PathVariable Long id, @RequestBody @Valid UserDTO userToUpdateDTO) {
        return userService.update(id, userToUpdateDTO);
    }

    @PostMapping(value = "/authenticate")
    public JwtResponse createAuthenticationToken(@RequestBody @Valid JwtRequest jwtRequest) {
        return authenticationService.createAuthenticationToken(jwtRequest);
    }
}
