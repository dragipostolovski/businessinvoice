package com.thevividworld.businessinvoice.businessinvoice;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Dragi Postolovski on 18-Aug-17.
 */

public class Invoice extends AppCompatActivity {

    private Database database;
    private Button dashboard, addInvoice, addClient, submitData;
    private EditText invoiceNumber;
    private AutoCompleteTextView firstName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice);

        dashboard = (Button)findViewById(R.id.dashboard);
        addInvoice = (Button)findViewById(R.id.add_invoice);
        addClient = (Button)findViewById(R.id.add_client);
        submitData = (Button)findViewById(R.id.add_invoice_data);

        invoiceNumber = (EditText) findViewById(R.id.invoice_number);

        database = new Database(this);

        //make the button clickable
        dashboard.setEnabled(true);
        //when clicked open MainActivity which is the Dashboard
        dashboard.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent dashboard = new Intent(Invoice.this, MainActivity.class);
                    finish();
                    overridePendingTransition(0, 0);
                    startActivity(dashboard);
                }
            }
        );
        //call the function
        AddData();
        //currently in Invoice activity, disable button to prevent reopening
        addInvoice.setEnabled(false);
        addClient.setEnabled(true);
        addClient.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent addClient = new Intent(Invoice.this, Client.class);
                    finish();
                    overridePendingTransition(0, 0);
                    startActivity(addClient);
                }
            }
        );
    }

    //function for adding the data
    public void AddData() {
        //class which will be used to call the function from the Database class
        final Cursor data = database.getClient(); // get the clients
        final ArrayList<String> listData = new ArrayList<>();
        firstName = (AutoCompleteTextView) findViewById(R.id.first_name);
        while(data.moveToNext()){
            //get the value from the database in column 1 and 2
            //then add it to the ArrayList
            // 0 column is the ID
            listData.add(data.getString(1) + " " + data.getString(2));
        }
        //create the list adapter and set the adapter
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listData);
        firstName.setAdapter(adapter);
        submitData.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //get what is currently in the text fields
                    String entryInvoiceNumber = invoiceNumber.getText().toString();
                    String entryFirstName = firstName.getText().toString();
                    if (entryInvoiceNumber.length() != 0
                            && entryFirstName.length() != 0) {
                        if(entryInvoiceNumber.length() < 6){
                            toastMessage("The invoice number must be 6 digits!");
                        }else {
                            //insert the data
                            boolean isInserted = database.addData(entryInvoiceNumber, entryFirstName);
                            if(isInserted = true){
                                toastMessage("Data inserted!");
                                //empty the fields
                                invoiceNumber.setText("");
                                firstName.setText("");
                            } else {
                                toastMessage("Something went wrong!");
                            }
                        }
                    } else {
                        toastMessage("Enter field values!");
                    }
                }
            }
        );
    }

    /**
     * customizable toast
     * @param message
     */
    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }

}
