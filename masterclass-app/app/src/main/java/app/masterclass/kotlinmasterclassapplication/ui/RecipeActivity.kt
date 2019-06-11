package app.masterclass.kotlinmasterclassapplication.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import app.masterclass.kotlinmasterclassapplication.R
import app.masterclass.kotlinmasterclassapplication.cache.BitmapLruCache
import app.masterclass.kotlinmasterclassapplication.data.RecipeAdapter
import app.masterclass.kotlinmasterclassapplication.model.Recipe
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_another.*
import org.json.JSONException
import org.json.JSONObject
import com.android.volley.RequestQueue
import com.android.volley.toolbox.ImageLoader

//A nice place to find public APIs https://github.com/public-apis/public-apis
class RecipeActivity : AppCompatActivity() {
    private var requestQueue: RequestQueue? = null
    private var imageLoader: ImageLoader? = null
    private val recipeUrl = "http://www.recipepuppy.com/api/?i=onions,garlic&q=omelet&p=1"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe)
        getJsonObjectOverNetwork()
    }

    fun getJsonObjectOverNetwork() {
        val jsonObjectRequest =
            JsonObjectRequest(Request.Method.GET, recipeUrl, Response.Listener { response: JSONObject ->
                try {
                    //Parse the data
                    val recipes = ArrayList<Recipe>()
                    val results = response.getJSONArray("results")
                    for (i in 0 until results.length()) {
                        val recipe = results.getJSONObject(i)
                        recipes.add(
                            Recipe(
                                recipe.getString("title"),
                                recipe.getString("href"),
                                recipe.getString("ingredients"),
                                recipe.getString("thumbnail")
                            )
                        )
                    }

                    //Setup the views
                    val layoutManager = LinearLayoutManager(this)
                    val adapter = RecipeAdapter(recipes, getImageLoader())

                    recyclerView.layoutManager = layoutManager
                    recyclerView.adapter = adapter

                    adapter.notifyDataSetChanged()
                } catch (e: JSONException) {

                }
            }, Response.ErrorListener { error ->
                Toast.makeText(this, error.toString(), Toast.LENGTH_LONG).show()
            })

        getRequestQueue().add(jsonObjectRequest)
    }

    fun getRequestQueue(): RequestQueue {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(applicationContext)
        }
        return this.requestQueue!!
    }

    fun getImageLoader(): ImageLoader {
        getRequestQueue()
        if (imageLoader == null) {
            imageLoader = ImageLoader(this.requestQueue, BitmapLruCache())
        }

        return this.imageLoader!!
    }
}
