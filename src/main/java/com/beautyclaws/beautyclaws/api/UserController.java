package com.beautyclaws.beautyclaws.api;


import com.beautyclaws.beautyclaws.db.User;
import com.beautyclaws.beautyclaws.db.UserRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/myapi/users")
public class UserController {

    private UserRepository userRepository;
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllUsers(){
        List<User> users = userRepository.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody CreateUserRequest request){
        System.out.println("Create User");
        User user = new User();
        user.setUsername(request.getUsername());

        // add the encrypt password
        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(hashedPassword);
        //user.setConfirmPassword(request.getConfirmPassword());
        user.setMobile(request.getMobile());
        user.setEmail(request.getEmail());
        //validate if user exists by email
        Optional<User> userByEmail= userRepository.findByEmail(request.getEmail());
        if(userByEmail.isPresent())
            return new ResponseEntity<>(new APIResponseRequest(403,"Email Already in use",0), HttpStatus.OK);

        //validate if user exists by phone number
        User savedUser =userRepository.saveAndFlush(user);
        return new ResponseEntity<>(new APIResponseRequest(200,"User Created Successfully!", savedUser.getId()), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginReq request){
        //validate if user exists by email
        Optional<User> userByEmail= userRepository.findByEmail(request.getEmail());
        if(userByEmail.isPresent()){
            // check encrypted password instead
            //dec
            if (!BCrypt.checkpw(request.getPassword(), userByEmail.get().getPassword())) {
                return new ResponseEntity<>(new APIResponseRequest(200,"Login  Success!",userByEmail.get().getId()), HttpStatus.OK);
            }

            return new ResponseEntity<>(new APIResponseRequest(404,"Login  Failed! Password Mismatch",0), HttpStatus.OK);
        }
        return new ResponseEntity<>(new APIResponseRequest(404,"Login  Failed!",0), HttpStatus.OK);
    }

}
