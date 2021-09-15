package org.university.software;

import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import javax.swing.*;

import org.university.hardware.Classroom;
import org.university.people.Person;
import org.university.people.Student;


public class UniversityGUI extends JFrame {
	private University uni1;
	private JMenuBar menuBar;		//the horizontal container
	private JMenu adminMenu; //JMenu objects are added to JMenuBar objects as the "tabs"
	private ArrayList<Student> studentList;
	
	private JMenuItem adminPrintAll; 		//JMenuItem objects are added to JMenu objects as the drop down selections. 
	private JMenuItem adminSave;
	private JMenuItem adminLoad;
	private JMenuItem adminExit;
	private JMenuItem adminAddCourse;
	private JMenuItem adminDropCourse;
	private JMenuItem adminPrintSchedule;
	
	public UniversityGUI(String windowTitle, University univ) {
		super(windowTitle);
		
		uni1 = univ;
		studentList = new ArrayList<Student>();
		
		
		setSize(300, 100);
		setLayout(new FlowLayout(FlowLayout.LEFT));
		
		add(new JLabel("<HTML><center>Welcome to the University" +
				"<BR>Choose an action from the above menus.</center></HTML>"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		buildGUI();	
		setVisible(true);
		
	}
	
	public void buildGUI() {
		menuBar = new JMenuBar();
     	
		// Employee Student Menu
		
		adminMenu = new JMenu("File");
		
		adminSave = new JMenuItem("Save");
		adminLoad = new JMenuItem("Load");
		adminExit = new JMenuItem("Exit");
		
		adminSave.addActionListener(new MenuListener());
		adminLoad.addActionListener(new MenuListener());
		adminExit.addActionListener(new MenuListener());
		
		adminMenu.add(adminSave);
		adminMenu.add(adminLoad);
		adminMenu.add(adminExit);
		
		menuBar.add(adminMenu);
			
		setJMenuBar(menuBar);
		
		adminMenu = new JMenu("Students");
		
		adminAddCourse = new JMenuItem("Add Course");
		adminDropCourse = new JMenuItem("Drop Course");
		adminPrintSchedule = new JMenuItem("Print Student Schedule");
		
		adminAddCourse.addActionListener(new MenuListener());
		adminDropCourse.addActionListener(new MenuListener());
		adminPrintSchedule.addActionListener(new MenuListener());
		
		adminMenu.add(adminAddCourse);
		adminMenu.add(adminDropCourse);
		adminMenu.add(adminPrintSchedule);
		
		menuBar.add(adminMenu);
		
		setJMenuBar(menuBar);
		
		adminMenu = new JMenu("Administrators");
		
		adminPrintAll = new JMenuItem("Print All Info");
		
		adminPrintAll.addActionListener(new MenuListener());
		
		adminMenu.add(adminPrintAll);
		
		menuBar.add(adminMenu);
		
		setJMenuBar(menuBar);
		
	}
	
	private class MenuListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JMenuItem source = (JMenuItem)(e.getSource());
			if (source.equals(adminSave)) {
				handleAdminSave();
			}
			else if (source.equals(adminLoad)) {
				handleAdminLoad();
			}
			else if(source.equals(adminPrintAll))
			{
				handleAdminPrint();
			}
			else if (source.equals(adminAddCourse)) {
				handleAdminAddCourse();
			}
			else if (source.equals(adminDropCourse)) {
				handleAdminDropCourse();
			}
			else if (source.equals(adminPrintSchedule)) {
				handleAdminPrintSchedule();
			}
			else if (source.equals(adminExit)) {
				handleAdminExit();
			}
		}
	}
	
	private void handleAdminSave() {
		University.saveData(uni1);
	}
	
	private void handleAdminLoad() {
		uni1 = University.loadData();
	}
	
