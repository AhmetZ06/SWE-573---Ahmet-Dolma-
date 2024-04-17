package Users;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    private Integer UserID;
    private String name;
    private String surname;
    private String email;
    private String password;
    private String username;

    boolean isModerator;
    boolean isCommunityCreator;

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", username='" + username + '\'' +
                ", UserID=" + UserID +
                ", isModerator=" + isModerator +
                ", isCommunityCreator=" + isCommunityCreator +
                '}';
    }

}
