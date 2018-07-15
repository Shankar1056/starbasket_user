package apextechies.starbasket.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import apextechies.starbasket.R

class SplashScreen: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        startActivity(Intent(this@SplashScreen, MainActivity::class.java))
        finish()
    }
}