package com.hardikdosi;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Hardik Dosi on 11/16/2016.
 */

@Path("/files")
public class FilesResource {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void saveFile(FileInfo file) throws UnknownHostException {
        if (Authorize.isLoggedIn(file.getUsername())) {
            MongoDBSingleton dbSingleton = MongoDBSingleton.getInstance();
            DB db = dbSingleton.getDb();
            DBCollection table = db.getCollection("files");

            BasicDBObject searchQuery = new BasicDBObject();
            searchQuery.put("username", file.getUsername());
            searchQuery.put("fileName", file.getFileName());
            DBCursor cursor = table.find(searchQuery);
            if (cursor.hasNext()) {
                //update
                BasicDBObject document = new BasicDBObject();
                document.append("$set", new BasicDBObject().append("data", file.getData()));
                table.update(searchQuery, document);
            } else {
                //create new
                BasicDBObject document = new BasicDBObject();
                document.put("username", file.getUsername());
                document.put("fileName", file.getFileName());
                document.put("createdDate", new Date());
                document.put("data", file.getData());
                table.insert(document);
            }
        }
    }

    @GET
    @Path("/{username}/{filename}")
    @Produces(MediaType.APPLICATION_JSON)
    public FileInfo getFile(@PathParam("username") final String username, @PathParam("filename") final String filename) throws UnknownHostException {
        if (Authorize.isLoggedIn(username)) {
            MongoDBSingleton dbSingleton = MongoDBSingleton.getInstance();
            DB db = dbSingleton.getDb();
            DBCollection table = db.getCollection("files");

            BasicDBObject searchQuery = new BasicDBObject();
            searchQuery.put("username", username);
            searchQuery.put("fileName", filename);
            DBCursor cursor = table.find(searchQuery);

            FileInfo f = new FileInfo();
            if (cursor.hasNext()) {
                f.setUsername(username);
                f.setFileName(filename);
                f.setData(cursor.next().get("data").toString());
            }
            return f;
        }
        throw new RuntimeException();
    }

//    @GET
//    @Path("/{username}")
//    @Produces(MediaType.APPLICATION_JSON)
//    public ArrayList<String> getFileNames(@PathParam("username") final String username) {
//        MongoDBSingleton dbSingleton = MongoDBSingleton.getInstance();
//        DB db = dbSingleton.getDb();
//        DBCollection table = db.getCollection("files");
//
//        BasicDBObject searchQuery = new BasicDBObject();
//        searchQuery.put("username", username);
//        DBCursor cursor = table.find(searchQuery);
//        ArrayList<String> fileList = new ArrayList<String>();
//        while (cursor.hasNext()) {
//            fileList.add(cursor.next().get("fileName").toString());
//        }
//        return fileList;
//    }
}
