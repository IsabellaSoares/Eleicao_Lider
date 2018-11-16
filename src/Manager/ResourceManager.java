/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Manager;

import Model.Message;
import Model.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Isabella
 */
public class ResourceManager {
    
    private List<Resource> resourceList;
    
    public ResourceManager(){
        // iniciar a lista com um valor fixo de recursos
        resourceList = new ArrayList<>();
    }
    
    public void addResource(Resource resource){
        resourceList.add(resource);
    }
    
    public Resource removeResource(int resourceId){
        for(int i=0; i<resourceList.size(); i++){
            Resource resource = resourceList.get(i);
            if(resource.getResourceId()==resourceId){
                resourceList.remove(i);
                return resource;
            }
        }
        return null;
    }
    
    public boolean isUsing(int resourceId){
        for(Resource resource : resourceList){
            if(resource.getResourceId()==resourceId){
                return true;
            }
        }
        return false;
    }
    
    public void putMessage(int resourceId, Message message){
        for(Resource resource : resourceList){
            if(resource.getResourceId()==resourceId){
                resource.putMessage(message);
                return;
            }
        }
    }
    
    public boolean isEmpty(){
        return resourceList.isEmpty();
    }
    
    public void printResourceId(){
        System.out.print("[");
        for(int i=0; i<resourceList.size(); i++){
            Resource resource = resourceList.get(i);
            System.out.print(String.valueOf(resource.getResourceId()));
            if(i<(resourceList.size()-1)){
                System.out.print(" - ");
            }
        }
        System.out.print("]");
    }
}
