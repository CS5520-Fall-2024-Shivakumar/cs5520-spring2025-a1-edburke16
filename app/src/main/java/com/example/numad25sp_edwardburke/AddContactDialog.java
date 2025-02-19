package com.example.numad25sp_edwardburke;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;

public class AddContactDialog extends Dialog {

    public interface OnContactAddedListener {
        void onContactAdded(Contact contact);
    }

    private OnContactAddedListener listener;
    private Contact contactToEdit; // Used for editing contacts
    private EditText editName, editPhone;
    private Button btnSave, btnCancel;

    public AddContactDialog(@NonNull Context context, OnContactAddedListener listener) {
        super(context);
        this.listener = listener;
        this.contactToEdit = null; // This means it's an "Add" operation
    }

    // Overloaded constructor for editing an existing contact
    public AddContactDialog(@NonNull Context context, OnContactAddedListener listener, Contact contact) {
        super(context);
        this.listener = listener;
        this.contactToEdit = contact; // This means it's an "Edit" operation
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_add_contact);

        editName = findViewById(R.id.edit_name);
        editPhone = findViewById(R.id.edit_phone);
        btnSave = findViewById(R.id.btn_save);
        btnCancel = findViewById(R.id.btn_cancel);

        if (contactToEdit != null) {
            // If editing, pre-fill fields with existing contact data
            editName.setText(contactToEdit.getName());
            editPhone.setText(contactToEdit.getPhoneNumber());
        }

        btnSave.setOnClickListener(view -> {
            String name = editName.getText().toString().trim();
            String phone = editPhone.getText().toString().trim();

            if (!name.isEmpty() && !phone.isEmpty()) {
                if (contactToEdit != null) {
                    contactToEdit = new Contact(name, phone); // Update contact
                } else {
                    contactToEdit = new Contact(name, phone); // Create new contact
                }
                listener.onContactAdded(contactToEdit);
                dismiss();
            } else {
                editName.setError("Required");
                editPhone.setError("Required");
            }
        });

        btnCancel.setOnClickListener(view -> dismiss());
    }
}
