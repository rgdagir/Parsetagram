package io.github.rgdagir.parsetagram;

import android.app.Application;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseObject;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class ParseApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // Use for troubleshooting -- remove this line when live
        Parse.setLogLevel(Parse.LOG_LEVEL_VERBOSE);

        // Use for monitoring Parse OkHttp traffic
        // Can be Level.BASIC, Level.HEADERS, or Level.BODY
        // See http://square.github.io/okhttp/3.x/logging-interceptor/ to see the options.
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.networkInterceptors().add(httpLoggingInterceptor);

        // Registering the parse models
        ParseObject.registerSubclass(Post.class);

        // set applicationId, and server based on the values of the Heroku app
        // clientKey is not needed unless explicitly configured
        // any network interceptors must be added with the Configuration Builder given this syntax
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("parsetagramId")
                .clientKey(null)
                .clientBuilder(builder)
                .server("https://parsetagram-fb.herokuapp.com/parse/").build());
    }
}
