package com.example.clonediscordapp.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.clonediscordapp.R;
import com.example.clonediscordapp.databinding.FragmentHomeBinding;
import com.example.clonediscordapp.ui.adapters.DirectMessageAdapter;
import com.example.clonediscordapp.ui.adapters.ServerAdapter;
import com.example.clonediscordapp.ui.profile.UserProfileBottomSheet;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private HomeViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        // Server Adapter
        ServerAdapter serverAdapter = new ServerAdapter(server -> {
            // Navigate to server
            Navigation.findNavController(view).navigate(R.id.action_home_to_server);
        });
        binding.rvServers.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvServers.setAdapter(serverAdapter);
        serverAdapter.setActiveServerId("s0"); // Mock active server

        // DM Adapter
        DirectMessageAdapter dmAdapter = new DirectMessageAdapter(new DirectMessageAdapter.OnMessageClickListener() {
            @Override
            public void onMessageClick(com.example.clonediscordapp.data.model.DirectMessage message) {
                // Navigate to chat (not implemented yet)
            }

            @Override
            public void onAvatarClick(com.example.clonediscordapp.data.model.DirectMessage message) {
                UserProfileBottomSheet sheet = new UserProfileBottomSheet();
                sheet.show(getChildFragmentManager(), "ProfileSheet");
            }
        });
        binding.rvDirectMessages.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvDirectMessages.setAdapter(dmAdapter);

        // Observe Data
        viewModel.getServers().observe(getViewLifecycleOwner(), serverAdapter::submitList);
        viewModel.getDirectMessages().observe(getViewLifecycleOwner(), dmAdapter::submitList);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
