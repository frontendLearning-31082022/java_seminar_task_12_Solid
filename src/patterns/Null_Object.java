package patterns;

public class Null_Object {
    public static void main(String[] args) {
        Person person= PersonFactory.getPerson();
        new Module1().indexFields(person);
        new Module2().checkValue(person);
        new Module3().iterateValue(person);
    }
}

interface Person{}
class PersonFactory{
    public static Person getPerson(){
        Person person=new PersonHuman();
        if (person==null)return new NoNullCheckerEveryWhere();
        return person;
    }

    private static class PersonHuman implements Person{
        PersonHuman(){readFromSQL();}
        private void readFromSQL() {}
    }
}

class NoNullCheckerEveryWhere implements Person{} //empty fields and meths for logic - no NullPointerExcep
class Module1{ void indexFields(Person person){}}
class Module2{void checkValue(Person person){}}
class Module3{void iterateValue(Person person){}}