	private void handleAdminPrint() {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(baos);
		PrintStream old = System.out;
		System.setOut(ps);
		uni1.printAll();
		System.out.flush();
		System.setOut(old);
		if( uni1!=null) {
			JTextArea textArea = new JTextArea(baos.toString());
			JScrollPane scrollPane = new JScrollPane(textArea);
			textArea.setLineWrap(true);  
			textArea.setWrapStyleWord(true); 
			scrollPane.setPreferredSize(new Dimension( 500, 500 ));
			JOptionPane.showMessageDialog(null, 
					scrollPane, 
					"University", 
					JOptionPane.PLAIN_MESSAGE); 
		}
		else {
			JOptionPane.showMessageDialog(null, 
					"No University", 
					"Error", 
					JOptionPane.PLAIN_MESSAGE);
		}
	}
	
	private void handleAdminAddCourse() {
	      JTextField stuName = new JTextField(15);
	      JTextField dept = new JTextField(15);
	      JTextField courseNo = new JTextField(15);
	      JLabel stu = new JLabel("Student Name: ");
	      JLabel dep = new JLabel("Department: ");
	      JLabel coNo = new JLabel("Course #: ");
	      
	      
	      JPanel panel = new JPanel();
	      panel.setLayout(new GridLayout(6,1));
	      
	      panel.add(stu);
	      panel.add(stuName);
	      panel.add(dep);
	      panel.add(dept);
	      panel.add(coNo);
	      panel.add(courseNo);


		 JOptionPane.showInputDialog(null, 
					panel, 
					"Add Course", 
					JOptionPane.QUESTION_MESSAGE);
		
	      String name = stuName.getText();
	      String department = dept.getText();
	      String courseNumber = courseNo.getText();
	      
	      boolean flag = false;
	      
	      if(name.equals("") || department.equals("") || courseNumber.equals("")) {
				JOptionPane.showMessageDialog(null,
						"Please make sure all fields are input. (Disregard the input box below the course number input)",
						"Error ",
						JOptionPane.PLAIN_MESSAGE);
	      }
	 
	      else if (!containsStudent(name)) {
				JOptionPane.showMessageDialog(null,
						"Student \""+name+"\" doesn't exist.",
						"Error ",
						JOptionPane.PLAIN_MESSAGE);
	      }
	      else {
	    	  if (Integer.parseInt(courseNumber) < 600) {
	    	  for (int i = 0; i < uni1.departmentList.size(); i++) {
	    		  for (int j = 0; j < uni1.departmentList.get(i).getCampusCourseList().size();j++) {
	    			  if (!isValidCourse(department,Integer.parseInt(courseNumber)) ||
	    					  !isAvailableTo(uni1.departmentList.get(i).getCampusCourseList().get(j), name))  {
	    				  flag = true; 
	    				  break;
	    			  }
	    			  else if (uni1.departmentList.get(i).getCampusCourseList().get(j).getCourseNumber() == Integer.parseInt(courseNumber)) {
	    					  addCourse(uni1.departmentList.get(i).getCampusCourseList().get(j), name);
	    					  flag = true;
	    				  }
	    			  	else if (uni1.departmentList.get(i).getCampusCourseList().size() - 1 == j && !flag &&
	    			  			uni1.departmentList.get(i).getDepartmentName().equals(department)) {
							JOptionPane.showMessageDialog(null,
									"Course \""+courseNumber+"\" doesn't exist.",
									"Error ",
									JOptionPane.PLAIN_MESSAGE);
							flag = true;
							
							break;
		    		  	}
	    			  }
	    		  	if (flag) {
	    			  	break;
	    		  	}
	    		  }
	    	  }
	      else {
	    	  for (int i = 0; i < uni1.departmentList.size(); i++) {
	    		  for (int j = 0; j < uni1.departmentList.get(i).getOnlineCourseList().size();j++) {
	    			  if (!isValidCourse(department,Integer.parseInt(courseNumber)))  {
	    				  flag = true; 
	    				  break;
	    			  }
	    			  else if (uni1.departmentList.get(i).getOnlineCourseList().get(j).getCourseNumber() == Integer.parseInt(courseNumber)) {
	    				  addCourse(uni1.departmentList.get(i).getOnlineCourseList().get(j), name);
	    				  flag = true;
	    			  }
	    			  }
	    		  	if (flag) {
	    			  	break;
	    		  	}
	    	  }
	      	}
	      }

	}
	
