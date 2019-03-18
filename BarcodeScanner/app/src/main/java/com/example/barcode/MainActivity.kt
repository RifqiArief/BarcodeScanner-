package com.example.barcode

import android.content.ClipData
import android.content.ClipboardManager
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.annotation.IntegerRes
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import com.google.zxing.Result
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.barcode_text.view.*
import me.dm7.barcodescanner.zxing.ZXingScannerView
import java.util.jar.Manifest


class MainActivity : AppCompatActivity(), ZXingScannerView.ResultHandler {

    private var frame_barcode :FrameLayout? = null
    private var container_fragment : FrameLayout? = null
    private var clipboard: ClipboardManager? = null
    private var clip: ClipData? = null

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        var content: FrameLayout? = null
        when (item.itemId) {
            R.id.nav_generate-> {
                visibility(1)
                supportFragmentManager.beginTransaction().replace(R.id.container_fragment, FragmentGenerate()).commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_scan-> {
                clearFrag()
                visibility(0)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private lateinit var scannerView :ZXingScannerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        container_fragment = findViewById(R.id.container_fragment) as FrameLayout
        frame_barcode = findViewById(R.id.frame_scanner) as FrameLayout
        clipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager?
        visibility(0)
        initScanner()
        //initDefaultView()
        //x`navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    override fun onStart() {
        requestPermission()
        scannerView.startCamera()
        super.onStart()
    }

    override fun onPause() {
        scannerView.stopCamera()
        super.onPause()
    }

    private fun initScanner(){
        scannerView = ZXingScannerView(this)
        scannerView.setAutoFocus(true)
        scannerView.setResultHandler(this)
        frame_scanner.addView(scannerView)
    }

//    private fun initDefaultView() {
//        txtbarcode.text = "your QR Code"
//        btnreset.visibility = View.GONE
//    }

    private fun requestPermission(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(checkSelfPermission(android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                requestPermissions(arrayOf(android.Manifest.permission.CAMERA),100)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            100 -> {
                initScanner()
            }
            else -> {
                /* nothing to do in here */
            }
        }
    }

    override fun handleResult(rawResult: Result?) {

        val builder = AlertDialog.Builder(this)
//        builder.setTitle("Androidly Alert")
        builder.setMessage(rawResult?.text)

        builder.setPositiveButton("Copy") { dialog, which ->
            copyText(rawResult?.text.toString())
        }

        builder.setNeutralButton("OK") { dialog, which ->
            scannerView.resumeCameraPreview(this)
        }

        builder.setCancelable(false)
        builder.show()
    }

    private fun clearFrag() {
        for (fragment in supportFragmentManager.fragments) {
            supportFragmentManager.beginTransaction().remove(fragment).commit()
        }
    }

    private fun copyText(txtcopy: String) {
        scannerView.resumeCameraPreview(this)

        clip = ClipData.newPlainText("text", txtcopy)
        clipboard?.setPrimaryClip(clip)

        Toast.makeText(this, "Barcode text copied", Toast.LENGTH_SHORT).show();
    }

    fun visibility(case:Int){
        when (case){
            0 ->if (container_fragment?.visibility == View.VISIBLE){
                container_fragment?.visibility = View.GONE
                frame_barcode?.visibility = View.VISIBLE
            }

            1 ->if (frame_barcode?.visibility == View.VISIBLE){
                frame_barcode?.visibility = View.GONE
                container_fragment?.visibility = View.VISIBLE
            }
        }
    }
}
