package com.test.gpt_bakery;

public class Customer {
    public String City,EmailId,MobileNo,Name,State,times,cakes,prices,codes;
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

    public Customer(String city, String mobileNo, String name, String state, String times, String cakes, String prices,String codes) {
        City = city;
        MobileNo = mobileNo;
        Name = name;
        State = state;
        this.times = times;
        this.cakes = cakes;
        this.prices = prices;
        this.codes=codes;
    }
}
