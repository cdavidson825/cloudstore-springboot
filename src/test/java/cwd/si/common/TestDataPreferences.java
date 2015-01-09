package cwd.si.common;

import static org.junit.Assert.assertNull;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import cwd.cs.common.DataPreferences;
import cwd.cs.common.DataPreferences.DataPreferenceEnum;

public class TestDataPreferences
{

    @Test
    public void testPrefsNull()
    {
        DataPreferences prefs = new DataPreferences();
        assertNull(prefs.getPreference(DataPreferenceEnum.COMPRESS_DATA));
        assertNull(prefs.getPreference(DataPreferenceEnum.ENCRYPT_DATA));


    }

    @Test
    public void testCompressPrefTrue()
    {
        DataPreferences prefs = new DataPreferences();
        prefs.addPreference(DataPreferenceEnum.COMPRESS_DATA, "true");
        assertThat(prefs.getPreference(DataPreferenceEnum.COMPRESS_DATA), is("true"));
    }
    
    @Test
    public void testCompressPrefFalse()
    {
        DataPreferences prefs = new DataPreferences();
        prefs.addPreference(DataPreferenceEnum.COMPRESS_DATA, "false");
        assertThat(prefs.getPreference(DataPreferenceEnum.COMPRESS_DATA), is("false"));
    }
    
    @Test
    public void testEncryptPrefTrue()
    {
        DataPreferences prefs = new DataPreferences();
        prefs.addPreference(DataPreferenceEnum.ENCRYPT_DATA, "true");
        assertThat(prefs.getPreference(DataPreferenceEnum.ENCRYPT_DATA), is("true"));
    }
    
    @Test
    public void testEncryptPrefFalse()
    {
        DataPreferences prefs = new DataPreferences();
        prefs.addPreference(DataPreferenceEnum.ENCRYPT_DATA, "false");
        assertThat(prefs.getPreference(DataPreferenceEnum.ENCRYPT_DATA), is("false"));
    }
    
}
