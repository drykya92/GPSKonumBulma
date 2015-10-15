package com.example.drone.gps;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;

public class GPSManager extends Service implements LocationListener
{
    public GPSManager(Context context)
    {
        this.context = context;
        getLocation();
    }
    private final Context context;

    // Cihazda gps acik mi?
    boolean isGPSEnabled = false;

    // Cihazda veri baglantisi aktif mi?
    boolean isNetworkEnabled = false;

    public boolean canGetLocation = false;

    // Konum
    Location location;
    // Enlem
    double latitude;
    // Boylam
    double longitude;

    public double getLatitude()
    {
        if (location != null)
            return latitude;
        else {
            latitude = location.getLatitude();
            return latitude;
        }
    }

    public double getLongitude()
    {
        if (location != null)
            return longitude;
        else {
            longitude = location.getLatitude();
            return longitude;
        }
    }

    // LocationManager nesnesi
    protected LocationManager locationManager;

    //
    // Konum bilgisini dondurur
    //
    public Location getLocation()
    {
        locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);

        // GPS acik mi?
        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        // Internet acik mi?
        isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (!isGPSEnabled && !isNetworkEnabled)
        {
            return null;
        }
        else
        {
            if (isNetworkEnabled)
            {
                locationManager.requestLocationUpdates(locationManager.NETWORK_PROVIDER, 10, 600000, this);
                location = locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER);

                if (location != null) {
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();

                    canGetLocation = true;
                }
            }
            if (isGPSEnabled)
            {
                if (location == null)
                {
                    locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER, 10, 600000, this);
                    location = locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER);

                    latitude = location.getLatitude();
                    longitude = location.getLongitude();

                    canGetLocation = true;
                }
            }
            return location;
        }
    }

    public void showErrorAlert()
    {
        // Mesaj dialogu
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        // Mesaj basligi
        alertDialog.setTitle("GPS Kapalı");

        // Mesaj
        alertDialog.setMessage("Konum bilgisi alınamıyor. Ayarlara giderek gps'i aktif hale getiriniz.");

        // Mesaj ikonu
        //alertDialog.setIcon(R.drawable.delete);

        // Ayarlar butonuna tiklandiginda
        alertDialog.setPositiveButton("Ayarlar", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog,int which)
            {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                context.startActivity(intent);
            }
        });

        // Iptal butonuna tiklandiginda
        alertDialog.setNegativeButton("İptal", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.cancel();
            }
        });

        // Mesaj kutusunu goster
        alertDialog.show();
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}