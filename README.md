# Project-Aplikasi-Pemrograman-Mobile-Dapurku-
# TugasUjiansemesterAkhirPertemuan16PemrogramanMobile

<p align="center">
  <img src="https://capsule-render.vercel.app/api?type=waving&color=0:1565C0,100:1E88E5&height=230&section=header&text=Dhani%20Naufal%20Habibie&fontSize=40&fontColor=ffffff&fontAlignY=32&desc=Teknik%20Informatika%20%7C%20TI%2024A4&descAlignY=55" />
</p>

<p align="center">
  <b>Aplikasi Android berbasis Java & XML</b><br>
  Dikembangkan menggunakan Android Studio
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Platform-Android-green?logo=android&logoColor=white"/>
  <img src="https://img.shields.io/badge/IDE-Android%20Studio-3DDC84?logo=androidstudio&logoColor=white"/>
  <img src="https://img.shields.io/badge/Language-Java-red?logo=java&logoColor=white"/>
  <img src="https://img.shields.io/badge/UI-XML-blue?logo=xml&logoColor=white"/>
  <img src="https://img.shields.io/badge/Build-Gradle-02303A?logo=gradle&logoColor=white"/>
</p>

<hr>

## IDENTITAS MAHASISWA

| Keterangan | Detail |
|-----------|--------|
| **Nama Mahasiswa** | Dhani Naufal Habibie |
| **NIM** | 312410300 |
| **Kelas** | TI 24A4 |
| **Program Studi** | Teknik Informatika |
| **Fakultas** | Fakultas Teknik |
| **Perguruan Tinggi** | Universitas Pelita Bangsa - UPB |

---

## INFORMASI AKADEMIK

| Keterangan | Detail |
|-----------|--------|
| **Mata Kuliah** | Pemrograman Mobile |
| **Dosen Pengampu** | Bpk Donny Maulana, S.Kom., M.Kom |
| **Jenis Tugas** | UAS |
| **Judul Proyek** | Judul Aplikasi Android ( SholatYuk ) |
| **Tahun Akademik** | 2024 / 2025 |

## DESKRIPSI SINGKAT

Laporan ini disusun sebagai bagian dari **tugas mata kuliah Pemrograman Mobile**.  
Proyek berupa pengembangan **aplikasi Android** menggunakan **Android Studio**,  
bahasa pemrograman **Java**, serta **XML Layout** sebagai perancangan antarmuka pengguna.

---

## TEKNOLOGI YANG DIGUNAKAN

- Android Studio  
- Java Programming Language  
- XML Layout  
- Android SDK

## 🎯 Deskripsi Aplikasi

PADUKAMAMSKIEEE merupakan aplikasi Android yang dirancang untuk mengetahui jadwal sholaat secara otomatis dengan mengambil lokasi.
Aplikasi ini menyediakan fitur mulai dari pilih bahasa, dan Mengambil lokasi secara otomatis.
Aplikasi ini dibuat untuk memenuhi kebutuhan tugas perkuliahan dengan menerapkan konsep Activity-based navigation, UI XML, mengetahui jadwal sholat sederhana.

## Struktur Activity (Java)

*1. halamanutamaActivity.java*
~~~ java
package com.raply.tai; // Nama package sudah disesuaikan dengan proyek Anda

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

public class halamanutamaActivity extends AppCompatActivity {

    // Durasi splash screen dalam milidetik (misal: 3000ms = 3 detik)
    private static final int SPLASH_SCREEN_TIMEOUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_halamanutama);

        // Gunakan Handler untuk menunda perpindahan ke activity berikutnya
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                // Buat Intent untuk memulai activity berikutnya
                // MENGARAHKAN KE HALAMAN PEMILIHAN BAHASA SETELAH SPLASH SCREEN
                Intent intent = new Intent(halamanutamaActivity.this, bahasaActivity.class);
                startActivity(intent);

                // Tutup activity splash screen ini agar tidak bisa kembali dengan tombol "back"
                finish();
            }
        }, SPLASH_SCREEN_TIMEOUT);
    }
}
~~~

- Activity pertama yang dijalankan saat aplikasi dibuka
- Menampilkan logo atau tampilan awal aplikasi
- Berpindah otomatis setelah beberapa detik

