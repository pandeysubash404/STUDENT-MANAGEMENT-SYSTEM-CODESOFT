package students;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

public class StudentController {
	private StudentModel model;
	private StudentView view;

	public StudentController(StudentModel model, StudentView view) {
		this.model = model;
		this.view = view;

		// Action listeners to the view's buttons
		view.addAddButtonListener(new AddButtonListener());
		view.addUpdateButtonListener(new UpdateButtonListener());
		view.addDeleteButtonListener(new DeleteButtonListener());
		view.addSearchButtonListener(new SearchButtonListener());
		view.addRefreshButtonListener(new RefreshButtonListener());

		// Initial table data update
		updateTableData();
	}

	class AddButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String studentId = view.getStudentIdField().getText();
			String name = view.getNameField().getText();
			String address = view.getAddressField().getText();
			String stream = view.getStreamField().getSelectedItem().toString();

			if (!(studentId.isEmpty() || name.isEmpty() || address.isEmpty() || stream.isEmpty())) {
				int studentIdInt = Integer.parseInt(studentId);
				if (!model.isStudent(studentIdInt)) {
					Student student = new Student(studentId, name, address, stream);

					model.addStudent(student);

					// Update the table data in the view
					updateTableData();
				} else {
					JOptionPane.showMessageDialog(view, "Student ID already exits.", "Error",
							JOptionPane.ERROR_MESSAGE);
				} // nested else
			} else {
				JOptionPane.showMessageDialog(view, "Fill out all the field", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	class UpdateButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String studentId = view.getStudentIdField().getText();
			String name = view.getNameField().getText();
			String address = view.getAddressField().getText();
			String stream = view.getStreamField().getSelectedItem().toString();

			Student student = new Student(studentId, name, address, stream);

			Student existingStudent = model.searchStudent(studentId);
			if (existingStudent != null) {
				model.updateStudent(student);
				updateTableData();
			} else {
				JOptionPane.showMessageDialog(view, "Invalid Student ID. ", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	class DeleteButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String studentIdString = view.getStudentIdField().getText();

			try {
				int studentId = Integer.parseInt(studentIdString);

				if (model.isStudent(studentId)) {
					model.deleteStudent(studentId);

					updateTableData();
				} else {
					JOptionPane.showMessageDialog(view, "Invalid Student ID. Please enter a valid integer.", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			} catch (NumberFormatException ex) {
				// Handle the case when the input is not a valid integer
				JOptionPane.showMessageDialog(view, "Invalid Student ID.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	
	class RefreshButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// Call the method in StudentModel to get all students' data from the database
			view.updateStudentTableFromDatabase();
		}
	}

	class SearchButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String name = view.getSearchField().getText();

			Object[][] searchData = model.searchStudentByName(name);

			if (searchData != null) {
				view.updateStudentTable(searchData);
			} else {
				JOptionPane.showMessageDialog(view, "No student exists of name " + name + ".", "Error",
						JOptionPane.ERROR_MESSAGE);
				view.updateStudentTableFromDatabase();
			}
		}
	}

	// Helper method to update the table data in the view
	private void updateTableData() {
		Object[][] data = model.getAllStudentsData();
		view.updateStudentTable(data);
	}

}
