package app.masterclass.kotlinmasterclassapplication.data

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import app.masterclass.kotlinmasterclassapplication.R
import app.masterclass.kotlinmasterclassapplication.model.Person

/**
 * @author Martin Trollip <martint@discovery.co.za>
 * @since 2019/06/03 19:06
 */
class PersonAdapter(private val names: List<Person>) :
    RecyclerView.Adapter<PersonViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): PersonViewHolder {
        return PersonViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_list_item, parent,false)
        )
    }

    override fun getItemCount(): Int {
        return names.size
    }

    override fun onBindViewHolder(viewHolder: PersonViewHolder, position: Int) {
        viewHolder.bind(names[position])
    }
}