package com.example.test;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TextRes {

    @SerializedName("translation")
    private List<String> translation;
    @SerializedName("basic")
    private Basic basic;
    @SerializedName("errorCode")
    private Integer errorCode;
    @SerializedName("tSpeakUrl")
    private String tSpeakUrl;

    public List<String> getTranslation() {
        return translation;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public static class Basic {
        @SerializedName("us-phonetic")
        private String usphonetic;
        @SerializedName("uk-speech")
        private String ukspeech;
        @SerializedName("speech")
        private String speech;
        @SerializedName("phonetic")
        private String phonetic;
        @SerializedName("uk-phonetic")
        private String ukphonetic;
        @SerializedName("us-speech")
        private String usspeech;
        @SerializedName("explains")
        private List<String> explains;
    }

    public String getTrans() {
        return translation.get(0);
    }

    public String getExplains() {
        if (basic != null && basic.explains != null) {
            return basic.explains.get(0);
        }
        return null;
    }

    public String getUkPho() {
        if (basic != null && basic.ukphonetic != null)
            return "  英:[" + basic.ukphonetic + "]";
        return null;
    }

    public String getUsPho() {
        if (basic != null && basic.usphonetic != null)
            return "  美:[" + basic.usphonetic + "]";
        return null;
    }

    public String getUkSpeech() {
        if (basic != null)
            return basic.ukspeech;
        return null;
    }

    public String getUsSpeech() {
        if (basic != null)
            return basic.usspeech;
        return null;
    }

    public String gettSpeakUrl() {
        return tSpeakUrl;
    }
}
