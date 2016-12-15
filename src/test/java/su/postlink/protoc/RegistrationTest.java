package su.postlink.protoc;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by aleksandr on 15.12.16.
 */
public class RegistrationTest extends Assert{
    Registration.Body.Builder registration;
    @Before
    public void setUp() throws Exception {
        registration = new Registration.Body.Builder();
    }

    @Test
    public void testRegistrationMessage(){
        registration.setNickName("nickName");
        assertTrue(registration.hasNickName());
        assertEquals("nickName",registration.getNickName());
    }

    @Test
    public void testNullNickName(){
        assertFalse(registration.hasNickName());
        assertNotNull(registration.getNickName());
    }

}