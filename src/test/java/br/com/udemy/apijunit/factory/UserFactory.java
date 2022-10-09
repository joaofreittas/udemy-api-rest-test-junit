package br.com.udemy.apijunit.factory;

import br.com.udemy.apijunit.domain.user.User;
import br.com.udemy.apijunit.domain.user.dto.UserDTO;

public class UserFactory {

    public static final String NAME = "João Lucas";
    public static final int ID = 1;
    public static final String EMAIL = "joao.fr@gmail.com";
    public static final String PASSWORD = "1234";

    public static User createUser(Integer id) {
        return User.builder()
            .id(id)
            .name(NAME)
            .email(EMAIL)
            .password(PASSWORD)
            .build();
    }

    public static UserDTO createUserDTO(Integer id) {
        return UserDTO.builder()
            .name(NAME)
            .id(id)
            .email(EMAIL)
            .password(PASSWORD)
            .build();
    }

}