➡️ **Navigasi ke:** `bahasaActivity`

*2. bahasaActivity.java*
~~~ java
package com.raply.tai;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

// Ganti nama kelas dari bahasaActivity menjadi PilihBahasaActivity agar sesuai dengan nama file
// Namun, berdasarkan konteks Anda, sepertinya Anda memiliki dua file:
// 1. Sebuah file yang belum Anda tunjukkan (mungkin bernama bahasaActivity.java)
// 2. pilihbahasaActivity.java
// Saya akan asumsikan file pertama bernama bahasaActivity.java
public class bahasaActivity extends AppCompatActivity {

    private Button selectLanguageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bahasa);

        // Menghubungkan variabel Java dengan elemen Button di XML
        // Pastikan ID 'btn_pilih_bahasa' ada di file layout 'activity_bahasa.xml' Anda
        selectLanguageButton = findViewById(R.id.btn_select_language); // Ganti R.id.btn_select_language dengan ID tombol Anda

        // Menetapkan listener untuk menangani klik tombol
        selectLanguageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Membuat Intent untuk pindah dari bahasaActivity ke pilihbahasaActivity
                Intent intent = new Intent(bahasaActivity.this, pilihbahasaActivity.class);
                startActivity(intent);
            }
        });
    }
}
~~~

- Halaman Selamat Datang
- Pengguna melakukan proses untuk memilih bahasa
- Klik pilih bahasa sebelum mmemilih bahasa

➡️ **Navigasi ke:** `pilihbahasaActivity`

*3. pilihBahasActivity.java*
~~~ java
package com.raply.tai;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.card.MaterialCardView;

public class pilihbahasaActivity extends AppCompatActivity {

    private MaterialCardView cardIndonesia;
    private MaterialCardView cardUsa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pilihbahasa);

        // Menghubungkan variabel dengan ID elemen di layout XML
        cardIndonesia = findViewById(R.id.card_indonesia);
        cardUsa = findViewById(R.id.card_usa);

        // Membuat listener untuk kedua kartu
        View.OnClickListener languageClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Di sini Anda bisa menambahkan logika untuk menyimpan bahasa yang dipilih jika perlu
                // Misalnya menggunakan SharedPreferences

                // Pindah ke LandakActivity
                Intent intent = new Intent(pilihbahasaActivity.this, LandakActivity.class);
                startActivity(intent);

                // (Opsional) Menutup activity ini agar pengguna tidak bisa kembali ke pemilihan bahasa
                // dengan menekan tombol "back"
                finish();
            }
        };

        // Menetapkan listener yang sama ke kedua kartu
        cardIndonesia.setOnClickListener(languageClickListener);
        cardUsa.setOnClickListener(languageClickListener);

        // Kode boilerplate untuk EdgeToEdge
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
~~~

- Halaman pengguna untuk memilih bahasa yang digunakan
- Pengguna melakukan proses memilih bahasa

➡️ **Navigasi ke:** `LandakActivity`

