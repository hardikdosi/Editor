package com.hardikdosi;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by Hardik Dosi on 12/1/2016.
 */

@Path("/service_description")
public class ServiceDescription {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public UrlCollection getServiceDescription() {
        UrlCollection urls = new UrlCollection();
        urls.setAuthenticate("/rsc/users/auth");
        urls.setCodeStatus("http://api.compilers.sphere-engine.com/api/v3/submissions/");
        urls.setLoadFile("/rsc/files/");
        urls.setLogOut("/rsc/users/logout");
        urls.setRegisterUser("/rsc/users");
        urls.setSaveFile("/rsc/files");
        urls.setSubmitCode("http://api.compilers.sphere-engine.com/api/v3/submissions?access_token=");
        urls.setUserDetails("/rsc/users/");

        return urls;
    }
}
