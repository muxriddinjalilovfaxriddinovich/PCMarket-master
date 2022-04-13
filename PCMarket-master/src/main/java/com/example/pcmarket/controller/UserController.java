package com.example.pcmarket.controller;

import com.example.pcmarket.dto.ApiResponse;
import com.example.pcmarket.dto.UserDto;
import com.example.pcmarket.repository.UserRepository;
import com.example.pcmarket.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/api/user")
@RestController
@RequiredArgsConstructor
public class UserController {

    final UserRepository userRepository;
    final UserService userService;

    @GetMapping
    public HttpEntity<?> getAll(){
        return ResponseEntity.ok().body(userRepository.findAll());
    }
    @PostMapping
    public HttpEntity<?> add(@Valid @RequestBody UserDto dto){
        ApiResponse apiResponse=userService.add(dto);
        return ResponseEntity.status(apiResponse.isSuccess()?201:409).body(apiResponse);
    }
    @PutMapping("/{id}")
    public HttpEntity<?> edit(@PathVariable Integer id,@Valid @RequestBody UserDto dto){
        ApiResponse apiResponse=userService.edit(id,dto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }
    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable Integer id){
        ApiResponse apiResponse=userService.delete(id);
        return ResponseEntity.status(apiResponse.isSuccess()?204:404).body(apiResponse);
    }
}