*4. LandakActivity.java*
~~~ java
package com.raply.tai;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LandakActivity extends AppCompatActivity {

    private TextView tvCountdown, tvServerTime, tvCity;
    private TextView tvShubuhToday, tvZhuhurToday, tvAsharToday, tvMaghribToday, tvIsyaToday;
    private TextView tvDateHijri, tvDateToday;
    private ProgressBar pbSyncTime;
    private Button btnMap;

    private FusedLocationProviderClient fusedLocationProviderClient;
    private final Handler countdownHandler = new Handler(Looper.getMainLooper());
    private Runnable countdownRunnable;
    private RequestQueue requestQueue;

    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                pbSyncTime.setVisibility(View.GONE);
                if (isGranted) {
                    getCurrentLocationAndFetchPrayerTimes();
                } else {
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                        showSettingsDialog();
                    } else {
                        Toast.makeText(this, "Izin lokasi tetap ditolak.", Toast.LENGTH_LONG).show();
                        tvCountdown.setText("Butuh izin lokasi");
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_landak);

        requestQueue = Volley.newRequestQueue(this);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        initializeViews();

        btnMap.setOnClickListener(v -> {
            Toast.makeText(this, "Mencari ulang lokasi...", Toast.LENGTH_SHORT).show();
            checkLocationPermission();
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        new Handler(Looper.getMainLooper()).postDelayed(this::checkLocationPermission, 500);
        startServerTimeUpdater();
    }

    private void initializeViews() {
        tvCountdown = findViewById(R.id.tv_countdown);
        tvServerTime = findViewById(R.id.tv_server_time);
        tvCity = findViewById(R.id.tv_city);
        tvShubuhToday = findViewById(R.id.tv_shubuh_today);
        tvZhuhurToday = findViewById(R.id.tv_zhuhur_today);
        tvAsharToday = findViewById(R.id.tv_ashar_today);
        tvMaghribToday = findViewById(R.id.tv_maghrib_today);
        tvIsyaToday = findViewById(R.id.tv_isya_today);
        pbSyncTime = findViewById(R.id.pb_sync_time);
        btnMap = findViewById(R.id.btn_map);
        tvDateHijri = findViewById(R.id.tv_date_hijri);
        tvDateToday = findViewById(R.id.tv_date_today);
    }

    // --- FUNGSI INI TELAH DIPERBAIKI DENGAN METODE KEMENAG RI ---
    private void fetchPrayerTimesFromApi(double latitude, double longitude) {
        // Metode 10 = Kementerian Agama Republik Indonesia
        final int METHOD_ID = 10;

        // Penyesuaian (tune) dalam menit agar lebih sesuai dengan jadwal lokal.
        // Umumnya Subuh dan Isya ditambah 2 menit. Anda bisa mengubah ini jika perlu.
        final int FAJR_TUNE = 2;
        final int DHUHR_TUNE = 2;
        final int ASR_TUNE = 2;
        final int MAGHRIB_TUNE = 2;
        final int ISHA_TUNE = 2;

        // Format parameter 'tune' untuk URL. Urutannya: Imsak, Subuh, Terbit, Zuhur, Asar, Magrib, Terbenam, Isya, Tengah Malam
        String tuneParams = String.format(Locale.US, "0,%d,0,%d,%d,%d,0,%d,0",
                FAJR_TUNE, DHUHR_TUNE, ASR_TUNE, MAGHRIB_TUNE, ISHA_TUNE);

        // Buat URL untuk Aladhan API dengan metode dan penyesuaian Kemenag RI
        String url = String.format(Locale.US, "https://api.aladhan.com/v1/timings?latitude=%f&longitude=%f&method=%d&tune=%s",
                latitude, longitude, METHOD_ID, tuneParams);

        Log.d("API_URL", "Requesting URL: " + url); // Untuk debugging

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    Log.d("API_RESPONSE", response.toString());
                    try {
                        JSONObject data = response.getJSONObject("data");
                        JSONObject timings = data.getJSONObject("timings");
                        JSONObject dateInfo = data.getJSONObject("date");
                        JSONObject hijriInfo = dateInfo.getJSONObject("hijri");

                        String shubuh = timings.getString("Fajr");
                        String zhuhur = timings.getString("Dhuhr");
                        String ashar = timings.getString("Asr");
                        String maghrib = timings.getString("Maghrib");
                        String isya = timings.getString("Isha");

                        String gregorianDate = dateInfo.getString("readable");
                        String hijriDate = String.format("%s %s %s H",
                                hijriInfo.getString("day"),
                                hijriInfo.getJSONObject("month").getString("en"),
                                hijriInfo.getString("year"));

                        updateUiWithApiData(shubuh, zhuhur, ashar, maghrib, isya, gregorianDate, hijriDate);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(LandakActivity.this, "Gagal parsing data JSON", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    error.printStackTrace();
                    Toast.makeText(LandakActivity.this, "Gagal mengambil data waktu shalat", Toast.LENGTH_SHORT).show();
                    Log.e("API_ERROR", "Error: " + error.toString());
                });

        requestQueue.add(jsonObjectRequest);
    }

    private void updateUiWithApiData(String shubuh, String zhuhur, String ashar, String maghrib, String isya, String gregorianDate, String hijriDate) {
        tvShubuhToday.setText(shubuh);
        tvZhuhurToday.setText(zhuhur);
        tvAsharToday.setText(ashar);
        tvMaghribToday.setText(maghrib);
        tvIsyaToday.setText(isya);
        tvDateHijri.setText(hijriDate);
        tvDateToday.setText(gregorianDate);
        startCountdownUpdater();
    }

    private void checkLocationPermission() {
        pbSyncTime.setVisibility(View.VISIBLE);
        tvCity.setText("Mencari lokasi...");
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getCurrentLocationAndFetchPrayerTimes();
        } else if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            showRationaleDialog();
        } else {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
        }
    }

    private void showRationaleDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Izin Lokasi Dibutuhkan")
                .setMessage("Aplikasi ini memerlukan izin lokasi untuk menampilkan waktu shalat yang akurat. Izinkan aplikasi untuk mengakses lokasi?")
                .setPositiveButton("Izinkan", (dialog, which) -> requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION))
                .setNegativeButton("Tolak", (dialog, which) -> {
                    dialog.dismiss();
                    pbSyncTime.setVisibility(View.GONE);
                    tvCountdown.setText("Butuh izin lokasi");
                })
                .create()
                .show();
    }

    private void showSettingsDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Izin Ditolak Permanen")
                .setMessage("Aplikasi ini memerlukan izin lokasi untuk berfungsi. Silakan aktifkan izin lokasi di Pengaturan Aplikasi.")
                .setPositiveButton("Buka Pengaturan", (dialog, which) -> {
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                    intent.setData(uri);
                    startActivity(intent);
                })
                .setNegativeButton("Batal", (dialog, which) -> {
                    dialog.dismiss();
                    pbSyncTime.setVisibility(View.GONE);
                    tvCountdown.setText("Butuh izin lokasi");
                })
                .create()
                .show();
    }

    private void getCurrentLocationAndFetchPrayerTimes() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            pbSyncTime.setVisibility(View.GONE);
            return;
        }
        fusedLocationProviderClient.getLastLocation()
                .addOnSuccessListener(this, location -> {
                    pbSyncTime.setVisibility(View.GONE);
                    if (location != null) {
                        getCityNameFromCoordinates(location.getLatitude(), location.getLongitude());
                        fetchPrayerTimesFromApi(location.getLatitude(), location.getLongitude());
                    } else {
                        Toast.makeText(this, "Gagal mendapatkan lokasi. Pastikan GPS aktif.", Toast.LENGTH_LONG).show();
                        tvCountdown.setText("Gagal dapatkan lokasi");
                    }
                })
                .addOnFailureListener(this, e -> {
                    pbSyncTime.setVisibility(View.GONE);
                    Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    tvCountdown.setText("Gagal dapatkan lokasi");
                });
    }

    private void getCityNameFromCoordinates(double latitude, double longitude) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(() -> {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            String cityName = "Lokasi tidak diketahui";
            try {
                List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
                if (addresses != null && !addresses.isEmpty()) {
                    if (addresses.get(0).getSubAdminArea() != null) {
                        cityName = addresses.get(0).getSubAdminArea();
                    } else if (addresses.get(0).getLocality() != null) {
                        cityName = addresses.get(0).getLocality();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            String finalCityName = cityName;
            handler.post(() -> tvCity.setText(finalCityName));
        });
    }

    private void startCountdownUpdater() {
        if (countdownRunnable != null) {
            countdownHandler.removeCallbacks(countdownRunnable);
        }
        Map<String, String> prayerTimes = new LinkedHashMap<>();
        prayerTimes.put("Shubuh", tvShubuhToday.getText().toString());
        prayerTimes.put("Zhuhur", tvZhuhurToday.getText().toString());
        prayerTimes.put("Ashar", tvAsharToday.getText().toString());
        prayerTimes.put("Maghrib", tvMaghribToday.getText().toString());
        prayerTimes.put("Isya", tvIsyaToday.getText().toString());
        countdownRunnable = new Runnable() {
            @Override
            public void run() {
                calculateAndDisplayCountdown(prayerTimes);
                countdownHandler.postDelayed(this, 1000);
            }
        };
        countdownHandler.post(countdownRunnable);
    }

    private void calculateAndDisplayCountdown(Map<String, String> prayerTimes) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        Date now = new Date();
        String nextPrayerName = null;
        Date nextPrayerTime = null;
        try {
            for (Map.Entry<String, String> entry : prayerTimes.entrySet()) {
                if (entry.getValue() == null || entry.getValue().equals("--:--")) continue;
                Date prayerTime = sdf.parse(entry.getValue());
                Calendar prayerCal = Calendar.getInstance();
                prayerCal.setTime(prayerTime);
                Calendar todayCal = Calendar.getInstance();
                prayerCal.set(todayCal.get(Calendar.YEAR), todayCal.get(Calendar.MONTH), todayCal.get(Calendar.DAY_OF_MONTH));
                if (prayerCal.getTime().after(now)) {
                    nextPrayerName = entry.getKey();
                    nextPrayerTime = prayerCal.getTime();
                    break;
                }
            }
            if (nextPrayerName == null) {
                nextPrayerName = "Shubuh";
                String shubuhTimeStr = prayerTimes.get("Shubuh");
                if (shubuhTimeStr == null || shubuhTimeStr.equals("--:--")) return;
                Date shubuhTime = sdf.parse(shubuhTimeStr);
                Calendar shubuhCal = Calendar.getInstance();
                shubuhCal.setTime(shubuhTime);
                shubuhCal.add(Calendar.DAY_OF_MONTH, 1);
                nextPrayerTime = shubuhCal.getTime();
            }
            long diff = nextPrayerTime.getTime() - now.getTime();
            long hours = (diff / (1000 * 60 * 60)) % 24;
            long minutes = (diff / (1000 * 60)) % 60;
            long seconds = (diff / 1000) % 60;
            String countdownText = String.format(Locale.getDefault(), "%02d:%02d:%02d menuju %s", hours, minutes, seconds, nextPrayerName);
            tvCountdown.setText(countdownText);
        } catch (ParseException e) {
            e.printStackTrace();
            tvCountdown.setText("Error format waktu");
        }
    }

    private void startServerTimeUpdater() {
        Handler handler = new Handler(Looper.getMainLooper());
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss a", Locale.getDefault());
                String currentTime = sdf.format(Calendar.getInstance().getTime());
                tvServerTime.setText("Waktu lokal: " + currentTime);
                handler.postDelayed(this, 1000);
            }
        };
        handler.post(runnable);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (countdownRunnable != null) {
            countdownHandler.removeCallbacks(countdownRunnable);
        }
    }
}
~~~

