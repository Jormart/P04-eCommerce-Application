package com.udacity.jdnd.course4.ecommerce.controllers;

import com.udacity.jdnd.course4.ecommerce.model.persistence.User;
import com.udacity.jdnd.course4.ecommerce.model.persistence.repositories.CartRepository;
import com.udacity.jdnd.course4.ecommerce.model.persistence.repositories.UserRepository;
import com.udacity.jdnd.course4.ecommerce.model.requests.CreateUserRequest;
import org.junit.*;
import org.mockito.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserRepository userRepository = mock(UserRepository.class);

    @Mock
    private CartRepository cartRepository = mock(CartRepository.class);

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder = mock(BCryptPasswordEncoder.class);

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void create_user_happy_path() throws Exception{
        when(bCryptPasswordEncoder.encode("testPassword")).thenReturn("thisIsHashed");
        CreateUserRequest r = new CreateUserRequest();
        r.setUsername("test");
        r.setPassword("testPassword");
        r.setConfirmPassword("testPassword");

        final ResponseEntity<User> response = userController.createUser(r);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());

        User u = response.getBody();
        assertNotNull(u);
        assertEquals(0, u.getId());
        assertEquals("test", u.getUsername());
        assertEquals("thisIsHashed", u.getPassword());

    }

}
