package patterns;

import java.util.*;

final public class ImmutableClass {

    // Thread safe
    // final no extend
    // no setters
    // get objs new ids

    private String name;
    private Map<String,String> fields;
    private List<Date> someObject;

    public ImmutableClass(String name, Map<String, String> fields, List<Date> someObject) {
        this.name = name;
        this.fields =  Collections.unmodifiableMap( fields);

        this.someObject = (List<Date>) deepCopy(someObject);
    }

    public List<Date> getSomeObject() {return (List<Date>) deepCopy(this.someObject);}

    private Object deepCopy(List<Date> someObject){
        ArrayList someObjectClone=new ArrayList();
        for (Date date : someObject) {
            someObjectClone.add(date.clone());
        }
        return someObjectClone;
    }
    public String getName() {return name;}
    public Map<String, String> getFields() {return  fields;}

    public static void main(String[] args) {
        HashMap<String,String>testHashmap=new HashMap<>();
        testHashmap.put("one","1");
        testHashmap.put("two","2");
        ArrayList<Date>someObj=new ArrayList<>();
        someObj.add(new Date());
        someObj.add(new Date(0));

        ImmutableClass immutableClass=new ImmutableClass("Test",testHashmap,someObj);

        System.out.println("Map - 1: "+System.identityHashCode(testHashmap)+"\t 2: "+System.identityHashCode(immutableClass.getFields()));

        System.out.println("Arraylist obj(0) 1:"+System.identityHashCode(someObj.get(0))
                +" 2:"+System.identityHashCode(immutableClass.getSomeObject().get(0)));

        new String();
    }
}
