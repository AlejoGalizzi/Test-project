package jala.university.demoTest.controllers;

import jala.university.demoTest.entities.User;
import jala.university.demoTest.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> listUsers(){
        return ResponseEntity.ok().body(userService.getUsers());
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody @Valid User user) {
        return ResponseEntity.ok().body(userService.saveUser(user));
    }

    @GetMapping(value="/{id}")
    public ResponseEntity<User> getUser(@PathVariable @Valid UUID id) {
        return ResponseEntity.ok().body(userService.findUserById(id));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<User> updateUser(@PathVariable @Valid UUID id, @RequestBody @Valid User user) {
        return ResponseEntity.ok().body(userService.updateUser(user,id));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable @Valid UUID id){
        return ResponseEntity.noContent().build();
    }

}
