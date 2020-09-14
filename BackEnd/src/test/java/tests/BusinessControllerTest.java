package tests;

import controller.BusinessController;
import controller.util.Status;
import io.javalin.http.Context;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;


import static controller.BusinessController.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class BusinessControllerTest {

    @Test
    public void update_business_invalid_authentication() throws Exception {
        Context ctx = mock(Context.class);
        when(ctx.formParam("id")).thenReturn("1");
        when(ctx.formParam("loginemail")).thenReturn("someone@haircuts.com");
        when(ctx.formParam("loginpassword")).thenReturn("falsePassword");
        BusinessController.updateBusiness.handle(ctx);
        ArgumentCaptor<Status> argument = ArgumentCaptor.forClass(Status.class);
        //verify(mock).doSomething(argument.capture());
        verify(ctx).json(argument.capture());
        assertEquals(argument.getValue(), new Status("No account with those details"));
    }
    @Test
    public void update_business_valid_authentication() throws Exception {
        Context ctx = mock(Context.class);
        when(ctx.formParam("id")).thenReturn("1");
        when(ctx.formParam("loginemail")).thenReturn("manager@haircuts.com");
        when(ctx.formParam("loginpassword")).thenReturn("test");
        BusinessController.updateBusiness.handle(ctx);
        ArgumentCaptor<Status> argument = ArgumentCaptor.forClass(Status.class);
        //verify(mock).doSomething(argument.capture());
        verify(ctx).json(argument.capture());
        assertEquals(argument.getValue(), new Status());
    }

}