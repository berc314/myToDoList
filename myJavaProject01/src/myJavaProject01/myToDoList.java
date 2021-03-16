package myJavaProject01;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.DefaultListModel;
import java.util.ArrayList;
import javax.swing.ListSelectionModel;

public class myToDoList extends JFrame {

	private JPanel contentPane;
	private JTextField tfEingabe;											//Initialisierung des Eingabefelds
	private DefaultListModel<String> eingabeModel;							//Initialisierung der ListModel DefaultListModel
	private int eingabeToDo;												//Primitiver Datentyp als Index-Initialisierung
	private ArrayList<String> eingabeText;									//ArrayList für Flexibilität bei den ToDo-Einträgen
	private DefaultListModel<String> uebergabeModel;						//Initialisierung der ListModel DefaultListModel
	private ArrayList<String> uebergabeText;								//Initialisierung der ArrayList für die Übergabe zwischen zwei JList


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					myToDoList frame = new myToDoList();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public myToDoList() {
		setTitle("My ToDo list");								//Überschrift des Frames
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 410, 620);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTodoList = new JLabel("ToDo list");			//Überschrift des Programms
		lblTodoList.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblTodoList.setBounds(10, 30, 91, 34);
		contentPane.add(lblTodoList);
		
		tfEingabe = new JTextField();							//Eingabe der Aufgabe(n)
		tfEingabe.setBounds(10, 75, 144, 20);
		contentPane.add(tfEingabe);
		tfEingabe.setColumns(10);
		
		eingabeText = new ArrayList<String>();					//Erzeugung eines Strings zur Speicherung der String-Aufgaben
		
