package com.example.ch11_jetpack

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ch11_jetpack.databinding.FragmentOneBinding
import com.example.ch11_jetpack.databinding.ItemRecyclerviewBinding

/*
 * 리사이클러뷰
 * 일반적으로 앱을 사용하다 보면 여러가지 항목을 나열하는 목록 화면이 많다는 것을 알 수 있음.
 * 리사이클러뷰는 이러한 목록 화면을 만들 때 사용
 * 리사이클러뷰는 목록을 만드는데 RecyclerView 클래스만으로는 화면에 아무것도 출력되지 않음. 그러므로 다음과 같은 구성요소를 이용해야 함
 * ViewHolder(필수) = 항목에 필요한 뷰 객체를 가짐
 * Adapter(필수) = 항목을 구성
 * LayoutManager(필수) = 항목을 배치
 * ItemDecoration(옵션) = 항목을 꾸밈
 */

/**
 * 뷰 홀더 준비
 * 각 항목에 해당하는 뷰 객체를 가지는 뷰 홀더는 RecyclerView.ViewHolder를 상속받아 작성
 *
 * @property binding 리사이클러뷰 아이템 레이아웃
 */
class MyViewHolder(val binding: ItemRecyclerviewBinding) : RecyclerView.ViewHolder(binding.root)

/**
 * 어댑터 준비
 * 어탭터는 뷰 홀더의 뷰에 데이터를 출력해 각 항목을 만들어 주는 역할을 함
 * 리사이클러뷰를 위한 어댑터는 RecyclerView.Adapter를 상속받아 작성
 *
 * @property datas 각 아이템에 표시할 문자열 목록
 */
class MyAdapter(val datas: MutableList<String>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    /**
     * 항목 개수를 판단하려고 자동 호출됨
     *
     * @return 항목 개수
     */
    override fun getItemCount(): Int {
        return datas.size
    }

    /**
     * 새로운 ViewHolder를 생성
     *
     * RecyclerVIew가 화면에 표시할 아이템 뷰가 필요할 떄 호출되며,
     * item 레이아웃을 inflate하여 viewHolder를 생성해 반환
     *
     * @param parent ViewHolder에 포함될 아이템 뷰의 부모 ViewGroup
     * @param viewType 아이템 뷰 타임 (여러 타입을 사용하는 경우 구분 값)
     * @return 생성된 RecyclerView.ViewHolder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(
            ItemRecyclerviewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    /**
     * ViewHolder에 데이터를 바인딩한다
     *
     * 재사용되는 ViewHodler에 position에 해당하는 데이터를 설정하여
     * 화면에 올바른 아이템이 표시되도록 한다.
     *
     * @param holder 데이터를 표시할 viewHolder
     * @param position 바인딩할 데이터의 위치
     */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as MyViewHolder).binding
        binding.itemData.text = datas[position]
    }
}

class MyDecoration(val context: Context) : RecyclerView.ItemDecoration() {
    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
        val width = parent.width
        val height = parent.height

        val dr = ResourcesCompat.getDrawable(context.resources, R.drawable.kbo, null)
        val drWidth = dr?.intrinsicWidth
        val drHeight = dr?.intrinsicHeight

        val left = width / 2 - drWidth?.div(2) as Int
        val top = height / 2 - drHeight?.div(2) as Int

        c.drawBitmap(
            BitmapFactory.decodeResource(context.resources, R.drawable.kbo),
            left.toFloat(),
            top.toFloat(),
            null
        )
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val index = parent.getChildAdapterPosition(view) + 1

        if (index % 3 == 0)
            outRect.set(10, 10, 10, 60)
        else
            outRect.set(10, 10, 10, 0)

        view.setBackgroundColor(Color.parseColor("#28A0FF"))
        ViewCompat.setElevation(view, 20.0f)
    }
}

/**
 * 텍스튜 뷰나 버튼처럼 액티비티 화면을 구성하는 뷰인데, 그 자체만으로는 화면에 아무것도 출력되지 않음.
 * 프래그먼트가 다른 뷰와 다른 점은 액티비티처럼 동작한다는 것. 즉 액티비티에 작성할 수 있는 모든 코드는 프래그먼트에도 사용 할 수 있음
 * 대표적인 예로 탭과 뷰 페이저 화면을 들 수 있음.
 * 탭과 뷰 페이저 모두 여러 화면을 제공하는 것이 목적인데, 이를 모두 하나의 클래스에 구현하기에는 부담스러우므로 탭과 뷰 페이저가 제공하는 한 화면을 하나의 프래그먼트로 개발
 */

class OneFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentOneBinding.inflate(inflater, container, false)

        val datas = mutableListOf<String>()
        for (i in 1..9) {
            datas.add("Item $i")
        }

        val layoutManager = LinearLayoutManager(activity)
        binding.recyclerView.layoutManager = layoutManager
        val adapter = MyAdapter(datas)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.addItemDecoration(MyDecoration(activity as Context))

        return binding.root
    }
}