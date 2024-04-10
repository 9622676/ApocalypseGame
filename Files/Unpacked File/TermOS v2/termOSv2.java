import java.util.Arrays;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;


class terminalOS {
    public static int currentKey;
    public static boolean selected,menu, enter,inGame;
    public static JFrame myJFrame = new JFrame();
    // color 13 is blue background
    public static int hlColor = 13, crs = 0, mainBox = 2, oldMB = 5, x, y, kcode;
    //char codes are: Plain Text, adjustable Number, slider (newline), and adjustable Text. Colors are: clear color,
    public static final String[] charCodes = {":","|","/","-"}, c = {"\u001B[0m","\u001B[30m","\u001B[31m","\u001B[32m","\u001B[33m","\u001B[34m","\u001B[35m","\u001B[36m","\u001B[37m","\u001B[40m","\u001B[41m","\u001B[42m","\u001B[43m","\u001B[44m","\u001B[45m","\u001B[46m","\u001B[47m"};
    //text in boxes, subtract 2 from yw for the text line count
    public static String[][] boxText = {{"one","2","3","4","5","6","7","8"},{"two","2","3","4","5","6","7","8"},{"three","2","3","4","5","6","7","8"},{"four","2","3","4","5","6","7","8"},{"five","2","3","4","5","6","7","8"},{"six","2","3","4","5","6","7","8"},{"seven","2","3","4","5","6","7","8"},{"eight","2","3","4","5","6","7","8"},{"nine","2","3","4","5","6","7","8"},{"ten","2","3","4","5","6","7","8"},{"backgroundProcesses","."}};
    //boxVars is data stored in adjustable fields and stuff, first digit is(will be) cursor pos. boxData is x,y,w,h of box in that order.
    public static int[][] boxVars = {{},{},{},{},{},{},{},{},{},{},{}}, boxData = {{1,1,20,10},{2,2,20,10},{3,3,20,10},{4,4,20,10},{5,5,20,10},{6,6,20,10},{7,7,20,10},{8,8,20,10},{9,9,20,10},{10,10,20,10},{30,30,24,4}};
    public static int[] order = {0,1,2,3,4,5,6,7,8,9,10};
    public static boolean[] visible = {true,true,true,true,true,true,true,true,true,true,false}; //last one always false (:
    public static void main(String args[]){ //acting as startup code, KeyListener updates after every keypress
        sequencer(false);
        mainBox = 0;
        orderBoxes();
    }
    public static void printImage(int x, int y, String file, String format, int delay) {
        crs(50,0,""); //setup screen for drawing
        crs(0,0,"");
        BufferedReader br = null;
        try{
        br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
        } catch (Exception e) {System.out.println(e + " User File Corrupted, Aborting Startup"); System.exit(0);}
        String line;
        try{
        int r = 0;
        if(delay != 0) {
        while ((line = br.readLine()) != null) {
          crs(x,y+r,line);
          delay(delay);
          r++;
        }
    } else {
        while ((line = br.readLine()) != null) {
            crs(x,y+r,line);
            r++;
          }
    }
    } catch (IOException e) {System.out.println(e + " BIOS Corrupted, Aborting Startup"); System.exit(0);}
    }
    public static void sequencer(boolean first) {
        cls();
        if(first) {
        printImage(0,0,"UsrFile.txt","ASCII",80);
        delay(2000);
        slowCrs(80,19,"╓──────────────────────────────────────────────────────╖",80);
        slowCrs(80,20,"║Fetching OS Revision                                  ║",80);
        loadingDots(101,20,5);
        slowCrs(80,21,"║OS Revision 10.01c                                    ║",80);
        slowCrs(80,22,"║(October 1976 Edition)                                ║",80);
        slowCrs(80,23,"║Trial Edition: No                                     ║",80);
        slowCrs(80,24,"║Timesharing Edition: No                               ║",80);
        slowCrs(80,25,"║Fetching Current Machine                              ║",80);
        loadingDots(105,25,8);
        slowCrs(80,26,"║Asgard Systems Asgard II Minicomputer (1976)          ║",80);
        slowCrs(80,27,"║20MHz | Processors: 1 | ALUs: 1 | Word Width: 24 bits ║",80);
        slowCrs(80,28,"║Extended Memory: No                                   ║",80);
        slowCrs(80,29,"║Memory Size: 32KWord                                  ║",80);
        slowCrs(80,30,"║Extended Instruction Set: No                          ║",80);
        slowCrs(80,31,"║Floating Point Unit: Stock                            ║",80);
        slowCrs(80,32,"║Hard Disk Capacity: 512Megabits                       ║",80);
        slowCrs(80,33,"╙──────────────────────────────────────────────────────╜",80);
        slowCrs(140,19,"╓─────────────────────────────────╖",80);
        slowCrs(140,20,"║Fetching Terminal Type           ║",80);
        loadingDots(163,20,10);
        slowCrs(140,21,"║IBM Type 3270c +\u001B[36mc\u001B[31mo\u001B[32ml\u001B[33mo\u001B[34mr\u001B[35m!\u001B[0m Terminal  ║",80);
        slowCrs(140,22,"║Max Baud Rate: 2,358,700 baud    ║",80);
        slowCrs(140,23,"║Setting: MAX(auto) |57,600 baud| ║",80);
        slowCrs(140,24,"║Enabling                         ║",80);
        loadingDots(150,24,5);
        crs(140,25,    "║Displaying Test Pattern:         ║");
        printImage(140,26,"SpeedTest.txt","UTF-8",0);
        crs(180,19,"╓─────────────────────╖");
        crs(180,20,"║Fetching User Data   ║");
        loadingDots(199,20,10);
        crs(180,21,"║Data Found! Loading  ║");
        crs(180,22,"║User Select Menu     ║");
        loadingDots(197,22,3);
        crs(180,23,"║For User(Admin)      ║");
        crs(180,24,"╙─────────────────────╜");
        }
        crs(80,5,"╓─────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────╖");
        crs(80,6,"║                                                          Users:                                                         ║");
        crs(80,7,"╟─────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────╢");
        crs(80,8,"║                                                        Start Game                                                       ║");
        crs(80,9,"╟─────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────╢");
        crs(80,10,"║                                                       Settings/Exit                                                     ║");
        crs(80,11,"╙─────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────╜");
        Jsetup();
        while(true) {
        if(kcode == 38) {
            menu = false;
        }
        if (kcode == 40) {
            menu = true;
        }
        if(kcode == 10) {
            enter = true;
        }
        if(menu) {
            crs(136,8," ");
            crs(135,10,"*");
        } else {
            crs(135,10," ");
            crs(136,8,"*");
        }
        if(!menu && enter) {
        crs(80,12,"loading");
        loadingDots(87,12,5);
        inGame = true;
        return;
        } else if (enter) {
            enter = false;
            kcode = 0;
            crs(80,12,"loading");
            loadingDots(87,12,3);
            crs(80,15,"Set Highlight Color to Gray");
            crs(80,16,"Exit Game");
            while(true) {
                if(kcode == 38) {
                    menu = false;
                }
                if (kcode == 40) {
                    menu = true;
                }
                if(kcode == 10) {
                    enter = true;
                }
                if(menu) {
                    crs(79,15," ");
                    crs(79,16,"*");
                } else {
                    crs(79,16," ");
                    crs(79,15,"*");
                }
                if(menu && enter) {
                System.exit(0);
                } else if (enter) {
                    hlColor = 12;
                    inGame = true;
                    return;
                }
            }
        }
        }
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
       cls();
       crs(0,0,"Asgard OS Version 10 (Stable)");
       //if(true) { //FOR BACKGROUND IMAGE
       //printImage(20,0,"ascii-art.ans","ANSI",0);
       //}
       for(int i = 0; i < order.length; i++) { //render the boxes
        if(visible[order[i]]) {
           makeBox(order[i]);
        }
        makeBottomBar(visible);
      }
   }
    public static boolean getStat() {
    return inGame;
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
           case 52:
               mainBox = 3;
           break;
           case 53:
               mainBox = 4;
           break;
           case 54:
               mainBox = 5;
           break;
           case 55:
               mainBox = 6;
           break;
           case 56:
               mainBox = 7;
           break;
           case 57:
               mainBox = 8;
           break;
           case 58:
               mainBox = 9;
           break;
           case 10:
               selected = !selected;
           break;
           case 40: //down
           if(selected && crs == 0) {
            boxData[mainBox][1] = boxData[mainBox][1] + 1; //move box down 1 character
           } else {
            crs++; //moves cursor down
           }
           break;
           case 37: //left
           if(selected && crs == 0) {
            boxData[mainBox][0] = boxData[mainBox][0] - 1; //move box left 1 character
           } else {
            mainBox = Math.max((mainBox - 1),0); //switches active box when movebox is not selected
           }
           break;
           case 39: //right
           if(selected && crs == 0) {
            boxData[mainBox][0] = boxData[mainBox][0] + 1; //move box right 1 character
           } else {
            mainBox = Math.min((mainBox + 1),order.length-2); //switches active box when movebox is not selected, -2 because 0 is lowest & the last value isn't used
           }
           break;
           case 38: //up
           if(selected && crs == 0) {
            boxData[mainBox][1] = boxData[mainBox][1] - 1; //move box up 1 character
           } else {
            crs--; //moves cursor up
           }
           break;
        }
       crs = Math.min(Math.max(crs,0),boxText[mainBox].length-1); //constrains the cursor to the box
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
      crs(x,y+1,"║"+boxText[num][0]+mid.substring(boxText[num][0].length()+3)+" "+(c[((crs == 0 && num == mainBox) ? 1:0)*hlColor])+"▒"+ c[0] +" ║"); //draw desc box text
      crs(x,y+2,"╠"+top+"╣");
      //draw the rest of the box
      for(int i = 0; i < yw-3; i++) {
      crs(x,y+i+3,"║" +(c[hlColor*((crs == i+1 && num == mainBox) ? 1:0)])+boxText[num][i+1]+c[0]+mid.substring(boxText[num][(i+1)].length())+"║"); //fill a line
      }
      crs(x,y+yw,"╚"+top+"╝");
  }

    public static void cls() {
      System.out.print("\033[2J");
  }
  
    public static void crs(int column, int row, String txt) {
  System.out.print(String.format("%c[%d;%df",033,row,column));
  System.out.print(txt);
  }
    public static void slowCrs(int column, int row, String txt, int delay) {
    System.out.print(String.format("%c[%d;%df",033,row,column));
    System.out.print(txt);
    delay(delay);
    }

    public static void delay(int ms) {
      try {
      Thread.sleep(ms);
      } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      }
  }
    public static void makeBottomBar(boolean[] open) {
        int j = 0;
        for(int i = 0; i < open.length-1; i++) {
            if(open[i]) {
                boolean chosen = mainBox == i;
                int addChunk = (chosen ? -2:0);
                crs(2+(j*16),51+addChunk,(chosen ? c[hlColor]:c[0]) + boxText[i][0] + c[0]);
                crs(1+(j*16),50+addChunk,"╓───────────────");
                crs(1+(j*16),51+addChunk,"║");
                crs(1+(j*16),52+addChunk,"║");
                if(chosen) {
                    crs(17+(j*16),48,"╖");
                    crs(17+(j*16),49,"║");
                    crs(2+(j*16),51," | | ");
                    crs(2+(j*16),52,"\\___/");
                }
                j++;
            }
        }
    }
    public static void Jsetup() {
   myJFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   myJFrame.setVisible(true);
   myJFrame.addKeyListener(new KeyAdapter(){public void keyPressed(KeyEvent e) {
    if(getStat()) {
    keyDecode(e.getKeyCode());
    }
    kcode = e.getKeyCode();
}});
  }

    public static void loadingDots(int x, int y, int time) {
        for(int i = 0; i < time; i++) {
            slowCrs(x,y,".  ",60);
            slowCrs(x,y,".. ",60);
            slowCrs(x,y,"...",60);
            slowCrs(x,y," ..",60);
            slowCrs(x,y,"  .",60);
            slowCrs(x,y,"   ",60);
            }
    }

}
