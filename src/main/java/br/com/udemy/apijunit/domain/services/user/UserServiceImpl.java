package br.com.udemy.apijunit.domain.services.user;

import br.com.udemy.apijunit.domain.user.User;
import br.com.udemy.apijunit.domain.user.UserService;
import br.com.udemy.apijunit.domain.user.dto.UserDTO;
import br.com.udemy.apijunit.domain.user.exceptions.DataIntegratyViolationException;
import br.com.udemy.apijunit.domain.user.exceptions.UserNotFoundException;
import br.com.udemy.apijunit.infrastructure.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public UserDTO findById(final Integer id) {
        return userRepository
            .findById(id)
            .map(user -> modelMapper.map(user, UserDTO.class))
            .orElseThrow(() -> new UserNotFoundException("Usuario não encontrado com id: " + id));
    }

    @Override
    public List<UserDTO> findAll() {
        return userRepository
            .findAll()
            .stream()
            .map(user -> modelMapper.map(user, UserDTO.class))
            .collect(Collectors.toList());
    }

    @Override
    public UserDTO save(final UserDTO userDTO) {
        validateUser(userDTO);
        var user = modelMapper.map(userDTO, User.class);
        return modelMapper.map(userRepository.save(user), UserDTO.class);
    }

    @Override
    public UserDTO update(final UserDTO userDTO, Integer id) {
        findById(id);
        validateUser(userDTO);

        var user = modelMapper.map(userDTO, User.class);
        return modelMapper.map(userRepository.save(user), UserDTO.class);
    }

    @Override
    public void delete(final Integer id) {
        findById(id);
        userRepository.deleteById(id);
    }

    private void validateUser(final UserDTO userDTO) {
        var user = userRepository.findByEmail(userDTO.getEmail());

        user.ifPresent(use -> {
            if(userIsDifferentOfDTO(userDTO, user.get())) {
                throw new DataIntegratyViolationException("Email já está cadastrado no sistema: " + userDTO.getEmail());
            }
        });
    }

    private boolean userIsDifferentOfDTO(final UserDTO userDTO, User user) {
        return !user.getId().equals(userDTO.getId());
    }

}
