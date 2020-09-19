package tests;

import controller.BusinessController;
import controller.CustomerController;
import controller.util.Status;
import io.javalin.http.Context;
import model.Customer;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerControllerTest {

    @Test
    public void get_customer_valid_ID() throws Exception {
        Context ctx = mock(Context.class);
        when(ctx.queryParam("id")).thenReturn("1");
        CustomerController.getCustomer.handle(ctx);
        ArgumentCaptor<Status> argument = ArgumentCaptor.forClass(Status.class);
        verify(ctx).json(argument.capture());
        Status s = new Status(new Customer());
        assertEquals(argument.getValue().status, s.status);
        assert(argument.getValue().payload instanceof Customer);
    }

    @Test
    public void get_customer_invalid_ID() throws Exception {
        Context ctx = mock(Context.class);
        when(ctx.queryParam("id")).thenReturn("0");
        CustomerController.getCustomer.handle(ctx);
        ArgumentCaptor<Status> argument = ArgumentCaptor.forClass(Status.class);
        verify(ctx).json(argument.capture());
        Status s = new Status(new Customer());
        assertNotEquals(argument.getValue().status, s.status);
    }

    @Test
    public void get_customer_null_ID() throws Exception {
        Context ctx = mock(Context.class);

//        Not providing customer ID
//        when(ctx.queryParam("id")).thenReturn("0");
        CustomerController.getCustomer.handle(ctx);
        ArgumentCaptor<Status> argument = ArgumentCaptor.forClass(Status.class);
        verify(ctx).json(argument.capture());
        Status s = new Status("Error msg");
        assertEquals(argument.getValue().status, s.status);
    }


}