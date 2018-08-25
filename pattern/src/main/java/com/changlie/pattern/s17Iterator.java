package com.changlie.pattern;

import org.junit.Test;

public class s17Iterator {
    interface Iterator {
        public boolean hasNext();
        public Object next();
    }
    interface Container {
        public Iterator getIterator();
    }

    class NameRepository implements Container {
        public String names[] = {"Robert" , "John" ,"Julie" , "Lora"};

        @Override
        public Iterator getIterator() {
            return new NameIterator();
        }

        class NameIterator implements Iterator {

            int index;
            int len = names.length;

            @Override
            public boolean hasNext() {
                if(index < len){
                    return true;
                }
                return false;
            }

            @Override
            public Object next() {
                if(this.hasNext()){
                    return names[index++];
                }
                return null;
            }
        }
    }

    @Test
    public void test() throws Exception{
        NameRepository namesRepository = new NameRepository();

        for(Iterator iter = namesRepository.getIterator(); iter.hasNext();){
            String name = (String)iter.next();
            System.out.println("Name : " + name);
        }
    }
}
