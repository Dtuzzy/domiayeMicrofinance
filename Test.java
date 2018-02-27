
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import javax.sql.DataSource;
import javax.swing.*;

import javax.swing.table.DefaultTableModel;

public class Test extends JFrame {

	private JPanel ppane;
	private JTextField jtf01;
	private JButton login;
	private JPasswordField jpfd;
	private JTabbedPane mainpane;
	private Container tablePane, tp02, tp03,tp04,tp05,tp06,tp07;
	private Container pane1, pane2, pane3, pane4, pane5;
	private JLabel lbl_reg, other_name, lbl_install, lbl_doInstall, lbl_savings, lbl_id, lbl_name, freq_lbl, lbl_phone,
			if_group, lbl_group, lbl_amount, lbl_interestRate, lbl_period, lbl_expAmount, lbl_date, txt_expAmount,
			payfreqdisplay, payfreq;
	private JTextField txt_othername, txt_reg, txt_install, txt_savings, txt_id, txt_name, txt_name02,
			txt_phone, txt_group, txt_amount, txt_interestRate, txt_period;
	private JComboBox txt_doInstall;
	private JRadioButton personal, group, months, weeks, monthly, weekly, daily;
	private JButton new_Cost, cost_list, update_List, search_butts;
	String id, fullname, phone, grp, amount, rate, period, total, date;
	int sum;
	private static String[] days = { "Monday", "Tuesday", "Wednesday",
	"Thursday" , "Friday" , "Saturday" , "Sunday"};
	DefaultTableModel model02;
	Object [] row02;
	static Connection connection = null;
	 static DataSource ds = FakeSql.getBasicDataSource();
	JTable table, table02;
	int count = 0;

	public Test() {
		super("Domiaye Login Page");
		connection = localhost.dbConnector();
		// backEnd c = new backEnd();
		// c.insertTable();
ppane = new JPanel();

//Timer timers = new Timer(50000, new ActionListener() {
	//@Override
	//public void actionPerformed(ActionEvent e) {
		//try {
//try {
  // connection = ds.getConnection();
 //} catch (SQLException exe) {
 //  JOptionPane.showMessageDialog(null, "Unable To Connect" + exe);
//   exe.printStackTrace();
// }

		//} catch (Exception exe) {
		//	JOptionPane.showMessageDialog(null, "Unable To Connect" + exe);
			//exe.printStackTrace();
			
		//}
	//}
//});
//timers.start();
		
		jtf01 = new JTextField();
		jpfd = new JPasswordField();
		login = new JButton("Login");
		
		ppane.add(jtf01);
		ppane.add(jpfd);
		ppane.add(login);
		ppane.setLayout(new GridLayout(3,3));
		login.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (jtf01.getText().length() == 0)
					JOptionPane.showMessageDialog(null, "Empty fields detected! please fill up fields");

				else if (jpfd.getPassword().length == 0)
					JOptionPane.showMessageDialog(null, "Empty fields detected! please fill up fields");

				else {
					String user = jtf01.getText();
					char[] pass = jpfd.getPassword();

					String pwd = String.copyValueOf(pass);

					if (validate_login(user, pwd)){
						System.out.println("Logged in!");
						openMain();
						dispose();
				JOptionPane.showMessageDialog(null, "Success");
					}else{
						JOptionPane.showMessageDialog(null, "Try again please");
			}
					
				}
			}

