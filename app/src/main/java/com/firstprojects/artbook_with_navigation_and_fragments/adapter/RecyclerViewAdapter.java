package com.firstprojects.artbook_with_navigation_and_fragments.adapter;

import android.content.Intent;
import android.content.IntentSender;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.firstprojects.artbook_with_navigation_and_fragments.MainActivity;
import com.firstprojects.artbook_with_navigation_and_fragments.R;
import com.firstprojects.artbook_with_navigation_and_fragments.fragments.FirstFragmentDirections;
import com.firstprojects.artbook_with_navigation_and_fragments.fragments.SecondFragmentArgs;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolderOne>{
    private ArrayList<String> nameArrayList;

    public RecyclerViewAdapter(ArrayList<String> nameArrayList) {
        this.nameArrayList = nameArrayList;
    }
    @NonNull
    @Override
    public ViewHolderOne onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
       View view = layoutInflater.inflate(R.layout.layout_row_recyclerview,parent,false);
       return new ViewHolderOne(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderOne holder, int position) {
        holder.textView.setText(nameArrayList.get(position));
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
           try{
               FirstFragmentDirections.ActionFirstFragmentToSecondFragment action = FirstFragmentDirections.actionFirstFragmentToSecondFragment();
action.setİsItnew(false);
action.setİd(holder.getAdapterPosition());
               System.out.println("setResources is succesfull");
                Navigation.findNavController(v).navigate(action);}catch (Exception e) {
               e.printStackTrace();
               System.out.println(e.getLocalizedMessage());
           }


                return false;
            }
        });
    }
    @Override
    public int getItemCount() {
        System.out.println("Arraylist size = " + nameArrayList.size());
        return nameArrayList.size();

    }

    public class ViewHolderOne extends RecyclerView.ViewHolder {
      TextView textView;
        public ViewHolderOne(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.layout_row_recyclerview_itemgroup_textView);
        }
    }
}
