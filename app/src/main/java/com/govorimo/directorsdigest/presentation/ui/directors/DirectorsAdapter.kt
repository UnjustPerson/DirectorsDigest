package com.govorimo.directorsdigest.presentation.ui.directors

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.govorimo.directorsdigest.R
import com.govorimo.directorsdigest.databinding.DirectorItemBinding
import com.govorimo.directorsdigest.persistence.models.Director

class DirectorAdapter(val context: Context): RecyclerView.Adapter<DirectorAdapter.DirectorViewHolder>() {

    var callbacks: DirectorsFragment.Callbacks? = null

    private val requestOptions = RequestOptions
        .placeholderOf(R.drawable.tommy)
        .transform(RoundedCorners(140))
        .error(R.drawable.tommy)


    var directors: List<Director> = listOf<Director>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DirectorViewHolder {
        val binding = DirectorItemBinding.inflate(context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
        return DirectorViewHolder(binding, requestOptions)
    }

    override fun onBindViewHolder(holder: DirectorViewHolder, position: Int) {
        val director = directors[position]
        holder.bind(director)

    }


    override fun getItemCount(): Int {
        return directors.size
    }


    inner class DirectorViewHolder(val binding: DirectorItemBinding, val requestOptions: RequestOptions):
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        private lateinit var director: Director


        fun bind(director: Director){
            this.director = director
            binding.directorName.text = director.name
            binding.directorBiography.text = director.biography
            Glide.with(binding.root)
                .setDefaultRequestOptions(requestOptions)
                .load(director.photo)
                .into(binding.directorImage)
            binding.root.setOnClickListener {
                callbacks = context as DirectorsFragment.Callbacks?
                callbacks?.onDirectorSelected(director)
            }

        }

        override fun onClick(v: View?) {
            callbacks?.onDirectorSelected(director)
        }


    }







}