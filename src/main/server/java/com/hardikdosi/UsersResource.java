package com.hardikdosi;

import com.mongodb.*;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Date;

/**
 * Created by Hardik Dosi on 11/16/2016.
 */

@Path("/users")
public class UsersResource {
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void saveUserDetails(UserInfo user) {
        MongoDBSingleton dbSingleton = MongoDBSingleton.getInstance();
        DB db = dbSingleton.getDb();
        DBCollection table = db.getCollection("users");

        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put("username", user.getUsername());
        DBCursor cursor = table.find(searchQuery);
        if (cursor.hasNext()) {
            System.out.println("UserName Already Exists");
        } else {
            //create new
            BasicDBObject document = new BasicDBObject();
            document.put("username", user.getUsername());
            document.put("name", user.getName());
            document.put("email", user.getEmail());
            document.put("password", user.getPassword());
            document.put("createdDate", new Date());
            table.insert(document);
            //respond with success
        }
    }

    @GET
    @Path("/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public UserInfo getUserDetails(@PathParam("username") final String username) {
        if (Authorize.isLoggedIn(username)) {
            MongoDBSingleton dbSingleton = MongoDBSingleton.getInstance();
            DB db = dbSingleton.getDb();
            DBCollection table = db.getCollection("users");

            BasicDBObject searchQuery = new BasicDBObject();
            searchQuery.put("username", username);
            DBCursor cursor = table.find(searchQuery);

            UserInfo user = new UserInfo();
            if (cursor.hasNext()) {
                DBObject x = cursor.next();
                user.setUsername(x.get("username").toString());
                user.setName(x.get("name").toString());
                user.setEmail(x.get("email").toString());
            } else {
                user.setUsername("");
                user.setName("");
                user.setEmail("");
            }
            return user;
        }
        throw new RuntimeException();
    }

    @POST
    @Path("/auth")
    @Consumes(MediaType.APPLICATION_JSON)
    public void verifyUser(UserInfo user) {
        MongoDBSingleton dbSingleton = MongoDBSingleton.getInstance();
        DB db = dbSingleton.getDb();
        DBCollection table = db.getCollection("users");

        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put("username", user.getUsername());
        DBCursor cursor = table.find(searchQuery);

        if (cursor.hasNext()) {
            DBObject obj = cursor.next();
            if (user.getPassword().equals(obj.get("password").toString())) {
                Authorize.logIn(user.getUsername());
                return;
            }
        }

        throw new RuntimeException();
    }

    @POST
    @Path("/logout")
    @Consumes(MediaType.APPLICATION_JSON)
    public void removeUser(UserInfo user) {
        if (Authorize.isLoggedIn(user.getUsername())) {
            Authorize.logOut(user.getUsername());
        }
    }
}
