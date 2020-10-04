package tests;

import dao.EmployeeDAO;
import dao.PersonDAO;
import io.javalin.http.Context;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class EmployeeDAOTest {

    @Test
    void checkLogin_check_invalid_string() {
        assertNull(PersonDAO.checkLogin("someone@haircuts.com","wrongPassword"));
    }
    @Test
    void checkLogin_check_invalid_ctx() {
        Context ctx = mock(Context.class);
        when(ctx.formParam("loginemail")).thenReturn("someone@haircuts.com");
        when(ctx.formParam("loginpassword")).thenReturn("incorrectPassword");
        assertNull(PersonDAO.checkLogin(ctx));
    }
    @Test
    void checkLogin_check_valid_string() {
        assertNotNull(PersonDAO.checkLogin("someone@haircuts.com","test"));
    }
    @Test
    void checkLogin_check_valid_ctx() {
        Context ctx = mock(Context.class);
        when(ctx.formParam("loginemail")).thenReturn("someone@haircuts.com");
        when(ctx.formParam("loginpassword")).thenReturn("test");
        assertNotNull(PersonDAO.checkLogin(ctx));
    }
}