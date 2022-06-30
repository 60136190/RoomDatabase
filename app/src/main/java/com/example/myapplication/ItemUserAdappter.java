package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ItemUserAdappter extends RecyclerView.Adapter<ItemUserAdappter.ViewHoder>{
    private List<ItemUser> mListUser;
    private IClickItemUser iClickItemUser;

    public interface IClickItemUser{
        void updateUser(ItemUser itemUser);

        // delete user
        void deteleUser(ItemUser itemUser);
    }

    public ItemUserAdappter(IClickItemUser iClickItemUser) {
        this.iClickItemUser = iClickItemUser;
    }

    public void setData(List<ItemUser> list){
        this.mListUser = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemuser,parent,false);
        return new ViewHoder(view) ;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoder holder, int position) {
        ItemUser itemUser = mListUser.get(position);
        if (itemUser == null){
            return;
        }
        holder.user_name.setText(itemUser.getUserName());
        holder.pass_word.setText(itemUser.getPassWord());
        holder.age.setText(itemUser.getAge());

        holder.btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iClickItemUser.updateUser(itemUser);
            }
        });

        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iClickItemUser.deteleUser(itemUser);

            }
        });


    }

    @Override
    public int getItemCount() {
        if(mListUser != null)
        {
            return mListUser.size();
        }
        return 0;
    }

    public class ViewHoder extends RecyclerView.ViewHolder{
        private TextView user_name;
        private TextView pass_word;
        private TextView age;
        private Button btn_update;
        private Button btn_delete;
        public ViewHoder(@NonNull View itemView) {
            super(itemView);
            user_name = itemView.findViewById(R.id.tv_username);
            pass_word = itemView.findViewById(R.id.tv_password);
            age = itemView.findViewById(R.id.tv_age);
            btn_update = itemView.findViewById(R.id.btn_update);
            btn_delete = itemView.findViewById(R.id.btn_delete);
        }
    }
}
