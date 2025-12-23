package com.example.ch18_network

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ch18_network.databinding.FragmentRetrofitBinding
import com.example.ch18_network.model.PageListModel
import com.example.ch18_network.recycler.MyAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/*
HTTP 통신

Retrofit2
우리가 알려 준 인터페이스를 바탕으로 서비스를 만드므로 인터페이스에 선언한 함수를 그대로 포함
이 서비스의 함수를 호출하면 Call 객체를 반환하는데, 이 Call 객체의 enqueue() 함수를 호출하는 순간 통신을 수행

동작방식
1. 통신용 함수를 선언한 인터페이스를 작성
2. Retrofit에 인터페이스를 전달
3. Retrofit이 통신용 서비스 객체를 반환
4. 서비스의 통신용 함수를 호출한 수 Call객체를 반환
5. Call 객체의 enqueue() 함수를 호출하여 네트워크 통신을 수행
*/
class RetrofitFragment : Fragment() {
    companion object {
        private var TAG: String = "RetrofitFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentRetrofitBinding.inflate(inflater, container, false)

        val call: Call<PageListModel> = MyApplication.networkService.getList(
            MyApplication.QUERY,
            MyApplication.API_KEY,
            1,
            10
        )

        /**
         * enqueue() 실제 네트워크 요청
         * 비동기 네트워크 요청 -> 내부적으로 백그라운드 스레드에서 실행 -> 결과는 메인 스레드로 콜백
         */
        call?.enqueue(object : Callback<PageListModel> {
            /**
             * 성공 콜백
             * 서버 응답을 정상적으로 받았을 때 호출
             * HTTP 상태 코드 포함
             */
            override fun onResponse(call: Call<PageListModel>, response: Response<PageListModel>) {
                if (response.isSuccessful()) {
                    binding.retrofitRecyclerView.layoutManager = LinearLayoutManager(activity)
                    binding.retrofitRecyclerView.adapter =
                        MyAdapter(activity as Context, response.body()?.articles)
                }
            }

            override fun onFailure(call: Call<PageListModel>, t: Throwable) {

            }
        })
        return binding.root
    }
}