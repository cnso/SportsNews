package org.jash.sportsnews.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import org.jash.sportsnews.fragments.CategoryFragment
import org.jash.sportsnews.fragments.TopFragment
import org.jash.mylibrary.model.Category

class PageAdapter(fragmentManager: FragmentManager):FragmentPagerAdapter(fragmentManager) {
    var data:List<Category>? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    override fun getCount(): Int = (data?.size  ?: 0) + 2
    override fun getItem(position: Int): Fragment = when(position) {
        0 -> TopFragment.newInstance("tophot")
        1 -> TopFragment.newInstance("topnew")
        else -> CategoryFragment.newInstance(data?.get(position - 2)?.id ?: 0)
    }
    override fun getPageTitle(position: Int): CharSequence? = when(position) {
        0 -> "热门"
        1 -> "最新"
        else -> data?.get(position - 2)?.name
    }

}