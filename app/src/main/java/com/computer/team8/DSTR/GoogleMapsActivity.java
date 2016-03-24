package com.computer.team8.DSTR;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Random;


public class GoogleMapsActivity extends FragmentActivity {

    EditText addressEditText;

    private GoogleMap googleMap;

    LatLng addressPos; //= new LatLng(50,-70);

    Marker addressMarker;

    ArrayList<LatLng> markers = new ArrayList<>();

    ArrayList<Float> xyz = new ArrayList<>();

    Polyline line;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_map);

        addressEditText = (EditText) findViewById(R.id.addressEditText);


        try {

            if(googleMap == null) {
                googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
            }

            googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            googleMap.setMyLocationEnabled(true);
            googleMap.setTrafficEnabled(true);
            googleMap.setIndoorEnabled(true);
            googleMap.setBuildingsEnabled(true);
            googleMap.getUiSettings().setZoomControlsEnabled(true);
            googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

                @Override
                public void onMapClick(LatLng point) {

                    Random ran = new Random();
                    int z = ran.nextInt(30);

                    MarkerOptions marker = new MarkerOptions().position(
                            new LatLng(point.latitude, point.longitude)).title(String.valueOf(point.latitude)
                                        + ", " + String.valueOf(point.longitude) + ", " + String.valueOf(z));

                    googleMap.addMarker(marker);

                    markers.add(point);

                    xyz.add((float) point.latitude);
                    xyz.add((float) point.longitude);
                    xyz.add((float) z);

                    PolylineOptions options = new PolylineOptions().width(5).color(Color.BLUE).geodesic(true);
                    for (int i = 0; i < markers.size(); i++) {
                        LatLng newPoint = markers.get(i);
                        options.add(newPoint);
                    }
                    line = googleMap.addPolyline(options);

                }
            });

            googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                @Override
                public void onMapLongClick(LatLng point) {

                    //Remove all the markers from the map
                    googleMap.clear();
                    markers.clear();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showAddressMarker(View view) {

        String newAddress = addressEditText.getText().toString();

        if (newAddress != null) {
            new PlaceMarker().execute(newAddress);
        } //else System.out.print("Error - Address Null");

    }

    class PlaceMarker extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {

            String startAddress = params[0];
            startAddress = startAddress.replace(" ", "%20");

            try {
                getLatLong(startAddress);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Random ran = new Random();
            int z = ran.nextInt(30);

           addressMarker = googleMap.addMarker(new MarkerOptions().position(addressPos).title(String.valueOf(addressPos.latitude)
                                                  + ", " + String.valueOf(addressPos.longitude) + ", " + z).draggable(true));
            markers.add(addressPos);
           // MarkerOptions mMarkerOptions = new MarkerOptions();
           //addressMarker = googleMap.addMarker(mMarkerOptions.position(addressPos).title("turnUp").draggable(true));
            //mMarkerOptions.anchor(0.5f, 0.5f);

            xyz.add((float) addressPos.latitude);
            xyz.add((float) addressPos.longitude);
            xyz.add((float) z);

        }
    }

    protected void getLatLong(String address) throws UnsupportedEncodingException {

        String query = URLEncoder.encode(address, "utf-8");

        String uri = "http://maps.google.com/maps/api/geocode/json?address="+query+"&sensor=false";

        HttpGet httpGet = new HttpGet(uri);

        HttpClient client = new DefaultHttpClient();

        HttpResponse response;

        StringBuilder stringBuilder = new StringBuilder();

        try {

            response = client.execute(httpGet);

            HttpEntity entity = response.getEntity();

            InputStream stream = entity.getContent();

            int byteData;

            while ((byteData = stream.read()) != -1) {
                stringBuilder.append((char) byteData);
            }


        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        double lat = 0.0, lng = 0.0;

        // addressPos = new LatLng(lat, lng);

        JSONObject jsonObject;

        try {
            jsonObject = new JSONObject(stringBuilder.toString());

            lng = ((JSONArray)jsonObject.get("results")).getJSONObject(0)
                    .getJSONObject("geometry").getJSONObject("location")
                    .getDouble("lng");

            lat = ((JSONArray)jsonObject.get("results")).getJSONObject(0)
                    .getJSONObject("geometry").getJSONObject("location")
                    .getDouble("lat");

            addressPos = new LatLng(lat, lng);



        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


}
