package su.postlink;

import su.postlink.mock.LoggedUserMock;

import java.sql.ResultSet;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Server server = new Server("127.0.0.1", 8888);
        try {
            ResultSet rs = LoggedUserMock.resultSet();
            while (rs.next()){
                server.loadRegisterUser(rs);
            }
            server.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
