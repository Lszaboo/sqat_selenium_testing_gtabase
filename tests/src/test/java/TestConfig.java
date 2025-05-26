import org.json.JSONArray;
import org.json.JSONObject;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.ArrayList;

public class TestConfig {
    protected JSONObject mainJSON;
    protected List<String> urls;
    public final UserData user;

    public TestConfig(String path) throws Exception{
        String jsonContent = new String(Files.readAllBytes(Paths.get(path)));
        mainJSON = new JSONObject(jsonContent);
        
        urls = new ArrayList<String>();
        JSONArray urlsJSON = mainJSON.getJSONArray("urls");
        
        for (int i = 0; i< urlsJSON.length(); i++) {
            urls.add(urlsJSON.getString(i));
        }

        JSONObject userConfig = mainJSON.getJSONObject("user_data");
        user = new UserData(userConfig.getString("display_name"),
                            userConfig.getString("user_name"),
                            userConfig.getString("email_address"),
                            userConfig.getString("bio"),
                            userConfig.getString("password"));
    }

    public String getUrlAt(int index){
        return urls.get(index);
    }

    public int getUrlsLenght(){
        return urls.size();
    }
}
