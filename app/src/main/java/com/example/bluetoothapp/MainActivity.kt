package com.example.bluetoothapp

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.bluetoothapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val requestBtPermissions = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { _ ->
        updateUiWithBtStatus()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRequestPerms.setOnClickListener { ensureBtPermissions() }
        updateUiWithBtStatus()
    }

    private fun ensureBtPermissions() {
        val needed = mutableListOf<String>()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT)
                != PackageManager.PERMISSION_GRANTED) needed += Manifest.permission.BLUETOOTH_CONNECT
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN)
                != PackageManager.PERMISSION_GRANTED) needed += Manifest.permission.BLUETOOTH_SCAN
        } else {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH)
                != PackageManager.PERMISSION_GRANTED) needed += Manifest.permission.BLUETOOTH
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_ADMIN)
                != PackageManager.PERMISSION_GRANTED) needed += Manifest.permission.BLUETOOTH_ADMIN
        }
        if (needed.isNotEmpty()) requestBtPermissions.launch(needed.toTypedArray())
        else updateUiWithBtStatus()
    }

    private fun updateUiWithBtStatus() {
        val adapter = BluetoothAdapter.getDefaultAdapter()
        val supported = adapter != null
        val enabled = adapter?.isEnabled == true

        binding.txtInfo.text = buildString {
            appendLine("Bluetooth supported: $supported")
            appendLine("Bluetooth enabled:  $enabled")
        }
    }
}
