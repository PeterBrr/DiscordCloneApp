package com.example.clonediscordapp.ui.server;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.clonediscordapp.data.MockData;
import com.example.clonediscordapp.data.model.ChatMessage;
import com.example.clonediscordapp.data.model.Server;

import java.util.List;

public class ServerViewModel extends ViewModel {

    private final MutableLiveData<List<ChatMessage>> _messages = new MutableLiveData<>(MockData.getChatMessages());
    public LiveData<List<ChatMessage>> getMessages() { return _messages; }

    private final MutableLiveData<List<Server>> _servers = new MutableLiveData<>(MockData.getServers());
    public LiveData<List<Server>> getServers() { return _servers; }
}
