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

// Import untuk Geocoding
import android.location.Geocoder;
import android.location.Address;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

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

public class LocationActivity extends AppCompatActivity {

    private FusedLocationProviderClient fusedLocationClient;
    private static final int PERMISSION_ID = 44;
    private static final int REQUEST_CHECK_SETTINGS = 1001;
    private TextView locationTextView;
    private Button takeLocationButton;
    private static final String TAG = "LocationActivity";

    // Flag untuk mencegah transisi berulang setelah lokasi akurat didapat
    private boolean isLocationSent = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Pastikan Anda memiliki R.layout.activity_location.xml
        setContentView(R.layout.activity_location);

        locationTextView = findViewById(R.id.location_text_view);
        // language_button adalah ID dari tombol "Take Location" di XML Anda
        takeLocationButton = findViewById(R.id.language_button);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // Listener untuk tombol "Take Location"
        takeLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Reset flag dan mulai proses dari awal
                isLocationSent = false;
                checkLocationSettingsAndRequest();
            }
        });

        // Coba cek lokasi saat Activity pertama kali dibuat
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
                // Pengaturan lokasi OK (GPS/Network aktif), mulai request update
                requestNewLocationData();
            });

            task.addOnFailureListener(this, e -> {
                if (e instanceof ResolvableApiException) {
                    // Tampilkan dialog Google untuk mengaktifkan GPS
                    try {
                        ResolvableApiException resolvable = (ResolvableApiException) e;
                        resolvable.startResolutionForResult(
                                LocationActivity.this,
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

    // Konfigurasi LocationRequest: Akurasi Tinggi, update setiap 10 detik
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

    // Callback yang akan dipanggil saat lokasi baru tersedia
    private LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(@NonNull LocationResult locationResult) {
            Location lastLocation = locationResult.getLastLocation();

            if (lastLocation != null) {
                double latitude = lastLocation.getLatitude();
                double longitude = lastLocation.getLongitude();
                float accuracy = lastLocation.getAccuracy();

                // Panggil method untuk Geocoding Terbalik
                String addressResult = getAddressFromLocation(latitude, longitude);

                String locString = String.format(Locale.getDefault(),
                        "Lat: %.6f\nLon: %.6f\nAkurasi: %.1fm\n\nAlamat:\n%s",
                        latitude, longitude, accuracy, addressResult);

                locationTextView.setText(locString);
                Log.d(TAG, "Lokasi Baru: " + locString);

                // --- LOGIKA UTAMA: Pindah ke HomeActivity setelah lokasi akurat ---
                // Cek Akurasi: Jika akurasi di bawah 20 meter DAN belum pernah dikirim
                if (accuracy < 20.0 && !isLocationSent) {
                    isLocationSent = true;

                    // Penting: Hentikan update agar tidak menguras baterai
                    fusedLocationClient.removeLocationUpdates(locationCallback);

                    // Panggil fungsi untuk pindah Activity
                    navigateToHome(latitude, longitude, addressResult);
                }
            }
        }
    };

    // --- 3. Geocoding Terbalik (Mendapatkan Alamat dari Koordinat) ---

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

                // Ambil alamat yang paling spesifik
                String featureName = returnedAddress.getFeatureName();
                String thoroughfare = returnedAddress.getThoroughfare(); // Nama jalan
                String subLocality = returnedAddress.getSubLocality(); // Kelurahan/Desa
                String locality = returnedAddress.getLocality(); // Kota/Kabupaten
                String adminArea = returnedAddress.getAdminArea(); // Provinsi/State
                String country = returnedAddress.getCountryName(); // Nama Negara

                // Susun alamat
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

    // --- 4. Fungsi Transisi Activity ---

    private void navigateToHome(double lat, double lon, String address) {
        Toast.makeText(this, "Lokasi akurat didapatkan. Pindah ke Home.", Toast.LENGTH_LONG).show();

        Intent intent = new Intent(LocationActivity.this, HomeActivity.class);

        // Kirim data lokasi ke HomeActivity
        intent.putExtra("EXTRA_LATITUDE", lat);
        intent.putExtra("EXTRA_LONGITUDE", lon);
        intent.putExtra("EXTRA_ADDRESS", address);

        // Hapus LocationActivity dari stack agar pengguna tidak bisa kembali ke sini
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(intent);
        finish(); // Menutup LocationActivity
    }

    // --- 5. Penanganan Izin dan Siklus Hidup ---

    @Override
    protected void onResume() {
        super.onResume();
        // Coba lagi meminta update lokasi saat Activity dilanjutkan, jika belum dikirim
        if (checkPermissions() && !isLocationSent) {
            checkLocationSettingsAndRequest();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Hentikan pembaruan lokasi saat Activity tidak aktif
        if (fusedLocationClient != null) {
            fusedLocationClient.removeLocationUpdates(locationCallback);
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
                // Pengguna mengaktifkan lokasi, mulai permintaan update
                requestNewLocationData();
            } else {
                Toast.makeText(this, "Lokasi (GPS) harus diaktifkan.", Toast.LENGTH_LONG).show();
                locationTextView.setText("Lokasi (GPS) dinonaktifkan.");
            }
        }
    }
}