- Menampilkan tampilan utama dari aplikasi yang di dalamnya ada jadwal sholat, tanggal realtime, countdown waku sholat selanjutnya
- Menyediakan tombol untuk mengambil lokasi untuk mendeteksi lokasi anda berada dan menyesuaikan jadwal sholat di lokasi anda

## 🔹 Layout XML

*1. activity_splash_screen.xml*
~~~ xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="#E3F2FD"
    tools:context=".halamanutamaActivity">

    <!-- Kontainer utama untuk menengahkan semua elemen di layar -->
    <LinearLayout
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_centerInParent="true"
        android:gravity="center_horizontal"
        android:orientation="vertical">

        <!-- Logo Aplikasi dalam CardView Lingkaran -->
        <com.google.android.material.card.MaterialCardView
            android:layout_width="150dp"
            android:layout_height="150dp"
            app:cardCornerRadius="75dp"
            app:cardElevation="4dp"
            app:strokeWidth="2dp"
            app:strokeColor="#FFFFFF">

            <ImageView
                android:id="@+id/iv_logo"
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                android:contentDescription="@string/app_name"
                android:scaleType="centerCrop"
                android:src="@drawable/ic_mosque_logo" />

        </com.google.android.material.card.MaterialCardView>

        <!-- Nama Aplikasi -->
        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginTop="24dp"
            android:text="SholatYuk"
            android:textAppearance="@style/TextAppearance.Material3.HeadlineMedium"
            android:textColor="#1976D2"
            android:textStyle="bold" />

    </LinearLayout>

    <!-- Letakkan ProgressBar di bagian bawah layar untuk pengalaman pengguna yang lebih baik -->
    <ProgressBar
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_alignParentBottom="true"
        android:layout_centerHorizontal="true"
        android:layout_marginBottom="64dp"
        android:indeterminate="true"
        android:indeterminateTint="#1976D2" />

