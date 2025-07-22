package com.route.routecontactsapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.route.routecontactsapp.adapters.ContactsAdapter
import com.route.routecontactsapp.databinding.ActivityMainBinding
import com.route.routecontactsapp.fragments.AddContactBottomSheet
import com.route.routecontactsapp.models.ContactDM

class MainActivity : AppCompatActivity(), AddContactBottomSheet.OnContactAddedListener {
    lateinit var binding: ActivityMainBinding
    private lateinit var contactsAdapter: ContactsAdapter
    private var contactsList = mutableListOf<ContactDM>()
    lateinit var contactsRecyclerView: androidx.recyclerview.widget.RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecyclerView()
        setupClickListeners()
    }

    private fun setupRecyclerView() {
        contactsAdapter = ContactsAdapter(contactsList) { contact, position ->
            contactsAdapter.removeContact(position)
            updateRecyclerViewVisibility()
        }

        contactsRecyclerView = binding.contactsRecyclerView
        contactsRecyclerView.setAdapter(contactsAdapter)

        updateRecyclerViewVisibility()
    }

    private fun setupClickListeners() {
        binding.addContactBtn.setOnClickListener {
            val addContactBottomSheet = AddContactBottomSheet()
            addContactBottomSheet.onContactAddedListener = this
            addContactBottomSheet.show(supportFragmentManager, "AddContactBottomSheet")
        }
    }

    private fun updateRecyclerViewVisibility() {
        if (contactsList.isEmpty()) {
            binding.contactsRecyclerView.visibility = android.view.View.GONE
            binding.emptyListView.visibility = android.view.View.VISIBLE
        } else {
            binding.contactsRecyclerView.visibility = android.view.View.VISIBLE
            binding.emptyListView.visibility = android.view.View.GONE
        }
    }

    override fun onContactAdded(contact: ContactDM) {
        contactsList.add(contact)
        contactsAdapter.notifyItemInserted(contactsList.size - 1)
        updateRecyclerViewVisibility()
    }

}
