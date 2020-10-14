package com.twoyang.prodactivity.server.business.users;

import com.twoyang.prodactivity.server.api.User;
import com.twoyang.prodactivity.server.api.UserCreation;
import com.twoyang.prodactivity.server.business.util.CRUDService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements CRUDService<User, UserCreation> {
    private final UserRepository userRepository;
    private final ModelMapper mapper;

    public UserService(UserRepository userRepository, ModelMapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    public User create(UserCreation creation) {
        return mapper.map(userRepository.save(new UserEntity()), User.class);
    }

    public User create() {
        return mapper.map(userRepository.save(new UserEntity()), User.class);
    }

    public List<User> getAll() {
        return userRepository.findAll().stream().map(user -> mapper.map(user, User.class)).collect(Collectors.toList());
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
