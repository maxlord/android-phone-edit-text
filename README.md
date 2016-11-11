# android-phone-edit-text
Widget for enter russian phone numbers in Android application

# How To Use

```
import ru.maxlord.phonewatcher.PhoneInputFilter;
import ru.maxlord.phonewatcher.PhoneTextWatcher;


...
EditText input = (EditText)findViewById(R.id.input);
...
input.setFilters(new InputFilter[] { new PhoneInputFilter() });
input.addTextChangedListener(new PhoneTextWatcher());
```
