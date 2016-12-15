package su.postlink;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Host host = new Host("localhost", 8888);
        Server server = new Server(host);
        try {
            server.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
