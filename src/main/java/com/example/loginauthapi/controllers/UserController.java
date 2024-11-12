package com.example.loginauthapi.controllers;

import com.example.loginauthapi.dto.UserDTO;
import com.example.loginauthapi.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/save")
    public ResponseEntity<String> save (@RequestBody UserDTO user) {

        try {
            String mensagem = this.userService.createUser(user);
            return new ResponseEntity<>(mensagem, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Deu erro!"+e.getMessage(), HttpStatus.BAD_REQUEST );
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@RequestBody UserDTO user, @PathVariable String id){
        try {
            String mensagem = this.userService.updateUser(id,user);
            return new ResponseEntity<>(mensagem, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Deu erro!"+e.getMessage(), HttpStatus.BAD_REQUEST );
        }
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable String id){
        try {
            UserDTO user = this.userService.findUserByUUID(id);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST );
        }
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<UserDTO>> findAll(){
        try {
            List<UserDTO> lista = this.userService.findAllUsers();
            return new ResponseEntity<>(lista, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST );
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable String id){
        try {
            String mensagem = this.userService.deleteUser(id);
            return new ResponseEntity<>(mensagem, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST );
        }
    }
}
