package com.example.pcmarket.service;

import com.example.pcmarket.dto.ApiResponse;
import com.example.pcmarket.dto.UserDto;
import com.example.pcmarket.entity.User;
import com.example.pcmarket.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    final UserRepository userRepository;

    public ApiResponse add(UserDto dto) {
        User user=new User();
        user.setFullName(dto.getFullName());
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());
        User save = userRepository.save(user);
        return new ApiResponse("Added",true,save);
    }

    public ApiResponse edit(Integer id, UserDto dto) {
        Optional<User> byId = userRepository.findById(id);
        if (!byId.isPresent()) {
            return new ApiResponse("Not found",false);
        }
        User user = byId.get();
        user.setFullName(dto.getFullName());
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());
        User save = userRepository.save(user);
        return new ApiResponse("Edited",true,save);
    }

    public ApiResponse delete(Integer id) {
        Optional<User> byId = userRepository.findById(id);
        User user = byId.get();
        user.setActive(false);
        userRepository.save(user);
        return new ApiResponse("Blocked",true);
    }
}
