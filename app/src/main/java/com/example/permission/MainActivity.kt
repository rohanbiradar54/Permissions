package com.example.permission

import android.Manifest
import android.app.AlertDialog
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : AppCompatActivity() {

    private val cameraResultLauncher: ActivityResultLauncher<String> =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()){
                isGranted ->
            if(isGranted){
                Toast.makeText(this,"Permission granted",Toast.LENGTH_LONG).show()
            }
            else{
                Toast.makeText(this,"Permission denied ",Toast.LENGTH_LONG).show()
            }
        }

    private val cameraAndLocationResultLauncher: ActivityResultLauncher<Array<String>> =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()){
                permissions ->
            permissions.entries.forEach {
                val permissionName=it.key
                val isGranted=it.value
                if (isGranted){
                    if (permissionName==Manifest.permission.ACCESS_FINE_LOCATION){
                        Toast.makeText(this,"Permission for Fine Location granted",Toast.LENGTH_LONG).show()
                    }
                    else if (permissionName==Manifest.permission.ACCESS_COARSE_LOCATION){
                        Toast.makeText(this,"Permission for Coarse Location granted",Toast.LENGTH_LONG).show()
                    }
                    else{
                        Toast.makeText(this,"Permission for Camera granted",Toast.LENGTH_LONG).show()
                    }
                }
                else{
                    if (permissionName==Manifest.permission.ACCESS_FINE_LOCATION){
                        Toast.makeText(this,"Permission for Fine Location denied",Toast.LENGTH_LONG).show()
                    }
                    else if (permissionName==Manifest.permission.ACCESS_COARSE_LOCATION){
                        Toast.makeText(this,"Permission for Coarse Location denied",Toast.LENGTH_LONG).show()
                    }
                    else{
                        Toast.makeText(this,"Permission for Camera Denied",Toast.LENGTH_LONG).show()
                    }

                }
            }
        }





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnPermission: Button= findViewById(R.id.permission)
        btnPermission.setOnClickListener {
            if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M && shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)){
                showRationaleDialog("Permission Demo requires camera access","Camera cannot be used because Camera access is denied")
            }
            else{
                cameraAndLocationResultLauncher.launch(
                    arrayOf(Manifest.permission.CAMERA,Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION)
                )
            }
        }
    }

    private fun showRationaleDialog(
        title:String,
        message: String
    )
    {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle(title)
            .setMessage(message)
            .setPositiveButton("cancel"){dialog, _->
                dialog.dismiss()
            }
        builder.create().show()


    }


}