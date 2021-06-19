package com.example.smssherlar.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.smssherlar.fragments.ViewPagerFragment
import com.example.smssherlar.models.Category

class ViewPagerAdapter(var listImages:List<Category>,fragmentActivity: FragmentActivity):FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return listImages.size
    }

    override fun createFragment(position: Int): Fragment {
        return ViewPagerFragment.newInstance(position,listImages[position])
    }
}