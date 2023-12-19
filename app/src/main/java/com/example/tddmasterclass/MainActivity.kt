package com.example.tddmasterclass

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tddmasterclass.playlist.PlaylistFragment

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.container, PlaylistFragment.newInstance())
                .commit()
        }
    }
}