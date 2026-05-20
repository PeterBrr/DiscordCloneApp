package com.example.clonediscordapp.ui.server;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.clonediscordapp.databinding.FragmentServerSettingsBinding;

public class ServerSettingsFragment extends Fragment {

    private FragmentServerSettingsBinding binding;
    private int modLevelIndex = 1; // 0: Low, 1: Medium, 2: High
    private final String[] modLevels = {"Low (Verify Email)", "Medium (Verify Email & 5 mins)", "High (Verify Phone)"};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentServerSettingsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 1. Back Arrow Clicks - Returns to server chat
        binding.btnBack.setOnClickListener(v -> {
            Navigation.findNavController(v).navigateUp();
        });

        // 2. Save Button Clicks - Saves and returns
        binding.btnSave.setOnClickListener(v -> {
            String serverName = binding.etServerName.getText().toString().trim();
            if (serverName.isEmpty()) {
                Toast.makeText(requireContext(), "Server name cannot be empty!", Toast.LENGTH_SHORT).show();
                return;
            }

            Toast.makeText(requireContext(), "Server settings saved successfully: \"" + serverName + "\"! 💾✨", Toast.LENGTH_LONG).show();
            Navigation.findNavController(v).navigateUp();
        });

        // 3. Interactive verification level toggling simulation
        binding.btnModeration.setOnClickListener(v -> {
            modLevelIndex = (modLevelIndex + 1) % modLevels.length;
            binding.tvModerationLevel.setText(modLevels[modLevelIndex]);
            Toast.makeText(requireContext(), "Verification level updated to: " + modLevels[modLevelIndex], Toast.LENGTH_SHORT).show();
        });

        // 4. Bans Row Click list alert
        binding.btnBans.setOnClickListener(v -> {
            Toast.makeText(requireContext(), "Zero bans active in this server. Everything is peaceful! 🕊️", Toast.LENGTH_SHORT).show();
        });

        // Click Avatar mockup
        binding.ivServerAvatar.setOnClickListener(v -> 
            Toast.makeText(requireContext(), "Opening avatar gallery loader...", Toast.LENGTH_SHORT).show()
        );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
