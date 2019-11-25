package model;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/**
 * This is a class for describing a customer. A customer contains:
 * 1. Name
 * 2. Tax identification number
 * 3. Address
 * 4. Pin number
 */

public class Customers{

private int tax_id;
private String name;
private String address;
private String pin= "1717";

public Customers(){
        // Default Constructor
}

public Customers(int id, String name, String address){
        this.tax_id=id;
        this.name=name;
        this.address=address;
}

public String EncryptPin(String InputPin) throws NoSuchAlgorithmException {
        return toHexString(getSHA(InputPin));
}

public static byte[] getSHA(String input) throws NoSuchAlgorithmException {
        // Static getInstance method is called with hashing SHA
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        // digest() method called
        // to calculate message digest of an input
        // and return array of byte
        return md.digest(input.getBytes(StandardCharsets.UTF_8));
}

public static String toHexString(byte[] hash) {
        // Convert byte array into signum representation
        BigInteger number = new BigInteger(1, hash);
        // Convert message digest into hex value
        StringBuilder hexString = new StringBuilder(number.toString(16));
        // Pad with leading zeros
        while (hexString.length() < 32)
        {
                hexString.insert(0, '0');
        }
        return hexString.toString();
}

public void SetPin(String OldPin,String NewPin) throws NoSuchAlgorithmException {
        if( EncryptPin(pin).equals( EncryptPin(OldPin) ) ) {
                pin = NewPin;
        }else{
                System.out.print("wrong old pin");
        }
}

public boolean VertifyPin(String InputPin) throws NoSuchAlgorithmException {
        if(EncryptPin(pin).equals(InputPin)){
                return true;
        } else{
                return false;
        }
}

public void setID(int id){this.tax_id=id;}
public void setName(String name){this.name=name;}
public void setAddress(String address){this.address=address;}
public int getID(){return tax_id;}
public String getName(){return name;}
public String getAddress(){return address;}

}