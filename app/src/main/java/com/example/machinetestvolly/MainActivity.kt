package com.example.machinetestvolly

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.example.machinetestvolly.databinding.ActivityMainBinding
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject

class MainActivity : AppCompatActivity() {


    lateinit var binding: ActivityMainBinding
    var products = ArrayList<Product>()
    private lateinit var productAdapter:ProductAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        VolleySingleton.initializeRequestQueue(this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        CoroutineScope(Dispatchers.IO).launch {
            binding.btn.setOnClickListener {
                jsonObjectRequest()
            }
            productAdapter = ProductAdapter(products)

            CoroutineScope(Dispatchers.Main).launch {
                initRecyclerView()
            }
        }
    }

    private fun initRecyclerView() {
        binding.recyclerProducts.layoutManager = LinearLayoutManager(
            this, LinearLayoutManager.VERTICAL,
            false
        )
        binding.recyclerProducts.adapter = productAdapter

    }

    private fun jsonObjectRequest() {
        var volleyJsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,
            "https://dummyjson.com/products",
            null,
            JsonObjectRequestSuccessListener(),
            StringRequestErrorListener()
        )
        VolleySingleton.volleyRequestQueue!!.add(volleyJsonObjectRequest)
    }

    inner class JsonObjectRequestSuccessListener : Response.Listener<JSONObject> {
        override fun onResponse(response: JSONObject?) {
            var productResponse = Gson().fromJson(
                response.toString(),
                ProductResponse::class.java
            )

            products.addAll(productResponse.products)
            productAdapter?.notifyDataSetChanged()
        }
    }

    class StringRequestErrorListener : Response.ErrorListener {
        override fun onErrorResponse(error: VolleyError?) {
            Log.e("tag", "$error")
        }

    }




}

