package Users;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

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

    private String name;
    private String surname;
    private String email;
    private String password;
    private String username;
    private Integer UserID;
    boolean isModerator;
    boolean isCommunityCreator;

}
