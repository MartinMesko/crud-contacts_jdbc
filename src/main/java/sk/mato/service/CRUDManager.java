package sk.mato.service;

import sk.mato.db.Contact;
import sk.mato.db.DBContactService;
import sk.mato.utils.InputUtils;

import java.util.List;
import java.util.Optional;

public class CRUDManager {

    private final DBContactService contactService;

    public CRUDManager() {
        this.contactService = new DBContactService();
    }

    public void printOptions() {
        System.out.println("Hello ,welcome to contact manager\n");
        while (true) {
            System.out.println("0. Get all contacts");
            System.out.println("1. Edit contact");
            System.out.println("2. Add contact");
            System.out.println("3. Delete contact");
            System.out.println("4. Search contact");
            System.out.println("5. Exit");

            final int choice = InputUtils.readInt();
            switch (choice) {
                case 0 -> printAllContacts();
                case 1 -> editContact();
                case 2 -> createContact();
                case 3 -> deleteContact();
                case 4 -> System.out.println("Not implemented yet");
                case 5 -> {
                    System.out.println("Good bye");
                    System.exit(0);
                }
                default -> System.out.println("Invalid choice");

            }
        }
    }

    private void printAllContacts() {
        final List<Contact> contacts = contactService.readAll();
        contacts.forEach(System.out::println);
    }

    private void createContact() {
        System.out.println("Enter contact name: ");
        final String name = InputUtils.readString();
        System.out.println("Enter contact email: ");
        final String email = InputUtils.readString();
        System.out.println("Enter contact phone: ");
        final String phone = InputUtils.readString();
        if (contactService.create(name, email, phone) > 0) {
            System.out.println("Contact created");
        }
        else {
            System.out.println("Could't create contact");
        }
    }

    private void deleteContact() {
        Contact contacts = loadContacts();
        if (contactService.delete(contacts.getId()) > 0) {
            System.out.println("Contact deleted");

        }
    }

    private void editContact() {
        List<Contact> contacts = contactService.readAll();

        for (int i = 0; i < contacts.size(); i++) {
            System.out.println(i + 1 + ") " + contacts.get(i));
        }
        System.out.println("Select a contact to update");
        int choice = InputUtils.readInt();
        if (choice == 0) {
            System.out.println("Invalid choice");
            return;
        }
        else if (choice < 1 || choice > contacts.size()) {
            System.out.println("Invalid choice");
        }
        final Optional<Contact> contactToEdit = editContactFromInput(contacts.get(choice - 1));
        if (contactToEdit.isPresent()) {
            if (contactService.edit(contactToEdit.get()) > 0) {
                System.out.println("Contact updated");
                return;
            }
        }
    }

    private Optional<Contact> editContactFromInput(Contact contact) {
        String name = contact.getName();
        String email = contact.getEmail();
        String phone = contact.getPhone();

        while (true) {
            System.out.println("0. Back");
            System.out.println("1. Edit name (" + name + ")");
            System.out.println("2. Edit email (" + email + ")");
            System.out.println("3. Edit phone (" + phone + ")");

            final int choice = InputUtils.readInt();
            switch (choice) {
                case 0 -> {
                    return Optional.empty();
                }
                case 1 -> {
                    System.out.println("Enter new name: ");
                    name = InputUtils.readString();
                    return Optional.of(new Contact(contact.getId(), name, email, phone));
                }
                case 2 -> {
                    System.out.println("Enter new email: ");
                    email = InputUtils.readString();
                    return Optional.of(new Contact(contact.getId(), name, email, phone));
                }
                case 3 -> {
                    System.out.println("Enter new phone: ");
                    phone = InputUtils.readString();
                    return Optional.of(new Contact(contact.getId(), name, email, phone));
                }
                default -> System.out.println("Invalid choice");

            }

        }
    }



    private Contact loadContacts() {
        List<Contact> contacts = contactService.readAll();

        for (int i = 0; i < contacts.size(); i++) {
            System.out.println(i + 1 + ") " + contacts.get(i));
        }
        System.out.println("Select a contact to delete");
        int choice = InputUtils.readInt();
        if (choice == 0) {
            System.out.println("Invalid choice");
            return null;
        }
        else if (choice < 1 || choice > contacts.size()) {
            System.out.println("Invalid choice");
        }
        return contacts.get(choice - 1);
    }
}
