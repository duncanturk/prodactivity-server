package com.twoyang.prodactivity.server.business.users;

import com.twoyang.prodactivity.server.api.User;
import com.twoyang.prodactivity.server.api.UserCreation;
import com.twoyang.prodactivity.server.business.util.CRUDService;
import lombok.val;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements CRUDService<User, UserCreation> {
    private final UserRepository userRepository;
    private final ModelMapper mapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, ModelMapper mapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
    }

    public User create(UserCreation creation) {
        if (userRepository.existsByIdentifier(creation.getIdentifier())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Identifier is in use");
        }
        val entity = mapper.map(creation, UserEntity.class);
        entity.setSecret(passwordEncoder.encode(creation.getSecret()));
        return mapper.map(userRepository.save(entity), User.class);
    }

    public List<User> getAllForUser() {
        return userRepository.findAll().stream().map(user -> mapper.map(user, User.class)).collect(Collectors.toList());
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
