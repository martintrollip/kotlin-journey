package app.masterclass.kotlinmasterclassapplication.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import app.masterclass.kotlinmasterclassapplication.R
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_volley.*
import org.json.JSONException
import org.json.JSONObject

class VolleyActivity : AppCompatActivity() {
    var volleyQueue: RequestQueue? = null
    val stringUrl = "https://magadistudio.com/complete-android-developer-course-source-files/string.html"
    val jsonArray = "https://jsonplaceholder.typicode.com/posts"
    val jsonObject = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/1.0_hour.geojson"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_volley)
        initVolley()
        getStringOverNetwork()
        getJsonArrayOverNetwork()
        getJsonObjectOverNetwork()
    }

    private fun initVolley() {
        volleyQueue = Volley.newRequestQueue(this)
    }

    fun getStringOverNetwork() {
        val stringRequest = StringRequest(Request.Method.GET, stringUrl, Response.Listener { response ->
            txtString.text = response
        }, Response.ErrorListener { error ->
            txtString.text = "Error ${error.toString()}"
        })
        volleyQueue!!.add(stringRequest)
    }

    fun getJsonArrayOverNetwork() {
        val jsonArrayRequest =
            JsonArrayRequest(Request.Method.GET, jsonArray, Response.Listener { response ->
                txtString.text = response.toString()
            }, Response.ErrorListener { error ->
                txtString.text = "Error ${error.toString()}"
            })

        volleyQueue!!.add(jsonArrayRequest)
    }

    fun getJsonObjectOverNetwork() {
        val jsonObjectRequest =
            JsonObjectRequest(Request.Method.GET, jsonObject, Response.Listener { response: JSONObject ->
                try {
                    txtString.text = response.getString("type")
                    txtString.text = response.getJSONArray("features").getJSONObject(0).getString("type")
                } catch (e: JSONException) {

                }
            }, Response.ErrorListener { error ->
                txtString.text = "Error ${error.toString()}"
            })

        volleyQueue!!.add(jsonObjectRequest)
    }
}
