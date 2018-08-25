package com.changlie.pattern;

import org.junit.Test;

public class j2ee03CompositeEntity {
    class DependentObject1 {

        private String data;

        public void setData(String data){
            this.data = data;
        }

        public String getData(){
            return data;
        }
    }

    class DependentObject2 {

        private String data;

        public void setData(String data){
            this.data = data;
        }

        public String getData(){
            return data;
        }
    }

    class CoarseGrainedObject {
        DependentObject1 do1 = new DependentObject1();
        DependentObject2 do2 = new DependentObject2();

        public void setData(String data1, String data2){
            do1.setData(data1);
            do2.setData(data2);
        }

        public String[] getData(){
            return new String[] {do1.getData(),do2.getData()};
        }
    }

    class CompositeEntity {
        private CoarseGrainedObject cgo = new CoarseGrainedObject();

        public void setData(String data1, String data2){
            cgo.setData(data1, data2);
        }

        public String[] getData(){
            return cgo.getData();
        }
    }


    class Client {
        private CompositeEntity compositeEntity = new CompositeEntity();

        public void printData(){
            for (int i = 0; i < compositeEntity.getData().length; i++) {
                System.out.print(", Data: " + compositeEntity.getData()[i]);
            }
            System.out.println();
        }

        public void setData(String data1, String data2){
            compositeEntity.setData(data1, data2);
        }
    }

    @Test
    public void test() throws Exception{
        Client client = new Client();
        client.setData("Test", "Data");
        client.printData();
        client.setData("Second Test22", "Data222");
        client.printData();
    }
}
