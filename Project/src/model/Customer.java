package model;

public class Customer{

        private String tax_id;
        private String name;
        private String address;

        public Customer(){

        }

        public Customer(String id, String name, String address){
                this.tax_id=id;
                this.name=name;
                this.address=address;
        }

        public void setId(String id){this.tax_id=id;}
        public void setName(String name){this.name=name;}
        public void setAddress(String address){this.address=address;}
        public String  getId(){return tax_id;}
        public String getName(){return name;}
        public String getAddress(){return address;}


}