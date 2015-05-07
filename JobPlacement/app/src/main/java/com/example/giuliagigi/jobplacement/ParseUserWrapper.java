package com.example.giuliagigi.jobplacement;

import android.util.Log;

import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import bolts.Task;

/**
 * Created by MarcoEsposito90 on 04/05/2015.
 */
@ParseClassName("_User")
public class ParseUserWrapper extends ParseUser {

    protected static final String TYPE_FIELD = "type";
    public static final String TYPE_STUDENT = "Student";
    public static final String TYPE_COMPANY = "Company";
    public static final String USER_FIELD = "user";
    protected static final String EMAIL_VERIFIED_FIELD = "emailVerified";

    public static final String[] TYPES = new String[]{TYPE_STUDENT,TYPE_COMPANY};

    public ParseUserWrapper() {
        super();
    }

    public void setEmail(String email){
        super.setEmail(email);
    }
    public String getEmail(){
        return super.getEmail();
    }

    public void setUsername(String email){
        super.setUsername(email);
    }
    public String getUsername(){
        return super.getUsername();
    }

    public void setPassword(String password){
        super.setPassword(password);
    }

    public String getType() {
        return getString(TYPE_FIELD);
    }
    public void setType(String type) {
        this.put(TYPE_FIELD, type);
    }

    public boolean isEmailVerified(){
        return this.getBoolean(EMAIL_VERIFIED_FIELD);
    }

}
