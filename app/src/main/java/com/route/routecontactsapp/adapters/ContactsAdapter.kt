package com.route.routecontactsapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import com.route.routecontactsapp.R
import com.route.routecontactsapp.databinding.ItemContactBinding
import com.route.routecontactsapp.models.ContactDM

class ContactsAdapter(
    private var contactsList: MutableList<ContactDM>,
    private val onDeleteClick: (ContactDM, Int) -> Unit = { _, _ -> }
) : androidx.recyclerview.widget.RecyclerView.Adapter<ContactsAdapter.ContactViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ContactViewHolder {
        val binding = ItemContactBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ContactViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ContactViewHolder,
        position: Int
    ) {
        val currentContact = contactsList[position]
        holder.bindItem(currentContact)

        holder.itemBinding.buttonDelete.setOnClickListener {
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
            notifyItemRangeChanged(position, contactsList.size - position)
        }
    }

    fun addContact(contact: ContactDM) {
        contactsList.add(contact)
        notifyItemInserted(contactsList.size - 1)
    }

    class ContactViewHolder(val itemBinding: ItemContactBinding) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemBinding.root) {
        fun bindItem(contact: ContactDM) {
            itemBinding.textName.text = contact.name
            itemBinding.textPhone.text = contact.phoneNumber
            itemBinding.textEmail.text = contact.email ?: ""

            if (!contact.photoUri.isNullOrEmpty()) {
                itemBinding.imageProfile.setImageURI(contact.photoUri.toUri())
            } else {
                itemBinding.imageProfile.setImageResource(R.drawable.test_image)
            }
        }
    }
}
