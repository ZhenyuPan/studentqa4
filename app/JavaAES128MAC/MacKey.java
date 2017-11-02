package JavaAES128MAC;

import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;
public class MacKey 
{
	    /*@function Generate AES(id,Rand)
	     * @param :id the the id allocation to user
	     * @param :Rand the random number used for activation User
	     **/
	    static String padding(int Number)
	    {
	    	String Result = ""+Number;
	    	/*128 bit = 16 char */
	    	while( Result.length() < 16)
	    	{
	    		Result+= '0';
	    		//padding zero at the end
	    	}
	    	return Result;
	    }
      public  static String generateMacKey(int Id,int Rand)
        {
        	String PaddingID= padding(Id);
        	String PaddingRand= padding(Rand);
        	String Result ="";
        	Key aesKey = new SecretKeySpec(PaddingRand.getBytes(), "AES");
        	try {
				Cipher cipher = Cipher.getInstance("AES");
				cipher.init(Cipher.ENCRYPT_MODE, aesKey);
				byte[] encrypted = cipher.doFinal(PaddingID.getBytes());
				byte[] encodedBytes = Base64.encodeBase64(encrypted);
				for( int i = 0 ; i < encrypted.length ; i++)
				{
					if(encodedBytes[i] == '+')
					{
						encodedBytes[i] ='-';
					}
					if(encodedBytes[i] == '/')
					{
						encodedBytes[i] ='_';
					}
					Result +=(char) encodedBytes[i];
				}
				return Result;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	return "";
        }
}
