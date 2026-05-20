package com.example.clonediscordapp.ui.server;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import com.example.clonediscordapp.R;
import com.example.clonediscordapp.databinding.FragmentServerMenuSheetBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class ServerMenuBottomSheet extends BottomSheetDialogFragment {

    private FragmentServerMenuSheetBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentServerMenuSheetBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 1. Server Settings Row Navigation Click Listener
        binding.btnMenuServerSettings.setOnClickListener(v -> {
            dismiss();
            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
                    .navigate(R.id.nav_server_settings);
        });

        // 2. Channel Settings Row Navigation Click Listener
        binding.btnMenuChannelSettings.setOnClickListener(v -> {
            dismiss();
            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
                    .navigate(R.id.nav_channel_settings);
        });

        // 3. Invite People Row Clicks
        binding.btnMenuInvite.setOnClickListener(v -> {
            dismiss();
            Toast.makeText(requireContext(), "Invite link copied to clipboard! Share it with your friends! 👥✨", Toast.LENGTH_LONG).show();
        });

        // 4. Leave Server Row Clicks
        binding.btnMenuLeave.setOnClickListener(v -> {
            dismiss();
            Toast.makeText(requireContext(), "Leaving server requires owner confirmation. Mock warning: You cannot leave the Fantasy World! 🛑", Toast.LENGTH_LONG).show();
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
