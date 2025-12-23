package com.example.ch11_jetpack

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.ch11_jetpack.databinding.ActivityMainBinding

/**
 * ViewPager2를 이용해 여러 Fragment를 스와이프 방식으로 전환하는 메인 액티비티.
 *
 * Toolbar, DrawerLayout, SearchView를 함께 사용하며
 * ViewPager2를 통해 Fragment 간 좌우 전환을 제공한다.
 */
class MainActivity : AppCompatActivity() {

    /**
     * DrawerLayout과 Toolbar를 연결하는 토글 버튼
     */
    lateinit var toggle: ActionBarDrawerToggle

    /**
     * ViewPager2에서 사용할 FragmentStateAdapter
     *
     * 각 프레그먼트를 스와이프 방식으로 전환
     */
    class MyFragmentPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

        /**
         * ViewPager2에 표시할 Fragment 목록
         */
        val fragments: List<Fragment>

        init {
            fragments = listOf(OneFragment(), TwoFragment(), ThreeFragment())
        }

        /**
         * 전체 Fragment 개수 반환
         */
        override fun getItemCount(): Int {
            return fragments.size
        }

        /**
         * position에 해당하는 Fragment를 생성
         *
         * @param position Fragment의 위치
         * @return 해당 위치에 표시할 Fragment
         */
        override fun createFragment(position: Int): Fragment {
            return fragments[position]
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        toggle = ActionBarDrawerToggle(
            this, binding.main, R.string.drawer_opened,
            R.string.drawer_colsed
        )
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toggle.syncState()

        val adapter = MyFragmentPagerAdapter(this)
        binding.viewpager.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)

        val menuItem = menu?.findItem(R.id.menu_search)
        val searchView = menuItem?.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.d("kkang", "search text: $query")
                return true
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}