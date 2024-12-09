package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.Item;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class BookingsFragment extends Fragment {
    private ArrayList<Item> bookedBooks;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bookings, container, false);

        ListView listView = view.findViewById(R.id.listView);
        loadBookedBooks();

        GridAdapter adapter = new GridAdapter(getContext(), bookedBooks);
        listView.setAdapter(adapter);

        return view;
    }

    private void loadBookedBooks() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("bookings", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("bookedBooks", null);
        Type type = new TypeToken<ArrayList<Item>>() {}.getType();
        bookedBooks = gson.fromJson(json, type);

        if (bookedBooks == null) {
            bookedBooks = new ArrayList<>();
        }
    }
}
