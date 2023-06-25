package org.jash.sportsnews.adapter

import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import org.jash.sportsnews.fragments.BlankFragment
import org.jash.sportsnews.model.Category

class PageAdapter(fragmentManager: FragmentManager):FragmentPagerAdapter(fragmentManager) {
    var data:List<Category>? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    override fun getCount(): Int = data?.size ?: 0
    override fun getItem(position: Int): Fragment = BlankFragment.newInstance(data?.get(position)?.id ?: 0)
    override fun getPageTitle(position: Int): CharSequence? = data?.get(position)?.name

}