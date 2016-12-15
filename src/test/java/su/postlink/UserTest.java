package su.postlink;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by aleksandr on 15.12.16.
 */
public class UserTest extends Assert {
    User user;
    @Before
    public void setUp() throws Exception {
        user = new User(1, "nickName");
    }

    @Test
    public void getId() throws Exception {
        assertEquals(1, user.getId().intValue());
    }

    @Test
    public void setId() throws Exception {
        user.setId(100);
        assertEquals(100, user.getId().intValue());
    }

    @Test
    public void getNickName() throws Exception {
        assertEquals("nickName", user.getNickName());
    }

    @Test
    public void setNickName() throws Exception {
        user.setNickName("nick");
        assertEquals("nick", user.getNickName());

    }

}