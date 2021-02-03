package com.example.firebasedemo

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.firebasedemo.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repository = Repository()

        repository.signOut()

        binding.btnRegister.setOnClickListener {
            val email = binding.etEmailRegister.text.toString()
            val password = binding.etPasswordRegister.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                CoroutineScope(Dispatchers.Main).launch {
                    repository.registerUser(email, password)
                    repository.response.observe(this@MainActivity, {
                        binding.tvLoggedIn.text = it
                    })
                }
            }
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmailLogin.text.toString()
            val password = binding.etPasswordLogin.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                CoroutineScope(Dispatchers.Main).launch {
                    repository.loginUser(email, password)
                    repository.response.observe(this@MainActivity, {
                        binding.tvLoggedIn.text = it
                    })

                    binding.etUsername.setText(repository.getCurrentUser()?.displayName)
                    binding.ivProfilePicture.setImageURI(repository.getCurrentUser()?.photoUrl)
                }
            }
        }

        binding.btnUpdateProfile.setOnClickListener {
            val name = binding.etUsername.text.toString()
            val photoURI = Uri.parse("android.resource://$packageName/${R.drawable.image1}")

            if (name.isNotEmpty()) {
                CoroutineScope(Dispatchers.Main).launch {
                    repository.updateProfile(name, photoURI)
                    repository.response.observe(this@MainActivity, {
                        binding.tvLoggedIn.text = it
                    })
                }
            }
        }
    }
}