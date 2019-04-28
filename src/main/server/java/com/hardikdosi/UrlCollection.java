package com.hardikdosi;

/**
 * Created by Hardik Dosi on 12/1/2016.
 */
public class UrlCollection {
    private String registerUser;
    private String userDetails;
    private String authenticate;
    private String logOut;
    private String saveFile;
    private String loadFile;

    private String submitCode;
    private String codeStatus;

    public String getRegisterUser() {
        return registerUser;
    }

    public void setRegisterUser(String registerUser) {
        this.registerUser = registerUser;
    }

    public String getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(String userDetails) {
        this.userDetails = userDetails;
    }

    public String getAuthenticate() {
        return authenticate;
    }

    public void setAuthenticate(String authenticate) {
        this.authenticate = authenticate;
    }

    public String getLogOut() {
        return logOut;
    }

    public void setLogOut(String logOut) {
        this.logOut = logOut;
    }

    public String getSaveFile() {
        return saveFile;
    }

    public void setSaveFile(String saveFile) {
        this.saveFile = saveFile;
    }

    public String getLoadFile() {
        return loadFile;
    }

    public void setLoadFile(String loadFile) {
        this.loadFile = loadFile;
    }

    public String getSubmitCode() {
        return submitCode;
    }

    public void setSubmitCode(String submitCode) {
        this.submitCode = submitCode;
    }

    public String getCodeStatus() {
        return codeStatus;
    }

    public void setCodeStatus(String codeStatus) {
        this.codeStatus = codeStatus;
    }
}
