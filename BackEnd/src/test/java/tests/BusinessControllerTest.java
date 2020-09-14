package tests;

import controller.BusinessController;
import io.javalin.http.Context;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static controller.BusinessController.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BusinessControllerTest {
    private Context ctx = mock(Context.class); // "mock-maker-inline" must be enabled

    @Test
    public void POST_to_create_users_gives_201_for_valid_username() {
        when(ctx.queryParam("username")).thenReturn("Roland");
        BusinessController.updateBusiness(ctx); // the handler we're testing
        verify(ctx).status(201);
    }

}