package com.taskmanagementsystem.controller;

import com.taskmanagementsystem.db.UserRepository;
import com.taskmanagementsystem.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.taskmanagementsystem.security.JWTProperties.SECRET;

@RestController
@RequestMapping("pass")
@CrossOrigin
public class ResetController {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private EmailCfg emailCfg;

    public ResetController(UserRepository userRepository, PasswordEncoder passwordEncoder, EmailCfg emailCfg) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailCfg = emailCfg;
    }

    @PostMapping("reset")
    public String reset(@RequestBody Map<String,String> body){
        String email = body.get("email");
        List<User> userByEmail = userRepository.findAllByEmail(email);
        if(!userByEmail.isEmpty()){
            String token = Jwts.builder()
                    .setSubject(userByEmail.get(0).getUsername())
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() + 3000000))
                    .signWith(SignatureAlgorithm.HS512, SECRET.getBytes())
                    .compact();

            JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
            javaMailSender.setHost(this.emailCfg.getHost());
            javaMailSender.setPort(this.emailCfg.getPort());
            javaMailSender.setUsername(this.emailCfg.getUsername());
            javaMailSender.setPassword(this.emailCfg.getPassword());

            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom("taskmanager@tasks.com");
            simpleMailMessage.setTo(userByEmail.get(0).getEmail());
            simpleMailMessage.setSubject("Reset password from the Task Management application.");

            simpleMailMessage.setText("\n We have received a password change request for your Task Management account - " + userByEmail.get(0).getUsername() + ". \n " +
                    "If you didn't ask to change your password, then you can ignore this email. Click to link below to change password. Link will remain active for 30 minutes. \n \n " +
                    "http://localhost:3000/recovery?e=" + email + "&t=" + token);

            javaMailSender.send(simpleMailMessage);
        }
        else return "Not exist.";

        return "Sent";
    }

    @PostMapping("changePass")
    public String changePassword(@RequestBody Map<String,String> body){
        List<User> allByEmail = userRepository.findAllByEmail(body.get("email"));
        if(!allByEmail.isEmpty()){
            User user = allByEmail.get(0);
            user.setPassword(passwordEncoder.encode(body.get("newPass")));
            userRepository.save(user);

            return "Password successfully updated";
        }
        return "User doesn't exist.";
}
}
