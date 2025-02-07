package br.com.dicasdeumdev.api.services;

import br.com.dicasdeumdev.api.domain.User;
import br.com.dicasdeumdev.api.domain.dto.UserDTO;

public interface UserService {

    UserDTO findById(Integer id);
}
