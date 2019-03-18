package com.example.barcode

import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.qrcode.encoder.QRCode
import com.google.zxing.BarcodeFormat
import com.google.zxing.aztec.encoder.Encoder
import com.google.zxing.common.BitMatrix

class FragmentGenerate : Fragment(){

//    internal var bitmap: Bitmap? = null
//    private var etgenerate= view!!.findViewById<EditText>(R.id.et_generate)
//    private var iv = view!!.findViewById<ImageView>(R.id.iv)
//    private var btngenerate = view!!.findViewById<Button>(R.id.btn_generate)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val lf = activity!!.layoutInflater
        val view = lf.inflate(R.layout.generator, container, false) //pass the correct layout name for the fragment

//        btngenerate!!.setOnClickListener {
//            if (etgenerate!!.text.toString().trim { it <= ' ' }.length == 0) {
//                Toast.makeText(activity, "Enter String!", Toast.LENGTH_SHORT).show()
//            } else {
//                try {
//                    bitmap = barc(etqr!!.text.toString())
//                    iv!!.setImageBitmap(bitmap)
//                    val path = saveImage(bitmap)  //give read write permission
//                    Toast.makeText(this@MainActivity, "QRCode saved to -> $path", Toast.LENGTH_SHORT).show()
//                } catch (e: WriterException) {
//                    e.printStackTrace()
//                }
//
//            }
//        }

        return view
    }


}