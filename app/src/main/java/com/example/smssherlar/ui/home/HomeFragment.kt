package com.example.smssherlar.ui.home

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.smssherlar.adapters.ViewPagerAdapter
import com.example.smssherlar.databinding.FragmentHomeBinding
import com.example.smssherlar.databinding.ItemTabBinding
import com.example.smssherlar.models.Category
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.FirebaseFirestore

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    lateinit var root:View
    lateinit var viewPagerAdapter: ViewPagerAdapter
    lateinit var listCategory:ArrayList<Category>
    lateinit var firebaseFirestore: FirebaseFirestore
    // This property is only valid between onCreateView and
    // onDestroyView.

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        root = binding.root
        listCategory = ArrayList()
        firebaseFirestore = FirebaseFirestore.getInstance()
        firebaseFirestore.collection("cateory").addSnapshotListener { value, error ->
            value!!.documentChanges.forEach { queryDocumentSnapshot->
                when(queryDocumentSnapshot.type){
                    DocumentChange.Type.ADDED->{
                        val category = queryDocumentSnapshot.document.toObject(Category::class.java)
                        listCategory.add(category)
                    }
                }
            }
            viewPagerAdapter = ViewPagerAdapter(listCategory,requireActivity())
            binding.viewPager.adapter = viewPagerAdapter
            TabLayoutMediator(binding.tabLayout,binding.viewPager){ tab, position->
                tab.text = listCategory[position].name_category
            }.attach()
            setTabs()
            binding.tabLayout.addOnTabSelectedListener(object:TabLayout.OnTabSelectedListener{
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    val customView = tab!!.customView
                    val itemTabBinding = ItemTabBinding.bind(customView!!)
                    var gradientDrawable = itemTabBinding.cons.background.mutate() as GradientDrawable
                    gradientDrawable.setColor(Color.WHITE)
                    itemTabBinding.nameCategory.setTextColor(Color.parseColor("#02092E"))
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                    val customView = tab!!.customView
                    val itemTabBinding = ItemTabBinding.bind(customView!!)
                    var gradientDrawable = itemTabBinding.cons.background.mutate() as GradientDrawable
                    gradientDrawable.setColor(Color.parseColor("#02092E"))
                    itemTabBinding.nameCategory.setTextColor(Color.WHITE)
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {

                }

            })
        }



//
//            .get()
//            .addOnCompleteListener {
//                if (it.isSuccessful){
//                    val result = it.result
//                    result!!.forEach {queryDocumentSnapshot->
//                       listCategory.add(queryDocumentSnapshot.toObject(Category::class.java))
//                    }
//
//                }
//            }





        return root
    }

    private fun setTabs() {
        val tabCount = binding!!.tabLayout.tabCount
        for (i in 0 until tabCount){
            var itemTabBinding = ItemTabBinding.inflate(LayoutInflater.from(root.context),null,false)
            val tabAt = binding!!.tabLayout.getTabAt(i)
            tabAt!!.customView = itemTabBinding.root
            itemTabBinding.nameCategory.text = listCategory[i].name_category
            if (i==0){
                var gradientDrawable = itemTabBinding.cons.background.mutate() as GradientDrawable
                gradientDrawable.setColor(Color.WHITE)
                itemTabBinding.nameCategory.setTextColor(Color.parseColor("#02092E"))
            }else{
                var gradientDrawable = itemTabBinding.cons.background.mutate() as GradientDrawable
                gradientDrawable.setColor(Color.parseColor("#02092E"))
                itemTabBinding.nameCategory.setTextColor(Color.WHITE)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewPagerAdapter = ViewPagerAdapter(listCategory,requireActivity())
        binding.viewPager.adapter = viewPagerAdapter
    }

}