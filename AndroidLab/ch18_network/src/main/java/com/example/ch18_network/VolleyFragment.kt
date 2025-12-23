package com.example.ch18_network

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.ch18_network.databinding.FragmentVolleyBinding
import com.example.ch18_network.model.ItemModel
import com.example.ch18_network.recycler.MyAdapter
import org.json.JSONObject

/*
HTTP 통신

Volley의 핵심 클래스
RequestQueue : 서버 요청자
XXXRequest : XXX 타입의 결과를 받는 요청 정보

RequestQueue : 서버에 요청을 보내는 역할을 하며 이때 서버 URL과 결과를 가져오는 콜백 등
다양한 정보는 XXXRequest 객체에 담아서 전송
서버로부터 가져온 결과가 문자열이면 StringRequest를 이용하는 것처럼 데이터 타입에 따라 
ImageRequest, JsonObject Request, JsonArrayRequest등을 이용
*/

class VolleyFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentVolleyBinding.inflate(inflater, container, false)

        //add...................
        val url = MyApplication.BASE_URL + "/v2/everything?q=" +
                "${MyApplication.QUERY}&apiKey=" +
                "${MyApplication.API_KEY}&page=1&pageSize=5"

        val queue = Volley.newRequestQueue(activity)

        val jsonRequest = object : JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            Response.Listener<JSONObject> { response ->
                val jsonArray = response.getJSONArray("articles")
                val mutableList = mutableListOf<ItemModel>()
                for (i in 0 until jsonArray.length()) {
                    ItemModel().run {
                        val article = jsonArray.getJSONObject(i)
                        author = article.getString("author")
                        title = article.getString("title")
                        description = article.getString("description")
                        urlToImage = article.getString("urlToImage")
                        publishedAt = article.getString("publishedAt")
                        mutableList.add(this)
                    }
                }
                binding.volleyRecyclerView.layoutManager = LinearLayoutManager(activity)
                binding.volleyRecyclerView.adapter = MyAdapter(activity as Context, mutableList)
            },
            Response.ErrorListener { error ->
                println("error..... $error")
            }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                val map = mutableMapOf<String, String>(
                    "User-agent" to MyApplication.USER_AGENT
                )
                return map
            }
        }

        queue.add(jsonRequest)

        return binding.root
    }

}