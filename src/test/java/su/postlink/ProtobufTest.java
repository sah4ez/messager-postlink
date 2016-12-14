package su.postlink;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by aleksandr on 14.12.16.
 */
public class ProtobufTest extends Assert{

    Message.Body.Builder message = new Message.Body.Builder();
    Long time = System.currentTimeMillis();

    @Before
    public void setUp() {
        message.setDate(time)
                .setBody("hello")
                .setIdFrom(1)
                .setNickNameFrom("vasya")
                .setIdTo(2)
                .setNickNameTo("kolya").build();
    }

    @Test
    public void test() {
        assertEquals("hello", message.getBody());
        assertEquals(time.longValue(), message.getDate());
        assertEquals(1, message.getIdFrom());
        assertEquals("vasya", message.getNickNameFrom());
        assertEquals(2, message.getIdTo());
        assertEquals("kolya", message.getNickNameTo());
    }

}
