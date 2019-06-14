package app.masterclass.kotlinmasterclassapplication.data

import android.content.Intent
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import android.widget.Toast
import app.masterclass.kotlinmasterclassapplication.R
import app.masterclass.kotlinmasterclassapplication.model.Recipe
import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.NetworkImageView

/**
 * @author Martin Trollip ***REMOVED***
 * @since 2019/06/03 19:14
 */
class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(recipe: Recipe, imageLoader: ImageLoader) {
        val name = itemView.findViewById(R.id.txtName) as TextView
        val description = itemView.findViewById(R.id.txtDescription) as TextView
        val picture = itemView.findViewById(R.id.imgFood) as NetworkImageView

        name.text = recipe.title
        description.text = recipe.ingredients
        picture.setImageUrl(recipe.thumbnail, imageLoader)


        itemView.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(recipe.href)
            itemView.context.startActivity(intent)
        }

    }
}