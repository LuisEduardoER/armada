/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package network.datapackage;

import com.thoughtworks.xstream.XStream;

/**
 *
 * @author dolalima
 */
public class Package {
    
    private XStream serializer;
    
    private Flag flag;
    private Object data;
    
    public Package(Flag flag,Object data){
        this.flag = flag;
        this.data = data;
    }
    
    public Package(){
        
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Flag getFlag() {
        return flag;
    }

    public void setFlag(Flag flag) {
        this.flag = flag;
    }
    
    public String toXML(){
        String xml = this.serializer.toXML(this);
        xml = xml.replace("\n", "") + "\n";
        return xml;
    }
    
    public Package fromXml(String xml){
        Package pack = (Package) this.serializer.fromXML(xml);
        return pack;
    }
    
    
    
   
    

    
    
    
    
}
