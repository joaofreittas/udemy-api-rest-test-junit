package br.com.udemy.apijunit.domain.user;

import br.com.udemy.apijunit.domain.user.dto.UserDTO;

import java.util.List;

public interface UserService {

    UserDTO findById(Integer id);
    List<UserDTO> findAll();
    UserDTO save(UserDTO userDTO);
    UserDTO update(UserDTO userDTO, Integer id);
    void delete(Integer id);
}
