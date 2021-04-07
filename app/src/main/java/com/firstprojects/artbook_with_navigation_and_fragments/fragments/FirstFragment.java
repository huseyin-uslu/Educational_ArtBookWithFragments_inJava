package com.firstprojects.artbook_with_navigation_and_fragments.fragments;

import android.app.DownloadManager;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firstprojects.artbook_with_navigation_and_fragments.R;
import com.firstprojects.artbook_with_navigation_and_fragments.adapter.RecyclerViewAdapter;

import java.util.ArrayList;

public class FirstFragment extends Fragment {


    public FirstFragment() {
    }

    public static FirstFragment newInstance() {
        FirstFragment fragment = new FirstFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;
    ArrayList<String> arrayListName;

    SQLiteDatabase sqLite;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerView);
        arrayListName = new ArrayList<>();

       try {
           sqLite = requireActivity().openOrCreateDatabase("Arts", Context.MODE_PRIVATE, null);
       }catch (Exception e) {
           e.printStackTrace();
           System.out.println(e.getLocalizedMessage());
       }
        getDataFromSqlite();


    }
    public void getDataFromSqlite() {
       try{ Cursor cursor = sqLite.rawQuery("SELECT * FROM pictures",null);
       // int rawID = cursor.getColumnIndex("id");
        int rawName = cursor.getColumnIndex("name");
        while(cursor.moveToNext()) {
            arrayListName.add(cursor.getString(rawName));
            recyclerViewAdapter = new RecyclerViewAdapter(arrayListName);

        }cursor.close(); }catch (Exception e) {
           e.printStackTrace();
           System.out.println("YOU'VE GOT A PROBLEM = " + e.getLocalizedMessage());
       }
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        recyclerView.setAdapter(recyclerViewAdapter);

    }

}
