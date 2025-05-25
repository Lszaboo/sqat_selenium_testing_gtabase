import org.json.JSONObject;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TestConfig {
    protected JSONObject mainJSON;
    public final UserData user;

    public TestConfig(String path) throws Exception{
        String jsonContent = new String(Files.readAllBytes(Paths.get(path)));
        mainJSON = new JSONObject(jsonContent);
        JSONObject userConfig = mainJSON.getJSONObject("user_data");

        user = new UserData(userConfig.getString("display_name"),
                            userConfig.getString("user_name"),
                            userConfig.getString("email_address"),
                            userConfig.getString("password"));
    }
}
