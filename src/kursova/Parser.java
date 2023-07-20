package kursova;
import java.util.*;
import java.io.*;

public class Parser {
    private String filePath;
    private String stringContent;


    public Parser(String filePath) {
        this.filePath = filePath;
    }

    public int  loadXML()
    {
        try{
            BufferedReader reader=new BufferedReader(new FileReader(filePath));
            StringBuilder content=new StringBuilder();
            String line;
            while((line= reader.readLine())!=null)
            {
                content.append(line);
            }
            stringContent=content.toString().replace("\t","");
            reader.close();
            return 0 ;
        }
        catch(Exception e)
        {
            return -1;
        }
    }
    public void parseContent() {
        int currentIndex=0;
        XML rootXML = null;
        XML currentXML = rootXML;
        while(currentIndex<stringContent.length())
        {
            int startIndex=stringContent.indexOf("<",currentIndex);
            int endIndex=stringContent.indexOf(">",currentIndex);
            if(startIndex!=-1 && endIndex !=-1)
            {
                String element=stringContent.substring(startIndex+1,endIndex);
                currentIndex=endIndex+1;
                if(element.startsWith("/"))
                {
                    element.substring(1);
                    currentXML=currentXML.getParent();
                }
                else
                {
                    XML newXML = new XML();
                    newXML.setNodeName(element);
                    int attributeIndex=element.indexOf(" ");
                    if(attributeIndex!=-1)
                    {
                        String attributesString=element.substring(attributeIndex);
                        String[] attributePairs=attributesString.split("\" ");
                        for(String pair:attributePairs)
                        {
                            String[]attributeParts=pair.split("=\"");
                            if(attributeParts.length==2)
                            {
                                newXML.addAttribute(attributeParts[0].trim(),attributeParts[1].replace("\"",""));
                            }
                        }
                        newXML.setNodeName(element.substring(0,attributeIndex).trim());
                    }
                    int nextStart=stringContent.indexOf("<",currentIndex);
                    String content=stringContent.substring(currentIndex,nextStart).trim();
                    if(!content.isEmpty())
                    {
                        newXML.setContent(content);
                    }

//                    if (currentXML != null) {
//                        currentXML.addChild(newXML);
//                        newXML.setParent(currentXML);
//                    }
//                    currentXML = newXML;
                    if(currentXML!=null) {
                        newXML.setParent(currentXML);
                        currentXML.addChild(newXML);
                        currentXML=newXML;
                    }
                    else {
                        XML.setRoot(newXML);
                        rootXML=currentXML=newXML;

                    }

                }
            }
            else
            {
                break;
            }
        }
    }
public void saveToFile(XML xml) throws IOException {
    FileWriter writer = new FileWriter(this.filePath);
    writer.write(xml.toString());
    writer.close();

}
}





