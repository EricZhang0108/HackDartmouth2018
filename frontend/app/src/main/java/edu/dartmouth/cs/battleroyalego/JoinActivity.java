package edu.dartmouth.cs.battleroyalego;

import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.firebase.geofire.LocationCallback;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JoinActivity extends AppCompatActivity {

    Location user_location;
    double user_location_lat = user_location.getLatitude();
    double user_location_long = user_location.getLongitude();
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("https://hackdartmo.firebaseio.com");
    GeoFire geoFire = new GeoFire(ref);
    List<String> nearestGames = Arrays.asList(new String[5]);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        user_location = getIntent().getParcelableExtra("USER_LOCATION");
        GeoQuery geoQuery = geoFire.queryAtLocation(new GeoLocation(user_location_lat, user_location_long), 1);
        Button game1 = findViewById(R.id.game1);
        Button game2 = findViewById(R.id.game2);
        Button game3 = findViewById(R.id.game3);
        Button game4 = findViewById(R.id.game4);
        Button game5 = findViewById(R.id.game5);

        game1.setText(nearestGames.get(0));
        game2.setText(nearestGames.get(1));
        game3.setText(nearestGames.get(2));
        game4.setText(nearestGames.get(3));
        game5.setText(nearestGames.get(4));

        geoQuery.addGeoQueryEventListener(new GeoQueryEventListener() {
            int i = 0;
            @Override
            public void onKeyEntered(String key, GeoLocation location) {
                if (i < 5) {
                    nearestGames.set(i, key);
                    i++;
                }
            }

            @Override
            public void onKeyExited(String key) {

            }

            @Override
            public void onKeyMoved(String key, GeoLocation location) {

            }

            @Override
            public void onGeoQueryReady() {

            }

            @Override
            public void onGeoQueryError(DatabaseError error) {

            }
        });



    }
}
