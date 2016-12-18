package su.postlink.protoc;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by aleksandr on 18.12.16.
 */
public class CommandTest extends Assert {

    @Test
    public void getI() throws Exception {
        assertEquals(1, Command.REG.getI());
        assertEquals(2, Command.LOGIN.getI());
        assertEquals(3, Command.MSG.getI());
        assertEquals(4, Command.LIST.getI());
    }

    @Test
    public void getMessage(){
        assertEquals("registration", Command.REG.getMessage());
        assertEquals("login", Command.LOGIN.getMessage());
        assertEquals("message", Command.MSG.getMessage());
        assertEquals("list", Command.LIST.getMessage());
    }

}