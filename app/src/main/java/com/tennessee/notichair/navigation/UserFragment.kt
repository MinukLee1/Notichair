package com.tennessee.notichair.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.tennessee.notichair.R
import kotlinx.android.synthetic.main.fragment_mypage.*

class UserFragment : Fragment(){

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = LayoutInflater.from(activity).inflate(R.layout.fragment_mypage,container,false)
        return view
        Glide.with(this).load(R.raw.chair3).into(logoimage3)
    }
}