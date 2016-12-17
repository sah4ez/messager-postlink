package su.postlink;

import com.sun.istack.internal.NotNull;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by aleksandr on 15.12.16.
 */
public class User implements Comparable{
    @NotNull
    private Integer id;

    @NotNull
    private String nickName;

    public User(Integer id, String nickName)  {
        this.id = id;
        this.nickName = nickName;
    }

    public User(ResultSet rs) {
        try {
            setId(rs.getInt("id"));
        } catch (SQLException e) {
        }
        try {
            setNickName(rs.getString("nickName"));
        } catch (SQLException e) {
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    @Override
    public int compareTo(Object o) {
        User user = ((User) o);
        int i = user.getId().compareTo(getId());
        if (i == 0) return 0;
        return i;
    }
}
