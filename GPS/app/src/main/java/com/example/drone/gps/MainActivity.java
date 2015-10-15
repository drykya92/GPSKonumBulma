package com.example.drone.gps;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity
{

    private Button showLocationButton;
    private GPSManager gpsManager;

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showLocationButton = (Button)findViewById(R.id.ShowLocationButton);
        showLocationButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View arg0)
            {
                gpsManager = new GPSManager(MainActivity.this);

                if (gpsManager.canGetLocation) {
                    double latitude = gpsManager.getLatitude();
                    double longitude = gpsManager.getLongitude();
                    Toast.makeText(getApplicationContext(), "Konumunuz \nenlem" + latitude + "\nboylam" + longitude, Toast.LENGTH_LONG).show();

                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage("+905434700658", null, "Drone112 yardım çağrısı : \nEnlem " + latitude + "\nBoylam " + longitude, null, null);
                }
                else
                {
                    gpsManager.showErrorAlert();
                }
            }
        });
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
