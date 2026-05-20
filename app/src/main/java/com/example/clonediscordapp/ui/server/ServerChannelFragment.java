package com.example.clonediscordapp.ui.server;

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
import com.example.clonediscordapp.databinding.FragmentServerChannelBinding;
import com.example.clonediscordapp.ui.adapters.ChatMessageAdapter;
import com.example.clonediscordapp.ui.adapters.ServerAdapter;
import com.example.clonediscordapp.ui.profile.UserProfileBottomSheet;

public class ServerChannelFragment extends Fragment {

    private FragmentServerChannelBinding binding;
    private ServerViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentServerChannelBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(ServerViewModel.class);

        // Server Adapter
        ServerAdapter serverAdapter = new ServerAdapter(server -> {
            // Handle server change
        });
        binding.rvServers.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvServers.setAdapter(serverAdapter);
        serverAdapter.setActiveServerId("s1"); // Mock active server

        // Chat Adapter
        ChatMessageAdapter chatAdapter = new ChatMessageAdapter(message -> {
            UserProfileBottomSheet sheet = new UserProfileBottomSheet();
            sheet.show(getChildFragmentManager(), "ProfileSheet");
        });
        binding.rvChatMessages.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvChatMessages.setAdapter(chatAdapter);

        // Observe Data
        viewModel.getServers().observe(getViewLifecycleOwner(), serverAdapter::submitList);
        viewModel.getMessages().observe(getViewLifecycleOwner(), messages -> {
            chatAdapter.submitList(messages);
            if (!messages.isEmpty()) {
                binding.rvChatMessages.scrollToPosition(messages.size() - 1);
            }
        });

        // Setup Voice Banner
        binding.btnJoinVoice.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_server_to_voice);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
