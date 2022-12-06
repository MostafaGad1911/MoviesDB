package mostafagad.projects.movies.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import mostafagad.projects.movies.R
import mostafagad.projects.movies.databinding.MovieItemBinding
import mostafagad.projects.movies.domain.model.MovieModel
import mostafagad.projects.movies.ui.interfaces.MovieController

class MoviesAdapter(private val moviesList: ArrayList<MovieModel>, val movieController: MovieController? = null) :
    RecyclerView.Adapter<MoviesAdapter.MovieHolder>() {

    private lateinit var scaleAnim: Animation
    private var mLastPosition: Int = -1
    private lateinit var recyclerView: RecyclerView

    private val _movieController:MovieController? = movieController

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    class MovieHolder(private val marvelItemBinding: MovieItemBinding) : ViewHolder(marvelItemBinding.root) {
        fun bind(item:MovieModel){
            marvelItemBinding.movie = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val inflater = LayoutInflater.from(parent.context)
        val marvelCharacterBinding = MovieItemBinding.inflate(inflater , parent , false)
        return MovieHolder(marvelCharacterBinding)

    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        startAnimation(vew = holder.itemView, position = position)
        val itemWidth: Int = (recyclerView.layoutManager?.width!! - 62) / 2
        holder.itemView.layoutParams.width = itemWidth - 10

        holder.bind(item = moviesList[position])
        holder.itemView.setOnClickListener{
            _movieController?.getDetails(movieModel = moviesList[position])
        }

    }

    override fun getItemCount(): Int = moviesList.size

    private fun startAnimation(vew:View, position:Int){
        if (position > mLastPosition) {
            scaleAnim = AnimationUtils.loadAnimation(vew.context , R.anim.fall_down)
            vew.startAnimation(scaleAnim)
            mLastPosition = position
        }
    }

}