package ru.maxlord.phonewatcher;

import android.text.InputType;
import android.text.Spanned;
import android.text.method.NumberKeyListener;

/**
 * Filter for enter phone number
 * 
 * @author Lord (Kuleshov M.V.)
 * @since 11.11.2016
 *
 */
public class PhoneInputFilter extends NumberKeyListener {
	private String mPattern;
	
	public PhoneInputFilter() {
		mPattern = "^(\\+{1,1}(7([ ]{1,1}(\\({1,1}(\\d{1,1}(\\d{1,1}(\\d{1,1}(\\){1,1}([ ]{1,1}(\\d{1,1}(\\d{1,1}(\\d{1,1}(-(\\d{1,1}(\\d{1,1}(-(\\d{1,1}(\\d{1,1}?)?)?)?)?)?)?)?)?)?)?)?)?)?)?)?)?)?)?";
	}

	@Override
	public int getInputType() {
		return InputType.TYPE_CLASS_PHONE;
	}

	@Override
	protected char[] getAcceptedChars() {
		return new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '-', '+', ' ', '(', ')' };
	}
	
	@Override
	public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        if (end > start) {
            String destTxt = dest.toString();
            String resultingTxt = destTxt.substring(0, dstart) + source.subSequence(start, end) + destTxt.substring(dend);

            // Phone number must be equals to format +7 (XXX) XXX-XX-XX
            if (!resultingTxt.matches(mPattern)) { 
                return "";
            }
        } else { // in remove-mode
        	if (dest.length() <= "+7 (".length()) {
        		return "";
        	}
        }
        
        return null;
	}
}
