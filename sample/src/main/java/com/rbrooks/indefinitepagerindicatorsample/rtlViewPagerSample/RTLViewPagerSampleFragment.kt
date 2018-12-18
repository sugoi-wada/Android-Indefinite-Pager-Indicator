package com.rbrooks.indefinitepagerindicatorsample.viewPagerSample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.rbrooks.indefinitepagerindicator.IndefinitePagerIndicator
import com.rbrooks.indefinitepagerindicatorsample.MainActivity
import com.rbrooks.indefinitepagerindicatorsample.R
import com.rbrooks.indefinitepagerindicatorsample.util.OnPagerNumberChangeListener

class RTLViewPagerSampleFragment : Fragment(), OnPagerNumberChangeListener, View.OnClickListener {
    private lateinit var viewPager: ViewPager

    private lateinit var pagerIndicator: IndefinitePagerIndicator
    private lateinit var pagerIndicatorVertical: IndefinitePagerIndicator
    private lateinit var previousButton: Button
    private lateinit var nextButton: Button
    private var pagerAdapter: ViewPagerAdapter? = null
    private var isVerticalEnabled = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_rtl_view_pager_sample, container, false)

        isVerticalEnabled = requireContext().getSharedPreferences(
            MainActivity.SHARED_PREFERENCES,
            AppCompatActivity.MODE_PRIVATE
        ).getBoolean(
            MainActivity.isVerticalIndicatorKeyPreference,
            false
        )

        bindViews(view)
        setupViews()

        return view
    }

    private fun bindViews(view: View) {
        viewPager = view.findViewById(R.id.viewpager)
        pagerIndicator = view.findViewById(R.id.viewpager_pager_indicator)
        pagerIndicatorVertical = view.findViewById(R.id.viewpager_pager_indicator_vertical)
        previousButton = view.findViewById(R.id.viewpager_previous_button)
        nextButton = view.findViewById(R.id.viewpager_next_button)
    }

    private fun setupViews() {
        pagerAdapter = ViewPagerAdapter(requireContext())
        viewPager.adapter = pagerAdapter
        if (isVerticalEnabled) {
            pagerIndicatorVertical.attachToViewPager(viewPager)
            pagerIndicatorVertical.visibility = View.VISIBLE
        } else {
            pagerIndicator.attachToViewPager(viewPager)
            pagerIndicator.visibility = View.VISIBLE
        }


        previousButton.setOnClickListener(this)
        nextButton.setOnClickListener(this)
    }

    override fun onPagerNumberChanged() {
        pagerAdapter?.notifyDataSetChanged()
    }

    override fun onClick(v: View?) {
        val adapter = viewPager.adapter ?: return
        when (v?.id) {
            R.id.viewpager_previous_button -> {
                if (viewPager.currentItem == 0) {
                    viewPager.currentItem = adapter.count - 1
                } else {
                    viewPager.currentItem = viewPager.currentItem - 1
                }
            }
            R.id.viewpager_next_button -> {
                if (viewPager.currentItem == adapter.count - 1) {
                    viewPager.currentItem = 0
                } else {
                    viewPager.currentItem = viewPager.currentItem + 1
                }
            }
        }
    }
}
