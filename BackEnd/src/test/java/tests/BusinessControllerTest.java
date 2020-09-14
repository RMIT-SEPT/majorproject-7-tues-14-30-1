package tests;

import org.apache.velocity.context.Context;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class BusinessControllerTest {
    private Context ctx = mock(Context.class); // "mock-maker-inline" must be enabled
    @Test
    public void POST_to_create_users_gives_201_for_valid_username() {
        when(ctx.queryParam("username")).thenReturn("Roland");
        UserController.create(ctx); // the handler we're testing
        verify(ctx).status(201);
    }

}