	public void handleAdminDropCourse() {
		JTextField stuName = new JTextField(15);
	      JTextField dept = new JTextField(15);
	      JTextField courseNo = new JTextField(15);
	      JLabel stu = new JLabel("Student Name: ");
	      JLabel dep = new JLabel("Department: ");
	      JLabel coNo = new JLabel("Course #: ");
	      
	      
	      JPanel panel = new JPanel();
	      panel.setLayout(new GridLayout(6,1));
	      
	      panel.add(stu);
	      panel.add(stuName);
	      panel.add(dep);
	      panel.add(dept);
	      panel.add(coNo);
	      panel.add(courseNo);


		 JOptionPane.showInputDialog(null, 
					panel, 
					"Drop Course", 
					JOptionPane.QUESTION_MESSAGE);
		
	      String name = stuName.getText();
	      String department = dept.getText();
	      String courseNumber = courseNo.getText();
	      
	      boolean flag = false;
	      
	      if(name.equals("") || department.equals("") || courseNumber.equals("")) {
				JOptionPane.showMessageDialog(null,
						"Please make sure all fields are input. (Disregard the input box below the course number input)",
						"Error ",
						JOptionPane.PLAIN_MESSAGE);
	      }
	      else if (!containsStudent(name)) {
				JOptionPane.showMessageDialog(null,
						"Student \""+name+"\" doesn't exist.",
						"Error ",
						JOptionPane.PLAIN_MESSAGE);
	      }
	      else {
	    	  if (Integer.parseInt(courseNumber) < 600) {
	    	  for (int i = 0; i < uni1.departmentList.size(); i++) {
	    		  for (int j = 0; j < uni1.departmentList.get(i).getCampusCourseList().size();j++) {
	    			  if (!isValidCourse(department,Integer.parseInt(courseNumber)))  {
	    				  flag = true; 
	    				  break;
	    			  }
	    			  else if (uni1.departmentList.get(i).getCampusCourseList().get(j).getCourseNumber() == Integer.parseInt(courseNumber)) {
	    					  dropCourse(uni1.departmentList.get(i).getCampusCourseList().get(j), name);
	    					  flag = true;
	    				  }
	    		  	}
	    		  if(flag) {
	    			  break;
	    		  }
	      
	    	  	}
	    	  }
	    	  else {
		    	  for (int i = 0; i < uni1.departmentList.size(); i++) {
		    		  for (int j = 0; j < uni1.departmentList.get(i).getOnlineCourseList().size();j++) {
		    			  if (!isValidCourse(department,Integer.parseInt(courseNumber)))  {
		    				  flag = true; 
		    				  break;
		    			  }
		    			  else if (uni1.departmentList.get(i).getOnlineCourseList().get(j).getCourseNumber() == Integer.parseInt(courseNumber)) {
		    					  dropCourse(uni1.departmentList.get(i).getOnlineCourseList().get(j), name);
		    					  flag = true;
		    				  }
		    		  	}
		    		  if(flag) {
		    			  break;
		    		  }
		      
		    	  	}	    	  		    	  
	    	  }
	      }     
	}
	
