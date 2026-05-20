package com.example.clonediscordapp.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.clonediscordapp.data.MockData;
import com.example.clonediscordapp.data.model.DirectMessage;
import com.example.clonediscordapp.data.model.Server;

import java.util.List;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<List<DirectMessage>> _directMessages = new MutableLiveData<>(MockData.getDirectMessages());
    public LiveData<List<DirectMessage>> getDirectMessages() { return _directMessages; }

    private final MutableLiveData<List<Server>> _servers = new MutableLiveData<>(MockData.getServers());
    public LiveData<List<Server>> getServers() { return _servers; }
}
