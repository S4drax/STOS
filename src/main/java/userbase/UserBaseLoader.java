package userbase;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserBaseLoader {
    public List<User> loadUsers(){
        JSONParser jsonParser = new JSONParser();
        List<User> userBase = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(System.getProperty("user.dir")+"/users.json"),"UTF-8"))){
            Object obj = jsonParser.parse(reader);
            JSONArray users = (JSONArray) obj;
            users.forEach( user -> userBase.add(parseDirectory((JSONObject) user )));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return userBase;
    }

    private User parseDirectory(JSONObject user){
        JSONObject usr = (JSONObject) user.get("user");
        return User.builder()
                .login(usr.get("login").toString())
                .password(usr.get("password").toString())
                .accessLevel(Integer.parseInt(usr.get("access").toString()))
                .build();
    }



}
