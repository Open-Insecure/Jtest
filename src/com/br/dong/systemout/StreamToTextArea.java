package com.br.dong.systemout;
/** 
 * @author  hexd
 * 创建时间：2014-6-3 下午12:49:06 
 * 类说明 
 */
import javax.swing.JFrame;
import javax.swing.JTextArea;
import java.io.PrintStream;
class StreamToTextArea extends JFrame {
  //declare PrintStream and JTextArea
    private static PrintStream ps = null;
    private JTextArea textPane = new JTextArea();  //constructor
    public StreamToTextArea() {

    setSize( 310, 180 );

      getContentPane().add(textPane);

      //this is the trick: overload the println(String)
      //method of the PrintStream
      //and redirect anything sent to this to the text box
    ps = new PrintStream(System.out) {
      public void println(String x) {
        textPane.append(x + "\n");
      }
    };
    }

    public PrintStream getPs() {
      return ps;
    }

  public static void main(String args[]) {
    //create object
    StreamToTextArea blah = new StreamToTextArea();
    //show it
    blah.show();
    //redirect the output stream
    System.setOut(blah.getPs());
    //print to the text box
    System.out.println("IT'S ALIVE!!");
    //print to the terminal (not a string)
    System.out.println("1111");
    //print the same thing to the text box (now a string)
    for(int i=0;i<10;i++){
    	System.out.println(""+i);
    }
  }
}

