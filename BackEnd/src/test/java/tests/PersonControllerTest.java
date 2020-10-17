package tests;

import controller.EmployeeController;
import controller.PersonController;
import controller.util.Status;
import io.javalin.http.Context;
import model.Employee;
import model.Person;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PersonControllerTest {
    private final int admin=3;
    private final int worker=2;
    private final int customer=1;

    @Test
    public void check_login_admin_credentials() throws Exception {
        Context ctx = mock(Context.class);
        when(ctx.formParam("loginemail")).thenReturn("admin@besthaircuts.com");
        when(ctx.formParam("loginpassword")).thenReturn("abc123"); //The standard password we use for testing
        PersonController.checkLogin.handle(ctx);
        ArgumentCaptor<Status> argument = ArgumentCaptor.forClass(Status.class); //Used to catch the object being used by ctx
        verify(ctx).json(argument.capture());
        assertTrue(Person.class.isAssignableFrom(argument.getValue().payload.getClass()));
        Person person = (Person) argument.getValue().payload;
        assertEquals(person.getType(), admin);
    }
    @Test
    public void check_login_worker_credentials() throws Exception {
        Context ctx = mock(Context.class);
        when(ctx.formParam("loginemail")).thenReturn("junc@besthaircuts.com");
        when(ctx.formParam("loginpassword")).thenReturn("abc123"); //The standard password we use for testing
        PersonController.checkLogin.handle(ctx);
        ArgumentCaptor<Status> argument = ArgumentCaptor.forClass(Status.class); //Used to catch the object being used by ctx
        verify(ctx).json(argument.capture());
        assertTrue(Person.class.isAssignableFrom(argument.getValue().payload.getClass()));
        Person person = (Person) argument.getValue().payload;
        assertEquals(worker, person.getType());
    }
    @Test
    public void check_login_customer_credentials() throws Exception {
        Context ctx = mock(Context.class);
        when(ctx.formParam("loginemail")).thenReturn("KaleemStorey@gmail.com");
        when(ctx.formParam("loginpassword")).thenReturn("abc123"); //The standard password we use for testing
        PersonController.checkLogin.handle(ctx);
        ArgumentCaptor<Status> argument = ArgumentCaptor.forClass(Status.class); //Used to catch the object being used by ctx
        verify(ctx).json(argument.capture());
        assertTrue(Person.class.isAssignableFrom(argument.getValue().payload.getClass()));
        Person person = (Person) argument.getValue().payload;
        assertEquals(customer, person.getType());
    }
    @Test
    public void check_login_invalid_credentials() throws Exception {
        Context ctx = mock(Context.class);
        when(ctx.formParam("loginemail")).thenReturn("KaleemStorey@gmail.com");
        when(ctx.formParam("loginpassword")).thenReturn("abc1234"); //The standard password we use for testing
        PersonController.checkLogin.handle(ctx);
        ArgumentCaptor<Status> argument = ArgumentCaptor.forClass(Status.class); //Used to catch the object being used by ctx
        verify(ctx).json(argument.capture());
        assertNull(argument.getValue().payload);
        assertEquals("failed",argument.getValue().status);
    }
    @Test
    public void check_login_missing_credentials() throws Exception {
        Context ctx = mock(Context.class);
        when(ctx.formParam("loginemail")).thenReturn("KaleemStorey@gmail.com");
        //when(ctx.formParam("loginpassword")).thenReturn("abc1234"); //The standard password we use for testing
        PersonController.checkLogin.handle(ctx);
        ArgumentCaptor<Status> argument = ArgumentCaptor.forClass(Status.class); //Used to catch the object being used by ctx
        verify(ctx).json(argument.capture());
        assertNull(argument.getValue().payload);
        assertEquals("failed",argument.getValue().status);
        assertTrue(argument.getValue().message.contains("`loginpassword`"));
    }

}

