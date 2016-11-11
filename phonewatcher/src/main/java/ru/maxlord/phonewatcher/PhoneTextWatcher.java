package ru.maxlord.phonewatcher;

import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;

/**
 *  Phone number Watcher
 * 
 * @author Lord (Kuleshov M.V.)
 * @since 11.11.2016
 *
 */
public class PhoneTextWatcher implements TextWatcher {

	private boolean mIsFormatting;
	private boolean mDeletingHyphen;
	private int mHyphenStart;
	private boolean mDeletingBackward;

	@Override
	public void afterTextChanged(Editable text) {
		if (mIsFormatting)
			return;

		mIsFormatting = true;

		// If deleting hyphen, also delete character before or after it
		if (mDeletingHyphen && mHyphenStart > 0) {
			if (mDeletingBackward) {
				if (mHyphenStart - 1 < text.length()) {
					text.delete(mHyphenStart - 1, mHyphenStart);
				}
			} else if (mHyphenStart < text.length()) {
				text.delete(mHyphenStart, mHyphenStart + 1);
			}
			if (text.length() == 7) {
				text.delete(text.length() - 1, text.length());
			}
			if (text.length() == 2) {
				text.append(" (");
			}
		} else {
			if (text.length() == 2) { // +7
				text.append(" (");
			} else if (text.length() == 7) {
				text.append(") ");
			} else if (text.length() == 12 || text.length() == 15) {
				text.append("-");
			}	
		}
		
		mIsFormatting = false;
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		if (mIsFormatting)
			return;

		// Make sure user is deleting one char, without a selection
		final int selStart = Selection.getSelectionStart(s);
		final int selEnd = Selection.getSelectionEnd(s);
		if (s.length() > 1 // Can delete another character
				&& count == 1 // Deleting only one character
				&& after == 0 // Deleting
				&& (s.charAt(start) == '-' || s.charAt(start) == ' ' || s.charAt(start) == '(' || s.charAt(start) == ')') // a hyphen
				&& selStart == selEnd) { // no selection
			mDeletingHyphen = true;
			mHyphenStart = start;
			// Check if the user is deleting forward or backward
			if (selStart == start + 1) {
				mDeletingBackward = true;
			} else {
				mDeletingBackward = false;
			}
		} else {
			mDeletingHyphen = false;
		}
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {

	}
}
