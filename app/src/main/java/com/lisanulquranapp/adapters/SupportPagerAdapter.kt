package com.lisanulquranapp.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

class SupportPagerAdapter(fragmentManager: FragmentManager, private var fragments: List<Fragment>,
                          private var tabTitles: List<String>)
    : FragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment = fragments[position]

    override fun getPageTitle(position: Int): CharSequence = tabTitles[position]

    override fun getCount(): Int = fragments.size
}