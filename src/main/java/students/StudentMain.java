package students;

public class StudentMain {

	public static void main(String[] args) {
		StudentModel model = new StudentModel();
		StudentView view = new StudentView(model);
		new StudentController(model,view);
		view.setVisible(true);
	}

}
