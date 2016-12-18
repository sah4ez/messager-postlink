package su.postlink.protoc;

/**
 * Created by aleksandr on 18.12.16.
 */
public enum Command {
    REG(1, "registration"),
    LOGIN(2, "login"),
    MSG(3, "message"),
    LIST(4, "list");

    private int i = 0;
    private String message = "";

    Command(int i, String message){
        this.i = i;
        this.message = message;
    }

    public int getI() {
        return i;
    }

    public String getMessage() {
        return message;
    }
}
