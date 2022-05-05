package com.example.dtandroid.ui

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toDrawable
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.dtandroid.R
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var appBarConfig: AppBarConfiguration

    private val url =
        "https://image.winudf.com/v2/image/Y29tLmZhbmFydHdhbGxwYXBlcnMud2FsbHBhcGVyc2Zvcmpvam9faWNvbl8xNTI1Mjg3ODYzXzA5Ng/icon.png?w=340&fakeurl=1"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        setUpNavigation()
    }

    private fun setUpNavigation() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        navController = navHostFragment.findNavController()

        appBarConfig = AppBarConfiguration(navController.graph, drawerLayout)
        setupActionBarWithNavController(navController, appBarConfig)
        navView.setupWithNavController(navController)

        val headerView = navView.inflateHeaderView(R.layout.nav_header)
        val imageView = headerView.findViewById<ImageView>(R.id.avatar)
        Glide.with(this)
            .load(url)
            .placeholder(R.raw.placeholder.toDrawable())
            .error(R.raw.not_found.toDrawable())
            .apply(RequestOptions.bitmapTransform(RoundedCorners(100)))
            .override(180, 180)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(imageView)
    }


    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}