package com.paycore.service;

import com.paycore.dto.AuthResponse;
import com.paycore.dto.LoginRequest;
import com.paycore.entity.Employee;
import com.paycore.entity.Role;
import com.paycore.entity.User;
import com.paycore.repository.EmployeeRepository;
import com.paycore.repository.UserRepository;
import com.paycore.security.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtTokenProvider tokenProvider;

    @Mock
    private UserRepository userRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private AuthService authService;

    private User user;
    private Employee employee;

    @BeforeEach
    void setUp() {
        user = new User("john.doe@paycore.com", "encodedPass", Role.ROLE_EMPLOYEE);
        user.setId(1L);

        employee = new Employee(
                "EMP-1001",
                "John",
                "Doe",
                LocalDate.of(1990, 1, 1),
                "9876543210",
                "Software Engineer",
                "Engineering",
                LocalDate.of(2023, 1, 1),
                user
        );
        employee.setId(10L);
    }

    @Test
    void testLogin_Success_ReturnsJwtAndEmployeeDetails() {
        LoginRequest request = new LoginRequest();
        request.setEmail("john.doe@paycore.com");
        request.setPassword("Password123!");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(tokenProvider.generateToken(authentication)).thenReturn("jwt-token");
        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(user));
        when(employeeRepository.findByUserId(1L)).thenReturn(Optional.of(employee));

        AuthResponse response = authService.login(request);

        assertNotNull(response);
        assertEquals("jwt-token", response.getToken());
        assertEquals(1L, response.getUserId());
        assertEquals("john.doe@paycore.com", response.getEmail());
        assertEquals("ROLE_EMPLOYEE", response.getRole());
        assertEquals(10L, response.getEmployeeId());
        assertEquals("EMP-1001", response.getEmployeeCode());
        assertEquals("John Doe", response.getFullName());

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(tokenProvider).generateToken(authentication);
    }

    @Test
    void testLogin_UserMissingAfterAuthentication_ThrowsException() {
        LoginRequest request = new LoginRequest();
        request.setEmail("missing@paycore.com");
        request.setPassword("Password123!");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(tokenProvider.generateToken(authentication)).thenReturn("jwt-token");
        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> authService.login(request));

        assertEquals("User not found", exception.getMessage());
        verify(employeeRepository, never()).findByUserId(anyLong());
    }
}