</RelativeLayout>
~~~

*2. activity_login.xml*
~~~ xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:gravity="center"
    android:padding="32dp"
    android:background="#E3F2FD"
    tools:context=".bahasaActivity">

    <!-- Logo Lingkaran dengan MaterialCardView untuk efek yang lebih baik -->
    <com.google.android.material.card.MaterialCardView
        android:layout_width="150dp"
        android:layout_height="150dp"
        app:cardCornerRadius="75dp"
        app:cardElevation="4dp"
        app:strokeWidth="2dp"
        app:strokeColor="#FFFFFF">

        <ImageView
            android:id="@+id/iv_logo"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:src="@drawable/ic_mosque_logo"
            android:scaleType="centerCrop"
            android:contentDescription="Logo Aplikasi" />

    </com.google.android.material.card.MaterialCardView>

    <!-- Teks Sambutan atau Judul -->
    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginTop="48dp"
        android:text="Selamat Datang"
        android:textAppearance="@style/TextAppearance.Material3.HeadlineMedium"
        android:textColor="#1976D2"
        android:textStyle="bold" />

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginTop="8dp"
        android:text="Silakan pilih bahasa untuk melanjutkan"
        android:textAppearance="@style/TextAppearance.Material3.BodyLarge"
        android:textColor="@color/material_on_surface_emphasis_medium"
        android:gravity="center"/>

    <!-- Tombol Aksi yang Modern -->
    <com.google.android.material.button.MaterialButton
        android:id="@+id/btn_select_language"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginTop="32dp"
        android:text="Pilih Bahasa"
        app:backgroundTint="#1976D2"
        android:textColor="@color/white"
        app:cornerRadius="16dp"
        android:textSize="18sp"
        android:paddingStart="40dp"
        android:paddingEnd="40dp"
        android:paddingTop="12dp"
        android:paddingBottom="12dp" />

