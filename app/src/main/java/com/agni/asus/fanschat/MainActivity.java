package com.agni.asus.fanschat;

import android.content.Intent;
import android.net.Uri;
import android.renderscript.Sampler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    ArrayList<MessageObject> messageObjectArrayList;
    private String username=Values.anonymous;
    private static final int RC_SIGN_IN=123;
    private static final int RC_PHOTO_PICKER =  2;

    Button sendbutton;
    ImageButton imageButton;
    ListView chatlistview;
    EditText textmessage;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseStorage firebaseStorage;
    StorageReference imagestoragereference;
    FirebaseAuth firebaseAuth;
    FirebaseAuth.AuthStateListener authStateListener;
    MessageAdapter chatadapter;
    ImageView picaso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chatlistview=(ListView)findViewById(R.id.chatlistview);
        sendbutton=(Button)findViewById(R.id.sendbutton);
        textmessage=(EditText)findViewById(R.id.textmessage);
        imageButton=(ImageButton)findViewById(R.id.image_picker);
        picaso=(ImageView)findViewById(R.id.picaso_test);

        Picasso.with(getApplicationContext()).load("http://i.imgur.com/DvpvklR.png").into(picaso);

        firebaseDatabase=FirebaseDatabase.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        databaseReference=firebaseDatabase.getReference().child(Values.group1);
        firebaseStorage=FirebaseStorage.getInstance();
        imagestoragereference=firebaseStorage.getReference().child(Values.imagereference);

        messageObjectArrayList=new ArrayList<>();

        chatadapter=new MessageAdapter(getApplicationContext(),messageObjectArrayList);
        chatlistview.setAdapter(chatadapter);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), RC_PHOTO_PICKER);*/
                Toast.makeText(getApplicationContext(),"Clicked",Toast.LENGTH_SHORT).show();
            }
        });

        //!!!!!!-------->>>>>> MEssage repeats the number of times the intent code is runned


        sendbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (textmessage.getText().toString()!=null){
                    MessageObject message=new MessageObject(textmessage.getText().toString(),username,null);
                    databaseReference.push().setValue(message);
                    textmessage.setText("");
                }
            }
        });

        authStateListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user=firebaseAuth.getCurrentUser();
                if (user != null){
                    onSignedInInitialize(user.getDisplayName());
                    Toast.makeText(getApplicationContext(),"Signed in",Toast.LENGTH_SHORT).show();
                }else {
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setIsSmartLockEnabled(false)
                                    .setAvailableProviders(
                                            Arrays.asList(new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                                                    new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build()))
                                    .build(),
                            RC_SIGN_IN);
                }
            }
        };
    }

    private void onSignedInInitialize(String displayName) {
        username=displayName;
        attachDatabaseListener();
    }

    private void onSignedOutCleaner(){
        username= Values.anonymous;
        messageObjectArrayList.clear();
        detachDatabaseListener();
    }

    private void attachDatabaseListener(){
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                MessageObject messageObject = dataSnapshot.getValue(MessageObject.class);
                messageObjectArrayList.add(messageObject);
                chatlistview.setSelection(chatadapter.getCount() - 1);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void detachDatabaseListener(){
        databaseReference.addChildEventListener(null);
    }

    @Override
    protected void onResume() {
        super.onResume();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        firebaseAuth.removeAuthStateListener(authStateListener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==RC_SIGN_IN){
            if (resultCode==RESULT_OK){
                Toast.makeText(getApplicationContext(),"Welcome",Toast.LENGTH_SHORT).show();
            }
            else if (resultCode==RESULT_CANCELED){
                Toast.makeText(getApplicationContext(),"Exit",Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.sign_out_menu :
                firebaseAuth.signOut();
        }
        return super.onOptionsItemSelected(item);
    }
}
