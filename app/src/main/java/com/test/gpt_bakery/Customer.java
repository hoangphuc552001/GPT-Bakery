package com.test.gpt_bakery;

public class Customer {
    public String City,EmailId,MobileNo,Name,State;
    public Customer(){

    }
    public Customer(String EmailId,String City,String MobileNo,String Name,String State )
    {
        this.City=City;
        this.EmailId=EmailId;
        this.MobileNo=MobileNo;
        this.Name=Name;
        this.State=State;
    }
}
