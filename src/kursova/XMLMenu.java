package kursova;

import java.util.Scanner;

public class XMLMenu {
    private Scanner scanIn;
    private String filePath;
    private String helpMessage = "The following commands are supported:" +
            "\nprint                    display current file contents" +
            "\nselect <id> <key>        Display attribute value from given node name and attribute name" +
            "\nset <id> <key> <value>   Set attribute value from given node name, attribute name and new value" +
            "\nchildren <id>            prints child elements of given node" +
            "\nchild <id> <n>           gives access to the n-th child of given node"+
            "\n text <id>               prints the text of a given node"+
            "\nhelp                     this message appears" +
            "\nback                     Exits this menu\n";
    public XMLMenu( String filePath, Scanner scanIn)
    {
        this.scanIn=scanIn;
        this.filePath=filePath;
        Parser p = new Parser(filePath);
        if(p.loadXML()!=-1) {
            p.parseContent();
            System.out.println("Successfully opened "+filePath);
        }
        else {
            System.out.println("Error in loading file.");
            System.exit(-1);
        }
    }
    public void run() {
        System.out.println(helpMessage);
        boolean ret=false;
        while (true) {
            if(ret)
            {break;}
            String[] selection = scanIn.nextLine().split(" ", 4);
            if(this.filePath!=null) {
                System.out.println(this.filePath);
            }


            switch (selection[0]) {
                case "help":
                    System.out.println(helpMessage);
                    break;
                case "back":
                   ret=true;
                    break;

                case "print":
                    XML.getRoot().printXML("\t");
                    break;

                case "select":
                {
                    String result=XML.getRoot().getAttributeByNode(selection[1],selection[2]);
                    if(result=="")
                    {System.out.println("No attributes found for given node name.");}
                    else System.out.println(result);
                    break;
                }
                case "set":
                {
                    int result=XML.getRoot().setAttributeByNode(selection[1],selection[2],selection[3]);
                    if(result==0)
                    {System.out.println("No attributes affected.");}
                    else System.out.println(result+ " Attributes affected.");
                    break;
                }
                case "children":
                   String result=XML.getRoot().getAttributeByNode(selection[1],null);
                   if(result.equals(""))
                   {
                       System.out.println("This node has no children.");
                       break;
                   }
                   System.out.println(result);
                    break;
                case "child":
                    // do sth
                    break;
                case "text":
                    result="";
                     result=XML.getRoot().getTextByNode(selection[1]);
                     if(result.equals("")|| result.equals(null))
                     {
                         System.out.println("Nodes with name: "+selection[1]+" do not contain text");
                         break;
                     }
                     System.out.println(result);
                     break;
                default:
                    System.out.println("Command not supported. Type 'help' to display all available commands.");
            }
        }
    }
}
