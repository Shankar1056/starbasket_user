package apextechies.starbasket.sendmailinbackground;

import android.os.AsyncTask;
import android.util.Log;

import apextechies.starbasketseller.common.AppConstants;


/**
 * Created by GsolC on 2/24/2017.
 */

public class LongOperation extends AsyncTask<Void, Void, String> {
    private String email,otp;
    public LongOperation(){}
    @Override
    protected String doInBackground(Void... params) {
        try {

//            GMailSender sender = new GMailSender("sender.sendermail.com", "senders password");
//            sender.sendMail("subject",
//                    "body",
//                    "sender.sendermail.com",
//                    "reciepients.recepientmail.com");
//
            GMailSender sender = new GMailSender("dayashankargupta86@gmail.com", "gitajaanu!@#");
            sender.sendMail("OTP - CallUsPlz",
                    "OTP to reset your password: "+otp,"dayashankargupta86@gmail.com",
                    email);

        } catch (Exception e) {
            Log.e("error", e.getMessage(), e);
            return "Email Not Sent";
        }
        return "Email Sent";
    }

    @Override
    protected void onPostExecute(String result) {

        Log.e("LongOperation",result+"");
    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected void onProgressUpdate(Void... values) {
    }

    public void emailOtp(String email, String otp){

        this.email = email;
        this.otp = otp;
    }
}
