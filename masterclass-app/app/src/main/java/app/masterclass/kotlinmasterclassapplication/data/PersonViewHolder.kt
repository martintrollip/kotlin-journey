package app.masterclass.kotlinmasterclassapplication.data

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import app.masterclass.kotlinmasterclassapplication.R
import app.masterclass.kotlinmasterclassapplication.model.Person

/**
 * @author Martin Trollip
 * @since 2019/06/03 19:14
 */
class PersonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(person: Person) {
        val name = itemView.findViewById(R.id.txtName) as TextView
        val number = itemView.findViewById(R.id.txtNumber) as TextView
        val picture = itemView.findViewById(R.id.imgProfilePic) as ImageView

        name.text = person.name
        number.text = person.number
        picture.setImageResource(person.picture)

        itemView.setOnClickListener {
            Toast.makeText(itemView.context, "$name $number", Toast.LENGTH_LONG).show()
        }

    }
}