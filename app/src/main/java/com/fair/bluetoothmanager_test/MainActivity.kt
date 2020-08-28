package com.fair.bluetoothmanager_test

import android.bluetooth.BluetoothAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var bAdapter: BluetoothAdapter

    companion object {
        const val REQUEST_CODE_DISCOVERABLE_BT = 1023
        const val REQUEST_CODE_ENABLE_BT = 1246
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bAdapter = BluetoothAdapter.getDefaultAdapter()

        buttonTime.setOnClickListener {
            if(bAdapter.isEnabled) {
                //bluetooth is on
                Toast.makeText(this, "Already on", Toast.LENGTH_SHORT).show()
            } else {
                //bluetooth is off
                val intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                startActivityForResult(intent, REQUEST_CODE_ENABLE_BT)
            }

            // to turn off just bAdapter.disable()
        }
        buttonDiscover.setOnClickListener {
            discoverMode()
        }
        buttonPair.setOnClickListener {
            pairMode()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode) {
            REQUEST_CODE_ENABLE_BT -> {
                if (resultCode == RESULT_OK) {
                               Toast.makeText(this, "Bluetooth is on", Toast.LENGTH_SHORT).show()
                } else {
                               Toast.makeText(this, "couldn't turn on bluetooth", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun discoverMode() {
        if (!bAdapter.isDiscovering) {
             Toast.makeText(this, "making you discoverable", Toast.LENGTH_SHORT).show()
            val intent = Intent(Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE))
            startActivityForResult(intent, Companion.REQUEST_CODE_DISCOVERABLE_BT)
        } else {
            Toast.makeText(this, "already discovering", Toast.LENGTH_SHORT).show()
        }
    }

    private fun pairMode() {
        val list = mutableListOf<String>()
        if(bAdapter.isEnabled) {
            val devices = bAdapter.bondedDevices
            devices.forEach { obj ->
                     list.add(obj.name)
                 }
            statusTime.text = list.toString()
        } else {
              Toast.makeText(this, "turn on bluetooth first", Toast.LENGTH_SHORT).show()
          }
    }

}