package com.example.numad25sp_edwardburke;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import java.util.ArrayList;
import java.util.List;

public class Contacts extends AppCompatActivity implements ContactAdapter.OnContactClickListener{

    private RecyclerView recyclerView;
    private ContactAdapter contactAdapter;
    private List<Contact> contactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        contactList = new ArrayList<>();
        contactAdapter = new ContactAdapter(contactList, this);
        recyclerView.setAdapter(contactAdapter);

        FloatingActionButton fabAddContact = findViewById(R.id.fab_add_contact);
        fabAddContact.setOnClickListener(view -> showAddContactDialog());
    }

    private void showAddContactDialog() {
        AddContactDialog dialog = new AddContactDialog(this, contact -> {
            contactList.add(contact);
            contactAdapter.notifyDataSetChanged();

            Snackbar snackbar = Snackbar.make(recyclerView, "Contact added", Snackbar.LENGTH_LONG)
                    .setAction("UNDO", v -> {
                        contactList.remove(contact);
                        contactAdapter.notifyDataSetChanged();
                    });
            snackbar.show();
        });
        dialog.show();
    }

    @Override
    public void onContactClick(Contact contact) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + contact.getPhoneNumber()));
        startActivity(intent);
    }

    @Override
    public void onContactEdit(Contact contact, int position) {
        AddContactDialog dialog = new AddContactDialog(this, updatedContact -> {
            contactList.set(position, updatedContact);
            contactAdapter.notifyItemChanged(position);
            Toast.makeText(this, "Contact Updated", Toast.LENGTH_SHORT).show();
        }, contact);
        dialog.show();
    }

    @Override
    public void onContactDelete(int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Contact")
                .setMessage("Are you sure?")
                .setPositiveButton("Yes", (dialogInterface, i) -> {
                    contactList.remove(position);
                    contactAdapter.notifyItemRemoved(position);
                    Snackbar.make(recyclerView, "Contact deleted", Snackbar.LENGTH_SHORT).show();
                })
                .setNegativeButton("No", null)
                .show();
    }
}
