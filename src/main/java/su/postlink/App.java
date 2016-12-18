package su.postlink;

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
            server.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
