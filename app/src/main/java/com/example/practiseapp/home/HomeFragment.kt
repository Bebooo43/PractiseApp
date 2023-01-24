package com.example.practiseapp.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import coil.compose.rememberAsyncImagePainter
import com.example.practiseapp.databinding.FragmentHomeBinding
import com.example.practiseapp.models.MovieItem

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.rv.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = MovieAdapter(listOf())
        }
        return binding.root
//        return ComposeView(requireContext()).also {
//            it.setContent {
//                MoviesScreen(viewModel)
//            }
//        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.movies.observe(viewLifecycleOwner){
            it?.also {
                (binding.rv.adapter as? MovieAdapter)?.apply {
                    setMovies(it)
                }
            }
        }
        viewModel.errorMessage.observe(viewLifecycleOwner){
            it?.also {
                Toast.makeText(view.context, it, Toast.LENGTH_LONG).show()
            }
        }
        viewModel.fetchMovies()
    }

    @Composable
    fun MoviesScreen(viewModel: HomeViewModel){
        Surface (color = MaterialTheme.colors.background){
            val movies = viewModel.movies.observeAsState()
            MoviesList(
                modifier = Modifier.fillMaxWidth(),
                movies = movies.value ?: listOf()
            )
        }
    }

    @Composable
    fun MoviesList(
        modifier: Modifier,
        movies: List<MovieItem>,
    ) {
        LazyColumn(
            modifier = modifier,
            contentPadding = PaddingValues(15.dp,15.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(movies){
                MovieItemView(modifier, it)
            }
        }

    }

    @Composable
    fun MovieItemView(
        modifier: Modifier,
        movie: MovieItem
    ) {
        Card(modifier = modifier) {
            Column(modifier = modifier) {
                Image(painter = rememberAsyncImagePainter(movie.imageUrl), contentDescription = null,
                    modifier = Modifier.size(128.dp), contentScale = ContentScale.Crop)
                Text(movie.name?:"")
            }
        }
    }

}