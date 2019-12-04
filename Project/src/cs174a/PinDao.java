package cs174a;

import java.util.List;
import java.util.Map;

public class PinDao {

    private DBExecutor dbExecutor;

    public PinDao(){
        dbExecutor = new DBExecutor();
    }

    public boolean initialPin(String customerId){
        String sql = "INSERT INTO PINs VALUES(?,?)";
        String initPin = "1717";
        Object[] objects = {customerId, initPin};
        return dbExecutor.runUpdate(sql, objects);
    }

    public boolean verifyPin(String customerId, String pin){
        String sql = "SELECT * FROM PINs WHERE tax_id = " + customerId;
        List<Map<String, Object>> list = dbExecutor.query(sql);
        if(list != null && list.size() != 0){
            String exactPin = (String)list.get(0).get("pin");
            return exactPin.equals(pin);
        }
        return false;
    }

    public boolean setPin(String customerId, String oldPin, String newPin){
        if(verifyPin(customerId, oldPin)){
            String sql = "UPDATE PINs SET pin = " + newPin + " WHERE tax_id = " + customerId;
            return dbExecutor.runUpdate(sql);
        }
        return false;
    }

    //public String EncryptPin(String InputPin) throws NoSuchAlgorithmException {
    //        return toHexString(getSHA(InputPin));
    //}
    //
    //public static byte[] getSHA(String input) throws NoSuchAlgorithmException {
    //        // Static getInstance method is called with hashing SHA
    //        MessageDigest md = MessageDigest.getInstance("SHA-256");
    //        // digest() method called
    //        // to calculate message digest of an input
    //        // and return array of byte
    //        return md.digest(input.getBytes(StandardCharsets.UTF_8));
    //}
    //
    //public static String toHexString(byte[] hash) {
    //        // Convert byte array into signum representation
    //        BigInteger number = new BigInteger(1, hash);
    //        // Convert message digest into hex value
    //        StringBuilder hexString = new StringBuilder(number.toString(16));
    //        // Pad with leading zeros
    //        while (hexString.length() < 32)
    //        {
    //                hexString.insert(0, '0');
    //        }
    //        return hexString.toString();
    //}
    //
    //public void SetPin(String OldPin,String NewPin) throws NoSuchAlgorithmException {
    //        if( EncryptPin(pin).equals( EncryptPin(OldPin) ) ) {
    //                pin = NewPin;
    //        }else{
    //                System.out.print("wrong old pin");
    //        }
    //}
    //
    //public boolean VertifyPin(String InputPin) throws NoSuchAlgorithmException {
    //        if(EncryptPin(pin).equals(InputPin)){
    //                return true;
    //        } else{
    //                return false;
    //        }
    //}

}
