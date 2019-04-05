package phonebook.entity;

public class Contact
{
    private int index;
    private String name;
    private String number;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Contact()
    {

    }

    public Contact(int index, String name, String number)
    {
        this.index = index;
        this.name = name;
        this.number = number;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Contact)
        {
            Contact c = (Contact) obj;
            return this.name.equals(c.name);
        }
        return false;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
