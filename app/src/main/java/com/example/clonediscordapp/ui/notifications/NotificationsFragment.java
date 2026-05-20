package com.example.clonediscordapp.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.clonediscordapp.databinding.FragmentNotificationsBinding;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Simple interactive mock controls
        binding.btnAcceptFriend.setOnClickListener(v -> {
            Toast.makeText(requireContext(), "Friend request accepted!", Toast.LENGTH_SHORT).show();
            v.setEnabled(false);
            binding.btnDeclineFriend.setVisibility(View.GONE);
        });

        binding.btnDeclineFriend.setOnClickListener(v -> {
            Toast.makeText(requireContext(), "Friend request ignored.", Toast.LENGTH_SHORT).show();
            binding.btnAcceptFriend.setVisibility(View.GONE);
            v.setEnabled(false);
        });

        binding.btnJoinInvite.setOnClickListener(v -> {
            Toast.makeText(requireContext(), "Joining Cyber City...", Toast.LENGTH_SHORT).show();
            v.setEnabled(false);
        });

        binding.btnClearNotifications.setOnClickListener(v -> {
            Toast.makeText(requireContext(), "Cleared all notifications", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
