package TD2;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class PasswordRun implements Runnable{
	
	private String passEncrypted;
	private String passUncrypted = null;
	private int uncryptedLength;

	private PasswordRun(String passUncrypted)
			throws NoSuchAlgorithmException {
		this.passEncrypted = encryptPassword(passUncrypted);
		this.uncryptedLength = passUncrypted.length();
		this.passUncrypted = passUncrypted;
	}
	
	private PasswordRun(String passEncrypted, int uncryptedLength) {
		this.passEncrypted = passEncrypted;
		this.uncryptedLength = uncryptedLength;
	}

	private String encryptPassword(String pass) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("MD5");

		byte[] passBytes = pass.getBytes();

		md.update(passBytes);
		byte[] digest = md.digest(passBytes);

		StringBuilder sb = new StringBuilder();
		for (byte b : digest) { // convert to hex
			sb.append("0123456789ABCDEF".charAt((b & 0xF0) >> 4));
			sb.append("0123456789ABCDEF".charAt((b & 0x0F)));
		}
		return sb.toString();
	}
	
	private String generateRandomWord(int wordLength) {
		Random r = new Random(); // Initialize a Random Number Generator with
									// SysTime as the seed
		StringBuilder sb = new StringBuilder(wordLength);
		for (int i = 0; i < wordLength; i++) { // For each letter in the word
			char tmp = (char) ('a' + r.nextInt(26)); // Generate a letter
														// between a and z
			sb.append(tmp); // Add it to the String
		}
		return sb.toString();
	}

	private String randomSearch(int passLength) throws NoSuchAlgorithmException {
		System.out.println(Thread.currentThread().getName() + " cherche "
				+ this.passUncrypted);
		while (true) {
			String guess = generateRandomWord(passLength);
			PasswordRun crack = new PasswordRun(guess);
			if (this.passEncrypted.equals(crack.passEncrypted)) {
				System.out.println("your password is: " + guess);
				return guess;
			}
		}
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
			try {
				randomSearch(uncryptedLength);
				
			}catch(NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
		
	}
	

public static void main(String[] args) throws
	NoSuchAlgorithmException, InterruptedException {
	
	int uncryptedLength;
	String[] crypted = { "aaa", "bbbb", "ccc", "azert" };

	/*for (String s : passList) {
	
		*/
		
		
		
	
	
	PasswordRun[] pwd = new PasswordRun[crypted.length];
	Thread[] threads = new Thread[crypted.length];
	for(int i=0; i<crypted.length;i++) {
		pwd[i] = new PasswordRun(crypted[i], 5);
		threads[i] = new Thread(pwd[i]);
		threads[i].start();
	}
	for(Thread t : threads)
	{
		t.join();
	}
	}



}