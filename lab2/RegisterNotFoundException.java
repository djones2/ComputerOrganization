/* Exception for incorrect register declaration */
public class RegisterNotFoundException extends Exception {
	public RegisterNotFoundException(String msg) {
		super(msg);
	}
}
