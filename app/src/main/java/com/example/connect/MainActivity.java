package com.example.connect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.connect.Adapters.FragmentAdapter;
import com.example.connect.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth=FirebaseAuth.getInstance();
        binding.viewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager()));
        binding.tabLayout.setupWithViewPager(binding.viewPager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.logout:
                auth.signOut();
                Toast.makeText(this,"Sign out",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(this,SignIn.class);
                startActivity(intent);
                break;

            case R.id.Settings:
                Toast.makeText(this,"Settings",Toast.LENGTH_SHORT).show();
                Intent intent3=new Intent(this,SettingsActivity.class);
                startActivity(intent3);
                break;

            case R.id.groupChat:
                Toast.makeText(this,"Groupchat",Toast.LENGTH_SHORT).show();
                Intent intent2=new Intent(this,GroupChatActivity.class);
                startActivity(intent2);
                break;
        }

        return true;
    }
}