	public void handleAdminPrintSchedule() {
		String name = JOptionPane.showInputDialog("Student Name: ");
		boolean flag = false;
		
		for (int i = 0; i < uni1.departmentList.size(); i ++) {
			for (int j = 0; j < uni1.departmentList.get(i).getStudentList().size(); j++) {
				if (uni1.departmentList.get(i).getStudentList().get(j).getName().equals(name)) {
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					PrintStream ps = new PrintStream(baos);
					PrintStream old = System.out;
					System.setOut(ps);
					uni1.departmentList.get(i).getStudentList().get(j).printSchedule();
					System.out.flush();
					System.setOut(old);
						JTextArea textArea = new JTextArea(baos.toString());
						JScrollPane scrollPane = new JScrollPane(textArea);
						textArea.setLineWrap(true);  
						textArea.setWrapStyleWord(true); 
						scrollPane.setPreferredSize(new Dimension( 450, 350 ));
						JOptionPane.showMessageDialog(null, 
								scrollPane, 
								"Student " + name + "'s Schedule", 
								JOptionPane.PLAIN_MESSAGE); 
						flag = true;
				}
			}
		}
		if(!flag) {
			JOptionPane.showMessageDialog(null, 
					"Student " + name+ " could not be found.", 
					"Student not found", 
					JOptionPane.PLAIN_MESSAGE); 
		}
	}
	
	public void handleAdminExit() {
		System.exit(0);
	}
	
	public boolean containsStudent(String name) {
		for (int i = 0; i < uni1.departmentList.size(); i++) {
			for (int j = 0; j < uni1.departmentList.get(i).getStudentList().size(); j++) {
				if (uni1.departmentList.get(i).getStudentList().get(j).getName().equals(name)) {
					return true;
					
				}
			}
		}
		return false;
	}
	
	public boolean isValidCourse(String deptName, int courseNo) {
		boolean flag = false;
		for (int i = 0; i < uni1.departmentList.size(); i++) {
			if (uni1.departmentList.get(i).getDepartmentName().equals(deptName)) {
				for(int j = 0; j < uni1.departmentList.get(i).getCourseList().size(); j++) {
					if (uni1.departmentList.get(i).getCourseList().get(j).getCourseNumber() == courseNo) {
						return true;
					}
					else if (j == uni1.departmentList.get(i).getCourseList().size() - 1){
						JOptionPane.showMessageDialog(null,
								"Course \""+courseNo +"\" doesn't exist for department " + deptName +". ",
								"Error ",
								JOptionPane.PLAIN_MESSAGE);
						flag = true;
					}
				}
			}
				else if (i == uni1.departmentList.size() - 1 && !flag) {
				JOptionPane.showMessageDialog(null,
						"Department \""+deptName+"\" doesn't exist.",
						"Error ",
						JOptionPane.PLAIN_MESSAGE);
				}
			}
		return false;
		}
		
	
	public void addCourse(CampusCourse aCourse, String name) {
		boolean flag = false;
		for (int i = 0; i < uni1.departmentList.size(); i++) {
			for (int j = 0; j < uni1.departmentList.get(i).getStudentList().size(); j++) {
				if (uni1.departmentList.get(i).getStudentList().get(j).getName().equals(name) && !detectConflict(aCourse, name)) {
					uni1.departmentList.get(i).getStudentList().get(j).addCourse(aCourse);
					JOptionPane.showMessageDialog(null,
							"Success you have added " + aCourse.getName(),
							"Success",
							JOptionPane.PLAIN_MESSAGE);
					flag = true;
					break;
				}
				else if (detectConflict(aCourse, name)) {
					int x =0;
					int y=0;
					int a = 0;
					
					for (int k = 0; i<uni1.departmentList.size();i++) {
						for (int n =0; n < uni1.departmentList.get(k).getStudentList().size() ;n++) {
							for (int z = 0;z < uni1.departmentList.get(k).getStudentList().get(n).getcCourses().size(); z++) {
								if (uni1.departmentList.get(k).getStudentList().get(n).getcCourses().get(z).equals(aCourse)) {
									x=k;
									y=n;
									a=z;
								}
							}
						}
					}
					JOptionPane.showMessageDialog(null,
							aCourse.getDepartment().getDepartmentName() + aCourse.getCourseNumber() + " cannot be "
									+ "added to " + name+ "'s schedule. "+aCourse.getDepartment().getDepartmentName() + aCourse.getCourseNumber()
									+ " conflicts with "+uni1.departmentList.get(x).getStudentList().get(y).getcCourses().get(a).getDepartment()
									.getDepartmentName()+ uni1.departmentList.get(x).getStudentList().get(y).getcCourses().get(a).getCourseNumber()
									+ ". Conflicting time slot is " + Classroom.courseConverter(aCourse.getSchedule().get(a))
									+ ".",
							"L",
							JOptionPane.PLAIN_MESSAGE);
					flag = true;
					break;
				}
			}
			if (flag) {
				break;
			}
		
		}
	}
	
