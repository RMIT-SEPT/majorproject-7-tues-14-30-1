package tests;

import controller.CustomerController;
import controller.EmployeeController;
import controller.util.Status;
import io.javalin.http.Context;
import model.Customer;
import model.Employee;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmployeeControllerTest {
    @Test
    public void get_employee_valid_ID() throws Exception {
        Context ctx = mock(Context.class);
        when(ctx.queryParam("id")).thenReturn("1");
        EmployeeController.getEmployee.handle(ctx);
        ArgumentCaptor<Status> argument = ArgumentCaptor.forClass(Status.class);
        verify(ctx).json(argument.capture());
        Status s = new Status(new Employee());
        assertEquals(argument.getValue().status, s.status);
        assert(argument.getValue().payload instanceof Employee);
    }

    @Test
    public void get_Employee_invalid_ID() throws Exception {
        Context ctx = mock(Context.class);
        when(ctx.queryParam("id")).thenReturn("0");
        EmployeeController.getEmployee.handle(ctx);
        ArgumentCaptor<Status> argument = ArgumentCaptor.forClass(Status.class);
        verify(ctx).json(argument.capture());
        Status s = new Status(new Employee());
        assertNotEquals(argument.getValue().status, s.status);
    }

    @Test
    public void get_employee_null_ID() throws Exception {
        Context ctx = mock(Context.class);

//        Not providing customer ID
//        when(ctx.queryParam("id")).thenReturn("0");
        EmployeeController.getEmployee.handle(ctx);
        ArgumentCaptor<Status> argument = ArgumentCaptor.forClass(Status.class);
        verify(ctx).json(argument.capture());
        Status s = new Status("Error msg");
        assertEquals(argument.getValue().status, s.status);
    }

}