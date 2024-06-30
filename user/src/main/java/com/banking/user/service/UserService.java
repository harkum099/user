package com.banking.user.service;

import com.banking.user.repository.UserRepository;
import com.banking.user.entity.User;
import com.banking.user.exception.UserAlreadyExistsException;
import com.banking.user.exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public UserService(UserRepository userRepository, KafkaTemplate<String, String> kafkaTemplate) {
        this.userRepository = userRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    public User createUser(User user) {
        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            log.error("User with email id {} already exist.",user.getEmail());
            throw new UserAlreadyExistsException("User with email " + user.getEmail() + " already exists.");
        }
        userRepository.save(user);
        log.info("user is successfully created with id {}",user.getId());
        //kafkaTemplate.send("user-topic", "User created: " + user.getId());
        return user;
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User with id "+id+" not found"));
    }

    public User updateUser(Long id, User userDetails) {
        User user = getUserById(id);
        user.setName(userDetails.getName());
        user.setEmail(userDetails.getEmail());
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        User user = getUserById(id);
        userRepository.delete(user);
        kafkaTemplate.send("user-topic", "User deleted: " + id);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
