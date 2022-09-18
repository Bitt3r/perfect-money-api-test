# perfect-money-api-test
Due to no documentation on this payment processor, came up with quick solution to test API

 Once you finish setup of your Perfect Money account and enable API, you need to whitelist IP address from which you are making calls.
 <b>AccountID</b> at line 57 needs to be populated with your AccountID, which you are using to Sign in.
 <b>Passphrase</b> at line 58 needs to be populated with your password, which you are using to Sign in.
 Account can range from U-USD, E-EUR, G-GOLD, B-BTC. <br>
 No matter account type you only use first leter followed by numbers.

 * In Case of unverified with populated account name: <br>
   Status: 200 body: "Username, Not verified"

 * In case of verified with account name populated: <br>
   Status: 200 body: "Username, Verified"
   
 * In case of verified or not verified with non-populated account name: <br>
   Status: 200 body: ",Verified"

 * In case of non-existing account: <br>
   Status: 200 body: "ERROR: Invalid Account"
