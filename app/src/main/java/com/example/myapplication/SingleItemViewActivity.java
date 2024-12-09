package com.example.myapplication;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.SharedPreferences;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.gson.reflect.TypeToken;


import java.lang.reflect.Type;
import com.google.gson.Gson;

import java.util.ArrayList;

public class SingleItemViewActivity extends AppCompatActivity {
    private static final String CHANNEL_ID = "default_channel";
    private ArrayList<Item> bookedBooks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_single_item_view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageView imageView = findViewById(R.id.detail_image);
        TextView titleTextView = findViewById(R.id.detail_title);
        TextView authorTextView = findViewById(R.id.detail_price);
        TextView descriptionTextView = findViewById(R.id.detail_description);
        Button addButton = findViewById(R.id.add_button);

        Item item = (Item) getIntent().getSerializableExtra("item");
        imageView.setImageResource(item.getImage());
        titleTextView.setText(item.getText1());
        authorTextView.setText(item.getText3());
        descriptionTextView.setText(item.getDescription());

        loadBookedBooks();

        addButton.setOnClickListener((View v) -> {
            Toast.makeText(SingleItemViewActivity.this, "Item added to bookings", Toast.LENGTH_LONG).show();

            SharedPreferences sharedPreferences = getSharedPreferences("bookings", Context.MODE_PRIVATE);
            Gson gson = new Gson();
            String json = sharedPreferences.getString("bookedBooks", null);
            Type type = new TypeToken<ArrayList<Item>>() {}.getType();
            bookedBooks = gson.fromJson(json, type);

            if (bookedBooks == null) {
                bookedBooks = new ArrayList<>();
            }


            bookedBooks.add(item);

            saveBookedBooks();
            sendNotification();


        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Default Channel";
            String description = "Channel for notifications";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            // Register the channel with the system
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

    }


    private void sendNotification() {
        // Build the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.baseline_notifications_24)  // Set your icon here
                .setContentTitle("Service Booked")
                .setContentText("A new service has been booked.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        // Get the NotificationManager and send the notification
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        notificationManager.notify(1,builder.build()); // '1' is the notification ID
    }

    private void saveBookedBooks() {
        SharedPreferences sharedPreferences = getSharedPreferences("bookings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(bookedBooks);
        editor.putString("bookedBooks", json);
        editor.apply();
    }

    private void loadBookedBooks() {
        SharedPreferences sharedPreferences = getSharedPreferences("bookings", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("bookedBooks", null);
        Type type = new TypeToken<ArrayList<Item>>() {}.getType();
        bookedBooks = gson.fromJson(json, type);

        if (bookedBooks == null) {
            bookedBooks = new ArrayList<>();
        }
    }
}