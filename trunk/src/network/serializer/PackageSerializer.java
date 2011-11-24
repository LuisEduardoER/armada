/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package network.serializer;

import com.thoughtworks.xstream.XStream;
import network.datapackage.Package;
import network.object.ClientData;
import network.object.LocalGameData;
import network.object.RoomData;

/**
 *
 * @author dolalima
 */
public abstract class PackageSerializer {

    XStream serializer;

    public PackageSerializer() {
        this.serializer.alias("package", Package.class);
        this.serializer.alias("roomData", RoomData.class);
        this.serializer.alias("clientData", ClientData.class);
        this.serializer.alias("localGameData", LocalGameData.class);

    }

    public String serializerRoom(RoomData roomInfo) {
        
        String xml = "";
        xml = this.serializer.toXML(roomInfo);
        xml = xml.replace("\n", "")+"\n";
        
        //this.serializer.

        return xml;
    }
    
    public String serializerClient(ClientData clientInfo){
        
        String xml = "";
        xml = this.serializer.toXML(clientInfo);
        xml = xml.replace("\n", "")+"\n";

        return xml;
        
    }
    
   // public String serializer
}
