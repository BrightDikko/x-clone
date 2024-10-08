package com.xclonebackend.xclone.controllers;

import com.xclonebackend.xclone.exceptions.EmailAlreadyTakenException;
import com.xclonebackend.xclone.exceptions.EmailFailedToSendException;
import com.xclonebackend.xclone.exceptions.IncorrectVerificationCodeException;
import com.xclonebackend.xclone.exceptions.UserDoesNotExistException;
import com.xclonebackend.xclone.models.ApplicationUser;
import com.xclonebackend.xclone.models.RegistrationObject;
import com.xclonebackend.xclone.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final UserService userService;


    @ExceptionHandler({EmailAlreadyTakenException.class})
    public ResponseEntity<String> handleEmailAlreadyTaken() {
        return new ResponseEntity<String>("The email you provided is already in use", HttpStatus.CONFLICT);
    }

    @ExceptionHandler({UserDoesNotExistException.class})
    public ResponseEntity<String> handleUserDoesNotExist() {
        return new ResponseEntity<String>("The user you are looking for does not exist", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({EmailFailedToSendException.class})
    public ResponseEntity<String> handleEmailFailedToSendException() {
        return new ResponseEntity<String>("Email failed to send, try again in a moment", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({IncorrectVerificationCodeException.class})
    public ResponseEntity<String> handleIncorrectVerificationCodeException() {
        return new ResponseEntity<String>("The code provided does not match the user's verification code", HttpStatus.CONFLICT);
    }

    // go to http://localhost:8000/auth/register
    @PostMapping("/register")
    public ApplicationUser registerUser(@RequestBody RegistrationObject registrationRequest) {

        return userService.registerUser(registrationRequest);
    }

    @PutMapping("/update/phone")
    public ApplicationUser updatePhoneNumber(@RequestBody LinkedHashMap<String, String> body) {
        String username = body.get("username");
        String phone = body.get("phone");

        ApplicationUser user = userService.getUserByUsername(username);

        user.setPhone(phone);

        return userService.updateUser(user);
    }

    @PostMapping("/email/code")
    public ResponseEntity<String> createEmailVerificationCode(@RequestBody LinkedHashMap<String, String> body) {

        userService.generateEmailVerificationCode(body.get("username"));

        return new ResponseEntity<String>("Verification code generated, email sent", HttpStatus.OK);
    }

    @PostMapping("/email/verify")
    public ApplicationUser verifyEmail(@RequestBody LinkedHashMap<String, String> body) {

        Long code = Long.parseLong(body.get("code"));
        String username = body.get("username");

        ApplicationUser user = userService.getUserByUsername(username);

        return userService.verifyEmail(username, code);
    }

    @PutMapping("/update/password")
    public ApplicationUser updatePassword(@RequestBody LinkedHashMap<String, String> body) {

        String username = body.get("username");
        String password = body.get("password");

        return userService.setPassword(username, password);
    }
}
