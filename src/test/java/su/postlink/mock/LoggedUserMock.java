package su.postlink.mock;

import org.mockito.Mockito;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by aleksandr on 15.12.16.
 */
public final class LoggedUserMock {

    public static ResultSet resultSet() {
        ResultSet rs = Mockito.mock(ResultSet.class);
        try {
            Mockito.when(rs.getInt("id")).thenReturn(1)
                    .thenReturn(2)
                    .thenReturn(3)
                    .thenReturn(4)
                    .thenReturn(5)
                    .thenReturn(6);
            Mockito.when(rs.getString("nickName")).thenReturn("name1")
                    .thenReturn("name2")
                    .thenReturn("name3")
                    .thenReturn("name4")
                    .thenReturn("name5")
                    .thenReturn("name6");
            Mockito.when(rs.next()).thenReturn(true)
                    .thenReturn(true)
                    .thenReturn(true)
                    .thenReturn(true)
                    .thenReturn(true)
                    .thenReturn(true)
                    .thenReturn(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }
}