</LinearLayout>
~~~

*3. activity_home.xml*
~~~
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="#E3F2FD"
    tools:context=".pilihbahasaActivity">

    <!-- Wrapper untuk menengahkan semua konten -->
    <LinearLayout
        android:id="@+id/main"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_centerInParent="true"
        android:gravity="center_horizontal"
        android:orientation="vertical"
        android:padding="24dp">

        <!-- Logo dalam CardView Lingkaran -->
        <com.google.android.material.card.MaterialCardView
            android:layout_width="120dp"
            android:layout_height="120dp"
            app:cardCornerRadius="60dp"
            app:cardElevation="4dp"
            app:strokeWidth="2dp"
            app:strokeColor="#FFFFFF">

            <ImageView
                android:id="@+id/iv_logo"
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                android:contentDescription="Logo Aplikasi"
                android:scaleType="centerCrop"
                android:src="@drawable/ic_mosque_logo" />

        </com.google.android.material.card.MaterialCardView>

        <!-- Teks Instruksi -->
        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginTop="32dp"
            android:text="Pilih Bahasa Anda"
            android:textAppearance="@style/TextAppearance.Material3.HeadlineSmall"
            android:textColor="#1976D2"
            android:textStyle="bold" />

        <!-- Layout untuk dua pilihan bahasa -->
        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginTop="24dp"
            android:orientation="horizontal"
            android:gravity="center">

            <!-- Pilihan Bahasa Indonesia -->
            <com.google.android.material.card.MaterialCardView
                android:id="@+id/card_indonesia"
                android:layout_width="0dp"
                android:layout_height="wrap_content"
                android:layout_weight="1"
                android:layout_marginEnd="8dp"
                android:clickable="true"
                android:focusable="true"
                app:cardCornerRadius="16dp"
                app:cardElevation="2dp"
                app:strokeWidth="1dp"
                app:strokeColor="@color/material_on_surface_stroke">

                <LinearLayout
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:orientation="vertical"
                    android:padding="16dp"
                    android:gravity="center">

                    <ImageView
                        android:id="@+id/iv_indonesia_flag"
                        android:layout_width="80dp"
                        android:layout_height="50dp"
                        android:src="@drawable/indonesia"
                        android:scaleType="centerCrop"
                        android:contentDescription="Pilih Bahasa Indonesia" />

                    <TextView
                        android:layout_width="wrap_content"
                        android:layout_height="wrap_content"
                        android:layout_marginTop="8dp"
                        android:text="Indonesia"
                        android:textAppearance="@style/TextAppearance.Material3.BodyLarge"
                        android:textStyle="bold"/>
                </LinearLayout>
            </com.google.android.material.card.MaterialCardView>

            <!-- Pilihan Bahasa Inggris -->
            <com.google.android.material.card.MaterialCardView
                android:id="@+id/card_usa"
                android:layout_width="0dp"
                android:layout_height="wrap_content"
                android:layout_weight="1"
                android:layout_marginStart="8dp"
                android:clickable="true"
                android:focusable="true"
                app:cardCornerRadius="16dp"
                app:cardElevation="2dp"
                app:strokeWidth="1dp"
                app:strokeColor="@color/material_on_surface_stroke">

                <LinearLayout
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:orientation="vertical"
                    android:padding="16dp"
                    android:gravity="center">

                    <ImageView
                        android:id="@+id/iv_usa_flag"
                        android:layout_width="80dp"
                        android:layout_height="50dp"
                        android:src="@drawable/usa"
                        android:scaleType="centerCrop"
                        android:contentDescription="Select English Language" />

                    <TextView
                        android:layout_width="wrap_content"
                        android:layout_height="wrap_content"
                        android:layout_marginTop="8dp"
                        android:text="English"
                        android:textAppearance="@style/TextAppearance.Material3.BodyLarge"
                        android:textStyle="bold"/>
                </LinearLayout>
            </com.google.android.material.card.MaterialCardView>

        </LinearLayout>

    </LinearLayout>

