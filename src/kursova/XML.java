package kursova;
import java.util.*;

public class XML {
    private String nodeName;
    private Map<String,String> attributes;
    private List<XML>children;
    private String content;
    private XML parent;
    private static XML root;



    public XML() {
        this.attributes = new HashMap<>();
        this.children = new ArrayList<>();
    }
    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void addAttribute(String name, String value) {
        attributes.put(name, value);
    }

    public List<XML> getChildren() {
        return children;
    }

    public void addChild(XML child) {
        children.add(child);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public XML getParent() {
        return parent;
    }

    public void setParent(XML parent) {
        this.parent = parent;
    }
    public void printXML(String indent)
    {
        System.out.println(indent+"Node name: "+this.getNodeName());
        Map<String,String> attr=this.getAttributes();
        if(!attr.isEmpty())
        {
            System.out.println(indent + "Attributes:");
            for (Map.Entry<String, String> entry : attributes.entrySet()) {
                System.out.println(indent + "  " + entry.getKey() + " = " + entry.getValue());
            }
        }
        if(this.content!=null) {
            System.out.println(indent + "Contents: " + this.getContent());
        }
        List<XML>children=this.getChildren();
        if(!children.isEmpty())
        {
            System.out.println(indent+"Children: ");
            for(XML child :children)
            {
                child.printXML(indent+indent);
            }
        }
        }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        XML xml = (XML) o;

        if (!Objects.equals(nodeName, xml.nodeName)) return false;
        if (!Objects.equals(attributes, xml.attributes)) return false;
        if (!Objects.equals(children, xml.children)) return false;
        if (!Objects.equals(content, xml.content)) return false;
        return Objects.equals(parent, xml.parent);
    }

    @Override
    public int hashCode() {
        int result = nodeName != null ? nodeName.hashCode() : 0;
        result = 31 * result + (attributes != null ? attributes.hashCode() : 0);
        result = 31 * result + (children != null ? children.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (parent != null ? parent.hashCode() : 0);
        return result;
    }
    public String getAttributeByNode(String nodeName, String attributeName)
    {
        String result="";
        if(this.nodeName.equals(nodeName))
        {
            if(!this.attributes.isEmpty())
            {
                for(Map.Entry<String,String>entry:this.attributes.entrySet())
                        {
                            if(attributeName!=null) {
                                if (entry.getKey().equals(attributeName)) {
                                    result = "Node: " + this.nodeName + " Attribute: " + attributeName + " = " + entry.getValue() + "\n";
                                }
                            }
                            else {
                                result = "Node: " + this.nodeName + " Attribute: " + entry.getKey() + " = " + entry.getValue() + "\n";
                            }
                            }
                        }
            }
        for(XML x:this.getChildren())
        {
            result+=x.getAttributeByNode(nodeName,attributeName);
        }
        return result;
    }
    public int setAttributeByNode(String nodeName, String attributeName, String attributeValue)
    {
        String result="";
        int count=0;
        if(this.nodeName.equals(nodeName))
        {
            if(!this.attributes.isEmpty())
            {
                for(Map.Entry<String,String>entry:this.attributes.entrySet())
                {
                    if(entry.getKey().equals(attributeName))
                    {
                        entry.setValue(attributeValue);
                        count++;
                    }
                }
            }
        }
        for(XML x:this.getChildren())
        {
            count+=x.setAttributeByNode(nodeName,attributeName,attributeValue);
        }
        return count;
    }
    public String getTextByNode(String nodeName)
    {
        String result="";
        if(this.nodeName.equals(nodeName))
        {
            result+=this.content+"\n";
        }
        for(XML child: this.children)
        {
            result+=child.getTextByNode(nodeName);
        }
        return result;

    }

    public static XML getRoot() {
        return root;
    }

    public static void setRoot(XML root) {
        XML.root = root;
    }

    public String toString()
    {
        int indentCnt=0;
        XML parent=this.parent;
        while(parent!=null)
        {
            indentCnt++;
            parent=parent.parent;
        }
        String indent="\t".repeat(indentCnt);
        StringBuilder result=new StringBuilder();
        result.append(indent);
        result.append("<");
        result.append(this.nodeName);
        if(!this.attributes.isEmpty())
        {
            result.append(" ");
            for(Map.Entry<String,String>element:this.attributes.entrySet())
            {
                result.append(element.getKey());
                result.append("=\"");
                result.append(element.getValue());
                result.append("\" ");
            }
        }
        result.append(">");
        if(this.content!=null) {
            result.append(this.content);
        }

        for(XML x: this.children)
        {
            result.append(x.toString());
        }
        result.append("</");
        result.append(this.nodeName);
        result.append(">\n");
        return result.toString();
    }
}
