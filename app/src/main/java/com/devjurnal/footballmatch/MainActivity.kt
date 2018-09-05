package com.devjurnal.footballmatch

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.devjurnal.footballmatch.match.NextMatchFragment
import com.devjurnal.footballmatch.match.PrevMatchFragment
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.yesButton

class MainActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_prev -> {
                setJudul("Prev Match")
                setFragment(PrevMatchFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_next -> {
                setJudul("Next Match")
                setFragment(NextMatchFragment())
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        setFragment(PrevMatchFragment())
        setJudul("Prev Match")
    }

    override fun onBackPressed() {
//        super.onBackPressed()
        // MAINACTIVITY TODO DONE : Handle on Backpress
        alert("Apakah Anda Inging keluar dari aplikasi?") {
            title = getString(R.string.perhatian)
            yesButton { finish() }      // jika user menekan tombol Yes
            noButton {  }               // jika user menekan tombol NO
        }.show()
    }
    //MAIN (1) TODO setJudul
    fun setJudul(judul : String){
        supportActionBar!!.setTitle(judul)
    }

    //MAIN (2) TODO setFragment
    fun setFragment(fragmentGoal : Fragment) {
        //get functions can be called as instance varibales
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        //Otherwise same as normal java here
        fragmentTransaction.replace(R.id.container, fragmentGoal)
        fragmentTransaction.commit()
    }
}
