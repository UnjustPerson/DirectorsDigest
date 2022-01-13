package com.govorimo.directorsdigest.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.govorimo.directorsdigest.R
import com.govorimo.directorsdigest.persistence.models.Director
import com.govorimo.directorsdigest.persistence.models.Film
import com.govorimo.directorsdigest.presentation.ui.directors.DirectorsFragment
import com.govorimo.directorsdigest.presentation.ui.filmdetail.FilmDetailFragment
import com.govorimo.directorsdigest.presentation.ui.filmography.FilmographyFragment
import com.govorimo.directorsdigest.presentation.ui.films.FilmsFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), DirectorsFragment.Callbacks, FilmographyFragment.Callbacks {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
        if (currentFragment == null) {
            val fragment = DirectorsFragment()
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, fragment)
                .commit()
        }
        bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.directors ->setCurrentFragment(DirectorsFragment())
                R.id.films ->setCurrentFragment(FilmsFragment())
            }
            true
        }

    }

    private fun setCurrentFragment(fragment: Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
        }

    override fun onDirectorSelected(director: Director) {
        val fragment = FilmographyFragment.newInstance(director.name)
        Log.d("natovolimte", "onDirectorSelected: ${director.name}")
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
        }

    override fun onFilmSelected(film: Film) {
        val fragment = FilmDetailFragment.newInstance(film)
        Log.d("natovolimte", "onFilmSelected: ${film.film_name}")
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }


}