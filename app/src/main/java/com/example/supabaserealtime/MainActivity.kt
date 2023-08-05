package com.example.supabaserealtime

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.realtime.realtime
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var client: SupabaseClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        client = (application as MyApplication).supabase
        lifecycleScope.launch {
            client.realtime.connect()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        client.realtime.disconnect()
    }
}