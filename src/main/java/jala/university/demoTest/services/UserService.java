package jala.university.demoTest.services;

import jala.university.demoTest.entities.User;
import jala.university.demoTest.exceptions.NotFoundException;
import jala.university.demoTest.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    public UserRepository userRepository;

    public List<User> getUsers(){
        return userRepository.findAll();
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public User findUserById(UUID id) {
        User userDB = userRepository.findById(id).orElse(null);
        if(userDB == null) {
            throw new NotFoundException("User not found");
        }
        return userDB;
    }

    public User updateUser(User user, UUID id) {
        User userDB = userRepository.findById(id).orElse(null);
        if(userDB == null) {
            throw new NotFoundException("User not found");
        }
        userDB.setEmail(user.getEmail());
        userDB.setFirstName(user.getFirstName());
        userDB.setLastName(user.getLastName());
        return userRepository.save(userDB);
    }

    public void deleteUser(UUID id){
        User userDB = userRepository.findById(id).orElse(null);
        if(userDB == null) {
            throw new NotFoundException("User not found");
        }
        userRepository.deleteById(id);
    }
}
