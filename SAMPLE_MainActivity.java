package com.example.shop;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText searchInput;
    private Button searchButton;
    private ListView productList;
    private ArrayAdapter<String> adapter;
    private List<String> products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        searchInput = findViewById(R.id.search_input);
        searchButton = findViewById(R.id.search_button);
        productList = findViewById(R.id.product_list);

        // Initialize product list with sample data
        products = new ArrayList<>();
        products.add("Red Shirt - $20");
        products.add("Blue Shirt - $25");
        products.add("Green Shirt - $22");
        products.add("Black Shirt - $28");

        // Setup adapter
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, products);
        productList.setAdapter(adapter);

        // Search button click listener
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = searchInput.getText().toString().toLowerCase();
                if (query.isEmpty()) {
                    adapter.clear();
                    adapter.addAll(products);
                } else {
                    List<String> filtered = new ArrayList<>();
                    for (String product : products) {
                        if (product.toLowerCase().contains(query)) {
                            filtered.add(product);
                        }
                    }
                    adapter.clear();
                    adapter.addAll(filtered);
                }
                adapter.notifyDataSetChanged();
            }
        });
    }
}

