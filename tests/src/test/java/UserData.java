
public class UserData {
    public final String displayName;
    public final String userName;
    public final String emailAddress;
    public final String bio;
    public final String password;
    

    public UserData(String displayName, String userName, 
                    String emailAddress, String bio, String password){
        
        this.displayName = displayName;
        this.userName = userName;
        this.emailAddress = emailAddress;
        this.bio = bio;
        this.password = password;
    }
}
