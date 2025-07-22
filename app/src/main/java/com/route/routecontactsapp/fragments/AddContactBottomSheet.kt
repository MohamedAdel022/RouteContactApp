package com.route.routecontactsapp.fragments

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.doOnTextChanged
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.route.routecontactsapp.R
import com.route.routecontactsapp.databinding.FragmentAddContactBottomSheetBinding
import com.route.routecontactsapp.models.ContactDM

class AddContactBottomSheet : BottomSheetDialogFragment() {
    private var _binding: FragmentAddContactBottomSheetBinding? = null
    private val binding get() = _binding!!
    private var selectedImageUri: Uri? = null


    private val imagePickerLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            selectedImageUri = it
            binding.addProfileImage.setImageURI(it)
        }
    }

    interface OnContactAddedListener {
        fun onContactAdded(contact: ContactDM)
    }

    var onContactAddedListener: OnContactAddedListener? = null

    override fun getTheme(): Int {
        return R.style.BottomSheetDialogTheme
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddContactBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.etUserName.doOnTextChanged { text, _, _, _ ->
            binding.userNameTextView.text = text
        }
        binding.etPhone.doOnTextChanged { text, _, _, _ ->
            binding.phoneTextView.text = text
        }
        binding.etEmail.doOnTextChanged { text, _, _, _ ->
            binding.emailTextView.text = text
        }
        binding.addProfileImage.setOnClickListener {
            imagePickerLauncher.launch("image/*")
        }
        binding.btnEnterUser.setOnClickListener {
            addContact()
        }
    }

    private fun addContact() {
        val name = binding.etUserName.text.toString().trim()
        val phone = binding.etPhone.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()

        if (name.isNotEmpty() && phone.isNotEmpty()) {
            val contact = ContactDM(
                name = name,
                phoneNumber = phone,
                email = email.ifEmpty { null },
                photoUri = selectedImageUri?.toString()
            )
            onContactAddedListener?.onContactAdded(contact)
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}