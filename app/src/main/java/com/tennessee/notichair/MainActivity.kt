package com.tennessee.notichair

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.google.android.material.navigation.NavigationBarView
import com.tennessee.notichair.navigation.MainFragment
import com.tennessee.notichair.navigation.SensorViewFragment
import com.tennessee.notichair.navigation.UserFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), NavigationBarView.OnItemSelectedListener  {

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        when (p0.itemId){
            R.id.action_home -> {
                /* var mainFragment = MainFragment()
                 supportFragmentManager.beginTransaction().replace(R.id.main_content, mainFragment).commit()
                 return true*/
                val intent = Intent(this, youtubeview::class.java)
                startActivity(intent)
            }
            R.id.action_sensorview -> {
                /* var sensorViewFragment = SensorViewFragment()
                 supportFragmentManager.beginTransaction().replace(R.id.main_content, sensorViewFragment).commit()
                 return true*/
                val intent = Intent(this, BluetoothActivity::class.java)
                startActivity(intent)
            }
            R.id.action_mypage -> {
                var userFragment = UserFragment()
                supportFragmentManager.beginTransaction().replace(R.id.main_content, userFragment).commit()
                return true
            }
        }
        return false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottom_navigation.setOnItemSelectedListener(this)
    }
}

