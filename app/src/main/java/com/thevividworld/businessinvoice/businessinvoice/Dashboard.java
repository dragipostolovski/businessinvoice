package com.thevividworld.businessinvoice.businessinvoice;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Dragi Postolovski on 23-Aug-17.
 */

public class Dashboard extends AppCompatActivity{
    private static final String TAG = "Dashboard";

    Database database;
    private ListView listingData, listingClients;
    Button dashboard, addInvoice, addClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        dashboard = (Button) findViewById(R.id.dashboard);
        addInvoice = (Button) findViewById(R.id.add_invoice);
        addClient = (Button) findViewById(R.id.add_client);

        dashboard.setEnabled(false);
        addInvoice.setEnabled(true);
        addClient.setEnabled(true);

        listingData = (ListView) findViewById(R.id.listData);
        listingClients = (ListView) findViewById(R.id.listClient);
        database = new Database(this);

        populateListView();
        populateListViewClients();

    }

    private void populateListViewClients() {
        //get the data and append to a list
        final Cursor clients = database.getClient();
        final ArrayList<String> listClients = new ArrayList<>();
        while (clients.moveToNext()) {
            //get the value from the database in column 1
            //then add it to the ArrayList
            listClients.add(clients.getString(1) + " " + clients.getString(2) + " (" +
                    clients.getString(3) + ", " + clients.getString(4) + ")");
        }
        //create the list adapter and set the adapter
        final ListAdapter adapterClients = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listClients);
        listingClients.setAdapter(adapterClients);
    }

    private void populateListView() {
        //get the data and append to a list
        final Cursor data = database.getData();
        final ArrayList<String> listData = new ArrayList<>();
        while (data.moveToNext()) {
            //get the value from the database in column 1
            //then add it to the ArrayList
            listData.add(data.getString(1) + " (" + data.getString(2) + ")");
        }
        //create the list adapter and set the adapter
        final ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        listingData.setAdapter(adapter);
        //set an onItemClickListener to the ListView
        listingData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String invoiceNumber = adapterView.getItemAtPosition(i).toString();
                String upToNCharacters = invoiceNumber.substring(0, Math.min(invoiceNumber.length(), 6));
                Log.d(TAG, "onItemClick: You Clicked on " + upToNCharacters);
                Cursor data = database.getItemID(upToNCharacters);
                int itemID = -1;
                while (data.moveToNext()) {
                    itemID = data.getInt(0); ////get the id associated with that invoice number
                }
                database.deleteName(itemID);
                toastMessage("Invoice removed!");
                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
            }
        });
    }

    public void openInvoice(View view) {
        Intent addInvoice = new Intent(Dashboard.this, Invoice.class);
        finish();
        overridePendingTransition(0, 0);
        startActivity(addInvoice);
    }

    public void openClient(View view) {
        Intent addClient = new Intent(Dashboard.this, Client.class);
        finish();
        overridePendingTransition(0, 0);
        startActivity(addClient);
    }

    /**
     * customizable toast
     *
     * @param message
     */
    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
