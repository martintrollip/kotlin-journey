package app.masterclass.kotlinmasterclassapplication.data

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import app.masterclass.kotlinmasterclassapplication.R
import app.masterclass.kotlinmasterclassapplication.model.Recipe
import com.android.volley.toolbox.ImageLoader

/**
 * @author Martin Trollip
 * @since 2019/06/11 19:04
 */
class RecipeAdapter(private val recipes: List<Recipe>, private val imageLoader: ImageLoader) :
    RecyclerView.Adapter<RecipeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): RecipeViewHolder {
        return RecipeViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_recipe, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return recipes.size
    }

    override fun onBindViewHolder(viewHolder: RecipeViewHolder, position: Int) {
        viewHolder.bind(recipes[position], imageLoader)
    }
}