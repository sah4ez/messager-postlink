package su.postlink;

import com.sun.istack.internal.NotNull;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by aleksandr on 15.12.16.
 */
public class User implements Comparable{
    @NotNull
    private Integer id = -1;

    @NotNull
    private String nickName = "";

    public User(Integer id, String nickName)  {
        this.id = id;
        this.nickName = nickName;
    }

    public User(ResultSet rs) {
        try {
            setId(rs.getInt("id"));
        } catch (SQLException e) {
            setId(-1);
        }
        try {
            setNickName(rs.getString("nickName"));
        } catch (SQLException e) {
            setNickName("ERROR");
        }
    }

    public User() {

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
        i = user.getNickName().compareTo(getNickName());
        if (i == 0) return 0;
        return i;
    }

    @Override
    public boolean equals(Object o){
        if (o == null) return false;

        if (!User.class.isAssignableFrom(o.getClass())) return false;

        final User other = ((User) o);

        if ((this.nickName ==null) ? (other.nickName != null) : !this.nickName.equals(other.nickName)){
            return false;
        }

        if (this.id != other.id){
            return false;
        }

        return true;
    }

    @Override
    public int hashCode(){
        return new HashCodeBuilder(3, 53)
                .append(id).append(nickName).toHashCode();
    }
}
