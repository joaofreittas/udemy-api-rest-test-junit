package br.com.udemy.apijunit.domain.user;

import br.com.udemy.apijunit.domain.services.user.UserServiceImpl;
import br.com.udemy.apijunit.domain.user.dto.UserDTO;
import br.com.udemy.apijunit.domain.user.exceptions.DataIntegratyViolationException;
import br.com.udemy.apijunit.domain.user.exceptions.UserNotFoundException;
import br.com.udemy.apijunit.factory.UserFactory;
import br.com.udemy.apijunit.infrastructure.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static br.com.udemy.apijunit.factory.UserFactory.createUser;
import static br.com.udemy.apijunit.factory.UserFactory.createUserDTO;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @Test
    public void should_findById_when_there_is_a_user_with_id_informed() {
        var informedId = 1;
        var expectedUser = createUser(1);
        var expectedUserDTO = UserFactory.createUserDTO(1);

        when(userRepository.findById(informedId)).thenReturn(Optional.of(expectedUser));
        when(modelMapper.map(expectedUser, UserDTO.class)).thenReturn(expectedUserDTO);

        UserDTO resultDTO = userService.findById(informedId);

        assertEquals(expectedUser.getId(), resultDTO.getId());
        verify(userRepository).findById(informedId);
        verify(modelMapper).map(expectedUser, UserDTO.class);
    }

    @Test
    public void should_findById_when_there_is_not_a_user_with_id_informed() {
        var informedId = UserFactory.ID;

        when(userRepository.findById(informedId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.findById(informedId));
        verify(userRepository).findById(informedId);
    }

    @Test
    public void should_findAll_when_there_is_users_in_db() {
        var expectedUser = createUser(1);
        var expectedUserDTO = createUserDTO(1);
        var expectedList = List.of(expectedUser);
        var expectedListDTO = List.of(expectedUserDTO);

        when(userRepository.findAll()).thenReturn(expectedList);
        when(modelMapper.map(expectedUser, UserDTO.class)).thenReturn(expectedUserDTO);

        var resultUsers = userService.findAll();

        assertEquals(expectedListDTO, resultUsers);
        verify(userRepository).findAll();
        verify(modelMapper).map(expectedUser, UserDTO.class);
    }

    @Test
    public void should_save_when_data_informed_is_valid() {
        var expectedUserDto = UserFactory.createUserDTO(1);
        var informedUser = UserFactory.createUser(1);

        when(userRepository.save(informedUser)).thenReturn(informedUser);
        when(modelMapper.map(informedUser, UserDTO.class)).thenReturn(expectedUserDto);
        when(modelMapper.map(expectedUserDto, User.class)).thenReturn(informedUser);

        var resultUserDto = userService.save(expectedUserDto);

        assertEquals(expectedUserDto, resultUserDto);
        verify(userRepository).save(informedUser);
        verify(modelMapper).map(informedUser, UserDTO.class);
        verify(modelMapper).map(expectedUserDto, User.class);
    }

    @Test
    public void should_save_when_data_informed_is_invalid() {
        var expectedUserDto = UserFactory.createUserDTO(1);
        var userDtoWithId = UserFactory.createUser(2);

        when(userRepository.findByEmail(expectedUserDto.getEmail())).thenReturn(Optional.of(userDtoWithId));

        assertThrows(DataIntegratyViolationException.class, () -> userService.save(expectedUserDto));
    }

    @Test
    public void should_delete_when_exists_user() {
        var informedId = 1;
        var expectedUser = createUser(informedId);
        var expectedUserDto = createUserDTO(informedId);

        when(userRepository.findById(informedId)).thenReturn(Optional.of(expectedUser));
        when(modelMapper.map(expectedUser, UserDTO.class)).thenReturn(expectedUserDto);
        doNothing().when(userRepository).deleteById(informedId);

        assertDoesNotThrow(() -> userService.delete(informedId));

        verify(modelMapper).map(expectedUser, UserDTO.class);
        verify(userRepository).findById(informedId);
    }


    @Test
    public void should_delete_when_not_exists_user() {
        var informedId = 1;

        when(userRepository.findById(informedId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.delete(informedId));

        verify(userRepository).findById(informedId);
    }

    @Test
    public void should_update_when_data_informed_is_valid() {
        var informedId = 1;
        var expectedUserDto = UserFactory.createUserDTO(informedId);
        var informedUser = UserFactory.createUser(informedId);

        when(userRepository.findById(informedId)).thenReturn(Optional.of(informedUser));
        when(userRepository.save(informedUser)).thenReturn(informedUser);
        when(modelMapper.map(informedUser, UserDTO.class)).thenReturn(expectedUserDto);
        when(modelMapper.map(expectedUserDto, User.class)).thenReturn(informedUser);

        var resultUserDto = userService.update(expectedUserDto, informedId);

        assertEquals(expectedUserDto, resultUserDto);
        verify(userRepository).findById(informedId);
        verify(userRepository).save(informedUser);
        verify(modelMapper, times(2)).map(informedUser, UserDTO.class);
        verify(modelMapper).map(expectedUserDto, User.class);
    }

    @Test
    public void should_update_when_data_informed_is_invalid() {
        var informedId = 1;
        var expectedUserDto = UserFactory.createUserDTO(informedId);
        var expectedUser = UserFactory.createUser(informedId);
        var userDtoWithId = UserFactory.createUser(2);

        when(userRepository.findById(informedId)).thenReturn(Optional.of(expectedUser));
        when(modelMapper.map(expectedUser, UserDTO.class)).thenReturn(expectedUserDto);
        when(userRepository.findByEmail(expectedUserDto.getEmail())).thenReturn(Optional.of(userDtoWithId));

        assertThrows(DataIntegratyViolationException.class, () -> userService.update(expectedUserDto, informedId));
    }
}