</RelativeLayout>
~~~

*4. activity_produk.xml*
~~~ xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/main"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".LandakActivity">

    <!-- 1. Gambar Latar Belakang -->
    <ImageView
        android:layout_width="0dp"
        android:layout_height="0dp"
        android:src="@drawable/rounded_white_bg"
        android:scaleType="centerCrop"
        android:contentDescription="Background"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

    <!-- 2. Konten Utama (Kartu di Tengah) -->
    <LinearLayout
        android:id="@+id/card_content"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:orientation="vertical"
        android:layout_margin="16dp"
        android:padding="16dp"
        android:background="@drawable/rounded_white_bg"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent">

        <!-- Judul Utama -->
        <TextView
            android:id="@+id/tv_main_title"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="Waktu Shalat Indonesia"
            android:textSize="20sp"
            android:textStyle="bold"
            android:textColor="@android:color/black"
            android:gravity="center_horizontal" />

        <!-- Hitung Mundur -->
        <TextView
            android:id="@+id/tv_countdown"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="Memuat data..."
            android:textSize="22sp"
            android:textStyle="bold"
            android:textColor="@android:color/black"
            android:gravity="center_horizontal"
            android:layout_marginTop="4dp" />

        <!-- Waktu Server -->
        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:orientation="horizontal"
            android:gravity="center_vertical"
            android:layout_marginTop="16dp">

            <TextView
                android:id="@+id/tv_server_time"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="Waktu lokal: --:--:--"
                android:textSize="14sp"
                android:textColor="@android:color/black"/>

            <ProgressBar
                android:id="@+id/pb_sync_time"
                android:layout_width="16dp"
                android:layout_height="16dp"
                android:layout_marginStart="8dp"
                android:visibility="gone"
                tools:visibility="visible"
                style="?android:attr/progressBarStyleSmall" />
        </LinearLayout>

        <!-- Lokasi -->
        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:orientation="horizontal"
            android:gravity="center_vertical"
            android:layout_marginTop="8dp">

            <TextView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="Lokasi:"
                android:textSize="14sp"
                android:textColor="@android:color/black"/>

            <TextView
                android:id="@+id/tv_city"
                android:layout_width="0dp"
                android:layout_weight="1"
                android:layout_height="wrap_content"
                android:text="Mencari..."
                android:padding="8dp"
                android:background="@drawable/spinner_border"
                android:layout_marginStart="8dp"/>

            <Button
                android:id="@+id/btn_map"
                android:layout_width="wrap_content"
                android:layout_height="40dp"
                android:text="MAP"
                android:layout_marginStart="8dp"
                style="?android:attr/buttonStyleSmall"/>
        </LinearLayout>

        <!-- Tabel Waktu Shalat (Disederhanakan) -->
        <TableLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginTop="16dp"
            android:stretchColumns="1">

            <!-- Header Tanggal -->
            <TableRow android:background="#CCB2EBF2">
                <TextView
                    android:id="@+id/tv_date_hijri"
                    android:layout_span="2"
                    android:padding="8dp"
                    tools:text="Selasa, 19 Jumadil Akhir 1447 H"
                    android:textStyle="bold"
                    android:textColor="@android:color/black"/>
            </TableRow>
            <TableRow android:background="#CCB2EBF2" android:layout_marginBottom="4dp">
                <TextView
                    android:id="@+id/tv_date_today"
                    android:layout_span="2"
                    android:paddingStart="8dp"
                    android:paddingEnd="8dp"
                    android:paddingBottom="8dp"
                    tools:text="16 Desember 2025"
                    android:textColor="@android:color/black"/>
            </TableRow>

            <!-- Baris Shubuh -->
            <TableRow>
                <TextView android:text="Shubuh" android:padding="8dp"/>
                <TextView android:id="@+id/tv_shubuh_today" tools:text="04:30" android:gravity="center" android:padding="8dp"/>
            </TableRow>

            <!-- Baris Zhuhur -->
            <TableRow android:background="#0D000000">
                <TextView android:text="Zhuhur" android:padding="8dp"/>
                <TextView android:id="@+id/tv_zhuhur_today" tools:text="12:00" android:gravity="center" android:padding="8dp"/>
            </TableRow>

            <!-- Baris Ashar -->
            <TableRow>
                <TextView android:text="Ashar" android:padding="8dp"/>
                <TextView android:id="@+id/tv_ashar_today" tools:text="15:20" android:gravity="center" android:padding="8dp"/>
            </TableRow>

            <!-- Baris Maghrib -->
            <TableRow android:background="#0D000000">
                <TextView android:text="Maghrib" android:padding="8dp"/>
                <TextView android:id="@+id/tv_maghrib_today" tools:text="18:05" android:gravity="center" android:padding="8dp"/>
            </TableRow>

            <!-- Baris Isya -->
            <TableRow>
                <TextView android:text="Isya" android:padding="8dp"/>
                <TextView android:id="@+id/tv_isya_today" tools:text="19:15" android:gravity="center" android:padding="8dp"/>
            </TableRow>
        </TableLayout>

        <!-- INI ADALAH VIEW YANG SEBELUMNYA HILANG -->
        <TextView
            android:id="@+id/tv_api_source"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginTop="16dp"
            android:gravity="center"
            android:text="Sumber data"
            android:textColor="?android:attr/textColorSecondary"
            android:textSize="12sp" />

    </LinearLayout>

