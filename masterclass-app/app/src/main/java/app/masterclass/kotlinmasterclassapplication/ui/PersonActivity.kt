package app.masterclass.kotlinmasterclassapplication.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import app.masterclass.kotlinmasterclassapplication.R
import app.masterclass.kotlinmasterclassapplication.data.PersonAdapter
import app.masterclass.kotlinmasterclassapplication.model.Person
import kotlinx.android.synthetic.main.activity_another.*

class PersonActivity : AppCompatActivity() {

    private var adapter: PersonAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_another)

        val personList = ArrayList<Person>()
        val layoutManager = LinearLayoutManager(this)

        var adapter = PersonAdapter(personList)

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

        for (i in 0 until 10){
            personList.add(Person("Name $i", "Number $i", 0))
        }

        adapter.notifyDataSetChanged()
    }
}
