package com.scarrasco.expertosbackend.controller;

import com.scarrasco.expertosbackend.model.User.User;
import com.scarrasco.expertosbackend.payload.request.LoginRequest;
import com.scarrasco.expertosbackend.payload.request.SignupRequest;
import com.scarrasco.expertosbackend.payload.response.JwtResponse;
import com.scarrasco.expertosbackend.payload.response.MessageResponse;
import com.scarrasco.expertosbackend.repository.UserRepository;
import com.scarrasco.expertosbackend.security.jwt.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController<JwtUtils> {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @PostMapping("/login")
    // Permitir en producción
    @CrossOrigin(origins = "https://proyecto-experts.vercel.app", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.DELETE})
    //Permitir en local
    //@CrossOrigin(origins = "http://localhost:4200", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.DELETE})
    public ResponseEntity<JwtResponse> authenticateUser(@RequestBody LoginRequest loginRequest){

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtTokenUtil.generateJwtToken(authentication);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        return ResponseEntity.ok(new JwtResponse(jwt));
    }

    @PostMapping("/signup")
    // Permitir en producción
    //@CrossOrigin(origins = "https://proyecto-experts.vercel.app", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.DELETE})
    //Permitir en local
    @CrossOrigin(origins = "http://localhost:4200", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.DELETE})
    public ResponseEntity<MessageResponse> registerUser(@RequestBody SignupRequest signUpRequest) {

        // Check 1: username
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        // Check 2: email
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                            signUpRequest.getEmail(),
                            encoder.encode(signUpRequest.getPassword()));

        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
