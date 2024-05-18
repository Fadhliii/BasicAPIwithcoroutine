package com.example.api01

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.api01.Model.service.ApiClient
import com.example.api01.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val adapter by lazy { MovieAdapter() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // rv setup
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.adapter = adapter

        // request ke api corountine di viewmodel
        GlobalScope.launch(Dispatchers.IO) {
            launch(Dispatchers.Main) {
                flow {
                    val response =
                            ApiClient.movieService.getMovie("f06b99d0") // get the movie from the API movieservice
                    emit(response)
                } //memanggil apabila API request start
                    .onStart {
                        // show loading
                        binding.progressBar.isVisible = true
                    } //memanggil apabila API request selesai
                    .onCompletion {
                        // hide loading
                        binding.progressBar.isVisible = false
                    }
                    .catch {
                        // handle error
                        Toast.makeText(this@MainActivity, it.message, Toast.LENGTH_SHORT).show()
                        Log.e("Error", it.message.toString())
                    }
                    .collect {
                        // handle response
                        response ->
                        adapter.setData(mutableListOf(response))
                    }
            }


        }

    }
}