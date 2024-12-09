package com.example.myapplication;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.RouteListingPreference;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;


import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HomeFragment extends Fragment {
    List<Item> filteredList;
    List<Item> gridItems = new ArrayList<>();
    List<Item> filteredItemList;

    private SearchView searchView;
    private RecyclerView horizontalRecyclerView;
    private GridView gridView;
     ListView listView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        filteredItemList = new ArrayList<>();
        // Initialize views
        searchView = view.findViewById(R.id.search);
        listView = view.findViewById(R.id.listView);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });
        horizontalRecyclerView = view.findViewById(R.id.recycler_View);
        gridView = view.findViewById(R.id.gridView);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Item selectedItem = gridItems.get(position);
                Intent intent = new Intent(getContext(), SingleItemViewActivity.class);
                intent.putExtra("item",  selectedItem);
                startActivity(intent);
            }
        });




        listView.setVisibility(View.GONE);
        gridView.setVisibility(View.VISIBLE);
        horizontalRecyclerView.setVisibility(View.VISIBLE);
        // Set up the horizontal RecyclerView
        setupRecyclerView();

        // Set up the GridView
        setupGridView();

        return view;
    }



    private void setupRecyclerView() {
        List<Item> horizontalItems = new ArrayList<>();
        horizontalItems.add(new Item(R.drawable.carpet_cleaning, "Carpet Cleaning", "Discounted Price", "Rs. 1200","Professional carpet cleaning to remove stains, dirt, and allergens, leaving your carpets fresh, clean, and hygienic." ));
        horizontalItems.add(new Item(R.drawable.chair_cleaning, "Chair Cleaning", "Discounted Price", "Rs. 1274","Thorough chair cleaning to eliminate stains, dust, and odors, ensuring your chairs look and feel like new."));
        horizontalItems.add(new Item(R.drawable.curtain_cleaning, "Curtain Cleaning", "Discounted Price", "Rs. 1234","Expert curtain cleaning to remove dust, stains, and allergens, restoring freshness and elegance to your home decor."));
        horizontalItems.add(new Item(R.drawable.cement_water_tank_cleaning, "Cement Water Tank Cleaning", "Discounted Price", "Rs. 1075","Comprehensive cleaning of cement water tanks to remove dirt, algae, and impurities, ensuring safe and hygienic water storage."));
        horizontalItems.add(new Item(R.drawable.solar_panel_cleaning, "Solar Pane Cleaning", "Discounted Price", "Rs. 1000","Efficient solar panel cleaning to remove dirt and debris, maximizing energy efficiency and extending panel lifespan."));
        horizontalItems.add(new Item(R.drawable.sofa_cleaning, "Sofa Cleaning", "Discounted Price", "Rs. 934","Professional sofa cleaning to remove stains, dust, and odors, restoring comfort and freshness to your furniture."));
        horizontalItems.add(new Item(R.drawable.salon_deep_cleaning, "Salon Deep Cleaning", "Discounted Price", "Rs. 654","Comprehensive salon deep cleaning to maintain a spotless, hygienic, and welcoming environment for your clients."));
        horizontalItems.add(new Item(R.drawable.room_deep_cleaning, "Room Deep Cleaning", "Discounted Price", "Rs. 1124","Detailed room deep cleaning to eliminate dust, stains, and germs, creating a fresh and healthy living space."));
        horizontalItems.add(new Item(R.drawable.restaurant_deep_cleaning, "Restaurant Deep Cleaning", "Discounted Price", "Rs. 1564","Thorough restaurant deep cleaning to ensure a spotless, hygienic environment, meeting high standards for food safety and customer satisfaction."));


        HorizontalAdapter horizontalAdapter = new HorizontalAdapter(horizontalItems,item -> {
            Intent intent = new Intent(getContext(), SingleItemViewActivity.class);
            intent.putExtra("item", item);

            startActivity(intent);
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        horizontalRecyclerView.setLayoutManager(linearLayoutManager);
        horizontalRecyclerView.setAdapter(horizontalAdapter);
        LinearSnapHelper linearSnapHelper = new LinearSnapHelper();
        linearSnapHelper.attachToRecyclerView(horizontalRecyclerView);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(linearLayoutManager.findLastCompletelyVisibleItemPosition()<(horizontalAdapter.getItemCount()-1)){
                    linearLayoutManager.smoothScrollToPosition(horizontalRecyclerView, new RecyclerView.State(), linearLayoutManager.findLastCompletelyVisibleItemPosition() + 1);
                } else if (linearLayoutManager.findLastCompletelyVisibleItemPosition()==(horizontalAdapter.getItemCount()-1)) {
                    linearLayoutManager.smoothScrollToPosition(horizontalRecyclerView, new RecyclerView.State(), 0);
                }

            }

        }, 0 ,2000);
    }

    private void setupGridView() {

        gridItems.add(new Item(R.drawable.chair_cleaning, "Chair Cleaning", "Discounted Price", "Rs. 2674","Thorough chair cleaning to eliminate stains, dust, and odors, ensuring your chairs look and feel like new."));
        gridItems.add(new Item(R.drawable.carpet_cleaning, "Carpet Cleaning", "Discounted Price", "Rs. 865","Professional carpet cleaning to remove stains, dirt, and allergens, leaving your carpets fresh, clean, and hygienic."));
        gridItems.add(new Item(R.drawable.car_detailing_service, "Car Detailing Service", "Discounted Price", "Rs. 346","Complete car detailing service to restore your vehicle’s shine, remove dirt and stains, and maintain a fresh, spotless interior and exterior."));
        gridItems.add(new Item(R.drawable.cement_water_tank_cleaning, "Cement Water Tank Cleaning", "Discounted Price", "Rs. 1200","Comprehensive cleaning of cement water tanks to remove dirt, algae, and impurities, ensuring safe and hygienic water storage."));
        gridItems.add(new Item(R.drawable.chair_cleaning2, "Chair Cleaning 2", "Discounted Price", "Rs. 7745","Expert chair cleaning to remove stains, dust, and grime, ensuring your chairs are clean, fresh, and looking like new."));
        gridItems.add(new Item(R.drawable.curtain_cleaning, "Curtain Cleaning", "Discounted Price", "Rs. 543","Expert curtain cleaning to remove dust, stains, and allergens, restoring freshness and elegance to your home decor."));
        gridItems.add(new Item(R.drawable.dewan_cleaning, "Dewan Cleaning", "Discounted Price", "Rs. 3543","Thorough dewan cleaning to remove dirt, stains, and dust, ensuring your seating area is fresh, comfortable, and inviting."));
        gridItems.add(new Item(R.drawable.kitchen_deep_cleaning, "Kitchen Deep Cleaning", "Discounted Price", "Rs. 346","Comprehensive kitchen deep cleaning to remove grease, grime, and food residue, ensuring a spotless, hygienic cooking space."));
        gridItems.add(new Item(R.drawable.matrice_cleaning, "Matrice Cleaning", "Discounted Price", "Rs. 4643","Thorough matrice cleaning to remove dirt, dust, stains, and allergens, restoring freshness and comfort to your surfaces."));
        gridItems.add(new Item(R.drawable.office_deep_cleaning, "Office Deep Cleaning", "Discounted Price", "Rs. 356","Comprehensive office deep cleaning to ensure a clean, organized, and healthy workspace, enhancing productivity and creating a welcoming environment."));
        gridItems.add(new Item(R.drawable.restaurant_deep_cleaning, "Restaurant Deep Cleaning", "Discounted Price", "Rs. 453","Thorough restaurant deep cleaning to maintain hygiene standards, ensuring a spotless environment for food preparation and a welcoming atmosphere for guests."));
        gridItems.add(new Item(R.drawable.room_deep_cleaning, "Room Deep Cleaning", "Discounted Price", "Rs. 470","Detailed room deep cleaning to eliminate dust, dirt, and stains, leaving your space fresh, sanitized, and comfortable."));
        gridItems.add(new Item(R.drawable.salon_deep_cleaning, "Salon Deep Cleaning", "Discounted Price", "Rs. 1000","Extensive salon deep cleaning to ensure a spotless and hygienic environment, promoting a safe and inviting space for both clients and staff."));
        gridItems.add(new Item(R.drawable.sofa_cleaning, "Safa Cleaning", "Discounted Price", "Rs. 1160","Professional sofa cleaning to remove stains, dust, and allergens, restoring comfort and freshness to your furniture."));
        gridItems.add(new Item(R.drawable.solar_panel_cleaning, "Solar Pane Cleaning", "Discounted Price", "Rs. 800","Efficient solar panel cleaning to remove dust, dirt, and debris, ensuring optimal performance and extending the lifespan of your panels."));

        gridItems.add(new Item(R.drawable.ac_installation, "AC Installation", "Discounted Price", "Rs. 5743","Professional air conditioning installation services to ensure optimal performance and energy efficiency in your home or business."));
        gridItems.add(new Item(R.drawable.appliance_inspection, "Appliance Inspection", "Discounted Price", "Rs. 134","Comprehensive appliance inspection services to assess the condition, safety, and efficiency of your household devices."));
        gridItems.add(new Item(R.drawable.art_hanging, "Art Hanging", "Discounted Price", "Rs. 245","Expert art hanging services to display your artwork securely and beautifully, enhancing the aesthetic of any space."));
        gridItems.add(new Item(R.drawable.carpenter, "Carpenter", "Discounted Price", "Rs. 975","Skilled carpentry services for custom woodwork, furniture, and home improvements, tailored to your style and needs."));
        gridItems.add(new Item(R.drawable.construction_services, "Construction Services", "Discounted Price", "Rs. 1000","Comprehensive construction services, including project management, building, and renovations, delivering high-quality results on time and within budget."));
        gridItems.add(new Item(R.drawable.disinfaction, "Dsinfaction", "Discounted Price", "Rs. 256","Thorough disinfection services to eliminate harmful germs, bacteria, and viruses, ensuring a clean and safe environment for your home or business."));
        gridItems.add(new Item(R.drawable.financial_services, "Financial Services", "Discounted Price", "Rs. 876","Comprehensive financial services offering expert advice and solutions for budgeting, investments, retirement planning, and tax strategies to secure your financial future."));
        gridItems.add(new Item(R.drawable.instant_geyser_service, "Instant Geyser Service", "Discounted Price", "Rs. 1000","Prompt and reliable instant geyser services, including installation, repair, and maintenance, to ensure efficient hot water supply at all times."));
        gridItems.add(new Item(R.drawable.led_hanging, "LED Hanging", "Discounted Price", "Rs. 234","Professional LED hanging services to install and position energy-efficient lighting fixtures, enhancing the ambiance and functionality of your space."));
        gridItems.add(new Item(R.drawable.mirror_hanging, "Mirror Hanging", "Discounted Price", "Rs. 2456","Expert mirror hanging services to securely install mirrors of all sizes, enhancing the style and functionality of any room."));

        gridItems.add(new Item(R.drawable.painting, "Painting", "Discounted Price", "Rs. 1000","High-quality painting services to refresh and transform your space with expert techniques, precise finishes, and a wide range of color options."));
        gridItems.add(new Item(R.drawable.plumber_service, "Plumber Service", "Discounted Price", "Rs. 532","Reliable plumbing services for installation, repairs, and maintenance of pipes, fixtures, and drainage systems to ensure your home's water system runs smoothly."));
        gridItems.add(new Item(R.drawable.repairing_service, "Repairing Service", "Discounted Price", "Rs. 642","Comprehensive repairing services for a wide range of household items and appliances, ensuring quick and efficient fixes to restore functionality and performance."));
        gridItems.add(new Item(R.drawable.shelf_hanging, "Shelf Hanging", "Discounted Price", "Rs. 234","Professional shelf hanging services to securely install shelves, maximizing storage and enhancing the decor of any room."));

        gridItems.add(new Item(R.drawable.transport_service, "Transport Service", "Discounted Price", "Rs. 342","High-quality painting services to refresh and transform your space with expert techniques, precise finishes, and a wide range of color options."));


        GridAdapter gridAdapter = new GridAdapter(getContext(), gridItems);
        gridView.setAdapter(gridAdapter);
    }

    private void filterList(String text) {

        filteredList = new ArrayList<>();
        for(Item item : gridItems){
            if(item.getText1().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(item);
                listView.setVisibility(View.VISIBLE);
                gridView.setVisibility(View.GONE);
                horizontalRecyclerView.setVisibility(View.GONE);

            }
            if ( searchView.getQuery().toString().isEmpty()) {


                listView.setVisibility(View.GONE);
                gridView.setVisibility(View.VISIBLE);
                horizontalRecyclerView.setVisibility(View.VISIBLE);

            }

        }
        if(filteredList.isEmpty()){
            Toast.makeText(getContext(), "No data found", Toast.LENGTH_LONG).show();

            listView.setVisibility(View.GONE);
            gridView.setVisibility(View.VISIBLE);
            horizontalRecyclerView.setVisibility(View.VISIBLE);
        }
        else{

            GridAdapter adapter = new GridAdapter(getContext(), filteredList);
            listView.setAdapter(adapter);

        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Item selectedItem = filteredList.get(position);


                Intent intent = new Intent(getContext(), SingleItemViewActivity.class);
                intent.putExtra("item",  selectedItem);
                startActivity(intent);
            }
        });



    }





}
