package com.example.supabaserealtime

import android.app.Application
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.GoTrue
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.realtime.Realtime
import io.github.jan.supabase.storage.Storage

class MyApplication: Application() {
    lateinit var supabase: SupabaseClient
    override fun onCreate() {
        super.onCreate()
        supabase = createSupabaseClient("", "") {
            //Already the default serializer
            install(GoTrue){
                scheme = "com.example.supabaserealtime"
                host = "login-callback"
                alwaysAutoRefresh = true
            }
            install(Postgrest)
            install(Realtime)
            install(Storage)
        }
    }
}