package com.example.myapplication;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {
    private final String UKEY = "uKey";
    private final String PKEY = "pKey";
    private final String EKEY = "eKey";
    private final String PREF_NAME = "Authentication";
    private TextView textName, textEmail;
    Button btnLogOut, btnChangeImage, ShowMapBtn;
    private CircleImageView profileImage;
    private SharedPreferences sharedPreferences;
    private static final int PERMISSION_REQUEST_CODE = 1;
    private static final int PICK_IMAGE = 1;


    public ProfileFragment() {
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
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);
        // Check and request permissions if needed
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions( requireActivity(),
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        PERMISSION_REQUEST_CODE);
            }
        }

        // Initialize UI components
        profileImage = view.findViewById(R.id.profile_image);
        textName = view.findViewById(R.id.nameTextView);
        textEmail = view.findViewById(R.id.emailTextView);
        btnLogOut = view.findViewById(R.id.logoutButton);
        btnChangeImage = view.findViewById(R.id.btn_change_photo);
        ShowMapBtn = view.findViewById(R.id.mapBtn);
        ShowMapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MapActivity.class);
                startActivity(intent);
            }
        });

//        Fragment fragment = new MapFragment();
//        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.frameLayout, new MapFragment());
//        fragmentTransaction.commit();
//        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment).commit();
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences1 = getActivity().getSharedPreferences(PREF_NAME, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences1.edit();
                editor.putBoolean("flag",false);
                editor.commit();
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
        // Initialize SharedPreferences
        sharedPreferences = requireActivity().getSharedPreferences(PREF_NAME, MODE_PRIVATE);

        // Load user details from SharedPreferences
        loadUserDetails();

        // Button to change profile image (this opens the gallery)
        btnChangeImage.setOnClickListener(v -> {
            // Start an intent to pick an image from the gallery
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, PICK_IMAGE);
        });



        return view;
    }


    private void loadUserDetails() {
        // Load the profile image, name, and email from SharedPreferences
        String name = sharedPreferences.getString(UKEY, "No Name");
        String email = sharedPreferences.getString(EKEY, "No Email");

        // Set the loaded details into TextViews
        textName.setText(name);
        textEmail.setText(email);

        // Load the profile image from SharedPreferences (you can store the image as a URI or base64 string)
        String imageUri = sharedPreferences.getString("profile_image", null);
        if (imageUri != null) {
            // If there is a saved profile image, load it into the ImageView (this would require a method to load the image)
            // For now, use a placeholder as default.
            profileImage.setImageURI(android.net.Uri.parse(imageUri));
        } else {
            // Set default profile image if no image exists
            profileImage.setImageResource(R.drawable.ic_profile_placeholder);
        }
    }

    // Handling the result from the image picker
    @Override
     public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            // Get the image URI from the intent
            android.net.Uri selectedImageUri = data.getData();

            // Set the selected image as the profile image
            profileImage.setImageURI(selectedImageUri);

            // Save the new image URI to SharedPreferences
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("profile_image", selectedImageUri.toString());
            editor.apply();
        }
    }

    // Handle permission request result
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted
                Toast.makeText(getContext(), "Permission Granted", Toast.LENGTH_SHORT).show();
            } else {
                // Permission denied
                Toast.makeText(getContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}