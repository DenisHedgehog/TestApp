package org.hedgehog.testapp.rest.adapters

import android.support.v4.app.FragmentManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import org.hedgehog.testapp.R
import org.hedgehog.testapp.rest.models.UsersResponse

/**
 * Created by hedgehog on 06.10.17.
 */
class UsersRecyclerViewAdapter(val support: FragmentManager, val users: List<UsersResponse>) : RecyclerView.Adapter<UsersRecyclerViewAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.userName.text = users[position].name
        holder.userUsername.text = "@" + users[position].username
        holder.userEmail.text = "Электронная почта: " + users[position].email
        holder.userAddress.text = users[position].address.toString()
        holder.userPhone.text = "Телефон: " + users[position].phone
        holder.userWebsite.text = "Сайт: " + users[position].website
        holder.userCompany.text = users[position].company.toString()

    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent!!.context).inflate(R.layout.user_item, parent, false))
    }

    override fun getItemCount(): Int {
        return users.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var userName: TextView
        var userUsername: TextView
        var userEmail: TextView
        var userAddress: TextView
        var userPhone: TextView
        var userWebsite: TextView
        var userCompany: TextView

        init {
            userName = view.findViewById(R.id.user_name)
            userUsername = view.findViewById(R.id.user_username)
            userEmail = view.findViewById(R.id.user_email)
            userAddress = view.findViewById(R.id.user_address)
            userPhone = view.findViewById(R.id.user_phone)
            userWebsite = view.findViewById(R.id.user_website)
            userCompany = view.findViewById(R.id.user_company)
        }

    }

}