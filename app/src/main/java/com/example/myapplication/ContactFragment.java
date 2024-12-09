package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ContactFragment extends Fragment {

    CardView emailCard, messageCard;
    public ContactFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_contact, container, false);
        messageCard = view.findViewById(R.id.message_card);
        emailCard = view.findViewById(R.id.email_card);
        messageCard.setOnClickListener(v -> {


                Intent intent = new Intent(getActivity(), MessageActivity.class);
                startActivity(intent);

        });

        emailCard.setOnClickListener(v -> {

            Intent intent = new Intent(getActivity(), EmailActivity.class);
            startActivity(intent);

        });
                return view;

    }
}