</androidx.constraintlayout.widget.ConstraintLayout>
~~~

✅ **End Process**

📱 Aplikasi ini dikembangkan sebagai bagian dari tugas akademik dengan tujuan menerapkan konsep pengembangan aplikasi Android menggunakan **Java** dan **XML** secara terstruktur dan sistematis.  

🧭 Alur navigasi yang jelas, pemisahan tanggung jawab pada setiap Activity, serta penggunaan layout reusable menjadi fokus utama dalam pengembangan aplikasi ini agar mudah dipahami dan dikembangkan kembali.

🚀 Ke depannya, aplikasi ini masih dapat dikembangkan lebih lanjut dengan penambahan fitur seperti melihat arah kiblat, integrasi database, optimasi performa, adanya notifikasi yang muncul di HP saat mendekati jadwal sholat serta peningkatan desain antarmuka agar semakin responsif dan user-friendly.

🎓 Penulis berharap proyek ini dapat menjadi media pembelajaran yang bermanfaat serta memenuhi tujuan pembelajaran pada mata kuliah terkait.  
💡 Kritik dan saran yang membangun sangat diharapkan untuk penyempurnaan aplikasi di masa mendatang.

<hr>

<p align="center">
  📱 <b>Final Project Aplikasi Android</b><br>
  🎓 Mata Kuliah: Pemrograman Mobile<br>
  🏫 Program Studi: Teknik Informatika
</p>

<p align="center">
  Dikembangkan oleh <b>Rafli Anugrah Ramadhan</b><br>
  © 2025 • Android Studio • Java • XML
</p>

<p align="center">
  <img src="https://capsule-render.vercel.app/api?type=waving&color=0:1565C0,100:1E88E5&height=120&section=footer"/>
</p>

















