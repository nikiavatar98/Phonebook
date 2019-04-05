package phonebook.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.thymeleaf.model.IComment;
import phonebook.entity.Contact;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ContactController
{
    private List<Contact> contacts;
    private String errorMsg=null;
    private Contact errorContactReturned;

    public ContactController()
    {
        this.contacts = new ArrayList<>();

        //Default values
        addContact(new Contact(0, "Test1", "123456789"));
    }

    @GetMapping("/")
    public String index(Model model)
    {
        if(errorMsg!=null)
        {
            model.addAttribute("errorMsg", errorMsg);
            errorMsg=null;
            model.addAttribute("editContact", errorContactReturned);
        }
        model.addAttribute("contacts", this.contacts);
        model.addAttribute("type", "new");
        return "index";
    }

    private void addContact(Contact contact)
    {
        this.contacts.add(new Contact(this.contacts.size()+1, contact.getName(), contact.getNumber()));
    }

    @PostMapping("/")
    public String add(Contact contact)
    {
        if(contact.getName().equals(""))
        {
            errorMsg = "The field Name cannot be empty.";
            errorContactReturned = contact;
            return "redirect:/";
        }
        if(contact.getNumber().equals(""))
        {
            errorMsg = "The field Number cannot be empty.";
            errorContactReturned = contact;
            return "redirect:/";
        }
        addContact(contact);
        return "redirect:/";
    }

    @PostMapping("/delete")
    public String delete(Integer index)
    {
        contacts.remove(index-1);
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String editPage(Model model, @PathVariable int id)
    {
        if(errorMsg!=null) {model.addAttribute("errorMsg", errorMsg); errorMsg=null;}
        model.addAttribute("contacts", this.contacts);
        try
        {
            Contact contact = this.contacts.get(id-1);
            model.addAttribute("editContact", contact);
            model.addAttribute("type", "edit");
        }
        catch (IndexOutOfBoundsException e)
        {
            model.addAttribute("errorMsg", String.format("Contact with id number %d was not found.", id));
        }
        return "index";
    }

    @PostMapping("/edit")
    public String edit(Model model, Contact editedContact)
    {
        if(editedContact.getName().equals(""))
        {
            errorMsg = "The field Name cannot be empty.";
            return String.format("redirect:/%d", editedContact.getIndex());
        }
        if(editedContact.getNumber().equals(""))
        {
            errorMsg = "The field Number cannot be empty.";
            return String.format("redirect:/%d", editedContact.getIndex());
        }
        Contact contact = this.contacts.get(editedContact.getIndex()-1);
        contact.setName(editedContact.getName());
        contact.setNumber(editedContact.getNumber());
        return "redirect:/";
    }

    private Contact getContact(String name)
    {
        int index = contacts.indexOf(new Contact(0, name, ""));
        if(index!=-1) return contacts.get(index);
        else return null;
    }
}
