package com.example.home_management_app.for_you_old

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.home_management_app.databinding.FragmentForYouOld1Binding
import com.example.home_management_app.databinding.FragmentForYouOldNewsRowBinding
import kotlinx.coroutines.*
import org.jsoup.Jsoup

class ForYouOldFragment1 : Fragment() {
    lateinit var binding : FragmentForYouOld1Binding
    // database 연결 필요
    //val newsData: ArrayList<ForYouOldNewsData> = ArrayList()

    lateinit var adapter: ForYouOldRVAdapter

    val scope = CoroutineScope(Dispatchers.IO)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentForYouOld1Binding.inflate(inflater, container, false)
        init()

        return binding.root
    }

    fun getnews() {
        scope.launch {
            adapter.newsList.clear()
            // 크롤링할 URL 설정
            val url = "https://news.naver.com/main/list.naver?mode=LS2D&mid=shm&sid1=103&sid2=241"

            // Jsoup을 사용하여 웹 사이트에서 HTML 문서 가져오기
            val document = Jsoup.connect(url).get()

            // 원하는 작업 수행 (예: 특정 클래스의 요소 추출)
            val articles = document.select("div.list_body.newsflash_body ul li")

            for (article in articles) {
                val title = article.select("dt:not(.photo) a").text()
                val news = article.select("span.writing").text()
                val link = article.select("dt:not(.photo) a").attr("href")
                // database 연결
                //newsData.add(ForYouOldNewsData(title, news, link))
                adapter.newsList.add(ForYouOldNewsData(title, news, link))
            }
            withContext(Dispatchers.Main) {
                adapter.notifyDataSetChanged()
            }
        }
    }

    fun init() {

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.VERTICAL, false)
        //binding.recyclerView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        adapter = ForYouOldRVAdapter(ArrayList<ForYouOldNewsData>())
        adapter.itemClickListener = object : ForYouOldRVAdapter.OnItemClickListener {
            override fun OnItemClick(data: ForYouOldNewsData, pos: Int, binding: FragmentForYouOldNewsRowBinding) {
                // 클릭한 뉴스의 링크를 열기
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(data.link))
                startActivity(intent)
            }
        }
        binding.recyclerView.adapter = adapter
        getnews()
    }
}