		/*
		 * Mit dem Add-Button wird/werden die Aufgabe(n) zur Liste (siehe listNotFinished) hinzugefügt.
		 */
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				eingabeText.add(tfEingabe.getText());			//Mit der Add-Methode des ArrayList werden die Einträge zur Liste hinzugefügt
				eingabeModel.addElement(tfEingabe.getText());	//Mit der AddElement-Methode der JList werden die Strings in der Liste dargestellt 
				tfEingabe.setText("");							//Damit der letzterwähnte Text nicht manuell gelöscht werden muss
				tfEingabe.requestFocus();						//Der Cursor soll im JTextField bleiben
			}
		});
		btnAdd.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnAdd.setBounds(194, 74, 89, 23);
		contentPane.add(btnAdd);
		
		JLabel lblNotFinishedTasks = new JLabel("Not finished task(s):");		//Überschrift der Not-Finished-Task(s)
		lblNotFinishedTasks.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNotFinishedTasks.setBounds(10, 132, 144, 14);
		contentPane.add(lblNotFinishedTasks);
		
		JScrollPane scrollPane = new JScrollPane();								//Für die JList NotFinished task(s) mit Scrollbalken...
		scrollPane.setBounds(10, 160, 144, 137);
		contentPane.add(scrollPane);											//...damit bei langen Listen eine bessere Erreichbarkeit gewährt ist
		
		JList listNotFinished = new JList<String>();							//Erzeugung einer neuer Instanz JList...
		listNotFinished.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);		
		scrollPane.setViewportView(listNotFinished);							//...eingebettet in einem Scrollbalken
		
		eingabeModel = new DefaultListModel<String>();							//Erzeugung der Instanz DefaultListModel...
		listNotFinished.setModel(eingabeModel);									//...damit die Verbindung zwischen Model und JList hergestellt wird
		
		uebergabeText = new ArrayList<String>();
		
		/*
		 * Mit dem Finished-Button wird die markierte Aufgabe zur Liste listFinished hinzugefügt.
		 */
		JButton btnFinished = new JButton("Finished!");
		btnFinished.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
				uebergabeText.add(listNotFinished.getSelectedValue().toString());			//Die Add-Methode des ArrayList verlangt als Parameter String -> deshalb Umwandlung der markierten Aufgaben nach String
				uebergabeModel.addElement((String) listNotFinished.getSelectedValue());		//Die Add-Methode der JList verlangt als Parameter String -> deshalb Umwandlung der markierten Aufgaben nach String
				eingabeToDo = listNotFinished.getSelectedIndex();							//Der Index von eingabeToDo soll dem markierten Index entsprechend
					if((eingabeToDo >= 0) && (eingabeToDo < eingabeText.size()))
					{
						eingabeModel.remove(eingabeToDo);									//Remove-Methode zur Entfernung des gezeigten Listeneintrags
					} else{
						JOptionPane.showMessageDialog(null, "Fine! Everything is finished!");
					}
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Everything is finished!");
			    	}
			}
		});
		btnFinished.setVisible(false);														//Zu Beginn soll der Finished-Button unsichtbar sein
		btnFinished.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnFinished.setBounds(194, 192, 89, 23);
		contentPane.add(btnFinished);

		/*
		 * Mit dem Delete-Button wird die markierte Aufgabe aus der Liste listNotFinished gelöscht.
		 */
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					eingabeToDo = listNotFinished.getSelectedIndex();						//siehe auch JButton btnFinished
					if((eingabeToDo >= 0) && (eingabeToDo < eingabeText.size()))			//Der Index soll im gültigen Bereich finden
					{
						eingabeModel.remove(eingabeToDo);
					} else
					{
						JOptionPane.showMessageDialog(null, "The list is empty. Write a new task!");
					}	
				}
				catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Es gibt nichts zu löschen!");
			    }
			}
		});
		btnDelete.setVisible(false);														//siehe auch btnFinished
		btnDelete.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnDelete.setBounds(293, 192, 89, 23);
		contentPane.add(btnDelete);
		
		/*
		 * Mit dem Update-the-task-Button kann die markierte Aufgabe im ersten Schritt bearbeitet werden.
		 */
		JButton btnUpdateTask = new JButton("Update task!");
		btnUpdateTask.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					eingabeToDo = listNotFinished.getSelectedIndex();
					if((eingabeToDo >= 0) && (eingabeToDo < eingabeText.size()))
					{																			//Mit der Klick auf den Update task Button wird...
						btnDelete.setVisible(true);											    //der btnDelete-Button sichtbar und...
						btnFinished.setVisible(true);											//...der btnFinished-Button sichtbar.
					} else
					{
						JOptionPane.showMessageDialog(null, "Select the task, please!");
					}
			    } catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Fehler!");
			    }
			}
		});
		btnUpdateTask.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnUpdateTask.setBounds(194, 158, 188, 23);
		contentPane.add(btnUpdateTask);
		
		JLabel lblFinishedTasks = new JLabel("Finished task(s):");						//Überschrift (Label) zu Finished task(s) 
		lblFinishedTasks.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblFinishedTasks.setBounds(10, 335, 115, 14);
		contentPane.add(lblFinishedTasks);
		
		JScrollPane scrollPane_1 = new JScrollPane();									//Für die JList Finished task(s) mit Scrollbalken...									
		scrollPane_1.setBounds(10, 362, -50, -73);
		contentPane.add(scrollPane_1);													//...damit bei langen Listen eine bessere Erreichbarkeit gewährt ist
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(10, 360, 144, 137);
		contentPane.add(scrollPane_2);
		
		JList listFinished = new JList();												//Erzeugung einer neuen Instanz JList für die Finished task(s)
		listFinished.setEnabled(false);													//Keine weitere Bearbeitung der Aufgaben
		listFinished.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane_2.setViewportView(listFinished);										//eingebettet in einen Scrollbalken
		
		uebergabeModel = new DefaultListModel<String>();								//Erzeugung einer neuen DefaultListModel...
		listFinished.setModel(uebergabeModel);											//...damit eine Verbindung zwischen JList und Model hergestellt wird.
	}
}
