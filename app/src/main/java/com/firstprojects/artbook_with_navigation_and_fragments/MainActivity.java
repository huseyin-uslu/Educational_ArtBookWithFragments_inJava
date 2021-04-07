package com.firstprojects.artbook_with_navigation_and_fragments;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.firstprojects.artbook_with_navigation_and_fragments.fragments.FirstFragment;
import com.firstprojects.artbook_with_navigation_and_fragments.fragments.FirstFragmentDirections;
import com.firstprojects.artbook_with_navigation_and_fragments.fragments.SecondFragment;
import com.firstprojects.artbook_with_navigation_and_fragments.fragments.SecondFragmentDirections;

public class MainActivity extends AppCompatActivity {
   SecondFragment secondFragment;
   FragmentManager fragmentManager;
   FragmentTransaction fragmentTransaction;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_layout_associeted_with_main_activity,menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


            if (item.getItemId() == R.id.menu_layout_item_addImage) {
               /* fragmentTransaction.replace(R.id.fragment, secondFragment);
                fragmentTransaction.hide(new FirstFragment());
                fragmentTransaction.commit(); }*/

//              NavDirections navDirections = FirstFragmentDirections.actionFirstFragmentToSecondFragment();
                NavHostFragment navHostFragment = (NavHostFragment) fragmentManager.findFragmentById(R.id.fragment);
                assert navHostFragment != null;
                FirstFragmentDirections.ActionFirstFragmentToSecondFragment action = FirstFragmentDirections.actionFirstFragmentToSecondFragment();
                action.setİsItnew(true);

                NavController navController = navHostFragment.getNavController();
                navController.navigate(action);

            }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        secondFragment = new SecondFragment();
        fragmentManager = this.getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
    }
}