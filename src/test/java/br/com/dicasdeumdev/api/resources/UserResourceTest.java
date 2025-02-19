package br.com.dicasdeumdev.api.resources;

import br.com.dicasdeumdev.api.domain.User;
import br.com.dicasdeumdev.api.domain.dto.UserDTO;
import br.com.dicasdeumdev.api.services.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserResourceTest {

    private static final Integer ID = 1;
    private static final Integer INDEX = 0;
    private static final String NAME = "Emygdio";
    private static final String EMAIL = "emygdio.barreto@gmail.com";
    private static final String PASSWORD = "paladinoT8";

    private User user = new User();
    private UserDTO userDTO = new UserDTO();
    private UserDTO newUser = new UserDTO();

    @InjectMocks
    private UserResource resource;

    @Mock
    private UserServiceImpl service;

    @Mock
    private ModelMapper mapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startUser();
    }

    @Test
    void whenFindByIdThenReturnSuccess() {
        when(service.findById(anyInt())).thenReturn(user);
        when(mapper.map(any(), any())).thenReturn(userDTO);

        ResponseEntity<UserDTO> response = resource.findById(ID);

        assertNotNull(response);
        assertNotNull(userDTO);
        assertEquals(ResponseEntity.class, response.getClass());

        assertEquals(ID, userDTO.getId());
        assertEquals(NAME, userDTO.getName());
        assertEquals(EMAIL, userDTO.getEmail());
        assertEquals(PASSWORD, userDTO.getPassword());
    }

    @Test
    void whenFindAllThenReturnAListOfUserDTO() {
        when(service.findAll()).thenReturn(List.of(user));
        when(mapper.map(any(), any())).thenReturn(userDTO);

        ResponseEntity<List<UserDTO>> response = resource.findAll();

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(ArrayList.class, response.getBody().getClass());
        //assertEquals(UserDTO.class, response.getBody().get(INDEX).getClass());

        assertEquals(ID, userDTO.getId());
        assertEquals(NAME, userDTO.getName());
        assertEquals(EMAIL, userDTO.getEmail());
        assertEquals(PASSWORD, userDTO.getPassword());
    }

    @Test
    void whenCreateThenReturnCreated() {
        when(service.create(any())).thenReturn(user);

        ResponseEntity<UserDTO> response = resource.create(newUser);

        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getHeaders().get("Location"));
    }

    @Test
    void whenUpdateThenReturnSuccess() {
        when(service.create(userDTO)).thenReturn(user);
        when(mapper.map(any(), any())).thenReturn(userDTO);

        ResponseEntity<UserDTO> response = resource.update(ID, userDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());

        assertEquals(ID, userDTO.getId());
        assertEquals(NAME, userDTO.getName());
        assertEquals(EMAIL, userDTO.getEmail());
    }

    @Test
    void whenDeleteThenReturnSuccess() {
        doNothing().when(service).delete(anyInt());

        ResponseEntity<UserDTO> response = resource.delete(ID);

        assertNotNull(response);
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
//        verify(service, times(1)).delete(anyInt());
    }

    private void startUser() {
        user = new User(ID, NAME, EMAIL, PASSWORD);
        userDTO = new UserDTO(ID, NAME, EMAIL, PASSWORD);
        newUser = new UserDTO(null, "testes", "testes@teste.com", "1234");
    }
}