package com.example.loginauthapi.services;

import com.example.loginauthapi.dto.UserDTO;
import com.example.loginauthapi.entity.User;
import com.example.loginauthapi.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public String createUser(UserDTO userDto) {
        User user = new User();

        user.setName(userDto.name());
        user.setPosition(userDto.position());
        user.setSector(userDto.sector());

        userRepository.save(user);
        return "User created.";
    }

    public List<UserDTO> findAllUsers() {
        List<User> users = userRepository.findAll();

        return users.stream()
                .map(user -> new UserDTO(user.getName(), user.getEmail(), user.getPosition(), user.getSector()))
                .toList();
    }

    public UserDTO findUserByUUID(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return new UserDTO(user.getName(), user.getEmail(), user.getPosition(), user.getSector());
    }

    public String updateUser(String userId, UserDTO userDto) {
       User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
       user.setName(userDto.name());
       user.setPosition(userDto.position());
       user.setSector(userDto.sector());

       userRepository.save(user);

       return "User updated.";
    }

    public String deleteUser(String userId) {
        userRepository.deleteById(userId);
        return "User deleted.";
    }


}
