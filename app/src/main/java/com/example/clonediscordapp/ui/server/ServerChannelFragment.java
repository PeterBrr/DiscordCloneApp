package com.example.clonediscordapp.ui.server;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.clonediscordapp.R;
import com.example.clonediscordapp.data.MockData;
import com.example.clonediscordapp.data.model.ChatMessage;
import com.example.clonediscordapp.data.model.Firebase;
import com.example.clonediscordapp.data.model.Server;
import com.example.clonediscordapp.databinding.FragmentServerChannelBinding;
import com.example.clonediscordapp.ui.adapters.ChatMessageAdapter;
import com.example.clonediscordapp.ui.adapters.ServerAdapter;
import com.example.clonediscordapp.ui.profile.UserProfileBottomSheet;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ServerChannelFragment extends Fragment {

    private static final String TAG = "ServerChannelFragment";

    private FragmentServerChannelBinding binding;
    private FirebaseFirestore db;
    private DatabaseReference rtdbRef;
    private ValueEventListener chatListener;
    private ListenerRegistration serverListener;

    private String currentServerId = "s1";
    private String currentServerName = "Fantasy World";

    private String myUserName = "Me";
    private String myAvatar = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentServerChannelBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        db = FirebaseFirestore.getInstance();

        // 1. Fetch real current user profile info from Firestore
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            db.collection("users").document(currentUser.getUid()).get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            myUserName = documentSnapshot.getString("username");
                            myAvatar = documentSnapshot.getString("avatar");
                            if (myAvatar == null) myAvatar = "";
                        } else {
                            myUserName = currentUser.getDisplayName() != null ? currentUser.getDisplayName() : "User";
                            myAvatar = currentUser.getPhotoUrl() != null ? currentUser.getPhotoUrl().toString() : "";
                        }
                    });
        }

        // 2. Server Sidebar Home Button (Inactive) - Navigates Back to DMs screen
        binding.btnSidebarHome.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.nav_home);
        });

        // 3. Initialize Chat Adapter
        ChatMessageAdapter chatAdapter = new ChatMessageAdapter(message -> {
            UserProfileBottomSheet sheet = new UserProfileBottomSheet();
            sheet.show(getChildFragmentManager(), "ProfileSheet");
        });
        binding.rvChatMessages.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvChatMessages.setAdapter(chatAdapter);

        // 4. Initialize Server Adapter
        ServerAdapter serverAdapter = new ServerAdapter(server -> {
            currentServerId = server.getId();
            currentServerName = server.getName();
            binding.tvChannelName.setText("# general-chat"); // default subchannel
            binding.tvVoiceChannelTitle.setText(currentServerName);

            // Re-listen for chats
            listenForMessages(currentServerId, chatAdapter);
        });
        binding.rvServers.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvServers.setAdapter(serverAdapter);

        // 5. Seed Firestore with Mock Servers if empty, and listen in real-time
        checkAndSeedData(serverAdapter);

        // 6. Setup Voice Banner Navigation with arguments
        binding.btnJoinVoice.setOnClickListener(v -> {
            Bundle args = new Bundle();
            args.putString("SERVER_ID", currentServerId);
            args.putString("SERVER_NAME", currentServerName);
            Navigation.findNavController(v).navigate(R.id.action_server_to_voice, args);
        });

        // 7. Live Send Message Action
        binding.btnSend.setOnClickListener(v -> {
            String text = binding.etMessage.getText().toString().trim();
            if (text.isEmpty()) {
                return;
            }

            String uid = currentUser != null ? currentUser.getUid() : "u0";
            ChatMessage newMessage = new ChatMessage(
                    UUID.randomUUID().toString(),
                    uid,
                    myUserName,
                    myAvatar,
                    text,
                    System.currentTimeMillis()
            );

            if (rtdbRef != null) {
                rtdbRef.push().setValue(newMessage);
            }
            binding.etMessage.setText("");
        });

        // 8. Toolbar clicks and interactive accessories click handlers
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

    private void checkAndSeedData(ServerAdapter serverAdapter) {
        db.collection("servers").get().addOnSuccessListener(queryDocumentSnapshots -> {
            if (queryDocumentSnapshots.isEmpty()) {
                // Upload mock servers to Firestore so it is never blank
                List<Server> mockServers = MockData.getServers();
                for (Server server : mockServers) {
                    db.collection("servers").document(server.getId()).set(server);
                }
            }
            // Start snapshot listener for real-time updates
            listenForServers(serverAdapter);
        });
    }

    private void listenForServers(ServerAdapter serverAdapter) {
        if (serverListener != null) {
            serverListener.remove();
        }
        serverListener = db.collection("servers").orderBy("orderIndex", Query.Direction.ASCENDING)
                .addSnapshotListener((snapshots, e) -> {
                    if (e != null) {
                        Log.w(TAG, "Listen for servers failed", e);
                        return;
                    }
                    if (snapshots != null) {
                        List<Server> serversList = new ArrayList<>();
                        for (DocumentSnapshot doc : snapshots) {
                            Server server = doc.toObject(Server.class);
                            if (server != null) {
                                server.setId(doc.getId());
                                serversList.add(server);
                            }
                        }
                        serverAdapter.submitList(serversList);

                        // Auto-select the first server on initial load
                        if (!serversList.isEmpty() && currentServerId.equals("s1")) {
                            currentServerId = serversList.get(0).getId();
                            currentServerName = serversList.get(0).getName();
                            serverAdapter.setActiveServerId(currentServerId);
                            binding.tvVoiceChannelTitle.setText(currentServerName);

                            // Load chat messages
                            ChatMessageAdapter chatAdapter = (ChatMessageAdapter) binding.rvChatMessages.getAdapter();
                            if (chatAdapter != null) {
                                listenForMessages(currentServerId, chatAdapter);
                            }
                        }
                    }
                });
    }

    private void listenForMessages(String serverId, ChatMessageAdapter chatAdapter) {
        if (rtdbRef != null && chatListener != null) {
            rtdbRef.removeEventListener(chatListener);
        }
        rtdbRef = Firebase.getDatabase().getReference("chats").child(serverId);
        chatListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (getContext() == null) return;

                List<ChatMessage> messageList = new ArrayList<>();
                for (DataSnapshot data : snapshot.getChildren()) {
                    ChatMessage model = data.getValue(ChatMessage.class);
                    if (model != null) {
                        model.setMessageId(data.getKey());
                        messageList.add(model);
                    }
                }
                
                // If it is empty (newly created server), seed initial mock messages
                if (messageList.isEmpty()) {
                    List<ChatMessage> mockMessages = MockData.getChatMessages();
                    for (ChatMessage mockMsg : mockMessages) {
                        rtdbRef.push().setValue(mockMsg);
                    }
                    return;
                }

                chatAdapter.submitList(messageList);
                binding.rvChatMessages.scrollToPosition(messageList.size() - 1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "RTDB listen cancelled", error.toException());
            }
        };
        rtdbRef.addValueEventListener(chatListener);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (rtdbRef != null && chatListener != null) {
            rtdbRef.removeEventListener(chatListener);
        }
        if (serverListener != null) {
            serverListener.remove();
        }
        binding = null;
    }
}
