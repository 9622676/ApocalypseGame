import java.util.Arrays;
import javax.swing.*;
import java.awt.event.*;

class terminalOS {
    public static int currentKey;
    public static JFrame myJFrame = new JFrame();
    public static int[] decode = {0,0,0,0,0,0};
   // color 13 is blue background
   public static int hlColor = 13, crs = 0, mainBox = 2, oldMB = 5, x, y;
   //colors
   public static final String[] c = {"\u001B[0m","\u001B[30m","\u001B[31m","\u001B[32m","\u001B[33m","\u001B[34m","\u001B[35m","\u001B[36m","\u001B[37m","\u001B[40m","\u001B[41m","\u001B[42m","\u001B[43m","\u001B[44m","\u001B[45m","\u001B[46m","\u001B[47m"};
   //text in boxes, subtract 2 from yw for the text line count
   public static String[][] boxText = {{"one","2","3","4","5","6","7","8"},{"two","2","3","4","5","6","7","8"},{"three","2","3","4","5","6","7","8"},{"four","2","3","4","5","6","7","8"},{"five","2","3","4","5","6","7","8"},{"six","2","3","4","5","6","7","8"}};
   public static int[][] boxData = {{1,1,20,10},{2,2,20,10},{3,3,20,10},{4,4,20,10},{5,5,20,10},{6,6,20,10}};
   public static int[] order = {0,1,2,3,4,5}, keys = {49}, pressed = new int[keys.length];
   public static void main(String args[]){
        Jsetup();
        cls();
        mainBox = 0;
        orderBoxes();
   }

   public static void orderBoxes() {
        if(oldMB != mainBox) { //oldMB used on line 37
        int length = order.length-1;
        if((length+1)-mainBox > 1) {
        int ind = indexOf(order,mainBox);
        int[] SubArr = new int[length-ind+1];
        for(int i = ind+1; i <= length; i++) { //pull arr into mini arr    0,1,2,3,4
            SubArr[i-ind] = order[i];        //                                2,3,4
        }                                    //shift mini left and insert  0,2,3,4,1
        for(int i = ind+1; i <= length; i++) {
            order[i-1] = SubArr[(i-ind)];
        }
        order[length] = mainBox;
        } else {                            //just switches last and second to last   01234 -> 01243
            order[length] = mainBox-1;
            order[length-1] = oldMB-1;
        }
        oldMB = mainBox;
        }
        for(int i = 0; i < order.length; i++) { //render the boxes
            makeBox(order[i]);
       }
    }

    public static void keyDecode(int key) {
        switch(key) {
            case 49:
                mainBox = 0;
            break;
            case 50:
                mainBox = 1;
            break;
            case 51:
                mainBox = 2;
            break;
        }
        orderBoxes();
    }
   public static int indexOf(int[] array, int val) {
        int len = array.length;
        for(int i = 0; i<len;i++) {
        if(array[i] == val) {
            return i;
        }
   }
   throw new NullPointerException("IndexOf failed to find Value!");
   }

   public static void makeBox(int num) {
       int x = boxData[num][0], y = boxData[num][1], xw = boxData[num][2], yw = boxData[num][3];
       char[] topBar = new char[Math.max(xw-2,0)], midFill = new char[Math.max(xw-2,0)];
       String top = "", mid = "";
       if(topBar.length > 0) {
       Arrays.fill(topBar,'═');
       top = new String(topBar);
       }
       if(midFill.length > 0) {
       Arrays.fill(midFill,' ');
       mid = new String(midFill);
       }
       //draw description box
       crs(x,y,"╔"+top+"╗");
       crs(x,y+1,"║"+boxText[num][0]+mid.substring(boxText[num][0].length()+3)+" "+(c[hlColor*((crs == 0) ? 1:0)])+"▒"+ c[0] +" ║"); //draw desc box text
       crs(x,y+2,"╠"+top+"╣");
       //draw the rest of the box
       for(int i = 0; i < yw-3; i++) {
       crs(x,y+i+3,"║" +(c[hlColor*((crs == i+1) ? 1:0)])+boxText[num][i+1]+c[0]+mid.substring(boxText[num][(i+1)].length())+"║"); //fill a line
       }
       crs(x,y+yw,"╚"+top+"╝");
   }

   public static void cls() {
       System.out.print("\033[2J");
   }
 
   public static void crs(int column, int row, String txt) {
   //delay(20);
   System.out.print(String.format("%c[%d;%df",033,row,column));
   System.out.print(txt);
   }

   public static void delay(int ms) {
       try {
       Thread.sleep(ms);
       } catch (InterruptedException e) {
       Thread.currentThread().interrupt();
       }
   }
   
   public static void Jsetup() {
    myJFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    myJFrame.setVisible(true);
    myJFrame.addKeyListener(new KeyAdapter(){public void keyPressed(KeyEvent e) {keyDecode(e.getKeyCode());}});
   }
}