package com.example.backend.govflow.controller;

import com.example.backend.govflow.dto.ChangePasswordDto;
import com.example.backend.govflow.dto.UpdateProfileDto;
import com.example.backend.govflow.dto.UserDto;
import com.example.backend.govflow.entity.User;
import com.example.backend.govflow.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<UserDto> getMyProfile(@AuthenticationPrincipal User currentUser) {
        UserDto userDto = new UserDto(
                currentUser.getId(),
                currentUser.getFullName(),
                currentUser.getEmail(),
                currentUser.getRole().getName()
        );
        return ResponseEntity.ok(userDto);
    }

    @PutMapping
    public ResponseEntity<UserDto> updateMyProfile(
            @AuthenticationPrincipal User currentUser,
            @Valid @RequestBody UpdateProfileDto updateProfileDto) {

        UserDto updatedUser = userService.updateProfile(currentUser.getId(), updateProfileDto);
        return ResponseEntity.ok(updatedUser);
    }

    @PostMapping("/change-password")
    public ResponseEntity<Void> changeMyPassword(
            @AuthenticationPrincipal User currentUser,
            @Valid @RequestBody ChangePasswordDto changePasswordDto) {

        userService.changePassword(currentUser.getId(), changePasswordDto);
        return ResponseEntity.ok().build();
    }
}