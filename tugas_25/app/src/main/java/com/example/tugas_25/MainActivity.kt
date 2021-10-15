package com.example.tugas_25

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.Executor

class MainActivity : AppCompatActivity() {
    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //init biometric
        executor = ContextCompat.getMainExecutor(this)

        biometricPrompt = BiometricPrompt(this@MainActivity, executor, object : BiometricPrompt.AuthenticationCallback(){
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                //auth error, stop tasks that requires auth
                authStatusTv.text = "Authentication Error: $errString"
                Toast.makeText(this@MainActivity, "Authentication Error: $errString", Toast.LENGTH_SHORT).show()
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                //auth succeed, do tasks that requires auth
                authStatusTv.text = "Auth succeed...!"
                Toast.makeText(this@MainActivity, "Auth succeed...!", Toast.LENGTH_SHORT).show()

            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                //auth failed, stop tasks that requires auth
                authStatusTv.text = "Auth failed...!"
                Toast.makeText(this@MainActivity, "Auth failed...!", Toast.LENGTH_SHORT).show()
            }
        })
        //set properties like title and description on auth dialog
        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric Authentication")
            .setSubtitle("Login using fingerprint authentication")
            .setNegativeButtonText("Use App Password instead")
            .build()

        //handle click, start authtenitcation dialog
        authBtn.setOnClickListener {
            //show auth dialog
            biometricPrompt.authenticate(promptInfo)
        }
    }
}