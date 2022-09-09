package com.udacity

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.content_detail.*

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(toolbar)

        val fileName = intent.getStringExtra("fileName")
        file.text = fileName

        val statsv = intent.getStringExtra("status")
        textView4.text = statsv

//        scaler()
    }

//    private fun scaler() {
//        val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 4f)
//        val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 4f)
//        val animator = ObjectAnimator.ofPropertyValuesHolder(
//            stats, scaleX, scaleY)
//        animator.repeatCount = 1
//        animator.duration = 2000
//        animator.repeatMode = ObjectAnimator.REVERSE
//        animator.start()
//    }

    fun naveToMain(view: View){
        val intent = Intent(this, MainActivity::class.java)

        startActivity(intent)
    }

}
