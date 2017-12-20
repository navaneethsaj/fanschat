package com.agni.asus.fanschat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<MessageObject> messageObjectArrayList;
    private String username="Anonymous";

    Button sendbutton;
    ListView chatlistview;
    EditText textmessage;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chatlistview=(ListView)findViewById(R.id.chatlistview);
        sendbutton=(Button)findViewById(R.id.sendbutton);
        textmessage=(EditText)findViewById(R.id.textmessage);

        messageObjectArrayList=new ArrayList<>();

        messageObjectArrayList.add(new MessageObject("Hello",username,null));

        /*String list[]={"cat","dog"};
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,list);
        */
        MessageAdapter chatadapter=new MessageAdapter(getApplicationContext(),messageObjectArrayList);
        chatlistview.setAdapter(chatadapter);

        sendbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (textmessage.getText().toString()!=null){

                    textmessage.setText("");
                }
            }
        });
    }
}
