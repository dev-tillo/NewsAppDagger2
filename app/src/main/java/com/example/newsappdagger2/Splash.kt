package com.example.newsappdagger2

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.newsappdagger2.fragments.BasicActivity

class Splash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        supportActionBar?.hide()

        Handler().postDelayed({
            if (onBoardingFinished()!!) {
                startActivity(Intent(this@Splash, BasicActivity::class.java))
                finish()
            } else {
                val intent = Intent(this@Splash, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }, 500)
    }

    private fun onBoardingFinished(): Boolean? {
        val sharedpref = this.getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        return sharedpref?.getBoolean("Finished", false)
    }
}