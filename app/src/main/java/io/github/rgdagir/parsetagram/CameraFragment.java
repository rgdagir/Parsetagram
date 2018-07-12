package io.github.rgdagir.parsetagram;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import java.lang.String;

import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;

import static android.app.Activity.RESULT_OK;

public class CameraFragment extends Fragment {

    // The onCreateView method is called when Fragment should create its View object hierarchy,
    // either dynamically or via XML layout inflation.
    EditText caption;
    ImageView ivPhoto;
    Button postBtn;
    ParseUser user;

    private OnItemSelectedListener listener;

    ParseFile parseFile;

    public interface OnItemSelectedListener {
        // This can be any number of events to be sent to the activity
        void onLaunchCamera();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnItemSelectedListener) {
            listener = (OnItemSelectedListener) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement MyListFragment.OnItemSelectedListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        View v = inflater.inflate(R.layout.camera_fragment, parent, false);
        caption = v.findViewById(R.id.caption);
        ivPhoto = v.findViewById(R.id.ivPhoto);
        postBtn = v.findViewById(R.id.postBtn);
        user = ParseUser.getCurrentUser();

        listener.onLaunchCamera();

        postBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser user = ParseUser.getCurrentUser();
                createPost(caption.getText().toString(), parseFile, user);
                Log.d("postButton has been", "clicked");
            }
        });

        return v;
    }

    private void createPost(String caption, final ParseFile imageFile, ParseUser user){
        final Post newPost = new Post();
        newPost.setCaption(caption);
        newPost.setUser(user);
        newPost.setImage(imageFile);
        newPost.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null){
                    Log.d("Post creation!", "yay");
                } else {
                    Log.d("Post creation", "failed");

                    e.printStackTrace();
                }
            }
        });


    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


}