	public boolean detectConflict(CampusCourse aCourse, String name) {
		for (int i = 0; i < uni1.departmentList.size(); i++) {
			for (int j = 0; j < uni1.departmentList.get(i).getStudentList().size(); j++) {
				if (uni1.departmentList.get(i).getStudentList().get(j).getName().equals(name)) {
						if (uni1.departmentList.get(i).getStudentList().get(j).detectConflict(aCourse)) {
								return true;
							}
								
						}
					}
				}
		return false;
	}
	
	public boolean isAvailableTo(CampusCourse aCourse, String name) {
		for (int i = 0; i < aCourse.getStudentRoster().size(); i++) {
			if (aCourse.getStudentRoster().get(i).getName().equals(name)) {
				return true;
			}
		}
			if (aCourse.getMaxCourseLimit() <= aCourse.getStudentRoster().size()) {
					JOptionPane.showMessageDialog(null,
							name + " can't add Campus Course " + aCourse.getDepartment().getDepartmentName() +
							aCourse.getCourseNumber() + " " + aCourse.getName() + ". "
							+ " Because this Campus course has enough student.",
							"Error ",
							JOptionPane.PLAIN_MESSAGE);
					return false;
		}
		return true;
	}
	
	public void addCourse(OnlineCourse aCourse, String name) {
		for (int i = 0; i < uni1.departmentList.size(); i++) {
			for (int j = 0; j < uni1.departmentList.get(i).getStudentList().size(); j++) {
				if (uni1.departmentList.get(i).getStudentList().get(j).getName().equals(name)) {
					if (uni1.departmentList.get(i).getStudentList().get(j).creditsTaken() <= 6) {
						uni1.departmentList.get(i).getStudentList().get(j).addCourse(aCourse);
						JOptionPane.showMessageDialog(null,
								"Student " + name+ " has only " + uni1.departmentList.get(i).getStudentList().get(j).creditsTaken()
								+ " on campus credits enrolled. Should have at least 6 credits registered before registering online "
								+ " courses. \n" + name + " can't add online Course " + aCourse.getDepartment().getDepartmentName()
								+ aCourse.getCourseNumber() + " " + aCourse.getName()+ ". Because this student doesn't have "
										+ "enough Campus course credit.",
								"Error",
								JOptionPane.PLAIN_MESSAGE);
					}
					else {
					uni1.departmentList.get(i).getStudentList().get(j).addCourse(aCourse);
					JOptionPane.showMessageDialog(null,
							"Success you have added " + aCourse.getName(),
							"Success",
							JOptionPane.PLAIN_MESSAGE);
					}
				}
			}
		}
	}
	
	public void dropCourse(CampusCourse aCourse, String name) {
		boolean flag = false;
		
		if(canDrop(aCourse, name) ) {
		for (int i = 0; i < uni1.departmentList.size(); i++) {
			for (int j = 0; j < uni1.departmentList.get(i).getStudentList().size(); j++) {
				if (uni1.departmentList.get(i).getStudentList().get(j).getName().equals(name)) {
					uni1.departmentList.get(i).getStudentList().get(j).dropCourse(aCourse);
					JOptionPane.showMessageDialog(null,
							"Success you have dropped " + aCourse.getName(),
							"Success",
							JOptionPane.PLAIN_MESSAGE);
						flag = true;
						break;
					}
				}
			}
		}
	}
	
