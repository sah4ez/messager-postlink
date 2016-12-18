package su.postlink.protoc;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by aleksandr on 14.12.16.
 */
public class MessageTest extends Assert{

    Message.Body.Builder message = new Message.Body.Builder();
    Long time = System.currentTimeMillis();

    @Before
    public void setUp() {
        message.setType(3).setDate(time)
                .setBody("hello")
                .setNickNameFrom("vasya")
                .setNickNameTo("kolya").build();
    }

    @Test
    public void MsgTest() {
        assertEquals("hello", message.getBody());
        assertEquals(time.longValue(), message.getDate());
        assertEquals("vasya", message.getNickNameFrom());
        assertEquals("kolya", message.getNickNameTo());
    }

    @Test
    public void hasPropertyTest(){
        assertTrue(message.hasNickNameTo());
        assertTrue(message.hasNickNameFrom());
        assertTrue(message.hasDate());
        assertTrue(message.hasBody());

    }

}
