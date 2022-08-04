package Service.Util;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;

import com.google.crypto.tink.Aead;
import com.google.crypto.tink.CleartextKeysetHandle;
import com.google.crypto.tink.JsonKeysetReader;
import com.google.crypto.tink.JsonKeysetWriter;
import com.google.crypto.tink.KeyTemplates;
import com.google.crypto.tink.KeysetHandle;
import com.google.crypto.tink.aead.AeadConfig;

public class Cipher {

	private String plaintext;
	private String ciphertext;
	private String ID;
	private String Name;
	
	String keysetFilename;
	KeysetHandle keysetHandle;
	Aead aead;
	byte[] encrypted;
	byte[] decrypted;
	
	public void initTink()
	{
		try {
			AeadConfig.register();
		} 
		catch (GeneralSecurityException e) {
			e.printStackTrace();
		}
	}
	
	public void generateKey()
	{
		try 
		{
			keysetHandle = KeysetHandle.generateNew(KeyTemplates.get("AES128_GCM"));
			
		    try 
		    {
//		    	System.out.println(keysetFilename);
		    	File secret_key = new File(keysetFilename);
		    	secret_key.getParentFile().mkdirs(); 
		    	secret_key.createNewFile();
				CleartextKeysetHandle.write(keysetHandle, JsonKeysetWriter.withFile(secret_key));
			} 
		    catch (IOException e) 
		    {
				e.printStackTrace();
			}
		    
		} 
		catch (GeneralSecurityException e) 
		{
			e.printStackTrace();
		}
		
	}
	
	public void loadKeys()
	{
	    try 
	    {
			keysetHandle = CleartextKeysetHandle.read(JsonKeysetReader.withFile(new File(keysetFilename)));
		} 
	    catch (GeneralSecurityException | IOException e) 
	    {
			e.printStackTrace();
		}
	}
	
	public String encrypt()
	{
		initTink();
		generateKey();
		
		try 
		{
			aead = keysetHandle.getPrimitive(Aead.class);
			encrypted = aead.encrypt(plaintext.getBytes(StandardCharsets.ISO_8859_1), 
											keysetFilename.getBytes(StandardCharsets.ISO_8859_1));
		} 
		catch (GeneralSecurityException e) 
		{
			e.printStackTrace();
		}
		
		return new String(encrypted, StandardCharsets.ISO_8859_1);
	}
	
	public String decrypt() 
	{
		initTink();
		loadKeys();
		
		try 
		{
			aead = keysetHandle.getPrimitive(Aead.class);
			decrypted = aead.decrypt(ciphertext.getBytes(StandardCharsets.ISO_8859_1), keysetFilename.getBytes(StandardCharsets.ISO_8859_1));
		} 
		catch (GeneralSecurityException e) 
		{
			e.printStackTrace();
		}
		
		return new String(decrypted, StandardCharsets.ISO_8859_1);
	}
	
	public Cipher(String plaintext, String iD, String name, String who, boolean encrypt) {
		
		this.plaintext = plaintext;
		ID = iD;
		Name = name;
		 
		keysetFilename = "C:\\Users\\Kishore\\git\\System-Allocation-App\\System-Allocation"
							+ "\\src\\main\\java\\Service\\Util\\Secret Key\\" + who + "\\" + ID + Name + ".json";
	}
	
	public Cipher(String ciphertext, String iD, String name, String who) {
		
		this.ciphertext = ciphertext;
		ID = iD;
		Name = name;
		 
		keysetFilename = "C:\\Users\\Kishore\\git\\System-Allocation-App\\System-Allocation"
							+ "\\src\\main\\java\\Service\\Util\\Secret Key\\" + who + "\\"  + ID + Name + ".json";
	}
	
}