	public boolean canDrop(CampusCourse aCourse, String name) {
		int numCredit = 0;
		boolean flag = false;
		boolean isEnrolled = false;
		boolean hasOnline = false;
		
		for (int n = 0; n < uni1.departmentList.size(); n++) {
			for (int k = 0; k < uni1.departmentList.get(n).getStudentList().size(); k++) {
				if (uni1.departmentList.get(n).getStudentList().get(k).getName().equals(name)) {
					if (!uni1.departmentList.get(n).getStudentList().get(k).getoCourses().isEmpty()) {
						hasOnline = true;
					}
				}
			}
		}

		
		for (int n = 0; n < uni1.departmentList.size(); n++) {
			for (int k = 0; k < uni1.departmentList.get(n).getStudentList().size(); k++) {
				if (uni1.departmentList.get(n).getStudentList().get(k).getName().equals(name)) {
					flag = true;
					for (int a = 0; a< uni1.departmentList.get(n).getStudentList().get(k).getcCourses().size(); a++) {
						if (uni1.departmentList.get(n).getStudentList().get(k).getcCourses().get(a).getName().equals(aCourse.getName())) {
							isEnrolled = true;
						}
						numCredit = uni1.departmentList.get(n).getStudentList().get(k).getcCourses().get(a).getCreditUnits() + numCredit;
						if (a == uni1.departmentList.get(n).getStudentList().get(k).getcCourses().size() - 1 &&
								!isEnrolled) {
							JOptionPane.showMessageDialog(null,
									name + " is not enrolled in " + aCourse.getDepartment().getDepartmentName() +
									aCourse.getCourseNumber() + " " + aCourse.getName() + ".",
									"Error",
									JOptionPane.PLAIN_MESSAGE);
							return false;
						}
						if (a == uni1.departmentList.get(n).getStudentList().get(k).getcCourses().size() - 1 
							&& numCredit - aCourse.getCreditUnits() < 6 && hasOnline) {
							JOptionPane.showMessageDialog(null,
									name + " can't drop this CampusCourse, because this student doesn't hold"
											+ " enough campus course credit to hold the online course.",
									"Error",
									JOptionPane.PLAIN_MESSAGE);
							return false;
						}
					}
				}
			}
		}
		if (!flag) {
			JOptionPane.showMessageDialog(null,
					name + " is not enrolled in " + aCourse.getDepartment().getDepartmentName() +
					aCourse.getCourseNumber() + " " + aCourse.getName() + ".",
					"Error",
					JOptionPane.PLAIN_MESSAGE);
			return false;
		}
		return true;
	}
	
	public void dropCourse(OnlineCourse aCourse, String name) {
		boolean flag = false;
		
		for (int i = 0; i < uni1.departmentList.size(); i++) {
			for (int j = 0; j < uni1.departmentList.get(i).getStudentList().size(); j++) {
				if (uni1.departmentList.get(i).getStudentList().get(j).getName().equals(name)) {
					for (int k = 0 ; k<uni1.departmentList.get(i).getStudentList().get(j).getoCourses().size(); k++) {
						if (aCourse.getName().equals(uni1.departmentList.get(i).getStudentList().get(j).getoCourses().get(k).getName())) {
							flag = true;
						}
					}
					if (flag) {
					uni1.departmentList.get(i).getStudentList().get(j).dropCourse(aCourse);
					JOptionPane.showMessageDialog(null,
							"Success you have dropped " + aCourse.getName(),
							"Success",
							JOptionPane.PLAIN_MESSAGE);
					}
					}
				}
			}
		if (!flag) {
			JOptionPane.showMessageDialog(null,
					name + " is not enrolled in " + aCourse.getDepartment().getDepartmentName() +
					aCourse.getCourseNumber() + " " + aCourse.getName() + ".",
					"Error",
					JOptionPane.PLAIN_MESSAGE);
		}
		}

		
	
}
