package students;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class StudentModel {
    private Connection connection;

    public StudentModel() {
    	
        try {
            // Connect to your MySQL database
        	Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3307/practical";
            String user = "root";
            String password = "admin";
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    // Add a student to the database
    public void addStudent(Student student) {
        try {
            String sql = "INSERT INTO students (student_id, name, address, stream) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, student.getStudentId());
            statement.setString(2, student.getName());
            statement.setString(3, student.getAddress());
            statement.setString(4, student.getStream());
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to update a student in the database
    public void updateStudent(Student student) {
        try {
            String sql = "UPDATE students SET name=?, address=?, stream=? WHERE student_id=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, student.getName());
            statement.setString(2, student.getAddress());
            statement.setString(3, student.getStream());
            statement.setString(4, student.getStudentId());
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to delete a student from the database by studentId
    public void deleteStudent(int studentId) {
        try {
            String sql = "DELETE FROM students WHERE student_id=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, studentId);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Check the student is present in database or not
    public boolean isStudent(int studentId) {
    	boolean foundId=false;
    	try {
            String sql = "SELECT id FROM students WHERE student_id=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, studentId);
            ResultSet resultSet = statement.executeQuery();

            foundId= resultSet.next();
            
            resultSet.close();
            statement.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return foundId;
    }
    
 // Retrieve all students from the database
    public Object[][] getAllStudentsData() {
        try {
            String sql = "SELECT * FROM students";
            Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet resultSet = statement.executeQuery(sql);

            // Count the number of rows in the result set
            resultSet.last();
            int rowCount = resultSet.getRow();
            resultSet.beforeFirst();

            Object[][] data = new Object[rowCount][4];
            int i = 0;

            while (resultSet.next()) {
                String studentId = resultSet.getString("student_id");
                String name = resultSet.getString("name");
                String address = resultSet.getString("address");
                String stream = resultSet.getString("stream");
                data[i][0] = studentId;
                data[i][1] = name;
                data[i][2] = address;
                data[i][3] = stream;
                i++;
            }

            resultSet.close();
            statement.close();
            return data;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


/*
    // Method to get the total number of students in the database
    public int getStudentCount() {
        try {
            String sql = "SELECT COUNT(*) as count FROM students";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            if (resultSet.next()) {
                return resultSet.getInt("count");
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
   */
    
    // Method to search for a student by studentId
    public Student searchStudent(String studentId) {
        try {
            String sql = "SELECT * FROM students WHERE student_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, studentId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String name = resultSet.getString("name");
                String address = resultSet.getString("address");
                String stream = resultSet.getString("stream");
                return new Student(studentId, name, address, stream);
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
 // Method to search for students by name
    public Object[][] searchStudentByName(String name) {
        try {
            String sql = "SELECT * FROM students WHERE name LIKE ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
                preparedStatement.setString(1, "%" + name + "%");
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    // Count the number of rows in the result set
                    resultSet.last();
                    int rowCount = resultSet.getRow();
                    resultSet.beforeFirst();

                    if (rowCount == 0) {
                        return null; // Return null if no student is found
                    }

                    Object[][] data = new Object[rowCount][4];
                    int i = 0;

                    while (resultSet.next()) {
                        int studentId = resultSet.getInt("student_id");
                        String studentName = resultSet.getString("name");
                        String address = resultSet.getString("address");
                        String stream = resultSet.getString("stream");
                        data[i][0] = studentId;
                        data[i][1] = studentName;
                        data[i][2] = address;
                        data[i][3] = stream;
                        i++;
                    }

                    return data;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


}