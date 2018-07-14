package io.github.rgdagir.parsetagram;

import com.parse.Parse;
import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.Date;

@ParseClassName("Post")
public class Post extends ParseObject {

    private static final String KEY_USER = "user";
    private static final String KEY_IMAGE = "image";
    private static final String KEY_CAPTION = "caption";
    private static final String KEY_DATE = "createdAt";


    public void setUser(ParseUser user) {
        put(KEY_USER, user);
    }

    public ParseUser getUser(){
        return getParseUser(KEY_USER);
    }

    public void setImage(ParseFile image){
        put(KEY_IMAGE, image);
    }

    public ParseFile getImage(){
        return getParseFile(KEY_IMAGE);
    }

    public void setDate(Date date){
        put(KEY_DATE, date);
    }

    public Date getDate() {
        return getDate(KEY_DATE);
    }

    public void setCaption(String caption){
        put(KEY_CAPTION, caption);
    }

    public String getCaption(){
        return getString(KEY_CAPTION);
    }

    public static class Query extends ParseQuery<Post> {
        public Query() {
            super(Post.class);
        }

        public Query getTop(){
            setLimit(20);
            return this;
        }

        public Query withUser(){
            include("user");
            return this;
        }
    }

}
