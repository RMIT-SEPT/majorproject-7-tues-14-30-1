package tests;

import controller.BusinessController;
import io.javalin.http.Context;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static controller.BusinessController.*;
import static org.mockito.ArgumentMatchers.anyString;

class BusinessControllerTest {
    Context ctx;
    @BeforeAll
    public static void setupBusinessController(){
        Context ctx = Mockito.mock(Context.class);
        Mockito.when(ctx.formParam(anyString())).thenReturn("1");
    }
    @Test
    public void update_business_invalid_authentication() throws Exception {
        System.out.println(ctx.formParamMap());
        System.out.println(ctx);
        BusinessController.updateBusiness.handle(ctx); // the handler we're testing
        //verify(ctx).json("hello");
    }

}