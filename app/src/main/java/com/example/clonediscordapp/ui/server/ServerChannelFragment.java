package com.example.clonediscordapp.ui.server;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.clonediscordapp.R;
import com.example.clonediscordapp.data.MockData;
import com.example.clonediscordapp.data.model.ChatMessage;
import com.example.clonediscordapp.databinding.FragmentServerChannelBinding;
import com.example.clonediscordapp.ui.adapters.ChatMessageAdapter;
import com.example.clonediscordapp.ui.adapters.ServerAdapter;
import com.example.clonediscordapp.ui.profile.UserProfileBottomSheet;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

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

        // Server Sidebar Home Button (Inactive) - Navigates Back to DMs screen
        binding.btnSidebarHome.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.nav_home);
        });

        // Server Adapter
        ServerAdapter serverAdapter = new ServerAdapter(server -> {
            // Handle server change click with feedback
            Toast.makeText(requireContext(), "Opening " + server.getName() + " server...", Toast.LENGTH_SHORT).show();
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

        // Live Send Message Action Click Listener
        binding.btnSend.setOnClickListener(v -> {
            String text = binding.etMessage.getText().toString().trim();
            if (text.isEmpty()) {
                return;
            }

            // Get formatted current time
            SimpleDateFormat sdf = new SimpleDateFormat("h:mm a", Locale.US);
            String timeStr = sdf.format(new Date());

            // Construct new ChatMessage
            ChatMessage newMessage = new ChatMessage(
                    UUID.randomUUID().toString(),
                    MockData.ME, // Sent by "Me" user profile
                    text,
                    timeStr
            );

            // Add new message to ViewModel and LiveData
            viewModel.addMessage(newMessage);

            // Clear EditText field
            binding.etMessage.setText("");
        });

        // Toolbar clicks and interactive accessories click handlers
        binding.btnMenu.setOnClickListener(v -> {
            ServerMenuBottomSheet menuSheet = new ServerMenuBottomSheet();
            menuSheet.show(getParentFragmentManager(), "ServerMenuBottomSheet");
        });

        binding.btnToolbarMute.setOnClickListener(v -> 
            Toast.makeText(requireContext(), "Server channel notifications muted", Toast.LENGTH_SHORT).show()
        );

        binding.btnToolbarPins.setOnClickListener(v -> 
            Toast.makeText(requireContext(), "Opening pinned messages...", Toast.LENGTH_SHORT).show()
        );

        binding.btnToolbarMembers.setOnClickListener(v -> 
            Toast.makeText(requireContext(), "Opening server members list...", Toast.LENGTH_SHORT).show()
        );

        binding.btnChatUpload.setOnClickListener(v -> 
            Toast.makeText(requireContext(), "Opening file attachments...", Toast.LENGTH_SHORT).show()
        );

        binding.btnChatEmoji.setOnClickListener(v -> 
            Toast.makeText(requireContext(), "Opening custom server emojis panel...", Toast.LENGTH_SHORT).show()
        );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
