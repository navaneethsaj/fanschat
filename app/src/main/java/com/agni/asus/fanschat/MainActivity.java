package com.agni.asus.fanschat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<MessageObject> messageObjectArrayList;
    private String username=Values.anonymous;

    Button sendbutton;
    ListView chatlistview;
    EditText textmessage;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    MessageAdapter chatadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chatlistview=(ListView)findViewById(R.id.chatlistview);
        sendbutton=(Button)findViewById(R.id.sendbutton);
        textmessage=(EditText)findViewById(R.id.textmessage);

        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference().child(Values.group1);

        messageObjectArrayList=new ArrayList<>();

        chatadapter=new MessageAdapter(getApplicationContext(),messageObjectArrayList);
        chatlistview.setAdapter(chatadapter);

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
}
