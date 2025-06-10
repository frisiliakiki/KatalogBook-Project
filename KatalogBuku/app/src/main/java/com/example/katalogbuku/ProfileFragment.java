package com.example.katalogbuku;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import com.bumptech.glide.Glide;
import com.example.katalogbuku.data.local.AppDatabase;
import com.example.katalogbuku.data.local.BookDao;
import com.example.katalogbuku.utils.ProfileManager;
import com.example.katalogbuku.utils.Theme;
import com.google.android.material.switchmaterial.SwitchMaterial;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProfileFragment extends Fragment {

    private Theme themeManager;
    private ProfileManager profileManager;
    private BookDao bookDao;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    private ImageView ivAvatar;
    private TextView tvUserName;
    private TextView tvFavoriteCount;
    private ActivityResultLauncher<Intent> imagePickerLauncher;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imagePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        Uri imageUri = result.getData().getData();
                        if (imageUri != null) {
                            profileManager.saveAvatarUri(imageUri.toString());
                            loadProfileData(); // Muat ulang data untuk menampilkan gambar baru
                        }
                    }
                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Inisialisasi Manager dan DAO
        themeManager = new Theme(requireActivity());
        profileManager = new ProfileManager(requireActivity());
        bookDao = AppDatabase.getDatabase(requireContext()).bookDao();

        // Inisialisasi Views
        ivAvatar = view.findViewById(R.id.iv_avatar);
        tvUserName = view.findViewById(R.id.tv_user_name);
        tvFavoriteCount = view.findViewById(R.id.tv_favorite_count);
        SwitchMaterial switchTheme = view.findViewById(R.id.switch_theme);
        ImageButton btnEditName = view.findViewById(R.id.btn_edit_name);
        ImageView ivEditAvatar = view.findViewById(R.id.iv_edit_avatar);

        // Setup Listeners
        switchTheme.setChecked(themeManager.isDarkMode());
        switchTheme.setOnCheckedChangeListener((buttonView, isChecked) -> themeManager.setTheme(isChecked));

        btnEditName.setOnClickListener(v -> showEditNameDialog());
        ivAvatar.setOnClickListener(v -> openImagePicker());
        ivEditAvatar.setOnClickListener(v -> openImagePicker());
    }

    @Override
    public void onResume() {
        super.onResume();
        loadProfileData();
        loadFavoriteStats();
    }

    private void loadProfileData() {
        // Muat nama dan gambar dari SharedPreferences
        tvUserName.setText(profileManager.getUserName());
        String avatarUriString = profileManager.getAvatarUri();
        if (avatarUriString != null) {
            Glide.with(this).load(Uri.parse(avatarUriString)).into(ivAvatar);
        }
    }

    // --- INI ADALAH IMPLEMENTASI YANG BENAR ---
    private void loadFavoriteStats() {
        executor.execute(() -> {
            // Hitung jumlah buku favorit di background thread
            int favoriteCount = bookDao.getFavoriteBooks().size();
            String statsText = favoriteCount + " Buku Difavoritkan";

            // Kirim hasil ke Main Thread untuk memperbarui UI
            new Handler(Looper.getMainLooper()).post(() -> {
                if (isAdded()) { // Pastikan fragment masih aktif
                    tvFavoriteCount.setText(statsText);
                }
            });
        });
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        imagePickerLauncher.launch(intent);
    }

    private void showEditNameDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Ubah Nama");

        final EditText input = new EditText(requireContext());
        input.setText(profileManager.getUserName());
        builder.setView(input);

        builder.setPositiveButton("Simpan", (dialog, which) -> {
            String newName = input.getText().toString().trim();
            if (!newName.isEmpty()) {
                profileManager.saveUserName(newName);
                loadProfileData(); // Muat ulang untuk menampilkan nama baru
            }
        });
        builder.setNegativeButton("Batal", (dialog, which) -> dialog.cancel());

        builder.show();
    }
}
