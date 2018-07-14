package io.github.rgdagir.parsetagram;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.FileProvider;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements CameraFragment.OnItemSelectedListener {

    // defining fragments
    Fragment feedFragment = new FeedFragment();
    Fragment postPic = new CameraFragment();
    Fragment profile = new ProfileFragment();

    public final String APP_TAG = "Parsetagram";
    public final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034;
    public String photoFileName = "photo.jpg";
    File photoFile;

    final FragmentManager fragmentManager = getSupportFragmentManager();
    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        FragmentTransaction homeTransaction = fragmentManager.beginTransaction();
        homeTransaction.replace(R.id.fragmentContainer, feedFragment).addToBackStack(null).commit();
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.home:
                        // main feed
                        FragmentTransaction homeTransaction = fragmentManager.beginTransaction();
                        homeTransaction.replace(R.id.fragmentContainer, feedFragment).addToBackStack(null).commit();
                        return true;
                    case R.id.postpic:
                        FragmentTransaction postPicTransaction = fragmentManager.beginTransaction();
                        postPicTransaction.replace(R.id.fragmentContainer, postPic).addToBackStack(null).commit();
                        return true;

                    case R.id.profile:
                        FragmentTransaction profileTransaction = fragmentManager.beginTransaction();
                        profileTransaction.replace(R.id.fragmentContainer, profile).addToBackStack(null).commit();

                        return true;
                }
                return false;
            }
        });


    }


    // Returns the File for a photo stored on disk given the fileName
    public void onLaunchCamera() {
        // create Intent to take a picture and return control to the calling application
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Create a File reference to access to future access
        photoFile = getPhotoFileUri(photoFileName);

        // wrap File object into a content provider
        // required for API >= 24
        // See https://guides.codepath.com/android/Sharing-Content-with-Intents#sharing-files-with-api-24-or-higher
        Uri fileProvider = FileProvider.getUriForFile(this, "io.gihtub.rgdagir.fileprovider", photoFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);

        // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
        // So as long as the result is not null, it's safe to use the intent.
        if (intent.resolveActivity(getPackageManager()) != null) {
            // Start the image capture intent to take photo
            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }
    }

    public File getPhotoFileUri(String fileName) {
        // Get safe storage directory for photos
        // Use `getExternalFilesDir` on Context to access package-specific directories.
        // This way, we don't need to request external read/write runtime permissions.
        File mediaStorageDir = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), APP_TAG);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
            Log.d(APP_TAG, "failed to create directory");
        }

        // Return the file target for the photo based on filename
        File file = new File(mediaStorageDir.getPath() + File.separator + fileName);

        return file;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            // by this point we have the camera photo on disk
            Log.d("onActivityResult", "we got here bros");

            String imagePath = photoFile.getAbsolutePath();
            Bitmap rawTakenImage = BitmapFactory.decodeFile(imagePath);
            Bitmap resizedBitmap = BitmapScaler.scaleToFitWidth(rawTakenImage, 400);
            File photoFile = new File(imagePath);

            ((CameraFragment) postPic).parseFile = new ParseFile(photoFile);
            ((CameraFragment) postPic).ivPhoto.setImageBitmap(resizedBitmap);



        } else { // Result was a failure
            Toast.makeText(this, "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void killFragment(){
        // launch camera
        Log.d("KILLING FRAGMENT", "I'm in killfragment");
        FragmentTransaction backTransition = fragmentManager.beginTransaction();
        backTransition.replace(R.id.fragmentContainer, feedFragment).addToBackStack(null).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        ActionBar actionBar = getSupportActionBar();

        actionBar.setTitle("Instagram");
        actionBar.setLogo(R.drawable.nav_logo_whiteout);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.logout){
            logout();
        }
        return super.onOptionsItemSelected(item);
    }

    public void logout(){
        ParseUser currentUser = ParseUser.getCurrentUser(); // this will now be null
        currentUser.logOutInBackground();
        Intent goToLogin = new Intent(HomeActivity.this, LoginActivity.class);
        goToLogin.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(goToLogin);
    }
}
