package org.hedgehog.testapp.rest.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.widget.Toast
import org.hedgehog.testapp.R
import org.hedgehog.testapp.rest.fragments.CardsFragment
import org.hedgehog.testapp.rest.fragments.ContactsFragment
import android.net.ConnectivityManager


class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var cardsFragment: CardsFragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigationView = findViewById(R.id.bottom_navigation_view)

        val connManager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager

        if (connManager.activeNetworkInfo == null) {
            supportFragmentManager.beginTransaction().replace(R.id.my_container, ContactsFragment()).commit()
            bottomNavigationView.selectedItemId = R.id.contacts_menu_item
            Toast.makeText(this, getString(R.string.internet_warning), Toast.LENGTH_SHORT).show()
        } else {

            if (savedInstanceState == null) {
                cardsFragment = CardsFragment()
                supportFragmentManager.beginTransaction().replace(R.id.my_container, cardsFragment).commit()
            }

            bottomNavigationView.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.contacts_menu_item -> {
                        if (getVisibleFragment() !is ContactsFragment) {
                            supportFragmentManager.beginTransaction().replace(R.id.my_container, ContactsFragment()).commit()
                        }
                        true
                    }
                    R.id.cards_menu_item -> {
                        if (getVisibleFragment() !is CardsFragment) {
                            supportFragmentManager.beginTransaction().replace(R.id.my_container, CardsFragment()).commit()
                        }
                        true
                    }
                    else -> false
                }
            })
        }
    }

    private fun getVisibleFragment(): Fragment? {
        val fragments = supportFragmentManager.fragments
        if (fragments != null) {
            for (fragment in fragments) {
                if (fragment != null && fragment.isVisible)
                    return fragment
            }
        }
        return null
    }

}
