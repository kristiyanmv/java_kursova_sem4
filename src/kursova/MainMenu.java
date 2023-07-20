package kursova;

import java.util.Scanner;

public class MainMenu {

    Scanner scanIn = new Scanner(System.in);
    private static String filePath;
    private String helpMessage = "The following commands are supported:" +
            "\nopen <file>          opens <file>" +
            "\nclose                closes the current file" +
            "\nsave                 saves the current file" +
            "\nsaveAs <file>        saves current file in <file>" +
            "\nhelp                 this message appears" +
            "\nexit                 closes the program\n";

    public MainMenu() {
        run();
    }

    private void run() {
        System.out.println(helpMessage);
        while (true) {

            String[] selection = scanIn.nextLine().split(" ", 2);
            if(filePath!=""&& filePath!=null)
            {System.out.println(filePath);}
            else {System.out.println("No loaded file.");}

            switch (selection[0]) {
                case "help":
                    System.out.println(helpMessage);
                    break;
                case "exit":
                    System.out.println("Exiting the program.");
                    System.exit(0);
                case "open":
                    this.filePath=selection[1];
                    XMLMenu menu= new XMLMenu(this.filePath,scanIn);
                    menu.run();
                    break;
                case "saveas":
                    if(XML.getRoot()!=null)
                    this.filePath=selection[1];
                    else {
                        System.out.println("Open file first!");
                        break;
                    }
                case "save":
                    if(XML.getRoot()!=null) {
                        try {
                            Parser p = new Parser(this.filePath);
                            p.saveToFile(XML.getRoot());
                            System.out.println("Successfully saved to " + this.filePath);
                            break;
                        } catch (Exception e) {
                            System.out.println("Error in saving to file.");
                            break;
                        }
                    }
                    else
                    {
                        System.out.println("Open file first!");
                        break;
                    }
                case "close":
                    if(XML.getRoot()!=null) {
                        System.out.println("Closed file: " + this.filePath);
                        this.filePath = null;
                        XML.setRoot(null);
                    }
                    else {
                        System.out.println("Open file first!");
                    }
                    break;


                default:
                    System.out.println("Command not supported. Type 'help' to display all available commands.");
            }
        }
    }
}
