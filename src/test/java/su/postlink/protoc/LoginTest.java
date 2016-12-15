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
    }

    private void addInfo(){
        login.setNickName("name");
        login.setHost("213.123.123.141");
        login.setPort(8888);
    }

    @Test
    public void getName(){
        addInfo();
        assertEquals("name", login.getNickName());
    }

    @Test
    public void getHost(){
        addInfo();
        assertEquals("213.123.123.141", login.getHost());
    }

    @Test
    public void getPort(){
        addInfo();
        assertEquals(8888, login.getPort());
    }
    @Test
    public void testMakeLogin(){
        assertFalse(login.hasNickName());
        assertFalse(login.hasPort());
        assertFalse(login.hasHost());
    }



}