package org.example;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * @author Bitt3r
 * <p>
 * <p>
 * Once you finish setup of your Perfect Money account and enable API, you need to whitelist IP address from which you are making calls. <br></br>
 * <p>
 * <p>
 * <b>AccountID</b> at line 57 needs to be populated with your AccountID, which you are using to Sign in. <br></br>
 * <b>Passphrase</b> at line 58 needs to be populated with your password, which you are using to Sign in. <br></br>
 * Account can range from U-USD, E-EUR, G-GOLD, B-BTC. <br></br>
 * No matter account type you only use first leter followed by numbers.
 * <p>
 * <p>
 * In Case of unverified with populated account name: <br>
 * Status: 200 body: "Username, Not verified"
 * <p>
 * <p>
 * In case of verified with account name populated: <br>
 * Status: 200 body: "Username, Verified"
 * <p>
 * <p>
 * In case of verified or not verified with non-populated account name: <br>
 * Status: 200 body: ",Verified"
 * <p>
 * <p>
 * In case of non-existing account: <br>
 *  * Status: 200 body: "ERROR: Invalid Account"
 */
public class Main {

    public static void main(String[] args) {

        while (true) {

            Scanner scan = new Scanner(System.in);
            RestTemplate rest = new RestTemplate();
            System.out.println("Wallet numbers start with E, U, G or B followed by numbers");
            System.out.print("Enter number of wallet for validation: \n");

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
            map.add("AccountID", "Your account id");
            map.add("PassPhrase", "Your passphrase");
            map.add("Account", scan.nextLine());

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

            ResponseEntity<String> response = rest.postForEntity("https://perfectmoney.is/acct/acc_name.asp", request, String.class);

            System.out.printf("Status: %s body: \"%s\"%n", response.getStatusCode().toString(), response.getBody());

            if (response.getBody() != null) {
                if ("ERROR: Invalid Account".equals(response.getBody())) {
                    System.out.println("INVALID ACCOUNT");
                } else if (response.getBody().length() == 0 || response.getBody().contains("Not verified")) {
                    System.out.printf("Unverified account that has account name: %s \n", new StringTokenizer(response.getBody(), ",").nextToken().trim());
                } else if (response.getBody().equals(",Verified")) {
                    System.out.println("Verified account without account name !");
                } else {
                    System.out.println("Returned account: " + response.getBody());
                }
            } else {
                System.out.println("CRITICAL");
            }

            if("exit".equalsIgnoreCase(scan.nextLine())) {
                System.out.println("Bye bye");
                scan.close();
                break;
            }
        }
    }
}
