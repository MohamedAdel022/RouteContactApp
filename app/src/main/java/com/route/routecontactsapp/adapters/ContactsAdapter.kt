package com.route.routecontactsapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.net.toUri
import com.route.routecontactsapp.R
import com.route.routecontactsapp.models.ContactDM

class ContactsAdapter(
    private var contactsList: MutableList<ContactDM>,
    private val onDeleteClick: (ContactDM, Int) -> Unit = { _, _ -> }
) : androidx.recyclerview.widget.RecyclerView.Adapter<ContactsAdapter.ContactViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ContactViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_contact, parent, false)
        return ContactViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: ContactViewHolder,
        position: Int
    ) {
        val currentContact = contactsList[position]
        holder.contactName.text = currentContact.name
        holder.contactPhone.text = currentContact.phoneNumber
        holder.contactEmail.text = currentContact.email


        if (!currentContact.photoUri.isNullOrEmpty()) {
            holder.contactImage.setImageURI(currentContact.photoUri.toUri())
        } else {

            holder.contactImage.setImageResource(R.drawable.test_image)
        }

        holder.deleteButton.setOnClickListener {
            onDeleteClick(currentContact, position)
        }
    }

    override fun getItemCount(): Int {
        return contactsList.size
    }

    fun removeContact(position: Int) {
        if (position >= 0 && position < contactsList.size) {
            contactsList.removeAt(position)
            notifyItemRemoved(position)
            // Notify that the range of items after the removed position has changed
            notifyItemRangeChanged(position, contactsList.size - position)
        }
    }

    fun addContact(contact: ContactDM) {
        contactsList.add(contact)
        notifyItemInserted(contactsList.size - 1)
    }


    class ContactViewHolder(itemView: android.view.View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
        val contactName: android.widget.TextView = itemView.findViewById(R.id.textName)
        val contactPhone: android.widget.TextView = itemView.findViewById(R.id.textPhone)
        val contactEmail: android.widget.TextView = itemView.findViewById(R.id.textEmail)
        val contactImage: ImageView = itemView.findViewById(R.id.imageProfile)
        val deleteButton: com.google.android.material.button.MaterialButton = itemView.findViewById(R.id.buttonDelete)
    }
}
