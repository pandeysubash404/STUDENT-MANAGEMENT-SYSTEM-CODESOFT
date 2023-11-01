package students;

public class Student {
    private String studentId;
    private String name;
    private String address;
    private String stream;

    public Student(String studentId, String name, String address, String stream) {
        this.studentId = studentId;
        this.name = name;
        this.address = address;
        this.stream = stream;
    }

    // Getters and setters for the Student class properties
    public String getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStream() {
        return stream;
    }

    public void setStream(String stream) {
        this.stream = stream;
    }
}
