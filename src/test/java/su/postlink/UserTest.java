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

    @Test
    public void testEquals(){
        Integer i = 1;
        User user = new User(1, "name");

        User userId = new User(1, null);
        User userNickName = new User(null, "name");
        User userNext = new User(2, "name2");
        User userEq = new User(1, "name");

        assertFalse(user.equals(i));
        assertFalse(user.equals(null));
        assertFalse(user.equals(userId));
        assertFalse(user.equals(userNickName));
        assertFalse(user.equals(userNext));

        assertTrue(user.equals(userEq));
    }

    @Test
    public void testHashCode(){
         Integer i = 1;
        User user = new User(1, "name");

        User userId = new User(1, null);
        User userNickName = new User(null, "name");
        User userNext = new User(2, "name2");
        User userEq = new User(1, "name");

        assertNotEquals(userId.hashCode(), user.hashCode());
        assertNotEquals(userNext.hashCode(), user.hashCode());
        assertNotEquals(userNickName.hashCode(), user.hashCode());

        assertEquals(userEq.hashCode(), user.hashCode());
    }
}