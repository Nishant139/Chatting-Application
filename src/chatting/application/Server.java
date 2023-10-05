package chatting.application;

import javax.swing.border.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.text.*;
import java.net.*;
import java.io.*;

//not doing extend Jframe because in main method itself static and we can not call non static into static
//public class Server extends JFrame implements ActionListener {

public class Server  implements ActionListener {    
    JTextField text ;
    JPanel a1;
    static Box vertical = Box.createVerticalBox();  // this for setting message on vertical format
    static JFrame f = new JFrame();
    static DataOutputStream dout;
    
    Server()
    {
        f.setLayout(null);
        JPanel p1 = new JPanel();
        p1.setBackground(new Color(7,94,84));
        p1.setBounds(0,0,450,70);  // setBound is use for cordinate
        p1.setLayout(null);  // when we use setLayOut null then we have to then setBound will worked otherwise not
        f.add(p1);
        
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));
        Image i2 = i1.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);// image size seting using scaling
        ImageIcon i3 = new ImageIcon(i2);
        JLabel back = new JLabel(i3);
        back.setBounds(5,20,25,25);
//        add(back)  this only add on the frame but we add on JPanel
        p1.add(back);
        
        back.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent ae)
            {
                System.exit(0);
            }
        });
        
        
        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icons/1.png"));
        Image i5 = i4.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5);
        JLabel profile = new JLabel(i6);
        profile.setBounds(40,10,50,50);
        p1.add(profile);
        
        
        ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));
        Image i8 = i7.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon i9 = new ImageIcon(i8);
        JLabel video = new JLabel(i9);
        video.setBounds(300,20,30,30);
        p1.add(video);
        
        
        ImageIcon i10 = new ImageIcon(ClassLoader.getSystemResource("icons/phone.png"));
        Image i11 = i10.getImage().getScaledInstance(35, 30, Image.SCALE_DEFAULT);
        ImageIcon i12 = new ImageIcon(i11);
        JLabel phone = new JLabel(i12);
        phone.setBounds(360,20,35,30);
        p1.add(phone);
        
        
        ImageIcon i13 = new ImageIcon(ClassLoader.getSystemResource("icons/3icon.png"));
        Image i14 = i13.getImage().getScaledInstance(10, 25, Image.SCALE_DEFAULT);
        ImageIcon i15 = new ImageIcon(i14);
        JLabel morevert = new JLabel(i15);
        morevert.setBounds(415,20,10,25);
        p1.add(morevert);
        
//        now using JLabel writting profile name
        JLabel name = new JLabel("Nishant");
        name.setBounds(110,15,100,18);
        name.setForeground(Color.WHITE);
        name.setFont(new Font("SAN_SERIF" , Font.BOLD , 18));
        p1.add(name);
        
        JLabel status = new JLabel("Active Now");
        status.setBounds(110,40,100,18);
        status.setForeground(Color.WHITE);
        status.setFont(new Font("SAN_SERIF" , Font.BOLD , 14));
        p1.add(status);
        
        a1 = new JPanel();
        a1.setBounds(2, 73, 440, 540);
        f.add(a1);
        
        text = new JTextField();
        text.setBounds(2, 615, 350,40);
        text.setFont(new Font("SAN_SERIF",Font.PLAIN,16));
        f.add(text);
        
        
        JButton send = new JButton("Send");
        send.setBounds(352,615 ,70 ,40 );
        send.setBackground(new Color(7,94,84));
        send.setForeground(Color.WHITE);
        send.setFont(new Font("SAN_SERIF" , Font.PLAIN , 16));
        f.add(send);
        send.addActionListener(this);
        
        
        f.setSize(450,700);
        f.setLocation(200,50);
        f.getContentPane().setBackground(Color.WHITE);
        f.setVisible(true);
    }
    
    public void actionPerformed(ActionEvent ae)
    {  
        /*
        1.  get the text from user via textfield (we want to perform event click on send button)
        2.  box vertical which arrange the chat into verical format
        3.  borderLayout which will be arrange the element into top-bottom-right-left
        4.  Japnel which align to  the right
        5.  now right.add(which not takes string then we have to create JLabel -> JPanel
        6.  right.add(out,BorderLayout.LINE_END);    parameter not taking string.
        7.  vertical.add(Box.createVerticalStrut(15)); whcih space between two chat have height:15
        8.  a1.add(vertical,BorderLayout.PAGE_START);  chat is start from page start
        9.  BoxLayout will takes two argument which one is where to apply and align which axis
        10. If you want write any on frame then you first write on label then added on frame
        11. repaint();  
            invalidate();  marks a component as not valid -- that means, it's layout
                           is or may not be "up to date" anymore:
                           i.e. the component is resized, a border is added, it's font changes, etc.
            validate(); 
        12. whenever use server then use try and catch block
        13. server is only one but socket may multiple
        */
        try
        {
            String out = text.getText();
            JLabel output = new JLabel(out);  // out will convert into JLabel 
    //      JPanel p2 = new JPanel();


            JPanel p2 = formatLabel(out);       
            a1.setLayout(new BorderLayout());
            JPanel right = new JPanel(new BorderLayout());

            right.add(p2,BorderLayout.LINE_END); // self msg display on left side
            vertical.add(right);
            vertical.add(Box.createVerticalStrut(15));

            a1.add(vertical,BorderLayout.PAGE_START);

            dout.writeUTF(out); // writeUTF is protocol to send msg when click on send button event performs

            text.setText("");

            f.repaint();  
            f.invalidate();  
            f.validate();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
            
    // for creating box for chat otherwise only chat is printed 
    public static JPanel formatLabel(String out)
    {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        
        JLabel output = new JLabel(out);
        output.setFont(new Font("Tahoma" , Font.PLAIN,16));
        output.setBackground(new Color(37,211,102));
        output.setOpaque(true); // after this function color background  will be shows
        output.setBorder(new EmptyBorder(15,15,15,50));
        
        
        panel.add(output);
        
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        
        JLabel time = new JLabel();
        time.setText(sdf.format(cal.getTime()));
        panel.add(time);
        
        return panel;
    }        
    
    public static void main(String [] args) 
    {
        new Server();
        
        try{
           ServerSocket skt = new ServerSocket(6001); 
           while(true)
           {
               Socket s = skt.accept(); // if come from client then it accept that
                DataInputStream din = new DataInputStream(s.getInputStream()); // for receiving message
                dout = new DataOutputStream(s.getOutputStream()); // for seding message (event perform 
//                for sending msg )
                
                
                while(true)
                {
                    String msg = din.readUTF(); // receivied msg for reading (the msg come from client)
                    JPanel panel = formatLabel(msg); // that receivied msg display on frame
                    
                    
                    JPanel left = new JPanel(new BorderLayout()); // that on left side displays
                    left.add(panel,BorderLayout.LINE_START);
                    vertical.add(left);
                    f.validate(); // its use for refreshing means loading interface
                    
                }
           }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    
}
