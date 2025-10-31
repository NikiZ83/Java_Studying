public class Student {
    private String firstName;
    private String lastName;
    private int age;
    private double gpa;

    public Student(String firstName, String lastName, int age, double gpa) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.gpa = gpa;
    }

    public String getFirstName() { return firstName; }
    public String getLastName()  { return lastName; }
    public int getAge()          { return age; }
    public double getGpa()       { return gpa; }

    @Override
    public String toString() {
        return String.format("%s %s, age=%d, gpa=%.2f", firstName, lastName, age, gpa);
    }
}
