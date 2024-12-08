package com.example.talev1_0;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.talev1_0.adapters.ChatAdapter;
import com.example.talev1_0.helpers.Message;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class ChatFragment extends Fragment {

    private WebSocket webSocket;
    private EditText messageEditText;
    private ChatAdapter chatAdapter;
    private List<Message> messageList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        // Initialize message list and adapter
        messageList = new ArrayList<>();
        chatAdapter = new ChatAdapter(messageList);

        // Set up RecyclerView
        RecyclerView chatRecyclerView = view.findViewById(R.id.chatRecyclerView);
        chatRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        chatRecyclerView.setAdapter(chatAdapter);

        // Initialize UI elements
        messageEditText = view.findViewById(R.id.messageEditText);

        view.findViewById(R.id.sendButton).setOnClickListener(v -> {
            String message = messageEditText.getText().toString();
            if (!message.isEmpty()) {
                sendMessage(message);
                messageEditText.setText("");
            }
        });

        // Connect to the WebSocket server
        connectToWebSocket();

        return view;
    }

    private void connectToWebSocket() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("ws://10.0.2.2:8080/chat")
                .build();

        webSocket = client.newWebSocket(request, new WebSocketListener() {
            @Override
            public void onOpen(@NonNull WebSocket webSocket, @NonNull Response response) {
                requireActivity().runOnUiThread(() ->
                        Toast.makeText(requireContext(), "Connected to server", Toast.LENGTH_SHORT).show()
                );
            }

            @Override
            public void onMessage(@NonNull WebSocket webSocket, @NonNull String text) {
                requireActivity().runOnUiThread(() -> {
                    messageList.add(new Message(text));
                    chatAdapter.notifyItemInserted(messageList.size() - 1);

                    // Scroll to the last message
                    RecyclerView chatRecyclerView = requireView().findViewById(R.id.chatRecyclerView);
                    chatRecyclerView.scrollToPosition(messageList.size() - 1);
                });
            }


            @Override
            public void onFailure(@NonNull WebSocket webSocket, @NonNull Throwable t, @Nullable Response response) {
                requireActivity().runOnUiThread(() ->
                        Toast.makeText(requireContext(), "Connection failed: " + t.getMessage(), Toast.LENGTH_SHORT).show()
                );
            }
        });
    }

    public void sendMessage(String message) {
        if (webSocket != null) {
            webSocket.send(message);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (webSocket != null) {
            webSocket.close(1000, "Fragment destroyed");
        }
    }
}
