package br.com.dicasdeumdev.api.resources;

import br.com.dicasdeumdev.api.domain.dto.UserDTO;
import br.com.dicasdeumdev.api.repositories.UserRepository;
import br.com.dicasdeumdev.api.services.UserService;
import br.com.dicasdeumdev.api.services.exceptions.DataIntegratyViolationException;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/user")
public class UserResource {

    private final UserService service;
    private final ModelMapper mapper;
    private final UserRepository repository;

    public UserResource(UserService service, ModelMapper mapper, UserRepository repository) {
        this.service = service;
        this.mapper = mapper;
        this.repository = repository;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok().body(mapper.map(service.findById(id), UserDTO.class));
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> findAll() {
        return ResponseEntity.ok()
                .body(service.findAll()
                        .stream()
                        .map(user -> mapper.map(user, UserDTO.class)).toList());
    }

    @PostMapping
    public ResponseEntity<UserDTO> create(@RequestBody UserDTO obj) {
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.map(service.create(obj), UserDTO.class));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> update(@PathVariable Integer id, @RequestBody UserDTO obj) {
        return repository.findById(id)
                .map(user -> {
                    user.setName(obj.getName());
                    if (id.equals(obj.getId()) && !user.getEmail().equals(obj.getEmail())) {
                        user.setEmail(obj.getEmail());
                    }
                    obj.setPassword(user.getPassword());
                    return ResponseEntity.ok().body(mapper.map(service.update(obj), UserDTO.class));
                }).orElseThrow(() -> new DataIntegratyViolationException("Email já cadastrado no sistema"));
    }
}
