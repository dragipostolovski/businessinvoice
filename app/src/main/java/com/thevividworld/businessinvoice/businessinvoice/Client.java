package com.thevividworld.businessinvoice.businessinvoice;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Dragi Postolovski on 19-Aug-17.
 */

public class Client extends AppCompatActivity {

    private Database database;
    private Button dashboard, addInvoice, addClient, submitClient;
    private EditText clientFirstName, clientLastName, clientPhoneNumber, clientCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        dashboard = (Button)findViewById(R.id.dashboard);
        addInvoice = (Button)findViewById(R.id.add_invoice);
        addClient = (Button)findViewById(R.id.add_client);
        submitClient = (Button)findViewById(R.id.add_client_data);

        clientFirstName = (EditText) findViewById(R.id.client_first_name);
        clientLastName = (EditText) findViewById(R.id.client_last_name);
        clientPhoneNumber = (EditText) findViewById(R.id.client_phone_number);
        clientCity = (EditText) findViewById(R.id.client_city);

        database = new Database(this);
        dashboard.setEnabled(true);
        dashboard.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent dashboard = new Intent(Client.this, MainActivity.class);
                    finish();
                    overridePendingTransition(0, 0);
                    startActivity(dashboard);
                }
            }
        );
        addInvoice.setEnabled(true);
        addInvoice.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent addInvoice = new Intent(Client.this, Invoice.class);
                    finish();
                    overridePendingTransition(0, 0);
                    startActivity(addInvoice);
                }
            }
        );
        addClient.setEnabled(false);

        AddClient();
    }

    private void AddClient() {
        submitClient.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String entryClientFN = clientFirstName.getText().toString();
                    String entryClientLN = clientLastName.getText().toString();
                    String entryClientPN = clientPhoneNumber.getText().toString();
                    String entryClientCity = clientCity.getText().toString();
                    if (entryClientFN.length() != 0 && entryClientLN.length() != 0
                        && entryClientPN.length() != 0 && entryClientCity.length() != 0) {
                        boolean isInserted = database.addClient(entryClientFN, entryClientLN, entryClientPN, entryClientCity);
                        if(isInserted = true){
                            toastMessage("Data inserted!");
                            clientFirstName.setText(""); clientLastName.setText("");
                            clientPhoneNumber.setText(""); clientCity.setText("");
                        } else {
                            toastMessage("Something went wrong!");
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
