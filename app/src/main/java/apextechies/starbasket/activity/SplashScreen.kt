package apextechies.starbasket.activity

import android.animation.Animator
import android.animation.ValueAnimator
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.animation.AnimationUtils
import apextechies.starbasket.R
import kotlinx.android.synthetic.main.activity_splash.*

class SplashScreen: AppCompatActivity() {
    private val RC_AUTH = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window
                .setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_splash)
        overridePendingTransition(R.anim.left_in, R.anim.left_out)
        /*startActivity(Intent(this@SplashScreen, MainActivity::class.java))
        finish()*/

        val leftRight = AnimationUtils.loadAnimation(this, R.anim.left_to_middle)
        val cart = findViewById<View>(R.id.iv_cart)
        cart.startAnimation(leftRight)

        val cloud1 = AnimationUtils.loadAnimation(this, R.anim.cloud_anim1)
        findViewById<View>(R.id.iv_cloud1).startAnimation(cloud1)

        val cloud2 = AnimationUtils.loadAnimation(this, R.anim.cloud_anim2)
        findViewById<View>(R.id.iv_cloud2).startAnimation(cloud2)

        Handler().postDelayed({
            val animator = ValueAnimator.ofFloat(0f, -1f)
            animator.repeatCount = 0
            animator.duration = 2000
            animator.addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator) {

                }

                override fun onAnimationEnd(animation: Animator) {
                    startActivity(Intent(this@SplashScreen, MainActivity::class.java))
                    finish()
                }
                /*
                        if (!isFinishing()) {
                            if (new Preference(MainActivity.this).isLoggedIn()) {
                                startActivity(new Intent(MainActivity.this, HomeActivity.class));
                                finish();
                            } else {
                                Intent intent = new Intent(MainActivity.this, AuthActivity.class);
                                startActivityForResult(intent, RC_AUTH);
                            }
                        }
                    }*/

                override fun onAnimationCancel(animation: Animator) {

                }

                override fun onAnimationRepeat(animation: Animator) {

                }
            })
            animator.addUpdateListener { animation ->
                val progress = animation.animatedValue as Float
                val width = iv_building1.width.toFloat()
                val translationX = width * progress
                iv_building1.translationX = translationX
                iv_building2.translationX = translationX + width
            }
            animator.start()
        }, 1000)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_AUTH && resultCode == Activity.RESULT_OK) {
            // register FCM token in server database
            //startService(Intent(this, RegistrationIntentService::class.java))

            // authentication successful, goto HomeActivity
            startActivity(Intent(this, MainActivity::class.java))

        }

        finish()
    }
}