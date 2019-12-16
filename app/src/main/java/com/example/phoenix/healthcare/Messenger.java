package com.example.phoenix.healthcare;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.example.phoenix.healthcare.Adapter.ChatAdapter;
import com.example.phoenix.healthcare.Models.ChatModel;

import java.util.ArrayList;
import java.util.List;

public class Messenger extends AppCompatActivity {
    ListView listView;
    static int i=0;
    EditText editText;
    List<ChatModel> list_chat = new ArrayList<>();
    FloatingActionButton btn_send_message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.messenger);
        getSupportActionBar().setTitle("Messenger");
        listView = findViewById(R.id.list_of_message);
        editText = findViewById(R.id.user_message);
        btn_send_message = findViewById(R.id.fab);

        btn_send_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = editText.getText().toString();
                if (i==1){
                    ChatModel model = new ChatModel(text,true); // user send message
                    list_chat.add(model);
                    i=0;
                }
                else if (i==0){
                ChatModel model2 = new ChatModel(text,false); // user send message
                list_chat.add(model2);
                i=1;}
                //remove user message
                editText.setText("");
                ChatAdapter adapter = new ChatAdapter(list_chat,getApplicationContext());
                listView.setAdapter(adapter); }
        });
    }
}
