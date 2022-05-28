import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.Objects;
import javax.swing.*;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

class Sqrt{
    private static Integer Precision = 2;
    private static String local = "ru";

    public static JLabel ta = new JLabel("",SwingConstants.CENTER);
    public static String pattern = "##0.00";
    public static DecimalFormat decimalFormat = new DecimalFormat(pattern);
    public static JFrame frame;
    public static JMenu m1;
    public static JButton m4;
    public static JLabel label;
    public static JButton calculate;
    public static JLabel m2;
    public static JLabel m3;
    public static Double lastRes;
    public static Double lastResComplex;
    public static char lastSgnA = '+';
    public static char lastSgnB = '+';
	public static void main(String args[]){	
        //Creating the Frame
        frame = new JFrame(Messages.getStringLang(local, "HiLo.0")); //Get sqrt
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setMinimumSize(new Dimension(500, 500));

        //Creating the MenuBar and adding components
        JMenuBar mb = new JMenuBar();
        m1 = new JMenu(Messages.getStringLang(local,"HiLo.3")); //Parameters
        m2 = new JLabel(Messages.getStringLang(local,"HiLo.7")+(Objects.equals(local, "en")?"english":"русский"));
        m2.setFont(new Font("Verdana", Font.PLAIN, 12));
        m3 = new JLabel(Messages.getStringLang(local,"HiLo.1")+Precision+" "+Messages.getStringLang(local,GetNumber()));
        m3.setFont(new Font("Verdana", Font.PLAIN, 12));
        m1.setBorder(BorderFactory.createEtchedBorder());
        mb.add(m1);
        mb.add(Box.createHorizontalGlue());
        mb.add(m2);
        mb.add(Box.createHorizontalGlue());
        mb.add(m3);
        m4 = new JButton(Messages.getStringLang(local,"HiLo.41")); //Help
        mb.add(Box.createHorizontalGlue());
        m4.setBorder(BorderFactory.createEtchedBorder());
        mb.add(m4);
        JMenu m11 = new JMenu(Messages.getStringLang(local,"HiLo.5")); //Change language
        JMenuItem m12 = new JMenuItem(Messages.getStringLang(local,"HiLo.6")); //Change accuracy
        m1.add(m11);
        m1.add(m12);
        JMenuItem m111 = new JMenuItem(Messages.getStringLang(local,"HiLo.16")); //English
        JMenuItem m112 = new JMenuItem(Messages.getStringLang(local,"HiLo.17")); //Russian
        m11.add(m111);
        m11.add(m112);
        m12.addActionListener(new ActionListener() {
        	String result = ""; 
            public void actionPerformed(ActionEvent e) {
                result = JOptionPane.showInputDialog(m12, Messages.getStringLang(local,"HiLo.8"),String.valueOf(Precision)); //Enter Precision
                GetRes(result);
            }
        });
        m111.addActionListener(new ActionListener()
        {
        	public void actionPerformed(ActionEvent e) {
                local="en";
                Redraw(local);
            }
        });
        m112.addActionListener(new ActionListener()
        {
        	public void actionPerformed(ActionEvent e) {
                local="ru";
                Redraw(local);
            }
        });
        m4.addActionListener(new ActionListener()
        {
        	public void actionPerformed(ActionEvent e) {
        		OpenBrowser();
            }
        });
        //Creating the panel at bottom and adding components
        JPanel panel = new JPanel(); // the panel is not visible in output
        label = new JLabel(Messages.getStringLang(local,"HiLo.9")); //Enter Number
        JTextField tf = new JTextField(15); // accepts up to 20 characters
        calculate = new JButton(Messages.getStringLang(local,"HiLo.10")); //Calculate
        panel.add(label); // Components Added using Flow Layout
        panel.add(tf);
        panel.add(calculate);
        
        ta.setFont(new Font(Messages.getStringLang(local,"HiLo.12"), Font.PLAIN, 20)); //Dialog

        //Calculating result
        calculate.addActionListener(new ActionListener()
        {
        	 @Override
        	 public void actionPerformed(ActionEvent arg1) 
        	 {
        	 var text = tf.getText();
        	 try
        	 {
        		 var number = Double.valueOf(text);
        		 if(number>=0)
        		 {
        			 Clear();
        			 ta.setText(decimalFormat.format(Math.sqrt(number)));
        			 lastRes = Math.sqrt(number);
        		 }
        		 else
        		 {
        			 Clear();
        			 number*=-1;
        			 ta.setText(decimalFormat.format(Math.sqrt(number))+"i"); 
        			 lastRes = Math.sqrt(number);
        			 lastSgnA = '-';
        		 }
        	 }
        	 //Блок комплексных чисел
        	 catch(NumberFormatException e)
        	 {
        		 if(!text.contains("i")) //$NON-NLS-1$
        		 {
        			 ta.setText(Messages.getStringLang(local,"HiLo.15")); //InputError, please, repeat enter
        			 Clear();
  //      			 tf.setText("");
        			 return;
        		 }
        		 try 
        		 {
        		 var PlusMinus = Character.toString((char)0x00B1);
        		 char sgnA = '+';
        		 char sgnB = '+';
        		 //убрать знак в начале числа
        		 if(text.charAt(0)=='+')
        		 {
        			 text = text.substring(1);
        		 }
        		 if(text.charAt(0)=='-')
        		 {
        			 text = text.substring(1);
        			 sgnA = '-';
        		 }
        		 String[] complex;
        		 //случай типа 2i
        		 if((!text.contains("+"))&&(!text.contains("-"))) 
        		 {
        			 sgnB = sgnA;
        			 sgnA = '+';
        			 complex = new String[2];
        			 complex[0]="0"; 
        			 complex[1]=text.replace("i", ""); 
        		 }
        		 else
        		 {
        			 //разделение комплексного числа
        			 complex = text.split("\\+"); 
        			 if(complex.length<2)
        			 {
        				 sgnB='-';
        				 complex = text.split("-"); 
        			 }
        			 if(complex[0].contains("i"))
        			 {
        				 char c = sgnA;
        				 sgnA = sgnB;
        				 sgnB=c;
        				 complex[0]=complex[0].replace("i", ""); 
        				 String c2 = complex[0];
        				 complex[0]=complex[1];
        				 complex[1]=c2;
        			 }
        			 else
        			 {
        				 complex[1]=complex[1].replace("i", ""); 
        			 }
        		 }
        		 if (complex.length>2)
        		 {
        			 ta.setText(Messages.getStringLang(local,"HiLo.29")); //InputError, please, repeat enter
        			 Clear();
   //     			 tf.setText(""); 
        			 return;
        		 }
        		 if(complex[0]=="") 
        		 {
        			 complex[0]="1"; 
        		 }
        		 if(complex[1]=="") 
        		 {
        			 complex[1]="1"; 
        		 }
        		 var A = Double.valueOf(complex[0]);
        		 var B = Double.valueOf(complex[1]);
        		 
        		 lastRes = Math.sqrt((Math.sqrt(A*A+B*B)+A)/(2));
        		 String FullPart = decimalFormat.format(lastRes);
        		 lastResComplex = Math.sqrt((Math.sqrt(A*A+B*B)-A)/(2));
        		 String PartPart = decimalFormat.format(lastResComplex);
        		 String One = decimalFormat.format(1.f);
        		 ta.setText(PlusMinus+"("+ FullPart + sgnB+"i"+ (Objects.equals(PartPart, One) ? "" : PartPart )+")"); 
        		 }
        		 catch(Exception Ex)
            	 {
            		 ta.setText(Messages.getStringLang(local,"HiLo.39")); //InputError, please, repeat enter
            		 Clear();
   //         		 tf.setText(""); 
            	 }
        	 }
   //     	 tf.setText(""); 
        	 }
        	 });
        
        // Text Area at the Center


        //Adding Components to the frame.
        frame.getContentPane().add(BorderLayout.SOUTH, panel);
        frame.getContentPane().add(BorderLayout.NORTH, mb);
        frame.getContentPane().add(BorderLayout.CENTER, ta);
        frame.setVisible(true);
    }
    public static void GetRes(String res)
    {
    	try
    	{
    		if(res == null)
    			return;
    	var P = Integer.valueOf(res);
    	if(P<1||P>20)
    	{
    		ta.setText(Messages.getStringLang(local,"HiLo.44")); //InputError, please, repeat enter
    		Clear();
    		return;
    	}
    	Precision = P>=0?P:Precision;
        pattern = "##0."; 
        for(int i=0;i<Precision;i++)
        {
        	pattern+="0"; 
        }
        decimalFormat = new DecimalFormat(pattern);
    	m3.setText(Messages.getStringLang(local,"HiLo.1")+Precision+" "+Messages.getStringLang(local,GetNumber()));
    	if(lastResComplex != null)
    	{
    		var PlusMinus = Character.toString((char)0x00B1);
   		 String FullPart = decimalFormat.format(lastRes);
   		 String PartPart = decimalFormat.format(lastResComplex);
   		 String One = decimalFormat.format(1.f);
   		 ta.setText(PlusMinus+"("+ FullPart + lastSgnB+"i"+ (Objects.equals(PartPart, One) ? "" : PartPart )+")"); 
   		 
    	}
    	else if(lastRes == null && lastResComplex == null)
    	{
    		ta.setText("");
    	}
    	else if (Objects.equals(lastSgnA, '-'))
    	{
    		ta.setText(decimalFormat.format(lastRes)+"i"); 
    	}
    	else
    	{
    		ta.setText(decimalFormat.format(lastRes)); 
    	}
    	}
    	catch(Exception e)
    	{
    		ta.setText(Messages.getStringLang(local,"HiLo.44")); //InputError, please, repeat enter
    	}
    }
    public static void Redraw(String local)
    {
    	frame.setTitle(Messages.getStringLang(local, "HiLo.0"));
    	m1.setText(Messages.getStringLang(local, "HiLo.3"));
    	int number = 5;
    	int number2 = 16;
    	for (var a : m1.getMenuComponents())
    	{
    		((JMenuItem) a).setText(Messages.getStringLang(local, "HiLo."+number));
    		number++;
    		try
    		{
    		if(((JMenu)a).getMenuComponentCount()>0)
    		{
    			for (var b : ((JMenu)a).getMenuComponents())
    	    	{
    	    		((JMenuItem) b).setText(Messages.getStringLang(local, "HiLo."+number2));
    	    		number2++;
    	    	}
    		}
    		}
    		catch(Exception e)
    		{}
    	}
    	if(Objects.equals(ta.getText(), Messages.getStringLang(otherLocal(local), "HiLo.39")))
    		ta.setText(Messages.getStringLang(local, "HiLo.39"));
    	label.setText(Messages.getStringLang(local, "HiLo.9"));
    	calculate.setText(Messages.getStringLang(local,"HiLo.10"));
    	m2.setText(Messages.getStringLang(local,"HiLo.7")+(Objects.equals(local, "en")?"english":"русский"));
    	m3.setText(Messages.getStringLang(local,"HiLo.1")+Precision+" "+Messages.getStringLang(local,GetNumber()));
    	m4.setText(Messages.getStringLang(local,"HiLo.41"));
    }
    public static String GetNumber()
    {
    	if(Precision==1||(Precision>20&&Precision%10==1))
    		return "HiLo.22";
    	else if(Precision>1 && Precision<5)
    	{
    		return "HiLo.2";
    	}
    	else
    		return "HiLo.21";
    }
    public static void Clear()
    {
    	lastRes = null;
    	lastResComplex = null;
    	lastSgnA = '+';
    	lastSgnB = '+';
    }
    public static String otherLocal(String loc)
    {
    	return loc == "ru" ? "en" : "ru";
    
    }
    public static void OpenBrowser() {
        String url = "https://supportsqrt.blogspot.com/";

        if(Desktop.isDesktopSupported()){
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.browse(new URI(url));
            } catch (IOException | URISyntaxException e) {
            	ta.setText(Messages.getStringLang(local, "HiLo.39"));
            }
        }else{
            Runtime runtime = Runtime.getRuntime();
            try {
                runtime.exec("xdg-open " + url);
            } catch (IOException e) {
            	ta.setText(Messages.getStringLang(local, "HiLo.39"));
            }
        }
    }
}
