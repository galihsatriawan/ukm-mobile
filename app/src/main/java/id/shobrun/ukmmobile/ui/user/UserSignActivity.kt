package id.shobrun.ukmmobile.ui.user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import id.shobrun.ukmmobile.R
import id.shobrun.ukmmobile.ui.adapter.UserSignPagerAdapter

class UserSignActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_sign)

        val sectionsPagerAdapter =
            UserSignPagerAdapter(
                applicationContext,
                supportFragmentManager
            )
        val viewPager: ViewPager = findViewById(R.id.view_pager)

        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)
    }
}