			private boolean validate_login(String username, String password) {

				try {
					connection = localhost.dbConnector();
					
					PreparedStatement pst = connection
							.prepareStatement("SELECT * FROM auth where usernames=? and passkeys=?");
					pst.setString(1, username);
					pst.setString(2, password);
					ResultSet rs = pst.executeQuery();
					if (rs.next())
						return true;
					else
						return false;
				} catch (Exception e) {
					e.printStackTrace();
					return false;
				}
				
			}
		});

		add(ppane);
		setSize(300, 150);
		setVisible(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {
				try {

					String ObjButtons[] = { "Yes", "No" };
					int PromptResult = JOptionPane.showOptionDialog(null, "Are you sure you want to exit Domiaye?",
							"Domiaye", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, ObjButtons,
							ObjButtons[1]);
					if (PromptResult == JOptionPane.YES_OPTION) {
						System.exit(0);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
		}
		});
	
	}
		public void openMain(){
			
		
			connection = localhost.dbConnector();
			
			JFrame mainfrm = new JFrame();
			mainfrm.setTitle("Domiaye");
			JPanel topPane = new JPanel();
			topPane.setLayout(new BorderLayout());
			mainfrm.getContentPane().add(topPane);

			JMenu file = new JMenu("File");
			JMenuItem exit = new JMenuItem("Exit");
			exit.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					System.exit(0);
				}
			});
			file.add(exit);

			JMenu abt = new JMenu("About");

			JMenuBar bar = new JMenuBar();
			mainfrm.setJMenuBar(bar);
			bar.add(file);
			bar.add(abt);

			home();
			newCostumer();
			showTable();
			showCustomerTable();
			updateCostumer();

			mainpane = new JTabbedPane();
			mainpane.addTab("Home", pane1);
			mainpane.addTab("New Costumer", pane2);
			mainpane.addTab("Costumer List", pane3);
			mainpane.addTab("Update List", pane4);
			mainpane.addTab("Show Customer Table", pane5);
			topPane.add(mainpane, BorderLayout.CENTER);

			mainfrm.setTitle("Domiaye");
			mainfrm.setSize(1350, 720);
			mainfrm.setLocationRelativeTo(null);
			mainfrm.setVisible(true);
			mainfrm.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
			mainfrm.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent we) {
					try {

						String ObjButtons[] = { "Yes", "No" };
						int PromptResult = JOptionPane.showOptionDialog(null, "Are you sure you want to exit Domiaye?",
								"Domiaye", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, ObjButtons,
								ObjButtons[1]);
						if (PromptResult == JOptionPane.YES_OPTION) {
							System.exit(0);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}
		
		

	private void home() {

		pane1 = new Container();
		// pane1.setLayout(new GridLayout());
		new_Cost = new JButton("New Costumer");
		new_Cost.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 30));
		new_Cost.setBounds(60, 250, 300, 50);
		new_Cost.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainpane.setSelectedIndex(1);
			}
		});

		cost_list = new JButton("Costumer List");
		cost_list.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 30));
		cost_list.setBounds(60, 320, 300, 50);
		cost_list.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainpane.setSelectedIndex(2);
			}
		});

		update_List = new JButton("Update");
		update_List.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 30));
		update_List.setBounds(60, 390, 300, 50);
		update_List.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainpane.setSelectedIndex(3);
			}
		});

		pane1.add(new_Cost);
		pane1.add(cost_list);
		pane1.add(update_List);

		ImageIcon yo = new ImageIcon(this.getClass().getResource("images/home_bg.png"));
		JLabel well = new JLabel(yo);
		well.setBounds(0, 0, 1330, 680);
		pane1.add(well);

	}

	// second Tab
	private void newCostumer() {
		// initialize connection

		pane2 = new Container();
		// pane1.setLayout(new GridLayout());

		JLabel title = new JLabel("<html><U>Add A New Costumer</U></html>");
		title.setFont(new Font("Italic", Font.BOLD | Font.ITALIC, 30));
		title.setBounds(500, 20, 500, 50);
		pane2.add(title);

		JLabel info = new JLabel(
				"Please provide the loan information in the required fields and click the 'Add Costumer' button to upload");
		info.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 20));
		info.setBounds(200, 70, 1000, 30);
		pane2.add(info);

		JLabel basic = new JLabel("<html><U>Basic Information</U><html>");
		basic.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 20));
		basic.setBounds(30, 120, 800, 30);
		pane2.add(basic);

		Calendar day = Calendar.getInstance();
		String time = day.getTime().toString();

		// Labels
		lbl_id = new JLabel("Enter Costumer ID :");
		lbl_id.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 20));
		lbl_id.setBounds(30, 170, 200, 30);
		pane2.add(lbl_id);

		txt_id = new JTextField();
		txt_id.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 20));
		txt_id.setText("2017");
		txt_id.setBounds(230, 170, 200, 30);
		pane2.add(txt_id);

		lbl_name = new JLabel("Surname:");
		lbl_name.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 20));
		lbl_name.setBounds(30, 210, 100, 30);
		pane2.add(lbl_name);

		txt_name = new JTextField();
		txt_name.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 20));
		txt_name.setBounds(130, 210, 150, 30);
		pane2.add(txt_name);

		other_name = new JLabel("Other Names :");
		other_name.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 20));
		other_name.setBounds(285, 210, 200, 30);
		pane2.add(other_name);

		txt_othername = new JTextField();
		txt_othername.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 20));
		txt_othername.setBounds(430, 210, 200, 30);
		pane2.add(txt_othername);

		lbl_phone = new JLabel("Costumer Phone No:");
		lbl_phone.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 20));
		lbl_phone.setBounds(30, 250, 200, 30);
		pane2.add(lbl_phone);

		txt_phone = new JTextField();
		txt_phone.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 20));
		txt_phone.setBounds(240, 250, 200, 30);
		pane2.add(txt_phone);

		// Labels Right side
		lbl_reg = new JLabel("Registration Fee:");
		lbl_reg.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 20));
		lbl_reg.setBounds(700, 170, 200, 30);
		pane2.add(lbl_reg);

		txt_reg = new JTextField();
		txt_reg.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 20));
		txt_reg.setText("");
		txt_reg.setBounds(870, 170, 200, 30);
		pane2.add(txt_reg);

		lbl_install = new JLabel("Insatallment:");
		lbl_install.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 20));
		lbl_install.setBounds(700, 210, 200, 30);
		pane2.add(lbl_install);

		txt_install = new JTextField();
		txt_install.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 20));
		txt_install.setBounds(830, 210, 200, 30);
		pane2.add(txt_install);

		lbl_doInstall = new JLabel("Days Of Installment:");
		lbl_doInstall.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 20));
		lbl_doInstall.setBounds(700, 250, 200, 30);
		pane2.add(lbl_doInstall);

		txt_doInstall = new JComboBox(days);
		txt_doInstall.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 20));
		txt_doInstall.setBounds(900, 250, 200, 30);
		pane2.add(txt_doInstall);

		lbl_savings = new JLabel("Savings:");
		lbl_savings.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 20));
		lbl_savings.setBounds(700, 290, 200, 30);
		pane2.add(lbl_savings);

		txt_savings = new JTextField();
		txt_savings.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 20));
		txt_savings.setBounds(790, 290, 200, 30);
		pane2.add(txt_savings);

		JLabel loanInfo = new JLabel("<html><U>Loan Information</U></html>");
		loanInfo.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 20));
		loanInfo.setBounds(30, 300, 800, 30);
		pane2.add(loanInfo);

		lbl_group = new JLabel("Specify type of loan:-");
		lbl_group.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 20));
		lbl_group.setBounds(30, 340, 250, 30);
		pane2.add(lbl_group);

		personal = new JRadioButton("Personal");
		personal.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 20));
		personal.setBounds(260, 340, 150, 30);
		personal.setContentAreaFilled(false);
		personal.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent arg0) {
				txt_group.setText("Personal");
			}

		});
		pane2.add(personal);

		group = new JRadioButton("Group");
		group.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 20));
		group.setBounds(400, 340, 100, 30);
		group.setContentAreaFilled(false);
		group.setActionCommand("Group");
		pane2.add(group);

		ButtonGroup rgp01 = new ButtonGroup();
		rgp01.add(personal);
		rgp01.add(group);

		if_group = new JLabel("Group name:");
		if_group.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 20));
		if_group.setBounds(30, 380, 200, 30);
		pane2.add(if_group);

		txt_group = new JTextField();
		txt_group.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 20));
		txt_group.setBounds(180, 380, 200, 30);
		pane2.add(txt_group);

		lbl_amount = new JLabel("Amount Loaned :");
		lbl_amount.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 20));
		lbl_amount.setBounds(30, 420, 200, 30);
		pane2.add(lbl_amount);

		txt_amount = new JTextField();
		txt_amount.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 20));
		txt_amount.setBounds(200, 420, 200, 30);
		pane2.add(txt_amount);

		lbl_interestRate = new JLabel("Current Interest Rate (%):");
		lbl_interestRate.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 20));
		lbl_interestRate.setBounds(30, 460, 280, 30);
		pane2.add(lbl_interestRate);

		txt_interestRate = new JTextField();
		txt_interestRate.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 20));
		txt_interestRate.setBounds(310, 460, 200, 30);
		pane2.add(txt_interestRate);

		lbl_period = new JLabel("Term Of Loan:");
		lbl_period.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 20));
		lbl_period.setBounds(30, 500, 200, 30);
		pane2.add(lbl_period);

		txt_period = new JTextField();
		txt_period.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 20));
		txt_period.setBounds(180, 500, 200, 30);
		pane2.add(txt_period);

		months = new JRadioButton("Months");
		months.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 20));
		months.setBounds(380, 500, 100, 30);
		months.setContentAreaFilled(false);
		pane2.add(months);

		weeks = new JRadioButton("Weeks");
		weeks.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 20));
		weeks.setBounds(490, 500, 100, 30);
		weeks.setContentAreaFilled(false);
		pane2.add(weeks);

		ButtonGroup rgp = new ButtonGroup();
		rgp.add(months);
		rgp.add(weeks);

		freq_lbl = new JLabel("Payment Frequency");
		freq_lbl.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 20));
		freq_lbl.setBounds(30, 540, 250, 30);
		pane2.add(freq_lbl);

		monthly = new JRadioButton("Monthly");
		monthly.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 20));
		monthly.setBounds(30, 580, 110, 30);
		monthly.setActionCommand("Monthly");
		monthly.setContentAreaFilled(false);
		pane2.add(monthly);

		weekly = new JRadioButton("Weekly");
		weekly.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 20));
		weekly.setBounds(140, 580, 100, 30);
		weekly.setActionCommand("Weekly");
		weekly.setContentAreaFilled(false);
		pane2.add(weekly);

		daily = new JRadioButton("Daily");
		daily.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 20));
		daily.setBounds(240, 580, 100, 30);
		daily.setActionCommand("Daily");
		daily.setContentAreaFilled(false);
		// pane2.add(daily);

		ButtonGroup rgp02 = new ButtonGroup();
		rgp02.add(monthly);
		rgp02.add(weekly);
		// rgp02.add(daily);

		JButton lbl_expAmount = new JButton("Total Amount:");
		lbl_expAmount.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 20));
		lbl_expAmount.setBounds(1000, 340, 200, 30);
		lbl_expAmount.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				try {
					int totalAmount;
					int p, r, t;
					int fre, displayfreq;

					if (weeks.isSelected()) {

						p = Integer.parseInt(txt_amount.getText());
						r = Integer.parseInt(txt_interestRate.getText());
						t = Integer.parseInt(txt_period.getText());

						totalAmount = (p * r * t / 4) / 100 + p;
						txt_expAmount.setText(" " + totalAmount);
						payfreqdisplay.setText("The Weekly Payment = N " + totalAmount / t);

					} else if (months.isSelected()) {

						p = Integer.parseInt(txt_amount.getText());
						r = Integer.parseInt(txt_interestRate.getText());
						t = Integer.parseInt(txt_period.getText());

						totalAmount = (p * r * t) / 100 + p;
						txt_expAmount.setText(" " + totalAmount);
						payfreqdisplay.setText("The Monthly Payment = N " + totalAmount / t);

					}
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Invalid Inputs!! please enter correct values.");
				}

			}
		});

		pane2.add(lbl_expAmount);

		txt_expAmount = new JLabel();
		txt_expAmount.setBounds(1015, 390, 300, 30);
		txt_expAmount.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 24));
		pane2.add(txt_expAmount);

		payfreq = new JLabel("<html><U>Payment Frequency</U></html>");
		payfreq.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 20));
		payfreq.setBounds(1000, 430, 350, 30);

		pane2.add(payfreq);

		payfreqdisplay = new JLabel("Payment frequency will display here!!");
		payfreqdisplay.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 18));
		payfreqdisplay.setBounds(950, 460, 400, 30);
		pane2.add(payfreqdisplay);

		lbl_date = new JLabel(time);
		lbl_date.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 20));
		lbl_date.setBounds(1000, 10, 300, 50);
		pane2.add(lbl_date);
		
		JButton reConn = new JButton("Re-Establish Connection");
		reConn.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 14));
		reConn.setBounds(780, 580, 200, 20);
		reConn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				reConn.setBackground(Color.cyan);
				try {
					connection = localhost.dbConnector();
					JOptionPane.showMessageDialog(null, "Connection Re-Established..!!");
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, "Could Re-Establish Connection..!!"+e1);
					e1.printStackTrace();
					reConn.setBackground(Color.red);
				}
			}
		});
		pane2.add(reConn);
		
		JButton save_Cost = new JButton("Add Costumer");
		save_Cost.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 20));
		save_Cost.setBounds(1000, 510, 200, 100);
		save_Cost.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				 try {
					connection = localhost.dbConnector();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				PreparedStatement psts = null;
				try {
					loadInData();
					saveInDays();
					insertTable();
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Error!! Please Restart The Application And Try Again.");
					ex.printStackTrace();
				}

				txt_id.setText("2017");
				txt_name.setText("");
				txt_phone.setText("");
				txt_reg.setText("");
				txt_othername.setText("");
				txt_install.setText("");
				txt_savings.setText("");
				txt_group.setText("");
				txt_amount.setText("");
				txt_interestRate.setText("");
				txt_period.setText("");
				txt_expAmount.setText("");
					}
				});
		pane2.add(save_Cost);

		ImageIcon yo02 = new ImageIcon(this.getClass().getResource("images/img.jpg"));
		JLabel well = new JLabel(yo02);
		well.setBounds(0, 0, 1330, 680);
		pane2.add(well);
	}

	protected void saveInDays() {
		if(txt_doInstall.getSelectedItem().equals("Monday")){
			saveInMonday();
		}else if(txt_doInstall.getSelectedItem().equals("Tuesday")){
			saveInTueday();
		}else if(txt_doInstall.getSelectedItem().equals("Wednesday")){
			saveInWednesday();
		}else if(txt_doInstall.getSelectedItem().equals("Thursday")){
			saveInThursday();
		}else if(txt_doInstall.getSelectedItem().equals("Friday")){
			saveInFriday();
		}else if(txt_doInstall.getSelectedItem().equals("Saturday")){
			saveInSaturday();
		}else if(txt_doInstall.getSelectedItem().equals("Sunday")){
			saveInSunday();
		}
	}

	private void saveInSunday() {
		PreparedStatement psts = null;
		
		 try {
			 
				String sql = "INSERT INTO sunday"
						+ "(Id,Surname,Firstname,RegFee,Installment,DOInstallment,Savings,Phone,Grp,Amount,Interest,Period,Total,Date) VALUES"
						+ "(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

			psts = connection.prepareStatement(sql);
			

			psts.setString(1, txt_id.getText());
			psts.setString(2, txt_name.getText());
			psts.setString(3, txt_othername.getText());
			psts.setString(4, txt_reg.getText());
			psts.setString(5, txt_install.getText());
			psts.setString(6, (String)txt_doInstall.getSelectedItem());
			psts.setString(7, txt_savings.getText());
			psts.setString(8, txt_phone.getText());
			psts.setString(9, txt_group.getText());
			psts.setString(10, txt_amount.getText());
			psts.setString(11, txt_interestRate.getText());
			psts.setString(12, txt_period.getText());
			psts.setString(13, txt_expAmount.getText());
			psts.setDate(14, new java.sql.Date(new java.util.Date().getTime()));
			psts.executeUpdate();
			
			
			//JOptionPane.showMessageDialog(null, "New Customer Added!!");
			psts.close();
			connection.setAutoCommit(true);
		} catch (SQLException e) {
			//JOptionPane.showMessageDialog(null, "Error! please restart program!");
			e.printStackTrace();
		}
		
	}
	private void saveInSaturday() {
		PreparedStatement psts = null;
		
		 try {
			 
				String sql = "INSERT INTO saturday"
						+ "(Id,Surname,Firstname,RegFee,Installment,DOInstallment,Savings,Phone,Grp,Amount,Interest,Period,Total,Date) VALUES"
						+ "(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

			psts = connection.prepareStatement(sql);
			

			psts.setString(1, txt_id.getText());
			psts.setString(2, txt_name.getText());
			psts.setString(3, txt_othername.getText());
			psts.setString(4, txt_reg.getText());
			psts.setString(5, txt_install.getText());
			psts.setString(6, (String)txt_doInstall.getSelectedItem());
			psts.setString(7, txt_savings.getText());
			psts.setString(8, txt_phone.getText());
			psts.setString(9, txt_group.getText());
			psts.setString(10, txt_amount.getText());
			psts.setString(11, txt_interestRate.getText());
			psts.setString(12, txt_period.getText());
			psts.setString(13, txt_expAmount.getText());
			psts.setDate(14, new java.sql.Date(new java.util.Date().getTime()));
			psts.executeUpdate();
			
			
			//JOptionPane.showMessageDialog(null, "New Customer Added!!");
			psts.close();
			connection.setAutoCommit(true);
		} catch (SQLException e) {
			//JOptionPane.showMessageDialog(null, "Error! please restart program!");
			e.printStackTrace();
		}
		
	}
	private void saveInFriday() {
		PreparedStatement psts = null;
		
		 try {
			 
				String sql = "INSERT INTO friday"
						+ "(Id,Surname,Firstname,RegFee,Installment,DOInstallment,Savings,Phone,Grp,Amount,Interest,Period,Total,Date) VALUES"
						+ "(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

			psts = connection.prepareStatement(sql);
			

			psts.setString(1, txt_id.getText());
			psts.setString(2, txt_name.getText());
			psts.setString(3, txt_othername.getText());
			psts.setString(4, txt_reg.getText());
			psts.setString(5, txt_install.getText());
			psts.setString(6, (String)txt_doInstall.getSelectedItem());
			psts.setString(7, txt_savings.getText());
			psts.setString(8, txt_phone.getText());
			psts.setString(9, txt_group.getText());
			psts.setString(10, txt_amount.getText());
			psts.setString(11, txt_interestRate.getText());
			psts.setString(12, txt_period.getText());
			psts.setString(13, txt_expAmount.getText());
			psts.setDate(14, new java.sql.Date(new java.util.Date().getTime()));
			psts.executeUpdate();
			
			
			//JOptionPane.showMessageDialog(null, "New Customer Added!!");
			psts.close();
			connection.setAutoCommit(true);
		} catch (SQLException e) {
			//JOptionPane.showMessageDialog(null, "Error! please restart program!");
			e.printStackTrace();
		}
	}
	private void saveInThursday() {
		PreparedStatement psts = null;
		
		 try {
			 
				String sql = "INSERT INTO thursday"
						+ "(Id,Surname,Firstname,RegFee,Installment,DOInstallment,Savings,Phone,Grp,Amount,Interest,Period,Total,Date) VALUES"
						+ "(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

			psts = connection.prepareStatement(sql);
			

			psts.setString(1, txt_id.getText());
			psts.setString(2, txt_name.getText());
			psts.setString(3, txt_othername.getText());
			psts.setString(4, txt_reg.getText());
			psts.setString(5, txt_install.getText());
			psts.setString(6, (String)txt_doInstall.getSelectedItem());
			psts.setString(7, txt_savings.getText());
			psts.setString(8, txt_phone.getText());
			psts.setString(9, txt_group.getText());
			psts.setString(10, txt_amount.getText());
			psts.setString(11, txt_interestRate.getText());
			psts.setString(12, txt_period.getText());
			psts.setString(13, txt_expAmount.getText());
			psts.setDate(14, new java.sql.Date(new java.util.Date().getTime()));
			psts.executeUpdate();
			
			
			//JOptionPane.showMessageDialog(null, "New Customer Added!!");
			psts.close();
			connection.setAutoCommit(true);
		} catch (SQLException e) {
			//JOptionPane.showMessageDialog(null, "Error! please restart program!");
			e.printStackTrace();
		}
		
	}
	private void saveInWednesday() {
		PreparedStatement psts = null;
		
		 try {
			 
				String sql = "INSERT INTO wednesday"
						+ "(Id,Surname,Firstname,RegFee,Installment,DOInstallment,Savings,Phone,Grp,Amount,Interest,Period,Total,Date) VALUES"
						+ "(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

			psts = connection.prepareStatement(sql);
			

			psts.setString(1, txt_id.getText());
			psts.setString(2, txt_name.getText());
			psts.setString(3, txt_othername.getText());
			psts.setString(4, txt_reg.getText());
			psts.setString(5, txt_install.getText());
			psts.setString(6, (String)txt_doInstall.getSelectedItem());
			psts.setString(7, txt_savings.getText());
			psts.setString(8, txt_phone.getText());
			psts.setString(9, txt_group.getText());
			psts.setString(10, txt_amount.getText());
			psts.setString(11, txt_interestRate.getText());
			psts.setString(12, txt_period.getText());
			psts.setString(13, txt_expAmount.getText());
			psts.setDate(14, new java.sql.Date(new java.util.Date().getTime()));
			psts.executeUpdate();
			
			
			//JOptionPane.showMessageDialog(null, "New Customer Added!!");
			psts.close();
			connection.setAutoCommit(true);
		} catch (SQLException e) {
			//JOptionPane.showMessageDialog(null, "Error! please restart program!");
			e.printStackTrace();
		}
		
	}
	private void saveInTueday() {
		PreparedStatement psts = null;
		
		 try {
			 
				String sql = "INSERT INTO tuesday"
						+ "(Id,Surname,Firstname,RegFee,Installment,DOInstallment,Savings,Phone,Grp,Amount,Interest,Period,Total,Date) VALUES"
						+ "(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

			psts = connection.prepareStatement(sql);
			

			psts.setString(1, txt_id.getText());
			psts.setString(2, txt_name.getText());
			psts.setString(3, txt_othername.getText());
			psts.setString(4, txt_reg.getText());
			psts.setString(5, txt_install.getText());
			psts.setString(6, (String)txt_doInstall.getSelectedItem());
			psts.setString(7, txt_savings.getText());
			psts.setString(8, txt_phone.getText());
			psts.setString(9, txt_group.getText());
			psts.setString(10, txt_amount.getText());
			psts.setString(11, txt_interestRate.getText());
			psts.setString(12, txt_period.getText());
			psts.setString(13, txt_expAmount.getText());
			psts.setDate(14, new java.sql.Date(new java.util.Date().getTime()));
			psts.executeUpdate();
			
			
			//JOptionPane.showMessageDialog(null, "New Customer Added!!");
			psts.close();
			connection.setAutoCommit(true);
		} catch (SQLException e) {
			//JOptionPane.showMessageDialog(null, "Error! please restart program!");
			e.printStackTrace();
		}
		
	}

	private void saveInMonday() {
		PreparedStatement psts = null;
		
		 try {
			 
				String sql = "INSERT INTO monday"
						+ "(Id,Surname,Firstname,RegFee,Installment,DOInstallment,Savings,Phone,Grp,Amount,Interest,Period,Total,Date) VALUES"
						+ "(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

			psts = connection.prepareStatement(sql);
			

			psts.setString(1, txt_id.getText());
			psts.setString(2, txt_name.getText());
			psts.setString(3, txt_othername.getText());
			psts.setString(4, txt_reg.getText());
			psts.setString(5, txt_install.getText());
			psts.setString(6, (String)txt_doInstall.getSelectedItem());
			psts.setString(7, txt_savings.getText());
			psts.setString(8, txt_phone.getText());
			psts.setString(9, txt_group.getText());
			psts.setString(10, txt_amount.getText());
			psts.setString(11, txt_interestRate.getText());
			psts.setString(12, txt_period.getText());
			psts.setString(13, txt_expAmount.getText());
			psts.setDate(14, new java.sql.Date(new java.util.Date().getTime()));
			psts.executeUpdate();
			
			
			//JOptionPane.showMessageDialog(null, "New Customer Added!!");
			psts.close();
			connection.setAutoCommit(true);
		} catch (SQLException e) {
			//JOptionPane.showMessageDialog(null, "Error! please restart program!");
			e.printStackTrace();
		}

	}
		

	//show General Table
	public void showTable() {

		pane3 = new JPanel();

		table = new JTable();
		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);

		JLabel lblHeading = new JLabel("Customer Table");
		lblHeading.setFont(new Font("Arial", Font.TRUETYPE_FONT, 24));

		pane3.setLayout(new BorderLayout());

		pane3.add(lblHeading, BorderLayout.PAGE_START);
		pane3.add(scrollPane, BorderLayout.CENTER);

		ArrayList<User> list = userList();
		DefaultTableModel model = (DefaultTableModel) table.getModel();

		Object[] row = new Object[13];
		for (int i = 0; i < list.size(); i++) {
			row[0] = list.get(i).getcode_num();
			row[1] = list.get(i).getname();
			row[2] = list.get(i).getsurname();
			row[3] = list.get(i).getreg();
			row[4] = list.get(i).getinstall();
			row[5] = list.get(i).getdoinstall();
			row[6] = list.get(i).getsavings();
			row[7] = list.get(i).getphone();
			row[8] = list.get(i).getgrp();
			row[9] = list.get(i).getamount();
			row[10] = list.get(i).getperiod();
			row[11] = list.get(i).gettotal();
			row[12] = list.get(i).getdate();
			model.addRow(row);
			model.setColumnIdentifiers(new Object[] { "Customer Code", "Surname", "Other Names", "Registration Fee",
					"Installment", "Days Of Installment", "Savings", "Phone", "Principal", "Name Of Group", "Period",
					"Cumulative", "Date Of 1st Installment"

			});
		}

		Timer timer = new Timer(125, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				table.repaint();
			}
		});
		timer.start();

	}

	public ArrayList<User> userList() {
		try{
		
	
			connection = localhost.dbConnector();
ArrayList<User> usersList = new ArrayList();
try {
		String query = "Select * from costumerTable";
		Statement st = connection.createStatement();
		ResultSet rs = st.executeQuery(query);
		User user;
		while (rs.next()) {
			user = new User(rs.getString("Id"), rs.getString("Surname"), rs.getString("Firstname"),
					rs.getString("RegFee"), rs.getString("Installment"), rs.getString("DOInstallment"),
					rs.getString("Savings"), rs.getString("Phone"), rs.getString("Grp"), rs.getString("Amount"),
					rs.getString("Period"), rs.getString("Total"), rs.getString("Date"));
			usersList.add(user);
		}
		rs.close();
} catch (Exception e) {
    JOptionPane.showMessageDialog(null, "Table Cannot Be Displayed Due To Poor Conection");
    e.printStackTrace();
  }
  return usersList;
}
catch (Exception e1)
{
  e1.printStackTrace();
}
return null;
}
	// Showing cusstomer table
	public void showCustomerTable() {

		pane5 = new JPanel();

		table02 = new JTable();
		JScrollPane scrollPane02 = new JScrollPane(table02);
		table02.setFillsViewportHeight(true);

		JLabel lbl_s = new JLabel();
		lbl_s.setFont(new Font("Arial", Font.TRUETYPE_FONT, 24));

		JPanel jayp = new JPanel();
		jayp.setLayout(new GridLayout(1,1));
		
		JButton clrbutt = new JButton("Clear Table");
		clrbutt.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 30));
		clrbutt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				lbl_s.setText("");
				model02.setRowCount(0);
			}

		});
		search_butts = new JButton("Search");
		search_butts.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 30));
		search_butts.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {

				try {
					lbl_s.setText(txt_name02.getText());

					ArrayList<Detail> list = userpayList();
					 model02 = (DefaultTableModel) table02.getModel();

					row02 = new Object[6];
					for (int i = 0; i < list.size(); i++) {
						row02[0] = list.get(i).getcode_num();
						row02[1] = list.get(i).getPaytype();
						row02[2] = list.get(i).getpayment();
						row02[3] = list.get(i).getInsurance();
						row02[4] = list.get(i).getCumulative();
						row02[5] = list.get(i).getdate();

						model02.addRow(row02);
						model02.setColumnIdentifiers(new Object[] { "Customer Code", "Payment", "Savings", "Cumulative",
								"Insurance", "Date Of Installment"

						});

					}

				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null,
							"Please Go Back to 'UPDATE Customer' and Enter The Customer ID-Number");
				}
				count++;
				if (count >= 1)
					search_butts.setEnabled(false);
			}

		});

		jayp.add(search_butts);
		jayp.add(clrbutt);
		pane5.setLayout(new BorderLayout());

		pane5.add(jayp, BorderLayout.AFTER_LAST_LINE);
		pane5.add(lbl_s, BorderLayout.PAGE_START);
		pane5.add(scrollPane02, BorderLayout.CENTER);
	}

	public ArrayList<Detail> userpayList() {
		
		try{
			
			connection = localhost.dbConnector();
ArrayList<Detail> usersList02 = new ArrayList();
try {
		String query = "Select * from " + txt_name02.getText() + "";
		Statement st = connection.createStatement();
		ResultSet rs = st.executeQuery(query);
		Detail use;
		while (rs.next()) {
			use = new Detail(rs.getString("Id"), rs.getString("Fullname"), rs.getString("Insurance"),
					rs.getString("Payment"), rs.getString("PayType"), rs.getString("Insurance"),
					rs.getString("Principal"), rs.getString("Cumulative"), rs.getString("Date"),
					rs.getString("Date"), rs.getString("Date"));
			usersList02.add(use);
		}
		rs.close();
} catch (Exception e) {
    JOptionPane.showMessageDialog(null, "Table Cannot Be Displayed Due To Poor Conection");
    e.printStackTrace();
  }
  return usersList02;
}
catch (Exception e1)
{
  e1.printStackTrace();
}
return null;
}
	// end customer table

	private void updateCostumer() {

		pane4 = new Container();
		JLabel title02 = new JLabel("Search & Update Costumer Details");
		title02.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 20));
		title02.setBounds(300, 20, 450, 50);
		pane4.add(title02);

		JLabel search_lbl = new JLabel("Search Costumer Using ID");
		search_lbl.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 13));
		search_lbl.setBounds(30, 80, 450, 30);
		pane4.add(search_lbl);

		JTextField search_txt = new JTextField();
		search_txt.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 16));
		search_txt.setText("2017");
		search_txt.setBounds(30, 120, 150, 20);
		pane4.add(search_txt);

		JPanel boxPane = new JPanel();
		boxPane.setLayout(new GridLayout(9, 9));
		boxPane.setBounds(30, 160, 350, 350);

		JPanel myPanel=new JPanel();
		myPanel.setLayout(new GridBagLayout());
		
		monday();
		tuesday();
		wednesday();
		thursday();
		friday();
		saturday();
		sunday();
		
		JTabbedPane jTP=new JTabbedPane();
		jTP.add("Monday",tablePane);
		jTP.add("Tuesday",tp02);
		jTP.add("Wednesday",tp03);
		jTP.add("Thursday",tp04);
		jTP.add("Friday",tp05);
		jTP.add("Saturday",tp06);
		jTP.add("Sunday",tp07);//substitute your component instead of "new JPanel"
		GridBagConstraints myConstraints=new GridBagConstraints();
		myConstraints.ipadx=860;//streches the component being added along x axis - 200 px on both sides
		myConstraints.ipady=410;//streches the component being added along y axis - 200 px on both sides
		myPanel.add(jTP,myConstraints);
		myPanel.setBounds(430, 100, 900, 410);
		pane4.add(myPanel);
		
		
		// Labels
		lbl_id = new JLabel("Costumer ID :");
		lbl_id.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 20));

		lbl_name = new JLabel("Costumer Name :");
		lbl_name.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 20));

		lbl_phone = new JLabel("Costumer Phone No: :");
		lbl_phone.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 20));

		lbl_group = new JLabel("Costumer Group:");
		lbl_group.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 20));

		lbl_amount = new JLabel("Amount Paid :");
		lbl_amount.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 20));

		lbl_interestRate = new JLabel("Insurance :");
		lbl_interestRate.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 20));

		lbl_period = new JLabel("Savings :");
		lbl_period.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 20));

		lbl_expAmount = new JLabel("New Amount :");
		lbl_expAmount.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 20));

		// Fields
		JTextField txt_id02 = new JTextField();
		txt_id02.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 20));
		txt_id02.setEditable(false);

		txt_name02 = new JTextField();
		txt_name02.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 20));
		txt_name02.setEditable(false);

		JTextField txt_phone02 = new JTextField();
		txt_phone02.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 20));
		txt_phone02.setEditable(false);

		JTextField txt_group02 = new JTextField();
		txt_group02.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 20));
		txt_group02.setEditable(false);

		JTextField txt_amount02 = new JTextField();
		txt_amount02.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 20));

		JTextField txt_insurance = new JTextField();
		txt_insurance.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 20));

		JTextField txt_payType = new JTextField();
		txt_payType.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 20));

		JTextField txt_newAmount = new JTextField();
		txt_newAmount.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 20));

		boxPane.add(lbl_id);
		boxPane.add(txt_id02);

		boxPane.add(lbl_name);
		boxPane.add(txt_name02);

		boxPane.add(lbl_phone);
		boxPane.add(txt_phone02);

		boxPane.add(lbl_group);
		boxPane.add(txt_group02);

		boxPane.add(lbl_amount);
		boxPane.add(txt_amount02);

		boxPane.add(lbl_interestRate);
		boxPane.add(txt_insurance);

		boxPane.add(lbl_period);
		boxPane.add(txt_payType);

		boxPane.add(lbl_expAmount);
		boxPane.add(txt_newAmount);

		pane4.add(boxPane);

		JButton search_butt = new JButton("Search");
		search_butt.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 16));
		search_butt.setBounds(200, 120, 150, 20);
		search_butt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				search_butts.setEnabled(true);
				if (search_txt.getText().length() == 0) {
					JOptionPane.showMessageDialog(null, "Empty fields detected! please fill up fields");

				} else {

					validate_login();
				}
			}

			private boolean validate_login() {
				try {
					
					connection = localhost.dbConnector();
					 
					String query = "SELECT * FROM `costumerTable` where Id=?";
					PreparedStatement pst = connection.prepareStatement(query);

					pst.setString(1, search_txt.getText());

					ResultSet result = pst.executeQuery();
					while (result.next()) {

						id = result.getString("Id").trim();
						fullname = result.getString("Surname").trim();
						phone = result.getString("Phone").trim();
						grp = result.getString("Grp").trim();

						txt_id02.setText(id);
						txt_name02.setText(fullname);
						txt_phone02.setText(phone);
						txt_group02.setText(grp);

						System.out.println(id);
						System.out.println(fullname);
						System.out.println(phone);

					}
					search_txt.setText("2017");

					result.close();
					pst.close();

				} catch (Exception ex) {
					ex.printStackTrace();
				}
				return false;

			}
		});
		pane4.add(search_butt);
		
		JButton clr_cost = new JButton("Clear");
		clr_cost.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 20));
		clr_cost.setBounds(220, 570, 150, 30);
		clr_cost.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent ev){
				txt_id02.setText("");
				txt_name02.setText("");
				txt_phone02.setText("");
				txt_group02.setText("");
				txt_amount02.setText("");
				txt_insurance.setText("");
				txt_payType.setText("");
				txt_newAmount.setText("");
			}
		});
		pane4.add(clr_cost);

		JButton save_Cost02 = new JButton("Update");
		save_Cost02.setFont(new Font("TimesRoman", Font.BOLD | Font.ITALIC, 20));
		save_Cost02.setBounds(50, 570, 150, 30);
		save_Cost02.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				try {

					connection = localhost.dbConnector();
					 
					String sql = "INSERT INTO " + txt_name02.getText() + ""
							+ "(Id,Fullname,Grp,Payment,PayType,Insurance,Cumulative,Date,Principal) VALUES"
							+ "(?,?,?,?,?,?,?,?,?)";

					PreparedStatement pst = connection.prepareStatement(sql);

					pst.setString(1, txt_id02.getText());
					pst.setString(2, txt_name02.getText());
					pst.setString(3, txt_group02.getText());
					pst.setString(4, txt_amount02.getText());
					pst.setString(5, txt_payType.getText());
					pst.setString(6, txt_insurance.getText());
					pst.setString(7, txt_newAmount.getText());
					pst.setDate(8, new java.sql.Date(new java.util.Date().getTime()));
					pst.setString(9, txt_amount02.getText());
					pst.executeUpdate();

					JOptionPane.showMessageDialog(null, "Records Updated!!");
					pst.close();

				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Records Not Updated!!");
					ex.printStackTrace();
				}

				txt_id02.setText("");
				txt_name02.setText("");
				txt_phone02.setText("");
				txt_group02.setText("");
				txt_amount02.setText("");
				txt_insurance.setText("");
				txt_payType.setText("");
				txt_newAmount.setText("");
			}
		});
		pane4.add(save_Cost02);

		ImageIcon yo03 = new ImageIcon(this.getClass().getResource("images/img.jpg"));
		JLabel well03 = new JLabel(yo03);
		well03.setBounds(0, 0, 1330, 680);
		pane4.add(well03);
	}

	private void sunday() {
		tp07 = new JPanel();
		//tablePane.setBackground(Color.cyan);
		table = new JTable();
		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);

		JLabel lblHeading = new JLabel("Sunday");
		lblHeading.setFont(new Font("Arial", Font.TRUETYPE_FONT, 24));

		tp07.setLayout(new BorderLayout());

		tp07.add(lblHeading, BorderLayout.PAGE_START);
		tp07.add(scrollPane, BorderLayout.CENTER);

		ArrayList<Dates> list = userLists07();
		DefaultTableModel model = (DefaultTableModel) table.getModel();

		Object[] row = new Object[8];
		for (int i = 0; i < list.size(); i++) {
			row[0] = list.get(i).getcode_num();
			row[1] = list.get(i).getname();
			row[2] = list.get(i).getsurname();
			row[3] = list.get(i).getinstall();
			row[4] = list.get(i).getsavings();
			row[5] = list.get(i).getamount();
			row[6] = list.get(i).gettotal();
			row[7] = list.get(i).getdate();
			model.addRow(row);
			model.setColumnIdentifiers(new Object[] { "Code", "Surname", "Other Names",
					"Installment", "Savings", "Principal",
					"Cumulative", "Date"

			});
		}

		Timer timer = new Timer(125, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				table.repaint();
			}
		});
		timer.start();

	}

	public ArrayList<Dates> userLists07() {
		try{
		
	
			 connection = ds.getConnection();

ArrayList<Dates> usersLists = new ArrayList();
try {
		String query = "Select * from sunday";
		Statement st = connection.createStatement();
		ResultSet rs = st.executeQuery(query);
		Dates users;
		while (rs.next()) {
			users = new Dates(rs.getString("Id"),rs.getString("Surname"),rs.getString("Firstname"),
					 rs.getString("Installment"),rs.getString("Savings"), rs.getString("Amount"), rs.getString("Total"), rs.getString("Date"));
			usersLists.add(users);
		}
		rs.close();
} catch (Exception e) {
    JOptionPane.showMessageDialog(null, "Table Cannot Be Displayed Due To Poor Conection");
    e.printStackTrace();
  }
  return usersLists;
}
catch (Exception e1)
{
  e1.printStackTrace();
}
return null;
}
		
	private void saturday() {
		tp06 = new JPanel();
		//tablePane.setBackground(Color.cyan);
		table = new JTable();
		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);

		JLabel lblHeading = new JLabel("Saturday");
		lblHeading.setFont(new Font("Arial", Font.TRUETYPE_FONT, 24));

		tp06.setLayout(new BorderLayout());

		tp06.add(lblHeading, BorderLayout.PAGE_START);
		tp06.add(scrollPane, BorderLayout.CENTER);

		ArrayList<Dates> list = userLists06();
		DefaultTableModel model = (DefaultTableModel) table.getModel();

		Object[] row = new Object[8];
		for (int i = 0; i < list.size(); i++) {
			row[0] = list.get(i).getcode_num();
			row[1] = list.get(i).getname();
			row[2] = list.get(i).getsurname();
			row[3] = list.get(i).getinstall();
			row[4] = list.get(i).getsavings();
			row[5] = list.get(i).getamount();
			row[6] = list.get(i).gettotal();
			row[7] = list.get(i).getdate();
			model.addRow(row);
			model.setColumnIdentifiers(new Object[] { "Code", "Surname", "Other Names",
					"Installment", "Savings", "Principal",
					"Cumulative", "Date"

			});
		}

		Timer timer = new Timer(125, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				table.repaint();
			}
		});
		timer.start();

	}

	public ArrayList<Dates> userLists06() {
		try{
		
	
			 connection = ds.getConnection();
ArrayList<Dates> usersLists = new ArrayList();
try {
		String query = "Select * from saturday";
		Statement st = connection.createStatement();
		ResultSet rs = st.executeQuery(query);
		Dates users;
		while (rs.next()) {
			users = new Dates(rs.getString("Id"),rs.getString("Surname"),rs.getString("Firstname"),
					 rs.getString("Installment"),rs.getString("Savings"), rs.getString("Amount"), rs.getString("Total"), rs.getString("Date"));
			usersLists.add(users);
		}
		rs.close();
} catch (Exception e) {
    JOptionPane.showMessageDialog(null, "Table Cannot Be Displayed Due To Poor Conection");
    e.printStackTrace();
  }
  return usersLists;
}
catch (Exception e1)
{
  e1.printStackTrace();
}
return null;
		
	}
	private void friday() {
		tp05 = new JPanel();
		//tablePane.setBackground(Color.cyan);
		table = new JTable();
		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);

		JLabel lblHeading = new JLabel("Friday");
		lblHeading.setFont(new Font("Arial", Font.TRUETYPE_FONT, 24));

		tp05.setLayout(new BorderLayout());

		tp05.add(lblHeading, BorderLayout.PAGE_START);
		tp05.add(scrollPane, BorderLayout.CENTER);

		ArrayList<Dates> list = userLists05();
		DefaultTableModel model = (DefaultTableModel) table.getModel();

		Object[] row = new Object[8];
		for (int i = 0; i < list.size(); i++) {
			row[0] = list.get(i).getcode_num();
			row[1] = list.get(i).getname();
			row[2] = list.get(i).getsurname();
			row[3] = list.get(i).getinstall();
			row[4] = list.get(i).getsavings();
			row[5] = list.get(i).getamount();
			row[6] = list.get(i).gettotal();
			row[7] = list.get(i).getdate();
			model.addRow(row);
			model.setColumnIdentifiers(new Object[] { "Code", "Surname", "Other Names",
					"Installment", "Savings", "Principal",
					"Cumulative", "Date"

			});
		}

		Timer timer = new Timer(125, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				table.repaint();
			}
		});
		timer.start();

	}

	public ArrayList<Dates> userLists05() {
		try{
		
	
			connection = localhost.dbConnector();
ArrayList<Dates> usersLists = new ArrayList();
try {
		String query = "Select * from friday";
		Statement st = connection.createStatement();
		ResultSet rs = st.executeQuery(query);
		Dates users;
		while (rs.next()) {
			users = new Dates(rs.getString("Id"),rs.getString("Surname"),rs.getString("Firstname"),
					 rs.getString("Installment"),rs.getString("Savings"), rs.getString("Amount"), rs.getString("Total"), rs.getString("Date"));
			usersLists.add(users);
		}
		rs.close();
} catch (Exception e) {
    JOptionPane.showMessageDialog(null, "Table Cannot Be Displayed Due To Poor Conection");
    e.printStackTrace();
  }
  return usersLists;
}
catch (Exception e1)
{
  e1.printStackTrace();
}
return null;
	}
	private void thursday() {
		tp04 = new JPanel();
		//tablePane.setBackground(Color.cyan);
		table = new JTable();
		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);

		JLabel lblHeading = new JLabel("Thursday");
		lblHeading.setFont(new Font("Arial", Font.TRUETYPE_FONT, 24));

		tp04.setLayout(new BorderLayout());

		tp04.add(lblHeading, BorderLayout.PAGE_START);
		tp04.add(scrollPane, BorderLayout.CENTER);

		ArrayList<Dates> list = userLists04();
		DefaultTableModel model = (DefaultTableModel) table.getModel();

		Object[] row = new Object[8];
		for (int i = 0; i < list.size(); i++) {
			row[0] = list.get(i).getcode_num();
			row[1] = list.get(i).getname();
			row[2] = list.get(i).getsurname();
			row[3] = list.get(i).getinstall();
			row[4] = list.get(i).getsavings();
			row[5] = list.get(i).getamount();
			row[6] = list.get(i).gettotal();
			row[7] = list.get(i).getdate();
			model.addRow(row);
			model.setColumnIdentifiers(new Object[] { "Code", "Surname", "Other Names",
					"Installment", "Savings", "Principal",
					"Cumulative", "Date"

			});
		}

		Timer timer = new Timer(125, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				table.repaint();
			}
		});
		timer.start();

	}

	public ArrayList<Dates> userLists04() {
		try{
		
	
			connection = localhost.dbConnector();
ArrayList<Dates> usersLists = new ArrayList();
try {
		String query = "Select * from thursday";
		Statement st = connection.createStatement();
		ResultSet rs = st.executeQuery(query);
		Dates users;
		while (rs.next()) {
			users = new Dates(rs.getString("Id"),rs.getString("Surname"),rs.getString("Firstname"),
					 rs.getString("Installment"),rs.getString("Savings"), rs.getString("Amount"), rs.getString("Total"), rs.getString("Date"));
			usersLists.add(users);
		}
		rs.close();
} catch (Exception e) {
    JOptionPane.showMessageDialog(null, "Table Cannot Be Displayed Due To Poor Conection");
    e.printStackTrace();
  }
  return usersLists;
}
catch (Exception e1)
{
  e1.printStackTrace();
}
return null;
		
	}
	private void wednesday() {
		tp03 = new JPanel();
		//tablePane.setBackground(Color.cyan);
		table = new JTable();
		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);

		JLabel lblHeading = new JLabel("Wednesday");
		lblHeading.setFont(new Font("Arial", Font.TRUETYPE_FONT, 24));

		tp03.setLayout(new BorderLayout());

		tp03.add(lblHeading, BorderLayout.PAGE_START);
		tp03.add(scrollPane, BorderLayout.CENTER);

		ArrayList<Dates> list = userLists03();
		DefaultTableModel model = (DefaultTableModel) table.getModel();

		Object[] row = new Object[8];
		for (int i = 0; i < list.size(); i++) {
			row[0] = list.get(i).getcode_num();
			row[1] = list.get(i).getname();
			row[2] = list.get(i).getsurname();
			row[3] = list.get(i).getinstall();
			row[4] = list.get(i).getsavings();
			row[5] = list.get(i).getamount();
			row[6] = list.get(i).gettotal();
			row[7] = list.get(i).getdate();
			model.addRow(row);
			model.setColumnIdentifiers(new Object[] { "Code", "Surname", "Other Names",
					"Installment", "Savings", "Principal",
					"Cumulative", "Date"

			});
		}

		Timer timer = new Timer(125, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				table.repaint();
			}
		});
		timer.start();

	}

	public ArrayList<Dates> userLists03() {
		try{
		
	
			connection = localhost.dbConnector();
ArrayList<Dates> usersLists = new ArrayList();
try {
		String query = "Select * from wednesday";
		Statement st = connection.createStatement();
		ResultSet rs = st.executeQuery(query);
		Dates users;
		while (rs.next()) {
			users = new Dates(rs.getString("Id"),rs.getString("Surname"),rs.getString("Firstname"),
					 rs.getString("Installment"),rs.getString("Savings"), rs.getString("Amount"), rs.getString("Total"), rs.getString("Date"));
			usersLists.add(users);
		}
		rs.close();
} catch (Exception e) {
    JOptionPane.showMessageDialog(null, "Table Cannot Be Displayed Due To Poor Conection");
    e.printStackTrace();
  }
  return usersLists;
}
catch (Exception e1)
{
  e1.printStackTrace();
}
return null;
		
	}
	private void tuesday() {
		tp02 = new JPanel();
		//tablePane.setBackground(Color.cyan);
		table = new JTable();
		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);

		JLabel lblHeading = new JLabel("Tuesday");
		lblHeading.setFont(new Font("Arial", Font.TRUETYPE_FONT, 24));

		tp02.setLayout(new BorderLayout());

		tp02.add(lblHeading, BorderLayout.PAGE_START);
		tp02.add(scrollPane, BorderLayout.CENTER);

		ArrayList<Dates> list = userLists02();
		DefaultTableModel model = (DefaultTableModel) table.getModel();

		Object[] row = new Object[8];
		for (int i = 0; i < list.size(); i++) {
			row[0] = list.get(i).getcode_num();
			row[1] = list.get(i).getname();
			row[2] = list.get(i).getsurname();
			row[3] = list.get(i).getinstall();
			row[4] = list.get(i).getsavings();
			row[5] = list.get(i).getamount();
			row[6] = list.get(i).gettotal();
			row[7] = list.get(i).getdate();
			model.addRow(row);
			model.setColumnIdentifiers(new Object[] { "Code", "Surname", "Other Names",
					"Installment", "Savings", "Principal",
					"Cumulative", "Date"

			});
		}

		Timer timer = new Timer(125, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				table.repaint();
			}
		});
		timer.start();

	}

	public ArrayList<Dates> userLists02() {
		try{
		
	
			connection = localhost.dbConnector();

ArrayList<Dates> usersLists = new ArrayList();
try {
		String query = "Select * from tuesday";
		Statement st = connection.createStatement();
		ResultSet rs = st.executeQuery(query);
		Dates users;
		while (rs.next()) {
			users = new Dates(rs.getString("Id"),rs.getString("Surname"),rs.getString("Firstname"),
					 rs.getString("Installment"),rs.getString("Savings"), rs.getString("Amount"), rs.getString("Total"), rs.getString("Date"));
			usersLists.add(users);
		}
		rs.close();
} catch (Exception e) {
    JOptionPane.showMessageDialog(null, "Table Cannot Be Displayed Due To Poor Conection");
    e.printStackTrace();
  }
  return usersLists;
}
catch (Exception e1)
{
  e1.printStackTrace();
}
return null;
	}
	private void monday() {
		tablePane = new JPanel();
		//tablePane.setBackground(Color.cyan);
		table = new JTable();
		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);

		JLabel lblHeading = new JLabel("Monday");
		lblHeading.setFont(new Font("Arial", Font.TRUETYPE_FONT, 24));

		tablePane.setLayout(new BorderLayout());

		tablePane.add(lblHeading, BorderLayout.PAGE_START);
		tablePane.add(scrollPane, BorderLayout.CENTER);

		ArrayList<Dates> list = userLists();
		DefaultTableModel model = (DefaultTableModel) table.getModel();

		Object[] row = new Object[8];
		for (int i = 0; i < list.size(); i++) {
			row[0] = list.get(i).getcode_num();
			row[1] = list.get(i).getname();
			row[2] = list.get(i).getsurname();
			row[3] = list.get(i).getinstall();
			row[4] = list.get(i).getsavings();
			row[5] = list.get(i).getamount();
			row[6] = list.get(i).gettotal();
			row[7] = list.get(i).getdate();
			model.addRow(row);
			model.setColumnIdentifiers(new Object[] { "Code", "Surname", "Other Names",
					"Installment", "Savings", "Principal",
					"Cumulative", "Date"

			});
		}

		Timer timer = new Timer(125, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				table.repaint();
			}
		});
		timer.start();

	}

	public ArrayList<Dates> userLists() {
		try{
		
	
			connection = localhost.dbConnector();;

ArrayList<Dates> usersLists = new ArrayList();
try {
		String query = "Select * from monday";
		Statement st = connection.createStatement();
		ResultSet rs = st.executeQuery(query);
		Dates users;
		while (rs.next()) {
			users = new Dates(rs.getString("Id"),rs.getString("Surname"),rs.getString("Firstname"),
					 rs.getString("Installment"),rs.getString("Savings"), rs.getString("Amount"), rs.getString("Total"), rs.getString("Date"));
			usersLists.add(users);
		}
		rs.close();
} catch (Exception e) {
    JOptionPane.showMessageDialog(null, "Table Cannot Be Displayed Due To Poor Conection");
    e.printStackTrace();
  }
  return usersLists;
}
catch (Exception e1)
{
  e1.printStackTrace();
}
return null;
}
		
	
	public void insertTable() {
		// surround connection with try/catch to handle exceptions
		try {
			// call JDBC connection class
			Class.forName("com.mysql.jdbc.Driver");
			// instantiate connection variable and use deriver manager
			Connection c = DriverManager.getConnection("jdbc:mysql://www.ngg.company:3306/costumerdata", "mimix",
					"Jonbellion2017");
			System.out.println("Opened Database successfully!!");

			Statement stmt = c.createStatement();
			String sql = "CREATE TABLE " + txt_name.getText() + "  ( " + "Id INTEGER NOT NULL, "
					+ "Fullname varchar(40) NOT NULL, " + "Grp varchar(20) NOT NULL, "
					+ "Payment varchar(20) NOT NULL, " + "PayType varchar(20) NOT NULL, "
					+ "Insurance varchar(20) NOT NULL, " + "Principal varchar(20) NOT NULL, "
					+ "Cumulative varchar(20) NOT NULL, " + "Date char(50))";
			stmt.executeUpdate(sql);

			stmt.close();
			// close connection
			c.close();
		} catch (Exception ex) {
			// error handlers

			System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
			System.exit(0);
		}
		System.out.println("Records Created successfully!!");

	}
	
	public void loadInData(){
		
		PreparedStatement psts = null;
	
		 try {
			 
				String sql = "INSERT INTO costumerTable"
						+ "(Id,Surname,Firstname,RegFee,Installment,DOInstallment,Savings,Phone,Grp,Amount,Interest,Period,Total,Date) VALUES"
						+ "(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

			psts = connection.prepareStatement(sql);
			

			psts.setString(1, txt_id.getText());
			psts.setString(2, txt_name.getText());
			psts.setString(3, txt_othername.getText());
			psts.setString(4, txt_reg.getText());
			psts.setString(5, txt_install.getText());
			psts.setString(6, (String)txt_doInstall.getSelectedItem());
			psts.setString(7, txt_savings.getText());
			psts.setString(8, txt_phone.getText());
			psts.setString(9, txt_group.getText());
			psts.setString(10, txt_amount.getText());
			psts.setString(11, txt_interestRate.getText());
			psts.setString(12, txt_period.getText());
			psts.setString(13, txt_expAmount.getText());
			psts.setDate(14, new java.sql.Date(new java.util.Date().getTime()));
			psts.executeUpdate();
			
			
			JOptionPane.showMessageDialog(null, "New Customer Added!!");
			psts.close();
			connection.setAutoCommit(true);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error! please restart program!");
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("com.jtattoo.plaf.mcwin.McWinLookAndFeel");
			Test n = new Test();
			n.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
			n.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent we) {
					try {

						String ObjButtons[] = { "Yes", "No" };
						int PromptResult = JOptionPane.showOptionDialog(null, "Are you sure you want to exit Domiaye?",
								"Domiaye", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, ObjButtons,
								ObjButtons[1]);
						if (PromptResult == JOptionPane.YES_OPTION) {
							System.exit(0);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
