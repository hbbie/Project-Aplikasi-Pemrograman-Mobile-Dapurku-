package com.example.dapurkunaufal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
// Import Handler untuk jeda waktu
import android.os.Handler;

// Import untuk Geocoding
import android.location.Geocoder;
import android.location.Address;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// Import untuk Google Play Services Location API
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.Priority;
import com.google.android.gms.tasks.Task;

public class LokasiActivity extends AppCompatActivity { // Menggunakan nama class LokasiActivity

    private FusedLocationProviderClient fusedLocationClient;
    private static final int PERMISSION_ID = 44;
    private static final int REQUEST_CHECK_SETTINGS = 1001;
    private TextView locationTextView;
    private Button takeLocationButton;
    private static final String TAG = "LokasiActivity";
    private boolean isLocationSent = false;

    private ExecutorService executorService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        locationTextView = findViewById(R.id.location_text_view);
        takeLocationButton = findViewById(R.id.language_button);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        executorService = Executors.newSingleThreadExecutor();

        takeLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isLocationSent = false;
                checkLocationSettingsAndRequest();
            }
        });

        checkLocationSettingsAndRequest();
    }

    // --- 1. Memeriksa Pengaturan GPS dan Izin ---

    private void checkLocationSettingsAndRequest() {
        if (checkPermissions()) {
            LocationRequest locationRequest = createLocationRequest();
            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                    .addLocationRequest(locationRequest);

            Task<LocationSettingsResponse> task =
                    LocationServices.getSettingsClient(this)
                            .checkLocationSettings(builder.build());

            task.addOnSuccessListener(this, locationSettingsResponse -> {
                requestNewLocationData();
            });

            task.addOnFailureListener(this, e -> {
                if (e instanceof ResolvableApiException) {
                    try {
                        ResolvableApiException resolvable = (ResolvableApiException) e;
                        resolvable.startResolutionForResult(
                                LokasiActivity.this,
                                REQUEST_CHECK_SETTINGS);
                    } catch (IntentSender.SendIntentException sendEx) {
                        Log.e(TAG, "Gagal memulai resolusi pengaturan lokasi", sendEx);
                    }
                }
            });
        } else {
            requestPermissions();
        }
    }

    private LocationRequest createLocationRequest() {
        return new LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 10000)
                .setWaitForAccurateLocation(false)
                .setMinUpdateIntervalMillis(5000)
                .setMaxUpdateDelayMillis(15000)
                .build();
    }

    // --- 2. Meminta Pembaruan Lokasi ---

    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {
        LocationRequest locationRequest = createLocationRequest();

        fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.myLooper()
        );
        locationTextView.setText("Status Lokasi:\nMendapatkan data lokasi...");
        Log.d(TAG, "Memulai permintaan pembaruan lokasi.");
    }

    // --- 3. Callback Lokasi (Logika Asinkron dengan Handler) ---

    private LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(@NonNull LocationResult locationResult) {
            Location lastLocation = locationResult.getLastLocation();

            if (lastLocation != null) {
                double latitude = lastLocation.getLatitude();
                double longitude = lastLocation.getLongitude();
                float accuracy = lastLocation.getAccuracy();

                // Tampilkan koordinat dan akurasi SEGERA
                String initialLocString = String.format(Locale.getDefault(),
                        "Lat: %.6f\nLon: %.6f\nAkurasi: %.1fm\n\nAlamat:\nSedang dicari...",
                        latitude, longitude, accuracy);
                locationTextView.setText(initialLocString);

                // Cek Akurasi: Jika lokasi sudah cukup akurat (di bawah 20m)
                if (accuracy < 20.0 && !isLocationSent) {
                    isLocationSent = true;
                    fusedLocationClient.removeLocationUpdates(locationCallback);

                    // Jalankan Geocoding di Background Thread
                    executorService.execute(new Runnable() {
                        @Override
                        public void run() {
                            String addressResult = getAddressFromLocation(latitude, longitude);

                            // Kembali ke UI Thread untuk memberikan jeda dan transisi
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    // Tampilkan hasil Geocoding final
                                    String finalLocString = String.format(Locale.getDefault(),
                                            "Lat: %.6f\nLon: %.6f\nAkurasi: %.1fm\n\nAlamat:\n%s",
                                            latitude, longitude, accuracy, addressResult);
                                    locationTextView.setText(finalLocString);

                                    // Beri tahu pengguna bahwa transisi akan segera terjadi
                                    Toast.makeText(LokasiActivity.this, "Lokasi terkunci. Bersiap pindah...", Toast.LENGTH_SHORT).show();

                                    // --- Tambahkan JEDA 2 DETIK (2000ms) ---
                                    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            // Pindah Activity setelah jeda 2 detik
                                            navigateToHome(latitude, longitude, addressResult);
                                        }
                                    }, 2000);
                                }
                            });
                        }
                    });
                }
            }
        }
    };

    // --- 4. Geocoding Terbalik ---

    private String getAddressFromLocation(double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses;

        if (!Geocoder.isPresent()) {
            return "Layanan Geocoder tidak tersedia.";
        }

        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);

            if (addresses != null && addresses.size() > 0) {
                Address returnedAddress = addresses.get(0);
                StringBuilder result = new StringBuilder();

                String featureName = returnedAddress.getFeatureName();
                String thoroughfare = returnedAddress.getThoroughfare();
                String subLocality = returnedAddress.getSubLocality();
                String locality = returnedAddress.getLocality();
                String adminArea = returnedAddress.getAdminArea();
                String country = returnedAddress.getCountryName();

                if (thoroughfare != null) result.append(thoroughfare).append(", ");
                else if (featureName != null) result.append(featureName).append(", ");

                if (subLocality != null) result.append(subLocality).append(", ");

                if (locality != null) result.append(locality).append("\n");
                else if (adminArea != null) result.append(adminArea).append("\n");

                if (adminArea != null) result.append(adminArea).append(", ");

                if (country != null) result.append(country);

                return result.toString().trim().replaceAll(", \\n", "\n");

            } else {
                return "Alamat tidak ditemukan.";
            }

        } catch (IOException e) {
            Log.e(TAG, "Error Geocoding: Gagal koneksi atau I/O.", e);
            return "Gagal mendapatkan alamat (Periksa koneksi internet)";
        } catch (IllegalArgumentException e) {
            Log.e(TAG, "Koordinat tidak valid.", e);
            return "Koordinat tidak valid";
        }
    }

    // --- 5. Fungsi Transisi Activity ---

    private void navigateToHome(double lat, double lon, String address) {
        // Hapus toast asli karena toast jeda sudah ditambahkan di handler
        // Toast.makeText(this, "Lokasi akurat didapatkan. Pindah ke Home.", Toast.LENGTH_LONG).show();

        Intent intent = new Intent(LokasiActivity.this, HomeActivity.class);

        intent.putExtra("EXTRA_LATITUDE", lat);
        intent.putExtra("EXTRA_LONGITUDE", lon);
        intent.putExtra("EXTRA_ADDRESS", address);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(intent);
        finish();
    }

    // --- 6. Siklus Hidup & Izin ---

    @Override
    protected void onResume() {
        super.onResume();
        if (checkPermissions() && !isLocationSent) {
            checkLocationSettingsAndRequest();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (fusedLocationClient != null) {
            fusedLocationClient.removeLocationUpdates(locationCallback);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (executorService != null) {
            executorService.shutdown();
        }
    }

    private boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                PERMISSION_ID
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                checkLocationSettingsAndRequest();
            } else {
                Toast.makeText(this, "Izin lokasi ditolak.", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CHECK_SETTINGS) {
            if (resultCode == RESULT_OK) {
                requestNewLocationData();
            } else {
                Toast.makeText(this, "Lokasi (GPS) harus diaktifkan.", Toast.LENGTH_LONG).show();
                locationTextView.setText("Lokasi (GPS) dinonaktifkan.");
            }
        }
    }
}