import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class URLTest {
    public static void main(String[] args) {



        try {
            URL url = new URL("http://httpbin.org/get");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            Map<String, List<String>> headerFields = connection.getHeaderFields();

            JSONObject responseObject = new JSONObject();

            for(Map.Entry<String, List<String>> entry: headerFields.entrySet()) {
                String key = entry.getKey();
                if(entry.getKey() == null) {
                    key = "";
                }
                Object value = entry.getValue();
                responseObject.put(key,value);
            }
            System.out.println(responseObject.toString(4));


//            System.out.println(headerFields);

//            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

//            String line;
//            while((line = reader.readLine()) != null) {
//                System.out.println(line);
//            }



        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
