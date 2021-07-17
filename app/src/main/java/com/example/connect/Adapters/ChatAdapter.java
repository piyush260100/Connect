package com.example.connect.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.connect.R;
import com.example.connect.models.messageModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter {

    ArrayList<messageModel> messageModels;
    Context context;
    String recId;

    int SENDER_VIEW_TYPE=1;
    int RECEIVER_VIEW_TYPE=2;

    public ChatAdapter(ArrayList<messageModel> messageModels, Context context) {
        this.messageModels = messageModels;
        this.context = context;
    }

    public ChatAdapter(ArrayList<messageModel> messageModels, Context context, String recId) {
        this.messageModels = messageModels;
        this.context = context;
        this.recId = recId;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       if(viewType==SENDER_VIEW_TYPE)
       {
           View view = LayoutInflater.from(context).inflate(R.layout.samplesender,parent,false);
           return new senderviewHolder(view);
       }
       else
       {
           View view = LayoutInflater.from(context).inflate(R.layout.samplereceiver,parent,false);
           return new receiverviewHolder(view);
       }

    }

    @Override
    public int getItemViewType(int position) {

        if(messageModels.get(position).getuId().equals(FirebaseAuth.getInstance().getUid()))//
        {
            return  SENDER_VIEW_TYPE;
        }
        else
        {
            return RECEIVER_VIEW_TYPE;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        messageModel messageModel=messageModels.get(position);

        //Delete Alert Dialog
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                new AlertDialog.Builder(context).setTitle("Delete")
                        .setMessage("Are you sure you want to delete this message ")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FirebaseDatabase database=FirebaseDatabase.getInstance();
                                String senderRoom = FirebaseAuth.getInstance().getUid() + recId;
                                database.getReference().child("Chats").child(senderRoom)
                                        .child(messageModel.getuId()).setValue(null);

                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                });

                return false;
            }
        });

        if(holder.getClass()== senderviewHolder.class)
        {
            ((senderviewHolder)holder).senderMsg.setText(messageModel.getMsgText());

        }
        else
        {
            ((receiverviewHolder)holder).receiverMsg.setText(messageModel.getMsgText());
        }
    }

    @Override
    public int getItemCount() {
        return messageModels.size();
    }

    // receiver viewholder
    public class receiverviewHolder extends RecyclerView.ViewHolder{

        TextView receiverMsg, receiverTime;

        public receiverviewHolder(@NonNull View itemView) {
            super(itemView);

            receiverMsg=itemView.findViewById(R.id.receiverMsg);
            receiverTime=itemView.findViewById(R.id.receiverTime);

        }
    }

    public class senderviewHolder extends RecyclerView.ViewHolder{

        TextView senderMsg, senderTime;

        public senderviewHolder(@NonNull View itemView) {
            super(itemView);

            senderMsg=itemView.findViewById(R.id.senderMsg);
            senderTime=itemView.findViewById(R.id.senderTime);

        }
    }

}
