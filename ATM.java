import javax.swing.*;
import javax.swing.text.Position;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class ATM implements ActionListener{
		JFrame f,f1,f2,f3,f4;
		JLabel l1,l2,l3,l4,l5,l6;
		JTextField tf1,tf2,tf3,tf4,tf5,tf6,acc_no,amount;
//		JPasswordField p1;
		static JButton b1,b2,b3,insert_btn,balance_inq,withdraw,transfer,deposit,exit,deposit_btn,withdraw_btn,transfer_btn,transfer_money;
		JCheckBox ch1,ch2;
		JPanel panel;
		JTable table;
		
		
		public static Connection getConnection() throws ClassNotFoundException, SQLException{
			String dbDriver="com.mysql.cj.jdbc.Driver";
			String dburl="jdbc:mysql://localhost:3306/atm";
			String username="root";
			String password="";
			Class.forName(dbDriver);
			Connection con=DriverManager.getConnection(dburl,username,password);
			return con;
		}
		
		
		
void Signup_screen() {
	f1 = new JFrame();
	l4 = new JLabel("Card No.");
	l4.setBounds(20,100,200,30);
	tf3 = new JTextField();
	tf3.setBounds(100,100,200,30);
	
	l5 = new JLabel("Pin No.");
	l5.setBounds(20,140,200,30);
	tf4 = new JTextField();
	tf4.setBounds(100,140,200,30);
	
	insert_btn = new JButton("Create your account");
	insert_btn.setBounds(100,180,200,30);
	insert_btn.addActionListener(this);
	
	f1.add(tf3);
	f1.add(l4);
	f1.add(l5);
	f1.add(tf4);
	f1.add(insert_btn);
	f1.setSize(400,400);
	f1.setLayout(new BorderLayout());
	f1.setVisible(true);
	
}
void insert() throws ClassNotFoundException, SQLException{
	int status=0;
	String s1 = tf3.getText();
	String s2 = tf4.getText();
	
	Connection con=ATM.getConnection();
	PreparedStatement ps = con.prepareStatement("insert into users(Card_No,Pin_No)values(?,?)");
	ps.setString(1, s1);
	ps.setString(2, s2);
	status=ps.executeUpdate();
	if (status>0) {
		JOptionPane.showMessageDialog(insert_btn, "Congrats, your account has been successfully created.");
		f1.dispose();
	}else {
		JOptionPane.showMessageDialog(insert_btn, "SomeThing went Wrong");
	}
}
boolean getSingleRecord() throws ClassNotFoundException, SQLException
{
	boolean flag=false;
	
	String s1=tf3.getText();
	String s2=tf4.getText();
	Connection con=ATM.getConnection();
	PreparedStatement ps=con.prepareStatement("select Card_No,Pin_No from users where Card_No=? and Pin_No=?");
	ps.setString(1, s1);
	ps.setString(2, s2);
	ResultSet rs=ps.executeQuery();
	while(rs.next())
	{
		flag=true;
	}
	return flag;
	
}
void login() throws ClassNotFoundException, SQLException
{

	String s1=tf1.getText();
	String s2=tf2.getText();
	Connection con=ATM.getConnection();
	PreparedStatement ps=con.prepareStatement("select Card_No,Pin_No from users where Card_No=? and Pin_No=?");
	ps.setString(1, s1);
	ps.setString(2, s2);
	ResultSet rs=ps.executeQuery();
	if(rs.next())
	{
		JOptionPane.showMessageDialog(b1, "Wel You are in");
		f.dispose();
		your_account();
		
	}else {
		JOptionPane.showMessageDialog(b1, "Warning!");
	}
	

}


void your_account() {

	f2 = new JFrame("Your Account");
	withdraw = new JButton("Withdraw");
    withdraw.setBackground(Color.BLACK);
    withdraw.setForeground(Color.WHITE);
	withdraw.setBounds(290,190,200,50);
	deposit = new JButton("Deposit");
	deposit.setBackground(Color.BLACK);
    deposit.setForeground(Color.WHITE);
	deposit.setBounds(505,190,200,50);
	balance_inq = new JButton("Balance");
	balance_inq.setBackground(Color.BLACK);
    balance_inq.setForeground(Color.WHITE);
	balance_inq.setBounds(290,250,200,50);
	transfer = new JButton("Transfer");
	transfer.setBackground(Color.BLACK);
    transfer.setForeground(Color.WHITE);
	transfer.setBounds(505,250,200,50);
	exit = new JButton("Exit");
	exit.setBackground(Color.BLACK);
    exit.setForeground(Color.WHITE);
	exit.setBounds(800,400,150,30);
	exit.addActionListener(this);
	
	withdraw.addActionListener(this);
	deposit.addActionListener(this);
	balance_inq.addActionListener(this);
	transfer.addActionListener(this);
	
	
	f2.add(balance_inq);
	f2.add(withdraw);
	f2.add(deposit);
	f2.add(transfer);
	f2.add(exit);
	f2.setSize(1000,700);
	f2.setLayout(new BorderLayout());
	f2.setVisible(true);
}
	
void deposit_screen() {
	f3 = new JFrame();
	tf6 = new JTextField();
	tf6.setBounds(100,50,100,30);
	tf5 = new JTextField();
	tf5.setBounds(340,200,100,30);
	deposit_btn = new JButton("Deposit");
	deposit_btn.setBackground(Color.BLACK);
    deposit_btn.setForeground(Color.WHITE);
	deposit_btn.setBounds(290,250,200,50);
	tf6.setText(tf2.getText());
	deposit_btn.addActionListener(this);
	f3.add(tf5);
	//f3.add(tf6);
	f3.add(deposit_btn);
	f3.setSize(400,400);
	f3.setLayout(new BorderLayout());
	f3.setVisible(true);
    
}

int fatch_balance() throws SQLException, ClassNotFoundException{
	
	
	int upin = Integer.parseInt(tf6.getText());
	
	
	
	Connection con=ATM.getConnection();
	PreparedStatement ps=con.prepareStatement("select balance from users where Pin_No=?");
	ps.setInt(1, upin);
	
	
	ResultSet rs=ps.executeQuery();
	int balance = 0;
	while(rs.next()) {
		balance = rs.getInt("balance");
		
	}
	return balance;
}

void deposit() throws ClassNotFoundException, SQLException {
	int result = fatch_balance();
	int dep = Integer.parseInt(tf5.getText());
	int upin = Integer.parseInt(tf6.getText());
	result+=dep;
	
	Connection con = ATM.getConnection();
	PreparedStatement ps = con.prepareStatement("update users set balance=? where Pin_No=?");
	
	ps.setInt(1, result);
	ps.setInt(2, upin);
	
	int state = 0;
	state = ps.executeUpdate();
	if (state > 0) {
		JOptionPane.showMessageDialog(deposit_btn, "Deposited Sucessfully");
	}else {
		JOptionPane.showMessageDialog(deposit_btn, "Something Wrong");
	}
	
}

void withdraw_screen() {
	f3 = new JFrame();
	tf6 = new JPasswordField();
	tf6.setBounds(100,50,100,50);
	tf5 = new JTextField();
	tf5.setBounds(340,200,100,30);
	withdraw_btn = new JButton("withdraw");
	withdraw_btn.setBackground(Color.BLACK);
    withdraw_btn.setForeground(Color.WHITE);
	withdraw_btn.setBounds(290,250,200,50);
	withdraw_btn.addActionListener(this);
	
	tf6.setText(tf2.getText());
	
	
	f3.add(tf5);
//	f3.add(tf6);
	f3.add(withdraw_btn);
	f3.setSize(400,400);
	f3.setLayout(new BorderLayout());
	f3.setVisible(true);

}

void withdraw() throws ClassNotFoundException, SQLException {
	
	int result = fatch_balance();
	
	int wit = Integer.parseInt(tf5.getText());
	int ucard = Integer.parseInt(tf6.getText());
	if (result > wit){
		result-=wit;
		Connection con = ATM.getConnection();
		PreparedStatement ps = con.prepareStatement("update users set Balance=? where Pin_No=?");
		
		ps.setInt(1, result);
		ps.setInt(2, ucard);
		
			
		ps.executeUpdate();
		JOptionPane.showMessageDialog(withdraw_btn, "Cash Withdraw done Thanks for using our ATM");
		f3.dispose();
	}else {
		JOptionPane.showMessageDialog(withdraw_btn, "Sorry: You don't have enough cash.");
		
	} 
}
	
void transfer_screen()
{
	f4=new JFrame();
	acc_no=new JTextField();
	acc_no.setBounds(350,50,100,50);
	amount=new JTextField();
	amount.setBounds(350,200,100,30);
	transfer_money=new JButton("Transfer");
	transfer_money.setBackground(Color.BLACK);
    transfer_money.setForeground(Color.WHITE);
	transfer_money.setBounds(300,250,200,50);
	transfer_money.addActionListener(this);
	f4.add(acc_no);
	f4.add(amount);
	f4.add(transfer_money);
	f4.setSize(400,400);
	f4.setLayout(null);
	f4.setVisible(true);
	
	
	

}

void transfer_pay() throws ClassNotFoundException, SQLException
{
	
	int result = fatch_balance();
	int am=Integer.parseInt(amount.getText());
	int card_no=Integer.parseInt(acc_no.getText());
	Connection con = ATM.getConnection();
	PreparedStatement ps=con.prepareStatement("select Balance from users where Card_No=?");
	ps.setInt(1,card_no);
	
	
	ResultSet rs=ps.executeQuery();
	int rec_bal = 0;
	while(rs.next()) {
		rec_bal = rs.getInt("balance");
		
	}
	
	if(result>=am)
	{
		result=result-am;
		rec_bal=rec_bal+am;
		
		
	}
	int sen_pin=Integer.parseInt(tf2.getText());
	PreparedStatement ps2=con.prepareStatement("update users set Balance=? where Pin_No=?");
	ps2.setInt(1,result);
	ps2.setInt(2,sen_pin);
	
	int status=ps2.executeUpdate();
	
	PreparedStatement ps3=con.prepareStatement("update users set Balance=? where Card_No=?");
	ps3.setInt(1, rec_bal);
	ps3.setInt(2, card_no);
	
	int status2=ps3.executeUpdate();

	if(status>0 && status2>0)
	{
		JOptionPane.showMessageDialog(transfer, "Amount Transferred Successfully");
		
	}else {
		JOptionPane.showMessageDialog(transfer, "Amount cannot be transferred");
	}
	
	
	
}

ATM(){
		f = new JFrame("My ATM");
		
		l1 = new JLabel("W E L C O M E  T O   S B I   A T M !");
	    l1.setBounds(450,150,200,100);
		
		
		l2 = new JLabel("Card No:");
		l2.setBounds(400,200,100,100);
		tf1 = new JTextField();
		tf1.setBounds(495,240,190,20);
		l3 = new JLabel("PIN:");
		l3.setBounds(400,230,100,100);
		tf2 = new JPasswordField();
		tf2.setBounds(495,270,190,20);
		
		
		b1 = new JButton("SIGN IN");
		b1.setBackground(Color.BLACK);
        b1.setForeground(Color.WHITE);
		b1.setBounds(500,310,80,20);
		b2 = new JButton("CLEAR");
    	b2.setBackground(Color.BLACK);
	    b2.setForeground(Color.WHITE);
		b2.setBounds(600,310,80,20);
		b3 = new JButton("SIGN UP");
		b3.setBackground(Color.BLACK);
	    b3.setForeground(Color.WHITE);
		b3.setBounds(495,350,190,20);
		
		b1.addActionListener(this);
		b2.addActionListener(this);
		b3.addActionListener(this);
	
		
		f.add(l1);
		f.add(l2);
		f.add(b1);
		f.add(tf1);
		f.add(l3);
		f.add(tf2);
		f.add(b2);
		f.add(b3);
		
		f.setSize(1000,700);
		f.setLayout(null);
		f.setVisible(true);
	}
	
	public static void main(String args[]) {
		ATM at = new ATM();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==b3) {
			f.dispose();
			Signup_screen();
		}
		if(e.getSource()==insert_btn)
		{
			try
			{
			boolean result=getSingleRecord();
			if(result==true)
			{
				JOptionPane.showMessageDialog(insert_btn, "The specified account already exists.");
				tf3.setText("");
				tf4.setText("");
				
			}else {
				insert();
			}
			}catch(Exception ex) {
				ex.printStackTrace();
			}
		}
		if(e.getSource()==b1)
		{
			try {
				login();
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}	
		if (e.getSource()==b2) {
			tf1.setText("");
			tf2.setText("");
		}
		if (e.getSource()==deposit) {
			deposit_screen();
		}
		if (e.getSource()==deposit_btn) {
			try {
				deposit();
			} catch (ClassNotFoundException | SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		if (e.getSource()==withdraw) {
			withdraw_screen();
		}
		if (e.getSource()==withdraw_btn) {
			try {
				withdraw();
			} catch (ClassNotFoundException | SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		if (e.getSource()==transfer) {
			transfer_screen();
		}

		if (e.getSource()==transfer_btn) {
			try {
				transfer_pay();
			} catch (ClassNotFoundException | SQLException e1) {
				e1.printStackTrace();
			}
		}
		if (e.getSource()==exit) {
			JOptionPane.showMessageDialog(exit, "Come back Soon! Are you sure you want to Log out?");
			f2.dispose();
		}
	
		
	}
	
	
}