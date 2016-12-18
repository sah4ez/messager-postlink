package su.postlink.protoc;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by aleksandr on 15.12.16.
 */
public class LoginTest extends Assert {

    Login.Body.Builder login;

    @Before
    public void setUp() throws Exception {
        login = new Login.Body.Builder();
        login.setNickName("name");
    }

    @Test
    public void getName(){
        assertEquals("name", login.getNickName());
        assertTrue(login.hasNickName());
        assertNotNull(login.getNickName());
    }
}