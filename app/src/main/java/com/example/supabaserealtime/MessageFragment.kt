package com.example.supabaserealtime

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.realtime.PostgresAction
import io.github.jan.supabase.realtime.RealtimeChannel
import io.github.jan.supabase.realtime.createChannel
import io.github.jan.supabase.realtime.decodeRecord
import io.github.jan.supabase.realtime.postgresChangeFlow
import io.github.jan.supabase.realtime.realtime
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MessageFragment : Fragment() {

    private lateinit var client: SupabaseClient
    private lateinit var channel: RealtimeChannel
    private lateinit var chatBox: EditText
    private lateinit var messageView: TextView
    lateinit var sendButton: Button
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        client = (requireActivity().application as MyApplication).supabase
        channel = client.realtime.createChannel("channelId")
        return inflater.inflate(R.layout.fragment_message, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        chatBox = view.findViewById(R.id.chatBox)
        messageView = view.findViewById(R.id.message)
        sendButton = view.findViewById(R.id.buttonSend)
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

                val changeFlow =
                    channel.postgresChangeFlow<PostgresAction.Insert>(schema = "public") {
                        table = "messages"
                    }

                //in a new coroutine (or use Flow.onEach().launchIn(scope)):
                changeFlow.onEach {
                    val message = it.decodeRecord<Message>()
                    messageView.text = message.content
                    Log.d("TAG", "onViewCreated: ${it.record}")
                }.launchIn(lifecycleScope)
                channel.join()
            }
        }
        sendButton.setOnClickListener {
            val message = chatBox.text.trim().toString()
            insertMessage(Message(content = message))
        }
    }

    private fun insertMessage(message: Message) {
        viewLifecycleOwner.lifecycleScope.launch {
            client.postgrest["messages"].insert(message)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewLifecycleOwner.lifecycleScope.launch {
            channel.leave()
        }
    }
}