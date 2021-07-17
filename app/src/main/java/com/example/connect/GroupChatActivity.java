package com.example.connect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.connect.Adapters.ChatAdapter;
import com.example.connect.databinding.ActivityChatDetailBinding;
import com.example.connect.databinding.ActivityGroupChatBinding;
import com.example.connect.models.messageModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

public class GroupChatActivity extends AppCompatActivity {

    ActivityGroupChatBinding binding;
    FirebaseDatabase database;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGroupChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(GroupChatActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        final ArrayList<messageModel> messageModels = new ArrayList<>();

        final String senderId = auth.getUid();  // user login on app is send
        binding.userName.setText("Friends Group");

        final ChatAdapter Groupadapter = new ChatAdapter(messageModels, this);
        binding.chatRecyclerView.setAdapter(Groupadapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.chatRecyclerView.setLayoutManager(layoutManager);

        database.getReference().child("Groupchats").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                messageModels.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    messageModel model = snapshot1.getValue(messageModel.class);
                    messageModels.add(model);
                }
                Groupadapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(binding.messageSpace.getText().toString().isEmpty()){
                    binding.messageSpace.setError("Enter a message");
                    return;
                }

                final String message = binding.messageSpace.getText().toString();
                final messageModel model = new messageModel(senderId, message);
                model.setTimeStamp(new Date().getTime());
                binding.messageSpace.setText("");

                database.getReference().child("Groupchats").push().setValue(model)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {

                            }
                        });

            }
        });

    }
}